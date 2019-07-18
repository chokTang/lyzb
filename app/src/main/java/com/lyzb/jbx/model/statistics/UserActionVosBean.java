package com.lyzb.jbx.model.statistics;

public class UserActionVosBean {
    /**
     * id : 640
     * actionId : 1
     * groupId : 207210707098272192
     * userId : 109922
     * addTime : 2016-06-13 17:29:50
     * startDate : 2016-06-13 17:29:50
     * endDate : 2020-06-13 17:29:50
     * actionName : vip1
     * imageUrl : http://img1.jpg
     */

    private int id;
    private int actionId;
    private String groupId;
    private int userId;
    private String addTime;
    private String startDate;
    private String endDate;
    private String actionName;
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}