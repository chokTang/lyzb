package com.szy.yishopcustomer.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lyzb.jbx.R;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Activity.AccountBalanceActivity;
import com.szy.yishopcustomer.Activity.AccountSecurityActivity;
import com.szy.yishopcustomer.Activity.AddressListActivity;
import com.szy.yishopcustomer.Activity.BackActivity;
import com.szy.yishopcustomer.Activity.BonusActivity;
import com.szy.yishopcustomer.Activity.CollectionActivity;
import com.szy.yishopcustomer.Activity.DepositCardActivity;
import com.szy.yishopcustomer.Activity.DistribActivity;
import com.szy.yishopcustomer.Activity.DistributorCheckActivity;
import com.szy.yishopcustomer.Activity.GiftCardsActivity;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.HybridWebViewActivity;
import com.szy.yishopcustomer.Activity.IngotListActivity;
import com.szy.yishopcustomer.Activity.InventoryActivity;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.OrderListActivity;
import com.szy.yishopcustomer.Activity.OrderListFreeActivity;
import com.szy.yishopcustomer.Activity.ProfileActivity;
import com.szy.yishopcustomer.Activity.RecommStoreActivity;
import com.szy.yishopcustomer.Activity.RecommendActivity;
import com.szy.yishopcustomer.Activity.ReviewListActivity;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Activity.SetUpActivity;
import com.szy.yishopcustomer.Activity.UserCardActivity;
import com.szy.yishopcustomer.Activity.UserGroupOnListActivity;
import com.szy.yishopcustomer.Activity.UserIntegralActivity;
import com.szy.yishopcustomer.Activity.UserPayActivity;
import com.szy.yishopcustomer.Activity.im.LyMessageActivity;
import com.szy.yishopcustomer.Adapter.UserAdapter;
import com.szy.yishopcustomer.Adapter.UserTwoAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Interface.OnUserIngotNumberViewListener;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GuessGoodsModel;
import com.szy.yishopcustomer.ResponseModel.IngotList.UsableIngotModel;
import com.szy.yishopcustomer.ResponseModel.User.Model;
import com.szy.yishopcustomer.ResponseModel.User.UserModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.User.ButtonModel;
import com.szy.yishopcustomer.ViewModel.User.GuessTitleModel;
import com.szy.yishopcustomer.ViewModel.User.LineModel;
import com.szy.yishopcustomer.ViewModel.User.MyAssetsModel;
import com.szy.yishopcustomer.ViewModel.User.TitleModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;
import me.zongren.pullablelayout.View.PullableRecyclerView;

import static com.szy.yishopcustomer.Activity.AddressListActivity.IS_SHOW;

