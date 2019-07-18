package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.CartEditNumberActivity;
import com.szy.yishopcustomer.Activity.CheckoutActivity;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.GoodsBonusActivity;
import com.szy.yishopcustomer.Activity.GoodsListActivity;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.OrderDetailActivity;
import com.szy.yishopcustomer.Activity.OrderListActivity;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Adapter.CartAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Other.GoodsEventModel;
import com.szy.yishopcustomer.Other.GoodsNumberEvent;
import com.szy.yishopcustomer.Other.MapKeyComparator;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.CartIndex.ActInfo;
import com.szy.yishopcustomer.ResponseModel.CartIndex.FullCutModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.GiftModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.GoodsModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.InvalidListModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.InvalidTitleModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.ResponseCartModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.ShopListModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.SkuListBean;
import com.szy.yishopcustomer.ResponseModel.CartIndex.StoreTipModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.UnpaidOrderModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.UnpaidOrderTitleModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.UnpayedListMoreModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.WholeModel;
import com.szy.yishopcustomer.ResponseModel.CartSelectModel;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.EmptyItemModel;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * Created by 宗仁 on 2016/3/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CartFragment extends YSCBaseFragment implements OnPullListener, OnEmptyViewClickListener {

    @BindView(R.id.image_checkbox)
    ImageView mSelectAllImageView;
    @BindView(R.id.cart_toolbar)
    Toolbar mCartToolBar;
    @BindView(R.id.fragment_cart_total_price)
    TextView mGoodsSelectPrice;
    @BindView(R.id.fragment_cart_checkout_button)
    TextView mCheckoutButton;
    @BindView(R.id.fragment_cart_bottom_layout)
    LinearLayout mBottomLayout;
    @BindView(R.id.fragment_cart_delete_all_button)
    TextView mClearButton;
    @BindView(R.id.fragment_cart_goods_listView_layout)
    PullableLayout mPullableLayout;
    @BindView(R.id.login_layout)
    LinearLayout mLoginLayout;
    @BindView(R.id.login_button)
    Button mLoginButton;

    private CartAdapter mCartAdapter;
    private ResponseCartModel mModel;
    private int mGoodsNumberDelta;

    //店铺头部悬浮相关
    @BindView(R.id.fragment_cart_goods_listView)
    CommonRecyclerView mGoodsListView;
    @BindView(R.id.shop_layout)
    LinearLayout mShopLayout;
    @BindView(R.id.image_checkbox_s)
    ImageView mShopCheckBox;
    @BindView(R.id.item_cart_shop_name_textView)
    TextView mShopName;
    @BindView(R.id.item_cart_shop_grab_bonus)
    TextView mShopGrapBonus;
    private int mCurrentPosition = 0;//当前悬浮条信息来源的位置
    private int mSuspensionHeight;
    LinearLayoutManager layoutManager;
    int tag = 0;

    List<Object> invalidLists;

    private ArrayList<Integer> titlePositions;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (RequestCode.valueOf(requestCode)) {
            case REQUEST_CODE_TAKE_BONUS:
                if (resultCode == Activity.RESULT_OK) {
                    refresh();
                }
                break;
            case REQUEST_CODE_CHANGE_NUMBER:
                if (resultCode == Activity.RESULT_OK) {
                    refresh();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCanceled(PullableComponent pullableComponent) {
    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        if (pullableComponent.getSide().toString().equals("TOP")) {
            refresh();
        }
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {
    }

    @Override
    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        int extrainfo = Utils.getExtraInfoOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_GOODS:
                openGoodsOfPosition(position, extrainfo);
                break;
            case VIEW_TYPE_INVALID:
                openInvalidGoodsOfPosition();
                break;
            case VIEW_TYPE_GIFT:
                openGiftOfPosition(position);
                break;
            case VIEW_TYPE_SHOP:
                openShopOfPosition(position);
                break;
            case VIEW_TYPE_SELECT_GOODS:
                selectGoodsOfPosition(position, extrainfo);
                break;
            case VIEW_TYPE_SELECT_WHOLE:
                selectWholeOfPosition(position);
                break;
            case VIEW_TYPE_SELECT_WHOLE_LIST:
                selectWholeListOfPosition(position, extrainfo);
                break;
            case VIEW_TYPE_SELECT_SHOP:
                selectShopOfPosition(position);
                break;
            case VIEW_TYPE_SELECT_ALL:
                if (!Utils.isNull(mModel)) {
                    selectAll();
                }
                break;
            case VIEW_TYPE_CHECKOUT:
                goCheckOut();
                break;
            case VIEW_TYPE_LOGIN:
                openLoginActivity();
                break;
            case VIEW_TYPE_ORDER:
                openOrderOfPosition(position);
                break;
            case VIEW_TYPE_ORDER_LIST:
                openOrderListActivity(Macro.ORDER_TYPE_UNPAID);
                break;
            case VIEW_TYPE_CLEAR:
                clearCart();
                break;
            case VIEW_TYPE_CLEAR_INVALID:
                clearInvalidGoods();
                break;
            case VIEW_TYPE_GOODS_COLLECTION:
                collectInvalidGoods();
                break;
            case VIEW_TYPE_DELETE_GOODS:
                deleteGoodsOfPosition(position);
                break;
            case VIEW_TYPE_EMPTY:
                goIndex();
                break;
            case VIEW_TYPE_MINUS:
                reduceGoodsNumberOfPosition(position, extrainfo);
                break;
            case VIEW_TYPE_PLUS:
                increaseGoodsNumberOfPosition(position, extrainfo);
                break;
            case VIEW_TYPE_BONUS:
                openBonusActivity(position);
                break;
            case VIEW_TYPE_EDIT:
                openEditNumber(position, extrainfo);
                break;
            case VIEW_TYPE_EDIT_WHOLE_LIST:
                openEditNumberWhole(position, extrainfo);
                break;
            case VIEW_FULLCUT_LIST:
                openFullCutList(extrainfo);
                break;
            default:
                super.onClick(view);
                break;
        }
    }

    public void openFullCutList(int extrainfo) {
        Intent intent = new Intent(getContext(), GoodsListActivity.class);
        intent.putExtra(Key.KEY_API.getValue(), Api.API_FULLCUT_LIST + extrainfo);
        intent.putExtra(Key.KEY_ACT_ID.getValue(), extrainfo + "");
        startActivity(intent);
    }

    @Override
    public void onConfirmDialogCanceled(int viewType, int position, int extraInfo) {
        dismissConfirmDialog();
    }

    @Override
    public void onConfirmDialogConfirmed(int viewType, int position, int extraInfo) {
        switch (ViewType.valueOf(viewType)) {
            case VIEW_TYPE_CLEAR_CONFIRM:
                clearCartConfirm();
                break;
            case VIEW_TYPE_DELETE_SELECTED_GOODS_CONFIRM:
                deleteSelectedGoods();
                break;
            case VIEW_TYPE_DELETE_GOODS_CONFIRM:
                deleteGoodsOfPositionConfirm(position);
                break;
            case VIEW_TYPE_CLEAR_INVALID:
                deleteGoods(getInvalidIds());
                break;
        }
        dismissConfirmDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mGoodsListView.setAdapter(mCartAdapter);
        layoutManager = new LinearLayoutManager(getContext());
        mGoodsListView.setLayoutManager(layoutManager);
        mGoodsListView.setEmptyViewClickListener(this);
        mGoodsListView.setHasFixedSize(true);

        Utils.setViewTypeForTag(mSelectAllImageView, ViewType.VIEW_TYPE_SELECT_ALL);
        mSelectAllImageView.setOnClickListener(this);

        Utils.setViewTypeForTag(mCheckoutButton, ViewType.VIEW_TYPE_CHECKOUT);
        mCheckoutButton.setOnClickListener(this);

        Utils.setViewTypeForTag(mClearButton, ViewType.VIEW_TYPE_CLEAR);
        mClearButton.setOnClickListener(this);

        mPullableLayout.topComponent.setOnPullListener(this);

        if (App.getInstance().isLogin()) {
            mLoginLayout.setVisibility(View.GONE);
        } else {
            mLoginLayout.setVisibility(View.VISIBLE);
        }

        Utils.setViewTypeForTag(mLoginButton, ViewType.VIEW_TYPE_LOGIN);
        mLoginButton.setOnClickListener(this);

        if (getArguments() != null) {
            if (getArguments().getBoolean("type")) {
                mCartToolBar.setNavigationIcon(R.mipmap.btn_back_dark);//设置导航栏图标
                mCartToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().finish();
                    }
                });
            }
        }

        mGoodsSelectPrice.setText(" 0");
        refresh();
        return view;
    }

    private void updateSuspensionBar() {
        Object obj = mCartAdapter.data.get(mCurrentPosition);
        if (obj instanceof ShopListModel) {
            ShopListModel object = (ShopListModel) obj;
            mShopName.setText(object.shop_info.shop_name);
            if (Utils.isNull(object.bonus_list)) {
                mShopGrapBonus.setVisibility(View.GONE);
            } else {
                mShopGrapBonus.setVisibility(View.VISIBLE);
            }

            mShopCheckBox.setSelected(object.select);

            Utils.setViewTypeForTag(mShopName, ViewType.VIEW_TYPE_SHOP);
            Utils.setPositionForTag(mShopName, mCurrentPosition);
            mShopName.setOnClickListener(this);

            Utils.setViewTypeForTag(mShopGrapBonus, ViewType.VIEW_TYPE_BONUS);
            Utils.setPositionForTag(mShopGrapBonus, mCurrentPosition);
            mShopGrapBonus.setOnClickListener(this);

            Utils.setViewTypeForTag(mShopCheckBox, ViewType.VIEW_TYPE_SELECT_SHOP);
            Utils.setPositionForTag(mShopCheckBox, mCurrentPosition);
            mShopCheckBox.setOnClickListener(this);
        }
    }

    @Override
    public void onEmptyViewClicked() {
        refresh();
    }

    @Override
    public void onOfflineViewClicked() {
        refresh();
    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_UPDATE_CART_NUMBER:
                if (!event.isFrom(CartFragment.this)) {
//                    refresh();
                }
                break;
            case EVENT_CHECKOUT_SUCCEED:
                refresh();
                break;
            case EVENT_LOGIN:
                onLogin();
                break;
            case EVENT_LOGOUT:
                onLogout();
                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_CART_LIST:
                refreshFailed();
                break;
            default:
                super.onRequestFailed(what, response);
                break;
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_CART_LIST:
                refreshCallback(response);
                break;
            case HTTP_CART_SELECT:
                cartSelectCallBack(response);
                break;
            case HTTP_GOODS_NUMBER_CHANGE:
                updateGoodsNumberCallback(response);
                break;
            case HTTP_GOODS_DELETE:
                deleteGoodsCallback(response);
                break;
            case HTTP_GO_CHECKOUT:
                goCheckOutCallBack(response);
                mCheckoutButton.setEnabled(true);
                break;
            case HTTP_COLLECT:
                collectInvalidGoodsCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private String shop_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_cart;
        mCartAdapter = new CartAdapter();
        mCartAdapter.onClickListener = this;

        //用来存储所有店铺标题行的左边
        titlePositions = new ArrayList<>();

        shop_id = getActivity().getIntent().getStringExtra("shop_id");
    }

    public void openBonusActivity(int position) {
        Intent intent = new Intent();
        ShopListModel shopListModel = (ShopListModel) mCartAdapter.data.get(position);

        intent.putExtra(Key.KEY_SHOP_NAME.getValue(), shopListModel.shop_info.shop_name);
        intent.putParcelableArrayListExtra(Key.KEY_BONUS_LIST.getValue(), shopListModel.bonus_list);
        intent.setClass(getActivity(), GoodsBonusActivity.class);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_TAKE_BONUS.getValue());
    }

    public void openEditNumber(int position, int extrainfo) {
        Intent intent = new Intent();
        Object obj = mCartAdapter.data.get(position);
        if (obj instanceof GoodsModel) {
            GoodsModel goodsModel = (GoodsModel) obj;
            intent.putExtra(Key.KEY_GOODS_NUMBER.getValue(), goodsModel.goods_number);
            intent.putExtra(Key.KEY_SKU_ID.getValue(), goodsModel.sku_id);
            intent.setClass(getActivity(), CartEditNumberActivity.class);
            //startActivity(intent);
            startActivityForResult(intent, RequestCode.REQUEST_CODE_CHANGE_NUMBER.getValue());

        } else if (obj instanceof ActInfo) {
            ActInfo goodsModel = (ActInfo) obj;
            intent.putExtra(Key.KEY_GOODS_NUMBER.getValue(), goodsModel.goods_number);
            intent.putExtra(Key.KEY_SKU_ID.getValue(), goodsModel.sku_ids);
            intent.setClass(getActivity(), CartEditNumberActivity.class);
            //startActivity(intent);
            startActivityForResult(intent, RequestCode.REQUEST_CODE_CHANGE_NUMBER.getValue());
        } else if (obj instanceof FullCutModel) {
            FullCutModel fullCutModel = (FullCutModel) obj;
            GoodsModel goodsModel = fullCutModel.goods_list.get(extrainfo + "");
            intent.putExtra(Key.KEY_GOODS_NUMBER.getValue(), goodsModel.goods_number);
            intent.putExtra(Key.KEY_SKU_ID.getValue(), goodsModel.sku_id);
            intent.setClass(getActivity(), CartEditNumberActivity.class);
            //startActivity(intent);
            startActivityForResult(intent, RequestCode.REQUEST_CODE_CHANGE_NUMBER.getValue());
        }


    }

    public void openEditNumberWhole(int position, int itemPosition) {
        Intent intent = new Intent();
        WholeModel goodsModel = (WholeModel) mCartAdapter.data.get(position);
        if (goodsModel.sortSkuList != null) {
            SkuListBean skuListBean = goodsModel.sortSkuList.get(itemPosition);

            intent.putExtra(Key.KEY_GOODS_NUMBER.getValue(), skuListBean.goods_number);
            intent.putExtra(Key.KEY_SKU_ID.getValue(), skuListBean.sku_id);
            intent.setClass(getActivity(), CartEditNumberActivity.class);
            //startActivity(intent);
            startActivityForResult(intent, RequestCode.REQUEST_CODE_CHANGE_NUMBER.getValue());
        }
    }

    public void openGoodsActivity(String skuId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_SKU_ID.getValue(), skuId);
        intent.setClass(getActivity(), GoodsActivity.class);
        startActivity(intent);
    }

    public void openGoodsActivityForGoodId(String goodid) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_GOODS_ID.getValue(), goodid);
        intent.setClass(getActivity(), GoodsActivity.class);
        startActivity(intent);
    }

    public void openLoginActivity() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    public void openOrderDetailActivity(String orderId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_ORDER_ID.getValue(), orderId);
        intent.setClass(getActivity(), OrderDetailActivity.class);
        startActivity(intent);
    }

    public void openOrderListActivity(String status) {
        Intent intent = new Intent(getContext(), OrderListActivity.class);
        intent.putExtra(Key.KEY_ORDER_STATUS.getValue(), status);
        startActivity(intent);
    }

    public void openShopActivity(String shopId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), shopId);
        intent.setClass(getActivity(), ShopActivity.class);
        startActivity(intent);
    }

    private boolean isFistLoad = true;

    public void refresh() {
        CommonRequest mCartList = new CommonRequest(Api.API_CART_INDEX, HttpWhat.HTTP_CART_LIST.getValue());
        if (isFistLoad) {
            mCartList.alarm = false;
            isFistLoad = false;
        }

        if (!TextUtils.isEmpty(shop_id)) {
            mCartList.add("shop_id", shop_id);
        }
        addRequest(mCartList);
    }

    private void cartSelect() {
        CommonRequest request = new CommonRequest(Api.API_CART_SELECT, HttpWhat.HTTP_CART_SELECT
                .getValue(), RequestMethod.POST);
        request.setAjax(true);
        request.add("cart_ids", getSelectedCartIds());
        addRequest(request);
    }

    private void cartSelectCallBack(String response) {

        HttpResultManager.resolve(response, CartSelectModel.class, new HttpResultManager.HttpResultCallBack<CartSelectModel>() {
            @Override
            public void onSuccess(CartSelectModel model) {

                mModel.data.cart.shop_list = model.cart.shop_list;
                mModel.data.cart.select_goods_amount = model.data.select_goods_amount;
                mModel.data.cart.select_goods_number = model.data.select_goods_number;
                mModel.data.cart.select_goods_amount_format = model.data.select_goods_amount_format;

                mModel.data.cart.select_shop_amount = model.cart.select_shop_amount;
                mModel.data.cart.submit_enable = model.cart.submit_enable;
                mModel.data.cart.show_start_price_ids = model.cart.show_start_price_ids;
            }
        });

        setUpAdapterData();
        updateCheckoutLayout();
    }

    private void clearCart() {
        String selectedCartIds = getSelectedCartIds();
        if (Utils.isNull(selectedCartIds)) {
            showConfirmDialog(R.string.emptyShoppingCart, ViewType.VIEW_TYPE_CLEAR_CONFIRM
                    .ordinal());
        } else {
            showConfirmDialog(R.string.deleteCartGoodsAlert, ViewType
                    .VIEW_TYPE_DELETE_SELECTED_GOODS_CONFIRM.ordinal());
        }
    }

    private void clearInvalidGoods() {
        showConfirmDialog(R.string.emptyInvalidGoods, ViewType.VIEW_TYPE_CLEAR_INVALID.ordinal());
    }

    //将失效商品移入收藏夹
    private void collectInvalidGoods() {
        CommonRequest request = new CommonRequest(Api.API_COLLECT_BATCH, HttpWhat
                .HTTP_COLLECT.getValue(), RequestMethod.POST);
        request.add("goods_ids", getInvalidGoodsIds());
        addRequest(request);
    }

    private void collectInvalidGoodsCallback(String response) {
        HttpResultManager.resolve(response, ResponseCartModel.class, new HttpResultManager.HttpResultCallBack<ResponseCartModel>() {
            @Override
            public void onSuccess(ResponseCartModel back) {
                Toast.makeText(getActivity(), back.message, Toast.LENGTH_SHORT).show();
                deleteGoods(getInvalidIds());
            }
        }, true);
    }

    private void clearCartConfirm() {
        deleteGoods(getCartIds());
    }

    private void deleteGoods(String cartIds) {
        CommonRequest mDeleteGoodsRequest = new CommonRequest(Api.API_CART_DELETE, HttpWhat
                .HTTP_GOODS_DELETE.getValue(), RequestMethod.POST);
        mDeleteGoodsRequest.add("cart_ids", cartIds);
        mDeleteGoodsRequest.setAjax(true);
        addRequest(mDeleteGoodsRequest);
    }

    private void deleteGoodsCallback(String response) {
        HttpResultManager.resolve(response, ResponseCartModel.class, new HttpResultManager.HttpResultCallBack<ResponseCartModel>() {
            @Override
            public void onSuccess(ResponseCartModel back) {
                App.setCartNumber(back.data.cart.goods_number, CartFragment.this);
                mModel.data.cart = back.data.cart;
                setUpAdapterData();
                updateSelectAllImageView();
                updateClearButton();
                updateCheckoutLayout();
                postGoodsNumberUpdated();
            }
        });
    }

    private void deleteGoodsOfPosition(int position) {
        showConfirmDialog(R.string.confirmDeleteGoods, ViewType.VIEW_TYPE_DELETE_GOODS_CONFIRM
                .ordinal(), position);
    }

    private void deleteGoodsOfPositionConfirm(int position) {
        Object obj = mCartAdapter.data.get(position);
        if (obj instanceof GoodsModel) {
            GoodsModel goods = (GoodsModel) mCartAdapter.data.get(position);
            deleteGoods(goods.cart_id);
        } else if (obj instanceof ActInfo) {
            ActInfo goods = (ActInfo) mCartAdapter.data.get(position);
            deleteGoods(goods.cart_ids);
        }
    }

    private void deleteSelectedGoods() {
        String selectedCartIds = getSelectedCartIds();
        deleteGoods(selectedCartIds);
    }

    private String getCartIds() {
        List<String> list = new ArrayList<>();
        if (!Utils.isNull(mModel.data.cart.shop_list)) {
            for (ShopListModel shop : mModel.data.cart.shop_list) {
                if (!Utils.isNull(shop.goods_list_copy)) {
                    for (Object obj : shop.goods_list_copy) {
                        if (obj instanceof ActInfo) {

                            ActInfo actInfo = (ActInfo) obj;
                            String[] cartids = actInfo.cart_ids.split(",");
                            list.addAll(Arrays.asList(cartids));
                        } else if (obj instanceof FullCutModel) {
                            FullCutModel fullCutModel = (FullCutModel) obj;

                            for (Map.Entry<String, GoodsModel> entry : fullCutModel.goods_list
                                    .entrySet()) {
                                list.add(entry.getValue().cart_id);
                            }
                        } else if (obj instanceof GoodsModel) {
                            GoodsModel goods = (GoodsModel) obj;
                            list.add(goods.cart_id);
                        }
                    }
                }
                //批发商品cart_id
                if (!Utils.isNull(shop.whole_list)) {
                    for (Map.Entry<String, WholeModel> entry : shop.whole_list
                            .entrySet()) {
                        for (Map.Entry<String, SkuListBean> entry2 : entry.getValue().sku_list
                                .entrySet()) {
                            list.add(entry2.getValue().cart_id);
                        }
                    }
                }
            }
        }
        return Utils.join(list, ",");
    }

    private String getInvalidIds() {
        List<String> list = new ArrayList<>();

        for (Object object : invalidLists) {
            if (object instanceof InvalidListModel) {
                InvalidListModel invalidListModel = (InvalidListModel) object;
                list.add(invalidListModel.cart_id);
            } else if (object instanceof ActInfo) {
                ActInfo actInfo = (ActInfo) object;

                String[] cartids = actInfo.cart_ids.split(",");
                list.addAll(Arrays.asList(cartids));
            }
        }
        return Utils.join(list, ",");
    }


    private String getInvalidGoodsIds() {
        List<String> list = new ArrayList<>();

        for (Object object : invalidLists) {
            if (object instanceof InvalidListModel) {
                InvalidListModel invalidListModel = (InvalidListModel) object;
                list.add(invalidListModel.cart_id);
            } else if (object instanceof ActInfo) {
                ActInfo actInfo = (ActInfo) object;

                String[] cartids = actInfo.cart_ids.split(",");
                list.addAll(Arrays.asList(cartids));
            }
        }
        return Utils.join(list, ",");
    }

    private String getSelectedCartIds() {
        List<String> list = new ArrayList<>();
        try {
            if (!Utils.isNull(mModel.data.cart.shop_list)) {
                for (ShopListModel shop : mModel.data.cart.shop_list) {
                    if (!Utils.isNull(shop.goods_list_copy)) {
                        for (Object obj : shop.goods_list_copy) {
                            if (obj instanceof ActInfo) {
                                ActInfo actInfo = (ActInfo) obj;
                                if (actInfo.select) {
                                    String[] cartids = actInfo.cart_ids.split(",");
                                    list.addAll(Arrays.asList(cartids));
                                }
                            } else if (obj instanceof FullCutModel) {
                                FullCutModel fullCutModel = (FullCutModel) obj;

                                for (Map.Entry<String, GoodsModel> entry : fullCutModel.goods_list
                                        .entrySet()) {
                                    if (entry.getValue().select) {
                                        list.add(entry.getValue().cart_id);
                                    }
                                }
                            } else if (obj instanceof GoodsModel) {
                                GoodsModel goods = (GoodsModel) obj;
                                if (goods.select) {
                                    list.add(goods.cart_id);
                                }
                            }
                        }
                    }

                    if (!Utils.isNull(shop.whole_list)) {
                        for (WholeModel goods : shop.whole_list.values()) {
                            for (SkuListBean sku_list : goods.sku_list.values()) {
                                if (sku_list.select) {
                                    list.add(sku_list.cart_id);
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
        }

        return Utils.join(list, ",");
    }

    private void goCheckOut() {
        CommonRequest mGoCheckoutList = new CommonRequest(Api.API_GO_CHECKOUT, HttpWhat
                .HTTP_GO_CHECKOUT.getValue(), RequestMethod.POST);
        mGoCheckoutList.setAjax(true);
        mGoCheckoutList.alarm = true;
        addRequest(mGoCheckoutList);

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
                Toast.makeText(getActivity(), responseModel.message, Toast.LENGTH_LONG).show();
                openLoginActivity();
            }

        }, true);
    }

    private void goIndex() {
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_INDEX.getValue()));
        startActivity(new Intent().setClass(getActivity(), RootActivity.class));
        this.finish();
    }

    private void increaseGoodsNumberOfPosition(int position, int extrainfo) {
        Object object = mCartAdapter.data.get(position);
        if (object instanceof GoodsModel) {
            GoodsModel goods = (GoodsModel) mCartAdapter.data.get(position);
            int goods_number = Integer.valueOf(goods.goods_number);
            if (goods_number >= Integer.valueOf(goods.goods_max_number)) {
                Utils.makeToast(getActivity(), R.string.canNotAdd);
            } else {
                goods.goods_number = String.valueOf(goods_number + 1);
                mGoodsNumberDelta = 1;
                updateGoodsNumber(goods.goods_number, goods.sku_id);
            }

        } else if (object instanceof WholeModel) {
            WholeModel goods = (WholeModel) mCartAdapter.data.get(position);
            for (SkuListBean skuListBean : goods.sku_list.values()) {
                if (skuListBean.cart_id.equals(extrainfo + "")) {
                    int goods_number = Integer.valueOf(skuListBean.goods_number);
                    if (goods_number >= Integer.valueOf(skuListBean.goods_max_number)) {
                        Utils.makeToast(getActivity(), R.string.canNotAdd);
                    } else {
                        skuListBean.goods_number = String.valueOf(goods_number + 1);
                        mGoodsNumberDelta = 1;
                        updateGoodsNumber(skuListBean.goods_number, skuListBean.sku_id);
                    }
                    break;
                }
            }
        } else if (object instanceof ActInfo) {
            ActInfo actInfo = (ActInfo) object;
            int goods_number = Integer.valueOf(actInfo.goods_number);
            if (goods_number >= Integer.valueOf(actInfo.act_max_number)) {
                Utils.makeToast(getActivity(), R.string.canNotAdd);
            } else {
                actInfo.goods_number = String.valueOf(goods_number + 1);
                mGoodsNumberDelta = 1;
                updateGoodsNumber(actInfo.goods_number, actInfo.sku_ids);
            }
        } else if (object instanceof FullCutModel) {
            FullCutModel fullCutModel = (FullCutModel) object;

            GoodsModel goods = fullCutModel.goods_list.get(extrainfo + "");
            int goods_number = Integer.valueOf(goods.goods_number);
            if (goods_number >= Integer.valueOf(goods.goods_max_number)) {
                Utils.makeToast(getActivity(), R.string.canNotAdd);
            } else {
                goods.goods_number = String.valueOf(goods_number + 1);
                mGoodsNumberDelta = 1;
                updateGoodsNumber(goods.goods_number, goods.sku_id);
            }
        }
    }

    private void onLogin() {
        mLoginLayout.setVisibility(View.GONE);
        refresh();
    }

    private void onLogout() {
        mLoginLayout.setVisibility(View.VISIBLE);
        refresh();
    }

    private void openCheckoutActivity() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), CheckoutActivity.class);
        startActivity(intent);
    }

    private void openGiftOfPosition(int position) {
        GiftModel giftModel = (GiftModel) mCartAdapter.data.get(position);
        openGoodsActivity(String.valueOf(giftModel.gift_sku_id));
    }

    private void openInvalidGoodsOfPosition() {
        Utils.makeToast(getActivity(), "此商品已下架！");
    }


    private void openGoodsOfPosition(int position, int extrainfo) {
        Object obj = mCartAdapter.data.get(position);
        if (obj instanceof GoodsModel) {
            GoodsModel goods = (GoodsModel) obj;
            openGoodsActivity(goods.sku_id);
        } else if (obj instanceof WholeModel) {
            WholeModel goods = (WholeModel) obj;
            openGoodsActivityForGoodId(goods.goods_id);
        } else if (obj instanceof ActInfo) {
            ActInfo actInfo = (ActInfo) obj;
            GoodsModel goodsModel = actInfo.goods_list.get(extrainfo + "");
            openGoodsActivity(goodsModel.sku_id);
        } else if (obj instanceof FullCutModel) {
            FullCutModel fullCutModel = (FullCutModel) obj;
            GoodsModel goodsModel = fullCutModel.goods_list.get(extrainfo + "");
            openGoodsActivity(goodsModel.sku_id);
        }
    }

    private void openOrderOfPosition(int position) {
        UnpaidOrderModel unpaidOrderModel = (UnpaidOrderModel) mCartAdapter.data.get(position);
        openOrderDetailActivity(unpaidOrderModel.order_id);
    }

    private void openShopOfPosition(int position) {
        if (mCartAdapter.data.get(position) instanceof ShopListModel) {
            ShopListModel shop = (ShopListModel) mCartAdapter.data.get(position);
            openShopActivity(shop.shop_info.shop_id);
        } else if (mCartAdapter.data.get(position) instanceof StoreTipModel) {
            StoreTipModel shop = (StoreTipModel) mCartAdapter.data.get(position);
            openShopActivity(shop.shopid);
        }
    }

    private void postGoodsNumberUpdated() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                List<GoodsEventModel> goodsList = new ArrayList<>();
                if (!Utils.isNull(mModel.data.cart.shop_list)) {
                    for (ShopListModel entry : mModel.data.cart.shop_list) {
                        if (!Utils.isNull(entry.goods_list_copy)) {

                            for (Object obj : entry
                                    .goods_list_copy) {

                                if (obj instanceof ActInfo) {

                                    ActInfo actInfo = (ActInfo) obj;
                                    for (Map.Entry<String, GoodsModel> entry2 : actInfo.goods_list.entrySet()) {
                                        GoodsEventModel goodsEventModel = new GoodsEventModel();
                                        goodsEventModel.goodsId = entry2.getValue().goods_id;
                                        //这里使用套餐的数量
                                        goodsEventModel.cartNumber = Integer.valueOf(actInfo.goods_number);
                                        goodsList.add(goodsEventModel);
                                    }
                                } else if (obj instanceof FullCutModel) {
                                    FullCutModel fullCutModel = (FullCutModel) obj;

                                    for (Map.Entry<String, GoodsModel> entry3 : fullCutModel.goods_list
                                            .entrySet()) {
                                        GoodsEventModel goodsEventModel = new GoodsEventModel();
                                        goodsEventModel.goodsId = entry3.getValue().goods_id;
                                        //这里使用套餐的数量
                                        goodsEventModel.cartNumber = Integer.valueOf(entry3.getValue().goods_number);
                                        goodsList.add(goodsEventModel);
                                    }
                                } else if (obj instanceof GoodsModel) {
                                    GoodsModel goodsModel = (GoodsModel) obj;
                                    GoodsEventModel goodsEventModel = new GoodsEventModel();
                                    goodsEventModel.goodsId = goodsModel.goods_id;
                                    if (TextUtils.isEmpty(goodsModel.goods_number)) {
                                        goodsModel.goods_number = "";
                                    }
                                    goodsEventModel.cartNumber = Integer.valueOf(goodsModel.goods_number);
                                    goodsList.add(goodsEventModel);
                                }
                            }
                        }
                    }
                }

                EventBus.getDefault().post(new GoodsNumberEvent(EventWhat
                        .EVENT_UPDATE_GOODS_NUMBER.getValue(), this, goodsList));
            }
        });

    }

    private void reduceGoodsNumberOfPosition(int position, int extrainfo) {
        Object object = mCartAdapter.data.get(position);
        if (object instanceof GoodsModel) {
            GoodsModel goods = (GoodsModel) mCartAdapter.data.get(position);
            int goods_number = Integer.valueOf(goods.goods_number);
            if (goods_number - 1 < goods.goods_min_number) {
                Utils.makeToast(getActivity(), R.string.canNotReduce);
            } else {
                goods.goods_number = String.valueOf(goods_number - 1);
                mGoodsNumberDelta = -1;
                updateGoodsNumber(goods.goods_number, goods.sku_id);
            }
        } else if (object instanceof WholeModel) {
            WholeModel goods = (WholeModel) mCartAdapter.data.get(position);
            for (SkuListBean skuListBean : goods.sku_list.values()) {
                if (skuListBean.cart_id.equals(extrainfo + "")) {
                    int goods_number = Integer.valueOf(skuListBean.goods_number);
                    if (goods_number - 1 < skuListBean.goods_min_number) {
                        Utils.makeToast(getActivity(), R.string.canNotReduce);
                    } else {
                        skuListBean.goods_number = String.valueOf(goods_number - 1);
                        mGoodsNumberDelta = -1;
                        updateGoodsNumber(skuListBean.goods_number, skuListBean.sku_id);
                    }
                    break;
                }
            }
        } else if (object instanceof ActInfo) {
            ActInfo actInfo = (ActInfo) object;
            int goods_number = Integer.valueOf(actInfo.goods_number);
            if (goods_number - 1 < actInfo.act_min_number) {
                Utils.makeToast(getActivity(), R.string.canNotReduce);
            } else {
                actInfo.goods_number = String.valueOf(goods_number - 1);
                mGoodsNumberDelta = -1;
                updateGoodsNumber(actInfo.goods_number, actInfo.sku_ids);
            }
        } else if (object instanceof FullCutModel) {
            FullCutModel fullCutModel = (FullCutModel) object;
            GoodsModel goods = fullCutModel.goods_list.get(extrainfo + "");

            int goods_number = Integer.valueOf(goods.goods_number);
            if (goods_number - 1 < goods.goods_min_number) {
                Utils.makeToast(getActivity(), R.string.canNotReduce);
            } else {
                goods.goods_number = String.valueOf(goods_number - 1);
                mGoodsNumberDelta = -1;
                updateGoodsNumber(goods.goods_number, goods.sku_id);
            }
        }
    }

    private void refreshCallback(String response) {
        mPullableLayout.topComponent.finish(Result.SUCCEED);
        HttpResultManager.resolve(response, ResponseCartModel.class, new HttpResultManager.HttpResultCallBack<ResponseCartModel>() {
            @Override
            public void onSuccess(ResponseCartModel back) {

                mModel = back;

                setUpAdapterData();
                updateClearButton();
                updateCheckoutLayout();
                App.setCartNumber(mModel.data.context.cart.goods_count, CartFragment.this);
                updateDeleteButton();
                updateSelectAllImageView();

                mGoodsSelectPrice.setText(mModel.data.cart.select_goods_amount_format);
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
            }
        });

    }

    private void refreshFailed() {
        mPullableLayout.topComponent.finish(Result.FAILED);
        mGoodsListView.showOfflineView();
        mClearButton.setVisibility(View.GONE);
        mBottomLayout.setVisibility(View.GONE);
    }

    private void selectAll() {
        mSelectAllImageView.setSelected(!mSelectAllImageView.isSelected());
        boolean selected = mSelectAllImageView.isSelected();
        for (Object object : mCartAdapter.data) {
            if (object instanceof ShopListModel) {
                ShopListModel shop = (ShopListModel) object;
                shop.select = selected;
            } else if (object instanceof GoodsModel) {
                GoodsModel goods = (GoodsModel) object;
                goods.select = selected;
            } else if (object instanceof ActInfo) {
                ActInfo goods = (ActInfo) object;
                goods.select = selected;
            } else if (object instanceof WholeModel) {
                WholeModel goods = (WholeModel) object;
                goods.select = selected;
                if (goods.sku_list != null) {
                    for (SkuListBean skuListBean : goods.sku_list.values()) {
                        skuListBean.select = goods.select;
                    }
                }
            } else if (object instanceof FullCutModel) {
                FullCutModel fullCutModel = (FullCutModel) object;

                for (Map.Entry<String, GoodsModel> entry3 : fullCutModel.goods_list
                        .entrySet()) {
                    entry3.getValue().select = selected;
                }
            }
        }

        notifyDataSetChanged();
        updateDeleteButton();
        cartSelect();

        updateSuspensionBar();
    }

    private void selectGoodsOfPosition(int position, int extrainfo) {
        Object object = mCartAdapter.data.get(position);
        if (object instanceof GoodsModel) {
            GoodsModel good = (GoodsModel) object;
            good.select = !good.select;

        } else if (object instanceof ActInfo) {
            ActInfo goods = (ActInfo) object;
            goods.select = !goods.select;
        } else if (object instanceof FullCutModel) {
            FullCutModel fullCutModel = (FullCutModel) object;
            GoodsModel goods = fullCutModel.goods_list.get(extrainfo + "");
            goods.select = !goods.select;
        }

        checkAll(position, false);
    }

    private void selectWholeOfPosition(int position) {
        WholeModel goods = (WholeModel) mCartAdapter.data.get(position);
        goods.select = !goods.select;

        checkAll(position);
    }

    private void selectWholeListOfPosition(int position, int cartid) {
        WholeModel goods = (WholeModel) mCartAdapter.data.get(position);
        boolean select = true;
        for (SkuListBean skuListBean : goods.sku_list.values()) {
            if (skuListBean.cart_id.equals(cartid + "")) {
                skuListBean.select = !skuListBean.select;
            }
            select = select && skuListBean.select;
        }

        goods.select = select;

        checkAll(position, false);
    }

    private void checkAll(int position) {
        checkAll(position, true);
    }

    private void checkAll(int position, boolean isParentClick) {
        ShopListModel shop = null;
        for (int i = position - 1; i >= 0; i--) {
            Object object = mCartAdapter.data.get(i);
            if (object instanceof ShopListModel) {
                shop = (ShopListModel) object;
                break;
            }
        }

        if (shop != null) {
            boolean allSelected = true;
            if (shop.goods_list_copy != null) {
                for (Object obj : shop.goods_list_copy) {
                    if (obj instanceof ActInfo) {
                        ActInfo actInfo = (ActInfo) obj;
                        if (!actInfo.select) {
                            allSelected = false;
                            break;
                        }
                    } else if (obj instanceof FullCutModel) {
                        FullCutModel fullCutModel = (FullCutModel) obj;

                        for (Map.Entry<String, GoodsModel> entry3 : fullCutModel.goods_list
                                .entrySet()) {
                            if (!entry3.getValue().select) {
                                allSelected = false;
                                break;
                            }
                        }
                    } else if (obj instanceof GoodsModel) {
                        GoodsModel goodsItem = (GoodsModel) obj;
                        if (!goodsItem.select) {
                            allSelected = false;
                            break;
                        }
                    }
                }
            }

            if (shop.whole_list != null) {
                for (WholeModel goodsItem : shop.whole_list.values()) {
                    if (isParentClick) {
                        for (SkuListBean skuListBean : goodsItem.sku_list.values()) {
                            skuListBean.select = goodsItem.select;
                        }
                    }

                    if (!goodsItem.select) {
                        allSelected = false;
                    }
                }
            }
            shop.select = allSelected;
        }
        notifyDataSetChanged();

        updateDeleteButton();
        updateSelectAllImageView();
        cartSelect();
        updateSuspensionBar();
    }

    private void notifyDataSetChanged() {
        mCartAdapter.notifyDataSetChanged();

        //每次刷新布局的时候都重新获取一下标题的坐标
        titlePositions.clear();
        for (int i = 0; i < mCartAdapter.data.size(); i++) {
            if (mCartAdapter.data.get(i) instanceof ShopListModel) {
                titlePositions.add(i);
            }
        }
    }

    private void selectShopOfPosition(int position) {
        ShopListModel shop = (ShopListModel) mCartAdapter.data.get(position);
        shop.select = !shop.select;

        try {
            if (shop.goods_list_copy != null) {
                for (Object obj : shop.goods_list_copy) {

                    if (obj instanceof ActInfo) {

                        ActInfo actInfo = (ActInfo) obj;
                        actInfo.select = shop.select;
                    } else if (obj instanceof FullCutModel) {
                        FullCutModel fullCutModel = (FullCutModel) obj;

                        for (Map.Entry<String, GoodsModel> entry3 : fullCutModel.goods_list
                                .entrySet()) {
                            entry3.getValue().select = shop.select;
                        }
                    } else if (obj instanceof GoodsModel) {
                        GoodsModel goods = (GoodsModel) obj;
                        goods.select = shop.select;
                    }

                }
            }

            if (shop.whole_list != null) {
                for (WholeModel goods : shop.whole_list.values()) {
                    goods.select = shop.select;
                    for (SkuListBean skuListBean : goods.sku_list.values()) {
                        skuListBean.select = shop.select;
                    }
                }
            }

        } catch (Exception e) {
        }

        notifyDataSetChanged();
        updateSelectAllImageView();
        updateDeleteButton();
        cartSelect();
        updateSuspensionBar();
    }

    private int getTitleNextPosition(int position) {
        for (int i = 0; i < titlePositions.size(); i++) {
            if (i == titlePositions.size() - 1) {
                break;
            }

            if (position == titlePositions.get(i)) {
                return titlePositions.get(i + 1);
            }
        }
        return -1;
    }

    /**
     * @param visiblePosition 传入当前可见的顶部的position，
     * @return 返回应该显示的标题position，不显示返回-1
     */
    private int getTitlePosition(int visiblePosition) {

        for (int i = 0; i < titlePositions.size(); i++) {
            int position = titlePositions.get(i);

            if (i == 0 && visiblePosition < position) {
                break;
            }

            if (i == titlePositions.size() - 1) {
                return position;
            }

            int positionNext = titlePositions.get(i + 1);
            if (position <= visiblePosition && positionNext > visiblePosition) {
                return position;
            }
        }
        return -1;
    }


    private void setUpAdapterData() {
        if (mModel.data.cart.submit_enable == 0) {
            mCheckoutButton.setEnabled(false);
        } else {
            mCheckoutButton.setEnabled(true);
        }

        mCartAdapter.data.clear();
        DividerModel dividerModel = new DividerModel();

        if (!Utils.isNull(mModel.data.unpayed_list)) {
            UnpaidOrderTitleModel unpaidOrderTitleModel = new UnpaidOrderTitleModel();
            unpaidOrderTitleModel.number = String.valueOf(mModel.data.unpayed_list.size());
            mCartAdapter.data.add(unpaidOrderTitleModel);
            if (mModel.data.unpayed_list.size() > 2) {
                mCartAdapter.data.addAll(mModel.data.unpayed_list.subList(0, 2));
                mCartAdapter.data.add(new UnpayedListMoreModel());
            } else {
                mCartAdapter.data.addAll(mModel.data.unpayed_list);
            }
        }

        if (!Utils.isNull(mModel.data.cart.shop_list) && mModel.data.cart.shop_list.size() > 0) {
            for (ShopListModel shopListModel : mModel.data.cart.shop_list) {

                boolean haveGoodList = false;
                boolean haveWholeList = false;
                if (!Utils.isNull(shopListModel.goods_list) && shopListModel.goods_list.size() > 0) {
                    haveGoodList = true;
                }

                if (!Utils.isNull(shopListModel.whole_list) && shopListModel.whole_list.size() > 0) {
                    haveWholeList = true;
                }

                if (haveGoodList || haveWholeList) {
                    mCartAdapter.data.add(dividerModel);
                    mCartAdapter.data.add(shopListModel);

                    if (tag == 0) {
                        mCurrentPosition = mCartAdapter.data.size() - 1;
                        tag++;
                    }

                    //创建是否显示满 支付提示
                    if (!Utils.isNull(mModel.data.cart.show_start_price_ids)) {
                        for (String id : mModel.data.cart.show_start_price_ids) {
                            if (id.equals(shopListModel.shop_info.shop_id)) {
                                StoreTipModel storeTipModel = new StoreTipModel();
                                storeTipModel.shopid = shopListModel.shop_info.shop_id;
                                storeTipModel.start_price = shopListModel.shop_info.start_price;
                                storeTipModel.select_shop_amount = mModel.data.cart.select_shop_amount.get(shopListModel.shop_info.shop_id);
                                mCartAdapter.data.add(storeTipModel);
                            }
                        }
                    }
                }

                if (haveGoodList) {
                    if (shopListModel.goods_list_copy.size() > 0) {
                        mCartAdapter.data.addAll(shopListModel.goods_list_copy);
                    } else {
                        //由于map无法排序，所以数据类型改为list
                        shopListModel.goods_list_copy.clear();
                        for (GoodsModel goodsModel : shopListModel.goods_list) {
                            if (goodsModel.act_list != null && !TextUtils.isEmpty(goodsModel.act_list.act_id)) {
                                shopListModel.goods_list_copy.add(goodsModel.act_list);
                                mCartAdapter.data.add(goodsModel.act_list);
                            } else if (goodsModel.full_cut_list != null && goodsModel.full_cut_list.keySet().size() > 0) {

                                for (TreeMap.Entry<String, FullCutModel> entry : goodsModel.full_cut_list.entrySet()) {
                                    shopListModel.goods_list_copy.add(entry.getValue());
                                    mCartAdapter.data.add(entry.getValue());
                                }

                            } else {
                                shopListModel.goods_list_copy.add(goodsModel);
                                mCartAdapter.data.add(goodsModel);

                                if (goodsModel.gift_list != null) {
                                    mCartAdapter.data.addAll(goodsModel.gift_list);
                                }
                            }
                        }
                    }
                }

                if (haveWholeList) {
                    mCartAdapter.data.addAll(shopListModel.whole_list.values());
                }
            }

            //店铺头部悬浮相关
            mGoodsListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    mSuspensionHeight = mShopLayout.getHeight();
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    //获取首个可见位置
                    int visiblePosition = layoutManager.findFirstVisibleItemPosition();

                    int titlePosition = getTitlePosition(visiblePosition);
                    if (titlePosition >= 0) {

                        mCurrentPosition = titlePosition;

                        View view = null;
                        view = layoutManager.findViewByPosition(getTitleNextPosition(mCurrentPosition));

                        int h = mShopLayout.getTop();
                        if (view != null) {
                            if (view.getTop() <= mSuspensionHeight) {
                                mShopLayout.setY(-(mSuspensionHeight - view.getTop() - h));
                            } else {
                                mShopLayout.setY(h);
                            }
                        } else {
                            mShopLayout.setY(h);
                        }

                        mShopLayout.setVisibility(View.VISIBLE);
                        updateSuspensionBar();
                    } else {
                        mShopLayout.setVisibility(View.GONE);
                    }
                }
            });
        }

        //失效商品
        if (!Utils.isNull(mModel.data.cart.invalid_list)) {

//            Comparator<String> comparator = new Comparator<String>() {
//                @Override
//                public int compare(String o1, String o2) {
//                    int i = Integer.parseInt(o1);
//                    int j = Integer.parseInt(o2);
//                    return j - i;
//                }
//            };
//            TreeMap<String, InvalidListModel> treeMap = new TreeMap<String, String>(comparator);

            invalidLists = new ArrayList<>();

            List<InvalidListModel> invalidList = mModel.data.cart.invalid_list;

            for (InvalidListModel entry : invalidList) {
                if (entry.act_list != null && !TextUtils.isEmpty(entry.act_list.act_id)) {
                    entry.act_list.isInvalid = true;
                    invalidLists.add(entry.act_list);
                } else {
                    invalidLists.add(entry);
                }
            }

            mCartAdapter.data.add(dividerModel);
            InvalidTitleModel invalidTitleModel = new InvalidTitleModel();
            invalidTitleModel.invalidGoodsNumber = invalidList.size();
            mCartAdapter.data.add(invalidTitleModel);
            //mCartAdapter.data.addAll(mModel.data.cart.invalid_list.values());
            mCartAdapter.data.addAll(invalidLists);
            //样式修改，最后的清空按钮移至标题
            //mCartAdapter.data.add(new InvalidClearModel());
        }


        if (Utils.isNull(mModel.data.cart.invalid_list) && Utils.isNull(mModel.data.cart
                .shop_list)) {
            EmptyItemModel emptyModel = new EmptyItemModel();
            mCartAdapter.data.add(emptyModel);
        }

        notifyDataSetChanged();
    }

    private void updateCheckoutLayout() {
        if (Utils.isNull(mModel.data.cart.shop_list)) {
            mBottomLayout.setVisibility(View.GONE);
        } else {
            mBottomLayout.setVisibility(View.VISIBLE);
        }
        mGoodsSelectPrice.setText(mModel.data.cart.select_goods_amount_format);
        String s;

        if (Integer.parseInt(mModel.data.cart.select_goods_number) < 1) {
            s = getResources().getString(R.string.goCheckout);
        } else {
            s = String.format(getResources().getString(R.string.goCheckoutFormat),
                    Utils.formatNum(mModel.data.cart.select_goods_number, true));
        }

        mCheckoutButton.setText(Utils.spanSizeString(getActivity(), s, 3, s.length(), 12));
    }

    private void updateClearButton() {

        if (Utils.isNull(mModel.data.cart.shop_list)) {
            mClearButton.setVisibility(View.GONE);
        } else {
            mClearButton.setVisibility(View.VISIBLE);
        }
    }

    private void updateDeleteButton() {
        String selectedCartIds = getSelectedCartIds();
        String cartIds = getCartIds();

/*        boolean isSelected = false
        for (ShopListModel item : mModel.data.cart.shop_list.values()) {
            for (GoodsModel goods : item.goods_list.values()) {
                if (goods.select) {
                    isSelected = true;
                    break;
                }
            }
        }*/
        if (!Utils.isNull(selectedCartIds)) {
            if (selectedCartIds.length() != cartIds.length()) {
                mClearButton.setText(R.string.delete);
            } else {
                mClearButton.setText(R.string.clear);
            }
        } else {
            mClearButton.setText(R.string.clear);
        }
    }

    //更改购物车数量
    private void updateGoodsNumber(String goodsNumber, String sku_id) {
        CommonRequest mChangeGoodsNumberRequest = new CommonRequest(Api.API_CART_CHANGE_NUMBER,
                HttpWhat.HTTP_GOODS_NUMBER_CHANGE.getValue(), RequestMethod.POST);
        mChangeGoodsNumberRequest.add("sku_id", sku_id);
        mChangeGoodsNumberRequest.add("number", goodsNumber);
        mChangeGoodsNumberRequest.add("shop_id", shop_id);
        addRequest(mChangeGoodsNumberRequest);
    }

    private void updateGoodsNumberCallback(String response) {
        HttpResultManager.resolve(response, ResponseCartModel.class, new HttpResultManager.HttpResultCallBack<ResponseCartModel>() {
            @Override
            public void onSuccess(ResponseCartModel back) {
                mModel = back;
//                mModel.data.cart = back.data.cart;
                setUpAdapterData();
                updateClearButton();
                updateCheckoutLayout();
                App.addCartNumber(mGoodsNumberDelta, CartFragment.this);
                postGoodsNumberUpdated();
            }
        });
    }

    private void updateSelectAllImageView() {
        boolean allChecked = true;
        if (!Utils.isNull(mModel.data.cart.shop_list)) {
            for (ShopListModel item : mModel.data.cart.shop_list) {
                if (!item.select) {
                    allChecked = false;
                    break;
                }
            }
        }
        mSelectAllImageView.setSelected(allChecked);
    }

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public TreeMap<String, SkuListBean> sortMapByKey(TreeMap<String, SkuListBean> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        TreeMap<String, SkuListBean> sortMap = new TreeMap<String, SkuListBean>(
                new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }
}
