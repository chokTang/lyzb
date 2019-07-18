package com.lyzb.jbx.model.me.access;

import java.util.List;

public class AccessGoodsDetailModel {
    private int orderCount;
    private float orderAmount;
    private List<AccessGoodsModel> orderByDay;

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public float getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(float orderAmount) {
        this.orderAmount = orderAmount;
    }

    public List<AccessGoodsModel> getOrderByDay() {
        return orderByDay;
    }

    public void setOrderByDay(List<AccessGoodsModel> orderByDay) {
        this.orderByDay = orderByDay;
    }
}
