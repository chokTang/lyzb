package com.lyzb.jbx.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.szy.common.Other.CommonEvent;
import com.szy.yishopcustomer.Activity.ResultActivity;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.ResponseModel.Payment.WeixinExtraDataModel;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI mApi;
    private String mPaySuccess = "0";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = WXAPIFactory.createWXAPI(this, Config.WEIXIN_APP_ID, false);
        mApi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mApi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        if (resp instanceof PayResp) {
            EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_PAY_FINISH.getValue()));
            if (resp.errCode == 0) {
                mPaySuccess = "1";
                Toast.makeText(WXPayEntryActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                String extraData = ((PayResp) resp).extData;
                WeixinExtraDataModel model = JSON.parseObject(extraData, WeixinExtraDataModel.class);

                if (model != null) {

                    if (model.payType.equalsIgnoreCase(Macro.PAY_TYPE_CHECKOUT)) {
                        openResultActivity("", Macro.PAY_TYPE_CHECKOUT, model.orderSn);
                    } else if (model.payType.equalsIgnoreCase(Macro.PAY_TYPE_ORDER)) {
                        openResultActivity(model.orderId, Macro.PAY_TYPE_ORDER, model.orderSn);
                    } else if (model.payType.equalsIgnoreCase(Macro.PAY_TYPE_RECHARGE)) {
                        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_PAY_WX_SUCCESS.getValue()));
                        finish();
                    } else if (model.payType.equalsIgnoreCase(Macro.PAY_TYPE_GROUPON)) {
                        //拼团成功跳转支付结果页;只有在结果页才能获得gorderSn
                        openResultActivity("", Macro.PAY_TYPE_GROUPON, model.orderSn);
                        finish();
                    }
                } else {
                    EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_WX_PAY_SUCCESS_LIFE.getValue()));
                    finish();
                }

            } else if (resp.errCode == -1) {
                Toast.makeText(WXPayEntryActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                finish();
            } else if (resp.errCode == -2) {
                Toast.makeText(WXPayEntryActivity.this, "支付已取消", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_PAY_CANCEL.getValue()));
                finish();
            }
        }
    }

    private void openResultActivity(String orderId, String payType, String orderSn) {
        Intent intent = new Intent(this, ResultActivity.class);
        if (!Utils.isNull(orderId)) {
            intent.putExtra(Key.KEY_ORDER_ID.getValue(), orderId);
        }
        intent.putExtra(Key.KEY_ORDER_PAY_SUCCESS.getValue(), mPaySuccess);
        intent.putExtra(Key.KEY_ORDER_PAY_TYPE.getValue(), payType);
        if (!Utils.isNull(orderSn)) {
            intent.putExtra(Key.KEY_ORDER_SN.getValue(), orderSn);
        }
        startActivity(intent);
        finish();
    }
}