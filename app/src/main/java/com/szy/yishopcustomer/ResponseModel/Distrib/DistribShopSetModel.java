package com.szy.yishopcustomer.ResponseModel.Distrib;

/**
 * Created by liwei on 2017/7/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribShopSetModel {

    public int code;
    public DataBean data;

    public static class DataBean {

       public ShopInfoModel model;

        public static class ShopInfoModel{
            public String dis_id;
            public String user_id;
            public String shop_name;
            public String shop_headimg;
            public String shop_background;
            public String qq;
            public String wechat;
        }

    }
}
