package com.szy.yishopcustomer.ResponseModel.SameCity.Order;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/20.
 */

public class OrderShopingModel implements Serializable{
    private String shippingAddress;
    private String shippingId;
    private String shippingName;
    private String shippingPhone;
    private String shippingUserName;

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingId() {
        return shippingId;
    }

    public void setShippingId(String shippingId) {
        this.shippingId = shippingId;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getShippingPhone() {
        return shippingPhone;
    }

    public void setShippingPhone(String shippingPhone) {
        this.shippingPhone = shippingPhone;
    }

    public String getShippingUserName() {
        return shippingUserName;
    }

    public void setShippingUserName(String shippingUserName) {
        this.shippingUserName = shippingUserName;
    }
}
