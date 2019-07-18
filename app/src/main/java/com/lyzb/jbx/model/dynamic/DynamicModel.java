package com.lyzb.jbx.model.dynamic;

import com.lyzb.jbx.model.common.VipModel;
import com.lyzb.jbx.model.common.VoiceModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态实体
 * Created by shidengzhong on 2019/3/5.
 */

public class DynamicModel {
    private int commentCount;
    private String content;
    private String createDate;
    private String createMan;
    private String id;
    private int relationNum;//0 未关注 1关注
    private int shareCount;
    private String type;
    private int upCount;
    private List<VipModel> userVipAction;//Vip类型
    public List<DynamicFileModel> fileList;
    private String title;
    private String groupId;
    private String groupname;
    private String gsName;
    private String headimg;
    private String sex;
    private String shopName;
    private int viewCount;
    private String mobile;
    private int giveLike;//0:未点赞 1：已点赞
    private List<VoiceModel> introductionAudioFile;//语音文件

    public List<VoiceModel> getIntroductionAudioFile() {
        if (introductionAudioFile == null) return new ArrayList<>();
        return introductionAudioFile;
    }

    public void setIntroductionAudioFile(List<VoiceModel> introductionAudioFile) {
        this.introductionAudioFile = introductionAudioFile;
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
        if (fileList == null)
            return new ArrayList<>();
        return fileList;
    }

    public int getGiveLike() {
        return giveLike;
    }

    public void setGiveLike(int giveLike) {
        this.giveLike = giveLike;
    }

    public void setFileList(List<DynamicFileModel> fileList) {
        this.fileList = fileList;
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
        if (userVipAction == null)
            return new ArrayList<>();
        return userVipAction;
    }

    public void setUserActionVos(List<VipModel> userVipAction) {
        this.userVipAction = userVipAction;
    }
}
