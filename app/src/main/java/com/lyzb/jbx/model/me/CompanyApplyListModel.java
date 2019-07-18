package com.lyzb.jbx.model.me;

import java.util.List;

public class CompanyApplyListModel {

    /**
     * msg : 查询成功!
     * data : {"pageNum":1,"pageSize":10,"total":2,"pages":1,"list":[{"applyId":4,"currentDepartment":"无","applyTime":"2019-04-19 11:18:40","auditState":1,"extId":"","userId":1,"headimg":"","gsName":""},{"applyId":4,"currentDepartment":"无","applyTime":"2019-04-19 11:18:40","auditState":1,"extId":"","userId":1,"headimg":"","gsName":""}]}
     * status : 200
     * applyCount :
     */

    private String msg;
    private DataBean data;
    private String status;
    private String applyCount;

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

    public String getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(String applyCount) {
        this.applyCount = applyCount;
    }

    public static class DataBean {
        /**
         * pageNum : 1
         * pageSize : 10
         * total : 2
         * pages : 1
         * list : [{"applyId":4,"currentDepartment":"无","applyTime":"2019-04-19 11:18:40","auditState":1,"extId":"","userId":1,"headimg":"","gsName":""},{"applyId":4,"currentDepartment":"无","applyTime":"2019-04-19 11:18:40","auditState":1,"extId":"","userId":1,"headimg":"","gsName":""}]
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
             * applyId : 4
             * currentDepartment : 无
             * applyTime : 2019-04-19 11:18:40
             * auditState : 1
             * extId :
             * userId : 1
             * headimg :
             * gsName :
             */

            private String applyId;
            private String currentDepartment;
            private String applyTime;
            private int auditState;
            private String extId;
            private String userId;
            private String headimg;
            private String gsName;

            public String getApplyId() {
                return applyId;
            }

            public void setApplyId(String applyId) {
                this.applyId = applyId;
            }

            public String getCurrentDepartment() {
                return currentDepartment;
            }

            public void setCurrentDepartment(String currentDepartment) {
                this.currentDepartment = currentDepartment;
            }

            public String getApplyTime() {
                return applyTime;
            }

            public void setApplyTime(String applyTime) {
                this.applyTime = applyTime;
            }

            public int getAuditState() {
                return auditState;
            }

            public void setAuditState(int auditState) {
                this.auditState = auditState;
            }

            public String getExtId() {
                return extId;
            }

            public void setExtId(String extId) {
                this.extId = extId;
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
        }
    }
}
