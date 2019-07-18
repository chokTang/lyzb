package com.szy.yishopcustomer.ResponseModel.Distrib;

import java.util.List;

/**
 * Created by liwei on 2017/7/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribTeamModel {

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {

        public List<ListModel> list;
        public PaegModel page;
        public String one_level_user_count;//"4",
        public String two_level_user_count;//"4",
        public String three_level_user_count;//"4",
        public List dis_rank;


        public static class ListModel {
            public String user_name;
            public String total_user_count;
            public String reg_time_format;
            public String dis_total_money;
            public String headimg;
        }

        public static class PaegModel {

            public int cur_page;//1,
            public int page_count;//1,

        }

    }
}
