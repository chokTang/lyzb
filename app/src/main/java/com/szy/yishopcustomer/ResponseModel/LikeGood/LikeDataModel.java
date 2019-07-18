package com.szy.yishopcustomer.ResponseModel.LikeGood;

/**
 * @author wyx
 * @role 猜你喜欢 model
 * @time 2018 19:47
 */

public class LikeDataModel {

    public String goods_id;
    public String goods_name;
    public String shop_id;
    public String sku_id;
    public String goods_price;
    public String goods_image;
    public String max_integral_num;


    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getMax_integral_num() {
        return max_integral_num;
    }

    public void setMax_integral_num(String max_integral_num) {
        this.max_integral_num = max_integral_num;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }
}
