package com.szy.yishopcustomer.ResponseModel;

/**
 * Created by 宗仁 on 16/7/27.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShowCaptchaModel {
    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {
        public boolean show_captcha;
    }
}
