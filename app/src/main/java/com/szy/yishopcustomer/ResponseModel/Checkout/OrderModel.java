package com.szy.yishopcustomer.ResponseModel.Checkout;

/**
 * Created by liwei on 17/3/28.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class OrderModel {

    public String goods_amount_format;//商品总额
    public String shipping_fee;//运费
    public String cash_more;//货到付款加价
    public String total_bonus_amount_format;//红包
    public String balance_format;//余额
    public String balance;

    public String money_pay;//剩余应付金额
    public String money_pay_format;
    public String order_amount;//使用红包后的订单总额
    public boolean is_cod;//是否货到付款
    public boolean is_cash;//是否加价
    public String cash_more_format;//货到付款加价
}
