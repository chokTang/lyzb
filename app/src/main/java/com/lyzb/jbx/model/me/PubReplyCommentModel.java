package com.lyzb.jbx.model.me;

import com.lyzb.jbx.model.dynamic.DynamicFileModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的--我发表的-评论列表model
 *
 * @author longshao
 */
public class PubReplyCommentModel {
    private String content;//评论内容
    private String createDate;//创建时间
    private String id;
    private String replyId;
    private String topicId;
    private int type;
    private String userId;

    private List<DynamicFileModel> gsTopicFileList;//文件列表

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
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

    public List<DynamicFileModel> getGsTopicFileList() {
        if (gsTopicFileList == null) return new ArrayList<>();
        return gsTopicFileList;
    }

    public void setGsTopicFileList(List<DynamicFileModel> gsTopicFileList) {
        this.gsTopicFileList = gsTopicFileList;
    }
}
