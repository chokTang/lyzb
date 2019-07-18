package com.szy.yishopcustomer.ResponseModel.Distrib;

import java.util.List;

/**
 * Created by liwei on 2017/7/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribHelpModel {

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {

        public List<ListModel> list;
        public PaegModel page;


        public static class ListModel {
            public String article_id;
            public String title;
            public String cat_id;
        }

        public static class PaegModel {

            public int cur_page;//1,
            public int page_count;//1,

        }
    }
}
