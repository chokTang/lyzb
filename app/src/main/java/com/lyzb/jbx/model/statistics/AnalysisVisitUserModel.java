package com.lyzb.jbx.model.statistics;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/23 14:23
 */

public class AnalysisVisitUserModel {


    /**
     * {
     * "viewNum": 1,
     * "shareNum": 1,
     *"userNum": 11,
     *"orderNum": 1
     *}
     */
    /**
     * 浏览数
     */
    private int viewNum;
    /**
     * 分享数
     */
    private int shareNum;
    /**
     * 新增用户数
     */
    private int userNum;
    /**
     * 交易数
     */
    private int orderNum;

    public int getViewNum() {
        return viewNum;
    }

    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }

    public int getShareNum() {
        return shareNum;
    }

    public void setShareNum(int shareNum) {
        this.shareNum = shareNum;
    }

    public int getUserNum() {
        return userNum;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
}
