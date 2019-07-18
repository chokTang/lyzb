package com.szy.yishopcustomer.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.BalancePayResultModel;
import com.szy.yishopcustomer.Util.HttpResultManager;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BalancePayResultFragment extends YSCBaseFragment {

    @BindView(R.id.textViewPayResult)
    TextView textViewPayResult;
    @BindView(R.id.textViewPayAmount)
    TextView textViewPayAmount;
    @BindView(R.id.textViewShopName)
    TextView textViewShopName;

    private String url;

    private String shop_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_balance_pay_result;

        url = Config.BASE_URL + getActivity().getIntent().getStringExtra(Key.KEY_URL.getValue());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        refresh();
        return view;
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(url, HttpWhat.HTTP_RESULT
                .getValue());
        addRequest(request);
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_RESULT:
                refreshCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private void refreshCallback(String response) {
        HttpResultManager.resolve(response, BalancePayResultModel.class, new HttpResultManager.HttpResultCallBack<BalancePayResultModel>() {
            @Override
            public void onSuccess(BalancePayResultModel back) {
                textViewPayResult.setText("支付成功!");
                if(back.data.points > 0) {
                    textViewPayAmount.setText(Html.fromHtml(back.data.amount_format + "<font color='#666666'> + </font>" + back.data.points+ "积分"));
                } else {
                    textViewPayAmount.setText(back.data.amount_format);
                }
                textViewShopName.setText(back.data.name);
                shop_id = back.data.shop_id;
            }
        });
    }

    public void openShop() {
        if (!TextUtils.isEmpty(shop_id)) {
            Intent intent = new Intent(getContext(), ShopActivity.class);
            intent.putExtra(Key.KEY_SHOP_ID.getValue(), shop_id);
            startActivity(intent);
        }

        getActivity().finish();
    }
}
