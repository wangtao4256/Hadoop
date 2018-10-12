package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Title: ApplicationConfiguration
 * @Description: 获取application文件的信息
 */
@Component("applicationConfiguration")
public class ApplicationConfiguration {
    @Value("${kafka.servers}")
    private String servers;
    @Value("${kafka.topicName}")
    private String topicName;
    @Value("${kafka.autoCommit}")
    private String autoCommit;
    @Value("${kafka.maxPollRecords}")
    private int maxPollRecords;
    @Value("${kafka.groupId}")
    private String groupId;
    @Value("${kafka.commitRule}")
    private String commitRule;


    public String getServers() {
        return servers;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getAutoCommit() {
        return autoCommit;
    }

    public int getMaxPollRecords() {
        return maxPollRecords;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getCommitRule() {
        return commitRule;
    }


}
