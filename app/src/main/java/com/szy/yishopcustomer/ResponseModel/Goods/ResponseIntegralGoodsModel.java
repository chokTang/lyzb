package com.szy.yishopcustomer.ResponseModel.Goods;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by Smart on 2017/12/20.
 */

public class ResponseIntegralGoodsModel {

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {

        public ShopInfoBean shop_info;
        public String shop_goods_count;
        public String shop_collect_count;
        public String user_info;
        public GoodsBean goods;
        public boolean can_exchange;
        public String can_exchange_msg;
        public String context;
        public List<HotListBean> hot_list;

        public static class ShopInfoBean {

            public ShopModel shop;
            public String user;
            public String real;
            public String credit;
            public String customer_main;
            public String aliim_enable;
            public String address;
            public WangXin aliim;

        }


        public static class GoodsBean {
            /**
             * goods_id : 17
             * goods_name : 还是测试积分
             * goods_price : 99.00
             * market_price : 0.00
             * goods_number : 21
             * goods_integral : 1080
             * exchange_number : 1
             * start_time : 0
             * end_time : 0
             * add_time : 1513585923
             * goods_image : null
             * goods_images : [["/system/config/default_image/default_goods_image_0.png","/system/config/default_image/default_goods_image_0.png","/system/config/default_image/default_goods_image_0.png"]]
             * goods_video : null
             * pc_desc :
             * mobile_desc :
             * click_count : 0
             * keywords : null
             * sale_num : 0
             * collect_num : 0
             * goods_sort : 255
             * is_delete : 0
             * goods_status : 1
             * shop_id : 2
             * goods_price_format : ￥99.00
             * shop_collect : false
             */

            public String goods_id;
            public String goods_name;
            public String goods_price;
            public String market_price;
            public String goods_number;
            public int goods_integral;
            public int exchange_number;
            public int start_time;
            public int end_time;
            public int add_time;
            public String goods_image;
            public String goods_video;
            public String pc_desc;
            public String mobile_desc;
            public int click_count;
            public String keywords;
            public int sale_num;
            public int collect_num;
            public int goods_sort;
            public int is_delete;
            public int goods_status;
            public String shop_id;
            public String goods_price_format;
            public boolean shop_collect;
            public List<List<String>> goods_images;
        }

        public static class HotListBean {
            /**
             * goods_id : 12
             * goods_name : 荣事达（Royalstar）养生壶玻璃加厚1.7L全自动多功能煮茶器YSH1706
             * goods_price : 399.00
             * goods_number : 42
             * goods_integral : 3900
             * exchange_number : 35
             * goods_image : /backend/1/images/2017/12/13/15131534390123.jpg
             * goods_price_format : ￥99.00
             */

            public int goods_id;
            public String goods_name;
            public String goods_price;
            public int goods_number;
            public int goods_integral;
            public int exchange_number;
            public String goods_image;
            public String goods_price_format;
        }
    }
}
