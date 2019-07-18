package com.szy.yishopcustomer.ResponseModel.Distrib;

import java.util.List;

/**
 * Created by liwei on 2017/7/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribOrderModel {

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {

        public List<ListModel> list;
        public PaegModel page;
        public OrderCountsBean order_counts;


        public static class ListModel {
            public String user_name;
            public String shop_name;
            public String order_id;
            public String order_sn;
            public String dis_money;
            public String distrib_status;

            public RankInfoModel rank_info;

            public static class RankInfoModel {
                public String level;
                public String rate;
                public String img;
            }

        }

        public static class PaegModel {

            public int cur_page;//1,
            public int page_count;//1,

        }

        public static class OrderCountsBean {
            public String dis_all;// "9",
            public String dis_unalready;// "6",
            public String dis_already;// "2",
            public String dis_cancel;// "1"
        }
    }
}
