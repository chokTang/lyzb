package com.lyzb.jbx.model.statistics;

import java.util.List;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/23 17:49
 */

public class AnalysisNewUserModel {


    /**
     * msg :  查询成功
     * code : 200
     * totalNewUser : 0
     * dataList : [{"id":15,"shareUserId":109048,"shareUserGuid":"52b9bb27-bdf8-48b9-ba10-2ed443e641f0","browserUserId":110621,"browserUnionId":"oVqv2s4tuX4Fse6rQYQSg3dO4bR8","browseTime":"2019-04-12 15:21:30","createdTime":"2019-04-12 15:21:30","type":1,"userName":"前端","headimg":"","regTime":"1555401542","accountName":"jbx123","userGsName":"宁亿","userActionVos":[{"id":993,"actionId":5,"groupId":"0","userId":110621,"addTime":"2019-03-20 00:00:00","startDate":"2019-03-20 00:00:00","endDate":"2022-03-19 00:00:00","actionName":"三年服务","imageUrl":"http://img2.jpg"}],"shareName":"通过XXX引流"}]
     */

    private String msg;
    private String code;
    private int totalNewUser;
    private int total;
    private List<DataListBean> dataList;
    private List<DataListBean> list;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalNewUser() {
        if (totalNewUser == 0) {
            return total;
        }
        return totalNewUser;
    }

    public void setTotalNewUser(int totalNewUser) {
        this.totalNewUser = totalNewUser;
    }

    public List<DataListBean> getDataList() {
        if (dataList == null || dataList.size() < 1) {
            return list;
        } else {
            return dataList;
        }
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public List<DataListBean> getList() {
        return list;
    }

    public void setList(List<DataListBean> list) {
        this.list = list;
    }

    public class DataListBean {
        /**
         * id : 15
         * shareUserId : 109048
         * shareUserGuid : 52b9bb27-bdf8-48b9-ba10-2ed443e641f0
         * browserUserId : 110621
         * browserUnionId : oVqv2s4tuX4Fse6rQYQSg3dO4bR8
         * browseTime : 2019-04-12 15:21:30
         * createdTime : 2019-04-12 15:21:30
         * type : 1
         * userName : 前端
         * headimg :
         * regTime : 1555401542
         * accountName : jbx123
         * userGsName : 宁亿
         * userActionVos : [{"id":993,"actionId":5,"groupId":"0","userId":110621,"addTime":"2019-03-20 00:00:00","startDate":"2019-03-20 00:00:00","endDate":"2022-03-19 00:00:00","actionName":"三年服务","imageUrl":"http://img2.jpg"}]
         * shareName : 通过XXX引流
         */
        private int id;
        /**
         * 分享人id
         */
        private String shareUserId;
        /**
         * 分享人GUID
         */
        private String shareUserGuid;
        /**
         * 引流人id
         */
        private String browserUserId;
        /**
         * 引流人unionId
         */
        private String browserUnionId;
        /**
         * 浏览时间
         */
        private String browseTime;
        /**
         * 创建时间
         */
        private String createdTime;
        /**
         * 1：名片 2：热文（不知道做什么用的）
         */
        private int type;
        /**
         * 用户名
         */
        private String userName;
        /**
         * 头像
         */
        private String headimg;
        /**
         * 注册时间
         */
        private String regTime;
        /**
         * 团购企业账号名
         */
        private String accountName;
        /**
         * 团购企业账号使用者名
         */
        private String userGsName;
        /**
         * 通过什么引流
         */
        private String shareName;
        /**
         * 不知道这是什么玩意儿
         */
        private List<UserActionVosBean> userActionVos;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getShareUserId() {
            return shareUserId;
        }

        public void setShareUserId(String shareUserId) {
            this.shareUserId = shareUserId;
        }

        public String getShareUserGuid() {
            return shareUserGuid;
        }

        public void setShareUserGuid(String shareUserGuid) {
            this.shareUserGuid = shareUserGuid;
        }

        public String getBrowserUserId() {
            return browserUserId;
        }

        public void setBrowserUserId(String browserUserId) {
            this.browserUserId = browserUserId;
        }

        public String getBrowserUnionId() {
            return browserUnionId;
        }

        public void setBrowserUnionId(String browserUnionId) {
            this.browserUnionId = browserUnionId;
        }

        public String getBrowseTime() {
            return browseTime;
        }

        public void setBrowseTime(String browseTime) {
            this.browseTime = browseTime;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getRegTime() {
            return regTime;
        }

        public void setRegTime(String regTime) {
            this.regTime = regTime;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getUserGsName() {
            return userGsName;
        }

        public void setUserGsName(String userGsName) {
            this.userGsName = userGsName;
        }

        public String getShareName() {
            return shareName;
        }

        public void setShareName(String shareName) {
            this.shareName = shareName;
        }

        public List<UserActionVosBean> getUserActionVos() {
            return userActionVos;
        }

        public void setUserActionVos(List<UserActionVosBean> userActionVos) {
            this.userActionVos = userActionVos;
        }

    }
}
