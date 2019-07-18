package com.szy.yishopcustomer.ResponseModel.Distrib;

/**
 * Created by liwei on 2017/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribModel {

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {

        public InfoModel info;
        public String deposit_money;
        public ShopInfoModel shop_info;
        public Context context;


        public static class InfoModel {

            public UserInfoModel user_info;

            public static class UserInfoModel {
                public String user_id;
                public String nickname;
                public String headimg;
                public String parent_id;
                public String user_name;
            }
        }

        public static class ShopInfoModel {
            public String total_amount;
        }
        public static class Context {
            public CartModel cart;

            public static class CartModel{
                public String goods_count;
            }
        }

    }
}
