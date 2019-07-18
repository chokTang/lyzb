package com.like.longshaolib.wechat;

import android.app.Activity;

import com.like.longshaolib.app.LongshaoAPP;
import com.like.longshaolib.app.config.ConfigType;
import com.like.longshaolib.wechat.callback.IWXChatLoginCallback;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2017/8/14.
 */

public class LongWeChat {
    public static final String APP_ID = LongshaoAPP.getConfigurator().getConfiguration(ConfigType.WXCHAT_APP_ID);
    public static final String APP_SECRET = LongshaoAPP.getConfigurator().getConfiguration(ConfigType.WXCHAT_APP_SECRET);
    private final IWXAPI WXAPI;
    private IWXChatLoginCallback loginCallback = null;

    private static final class Holder {
        private static final LongWeChat INTANCE = new LongWeChat();
    }

    private LongWeChat() {
        final Activity activity = LongshaoAPP.getConfigurator().getConfiguration(ConfigType.ACTIVITY);
        WXAPI = WXAPIFactory.createWXAPI(activity, APP_ID, true);
        WXAPI.registerApp(APP_ID);
    }

    public static LongWeChat getIntance() {
        return Holder.INTANCE;
    }

    public final IWXAPI getWXAPI() {
        return WXAPI;
    }

    public LongWeChat onWXLoginSuccessCallback(IWXChatLoginCallback callback) {
        this.loginCallback = callback;
        return this;
    }

    public IWXChatLoginCallback getLoginCallback() {
        return loginCallback;
    }

    public final void WXLogin() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "random_state";
        WXAPI.sendReq(req);
    }
}
