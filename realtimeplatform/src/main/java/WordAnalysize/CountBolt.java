package WordAnalysize;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tao.wang
 * @Description 统计单词出现的个数
 */
public class CountBolt extends BaseRichBolt {
    private HashMap<String, Integer> counts = null;
    private long count = 1;

    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        System.out.println("prepare" + stormConf.get("test"));
        this.counts = new HashMap<String, Integer>();

    }

    public void execute(Tuple input) {
        String msg = input.getStringByField("count");
        /**
         *如果不包含该单词，则说明是第一次出现，否则进行加一
         */
        if (!counts.containsKey("msg")) {
            counts.put(msg, 1);
        } else {
            counts.put(msg, counts.get(msg) + 1);
        }
        count++;

    }

    /**
     * cleanup是IBolt接口中定义,用于释放bolt占用的资源。
     * Storm在终止一个bolt之前会调用这个方法。
     */
    public void cleanup() {
        System.out.println("=======开始显示单词数量=========");
        for (Map.Entry<String, Integer> entry : counts.entrySet()
                ) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        System.out.println("=======结束========");
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
