package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.szy.yishopcustomer.Activity.ShoppingBagActivity;
import com.szy.yishopcustomer.Adapter.CartFreeAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Other.GoodsEventModel;
import com.szy.yishopcustomer.Other.GoodsNumberEvent;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.CartIndex.GiftModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.GoodsModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.InvalidClearModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.InvalidListModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.InvalidTitleModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.ResponseCartModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.ShopListModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.StoreTipModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.UnpaidOrderModel;
import com.szy.yishopcustomer.ResponseModel.CartSelect.ResponseCartSelectModel;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.EmptyItemModel;
import com.szy.yishopcustomer.ViewModel.ShoppingBagModel;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * Created by Smart on 2017/8/5.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CartFreeFragment extends YSCBaseFragment implements OnPullListener {
    @BindView(R.id.imageView_back)
    View imageView_back;
    @BindView(R.id.fragment_cart_total_price)
    TextView mGoodsSelectPrice;
    @BindView(R.id.fragment_cart_checkout_button)
    TextView mCheckoutButton;
    @BindView(R.id.fragment_cart_bottom_layout)
    RelativeLayout mBottomLayout;
    @BindView(R.id.fragment_cart_delete_all_button)
    TextView mClearButton;
    @BindView(R.id.fragment_cart_goods_listView)
    CommonRecyclerView mGoodsListView;
    @BindView(R.id.fragment_cart_goods_listView_layout)
    PullableLayout mPullableLayout;
    @BindView(R.id.login_layout)
    View mLoginLayout;
    @BindView(R.id.login_button)
    Button mLoginButton;
    @BindView(R.id.fragment_cart_back)
    View fragment_cart_back;

    @BindView(R.id.linearlayout_go_order_list)
    View linearlayout_go_order_list;

    private CartFreeAdapter mCartAdapter;
    private ResponseCartModel mModel;
    private int mGoodsNumberDelta;

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
            case VIEW_TYPE_ORDER_LIST_FREE:
                openOrderListFreeActivity(Macro.ORDER_TYPE_ALL);
                break;
            case VIEW_TYPE_CLEAR:
                clearCart();
                break;
            case VIEW_TYPE_CLEAR_INVALID:
                clearInvalidGoods();
                break;
            case VIEW_TYPE_DELETE_GOODS:
                deleteGoodsOfPosition(position);
                break;
            case VIEW_TYPE_EMPTY:
//                goIndex();
                getActivity().finish();
                break;
            case VIEW_TYPE_MINUS:
                reduceGoodsNumberOfPosition(position);
                break;
            case VIEW_TYPE_PLUS:
                increaseGoodsNumberOfPosition(position);
                break;
            case VIEW_TYPE_BONUS:
                openBonusActivity(position);
                break;
            case VIEW_TYPE_EDIT:
                openEditNumber(position);
                break;
            case VIEW_TYPE_BACK_WAY:
                getActivity().finish();
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

    void setEnabled(boolean state) {
        mCheckoutButton.setEnabled(state);
        if (state) {
            mCheckoutButton.setBackgroundResource(R.color.free_buy_primary);
        } else {
            mCheckoutButton.setBackgroundResource(R.color.colorNine);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mGoodsListView.setAdapter(mCartAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mGoodsListView.setLayoutManager(layoutManager);


        Utils.setViewTypeForTag(linearlayout_go_order_list, ViewType.VIEW_TYPE_ORDER_LIST_FREE);
        linearlayout_go_order_list.setOnClickListener(this);

        Utils.setViewTypeForTag(mCheckoutButton, ViewType.VIEW_TYPE_CHECKOUT);
        mCheckoutButton.setOnClickListener(this);

        Utils.setViewTypeForTag(mClearButton, ViewType.VIEW_TYPE_CLEAR);
        mClearButton.setOnClickListener(this);

        Utils.setViewTypeForTag(fragment_cart_back, ViewType.VIEW_TYPE_BACK_WAY);
        fragment_cart_back.setOnClickListener(this);

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
                Drawable upArrow = getResources().getDrawable(R.mipmap.btn_back_dark);

                imageView_back.setVisibility(View.VISIBLE);
                imageView_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().finish();
                    }
                });
            }
        }

        mGoodsSelectPrice.setText(" 0");

        setEnabled(false);
        return view;
    }

