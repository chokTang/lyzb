package com.szy.yishopcustomer.Constant;

import android.content.Context;
import android.text.TextUtils;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.App;


/**
 * Created by Smart on 2017/5/8.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 * <p>
 * 项目差异化配置
 */
public class Config {

    public static Context app = App.getInstance().mContext;

      public static final String BASE_URL = qqq(/* 网站配置 */app.getString(R.string.BASE_URL));
    //public static final String BASE_URL = "http://www.68dsw.com";

    /*微信配置*/
    public static final String WEIXIN_APP_ID = app.getString(R.string.WEIXIN_APP_ID);
    public static final String WEIXIN_APP_SECRET = app.getString(R.string.WEIXIN_APP_SECRET);
    public static final String WXMINI_APP_ID="gh_382155c800a8";//微信小程序原始ID

    /*微博配置*/
    public static final String WEIBO_KEY = app.getString(R.string.WEIBO_KEY);
    /*QQ配置*/
    public static final String TENCENT_ID = app.getString(R.string.TENCENT_ID);
    /*阿里百川热修复配置*/
    public static final String HOTFIX_APP_KEY = app.getString(R.string.HOTFIX_APP_KEY);
    /*调试模式，正式版将其置成false*/
    public static final boolean DEBUG = true;
    /*字体配置*/
    public static final String CUSTOM_FONT_NAME = "fonts/default.ttf";

    public static String qqq(String url) {
        if (!TextUtils.isEmpty(url) && url.endsWith("/")) {
            return url.substring(0, url.length() - 1);
        }

        return url;
    }
}