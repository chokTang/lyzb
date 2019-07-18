package com.lyzb.jbx.model.statistics;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/22 20:14
 */

public class AnalysisShareModel {


    /**
     * shareSum : 1
     * shareExtSum : 1
     * shareTopicSum : 0
     * userExtNum : 13
     * userExtSumNum : 13
     * userTopicNum : 4
     * userTopicSumNum : 4
     */
    /**
     * 分享总次数
     */
    private int shareSum;
    /**
     * 分享名片总次数
     */
    private int shareExtSum;
    /**
     * 分享动态总次数
     */
    private int shareTopicSum;
    /**
     * 名片访问人数
     */
    private int userExtNum;
    /**
     * 名片访问总次数
     */
    private int userExtSumNum;
    /**
     * 动态访问人数
     */
    private int userTopicNum;
    /**
     * 动态访问总次数
     */
    private int userTopicSumNum;

    private int shareHotSum;//热文分享次数
    private int userHotNum;//热文访问人数
    private int userHotSumNum;//热文访问总次数

    public int getShareHotSum() {
        return shareHotSum;
    }

    public int getUserHotNum() {
        return userHotNum;
    }

    public int getUserHotSumNum() {
        return userHotSumNum;
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
