package com.szy.yishopcustomer.ResponseModel.OrderDetailModel;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwei on 17/11/17.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class ComplaintDetailModel {

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {
        public String id;
        public ComplaintViewBean complaint_view;
        public String involve_time;
        public List<ComplaintReplyBean> complaint_reply;
        public List<String> complaint_item;
        public InvolveStatusBean involve_status;

        public static class ComplaintViewBean {
            public String complaint_id;
            public String complaint_sn;
            public String order_id;
            public String goods_id;
            public String sku_id;
            public String shop_id;
            public String user_id;
            public String parent_id;
            public String role_type;
            public int complaint_type;
            public String complaint_mobile;
            public String complaint_images;
            public String complaint_desc;
            public int complaint_status;
            public String add_time;
            public String close_time;
            public String deduct_credit;
            public String deduct_money;
            public String amount;
            public String order_sn;
            public String order_amount;
            public String shipping_fee;
            public String pay_time;
            public String order_add_time;
            public String shop_name;

        }

        public static class ComplaintReplyBean {
            public String complaint_id;
            public String complaint_sn;
            public String order_id;
            public String goods_id;
            public String sku_id;
            public String shop_id;
            public String user_id;
            public String parent_id;
            public String role_type;
            public String complaint_type;
            public String complaint_mobile;
            public String complaint_images;
            public String complaint_desc;
            public String complaint_status;
            public String add_time;
            public String close_time;
            public String deduct_credit;
            public String deduct_money;
            public String user_name;
            public ArrayList<String> images;
        }

        public static class InvolveStatusBean {
            public String show;
            public String count_down;
        }
    }
}
