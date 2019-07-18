package com.szy.yishopcustomer.ResponseModel.Distrib;

/**
 * Created by liwei on 2017/7/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribApplyModel {

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {

        public String code;//0,
        public String is_audit;//"1",
        public String message;//"申请成功"

    }
}
