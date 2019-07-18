package com.szy.yishopcustomer.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.szy.yishopcustomer.ResponseModel.PaymentIdenModel;
import com.szy.yishopcustomer.ResponseModel.ScanCodePaymentModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Smart on 2018/1/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class MerchantPaymentFragment extends YSCBaseFragment {

    @BindView(R.id.imageViewHeadImg)
    CircleImageView imageViewHeadImg;
    @BindView(R.id.textViewNickName)
    TextView textViewNickName;

    @BindView(R.id.textViewUserBalance)
    TextView textViewUserBalance;
    @BindView(R.id.textViewCueWord)
    TextView textViewCueWord;

    @BindView(R.id.textViewPaymentAmount)
    TextView textViewPaymentAmount;
    @BindView(R.id.editTextPaymentAmount)
    EditText editTextPaymentAmount;
    @BindView(R.id.linearlayoutPaymentAmount)
    View linearlayoutPaymentAmount;

    @BindView(R.id.fragment_login_action_button)
    View fragment_login_action_button;
    @BindView(R.id.imageViewBackground)
    ImageView imageViewBackground;

    private String code;

    private String payment_iden;
    private String amount;
    private String shop_id;

    //默认没有设置支付密码
    private int balance_password_enable = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_merchant_payment;

        code = getActivity().getIntent().getStringExtra(Key.KEY_BARCODE.getValue());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        editTextPaymentAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                amount = s.toString();
            }
        });

        fragment_login_action_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(amount)) {
                    toast("请输入支付金额");
                    return;
                }

                try {
                    if (Double.parseDouble(amount) <= 0) {
                        toast("支付金额不能小于0.01");
                        return;
                    }
                }catch (Exception e) {
                    toast("支付金额不能小于0.01");
                    return;
                }

                //判断是否有支付密码
                if (balance_password_enable == 0) {
                    scanCodePaymentSubmit();
                } else {
                    Intent intent = new Intent(getActivity(), PaymentPwdVerActivity.class);
                    intent.putExtra(Key.KEY_SHOP_ID.getValue(), shop_id);
                    intent.putExtra(Key.KEY_AMOUNT.getValue(), amount);
                    intent.putExtra(Key.KEY_PAYMENT_IDEN.getValue(), payment_iden);
                    startActivity(intent);
                    finish();
                }
            }
        });

        refresh();
        return view;
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_USER_SCAN_CODE_SCAN, HttpWhat.HTTP_SCAN_CODE_INDEX
                .getValue());
        request.add("code", code);
        addRequest(request);
    }

    private void scanCodePayment() {
        CommonRequest request = new CommonRequest(Api.API_USER_SCAN_CODE_PAYMENT, HttpWhat.HTTP_USER_SCAN_CODE_PAYMENT
                .getValue());
        request.add("payment_iden", payment_iden);
        addRequest(request);
    }

    private void scanCodePaymentSubmit() {
        CommonRequest request = new CommonRequest(Api.API_USER_SCAN_CODE_PAYMENT, HttpWhat.HTTP_SUBMIT
                .getValue(), RequestMethod.POST);
        request.add("payment_iden", payment_iden);
        request.add("OutLinePayModel[shop_id]", shop_id);
        request.add("OutLinePayModel[amount]", amount);
        request.setAjax(true);
        addRequest(request);
    }


    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_SCAN_CODE_INDEX:
                refreshCallback(response);
                break;
            case HTTP_USER_SCAN_CODE_PAYMENT:
                scanCodePaymentCllback(response);
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
        HttpResultManager.resolve(response, PaymentIdenModel.class, new HttpResultManager.HttpResultCallBack<PaymentIdenModel>() {
            @Override
            public void onSuccess(PaymentIdenModel back) {
                payment_iden = back.data.payment_iden;

                scanCodePayment();
            }
        });
    }

    private void scanCodePaymentCllback(String response) {
        HttpResultManager.resolve(response, ScanCodePaymentModel.class, new HttpResultManager.HttpResultCallBack<ScanCodePaymentModel>() {
            @Override
            public void onSuccess(ScanCodePaymentModel back) {

                payment_iden = back.data.payment_iden;
                shop_id = back.data.model.shop_id;

                if (!TextUtils.isEmpty(back.data.scan_code_payment_bgimage)) {
                    ImageLoader.displayImage(Utils.urlOfImage(back.data.scan_code_payment_bgimage, false), imageViewBackground);
                }

                ImageLoader.displayImage(Utils.urlOfImage(back.data.logo, false), imageViewHeadImg);
                textViewNickName.setText(back.data.name);

                editTextPaymentAmount.setFilters(Utils.filterMaxNumber(getContext(), Double.parseDouble(back.data.user_info.balance_format), "最多可以使用余额$1元"));
                textViewUserBalance.setText(Html.fromHtml("余额（<font color='#f23030'>" + back.data.user_info.balance_format + "</font>）"));
                textViewCueWord.setText("付款完成后，如有问题，请线下联系" + back.data.name);

                if (Double.parseDouble(back.data.amount) > 0) {
                    linearlayoutPaymentAmount.setVisibility(View.GONE);
                    textViewPaymentAmount.setVisibility(View.VISIBLE);
                    textViewPaymentAmount.setText("支付金额：" + back.data.amount_format);

                    amount = back.data.amount;
                } else {
                    textViewPaymentAmount.setVisibility(View.GONE);
                    linearlayoutPaymentAmount.setVisibility(View.VISIBLE);
                }
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
