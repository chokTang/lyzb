package com.lyzb.jbx.model.me;

import java.util.List;

/**
 * @author wyx
 * @role 我的圈子-成员列表 model
 * @time 2019 2019/3/7 20:20
 */

public class CircleMerberModel {


    /**
     * isJoin : true
     * data : {"pageNum":1,"pageSize":10,"total":1,"pages":1,"list":[{"pageNum":1,"extPageSize":30,"childPageSize":1,"id":506,"groupId":"77068079529985","memberId":"jbx109112","banned":false,"admin":false,"owner":true,"black":false,"pass":true,"userName":"测试名称1122","headimg":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/20/lj52hykzTN.jpg","userId":"109112","companyInfo":"企业名称测试new"}]}
     * isOwner : true
     * banned : false
     * status : 200
     */

    private boolean isJoin;
    private DataBean data;
    private boolean isOwner;
    private boolean banned;
    private String status;

    public boolean isIsJoin() {
        return isJoin;
    }

    public void setIsJoin(boolean isJoin) {
        this.isJoin = isJoin;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isIsOwner() {
        return isOwner;
    }

    public void setIsOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
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
         * total : 1
         * pages : 1
         * list : [{"pageNum":1,"extPageSize":30,"childPageSize":1,"id":506,"groupId":"77068079529985","memberId":"jbx109112","banned":false,"admin":false,"owner":true,"black":false,"pass":true,"userName":"测试名称1122","headimg":"https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/20/lj52hykzTN.jpg","userId":"109112","companyInfo":"企业名称测试new"}]
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
             * pageNum : 1
             * extPageSize : 30
             * childPageSize : 1
             * id : 506
             * groupId : 77068079529985
             * memberId : jbx109112
             * banned : false
             * admin : false
             * owner : true
             * black : false
             * pass : true
             * userName : 测试名称1122
             * headimg : https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzblbs/client/2019/03/20/lj52hykzTN.jpg
             * userId : 109112
             * companyInfo : 企业名称测试new
             */

            private int pageNum;
            private int extPageSize;
            private int childPageSize;
            private int id;
            private String groupId;
            private String memberId;
            private boolean banned;
            private boolean admin;
            private boolean owner;
            private boolean black;
            private boolean pass;
            private String userName;
            private String headimg;
            private String userId;
            private String companyInfo;

            public int getPageNum() {
                return pageNum;
            }

            public void setPageNum(int pageNum) {
                this.pageNum = pageNum;
            }

            public int getExtPageSize() {
                return extPageSize;
            }

            public void setExtPageSize(int extPageSize) {
                this.extPageSize = extPageSize;
            }

            public int getChildPageSize() {
                return childPageSize;
            }

            public void setChildPageSize(int childPageSize) {
                this.childPageSize = childPageSize;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getGroupId() {
                return groupId;
            }

            public void setGroupId(String groupId) {
                this.groupId = groupId;
            }

            public String getMemberId() {
                return memberId;
            }

            public void setMemberId(String memberId) {
                this.memberId = memberId;
            }

            public boolean isBanned() {
                return banned;
            }

            public void setBanned(boolean banned) {
                this.banned = banned;
            }

            public boolean isAdmin() {
                return admin;
            }

            public void setAdmin(boolean admin) {
                this.admin = admin;
            }

            public boolean isOwner() {
                return owner;
            }

            public void setOwner(boolean owner) {
                this.owner = owner;
            }

            public boolean isBlack() {
                return black;
            }

            public void setBlack(boolean black) {
                this.black = black;
            }

            public boolean isPass() {
                return pass;
            }

            public void setPass(boolean pass) {
                this.pass = pass;
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

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getCompanyInfo() {
                return companyInfo;
            }

            public void setCompanyInfo(String companyInfo) {
                this.companyInfo = companyInfo;
            }
        }
    }
}
