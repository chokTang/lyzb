package com.szy.yishopcustomer.ResponseModel.Goods;

/**
 * Created by liwei on 2016/8/10.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShippingFeeModel {
    public int code;
    public DataModel data;
    public String message;

    public class DataModel {
        public String freight_fee;// "0.00",
        public String freight_info;// "运费：￥0.00"
    }
}
