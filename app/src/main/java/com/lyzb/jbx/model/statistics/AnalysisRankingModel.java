package com.lyzb.jbx.model.statistics;

import java.util.List;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/24 16:33
 */

public class AnalysisRankingModel {


    /**
     * msg : 查询成功!
     * code : 200
     * dataList : [{"userId":110813,"extId":7768,"headImg":"http://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/gs_app/init/%E5%AE%A0%E7%89%A9logo/1.png","accountName":"wlt5","userName":"cccc","totalNum":0,"extNum":0,"goodsNum":0,"topicNum":0,"newUserNum":0,"orderCount":0,"orderAmount":0}]
     */

    private String msg;
    /**
     * 状态码（ 200：查询成功； 201：未查询到相关团购企业数据； 202：未查询到相关数据；403：抱歉,您无查询权限； 500：参数错误 ）
     */
    private int code;
    private List<DataListBean> dataList;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public class DataListBean {
        /**
         * userId : 110813
         * extId : 7768
         * headImg : http://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/gs_app/init/%E5%AE%A0%E7%89%A9logo/1.png
         * accountName : wlt5
         * userName : cccc
         * totalNum : 0
         * extNum : 0
         * goodsNum : 0
         * topicNum : 0
         * newUserNum : 0
         * orderCount : 0
         * orderAmount : 0
         */
        /**
         * 用户id
         */
        private String userId;
        /**
         * 名片id
         */
        private String extId;
        /**
         * 头像地址
         */
        private String headImg;
        /**
         * 团购企业账号名
         */
        private String accountName;
        /**
         * 团购企业账使用者名称
         */
        private String userName;
        /**
         * 【访问排行/分享排行】-全部访问次数/总共分享次数
         */
        private int totalNum;
        /**
         * 【访问排行/分享排行】-名片访问次数/分享名片次数
         */
        private int extNum;
        /**
         * 【访问排行/分享排行】-商品访问次数/分享商品次数
         */
        private int goodsNum;
        /**
         * 【访问排行/分享排行】-访问动态次数/动态分享次数
         */
        private int topicNum;
        /**
         * 引流排行-引流新用户数量
         */
        private int newUserNum;
        /**
         * 交易排行榜-交易总笔数
         */
        private int orderCount;
        /**
         * 交易排行榜-交易总金额
         */
        private int orderAmount;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getExtId() {
            return extId;
        }

        public void setExtId(String extId) {
            this.extId = extId;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public int getExtNum() {
            return extNum;
        }

        public void setExtNum(int extNum) {
            this.extNum = extNum;
        }

        public int getGoodsNum() {
            return goodsNum;
        }

        public void setGoodsNum(int goodsNum) {
            this.goodsNum = goodsNum;
        }

        public int getTopicNum() {
            return topicNum;
        }

        public void setTopicNum(int topicNum) {
            this.topicNum = topicNum;
        }

        public int getNewUserNum() {
            return newUserNum;
        }

        public void setNewUserNum(int newUserNum) {
            this.newUserNum = newUserNum;
        }

        public int getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(int orderCount) {
            this.orderCount = orderCount;
        }

        public int getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(int orderAmount) {
            this.orderAmount = orderAmount;
        }
    }
}
