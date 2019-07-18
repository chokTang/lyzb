package com.szy.yishopcustomer.ResponseModel.Express;

/**
 * Created by liwei on 2017/7/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class PostmanInfoModel {

    public int code;
    public DataBean data;

    public static class DataBean {
        public String deliver_point;//"119.522711,39.900619",
        public String receive_point;//"119.518238,39.903644"
    }
}
