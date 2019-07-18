package com.lyzb.jbx.model.me;

import com.lyzb.jbx.model.common.VipModel;
import com.lyzb.jbx.model.dynamic.DynamicFileModel;

import java.util.List;

/**
 * @author wyx
 * @role 我的收藏 model
 * @time 2019 2019/3/7 20:20
 */

public class CollectModel {
    private int commentCount;//评论数
    private String content;//动态内容
    private String gsName;//姓名
    private List<DynamicFileModel> gsTopicFileList;
    private String headimg;
    private int relationNum;//0 未关注 1关注
    private int shareCount;//分享数
    private String shopName;//商家名称
    private String title;
    private String topicId;//动态id
    private String createMan;//动态id
    private int upCount;//赞数
    private String userExtId;//名片id
    private String userId;//名片用户id
    private int viewCount;//浏览数
    private List<VipModel> userVipAction;
    private int giveLike;//是否点赞 0未点赞 1点赞

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public int getGiveLike() {
        return giveLike;
    }

    public void setGiveLike(int giveLike) {
        this.giveLike = giveLike;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGsName() {
        return gsName;
    }

    public void setGsName(String gsName) {
        this.gsName = gsName;
    }

    public List<DynamicFileModel> getGsTopicFileList() {
        return gsTopicFileList;
    }

    public void setGsTopicFileList(List<DynamicFileModel> gsTopicFileList) {
        this.gsTopicFileList = gsTopicFileList;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public int getRelationNum() {
        return relationNum;
    }

    public void setRelationNum(int relationNum) {
        this.relationNum = relationNum;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
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

    public int getUpCount() {
        return upCount;
    }

    public void setUpCount(int upCount) {
        this.upCount = upCount;
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
        return userVipAction;
    }

    public void setUserVipAction(List<VipModel> userVipAction) {
        this.userVipAction = userVipAction;
    }
}
