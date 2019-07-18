package com.szy.yishopcustomer.ResponseModel.SameCity.Order;

import java.util.List;

/**
 * 同城生活 外卖订单详情 实体
 * Created by Administrator on 2018/6/13.
 */

public class OrderTakeOutDetailModel {
    public String costCode;//核销码
    public String costTime;//消费时间
    public String gmtCreate;//下单时间
    public List<GoodModel> goodsList;//购买的商品集合
    public String invCode;//发票纳税人识别号
    public String invPayee;//发票抬头
    public String invType;//发票类型
    public String orderCode;//订单编码
    public String orderId;//订单ID
    public String orderMent;//订单类型（1团购，2到店付）
    public int orderStatus;//订单状态（1:待付款;2:待使用;3:待评价;4:退款中;5:已完成;6:已退款;7:已取消）
    public String orderStatusMsg;//订单状态描述
    public float payAmount;//订单总价
    public String payCode;//支付平台流水号
    public String payMent;//支付方式（1微信2支付宝）
    public String payMentMsg;//支付方式描述
    public float payPoint;//抵扣元宝
    public String payTime;//支付时间（回调成功时间）
    public int prodCount;//订单商品总数
    public String refundRefuseMsg;//商家拒绝理由
    public String refundTime;//退款理由1-5
    public List<OrderDistributionModel> shipping;//配送地址
    public float shippingFee;//配送费
    public int shippingStatus;//配送状态
    public String shopLogo;//店铺logo
    public String storeId;
    public String storeName;
    public int timeUp;//倒计时
    public String userId;
    public String userName;
    public String userPhone;

    public class GoodModel{
        public String id;
        public String orderId;
        public String orderProdContent;
        public String orderProdLogo;
        public String prodAmount;//商品总价
        public String prodId;//商品ID
        public String prodName;//商品名称
        public int prodNum;//商品数量
        public float prodPrice;//商品单价
    }

    public class OrderDistributionModel{
        public String shippingAddress;//配送地址
        public String shippingId;//配送地址ID
        public String shippingName;//配送类型
        public String shippingPhone;//联系电话
        public String shippingUserName;//收货人
    }
}
