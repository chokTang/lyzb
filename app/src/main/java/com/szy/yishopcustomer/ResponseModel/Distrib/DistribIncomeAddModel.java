package com.szy.yishopcustomer.ResponseModel.Distrib;

/**
 * Created by liwei on 2017/7/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribIncomeAddModel {

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {

        public String dis_reserve_money;// "预留金额
        public String deposit_money;// "可提现金额

    }
}
