package com.lyzb.jbx.model.campagin;

import com.lyzb.jbx.model.common.VipModel;

import java.util.List;

public class CampaginUserListModel {
    private String activityId;
    private String activityLogo;
    private String applyDate;
    private String companyInfo;
    private String headimg;
    private String id;
    private int status;//状态：1：生效；2：取消
    private int type;//类型（1：参加，2：关注，3：浏览,4:分享）
    private List<VipModel> userActionVos;
    private String userHeadName;
    private String userId;
    private String userName;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityLogo() {
        return activityLogo;
    }

    public void setActivityLogo(String activityLogo) {
        this.activityLogo = activityLogo;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<VipModel> getUserActionVos() {
        return userActionVos;
    }

    public void setUserActionVos(List<VipModel> userActionVos) {
        this.userActionVos = userActionVos;
    }

    public String getUserHeadName() {
        return userHeadName;
    }

    public void setUserHeadName(String userHeadName) {
        this.userHeadName = userHeadName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
