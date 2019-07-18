package com.like.longshaolib.wechat.template;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.like.longshaolib.app.LongshaoAPP;
import com.like.longshaolib.app.config.ConfigType;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by longshao on 2017/8/14.
 */

public class AppRegisterTemplate extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);

        // 将该app注册到微信
        msgApi.registerApp((String) LongshaoAPP.getConfiguration(ConfigType.WXCHAT_APP_ID));
    }
}
