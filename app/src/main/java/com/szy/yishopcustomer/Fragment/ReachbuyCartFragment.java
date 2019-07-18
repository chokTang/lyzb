package com.szy.yishopcustomer.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.CartEditNumberActivity;
import com.szy.yishopcustomer.Activity.CheckoutFreeActivity;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.GoodsBonusActivity;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.OrderDetailActivity;
import com.szy.yishopcustomer.Activity.OrderListActivity;
import com.szy.yishopcustomer.Activity.OrderListFreeActivity;
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
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.EmptyItemModel;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * Created by smart on 2017/10/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ReachbuyCartFragment  extends YSCBaseFragment implements OnPullListener,
        OnEmptyViewClickListener {
    @BindView(R.id.fragment_cart_total_price)
    TextView mGoodsSelectPrice;
    @BindView(R.id.fragment_cart_checkout_button)
    TextView mCheckoutButton;
    @BindView(R.id.fragment_cart_bottom_layout)
    RelativeLayout mBottomLayout;
    @BindView(R.id.fragment_cart_goods_listView)
    CommonRecyclerView mGoodsListView;
    @BindView(R.id.fragment_cart_goods_listView_layout)
    PullableLayout mPullableLayout;
    @BindView(R.id.login_button)
    Button mLoginButton;

    @BindView(R.id.imageViewBack)
    View imageViewBack;

    private CartAdapter mCartAdapter;
    private ResponseCartModel mModel;
    private int mGoodsNumberDelta;

    @BindView(R.id.linearlayout_go_order_list)
    View linearlayout_go_order_list;

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
                openGoodsOfPosition(position);
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
                selectGoodsOfPosition(position);
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
            case VIEW_TYPE_CHECKOUT:
                mCheckoutButton.setEnabled(false);
                goCheckOut();
                break;
            case VIEW_TYPE_LOGIN:
                openLoginActivity();
                break;
            case VIEW_TYPE_ORDER:
                openOrderOfPosition(position);
                break;
            case VIEW_TYPE_ORDER_LIST_FREE:
                openOrderListFreeActivity(Macro.ORDER_TYPE_ALL);
                break;
            case VIEW_TYPE_ORDER_LIST:
                openOrderListActivity(Macro.ORDER_TYPE_UNPAID);
                break;
            case VIEW_TYPE_CLEAR_INVALID:
                clearInvalidGoods();
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
                openEditNumber(position);
                break;
            case VIEW_TYPE_EDIT_WHOLE_LIST:
                openEditNumber(position, extrainfo);
                break;
            default:
                super.onClick(view);
                break;
        }
    }

    @Override
    public void onConfirmDialogCanceled(int viewType, int position, int extraInfo) {
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
                deleteInvalidGoodsConfirm();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mGoodsListView.setAdapter(mCartAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mGoodsListView.setLayoutManager(layoutManager);
        mGoodsListView.setEmptyViewClickListener(this);


        Utils.setViewTypeForTag(linearlayout_go_order_list, ViewType.VIEW_TYPE_ORDER_LIST_FREE);
        linearlayout_go_order_list.setOnClickListener(this);

        Utils.setViewTypeForTag(mCheckoutButton, ViewType.VIEW_TYPE_CHECKOUT);
        mCheckoutButton.setOnClickListener(this);

        mPullableLayout.topComponent.setOnPullListener(this);

        Utils.setViewTypeForTag(mLoginButton, ViewType.VIEW_TYPE_LOGIN);
        mLoginButton.setOnClickListener(this);

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mGoodsSelectPrice.setText(" 0");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
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
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private String rc_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_cart_reachbuy;
        mCartAdapter = new CartAdapter();
        mCartAdapter.onClickListener = this;

        rc_id = getActivity().getIntent().getStringExtra("rc_id");
    }

    public void openBonusActivity(int position) {
        Intent intent = new Intent();
        ShopListModel shopListModel = (ShopListModel) mCartAdapter.data.get(position);

        intent.putExtra(Key.KEY_SHOP_NAME.getValue(), shopListModel.shop_info.shop_name);
        intent.putParcelableArrayListExtra(Key.KEY_BONUS_LIST.getValue(), shopListModel.bonus_list);
        intent.setClass(getActivity(), GoodsBonusActivity.class);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_TAKE_BONUS.getValue());
    }

    public void openEditNumber(int position) {
        Intent intent = new Intent();
        GoodsModel goodsModel = (GoodsModel) mCartAdapter.data.get(position);
        intent.putExtra(Key.KEY_GOODS_NUMBER.getValue(), goodsModel.goods_number);
        intent.putExtra(Key.KEY_SKU_ID.getValue(), goodsModel.sku_id);
        intent.setClass(getActivity(), CartEditNumberActivity.class);
        intent.putExtra(CartEditNumberActivity.TYPE,CartEditNumberActivity.TYPE_REACHBUY_CART);
        //startActivity(intent);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_CHANGE_NUMBER.getValue());
    }

    public void openEditNumber(int position, int itemPosition) {
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
        intent.putExtra("rc_id",rc_id);
        intent.setClass(getActivity(), GoodsActivity.class);
        startActivity(intent);
    }

    public void openGoodsActivityForGoodId(String goodid) {
        Intent intent = new Intent();
        intent.putExtra("rc_id",rc_id);
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

    public void refresh() {
        CommonRequest mCartList = new CommonRequest(Api.API_REACHBUY_CART_INDEX, HttpWhat.HTTP_CART_LIST
                .getValue());
            mCartList.add("rc_id", rc_id);
        addRequest(mCartList);
    }

    private void cartSelect() {
        CommonRequest request = new CommonRequest(Api.API_CART_SELECT, HttpWhat.HTTP_CART_SELECT
                .getValue(), RequestMethod.POST);
        request.setAjax(true);
        request.add("cart_ids", getSelectedCartIds());
        addRequest(request);
    }

    public void openOrderListFreeActivity(String status) {
        Intent intent = new Intent(getContext(), OrderListFreeActivity.class);
        intent.putExtra(OrderListFreeFragment.PARAMS_TYPE,OrderListFreeFragment.TYPE_REACHBUY);
        intent.putExtra(Key.KEY_ORDER_STATUS.getValue(), status);
        startActivity(intent);
    }

    private void cartSelectCallBack(String response) {
        HttpResultManager.resolve(response, CartSelectModel.class, new HttpResultManager.HttpResultCallBack<CartSelectModel>() {
            @Override
            public void onSuccess(CartSelectModel model) {
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

    private void clearInvalidGoods() {
        showConfirmDialog(R.string.emptyInvalidGoods, ViewType.VIEW_TYPE_CLEAR_INVALID.ordinal());
    }

    private void clearCartConfirm() {
        deleteGoods(getCartIds());
    }

    private void deleteGoods(String cartIds) {
        CommonRequest mDeleteGoodsRequest = new CommonRequest(Api.API_REACHBUY_CART_DELETE, HttpWhat
                .HTTP_GOODS_DELETE.getValue(), RequestMethod.POST);
        mDeleteGoodsRequest.add("cart_ids", cartIds);
        mDeleteGoodsRequest.add("rc_id",rc_id);
        mDeleteGoodsRequest.setAjax(true);
        addRequest(mDeleteGoodsRequest);
    }

    private void deleteGoodsCallback(String response) {
        HttpResultManager.resolve(response, ResponseCartModel.class, new HttpResultManager.HttpResultCallBack<ResponseCartModel>() {
            @Override
            public void onSuccess(ResponseCartModel back) {
                mModel.data.cart = back.data.cart;
                setUpAdapterData();
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
        GoodsModel goods = (GoodsModel) mCartAdapter.data.get(position);
        deleteGoods(goods.cart_id);
    }

    private void deleteSelectedGoods() {
        String selectedCartIds = getSelectedCartIds();
        deleteGoods(selectedCartIds);
    }

    private void deleteInvalidGoodsConfirm() {
        deleteGoods(getInvalidIds());
    }

    private String getCartIds() {
        List<String> list = new ArrayList<>();
        if (!Utils.isNull(mModel.data.cart.shop_list)) {
            for (ShopListModel shop : mModel.data.cart.shop_list) {
                if (!Utils.isNull(shop.goods_list)) {
                    for (GoodsModel goods : shop.goods_list) {
                        list.add(goods.cart_id);
                    }
                }
            }
        }
        return Utils.join(list, ",");
    }

    private String getInvalidIds() {
        List<String> list = new ArrayList<>();

        for (InvalidListModel entry :  mModel.data.cart.invalid_list) {
                InvalidListModel invalidListModel = entry;
                list.add(invalidListModel.cart_id);
        }

        return Utils.join(list, ",");
    }

    private String getSelectedCartIds() {
        List<String> list = new ArrayList<>();
        try {
            if (!Utils.isNull(mModel.data.cart.shop_list)) {
                for (ShopListModel shop : mModel.data.cart.shop_list) {
                    if (!Utils.isNull(shop.goods_list)) {
                        for (GoodsModel goods : shop.goods_list) {
                            if (goods.select) {
                                list.add(goods.cart_id);
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
        CommonRequest mGoCheckoutList = new CommonRequest(Api.API_REACHBUY_GO_CHECKOUT, HttpWhat
                .HTTP_GO_CHECKOUT.getValue(), RequestMethod.POST);
        mGoCheckoutList.add("rc_id",rc_id);
        mGoCheckoutList.setAjax(true);
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
                Utils.makeToast(getActivity(), responseModel.message);
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
        }
    }

    private void openCheckoutActivity() {
        Intent intent = new Intent();
        intent.putExtra("rc_id",rc_id);
        intent.setClass(getActivity(), CheckoutFreeActivity.class);
        startActivity(intent);
    }

    private void openGiftOfPosition(int position) {
        GiftModel giftModel = (GiftModel) mCartAdapter.data.get(position);
        openGoodsActivity(String.valueOf(giftModel.gift_sku_id));
    }

    private void openInvalidGoodsOfPosition() {
        Utils.makeToast(getActivity(), "此商品已下架！");
    }

    private void openGoodsOfPosition(int position) {
        Object obj = mCartAdapter.data.get(position);
        if (obj instanceof GoodsModel) {
            GoodsModel goods = (GoodsModel) mCartAdapter.data.get(position);
            openGoodsActivity(goods.sku_id);
        } else if (obj instanceof WholeModel) {
            WholeModel goods = (WholeModel) mCartAdapter.data.get(position);
            openGoodsActivityForGoodId(goods.goods_id);
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
                        if (!Utils.isNull(entry.goods_list)) {
                            for (GoodsModel goodsEntry: entry.goods_list) {

                                GoodsEventModel goodsEventModel = new GoodsEventModel();
                                goodsEventModel.goodsId = goodsEntry.goods_id;
                                goodsEventModel.cartNumber = Integer.valueOf(goodsEntry.goods_number);
                                goodsList.add(goodsEventModel);
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
        }
    }

    private void refreshCallback(String response) {
        mPullableLayout.topComponent.finish(Result.SUCCEED);
        HttpResultManager.resolve(response, ResponseCartModel.class, new HttpResultManager.HttpResultCallBack<ResponseCartModel>() {
            @Override
            public void onSuccess(ResponseCartModel back) {
                mModel = back;

                setUpAdapterData();
                updateCheckoutLayout();

                mGoodsSelectPrice.setText(mModel.data.cart.select_goods_amount_format);

//                String checkOutButtonText = String.format(getResources().getString(R.string
//                        .goCheckoutFormat), mModel.data.cart.select_goods_number);
//                mCheckoutButton.setText(checkOutButtonText);

                if(TextUtils.isEmpty(mModel.data.reachbuy_list)) {
                    linearlayout_go_order_list.setVisibility(View.GONE);
                } else {
                    linearlayout_go_order_list.setVisibility(View.VISIBLE);
                }
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
        mBottomLayout.setVisibility(View.GONE);
        imageViewBack.setVisibility(View.GONE);
    }


    private void selectGoodsOfPosition(int position) {
        GoodsModel goods = (GoodsModel) mCartAdapter.data.get(position);
        goods.select = !goods.select;

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
            if (shop.goods_list != null) {
                for (GoodsModel goodsItem : shop.goods_list) {
                    if (!goodsItem.select) {
                        allSelected = false;
                        break;
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
        mCartAdapter.notifyDataSetChanged();
        cartSelect();
    }

    private void selectShopOfPosition(int position) {
        ShopListModel shop = (ShopListModel) mCartAdapter.data.get(position);
        shop.select = !shop.select;


        try {
            if (shop.goods_list != null) {
                for (GoodsModel goods : shop.goods_list) {
                    goods.select = shop.select;
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

        mCartAdapter.notifyDataSetChanged();
        cartSelect();
    }

    private void setUpAdapterData() {
//        if (mModel.data.cart.submit_enable == 0) {
//            setEnabled(false);
//        } else {
//            setEnabled(true);
//        }

        mCartAdapter.rc_code = mModel.data.rc_model.code;
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

        if (!Utils.isNull(mModel.data.cart.shop_list)) {
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
//                    //创建是否显示满 支付提示
//                    if (!Utils.isNull(mModel.data.cart.show_start_price_ids)) {
//                        for (String id : mModel.data.cart.show_start_price_ids) {
//                            if (id.equals(shopListModel.shop_info.shop_id)) {
//                                StoreTipModel storeTipModel = new StoreTipModel();
//                                storeTipModel.shopid = shopListModel.shop_info.shop_id;
//                                storeTipModel.start_price = shopListModel.shop_info.start_price;
//                                storeTipModel.select_shop_amount = mModel.data.cart.select_shop_amount.get(shopListModel.shop_info.shop_id);
//                                mCartAdapter.data.add(storeTipModel);
//                            }
//                        }
//                    }
                }

                if (haveGoodList) {
                    for (GoodsModel goodsModel : shopListModel.goods_list) {
                        goodsModel.goods_moq = "1";
                        mCartAdapter.data.add(goodsModel);
                        if (goodsModel.gift_list != null) {
                            for (GiftModel giftModel : goodsModel.gift_list) {
                                mCartAdapter.data.add(giftModel);
                            }
                        }
                    }
                }

                if (haveWholeList) {
                    for (WholeModel wholeModel : shopListModel.whole_list.values()) {
                        wholeModel.goods_moq = "1";
//                        wholeModel.sku_list = sortMapByKey(wholeModel.sku_list);
                        mCartAdapter.data.add(wholeModel);
                    }
                }
            }
        }

        if (!Utils.isNull(mModel.data.cart.invalid_list)) {
            mCartAdapter.data.add(dividerModel);
            mCartAdapter.data.add(new InvalidTitleModel());
            for (InvalidListModel entry :  mModel.data.cart.invalid_list) {
                    InvalidListModel invalidListModel = entry;
                    mCartAdapter.data.add(invalidListModel);
            }
//            mCartAdapter.data.add(new InvalidClearModel());
            mCartAdapter.data.add(dividerModel);
        }

        if (Utils.isNull(mModel.data.cart.invalid_list) && Utils.isNull(mModel.data.cart
                .shop_list)) {
            EmptyItemModel emptyModel = new EmptyItemModel();
            mCartAdapter.data.add(emptyModel);
        } else {

            mCartAdapter.data.add(new DividerModel());
            mCartAdapter.data.add(new DividerModel());
            mCartAdapter.data.add(new DividerModel());
        }

        mCartAdapter.notifyDataSetChanged();
    }

    private void updateCheckoutLayout() {
        if (Utils.isNull(mModel.data.cart.shop_list)) {
            mBottomLayout.setVisibility(View.GONE);
            imageViewBack.setVisibility(View.GONE);
        } else {
            mBottomLayout.setVisibility(View.VISIBLE);
            imageViewBack.setVisibility(View.VISIBLE);
        }
        mGoodsSelectPrice.setText(Utils.formatMoney(mGoodsSelectPrice.getContext(), mModel.data.cart.select_goods_amount_format));
//        mCheckoutButton.setText(String.format(getResources().getString(R.string.goCheckoutFormat),
//                mModel.data.cart.select_goods_number));
    }

    //更改购物车数量
    private void updateGoodsNumber(String goodsNumber, String sku_id) {
        CommonRequest mChangeGoodsNumberRequest = new CommonRequest(Api.API_REACHBUY_CART_CHANGE_NUMBER,
                HttpWhat.HTTP_GOODS_NUMBER_CHANGE.getValue(), RequestMethod.POST);
        mChangeGoodsNumberRequest.add("sku_id", sku_id);
        mChangeGoodsNumberRequest.add("number", goodsNumber);
        mChangeGoodsNumberRequest.add("rc_id", rc_id);
        addRequest(mChangeGoodsNumberRequest);
    }

    private void updateGoodsNumberCallback(String response) {
        HttpResultManager.resolve(response, ResponseCartModel.class, new HttpResultManager.HttpResultCallBack<ResponseCartModel>() {
            @Override
            public void onSuccess(ResponseCartModel back) {
                mModel.data.cart = back.data.cart;
                setUpAdapterData();
                updateCheckoutLayout();
                postGoodsNumberUpdated();
            }
        });
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
