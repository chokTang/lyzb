package com.lyzb.jbx.model.dynamic;

/**
 * Created by :TYK
 * Date: 2019/3/18  16:37
 * Desc:  动态/评论(浏览，点赞 ,收藏)  model
 */
public class AddLikeOrFollowModel {

    /**
     * id : 30dc254791cd4ca7bfeb3a1cb5531b98
     * topicId : 00760ea444284e6fbfe57a109242354b
     * userId : 109922
     * createDate : 2019-03-18 17:05:55
     * type : 2
     */

    private String id;
    private String topicId;
    private String userId;
    private String createDate;
    private int type;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
