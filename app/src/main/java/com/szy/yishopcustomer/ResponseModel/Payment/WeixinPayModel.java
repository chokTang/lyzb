package com.szy.yishopcustomer.ResponseModel.Payment;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by 宗仁 on 16/7/25.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class WeixinPayModel {
    public String appId;
    public String partnerId;
    public String prepayId;
    public String nonceStr;
    public String timeStamp;
    @JSONField(name = "package")
    public String packageValue;//固定为"Sign=WXPay"
    public String sign;
}