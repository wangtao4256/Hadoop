package WordAnalysize;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.Map;

/**
 * @author tao.wang
 * @Description 用于分割单词
 */
public class WordBolt extends BaseRichBolt {

    private OutputCollector collector;


    /**
     * 在Bolt启动前执行，提供Bolt启动环境配置的入口
     * 一般对于不可序列化的对象进行实例化。
     * 注:如果是可以序列化的对象，那么最好是使用构造函数。
     */
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        System.out.println("prepare" + stormConf.get("test"));
        this.collector = collector;

    }

    /**
     * execute()方法是Bolt实现的核 心。
     * 也就是执行方法，每次Bolt从流接收一个订阅的tuple，都会调用这个方法。
     */
    public void execute(Tuple input) {
        String msg = input.getStringByField("word");
        System.out.println("开始分割句子" + msg);
        String[] words = msg.toLowerCase().split(" ");
        for (String word : words
                ) {
            this.collector.emit(new Values(word));

        }

    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("count"));
    }

    public void cleanup() {
        System.out.println("清理释放资源");
    }
}

