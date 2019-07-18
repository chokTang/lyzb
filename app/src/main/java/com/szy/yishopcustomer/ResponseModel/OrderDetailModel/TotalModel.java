package com.szy.yishopcustomer.ResponseModel.OrderDetailModel;

import com.szy.yishopcustomer.Constant.Macro;

/**
 * Created by liwei on 2016/8/27.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class TotalModel {
    public String goods_amount;//商品总额
    public String goods_amount_format;//商品总额
    public String shipping_fee;//运费
    public String shipping_fee_format;//运费
    public String pay_code;//支付方式
    public String money_paid;//线上付款/待付款
    public String money_paid_format;//线上付款/待付款
    public String surplus;//余额
    public String surplus_format;//余额
    public String order_amount;//订单总额
    public String bonus;//平台红包
    public String all_bonus_format;//红包
    public String cash_more;//货到付款加价
    public String cash_more_format;//货到付款加价
    public String benefit;//优惠
    public Double store_card_price;//店铺储值卡
    public String store_card_price_format;//店铺储值卡


    public int pay_status;//支付状态
    public String order_status_code;//订单状态
    public String pay_status_format;//显示实付款或代付款的label
    public String integral;//元宝实际抵扣金额



    public int order_status = -1;

    //交易是否关闭
    public boolean isExchangeClosed() {
        if (order_status == Macro.ORDER_DISABLE || order_status == Macro.ORDER_CANCEL || order_status == Macro.ORDER_SYS_CANCEL) {
            return true;
        }

        return false;
    }

}
