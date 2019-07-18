package com.lyzb.jbx.model.send;

import java.util.List;

/**
 * Created by :TYK
 * Date: 2019/5/15  10:34
 * Desc:
 */
public class GoodsList {

    /**
     * code : 2
     * data : [{"goods_name":"商品名称","goods_image":"/shop/389/gallery/2018/01/31/sp_20180131134928_95505.jpg","goods_price":"29.90","max_integral_num":"0"}]
     */

    private int code;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * goods_name : 商品名称
         * goods_image : /shop/389/gallery/2018/01/31/sp_20180131134928_95505.jpg
         * goods_price : 29.90
         * max_integral_num : 0
         */

        private String goods_name;
        private String goods_image;
        private String goods_price;
        private String goods_id;
        private String sku_id;
        private int can_buy=1;
        private String shop_name;
        private String max_integral_num;

        public int getCan_buy() {
            return can_buy;
        }

        public void setCan_buy(int can_buy) {
            this.can_buy = can_buy;
        }

        public String getSku_id() {
            return sku_id;
        }

        public void setSku_id(String sku_id) {
            this.sku_id = sku_id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
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
    }
}
