package pojo;

import org.apache.storm.shade.org.eclipse.jetty.util.ajax.JSON;

import java.io.Serializable;

/**
 * @author tao.wang
 * @Title: User
 * @Description:用户pojo类
 */
public class User implements Serializable {
    /**
     * 编号
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    public User() {

    }

    public User(Integer id, String name, Integer age) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String toString() {
        return JSON.toString(this);
    }

}
