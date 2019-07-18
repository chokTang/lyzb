package com.szy.yishopcustomer.ViewModel;

import java.util.List;

/**
 * Created by Smart on 2017/7/14.
 */

public class ShopCartModel {

    /**
     * code : 0
     * data : null
     * message :
     * count : 3
     * amount : 267
     * cart_count : 3
     * select_goods_number : 3
     * select_goods_amount : 267
     * amount_format : ￥267
     * type : 1
     * cart_goods_list : [{"cart_id":"963","user_id":null,"session_id":"iti0f9imaj55jfo4vlanlfkt30","shop_id":"2","goods_id":"275","sku_id":"813","goods_name":"完美芦荟胶3只组合正品祛痘淡痘印晒后修护大包装","goods_number":"3","goods_price":89,"goods_type":"0","parent_id":"0","is_gift":"0","buyer_type":"0","add_time":"1500020834","cat_id":"56","brand_id":"71","goods_sn":"","goods_status":true,"goods_audit":"1","goods_image":"/taobao-yun-images/521226854595/TB1Uj.bGVXXXXcPaXXXXXXXXXXX_!!0-item_pic.jpg","goods_images":"a:1:{i:411;a:5:{i:0;s:76:\"/taobao-yun-images/521226854595/TB1Uj.bGVXXXXcPaXXXXXXXXXXX_!!0-item_pic.jpg\";i:1;s:76:\"/taobao-yun-images/521226854595/TB1Uj.bGVXXXXcPaXXXXXXXXXXX_!!0-item_pic.jpg\";i:2;s:75:\"/taobao-yun-images/521226854595/TB2mSCQepXXXXXnXpXXXXXXXXXX_!!725677994.jpg\";i:3;s:75:\"/taobao-yun-images/521226854595/TB2LZGFepXXXXcjXpXXXXXXXXXX_!!725677994.jpg\";i:4;s:75:\"/taobao-yun-images/521226854595/TB2Bne_epXXXXXiXXXXXXXXXXXX_!!725677994.jpg\";}}","give_integral":"0","invoice_type":"0","stock_mode":"1","spu_number":"90","contract_ids":"","act_id":"0","goods_moq":"1","sales_model":"0","sku_name":"完美芦荟胶3只组合正品祛痘淡痘印晒后修护大包装 654/27","sku_image":"/taobao-yun-images/521226854595/TB1Uj.bGVXXXXcPaXXXXXXXXXXX_!!0-item_pic.jpg","sku_number":"90","market_price":"114.00","spec_names":["颜色分类：654/27"],"sku_sn":"","original_price":"89.00","goods_min_number":1,"goods_max_number":"90","select":1,"activity":null,"goods_price_format":"￥89","market_price_format":"￥114.00"}]
     */
    public int code;
    public String data;
    public String message;
    public int count;
    public float amount;
    public int cart_count;
    public String select_goods_number;
    public String select_goods_amount;
    public String amount_format;
    public int type;
    public List<com.szy.yishopcustomer.ResponseModel.CartIndex.GoodsModel> cart_goods_list;
    public double start_price;
    public String select_goods_amount_format;

}
