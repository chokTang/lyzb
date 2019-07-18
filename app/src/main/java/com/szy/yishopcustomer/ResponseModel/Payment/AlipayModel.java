package com.szy.yishopcustomer.ResponseModel.Payment;

/**
 * Created by 宗仁 on 16/7/25.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class AlipayModel {


    /***
     *
     *{
     "code":0,
     "data":{
     "_input_charset":"utf-8",
     "body":"订单支付",
     "notify_url":"http://www.jibaoh.com:10000/respond/back-alipay.html",
     "out_trade_no":"20180429104045331310",
     "partner":"2088421733825872",
     "seller_id":"851053503@qq.com",
     "service":"mobile.securitypay.pay",
     "subject":"集宝箱-订单支付",
     "total_fee":"136",
     "sign":"5d60f2cbfa1aec8d424f0603993ccac7",
     "sign_type":"MD5"
     },
     "message":"",
     "pay_code":"alipay"
     }
     */

    public String _input_charset;//;//utf-8",
    public String body;//订单支付",
    public String notify_url;//http://www.68shop.test/respond/back-alipay",
    public String out_trade_no;//20160725041880",
    public String partner;//2088221975726092",
    public String seller_id;//szy_ysc@68ecshop.com",
    public String service;//create_direct_pay_by_user",
    public String subject;//翼商城-订单支付",
    public String total_fee;// 279,
    public String sign;//89c7cf2bc6ff2c0d850cbc5a45efc693",
    public String sign_type;//MD5"

    @Override
    public String toString() {
        String string = "";
        string += "_input_charset=" + _input_charset;
        string += "&body=" + body;
        string += "&notify_url=" + notify_url;
        string += "&out_trade_no=" + out_trade_no;
        string += "&partner=" + partner;
        string += "&seller_id=" + seller_id;
        string += "&service=" + service;
        string += "&subject=" + subject;
        string += "&total_fee=" + total_fee;
        string += "&sign=" + sign;
        string += "&sign_type=" + sign_type;
        return string;
    }
}
