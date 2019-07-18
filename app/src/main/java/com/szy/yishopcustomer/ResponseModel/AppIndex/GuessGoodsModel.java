package com.szy.yishopcustomer.ResponseModel.AppIndex;

/**
 * @author wyx
 * @role 猜你喜欢 model
 * @time 2018 2018/8/14 10:40
 */

public class GuessGoodsModel {


    public String goods_id;
    public String shop_name;
    public String shop_id;
    public String goods_name;
    public String goods_image;
    public String goods_price;
    public String goods_price_format;

    public int max_integral_num;//元宝最高抵扣数目

    public String goods_bxprice_format;//宝箱价

    public String max_integral_num_format;//元宝最高抵扣文本
    public String sku_id;
    /**标签-数组形式*/
    public String tags;


    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }
}
