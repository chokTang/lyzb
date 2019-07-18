package com.szy.yishopcustomer.Activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lyzb.jbx.R;
import com.lyzb.jbx.util.AppPreference;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.szy.common.Fragment.CommonFragment;
import com.szy.common.Interface.SimpleImageLoadingListener;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Util.ImageLoader;
import com.szy.common.Util.JSON;
import com.szy.yishopcustomer.Activity.im.LyMessageActivity;
import com.szy.yishopcustomer.Activity.samecity.CityLifeActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Fragment.CartFragment;
import com.szy.yishopcustomer.Fragment.CategoryFragment;
import com.szy.yishopcustomer.Fragment.CityLifeFragment;
import com.szy.yishopcustomer.Fragment.IndexFragment;
import com.szy.yishopcustomer.Fragment.InventoryFragment;
import com.szy.yishopcustomer.Fragment.ShopStreetFragment;
import com.szy.yishopcustomer.Fragment.UserFragment;
import com.szy.yishopcustomer.Interface.CartAnimationMaker;
import com.szy.yishopcustomer.Interface.OnUserIngotNumberViewListener;
import com.szy.yishopcustomer.ResponseModel.AppInfo.DataModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.LogUtils;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.Util.json.GsonUtils;
import com.szy.yishopcustomer.ViewModel.HomeTableBarModel;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by 宗仁 on 16/4/26.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class RootActivity extends YSCBaseActivity implements CartAnimationMaker,
        View.OnClickListener, OnUserIngotNumberViewListener {
    private static final String TAG = "RootActivity";
    private static final String SHOP_TAG = "SHOP";
    private static final String INDEX_FRAGMENT_TAG = "HOME";
    private static final String CATEGORY_FRAGMENT_TAG = "KEY_CATEGORY";
    private static final String INVENTORY_FRAGMENT_TAG = "INVENTORY";
    private static final String CART_FRAGMENT_TAG = "CART";
    private static final String USER_FRAGMENT_TAG = "HTTP_USER";

    private static final String CITY_LIFE = "CITY_LIFE";

    private static final String TEMP_DISPLAY_TAG = "temp_diaplay";

    private static final int LOCATION_PERMISSION = 1002;

    @BindView(R.id.activity_root_contentView)
    ViewGroup mContentView;
    @BindView(R.id.activity_root_tab_cart_badgeTextView)
    TextView mCartTextView;
    @BindView(R.id.activity_root_tabCart)
    RelativeLayout mTabCart;
    @BindView(R.id.activity_root_tabHome)
    LinearLayout mTabHome;

    @BindView(R.id.activity_root_tabWrapperLinearLayout)
    LinearLayout activity_root_tabWrapperLinearLayout;

    @BindView(R.id.activity_root_tabCategory)
    LinearLayout mTabCategory;
    @BindView(R.id.activity_root_tabInventory)
    RelativeLayout mTabInventory;
    @BindView(R.id.activity_root_tabUser)
    LinearLayout mTabUser;
    @BindView(R.id.activity_root_tabShop)
    RelativeLayout mTabShop;
    private List<View> mTabs;
    private IndexFragment mIndexFragment;
    private CategoryFragment mCategoryFragment;
    private InventoryFragment mInventoryFragment;
    private CartFragment mCartFragment;
    private UserFragment mUserFragment;
    private ShopStreetFragment mShopStreetFragment;

    private FragmentTransaction transaction;

    public TextView userIngotNumberView;
    List<DataModel.SiteNavListBean> navListBeens = new ArrayList<>();
    HomeTableBarModel homeTableBarModel;
    String[] tabName = {"首页", "共商", "同城生活", "购物车", "我的"};

    /**
     * 同城服务 fragment
     **/
    private CityLifeFragment mCityLifeFragment;

    private long exitTime = 0;

    @Override
    public CommonFragment createFragment() {
        return null;
    }

    @Override
    @SuppressLint("InflateParams")
    protected void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.activity_root;
        super.onCreate(savedInstanceState);

        mTabs = new ArrayList<>();
        mTabs.add(mTabHome);
        mTabs.add(mTabCategory);
        //mTabs.add(mTabInventory);
        mTabs.add(mTabShop);
        mTabs.add(mTabCart);
        mTabs.add(mTabUser);
        mTabHome.setOnClickListener(this);
        mTabCategory.setOnClickListener(this);
        mTabInventory.setOnClickListener(this);
        mTabCart.setOnClickListener(this);
        mTabUser.setOnClickListener(this);
        mTabShop.setOnClickListener(this);
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(false);
        }
        getTableBarImgaeView();

        //默认图片设置
        Drawable ddd = getResources().getDrawable(R.mipmap.pl_image);
        ImageLoader.ic_error = ddd;
        ImageLoader.ic_stub = ddd;

        ImageLoader.options = new DisplayImageOptions.Builder()
                .showImageOnLoading(ImageLoader.ic_stub)
                .showImageOnFail(ImageLoader.ic_stub)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new FadeInBitmapDisplayer(600))//是否图片加载好后渐入的动画时间
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        if (!TextUtils.isEmpty(App.getInstance().default_lazyload)) {
            ImageLoader.loadImage(Utils.urlOfImage(App.getInstance().default_lazyload), new SimpleImageLoadingListener() {

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    Drawable drawable = new BitmapDrawable(loadedImage);
                    ImageLoader.ic_error = drawable;
                    ImageLoader.ic_stub = drawable;

                    ImageLoader.options = new DisplayImageOptions.Builder()
                            .showImageOnLoading(ImageLoader.ic_stub)
                            .showImageOnFail(ImageLoader.ic_stub)
                            .cacheInMemory(true)
                            .cacheOnDisk(true)
                            .displayer(new FadeInBitmapDisplayer(600))//是否图片加载好后渐入的动画时间
                            .bitmapConfig(Bitmap.Config.RGB_565)
                            .build();

                }
            });
        }

        String adcode = (String) SharedPreferencesUtils.getParam(this, "no_alert_adcode", "");
        if (!TextUtils.isEmpty(adcode) && adcode.equals(App.getInstance().adcode)) {
            SharedPreferencesUtils.setParam(this, "no_alert_adcode", "");
        }

        //startActivity(new Intent(this,AccountBalanceActivity.class));
        getLocationPermis();
    }

    public void exit() {
        if (mTabs.get(0).isSelected()) {
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                Utils.toastUtil.showToast(RootActivity.this, "再按一下返回键退出");
//                exitTime = System.currentTimeMillis();
//            } else {
//                this.finish();
//            }
            this.finish();
        } else {
            setCurrentTab(0);
        }
    }

    @Override
    public void makeAnimation(Drawable anim, int x, int y) {
        if (x == 0 && y < 0) {
            return;
        }

        int[] endLocation = new int[2];
        mTabCart.getLocationInWindow(endLocation);
        endLocation[0] += mTabCart.getWidth() / 2;
        startAnimation(anim, x, y, endLocation[0], endLocation[1]);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }

        switch (view.getId()) {
            case R.id.activity_root_tabHome:
                setCurrentTab(0);
                break;
            case R.id.activity_root_tabCategory:
//                setCurrentTab(1);
                finish();
                break;
       /*     case R.id.activity_root_tabInventory:
                setCurrentTab(2);
                break;*/
            case R.id.activity_root_tabShop:
                setCurrentTab(2);
                break;
            case R.id.activity_root_tabCart:
                setCurrentTab(3);
//                startActivity(new Intent(this, CouponActivity.class));
                break;
            case R.id.activity_root_tabUser:
                setCurrentTab(4);
                break;
        }
    }


    public void openMessageActivity() {
//        Intent intent = new Intent();
//        intent.setClass(this, MessageActivity.class);
//        startActivity(intent);
        startActivity(new Intent(this, LyMessageActivity.class));
    }

    @Subscribe
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_LOAD_FRAGMENT:
                setCurrentTab(-1);
                //显示fragment
                String className = event.getMessage();

                Fragment aaa = null;
                try {
                    aaa = (Fragment) Class.forName(App.packageName + ".Fragment." + className).newInstance();
                } catch (Exception e) {
                }

                if (aaa != null) {
                    if (!TextUtils.isEmpty(event.getMessageSource())) {
                        Bundle arguments = new Bundle();
                        Map<String, Object> params = JSON.parseObject(event.getMessageSource(), Map.class);
                        for (Map.Entry<String, Object> entry : params.entrySet()) {
                            if (entry.getValue() instanceof Integer) {
                                arguments.putInt(entry.getKey(), (Integer) entry.getValue());
                            } else if (entry.getValue() instanceof String) {
                                arguments.putString(entry.getKey(), (String) entry.getValue());
                            }
                        }
                        aaa.setArguments(arguments);
                    }

                    showFragment(TEMP_DISPLAY_TAG, aaa);
                }

                break;
            case EVENT_OPEN_CART_TAB:
                setCurrentTab(3);
                break;
            case EVENT_LOGIN:
                if (App.getInstance().isOpenMessageActivity) {
                    openMessageActivity();
                    App.getInstance().isOpenMessageActivity = false;
                }
                break;
            case EVENT_OPEN_SHOP_STREET_TAB:
                setCurrentTab(2);
                break;
            case EVENT_OPEN_CATEGORY_TAB:
                setCurrentTab(1);
                break;
            case EVENT_OPEN_USER_TAB:
                setCurrentTab(4);
                break;
            case EVENT_ORDER_LIST:
                openOrderList();
                break;
            case EVENT_UNPAID_ORDER_LIST:
                openUnpaidOrderList();
                break;
            case EVENT_AWAIT_SHIP_ORDER_LIST:
                openUnshippedOrderList();
                break;
            case EVENT_UPDATE_CART_NUMBER:
                String cartNumber = App.getCartString();
                if ("99+".equals(App.getCartString())) {
                    mCartTextView.setTextSize(8);
                } else {
                    if (Integer.parseInt(App.getCartString()) >= 10) {
                        mCartTextView.setTextSize(8);
                    } else {
                        mCartTextView.setTextSize(10);
                    }
                }
                mCartTextView.setText(App.getCartString());
                break;
            case EVENT_OPEN_INDEX:
                setCurrentTab(0);
                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void hideCartBadgeView() {
        if (mCartTextView != null) {
            mCartTextView.setVisibility(View.INVISIBLE);
        }
    }

