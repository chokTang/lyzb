package com.lyzb.jbx.model.me.access;

/**
 * 我的-访客追踪-访问次数实体
 * 对应接口：/lbs/gs/user/selectCurrentView
 * @author shidengzhong
 */
public class AccessNumberDetailModel {
    private int viewNum;
    private int extNum;
    private int shopNum;
    private int topicNum;
    private int hotNum;
    private String appRatioNum;
    private String wxRatioNum;
    private String miniRatioNum;
    private String otherRatioNum;

    public int getHotNum() {
        return hotNum;
    }

    public void setHotNum(int hotNum) {
        this.hotNum = hotNum;
    }

    public int getViewNum() {
        return viewNum;
    }

    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }

    public int getExtNum() {
        return extNum;
    }

    public void setExtNum(int extNum) {
        this.extNum = extNum;
    }

    public int getShopNum() {
        return shopNum;
    }

    public void setShopNum(int shopNum) {
        this.shopNum = shopNum;
    }

    public int getTopicNum() {
        return topicNum;
    }

    public void setTopicNum(int topicNum) {
        this.topicNum = topicNum;
    }

    public String getAppRatioNum() {
        return appRatioNum;
    }

    public void setAppRatioNum(String appRatioNum) {
        this.appRatioNum = appRatioNum;
    }

    public String getWxRatioNum() {
        return wxRatioNum;
    }

    public void setWxRatioNum(String wxRatioNum) {
        this.wxRatioNum = wxRatioNum;
    }

    public String getMiniRatioNum() {
        return miniRatioNum;
    }

    public void setMiniRatioNum(String miniRatioNum) {
        this.miniRatioNum = miniRatioNum;
    }

    public String getOtherRatioNum() {
        return otherRatioNum;
    }

    public void setOtherRatioNum(String otherRatioNum) {
        this.otherRatioNum = otherRatioNum;
    }
}
