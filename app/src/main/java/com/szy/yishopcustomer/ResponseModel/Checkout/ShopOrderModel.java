package com.szy.yishopcustomer.ResponseModel.Checkout;

import java.util.List;

/**
 * Created by 宗仁 on 2016/7/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopOrderModel {
    public String order_amount;//4513,
    public String goods_amount;//4613,
    public String shipping_fee;//0,
    public String give_integral;//0,
    public String inv_fee;//0,
    public String is_cash;//1,
    public String cash_more;//0,
    public String shop_bonus_id;//"23",
    public String shop_id;//"23",
    public String shop_bonus_amount;//"100",
    public String order_amount_format;//"￥4,513.00",
    public String shop_bonus_amount_format;//"￥100.00",
    public String cash_more_format;//"￥0.00"
    public String shipping_fee_format;//"￥12.00"
    public String integral_amount_format;//元宝实际抵扣
    public int order_type;

    public int full_cut_amount;
    public String full_cut_amount_format;
    public List<String> full_cut_bonus;
    public String full_cut_bonus_format;
    public String shop_integral_num;
}
