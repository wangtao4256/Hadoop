
package storm.Spout;

import com.alibaba.fastjson.JSON;
import config.ApplicationConfiguration;
import constant.Constants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.User;
import util.GetSpringBean;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author tao.wang
 * @Title: KafkaInsertDataSpout
 * @Description: 从kafka获取新增数据
 */
public class KafkaInsertDataSpout extends BaseRichSpout {
    private static final Logger logger = LoggerFactory.getLogger(KafkaInsertDataSpout.class);
    private SpoutOutputCollector collector;
    private KafkaConsumer<String, String> consumer;
    private ConsumerRecords<String, String> msglist;
    private ApplicationConfiguration app;

    @SuppressWarnings("rawtypes")
    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        app = GetSpringBean.getBean(ApplicationConfiguration.class);
        kafkaInit();
        this.collector = collector;
    }

    @Override
    public void nextTuple() {
        for (; ; ) {
            String msg = "";
            msglist = consumer.poll(1000);
            if (msglist != null && !msglist.isEmpty()) {
                for (ConsumerRecord<String, String> record : msglist) {
                    msg = record.value();
                    if (msg == null & "".equals(msg.trim())) ;
                    continue;
                }
                List<User> list = new ArrayList<User>();
                try {
                    list.add(JSON.parseObject(msg, User.class));
                } catch (Exception e) {
                    logger.error("数据格式不对" + msg);
                    continue;
                }
                logger.info("发射列表：" + list);
                this.collector.emit(new Values(JSON.toJSONString(list)));
                consumer.commitAsync();
            } else {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    logger.error("暂停失败" + e);
                }
            }
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields(Constants.FIELD));
    }


    private void kafkaInit() {
        Properties props = new Properties();
        props.put("bootstrap.servers", app.getServers());
        props.put("max.poll.records", app.getMaxPollRecords());
        props.put("enable.auto.commit", app.getAutoCommit());
        props.put("group.id", app.getGroupId());
        props.put("auto.offset.reset", app.getCommitRule());
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<String, String>(props);
        String topic = app.getTopicName();
        this.consumer.subscribe(Arrays.asList(topic));
        logger.info("消息队列[" + topic + "] 开始初始化...");
    }
}

