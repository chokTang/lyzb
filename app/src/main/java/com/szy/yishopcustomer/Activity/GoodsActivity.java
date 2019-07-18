package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gxz.PagerSlidingTabStrip;
import com.hyphenate.easeui.EaseConstant;
import com.lyzb.jbx.R;
import com.lyzb.jbx.util.AppPreference;
import com.lyzb.jbx.webscoket.BaseClient;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Activity.im.ImCommonActivity;
import com.szy.yishopcustomer.Adapter.ItemTitlePagerAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Fragment.GoodsDetailFragment;
import com.szy.yishopcustomer.Fragment.GoodsImageDetailFragment;
import com.szy.yishopcustomer.Fragment.GoodsIndexFragment;
import com.szy.yishopcustomer.Fragment.GoodsIndexImageDetailFragment;
import com.szy.yishopcustomer.Fragment.GoodsReviewFragment;
import com.szy.yishopcustomer.ResponseModel.Goods.ShopInfoModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.JSONUtil;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.Util.im.ImHelper;
import com.szy.yishopcustomer.View.MenuPopwindow;
import com.szy.yishopcustomer.View.NoScrollViewPager;
import com.szy.yishopcustomer.ViewModel.im.ImHeaderGoodsModel;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by liuzhifeng on 2017/2/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsActivity extends YSCBaseActivity implements GoodsIndexFragment.OnClickListener {
    public NoScrollViewPager mScrollView;
    public PagerSlidingTabStrip mTabView;
    public TextView mTitleTextView;

    private GoodsIndexFragment goodsInfoFragment;
    private GoodsDetailFragment goodsDetailFragment;
    private GoodsReviewFragment goodsCommentFragment;

    private List<Fragment> fragmentList = new ArrayList<>();
    private ItemTitlePagerAdapter adapter;


    public static LinearLayout mTelButton;
    public static ImageView mTelImage;
    public static TextView mTelText;
    public static LinearLayout mIndexButton;
    public static LinearLayout mCollectButton;
    public static ImageView mCollectImage;

    /****
     * 底部操作栏
     *
     */
    public static View doBuyView;

    /**
     * 加入购物车 布局
     */
    public static LinearLayout mAddToCartButton;
    /**
     * 加入购物车 文本
     */
    public static TextView mButtonOne;

    /**
     * 立即购买 布局
     */
    public static LinearLayout mBuyNowButton;
    /**
     * 立即购买 文本
     */
    public static TextView mButtonTwo;


    public static TextView mUnableToBuyLayout;//立即登录
    public static TextView mUnableToBuyButton;
    public static TextView mCollectTip;//收藏

    private String uuid;
    Toolbar toolbar;
    private final static int REQUEST_GOOD_SERVICE = 0x1463;//客户-登录

    public String getUUID() {
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
        }

        return uuid;
    }

    private void initBottomLayout() {
        mTelButton = (LinearLayout) findViewById(R.id.fragment_goods_tel_button);
        mTelImage = (ImageView) findViewById(R.id.img_service_tel);
        mTelText = (TextView) findViewById(R.id.tv_service_tel);
        mIndexButton = (LinearLayout) findViewById(R.id.fragment_goods_index_button);
        mCollectButton = (LinearLayout) findViewById(R.id.fragment_goods_collect_button);
        mCollectImage = (ImageView) findViewById(R.id.tab_star);

        doBuyView = findViewById(R.id.view_goods_do_buy);

        mAddToCartButton = (LinearLayout) findViewById(R.id.fragment_goods_add_to_cart_button);
        mButtonOne = (TextView) findViewById(R.id.fragment_goods_add_to_cart_button_one);
        mBuyNowButton = (LinearLayout) findViewById(R.id.fragment_goods_buy_now_button);
        mButtonTwo = (TextView) findViewById(R.id.fragment_goods_add_to_cart_button_two);
        mUnableToBuyLayout = (TextView) findViewById(R.id.fragment_goods_buy_disable_buttonLayout);
        mCollectTip = (TextView) findViewById(R.id.textView14);
        mTelButton.setOnClickListener(this);
        mIndexButton.setOnClickListener(this);
        mCollectButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (goodsInfoFragment != null && goodsInfoFragment.mResponseGoodsModel != null && goodsInfoFragment.mResponseGoodsModel.data != null) {
            ShopInfoModel shopInfo = goodsInfoFragment.mResponseGoodsModel.data.shop_info;
            if (Utils.isDoubleClick()) {
                return;
            }
            switch (v.getId()) {
                case R.id.fragment_goods_tel_button:
//                    if (goodsInfoFragment.mResponseGoodsModel != null && shopInfo.aliim_enable
//                            .equals("1")) {
//                        goodsInfoFragment.openServiceActivity();
//                    } else {
//                        if (!Utils.isNull(shopInfo.shop.service_tel)) {
//                            Utils.openPhoneDialog(this, shopInfo.shop.service_tel);
//                        } else {
//                            Toast.makeText(this, "商家未填写电话", Toast.LENGTH_SHORT).show();
//                        }
//                    }
                    if (!App.getInstance().isLogin()) {//未登录
                        Intent intent = new Intent(GoodsActivity.this, LoginActivity.class);
                        startActivityForResult(intent, REQUEST_GOOD_SERVICE);
                    } else {
                        onOpenImActivity();
                    }
                    break;
                case R.id.fragment_goods_index_button:
                    goodsInfoFragment.openShopActivity(shopInfo.shop.shop_id);
                    break;
                case R.id.fragment_goods_collect_button:
                    goodsInfoFragment.collectGoods(goodsInfoFragment.goods_id, goodsInfoFragment
                            .sku_id);
                    break;
                default:
                    ViewType viewType = Utils.getViewTypeOfTag(v);
                    int position = Utils.getPositionOfTag(v);
                    int extra = Utils.getExtraInfoOfTag(v);
                    switch (viewType) {
                    }
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);

        initToolbar();
        mTabView = (PagerSlidingTabStrip) findViewById(R.id.activity_goods_tab);
        mScrollView = (NoScrollViewPager) findViewById(R.id.activity_goods_noScrollViewPager);
        mTitleTextView = (TextView) findViewById(R.id.activity_goods_title);

        fragmentList.add(goodsInfoFragment = new GoodsIndexFragment());
        fragmentList.add(goodsDetailFragment = new GoodsDetailFragment());
        fragmentList.add(goodsCommentFragment = new GoodsReviewFragment());

        GoodsImageDetailFragment mGoodsImageDetailFragment = new GoodsImageDetailFragment();
        GoodsIndexImageDetailFragment mGoodsIndexImageDetailFragment = new
                GoodsIndexImageDetailFragment();

        mScrollView.setAdapter(adapter = new ItemTitlePagerAdapter(getSupportFragmentManager(),
                fragmentList, new String[]{"商品", "详情", "评价"}));
        mScrollView.setOffscreenPageLimit(3);

        mTabView.setViewPager(mScrollView);
        goodsInfoFragment.setOnClickListener(this);
        if (mGoodsIndexImageDetailFragment.getArguments() == null) {
            mGoodsIndexImageDetailFragment.setArguments(getIntent().getExtras());
        } else {
            mGoodsIndexImageDetailFragment.getArguments().putAll(getIntent().getExtras());
        }
        if (mGoodsImageDetailFragment.getArguments() == null) {
            mGoodsImageDetailFragment.setArguments(getIntent().getExtras());
        } else {
            mGoodsImageDetailFragment.getArguments().putAll(getIntent().getExtras());
        }

        Bundle arguments = getIntent().getExtras();
        String id =  arguments.getString(Key.KEY_GOODS_ID.getValue());

        if (goodsInfoFragment.getArguments() == null) {
            goodsInfoFragment.setArguments(getIntent().getExtras());
        } else {
            goodsInfoFragment.getArguments().putAll(getIntent().getExtras());
        }
        if (goodsDetailFragment.getArguments() == null) {
            goodsDetailFragment.setArguments(getIntent().getExtras());
        } else {
            goodsDetailFragment.getArguments().putAll(getIntent().getExtras());
        }
        if (goodsCommentFragment.getArguments() == null) {
            goodsCommentFragment.setArguments(getIntent().getExtras());
        } else {
            goodsCommentFragment.getArguments().putAll(getIntent().getExtras());
        }

        initBottomLayout();
    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.activity_goods_toolbar);
        toolbar.setBackgroundResource(R.drawable.toolbar_bottom_border);
        toolbar.setNavigationIcon(R.mipmap.btn_back_dark);//设置返回
        if (App.getCartNumber() > 0) {
            toolbar.inflateMenu(R.menu.activity_base_goods_custem2);//设置右上角的填充菜单
        } else {
            toolbar.inflateMenu(R.menu.activity_base_goods_custem);//设置右上角的填充菜单
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsActivity.this.finish();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();

                switch (menuItemId) {
                    case R.id.activity_base_moreMenu:
                        mCustemMenuStyle = MenuPopwindow.MENU_STYLE_BASE;
                        initCustemMenu();
                        menuPopwindow.showPopupWindow(GoodsActivity.this.getWindow().getDecorView
                                ());
                        break;
                    case R.id.activity_base_cartMenu:
                        openCartActivity();
                        break;
                }
                return true;
            }
        });
    }


    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_UPDATE_CART_NUMBER:
                toolbar.getMenu().clear();
                if (App.getCartNumber() > 0) {
                    toolbar.inflateMenu(R.menu.activity_base_goods_custem2);//设置右上角的填充菜单
                } else {
                    toolbar.inflateMenu(R.menu.activity_base_goods_custem);//设置右上角的填充菜单
                }
                break;
        }
    }

