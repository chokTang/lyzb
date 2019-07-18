package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Activity.CartEditNumberActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.CartIndex.ResponseCartModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import butterknife.BindView;

/**
 * Created by liwei on 2017/5/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CartEditNumberFragment extends YSCBaseFragment {
    @BindView(R.id.fragment_cart_edit_number_cancel)
    Button cancelButton;
    @BindView(R.id.fragment_cart_edit_number_confirm)
    Button confirmButton;
    @BindView(R.id.fragment_cart_edit_number_editText)
    EditText editText;
    @BindView(R.id.fragment_cart_edit_number_add)
    TextView addNumber;
    @BindView(R.id.fragment_cart_edit_number_reduce)
    TextView reduceNumber;
    private String skuId;
    private String goodsNumber;

    private int type = 0;
    @Override
    public void onClick(View view) {
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_CANCEL:
                finish();
                break;
            case VIEW_TYPE_CONFIRM:
                updateGoodsNumber(editText.getText().toString(), skuId);
                break;
            case VIEW_TYPE_ADD:
                int tempNumber = Integer.parseInt(editText.getText().toString());
                tempNumber++;
                editText.setText(tempNumber + "");
                editText.setSelection((tempNumber + "").length());
                break;
            case VIEW_TYPE_MINUS:
                int tempNumber2 = Integer.parseInt(editText.getText().toString());
                if (tempNumber2 > 0) {
                    tempNumber2--;
                    editText.setText(tempNumber2 + "");
                    editText.setSelection((tempNumber2 + "").length());
                }
                break;
            default:
                super.onClick(view);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_cart_edit_number;

        Intent intent = getActivity().getIntent();
        skuId = intent.getStringExtra(Key.KEY_SKU_ID.getValue());
        goodsNumber = intent.getStringExtra(Key.KEY_GOODS_NUMBER.getValue());

        type = intent.getIntExtra(CartEditNumberActivity.TYPE,0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        Utils.setViewTypeForTag(cancelButton, ViewType.VIEW_TYPE_CANCEL);
        cancelButton.setOnClickListener(this);
        Utils.setViewTypeForTag(confirmButton, ViewType.VIEW_TYPE_CONFIRM);
        confirmButton.setOnClickListener(this);
        Utils.setViewTypeForTag(addNumber, ViewType.VIEW_TYPE_ADD);
        addNumber.setOnClickListener(this);
        Utils.setViewTypeForTag(reduceNumber, ViewType.VIEW_TYPE_MINUS);
        reduceNumber.setOnClickListener(this);

        editText.setText(goodsNumber);
        editText.setSelection(goodsNumber.length());
        return view;
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GOODS_NUMBER_CHANGE:
                updateGoodsNumberCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    //更改购物车数量
    private void updateGoodsNumber(String goodsNumber, String sku_id) {

        String url = Api.API_CART_CHANGE_NUMBER;
        if(type == CartEditNumberActivity.TYPE_FREE_CART) {
            url = Api.API_FREEBUY_CART_CHANGE_NUMBER;
        } else if (type == CartEditNumberActivity.TYPE_REACHBUY_CART) {
                url = Api.API_REACHBUY_CART_CHANGE_NUMBER;
        }

        CommonRequest mChangeGoodsNumberRequest = new CommonRequest(url,
                HttpWhat.HTTP_GOODS_NUMBER_CHANGE.getValue(), RequestMethod.POST);
        mChangeGoodsNumberRequest.add("sku_id", sku_id);
        mChangeGoodsNumberRequest.add("number", goodsNumber);
        addRequest(mChangeGoodsNumberRequest);
    }

    private void updateGoodsNumberCallback(String response) {
        HttpResultManager.resolve(response, ResponseCartModel.class, new HttpResultManager.HttpResultCallBack<ResponseCartModel>() {
            @Override
            public void onSuccess(ResponseCartModel back) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
