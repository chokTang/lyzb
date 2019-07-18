package com.szy.yishopcustomer.ResponseModel;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by Smart on 2017/10/16.
 */

public class ReachbuyGoodsList {

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {

        public int shop_id;
        public String shop_info;
        public String region_name;
        public String duration_time;
        public int is_collect;
        public String collect_count;
        public int goods_count;
        public String bonus_count;
        public String price_show;
        public PageModel page;
        public String filter;
        public String params;
        public String keyword;
        public String parent_id;
        public int cat_id;
        public int cart_count;
        public int select_goods_amount;
        public String select_goods_amount_format;
        public String shop_name;
        public int scroll;
        public String show_sale_number;
        public String rc_id;
        public String context;
        public String article;
        public List<GoodsModel> list;
        public List<CategoryModel> category;


    }
}
