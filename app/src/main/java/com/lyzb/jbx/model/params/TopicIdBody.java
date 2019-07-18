package com.lyzb.jbx.model.params;

/**
 * 来源接口 /lbs/gs/topic/saveGsTopicShare
 *
 *  我的-动态（分享记数/记录）
 */
public class TopicIdBody {
    private String topicId;

    public TopicIdBody(String topicId) {
        this.topicId = topicId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }
}