//    @Override
//    public void onEvent(CommonEvent event) {
//        switch (EventWhat.valueOf(event.getWhat())) {
//            case EVENT_UPDATE_CART_NUMBER:
//                if (!event.isFrom(this)) {
//                    refresh();
//                }
//                break;
//            case EVENT_CHECKOUT_SUCCEED:
//                refresh();
//                break;
//            case EVENT_LOGIN:
//                onLogin();
//                break;
//            case EVENT_LOGOUT:
//                onLogout();
//                break;
//            default:
//                super.onEvent(event);
//                break;
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
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
                break;
            case HTTP_CART_GET_SHOP_BAG:
                getShopBagCallback(response);
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
        mLayoutId = R.layout.fragment_cart_free;
        mCartAdapter = new CartFreeAdapter();
        mCartAdapter.onClickListener = this;

        shop_id = getActivity().getIntent().getStringExtra("shop_id");
    }

    public void openBonusActivity(int position) {
        Intent intent = new Intent();
        ShopListModel shopListModel = (ShopListModel) mCartAdapter.data.get(position);

        intent.putExtra(Key.KEY_SHOP_NAME.getValue(), shopListModel.shop_info.shop_name);
        intent.putParcelableArrayListExtra(Key.KEY_BONUS_LIST.getValue(), shopListModel.bonus_list);
        intent.putExtra("type", 1);
        intent.setClass(getActivity(), GoodsBonusActivity.class);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_TAKE_BONUS.getValue());
    }

    public void openEditNumber(int position) {
        Intent intent = new Intent();
        GoodsModel goodsModel = (GoodsModel) mCartAdapter.data.get(position);
        intent.putExtra(Key.KEY_GOODS_NUMBER.getValue(), goodsModel.goods_number);
        intent.putExtra(Key.KEY_SKU_ID.getValue(), goodsModel.sku_id);
        intent.putExtra(CartEditNumberActivity.TYPE, CartEditNumberActivity.TYPE_FREE_CART);
        intent.setClass(getActivity(), CartEditNumberActivity.class);
        //startActivity(intent);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_CHANGE_NUMBER.getValue());
    }

    public void openGoodsActivity(String skuId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_SKU_ID.getValue(), skuId);
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

    public void openOrderListFreeActivity(String status) {
        Intent intent = new Intent(getContext(), OrderListFreeActivity.class);
        intent.putExtra(Key.KEY_ORDER_STATUS.getValue(), status);
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
        CommonRequest mCartList = new CommonRequest(Api.API_FREEBUY_CART_INDEX, HttpWhat.HTTP_CART_LIST
                .getValue());
        if (!TextUtils.isEmpty(shop_id)) {
            mCartList.add("shop_id", shop_id);
        }
        addRequest(mCartList);
    }

    private void cartSelect() {
        CommonRequest request = new CommonRequest(Api.API_CART_SELECT, HttpWhat.HTTP_CART_SELECT
                .getValue(), RequestMethod.POST);
        request.setAjax(true);
        String cart_ids = getSelectedCartIds();
        if (!TextUtils.isEmpty(cart_ids)) {
            request.add("cart_ids", cart_ids);
            request.add("shop_id", shop_id);
            request.add("buyer_type", 2);
            addRequest(request);
        }
    }

    private void cartSelectCallBack(String response) {
        HttpResultManager.resolve(response, ResponseCartSelectModel.class, new HttpResultManager.HttpResultCallBack<ResponseCartSelectModel>() {
            @Override
            public void onSuccess(ResponseCartSelectModel model) {
                mModel.data.cart.select_goods_amount = model.cart.select_goods_amount;
                mModel.data.cart.select_goods_number = model.cart.select_goods_number;
                mModel.data.cart.select_goods_amount_format = model.cart.select_goods_amount_format;

                mModel.data.cart.select_shop_amount = model.cart.select_shop_amount;
                mModel.data.cart.submit_enable = model.cart.submit_enable;
                mModel.data.cart.show_start_price_ids = model.cart.show_start_price_ids;

                setUpAdapterData();
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

    private void clearCartConfirm() {
        deleteGoods(getCartIds());
    }

    private void deleteGoods(String cartIds) {
        CommonRequest mDeleteGoodsRequest = new CommonRequest(Api.API_FREEBUY_CART_DELETE, HttpWhat
                .HTTP_GOODS_DELETE.getValue(), RequestMethod.POST);
        mDeleteGoodsRequest.add("cart_ids", cartIds);
        mDeleteGoodsRequest.add("shop_id", shop_id);
        mDeleteGoodsRequest.setAjax(true);
        addRequest(mDeleteGoodsRequest);
    }

    private void deleteGoodsCallback(String response) {
        HttpResultManager.resolve(response, ResponseCartModel.class, new HttpResultManager.HttpResultCallBack<ResponseCartModel>() {
            @Override
            public void onSuccess(ResponseCartModel back) {
                App.setCartNumber(back.data.cart.goods_number, CartFreeFragment.this);
                mModel.data.cart = back.data.cart;
                setUpAdapterData();
                updateCheckoutLayout();
//                postGoodsNumberUpdated();
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
        try {
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
        } catch (Exception e) {

        }

        return "";
    }

    private String getInvalidIds() {
        List<String> list = new ArrayList<>();

        for (InvalidListModel entry : mModel.data.cart.invalid_list) {
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
                }
            }
        } catch (Exception e) {
        }

        return Utils.join(list, ",");
    }

    private boolean haveBag = false;

    private void goCheckOut() {
        //先判断购物车内有没有购物袋，有的话就直接进入订单页面，没有的就选择购物袋
        if (!haveBag) {
            //判断店铺下是否有购物袋，有的话在打开购物袋接口
            getShoppingBag();

//            Intent intent = new Intent(getActivity(), ShoppingBagActivity.class);
//            intent.putExtra(Key.KEY_SHOP_ID.getValue(), shop_id);
//            startActivity(intent);
        } else {
            goCheckOutAction();
        }
    }

    private void goCheckOutAction() {
        CommonRequest mGoCheckoutList = new CommonRequest(Api.API_FREEBUY_GO_CHECKOUT, HttpWhat
                .HTTP_GO_CHECKOUT.getValue(), RequestMethod.GET);
        mGoCheckoutList.setAjax(true);
        mGoCheckoutList.add("shop_id", shop_id);
        addRequest(mGoCheckoutList);
    }


    private void getShoppingBag() {
        CommonRequest commonRequest = new CommonRequest(Api.API_FREEBUY_CART_GET_SHOP_BAG, HttpWhat.HTTP_CART_GET_SHOP_BAG.getValue());
        commonRequest.add("shop_id", shop_id);
        addRequest(commonRequest);

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

    private void increaseGoodsNumberOfPosition(int position) {
        GoodsModel goods = (GoodsModel) mCartAdapter.data.get(position);
        int goods_number = Integer.valueOf(goods.goods_number);
        if (goods_number >= Integer.valueOf(goods.goods_max_number)) {
            Utils.makeToast(getActivity(), R.string.canNotAdd);
        } else {
            goods.goods_number = String.valueOf(goods_number + 1);
            mGoodsNumberDelta = 1;
            updateGoodsNumber(goods.goods_number, goods.sku_id);
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
        intent.setClass(getActivity(), CheckoutFreeActivity.class);
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), shop_id);
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
        GoodsModel goods = (GoodsModel) mCartAdapter.data.get(position);
        openGoodsActivity(goods.sku_id);
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
                            for (GoodsModel goodsEntry : entry.goods_list) {

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

    private void reduceGoodsNumberOfPosition(int position) {
        GoodsModel goods = (GoodsModel) mCartAdapter.data.get(position);
        int goods_number = Integer.valueOf(goods.goods_number);
        if (goods_number - 1 < goods.goods_min_number) {
            Utils.makeToast(getActivity(), R.string.canNotReduce);
        } else {
            goods.goods_number = String.valueOf(goods_number - 1);
            mGoodsNumberDelta = -1;
            updateGoodsNumber(goods.goods_number, goods.sku_id);
        }
    }

    private void getShopBagCallback(String response) {
        HttpResultManager.resolve(response, ShoppingBagModel.class, new HttpResultManager.HttpResultCallBack<ShoppingBagModel>() {
            @Override
            public void onSuccess(ShoppingBagModel back) {
                //这里的数据可以直接传递给下个界面，先保留

                Intent intent = new Intent(getActivity(), ShoppingBagActivity.class);
                intent.putExtra(Key.KEY_SHOP_ID.getValue(), shop_id);
                startActivity(intent);
            }

            @Override
            public void onFailure(String message) {
                goCheckOutAction();
            }

            @Override
            public void ohter(int code, String message) {
                goCheckOutAction();
            }
        }, true);
    }

    private void refreshCallback(String response) {
        mRequestQueue.cancelAll();
        mPullableLayout.topComponent.finish(Result.SUCCEED);
        HttpResultManager.resolve(response, ResponseCartModel.class, new HttpResultManager.HttpResultCallBack<ResponseCartModel>() {
            @Override
            public void onSuccess(ResponseCartModel back) {
                mModel = back;

                setUpAdapterData();
                updateCheckoutLayout();
                App.setCartNumber(mModel.data.context.cart.goods_count, CartFreeFragment.this);
                updateDeleteButton();

                mGoodsSelectPrice.setText(mModel.data.cart.select_goods_amount_format);

//                String checkOutButtonText = String.format(getResources().getString(R.string
//                        .checkoutFormat), mModel.data.cart.select_goods_number);

                String checkOutButtonText = getResources().getString(R.string
                        .goCheckout);
                mCheckoutButton.setText(checkOutButtonText);

                if (TextUtils.isEmpty(mModel.data.freebuy_list)) {
                    linearlayout_go_order_list.setVisibility(View.GONE);
                } else {
                    linearlayout_go_order_list.setVisibility(View.VISIBLE);
                }
//                selectAll();
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
            }
        });

    }

    private void refreshFailed() {
        mRequestQueue.cancelAll();
        mPullableLayout.topComponent.finish(Result.FAILED);
    }


    private void setUpAdapterData() {

        if ("1".equals(mModel.data.cart.shopping_bag)) {
            haveBag = true;
        } else {
            haveBag = false;
        }

        if (mModel.data.cart.submit_enable == 0 || Utils.isNull(mModel.data.cart.shop_list)) {
            setEnabled(false);
        } else {
            setEnabled(true);
        }

        mCartAdapter.data.clear();
        DividerModel dividerModel = new DividerModel();
//        if (!Utils.isNull(mModel.data.unpayed_list)) {
//            UnpaidOrderTitleModel unpaidOrderTitleModel = new UnpaidOrderTitleModel();
//            unpaidOrderTitleModel.number = String.valueOf(mModel.data.unpayed_list.size());
//            mCartAdapter.data.add(unpaidOrderTitleModel);
//            if (mModel.data.unpayed_list.size() > 2) {
//                mCartAdapter.data.addAll(mModel.data.unpayed_list.subList(0, 2));
//                mCartAdapter.data.add(new UnpayedListMoreModel());
//            } else {
//                mCartAdapter.data.addAll(mModel.data.unpayed_list);
//            }
//        }

        if (!Utils.isNull(mModel.data.cart.shop_list)) {
            for (ShopListModel shopListModel : mModel.data.cart.shop_list) {
                if (!Utils.isNull(shopListModel.goods_list)) {
                    if (shopListModel.goods_list.size() > 0) {
                        mCartAdapter.data.add(dividerModel);
                        mCartAdapter.data.add(shopListModel);

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

                        for (GoodsModel goodsModel : shopListModel.goods_list) {
                            mCartAdapter.data.add(goodsModel);
                            if (goodsModel.gift_list != null) {
                                for (GiftModel giftModel : goodsModel.gift_list) {
                                    mCartAdapter.data.add(giftModel);
                                }
                            }
                        }
                    }
                }
            }
        }


        if (!Utils.isNull(mModel.data.cart.invalid_list)) {
            mCartAdapter.data.add(dividerModel);
            mCartAdapter.data.add(new InvalidTitleModel());

            for (InvalidListModel entry : mModel.data.cart.invalid_list) {
                InvalidListModel invalidListModel = entry;
                mCartAdapter.data.add(invalidListModel);
            }
            mCartAdapter.data.add(new InvalidClearModel());
            mCartAdapter.data.add(dividerModel);
        }

        if (Utils.isNull(mModel.data.cart.invalid_list) && Utils.isNull(mModel.data.cart
                .shop_list)) {
            EmptyItemModel emptyModel = new EmptyItemModel();
            mCartAdapter.data.add(emptyModel);
//            linearlayout_go_order_list.setVisibility(View.GONE);

            mClearButton.setVisibility(View.GONE);
        } else {
//            linearlayout_go_order_list.setVisibility(View.VISIBLE);


            mCartAdapter.data.add(new DividerModel());
            mCartAdapter.data.add(new DividerModel());
            mCartAdapter.data.add(new DividerModel());
            mClearButton.setVisibility(View.VISIBLE);
        }


//        for(int i = 0 ; i< mCartAdapter.data.size(); i ++) {
//            Log.e("TTT",mCartAdapter.data.get(i).getClass().getName());
//        }
        mCartAdapter.notifyDataSetChanged();
    }

    private void updateCheckoutLayout() {
        mGoodsSelectPrice.setText(Utils.formatMoney(mGoodsSelectPrice.getContext(), mModel.data.cart.select_goods_amount_format));
//        mCheckoutButton.setText(String.format(getResources().getString(R.string.checkoutFormat),
//                mModel.data.cart.select_goods_number));
//
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

        CommonRequest mChangeGoodsNumberRequest = new CommonRequest(Api.API_FREEBUY_CART_CHANGE_NUMBER,
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
                mModel.data.cart = back.data.cart;
                setUpAdapterData();
                updateCheckoutLayout();
//                App.addCartNumber(mGoodsNumberDelta, this);
//                postGoodsNumberUpdated();
            }
        });
    }


    private void selectAll() {
        for (Object object : mCartAdapter.data) {
            if (object instanceof ShopListModel) {
                ShopListModel shop = (ShopListModel) object;
                shop.select = true;
            } else if (object instanceof GoodsModel) {
                GoodsModel goods = (GoodsModel) object;
                goods.select = true;
            }
        }
        mCartAdapter.notifyDataSetChanged();
        updateDeleteButton();
        cartSelect();
    }
}
