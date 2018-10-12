package storm.bolt;

import com.alibaba.fastjson.JSON;
import constant.Constants;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.User;
import service.UserService;
import util.GetSpringBean;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author tao.wang
 * @Title: InsertBolt
 * @Description: 写入数据的bolt
 */
public class InsertBolt extends BaseRichBolt {
    private static final Logger logger = LoggerFactory.getLogger(InsertBolt.class);

    private UserService userService;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        userService = GetSpringBean.getBean(UserService.class);
    }

    @Override
    public void execute(Tuple input) {
        String msg = input.getStringByField(Constants.FIELD);
        try {
            List<User> users = JSON.parseArray(msg, User.class);
            if (users.size() > 0 && users != null) {
                Iterator<User> iterator = users.iterator();
                while (iterator.hasNext()) {
                    User user = iterator.next();
                    if (user.getAge() < 10) {
                        logger.warn("Bolt移除的数据" + user);
                        iterator.remove();
                    }
                }
                if (users.size() > 0 && users != null) {
                    userService.insertBatch(users);
                }

            }
        } catch (Exception e) {
            logger.error("Bolt处理数据失败", msg, e);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
