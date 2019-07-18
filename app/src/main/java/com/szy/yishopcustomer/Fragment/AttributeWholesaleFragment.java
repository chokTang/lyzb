package com.szy.yishopcustomer.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.Util.ImageLoader;
import com.szy.common.View.CommonRecyclerView;
import com.szy.common.View.FlowLayoutManager;
import com.szy.yishopcustomer.Activity.CheckoutActivity;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Adapter.AttributeWholesaleAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Attribute.AttributeModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SkuModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SpecificationModel;
import com.szy.yishopcustomer.ResponseModel.BaseEntity;
import com.szy.yishopcustomer.ResponseModel.Goods.AddToCartModel;
import com.szy.yishopcustomer.ResponseModel.Goods.DataModel;
import com.szy.yishopcustomer.ResponseModel.GoodsGetStepPriceModel;
import com.szy.yishopcustomer.ResponseModel.GoodsWholeAttrModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.Attribute.AttributeLineModel;
import com.szy.yishopcustomer.ViewModel.Attribute.ResultModel;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Smart on 2017/9/8.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class AttributeWholesaleFragment extends YSCBaseFragment {

    @BindView(R.id.fragment_attribute_recyclerView)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.fragment_attribute_goodsImageView)
    ImageView mGoodsImageView;
    @BindView(R.id.fragment_attribute_closeButton)
    Button mCloseButton;
    @BindView(R.id.fragment_attribute_addToCartLayout)
    View mNormalButtonLayout;

    @BindView(R.id.fragment_attribute_numberWrapperRelativeLayout)
    LinearLayout mNumberWrapperRelativeLayout;
    @BindView(R.id.fragment_attribute_addToCartButton)
    Button mAddToCartButton;
    @BindView(R.id.fragment_attribute_buyNowButton)
    Button fragment_attribute_buyNowButton;

    @BindView(R.id.fragment_attribute_priceTextView)
    TextView mPriceTextView;
    @BindView(R.id.fragment_attribute_stockTextView)
    TextView mStockTextView;
    @BindView(R.id.fragment_attribute_hide_layout)
    LinearLayout mHideLayout;

    @BindView(R.id.fragment_goods_step_price)
    LinearLayout mGoodsStepPrice;

    @BindView(R.id.item_cart_goods_number)
    EditText item_cart_goods_number;
    @BindView(R.id.item_cart_goods_minus_button)
    ImageView item_cart_goods_minus_button;
    @BindView(R.id.item_cart_goods_add_button)
    ImageView item_cart_goods_add_button;
    @BindView(R.id.textView_sku_name)
    TextView textView_sku_name;

    private int singleNum = 0;

    //单一属性的商品
    @BindView(R.id.linearlayout_single_attribute_goods)
    LinearLayout linearlayout_single_attribute_goods;

    private String mSkuId;
    private String mGoodsId;
    private com.szy.yishopcustomer.ResponseModel.Goods.SkuModel mSkuModel;
    private ResultModel mResultModel;
    private AttributeWholesaleAdapter mAdapter;
    private List<SkuModel> mSkuList;
    private List<SpecificationModel> mSpecificationList;

    private DataModel data;

    private int mType;

    //用于保存最后一组数据
    private List<AttributeModel> last_attributeModels;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_attribute_wholesale;

        Bundle arguments = getArguments();
        if (arguments == null) {
            Toast.makeText(getContext(), "Empty arguments", Toast.LENGTH_SHORT).show();
            return;
        }

        String temp = arguments.getString("data");
        if (!TextUtils.isEmpty(temp)) {
            data = JSON.parseObject(temp, DataModel.class);
        }

        mSkuList = arguments.getParcelableArrayList(Key.KEY_SKU_LIST.getValue());
        mSpecificationList = arguments.getParcelableArrayList(Key.KEY_SPECIFICATION_LIST.getValue
                ());

        mSkuId = arguments.getString(Key.KEY_SKU_ID.getValue());
        mGoodsId = arguments.getString(Key.KEY_GOODS_ID.getValue());
        mType = arguments.getInt(Key.KEY_TYPE.getValue(), Macro.BUTTON_TYPE_ADD_TO_CART);

        last_attributeModels = new ArrayList<>();

        mAdapter = new AttributeWholesaleAdapter();
        mAdapter.onClickListener = this;
        mResultModel = new ResultModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        init();

        //属性列表显示与否
        if (!Utils.isNull(mSkuList) && !Utils.isNull(mSpecificationList)) {
            FlowLayoutManager layoutManager = new FlowLayoutManager();
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(mAdapter);
            setUpAdapterData();
        } else {
            mRecyclerView.setVisibility(View.GONE);
            linearlayout_single_attribute_goods.setVisibility(View.VISIBLE);

            textView_sku_name.setText(data.goods.goods_number + "件可售");
        }

        Utils.setViewTypeForTag(mCloseButton, ViewType.VIEW_TYPE_CLOSE);
        mCloseButton.setOnClickListener(this);
        Utils.setViewTypeForTag(mHideLayout, ViewType.VIEW_TYPE_CLOSE);
        mHideLayout.setOnClickListener(this);

        if (mType == Macro.BUTTON_TYPE_ADD_TO_CART) {
            Utils.setViewTypeForTag(mAddToCartButton, ViewType.VIEW_TYPE_ADD_TO_CART);
            mAddToCartButton.setOnClickListener(this);

            fragment_attribute_buyNowButton.setVisibility(View.GONE);
            mAddToCartButton.setVisibility(View.VISIBLE);
            mAddToCartButton.setText(R.string.confirm);

        } else if (mType == Macro.BUTTON_TYPE_BUY_NOW) {
            Utils.setViewTypeForTag(fragment_attribute_buyNowButton, ViewType.VIEW_TYPE_BUY_NOW);
            fragment_attribute_buyNowButton.setOnClickListener(this);

            fragment_attribute_buyNowButton.setText(R.string.confirm);
        } else if (mType == Macro.BUTTON_TYPE_ATTRIBUTE) {
            Utils.setViewTypeForTag(fragment_attribute_buyNowButton, ViewType.VIEW_TYPE_BUY_NOW);
            fragment_attribute_buyNowButton.setOnClickListener(this);

            mAddToCartButton.setVisibility(View.VISIBLE);
            Utils.setViewTypeForTag(mAddToCartButton, ViewType.VIEW_TYPE_ADD_TO_CART);
            mAddToCartButton.setOnClickListener(this);
        }

        mStockTextView.setText(Html.fromHtml("共<font color='#f23030'>0</font>件"));

        Utils.setViewTypeForTag(item_cart_goods_minus_button, ViewType.VIEW_TYPE_MINUS);
        Utils.setPositionForTag(item_cart_goods_minus_button, -1);
        item_cart_goods_minus_button.setOnClickListener(this);

        Utils.setViewTypeForTag(item_cart_goods_add_button, ViewType.VIEW_TYPE_PLUS);
        Utils.setPositionForTag(item_cart_goods_add_button, -1);
        item_cart_goods_add_button.setOnClickListener(this);


        item_cart_goods_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    openEditNumberTwo();
                }
            }
        });

        if (data != null) {
            ImageLoader.displayImage(Utils.urlOfImage(data.goods.goods_image), mGoodsImageView);
            //设置显示批发价格
            mGoodsStepPrice.setVisibility(View.VISIBLE);
            mGoodsStepPrice.removeAllViews();
            View whole = LayoutInflater.from(getActivity()).inflate(R.layout
                    .fragment_goods_whole_price_child_two, null);
            TextView textView_whole_price_key = (TextView) whole.findViewById(R.id
                    .textView_whole_price_key);
            TextView textView_whole_price_value = (TextView) whole.findViewById(R.id
                    .textView_whole_price_value);
            textView_whole_price_key.setText("售价");
            textView_whole_price_value.setText("起批量");

            textView_whole_price_key.setTextColor(Color.parseColor("#666666"));
            textView_whole_price_value.setTextColor(Color.parseColor("#666666"));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup
                    .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, Utils.dpToPx(getContext(), 8), 0, 0);
            whole.setLayoutParams(params);
            mGoodsStepPrice.addView(whole);

            for (String key : data.sku.step_price.keySet()) {
                whole = LayoutInflater.from(getActivity()).inflate(R.layout
                        .fragment_goods_whole_price_child_two, null);
                textView_whole_price_key = (TextView) whole.findViewById(R.id
                        .textView_whole_price_key);
                textView_whole_price_value = (TextView) whole.findViewById(R.id
                        .textView_whole_price_value);

                String price = data.sku.step_price.get(key);
                try {
                    double dprice = Double.valueOf(price);
                    textView_whole_price_key.setText("¥" + price);
                } catch (Exception e) {
                    textView_whole_price_key.setText(price);
                }
                textView_whole_price_value.setText(key + data.goods.unit_name);

                textView_whole_price_key.setTextColor(textView_whole_price_value.getTextColors()
                        .getDefaultColor());
                textView_whole_price_value.setTextColor(Color.parseColor("#666666"));

                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, Utils.dpToPx(getContext(), 8), 0, 0);
                whole.setLayoutParams(params);
                mGoodsStepPrice.addView(whole);
            }

        }
        return view;
    }


    private void init() {
        //提取最后一组得spec，存入last_attributeModels，将其作为数据源
        if (mSpecificationList != null && mSpecificationList.size() > 0) {
            //默认设置第一个属性为选中，记录每一个选中得specid
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0, len = mSpecificationList.size() - 1; i < len; i++) {
                for (AttributeModel entry : mSpecificationList.get(i)
                        .attr_values) {
                    AttributeModel attributeModel = entry;
                    stringBuffer.append(attributeModel.spec_id);
                    stringBuffer.append("|");
                    attributeModel.selected = true;
                    break;
                }
            }

            SpecificationModel specificationModel = mSpecificationList.get(mSpecificationList
                    .size() - 1);
            for (AttributeModel attributeModel : specificationModel.attr_values) {
                attributeModel.isLast = true;
                //将第一次拼接好得spec_ids保存到属性里面，用于保存数量
                String spec_ids = stringBuffer.toString() + attributeModel.spec_id;
                attributeModel.spec_ids = spec_ids;
                try {
                    SkuModel skuModel = getSkuModel(spec_ids);
                    if (skuModel != null) {
                        //获取商品库存
                        attributeModel.last_stock = Integer.valueOf(skuModel.goods_number);
                    }
                } catch (Exception e) {
                }

                last_attributeModels.add(attributeModel);
            }
        }
    }

    public SkuModel getSkuModel(String spec_ids) {
        List<String> specificationList = Arrays.asList(spec_ids.split("\\|"));

        for (int i = 0, len = mSkuList.size(); i < len; i++) {
            List<String> temp = Arrays.asList(mSkuList.get(i).spec_ids.split("\\|"));
            if (Utils.isSame(specificationList, temp)) {
                return mSkuList.get(i);
            }
        }
        return null;
    }


    public AttributeModel getAttributeModel(int position) {
        int cursor = 0;
        for (SpecificationModel specificationModel : mSpecificationList) {
            for (AttributeModel entry : specificationModel.attr_values) {
                cursor++;
                if (cursor == position) {
                    return entry;
                }
            }
            cursor++;
            cursor++;
        }
        return null;
    }

    public List<SkuModel> getSelectedSkuModel() {
        StringBuffer stringBuffer = new StringBuffer();
        for (SpecificationModel specificationModel : mSpecificationList) {
            for (AttributeModel entry : specificationModel.attr_values) {
                AttributeModel attributeModel = entry;
                if (attributeModel.selected && !attributeModel.isLast) {
                    stringBuffer.append(attributeModel.spec_id);
                    stringBuffer.append("|");
                }
            }
        }

        List<SkuModel> selectedSkuModel = new ArrayList<>();
        for (SkuModel skuModel : mSkuList) {
            List<String> specificationList = Arrays.asList(skuModel.spec_ids.split("\\|"));

            for (AttributeModel lastSpecId : last_attributeModels) {
                String spec_ids = stringBuffer.toString() + lastSpecId.spec_id;
                List<String> tempSpec = Arrays.asList(spec_ids.split("\\|"));
                if (Utils.isSame(specificationList, tempSpec)) {
                    lastSpecId.spec_ids = spec_ids;
                    lastSpecId.last_goodNum = skuModel.QTY;
                    lastSpecId.last_stock = Integer.valueOf(skuModel.goods_number);
                    selectedSkuModel.add(skuModel);
                }
            }
        }
        return selectedSkuModel;
    }

    public SpecificationModel getSpecificationModel(int position) {
        int cursor = 0;
        for (int i = 0; i < mSpecificationList.size(); i++) {
            SpecificationModel specificationModel = mSpecificationList.get(i);
            for (int j = 0; j < specificationModel.attr_values.size(); j++) {
                cursor++;
                if (cursor == position) {
                    return specificationModel;
                }
            }
            cursor++;
            cursor++;
        }
        return null;
    }

    @Override
    public void onClick(View view) {
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        int extraInfo = Utils.getExtraInfoOfTag(view);

        switch (viewType) {
            case VIEW_TYPE_CLOSE:
                finish();
                break;
            case VIEW_TYPE_ITEM:
                selectAttribute(getSpecificationModel(position).attr_id, getAttributeModel
                        (position).spec_id);
                break;
            case VIEW_TYPE_MINUS:
                if (position == -1) {
                    reduceGoodsNumberTwo();
                } else {
                    reduceGoodsNumber(position);
                }
                break;
            case VIEW_TYPE_PLUS:
                if (position == -1) {
                    increaseGoodsNumberTwo();
                } else {
                    increaseGoodsNumber(position);
                }
                break;
            case VIEW_TYPE_EDIT:
                openEditNumber(position);
                break;
            case VIEW_TYPE_BUY_NOW:
                quickBuy();
                break;
            case VIEW_TYPE_ADD_TO_CART:
                addToCart();
                break;
        }
    }

    public void openEditNumberTwo() {
        int maxNumber = Integer.valueOf(data.goods.goods_number);
        if (singleNum < maxNumber && singleNum >= 0) {
            singleNum = Integer.parseInt(item_cart_goods_number.getText().toString());
        }
        getStepPrice();
    }

    public void openEditNumber(int position) {
        AttributeModel attributeModel = (AttributeModel) mAdapter.data.get(position);

        int goodsNumber = attributeModel.last_goodNum;
        int maxNumber = attributeModel.last_stock;

        if (goodsNumber >= maxNumber || goodsNumber <= 0) {
            return;
        }
        attributeModel.last_goodNum = goodsNumber;
        getSkuModel(attributeModel.spec_ids).QTY = attributeModel.last_goodNum;
        getStepPrice();

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GOODS_GET_WHOLE_ATTR:
                refreshCallback(response);
                break;
            case HTTP_GOODS_GET_STEP_PRICE:
                refreshGetPrice(response);
                break;
            case HTTP_ADD_TO_CART:
                addToCartCallback(response);
                break;
            case HTTP_QUICK_BUY:
                quickBuyCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    public void refresh() {
//        CommonRequest request = new CommonRequest(Api.API_GOODS_GET_WHOLE_ATTR, HttpWhat
// .HTTP_GOODS_GET_WHOLE_ATTR
//                .getValue());
//
//        request.add("goods_id", mGoodsId);
//        List<String> selectedSpec = getSelectSpecModel();
//        for (int i = 0, len = selectedSpec.size(); i < len; i++) {
//            request.add("sku_list[]", selectedSpec.get(i));
//        }
//
//        addRequest(request);

        getSelectedSkuModel();
    }

    private void finish(String type) {
        mResultModel.resultType = type;

//        if (!Utils.isNull(getSelectedSkuModel())) {
//            mResultModel.skuId = getSelectedSkuModel().sku_id;
//            if (mSkuModel != null) {
//                mResultModel.specValue = mSkuModel.spec_attr_value;
//            }
//
//        } else {
//            mResultModel.skuId = mSkuId;
//        }

        Intent intent = new Intent();
        intent.putExtra(Key.KEY_RESULT.getValue(), mResultModel);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void increaseGoodsNumberTwo() {
        int maxNumber = Integer.valueOf(data.goods.goods_number);
        if (singleNum < maxNumber) {
            singleNum += 1;
        }
        item_cart_goods_number.setText(singleNum + "");
        getStepPrice();
    }

    private void increaseGoodsNumber(int position) {
        AttributeModel attributeModel = (AttributeModel) mAdapter.data.get(position);

        int goodsNumber = attributeModel.last_goodNum;
        int maxNumber = attributeModel.last_stock;

        if (goodsNumber >= maxNumber) {
            return;
        }
        attributeModel.last_goodNum = ++goodsNumber;
        getSkuModel(attributeModel.spec_ids).QTY = attributeModel.last_goodNum;
        getStepPrice();

        mAdapter.notifyDataSetChanged();


        //调用网络接口获取数据更新价格
    }

    private void reduceGoodsNumberTwo() {
        if (singleNum > 0) {
            singleNum -= 1;
        }
        item_cart_goods_number.setText(singleNum + "");
        getStepPrice();
    }

    private void reduceGoodsNumber(int position) {
        AttributeModel attributeModel = (AttributeModel) mAdapter.data.get(position);

        int goodsNumber = attributeModel.last_goodNum;
        if (goodsNumber <= 0) {
            return;
        }
        attributeModel.last_goodNum = --goodsNumber;
        getSkuModel(attributeModel.spec_ids).QTY = attributeModel.last_goodNum;

        getStepPrice();
        mAdapter.notifyDataSetChanged();
        //调用网络接口获取数据更新价格
    }


    private void refreshCallback(String response) {

        HttpResultManager.resolve(response, BaseEntity.class, new HttpResultManager
                .HttpResultCallBack<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity model) {
                String data = model.getData();
                GoodsWholeAttrModel goodsWholeAttrModel = JSON.parseObject(data,
                        GoodsWholeAttrModel.class);
//                for (GoodsWholeAttrModel.DataBean.SpecListBean specListBean :
// goodsWholeAttrModel.data.spec_list.values()) {
//                    for (AttributeModel attributeModel : last_attributeModels) {
//                        attributeModel.last_stock = Integer.valueOf(specListBean.goods_number);
//                    }
//                }

                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void refreshGetPrice(String response) {
        HttpResultManager.resolve(response, GoodsGetStepPriceModel.class, new HttpResultManager
                .HttpResultCallBack<GoodsGetStepPriceModel>() {
            @Override
            public void onSuccess(GoodsGetStepPriceModel model) {
                mStockTextView.setText(Html.fromHtml("共<font color='#f23030'>" + getGoodsTotal()
                        + "</font>件"));
                mPriceTextView.setText("¥" + model.data.total);
            }
        });
    }

    private void selectAttribute(String attrId, String specId) {
        for (SpecificationModel specificationModel : mSpecificationList) {
            if (specificationModel.attr_id.equals(attrId)) {
;
                AttributeModel attributeModel1 = null;
                for(AttributeModel attributeModel : specificationModel.attr_values) {
                    if(attributeModel.spec_id.equals(specId)) {
                        attributeModel1 = attributeModel;
                    }
                }
                if (!attributeModel1.selected) {
                    for (AttributeModel entry : specificationModel.attr_values) {
                        AttributeModel attributeModel = entry;
                        attributeModel.selected = attributeModel.spec_id.equals(specId);
                    }

                    List<SkuModel> skuModels = getSelectedSkuModel();
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void setUpAdapterData() {
        mAdapter.data.clear();

        for (int i = 0; i < mSpecificationList.size() - 1; i++) {
            SpecificationModel specificationModel = mSpecificationList.get(i);
            mAdapter.data.add(specificationModel);

            for (AttributeModel entry : specificationModel.attr_values) {
                AttributeModel attributeModel = entry;
                mAdapter.data.add(attributeModel);
            }
            mAdapter.data.add(new AttributeLineModel());
        }

        SpecificationModel specificationModel = mSpecificationList.get(mSpecificationList.size()
                - 1);
        mAdapter.data.add(specificationModel);
        for (int i = 0; i < last_attributeModels.size(); i++) {
            mAdapter.data.add(last_attributeModels.get(i));
        }
        mAdapter.notifyDataSetChanged();
    }

    public int getGoodsTotal() {
        int total = 0;
        for (int j = 0, jlen = mSkuList.size(); j < jlen; j++) {
            total += mSkuList.get(j).QTY;
        }

        total += singleNum;

        if (total <= 0) {
            mAddToCartButton.setEnabled(false);
            mAddToCartButton.setBackgroundResource(R.color.colorNine);
            fragment_attribute_buyNowButton.setEnabled(false);
            fragment_attribute_buyNowButton.setBackgroundResource(R.color.colorNine);
        } else {
            mAddToCartButton.setEnabled(true);
            fragment_attribute_buyNowButton.setEnabled(true);

            if (mAddToCartButton.getVisibility() == View.GONE || fragment_attribute_buyNowButton.getVisibility() == View.GONE) {
                mAddToCartButton.setBackgroundResource(R.color.colorPrimary);
                fragment_attribute_buyNowButton.setBackgroundResource(R.color.colorPrimary);
            } else {
                mAddToCartButton.setBackgroundResource(R.color.colorCyan);
                fragment_attribute_buyNowButton.setBackgroundResource(R.color.colorPrimary);
            }
        }

        return total;
    }

    public void getStepPrice() {
        int total = getGoodsTotal();

        CommonRequest request = new CommonRequest(Api.API_GOODS_GET_STEP_PRICE, HttpWhat
                .HTTP_GOODS_GET_STEP_PRICE.getValue());
        request.add("goods_id", mGoodsId);
        request.add("number", total);
        addRequest(request);
    }


    public void addToCart() {
        CommonRequest mAddToCartRequest = new CommonRequest(Api.API_CART_BATCH_ADD, HttpWhat
                .HTTP_ADD_TO_CART.getValue(), RequestMethod.POST);
        mAddToCartRequest.add("goods_id", mGoodsId);
        List<SkuModel> skuModels = getSelectedSkuModel();
        if (skuModels != null && skuModels.size() > 0) {
            for (SkuModel skuModel : skuModels) {
                if (skuModel.QTY > 0) {
                    mAddToCartRequest.add("sku_list[]", skuModel.sku_id + "-" + skuModel.QTY);
                }
            }
        } else {
            mAddToCartRequest.add("sku_list[]", data.sku.sku_id + "-" + singleNum);
        }

        mAddToCartRequest.setAjax(true);
        addRequest(mAddToCartRequest);
    }

    private void addToCartCallback(String response) {
        HttpResultManager.resolve(response, AddToCartModel.class, new HttpResultManager
                .HttpResultCallBack<AddToCartModel>() {
            @Override
            public void onSuccess(AddToCartModel back) {
                Utils.makeToast(getActivity(), back.message);
                App.addCartNumber(back.data.number, this);

                if (mRecyclerView.getVisibility() == View.GONE) {
                    finish();
                } else {
                    //清除所有的商品数量

                }

            }
        }, true);
    }

    public void quickBuy() {
        List<SkuModel> skuModels = getSelectedSkuModel();
        CommonRequest mQuickBuyRequest = new CommonRequest(Api.API_CART_BATCH_QUICK_BUY, HttpWhat
                .HTTP_QUICK_BUY.getValue(), RequestMethod.POST);
        mQuickBuyRequest.add("goods_id", mGoodsId);
        mQuickBuyRequest.add("whole_number", getGoodsTotal());

        if (skuModels != null && skuModels.size() > 0) {
            for (SkuModel skuModel : skuModels) {
                if (skuModel.QTY > 0) {
                    mQuickBuyRequest.add("sku_list[]", skuModel.sku_id + "-" + skuModel.QTY);
                }
            }
        } else {
            mQuickBuyRequest.add("sku_list[]", data.sku.sku_id + "-" + singleNum);
        }

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
        intent.setClass(getActivity(), CheckoutActivity.class);
        startActivity(intent);

        finish();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (RequestCode.valueOf(requestCode)) {
            case REQUEST_CODE_LOGIN_FOR_QUICK_BUY:
                if (resultCode == Activity.RESULT_OK) {
                    quickBuy();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
