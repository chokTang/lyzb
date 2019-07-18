package com.szy.yishopcustomer.ResponseModel.Distrib;

/**
 * Created by liwei on 2017/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribIncomeModel {

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {

        public ShopInfoModel shop_info;

        public String total_dis_money;// "￥51.21",
        public String deposit_money;// "￥42.00",
        public String total_income_money;// "￥8.21",


        public static class ShopInfoModel {
            public String total_amount;
        }

    }
}