/**
 * Created by liuzhifeng on 17/2/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class UserFragment extends YSCBaseFragment implements OnPullListener, OnUserIngotNumberViewListener {

    @BindView(R.id.fragment_user_pullableLayout)
    PullableLayout mPullableLayout;
    @BindView(R.id.fragment_user_pullableRecyclerView)
    PullableRecyclerView mRecyclerView;

    private final int CALL_PHONE_PERMISSION = 1133;

    //http://192.168.200.126:8080/#/newPeopleEnjoy

    //猜你喜欢数据 分页int
    private int guess_page = 1;
    private boolean guess_more = true;
    private List<GuessGoodsModel> mGoodsModelList;

    private String progress = "0";
    //    private GridLayoutManager mLayoutManager;
    private GridLayoutManager mLayoutManagerTwo;
    private UserTwoAdapter mAdapter;
    private Model mModel;
    private OnLoginCallback mLoginCallback;
    private List<ViewType> mAuthorizedTypes = new ArrayList() {{
        add(ViewType.VIEW_TYPE_MESSAGE);
        add(ViewType.VIEW_TYPE_AVATAR);
        add(ViewType.VIEW_TYPE_NAME);
        add(ViewType.VIEW_TYPE_REVIEW_LIST);
        add(ViewType.VIEW_TYPE_BALANCE);
        add(ViewType.VIEW_TYPE_BONUS);
        add(ViewType.VIEW_TYPE_ALL_ORDER);
        add(ViewType.VIEW_TYPE_AWAIT_PAY);
        add(ViewType.VIEW_TYPE_AWAIT_SHIP);
        add(ViewType.VIEW_TYPE_AWAIT_CONFIRM);
        add(ViewType.VIEW_TYPE_AWAIT_REVIEW);
        add(ViewType.VIEW_TYPE_AWAIT_CANCEL);
        add(ViewType.VIEW_TYPE_COLLECTION);
        add(ViewType.VIEW_TYPE_SPELL_GROUP);
        add(ViewType.VIEW_TYPE_CONSIGNEE_ADDRESS);
        add(ViewType.VIEW_TYPE_SERVICE_TEL);
        add(ViewType.VIEW_TYPE_USER_CARD);
        add(ViewType.VIEW_TYPE_USER_INTEGRAL);
        add(ViewType.VIEW_TYPE_USER_PROFILE);
        add(ViewType.VIEW_TYPE_RECOMMEND);
        add(ViewType.VIEW_TYPE_DISTRIBUTOR);
        add(ViewType.VIEW_TYPE_DISTRIBUTOR_APPLY);
        add(ViewType.VIEW_TYPE_RECOMM_STORE);
        add(ViewType.VIEW_TYPE_INVENTORY);
        add(ViewType.VIEW_TYPE_MERCHANTS_SETTLED);
        add(ViewType.VIEW_TYPE_USER_SECURITY);
        add(ViewType.VIEW_TYPE_USER_GIFT_CARD);
        add(ViewType.VIEW_TYPE_TOOL_PAYMENT);
        add(ViewType.VIEW_TYPE_TOOL_USERCARD);

    }};

    private UsableIngotModel mUsableIngotModel = null;

    TextView mTextView_IngotNumber = null;

    private List<Object> titleModels;
    private String integral_model = "0";

    @Override
    public void onCanceled(PullableComponent pullableComponent) {
    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        refresh();
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }


    @Override
    public void onClick(View view) {
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int extraInfo = Utils.getExtraInfoOfTag(view);
        if (!handleViewType(viewType, extraInfo)) {
            super.onClick(view);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View v = super.onCreateView(inflater, container, savedInstance);
//        mLayoutManager = new GridLayoutManager(getContext(), 5);
//        mLayoutManager.setSpanSizeLookup(new SpanSizeLookUp());

        mLayoutManagerTwo = new GridLayoutManager(getContext(), 20);
        mLayoutManagerTwo.setSpanSizeLookup(new SpanSizeLookUpTwo());

        mRecyclerView.setLayoutManager(mLayoutManagerTwo);
        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.addItemDecoration(new ItemDecoration());
        mPullableLayout.topComponent.setOnPullListener(this);
        refresh();
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        return v;
    }


    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            /*** 底部监听 **/
            if (Utils.isRecyViewBottom(recyclerView)) {

                /** 加载更多 **/
                if (guess_more) {
                    guess_page++;
                    getLikeData();
                } else {
                    Toast.makeText(getActivity(), "暂无更多数据", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_LOGIN:
                if (!event.isFrom(this)) {
                    refresh();
                    if (mLoginCallback != null) {
                        mLoginCallback.onLoginCallback();
                        mLoginCallback = null;
                    }
                }
                break;
            case EVENT_LOGOUT:
                refresh();
                //切换到首页，然后跳转到登录页面
                openLoginActivityForResult(RequestCode.REQUEST_CODE_LOGIN_FOR_REFRESH);
                break;
            case EVENT_RECEIVER_MESSAGE:
                refresh();
                break;
            case EVENT_REFRESH_USER_INFO:
                refresh();
                break;
            default:
                super.onEvent(event);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (RequestCode.valueOf(requestCode)) {
            case REQUEST_CODE_LOGIN_FOR_REFRESH:
                if (resultCode == Activity.RESULT_OK) {
                    EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_USER_TAB.getValue()));
                    refresh();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private void openLoginActivityForResult(RequestCode requestCode) {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        this.startActivityForResult(intent, requestCode.getValue());
        openRootIndex();
    }

    private void openRootIndex() {
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_INDEX.getValue()));
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        mPullableLayout.topComponent.finish(Result.FAILED);
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_USER:
                refreshCallback(response);
                getLikeData();//猜你喜欢
                break;

            case HTPP_USER_INGOT_NUMBER:
                setIngotData(response);
                break;
            case HTTP_INDEX_GUESS_LIKE://猜你喜欢数据回调
                callBackGuessLike(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_user;
        mAdapter = new UserTwoAdapter();
        mAdapter.onClickListener = this;
        mAdapter.setUserIngotNumber(this);

        setAdapterData();


    }

    public void openOrderListActivity(final String status) {
        Intent intent = new Intent(getContext(), OrderListActivity.class);
        intent.putExtra(Key.KEY_ORDER_STATUS.getValue(), status);
        startActivity(intent);
    }

    @Override
    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_USER, HttpWhat.HTTP_USER.getValue());
        request.setAjax(true);
        addRequest(request);
    }

    public void getIngotData() {
        CommonRequest request = new CommonRequest(Api.API_INGOT_USABLE, HttpWhat.HTPP_USER_INGOT_NUMBER.getValue());
        addRequest(request);
    }

    public void refreshCallback(String response) {
        mPullableLayout.topComponent.finish(Result.SUCCEED);
        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model back) {
                mModel = back;
                App.getInstance().userEmail = mModel.data.context.user_info.email;
                App.getInstance().userName = mModel.data.context.user_info.user_name;
                App.getInstance().userPhone = mModel.data.context.user_info.mobile;
                App.getInstance().headimg = mModel.data.context.user_info.headimg;
                App.getInstance().user_money_format = mModel.data.info.user_money_format;
//                App.getInstance().nickName = mModel.data.context.user_info.nickname;

                integral_model = mModel.data.integral_model;

                if (!"0".equals(mModel.data.reachbuy_order_counts)) {
                    App.getInstance().is_reachbuy_enable = 1;
                } else {
                    App.getInstance().is_reachbuy_enable = 0;
                }

                if (!"0".equals(mModel.data.is_scancode_enable)) {
                    App.getInstance().is_scancode_enable = 1;
                } else {
                    App.getInstance().is_scancode_enable = 0;
                }

                progress = mModel.data.progress;

                EventBus.getDefault().post(new CommonEvent.Builder(EventWhat.EVENT_LOGIN.getValue()).setMessageSource(this));
                App.setCartNumber(mModel.data.context.cart.goods_count, this);

                setAdapterData();

                getIngotData();
            }

            @Override
            public void onLogin() {
            }
        });
    }

    public void setIngotData(String ingotData) {
        mUsableIngotModel = JSON.parseObject(ingotData, UsableIngotModel.class);
        if (mUsableIngotModel.getCode() == 0) {
            App.getInstance().user_ingot_number = mUsableIngotModel.getData().getTotal_integral().getIntegral_num();
            if (((RootActivity) getActivity()).userIngotNumberView != null) {
                ((RootActivity) getActivity()).userIngotNumberView.setText(App.getInstance().user_ingot_number);
            }

            if (mTextView_IngotNumber != null) {
                mTextView_IngotNumber.setText(App.getInstance().user_ingot_number);
            }
        }
    }

    private boolean handleViewType(final ViewType viewType, int position) {
        if (Utils.isDoubleClick()) {
            return true;
        }

        mLoginCallback = null;
        if (mAuthorizedTypes.contains(viewType) && !App.getInstance().isLogin()) {
            mLoginCallback = new OnLoginCallback() {
                @Override
                public void onLoginCallback() {
                    handleViewType(viewType, 0);
                }
            };
            openLoginActivity();
            return true;
        }
        switch (viewType) {
            case VIEW_TYPE_SETTING://设置
                openSettingActivity();
                return true;
            case VIEW_TYPE_MESSAGE://消息
                openMessageActivity();
                return true;
            case VIEW_TYPE_AVATAR://头像名字
            case VIEW_TYPE_NAME:
                openProfileActivity();
                return true;
            case VIEW_TYPE_BALANCE:
                openBalanceActivity();
                return true;
            case VIEW_TYPE_BONUS:
                openBonusActivity();
                return true;
            case VIEW_TYPE_ALL_ORDER:
                openOrderListActivity(Macro.ORDER_TYPE_ALL);
                return true;
            case VIEW_TYPE_AWAIT_PAY:
                openOrderListActivity(Macro.ORDER_TYPE_UNPAID);
                return true;
            case VIEW_TYPE_AWAIT_SHIP:
                openOrderListActivity(Macro.ORDER_TYPE_AWAIT_SHIPPED);
                return true;
            case VIEW_TYPE_AWAIT_CONFIRM:
                openOrderListActivity(Macro.ORDER_TYPE_SHIPPED);
                return true;
            case VIEW_TYPE_AWAIT_REVIEW:
                openOrderListActivity(Macro.ORDER_TYPE_AWAIT_REVIEWED);
                return true;
            case VIEW_TYPE_AWAIT_CANCEL:
                openCancelGoodsListActivity();
                return true;
            case VIEW_TYPE_COLLECTION:
                openCollectionActivity();
                return true;
            case VIEW_TYPE_SPELL_GROUP:
                openSpellGroupActivity();
                return true;
            case VIEW_TYPE_INVENTORY:
                openInventoryActivity();
                return true;
            case VIEW_TYPE_CONSIGNEE_ADDRESS:
                openAddressListActivity();
                return true;
            case VIEW_TYPE_REVIEW_LIST:
                openReviewListActivity();
                return true;
            case VIEW_TYPE_USER_PROFILE:
                openProfileActivity();
                return true;
            case VIEW_TYPE_USER_SECURITY:
                openSecurityActivity();
                return true;
            case VIEW_TYPE_LOGOUT:
                return true;
            case VIEW_TYPE_LOGIN:
                openLoginActivity();
                return false;
            case VIEW_TYPE_RECOMM_STORE:
                //openRecommStoreActivity();
                return true;
            case VIEW_TYPE_RECOMMEND:
                openRecommendActivity();
                return true;
            case VIEW_TYPE_DISTRIBUTOR:
                openDistribActivity();
                return true;
            case VIEW_TYPE_SERVICE_TEL:

                //权限检查
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION);
                    } else {
                        callServicePhone();
                    }
                } else {
                    callServicePhone();
                }
                return true;
            case VIEW_TYPE_DISTRIBUTOR_APPLY:
                openDistributorCheckActivity();
                return true;
            case VIEW_TYPE_USER_CARD:
                openUserCard();
                return true;
            case VIEW_TYPE_USER_GIFT_CARD:
                //openUserGiftCard();
                break;
            case VIEW_TYPE_USER_INTEGRAL:

                /**跳转 我的元宝**/
                openIngot();
                return true;

            case VIEW_TYPE_MERCHANTS_SETTLED:
                /*** 商家入驻 **/
                openHybridWebView(Utils.getMallMBaseUrl() + "/shop/apply/register.html");
                return true;
            case VIEW_TYPE_TOOL_PAYMENT:
                openPaymentActivity();
                return true;
            case VIEW_TYPE_TOOL_USERCARD:
                openUserCardActivity();
                return true;
            case VIEW_INDEX_GUESS_LIKE:
                openGuessGoodItem(position);
                break;
        }
        return false;
    }


    private void openGuessGoodItem(int position) {
        GuessGoodsModel dataModel = mAdapter.mGoodsModelList.get(position);
        openGoodsActivity(dataModel.sku_id);
    }

    public void openGoodsActivity(String skuId) {
        if (isFastClick()) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), GoodsActivity.class);
            intent.putExtra(Key.KEY_SKU_ID.getValue(), skuId);
            startActivity(intent);
        }
    }

    public void openPaymentActivity() {
        Intent intent = new Intent(getActivity(), UserPayActivity.class);
        startActivity(intent);
    }

    public void openUserCardActivity() {
        Intent intent = new Intent(getActivity(), UserCardActivity.class);
        startActivity(intent);
    }

    public void openBilladingActivity() {
        Intent intent = new Intent(getActivity(), OrderListFreeActivity.class);
        intent.putExtra(Key.KEY_ORDER_STATUS.getValue(), Macro.ORDER_TYPE_ALL);
        intent.putExtra(OrderListFreeFragment.PARAMS_TYPE, OrderListFreeFragment.TYPE_REACHBUY);
        startActivity(intent);
    }


    private void openHybridWebView(String url) {
        Intent intent = new Intent(getActivity(), HybridWebViewActivity.class);
        intent.putExtra(Key.KEY_URL.getValue(), url);
        startActivity(intent);
    }

    private void openCancelGoodsListActivity() {
        //Intent intent = new Intent(getContext(), BackListActivity.class);
        Intent intent = new Intent(getContext(), BackActivity.class);
        startActivity(intent);
    }

    private void openAddressListActivity() {
        Intent intent = new Intent(getContext(), AddressListActivity.class);
        intent.putExtra(Key.KEY_ADDRESS_LIST_AVAILABLE_TYPE.getValue(), AddressListActivity
                .TYPE_EDIT);
        intent.putExtra(Key.KEY_ADDRESS_LIST_CURRENT_TYPE.getValue(), AddressListActivity
                .TYPE_EDIT);
        intent.putExtra(IS_SHOW, "333");
        startActivity(intent);
    }

    /**
     * 跳转 余额
     */
    private void openBalanceActivity() {
        Intent intent = new Intent(getContext(), AccountBalanceActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转 我的红包
     */
    private void openBonusActivity() {
        Intent intent = new Intent(getContext(), BonusActivity.class);
        startActivity(intent);
    }

    private void openCollectionActivity() {
        Intent intent = new Intent(getContext(), CollectionActivity.class);
        startActivity(intent);
    }

    private void openSpellGroupActivity() {
        Intent intent = new Intent(getContext(), UserGroupOnListActivity.class);
        startActivity(intent);
    }

    private void openLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }

    /***
     * 客服电话拨打
     */
    private void callServicePhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "4000-266-357");
        intent.setData(data);
        startActivity(intent);
    }

    /**
     * 打开 消息ac
     */
    private void openMessageActivity() {
//        Intent intent = new Intent(getContext(), MessageActivity.class);
//        startActivity(intent);
        Intent intent = new Intent(getContext(), LyMessageActivity.class);
        startActivity(intent);
    }

    private void openProfileActivity() {
        Intent intent = new Intent(getContext(), ProfileActivity.class);
        startActivity(intent);
    }

    private void openSecurityActivity() {
        Intent intent = new Intent(getContext(), AccountSecurityActivity.class);
        startActivity(intent);
    }

    private void openRecommStoreActivity() {
        Intent intent = new Intent(getContext(), RecommStoreActivity.class);
        startActivity(intent);
    }

    private void openRecommendActivity() {
        Intent intent = new Intent(getContext(), RecommendActivity.class);
        startActivity(intent);
    }

    private void openDistribActivity() {
        Intent intent = new Intent(getContext(), DistribActivity.class);
        intent.putExtra(Key.KEY_DISTRIB_TEXT.getValue(), mModel.data.app_distrib.distrib_text);
        intent.putExtra(Key.KEY_GETDISTRIBUTOR_TEXT.getValue(), mModel.data.app_distrib.getDistributorText);
        startActivity(intent);
    }

    private void openDistributorCheckActivity() {
        Intent intent = new Intent(getContext(), DistributorCheckActivity.class);
        intent.putExtra(Key.KEY_DISTRIB_TEXT.getValue(), mModel.data.app_distrib.distrib_text);
        intent.putExtra(Key.KEY_GETDISTRIBUTOR_TEXT.getValue(), mModel.data.app_distrib.getDistributorText);
        startActivity(intent);
    }

    private void openUserCard() {
        Intent intent = new Intent(getContext(), DepositCardActivity.class);
        startActivity(intent);
    }

    private void openUserGiftCard() {
        Intent intent = new Intent(getContext(), GiftCardsActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转 我的积分
     */
    private void openUserIntegral() {
        Intent intent = new Intent(getContext(), UserIntegralActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转 我的元宝
     */
    private void openIngot() {
        Intent intent = new Intent(getContext(), IngotListActivity.class);
        startActivity(intent);
    }

    private void openReviewListActivity() {
        Intent intent = new Intent(getContext(), ReviewListActivity.class);
        startActivity(intent);
    }

    private void openSettingActivity() {
        Intent intent = new Intent(getContext(), SetUpActivity.class);
        startActivity(intent);
    }

    private void setAdapterData() {
        mAdapter.data.clear();
        UserModel userModel = null;

        MyAssetsModel myAssetsModel = new MyAssetsModel();

        TitleModel allOrder = new TitleModel(true, getString(R.string.allOrder), getString(R
                .string.checkAllOrder), ViewType.VIEW_TYPE_ALL_ORDER.ordinal());
        allOrder.isTitle = true;

        TitleModel toolbox = new TitleModel(true, getString(R.string.toolbox), null, 0);
        toolbox.isTitle = true;

        TitleModel myassets = new TitleModel(true, getString(R.string.myassets), null, 0);
        myassets.isTitle = true;


        TitleModel toolPayment = new TitleModel(getString(R.string.toolPayment), true, "", R.mipmap
                .btn_payment, ViewType.VIEW_TYPE_TOOL_PAYMENT.ordinal());
        TitleModel toolUserCard = new TitleModel(getString(R.string.toolUserCard), true, "", R.mipmap
                .btn_membership_card, ViewType.VIEW_TYPE_TOOL_USERCARD.ordinal());

        TitleModel collection = new TitleModel(getString(R.string.myCollection), true, getString
                (R.string.goodsCollectionShopCollection), R.mipmap.btn_goods_collection, ViewType.VIEW_TYPE_COLLECTION.ordinal());

        //我的拼团 隐藏
        //TitleModel spellGroup = new TitleModel(getString(R.string.userGroupOn), true, null, R.mipmap.btn_groupon, ViewType.VIEW_TYPE_SPELL_GROUP.ordinal());
        //常购清单 隐藏
        //TitleModel inventory = new TitleModel(getString(R.string.activityInventory), true, null, R.mipmap.btn_user_buylist, ViewType.VIEW_TYPE_INVENTORY.ordinal());

        TitleModel address = new TitleModel(getString(R.string.consigneeAddress), true, getString
                (R.string.addSlashEdit), R.mipmap.btn_consignee_address, ViewType
                .VIEW_TYPE_CONSIGNEE_ADDRESS.ordinal());
        TitleModel review = new TitleModel(getString(R.string.reviewSlashShow), true, getString(R
                .string.checkReviewSlashShow), R.mipmap.btn_review, ViewType
                .VIEW_TYPE_REVIEW_LIST.ordinal());
        TitleModel profile = new TitleModel(getString(R.string.userProfile), true, "查看/修改", R.mipmap
                .btn_user_info, ViewType.VIEW_TYPE_USER_PROFILE.ordinal());

        //账户安全
        TitleModel security = new TitleModel(getString(R.string.userAccountSecurity), true, "", R.mipmap
                .btn_account_safe, ViewType.VIEW_TYPE_USER_SECURITY.ordinal());

        //推荐店铺 隐藏
        //TitleModel recomm = new TitleModel(getString(R.string.recomme_store), true, null, R.mipmap.btn_user_recommend, ViewType.VIEW_TYPE_RECOMM_STORE.ordinal());


        String merchants_settled_msg = "";
        int viewTypEvent = -1;
//        if ("5".equals(progress)) {
//            merchants_settled_msg = "";
//        }
        if ("0".equals(progress)) {
            merchants_settled_msg = "商家入驻";
            viewTypEvent = ViewType.VIEW_TYPE_MERCHANTS_SETTLED.ordinal();
        } else {
            merchants_settled_msg = "查看入驻进度";
            viewTypEvent = ViewType.VIEW_TYPE_MERCHANTS_SETTLED.ordinal();
        }

        //商家入住
        TitleModel merchants_settled = new TitleModel(merchants_settled_msg, true, null, R.mipmap.btn_bussiness_settled, viewTypEvent);


        //客服电话
        TitleModel service_tel = new TitleModel(getString(R.string.service_tel), true, null, R.mipmap.sev_tel, ViewType.VIEW_TYPE_SERVICE_TEL.ordinal());
        //空白title
        TitleModel nulltitle = new TitleModel("", true, null, 0, 0);

        //我的 储值卡 隐藏
        //TitleModel card = new TitleModel(getString(R.string.mySavingsCard), true, null, R.mipmap.btn_stored_value_card, ViewType.VIEW_TYPE_USER_CARD.ordinal());

        //我的提货卷 隐藏
        //TitleModel gift = new TitleModel(getString(R.string.myGiftCards), true, null, R.mipmap.btn_gift_card, ViewType.VIEW_TYPE_USER_GIFT_CARD.ordinal());

        //我的积分 隐藏
        //TitleModel integral = new TitleModel(getString(R.string.Integral), true, null, R.mipmap.btn_integral, ViewType.VIEW_TYPE_USER_INTEGRAL.ordinal());

        TitleModel logOff = null;
        //待付款
        ButtonModel awaitPay = new ButtonModel("0", getString(R.string.awaitPay), R.mipmap
                .btn_await_pay);
        awaitPay.extraInfo = ViewType.VIEW_TYPE_AWAIT_PAY.getValue();
        //代发货
        ButtonModel awaitShip = new ButtonModel("0", getString(R.string.awaitShip), R.mipmap
                .btn_await_ship);
        awaitShip.extraInfo = ViewType.VIEW_TYPE_AWAIT_SHIP.getValue();
        //代收货
        ButtonModel awaitConfirm = new ButtonModel("0", getString(R.string.awaitConfirm), R
                .mipmap.btn_shipped);
        awaitConfirm.extraInfo = ViewType.VIEW_TYPE_AWAIT_CONFIRM.getValue();
        //待评价
        ButtonModel awaitReview = new ButtonModel("0", getString(R.string.awaitReview), R.mipmap
                .btn_await_review);
        awaitReview.extraInfo = ViewType.VIEW_TYPE_AWAIT_REVIEW.getValue();
        //退款/售后
        ButtonModel awaitCancel = new ButtonModel("0", getString(R.string.awaitCancel), R.mipmap
                .btn_await_return);
        awaitCancel.extraInfo = ViewType.VIEW_TYPE_AWAIT_CANCEL.getValue();

        if (App.getInstance().isLogin() && mModel != null && mModel.data != null) {

            myAssetsModel.bonusCount = mModel.data.bonus_count + "";
            myAssetsModel.money_all_format = mModel.data.info.money_all_format;
            myAssetsModel.pay_point = mModel.data.info.pay_point;

            userModel = mModel.data.info.user_info;
            userModel.bonusCount = mModel.data.bonus_count + "";
            userModel.rankName = mModel.data.user_rank.rank_name;
            userModel.no_read_count = mModel.data.no_read_count;
            userModel.money_all_format = mModel.data.info.money_all_format;

            if (TextUtils.isEmpty(App.getInstance().userCenterBgimage)) {
                userModel.user_center_bgimage = App.getInstance().userCenterBgimage = TextUtils.isEmpty(mModel.data.user_center_bgimage) ? "" : (mModel.data.user_center_bgimage + "?" + System.currentTimeMillis());
            } else {
                userModel.user_center_bgimage = App.getInstance().userCenterBgimage;
            }

            awaitPay.badgeNumber = mModel.data.order_counts.unpayed;
            awaitShip.badgeNumber = mModel.data.order_counts.unshipped;
            awaitConfirm.badgeNumber = mModel.data.order_counts.shipped;
            awaitReview.badgeNumber = mModel.data.order_counts.unevaluate;

            logOff = new TitleModel(getString(R.string.logOff), false, null, R.mipmap.btn_sign_out,
                    ViewType.VIEW_TYPE_LOGOUT.ordinal());
        } else {
            userModel = new UserModel();
            if (mModel != null && mModel.data != null) {
                userModel.user_center_bgimage = App.getInstance().userCenterBgimage;
            }
        }

        mAdapter.data.add(userModel);
        mAdapter.data.add(myAssetsModel);

//        mAdapter.data.add(new LineModel().setHeightDp(10.0f));
        mAdapter.data.add(allOrder);
        mAdapter.data.add(awaitPay);
        mAdapter.data.add(awaitShip);
        mAdapter.data.add(awaitConfirm);
        mAdapter.data.add(awaitReview);
        mAdapter.data.add(awaitCancel);
//    mAdapter.data.add(new LineModel().setHeightDp(10.f));

//        mAdapter.data.add(myassets);
//        mAdapter.data.add(myAssetsModel);/
        mAdapter.data.add(new LineModel().setHeightDp(10.f));

        mAdapter.data.add(toolbox);

        titleModels = new ArrayList<>();

//        titleModels.add(toolPayment);
//        titleModels.add(toolUserCard);

        titleModels.add(collection);

        //titleModels.add(gift);
        //titleModels.add(spellGroup);
        //titleModels.add(card);
        //titleModels.add(inventory);
        //titleModels.add(integral);

        titleModels.add(address);
        titleModels.add(review);
        titleModels.add(profile);
        titleModels.add(security);

        //微分销根据后台返回添加菜单
        if (mModel != null && mModel.data != null) {
            if (mModel.data.is_recommend_reg) {
                //我的推荐 隐藏
                //TitleModel recommend = new TitleModel(getString(R.string.myRecommend), true, null, R.mipmap.btn_recommend, ViewType.VIEW_TYPE_RECOMMEND.ordinal());
                //titleModels.add(recommend);
            }

//            if (mModel.data.is_distrib && mModel.data.shop_info.status.equals("")) {
//                TitleModel distributor = new TitleModel("申请" + mModel.data.app_distrib.getDistributorText, true, null, R.mipmap
//                        .btn_distribution, ViewType.VIEW_TYPE_DISTRIBUTOR_APPLY.ordinal());
//                titleModels.add(distributor);
//            } else if (mModel.data.is_distrib && mModel.data.shop_info.status.equals("1")) {
//                TitleModel distributor = new TitleModel("我的" + mModel.data.app_distrib.distrib_text, true, null, R.mipmap
//                        .btn_distribution, ViewType.VIEW_TYPE_DISTRIBUTOR.ordinal());
//                titleModels.add(distributor);
//            }
        }

        if (!TextUtils.isEmpty(merchants_settled_msg)) {
            titleModels.add(merchants_settled);
        }

        titleModels.add(service_tel);

        titleModels.add(nulltitle);

        //titleModels.add(recomm);

        int titleModelsRemainder;
        if ((titleModelsRemainder = titleModels.size() % 4) != 0) {
            for (int i = 0; i < (4 - titleModelsRemainder); i++) {
                titleModels.add(new TitleModel("", true, null, 0, 0));
            }
        }

        int maxLine = (int) (Math.ceil(titleModels.size() / 4.0f) - 1);
        int tempCount = 0;
        //循环工具标签，没隔4个加一个横线
        for (int i = 4; i < titleModels.size(); i += 4) {
            if (tempCount < maxLine) {
//                if ((tempCount + 1) % 2 == 0) {
//                    titleModels.add(i, new LineModel().setHeightDp(1.5f).setLrPadding(10f).setResourceId(R.color.colorNine));
//                } else {
//                    titleModels.add(i, new LineModel().setHeightDp(0.6f).setLrPadding(10f).setResourceId(R.color.colorEight));
//                }

                //加一条横线
//                titleModels.add(i, new LineModel().setHeightDp(0.5f).setLrPadding(10f).setResourceId(R.color.colorEight));
                i++;
                tempCount++;
            } else {
                break;
            }
        }

        mAdapter.data.addAll(titleModels);
        //退出登录按钮model添加
//        if (logOff != null) {
//            mAdapter.data.add(new LineModel().setHeightDp(10.f));
//            mAdapter.data.add(logOff);
//        }
        mAdapter.data.add(new LineModel().setHeightDp(20.f));
        GuessGoodsModel goodsModel = new GuessGoodsModel();
        GuessTitleModel guessTitleModel = new GuessTitleModel();
        mAdapter.data.add(guessTitleModel);//添加猜你喜欢titlemodel
        mAdapter.data.add(goodsModel);//添加猜你喜欢数据model
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void getIngotView(TextView textView) {
        mTextView_IngotNumber = textView;
    }

    private interface OnLoginCallback {
        void onLoginCallback();
    }

    private class ItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView
                .State state) {
            int position = parent.getChildAdapterPosition(view);
            Object object = mAdapter.data.get(position);
            if (object instanceof TitleModel) {
                TitleModel item = (TitleModel) object;

                if (item.extraInfo == ViewType.VIEW_TYPE_COLLECTION.ordinal() && App.getInstance().is_reachbuy_enable != 1 && App.getInstance().is_freebuy_enable != 1) {
                    outRect.top = Utils.getPixel(getContext(), 10);
                }

                if (item.extraInfo == ViewType.VIEW_TYPE_ALL_ORDER.ordinal()) {
                    outRect.top = Utils.getPixel(getContext(), 10);
                }

                if (item.extraInfo == ViewType.VIEW_TYPE_LOGOUT.ordinal()) {
                    outRect.bottom = Utils.getPixel(getContext(), 30);
                }
//                if (item.extraInfo == ViewType.VIEW_TYPE_USER_PROFILE.ordinal()) {
//                    outRect.bottom = Utils.getPixel(getContext(), 10);
//                }
                if (item.extraInfo == ViewType.VIEW_TYPE_USER_SECURITY.ordinal()) {
                    outRect.bottom = Utils.getPixel(getContext(), 10);
                }

                if (item.extraInfo == ViewType.VIEW_TYPE_RECOMM_STORE.ordinal()) {
                    outRect.bottom = Utils.getPixel(getContext(), 10);
                }
            }
        }
    }

    private class SpanSizeLookUp extends GridLayoutManager.SpanSizeLookup {
        @Override
        public int getSpanSize(int position) {
            switch (mAdapter.getItemViewType(position)) {
                case UserAdapter.BUTTON_VIEW:
                    return 1;
                default:
                    return 5;
            }
        }
    }

    private class SpanSizeLookUpTwo extends GridLayoutManager.SpanSizeLookup {
        @Override
        public int getSpanSize(int position) {
            switch (mAdapter.getItemViewType(position)) {
                case UserTwoAdapter.BUTTON_VIEW:
                    return 4;
                case UserTwoAdapter.TITLE_ITEM_VIEW:
                    return 5;
                default:
                    return 20;
            }
        }
    }

    private void openInventoryActivity() {
        Intent intent = new Intent(getContext(), InventoryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PHONE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callServicePhone();
            } else {
                Toast.makeText(getActivity(), "缺少拨打电话的权限,无法拨打客服电话", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (App.getInstance().isLogin()) {
            getIngotData();
        }
    }


    /***
     * 猜你喜欢 data获取
     */
    private void getLikeData() {

        CommonRequest request = new CommonRequest(Api.API_GUESS_LIKE_URL, HttpWhat.HTTP_INDEX_GUESS_LIKE.getValue());
        request.setUserAgent("szyapp/android");
        if (App.getInstance().isLogin()) {
            request.add("user_id", App.getInstance().userId);
        } else {
            request.add("user_id", 0);
        }
        request.add("cur_page", guess_page);
        request.add("page_size", 12);

        addRequest(request);
    }

    /**
     * 猜你喜欢数据处理
     */
    private void callBackGuessLike(String response) {

        try {
            JSONObject object_data = JSONObject.parseObject(response).getJSONObject("data");
            mGoodsModelList = JSON.parseArray(object_data.getString("list"), GuessGoodsModel.class);

            if (guess_page > 1) {
                if (mGoodsModelList.size() > 0) {
                    mAdapter.mGoodsModelList.addAll(mGoodsModelList);
                } else {
                    guess_more = false;
                }
            } else {
                if (mGoodsModelList.size() > 0) {
                    mAdapter.mGoodsModelList = mGoodsModelList;
                } else {
                    mGoodsModelList.clear();
                    mAdapter.mGoodsModelList = mGoodsModelList;
                    guess_more = false;
                }
            }
            mAdapter.notifyDataSetChanged();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
