package com.lyzb.jbx.model.dynamic;

import java.util.List;

/**
 * Created by :TYK
 * Date: 2019/3/18  10:39
 * Desc:  动态详情点赞model
 */
public class DynamicLikeModel {

    /**
     * pageNum : 1
     * pageSize : 10
     * total : 1
     * pages : 1
     * list : [{"id":"231b3bb273aa40fb9b092c25c4b9a257","topicId":"ace039b94ade11e99aa6e0d55e132c77","userId":109922,"createDate":"2019-03-20 17:02:13","type":2,"userName":"JBX188SXFR0004","userActionVos":[{"id":640,"actionId":1,"groupId":"207210707098272192","userId":109922,"addTime":"2016-06-13 17:29:50","startDate":"2016-06-13 17:29:50","endDate":"2020-06-13 17:29:50","actionName":"vip1","imageUrl":"http://img1.jpg"}]}]
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
         * id : 231b3bb273aa40fb9b092c25c4b9a257
         * topicId : ace039b94ade11e99aa6e0d55e132c77
         * userId : 109922
         * createDate : 2019-03-20 17:02:13
         * type : 2
         * userName : JBX188SXFR0004
         * userActionVos : [{"id":640,"actionId":1,"groupId":"207210707098272192","userId":109922,"addTime":"2016-06-13 17:29:50","startDate":"2016-06-13 17:29:50","endDate":"2020-06-13 17:29:50","actionName":"vip1","imageUrl":"http://img1.jpg"}]
         */

        private String id;
        private String topicId;
        private int userId;
        private String createDate;
        private int type;
        private String userName;
        private String headimg;
        private String companyInfo;
        private List<UserActionVosBean> userActionVos;
        private String shopName;
        private String shopAddress;

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopAddress() {
            return shopAddress;
        }

        public void setShopAddress(String shopAddress) {
            this.shopAddress = shopAddress;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getCompanyInfo() {
            return companyInfo;
        }

        public void setCompanyInfo(String companyInfo) {
            this.companyInfo = companyInfo;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTopicId() {
            return topicId;
        }

        public void setTopicId(String topicId) {
            this.topicId = topicId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
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

        public List<UserActionVosBean> getUserActionVos() {
            return userActionVos;
        }

        public void setUserActionVos(List<UserActionVosBean> userActionVos) {
            this.userActionVos = userActionVos;
        }

        public static class UserActionVosBean {
            /**
             * id : 640
             * actionId : 1
             * groupId : 207210707098272192
             * userId : 109922
             * addTime : 2016-06-13 17:29:50
             * startDate : 2016-06-13 17:29:50
             * endDate : 2020-06-13 17:29:50
             * actionName : vip1
             * imageUrl : http://img1.jpg
             */

            private int id;
            private int actionId;
            private String groupId;
            private int userId;
            private String addTime;
            private String startDate;
            private String endDate;
            private String actionName;
            private String imageUrl;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getActionId() {
                return actionId;
            }

            public void setActionId(int actionId) {
                this.actionId = actionId;
            }

            public String getGroupId() {
                return groupId;
            }

            public void setGroupId(String groupId) {
                this.groupId = groupId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public String getStartDate() {
                return startDate;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
            }

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }

            public String getActionName() {
                return actionName;
            }

            public void setActionName(String actionName) {
                this.actionName = actionName;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }
        }
    }
}
