package com.lyzb.jbx.model.statistics;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * @author wyx
 * @role 名片商场交易记录1级实体。
 * @time 2019 2019/4/18 15:26
 */

public class AnalysisTransactionDateModel extends AbstractExpandableItem<AnalysisTransactionContentModel>
        implements MultiItemEntity {
    /**
     * 当天订单数
     */
    private int orderCount;
    /**
     * 订单总金额
     */
    private double orderAmount;
    /**
     * 当天日期
     */
    private String addTime;

    private List<AnalysisTransactionContentModel> list;


    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public List<AnalysisTransactionContentModel> getList() {
        return list;
    }

    public void setList(List<AnalysisTransactionContentModel> list) {
        this.list = list;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}
