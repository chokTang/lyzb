package com.szy.yishopcustomer.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.*;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.easeui.EaseConstant;
import com.szy.common.Fragment.CommonFragment;
import com.szy.common.Interface.SimpleImageLoadingListener;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.Util.ImageLoader;
import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Activity.im.ImCommonActivity;
import com.szy.yishopcustomer.Adapter.ShopRedPacketAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Fragment.ShopGoodsListFragment;
import com.szy.yishopcustomer.Fragment.ShopIndexFragment;
import com.szy.yishopcustomer.Fragment.ShopInfoFragment;
import com.szy.yishopcustomer.Interface.CartAnimationMaker;
import com.szy.yishopcustomer.Interface.ListStyleObserver;
import com.szy.yishopcustomer.Interface.OnLoginListener;
import com.szy.yishopcustomer.Interface.ScrollObservable;
import com.szy.yishopcustomer.Interface.ScrollObserver;
import com.szy.yishopcustomer.Interface.ServiceProvider;
import com.szy.yishopcustomer.Interface.SimpleAnimatorListener;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.VideoModel;
import com.szy.yishopcustomer.ResponseModel.Follow.ResponseFollowModel;
import com.szy.yishopcustomer.ResponseModel.Shop.ResponseShopModel;
import com.szy.yishopcustomer.ResponseModel.ShopBonusListModel;
import com.szy.yishopcustomer.ResponseModel.ShopIndex.ResponseShopIndexModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.JSONUtil;
import com.szy.yishopcustomer.Util.PermissionUtils;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.MenuPopwindow;
import com.szy.yishopcustomer.ViewModel.EmptyItemModel;
import com.szy.yishopcustomer.ViewModel.ShopCartModel;
import com.szy.yishopcustomer.ViewModel.im.ImHeaderGoodsModel;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.PERMISSION_CODE;

/**
 * Created by 宗仁 on 2016/7/1.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopActivity extends YSCBaseActivity implements ServiceProvider, CartAnimationMaker,
        TextView.OnEditorActionListener, PopupMenu.OnMenuItemClickListener, ScrollObserver {

    private static final String writePermission = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static final int HTTP_WHAT_CART_BOX_GOODS_LIST = 2;
    private static final int HTTP_WHAT_FOLLOW = 0;
    private static final int HTTP_WHAT_SHOP_INFO = 1;
    private static final int HTTP_GO_CHECKOUT = 3;
    private static final int HTTP_WHAT_BONUS_LIST = 4;
    private static final int HTTP_RECEIVE_BONUS = 5;
    private static final int HTTP_WHAT_VIDEO_LIST = 6;

    private static final int HTTP_IM_INFO = 8;

    private static final int REQUEST_CODE_LOGIN = 0;
    private static final int REQUEST_CODE_LOGIN_FOR_COLLECT_SHOP = 2;


    private int mposition = 0;

    @BindView(R.id.activity_shop_collection)
    LinearLayout mCollection;
    @BindView(R.id.activity_shop_detail)
    TextView mArticle;
    @BindView(R.id.activity_shop_detail1)
    TextView mArticle1;
    @BindView(R.id.activity_shop_share_two)
    LinearLayout mShare;
    @BindView(R.id.activity_shop_imageView)
    CircleImageView mTitleImageView;
    @BindView(R.id.activity_shop_info_wrapper)
    RelativeLayout mWrapperLayout;
    @BindView(R.id.activity_shop_info_wrapper_two)
    RelativeLayout mWrapperLayoutTwo;
    @BindView(R.id.activity_shop_contentView)
    ViewGroup mContentView;
    //    @BindView(R.id.linearLayout)
//    LinearLayout mLinearLayout;
    @BindView(R.id.activity_shop_fans_number)
    TextView mFansNumber;
    @BindView(R.id.activity_shop_toolbarWrapper)
    View mToolbarWrapper;
    @BindView(R.id.activity_shop_backImageButton)
    ImageButton mBackImageButton;
    @BindView(R.id.activity_shop_commonEditText)
    CommonEditText mCommonEditText;
    @BindView(R.id.activity_shop_shareImageButton)
    ImageButton mShareImageButton;
    @BindView(R.id.activity_shop_moreImageButton)
    ImageButton mMoreImageButton;
    @BindView(R.id.activity_shop_logoImageView)
    ImageView mLogoImageView;
    @BindView(R.id.activity_shop_topImageView)
    ImageView mTopImageView;
    @BindView(R.id.activity_shop_nameTextView)
    TextView mNameTextView;
    @BindView(R.id.activity_shop_name)
    TextView mShopNameTextView;
    @BindView(R.id.activity_shop_followWrapper)
    View mFollowWrapper;
    @BindView(R.id.activity_shop_followImageView)
    ImageView mFollowImageView;
    @BindView(R.id.activity_shop_followTextView)
    TextView mFollowTextView;
    //    @BindView(R.id.activity_shop_tabTopIndexWrapper)
//    View mTabTopIndexWrapper;

    /**
     * 店铺页面-店铺首页
     */
    @BindView(R.id.activity_shop_tabBottomIndexWrapper)
    View mTabBottomIndexWrapper;
    //    @BindView(R.id.activity_shop_tabIndexImageView)
//    ImageView mTabIndexImageView;
    @BindView(R.id.activity_shop_tabIndexTextView)
    TextView mTabIndexTextView;
    //    @BindView(R.id.activity_shop_tabTopCategoryWrapper)
//    View mTabTopCategoryWrapper;

    /**
     * 店铺页面-全部商品
     */
    @BindView(R.id.activity_shop_tabBottomCategoryWrapper)
    View mTabBottomCategoryWrapper;
    //    @BindView(R.id.activity_shop_tabCategoryTopTextView)
//    TextView mTabCategoryTopTextView;
    @BindView(R.id.activity_shop_tabCategoryBottomTextView)
    TextView mTabCategoryBottomTextView;
    //    @BindView(R.id.activity_shop_tabTopInfoWrapper)
