package kafka;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.List;
import java.util.Properties;

/**
 * @author tao.wang
 * @Title: KafkaProducerUtil
 * @Description: kafka消息生产者
 */
public class KafkaProduceUtil {
    /**
     * 向kafka发送单条消息
     *
     * @param msg       发送的消息
     * @param url       发送的地址
     * @param topicName 消息名称
     * @return
     * @throws Exception
     */
    public static boolean sendMessage(String msg, String url, String topicName) {
        KafkaProducer<String, String> producer = null;
        Properties props = init(url);
        boolean flag = false;
        try {
            producer = new KafkaProducer<String, String>(props);
            producer.send(new ProducerRecord(topicName, msg));
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
        return flag;
    }

    /**
     * 向kafka发送批量消息
     *
     * @param listMsg   发送的消息
     * @param url       发送的地址
     * @param topicName 消息名称
     * @return
     * @throws Exception
     */
    public static boolean sendMessage(List<String> listMsg, String url, String topicName) {
        KafkaProducer<String, String> producer = null;
        boolean flag = false;
        Properties prop = init(url);
        try {
            producer = new KafkaProducer<String, String>(prop);
            for (String msg : listMsg) {
                producer.send(new ProducerRecord<String, String>(topicName, msg));

            }
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }

        return flag;
    }

    /**
     * 初始化配置
     *
     * @param url kafka地址,多个地址则用‘,’隔开
     * @return
     */
    private static Properties init(String url) {
        Properties prop = new Properties();
        prop.put("bootstrap.servers", url);
        //acks=0 生产者不会等待kafka响应
        //acks=1 kafka会把这条消息写进本地日志文件中，但是不会等待集群中其他机器成功响应。
        //acks=all leader会等待所有follower同步完成，确保消息不会丢失，除非kafka所有机器坏掉。最强的可用性保证。
        prop.put("acks", "all");
        //配置大于0的值，客户端会在消息发送失败时重新发送
        prop.put("retries", 0);
        //当多条消息尝试发送到同一分区时，生产者会尝试合并网络请求。会提高client和生产者的效率
        prop.put("batch.size", 16384);
        prop.put("key.serializer", StringSerializer.class.getName());
        prop.put("value.serializer", StringSerializer.class.getName());
        return prop;
    }
}
