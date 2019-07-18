package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.*;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.chinaums.pppay.unify.UnifyPayPlugin;
import com.chinaums.pppay.unify.UnifyPayRequest;
import com.lyzb.jbx.util.AppPreference;
import com.szy.common.Other.CommonEvent;
import com.szy.yishopcustomer.Activity.samecity.GroupBuyPayFailActivity;
import com.szy.yishopcustomer.Activity.samecity.GroupBuyPaySuccessActivity;
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.ResponseModel.Payment.JbxAliContModel;
import com.szy.yishopcustomer.ResponseModel.Payment.JbxAliModel;
import com.szy.yishopcustomer.ResponseModel.Payment.JbxWxModel;
import com.szy.yishopcustomer.ResponseModel.SameCity.Order.OrderModel;
import com.szy.yishopcustomer.Service.Location;
import com.szy.yishopcustomer.Util.AliPayResult;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.LogUtils;
import com.szy.yishopcustomer.Util.LubanImg;
import com.szy.yishopcustomer.Util.Oss;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.Util.WebviewUtils;
import com.szy.yishopcustomer.core.PayUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.PhotoPicker;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.KEY_BUNDLE;
import static com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.KEY_PRODUCT_ID;
import static com.szy.yishopcustomer.Adapter.CityHomeAdapter.KEY_SHOP_ID;

/**
 * @author wyx
 * @role base web
 * @time 2018 23:59
 */

public abstract class IBaseWebview extends Activity {

    protected void bindLayoutId() {

    }

    protected void inittView() {

    }

    public static String webUrl = null;

    public static final String KEY_ORDER = "keyorder";

    private final static int PROGRESS_100 = 100;
    private final static int WEB_BACK = 102;

    private final static int LOGIN = 10001;
    private final static int LOCATION = 10002;
    private static final int SCAN_CODE = 10003;

    private static final int SHARE = 10004;
    private static final int CALL_PHONE = 10005;
    private static final int OPEN_PHOTO = 10006;
    private static final int UPLOAD_PHOTO = 10007;
    private static final int USER_INFO = 10008;
    private static final int USER_LOGIN = 100010;
    private static final int GROUP_TO_SHOP_DETAIL = 100040;
    private static final int Go_GROUP_BUY_DETAIL = 100041;

    public final static int PAY_ALI = 10009;
    public final static int PAY_WX = 10010;
    private static final int PAYALI_RESULT = 10011;
    private static final int PAYWX_RESULT = 10012;

    private static final int GOOD_DETAILS = 10013;
    private static final int GO_HOME = 10014;

    public static final int QR_CODE_RESULT = 10016;

    public static final int BACK_HOME = 10018;
    public static final int BACK_NEAR = 10020;
    public static final int BACK_ORDER_LIST = 10022;
    public static final int LOCATION_H5 = 10024;
    public static final int CHECK_PAY = 10028;//检查是否安装 微信or支付宝

    public static final int GO_ADDRESSLIST = 10032;//跳转到收货地址列表
    public static final int ADDRESS_INFO = 10034;

    public static final int OPEN_WX = 100661;     //打开微信

    private final String HTTP = "http:";
    private final String HTTPS = "https:";

    private final String PAY_SUCCESS = "/paySuccess";
    private final String PAY_FAIL = "/payFail";

    private String orderId = "";//支付订单
    private int wxpayType = 0;//1 微信原生支付
    private int alipayType = 0;//1 支付宝原生支付
    private int shop_id;
    private int product_id;

    private String WX_PACKAGE = "Sign=WXPay";

    private String YlWxData = null;//银联 微信 json
    private String YlAliData = null;//银联 支付宝

    private String YlOrderIdWx = null;    //银联 微信 订单ID
    private String YlOrderIdAli = null;   //银联 支付宝 订单ID
    private int YlPayType = 0;            //银联 支付方式

    /**
     * webview 播放视频设置
     ***/
    private FrameLayout mFrameLayout;

    JSONObject mJSONObject_Location = new JSONObject();
    JSONObject mJSONObject_UserInfo = new JSONObject();
    JSONObject mJSONObject_Share = new JSONObject();

    JSONObject mJSONObject_WXPAY = null;
    JSONObject mJSONObject_ALIPAY = null;

    JSONObject mJSONObject_PayResult = new JSONObject();
    String ali_orderNumber;
    String wx_orderNumber;
    String pay_success;

    JSONObject mJSONObject_Login = new JSONObject();
    String user_token;
    String user_id;
    String user_identity;

    String is_login;

    ArrayList<String> mShareData = new ArrayList<>();

    /***多图选择 图片 返回的本地路径集合*/
    ArrayList<String> mArrayList_Imglocal = new ArrayList<>();
    /***上传图片成功 返回的图片网络地址集合 */
    ArrayList<String> mArrayList_ImgNet = new ArrayList<>();

    WebviewUtils mWebviewUtils;
    Intent mIntent;

    public Activity mActivity;
    WebView mWebView;
    ProgressBar mProgressBar;
    View mView_Hint;
    Button mButton_Reload;

    CookieManager mCookieManager;
    Message mMessage;

    private List<String> urlList = new ArrayList<>();

