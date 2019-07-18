package com.lyzb.jbx.model.campagin;

import java.util.ArrayList;
import java.util.List;

public class CampaginDetailModel {
    private int access;//活动来源,后台发起0 app发起1
    private int activityCount;//活动数
    private String activityEnd;//活动结束时间
    private String activityStart;//活动开始时间
    private String activityLogo;//活动logo
    private List<CampaginDetailUserModel> activityParticipantList;
    private int activitySatatus;//活动状态:1：进行中；2：未开始；3：结束
    private int collectCount;//收藏数
    private String content;//内容
    private String createMan;//创建人Id
    private String groupId;//活动所属群
    private String headimg;//发起人头像
    private String id;//发起人头像
    private int isPublic; //是否公共活动1:公共活动，2：圈内活动
    private String label; //标签
    private int lookCount;//浏览人数
    private int partCount;//参与人数
    private String place;//举办地址
    private int shareCount;//分享数
    private String title;//活动标题
    private String userName;//	举办人名称
    private boolean collect;//是否收藏
    private int fansNum;
    private int concern;//是否关注：大于0表示已关注；反之未关注
    private int parUserActivity;//大于0表示已报名
    private float activityLat;//维度
    private float activityLng;//经度
    private String comAaccount;//环信账号

    public int getParUserActivity() {
        return parUserActivity;
    }

    public void setParUserActivity(int parUserActivity) {
        this.parUserActivity = parUserActivity;
    }

    public String getComAaccount() {
        return comAaccount;
    }

    public void setComAaccount(String comAaccount) {
        this.comAaccount = comAaccount;
    }

    public float getActivityLat() {
        return activityLat;
    }

    public void setActivityLat(float activityLat) {
        this.activityLat = activityLat;
    }

    public float getActivityLng() {
        return activityLng;
    }

    public void setActivityLng(float activityLng) {
        this.activityLng = activityLng;
    }

    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public int getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(int activityCount) {
        this.activityCount = activityCount;
    }

    public String getActivityEnd() {
        return activityEnd;
    }

    public void setActivityEnd(String activityEnd) {
        this.activityEnd = activityEnd;
    }

    public String getActivityStart() {
        return activityStart;
    }

    public void setActivityStart(String activityStart) {
        this.activityStart = activityStart;
    }

    public String getActivityLogo() {
        return activityLogo;
    }

    public void setActivityLogo(String activityLogo) {
        this.activityLogo = activityLogo;
    }

    public List<CampaginDetailUserModel> getActivityParticipantList() {
        if (activityParticipantList == null)
            return new ArrayList<>();
        return activityParticipantList;
    }

    public void setActivityParticipantList(List<CampaginDetailUserModel> activityParticipantList) {
        this.activityParticipantList = activityParticipantList;
    }

    public int getActivitySatatus() {
        return activitySatatus;
    }

    public String getActivitySatatusZh() {
        switch (activitySatatus) {
            case 1:
                return "进行中";
            case 2:
                return "我要报名";
            case 3:
                return "已结束";
        }
        return "未知状态";
    }

    public void setActivitySatatus(int activitySatatus) {
        this.activitySatatus = activitySatatus;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getLookCount() {
        return lookCount;
    }

    public void setLookCount(int lookCount) {
        this.lookCount = lookCount;
    }

    public int getPartCount() {
        return partCount;
    }

    public void setPartCount(int partCount) {
        this.partCount = partCount;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getConcern() {
        return concern;
    }

    public void setConcern(int concern) {
        this.concern = concern;
    }
}
