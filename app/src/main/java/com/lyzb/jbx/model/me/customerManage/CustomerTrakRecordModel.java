package com.lyzb.jbx.model.me.customerManage;

import java.util.List;

/**
 * @author wyx
 * @role 客户跟进记录
 * @time 2019 2019/5/8 13:54
 */

public class CustomerTrakRecordModel {


    /**
     * pageNum : 1
     * pageSize : 30
     * total : 1
     * pages : 1
     * list : [{"id":1,"customerId":2,"content":"这个客户很重要，对于整个营销团队影响力很大","creatorTime":"2019-05-08 10:46:33","addUserId":110890}]
     */

    private String pageNum;
    private String pageSize;
    private String total;
    private String pages;
    private List<ListBean> list;

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
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
         * id : 1
         * customerId : 2
         * content : 这个客户很重要，对于整个营销团队影响力很大
         * creatorTime : 2019-05-08 10:46:33
         * addUserId : 110890
         */

        private String id;
        /**
         * 客户id
         */
        private String customerId;
        /**
         * 跟进记录
         */
        private String content;
        /**
         * 添加时间
         */
        private String creatorTime;
        /**
         * 操作人id
         */
        private String addUserId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatorTime() {
            return creatorTime;
        }

        public void setCreatorTime(String creatorTime) {
            this.creatorTime = creatorTime;
        }

        public String getAddUserId() {
            return addUserId;
        }

        public void setAddUserId(String addUserId) {
            this.addUserId = addUserId;
        }
    }
}
