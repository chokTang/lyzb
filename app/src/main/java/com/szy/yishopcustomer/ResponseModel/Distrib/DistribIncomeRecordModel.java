package com.szy.yishopcustomer.ResponseModel.Distrib;

import java.util.List;

/**
 * Created by liwei on 2017/7/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribIncomeRecordModel {

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {

        public List<ListModel> list;
        public PaegModel page;


        public static class ListModel {
            public String income_id;//"1",
            public String money;//"5.00",
            public String user_id;//"3",
            public String add_time;//"1478841962",
            public String week;//"周五"
        }

        public static class PaegModel {

            public int cur_page;//1,
            public int page_count;//1,

        }
    }
}