//    View mTabTopInfoWrapper;

    /**
     * 店铺页面-店铺详情
     */
    @BindView(R.id.activity_shop_tabBottomInfoWrapper)
    View mTabBottomInfoWrapper;
    //    @BindView(R.id.activity_shop_tabInfoImageView)
//    ImageView mTabInfoImageView;
    @BindView(R.id.activity_shop_tabInfoTextView)
    TextView mTabInfoTextView;

    @BindView(R.id.activity_shop_cartWrapperOne)
    View mCartWrapperOne;
    @BindView(R.id.activity_shop_cartWrapperTwo)
    View mCartWrapperTwo;
    @BindView(R.id.fragment_shop_goods_list_cartNumberTextView)
    TextView mCartNumberTextView;
    @BindView(R.id.fragment_shop_goods_list_checkoutButton)
    Button mCheckoutButton;
    @BindView(R.id.fragment_shop_goods_list_cartImageView)
    ImageView mCartImageView;

    @BindView(R.id.activity_shop_appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.coordinatorLayout)
    View coordinatorLayout;

    boolean isTitleTop = true;
    int toolbarHeight = 0;

    @BindView(R.id.textView_collection)
    TextView textView_collection;
    @BindView(R.id.textView_info)
    TextView textView_info;
    @BindView(R.id.imageView_empty_cart)
    ImageView imageView_empty_cart;
    @BindView(R.id.fragment_shop_qrcode)
    View fragment_shop_qrcode;

    @BindView(R.id.activity_shop_tab_red_packet)
    View activity_shop_tab_red_packet;
    @BindView(R.id.recyclerViewRedPacket)
    RecyclerView recyclerViewRedPacket;
    @BindView(R.id.activity_shop_tab_bottom_wrapper)
    LinearLayout activity_shop_tab_bottom_wrapper;

    @BindView(R.id.imageViewSearch)
    View imageViewSearch;

    private ArrayList<String> shareData = new ArrayList();
    private Class<Fragment>[] mFragments = new Class[]{ShopIndexFragment.class,
            ShopGoodsListFragment.class, ShopInfoFragment.class};
    private List<Bundle> mFragmentParameters;
    private Map<String, Fragment> mFragmentMap;
    private List<List<View>> mTabViews;
    public String mShopId;
    public String mCategory = "";
    private String mShopAllGoods;
    private ResponseShopModel mShopModel;
    private OnLoginListener mLoginListener;
    private ImageView mQrCodeImageView;
    private Integer mTeampFansNumber;
    private Bundle fragmentParameter;
    private String videoDataString;

    private ResponseShopIndexModel mShopIndexModel = new ResponseShopIndexModel();

    @Override
    public void makeAnimation(Drawable makeAnimation, int x, int y) {

        if (x == 0 && y < 0) {
            return;
        }

        int[] endLocation = new int[2];
        mCartWrapperTwo.getLocationInWindow(endLocation);
        endLocation[0] += mCartWrapperTwo.getWidth() / 2;
        startAnimation(makeAnimation, x, y, endLocation[0], endLocation[1]);
    }

    @Override
    public void onClick(View view) {
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        int extraInfo = Utils.getExtraInfoOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_ARTICLE:
                openShopArticleActivity();
                break;
            case VIEW_TYPE_CLOSE:
                finish();
                break;
            /**店铺主页分享按钮*/
            case VIEW_TYPE_SHARE:
                openShareActivity();
                break;
            case VIEW_TYPE_MORE:
                openPopMenu();
                break;
            case VIEW_TYPE_TAB:
                mposition = position;
                if (PermissionUtils.hasPermission(this, writePermission)) {
                    showTab(position);
                } else {
                    PermissionUtils.requestPermission(this, PERMISSION_CODE, writePermission);
                }
                break;
            case VIEW_TYPE_SERVICE:
                openServiceActivity();
                break;
            case VIEW_TYPE_FOLLOW:
                follow();
                break;
            case VIEW_TYPE_CART:
                openCartActivity();
                break;
            case VIEW_TYPE_CHECKOUT:
                goCheckOut();
                break;
            case VIEW_TYPE_QR_CODE:
                openScanActivity();
                break;
            case VIEW_TYPE_SHOP_BONUS_USED:
                //使用红包
                openGoodsListActivity();
                break;
            case VIEW_TYPE_SHOP_BONUS_RECIVE:
                //领取红包
                receiveBonus(extraInfo + "");
                break;
        }
    }

    private void openGoodsListActivity() {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), mShopId);
        intent.setClass(this, GoodsListActivity.class);
        startActivity(intent);
    }

    private void receiveBonusCallBack(String response) {
        ResponseCommonModel model = JSON.parseObject(response, ResponseCommonModel.class);
        HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
            @Override
            public void onSuccess(ResponseCommonModel model) {
                toast(model.message);
                getBonusList();
            }
        }, true);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_CODE:
                showTab(mposition);
                break;
        }
    }

    private void receiveBonus(String bonusId) {
        CommonRequest mRequest = new CommonRequest(Api.API_RECEIVE_BONUS, HTTP_RECEIVE_BONUS, RequestMethod.POST);
        mRequest.add("bonus_id", bonusId);
        //mRequest.setAjax(true);
        addRequest(mRequest);
    }

    private void openScanActivity() {
        Intent intent = new Intent();
        intent.setClass(this, ScanActivityTwo.class);
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), mShopId);
        intent.putExtra(Key.KEY_SHOP_NAME.getValue(), mNameTextView.getText().toString());
        startActivity(intent);
    }


    private void openShopArticleActivity() {
        Intent intent = new Intent();
        intent.setClass(this, ShopArticleActivity.class);
        intent.putExtra(Key.KEY_SHOP_MODEL.getValue(), mShopModel.data.shop_info.shop);
        startActivity(intent);
    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_UPDATE_CART_NUMBER:
                updateCartView();
                break;
            case EVENT_SHOP_CART_UPDATE:
                updateCart();
                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_LOGIN:
                if (resultCode == RESULT_OK) {
                    if (mLoginListener != null) {
                        mLoginListener.onLogin();
                    }
                }
                break;
            case REQUEST_CODE_LOGIN_FOR_COLLECT_SHOP:
                if (resultCode == Activity.RESULT_OK) {
                    follow();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_SHOP_INFO:
                break;
            case HTTP_WHAT_FOLLOW:
                break;
            default:
                super.onRequestFailed(what, response);
                break;
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_FOLLOW:
                followSucceed(response);
                break;
            case HTTP_WHAT_SHOP_INFO:
                refreshSucceed(response);
                break;
            case HTTP_WHAT_BONUS_LIST:
                getBonusListSucceed(response);
                break;
            case HTTP_WHAT_VIDEO_LIST:
                requestVideoCallback(response);
                break;
            case HTTP_WHAT_CART_BOX_GOODS_LIST:
                updateCartSucceed(response);
                break;
            case HTTP_GO_CHECKOUT:
                goCheckOutCallBack(response);
                mCheckoutButton.setEnabled(true);
                break;
            case HTTP_RECEIVE_BONUS:
                receiveBonusCallBack(response);
                break;
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
                    goodsModel.setShopId(mShopModel.data.shop_info.shop.shop_id);
                    goodsModel.setShopImName(userName);
                    goodsModel.setShopName(mShopModel.data.shop_info.shop.shop_name);
                    goodsModel.setShopHeadimg(headimg);
                    Intent intent = new Intent(this, ImCommonActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable(ImCommonActivity.PARAMS_GOODS, goodsModel);
                    intent.putExtras(args);
                    startActivity(intent);
                } else {
                    toast(JSONUtil.get(infoObject, "msg", ""));
                }
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private void goCheckOutCallBack(String response) {
        HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {

            ResponseCommonModel responseModel;

            @Override
            public void getObj(ResponseCommonModel back) {
                responseModel = back;
            }

            @Override
            public void onSuccess(ResponseCommonModel back) {
                openCheckoutActivity();
            }

            @Override
            public void onLogin() {
                toast(responseModel.message);
                openLoginActivity();
            }

        }, true);
    }

    public void openLoginActivity() {
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        startActivity(intent);
    }

    private void openCheckoutActivity() {
        Intent intent = new Intent();
        intent.setClass(this, CheckoutActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //        getMenuInflater().inflate(R.menu.activity_shop, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected CommonFragment createFragment() {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.activity_shop;
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent == null) {
            Toast.makeText(this, R.string.pleaseEnterShopId, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        mShopId = intent.getStringExtra(Key.KEY_SHOP_ID.getValue());
        mCategory = intent.getStringExtra(Key.KEY_CATEGORY.getValue());

        if (mShopId == null || mShopId.equals("0")) {
            Toast.makeText(this, R.string.pleaseEnterShopId, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Utils.setViewTypeForTag(fragment_shop_qrcode, ViewType.VIEW_TYPE_QR_CODE);
        fragment_shop_qrcode.setOnClickListener(this);

        Utils.setViewTypeForTag(mBackImageButton, ViewType.VIEW_TYPE_CLOSE);
        mBackImageButton.setOnClickListener(this);
        Utils.setViewTypeForTag(mShareImageButton, ViewType.VIEW_TYPE_SHARE);
        mShareImageButton.setOnClickListener(this);
        Utils.setViewTypeForTag(mShare, ViewType.VIEW_TYPE_SHARE);
        mShare.setOnClickListener(this);
        Utils.setViewTypeForTag(mMoreImageButton, ViewType.VIEW_TYPE_MORE);
        mMoreImageButton.setOnClickListener(this);
        Utils.setViewTypeForTag(mCommonEditText, ViewType.VIEW_TYPE_SEARCH);
        mCommonEditText.setOnEditorActionListener(this);

        Utils.setViewTypeForTag(mFollowWrapper, ViewType.VIEW_TYPE_FOLLOW);
        mFollowWrapper.setOnClickListener(this);
        Utils.setViewTypeForTag(mCollection, ViewType.VIEW_TYPE_FOLLOW);
        mCollection.setOnClickListener(this);

        Utils.setViewTypeForTag(mCartImageView, ViewType.VIEW_TYPE_CART);
        mCartImageView.setOnClickListener(this);

        Utils.setViewTypeForTag(mArticle, ViewType.VIEW_TYPE_ARTICLE);
        mArticle.setOnClickListener(this);
        Utils.setViewTypeForTag(mArticle1, ViewType.VIEW_TYPE_ARTICLE);
        mArticle1.setOnClickListener(this);
        Utils.setViewTypeForTag(mNameTextView, ViewType.VIEW_TYPE_ARTICLE);
        mNameTextView.setOnClickListener(this);
        Utils.setViewTypeForTag(mShopNameTextView, ViewType.VIEW_TYPE_ARTICLE);
        mShopNameTextView.setOnClickListener(this);

        Utils.setViewTypeForTag(mCheckoutButton, ViewType.VIEW_TYPE_CHECKOUT);
        mCheckoutButton.setOnClickListener(this);

        mCartWrapperOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mShopAllGoods = intent.getStringExtra(Key.KEY_SHOP_ALL_GOODS.getValue());
        mFragmentMap = new HashMap<>();

        mTabViews = new ArrayList<>();
        List<View> tabViews = new ArrayList<>();
//        tabViews.add(mTabTopIndexWrapper);
        tabViews.add(mTabBottomIndexWrapper);
//        tabViews.add(mTabIndexImageView);
        tabViews.add(mTabIndexTextView);
        mTabViews.add(tabViews);

        tabViews = new ArrayList<>();
//        tabViews.add(mTabTopCategoryWrapper);
        tabViews.add(mTabBottomCategoryWrapper);
//        tabViews.add(mTabCategoryTopTextView);
        tabViews.add(mTabCategoryBottomTextView);
        mTabViews.add(tabViews);

        tabViews = new ArrayList<>();
//        tabViews.add(mTabTopInfoWrapper);
        tabViews.add(mTabBottomInfoWrapper);
//        tabViews.add(mTabInfoImageView);
        tabViews.add(mTabInfoTextView);
        mTabViews.add(tabViews);

        for (int i = 0; i < mTabViews.size(); i++) {
            for (View view : mTabViews.get(i)) {
                Utils.setViewTypeForTag(view, ViewType.VIEW_TYPE_TAB);
                Utils.setPositionForTag(view, i);
                view.setOnClickListener(this);
            }
        }

        mFragmentParameters = new ArrayList<>();

        fragmentParameter = new Bundle();
        mFragmentParameters.add(fragmentParameter);

        fragmentParameter = new Bundle();
        fragmentParameter.putString(Key.KEY_REQUEST_SHOP_ID.getValue(), mShopId);
        mFragmentParameters.add(fragmentParameter);

        /***
         * TOOD 店铺主页 默认 全部商品
         */
        showTab(1);

        if (!Utils.isNull(mShopAllGoods)) {
            showTab(1);
        } else if (!TextUtils.isEmpty(mCategory)) {
            showTab(1);
        }

        requestVideo();
        refresh();
        getBonusList();
        updateCartView();


        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mTopImageView.scrollTo(0, verticalOffset * -1 / 6);

                int[] outLocation = new int[2];
                activity_shop_tab_bottom_wrapper.getLocationInWindow(outLocation);

                int totalHeight = outLocation[1] - verticalOffset - 150;
                float alpha = 1 - Math.abs(verticalOffset) * 1.0f / totalHeight;

                mWrapperLayout.setAlpha(alpha);
//                mWrapperLayoutTwo.setAlpha(alpha);
            }
        });

       /* mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {

                //现在还不好使
                if (state == State.EXPANDED) {
                    //展开状态
                } else if (state == State.COLLAPSED) {
                    activity_shop_tab_bottom_wrapper.getLayoutParams().height = Utils.dpToPx(mContext, 45);
                    //折叠状态
                } else {
                    activity_shop_tab_bottom_wrapper.getLayoutParams().height = Utils.dpToPx(mContext, 30);
                    //中间状态
                }
            }
        });*/

        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShopGoodsListActivity();
            }
        });
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
        switch (textView.getId()) {
            case R.id.activity_shop_commonEditText:
                if (EditorInfo.IME_ACTION_SEARCH == actionId || EditorInfo.IME_ACTION_DONE ==
                        actionId || EditorInfo.IME_ACTION_UNSPECIFIED == actionId) {
                    openShopGoodsListActivity();
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_base_homeMenu:
                startActivity(new Intent().setClass(ShopActivity.this, RootActivity.class));
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_INDEX.getValue()));
                ShopActivity.this.finish();
                return true;
            case R.id.activity_base_messageMenu:
                startActivity(new Intent().setClass(this, MessageActivity.class));
                return true;
            case R.id.activity_base_searchMenu:
                startActivity(new Intent().setClass(this, SearchActivity.class));
                return true;
            case R.id.action_my_following:
                openCollectionActivity();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void openServiceActivity() {
        if (!App.getInstance().isLogin()) {
            ShopIndexFragment.mProgress.hide();
            mLoginListener = new OnLoginListener() {
                @Override
                public void onLogin() {
//                    openServiceActivity();
                    onOpenImActivity();
                }
            };
            openLoginActivityForResult();
            return;
        } else {
            onOpenImActivity();
        }
//        WangXin wangXinModel = null;
//        try {
//            wangXinModel = mShopModel.data.shop_info.aliim;
//        } catch (Exception e) {
//
//        }
//
//        if (wangXinModel == null) {
//            toast("没有成功获取客服信息！");
//            return;
//        }
//
//        boolean wangXinEnabled = mShopModel.data.shop_info.aliim_enable.equals("1");
//        if (!wangXinEnabled || Utils.isNull(wangXinModel.aliim_appkey)) {
//            ShopIndexFragment.mProgress.hide();
//            if (!Utils.isNull(mShopModel.data.shop_info.shop.service_tel)) {
//                Utils.openPhoneDialog(this,mShopModel.data.shop_info.shop.service_tel);
//                return;
//            } else {
//                Toast.makeText(this, "该店铺未配置客服信息", Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }
//
//        final String userId = wangXinModel.aliim_uid;
//        final String userPassword = wangXinModel.aliim_pwd;
//        final String appKey = wangXinModel.aliim_appkey;
//        final String serviceId = wangXinModel.aliim_main_customer;
//
//        if (!App.getInstance().YWApiIsInit) {
//            Application mContext = (Application) getApplicationContext();
//            YWAPI.init(mContext, appKey);
//        }
//
//        final YWIMKit mIMKit = YWAPI.getIMKitInstance(userId, appKey);
//        mIMKit.showCustomView(null);
//
//        IYWLoginService loginService = mIMKit.getLoginService();
//        YWLoginParam loginParam = YWLoginParam.createLoginParam(userId, userPassword);
//        loginService.login(loginParam, new IWxCallback() {
//            @Override
//            public void onSuccess(Object... arg0) {
//                ShopIndexFragment.mProgress.hide();
//                final IYWContactService contactService = mIMKit.getContactService();
//                contactService.setCrossContactProfileCallback(new IYWCrossContactProfileCallback() {
//
//                    @Override
//                    public IYWContact onFetchContactInfo(String userId, String appKey) {
//                        //这里的Obj是用户自定义对
//                        // 象，此对象需要实现IYWContact
//                        //参数userId是要显示头像的用户id
//                        //具体使用示例可以参考我们提供的demo
//                        if (userId.equals(serviceId)) {
//                            return new YWAvatar(); //客服的信息，XXX为客户的ID号
//                        }
//                        return null;
//                    }
//
//                    @Override
//                    public Intent onShowProfileActivity(String userId, String appKey) {
//                        //这里支持头像点击事件，需要开发者返回一个Intent
//                        return null;
//                    }
//
//                    @Override
//                    public void updateContactInfo(Contact contact) {
//
//                    }
//                });
//                EServiceContact contact = new EServiceContact(serviceId, 0);
//                contact.setNeedByPass(false);
//                Intent intent = mIMKit.getChattingActivityIntent(contact);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onError(int errCode, String description) {
//            }
//
//            @Override
//            public void onProgress(int arg0) {
//            }
//        });
    }

    @Override
    public void scrollToTop() {
        mAppBarLayout.setExpanded(true, true);
    }

    private void follow() {
        CommonRequest request = new CommonRequest(Api.API_COLLECT_TOGGLE, HTTP_WHAT_FOLLOW,
                RequestMethod.POST);
        request.setAjax(true);
        request.add("shop_id", mShopId);
        addRequest(request);
    }

    private void followSucceed(String response) {
        ResponseFollowModel model = JSON.parseObject(response, ResponseFollowModel.class);
        if (model.code == 0) {
            if (!Utils.isNull(model.data)) {
                mShopModel.data.is_collect = model.data == 1;
            } else {
                mShopModel.data.is_collect = false;
            }

            updateFollowViewStatus();
            Toast.makeText(this, model.message, Toast.LENGTH_SHORT).show();
            if (model.data == 1) {
                if (!Utils.isNull(model.bonus_info) && !model.bonus_info.bonus_amount.equals("")) {
                    openBonusActivity(model.bonus_info.bonus_number, model.bonus_info
                            .bonus_amount_format, model.bonus_info.bonus_name);
                }
                mTeampFansNumber += 1;
                mFansNumber.setText(mTeampFansNumber + "");
                textView_collection.setText("已收藏");
            } else {
                mTeampFansNumber -= 1;
                mFansNumber.setText(mTeampFansNumber + "");
                textView_collection.setText("收藏");
            }
        } else {
            Toast.makeText(this, model.message, Toast.LENGTH_SHORT).show();
            if (model.code == 99) {
                openLoginActivityForResult(REQUEST_CODE_LOGIN_FOR_COLLECT_SHOP);
            }
        }
    }

    private ShopIndexFragment getShopIndexFragment() {
        for (Map.Entry<String, Fragment> entry : mFragmentMap.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(ShopIndexFragment.class.getSimpleName())) {
                return (ShopIndexFragment) entry.getValue();
            }
        }
        return null;
    }

    private ShopInfoFragment getShopInfoFragment() {
        for (Map.Entry<String, Fragment> entry : mFragmentMap.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(ShopInfoFragment.class.getSimpleName())) {
                return (ShopInfoFragment) entry.getValue();
            }
        }
        return null;
    }

    private void hideCartView() {
        if (mCartWrapperOne.getVisibility() == View.INVISIBLE) {
            return;
        }
        ObjectAnimator animatorOne = ObjectAnimator.ofFloat(mCartWrapperOne, "translationY", 0,
                Utils.dpToPx(this, 40));
        animatorOne.setDuration(300);
        animatorOne.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCartWrapperOne.setVisibility(View.INVISIBLE);
            }
        });
        animatorOne.start();
        ObjectAnimator animatorTwo = ObjectAnimator.ofFloat(mCartWrapperTwo, "translationY", 0,
                Utils.dpToPx(this, 70));
        animatorTwo.setDuration(300);
        animatorTwo.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                switchBottomCart(false);
            }
        });
        animatorTwo.start();
    }

    private void openBonusActivity(String number, String value, String name) {
        Intent intent = new Intent();
        intent.setClass(this, RegisterBonusActivity.class);
        intent.putExtra(Key.KEY_BONUS_NUMBER.getValue(), number);
        intent.putExtra(Key.KEY_BONUS_VALUE.getValue(), value);
        intent.putExtra(Key.KEY_BONUS_NAME.getValue(), name);
        intent.putExtra(Key.KEY_BONUS_TYPE.getValue(), 1);
        startActivity(intent);
    }

    private void openCartActivity() {
        Intent intent = new Intent(this, ShopCartActivity.class);
        intent.putExtra("shop_id", mShopId);
        startActivity(intent);
    }

    protected void openCollectionActivity() {
        if (!App.getInstance().isLogin()) {
            mLoginListener = new OnLoginListener() {
                @Override
                public void onLogin() {
                    openCollectionActivity();
                }
            };
            openLoginActivityForResult();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("type", "goodsCollection");
        intent.setClass(this, CollectionActivity.class);
        startActivity(intent);
    }

    private void openLoginActivityForResult() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, REQUEST_CODE_LOGIN);
    }

    private void openLoginActivityForResult(Integer i) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, i);
    }


    private void openPopMenu() {
//        PopupMenu popupMenu = new PopupMenu(this, mMoreImageButton);
//
//        popupMenu.inflate(R.menu.activity_shop);
//        popupMenu.setOnMenuItemClickListener(this);
//        popupMenu.show();
//        Field field = null;
//        try {
//            field = popupMenu.getClass().getDeclaredField("mPopup");
//            field.setAccessible(true);
//            MenuPopupHelper mHelper = (MenuPopupHelper) field.get(popupMenu);
//            mHelper.setForceShowIcon(true);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
        mCustemMenuStyle = MenuPopwindow.MENU_STYLE_SHOP;
        initCustemMenu();
//        menuPopwindow.showPopupWindowAsDropDown(mMoreImageButton);
        menuPopwindow.showPopupWindow(this.getWindow().getDecorView());
    }

    void openShareActivity() {
        Intent intent = new Intent(mContext, ShareActivity.class);
        intent.putStringArrayListExtra(ShareActivity.SHARE_DATA, shareData);
        intent.putExtra(ShareActivity.SHARE_TYPE, ShareActivity.TYPE_SHOP);
        startActivity(intent);
    }

    private void openShopGoodsListActivity() {
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow
                    (getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        Intent shopGoodsIntent = new Intent();
        shopGoodsIntent.putExtra(Key.KEY_REQUEST_KEYWORD.getValue(), String.valueOf
                (mCommonEditText.getText()));
        shopGoodsIntent.putExtra(Key.KEY_REQUEST_SHOP_ID.getValue(), mShopId);
        shopGoodsIntent.setClass(this, ShopGoodsListActivity.class);
        startActivity(shopGoodsIntent);
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_SHOP, HTTP_WHAT_SHOP_INFO);
        request.add("id", mShopId);
        addRequest(request);
    }

    public void getBonusList() {
        CommonRequest request = new CommonRequest(Api.API_SHOP_BONUS_LIST, HTTP_WHAT_BONUS_LIST);
        request.add("shop_id", mShopId);
        addRequest(request);
    }

    private void refreshSucceed(String response) {
        mShopModel = JSON.parseObject(response, ResponseShopModel.class);

        if ("1".equals(mShopModel.data.freebuy_enable)) {
            fragment_shop_qrcode.setVisibility(View.VISIBLE);
        } else {
            fragment_shop_qrcode.setVisibility(View.GONE);
        }

        if ("1".equals(mShopModel.data.shop_header_style)) {
            mShareImageButton.setVisibility(View.GONE);
            mWrapperLayout.setVisibility(View.GONE);
            mWrapperLayoutTwo.setVisibility(View.VISIBLE);


            float windowWidth = Utils.getWindowWidth(this);
            double realHeight = windowWidth * 0.4;

//            mLinearLayout.getLayoutParams().height = Utils.dpToPx(this, 200) + (int) realHeight;


            if (mShopModel.data.is_collect) {
                textView_collection.setText("已收藏");
            } else {
                textView_collection.setText("收藏");
            }

        } else {
            mWrapperLayout.setVisibility(View.VISIBLE);
            mWrapperLayoutTwo.setVisibility(View.GONE);
//            mLinearLayout.getLayoutParams().height = Utils.dpToPx(this, 150);
        }
        if (!mShopModel.data.is_opening) {
            openCloseShop();
        }
        if (!Utils.isNull(mShopModel.data.shop_info.shop.shop_image)) {

            ImageLoader.loadImage(Utils.urlOfImage(mShopModel.data.shop_info.shop.shop_image), new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    mLogoImageView.setImageBitmap(Utils.bimapRound(loadedImage, 15));
                }
            });