/*    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.activity_base_cartMenu).setVisible(false);
        menu.findItem(R.id.activity_base_cartNumMenu).setVisible(true);
        menu.findItem(R.id.activity_base_moreMenu).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }*/

    private void openCartActivity() {
        startActivity(new Intent().setClass(this, CartActivity.class));
    }

    @Override
    public void onClickListener(String id) {
        mScrollView.setCurrentItem(2);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //客户登录状态返回
            case REQUEST_GOOD_SERVICE:
                if (resultCode == Activity.RESULT_OK) {

                    /***
                     * 用户账号登录成功后的回调
                     * 环信IM登录可能会造成延迟 未拿到IM的相关数据
                     *
                     */

                    String im_user_name = SharedPreferencesUtils.getParam(GoodsActivity.this, Key.IM_USERNAME.name(), "").toString();
                    String im_user_pwd = SharedPreferencesUtils.getParam(GoodsActivity.this, Key.IM_USERPASSWORD.name(), "").toString();


                    if (TextUtils.isEmpty(im_user_name) || TextUtils.isEmpty(im_user_pwd)) {
                        //IM登录
                        loginIm(App.getInstance().userId);
                    } else {
                        onOpenImActivity();
                    }
                }
                break;
        }
    }

    /**
     * 获取商户信息
     */
    private final void onOpenImActivity() {

        CommonRequest request = new CommonRequest(Api.API_CITY_IM_GET_IMINFO, HttpWhat.HTTP_IM_INFO.getValue(), RequestMethod.POST);
        RequestAddHead.addHead(request, GoodsActivity.this, Api.API_CITY_IM_GET_IMINFO, "POST");
        JSONObject object = new JSONObject();
        try {
            object.put("userName", goodsInfoFragment.mResponseGoodsModel.data.shop_info.kf.user_id);
            object.put("extType", "shop");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.setDefineRequestBodyForJson(object);
        addRequest(request);
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            //获取IM商户信息
            case HTTP_IM_INFO:
                JSONObject infoObject = JSONUtil.toJsonObject(response);
                int infoStatus = JSONUtil.get(infoObject, "status", 0);
                if (infoStatus == 200) {
                    String nickName = JSONUtil.get(infoObject, "nickName", "");
                    String userName = JSONUtil.get(infoObject, "userName", "");
                    String headimg = JSONUtil.get(infoObject, "headimg", "");

                    ImHeaderGoodsModel goodsModel = new ImHeaderGoodsModel();
                    goodsModel.setChatType(EaseConstant.CHATTYPE_SINGLE);

                    goodsModel.setGoodPrice(goodsInfoFragment.mResponseGoodsModel.data.goods.goods_price);
                    goodsModel.setGoodsName(goodsInfoFragment.mResponseGoodsModel.data.goods.goods_name);
                    goodsModel.setImageUrl(Utils.urlOfImage(goodsInfoFragment.mResponseGoodsModel.data.goods.goods_image));
                    goodsModel.setGoodsId(goodsInfoFragment.mResponseGoodsModel.data.goods.goods_id);
                    goodsModel.setShopImName(userName);
                    goodsModel.setShopName(goodsInfoFragment.mResponseGoodsModel.data.shop_info.shop.shop_name);
                    goodsModel.setShopHeadimg(headimg);
                    goodsModel.setShopId(goodsInfoFragment.mResponseGoodsModel.data.shop_info.shop.shop_id);
                    Intent intent = new Intent(this, ImCommonActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable(ImCommonActivity.PARAMS_GOODS, goodsModel);
                    intent.putExtras(args);
                    startActivity(intent);
                } else {
                    toast(JSONUtil.get(infoObject, "msg", ""));
                }
                break;
        }
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        toast(response);
    }

    public void loginIm(String userId) {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_CITY_IM_GET_IMINFO, RequestMethod.POST);
        RequestAddHead.addNoHttpHead(request, this, Api.API_CITY_IM_GET_IMINFO, "POST");
        com.alibaba.fastjson.JSONObject object = new com.alibaba.fastjson.JSONObject();
        object.put("userName", userId);
        request.setDefineRequestBodyForJson(object.toJSONString());
        requestQueue.add(HttpWhat.HTTP_IM_USERINFO.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                com.alibaba.fastjson.JSONObject object = com.alibaba.fastjson.JSONObject.parseObject(response.get());

                if (Integer.valueOf(object.getString("status")) == 200) {
                    String nickName = object.getString("nickName");
                    final String userName = object.getString("userName");
                    String password = object.getString("password");
                    String uuid = object.getString("uuid");
                    String headimg = object.getString("headimg");

                    SharedPreferencesUtils.setParam(App.getInstance().mContext, Key.IM_USERNAME.name(), userName);
                    EaseConstant.CHAT_USER_ID = userName;

                    SharedPreferencesUtils.setParam(App.getInstance().mContext, Key.IM_USERPASSWORD.name(), password);
                    SharedPreferencesUtils.setParam(App.getInstance().mContext, Key.IM_USERUUID.name(), uuid);
                    SharedPreferencesUtils.setParam(App.getInstance().mContext, Key.IM_USERNICK.name(), nickName);
                    SharedPreferencesUtils.setParam(App.getInstance().mContext, Key.IM_USERHEADING.name(), headimg);
                    AppPreference.getIntance().setHxHeaderImg(headimg);
                    AppPreference.getIntance().setHxNickName(nickName);

                    ImHelper.getIntance().onLogin(userName, password, null);

                } else {
                    Log.d("wyx", "im信息获取异常-");
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
}