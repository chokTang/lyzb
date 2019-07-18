package com.szy.yishopcustomer.View;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.*;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.szy.common.View.CommonRecyclerView;
import com.szy.common.View.FlowLayoutManager;
import com.szy.yishopcustomer.Adapter.AttributeFreeAdapter;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Other.MapKeyComparator;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Attribute.AttributeModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SkuModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SpecificationModelTwo;
import com.szy.yishopcustomer.ResponseModel.Goods.GoodsModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.Attribute.AttributeLineModel;
import com.szy.yishopcustomer.ViewModel.FreeSkuListModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 2017/7/19.
 */

public class AttributeDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.fragment_attribute_recyclerView)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.textView_goods_name)
    TextView textView_goods_name;
    @BindView(R.id.fragment_attribute_priceTextView)
    TextView fragment_attribute_priceTextView;
    @BindView(R.id.fragment_attribute_stockTextView)
    TextView fragment_attribute_stockTextView;
    @BindView(R.id.fragment_purchase_num)
    TextView fragment_purchase_num;
    @BindView(R.id.fragment_attribute_numberTip)
    TextView fragment_attribute_numberTip;
    @BindView(R.id.fragment_attribute_numberEditText)
    EditText fragment_attribute_numberEditText;

    @BindView(R.id.fragment_attribute_closeButton)
    View fragment_attribute_closeButton;
    @BindView(R.id.fragment_attribute_addToCartButton)
    View fragment_attribute_addToCartButton;
    @BindView(R.id.fragment_attribute_buyNowButton)
    View fragment_attribute_buyNowButton;
    @BindView(R.id.fragment_attribute_minusButton)
    View fragment_attribute_minusButton;
    @BindView(R.id.fragment_attribute_plusButton)
    View fragment_attribute_plusButton;

    @BindView(R.id.fragment_attribute_cancel)
    View fragment_attribute_cancel;

    private AttributeFreeAdapter mAdapter;
    private Context mContext;
    private OnGoodsBuyClick callback;

    private FreeSkuListModel responseGoodsModel;
    private List<SkuModel> mSkuList;
    private List<SpecificationModelTwo> mSpecificationList;
    private SkuModel mSkuModel;

    private String sku_id = "";

    public AttributeDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        mContext = context;
        mAdapter = new AttributeFreeAdapter();
        mAdapter.onCLickListener = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
