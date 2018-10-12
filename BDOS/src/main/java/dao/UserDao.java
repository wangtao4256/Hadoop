package dao;

import org.apache.ibatis.annotations.Mapper;
import pojo.User;

import java.util.List;

/**
 * Title: UserDao
 * Description:
 * 用户数据接口
 *
 * @author tao.wang
 */
@Mapper
public interface UserDao {
    /**
     * @return
     * @throws Exception
     * @Description 批量新增插入用户数据
     */
    boolean insertBatch(List<User> entityList) throws Exception;

    /**
     * @param user
     * @return
     * @throws Exception
     */
    List<User> findByUser(User user);

}
