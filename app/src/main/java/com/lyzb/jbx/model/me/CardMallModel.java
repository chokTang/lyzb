package com.lyzb.jbx.model.me;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wyx
 * @role 智能卡片-商城 model
 * @time 2019 2019/3/8 11:37
 */

public class CardMallModel implements Serializable {


    private PageBean page;
    private List<ListBean> list;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<ListBean> getList() {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class PageBean {
        /**
         * page_key : page
         * page_id : pagination
         * default_page_size : 10
         * cur_page : 2
         * page_size : 10
         * page_size_list : [10,50,500,1000]
         * record_count : 299
         * page_count : 30
         * offset : 10
         * url : null
         * sql : null
         */

        private String page_key;
        private String page_id;
        private int default_page_size;
        private int goods_audit;//审核状态 0 待审核 1 审核通过 2 未通过
        private int cur_page;
        private String page_size;
        private int record_count;
        private int page_count;
        private int offset;
        private Object url;
        private Object sql;
        private List<Integer> page_size_list;

        public int getGoods_audit() {
            return goods_audit;
        }

        public void setGoods_audit(int goods_audit) {
            this.goods_audit = goods_audit;
        }

        public String getPage_key() {
            return page_key;
        }

        public void setPage_key(String page_key) {
            this.page_key = page_key;
        }

        public String getPage_id() {
            return page_id;
        }

        public void setPage_id(String page_id) {
            this.page_id = page_id;
        }

        public int getDefault_page_size() {
            return default_page_size;
        }

        public void setDefault_page_size(int default_page_size) {
            this.default_page_size = default_page_size;
        }

        public int getCur_page() {
            return cur_page;
        }

        public void setCur_page(int cur_page) {
            this.cur_page = cur_page;
        }

        public String getPage_size() {
            return page_size;
        }

        public void setPage_size(String page_size) {
            this.page_size = page_size;
        }

        public int getRecord_count() {
            return record_count;
        }

        public void setRecord_count(int record_count) {
            this.record_count = record_count;
        }

        public int getPage_count() {
            return page_count;
        }

        public void setPage_count(int page_count) {
            this.page_count = page_count;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public Object getUrl() {
            return url;
        }

        public void setUrl(Object url) {
            this.url = url;
        }

        public Object getSql() {
            return sql;
        }

        public void setSql(Object sql) {
            this.sql = sql;
        }

        public List<Integer> getPage_size_list() {
            return page_size_list;
        }

        public void setPage_size_list(List<Integer> page_size_list) {
            this.page_size_list = page_size_list;
        }
    }

    public static class ListBean {
        /**
         * shop_name : xmx
         * shop_id : 1062
         * goods_audit : 1
         * goods_id : 325275
         * goods_name : ceshi123看见收到14
         * max_integral_num : 0
         * goods_price : 20.00
         * goods_url : http://www.jibaoh.com/goods-325275.html
         * goods_image : /system/config/default_image/default_goods_image_0.png
         * mobile_desc : null
         */

        private String shop_name;           //店铺名称
        private String shop_id;             //店铺id
        private int goods_audit;            //审核状态 0 待审核 1 审核通过 2 未通过
        private String goods_id;            //商品id
        private Boolean can_del;            //可删除
        private int can_buy;            //可买
        private String goods_name;          //商品名称
        private String max_integral_num;    //元宝最大抵扣
        private String goods_price;         //商品价格
        private String goods_url;
        private String goods_image;         //商品图片
        private Object mobile_desc;         //商品描述

        public Boolean getCan_del() {
            return can_del;
        }

        public void setCan_del(Boolean can_del) {
            this.can_del = can_del;
        }

        public int getCan_buy() {
            return can_buy;
        }

        public void setCan_buy(int can_buy) {
            this.can_buy = can_buy;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public int getGoods_audit() {
            return goods_audit;
        }

        public void setGoods_audit(int goods_audit) {
            this.goods_audit = goods_audit;
        }

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

        public String getMax_integral_num() {
            return max_integral_num;
        }

        public void setMax_integral_num(String max_integral_num) {
            this.max_integral_num = max_integral_num;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_url() {
            return goods_url;
        }

        public void setGoods_url(String goods_url) {
            this.goods_url = goods_url;
        }

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public Object getMobile_desc() {
            return mobile_desc;
        }

        public void setMobile_desc(Object mobile_desc) {
            this.mobile_desc = mobile_desc;
        }
    }
}