//            ImageLoader.displayImage(Utils.urlOfImage(mShopModel.data.shop_info.shop.shop_image),
//                    mLogoImageView);
            ImageLoader.displayImage(Utils.urlOfImage(mShopModel.data.shop_info.shop.shop_image),
                    mTitleImageView);
        }
        if (!Utils.isNull(mShopModel.data.shop_info.shop.shop_sign_m)) {
            ImageLoader.displayImage(Utils.urlOfImage(mShopModel.data.shop_info.shop.shop_sign_m)
                    , mTopImageView);
        }

        if (!Utils.isNull(mShopModel.data.shop_info.shop.detail_introduce)) {
            mArticle.setText(Html.fromHtml(mShopModel.data.shop_info.shop.detail_introduce).toString());
            mArticle1.setText(Html.fromHtml(mShopModel.data.shop_info.shop.detail_introduce).toString());
        }

        mShopModel.data.shop_info.shop.url = mShopModel.data.shop_info.credit.credit_img;

        try {
            mTeampFansNumber = Integer.valueOf(mShopModel.data.collect_count);
        } catch (Exception e) {
            mTeampFansNumber = 0;
        }

        if (!Utils.isNull(mShopModel.data.collect_count)) {
            mFansNumber.setText(mShopModel.data.collect_count);
        } else {
            mFansNumber.setText("0");
        }

        shareData.add(Utils.urlOfWapShopindex() + mShopModel.data.shop_id + ".html");
        shareData.add(mShopModel.data.shop_info.share.seo_shop_title);
        shareData.add(mShopModel.data.shop_info.share.seo_shop_discription);
        shareData.add(Utils.setImgNetUrl(Api.API_HEAD_IMG_URL, mShopModel.data.shop_info.share.seo_shop_image));
        shareData.add(mShopModel.data.shop_id);