    /**
     * 银联支付-相关
     */
    private UnifyPayPlugin mPayPlugin;
    private UnifyPayRequest mPayRequest;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WEB_BACK:
                    if (mWebView.canGoBack() || urlList.size() > 1) {
                        urlList.remove(mWebView.getUrl());
                        if (mWebView.getUrl().contains(PAY_SUCCESS)) {

                            mWebView.goBack();
                            mWebView.goBack();
                            mWebView.goBack();
                        } else if (mWebView.getUrl().contains(PAY_FAIL)) {
                            mWebView.goBack();
                            mWebView.goBack();
                        } else {
                            mWebView.goBack();
                        }
                    } else {
                        finish();
                    }
                    break;
                case LOGIN:
                    //登录
                    Toast.makeText(mActivity, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    mIntent = new Intent(mActivity, LoginActivity.class);
                    startActivity(mIntent);
                    break;
                case USER_LOGIN:

                    mJSONObject_Login.clear();
                    //重新获取 用户信息
                    user_token = (String) SharedPreferencesUtils.getParam(mActivity, Key.USER_INFO_TOKEN.getValue(), "");
                    user_id = (String) SharedPreferencesUtils.getParam(mActivity, Key.USER_INFO_ID.getValue(), "");
                    user_identity = (String) SharedPreferencesUtils.getParam(mActivity, Key.USER_INFO_IDENTITY.getValue(), "");

                    mJSONObject_Login.put("token", user_token);
                    mJSONObject_Login.put("userId", user_id);

                    if (mWebView != null) {
                        mWebView.loadUrl("javascript:vueObj.goLoginPage('" + mJSONObject_Login.toString() + "')");
                    }

                    /**写入cookie 值*/
                    setWebCookies(mWebView.getUrl());
                    break;

                case GROUP_TO_SHOP_DETAIL:
                    mIntent = new Intent(mActivity, ShopDetailActivity.class);
                    mIntent.putExtra(KEY_SHOP_ID, msg.obj.toString());
                    mActivity.startActivity(mIntent);
                    break;
                case Go_GROUP_BUY_DETAIL:
                    mIntent = new Intent(mActivity, GroupBuyActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(KEY_SHOP_ID, msg.obj.toString());
                    mIntent.putExtra(KEY_BUNDLE, bundle);
                    mIntent.putExtra(KEY_PRODUCT_ID, msg.arg1 + "");
                    break;
                case LOCATION:
                    //定位
                    if (App.getInstance().isLocation) {
                        mWebView.loadUrl("javascript:vueObj.getCurrentPositionPage('" + mJSONObject_Location.toString() + "')");
                    } else {
                        mJSONObject_Location.clear();

                        mJSONObject_Location.put("longitude", "0");
                        mJSONObject_Location.put("latitude", "0");
                        mJSONObject_Location.put("province", "0");
                        mJSONObject_Location.put("city", "0");
                        mJSONObject_Location.put("district", "0");
                        mJSONObject_Location.put("address", "0");

                        mWebView.loadUrl("javascript:vueObj.getCurrentPositionPage('" + mJSONObject_Location.toString() + "')");
                    }
                    break;
                case SCAN_CODE:
                    //二维码扫描
                    if (Utils.cameraIsCanUse()) {
                        mIntent = new Intent(mActivity, ScanActivity.class);
                        mIntent.putExtra(Key.KEY_TYPE.getValue(), ScanActivity.TYPE_CITYLIFE);
                        startActivityForResult(mIntent, RequestCode.REQUEST_CODE_SCAN.getValue());
                    } else {
                        Toast.makeText(mActivity, "没有拍照权限,请到设置里开启权限", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case SHARE:
                    //分享
                    mShareData.clear();

                    mJSONObject_Share = JSONObject.parseObject(msg.obj.toString());

                    mShareData.add(mJSONObject_Share.getString("shareUrl"));
                    mShareData.add(mJSONObject_Share.getString("shareTitle"));
                    mShareData.add(mJSONObject_Share.getString("shareContent"));
                    mShareData.add(mJSONObject_Share.getString("shareImgUrl"));

                    //1:微信好友+朋友圈
                    int shareScope = mJSONObject_Share.getInteger("shareScope");

                    mIntent = new Intent(mActivity, ShareActivity.class);
                    mIntent.putStringArrayListExtra(ShareActivity.SHARE_DATA, mShareData);
                    mIntent.putExtra(ShareActivity.SHARE_SCOPE, shareScope);
                    mIntent.putExtra(ShareActivity.SHARE_SOURCE, ShareActivity.TYPE_SOURCE);
                    startActivity(mIntent);
                    break;
                case CALL_PHONE:
                    //号码拨打
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + (String) mMessage.obj + ""));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case OPEN_PHOTO:
                    //打开相册
                    int maxN = Integer.parseInt(msg.obj.toString());

                    PhotoPicker.builder()
                            .setPhotoCount(maxN)
                            .setShowCamera(false)
                            .setShowGif(false)
                            .setPreviewEnabled(false)
                            .start(mActivity, PhotoPicker.REQUEST_CODE);

                    break;
                case UPLOAD_PHOTO:
                    //上传图片
                    int size = mArrayList_ImgNet.size();
                    String[] imgs = mArrayList_ImgNet.toArray(new String[size]);
                    JSONObject object = new JSONObject();
                    object.put("imgList", imgs);

                    mWebView.loadUrl("javascript:vueObj.selectPhotosPage('" + object + "')");

                    mArrayList_Imglocal.clear();
                    mArrayList_ImgNet.clear();
                    break;
                case USER_INFO:
                    //传递用户信息
                    if (App.getInstance().isLogin()) {
                        user_token = (String) SharedPreferencesUtils.getParam(mActivity, Key.USER_INFO_TOKEN.getValue(), "");
                        user_id = (String) SharedPreferencesUtils.getParam(mActivity, Key.USER_INFO_ID.getValue(), "");

                        mJSONObject_UserInfo.put("token", user_token);
                        mJSONObject_UserInfo.put("userId", user_id);
                        mWebView.loadUrl("javascript:vueObj.getUserInfoPage('" + mJSONObject_UserInfo + "')");
                    } else {
                        mJSONObject_UserInfo.put("token", "0");
                        mJSONObject_UserInfo.put("userid", "0");
                        mWebView.loadUrl("javascript:vueObj.getUserInfoPage('" + mJSONObject_UserInfo + "')");
                    }
                    break;
                case PAY_ALI:
                    alipayType = msg.arg1;
                    orderId = JSONObject.parseObject(msg.obj.toString()).getString("orderId");
                    //支付宝
                    final JbxAliModel aliModel = JSONObject.parseObject(msg.obj.toString(), JbxAliModel.class);
                    YlAliData = JSONObject.parseObject(msg.obj.toString()).getString("data");

                    /*** type=1 易极付(原生)支付  type=2 银联支付 **/
                    if (aliModel.type == 1) {
                        mJSONObject_ALIPAY = JSONObject.parseObject(YlAliData);
                        JbxAliContModel aliContModel = JSONObject.parseObject(aliModel.data.biz_content, JbxAliContModel.class);
                        ali_orderNumber = aliContModel.out_trade_no;
                        startAlipay(mJSONObject_ALIPAY);
                    } else if (aliModel.type == 2) {
                        YlOrderIdAli = JSONObject.parseObject(msg.obj.toString()).getString("orderId");
                        payAliYl(YlAliData);
                    }
                    break;
                case PAY_WX:
                    wxpayType = msg.arg1;
                    orderId = JSONObject.parseObject(msg.obj.toString()).getString("orderId");
                    //微信
                    if (Utils.isWeixinAvilible(mActivity)) {
                        final IWXAPI mIWXAPI = WXAPIFactory.createWXAPI(mActivity, null);
                        mIWXAPI.registerApp(Config.WEIXIN_APP_ID);

                        final JbxWxModel wxModel = JSONObject.parseObject(msg.obj.toString(), JbxWxModel.class);
                        YlWxData = JSONObject.parseObject(msg.obj.toString()).getString("data");

                        wx_orderNumber = wxModel.data.prepayid;

                        /*** type=1 易极付(原生)支付  type=2 银联支付 **/
                        if (wxModel.type == 1) {

                            Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    PayReq request = new PayReq();

                                    request.appId = wxModel.data.appid;
                                    request.partnerId = wxModel.data.partnerid;
                                    request.prepayId = wxModel.data.prepayid;
                                    request.packageValue = WX_PACKAGE;
                                    request.nonceStr = wxModel.data.noncestr;
                                    request.timeStamp = wxModel.data.timestamp;
                                    request.sign = wxModel.data.sign;
                                    mIWXAPI.sendReq(request);
                                }
                            };
                            Thread payThread = new Thread(runnable);
                            payThread.start();

                        } else if (wxModel.type == 2) {
                            YlOrderIdWx = JSONObject.parseObject(msg.obj.toString()).getString("orderId");
                            payWxYl(YlWxData);
                        }

                    } else {
                        Utils.toastUtil.showToast(mActivity, "请先安装微信客户端");
                    }
                    break;

