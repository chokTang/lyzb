package com.lyzb.jbx.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.szy.common.Activity.CommonActivity;
import com.szy.common.Fragment.CommonFragment;
import com.szy.common.Other.CommonEvent;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Util.App;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXEntryActivity extends CommonActivity implements IWXAPIEventHandler {
    private IWXAPI mApi;

    @Override
    public CommonFragment createFragment() {
        return null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mApi.handleIntent(intent, this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = WXAPIFactory.createWXAPI(this, Config.WEIXIN_APP_ID, false);
        mApi.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp instanceof SendAuth.Resp) {
            SendAuth.Resp newResp = (SendAuth.Resp) resp;
            App.getInstance().weixin_get_access_token_code = newResp.code;
            EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_WEIXIN_LOGIN.getValue(), String.valueOf(resp.errCode)));
        }
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_WEIXIN_SHARE.getValue(), String.valueOf(resp.errCode)));
        finish();
    }

}