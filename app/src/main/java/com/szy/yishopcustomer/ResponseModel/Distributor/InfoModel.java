package com.szy.yishopcustomer.ResponseModel.Distributor;

/**
 * Created by liwei on 17/2/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class InfoModel {
    public String code;// 1,
    public String message;// "累计订单金额不足！",

    public String dis_order_money;// 应满足的订单金额"10",
    public String order_amount;// 实际订单金额"0.03",
    public String dis_order_count;//应满足的订单数量
    public String order_count;//实际订单数量

    public String dis_con;// "1"：满足消费金额；“2”：满足订单数量
}
