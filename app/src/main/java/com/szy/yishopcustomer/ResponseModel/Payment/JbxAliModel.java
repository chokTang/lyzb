package com.szy.yishopcustomer.ResponseModel.Payment;

/**
 * @author wyx
 * @role
 * @time 2018 2018/12/4 14:16
 */

public class JbxAliModel {

    public int isFree;
    public OrerData data;

    public class OrerData {

        public String app_id;
        public String biz_content;
        public String charset;
        public String format;
        public String method;
        public String notify_url;

        public String orderInfo;
        public String sign;
        public String sign_type;
        public String timestamp;
        public String version;


    }

    public int type;
}
