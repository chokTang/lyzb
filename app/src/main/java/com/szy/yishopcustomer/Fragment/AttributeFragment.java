package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Other.CommonRequest;
import com.szy.common.Util.ImageLoader;
import com.szy.common.View.CommonRecyclerView;
import com.szy.common.View.FlowLayoutManager;
import com.szy.yishopcustomer.Adapter.AttributeAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Attribute.AttributeModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SkuModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SpecificationModel;
import com.szy.yishopcustomer.ResponseModel.GoodsSku.Model;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.LogUtils;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.Attribute.AttributeLineModel;
import com.szy.yishopcustomer.ViewModel.Attribute.ResultModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by 宗仁 on 16/8/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class AttributeFragment extends YSCBaseFragment implements TextWatcher {
    @BindView(R.id.fragment_attribute_recyclerView)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.fragment_attribute_goodsImageView)
    ImageView mGoodsImageView;
    @BindView(R.id.fragment_attribute_closeButton)
    Button mCloseButton;
    @BindView(R.id.fragment_attribute_buyNowButton)
    Button mBuyNow;
    @BindView(R.id.fragment_attribute_addToCartLayout)
    LinearLayout mNormalButtonLayout;
    @BindView(R.id.fragment_attribute_numberWrapperRelativeLayout)
    LinearLayout mNumberWrapperRelativeLayout;
    @BindView(R.id.fragment_attribute_goodsMoq)
    TextView mGoodsMoqTextView;
    @BindView(R.id.fragment_attribute_addToCartButton)
    Button mAddToCartButton;
    @BindView(R.id.fragment_attribute_priceTextView)
    TextView mPriceTextView;
    @BindView(R.id.fragment_attribute_deductibleTextView)
    TextView mDeductibleTextView;
    @BindView(R.id.fragment_attribute_stockTextView)
    TextView mStockTextView;
    @BindView(R.id.fragment_purchase_num)
    TextView mPurchaseTextView;
    @BindView(R.id.minus_button)
    ImageView mMinusButton;
    @BindView(R.id.plus_button)
    ImageView mPlusButton;
    @BindView(R.id.goods_number)
    EditText mNumberEditText;
    @BindView(R.id.fragment_attribute_selectedTextView)
    TextView mSelectedTextView;
    @BindView(R.id.fragment_attribute_selectedLabelTextView)
    TextView mSelectedLabelTextView;
    @BindView(R.id.fragment_attribute_hide_layout)
    LinearLayout mHideLayout;
    @BindView(R.id.fragment_attribute_disableButton)
    Button mDisableButton;
    private int mType;
    private boolean isShowStock;
    private String mSkuId;
    private String mGoodsId;
    private com.szy.yishopcustomer.ResponseModel.Goods.SkuModel mSkuModel;
    private ResultModel mResultModel;
    private AttributeAdapter mAdapter;
    private List<SkuModel> mSkuList;
    private List<SpecificationModel> mSpecificationList;
    private boolean attributeEnable = true;//属性是否可用

    //切换规格的时候是否请求接口，默认是请求的
    private boolean requestInterface = true;
    List<List<String>> availableIds;


    //在有些商品中，属性这里不使用spec_id判断，而使用attr_vid来判断sku
    public static final String TYPE_ATTR_ID = "user_attr_id";
    private boolean isUseAttrId = false;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        mResultModel.goodsNumber = editable.toString();
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

    public SkuModel getSelectedSkuModel() {
        return getSelectedSkuModel(true);
    }

    public SkuModel getSelectedSkuModel(boolean flag) {
        List<String> selectedSpecificationList = new ArrayList<>();

        for (SpecificationModel specificationModel : mSpecificationList) {
            for (AttributeModel entry : specificationModel.attr_values) {
                AttributeModel attributeModel = entry;

                if (attributeModel.selected) {

                    //同注释(1)一样的理由
                    if (isUseAttrId) {
                        selectedSpecificationList.add(attributeModel.attr_vid);
                    } else {
                        selectedSpecificationList.add(attributeModel.spec_id);
                    }
                }
            }
        }

        SkuModel selectedSkuModel = null;
        if (flag) {
            selectedSkuModel = mSkuList.get(0);
        }

        for (SkuModel skuModel : mSkuList) {

            String[] specificationIds = new String[]{};

            if (isUseAttrId) {
                specificationIds = skuModel.spec_vids.trim().split("-");
            } else {
                specificationIds = skuModel.spec_ids.trim().split("\\|");
            }

            List<String> specificationList = Arrays.asList(specificationIds);
            if (Utils.isSame(specificationList, selectedSpecificationList)) {
                selectedSkuModel = skuModel;
            }
        }

        if (mSpecificationList.size() != 0 && selectedSkuModel == null) {
            attributeEnable = false;
        } else {
            attributeEnable = true;
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
                if (!Utils.isNull(mSkuList) && !Utils.isNull(mSpecificationList) && !Utils.isNull
                        (getSelectedSkuModel()) && (!getSelectedSkuModel().sku_id.equals(mSkuId))) {
                    finish(Macro.RESULT_TYPE_ATTRIBUTE_SELECTED);
                } else {
                    finish();
                }
                break;
            case VIEW_TYPE_ITEM:
                if (isUseAttrId) {
                    selectAttribute(getSpecificationModel(position).attr_id, getAttributeModel
                            (position).attr_vid);
                } else {
                    selectAttribute(getSpecificationModel(position).attr_id, getAttributeModel
                            (position).spec_id);
                }

                break;
            case VIEW_TYPE_MINUS:
                reduceGoodsNumber();
                break;
            case VIEW_TYPE_PLUS:
                increaseGoodsNumber();
                break;
            case VIEW_TYPE_BUY_NOW:
                if (!Utils.isNull(mSkuModel.activity) && mSkuModel.activity.act_type == Macro
                        .OT_FIGHT_GROUP) {
                    mResultModel.resultType = Macro.RESULT_TYPE_BUY_NOW_GROUP;
                    if (mSkuModel != null) {
                        mResultModel.skuId = getSelectedSkuModel().sku_id;
                        mResultModel.specValue = mSkuModel.spec_attr_value;

                    } else {
                        mResultModel.skuId = mSkuId;
                    }
                    Intent intent = new Intent();
                    intent.putExtra(Key.KEY_RESULT.getValue(), mResultModel);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    finish(Macro.RESULT_TYPE_BUY_NOW);
                }
                break;
            case VIEW_TYPE_ADD_TO_CART:
                finish(Macro.RESULT_TYPE_ADD_TO_CART);
                break;
            case VIEW_TYPE_BUY_NOW_SPEC_SINGE:
                finish(Macro.RESULT_TYPE_BUY_NOW);
                break;
            case VIEW_TYPE_BUY_NOW_SPEC:
                mResultModel.resultType = Macro.RESULT_TYPE_BUY_NOW_GROUP;
                if (mSpecificationList.size() != 0) {
                    SkuModel skuModel = getSelectedSkuModel();
                    mResultModel.skuId = skuModel.sku_id;
                    mResultModel.goodStock = skuModel.goods_number;
                    mResultModel.specValue = mSkuModel.spec_attr_value;
                } else {
                    mResultModel.skuId = mSkuId;
                }
                Intent intent = new Intent();
                intent.putExtra(Key.KEY_RESULT.getValue(), mResultModel);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
        }
    }


    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GOODS_SKU:
                refreshCallback(response);
                mAddToCartButton.setEnabled(true);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_attribute;

        Bundle arguments = getArguments();
        if (arguments == null) {
            Toast.makeText(getContext(), "Empty arguments", Toast.LENGTH_SHORT).show();
            return;
        }

        mSkuList = arguments.getParcelableArrayList(Key.KEY_SKU_LIST.getValue());

        mSpecificationList = arguments.getParcelableArrayList(Key.KEY_SPECIFICATION_LIST.getValue
                ());

        requestInterface = arguments.getBoolean(Key.KEY_REQUEST_INTERFACE.getValue(), true);
        isUseAttrId = arguments.getBoolean(TYPE_ATTR_ID, false);

        mSkuId = arguments.getString(Key.KEY_SKU_ID.getValue());
        mGoodsId = arguments.getString(Key.KEY_GOODS_ID.getValue());
        mType = arguments.getInt(Key.KEY_TYPE.getValue(), Macro.BUTTON_TYPE_ADD_TO_CART);
        isShowStock = arguments.getBoolean(Key.KEY_IS_STOCK.getValue(), true);

        mAdapter = new AttributeAdapter();
        mAdapter.onCLickListener = this;
        mResultModel = new ResultModel();

        availableIds = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        //属性列表显示与否
        if (!Utils.isNull(mSkuList) && !Utils.isNull(mSpecificationList)) {
            FlowLayoutManager layoutManager = new FlowLayoutManager();
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(mAdapter);
            setUpSpecificationList();
            setUpAdapterData();
        } else {
            mRecyclerView.setVisibility(View.GONE);
        }

        mNumberEditText.addTextChangedListener(this);
        Utils.setViewTypeForTag(mCloseButton, ViewType.VIEW_TYPE_CLOSE);
        mCloseButton.setOnClickListener(this);
        Utils.setViewTypeForTag(mMinusButton, ViewType.VIEW_TYPE_MINUS);
        mMinusButton.setOnClickListener(this);
        Utils.setViewTypeForTag(mPlusButton, ViewType.VIEW_TYPE_PLUS);
        mPlusButton.setOnClickListener(this);
        Utils.setViewTypeForTag(mHideLayout, ViewType.VIEW_TYPE_CLOSE);
        mHideLayout.setOnClickListener(this);

        mBuyNow.setOnClickListener(this);
        mAddToCartButton.setOnClickListener(this);

        mAddToCartButton.setEnabled(false);

        refreshButton();

        refresh();


        return view;
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_GOODS_SKU, HttpWhat.HTTP_GOODS_SKU.getValue());
        if (!Utils.isNull(getSelectedSkuModel())) {
            request.add("sku_id", getSelectedSkuModel().sku_id);
            addRequest(request);
        } else {
            request.add("sku_id", mSkuId);
            addRequest(request);
        }
    }

    private void finish(String type) {
        mResultModel.resultType = type;

        if (!Utils.isNull(getSelectedSkuModel())) {
            mResultModel.skuId = getSelectedSkuModel().sku_id;
            if (mSkuModel != null) {
                mResultModel.specValue = mSkuModel.spec_attr_value;
            }

        } else {
            mResultModel.skuId = mSkuId;
        }

        Intent intent = new Intent();
        intent.putExtra(Key.KEY_RESULT.getValue(), mResultModel);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void increaseGoodsNumber() {
        int goodsNumber = 0;
        if (!"".equals(mNumberEditText.getText().toString())) {
            goodsNumber = Integer.parseInt(mNumberEditText.getText().toString());
        } else {
            goodsNumber = 0;
        }

        int maxNumber = Integer.MAX_VALUE;
        if (mSkuModel != null) {
            maxNumber = Integer.parseInt(mSkuModel.goods_number);
        }

        if (goodsNumber >= maxNumber) {
            return;
        }
        goodsNumber++;
        mResultModel.goodsNumber = String.valueOf(goodsNumber);
        mNumberEditText.setText(String.valueOf(goodsNumber));
    }

    private void reduceGoodsNumber() {
        int goodsNumber = Integer.parseInt(mNumberEditText.getText().toString());
        if (goodsNumber <= 1) {
            return;
        }
        goodsNumber--;
        mResultModel.goodsNumber = String.valueOf(goodsNumber);
        mNumberEditText.setText(String.valueOf(goodsNumber));
    }


    private void refreshCallback(String response) {

        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model model) {
                mSkuModel = model.data;

                setDate(model.data);
            }
        });
    }


    void setDate(com.szy.yishopcustomer.ResponseModel.Goods.SkuModel model) {


        LogUtils.Companion.e("库存为"+ model.original_number + model.unit_name);
        /**
         * 拼团活动特殊
         * 拼团活动可以拼团购买和单独购买
         * 拼团购买时，走活动价，活动库存
         * 拼团单独购买时，走原价，原库存
         * 所以这里对不同库存是否充足做判断
         * */
        if (mType == Macro.BUTTON_TYPE_GROUP_ON_SINGLE) {
            mPriceTextView.setText(Utils.formatMoney(mPriceTextView.getContext(), model.original_price_format));
            /**
             * 分为以下几种情况显示按钮
             * 1、没有购买权限，无库存
             * 2、没有购买权限，有库存
             * 3、有购买权限，无库存
             * 4、有购买权限，有库存
             * 如果没有购买权限，同时无库存，优先显示无购买权限按钮
             * */
            //如果没有权限购买，再判断是否有库存
            if (model.buy_enable.code.equals("0")) {
                if (model.original_number.equals("0")) {
                    unableView();
                    disableView();
                } else {
                    enalbeView();
                    if (isShowStock) {
                        mStockTextView.setText("库存：" + model.original_number + model.unit_name);
                    } else {
                        mStockTextView.setText("有货");
                    }
                    disableView();
                }

                if (!TextUtils.isEmpty(model.max_integral_num) && Integer.parseInt(model.max_integral_num) > 0) {
                    mDeductibleTextView.setVisibility(View.VISIBLE);
                    mDeductibleTextView.setText("元宝最高抵扣:" + Utils.formatMoney(mDeductibleTextView.getContext(), model.max_integral_num));
                } else {
                    mDeductibleTextView.setVisibility(View.INVISIBLE);
                }

            } else {
                if (model.original_number.equals("0")) {
                    unableView();
                } else {
                    enalbeView();
                    if (isShowStock) {
                        mStockTextView.setText("库存：" + model.original_number + model.unit_name);
                    } else {
                        mStockTextView.setText("有货");
                    }
                    refreshButton();
                }
                if (!TextUtils.isEmpty(model.max_integral_num) && Integer.parseInt(model.max_integral_num) > 0) {
                    mDeductibleTextView.setVisibility(View.VISIBLE);
                    mDeductibleTextView.setText("元宝最高抵扣:" + Utils.formatMoney(mDeductibleTextView.getContext(), model.max_integral_num));
                } else {
                    mDeductibleTextView.setVisibility(View.INVISIBLE);
                }
            }
        } else {

            if (mType == Macro.BUTTON_TYPE_GROUP_ON_NOW) {
                mPriceTextView.setText(Utils.formatMoney(mPriceTextView.getContext(), model.activity.act_price_format));
            } else {
                mPriceTextView.setText(Utils.formatMoney(mPriceTextView.getContext(), model.goods_price_format));
            }


            //如果没有权限购买
            if (model.buy_enable.code.equals("0")) {
                if (model.goods_number.equals("0")) {
                    unableView();
                    disableView();
                } else {
                    enalbeView();
                    showStock();
                    disableView();
                }
            } else {
                if (model.original_number.equals("0")) {
                    unableView();
                } else {
                    enalbeView();
                    showStock();
                    refreshButton();
                }
            }

            //限购：除去拼团单独购买，其他情况显示限购
            if (!Utils.isNull(model.purchase_num) && !model.purchase_num.equals
                    ("0")) {
                mPurchaseTextView.setVisibility(View.VISIBLE);
                mPurchaseTextView.setText(String.format(getResources().getString(R.string
                        .purchase_number), model.purchase_num));
            } else {
                mPurchaseTextView.setVisibility(View.GONE);
            }
        }

        if (!"".equals(model.sku_image)) {
            ImageLoader.displayImage(Utils.urlOfImage(model.sku_image), mGoodsImageView);
        } else if (!"".equals(model.goods_image)) {
            ImageLoader.displayImage(Utils.urlOfImage(model.goods_image), mGoodsImageView);
        }

        //只有普通商品显示起订量
        if (mType == Macro.BUTTON_TYPE_ADD_TO_CART || mType == Macro.BUTTON_TYPE_BUY_NOW
                || mType == Macro.BUTTON_TYPE_ATTRIBUTE || mType == Macro.BUTTON_TYPE_ADD_TO_CART_GOODSLIST) {
            if (model.goods_moq > 1) {
                mGoodsMoqTextView.setVisibility(View.VISIBLE);
                mGoodsMoqTextView.setText("(起订量≥" + model.goods_moq + ")");
            } else {
                mGoodsMoqTextView.setVisibility(View.INVISIBLE);
            }
        }

        //属性
        if (!Utils.isNull(model.spec_attr_value)) {
            mSelectedTextView.setText(model.spec_attr_value);
            mSelectedTextView.setVisibility(View.VISIBLE);
            mSelectedLabelTextView.setVisibility(View.VISIBLE);
        } else {
            mSelectedTextView.setVisibility(View.GONE);
            mSelectedLabelTextView.setVisibility(View.GONE);
        }
    }

    /*****
     * 库存显示
     * 原始库存显示
     * 拼团属性的商品 单独取
     * 活动属性的商品 mSkuModel.activity 非空  取值为:mSkuModel.activity.goods_number
     *
     * wyx
     */
    private void showStock() {

        if (mType == Macro.BUTTON_TYPE_GROUP_ON_ATTRIBUTE || mType == Macro.BUTTON_TYPE_GROUP_ON_NOW ||
                (!Utils.isNull(mSkuModel.activity) && mSkuModel.activity.act_type == Macro.GT_GROUP_BUY_GOODS)) {

            //拼团商品属性\立即购买\团购商品
            if (isShowStock) {
                mStockTextView.setText("库存：" + mSkuModel.goods_number + mSkuModel.unit_name);
            } else {
                mStockTextView.setText("有货");
            }
            if (!TextUtils.isEmpty(mSkuModel.max_integral_num) && Integer.parseInt(mSkuModel.max_integral_num) > 0) {
                mDeductibleTextView.setVisibility(View.VISIBLE);
                mDeductibleTextView.setText("元宝最高抵扣:" + Utils.formatMoney(mDeductibleTextView.getContext(), mSkuModel.max_integral_num));
            } else {
                mDeductibleTextView.setVisibility(View.INVISIBLE);
            }
        } else if (!Utils.isNull(mSkuModel.activity)) {

            if (isShowStock) {
                if (TextUtils.isEmpty(mSkuModel.activity.goods_number)){
                    mStockTextView.setText("库存：" + mSkuModel.goods_number + mSkuModel.unit_name);
                }else {
                    mStockTextView.setText("库存：" + mSkuModel.activity.goods_number + mSkuModel.unit_name);
                }
            } else {
                mStockTextView.setText("有货");
            }

            if (!TextUtils.isEmpty(mSkuModel.max_integral_num) && Integer.parseInt(mSkuModel.max_integral_num) > 0) {
                mDeductibleTextView.setVisibility(View.VISIBLE);
                mDeductibleTextView.setText("元宝最高抵扣:" + Utils.formatMoney(mDeductibleTextView.getContext(), mSkuModel.max_integral_num));
            } else {
                mDeductibleTextView.setVisibility(View.INVISIBLE);
            }
        } else {

            if (isShowStock) {
                mStockTextView.setText("库存：" + mSkuModel.original_number + mSkuModel.unit_name);
            } else {
                mStockTextView.setText("有货");
            }
            if (!TextUtils.isEmpty(mSkuModel.max_integral_num) && Integer.parseInt(mSkuModel.max_integral_num) > 0) {
                mDeductibleTextView.setVisibility(View.VISIBLE);
                mDeductibleTextView.setText("元宝最高抵扣:" + Utils.formatMoney(mDeductibleTextView.getContext(), mSkuModel.max_integral_num));
            } else {
                mDeductibleTextView.setVisibility(View.INVISIBLE);
            }
        }
    }

    //按钮不同情况
    private void refreshButton() {
        if (mType == Macro.BUTTON_TYPE_ATTRIBUTE) {
            mNormalButtonLayout.setVisibility(View.VISIBLE);
            mDisableButton.setVisibility(View.GONE);

            mBuyNow.setText("立即购买");
            mAddToCartButton.setText("加入购物车");

            Utils.setViewTypeForTag(mBuyNow, ViewType.VIEW_TYPE_BUY_NOW);
            Utils.setViewTypeForTag(mAddToCartButton, ViewType.VIEW_TYPE_ADD_TO_CART);

        } else if (mType == Macro.BUTTON_TYPE_ADD_TO_CART) {
            mNormalButtonLayout.setVisibility(View.VISIBLE);
            mDisableButton.setVisibility(View.GONE);

            mBuyNow.setVisibility(View.GONE);
            mAddToCartButton.setText("确定");
            Utils.setViewTypeForTag(mAddToCartButton, ViewType.VIEW_TYPE_ADD_TO_CART);

        } else if (mType == Macro.BUTTON_TYPE_ADD_TO_CART_GOODSLIST) {
            mNormalButtonLayout.setVisibility(View.VISIBLE);
            mDisableButton.setVisibility(View.GONE);

            mBuyNow.setVisibility(View.GONE);
            mAddToCartButton.setText("加入购物车");
            Utils.setViewTypeForTag(mAddToCartButton, ViewType.VIEW_TYPE_ADD_TO_CART);

        } else if (mType == Macro.BUTTON_TYPE_BUY_NOW) {
            mNormalButtonLayout.setVisibility(View.VISIBLE);
            mDisableButton.setVisibility(View.GONE);

            mBuyNow.setVisibility(View.GONE);
            mAddToCartButton.setText("确定");
            Utils.setViewTypeForTag(mAddToCartButton, ViewType.VIEW_TYPE_BUY_NOW);

        } else if (mType == Macro.BUTTON_TYPE_GROUP_ON_ATTRIBUTE) {
            mNormalButtonLayout.setVisibility(View.VISIBLE);
            mDisableButton.setVisibility(View.GONE);

            mBuyNow.setText(Utils.formatMoney(getContext(), mSkuModel.original_price) +
                    "\n单独购买");
            mAddToCartButton.setText(mSkuModel.activity.act_price_format + "\n" + mSkuModel.activity.fight_num +
                    "人团");

            mBuyNow.setBackgroundColor(Color.parseColor("#fd948e"));
            mAddToCartButton.setBackgroundColor(ContextCompat.getColor
                    (getActivity(), R.color.colorPrimary));

            Utils.setViewTypeForTag(mBuyNow, ViewType.VIEW_TYPE_BUY_NOW_SPEC_SINGE);
            Utils.setViewTypeForTag(mAddToCartButton, ViewType.VIEW_TYPE_BUY_NOW_SPEC);

        } else if (mType == Macro.BUTTON_TYPE_GROUP_ON_NOW) {
            mNormalButtonLayout.setVisibility(View.VISIBLE);
            mDisableButton.setVisibility(View.GONE);

            mBuyNow.setVisibility(View.GONE);
            mAddToCartButton.setText("确定");
            Utils.setViewTypeForTag(mAddToCartButton, ViewType.VIEW_TYPE_BUY_NOW_SPEC);
        } else if (mType == Macro.BUTTON_TYPE_GROUP_ON_SINGLE) {
            mNormalButtonLayout.setVisibility(View.VISIBLE);
            mDisableButton.setVisibility(View.GONE);

            mBuyNow.setVisibility(View.GONE);
            mAddToCartButton.setText("确定");
            Utils.setViewTypeForTag(mAddToCartButton, ViewType.VIEW_TYPE_BUY_NOW_SPEC_SINGE);
        } else if (mType == Macro.BUTTON_TYPE_PROMOTION) {

            mNumberWrapperRelativeLayout.setVisibility(View.GONE);
            mPriceTextView.setVisibility(View.INVISIBLE);
            mPriceTextView.getLayoutParams().height = 0;

            mNormalButtonLayout.setVisibility(View.VISIBLE);
            mDisableButton.setVisibility(View.GONE);

            mBuyNow.setVisibility(View.GONE);
            mAddToCartButton.setText("确定");
            Utils.setViewTypeForTag(mAddToCartButton, ViewType.VIEW_TYPE_BUY_NOW_SPEC);
        }
    }


    private void unableView() {
        mStockTextView.setText("库存不足");

        mDisableButton.setVisibility(View.VISIBLE);
        mDisableButton.setEnabled(false);
        mDisableButton.setText("库存不足");
        mNormalButtonLayout.setVisibility(View.INVISIBLE);
    }

    private void enalbeView() {
        mDisableButton.setVisibility(View.GONE);
        mNormalButtonLayout.setVisibility(View.VISIBLE);
    }

    private void disableView() {
        mDisableButton.setVisibility(View.VISIBLE);
        mDisableButton.setEnabled(false);
        mDisableButton.setText("确定");
        mNormalButtonLayout.setVisibility(View.INVISIBLE);
    }

    Map<String, String> selectSpec = new HashMap<>();

    private void selectAttribute(String attrId, String specId) {


        //存储选中的规格
        selectSpec.put(attrId, specId);


        if (isUseAttrId) {
            for (SpecificationModel specificationModel : mSpecificationList) {
                for (AttributeModel entry : specificationModel.attr_values) {
                    AttributeModel attributeModel = entry;

                    if (specificationModel.attr_id.equals(attrId)) {
                        attributeModel.selected = attributeModel.attr_vid.equals(specId);
                    }

                    attributeModel.actived = true;
                    if (!attributeModel.selected) {
                        if (!qqq(specificationModel.attr_id, attributeModel.attr_vid)) {
                            attributeModel.actived = false;
                        }
                    }
                }
            }
        } else {
            for (SpecificationModel specificationModel : mSpecificationList) {
                for (AttributeModel entry : specificationModel.attr_values) {
                    AttributeModel attributeModel = entry;

                    if (specificationModel.attr_id.equals(attrId)) {
                        attributeModel.selected = attributeModel.spec_id.equals(specId);
                    }

                    attributeModel.actived = true;
                    if (!attributeModel.selected) {
                        if (!qqq(specificationModel.attr_id, attributeModel.spec_id)) {
                            attributeModel.actived = false;
                        }
                    }
                }
            }
        }


        setUpAdapterData();
        if (getSelectedSkuModel(false) == null) {
            unableView();
        } else {
            refresh();
        }
    }

    private boolean qqq(String attr_id, String spec_id) {

        List<String> spec_ids = new ArrayList<>();

        for (Map.Entry<String, String> entry : selectSpec.entrySet()) {
            if (!entry.getKey().equals(attr_id)) {
                spec_ids.add(entry.getValue());
            }
        }
        spec_ids.add(spec_id);


        for (List<String> aids : availableIds) {

            boolean flagchild = Utils.isSame(aids, spec_ids);
            if (flagchild) {
                return flagchild;
            }
        }
        return false;
    }

    private void setUpAdapterData() {
        mAdapter.data.clear();

        for (SpecificationModel specificationModel : mSpecificationList) {
            mAdapter.data.add(specificationModel);
            for (AttributeModel entry : specificationModel.attr_values) {
                mAdapter.data.add(entry);
            }
            mAdapter.data.add(new AttributeLineModel());
        }
        mAdapter.notifyDataSetChanged();
    }

    private void setUpSpecificationList() {

        SkuModel selectedSkuModel = mSkuList.get(0);

        //---------
        for (SkuModel skuModel : mSkuList) {
            int good_num = Integer.parseInt(skuModel.goods_number);
            if (good_num > 0 && (!TextUtils.isEmpty(skuModel.spec_vids) || !TextUtils.isEmpty(skuModel.spec_ids))) {
                selectedSkuModel = skuModel;
                break;
            }
        }
        //------------

        if (mSkuModel != null) {
            mSkuId = mSkuModel.sku_id;
        } else if (mSkuId != null) {
            for (SkuModel skuModel : mSkuList) {
                if (skuModel.sku_id.equals(mSkuId)) {
                    selectedSkuModel = skuModel;
                    break;
                }
            }
        }

        String[] specificationIds;

        //同注释(1)一样的理由
        if (isUseAttrId) {
            specificationIds = selectedSkuModel.spec_vids.split("-");
        } else {
            specificationIds = selectedSkuModel.spec_ids.split("\\|");
        }

        List<String> specificationList = Arrays.asList(specificationIds);

        //检查可用的规格组合
        for (SkuModel skuModel : mSkuList) {
            String[] ids;

            if (!TextUtils.isEmpty(skuModel.spec_vids)) {
                ids = skuModel.spec_vids.split("-");
            } else {
                ids = skuModel.spec_ids.split("\\|");
            }

            List<String> idsList = Arrays.asList(ids);
            availableIds.add(idsList);
        }


        for (SpecificationModel specificationModel : mSpecificationList) {

            for (AttributeModel entry : specificationModel.attr_values) {
                AttributeModel attributeModel = entry;

                String spec_id = "";
                //同注释(1)一样的理由
                if (isUseAttrId) {
                    attributeModel.selected = specificationList.contains(attributeModel.attr_vid);
                    spec_id = attributeModel.attr_vid;

                } else {
                    attributeModel.selected = specificationList.contains(attributeModel.spec_id);
                    spec_id = attributeModel.spec_id;
                }

                if (attributeModel.selected) {
                    selectSpec.put(specificationModel.attr_id, spec_id);
                }
            }
        }

        //设置虚线状态
        for (SpecificationModel specificationModel : mSpecificationList) {

            for (AttributeModel entry : specificationModel.attr_values) {
                AttributeModel attributeModel = entry;

                if (!attributeModel.selected) {
                    if (isUseAttrId) {
                        if (!qqq(specificationModel.attr_id, attributeModel.attr_vid)) {
                            attributeModel.actived = false;
                        }
                    } else {
                        if (!qqq(specificationModel.attr_id, attributeModel.spec_id)) {
                            attributeModel.actived = false;
                        }
                    }
                }
            }
        }
//        refresh();
    }


    //注释(1)
    //这里的判断是因为促销商品使用的条件和正常商品的属性不一样
    //正常的话arrt_vid 和 spec_id是不会同时出现的，如果接口没有改动的话
}
