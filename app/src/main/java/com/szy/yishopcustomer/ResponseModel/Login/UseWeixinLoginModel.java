package com.szy.yishopcustomer.ResponseModel.Login;

/**
 * Created by liwei on 17/1/10.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class UseWeixinLoginModel {
    public int code;
    public DataModel data;
    public String message;

    public class DataModel {
        public int use_weixin_login;// "1",
        public String wx_login_logo;// "/system/config/mobile_set/wx_login_logo_0.gif"
    }
}
