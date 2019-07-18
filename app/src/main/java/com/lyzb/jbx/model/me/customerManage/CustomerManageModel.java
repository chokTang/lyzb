package com.lyzb.jbx.model.me.customerManage;

import java.util.List;

/**
 * @author wyx
 * @role 客户管理
 * @time 2019 2019/5/8 13:40
 */

public class CustomerManageModel {


    /**
     * pageNum : 1
     * pageSize : 30
     * total : 1
     * pages : 1
     * list : [{"id":2,"remarkName":"小不点","sex":1,"mobile":"187237914678","company":"重庆礼仪之邦电子商务有限公司","position":"java开发","residence":"50，105，2355","address":"重庆市两江新区海王星","birthday":"2010-09-10 00:00:00","wxAccount":"18723465895","remark":"重要客户","customerUserId":110891,"createTime":"2019-05-08 10:44:38","addUserId":110890,"status":"1","browseNum":0,"topicNum":0,"goodsNum":0,"customersFollowNum":0,"headimg":"www.jibaoxianggo.com/d.jpg"}]
     */

    private int pageNum;
    private int pageSize;
    private int total;
    private int pages;
    private List<CustomerInfoModel> list;

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

    public List<CustomerInfoModel> getList() {
        return list;
    }

    public void setList(List<CustomerInfoModel> list) {
        this.list = list;
    }

}
