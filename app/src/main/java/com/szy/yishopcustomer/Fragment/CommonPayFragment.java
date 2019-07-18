package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Activity.ResultActivity;
import com.szy.yishopcustomer.Activity.UnionpayDummyActivity;
import com.szy.yishopcustomer.Activity.UserGroupOnDetailActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Payment.AlipayModel;
import com.szy.yishopcustomer.ResponseModel.Payment.ResponsePaymentModel;
import com.szy.yishopcustomer.ResponseModel.Payment.UnionPayModel;
import com.szy.yishopcustomer.ResponseModel.Payment.WeixinExtraDataModel;
import com.szy.yishopcustomer.ResponseModel.Payment.WeixinPayModel;
import com.szy.yishopcustomer.Service.Location;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.core.PayUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 宗仁 on 16/8/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public abstract class CommonPayFragment extends YSCBaseFragment {
    private static final String UNIONPAY_MODE = "00";
    private static final int MESSAGE_ALIPAY = 100;
    public String mPaySuccess;
    public String mOrderId;
    private String payType;
    private String key;
    private String order_sn;
    private String group_sn;

    public Class resultClass = null;
    public boolean isRefresh = false;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_ALIPAY:
                    String response = (String) msg.obj;
                    String resultStatus = Utils.getAlipayResultStatus(response);
                    String memo = Utils.getAlipayMemo(response);
                    String result = Utils.getAlipayResult(response);
                    String messagee = "";
                    if (resultStatus.equalsIgnoreCase("9000")) {
                        mPaySuccess = "1";
                        messagee = "订单支付成功";
                    } else if (resultStatus.equalsIgnoreCase("8000")) {
                        messagee = "正在处理";
                    } else if (resultStatus.equalsIgnoreCase("4000")) {
                        messagee = "订单支付失败,请检查是否装有支付宝客户端";
                    } else if (resultStatus.equalsIgnoreCase("6001")) {
                        messagee = "用户中途取消";
                    } else if (resultStatus.equalsIgnoreCase("6002")) {
                        messagee = "网络连接出错";
                    } else if (resultStatus.equalsIgnoreCase("6004")) {
                        messagee = "正在处理";
                    } else {
                        messagee = "未知错误 status is " + resultStatus + ",memo is " + memo + "," +
                                "result is " + result + ",whole response is " + response;
                    }
                    try {
                        Toast.makeText(getActivity(), messagee, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                    onFinishAliPay();
                    break;

                default:
                    break;
            }
        }
    };

    public IWXAPI msgApi;
    public boolean isRegWX = false;

    public abstract String getPayType();

    public void getPayment(String orderSn, String type) {
        CommonRequest request = new CommonRequest(Api.API_PAYMENT, HttpWhat.HTTP_PAYMENT.getValue());
        request.add("order_sn", orderSn);
        addRequest(request);
        payType = type;
        order_sn = orderSn;
    }

    public void getPayment(String orderSn, String type, String checkoutKey, String groupSn) {
        CommonRequest request = new CommonRequest(Api.API_PAYMENT, HttpWhat.HTTP_PAYMENT.getValue());

        request.add("order_sn", orderSn);
        addRequest(request);
        payType = type;
        order_sn = orderSn;
        key = checkoutKey;
        group_sn = groupSn;
    }

    public void getPaymentCallback(String response) {

        HttpResultManager.resolve(response,
                ResponsePaymentModel.class, new HttpResultManager.HttpResultCallBack<ResponsePaymentModel>() {
                    @Override
                    public void onSuccess(ResponsePaymentModel responsePaymentModel) {
                        if (responsePaymentModel.pay_code.equalsIgnoreCase(Macro.ALIPAY_CODE)) {
                            AlipayModel alipayModel = JSON.parseObject(responsePaymentModel.data, AlipayModel
                                    .class);
                            startAlipay(alipayModel.toString());
                        } else if (responsePaymentModel.pay_code.equalsIgnoreCase(Macro.WEIXIN_CODE)) {
                            if (Utils.isWeixinAvilible(getActivity())) {
                                WeixinPayModel weixinPayModel = JSON.parseObject(responsePaymentModel.data,
                                        WeixinPayModel.class);

                                startWeixinPay(weixinPayModel);
                            } else {
                                Toast.makeText(getActivity(), R.string
                                        .pleaseInstallWeixin, Toast.LENGTH_SHORT).show();
                                onFinishPay();
                            }
                        } else if (responsePaymentModel.pay_code.equalsIgnoreCase(Macro.UNIONPAY_CODE)) {
                            UnionPayModel unionPayModel = JSON.parseObject(responsePaymentModel.data,
                                    UnionPayModel.class);
                            startUnionPay(unionPayModel.tn, UNIONPAY_MODE);
                        } else if (responsePaymentModel.pay_code.equals("0")) {
                            mPaySuccess = "1";
                            onFinishPay();
                        } else {
                            Toast.makeText(getActivity(), "Not support this payment,code is " +
                                    responsePaymentModel.pay_code, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (RequestCode.valueOf(requestCode)) {
            case REQUEST_CODE_UNIONPAY_REQUEST_CODE:
                String result = data.getStringExtra(Key.KEY_RESULT.getValue());
                unionPayCallback(result);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultClass = ResultActivity.class;
        mPaySuccess = "0";
        Bundle arguments = getArguments();
        if (arguments != null) {
            //订单再次支付
            mOrderId = arguments.getString(Key.KEY_ORDER_ID.getValue());
        }

        msgApi = WXAPIFactory.createWXAPI(getContext(), null);
        isRegWX = msgApi.registerApp(Config.WEIXIN_APP_ID);
    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_PAY_CANCEL:
                onFinishPayOpenActivity();
                finish();
                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    public void startAlipay(final String orderInfo) {
        isRefresh = true;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayUtils payUtils = new PayUtils(getActivity());
                payUtils.AliPay(orderInfo, new PayUtils.OnPayCallback() {
                    @Override
                    public void aliCallback(Object object) {
                        Message msg = new Message();
                        msg.what = MESSAGE_ALIPAY;
                        msg.obj = object;
                        mHandler.sendMessage(msg);
                    }
                });
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public void startUnionPay(String orderInfo, String mode) {
        Intent intent = new Intent(getContext(), UnionpayDummyActivity.class);
        intent.putExtra(Key.KEY_ORDER_ID.getValue(), orderInfo);
        intent.putExtra(Key.KEY_UNIONPAY_MODE.getValue(), mode);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_UNIONPAY_REQUEST_CODE.getValue());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        msgApi.unregisterApp();
    }

    public void startWeixinPay(WeixinPayModel model) {
        isRefresh = true;
        if (!isRegWX) {
            isRegWX = msgApi.registerApp(Config.WEIXIN_APP_ID);
        }

        if (isRegWX) {
            PayReq request = new PayReq();
            request.appId = model.appId;
            request.partnerId = model.partnerId;
            request.prepayId = model.prepayId;//预支付交易ID
            request.packageValue = model.packageValue;
            request.nonceStr = model.nonceStr;
            request.timeStamp = model.timeStamp;
            request.sign = model.sign;

            WeixinExtraDataModel extraDataModel = new WeixinExtraDataModel();
            extraDataModel.payType = getPayType();
            if (Utils.isNull(mOrderId)) {
                extraDataModel.orderId = "-1";
            } else {
                extraDataModel.orderId = mOrderId;
            }
            extraDataModel.orderSn = order_sn;
            request.extData = JSON.toJSONString(extraDataModel, false);

            msgApi.sendReq(request);
        } else {
            Toast.makeText(getContext(), "注册app失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 拼团支付完成后比较特殊，会跳转到拼团详情，而不是支付结果
     * 余额支付完成后可获得groupSn，可正常跳转拼团详情
     * 支付宝等在线支付完成后，无法获得groupSn，所以只能跳转支付结果
     */
    protected void onFinishAliPay() {
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_PAY_FINISH.getValue()));
        openResultActivity(mOrderId);
        finish();
    }

    protected void onFinishPay() {
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_PAY_FINISH.getValue()));
        onFinishPayOpenActivity();
        finish();
    }

    private void onFinishPayOpenActivity() {
        //不同操作支付完执行的操作不同，
        if (payType.equals(Macro.PAY_TYPE_RECHARGE)) {
            //充值成功
            openResultActivity(mOrderId);
        } else if (payType.equals(Macro.PAY_TYPE_CHECKOUT)) {
            //结算页支付成功跳转支付结果
            openResultActivity(mOrderId);
        } else if (payType.equals(Macro.PAY_TYPE_ORDER)) {
            //订单再次支付成功后跳转支付结果
            openResultActivity(mOrderId);
        } else if (payType.equals(Macro.PAY_TYPE_GROUPON)) {
            if (mPaySuccess.equals("1")) {
                //拼团订单支付成功跳转到拼团详情
                openGrouponActivity(group_sn);
            } else {
                openResultActivity(mOrderId);
            }
        }
    }

    void openResultActivity(String orderId) {
        try {
            Intent intent = new Intent(getContext(), resultClass);
            if (!Utils.isNull(orderId)) {
                intent.putExtra(Key.KEY_ORDER_ID.getValue(), orderId);
            }

            intent.putExtra(Key.KEY_ORDER_PAY_SUCCESS.getValue(), mPaySuccess);
            intent.putExtra(Key.KEY_ORDER_PAY_TYPE.getValue(), payType);
            intent.putExtra(Key.KEY_ORDER_SN.getValue(), order_sn);

            if (!Utils.isNull(key)) {
                intent.putExtra(Key.KEY_APP_KEY.getValue(), key);
            }

            startActivity(intent);
        } catch (Exception e) {
        }
    }

    private void openGrouponActivity(String groupSn) {
        Intent intent = new Intent(getContext(), UserGroupOnDetailActivity.class);
        intent.putExtra(Key.KEY_GROUP_SN.getValue(), groupSn);
        startActivity(intent);
    }

    private void unionPayCallback(String payResult) {
        String message = "";
        if (payResult.equalsIgnoreCase("success")) {
            message = "支付成功！";
            mPaySuccess = "1";
        } else if (payResult.equalsIgnoreCase("fail")) {
            message = "支付失败！";
        } else if (payResult.equalsIgnoreCase("cancel")) {
            message = "用户取消了支付";
        }
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_PAY_FINISH.getValue()));
        if (payType.equals(Macro.PAY_TYPE_RECHARGE)) {
            //充值成功
        } else {
            openResultActivity(mOrderId);
        }
        finish();
    }
}
