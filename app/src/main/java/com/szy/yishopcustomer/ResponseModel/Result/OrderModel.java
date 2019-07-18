package com.szy.yishopcustomer.ResponseModel.Result;

/**
 * Created by 宗仁 on 16/8/1.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class OrderModel {
    public int is_cod;// 0,
    public double order_amount;// 171,
    public double money_paid;// 0,
    public int is_pay;// 1,
    public String order_amount_format;//'¥171",
    public String money_paid_format;//"¥0"


    public String reachbuy_code;
    public String rc_id;

    public int buy_type;

    public int pay_status;

    public String order_sn;
    public String add_time;

    public String total_integral;//可用元宝
    public String integral;//总消费元宝
    public String give_integral;//共计 获得元宝
}
