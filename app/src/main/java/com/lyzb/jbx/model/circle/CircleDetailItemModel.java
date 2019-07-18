package com.lyzb.jbx.model.circle;

import com.lyzb.jbx.model.common.VipModel;
import com.lyzb.jbx.model.dynamic.DynamicFileModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子的动态item实体 其实和圈子的item一抹一样，但是因为写的人不同，所以造成了 少数几个字段不一样，
 */
public class CircleDetailItemModel {
    private int commentCount;
    private String content;
    private String createDate;
    private String createMan;
    private String id;
    private int relationNum;//0 未关注 1关注
    private int shareCount;
    private int concern;//0 未关注 1关注   (/lbs/gsGroup/topicList 当前接口用的这个字段)
    private String type;
    private int upCount;
    private List<VipModel> userActionVos;//Vip类型
    private List<DynamicFileModel> topicFileList;
    private String title;
    private String groupId;
    private String groupname;
    private String gsName;
    private String headimg;
    private String userName;
    private String sex;
    private String shopName;
    private int viewCount;
    private String mobile;
    private String companyInfo;
    private int giveLike;//0:未点赞 1：已点赞

    private boolean isFooter=false;

    public CircleDetailItemModel() {
    }

    public CircleDetailItemModel(boolean isFooter) {
        this.isFooter = isFooter;
    }

    public boolean isFooter() {
        return isFooter;
    }

    public int getConcern() {
        return concern;
    }

    public void setConcern(int concern) {
        this.concern = concern;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return createMan;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<DynamicFileModel> getFileList() {
        if (topicFileList == null)
            return new ArrayList<>();
        return topicFileList;
    }

    public int getGiveLike() {
        return giveLike;
    }

    public void setGiveLike(int giveLike) {
        this.giveLike = giveLike;
    }

    public void setFileList(List<DynamicFileModel> fileList) {
        this.topicFileList = fileList;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateMan() {
        if (createMan == null) return "";
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUpCount() {
        if (upCount < 0) {
            upCount = 0;
        }
        return upCount;
    }

    public void setUpCount(int upCount) {
        this.upCount = upCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public List<VipModel> getUserActionVos() {
        if (userActionVos == null)
            return new ArrayList<>();
        return userActionVos;
    }

    public void setUserActionVos(List<VipModel> userVipAction) {
        this.userActionVos = userVipAction;
    }
}
