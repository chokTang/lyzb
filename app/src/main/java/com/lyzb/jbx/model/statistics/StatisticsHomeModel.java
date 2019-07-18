package com.lyzb.jbx.model.statistics;

import java.util.List;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/23 20:46
 */

public class StatisticsHomeModel {


    /**
     * msg : 查询成功!
     * code : 200
     * totalNewUser : 0
     * totalVisit : 0
     * dataList : [{"userId":110813,"extId":7768,"headImg":"http://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/gs_app/init/%E5%AE%A0%E7%89%A9logo/1.png","accountName":"wlt5","userName":"cccc","lastLoginTime":"2019-04-18 17:04:01","visitNum":0,"shareNum":0,"newUserNum":0,"orderNum":0}]
     * totalOrder : 0
     * totalShare : 0
     */

    /**
     * 提示语
     */
    private String msg;
    /**
     *状态码（ 200：查询成功 201：未查询到相关团购企业数据 403：抱歉,您无查询权限 500：参数错误 ）
     */
    private int code;
    /**
     *累计新用户
     */
    private int totalNewUser;
    /**
     *累计访问
     */
    private int totalVisit;
    /**
     *累计交易笔数
     */
    private int totalOrder;
    /**
     *累计分享次数
     */
    private int totalShare;
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

    public int getTotalNewUser() {
        return totalNewUser;
    }

    public void setTotalNewUser(int totalNewUser) {
        this.totalNewUser = totalNewUser;
    }

    public int getTotalVisit() {
        return totalVisit;
    }

    public void setTotalVisit(int totalVisit) {
        this.totalVisit = totalVisit;
    }

    public int getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(int totalOrder) {
        this.totalOrder = totalOrder;
    }

    public int getTotalShare() {
        return totalShare;
    }

    public void setTotalShare(int totalShare) {
        this.totalShare = totalShare;
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
         * lastLoginTime : 2019-04-18 17:04:01
         * visitNum : 0
         * shareNum : 0
         * newUserNum : 0
         * orderNum : 0
         */
        /**
         * 用户id
         */
        private String userId;
        /**
         *名片id
         */
        private String extId;
        /**
         *头像地址
         */
        private String headImg;
        /**
         *企业账号
         */
        private String accountName;
        /**
         *姓名
         */
        private String userName;
        /**
         *最后登录时间
         */
        private String lastLoginTime;
        /**
         *被访问次数
         */
        private int visitNum;
        /**
         *分享次数
         */
        private int shareNum;
        /**
         *新用户
         */
        private int newUserNum;
        /**
         *交易笔数
         */
        private int orderNum;

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

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public int getVisitNum() {
            return visitNum;
        }

        public void setVisitNum(int visitNum) {
            this.visitNum = visitNum;
        }

        public int getShareNum() {
            return shareNum;
        }

        public void setShareNum(int shareNum) {
            this.shareNum = shareNum;
        }

        public int getNewUserNum() {
            return newUserNum;
        }

        public void setNewUserNum(int newUserNum) {
            this.newUserNum = newUserNum;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }
    }
}
