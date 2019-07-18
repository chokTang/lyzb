package com.lyzb.jbx.model.me.company;

import android.text.TextUtils;

import java.util.List;

public class StaffInfoModel {


    /**
     * msg : 查询成功
     * code : 200
     * data : {"accountName":"tghd3","accountSt":1,"mobile":"17623147972","orgList":[{"role":"2","orgName":"代付电费","orgId":"33EBE18B-D87E-4414-BCAC-EF166569DB2B"}],"gsName":"张三","userId":"245323"}
     */

    private String msg;
    private int code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean {
        /**
         * accountName : tghd3
         * accountSt : 1
         * mobile : 17623147972
         * orgList : [{"role":"2","orgName":"代付电费","orgId":"33EBE18B-D87E-4414-BCAC-EF166569DB2B"}]
         * gsName : 张三
         * userId : 245323
         */

        private String accountName;
        private int accountSt;
        private String mobile;
        private String gsName;
        private String userId;
        private String userName;
        private boolean canDel;
        private List<ChildrenBean> orgList;

        public String getAccountName() {
            return TextUtils.isEmpty(accountName) ? userName : accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public int getAccountSt() {
            return accountSt;
        }

        public void setAccountSt(int accountSt) {
            this.accountSt = accountSt;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getGsName() {
            return gsName;
        }

        public void setGsName(String gsName) {
            this.gsName = gsName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public boolean isCanDel() {
            return canDel;
        }

        public void setCanDel(boolean canDel) {
            this.canDel = canDel;
        }

        public List<ChildrenBean> getOrgList() {
            return orgList;
        }

        public void setOrgList(List<ChildrenBean> orgList) {
            this.orgList = orgList;
        }

    }
}
