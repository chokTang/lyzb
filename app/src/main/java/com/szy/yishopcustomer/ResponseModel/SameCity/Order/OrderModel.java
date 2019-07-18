package com.szy.yishopcustomer.ResponseModel.SameCity.Order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/28.
 */

public class OrderModel implements Serializable {

    private String orderId;
    private String userId;
    private String orderCode;
    private String payTime;
    private float payAmount;
    private int payMent;
    private int orderMent;//1团购，2到店付
    private String gmtCreate;
    private int orderStatus;//（1:待付款;2:待使用;3:待评价;4:退款中;5:已完成;6:已退款;7:已取消;8:商
    private String storeId;
    private int payPoint;
    private String costCode;
    private String storeName;
    private String costTime;
    private String invoiceDutyParagraph;
    private String invoiceRise;
    private String invoiceType;
    private String orderStatusMsg;
    private float packFee;//包装费
    private String payMentMsg;
    private String payCode;
    private String userName;
    private String userPhone;
    private int prodCount;
    private String shopLogo;
    private int timeUp;
    private String refundRefuseMsg;
    private String refundTime;
    private String refundType;
    private float shippingFee;
    private String shippingTime;
    private List<OrderShopingModel> shipping;
    private int shippingStatus;
    private int shippingType;
    private List<OrderGoodModel> goodsList;

    private String storeAddress;
    private String serviceTel;
    private int refundTimeUp;
    private String shippingTimeEnd;
    private String shippingTimeStart;
    private String shopLat;
    private String shopLng;

    //缺少参数
    public String remark;

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayAmount(float payAmount) {
        this.payAmount = payAmount;
    }

    public float getPayAmount() {
        return payAmount;
    }

    public void setPayMent(int payMent) {
        this.payMent = payMent;
    }

    public int getPayMent() {
        return payMent;
    }

    public void setOrderMent(int orderMent) {
        this.orderMent = orderMent;
    }

    public int getOrderMent() {
        return orderMent;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setPayPoint(int payPoint) {
        this.payPoint = payPoint;
    }

    public int getPayPoint() {
        return payPoint;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    public String getCostCode() {
        return costCode;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setProdCount(int prodCount) {
        this.prodCount = prodCount;
    }

    public int getProdCount() {
        return prodCount;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setTimeUp(int timeUp) {
        this.timeUp = timeUp;
    }

    public int getTimeUp() {
        return timeUp;
    }

    public void setGoodsList(List<OrderGoodModel> goodsList) {
        this.goodsList = goodsList;
    }

    public List<OrderGoodModel> getGoodsList() {
        return goodsList;
    }

    public String getCostTime() {
        return costTime;
    }

    public void setCostTime(String costTime) {
        this.costTime = costTime;
    }

    public String getInvoiceDutyParagraph() {
        return invoiceDutyParagraph;
    }

    public void setInvoiceDutyParagraph(String invoiceDutyParagraph) {
        this.invoiceDutyParagraph = invoiceDutyParagraph;
    }

    public String getInvoiceRise() {
        return invoiceRise;
    }

    public void setInvoiceRise(String invoiceRise) {
        this.invoiceRise = invoiceRise;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getOrderStatusMsg() {
        return orderStatusMsg;
    }

    public void setOrderStatusMsg(String orderStatusMsg) {
        this.orderStatusMsg = orderStatusMsg;
    }

    public float getPackFee() {
        return packFee;
    }

    public void setPackFee(float packFee) {
        this.packFee = packFee;
    }

    public String getPayMentMsg() {
        return payMentMsg;
    }

    public void setPayMentMsg(String payMentMsg) {
        this.payMentMsg = payMentMsg;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getRefundRefuseMsg() {
        if ("null".equals(refundRefuseMsg))
            refundRefuseMsg = "";
        return refundRefuseMsg;
    }

    public void setRefundRefuseMsg(String refundRefuseMsg) {
        this.refundRefuseMsg = refundRefuseMsg;
    }

    public String getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(String refundTime) {
        this.refundTime = refundTime;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public float getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(float shippingFee) {
        this.shippingFee = shippingFee;
    }

    public List<OrderShopingModel> getShipping() {
        return shipping;
    }

    public void setShipping(List<OrderShopingModel> shipping) {
        this.shipping = shipping;
    }

    public int getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(int shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public int getShippingType() {
        return shippingType;
    }

    public void setShippingType(int shippingType) {
        this.shippingType = shippingType;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getServiceTel() {
        return serviceTel;
    }

    public void setServiceTel(String serviceTel) {
        this.serviceTel = serviceTel;
    }

    public int getRefundTimeUp() {
        return refundTimeUp;
    }

    public void setRefundTimeUp(int refundTimeUp) {
        this.refundTimeUp = refundTimeUp;
    }

    public String getShippingTime() {
        if (shippingTime == null) {
            return "";
        }
        return shippingTime;
    }

    public void setShippingTime(String shippingTime) {
        this.shippingTime = shippingTime;
    }

    public String getShippingTimeEnd() {
        return shippingTimeEnd;
    }

    public void setShippingTimeEnd(String shippingTimeEnd) {
        this.shippingTimeEnd = shippingTimeEnd;
    }

    public String getShippingTimeStart() {
        return shippingTimeStart;
    }

    public void setShippingTimeStart(String shippingTimeStart) {
        this.shippingTimeStart = shippingTimeStart;
    }

    public String getShopLat() {
        return shopLat;
    }

    public void setShopLat(String shopLat) {
        this.shopLat = shopLat;
    }

    public String getShopLng() {
        return shopLng;
    }

    public void setShopLng(String shopLng) {
        this.shopLng = shopLng;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", orderCode='" + orderCode + '\'' +
                ", payTime='" + payTime + '\'' +
                ", payAmount=" + payAmount +
                ", payMent=" + payMent +
                ", orderMent=" + orderMent +
                ", gmtCreate='" + gmtCreate + '\'' +
                ", orderStatus=" + orderStatus +
                ", storeId='" + storeId + '\'' +
                ", payPoint=" + payPoint +
                ", costCode='" + costCode + '\'' +
                ", storeName='" + storeName + '\'' +
                ", costTime='" + costTime + '\'' +
                ", invoiceDutyParagraph='" + invoiceDutyParagraph + '\'' +
                ", invoiceRise='" + invoiceRise + '\'' +
                ", invoiceType='" + invoiceType + '\'' +
                ", orderStatusMsg='" + orderStatusMsg + '\'' +
                ", packFee=" + packFee +
                ", payMentMsg='" + payMentMsg + '\'' +
                ", payCode='" + payCode + '\'' +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", prodCount=" + prodCount +
                ", shopLogo='" + shopLogo + '\'' +
                ", timeUp=" + timeUp +
                ", refundRefuseMsg='" + refundRefuseMsg + '\'' +
                ", refundTime='" + refundTime + '\'' +
                ", refundType='" + refundType + '\'' +
                ", shippingFee=" + shippingFee +
                ", shippingTime='" + shippingTime + '\'' +
                ", shipping=" + shipping +
                ", shippingStatus=" + shippingStatus +
                ", shippingType=" + shippingType +
                ", goodsList=" + goodsList +
                ", storeAddress='" + storeAddress + '\'' +
                ", serviceTel='" + serviceTel + '\'' +
                ", refundTimeUp=" + refundTimeUp +
                ", shippingTimeEnd='" + shippingTimeEnd + '\'' +
                ", shippingTimeStart='" + shippingTimeStart + '\'' +
                ", shopLat='" + shopLat + '\'' +
                ", shopLng='" + shopLng + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