//    private void openCartFragment() {
//        if (mCartFragment == null) {
//            mCartFragment = new CartFragment();
//        } else {
//            mCartFragment.refresh();
//        }
//        showFragment(CART_FRAGMENT_TAG, mCartFragment);
//    }

    private void openCategoryFragment() {
        if (mCategoryFragment == null) {
            mCategoryFragment = new CategoryFragment();
        }

        showFragment(CATEGORY_FRAGMENT_TAG, mCategoryFragment);
        if (!mCategoryFragment.isLoadCompleted()) {
            mCategoryFragment.againLoadData();
        }
    }

    /***
     * 首页 fragment
     */
    private void openIndexFragment() {
        if (mIndexFragment == null) {
            mIndexFragment = new IndexFragment();

            transaction = getSupportFragmentManager().beginTransaction();
            safeCommit(transaction);
        }
        showFragment(INDEX_FRAGMENT_TAG, mIndexFragment);
    }


    /***
     * 分类 fragment
     */
    public void openSortFragment() {
        activity_root_tabWrapperLinearLayout.setVisibility(View.GONE);

        if (mCategoryFragment == null) {
            mCategoryFragment = new CategoryFragment();

            transaction = getSupportFragmentManager().beginTransaction();
            safeCommit(transaction);
        }

        showFragment(CATEGORY_FRAGMENT_TAG, mCategoryFragment);

        if (!mCategoryFragment.isLoadCompleted()) {
            mCategoryFragment.againLoadData();
        }
    }


    /***
     * 购物车 fragment
     */
    private void oepnCartFragment() {
        if (mCartFragment == null) {
            mCartFragment = new CartFragment();

            transaction = getSupportFragmentManager().beginTransaction();
            safeCommit(transaction);
        } else {
            mCartFragment.refresh();
        }
        showFragment(CART_FRAGMENT_TAG, mCartFragment);
    }

    /***
     * 我的 fragment
     */
    private void openUserFragment() {

        if (mUserFragment == null) {
            mUserFragment = new UserFragment();

            transaction = getSupportFragmentManager().beginTransaction();
            safeCommit(transaction);
        }
        showFragment(USER_FRAGMENT_TAG, mUserFragment);
    }


    /**
     * 同城生活 ac(fragment)
     **/
    private void openSameCity() {
        App.getInstance().isCityChanage = true;
        startActivity(new Intent(RootActivity.this, CityLifeActivity.class));
    }

    private void openOrderList() {
        setCurrentTab(4);
        mUserFragment.openOrderListActivity(Macro.ORDER_TYPE_ALL);
    }

    private void openUnpaidOrderList() {
        setCurrentTab(4);
        mUserFragment.openOrderListActivity(Macro.ORDER_TYPE_UNPAID);
    }

    private void openUnshippedOrderList() {
        setCurrentTab(4);
        mUserFragment.openOrderListActivity(Macro.ORDER_TYPE_AWAIT_SHIPPED);
    }

    public void setCurrentTab(int index) {
        switch (index) {
            case -1:
                activity_root_tabWrapperLinearLayout.setVisibility(View.VISIBLE);
                hideCartBadgeView();
                setTitle("");
                mActionBar.hide();
                updateTabSelectedStatus(index);
                break;
            case 0:
                updateTabSelectedStatus(index);
                activity_root_tabWrapperLinearLayout.setVisibility(View.VISIBLE);
                openIndexFragment();

                showCartBadgeView();
                setTitle("");
                mActionBar.hide();
                break;
            case 1:
                //openCategoryFragment();

                activity_root_tabWrapperLinearLayout.setVisibility(View.VISIBLE);
                if (App.getInstance().isLogin()) {//登录了
                    openMessageActivity();
                } else {//未登录
                    Intent mIntent = new Intent(RootActivity.this, LoginActivity.class);
                    startActivity(mIntent);
                }
                showCartBadgeView();
                setTitle("");
                mActionBar.hide();
                updateTabSelectedStatus(index);
                break;
            case 2:
                activity_root_tabWrapperLinearLayout.setVisibility(View.VISIBLE);
                openSameCity();

                showCartBadgeView();
                mActionBar.hide();
                updateTabSelectedStatus(index);
                break;
            case 3:
                //openCartFragment();
                activity_root_tabWrapperLinearLayout.setVisibility(View.VISIBLE);

                oepnCartFragment();

                hideCartBadgeView();
                setTitle("");
                mActionBar.hide();
                updateTabSelectedStatus(index);
                break;
            case 4:
                activity_root_tabWrapperLinearLayout.setVisibility(View.VISIBLE);
                if (App.getInstance().isLogin()) {
                    openUserFragment();

                    showCartBadgeView();
                    setTitle("");
                    mActionBar.hide();
                    updateTabSelectedStatus(index);
                } else {
                    openLoginActivityForResult(RequestCode.REQUEST_CODE_LOGIN_FOR_REFRESH);
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mIndexFragment != null) {
            mIndexFragment.onActivityResult(requestCode, resultCode, data);
        }

        try {
            RequestCode code = RequestCode.valueOf(requestCode);
            if (code != null) {
                switch (RequestCode.valueOf(requestCode)) {
                    case REQUEST_CODE_LOGIN_FOR_REFRESH:
                        if (resultCode == Activity.RESULT_OK) {
                            setCurrentTab(4);
                        }
                        break;
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {

        }
    }

    private void openLoginActivityForResult(final RequestCode requestCode) {
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivityForResult(intent, requestCode.getValue());
                return false;
            }
        }).sendEmptyMessageDelayed(0, 100);
    }

    private void showCartBadgeView() {
        if (mCartTextView != null) {
            mCartTextView.setVisibility(View.VISIBLE);
        }
    }

    //临时显示的fragment，也就是没有底部按钮的fragment
    private Fragment tempDisplayFragment = null;

    @SuppressLint("RestrictedApi")
    private void showFragment(String tag, Fragment showFragment) {
        boolean mExist = false;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (getSupportFragmentManager().getFragments() != null) {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                if (!fragment.getTag().equalsIgnoreCase(tag)) {
                    transaction.hide(fragment);
                } else {
                    //如果不等于临时显示的fragment，才算已经存在，
                    if (!fragment.getTag().equalsIgnoreCase(TEMP_DISPLAY_TAG)) {
                        showFragment = fragment;
                        mExist = true;
                    }
                }
            }
        }

        if (!mExist) {
            if (tag.equalsIgnoreCase(TEMP_DISPLAY_TAG)) {
                if (tempDisplayFragment != null) transaction.remove(tempDisplayFragment);
                tempDisplayFragment = showFragment;
            }
            transaction.add(R.id.activity_root_fragmentContainer, showFragment, tag);
        }
        transaction.show(showFragment);

        //        transaction.commitAllowingStateLoss();
        safeCommit(transaction);
    }


    private void startAnimation(final Drawable anim, final int startX, final int startY, final int endX, final int endY) {
        final View animationView = LayoutInflater.from(this).inflate(
                R.layout.activity_shop_animator1, null);
        ImageView im = (ImageView) animationView.findViewById(R.id.imageView_anim);
        im.setImageDrawable(anim);
        mContentView.addView(animationView);

        int picSize = Utils.dpToPx(im.getContext(), 50) / 2;

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(animationView, "X", startX - picSize, endX - picSize);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(animationView, "Y", startY - picSize, endY - picSize);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(animationView, "scaleX", 1f, 0.25f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(animationView, "scaleY", 1f, 0.25f);


        animatorY.setInterpolator(new AnticipateInterpolator(2.0f));
        animatorX.setInterpolator(new AccelerateInterpolator());

        scaleX.setInterpolator(new AnticipateInterpolator(2.0f));
        scaleY.setInterpolator(new AnticipateInterpolator(2.0f));


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(700);
        animatorSet.playTogether(animatorX, animatorY, scaleX, scaleY);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mContentView.removeView(animationView);
                startCartViewAnimation();
            }
        });
    }

    private void startAnimation(final int startX, final int startY, final int endX, final int
            endY) {
        final View animationView = LayoutInflater.from(this).inflate(R.layout
                .activity_shop_animator, mContentView, false);
        mContentView.addView(animationView);

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(animationView, "x", startX, endX);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(animationView, "y", startY, endY);
        animatorY.setInterpolator(new AnticipateInterpolator(2.0f));
        animatorX.setInterpolator(new AccelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(700);
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mContentView.removeView(animationView);
                startCartViewAnimation();
            }
        });
    }

    private void startCartViewAnimation() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mTabCart, "scaleX", 1.0f, 1.4f, 1.0f, 1.2f, 1.0f);
        animatorX.setDuration(200);
        animatorX.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorX.start();

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mTabCart, "scaleY", 1.0f, 1.4f, 1.0f, 1.2f, 1.0f);
        animatorY.setDuration(200);
        animatorY.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorY.start();
    }

    private boolean isTopHomePage = false;
    private int lastPage = 0;


    /**
     * 变换主页左下角图标
     *
     * @param bool
     */
    public void changeHomeTop(boolean bool) {
        isTopHomePage = bool;

        ViewGroup home_view = (ViewGroup) mTabs.get(0);
        ImageView homeImage = (ImageView) home_view.getChildAt(1);

        if (null != homeTableBarModel) {
            if (isTopHomePage) {
                if (!TextUtils.isEmpty(homeTableBarModel.getTabbar_home_bao())) {
                    Glide.with(this).load(Utils.urlOfImage(homeTableBarModel.getTabbar_home_bao())).into(homeImage);
                } else {
                    homeImage.setImageResource(R.mipmap.tab_home_index);
                }
            } else {
                if (!TextUtils.isEmpty(homeTableBarModel.getTabbar_home_up())) {
                    Glide.with(this).load(Utils.urlOfImage(homeTableBarModel.getTabbar_home_up())).into(homeImage);
                } else {
                    homeImage.setImageResource(R.mipmap.tab_home_backtotop);
                }
            }
        }


    }


    /**
     * 获取首页下面的tablebar相关图片
     */
    private void getTableBarImgaeView() {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest(Api.API_HOME_TABLE_BAR_URL, RequestMethod.GET);
        RequestAddHead.addNoHttpHead(request, this, Api.API_HOME_TABLE_BAR_URL, "GET");

        request.add("key", "android");
        requestQueue.add(HttpWhat.HTTP_HOME_TABLE_BAR.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.Companion.e("当前首页图标地址为" + response);
                if (response.responseCode() == 200) {
                    homeTableBarModel = GsonUtils.Companion.toObj(response.get(), HomeTableBarModel.class);
                    if (null != homeTableBarModel) {
                        if (!TextUtils.isEmpty(homeTableBarModel.getTabbar_background()) && !RootActivity.this.isDestroyed()) {
                            Glide.with(RootActivity.this)
                                    .load(Utils.setImgNetUrl("", homeTableBarModel.getTabbar_background()))
                                    .thumbnail(0.2f)
                                    .into(new ViewTarget<View, Drawable>(activity_root_tabWrapperLinearLayout) {
                                              @Override
                                              public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                                  this.view.setBackground(resource.getCurrent());
                                              }
                                          }
                                    );
                        }
                        for (int i = 0; i < 5; i++) {
                            DataModel.SiteNavListBean bean = new DataModel.SiteNavListBean();
                            switch (i) {
                                case 0:
                                    bean.nav_name = tabName[i];
                                    bean.nav_icon_active = homeTableBarModel.getTabbar_normal1().getChecked();
                                    bean.nav_icon = homeTableBarModel.getTabbar_normal1().getUnchecked();
                                    break;
                                case 1:
                                    bean.nav_name = tabName[i];
                                    bean.nav_icon_active = homeTableBarModel.getTabbar_normal2().getChecked();
                                    bean.nav_icon = homeTableBarModel.getTabbar_normal2().getUnchecked();
                                    break;
                                case 2:
                                    bean.nav_name = tabName[i];
                                    bean.nav_icon_active = homeTableBarModel.getTabbar_normal3().getChecked();
                                    bean.nav_icon = homeTableBarModel.getTabbar_normal3().getUnchecked();

                                    break;
                                case 3:
                                    bean.nav_name = tabName[i];
                                    bean.nav_icon_active = homeTableBarModel.getTabbar_normal4().getChecked();
                                    bean.nav_icon = homeTableBarModel.getTabbar_normal4().getUnchecked();

                                    break;
                                case 4:
                                    bean.nav_name = tabName[i];
                                    bean.nav_icon_active = homeTableBarModel.getTabbar_normal5().getChecked();
                                    bean.nav_icon = homeTableBarModel.getTabbar_normal5().getUnchecked();
                                    break;
                            }
                            navListBeens.add(bean);
                        }
                    }
                }

                setCurrentTab(0);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    private void updateTabSelectedStatus(int index) {
//        List<DataModel.SiteNavListBean> navListBeens= App.getInstance().site_nav_list;
//        navListBeens = null;

        if (null != navListBeens && navListBeens.size() > 0) {//不为空
            ViewGroup home_view = (ViewGroup) mTabs.get(0);
            LinearLayout homeView = (LinearLayout) home_view.getChildAt(0);
            ImageView homeImagetwo = (ImageView) home_view.getChildAt(1);
            TextView homeText = (TextView) ((LinearLayout) home_view.getChildAt(0)).getChildAt(1);
            ImageView homeimg = (ImageView) ((LinearLayout) home_view.getChildAt(0)).getChildAt(0);
            homeText.setText(navListBeens.get(0).nav_name);
            if (index == 0) {
                home_view.setSelected(true);
                homeView.setVisibility(View.GONE);
                homeImagetwo.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(navListBeens.get(0).nav_icon_active)) {
                    Glide.with(this).load(Utils.urlOfImage(navListBeens.get(0).nav_icon_active)).into(homeImagetwo);
                }
                if (mIndexFragment != null && lastPage == 0) {
                    if (isTopHomePage) {
                        //跳转到home页面的最后一个模板
                        mIndexFragment.jumpLastTemplate();
                    } else {
                        //返回顶部
                        mIndexFragment.jumpViewTop();
                    }
                    changeHomeTop(!isTopHomePage);
                } else {
                    changeHomeTop(isTopHomePage);
                }

            } else {
                home_view.setSelected(false);
                homeView.setVisibility(View.VISIBLE);
                homeImagetwo.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(navListBeens.get(0).nav_icon)) {
                    Glide.with(this).load(Utils.urlOfImage(navListBeens.get(0).nav_icon)).into(homeimg);
                }
            }

            for (int i = 1; i < mTabs.size(); i++) {
                ViewGroup view = (ViewGroup) mTabs.get(i);

                ImageView imageView = (ImageView) view.getChildAt(0);
                TextView textView = (TextView) view.getChildAt(1);

                if (index == i) {
                    view.setSelected(true);
                    DataModel.SiteNavListBean siteNavListBean = navListBeens.get(i);
                    if (siteNavListBean != null && !TextUtils.isEmpty(siteNavListBean.nav_icon_active)) {
                        //更改底部文字
                        textView.setText(siteNavListBean.nav_name);
                        Glide.with(this).load(Utils.urlOfImage(siteNavListBean.nav_icon_active)).into(imageView);
                    }
                } else {
                    view.setSelected(false);
                    DataModel.SiteNavListBean siteNavListBean = navListBeens.get(i);
                    if (siteNavListBean != null && !TextUtils.isEmpty(siteNavListBean.nav_icon)) {
                        //更改底部文字
                        textView.setText(siteNavListBean.nav_name);
                        Glide.with(this).load(Utils.urlOfImage(siteNavListBean.nav_icon)).into(imageView);
                    }
                }
            }
        } else {//为空

            ViewGroup home_view = (ViewGroup) mTabs.get(0);
            LinearLayout homeView = (LinearLayout) home_view.getChildAt(0);
            ImageView homeImagetwo = (ImageView) home_view.getChildAt(1);

            if (index == 0) {
                home_view.setSelected(true);
                homeView.setVisibility(View.GONE);
                homeImagetwo.setVisibility(View.VISIBLE);

                if (mIndexFragment != null && lastPage == 0) {
                    if (isTopHomePage) {
                        //跳转到home页面的最后一个模板
                        mIndexFragment.jumpLastTemplate();
                    } else {
                        //返回顶部
                        mIndexFragment.jumpViewTop();
                    }
                    changeHomeTop(!isTopHomePage);
                } else {
                    changeHomeTop(isTopHomePage);
                }

            } else {
                home_view.setSelected(false);
                homeView.setVisibility(View.VISIBLE);
                homeImagetwo.setVisibility(View.GONE);

            }
            for (int i = 1; i < mTabs.size(); i++) {
                ViewGroup view = (ViewGroup) mTabs.get(i);

                ImageView imageView = (ImageView) view.getChildAt(0);
                TextView textView = (TextView) view.getChildAt(1);
                if (index == i) {
                    view.setSelected(true);
                    if (navListBeens != null && navListBeens.size() > i) {
                        DataModel.SiteNavListBean siteNavListBean = navListBeens.get(i);
                        //更改底部文字
                        textView.setText(siteNavListBean.nav_name);

                        if (!TextUtils.isEmpty(siteNavListBean.nav_icon_active)) {
                            final ImageView finalImageView = imageView;
                            ImageLoader.loadImage(Utils.urlOfImage(siteNavListBean.nav_icon_active), new SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                    finalImageView.setImageBitmap(loadedImage);
                                }
                            });
                        }
                    }
                } else {
                    view.setSelected(false);
                    if (navListBeens != null && navListBeens.size() > i) {
                        DataModel.SiteNavListBean siteNavListBean = navListBeens.get(i);
                        //更改底部文字
                        textView.setText(siteNavListBean.nav_name);

                        if (!TextUtils.isEmpty(siteNavListBean.nav_icon)) {
                            final ImageView finalImageView1 = imageView;
                            ImageLoader.loadImage(Utils.urlOfImage(siteNavListBean.nav_icon), new SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                    finalImageView1.setImageBitmap(loadedImage);
                                }
                            });
                        }
                    }
                }
            }
        }

        lastPage = index;
    }


    @Override
    public void getIngotView(TextView textView) {
        userIngotNumberView = textView;
    }

    /**
     * 定位 权限申请
     */
    public void getLocationPermis() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION);
        } else {
            //已申请
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                //已拒绝定位权限申请
                Toast.makeText(mContext, "定位权限申请已拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppPreference.getIntance().setDoubleHome(false);
    }
}
