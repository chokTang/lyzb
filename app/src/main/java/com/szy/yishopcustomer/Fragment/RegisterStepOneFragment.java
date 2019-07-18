package com.szy.yishopcustomer.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lyzb.jbx.R;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Activity.FindPasswordActivity;
import com.szy.yishopcustomer.Activity.UserAgreementActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.ResponseModel.UserAgreement.Model;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;

import butterknife.BindView;

/**
 * Created by liwei on 2016/4/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class RegisterStepOneFragment extends YSCBaseFragment implements TextWatcher, TextView
        .OnEditorActionListener {

    private static final String TAG = "RegisterStepOneFragment";
    @BindView(R.id.fragment_register_phone_number_editText)
    CommonEditText mPhoneNumberEditText;
    @BindView(R.id.image_checkbox)
    ImageView mAgreeButton;
    @BindView(R.id.fragment_register_agreement_link_button)
    TextView mAgreementLinkButton;
    @BindView(R.id.submit_button)
    Button mStepFinishButton;
    @BindView(R.id.warning_layout)
    LinearLayout mRegisterStepOneTipLayout;
    @BindView(R.id.warning_tip)
    TextView mRegisterStepOneTip;

    private FinishInputNumber mFinishInputNumber;
    private String mPhoneNumber = "";
    private boolean checked = true;
    private String user_agreement;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        updateFinishButtonEnabled();
        mPhoneNumber = mPhoneNumberEditText.getText().toString();

        if (mPhoneNumberEditText.getText().toString().length() != 0) {

            if (isPhone()) {
                mRegisterStepOneTipLayout.setVisibility(View.GONE);
            } else {
                mRegisterStepOneTipLayout.setVisibility(View.VISIBLE);
                mRegisterStepOneTip.setText("手机号是无效的");
            }

        } else {

            mRegisterStepOneTipLayout.setVisibility(View.VISIBLE);
            mRegisterStepOneTip.setText("手机号不能为空");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_button:
                CommonRequest mPhoneExistRequest = new CommonRequest(Api
                        .API_REGISTER_PHONE_EXIST, HttpWhat.HTTP_REGISTER_PHONE_EXIST.getValue());
                mPhoneExistRequest.add("MobileRegisterModel[mobile]", mPhoneNumber);
                addRequest(mPhoneExistRequest);
                break;
            case R.id.image_checkbox:
                checked = !checked;
                mAgreeButton.setSelected(checked);
                updateFinishButtonEnabled();
                break;
            case R.id.fragment_register_agreement_link_button:
                Intent intent = new Intent();
                intent.putExtra(Key.KEY_AGREEMENT.getValue(), user_agreement);
                intent.setClass(getActivity(), UserAgreementActivity.class);
                startActivity(intent);
                break;
            default:
                super.onClick(v);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        mAgreeButton.setOnClickListener(this);
        mAgreementLinkButton.setOnClickListener(this);
        mStepFinishButton.setOnClickListener(this);
        mPhoneNumberEditText.addTextChangedListener(this);
        mPhoneNumberEditText.setOnEditorActionListener(this);
        mStepFinishButton.setText(getResources().getString(R.string.next_step));
        mAgreeButton.setSelected(true);
        refresh();
        Utils.showSoftInputFromWindow(getActivity(), mPhoneNumberEditText);
        //mAgreementLinkButton.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        mAgreeButton.setSelected(checked);
        return v;
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_REGISTER_PHONE_EXIST:
                ResponseCommonModel model = JSON.parseObject(response, ResponseCommonModel.class);
                HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
                    @Override
                    public void onSuccess(ResponseCommonModel back) {
                        if (mFinishInputNumber != null) {
                            mFinishInputNumber.onFinishInputNumber(mPhoneNumber);
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        dialog();
                    }
                }, true);
                break;
            case HTTP_USER_AGREEMENT:
                refreshCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_register_step_one;

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {
            case R.id.fragment_register_phone_number_editText:
                if (isFinishButtonEnabled()) {
                    mStepFinishButton.performClick();
                }
                return false;
            default:
                return false;
        }
    }

    public void setFinishInputNumber(FinishInputNumber finishInputNumber) {
        mFinishInputNumber = finishInputNumber;
    }

    private void dialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View View = layoutInflater.inflate(R.layout.item_fragment_register_view, null);
        View mButtonOne = View.findViewById(R.id.dialong_button_one);
        View mButtonTwo = View.findViewById(R.id.dialong_button_two);
        View mButtonThree = View.findViewById(R.id.dialong_button_three);

        final AlertDialog mDialog = new AlertDialog.Builder(getActivity()).setView(View).create();

        mDialog.show();
        mButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), FindPasswordActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        mButtonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
    }

    private boolean isFinishButtonEnabled() {
        return checked && isPhone();
    }

    private boolean isPhone() {
        String phone_number = mPhoneNumberEditText.getText().toString();
        return Utils.isPhone(phone_number);
    }

    private void updateFinishButtonEnabled() {
        mStepFinishButton.setEnabled(isFinishButtonEnabled());
    }

    public interface FinishInputNumber {
        void onFinishInputNumber(String number);
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_USER_AGREEMENT, HttpWhat
                .HTTP_USER_AGREEMENT.getValue());
        addRequest(request);
    }

    private void refreshCallback(String response) {
        Model model = JSON.parseObject(response, Model.class);
        user_agreement = model.data.user_protocol;
        int mobileRegister = model.data.mobile_register;
        int emailRegister = model.data.email_register;
        boolean is_close = model.data.is_close;

        if (is_close) {
            Toast.makeText(getActivity(), model.data.close_reason, Toast.LENGTH_LONG).show();
            finish();
        } else {
            if (mobileRegister == 0 && emailRegister == 1) {
                Toast.makeText(getActivity(), "商城暂不支持手机注册，请前往PC端或微商城端注册", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

}
