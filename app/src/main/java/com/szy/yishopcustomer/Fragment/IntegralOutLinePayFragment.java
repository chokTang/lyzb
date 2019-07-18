package com.szy.yishopcustomer.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.EditText;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Activity.BalancePayResultActivity;
import com.szy.yishopcustomer.Activity.PaymentPwdVerActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.BaseEntity;
import com.szy.yishopcustomer.ResponseModel.IntegralOutLinePayModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.OutLinePayExplainDialog;
import com.yolanda.nohttp.RequestMethod;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Smart on 2018/1/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class IntegralOutLinePayFragment extends YSCBaseFragment {

    @BindView(R.id.imageViewHeadImg)
    CircleImageView imageViewHeadImg;
    @BindView(R.id.textViewNickName)
    TextView textViewNickName;

    @BindView(R.id.textViewUserBalance)
    TextView textViewUserBalance;
    @BindView(R.id.textViewUserntegral)
    TextView textViewUserntegral;
    @BindView(R.id.textViewCueWord)
    TextView textViewCueWord;

    @BindView(R.id.editTextPaymentAmount)
    EditText editTextPaymentAmount;
    @BindView(R.id.editTextPaymentIntegral)
    EditText editTextPaymentIntegral;

    @BindView(R.id.imageViewExplain)
    View imageViewExplain;
    @BindView(R.id.fragment_login_action_button)
    View fragment_login_action_button;


    private String points;
    private String amount;
    private String shop_id;

    int balance_password_enable = 0;

    OutLinePayExplainDialog outLinePayExplainDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_integral_out_line_pay;

        outLinePayExplainDialog = new OutLinePayExplainDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        imageViewExplain.setOnClickListener(this);
        fragment_login_action_button.setOnClickListener(this);

        refresh();
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewExplain:
                if(!outLinePayExplainDialog.isShowing()) {
                    outLinePayExplainDialog.show();
                }
                break;
            case R.id.fragment_login_action_button:

                points = editTextPaymentIntegral.getText().toString();
                amount = editTextPaymentAmount.getText().toString();

                if(TextUtils.isEmpty(amount)) {
                    toast("请输入支付金额");
                    return;
                }

                if(TextUtils.isEmpty(points)) {
                    toast("请输入支付积分");
                    return;
                }

                //判断是否有支付密码
                if(balance_password_enable == 0) {
                    submit();
                } else {
                    Intent intent = new Intent(getActivity(),PaymentPwdVerActivity.class);
                    intent.putExtra(PaymentPwdVerFragment.PAY_TYPE,PaymentPwdVerFragment.TYPE_OUT_LINE_PAY);
                    intent.putExtra(Key.KEY_SHOP_ID.getValue(),shop_id);
                    intent.putExtra(Key.KEY_AMOUNT.getValue(),amount);
                    intent.putExtra(Key.KEY_POINTS.getValue(),points);
                    startActivity(intent);
                    finish();
                }
                break;
        }
        super.onClick(view);
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_USER_INTEGRAL_OUT_LINE_PAY, HttpWhat.HTTP_USER_INTEGRAL_OUT_LINE_PAY
                .getValue());
        addRequest(request);
    }

    public void submit(){

        CommonRequest request = new CommonRequest(Api.API_USER_INTEGRAL_OUT_LINE_PAY, HttpWhat.HTTP_SUBMIT
                .getValue(), RequestMethod.POST);
        request.add("OutLinePayModel[shop_id]",shop_id);
        request.add("OutLinePayModel[amount]",amount);
        request.add("OutLinePayModel[points]",points);
        request.setAjax(true);
        addRequest(request);
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_USER_INTEGRAL_OUT_LINE_PAY:
                refreshCallback(response);
                break;
            case HTTP_SUBMIT:
                submitCllback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private void refreshCallback(String response) {
        HttpResultManager.resolve(response, IntegralOutLinePayModel.class, new HttpResultManager.HttpResultCallBack<IntegralOutLinePayModel>() {
            @Override
            public void onSuccess(IntegralOutLinePayModel back) {
                shop_id = back.data.model.shop_id;

                ImageLoader.displayImage(Utils.urlOfImage(back.data.logo, false), imageViewHeadImg);
                textViewNickName.setText(back.data.name);

                editTextPaymentAmount.setFilters(Utils.filterMaxNumber(getContext(), Double.parseDouble(back.data.user_info.user_money), "最多可以使用余额$1元"));
                editTextPaymentIntegral.setFilters(Utils.filterMaxNumber(getContext(), back.data.user_info.pay_point, "最多可以使用" + back.data.user_info.pay_point + "积分"));

                textViewUserBalance.setText(Html.fromHtml("余额（<font color='#f23030'>" + back.data.user_info.user_money + "</font>）"));
                textViewUserntegral.setText(Html.fromHtml("积分（<font color='#f23030'>" + back.data.user_info.pay_point + "</font>）"));
                textViewCueWord.setText("付款完成后，如有问题，请线下联系" + back.data.name);
            }
        });
    }


    private void submitCllback(String response) {
        HttpResultManager.resolve(response, BaseEntity.class, new HttpResultManager.HttpResultCallBack<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity back) {
                openResult(back.getData());
            }
        });
    }

    void openResult(String data) {
        Intent intent = new Intent();
        intent.setClass(getContext(), BalancePayResultActivity.class);
        intent.putExtra(Key.KEY_URL.getValue(), data);
        startActivity(intent);
        finish();
    }

    @Override
    protected void finish() {
        getActivity().finish();
    }
}
