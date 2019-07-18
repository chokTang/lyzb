package com.szy.yishopcustomer.ResponseModel.Payment;

/**
 * @author wyx
 * @role
 * @time 2018 2018/12/4 14:16
 */

public class JbxWxModel {

    public int isFree;
    public OrderData data;

    public class OrderData{

        public String appid;
        public String noncestr;
        public String orderInfo;
        public String partnerid;
        public String prepayid;
        public String sign;
        public String timestamp;

    }

    public int type;

}
