package com.szy.yishopcustomer.ResponseModel.OrderList;

import java.util.List;

/**
 * Created by lw on 2016/6/12.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class OrderListModel {
    public String order_id;
    public String order_sn;
    public String parent_sn;
    public String user_id;
    public int order_status = -1;
    public String shop_id;
    public String site_id;
    public String store_id;
    public int shipping_status;
    public String pay_status;
    public String order_status_code;// "unpayed",
    public String order_status_format;// "买家已付款",;
    public String order_amount;
    public String shipping_fee;
    public String rowspan_all;
    public String goods_name;
    public String shop_name;
    public String goods_num;
    public String pickup_name;
    public String pickup_id;
    public String address_lng;
    public String address_lat;
    public Double change_amount;
    public int order_cancel;
    public int delay_days;

    public String reachbuy_code;


    public int order_type;
    public int groupon_status;
    public String groupon_status_format;
    public String group_sn;

    public String integral;

    //public ShopInfoModel shop_info;
    public List<GoodsListModel> goods_list;
    public List buttons;

}
