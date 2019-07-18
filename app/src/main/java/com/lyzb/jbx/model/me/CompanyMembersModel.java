package com.lyzb.jbx.model.me;

import java.util.List;

public class CompanyMembersModel {

    /**
     * msg : 查询成功!
     * data : {"pageNum":1,"pageSize":10,"total":1,"pages":1,"list":[{"gsName":"德玛西亚","extId":7759,"position":"老板","headimg":"http://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/gs_app/init/%E5%AE%A0%E7%89%A9logo/1.png","role":2,"mobile":"","mOrganId":"","userId":1,"dataType":1}]}
     * showBtnCon : 2
     * status : 200
     * userId : 1
     */

    private String msg;
    private DataBean data;
    private int showBtnCon;
    private String status;
    private String userId;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getShowBtnCon() {
        return showBtnCon;
    }

    public void setShowBtnCon(int showBtnCon) {
        this.showBtnCon = showBtnCon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static class DataBean {
        /**
         * pageNum : 1
         * pageSize : 10
         * total : 1
         * pages : 1
         * list : [{"gsName":"德玛西亚","extId":7759,"position":"老板","headimg":"http://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/gs_app/init/%E5%AE%A0%E7%89%A9logo/1.png","role":2,"mobile":"","mOrganId":"","userId":1,"dataType":1}]
         */

        private int pageNum;
        private int pageSize;
        private int total;
        private int pages;
        private List<ListBean> list;

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * gsName : 德玛西亚
             * extId : 7759
             * position : 老板
             * headimg : http://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/gs_app/init/%E5%AE%A0%E7%89%A9logo/1.png
             * role : 2
             * mobile :
             * mOrganId :
             * userId : 1
             * dataType : 1
             */

            private String gsName;
            private String extId;
            private String position;
            private String headimg;
            private int role;
            private String mobile;
            private String companyId;
            private String userId;
            private int dataType;
            private int accountSt;

            public String getGsName() {
                return gsName;
            }

            public void setGsName(String gsName) {
                this.gsName = gsName;
            }

            public String getExtId() {
                return extId;
            }

            public void setExtId(String extId) {
                this.extId = extId;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getHeadimg() {
                return headimg;
            }

            public void setHeadimg(String headimg) {
                this.headimg = headimg;
            }

            public int getRole() {
                return role;
            }

            public void setRole(int role) {
                this.role = role;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getCompanyId() {
                return companyId;
            }

            public void setCompanyId(String companyId) {
                this.companyId = companyId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public int getDataType() {
                return dataType;
            }

            public void setDataType(int dataType) {
                this.dataType = dataType;
            }

            public int getAccountSt() {
                return accountSt;
            }

            public void setAccountSt(int accountSt) {
                this.accountSt = accountSt;
            }
        }
    }
}
