package com.lyzb.jbx.model.send;

import java.io.Serializable;

/**
 * Created by :TYK
 * Date: 2019/5/8  14:49
 * Desc:
 */
public class DefaultProductModel implements Serializable {

    /**
     * sku_id : 100783
     * goods_id : 80718
     * sku_name : 碧玺镶嵌祖母绿切工C114 绿色 14号 AU750
     * sku_image : /shop/4502/gallery/2018/12/03/15438220232192.jpg
     * agency_price : 0.00
     * goods_price : 45800.00
     * mobile_price : 0.00
     * market_price : 0.00
     * click_count : 32
     * max_integral_num : 8700
     * warn_number : 0
     * goods_number : 2
     * guagua_desc : null
     * collect_num : 0
     * share_fee : 2100.00
     * sale_num : 0
     * other_attrs : []
     * goods_images : /shop/4502/gallery/2018/12/03/15438220232192.jpg,/shop/4502/gallery/2018/12/03/15438220223799.jpg,/shop/4502/gallery/2018/12/03/15438220221574.jpg,/shop/4502/gallery/2018/12/03/15438220236934.jpg,/shop/4502/gallery/2018/12/03/15438220221325.jpg
     * goods_image : http://lyzbimage.jbxgo.com/lyzbjbxgo/shop/4502/gallery/2018/12/03/15438220223799.jpg
     * goods_subname : 港版镶嵌，私家手工高定，官方直售正品，光泽闪耀
     * deduction_rate : 10.00
     * make_money : 1145.00
     * hfive_url : http://m.jbxgo.com/goods-80718.html
     * server_name : http://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzbjbxgo
     * goods : {"shop_id":"4502","goods_status":"1","is_delete":"0","goods_sort":"255","goods_name":"碧玺镶嵌祖母绿切工C114","cat_id":"747","goods_price":"43500.00","mobile_price":"0.00","agency_price":null,"market_price":"86900.00","sales_model":"0"}
     * supplier_id : cca34b5e-f240-11e8-840e-7cd30adaaeaa
     */

    private boolean isSelected = false;

    private String sku_id;
    private String goods_id;
    private String sku_name;
    private String sku_image;
    private String agency_price;
    private String goods_price;
    private String mobile_price;
    private String market_price;
    private String click_count;
    private String max_integral_num;
    private String warn_number;
    private String goods_number;
    private String guagua_desc;
    private String collect_num;
    private String share_fee;
    private String sale_num;
    private String other_attrs;
    private String goods_images;
    private String goods_image;
    private String goods_subname;
    private String deduction_rate;
    private String make_money;
    private String hfive_url;
    private String server_name;
    private GoodsBean goods;
    private String supplier_id;
    private String shop_name;

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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

    public String getSku_name() {
        return sku_name;
    }

    public void setSku_name(String sku_name) {
        this.sku_name = sku_name;
    }

    public String getSku_image() {
        return sku_image;
    }

    public void setSku_image(String sku_image) {
        this.sku_image = sku_image;
    }

    public String getAgency_price() {
        return agency_price;
    }

    public void setAgency_price(String agency_price) {
        this.agency_price = agency_price;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getMobile_price() {
        return mobile_price;
    }

    public void setMobile_price(String mobile_price) {
        this.mobile_price = mobile_price;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getClick_count() {
        return click_count;
    }

    public void setClick_count(String click_count) {
        this.click_count = click_count;
    }

    public String getMax_integral_num() {
        return max_integral_num;
    }

    public void setMax_integral_num(String max_integral_num) {
        this.max_integral_num = max_integral_num;
    }

    public String getWarn_number() {
        return warn_number;
    }

    public void setWarn_number(String warn_number) {
        this.warn_number = warn_number;
    }

    public String getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(String goods_number) {
        this.goods_number = goods_number;
    }

    public String getGuagua_desc() {
        return guagua_desc;
    }

    public void setGuagua_desc(String guagua_desc) {
        this.guagua_desc = guagua_desc;
    }

    public String getCollect_num() {
        return collect_num;
    }

    public void setCollect_num(String collect_num) {
        this.collect_num = collect_num;
    }

    public String getShare_fee() {
        return share_fee;
    }

    public void setShare_fee(String share_fee) {
        this.share_fee = share_fee;
    }

    public String getSale_num() {
        return sale_num;
    }

    public void setSale_num(String sale_num) {
        this.sale_num = sale_num;
    }

    public String getOther_attrs() {
        return other_attrs;
    }

    public void setOther_attrs(String other_attrs) {
        this.other_attrs = other_attrs;
    }

    public String getGoods_images() {
        return goods_images;
    }

    public void setGoods_images(String goods_images) {
        this.goods_images = goods_images;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }

    public String getGoods_subname() {
        return goods_subname;
    }

    public void setGoods_subname(String goods_subname) {
        this.goods_subname = goods_subname;
    }

    public String getDeduction_rate() {
        return deduction_rate;
    }

    public void setDeduction_rate(String deduction_rate) {
        this.deduction_rate = deduction_rate;
    }

    public String getMake_money() {
        return make_money;
    }

    public void setMake_money(String make_money) {
        this.make_money = make_money;
    }

    public String getHfive_url() {
        return hfive_url;
    }

    public void setHfive_url(String hfive_url) {
        this.hfive_url = hfive_url;
    }

    public String getServer_name() {
        return server_name;
    }

    public void setServer_name(String server_name) {
        this.server_name = server_name;
    }

    public GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public static class GoodsBean implements Serializable{
        /**
         * shop_id : 4502
         * goods_status : 1
         * is_delete : 0
         * goods_sort : 255
         * goods_name : 碧玺镶嵌祖母绿切工C114
         * cat_id : 747
         * goods_price : 43500.00
         * mobile_price : 0.00
         * agency_price : null
         * market_price : 86900.00
         * sales_model : 0
         */

        private String shop_id;
        private String goods_status;
        private String is_delete;
        private String goods_sort;
        private String goods_name;
        private String cat_id;
        private String goods_price;
        private String mobile_price;
        private String agency_price;
        private String market_price;
        private String sales_model;

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getGoods_status() {
            return goods_status;
        }

        public void setGoods_status(String goods_status) {
            this.goods_status = goods_status;
        }

        public String getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(String is_delete) {
            this.is_delete = is_delete;
        }

        public String getGoods_sort() {
            return goods_sort;
        }

        public void setGoods_sort(String goods_sort) {
            this.goods_sort = goods_sort;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getMobile_price() {
            return mobile_price;
        }

        public void setMobile_price(String mobile_price) {
            this.mobile_price = mobile_price;
        }

        public String getAgency_price() {
            return agency_price;
        }

        public void setAgency_price(String agency_price) {
            this.agency_price = agency_price;
        }

        public String getMarket_price() {
            return market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public String getSales_model() {
            return sales_model;
        }

        public void setSales_model(String sales_model) {
            this.sales_model = sales_model;
        }
    }
}
