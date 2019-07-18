package com.lyzb.jbx.model.me.access;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的-访客分析-访客的商城交易记录实体
 * 接口：/lbs/gs/home/getOrdersByCard
 *
 * @author shidengzhong
 */
public class AccessGoodsModel {
    private int orderCount;
    private float orderAmount;
    private String addTime;
    private List<AccessGoodsItemModel> list;

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

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public List<AccessGoodsItemModel> getList() {
        if (list == null)
            return new ArrayList<>();
        return list;
    }

    public void setList(List<AccessGoodsItemModel> list) {
        this.list = list;
    }
}
