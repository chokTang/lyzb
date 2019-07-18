package com.lyzb.jbx.model.me;

public class CompanyAccountDetailModel {

    /**
     * msg : 查询成功
     * detailData : {"mOrganId":"03182545-40EB-4F7F-9B08-1D2DCE0576A4","role":"2","accountName":"ppn067","accountSt":1,"position":"","userId":"110755","mobile":"","remark":"","gsName":""}
     * status : 200
     */

    private String msg;
    private DetailDataBean detailData;
    private String status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DetailDataBean getDetailData() {
        return detailData;
    }

    public void setDetailData(DetailDataBean detailData) {
        this.detailData = detailData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class DetailDataBean {
        /**
         * mOrganId : 03182545-40EB-4F7F-9B08-1D2DCE0576A4
         * role : 2
         * accountName : ppn067
         * accountSt : 1
         * position :
         * userId : 110755
         * mobile :
         * remark :
         * gsName :
         */

        private String companyId;
        private String role;
        private String accountName;
        private int accountSt;
        private String position;
        private String userId;
        private String mobile;
        private String remark;
        private String gsName;

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getAccountName() {
            return accountName;
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

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getGsName() {
            return gsName;
        }

        public void setGsName(String gsName) {
            this.gsName = gsName;
        }
    }
}
