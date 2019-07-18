package com.lyzb.jbx.model.statistics;

import java.util.List;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/23 11:28
 */

public class AnalysisTransactionModel {


    /**
     * orderCount : 5
     * orderAmount : 5
     * orderByDay : [{"orderCount":1,"orderAmount":1,"addTime":"2019-04-29 09:53:34","list":[{"goodsNumber":2,"goodsId":337402,"goodsName":"setGoodsName","goodsPrice":1,"accountName":"cod1","userName":"宁亿"}]},{"orderCount":1,"orderAmount":1,"addTime":"2019-04-29 09:53:34","list":[{"goodsNumber":2,"goodsId":337402,"goodsName":"setGoodsName","goodsPrice":1,"accountName":"cod1","userName":"宁亿"}]},{"orderCount":1,"orderAmount":1,"addTime":"2019-04-29 09:53:34","list":[{"goodsNumber":2,"goodsId":337402,"goodsName":"setGoodsName","goodsPrice":1,"accountName":"cod1","userName":"宁亿"}]},{"orderCount":1,"orderAmount":1,"addTime":"2019-04-29 09:53:34","list":[{"goodsNumber":2,"goodsId":337402,"goodsName":"setGoodsName","goodsPrice":1,"accountName":"cod1","userName":"宁亿"}]},{"orderCount":1,"orderAmount":1,"addTime":"2019-04-29 09:53:34","list":[{"goodsNumber":2,"goodsId":337402,"goodsName":"setGoodsName","goodsPrice":1,"accountName":"cod1","userName":"宁亿"}]}]
     */
    private int code;
    private String msg;
    private int orderCount;
    private int orderAmount;
    private List<AnalysisTransactionDateModel> orderByDay;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public List<AnalysisTransactionDateModel> getOrderByDay() {
        return orderByDay;
    }

    public void setOrderByDay(List<AnalysisTransactionDateModel> orderByDay) {
        this.orderByDay = orderByDay;
    }
}
