package com.lyzb.jbx.model.params;

//报名活动
public class SignCampaginBody {
    private String activityId;
    private String invitePeople;
    private String phone;
    private String userName;
    private String userNum;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getInvitePeople() {
        return invitePeople;
    }

    public void setInvitePeople(String invitePeople) {
        this.invitePeople = invitePeople;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }
}
