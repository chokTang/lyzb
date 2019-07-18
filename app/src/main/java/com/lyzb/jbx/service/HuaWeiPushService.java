package com.lyzb.jbx.service;

import android.content.Context;

import com.huawei.hms.support.api.push.PushReceiver;
import com.hyphenate.chat.EMClient;
import com.like.utilslib.other.LogUtil;
import com.lyzb.jbx.util.AppPreference;

/**
 * 华为推送
 */
public class HuaWeiPushService extends PushReceiver {

    @Override
    public void onToken(Context context, String token) {
        super.onToken(context, token);
        EMClient.getInstance().sendHMSPushTokenToServer("100219423", token);
        AppPreference.getIntance().setHXHuaWeiToken(token);
        LogUtil.loge("环信-华为推送获取token返回值1:" + token);
    }
}
