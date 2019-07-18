package com.szy.yishopcustomer.Other;

/**
 * 同城生活 -外卖订单删除- Bus
 * Created by Administrator on 2018/6/21.
 */

public class SameCityDeleteOrderBus {
    private String orderId;

    public SameCityDeleteOrderBus(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
