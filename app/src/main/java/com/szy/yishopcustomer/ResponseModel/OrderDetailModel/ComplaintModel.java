package com.szy.yishopcustomer.ResponseModel.OrderDetailModel;

import java.util.List;

/**
 * Created by liwei on 2017/11/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ComplaintModel {

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {
        public ModelBean model;
        public ComplaintOrderBean complaint_order;
        public List<String> complaint_item;

        public static class ModelBean {
            public String complaint_id;//16,
            public String complaint_sn;//"2017111505542887556",
            public String order_id;//4406,
            public String goods_id;//0,
            public String sku_id;//0,
            public String shop_id;//2,
            public String user_id;//27,
            public String parent_id;//0,
            public String role_type;//0,
            public int complaint_type;//2,
            public String complaint_mobile;//"13333333333",
            public String complaint_images;//"/user/27/images/2017/11/15/15107252158483.jpeg",
            public String complaint_desc;//"这是投诉说明",
            public String complaint_status;//0,
            public String add_time;//1510725268,
            public String close_time;//0,
            public String deduct_credit;//0,
            public String deduct_money;//0
        }

        public static class ComplaintOrderBean {
            public String shop_name;
            public String order_sn;
            public String order_amount;
            public String shipping_fee;
            public String pay_time;
        }

    }
}
