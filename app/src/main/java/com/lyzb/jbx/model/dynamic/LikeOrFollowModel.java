package com.lyzb.jbx.model.dynamic;

/**
 * Created by :TYK
 * Date: 2019/3/18  16:01
 * Desc: 动态详情中点赞和收藏详情
 */
public class LikeOrFollowModel {

    /**
     * createDate : 2019-03-06 16:14:19
     * id : 0f8e0e0947204bca85409569b3a86cf7
     * topicId : 00760ea444284e6fbfe57a109242354b
     * type : 2
     * userId : 32801
     */

    private String createDate;
    private String id;
    private String topicId;
    private int type;
    private String userId;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
