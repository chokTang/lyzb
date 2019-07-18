package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Activity.LoginBindingStepTwoActivity;
import com.szy.yishopcustomer.Activity.UserAgreementActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.UserAgreement.Model;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import butterknife.BindView;

/**
 * Created by liwei on 2017/1/12.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class LoginBindingFragmentStepOne extends YSCBaseFragment implements TextWatcher {
    @BindView(R.id.fragment_binding_phone_number_editText)
    CommonEditText mPhoneNumberEditText;
    @BindView(R.id.submit_button)
    Button mStepFinishButton;
    @BindView(R.id.image_checkbox)
    ImageView mAgreeButton;
    @BindView(R.id.fragment_register_agreement_link_button)
    TextView mAgreeArticle;
    @BindView(R.id.warning_layout)
    LinearLayout mBindingStepOneTipLayout;
    @BindView(R.id.warning_tip)
    TextView mBindingStepOneTip;
    @BindView(R.id.bindingTip)
    TextView mBindingStepTip;

    private String mPhoneNumber = "";
    private String logInType;
    private boolean checked = true;
    private String user_agreement;
    private boolean showCaptcha;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        mStepFinishButton.setEnabled(isFinishButtonEnabled());
        mPhoneNumber = mPhoneNumberEditText.getText().toString();

        if (mPhoneNumberEditText.getText().toString().length() != 0) {
            if (isPhone()) {
                mBindingStepOneTipLayout.setVisibility(View.GONE);
            } else {
                mBindingStepOneTipLayout.setVisibility(View.VISIBLE);
                mBindingStepOneTip.setText("手机号是无效的");
            }

        } else {

            mBindingStepOneTipLayout.setVisibility(View.VISIBLE);
            mBindingStepOneTip.setText("手机号不能为空");
        }
    }

    @Override
    public void onClick(View v) {
        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        int extraInfo = Utils.getExtraInfoOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_CHECKBOX:
                checked = !checked;
                mAgreeButton.setSelected(checked);
                mStepFinishButton.setEnabled(isFinishButtonEnabled());
                break;
            case VIEW_TYPE_FINISH_BUTTON:
                openBindingFragmentTwo();
                //checkMobile();
                break;
            case VIEW_TYPE_AGREEMENT:
                Intent intent = new Intent();
                intent.putExtra(Key.KEY_AGREEMENT.getValue(),user_agreement);
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
        View view = super.onCreateView(inflater, container, savedInstanceState);

        if(logInType.equals("weibo")){
            mBindingStepTip.setText(String.format(getResources().getString(R.string.bindingTipOneFormat),"微博"));
        }else if(logInType.equals("qq")){
            mBindingStepTip.setText(String.format(getResources().getString(R.string.bindingTipOneFormat),"QQ"));
        }else if(logInType.equals("weixin")){
            mBindingStepTip.setText(String.format(getResources().getString(R.string.bindingTipOneFormat),"微信"));
        }


        mPhoneNumberEditText.addTextChangedListener(this);
        Utils.setViewTypeForTag(mAgreeButton, ViewType.VIEW_TYPE_CHECKBOX);
        mAgreeButton.setOnClickListener(this);
        Utils.setViewTypeForTag(mStepFinishButton, ViewType.VIEW_TYPE_FINISH_BUTTON);
        mStepFinishButton.setOnClickListener(this);
        Utils.setViewTypeForTag(mAgreeArticle, ViewType.VIEW_TYPE_AGREEMENT);
        mAgreeArticle.setOnClickListener(this);
        mStepFinishButton.setText(getResources().getString(R.string.next_step));
        mAgreeButton.setSelected(true);

        refresh();
        return view;
    }

    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_CHECK_MOBILE:
                ResponseCommonModel commonModel = JSON.parseObject(response, ResponseCommonModel
                        .class);
//                if (commonModel.code == 0) {
                    openBindingFragmentTwo();
//                } else {
//                    Utils.toastUtil.showToast(getActivity(), commonModel.message);
//                }
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
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_BIND_SUCCESS:
                finish();
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_login_binding_step_one;
        Intent intent = getActivity().getIntent();
        logInType = intent.getStringExtra(Key.LOGIN_TYPE.getValue());
    }


    public void refresh(){
        CommonRequest request = new CommonRequest(Api.API_USER_AGREEMENT, HttpWhat
                .HTTP_USER_AGREEMENT.getValue());
        addRequest(request);
    }

    private void refreshCallback(String response) {
        Model model = com.szy.yishopcustomer.Util.JSON.parseObject(response, Model.class);
        user_agreement = model.data.user_protocol;
        showCaptcha = model.data.show_captcha;
    }

    private boolean isFinishButtonEnabled() {
        boolean isEnabled = checked && isPhone();
        return isEnabled;
    }

    private boolean isPhone() {
        String phone_number = mPhoneNumberEditText.getText().toString();
        return Utils.isPhone(phone_number);
    }

    private void openBindingFragmentTwo() {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_PHONE_NUMBER.getValue(), mPhoneNumber);
        intent.putExtra(Key.KEY_BOOLEAN.getValue(), showCaptcha);
        intent.setClass(getActivity(), LoginBindingStepTwoActivity.class);
        startActivity(intent);
    }

    private void checkMobile() {
        CommonRequest checkMobile = new CommonRequest(Api.API_CHECK_BIND, HttpWhat
                .HTTP_CHECK_MOBILE.getValue(), RequestMethod.POST);
        checkMobile.add("MobileBindModel[mobile]", mPhoneNumber);
        addRequest(checkMobile);
    }

}