                case PAYALI_RESULT:
                    switch (msg.arg1) {
                        case 1://支付宝原生支付回调
                            //支付结果回调 支付宝
                            AliPayResult payResult1 = new AliPayResult((Map<String, String>) msg.obj);
                            String resultStatus1 = payResult1.getResultStatus();

                            if (TextUtils.equals(resultStatus1, "9000")) {
                                pay_success = "1";
                                Toast.makeText(mActivity, "支付成功", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(IBaseWebview.this, GroupBuyPaySuccessActivity.class)
                                        .putExtra(KEY_ORDER, orderId));
                            } else {
                                pay_success = "2";
                                if (TextUtils.equals(resultStatus1, "4000")) {
                                    Toast.makeText(mActivity, "支付失败,请检查是否装有支付宝客户端", Toast.LENGTH_SHORT).show();
                                } else if (TextUtils.equals(resultStatus1, "6001")) {
                                    Toast.makeText(mActivity, "支付取消", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(mActivity, "支付失败", Toast.LENGTH_SHORT).show();
                                }
                                startActivity(new Intent(IBaseWebview.this, GroupBuyPayFailActivity.class)
                                        .putExtra(KEY_ORDER, orderId));
                            }
                            break;
                        case 0://支付宝H5支付回调
                            //支付结果回调 支付宝
                            AliPayResult payResult = new AliPayResult((Map<String, String>) msg.obj);
                            String resultStatus = payResult.getResultStatus();

                            if (TextUtils.equals(resultStatus, "9000")) {
                                pay_success = "1";
                                Toast.makeText(mActivity, "支付成功", Toast.LENGTH_SHORT).show();
                            } else {
                                pay_success = "2";
                                if (TextUtils.equals(resultStatus, "4000")) {
                                    Toast.makeText(mActivity, "支付失败,请检查是否装有支付宝客户端", Toast.LENGTH_SHORT).show();
                                } else if (TextUtils.equals(resultStatus, "6001")) {
                                    Toast.makeText(mActivity, "支付取消", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(mActivity, "支付失败", Toast.LENGTH_SHORT).show();
                                }
                            }

                            mJSONObject_PayResult.put("orderSN", ali_orderNumber);
                            mJSONObject_PayResult.put("paySuccess", pay_success);
                            mJSONObject_PayResult.put("payType", "2");

                            mWebView.loadUrl("javascript:vueObj.payGetResultPage('" + mJSONObject_PayResult.toString() + "')");
                            break;
                    }

                    break;
                case PAYWX_RESULT:
                    switch (msg.arg1) {
                        case 1://原生微信支付回调
                            Log.e("原生支付回调", msg.toString());
                            wxpayDataCallBack(orderId);

                            break;
                        case 0://H5支付回调
                            //支付结果回调 微信
                            mJSONObject_PayResult.put("orderSN", wx_orderNumber);
                            mJSONObject_PayResult.put("paySuccess", pay_success);
                            mJSONObject_PayResult.put("payType", "1");

                            if (!TextUtils.isEmpty(mJSONObject_PayResult.toString())) {
                                mWebView.loadUrl("javascript:vueObj.payGetResultPage('" + mJSONObject_PayResult.toString() + "')");
                            }
                            break;
                    }
                    break;
                case GOOD_DETAILS:
                    //商品详情 进入
                    mIntent = new Intent();
                    mIntent.setClass(mActivity, GoodsActivity.class);
                    mIntent.putExtra(Key.KEY_GOODS_ID.getValue(), (String) mMessage.obj);
                    startActivity(mIntent);
                    break;
                case GO_HOME:
                    //返回 上一级
                    finish();
                    break;
                case BACK_HOME:
                    EventBus.getDefault().post(new CommonEvent(EventWhat.SAMECITY_HOME.getValue()));
                    finish();
                    break;
                case BACK_NEAR:
                    EventBus.getDefault().post(new CommonEvent(EventWhat.SAMECITY_HOME.getValue()));
                    finish();
                    break;
                case BACK_ORDER_LIST:
                    EventBus.getDefault().post(new CommonEvent(EventWhat.SAMECITY_ORDER.getValue()));
                    finish();
                    break;
                case LOCATION_H5:

                    if (App.getInstance().isLocation) {

                        JSONObject location_object = new JSONObject();
                        location_object.put("address", App.getInstance().location);
                        location_object.put("district", App.getInstance().district);
                        location_object.put("longitude", App.getInstance().lng);
                        location_object.put("latitude", App.getInstance().lat);
                        location_object.put("province", App.getInstance().province);
                        location_object.put("city", App.getInstance().city);

                        if (App.getInstance().city_name != null || App.getInstance().city_code != null) {
                            location_object.put("city_code", App.getInstance().city_code);
                            location_object.put("postionType", "2");
                        } else {
                            location_object.put("postionType", "1");
                        }

                        if (mWebView != null) {
                            mWebView.loadUrl("javascript:vueObj.syncPostionDataPage('" + location_object + "')");
                        }
                    }
                    break;
                case CHECK_PAY:
                    mWebView.loadUrl("javascript:vueObj.checkAliPayPage(2)");
                    break;
                case GO_ADDRESSLIST:
                    mIntent = new Intent(mActivity, TakeOutAddressListActivity.class);
                    mIntent.putExtra(Key.ADS_SHOPID.getValue(), shop_id);
                    startActivity(mIntent);
                    break;
                case ADDRESS_INFO:
                    JSONObject ads_info = new JSONObject();
                    ads_info.put("ads_info", App.getInstance().ads_info);
                    ads_info.put("ads_name", App.getInstance().ads_name);
                    ads_info.put("ads_phone", App.getInstance().ads_phone);
                    ads_info.put("ads_id", App.getInstance().ads_id);
                    mWebView.loadUrl("javascript:vueObj.goToSelectAddressPage('" + ads_info + "')");
                    break;
                case OPEN_WX:
                    if (Utils.isWeixinAvilible(mActivity)) {
                        Intent iten = new Intent(Intent.ACTION_MAIN);
                        ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                        iten.addCategory(Intent.CATEGORY_LAUNCHER);
                        iten.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        iten.setComponent(cmp);
                        startActivity(iten);
                    } else {
                        Toast.makeText(mActivity, "请先安装微信!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindLayoutId();
        inittView();

        EventBus.getDefault().register(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
//        mWebView.loadUrl("about:blank");
    }

    /***
     * webview 初始化 设置
     * @param activity
     * @param webView
     * @param progressBar
     * @param view_Hint
     * @param button_Reload
     * @param url
     */
    public void setWebview(Activity activity, WebView webView, ProgressBar progressBar, View view_Hint, Button button_Reload, String url) {

        this.mActivity = activity;
        this.mWebView = webView;
        this.mProgressBar = progressBar;
        this.mView_Hint = view_Hint;
        this.mButton_Reload = button_Reload;


        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPayPlugin = UnifyPayPlugin.getInstance(mActivity);
        mPayRequest = new UnifyPayRequest();

        mWebviewUtils = new WebviewUtils(activity, mWebView);
        mWebviewUtils.webviewSet();
        mWebviewUtils.setWebViewImage(false);

        Location.locationCallback(new Location.OnLocationListener() {
            @Override
            public void onSuccess(AMapLocation amapLocation) {
                App.getInstance().isLocation = true;
            }

            @Override
            public void onError(AMapLocation amapLocation) {
                App.getInstance().isLocation = false;
            }

            @Override
            public void onFinished(AMapLocation amapLocation) {

            }
        });


        /***web 交互指定***/
        mWebView.addJavascriptInterface(new NativeInterface(), "NativeInterface");

        mWebView.setWebChromeClient(new mWebChromeClient());
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.loadUrl(url);

        mButton_Reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.reload();
            }
        });
    }

    @Subscribe
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_LOGIN:
                mMessage = new Message();
                mMessage.what = USER_LOGIN;
                mHandler.sendMessage(mMessage);
                break;
            case EVENT_WX_PAY_SUCCESS_LIFE:
                pay_success = "1";
                mMessage = new Message();

                mMessage.what = PAYWX_RESULT;
                mMessage.arg1 = wxpayType;
                mHandler.sendMessage(mMessage);
                break;
            case EVENT_PAY_WX_FAILURE:
                pay_success = "2";
                mMessage = new Message();

                mMessage.what = PAYWX_RESULT;
                mMessage.arg1 = wxpayType;
                mHandler.sendMessage(mMessage);
                break;
            case EVENT_PAY_CANCEL:
                pay_success = "2";
                mMessage = new Message();

                mMessage.what = PAYWX_RESULT;
                mMessage.arg1 = wxpayType;
                mHandler.sendMessage(mMessage);
                break;
            case SAMECITY_ADDRESS_INFO:
                mMessage = new Message();
                mMessage.what = ADDRESS_INFO;
                mHandler.sendMessage(mMessage);
                break;
            case EVENT_LOGOUT:
                clearCookies(mActivity);
                break;
        }
    }

    /***
     * cookies 同步
     * @param url
     */
    public void setWebCookies(String url) {

        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if (uri != null) {

            mCookieManager = CookieManager.getInstance();
            mCookieManager.setAcceptCookie(true);

            //将自己选择城市或则定位选择城市 将经纬度 和区域ID分别添加到cookie中
            if (App.getInstance().isLocation) {//选择城市
                if (!TextUtils.isEmpty(App.getInstance().lat)) {//定位成功
                    if (App.getInstance().clickChangeCity) {//选择城市(点击变换的)

                        mCookieManager.setCookie(url, "areaId" + App.getInstance().home_area_code);
                        mCookieManager.setCookie(url, "regionCode" + App.getInstance().city_code);
                    } else {
                        mCookieManager.setCookie(url, "areaId" + App.getInstance().adcode);
                        mCookieManager.setCookie(url, "latitude" + App.getInstance().lat);
                        mCookieManager.setCookie(url, "longitude" + App.getInstance().lng);
                    }
                } else {
                    mCookieManager.setCookie(url, "areaId" + App.getInstance().adcode);
                    mCookieManager.setCookie(url, "latitude" + App.getInstance().lat);
                    mCookieManager.setCookie(url, "longitude" + App.getInstance().lng);
                }
            } else {//定位失败(选择了城市)
                mCookieManager.setCookie(url, "areaId" + App.getInstance().home_area_code);
                mCookieManager.setCookie(url, "regionCode" + App.getInstance().city_code);
            }

            mCookieManager.setCookie(url, "LBS_TOKEN=" + user_token);
            mCookieManager.setCookie(url, "USER_ID=" + user_id);
            mCookieManager.setCookie(url, "_identity=" + user_identity);
            mCookieManager.setCookie(url,"USER_GUID="+ AppPreference.getIntance().getUserGuid());

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(this);
                cookieSyncManager.sync();
            } else {
                mCookieManager.flush();
            }
        }
    }

    /***
     * 清除cookies
     */
    public void clearCookies(Context context) {

        try {
            CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();//移除
            cookieManager.removeAllCookie();
            if (Build.VERSION.SDK_INT < 21) {
                CookieSyncManager.getInstance().sync();
            } else {
                CookieManager.getInstance().flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == PhotoPicker.REQUEST_CODE && data != null) {

                mArrayList_Imglocal = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);

                Luban.with(this)
                        .load(mArrayList_Imglocal)
                        .ignoreBy(100)
                        .setTargetDir(LubanImg.getPath())
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onSuccess(File file) {

                                Oss.getInstance().updaLoadImage(mActivity, user_token, file.getAbsolutePath(), new Oss.OssListener() {
                                    @Override
                                    public void onProgress(int progress) {

                                    }

                                    @Override
                                    public void onSuccess(String url) {

                                        mArrayList_ImgNet.add(url);

                                        if (mArrayList_ImgNet.size() == mArrayList_Imglocal.size()) {
                                            mMessage = new Message();
                                            mMessage.what = UPLOAD_PHOTO;
                                            mHandler.sendMessage(mMessage);
                                        }
                                    }

                                    @Override
                                    public void onFailure() {

                                    }
                                });

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                        }).launch();


            } else {
                Toast.makeText(mActivity, "暂无图片数据", Toast.LENGTH_SHORT).show();
            }

        } else if (RequestCode.valueOf(requestCode) == RequestCode.REQUEST_CODE_SCAN) {
            if (resultCode == QR_CODE_RESULT) {
                mWebView.loadUrl(data.getStringExtra(Key.KEY_RESULT.getValue()));
            }
        }
    }

    /****
     * 银联 微信支付
     * @param data
     */
    private void payWxYl(String data) {
        mPayRequest.payChannel = UnifyPayRequest.CHANNEL_WEIXIN;
        mPayRequest.payData = data;

        mPayPlugin.sendPayRequest(mPayRequest);
    }

    /****
     * 银联 支付宝支付
     * @param data
     */
    private void payAliYl(String data) {
        mPayRequest.payChannel = UnifyPayRequest.CHANNEL_ALIPAY;
        mPayRequest.payData = data;

        mPayPlugin.sendPayRequest(mPayRequest);
    }

    public class NativeInterface {

        @JavascriptInterface
        public void getCurrentPosition() {
            mMessage = new Message();
            mMessage.what = LOCATION;
            mHandler.sendMessage(mMessage);
        }

        @JavascriptInterface
        public void scanCode() {
            mMessage = new Message();
            mMessage.what = SCAN_CODE;
            mHandler.sendMessage(mMessage);
        }

        @JavascriptInterface
        public void aliPayOrder(String orderData) {
            mMessage = new Message();
            mMessage.what = PAY_ALI;
            mMessage.obj = orderData;
            mHandler.sendMessage(mMessage);
        }

        @JavascriptInterface
        public void weixinPayOrder(String orderData) {
            mMessage = new Message();
            mMessage.what = PAY_WX;
            mMessage.obj = orderData;
            mHandler.sendMessage(mMessage);
        }

        @JavascriptInterface
        public void sharePages(String shareData) {
            mMessage = new Message();
            mMessage.what = SHARE;
            mMessage.obj = shareData;
            mHandler.sendMessage(mMessage);
        }

        @JavascriptInterface
        public void giveCall(String phoneNumber) {
            mMessage = new Message();
            mMessage.what = CALL_PHONE;
            mMessage.obj = phoneNumber;
            mHandler.sendMessage(mMessage);
        }

        @JavascriptInterface
        public void selectPhotos(String maxNumber) {
            mMessage = new Message();
            mMessage.what = OPEN_PHOTO;
            mMessage.obj = maxNumber;
            mHandler.sendMessage(mMessage);
        }

        @JavascriptInterface
        public void goLogin(String message) {
            mMessage = new Message();
            mMessage.obj = message;
            mMessage.what = LOGIN;
            mHandler.sendMessage(mMessage);
        }

        @JavascriptInterface
        public void getUserInfo() {
            mMessage = new Message();
            mMessage.what = USER_INFO;
            mHandler.sendMessage(mMessage);
        }

        @JavascriptInterface
        public void goGoodsDetail(String skuid) {
            mMessage = new Message();
            mMessage.what = GOOD_DETAILS;
            mMessage.obj = skuid;
            mHandler.sendMessage(mMessage);
        }

        @JavascriptInterface
        public void goHome() {
            mMessage = new Message();
            mMessage.what = GO_HOME;
            mHandler.sendMessage(mMessage);
        }

        @JavascriptInterface
        public void webGoBack() {
            mMessage = new Message();
            mMessage.what = WEB_BACK;
            mHandler.sendMessage(mMessage);
        }

        @JavascriptInterface
        public void goBack() {
            mMessage = new Message();
            mMessage.what = WEB_BACK;
            mHandler.sendMessage(mMessage);
        }

        @JavascriptInterface
        public void backCityHome() {

            mMessage = new Message();
            mMessage.what = BACK_HOME;
            mHandler.sendMessage(mMessage);
        }

        @JavascriptInterface
        public void backCityCategorys() {
            mMessage = new Message();
            mMessage.what = BACK_NEAR;
            mHandler.sendMessage(mMessage);
        }

        @JavascriptInterface
        public void backCityOrder() {
            mMessage = new Message();
            mMessage.what = BACK_ORDER_LIST;
            mHandler.sendMessage(mMessage);
        }

        @JavascriptInterface
        public void syncPostionData() {
            mMessage = new Message();
            mMessage.what = LOCATION_H5;
            mHandler.sendMessage(mMessage);
        }

        @JavascriptInterface
        public void checkAliPay() {
            mMessage = new Message();
            mMessage.what = CHECK_PAY;
            mHandler.sendMessage(mMessage);
        }

        @JavascriptInterface
        public void goToSelectAddress(String shopId) {
            shop_id = Integer.parseInt(shopId);
            mMessage = new Message();
            mMessage.what = GO_ADDRESSLIST;
            mHandler.sendMessage(mMessage);
        }

        @JavascriptInterface
        public void goToGroupShopDetail(String shopId) {
            shop_id = Integer.parseInt(shopId);
            mMessage = new Message();
            mMessage.what = GROUP_TO_SHOP_DETAIL;
            mMessage.obj = shopId;
            mHandler.sendMessage(mMessage);
        }

        @JavascriptInterface
        public void goToGroupBuyDetail(String shopId, String productId) {
            shop_id = Integer.parseInt(shopId);
            product_id = Integer.parseInt(productId);

            mMessage = new Message();
            mMessage.what = Go_GROUP_BUY_DETAIL;
            mMessage.obj = shopId;
            mMessage.arg1 = product_id;
            mHandler.sendMessage(mMessage);
        }

        @JavascriptInterface
        public void openWechat(String message) {
            mMessage = new Message();
            mMessage.obj = message;
            mMessage.what = OPEN_WX;
            mHandler.sendMessage(mMessage);
        }


    }

    public class mWebChromeClient extends WebChromeClient {

        private View mCustomView;
        private CustomViewCallback mCustomViewCallback;

        /***
         * webview 加载进度
         * @param view
         * @param newProgress
         */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);

            if (newProgress == PROGRESS_100) {
                mProgressBar.setVisibility(View.GONE);
                mWebviewUtils.setWebViewImage(true);
            } else {
                mProgressBar.setProgress(newProgress);
            }
        }

        /***
         * webview 标题
         * @param view
         * @param title
         */
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }


        @Override
        public View getVideoLoadingProgressView() {
            return super.getVideoLoadingProgressView();
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);

            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomView = view;
            mFrameLayout.addView(mCustomView);
            mCustomViewCallback = callback;
            mWebView.setVisibility(View.GONE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        @Override
        public void onHideCustomView() {

            mWebView.setVisibility(View.VISIBLE);
            if (mCustomView == null) {
                return;
            }
            mCustomView.setVisibility(View.GONE);
            mFrameLayout.removeView(mCustomView);
            mCustomViewCallback.onCustomViewHidden();
            mCustomView = null;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            super.onHideCustomView();
        }


    }

    WebViewClient mWebViewClient = new WebViewClient() {

        /***
         * webview得到页面scale值发生改变时回调
         * @param view
         * @param oldScale
         * @param newScale
         */
        @Override
        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            super.onScaleChanged(view, oldScale, newScale);
        }


        /***
         * webview内部加载url
         * @param view
         * @param url
         * @return
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url == null) {
                return false;
            }

            //Android8.0以下的需要返回true 并且需要loadUrl；8.0之后效果相反
//            if (Build.VERSION.SDK_INT < 26) { }

            if (url.contains(HTTP) || url.contains(HTTPS)) {

                if (url.contains("webview_type=1")) {
                    mIntent = new Intent(mActivity, YSCWebViewActivity.class);
                    mIntent.putExtra(Key.KEY_URL.getValue(), url);
                    mActivity.startActivity(mIntent);
                } else {
                    view.loadUrl(url);
                }
                return true;
            } else {
                mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                mActivity.startActivity(mIntent);
                return true;
            }
        }

        /***
         * webview开始加载
         * @param view
         * @param url
         * @param favicon
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            /***同步 cookies  登录**/
            if (App.getInstance().isLogin()) {
                user_token = (String) SharedPreferencesUtils.getParam(mActivity, Key.USER_INFO_TOKEN.getValue(), "");
                user_id = (String) SharedPreferencesUtils.getParam(mActivity, Key.USER_INFO_ID.getValue(), "");
                user_identity = (String) SharedPreferencesUtils.getParam(mActivity, Key.USER_INFO_IDENTITY.getValue(), "");

                setWebCookies(url);
            } else {
                /**清除cookies*/
                clearCookies(mActivity);
            }
        }

        /***
         * webview加载完成
         * @param view
         * @param url
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

            webUrl = url;

            if (!urlList.contains(url)) {
                urlList.add(url);
            }
            if (mWebView.getUrl().contains(Api.H5_CITYLIFE_URL)) {
                /***验证登录状态 传递相关值**/
                if (App.getInstance().isLogin()) {
                    user_token = (String) SharedPreferencesUtils.getParam(mActivity, Key.USER_INFO_TOKEN.getValue(), "");
                    user_id = (String) SharedPreferencesUtils.getParam(mActivity, Key.USER_INFO_ID.getValue(), "");
                    user_identity = (String) SharedPreferencesUtils.getParam(mActivity, Key.USER_INFO_IDENTITY.getValue(), "");

                    is_login = "1";

                    mJSONObject_Login.put("isLogin", is_login);
                    mJSONObject_Login.put("userId", user_id);
                    mJSONObject_Login.put("token", user_token);
                    mJSONObject_Login.put("_identity", user_identity);

                    mWebView.loadUrl("javascript:vueObj.syncLoginStatusPage('" + mJSONObject_Login + "')");
                } else {
                    is_login = "0";
                    mJSONObject_Login.put("isLogin", is_login);
                    mWebView.loadUrl("javascript:vueObj.syncLoginStatusPage('" + mJSONObject_Login + "')");
                }
            }

            //验证cookie值
            CookieManager manager = CookieManager.getInstance();
            String cookie_value = manager.getCookie(url);
        }

        /***
         * webview加载资源
         * @param view
         * @param url
         */
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }


        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return;
            }
            /** webview 加载错误or失败 显示 6.0以下 ***/
            mView_Hint.setVisibility(View.VISIBLE);
        }

        /***
         * webview 访问url 错误  自定义加载错误view
         * @param view
         * @param request
         * @param error
         */
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            /** webview 加载错误or失败 显示 6.0以上 ***/
            if (request.isForMainFrame()) {
                mView_Hint.setVisibility(View.VISIBLE);
            }
        }

        /***
         * webview ssl 证书访问出错 handler.cancel()取消加载  handler.proceed()继续加载
         * @param view
         * @param handler
         * @param error
         */
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    };

    public void startAlipay(final JSONObject jsonObject) {

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {

                PayUtils payUtils = new PayUtils(mActivity);

                payUtils.AliPayV2(jsonObject, new PayUtils.OnPayCallback() {
                    @Override
                    public void aliCallback(Object object) {
                        mMessage = new Message();
                        mMessage.what = PAYALI_RESULT;
                        mMessage.obj = object;
                        mMessage.arg1 = alipayType;
                        mHandler.sendMessage(mMessage);
                    }
                });

            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public void payDataCallBack(String orderId) {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_CITY_ORDER_DETAIL, RequestMethod.GET);
        RequestAddHead.addNoHttpHead(request, mActivity, Api.API_CITY_ORDER_DETAIL, "GET");
        request.add("orderId", orderId);
        requestQueue.add(HttpWhat.HTTP_ORDER_RS_DATA.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                JSONObject object = JSON.parseObject(response.get());
                OrderModel result = JSON.parseObject(response.get(), OrderModel.class);
                if (response.responseCode() == 200) {
                    if (result == null) {
                        return;
                    }

                    if (result.getOrderStatus() == 2 || result.getOrderStatus() == 5) {

                        /****
                         * 支付成功
                         * payType 1 微信支付  2 支付宝支付
                         */
                        if (YlPayType == 1) {
                            mJSONObject_PayResult.put("orderSN", result.getOrderCode());
                            mJSONObject_PayResult.put("paySuccess", "1");
                            mJSONObject_PayResult.put("payType", "1");

                            mWebView.loadUrl("javascript:vueObj.payGetResultPage('" + mJSONObject_PayResult.toString() + "')");

                        } else if (YlPayType == 2) {

                            mJSONObject_PayResult.put("orderSN", result.getOrderCode());
                            mJSONObject_PayResult.put("paySuccess", "1");
                            mJSONObject_PayResult.put("payType", "2");

                            mWebView.loadUrl("javascript:vueObj.payGetResultPage('" + mJSONObject_PayResult.toString() + "')");
                        }


                    } else {

                        //支付失败
                        if (YlPayType == 1) {
                            mJSONObject_PayResult.put("orderSN", result.getOrderCode());
                            mJSONObject_PayResult.put("paySuccess", "2");
                            mJSONObject_PayResult.put("payType", "1");

                            mWebView.loadUrl("javascript:vueObj.payGetResultPage('" + mJSONObject_PayResult.toString() + "')");

                        } else if (YlPayType == 2) {

                            mJSONObject_PayResult.put("orderSN", result.getOrderCode());
                            mJSONObject_PayResult.put("paySuccess", "2");
                            mJSONObject_PayResult.put("payType", "2");

                            mWebView.loadUrl("javascript:vueObj.payGetResultPage('" + mJSONObject_PayResult.toString() + "')");
                        }

                    }

                } else {
                    Toast.makeText(mActivity, (object.getString("message")), Toast.LENGTH_SHORT).show();
                }

                YlOrderIdAli = null;
                YlOrderIdWx = null;
            }

            @Override
            public void onFailed(int what, Response<String> response) {

                YlOrderIdAli = null;
                YlOrderIdWx = null;

                Toast.makeText(mActivity, "数据异常,请稍候尝试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    public void wxpayDataCallBack(final String orderId) {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_CITY_ORDER_DETAIL, RequestMethod.GET);
        RequestAddHead.addNoHttpHead(request, mActivity, Api.API_CITY_ORDER_DETAIL, "GET");
        request.add("orderId", orderId);
        requestQueue.add(HttpWhat.HTTP_ORDER_RS_DATA.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                JSONObject object = JSON.parseObject(response.get());
                OrderModel result = JSON.parseObject(response.get(), OrderModel.class);
                if (response.responseCode() == 200) {
                    if (result == null) {
                        return;
                    }

                    if (result.getOrderStatus() == 2 || result.getOrderStatus() == 5) {
                        /****
                         * 支付成功
                         * payType 1 微信支付  2 支付宝支付
                         */
                        startActivity(new Intent(IBaseWebview.this, GroupBuyPaySuccessActivity.class)
                                .putExtra(KEY_ORDER, orderId));
                    } else {
                        startActivity(new Intent(IBaseWebview.this, GroupBuyPayFailActivity.class)
                                .putExtra(KEY_ORDER, orderId));
                    }

                } else {
                    Toast.makeText(mActivity, (object.getString("message")), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {

                Toast.makeText(mActivity, "数据异常,请稍候尝试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                break;
        }
    }

    private Handler timeHandler = new Handler();

    @Override
    protected void onResume() {
        super.onResume();

        timeHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /*** 支付结果查询 **/
                if (YlOrderIdAli != null) {
                    YlPayType = 2;
                    payDataCallBack(YlOrderIdAli);
                } else if (YlOrderIdWx != null) {
                    YlPayType = 1;
                    payDataCallBack(YlOrderIdWx);
                }
            }
        }, 2000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {

                if (mWebView != null) {

                    if (mWebView.canGoBack()) {

                        if (mWebView.getUrl().contains(PAY_SUCCESS)) {

                            mWebView.goBack();
                            mWebView.goBack();
                            mWebView.goBack();
                        } else if (mWebView.getUrl().contains(PAY_FAIL)) {
                            mWebView.goBack();
                            mWebView.goBack();
                        } else {
                            mWebView.goBack();
                        }

                        return true;
                    } else {

                        if (App.getInstance().ads_h5) {
//                            startActivity(new Intent(this, RootActivity.class));
                            App.getInstance().ads_h5 = false;
                            finish();
                        } else {
                            finish();
                        }
                    }


                } else {
                    finish();
                }
            }
        }
        //监听事件继续生效
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {

        if (mWebView != null) {
            mWebView.loadUrl(null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeAllViews();

            mWebView.destroy();
            mWebView = null;
        }

        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
