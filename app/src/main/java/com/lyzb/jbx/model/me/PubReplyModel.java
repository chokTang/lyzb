package com.lyzb.jbx.model.me;

import com.lyzb.jbx.model.common.VipModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wyx
 * @role 我的发表-回复 model
 * @time 2019 2019/3/7 15:27
 */

public class PubReplyModel {

    private String content;//动态内容
    private String createDate;//评论最新时间
    private String gsName;//姓名
    private String headimg;//头像
    private String shopName;//商家名称
    private String title;//标题
    private String topicId;//动态id
    private String userExtId;//名片id
    private String userId;//名片用户id
    private List<VipModel> userVipAction;//用户购买权限标识
    private List<PubReplyCommentModel> gsTopicCommentList;//评论列表

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

    public String getGsName() {
        return gsName;
    }

    public void setGsName(String gsName) {
        this.gsName = gsName;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getUserExtId() {
        return userExtId;
    }

    public void setUserExtId(String userExtId) {
        this.userExtId = userExtId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<VipModel> getUserVipAction() {
        if (userVipAction == null) return new ArrayList<>();
        return userVipAction;
    }

    public void setUserVipAction(List<VipModel> userVipAction) {
        this.userVipAction = userVipAction;
    }

    public List<PubReplyCommentModel> getGsTopicCommentList() {
        if (gsTopicCommentList == null) return new ArrayList<>();
        return gsTopicCommentList;
    }

    public void setGsTopicCommentList(List<PubReplyCommentModel> gsTopicCommentList) {
        this.gsTopicCommentList = gsTopicCommentList;
    }
}
