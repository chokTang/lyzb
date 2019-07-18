package com.lyzb.jbx.model.me.access;

/**
 * 我的-访客追踪-分享累计次数
 * 对应接口：/lbs/gs/user/selectMyShareNumVo
 * @author shidengzhong
 */
public class AccessShareDetailModel {
    private int shareSum;
    private int shareExtSum;
    private int shareTopicSum;
    private int userExtNum;
    private int userExtSumNum;
    private int userTopicNum;
    private int userTopicSumNum;
    private int shareHotSum;
    private int userHotNum;
    private int userHotSumNum;

    public int getShareHotSum() {
        return shareHotSum;
    }

    public void setShareHotSum(int shareHotSum) {
        this.shareHotSum = shareHotSum;
    }

    public int getUserHotNum() {
        return userHotNum;
    }

    public void setUserHotNum(int userHotNum) {
        this.userHotNum = userHotNum;
    }

    public int getUserHotSumNum() {
        return userHotSumNum;
    }

    public void setUserHotSumNum(int userHotSumNum) {
        this.userHotSumNum = userHotSumNum;
    }

    public int getShareSum() {
        return shareSum;
    }

    public void setShareSum(int shareSum) {
        this.shareSum = shareSum;
    }

    public int getShareExtSum() {
        return shareExtSum;
    }

    public void setShareExtSum(int shareExtSum) {
        this.shareExtSum = shareExtSum;
    }

    public int getShareTopicSum() {
        return shareTopicSum;
    }

    public void setShareTopicSum(int shareTopicSum) {
        this.shareTopicSum = shareTopicSum;
    }

    public int getUserExtNum() {
        return userExtNum;
    }

    public void setUserExtNum(int userExtNum) {
        this.userExtNum = userExtNum;
    }

    public int getUserExtSumNum() {
        return userExtSumNum;
    }

    public void setUserExtSumNum(int userExtSumNum) {
        this.userExtSumNum = userExtSumNum;
    }

    public int getUserTopicNum() {
        return userTopicNum;
    }

    public void setUserTopicNum(int userTopicNum) {
        this.userTopicNum = userTopicNum;
    }

    public int getUserTopicSumNum() {
        return userTopicSumNum;
    }

    public void setUserTopicSumNum(int userTopicSumNum) {
        this.userTopicSumNum = userTopicSumNum;
    }
}
