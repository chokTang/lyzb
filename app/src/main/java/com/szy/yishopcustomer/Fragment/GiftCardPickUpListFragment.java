package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.AttributeActivity;
import com.szy.yishopcustomer.Activity.CheckoutExchangeActivity;
import com.szy.yishopcustomer.Activity.GiftCardPickUpActivity;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Adapter.GiftCardPickUpListAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AddToCartModel.ResponseAddToCartModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SkuModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SpecificationModel;
import com.szy.yishopcustomer.ResponseModel.GiftCard.GiftCardPickUpListModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.Attribute.ResultModel;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Side;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * Created by Smart on 2017/11/16.
 */
public class GiftCardPickUpListFragment extends YSCBaseFragment {

    @BindView(R.id.fragment_pullableLayout)
    PullableLayout fragment_pullableLayout;
    @BindView(R.id.fragment_recyclerView)
    CommonRecyclerView fragment_recyclerView;

    @BindView(R.id.textView_exit_card_list)
    TextView textView_exit_card_list;
    @BindView(R.id.textView_card_no)
    TextView textView_card_no;

    GiftCardPickUpListAdapter mAdapter;

    private GiftCardPickUpListModel mModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_gift_card_pick_up_list;
        mAdapter = new GiftCardPickUpListAdapter();
        mAdapter.onClickListener = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        textView_exit_card_list.setOnClickListener(this);

        fragment_recyclerView.addOnScrollListener(mOnScrollListener);
        fragment_recyclerView.setAdapter(mAdapter);
        fragment_recyclerView.addItemDecoration(new SpacingItemDecoration());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragment_recyclerView.setLayoutManager(layoutManager);

