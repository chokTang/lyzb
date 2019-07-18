package com.szy.yishopcustomer.ResponseModel.SameCity.Order;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/28.
 */

public class OrderGoodModel implements Serializable{
    private String id;
    private String orderId;
    private String prodId;
    private float prodAmount;
    private float prodPrice;
    private int prodNum;
    private String orderProdLogo;
    private String orderProdContent;
    private String prodName;
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getOrderId() {
        return orderId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }
    public String getProdId() {
        return prodId;
    }

    public void setProdAmount(float prodAmount) {
        this.prodAmount = prodAmount;
    }
    public float getProdAmount() {
        return prodAmount;
    }

    public void setProdPrice(float prodPrice) {
        this.prodPrice = prodPrice;
    }
    public float getProdPrice() {
        return prodPrice;
    }

    public void setProdNum(int prodNum) {
        this.prodNum = prodNum;
    }
    public int getProdNum() {
        return prodNum;
    }

    public void setOrderProdLogo(String orderProdLogo) {
        this.orderProdLogo = orderProdLogo;
    }
    public String getOrderProdLogo() {
        return orderProdLogo;
    }

    public void setOrderProdContent(String orderProdContent) {
        this.orderProdContent = orderProdContent;
    }
    public String getOrderProdContent() {
        return orderProdContent;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
    public String getProdName() {
        return prodName;
    }
}
