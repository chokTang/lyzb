package com.szy.yishopcustomer.ResponseModel.SameCity.Order;

import java.util.List;

/**
 * Created by Administrator on 2018/5/28.
 */

public class OrderListResult {

    private int pageNum;
    private int pageSize;
    private int total;
    private int pages;
    private List<OrderModel> list;
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getPageNum() {
        return pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getPageSize() {
        return pageSize;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    public int getTotal() {
        return total;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
    public int getPages() {
        return pages;
    }

    public void setList(List<OrderModel> list) {
        this.list = list;
    }
    public List<OrderModel> getList() {
        return list;
    }
}
