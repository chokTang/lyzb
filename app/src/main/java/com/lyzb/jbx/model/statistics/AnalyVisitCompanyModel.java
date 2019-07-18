package com.lyzb.jbx.model.statistics;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/24 16:06
 */

public class AnalyVisitCompanyModel {


    /**
     * viewNum : 18
     * extNum : 13
     * shopNum : 1
     * topicNum : 4
     * appRatioNum : 38.89
     * wxRatioNum : 11.11
     * miniRatioNum : 16.67
     * otherRatioNum : 33.33
     */
    /**
     * 访问次数
     */
    private int viewNum;
    /**
     * 名片访问次数
     */
    private int extNum;

    /**
     * 商品访问次数
     */
    private int shopNum;
    /**
     * 动态访问次数
     */
    private int topicNum;
    private int hotNum;//热文数据
    /**
     * 共商联盟APP
     */
    private String appRatioNum;
    /**
     * 微信分享
     */
    private String wxRatioNum;
    /**
     * 共商联盟小程序
     */
    private String miniRatioNum;
    /**
     * 其他
     */
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