//        window.setWindowAnimations(R.style.bottom_menu_animation); // 添加动画效果
        setContentView(R.layout.dialog_attribute);
        ButterKnife.bind(this);

        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth() * 5 / 6; // 设置dialog宽度为屏幕的5/6
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(true);// 点击Dialog外部消失

        fragment_attribute_closeButton.setOnClickListener(this);
        fragment_attribute_addToCartButton.setOnClickListener(this);
        fragment_attribute_buyNowButton.setOnClickListener(this);
        fragment_attribute_plusButton.setOnClickListener(this);
        fragment_attribute_minusButton.setOnClickListener(this);
        fragment_attribute_cancel.setOnClickListener(this);

        if (responseGoodsModel != null) {

            mRecyclerView.setVisibility(View.GONE);
            if (responseGoodsModel.data.is_sku) {
                setUpData();
            } else {
                if (mSkuList != null && mSkuList.size() > 0) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    FlowLayoutManager layoutManager = new FlowLayoutManager();
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                    setUpAdapterData();
                    getSelectedSkuModel();
                }
            }
        }
    }

    public void setData(FreeSkuListModel response, OnGoodsBuyClick call) {
        try {
            callback = call;
            responseGoodsModel = response;
            mSpecificationList = response.data.spec_list;
            mSkuList = new ArrayList<>();

            if (!response.data.is_sku) {
                if (responseGoodsModel.data.sku_list != null) {
                    for (Map.Entry<String, SkuModel> entry
                            : responseGoodsModel.data.sku_list.entrySet()) {
                        mSkuList.add(responseGoodsModel.data.sku_list.get(entry.getKey()));
                    }
                }

                for (SpecificationModelTwo specificationModel : mSpecificationList) {
                    for (int i = 0; i < specificationModel.attr_values.size(); i++) {
                        AttributeModel attributeModel = specificationModel.attr_values.get(i);
                        attributeModel.selected = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    private boolean isMatch(String spec_ids, String spec) {
        if (spec_ids.equals(spec)) {
            return true;
        }
        return false;
    }

    public void setUpData() {
        if (responseGoodsModel != null) {
            GoodsModel goodsModel = responseGoodsModel.data.goods;
            textView_goods_name.setText(goodsModel.sku_name);

            fragment_attribute_priceTextView.setText(" " + goodsModel.goods_price);

            if ("0".equals(goodsModel.goods_number)) {
                fragment_attribute_stockTextView.setText("库存不足");
            } else {
                if ("1".equals(responseGoodsModel.data.show_goods_stock)) {
                    fragment_attribute_stockTextView.setText("库存：" + goodsModel.goods_number);
                } else {
                    fragment_attribute_stockTextView.setText("有货");
                }
            }

            fragment_purchase_num.setVisibility(View.GONE);
            fragment_attribute_numberTip.setText("数量");

            try {
                int num = Integer.parseInt(fragment_attribute_numberEditText.getText().toString());
                if (num == 0) {
                    fragment_attribute_numberEditText.setText("1");
                }
                int max_num = Integer.parseInt(goodsModel.goods_number);
                if (max_num > 0) {
                    fragment_attribute_addToCartButton.setEnabled(true);
                    if (max_num < num) {
                        fragment_attribute_numberEditText.setText(max_num + "");
                    }
                } else {
                    fragment_attribute_numberEditText.setText("0");
                    fragment_attribute_addToCartButton.setEnabled(false);
                }
            } catch (Exception e) {
            }

            sku_id = goodsModel.sku_id;
        }
    }

    public void setUpAdapterData() {
        mAdapter.data.clear();

        for (SpecificationModelTwo specificationModel : mSpecificationList) {
            mAdapter.data.add(specificationModel);
            for (int i = 0; i < specificationModel.attr_values.size(); i++) {
                mAdapter.data.add(specificationModel.attr_values.get(i));
            }
            mAdapter.data.add(new AttributeLineModel());
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
//        String number = fragment_attribute_numberEditText.getText().toString();

        String number = fragment_attribute_numberEditText.getText().toString();
        switch (view.getId()) {
            case R.id.fragment_attribute_cancel:
            case R.id.fragment_attribute_closeButton:
                this.dismiss();
                break;
            case R.id.fragment_attribute_addToCartButton:
                if (!TextUtils.isEmpty(sku_id)) {
                    callback.addToCart(sku_id, number);
                }
                break;
            case R.id.fragment_attribute_buyNowButton:
                if (mSpecificationList != null && mSpecificationList.size() > 0) {
                    callback.quickBuy(sku_id, number);
                }
                break;
            case R.id.fragment_attribute_plusButton:
                increaseGoodsNumber();
                break;
            case R.id.fragment_attribute_minusButton:
                reduceGoodsNumber();
                break;
            default:
                ViewType viewType = Utils.getViewTypeOfTag(view);
                int position = Utils.getPositionOfTag(view);
                switch (viewType) {
                    case VIEW_TYPE_ITEM:
                        selectAttribute(getSpecificationModel(position).attr_id, getAttributeModel
                                (position).spec_id);
                        break;
                }
                break;
        }
    }

    public AttributeModel getAttributeModel(int position) {
        int cursor = 0;
        for (SpecificationModelTwo specificationModel : mSpecificationList) {
            for (int i = 0; i < specificationModel.attr_values.size(); i++) {
                cursor++;

                if (cursor == position) {
                    return specificationModel.attr_values.get(i);
                }
            }

            cursor++;
            cursor++;
        }
        return null;
    }

    public SpecificationModelTwo getSpecificationModel(int position) {
        int cursor = 0;
        for (int i = 0; i < mSpecificationList.size(); i++) {
            SpecificationModelTwo specificationModel = mSpecificationList.get(i);
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


    public SkuModel getSelectedSkuModel() {
        SkuModel selectedSkuModel = null;
        List<String> selectedSpecificationList = new ArrayList<>();
        if (mSpecificationList != null && mSpecificationList.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            for (SpecificationModelTwo specificationModel : mSpecificationList) {
                for (int i = 0; i < specificationModel.attr_values.size(); i++) {
                    AttributeModel attributeModel = specificationModel.attr_values.get(i);

                    if (attributeModel.selected) {
                        selectedSpecificationList.add(attributeModel.spec_id);
                    }
                }
            }

            for (SkuModel skuModel : mSkuList) {
                String[] specificationIds = skuModel.spec_ids.split("\\|");
                List<String> specificationList = Arrays.asList(specificationIds);

                if (Utils.isSame(specificationList, selectedSpecificationList)) {
                    selectedSkuModel = skuModel;
                }
            }
        } else {
            mRecyclerView.setVisibility(View.GONE);
            if (mSkuList != null && mSkuList.size() > 0) {
                selectedSkuModel = mSkuList.get(0);
            }
        }

        if (selectedSkuModel == null) {
            selectedSkuModel = new SkuModel();
            selectedSkuModel.goods_number = "0";
            selectedSkuModel.sku_id = "";
            selectedSkuModel.spec_ids = "";
            selectedSkuModel.sku_name = "";
            selectedSkuModel.goods_price = "0";
        }
        if (!TextUtils.isEmpty(selectedSkuModel.sku_name)) {
            textView_goods_name.setText(selectedSkuModel.sku_name);
        } else {
            if (TextUtils.isEmpty(textView_goods_name.getText().toString())) {
                try {
                    textView_goods_name.setText(responseGoodsModel.data.goods.sku_name);
                } catch (Exception e) {
                }
            }
        }

        fragment_attribute_priceTextView.setText(" " + selectedSkuModel.goods_price);
        if ("0".equals(selectedSkuModel.goods_number)) {
            fragment_attribute_stockTextView.setText("库存不足");
        } else {
            if ("1".equals(responseGoodsModel.data.show_goods_stock)) {
                fragment_attribute_stockTextView.setText("库存：" + selectedSkuModel.goods_number);
            } else {
                fragment_attribute_stockTextView.setText("有货");
            }
        }

        fragment_purchase_num.setVisibility(View.GONE);
        fragment_attribute_numberTip.setText("数量");

        try {
            int num = Integer.parseInt(fragment_attribute_numberEditText.getText().toString());
            if (num == 0) {
                fragment_attribute_numberEditText.setText("1");
            }
            int max_num = Integer.parseInt(selectedSkuModel.goods_number);
            if (max_num > 0) {
                fragment_attribute_addToCartButton.setEnabled(true);
                if (max_num < num) {
                    fragment_attribute_numberEditText.setText(max_num + "");
                }
            } else {
                fragment_attribute_numberEditText.setText("0");
                fragment_attribute_addToCartButton.setEnabled(false);
            }
        } catch (Exception e) {
        }

        mSkuModel = selectedSkuModel;

        sku_id = mSkuModel.sku_id;
        return selectedSkuModel;
    }

    public void selectAttribute(String attrId, String specId) {
        for (SpecificationModelTwo specificationModel : mSpecificationList) {
            if (specificationModel.attr_id.equals(attrId)) {
                for (int i = 0; i < specificationModel.attr_values.size(); i++) {
                    AttributeModel attributeModel = specificationModel.attr_values.get(i);
                    attributeModel.selected = attributeModel.spec_id.equals(specId);
                }
            }
        }
        setUpAdapterData();
        getSelectedSkuModel();
    }

    private void reduceGoodsNumber() {
        int goodsNumber = Integer.parseInt(fragment_attribute_numberEditText.getText().toString());
        if (goodsNumber <= 1) {
            return;
        }
        goodsNumber--;
        fragment_attribute_numberEditText.setText(String.valueOf(goodsNumber));
    }

    private void increaseGoodsNumber() {
        int goodsNumber = Integer.parseInt(fragment_attribute_numberEditText.getText().toString());
        int maxNumber = Integer.MAX_VALUE;
        if (mSkuModel != null) {
            maxNumber = Integer.parseInt(mSkuModel.goods_number);
        }
        if (goodsNumber >= maxNumber) {
            return;
        }
        goodsNumber++;
        fragment_attribute_numberEditText.setText(String.valueOf(goodsNumber));
    }


    public interface OnGoodsBuyClick {
        void addToCart(String skuId, String goodsNumber);

        void quickBuy(String skuId, String goodsNumber);
    }

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public Map<String, AttributeModel> sortMapByKey(Map<String, AttributeModel> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, AttributeModel> sortMap = new TreeMap<String, AttributeModel>(
                new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

}
