package com.szy.yishopcustomer.ResponseModel.OrderList;

import java.util.Map;

/**
 * Created by lw on 2016/6/12.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class GoodsListModel {
    public String record_id;
    public String order_id;
    public String goods_id;
    public String sku_id;
    public String spec_info;// "颜色:黑色",
    public String goods_name;// "ONLY2016春装新品印花翻边花苞袖圆领修身连衣裙女",
    public String goods_sn;
    public String goods_image;// "/upload/images/2016/05/06/14625191187927.jpg",
    public String goods_price;
    public String goods_number;
    public String other_price;
    public String parent_id;//        "": "0",
    public String is_gift; //       "": "0",
    public String is_evaluate;//        "": "0",
    public String goods_status;//        "": "0",
    public String give_integral;//        "": "0",
    public String goods_stock;//        "": 101,
    public String goods_default_price;//       "": "249.00",
    public String sku_img;//       "": "/upload/images/2016/05/06/14625191187927.jpg",
    public String send_number;//       "": 0,
    public String goods_status_format;//     "": "待发货",
    public String goods_service_status_format;//      "": "正常",
    public String back_id;      //"": "0"
    public String goods_back_format;
    public String max_integral_num;
    public int goods_type;
    public Map<String, GiftsListModel> gifts_list;
}
