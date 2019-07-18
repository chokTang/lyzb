package com.szy.yishopcustomer.ResponseModel.Distrib;

import java.util.List;

/**
 * Created by liwei on 2017/7/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribGoodsListModel {

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {

        public List<DistribGoodsItemModel> list;
        public PaegModel page;


        public static class DistribGoodsItemModel {
            public String id;//"32",
            public String goods_id;//"39029",
            public String shop_id;//"248",
            public String site_id;//"1",
            public String distrib_type;//"0",
            public String start_time;//"-28800",
            public String end_time;//"-28800",
            public String percent;//"5",
            public String goods_name;//"好丽友木糖醇",
            public String goods_image;//"http://68dsw.oss-cn-beijing.aliyuncs
            // .com/images/system/config/default_image/default_goods_image_0.gif?x-oss-process
            // =image/resize,m_pad,limit_0,h_220,w_220",
            public String goods_price;//"1.00",
            public List profit;
            public String is_sale;
        }

        public static class PaegModel {

            public int cur_page;//1,
            public int page_count;//1,

        }

    }
}
