package com.szy.yishopcustomer.ResponseModel.Back;

/**
 * Created by liwei on 2017/3/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BackItemModel {
    public String back_id;
    public String back_sn;
    public String back_type;

    public String sku_name;
    public String sku_image;

    public String order_amount;
    public String refund_money;//退款金额
    public String refund_freight;//运费金额

    public ShopModel shop;
    public String back_status_format;
}
