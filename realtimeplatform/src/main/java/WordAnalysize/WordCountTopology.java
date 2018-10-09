package WordAnalysize;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;

/**
 * @author tao.wang
 * @Description storm词频统计测试
 */
public class WordCountTopology {
    private static final String word_spout = "word_spout";
    private static final String word_bolt1 = "word_bolt1";
    private static final String word_bolt2 = "word_bolt2";

    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, InterruptedException {
        //定义一个拓扑
        TopologyBuilder builder = new TopologyBuilder();
        //设置spout为一个线程
        builder.setSpout(word_spout, new MessageSpout(), 1);
        //设置一个线程，一个task
        //shuffleGrouping表示随机分组
        builder.setBolt(word_bolt1, new WordBolt(), 1).setNumTasks(1).shuffleGrouping(word_spout);
        //设置一个线程和一个task
        builder.setBolt(word_bolt2, new CountBolt(), 1).shuffleGrouping(word_bolt1).setNumTasks(1);
        Config conf = new Config();
        conf.put("test", "Wang");
        if (args != null && args.length > 0) {
            System.out.println("远程提交模式");
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        } else {
            System.out.println("运行本地模式");
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("WordCount", conf, builder.createTopology());
            Thread.sleep(20000);
            cluster.shutdown();
        }

    }
}
