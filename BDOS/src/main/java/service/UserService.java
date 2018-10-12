package service;

import pojo.User;

import java.util.List;

/**
 * Title: UserService
 * Description:用户接口
 *
 * @author tao.wang
 */
public interface UserService {

    /**
     * 批量新增用户
     *
     * @param user
     * @return
     */
    boolean insertBatch(List<User> user);


    /**
     * 查询用于
     *
     * @param user
     * @return
     */
    List<User> findByUser(User user);

}
