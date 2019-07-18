package com.lyzb.jbx.model.campagin;

import com.like.utilslib.date.DateStyle;
import com.like.utilslib.date.DateUtil;
import com.lyzb.jbx.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页活动model
 */
public class CampaignModel {
    private int access;//活动来源,后台发起0（线上） app发起1（线下）
    private String activityStart;
    private String activityEnd;
    private List<CampaignUserModel> activityParticipantList;//参与人列表
    private int activitySatatus;//活动状态:1：进行中；2：未开始；3：结束
    private String content;//内容
    private String createMan;//创建人ID
    private String flag;//发布标志(0-未发布，1-已发布)（原有）
    private String groupId;//活动所属群
    private String headimg;//用户人头像
    private String id;//活动Id
    private int isPublic;//是否公共活动1:公共活动，2：圈内活动
    private String place;//活动地点
    private String title;//标题
    private int partCount;//参与人数
    private String userName;//用户名
    private String activityLogo;//活动背景
    private int showtel;

    public String getActivityLogo() {
        return activityLogo;
    }

    public void setActivityLogo(String activityLogo) {
        this.activityLogo = activityLogo;
    }

    public int getPartCount() {
        return partCount;
    }

    public void setPartCount(int partCount) {
        this.partCount = partCount;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public List<CampaignUserModel> getActivityParticipantList() {
        if (activityParticipantList == null) {
            return new ArrayList<>();
        }
        return activityParticipantList;
    }

    public void setActivityParticipantList(List<CampaignUserModel> activityParticipantList) {
        this.activityParticipantList = activityParticipantList;
    }

    public int getActivitySatatus() {
        return activitySatatus;
    }

    /**
     * 获取中文状态的活动状态
     *
     * @return
     */
    public String getActivitySatatusZh() {
        switch (activitySatatus) {
            case 1:
                return "进行中";
            case 2:
                return DateUtil.DateToString(DateUtil.StringToDate(activityStart), DateStyle.MM_DD_HH_MM_CN) + "    开始";
            case 3:
                return "已结束";
        }
        return "未知状态";
    }

    public int getActivitySatatusColor() {
        switch (activitySatatus) {
            case 1:
                return R.color.app_green;
            case 2:
                return R.color.fontcColor2;
            case 3:
                return R.color.fontcColor1;
        }
        return R.color.fontcColor2;
    }

    public void setActivitySatatus(int activitySatatus) {
        this.activitySatatus = activitySatatus;
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    public String getActivityStart() {
        return activityStart;
    }

    public void setActivityStart(String activityStart) {
        this.activityStart = activityStart;
    }

    public String getActivityEnd() {
        return activityEnd;
    }

    public void setActivityEnd(String activityEnd) {
        this.activityEnd = activityEnd;
    }

    public int getShowtel() {
        return showtel;
    }

    public void setShowtel(int showtel) {
        this.showtel = showtel;
    }
}
