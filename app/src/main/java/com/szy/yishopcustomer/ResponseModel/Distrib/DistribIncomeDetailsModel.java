package com.szy.yishopcustomer.ResponseModel.Distrib;

import java.util.List;

/**
 * Created by liwei on 2017/7/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribIncomeDetailsModel {

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {

        public List<ListModel> list;
        public PaegModel page;


        public static class ListModel {
            public String log_id;// "38",
            public String order_id;// "2654",
            public String goods_id;// "38670",
            public String sku_id;// "40936",
            public String user_id;// "3",
            public String dis_amount;// "1.25",
            public String dis_level;// "1",
            public String dis_rate;// "50",
            public String dis_money;// "0.63",
            public String dis_status;// "1",
            public String add_time;// "1487730002",
            public String remark;// "订单分成",
            public String order_sn;// "20170222017860",
            public String user_name;// "达芙妮旗舰店店长",
            public String headimg;// "/user/3/headimg/14829721849601.png"
        }

        public static class PaegModel {

            public int cur_page;//1,
            public int page_count;//1,

        }
    }
}
