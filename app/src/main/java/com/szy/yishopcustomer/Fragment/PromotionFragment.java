package com.szy.yishopcustomer.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.TextView;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.AttributeActivity;
import com.szy.yishopcustomer.Activity.CartActivity;
import com.szy.yishopcustomer.Activity.CheckoutActivity;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Adapter.PromotionAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Attribute.AttributeModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SpecificationModel;
import com.szy.yishopcustomer.ResponseModel.BaseEntity;
import com.szy.yishopcustomer.ResponseModel.GoodsMixAmountModel;
import com.szy.yishopcustomer.ResponseModel.PromotionListModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.Attribute.ResultModel;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Side;

/**
 * A simple {@link Fragment} subclass.
 */
public class PromotionFragment extends YSCBaseFragment {

    @BindView(R.id.fragment_recyclerView)
    CommonRecyclerView fragment_recyclerView;

    @BindView(R.id.cart_number_textView)
    TextView cart_number_textView;
    @BindView(R.id.layout_float_cart)
    View relativeLayout_cart;

    private String skuid;

    private PromotionAdapter mAdapter;

    private List<PromotionListModel.PromotionList> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_promotion;

        data = new ArrayList<>();
        mAdapter = new PromotionAdapter();
        mAdapter.onClickListener = this;
        skuid = getActivity().getIntent().getStringExtra(Key.KEY_SKU_ID.getValue());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);


        fragment_recyclerView.addOnScrollListener(mOnScrollListener);
        fragment_recyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragment_recyclerView.setLayoutManager(layoutManager);
        relativeLayout_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCartActivity();
            }
        });

        refresh();
        return view;
    }

    private void openCartActivity() {
        Intent intent = new Intent(getActivity(), CartActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        int extraInfo = Utils.getExtraInfoOfTag(view);

        switch (viewType) {
            case VIEW_TYPE_GOODS:
                PromotionListModel.PromotionList curPromotionList = mAdapter.data.get(position);
                PromotionListModel.ActInfo.GoodsInfoBean goodsInfoBean = curPromotionList.act_info.goods_info.get(extraInfo);
                openGoodsActivity(goodsInfoBean.sku_id);
                break;
            case VIEW_TYPE_SELECT:
                curPromotionListPosition = position;
                curGoodPosition = extraInfo;
                openAttributeActivity(Macro.BUTTON_TYPE_PROMOTION);
                break;
            case VIEW_TYPE_ADD_TO_CART:
                PromotionListModel.PromotionList promotionList = mAdapter.data.get(position);
                addToCart(promotionList);
                break;
            case VIEW_TYPE_BUY_NOW:
                quickBuy();
                break;
            default:
                super.onClick(view);
                break;
        }
    }

    public void openGoodsActivity(String skuId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_SKU_ID.getValue(), skuId);
        intent.setClass(getActivity(), GoodsActivity.class);
        startActivity(intent);
    }

    private int addCartNum = 0;

    public void getGoodsMixAmount() {

        if (mAdapter.Verification()) {
            PromotionListModel.PromotionList promotionList = mAdapter.data.get(mAdapter.openPosition);
//            int buy_num = promotionList.buy_number;
            int buy_num = 1;

            StringBuffer all_sku_ids = new StringBuffer();
            for (int i = 0, len = promotionList.act_info.goods_info.size(); i < len; i++) {
                PromotionListModel.ActInfo.GoodsInfoBean goodsInfoBean = promotionList.act_info.goods_info.get(i);

                if (!TextUtils.isEmpty(goodsInfoBean.sku_id) && !"0".equals(goodsInfoBean.sku_id)) {
                    all_sku_ids.append(goodsInfoBean.sku_id);
                    all_sku_ids.append(",");
                }
            }

            CommonRequest request = new CommonRequest(Api.API_GOODS_MIX_AMOUNT, HttpWhat
                    .HTTP_GOODS_MIX_AMOUNT.getValue(), RequestMethod.POST);
            request.add("all_sku_ids", all_sku_ids.toString());
            request.add("act_id", promotionList.act_info.act_id);
            request.add("goods_number", buy_num);
            addRequest(request);

        }
    }

    public void addToCart(PromotionListModel.PromotionList promotionList) {
        CommonRequest mAddToCartRequest = new CommonRequest(Api.API_ADD_TO_CART, HttpWhat
                .HTTP_ADD_TO_CART.getValue(), RequestMethod.POST);
        mAddToCartRequest.add("act_id", promotionList.act_info.act_id);
        mAddToCartRequest.add("number", promotionList.buy_number);
        mAddToCartRequest.add("act_type", promotionList.act_info.act_type);

        addCartNum = promotionList.buy_number * promotionList.act_info.goods_info.size();
        for (int i = 0, len = promotionList.act_info.goods_info.size(); i < len; i++) {
            mAddToCartRequest.add("sku_ids[]", promotionList.act_info.goods_info.get(i).sku_id);
        }
        mAddToCartRequest.add("sku_id", 0);
        mAddToCartRequest.setAjax(true);
        addRequest(mAddToCartRequest);
    }

    public void quickBuy() {
        PromotionListModel.PromotionList promotionList = mAdapter.data.get(mAdapter.openPosition);

        String api = Api.API_QUICK_BUY;
        CommonRequest mQuickBuyRequest = new CommonRequest(api, HttpWhat
                .HTTP_QUICK_BUY.getValue(), RequestMethod.POST);
        mQuickBuyRequest.add("act_id", promotionList.act_info.act_id);
        mQuickBuyRequest.add("number", promotionList.buy_number);
        mQuickBuyRequest.add("act_type", promotionList.act_info.act_type);

        addCartNum = promotionList.buy_number * promotionList.act_info.goods_info.size();
        for (int i = 0, len = promotionList.act_info.goods_info.size(); i < len; i++) {
            mQuickBuyRequest.add("sku_ids[]", promotionList.act_info.goods_info.get(i).sku_id);
        }
        mQuickBuyRequest.add("sku_id", 0);
        addRequest(mQuickBuyRequest);
    }

    private int curPromotionListPosition;
    private int curGoodPosition;

    private void openAttributeActivity(int buttonType) {
        Intent intent = new Intent();

        PromotionListModel.PromotionList curPromotionList = mAdapter.data.get(curPromotionListPosition);
        PromotionListModel.ActInfo.GoodsInfoBean goodsInfoBean = curPromotionList.act_info.goods_info.get(curGoodPosition);
        //属性传值
        ArrayList<com.szy.yishopcustomer.ResponseModel.Attribute.SkuModel> skuList = new
                ArrayList<>();
        if (curPromotionList.sku_list != null) {
            for (Map.Entry<String, com.szy.yishopcustomer.ResponseModel.Attribute.SkuModel> entry
                    : curPromotionList.sku_list.entrySet()) {
                skuList.add(curPromotionList.sku_list.get(entry.getKey()));
            }
        }


        ArrayList<SpecificationModel> specificationList = new ArrayList<>();
        if (goodsInfoBean.spec_list != null) {
            specificationList.addAll(goodsInfoBean.spec_list);
        }
        intent.putParcelableArrayListExtra(Key.KEY_SPECIFICATION_LIST.getValue(),
                specificationList);

        //筛选去除不属于这个商品的sku
        List<String> vids = new ArrayList<>();

        int attrCount = 0;
        for (SpecificationModel specificationModel : specificationList) {

            attrCount++;
            for (AttributeModel entry : specificationModel.attr_values) {
                AttributeModel attributeModel = entry;
                vids.add(attributeModel.attr_vid);
            }
        }


        for (int i = 0; i < skuList.size(); i++) {
            String[] ss = skuList.get(i).spec_vids.split("-");
            List<String> ssList = Arrays.asList(ss);

            if (!listIsContains(ssList, vids) || attrCount != ssList.size()) {
                skuList.remove(i);
                i--;
            }
        }

        //-------------------
        intent.putParcelableArrayListExtra(Key.KEY_SKU_LIST.getValue(), skuList);
        intent.putExtra(Key.KEY_SKU_ID.getValue(), goodsInfoBean.sku_id);
        intent.putExtra(Key.KEY_GOODS_ID.getValue(), goodsInfoBean.goods_id);
        intent.putExtra(Key.KEY_TYPE.getValue(), buttonType);


        //是否请求接口
        intent.putExtra(Key.KEY_REQUEST_INTERFACE.getValue(), false);
        intent.putExtra(AttributeFragment.TYPE_ATTR_ID, true);

        intent.setClass(getActivity(), AttributeActivity.class);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_ADD_CART.getValue());
    }

    private boolean listIsContains(List<String> li1, List<String> li2) {
        boolean isMatched = true;
        for (String obj : li1) {
            if (!li2.contains(obj)) {
                isMatched = false;
                break;
            }
        }

        return isMatched;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (RequestCode.valueOf(requestCode)) {
            case REQUEST_CODE_ADD_CART:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    ResultModel resultModel = data.getParcelableExtra(Key.KEY_RESULT.getValue());
                    if (resultModel.resultType.equals(Macro.RESULT_TYPE_BUY_NOW_GROUP)) {
                        PromotionListModel.PromotionList curPromotionList = mAdapter.data.get(curPromotionListPosition);
                        PromotionListModel.ActInfo.GoodsInfoBean goodsInfoBean = curPromotionList.act_info.goods_info.get(curGoodPosition);
                        goodsInfoBean.sku_id = resultModel.skuId;
                        goodsInfoBean.specValue = resultModel.specValue;
                        goodsInfoBean.goods_number = resultModel.goodStock;

                        mAdapter.notifyDataSetChanged();

                        //判断是否全部商品的属性都确定了，然后获取原价格
                        getGoodsMixAmount();
                    }
                }
                break;
            case REQUEST_CODE_LOGIN_FOR_QUICK_BUY:
                if (resultCode == Activity.RESULT_OK) {
                    quickBuy();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;

        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_SIMPLE:
                refreshCallback(response);
                break;
            case HTTP_ADD_TO_CART:
                refreshCallbackAddCart(response);
                break;
            case HTTP_GOODS_MIX_AMOUNT:
                goodsMixAmountCallback(response);
                break;
            case HTTP_QUICK_BUY:
                quickBuyCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
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
                openLoginActivityForResult(RequestCode.REQUEST_CODE_LOGIN_FOR_QUICK_BUY);
            }
        }, true);
    }

    private void openLoginActivityForResult(RequestCode requestCode) {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivityForResult(intent, requestCode.getValue());
    }

    public void goCheckout() {
        Intent intent = new Intent();
        intent.setClass(getContext(), CheckoutActivity.class);
        startActivity(intent);
    }

    private void goodsMixAmountCallback(String response) {
        HttpResultManager.resolve(response, GoodsMixAmountModel.class, new HttpResultManager.HttpResultCallBack<GoodsMixAmountModel>() {
            @Override
            public void onSuccess(GoodsMixAmountModel back) {
                PromotionListModel.PromotionList curPromotionList = mAdapter.data.get(curPromotionListPosition);
                curPromotionList.act_info.min_all_goods = back.goods_price_amount;
                curPromotionList.act_info.max_all_goods = back.goods_price_amount;

                curPromotionList.act_info.min_all_goods_format = back.goods_price_amount_format;
                curPromotionList.act_info.max_all_goods_format = back.goods_price_amount_format;

                mAdapter.notifyDataSetChanged();
            }
        }, true);

    }

    private void refreshCallbackAddCart(String response) {
        HttpResultManager.resolve(response, BaseEntity.class, new HttpResultManager.HttpResultCallBack<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity back) {
                App.addCartNumber(addCartNum, PromotionFragment.this);
                toast(back.getMessage());
            }
        }, true);
    }

    @Subscribe
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_UPDATE_CART_NUMBER:
                String cartNumber = App.getCartString();
                if ("99+".equals(cartNumber)) {
                    cart_number_textView.setTextSize(8);
                } else {
                    if (Integer.parseInt(App.getCartString()) >= 10) {
                        cart_number_textView.setTextSize(8);
                    } else {
                        cart_number_textView.setTextSize(10);
                    }
                }
                cart_number_textView.setText(cartNumber);
                break;
        }
    }

    @Override
    void refresh() {
        CommonRequest request = new CommonRequest(Api.API_PACKAGE,
                HttpWhat.HTTP_SIMPLE.getValue());
        request.add("sku_id", skuid);
        addRequest(request);
    }

    @Override
    protected void onRequestFinish(int what) {
        super.onRequestFinish(what);
    }

    private void refreshCallback(String response) {
        HttpResultManager.resolve(response, PromotionListModel.class, new HttpResultManager.HttpResultCallBack<PromotionListModel>() {
            @Override
            public void onSuccess(PromotionListModel back) {
                mAdapter.data.clear();

                if (!TextUtils.isEmpty(back.data.context.cart.goods_count)) {
                    cart_number_textView.setText(back.data.context.cart.goods_count);
                }

                Map<String, PromotionListModel.PromotionList> map = back.data.list;

                for (Map.Entry<String, PromotionListModel.PromotionList> entry : map.entrySet()) {
                    data.add(entry.getValue());
                }

                mAdapter.data.addAll(data);
                mAdapter.notifyDataSetChanged();
            }
        });
    }


    private boolean upDataSuccess = true;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 0) {
                if (fragment_recyclerView.reachEdgeOfSide(Side.BOTTOM)) {
                    if (upDataSuccess) {
//                        if (data != null) {
//                            cur_page = data.data.page.cur_page + 1;
//                            refresh();
//                        }
                    }
                }
            }
        }
    };
}