//        if (Utils.isNull(mShopModel.data.context.cart.goods_count)) {
//            mCartNumberTextView.setText(App.getCartString());
//        } else {
//            mCartNumberTextView.setText(mShopModel.data.context.cart.goods_count);
//        }

        updateFollowViewStatus();

        mNameTextView.setText(mShopModel.data.shop_info.shop.shop_name);
        mShopNameTextView.setText(mShopModel.data.shop_info.shop.shop_name);
//        mTabCategoryTopTextView.setText(mShopModel.data.goods_count);
        mTabCategoryBottomTextView.setText("全部商品(" + mShopModel.data.goods_count + ")");

        fragmentParameter = new Bundle();
        mShopModel.data.shop_info.shop.duration_time = mShopModel.data.duration_time;
        mShopModel.data.shop_info.shop.region_name = mShopModel.data.region_name;
        fragmentParameter.putParcelable(Key.KEY_SHOP_INFO.getValue(), mShopModel.data.shop_info
                .shop);
        fragmentParameter.putString("username", mShopModel.data.shop_info
                .user.nickname);
        fragmentParameter.putString("real", mShopModel.data.shop_info
                .real.special_aptitude);

        mFragmentParameters.add(fragmentParameter);

        refrshShopInfo();

        mShopIndexModel = JSON.parseObject(response, ResponseShopIndexModel.class);
        if (mShopIndexModel.data.data_template == null) {
            mShopIndexModel.data.data_template = new ArrayList<>();
        }

        ShopIndexFragment fragment = getShopIndexFragment();
        if (fragment != null) {
            fragment.videoDataString = videoDataString;
            fragment.setUpAdapterData(mShopIndexModel.data.data_template, mShopId);
        }
    }

    ShopRedPacketAdapter shopRedPacketAdapter;

    private void getBonusListSucceed(String response) {
        if (shopRedPacketAdapter == null) {
            shopRedPacketAdapter = new ShopRedPacketAdapter();
            shopRedPacketAdapter.onClickListener = this;
            recyclerViewRedPacket.setAdapter(shopRedPacketAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShopActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerViewRedPacket.setLayoutManager(linearLayoutManager);
        }

        HttpResultManager.resolve(response, ShopBonusListModel.class, new HttpResultManager.HttpResultCallBack<ShopBonusListModel>() {
            @Override
            public void onSuccess(ShopBonusListModel back) {
                if (back.data.list == null || back.data.list.size() <= 0) {
                    activity_shop_tab_red_packet.setVisibility(View.GONE);
                } else {
                    activity_shop_tab_red_packet.setVisibility(View.VISIBLE);

                    shopRedPacketAdapter.data.clear();
                    List<Object> redpacketList = new ArrayList<>();
                    redpacketList.addAll(back.data.list);
                    redpacketList.add(new EmptyItemModel());

                    shopRedPacketAdapter.data.addAll(redpacketList);
                    shopRedPacketAdapter.notifyDataSetChanged();
                }
            }
        }, true);
    }

    boolean isCarViewShow = false;

    private void updateCartSucceed(String response) {
        HttpResultManager.resolve(response, ShopCartModel.class, new HttpResultManager.HttpResultCallBack<ShopCartModel>() {
            @Override
            public void onSuccess(ShopCartModel back) {
                try {
                    BigDecimal b1 = new BigDecimal(back.start_price);
                    BigDecimal b2 = new BigDecimal(back.select_goods_amount);

                    if (b2.compareTo(new BigDecimal(0)) > 0 && b2.compareTo(b1) >= 0) {
                        mCheckoutButton.setEnabled(true);
                        mCheckoutButton.setText("去结算");
                    } else {
                        mCheckoutButton.setEnabled(false);

                        if (b1.subtract(b2).doubleValue() > 0) {
                            mCheckoutButton.setText("还差¥" + b1.subtract(b2).doubleValue());
                        } else {
                            if (b1.compareTo(new BigDecimal(0)) == 0) {
                                mCheckoutButton.setText("去结算");
                            } else {
                                mCheckoutButton.setText("¥" + back.start_price + "元起送");
                            }

                        }
                    }
                    mCartNumberTextView.setText(back.cart_count + "");

                    if (back.cart_count <= 0) {
                        isCarViewShow = false;
                    } else {
                        isCarViewShow = true;
                    }

                    if (isCarViewShow) {
                        mCartWrapperTwo.setVisibility(View.VISIBLE);
                        imageView_empty_cart.setVisibility(View.INVISIBLE);
                        textView_info.setTextColor(getResources().getColor(R.color.colorPrimary));
                        textView_info.setText(back.select_goods_amount_format);
                        textView_info.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                    } else {
                        mCartWrapperTwo.setVisibility(View.INVISIBLE);
                        imageView_empty_cart.setVisibility(View.VISIBLE);
                        textView_info.setTextColor(getResources().getColor(R.color.colorThree));
                        textView_info.setText("购物车为空");
                        textView_info.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                    }
                } catch (Exception e) {
                }
            }
        }, true);
    }

    public void refrshShopIndexFragment() {
        if (mShopIndexModel == null) {
            refresh();
        } else {
            ShopIndexFragment fragment = getShopIndexFragment();
            if (fragment != null) {
                fragment.videoDataString = videoDataString;
                fragment.setUpAdapterData(mShopIndexModel.data.data_template, mShopId);
            }
        }
    }

    public void refrshShopInfoFragment() {
        if (mShopModel == null) {
            refresh();
        } else {
            refrshShopInfo();
        }
    }

    public void refrshShopInfo() {
        ShopInfoFragment shopInfoFragment = getShopInfoFragment();
        if (shopInfoFragment != null) {
            shopInfoFragment.refrsh(mShopModel.data.shop_info
                    .shop, mShopModel.data.shop_info
                    .user.user_name, mShopModel.data.shop_info.real.special_aptitude);
        }
    }

    void switchBottomCart(boolean visible) {
        if (isCarViewShow) {
            if (visible) {
                mCartWrapperTwo.setVisibility(View.VISIBLE);
            } else {
                mCartWrapperTwo.setVisibility(View.INVISIBLE);
            }

            imageView_empty_cart.setVisibility(View.INVISIBLE);
        } else {
            mCartWrapperTwo.setVisibility(View.GONE);

            if (visible) {
                imageView_empty_cart.setVisibility(View.VISIBLE);
            } else {
                imageView_empty_cart.setVisibility(View.INVISIBLE);
            }

        }
    }

    private void openCloseShop() {
        startActivity(new Intent(this, ShopCloseActivity.class));
    }

    private void showCartView() {
        if (mCartWrapperOne.getVisibility() == View.VISIBLE) {
            return;
        }
        mCartWrapperOne.setVisibility(View.VISIBLE);
        ObjectAnimator animatorOne = ObjectAnimator.ofFloat(mCartWrapperOne, "translationY",
                Utils.dpToPx(this, 40), 0);
        animatorOne.setDuration(300);
        animatorOne.start();
        animatorOne.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCartWrapperOne.setVisibility(View.VISIBLE);
            }
        });
        ObjectAnimator animatorTwo = ObjectAnimator.ofFloat(mCartWrapperTwo, "translationY",
                Utils.dpToPx(this, 70), 0);
        animatorTwo.setDuration(300);
        animatorTwo.start();
        animatorTwo.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                switchBottomCart(true);
            }
        });
    }

    @SuppressLint("RestrictedApi")
    private void showFragment(Class<Fragment>[] classes, int index) {
        Fragment fragment = null;
        Class<Fragment> clazz = classes[index];
        String tag = clazz.getSimpleName();
        boolean mExist = false;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (getSupportFragmentManager().getFragments() != null) {
            for (Fragment existedFragment : getSupportFragmentManager().getFragments()) {
                if (!existedFragment.getTag().equalsIgnoreCase(tag)) {
                    transaction.hide(existedFragment);
                } else {
                    fragment = existedFragment;
                    mExist = true;
                }
            }
        }
        if (!mExist) {
            try {
                fragment = clazz.newInstance();
                if (mFragmentParameters != null && mFragmentParameters.size() > index) {
                    Bundle arguments = mFragmentParameters.get(index);
                    if (fragment.getArguments() != null) {
                        fragment.getArguments().putAll(arguments);
                    } else {
                        fragment.setArguments(arguments);
                    }
                }

            } catch (InstantiationException exception) {
                Toast.makeText(this, "Failed to instantiate class " + tag + "," + exception
                        .getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                exception.printStackTrace();
            } catch (IllegalAccessException exception) {
                Toast.makeText(this, "Failed to access class " + tag + "," + exception
                        .getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                exception.printStackTrace();
            }
            if (fragment != null) {
                if (fragment instanceof ScrollObservable) {
                    ScrollObservable observable = (ScrollObservable) fragment;
                    observable.addScrollObserver(ShopActivity.this);
                }
                if (fragment instanceof ListStyleObserver) {
                    ListStyleObserver observer = (ListStyleObserver) fragment;
                    observer.setStyle(Macro.STYLE_LIST);
                }
                mFragmentMap.put(tag, fragment);
                transaction.add(R.id.activity_shop_frameLayout, fragment, tag);
            }
        }
        if (fragment != null) {
            transaction.show(fragment);
        }
        safeCommit(transaction);
    }

    private void showTab(int index) {
        updateMenu(index);
        showFragment(mFragments, index);
        if (index == 1) {
            showCartView();
        } else {
            hideCartView();
        }
    }

    public void updateCart() {
        CommonRequest request = new CommonRequest(Api.API_CART_BOX_GOODS_LIST, HTTP_WHAT_CART_BOX_GOODS_LIST);
        request.setAjax(true);
        request.alarm = false;
        request.add("shop_id", mShopId);
        addRequest(request);
    }

    private void startAnimation(Drawable animView, final int startX, final int startY, final int
            endX, final int endY) {
        final View animationView = LayoutInflater.from(this).inflate(R.layout
                .activity_shop_animator1, null);

        ImageView im = (ImageView) animationView.findViewById(R.id.imageView_anim);
        im.setImageDrawable(animView);

        mContentView.addView(animationView);

        int picSize = Utils.dpToPx(im.getContext(), 50) / 2;

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(animationView, "X", startX - picSize,
                endX - picSize);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(animationView, "Y", startY - picSize,
                endY - picSize);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(animationView, "scaleX", 1f, 0.25f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(animationView, "scaleY", 1f, 0.25f);

        scaleX.setInterpolator(new AnticipateInterpolator(2.0f));
        scaleY.setInterpolator(new AnticipateInterpolator(2.0f));
        animatorY.setInterpolator(new AnticipateInterpolator(2.0f));
        animatorX.setInterpolator(new AccelerateInterpolator());

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
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mCartWrapperTwo, "scaleX", 1.0f, 1.4f,
                1.0f, 1.2f, 1.0f);
        animatorX.setDuration(200);
        animatorX.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorX.start();

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mCartWrapperTwo, "scaleY", 1.0f, 1.4f,
                1.0f, 1.2f, 1.0f);
        animatorY.setDuration(200);
        animatorY.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorY.start();
    }

    private void updateCartView() {
        mCheckoutButton.setEnabled(App.getCartNumber() > 0);
        mCartNumberTextView.setText(App.getCartString());
    }

    private void updateFollowViewStatus() {
        mFollowImageView.setSelected(mShopModel.data.is_collect);
        mFollowTextView.setText(mShopModel.data.is_collect ? R.string.buttonFollowed : R.string
                .buttonFollow);
    }

    private void updateMenu(int index) {
        for (int i = 0; i < mTabViews.size(); i++) {
            for (View view : mTabViews.get(i)) {
                view.setSelected(i == index);
            }
        }
    }

    private void goCheckOut() {
        CommonRequest mGoCheckoutList = new CommonRequest(Api.API_GO_CHECKOUT, HTTP_GO_CHECKOUT, RequestMethod.POST);
        mGoCheckoutList.setAjax(true);
        addRequest(mGoCheckoutList);
    }

    private void requestVideo() {
        CommonRequest request = new CommonRequest(Api.API_INDEX_SITE, HTTP_WHAT_VIDEO_LIST, RequestMethod.GET);
        request.add("tpl_code", "m_video");
        request.add("shop_id", mShopId);
        request.alarm = false;
        addRequest(request);
    }

    private void requestVideoCallback(final String response) {
        HttpResultManager.resolve(response, VideoModel.class, new HttpResultManager.HttpResultCallBack<VideoModel>() {
            @Override
            public void onSuccess(VideoModel videoModel) {
                videoDataString = response;
            }
        });
    }

    /**
     * 获取商户信息
     */
    private final void onOpenImActivity() {

        CommonRequest request = new CommonRequest(Api.API_CITY_IM_GET_IMINFO, HTTP_IM_INFO, RequestMethod.POST);
        RequestAddHead.addHead(request, ShopActivity.this, Api.API_CITY_IM_GET_IMINFO, "POST");
        JSONObject object = new JSONObject();
        try {
            object.put("userName", mShopModel.data.shop_info.kf.user_id);
            object.put("extType", "shop");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.setDefineRequestBodyForJson(object);
        addRequest(request);
    }
}
