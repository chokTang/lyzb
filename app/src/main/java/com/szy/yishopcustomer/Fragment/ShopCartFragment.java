package com.szy.yishopcustomer.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.ResponseModel.Common.ResponseCommonStringModel;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.CheckoutActivity;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Adapter.ShopCartAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AddToCartModel.ResponseAddToCartModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.GoodsModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.ResponseCartModel;
import com.szy.yishopcustomer.ResponseModel.CartIndex.ShopListModel;
import com.szy.yishopcustomer.ResponseModel.CartSelect.ResponseCartSelectModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.ShopCartModel;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopCartFragment extends YSCBaseFragment {

    private static final int REQUEST_WHAT_ADD_TO_CART = 4;

    private static final int HTTP_WHAT_CART_BOX_GOODS_LIST = 1;
    private static final int HTTP_GO_CHECKOUT = 3;

    private static final int REQUEST_WHAT_REMOVE_CART = 2;
    private static final int REQUEST_WHAT_REMOVE_CART_ALL = 5;
    private static final int HTTP_CART_SELECT = 6;

    @BindView(R.id.fragment_recyclerView)
    CommonRecyclerView fragment_recyclerView;
    @BindView(R.id.textViewHaveChosen)
    TextView textViewHaveChosen;
    @BindView(R.id.textViewTotalPrice)
    TextView textViewTotalPrice;
    @BindView(R.id.fragment_shop_goods_list_checkoutButton)
    Button checkButton;
    @BindView(R.id.image_checkbox)
    ImageView imageView_select_all;

    @BindView(R.id.linearlayout_clean_all)
    LinearLayout linearlayout_clean_all;
    @BindView(R.id.linearlayout_select_all)
    LinearLayout linearlayout_select_all;

    private boolean isSelectAll = false;

    @BindView(R.id.view_close)
    View view_close;
    @BindView(R.id.fragment_shop_goods_list_cartNumberTextView)
    TextView cartNumberTextView;

    private ShopCartAdapter mAdapter;
    private ShopCartModel data;
    private String shop_id = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_shop_cart;

        mAdapter = new ShopCartAdapter();
        mAdapter.onClickListener = this;

        shop_id = getActivity().getIntent().getStringExtra("shop_id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        view_close.setOnClickListener(this);
        checkButton.setOnClickListener(this);
        linearlayout_select_all.setOnClickListener(this);
        linearlayout_clean_all.setOnClickListener(this);

        fragment_recyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragment_recyclerView.setLayoutManager(layoutManager);

        refresh();
        return view;
    }


    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_CART_BOX_GOODS_LIST, HTTP_WHAT_CART_BOX_GOODS_LIST);
        request.setAjax(true);
        request.alarm = false;
        request.add("shop_id", shop_id);
        addRequest(request);
    }

    //接口成功回调
    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_CART_BOX_GOODS_LIST:
                refreshSucceed(response);
                break;
            case REQUEST_WHAT_REMOVE_CART_ALL:
                removeGoodsFromCartAllSucceed(response);
                break;
            case REQUEST_WHAT_REMOVE_CART:
                removeGoodsFromCartSucceed(response);
                break;
            case REQUEST_WHAT_ADD_TO_CART:
                addToCartSucceed(response);
                break;
            case HTTP_GO_CHECKOUT:
                goCheckOutCallBack(response);
                break;
            case HTTP_CART_SELECT:
                cartSelectCallBack(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    /**
     * 商品添加到购物车成功
     *
     * @param response
     */
    private void addToCartSucceed(String response) {
        HttpResultManager.resolve(response, ResponseAddToCartModel.class, new HttpResultManager.HttpResultCallBack<ResponseAddToCartModel>() {
            @Override
            public void onSuccess(ResponseAddToCartModel back) {
                refresh();
            }
        }, true);
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

    public void openLoginActivity() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    private void openCheckoutActivity() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), CheckoutActivity.class);
        startActivity(intent);
    }

    //设置数据
    private void refreshSucceed(String response) {
        HttpResultManager.resolve(response, ShopCartModel.class, new HttpResultManager.HttpResultCallBack<ShopCartModel>() {
            @Override
            public void onSuccess(ShopCartModel back) {

                data = back;
                if (back.amount > back.start_price) {
                }


                setUpAdapterData();
            }
        }, true);
    }

    private void setUpAdapterData() {


        int select_num = 0;
        try {
            select_num = Integer.valueOf(data.select_goods_number);
        } catch (Exception e) {
        }

        isSelectAll = true;
        for (int i = 0, len = data.cart_goods_list.size(); i < len; i++) {
            if (!data.cart_goods_list.get(i).select) {
                isSelectAll = false;
                break;
            }
        }
        selectAll();

        cartNumberTextView.setText(data.cart_count + "");
        textViewHaveChosen.setText("（已选" + data.select_goods_number + "件)");
        textViewTotalPrice.setText("¥" + data.select_goods_amount);
        mAdapter.data.clear();
        mAdapter.data.addAll(data.cart_goods_list);
        mAdapter.notifyDataSetChanged();


        BigDecimal b1 = new BigDecimal(data.start_price);
        BigDecimal b2 = new BigDecimal(data.select_goods_amount);

        if (b2.compareTo(new BigDecimal(0)) > 0 && b2.compareTo(b1) >= 0) {
            checkButton.setEnabled(true);
            checkButton.setText("去结算");
        } else {
            checkButton.setEnabled(false);

            if (b1.subtract(b2).doubleValue() > 0) {
                checkButton.setText("还差¥" + b1.subtract(b2).doubleValue());
            } else {
                if (b1.compareTo(new BigDecimal(0)) == 0) {
                    checkButton.setText("去结算");
                } else {
                    checkButton.setText("¥" + data.start_price + "元起送");
                }

            }
        }
    }


    private void cartSelectCallBack(String response) {
        HttpResultManager.resolve(response, ResponseCartSelectModel.class, new HttpResultManager.HttpResultCallBack<ResponseCartSelectModel>() {
            @Override
            public void onSuccess(ResponseCartSelectModel model) {
                data.select_goods_amount = model.data.select_goods_amount;
                data.select_goods_number = model.data.select_goods_number;

                for (ShopListModel shop : model.cart.shop_list) {
                    if (!Utils.isNull(shop.goods_list)) {
                        for (GoodsModel goods : shop.goods_list) {
                            for (int i = 0, len = data.cart_goods_list.size(); i < len; i++) {
                                if (goods.cart_id.equals(data.cart_goods_list.get(i).cart_id)) {
                                    data.cart_goods_list.remove(i);
                                    data.cart_goods_list.add(i, goods);

                                    break;
                                }
                            }
                        }
                    }
                }
                setUpAdapterData();
            }
        });
    }

    private void removeGoodsFromCartAllSucceed(String response) {
        HttpResultManager.resolve(response, ResponseCommonStringModel
                .class, new HttpResultManager.HttpResultCallBack<ResponseCommonStringModel>() {
            @Override
            public void onSuccess(ResponseCommonStringModel back) {
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_SHOP_GOODS_LIST_REFRESH.getValue()));
                finish();
            }
        }, true);
    }

    private void removeGoodsFromCartSucceed(String response) {
        HttpResultManager.resolve(response, ResponseCartModel
                .class, new HttpResultManager.HttpResultCallBack<ResponseCartModel>() {
            @Override
            public void onSuccess(ResponseCartModel back) {
                refresh();
            }
        }, true);
    }

    @Override
    public void onClick(View v) {
        if (Utils.isDoubleClick()) {
            return;
        }

        switch (v.getId()) {
            case R.id.view_close:
                finish();
                break;
            case R.id.fragment_shop_goods_list_checkoutButton:
                //结算按钮
                goCheckOut();
                break;
            case R.id.linearlayout_select_all:
                //全选
                isSelectAll = !isSelectAll;
                selectAll();
                cartSelect();
                break;
            case R.id.linearlayout_clean_all:
                //删除全部
                removeGoodsFromCart();
                break;
            default:
                ViewType viewType = Utils.getViewTypeOfTag(v);
                int position = Utils.getPositionOfTag(v);
                switch (viewType) {
                    case VIEW_TYPE_ITEM:
                        com.szy.yishopcustomer.ResponseModel.CartIndex.GoodsModel cartGoodsListBean = mAdapter.data.get(position);

                        cartGoodsListBean.select = !cartGoodsListBean.select;

                        ImageView iv = (ImageView) v.findViewById(R.id.image_checkbox);
                        iv.setSelected(cartGoodsListBean.select);
                        cartSelect();
                        break;
                    case VIEW_TYPE_ADD:
                        add(position);
                        break;
                    case VIEW_TYPE_MINUS:
                        minus(position);
                        break;
                }
                break;
        }

    }

    private void cartSelect() {
        CommonRequest request = new CommonRequest(Api.API_CART_SELECT, HTTP_CART_SELECT, RequestMethod.POST);
        request.setAjax(true);

        List<String> cartids = getCartIdLists(false);
        for (int i = 0; i < cartids.size(); i++) {
            request.add("cart_ids[]", cartids.get(i));
        }

        request.add("shop_id", shop_id);
        addRequest(request);
    }

    private List<String> getCartIdLists(boolean flag) {
        List<String> cartids = new ArrayList<>();

        for (int i = 0; i < mAdapter.data.size(); i++) {
            com.szy.yishopcustomer.ResponseModel.CartIndex.GoodsModel cartGoodsListBean = mAdapter.data.get(i);
            if (flag) {
                cartids.add(cartGoodsListBean.cart_id);
            } else {
                if (cartGoodsListBean.select) {
                    cartids.add(cartGoodsListBean.cart_id);
                }
            }
        }
        return cartids;
    }

    private String getCart_ids(boolean flag) {
        List<String> cartids = new ArrayList<>();

        String cart_ids = "";
        for (int i = 0; i < mAdapter.data.size(); i++) {
            com.szy.yishopcustomer.ResponseModel.CartIndex.GoodsModel cartGoodsListBean = mAdapter.data.get(i);
            if (flag) {
                cartids.add(cartGoodsListBean.cart_id);
            } else {
                if (cartGoodsListBean.select) {
                    cartids.add(cartGoodsListBean.cart_id);
                }
            }
        }

        for (int i = 0; i < cartids.size(); i++) {
            cart_ids += cartids.get(i);
            if (i != cartids.size() - 1) {
                cart_ids += ",";
            }
        }

        return cart_ids;
    }

    private void selectAll() {
        imageView_select_all.setSelected(isSelectAll);

        mAdapter.selectAll(isSelectAll);
    }

    private void removeGoodsFromCart() {
        CommonRequest request = new CommonRequest(Api.API_REMOVE_CART, REQUEST_WHAT_REMOVE_CART_ALL, RequestMethod.POST);
        request.setAjax(true);
        List<String> cartids = getCartIdLists(true);
        for (int i = 0; i < cartids.size(); i++) {
            request.add("cart_ids[]", cartids.get(i));
        }
        request.add("shop_id", shop_id);
        addRequest(request);
    }

    int getGoodNum(String goods_number) {
        int good_num = 1;

        try {
            good_num = Integer.valueOf(goods_number);
        } catch (Exception e) {
        }
        return good_num;
    }

    void add(int position) {
        com.szy.yishopcustomer.ResponseModel.CartIndex.GoodsModel cartGoodsListBean = mAdapter.data.get(position);

//        cartGoodsListBean.goods_number = getGoodNum(cartGoodsListBean.goods_number) + 1 +"";
//        mAdapter.notifyDataSetChanged();

        mRequestQueue.cancelAll();

        CommonRequest request = new CommonRequest(Api.API_ADD_TO_CART, REQUEST_WHAT_ADD_TO_CART,
                RequestMethod.POST);
        request.alarm = false;

        request.setAjax(true);
        request.add("goods_id", cartGoodsListBean.goods_id);
        request.add("number", 1);
        request.add("sku_id", cartGoodsListBean.sku_id);
        request.add("shop_id", cartGoodsListBean.shop_id);

        addRequest(request);

    }

    void minus(int position) {
        com.szy.yishopcustomer.ResponseModel.CartIndex.GoodsModel cartGoodsListBean = mAdapter.data.get(position);

        int good_num = getGoodNum(cartGoodsListBean.goods_number);

        if (good_num > 1) {
            cartGoodsListBean.goods_number = --good_num + "";
            mAdapter.notifyDataSetChanged();
        } else {
            return;
        }

        mRequestQueue.cancelAll();

        CommonRequest mChangeGoodsNumberRequest = new CommonRequest(Api.API_CART_CHANGE_NUMBER,
                REQUEST_WHAT_REMOVE_CART, RequestMethod.POST);
        mChangeGoodsNumberRequest.alarm = false;
        mChangeGoodsNumberRequest.add("sku_id", cartGoodsListBean.sku_id);
        mChangeGoodsNumberRequest.add("number", cartGoodsListBean.goods_number);
        mChangeGoodsNumberRequest.add("sku_id", cartGoodsListBean.sku_id);
        mChangeGoodsNumberRequest.add("shop_id", cartGoodsListBean.shop_id);
        addRequest(mChangeGoodsNumberRequest);

        //event通知下面的商品增加删除
    }

    private void goCheckOut() {
        CommonRequest mGoCheckoutList = new CommonRequest(Api.API_GO_CHECKOUT, HTTP_GO_CHECKOUT, RequestMethod.POST);
        mGoCheckoutList.setAjax(true);
        addRequest(mGoCheckoutList);
    }

}
 