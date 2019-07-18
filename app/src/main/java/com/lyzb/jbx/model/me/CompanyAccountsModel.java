package com.lyzb.jbx.model.me;

import java.util.List;

public class CompanyAccountsModel {

    /**
     * msg : 查询成功!
     * data : {"pageNum":1,"pageSize":10,"total":100,"pages":10,"list":[{"role":"1","accountName":"ppn001","accountSt":1,"remark":"暂无人员使用","extId":"7643","position":"","userId":"110689","headimg":"","gsName":"（宁亿）","mOrganId":""},{"role":"1","accountName":"ppn001","accountSt":1,"remark":"暂无人员使用","extId":"7643","position":"","userId":"110689","headimg":"","gsName":"（宁亿）","mOrganId":""},{"role":"1","accountName":"ppn001","accountSt":1,"remark":"暂无人员使用","extId":"7643","position":"","userId":"110689","headimg":"","gsName":"（宁亿）","mOrganId":""},{"role":"1","accountName":"ppn001","accountSt":1,"remark":"暂无人员使用","extId":"7643","position":"","userId":"110689","headimg":"","gsName":"（宁亿）","mOrganId":""},{"role":"1","accountName":"ppn001","accountSt":1,"remark":"暂无人员使用","extId":"7643","position":"","userId":"110689","headimg":"","gsName":"（宁亿）","mOrganId":""},{"role":"1","accountName":"ppn001","accountSt":1,"remark":"暂无人员使用","extId":"7643","position":"","userId":"110689","headimg":"","gsName":"（宁亿）","mOrganId":""},{"role":"1","accountName":"ppn001","accountSt":1,"remark":"暂无人员使用","extId":"7643","position":"","userId":"110689","headimg":"","gsName":"（宁亿）","mOrganId":""},{"role":"1","accountName":"ppn001","accountSt":1,"remark":"暂无人员使用","extId":"7643","position":"","userId":"110689","headimg":"","gsName":"（宁亿）","mOrganId":""},{"role":"1","accountName":"ppn001","accountSt":1,"remark":"暂无人员使用","extId":"7643","position":"","userId":"110689","headimg":"","gsName":"（宁亿）","mOrganId":""},{"role":"1","accountName":"ppn001","accountSt":1,"remark":"暂无人员使用","extId":"7643","position":"","userId":"110689","headimg":"","gsName":"（宁亿）","mOrganId":""}]}
     * status : 200
     */

    private String msg;
    private DataBean data;
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * pageNum : 1
         * pageSize : 10
         * total : 100
         * pages : 10
         * list : [{"role":"1","accountName":"ppn001","accountSt":1,"remark":"暂无人员使用","extId":"7643","position":"","userId":"110689","headimg":"","gsName":"（宁亿）","mOrganId":""},{"role":"1","accountName":"ppn001","accountSt":1,"remark":"暂无人员使用","extId":"7643","position":"","userId":"110689","headimg":"","gsName":"（宁亿）","mOrganId":""},{"role":"1","accountName":"ppn001","accountSt":1,"remark":"暂无人员使用","extId":"7643","position":"","userId":"110689","headimg":"","gsName":"（宁亿）","mOrganId":""},{"role":"1","accountName":"ppn001","accountSt":1,"remark":"暂无人员使用","extId":"7643","position":"","userId":"110689","headimg":"","gsName":"（宁亿）","mOrganId":""},{"role":"1","accountName":"ppn001","accountSt":1,"remark":"暂无人员使用","extId":"7643","position":"","userId":"110689","headimg":"","gsName":"（宁亿）","mOrganId":""},{"role":"1","accountName":"ppn001","accountSt":1,"remark":"暂无人员使用","extId":"7643","position":"","userId":"110689","headimg":"","gsName":"（宁亿）","mOrganId":""},{"role":"1","accountName":"ppn001","accountSt":1,"remark":"暂无人员使用","extId":"7643","position":"","userId":"110689","headimg":"","gsName":"（宁亿）","mOrganId":""},{"role":"1","accountName":"ppn001","accountSt":1,"remark":"暂无人员使用","extId":"7643","position":"","userId":"110689","headimg":"","gsName":"（宁亿）","mOrganId":""},{"role":"1","accountName":"ppn001","accountSt":1,"remark":"暂无人员使用","extId":"7643","position":"","userId":"110689","headimg":"","gsName":"（宁亿）","mOrganId":""},{"role":"1","accountName":"ppn001","accountSt":1,"remark":"暂无人员使用","extId":"7643","position":"","userId":"110689","headimg":"","gsName":"（宁亿）","mOrganId":""}]
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
             * role : 1
             * accountName : ppn001
             * accountSt : 1
             * remark : 暂无人员使用
             * extId : 7643
             * position :
             * userId : 110689
             * headimg :
             * gsName : （宁亿）
             * mOrganId :
             */

            private String role;
            private String accountName;
            private int accountSt;
            private String remark;
            private String extId;
            private String position;
            private String userId;
            private String headimg;
            private String gsName;
            private String companyId;

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

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
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

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getHeadimg() {
                return headimg;
            }

            public void setHeadimg(String headimg) {
                this.headimg = headimg;
            }

            public String getGsName() {
                return gsName;
            }

            public void setGsName(String gsName) {
                this.gsName = gsName;
            }

            public String getCompanyId() {
                return companyId;
            }

            public void setCompanyId(String companyId) {
                this.companyId = companyId;
            }
        }
    }
}
