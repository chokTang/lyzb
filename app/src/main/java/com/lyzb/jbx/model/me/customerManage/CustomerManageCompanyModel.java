package com.lyzb.jbx.model.me.customerManage;

import java.util.List;

/**
 * @author wyx
 * @role
 * @time 2019 2019/5/10 10:19
 */

public class CustomerManageCompanyModel {


    /**
     * msg : 查询成功!
     * data : {"pageNum":1,"pageSize":10,"total":99,"pages":10,"list":[{"userId":110718,"extId":7672,"headImg":"http://b-ssl.duitang.com/uploads/item/201902/04/20190204151133_wvebd.jpg","accountName":"ppn030","userName":"（ccccccccc）","lastLoginTime":"2019-04-17 16:31:33","totalCus":"共0名客户"}]}
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

    public class DataBean {
        /**
         * pageNum : 1
         * pageSize : 10
         * total : 99
         * pages : 10
         * list : [{"userId":110718,"extId":7672,"headImg":"http://b-ssl.duitang.com/uploads/item/201902/04/20190204151133_wvebd.jpg","accountName":"ppn030","userName":"（ccccccccc）","lastLoginTime":"2019-04-17 16:31:33","totalCus":"共0名客户"}]
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

        public class ListBean {
            /**
             * userId : 110718
             * extId : 7672
             * headImg : http://b-ssl.duitang.com/uploads/item/201902/04/20190204151133_wvebd.jpg
             * accountName : ppn030
             * userName : （ccccccccc）
             * lastLoginTime : 2019-04-17 16:31:33
             * totalCus : 共0名客户
             */

            private String userId;
            private String extId;
            private String headImg;
            private String accountName;
            private String userName;
            private String lastLoginTime;
            private String totalCus;

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

            public String getTotalCus() {
                return totalCus;
            }

            public void setTotalCus(String totalCus) {
                this.totalCus = totalCus;
            }
        }
    }
}
