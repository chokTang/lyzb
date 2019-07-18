package com.lyzb.jbx.model.campagin;

import com.lyzb.jbx.model.common.VipModel;

import java.util.ArrayList;
import java.util.List;

public class CampaginDetailUserModel {
    private String activityId;//活动id
    private String headimg;//活动参与人头像
    private String id;//主键ID
    private String phone;
    private int status;//状态：1：生效；2：取消
    private int type;//类型（1：参加，2：关注，3：浏览,4:分享）
    private String userHeadName;//参与人头像
    private String userId;//用户id
    private String userName;//报名人
    private List<VipModel> userActionVos;//大于0 表示VIp

    public List<VipModel> getUserActionVos() {
        if (userActionVos == null)
            return new ArrayList<>();
        return userActionVos;
    }

    public void setUserActionVos(List<VipModel> userActionVos) {
        this.userActionVos = userActionVos;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
