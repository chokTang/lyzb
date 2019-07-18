package com.szy.yishopcustomer.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONException;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.swipbackhelper.SwipeListener;
import com.lyzb.jbx.R;
import com.szy.common.Activity.CommonActivity;
import com.szy.common.Fragment.CommonFragment;
import com.szy.common.Other.CommonEvent;
import com.szy.yishopcustomer.Activity.im.LyMessageActivity;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.MenuPopwindow;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

import java.net.HttpCookie;
import java.net.URLEncoder;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by 宗仁 on 2016/12/13.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public abstract class YSCBaseActivity extends CommonActivity {
    private static final int YSC_REQUEST_CODE_LOGIN = 0;
    protected boolean mEnableBaseMenu = false;
    protected int mBaseMenuId = R.menu.activity_base;
    protected MenuPopwindow menuPopwindow;
    protected Context mContext;
    //判断界面是否需要登录
    protected boolean requiredLanding = false;

    //自定义的menu样式，如果不为0的话就使用自定义的pop
    protected int mCustemMenuStyle = 1;

    String activityName = "YSCBaseActivity";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mEnableBaseMenu) {
            if (mCustemMenuStyle > 0) {
                mBaseMenuId = R.menu.activity_base_custem;
            }

            getMenuInflater().inflate(mBaseMenuId, menu);
//            final MenuItem item = menu.findItem(R.id.activity_base_moreMenu);
            //初始化自定义的pop菜单，提取方法是为了，如果不走这个创建菜单的方法可以手动调用
            initCustemMenu();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_base_moreMenu:
                menuPopwindow.showPopupWindow(this.getWindow().getDecorView());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected CommonFragment createFragment() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //通过获取activity名字判断一些特殊的操作，比如主页面不需要滑动关闭
        try {
            activityName = getClass().getName().substring(getClass().getName().lastIndexOf(".") + 1);
        } catch (Exception e) {

        }

        Log.e("onCreate", activityName);
        mContext = this;

        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)//获取当前页面
                .setSwipeBackEnable(false)//设置是否可滑动
                .setSwipeEdge(100)//可滑动的范围。px。200表示为左边200px的屏幕
                .setSwipeEdgePercent(0.1f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
                .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
                .setScrimColor(Color.parseColor("#80000000"))//底层阴影颜色
                .setClosePercent(0.8f)//触发关闭Activity百分比
                .setSwipeRelateEnable(false)//是否与下一级activity联动(微信效果)。默认关
                .setSwipeRelateOffset(100)//activity联动时的偏移量。默认500px。
                .setDisallowInterceptTouchEvent(false)//不抢占事件，默认关（事件将先由子View处理再由滑动关闭处理）
                .addListener(new SwipeListener() {//滑动监听

                    @Override
                    public void onScroll(float percent, int px) {//滑动的百分比与距离
                    }

                    @Override
                    public void onEdgeTouch() {//当开始滑动
                    }

                    @Override
                    public void onScrollToClose() {//当滑动关闭
                    }
                });

        //如果需要登录的话，之后的都不执行
        if (requiredLanding) {
            getClass().getPackage();
            //判断是否需要登录
            if (!App.getInstance().isLogin()) {
                Intent intent = new Intent(this, LoginActivity.class);
                //这样写在登录界面就可以获取到打开这个界面所传递的值了
//                Intent intent = getIntent();
//                intent.setClass(this, LoginActivity.class);
                intent.putExtra("activityName", getClass().getName());
                startActivityForResult(intent, YSC_REQUEST_CODE_LOGIN);
                //结束本页面，并将当前页面名称传入登录页面，用以登陆成功后重新打开本页面
                finish();
                return;
            }
        }

        //特殊处理查看大图界面的滑动颜色
        if ("ViewOriginalImageActivity".equals(activityName)) {
            SwipeBackHelper.getCurrentPage(this)//获取当前页面
                    .setScrimColor(Color.parseColor("#00000000"));
        }

        mRequestListener = new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                updateDialog();
                onRequestStart(what);
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String responseString = response.get();
                try {
                    com.alibaba.fastjson.JSON.parseObject(responseString);
                    onRequestSucceed(what, responseString);

                    /*** 登录API 存入identity **/
                    if (what == HttpWhat.HTTP_LOGIN.getValue() || what == HttpWhat.HTTP_LOGIN_OTHER.getValue()) {
                        /****
                         * 拿到identity
                         *
                         */
                        List<HttpCookie> cookies = response.getHeaders().getCookies();
                        for (HttpCookie httpCookie : cookies) {
                            if (httpCookie.getName().equals("_identity")) {

                                try {
                                    String identity = URLEncoder.encode(httpCookie.getValue(), "UTF-8");
                                    SharedPreferencesUtils.setParam(YSCBaseActivity.this, Key.USER_INFO_IDENTITY.getValue(), identity);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }

                                break;
                            }
                        }
                    }

                } catch (JSONException e) {
                    onRequestFailed(what, responseString);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                onRequestFailed(what, response.get());
            }

            @Override
            public void onFinish(int what) {
                mRequests.remove(what);
                updateDialog();
                onRequestFinish(what);
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        App.activityCount++;
    }

    @Override
    public void onStop() {
        super.onStop();
        App.activityCount--;
        if (App.activityCount <= 0) {
            if (Utils.getBoolFromSharedPreferences(getApplicationContext(),
                    Key.KEY_NEED_RESTART.getValue())) {
                Utils.setSharedPreferences(getApplicationContext(), Key.KEY_NEED_RESTART.getValue(),
                        false);
                System.exit(0);
            }
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        if (!Utils.isNull(Config.CUSTOM_FONT_NAME)) {
            super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
        } else {
            super.attachBaseContext(base);
        }
    }


    protected void openMessageActivity() {
        startActivity(new Intent(this, LyMessageActivity.class));
    }

    protected void openRootCart() {
        startActivity(new Intent().setClass(this, RootActivity.class));
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_CART_TAB.getValue()));
        finish();
    }

    protected void openRootCategory() {
        startActivity(new Intent().setClass(this, RootActivity.class));
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_CATEGORY_TAB.getValue()));
        finish();
    }

    protected void openRootIndex() {
        startActivity(new Intent().setClass(this, RootActivity.class));
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_INDEX.getValue()));
        finish();
    }

    protected void openRootUser() {
        startActivity(new Intent().setClass(this, RootActivity.class));
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_USER_TAB.getValue()));
        finish();
    }

    protected void openSearchActivity() {
        startActivity(new Intent().setClass(this, SearchActivity.class));
    }

    protected void initCustemMenu() {
        if (menuPopwindow == null) {
            menuPopwindow = new MenuPopwindow(this, mCustemMenuStyle, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.activity_base_homeMenu:
                            openRootIndex();
                            break;
                        case R.id.activity_base_categoryMenu:
                            openRootCategory();
                            break;
                        case R.id.activity_base_searchMenu:
                            openSearchActivity();
                            break;
                        case R.id.activity_base_messageMenu:
                            /**消息 menu*/
                            openMessageActivity();

                            break;
                        case R.id.activity_base_cartMenu:
                            openRootCart();
                            break;
                        case R.id.activity_base_userMenu:
                            openRootUser();
                            break;
                        case R.id.action_my_following:
                            openCollectionActivity();
                            break;
                        case R.id.activity_base_share:
                            //menu的分享
                            openShareActivity();
                            break;
                        case R.id.activity_base_blank:
                            menuPopwindow.dismiss();
                            break;
                    }
                    menuPopwindow.dismiss();
                }
            });
        }
    }

    void openCollectionActivity() {
    }

    void openShareActivity() {
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }


    void toast(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 判断是否拥有权限
     *
     * @param permissions
     * @return
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    /**
     * 请求权限
     */
    protected void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
        Utils.toastUtil.showToast(this, "如果拒绝授权,会导致应用无法正常使用");
    }


    /**
     * 请求权限的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */

//说明：
//Constants.CODE_CAMERA
//这是在外部封装的一个常量类，里面有许多静态的URL以及权限的CODE，可以自定义
//但是在调用的时候，记得这个CODE要和你自己定义的CODE一一对应
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

//    @Override
//    public void startActivity(Intent intent) {
//        super.startActivity(intent);
//        overridePendingTransition(R.anim.exit_anim, R.anim.pop_enter_anim);
//    }
//
//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.enter_anim, R.anim.popexit_anim);
//    }
}
