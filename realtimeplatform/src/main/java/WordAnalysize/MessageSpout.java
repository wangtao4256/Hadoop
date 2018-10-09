package WordAnalysize;


import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.util.Map;

/**
 * Title:MessageSpout
 * Description:发送信息
 *
 * @author tao.wang
 */
public class MessageSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private static final String field = "word";
    private int count = 1;
    private String[] message = {"My name is wangtao",
            "I love Messi",
            "I'm proud of what i'm working on"};

    /**
     * open()方法中是在ISpout接口中定义，在Spout组件初始化时被调用。
     * 有三个参数:
     * 1.Storm配置的Map;
     * 2.topology中组件的信息;
     * 3.发射tuple的方法;
     */
    public void open(Map map, TopologyContext context, SpoutOutputCollector collector) {
        System.out.println("open" + map.get("test"));
        this.collector = collector;
    }

    /**
     * Spout的核心方法
     * 主要执行方法，用于输出信息，通过collector.emit方法发射
     */
    public void nextTuple() {
        if (count <= message.length) {
            System.out.println("第" + count + "次发送消息");
            this.collector.emit(new Values(message[count - 1]));
        }
        count++;
    }

    /**
     * declareOutputFields是在IComponent接口中定义，用于声明数据格式。
     * 即输出的一个Tuple中，包含几个字段。
     */
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        System.out.println("定义格式");
        declarer.declare(new Fields("word"));
    }
}
