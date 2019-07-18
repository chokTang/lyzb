package com.szy.yishopcustomer.ResponseModel;

import java.util.List;

/**
 * Created by Smart on 2017/6/22.
 */

public class IndexGoodListModel {

    private List<Goods1Bean> goods_1;

    public List<Goods1Bean> getGoods_1() {
        return goods_1;
    }

    public void setGoods_1(List<Goods1Bean> goods_1) {
        this.goods_1 = goods_1;
    }

    public static class Goods1Bean {
        /**
         * goods_id : 39798
         * sku_id : 44193
         * goods_price : 15.00
         * sku_name : 枪手蚊香片
         * goods_image : http://68dsw.oss-cn-beijing.aliyuncs.com/images/shop/2/gallery/2017/06/12/14972580852092.jpg?x-oss-process=image/resize,m_pad,limit_0,h_220,w_220
         * goods_name : 枪手蚊香片
         * is_best : 0
         * is_new : 0
         * is_hot : 0
         * sku_image : http://68dsw.oss-cn-beijing.aliyuncs.com/images/shop/2/gallery/2017/06/12/14972581451726.jpg?x-oss-process=image/resize,m_pad,limit_0,h_220,w_220
         * sort : 1
         */

        private String goods_id;
        private String sku_id;
        private String goods_price;
        private String sku_name;
        private String goods_image;
        private String goods_name;
        private String is_best;
        private String is_new;
        private String is_hot;
        private String sku_image;
        private String sort;

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
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

        public String getSku_name() {
            return sku_name;
        }

        public void setSku_name(String sku_name) {
            this.sku_name = sku_name;
        }

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getIs_best() {
            return is_best;
        }

        public void setIs_best(String is_best) {
            this.is_best = is_best;
        }

        public String getIs_new() {
            return is_new;
        }

        public void setIs_new(String is_new) {
            this.is_new = is_new;
        }

        public String getIs_hot() {
            return is_hot;
        }

        public void setIs_hot(String is_hot) {
            this.is_hot = is_hot;
        }

        public String getSku_image() {
            return sku_image;
        }

        public void setSku_image(String sku_image) {
            this.sku_image = sku_image;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }
    }
}
