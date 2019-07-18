package com.szy.yishopcustomer.ResponseModel.Distrib;

/**
 * Created by liwei on 2017/7/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */


public class DistribShopSetValueModel {

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {
        public int result;
        public ModelBean model;

        public static class ModelBean {
            public String shop_headimg;
            public String shop_background;
            public String shop_name;
            public String qq;
            public String wechat;
        }
    }
}
