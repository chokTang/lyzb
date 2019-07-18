package com.szy.yishopcustomer.ResponseModel;

import java.util.List;

/**
 * Created by liwei on 2017/8/28.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */

public class UserBonusModel {

    public int code;
    public DataBean data;

    public static class DataBean {

        public List<ListBean> list;
        public int system_bonus_count;//"0",
        public int shop_bonus_count;//"10",
        public String type;//1,
        public PageModel page;

        public static class ListBean {
            public String user_bonus_id;// "200",
            public String user_id;// "4",
            public String bonus_id;// "164",
            public String bonus_price;// "10.00",
            public String start_time;// "1481472000",
            public String end_time;// "1482076800",
            public String add_time;// "1481521393",
            public String order_sn;// "0",
            public String bonus_status;// "0",
            public String is_delete;// "0",
            public String user_name;// "苹果店长",
            public String shop_id;// "2",
            public String use_range;// "0",
            public String shop_name;// "达芙妮旗舰店",
            public String min_goods_amount;// "100.00",
            public String start_time_format;// "2016-12-12",
            public String end_time_format;// "2016-12-19",
            public String search_url;// "/search.html?shop_id=2",
            public String bonus_desc;// "使用条件：全站通用 购物满￥100.00可用"
            public String is_self_shop;
        }

        public static class PageModel {
            public String page_key;//"page",
            public String page_id;//"pagination",
            public String default_page_size;//15,
            public int cur_page;//1,
            public String page_size;//"5",
            public String record_count;//10,
            public int page_count;//2,
            public String offset;//0,

        }
    }

}
