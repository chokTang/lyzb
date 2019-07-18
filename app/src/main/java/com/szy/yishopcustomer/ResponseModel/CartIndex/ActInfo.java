package com.szy.yishopcustomer.ResponseModel.CartIndex;


import java.util.List;
import java.util.TreeMap;

/**
 * Created by Smart on 2017/11/23.
 */

public class ActInfo{

    /**
     * act_id : 375
     * act_img : null
     * act_type : 10
     * act_name : 21e
     * purchase_num : 0
     * ext_info : a:3:{s:10:"price_mode";s:1:"0";s:9:"act_price";s:2:"11";s:13:"discount_show";s:1:"1";}
     * end_time : 1511508536
     * start_time : 1510903736
     * act_price : 0.30
     * act_stock : 0
     * sale_base : 0
     * sku_id : 2902
     * goods_number : 1
     * goods_status : 1
     * invalid : false
     * price_mode : 0
     * discount_show : 1
     * act_code : goods_mix
     * act_color : null
     * sku_ids : 2902_2903_2906_2940
     * cart_ids : 4957,4958,4959,4960
     * select : false
     * act_goods_price : 11.3
     * act_market_price : ￥99.00
     * act_max_number : 100
     * act_goods_amount : 11.3
     * act_market_amount : ￥99.00
     * act_goods_amount_format : ￥11.30
     * act_goods_price_format : ￥11.30
     * act_min_number : 1
     */

    public String act_id;
    public String act_img;
    public String act_type;
    public String act_name;
    public String purchase_num;
    public String ext_info;
    public String end_time;
    public String start_time;
    public String act_price;
    public String act_stock;
    public String sale_base;
    public String sku_id;
    public String goods_number;
    public String goods_status;
    public boolean invalid;
    public String price_mode;
    public String discount_show;
    public String act_code;
    public String act_color;
    public String sku_ids;
    public String cart_ids;
    public boolean select;
    public double act_goods_price;
    public String act_market_price;
    public String act_max_number;
    public double act_goods_amount;
    public String act_market_amount;
    public String act_goods_amount_format;
    public String act_goods_price_format;
    public int act_min_number;
    public String order_act_id;
    public TreeMap<String, GoodsModel> goods_list;

    public List<GiftModel> gift_list;
    public String reduce_cash;
    public String act_message;

    public boolean isInvalid = false;
}
