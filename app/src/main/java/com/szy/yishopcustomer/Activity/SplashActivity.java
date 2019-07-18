package com.szy.yishopcustomer.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.baidu.mobstat.StatService;
import com.hyphenate.easeui.EaseConstant;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.other.LogUtil;
import com.like.utilslib.other.ToastUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.activity.HomeActivity;
import com.lyzb.jbx.model.account.IsPerfectModel;
import com.lyzb.jbx.util.AppPreference;
import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonEvent;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.ResponseModel.AppInfo.ResponseAppInfoModel;
import com.szy.yishopcustomer.ResponseModel.Guide.Model;
import com.szy.yishopcustomer.Service.Location;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.LogUtils;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.Util.im.IMDoneListener;
import com.szy.yishopcustomer.Util.im.ImHelper;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.StringRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by 宗仁 on 16/8/10.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class SplashActivity extends AppCompatActivity implements OnResponseListener<String>, View
        .OnClickListener, OnEmptyViewClickListener {

    private static final int RC_LOCATION_CONTACTS_PERM = 101;//定位权限
    private static final String XG_TAG = "xinge";
    private static final int REQUEST_GUIDE = 10;
    RequestQueue mRequestQueue;
    TextView mUpdateTextView;
    Button mUpdateButton;//更新下载
    Button mUseButton;//继续使用

    TextView mCloseTextView;
    ResponseAppInfoModel mResponseAppInfoModel;
    View mOfflineView;
    private Toast toast;
    private Context mContext;

    private boolean isFinished;
    private boolean isUsed;


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_splash_update_button:
                goUpdate();
                break;
/*            case R.id.network_request_failed_button:
                refresh();
                break;*/
            case R.id.activity_splash_use_button:
                openRootActivity();
                break;
        }
    }

    @Override
    public void onEmptyViewClicked() {
    }

    @Override
    public void onOfflineViewClicked() {
        mOfflineView.setVisibility(View.GONE);
        refresh();
    }

    @Override
    public void onStart(int what) {

    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GUIDE:
//                getGuideCallback(response.get());
                break;
            //获取用户数据
            case HTTP_APP_INFO:
                refreshCallback(response.get());
                break;
            //获取猜你喜欢的数据
            case HTTP_INDEX_GUESS_LIKE:
                App.getInstance().index_like_data = response.get();
                break;
            //获取广告接口
            case HTTP_ADS:
                getAdsDataCallback(response.get());
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        showOfflineView();
    }

    @Override
    public void onFinish(int what) {
        if (what == HttpWhat.HTTP_ADS.getValue()) {
            mRequestQueue.stop();
        }
    }

    public void showOfflineView() {
        mOfflineView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_GUIDE:
                if ("1".equals(mResponseAppInfoModel.data.SYS_SITE_MODE) && Utils.isNull
                        (mResponseAppInfoModel.data.site_id)) {
                    openSiteActivity();
                } else {
                    openRootActivity();
                }
                break;
            case RC_LOCATION_CONTACTS_PERM:
                //toast.setText("onActivityResult定位和获取联系人权限");
                //toast.show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int num = AppPreference.getIntance().getKeyHintThree();
        LogUtil.loge("当前次数为" + num);
        AppPreference.getIntance().setKeyHintThree(num + 1);
        LogUtil.loge("当前次数为------>>>>>" + AppPreference.getIntance().getKeyHintThree());

        //百度统计 设置
        // setSendLogStrategy已经@deprecated，建议使用新的start接口
        // 如果没有页面和自定义事件统计埋点，此代码一定要设置，否则无法完成统计
        // 进程第一次执行此代码，会导致发送上次缓存的统计数据；若无上次缓存数据，则发送空启动日志
        // 由于多进程等可能造成Application多次执行，建议此代码不要埋点在Application中，否则可能造    成启动次数偏高
        // 建议此代码埋点在统计路径触发的第一个页面中，若可能存在多个则建议都埋点
        StatService.start(this);

        /**隐藏状态栏 5.0以上*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_splash);
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        mContext = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mOfflineView = findViewById(R.id.activity_splash_offline_view);
        Button button = (Button) findViewById(com.szy.common.R.id.empty_view_button);
        ImageView imageView = (ImageView) findViewById(com.szy.common.R.id.empty_view_imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOfflineViewClicked();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOfflineViewClicked();
            }
        });

        isUsed = Utils.getBoolFromSharedPreferences(getApplicationContext(), Key.KEY_IS_USED.toString());

        mRequestQueue = NoHttp.newRequestQueue(4);

        refresh();
        new android.os.Handler().postDelayed(new Runnable() {
            public void run() {
                openRootActivity();
            }
        }, 1000);
        Location.locationCallback(null);
    }

    @Override
    public void onStart() {
        super.onStart();
        App.activityCount++;

    }

    /****
     * 获取当前用户是否完善信息
     *
     */
    public void getInfoStatus() {
        final RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_LOGIN_IS_PERFECT_MSG, RequestMethod.GET);
        RequestAddHead.addNoHttpHead(request, this, Api.API_LOGIN_IS_PERFECT_MSG, "GET");
        requestQueue.add(HttpWhat.HTTP_IS_PERFECT.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                if (response.responseCode() == 200) {
                    IsPerfectModel model = GSONUtil.getEntity(response.get(), IsPerfectModel.class);
                    if (null != model) {
                        App.getInstance().isUserInfoPer = model.isSt();
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        App.activityCount--;
        if (App.activityCount <= 0) {
            if (Utils.getBoolFromSharedPreferences(getApplicationContext(), Key.KEY_NEED_RESTART
                    .getValue())) {
                Utils.setSharedPreferences(getApplicationContext(), Key.KEY_NEED_RESTART.getValue
                        (), false);
                System.exit(0);
            }
        }
    }


    private void getGuide() {
        StringRequest request = new StringRequest(Api.API_APP_GUIDE, RequestMethod.GET);
        request.setUserAgent("szyapp/android");
        mRequestQueue.add(HttpWhat.HTTP_GUIDE.getValue(), request, this);
    }

    private void getGuideCallback(String response) {
        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model model) {
                if (model.data.is_guide_open.equals("1") && !Utils.isNull(model.data.img_list)) {
                    openGuideActivity(model);
                } else {
                    openRootActivity();
                }
            }

            @Override
            public void onFailure(String message) {
                openRootActivity();
            }
        });
    }

    private void goUpdate() {
        if (!Utils.isNull(mResponseAppInfoModel.data.update_url)) {
            Utils.openBrowser(this, mResponseAppInfoModel.data.update_url);
        } else {
            Toast.makeText(this, "应用下载链接为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void getAdsData() {
        StringRequest request = new StringRequest(Api.API_ADS_INFO, RequestMethod.GET);
        request.add("sys_type", 1);
        mRequestQueue.add(HttpWhat.HTTP_ADS.getValue(), request, this);
    }

    private void getAdsDataCallback(String response) {
        int code = JSONObject.parseObject(response).getInteger("code");
        if (code == 0) {
            JSONObject data = JSONObject.parseObject(response).getJSONObject("data");
            App.getInstance().ads_url = data.getString("xhdpi");
            App.getInstance().ads_link = data.getString("advert_link");
            App.getInstance().ads_skip_time = data.getString("advert_time");
            openAdsActivity();
        } else {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /***
     * 跳转到引导页
     * @param model
     */
    private void openGuideActivity(Model model) {
        Intent intent = new Intent(this, GuideActivity.class);
        ArrayList<String> imageList = new ArrayList<>();
        imageList.addAll(model.data.img_list);
        intent.putStringArrayListExtra(Key.KEY_GUIDE_IMAGES.getValue(), imageList);
        intent.putExtra(Key.KEY_GUIDE_BUTTON.getValue(), model.data.app_enter_button);
        startActivityForResult(intent, REQUEST_GUIDE);
    }

    /***
     * 跳转广告页面
     */
    private void openAdsActivity() {
        startActivity(new Intent(this, AdsActivity.class));
        finish();
    }

    private void openRootActivity() {
        if (!isFinished) {
            isFinished = true;
            return;
        }
        if (mResponseAppInfoModel.data.SYS_SITE_MODE.equals("1") && Utils.isNull
                (mResponseAppInfoModel.data.site_id)) {
            openSiteActivity();
            return;
        }
//        } else {
//            Intent intent = new Intent(this, HomeActivity.class);
//            startActivity(intent);
//            finish();
//        }

        getAdsData();
    }

    private void openSiteActivity() {
        Intent intent = new Intent(this, SiteActivity.class);
        intent.putExtra(Key.KEY_ENABLE_CLOSE_BUTTON.getValue(), false);
        startActivity(intent);
        finish();
    }

    private void refresh() {
        StringRequest request = new StringRequest(Api.API_APP_INFO, RequestMethod.GET);
        request.setUserAgent("szyapp/android");
        mRequestQueue.add(HttpWhat.HTTP_APP_INFO.getValue(), request, this);
    }

    /***
     * 获取-猜你喜欢data
     */
    private void getLikeData() {

        StringRequest request = new StringRequest(Api.API_GUESS_LIKE_URL, RequestMethod.GET);
        request.setUserAgent("szyapp/android");
        mRequestQueue.add(HttpWhat.HTTP_INDEX_GUESS_LIKE.getValue(), request, this);

        if (App.getInstance().isLogin()) {
            request.add("user_id", App.getInstance().userId);
        } else {
            request.add("user_id", 0);
        }
        request.add("cur_page", 1);
        request.add("page_size", 24);
    }

    private void refreshCallback(String response) {
        LogUtils.Companion.e("版本新信息" + response);
        if (response.equals(getResources().getString(R.string.error))) {
            showOfflineView();
            return;
        }
        HttpResultManager.resolve(response, ResponseAppInfoModel.class, new HttpResultManager.HttpResultCallBack<ResponseAppInfoModel>() {
            @Override
            public void onSuccess(ResponseAppInfoModel back) {
                mResponseAppInfoModel = back;
                if (!Utils.isNull(mResponseAppInfoModel.data)) {
                    App.getInstance().setImageBaseUrl(mResponseAppInfoModel.data.image_url);
                    if (!Utils.isNull(mResponseAppInfoModel.data.user_id) && Integer.valueOf(mResponseAppInfoModel.data.user_id) > 0) {

                        App.getInstance().setLogin(true);
                        App.getInstance().xingeUserId = XG_TAG + mResponseAppInfoModel.data.user_id;
                        App.getInstance().userId = mResponseAppInfoModel.data.user_id;
                        App.getInstance().user_token = (String) SharedPreferencesUtils.getParam(SplashActivity.this,
                                Key.USER_INFO_TOKEN.getValue(), "");

                    } else {
                        App.getInstance().setLogin(false);
                    }

                    EaseConstant.CHAT_USER_ID = SharedPreferencesUtils.getParam(SplashActivity.this, Key.IM_USERNAME.name(),
                            "").toString();

                    getLikeData();

                    getInfoStatus();

                    App.getInstance().weixinLogin = mResponseAppInfoModel.data.use_weixin_login.contentEquals("1");
                    App.getInstance().weixinLoginLogo = TextUtils.isEmpty(mResponseAppInfoModel.data.wx_login_logo) ? "" : (mResponseAppInfoModel.data.wx_login_logo + "?" + System.currentTimeMillis());
                    App.getInstance().loginLogo = TextUtils.isEmpty(mResponseAppInfoModel.data.login_logo) ? "" : (mResponseAppInfoModel.data.login_logo + "?" + System.currentTimeMillis());
                    App.getInstance().loginBackground = TextUtils.isEmpty(mResponseAppInfoModel.data.login_bgimg) ? "" : (mResponseAppInfoModel.data.login_bgimg + "?" + System.currentTimeMillis());

                    App.getInstance().wangXinAppKey = mResponseAppInfoModel.data.aliim_appkey;
                    App.getInstance().wangXinUserId = mResponseAppInfoModel.data.aliim_uid;
                    App.getInstance().wangXinPassword = mResponseAppInfoModel.data.aliim_pwd;
                    App.getInstance().wangXinServiceId = mResponseAppInfoModel.data.aliim_main_customer;
                    App.getInstance().phoneNumber = mResponseAppInfoModel.data.mall_phone;
                    //App.getInstance().qq = mResponseAppInfoModel.data.qq;
                    App.getInstance().YWEnable = mResponseAppInfoModel.data.aliim_enable;
                    App.getInstance().aliim_icon_show = mResponseAppInfoModel.data.aliim_icon_show;
                    App.getInstance().aliim_icon = mResponseAppInfoModel.data.aliim_icon;
                    App.getInstance().wangXinAvatar = mResponseAppInfoModel.data.aliim_customer_logo;
                    App.getInstance().userCenterBgimage = TextUtils.isEmpty(mResponseAppInfoModel.data.m_user_center_bgimage) ? "" : (mResponseAppInfoModel.data.m_user_center_bgimage + "?" + System.currentTimeMillis());
                    App.getInstance().default_lazyload = mResponseAppInfoModel.data.default_lazyload;
                    App.getInstance().aliim_icon = TextUtils.isEmpty(mResponseAppInfoModel.data.aliim_icon) ? "" : (mResponseAppInfoModel.data.aliim_icon + "?" + System.currentTimeMillis());

                    App.getInstance().default_user_portrait = mResponseAppInfoModel.data.default_user_portrait;
                    App.getInstance().default_shop_image = mResponseAppInfoModel.data.default_shop_image;
                    App.getInstance().default_micro_shop_image = mResponseAppInfoModel.data.default_micro_shop_image;
                    App.getInstance().site_nav_list = mResponseAppInfoModel.data.site_nav_list;

                    App.getInstance().site_region_code = mResponseAppInfoModel.data.region_code;

                    AppPreference.getIntance().setUserGuid(mResponseAppInfoModel.data.user_guid);

                    //处理环信登录
                    App.getInstance().nickName = AppPreference.getIntance().getHxNickName();
                    App.getInstance().headimg = AppPreference.getIntance().getHxHeaderImg();

                    String userName = SharedPreferencesUtils.getParam(App.getInstance().mContext, Key.IM_USERNAME.name(), "").toString();
                    String password = SharedPreferencesUtils.getParam(App.getInstance().mContext, Key.IM_USERPASSWORD.name(), "").toString();
                    if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
                        ImHelper.getIntance().onLogin(userName, password, new IMDoneListener() {
                            @Override
                            public void onSuccess() {
                                //注册监听
                                EventBus.getDefault().post(new CommonEvent(EventWhat.HX_LISTINENER
                                        .getValue()));
                            }

                            @Override
                            public void onFailer(String message) {

                            }
                        });
                    }

                    /****
                     * 系统维护中
                     *
                     */
                    if (mResponseAppInfoModel.data.app_android_is_open == 0 && Utils
                            .compareTo(mResponseAppInfoModel.data.app_android_use_version, Utils.getVersionName(mContext)) >= 0) {

                        setContentView(R.layout.activity_splash_closed);
                        mCloseTextView = (TextView) findViewById(R.id.activity_splash_closed_textView);

                        if (!Utils.isNull(mResponseAppInfoModel.data.close_reason)) {
                            mCloseTextView.setText(mResponseAppInfoModel.data.close_reason);
                            mCloseTextView.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                    }
                    /***
                     * 版本更新
                     *
                     */
                    else if (Utils.compareTo(mResponseAppInfoModel.data.latest_version, Utils.getVersionName(mContext)) > 0) {
                        setContentView(R.layout.activity_splash_update);
                        mUpdateTextView = (TextView) findViewById(R.id.activity_splash_update_textView);

                        mUpdateButton = (Button) findViewById(R.id.activity_splash_update_button);
                        mUpdateButton.setOnClickListener(SplashActivity.this);

                        mUseButton = (Button) findViewById(R.id.activity_splash_use_button);
                        mUseButton.setOnClickListener(SplashActivity.this);

                        if (!Utils.isNull(mResponseAppInfoModel.data.app_android_update_content)) {
                            mUpdateTextView.setText(mResponseAppInfoModel.data.app_android_update_content);
                        }

                        if (mResponseAppInfoModel.data.app_android_is_force == 0) {
                            //强制更新
                            mUseButton.setVisibility(View.GONE);
                        } else if (mResponseAppInfoModel.data.app_android_is_force == 1) {
                            mUseButton.setVisibility(View.VISIBLE);
                        }

                    } else if (!isUsed) {
                        App.getInstance().isGuide = true;
                        //getGuide();
                        getAdsData();
                    } else if (mResponseAppInfoModel.data.SYS_SITE_MODE.equals("1") && Utils.isNull(mResponseAppInfoModel.data.site_id)) {
                        openSiteActivity();
                    } else {
                        openRootActivity();
                    }
                }
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                showOfflineView();
            }
        });
    }
}