        refresh();
        return view;
    }

    public class SpacingItemDecoration extends RecyclerView.ItemDecoration {

        public SpacingItemDecoration() {
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position

            if(position == 0) {
                outRect.top = Utils.dpToPx(getContext(), 10);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView_exit_card_list:
                showConfirmDialog(R.string.confirmExitPickupList, ViewType.VIEW_TYPE_SCAN
                        .ordinal(), 0);
                break;
            default:
                ViewType viewType = Utils.getViewTypeOfTag(view);
                int position = Utils.getPositionOfTag(view);
                GiftCardPickUpListModel.DataBean.ListBean temp = mAdapter.data.get(position);
                switch (viewType) {
                    case VIEW_TYPE_GOODS:
                        //进入商品详情
                        Intent intent = new Intent();
                        intent.putExtra(Key.KEY_GOODS_ID.getValue(), temp.goods_id + "");
                        intent.setClass(getActivity(), GoodsActivity.class);
                        startActivity(intent);
                        break;
                    case VIEW_TYPE_SUBMIT:
                        tempSubmitObj = temp;
                        addGift(temp.sku_id, temp.goods_id, "0");
                        break;
                }
                break;
        }
        super.onClick(view);
    }

    GiftCardPickUpListModel.DataBean.ListBean tempSubmitObj;

    private void openAttributeActivity(ResponseAddToCartModel model) {
        Intent intent = new Intent();
        ArrayList<SkuModel> skuList = new ArrayList<>();
        if (model.data.sku_list != null) {
            for (Map.Entry<String, SkuModel> entry : model.data.sku_list.entrySet()) {
                skuList.add(model.data.sku_list.get(entry.getKey()));
            }
        }
        intent.putParcelableArrayListExtra(Key.KEY_SKU_LIST.getValue(), skuList);
        ArrayList<SpecificationModel> specificationList = new ArrayList<>();
        if (model.data.spec_list != null) {
            specificationList.addAll(model.data.spec_list);
        }
        intent.putParcelableArrayListExtra(Key.KEY_SPECIFICATION_LIST.getValue(),
                specificationList);

        intent.putExtra(Key.KEY_SKU_ID.getValue(), tempSubmitObj.sku_id);
        intent.putExtra(Key.KEY_GOODS_ID.getValue(), tempSubmitObj.goods_id);
        intent.putExtra(Key.KEY_TYPE.getValue(), Macro.BUTTON_TYPE_PROMOTION);

        intent.setClass(getActivity(), AttributeActivity.class);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_ADD_CART.getValue());

    }

    private void openAttributeActivitySucceed(Intent data) {
        if (data != null) {
            ResultModel resultModel = data.getParcelableExtra(Key.KEY_RESULT.getValue());

            tempSubmitObj.sku_id = resultModel.skuId;

            if (resultModel.resultType.equals(Macro.RESULT_TYPE_BUY_NOW_GROUP)) {
                addGift(resultModel.skuId, tempSubmitObj.goods_id, "1");
            }
        }
    }

    public void addGift(String skuId, String goodid, String is_sku) {
        CommonRequest mQuickBuyRequest = new CommonRequest(Api.API_CART_ADD_GIFT, HttpWhat
                .HTTP_CART_ADD_GIFT.getValue(), RequestMethod.POST);
        mQuickBuyRequest.add("goods_id", goodid);
        mQuickBuyRequest.add("sku_id", skuId);
        mQuickBuyRequest.add("is_sku", is_sku);
        addRequest(mQuickBuyRequest);
    }

    public void quickBuy(String skuId, String goodid) {
        String api = Api.API_QUICK_BUY;

        CommonRequest mQuickBuyRequest = new CommonRequest(api, HttpWhat
                .HTTP_QUICK_BUY.getValue(), RequestMethod.POST);
        mQuickBuyRequest.add("goods_id", goodid);
        mQuickBuyRequest.add("sku_id", skuId);
        mQuickBuyRequest.add("gift", 1);
        addRequest(mQuickBuyRequest);
    }

    private void quickBuyCallback(String response) {
        HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager
                .HttpResultCallBack<ResponseCommonModel>() {
            @Override
            public void onSuccess(ResponseCommonModel back) {
                goCheckout();
            }

            @Override
            public void onLogin() {
//                super.onLogin();
                openLoginActivityForResult(RequestCode.REQUEST_CODE_LOGIN_FOR_QUICK_BUY);
            }

        }, true);
    }

    private void addGiftCallback(String response) {
        HttpResultManager.resolve(response, ResponseAddToCartModel.class, new HttpResultManager
                .HttpResultCallBack<ResponseAddToCartModel>() {
            @Override
            public void onSuccess(ResponseAddToCartModel back) {

                if (back.data.sku_open.contentEquals("1")) {
                    openAttributeActivity(back);
                } else {
                    quickBuy(tempSubmitObj.sku_id, tempSubmitObj.goods_id);
                }
            }

        }, true);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (RequestCode.valueOf(requestCode)) {
            case REQUEST_CODE_LOGIN_FOR_QUICK_BUY:
                if (resultCode == Activity.RESULT_OK) {
                    quickBuy(tempSubmitObj.sku_id, tempSubmitObj.goods_id);
                }
                break;
            case REQUEST_CODE_ADD_CART:
                if (resultCode == Activity.RESULT_OK) {
                    openAttributeActivitySucceed(data);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }

    }

    private void openLoginActivityForResult(RequestCode requestCode) {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivityForResult(intent, requestCode.getValue());
    }

    public void goCheckout() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), CheckoutExchangeActivity.class);
        intent.putExtra(CheckoutExchangeFragment.ORDER_TYPE, CheckoutExchangeFragment.TYPE_GIFTCARD);
        startActivity(intent);

        getActivity().finish();
    }

    @Override
    public void onConfirmDialogConfirmed(int viewType, int position, int extraInfo) {
        exitCardList();
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_USER_GIFT_CARD_GOODS, HttpWhat
                .HTTP_INDEX.getValue(), RequestMethod.GET);
        addRequest(request);
    }

    //退出提货券
    public void exitCardList() {
        CommonRequest request = new CommonRequest(Api.API_USER_GIFT_CARD_EXIT, HttpWhat
                .HTTP_SIMPLE.getValue(), RequestMethod.GET);
        addRequest(request);
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_INDEX:
                HttpResultManager.resolve(response, GiftCardPickUpListModel.class, new HttpResultManager.HttpResultCallBack<GiftCardPickUpListModel>() {

                    @Override
                    public void onSuccess(GiftCardPickUpListModel back) {
                        mModel = back;
                        setUpAdapterData();

                    }

                }, true);
                break;
            case HTTP_SIMPLE:
                HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
                    @Override
                    public void onSuccess(ResponseCommonModel back) {
                        startActivity(new Intent(getActivity(), GiftCardPickUpActivity.class));
                        getActivity().finish();
                    }
                }, true);
                break;
            case HTTP_QUICK_BUY:
                quickBuyCallback(response);
                break;
            case HTTP_CART_ADD_GIFT:
                addGiftCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private void setUpAdapterData() {
        mAdapter.shop_name = mModel.data.shop_info.shop_name;
        textView_card_no.setText("卡号：" + mModel.data.card_info.card_no);
        mAdapter.data.clear();
        mAdapter.data.addAll(mModel.data.list);
        mAdapter.notifyDataSetChanged();
    }

    private boolean upDataSuccess = true;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 0) {
                if (fragment_recyclerView.reachEdgeOfSide(Side.BOTTOM)) {
                    if (upDataSuccess) {
                        if (mModel != null) {
                        }
                    }
                }
            }
        }
    };

}
