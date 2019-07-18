package com.lyzb.jbx.model.dynamic;

/**
 * Created by :TYK
 * Date: 2019/3/18  15:53
 * Desc:
 */
public class AddLikeOrFollowBody {
    public String topicId;//主键ID
    public String type;//2：点赞 ，3：收藏

    public AddLikeOrFollowBody(){

    }

    public AddLikeOrFollowBody(String topicId, String type) {
        this.topicId = topicId;
        this.type = type;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
