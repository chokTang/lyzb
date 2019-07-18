package com.szy.yishopcustomer.ResponseModel.OrderList;

import java.util.List;
import java.util.TreeMap;

/**
 * Created by liwei on 2017/11/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class ComplaintListModel {

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {
        public List<String> complaint_item;
        public PageBean page;
        public TreeMap<String,String> complaint_status_list;
        public List<ListBean> list;

        public static class PageBean{
            public int cur_page;
            public int page_count;
        }

        public static class ListBean {
            public String complaint_id;
            public String complaint_sn;
            public String order_id;
            public String goods_id;
            public String sku_id;
            public String shop_id;
            public String user_id;
            public String parent_id;
            public String role_type;
            public String role_type_string;
            public int complaint_type;
            public String complaint_type_string;
            public String complaint_mobile;
            public String complaint_images;
            public String complaint_desc;
            public String complaint_status;
            public String add_time;
            public String add_time_format;
            public String close_time;
            public String deduct_credit;
            public String deduct_money;
            public String user_name;
            public String shop_name;
            public String order_sn;
            public String aliim_enable;
            public Object system_aliim_enable;
        }
    }
}
