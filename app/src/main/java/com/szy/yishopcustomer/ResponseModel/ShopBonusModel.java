package com.szy.yishopcustomer.ResponseModel;

/**
 * Created by Smart on 2017/10/16.
 */

public class ShopBonusModel {
    /**
     * bonus_id : 164
     * shop_id : 6
     * bonus_type : 1
     * bonus_name : 测试店铺红包
     * bonus_desc : null
     * bonus_image : null
     * send_type : 0
     * bonus_amount : 11.00
     * receive_count : 1
     * bonus_number : 11
     * use_range : 0
     * bonus_data : {"goods_ids":null}
     * min_goods_amount : 2.00
     * is_original_price : 1
     * start_time : 1508083200
     * end_time : 1508774399
     * is_enable : 1
     * is_delete : 0
     * add_time : 1508123726
     * receive_number : 1
     * used_number : 1
     * shop_name : 广缘超市
     * start_time_format : 2017-10-16
     * end_time_format : 2017-10-23
     * bonus_type_name : 到店送红包
     * min_goods_amount_format : ￥2.00
     * bonus_status : 0
     * bonus_status_format : 正常
     * user_bonus_status : 0
     * search_url : /search.html?shop_id=6
     * bonus_amount_format : ￥11.00
     * goods_ids : null
     */

    public String bonus_id;
    public String shop_id;
    public String bonus_type;
    public String bonus_name;
    public String bonus_desc;
    public String bonus_image;
    public String send_type;
    public String bonus_amount;
    public String receive_count;
    public String bonus_number;
    public String use_range;
    public BonusDataBean bonus_data;
    public String min_goods_amount;
    public String is_original_price;
    public String start_time;
    public String end_time;
    public String is_enable;
    public String is_delete;
    public String add_time;
    public int receive_number;
    public String used_number;
    public String shop_name;
    public String start_time_format;
    public String end_time_format;
    public String bonus_type_name;
    public String min_goods_amount_format;
    public int bonus_status;
    public String bonus_status_format;
    public int user_bonus_status;
    public String search_url;
    public String bonus_amount_format;
    public String goods_ids;

    public static class BonusDataBean {
        /**
         * goods_ids : null
         */

        public String goods_ids;
    }

}
