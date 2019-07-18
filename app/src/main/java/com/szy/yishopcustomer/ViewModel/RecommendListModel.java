package com.szy.yishopcustomer.ViewModel;

import java.util.List;

/**
 * Created by liwei on 2017/7/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class RecommendListModel {

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {

        public String user_count;
        public String user_total_bonus;
        public Boolean is_bonus_reward;
        public List<ListBean> list;
        public PageBean page;

        public static class ListBean {
            public String user_id;
            public String headimg;
            public String user_name;
            public String reg_time;
        }

        public static class PageBean {
            public String page_key;
            public String page_id;
            public int default_page_size;
            public int cur_page;
            public int page_size;
            public int record_count;
            public int page_count;
            public int offset;
            public Object url;
            public Object sql;
        }
    }
}
