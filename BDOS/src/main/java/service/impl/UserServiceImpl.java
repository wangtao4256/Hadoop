package service.impl;

import dao.UserDao;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.User;
import service.UserService;

import java.io.Serializable;
import java.util.List;

/**
 * @author tao.wang
 * @Title: UserServiceImpl
 * @Description: 用户操作实现类
 * 需要序列化
 */
@Service
public class UserServiceImpl implements UserService, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    public boolean insertBatch(List<User> user) {
        boolean flag = false;
        try {
            userDao.insertBatch(user);
            logger.info("批量新增" + user.size() + "条用户数据成功");
        } catch (Exception e) {
            logger.error("批量插入用户数据失败，原因是：" + e);
        }
        return flag;

    }

    public List<User> findByUser(User user) {
        return userDao.findByUser(user);
    }
}
