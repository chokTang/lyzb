package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AttributeIntegralModel;
import com.szy.yishopcustomer.Util.Utils;

import butterknife.BindView;

/**
 * Created by Smart on 17/12/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class AttributeIntegralFragment extends YSCBaseFragment {

    @BindView(R.id.fragment_attribute_goodsImageView)
    ImageView fragment_attribute_goodsImageView;
    @BindView(R.id.fragment_attribute_priceTextView)
    TextView fragment_attribute_priceTextView;
    @BindView(R.id.fragment_attribute_stockLabelTextView)
    TextView fragment_attribute_stockLabelTextView;
    @BindView(R.id.fragment_attribute_hide_layout)
    LinearLayout mHideLayout;

    @BindView(R.id.fragment_attribute_closeButton)
    Button mCloseButton;
    @BindView(R.id.fragment_attribute_minusButton)
    Button mMinusButton;
    @BindView(R.id.fragment_attribute_plusButton)
    Button mPlusButton;
    @BindView(R.id.fragment_attribute_numberEditText)
    EditText mNumberEditText;

    @BindView(R.id.fragment_attribute_disableButton)
    Button mDisableButton;

    private AttributeIntegralModel attributeIntegralModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_integral_attribute;

        Bundle bundle = getActivity().getIntent().getExtras();
        attributeIntegralModel = (AttributeIntegralModel) bundle.getSerializable(Key.KEY_SKU_MODEL.getValue());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        updateButtonState();

        ImageLoader.displayImage(Utils.urlOfImage(attributeIntegralModel.goods_image), fragment_attribute_goodsImageView);
        fragment_attribute_priceTextView.setText(attributeIntegralModel.goods_integral + "积分");
        fragment_attribute_stockLabelTextView.setText("库存：" + attributeIntegralModel.goods_number + "件");

        Utils.setViewTypeForTag(mCloseButton, ViewType.VIEW_TYPE_CLOSE);
        mCloseButton.setOnClickListener(this);
        Utils.setViewTypeForTag(mMinusButton, ViewType.VIEW_TYPE_MINUS);
        mMinusButton.setOnClickListener(this);
        Utils.setViewTypeForTag(mPlusButton, ViewType.VIEW_TYPE_PLUS);
        mPlusButton.setOnClickListener(this);
        Utils.setViewTypeForTag(mHideLayout, ViewType.VIEW_TYPE_CLOSE);
        mHideLayout.setOnClickListener(this);


        if (attributeIntegralModel.can_exchange) {
            Utils.setViewTypeForTag(mDisableButton, ViewType.VIEW_TYPE_BUY_NOW);
            mDisableButton.setOnClickListener(this);
        } else {
            mDisableButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toast(attributeIntegralModel.can_exchange_msg);
                }
            });
        }


        return view;
    }


    private void finish(String type) {

        Intent intent = new Intent();
        intent.putExtra(Key.KEY_RESULT.getValue(), "");
        setResult(Activity.RESULT_OK, intent);
        finish();
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
                break;
            case VIEW_TYPE_MINUS:
                reduceGoodsNumber();
                break;
            case VIEW_TYPE_PLUS:
                increaseGoodsNumber();
                break;
            case VIEW_TYPE_BUY_NOW:
                //返回商品数量
                String goodsNumber = mNumberEditText.getText().toString();
                Intent intent = new Intent();
                intent.putExtra(Key.KEY_RESULT.getValue(), goodsNumber);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
        }
    }

    void updateButtonState() {
        if (attributeIntegralModel.can_exchange) {
            mDisableButton.setBackgroundColor(ContextCompat.getColor
                    (getActivity(), R.color.colorPrimary));
            mDisableButton.setTextColor(Color.WHITE);
        } else {
            mDisableButton.setBackgroundColor(ContextCompat.getColor
                    (getActivity(), R.color.colorEight));
            mDisableButton.setTextColor(getResources().getColor(R.color.colorThree));
        }
    }

    private void reduceGoodsNumber() {
        int goodsNumber = Integer.parseInt(mNumberEditText.getText().toString());
        if (goodsNumber <= 1) {
            return;
        }
        goodsNumber--;
        mNumberEditText.setText(String.valueOf(goodsNumber));

    }

    private void increaseGoodsNumber() {
        int goodsNumber = 0;
        if (!"".equals(mNumberEditText.getText().toString())) {
            goodsNumber = Integer.parseInt(mNumberEditText.getText().toString());
        } else {
            goodsNumber = 0;
        }

        int maxNumber = Integer.MAX_VALUE;
        if (attributeIntegralModel != null) {
            maxNumber = Integer.parseInt(attributeIntegralModel.goods_number);
        }

        if (goodsNumber >= maxNumber) {
            return;
        }
        goodsNumber++;
        mNumberEditText.setText(String.valueOf(goodsNumber));
    }
}
