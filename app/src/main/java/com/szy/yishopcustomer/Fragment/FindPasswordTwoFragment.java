package com.szy.yishopcustomer.Fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.FindPassword.FindPasswordStepTwo;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import butterknife.BindView;

/**
 * Created by liwei on 2016/4/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class FindPasswordTwoFragment extends YSCBaseFragment implements TextWatcher, TextView
        .OnEditorActionListener {
    private static final String TAG = "FindPasswordTwoFragment";
    @BindView(R.id.fragment_identity_type)
    TextView mDescription;
    /*    @BindView(R.id.fragment_find_password_nikename)
        TextView mNikeNameTextView;*/
    @BindView(R.id.fragment_find_password_verify_code_editText)
    CommonEditText mVerifyCodeEditText;
    @BindView(R.id.send_code)
    TextView mSendButton;
    @BindView(R.id.submit_button)
    Button mStepFinishButton;
    @BindView(R.id.warning_layout)
    LinearLayout mFindPasswordTipLayout;
    @BindView(R.id.warning_tip)
    TextView mFindPasswordTip;
    @BindView(R.id.fragment_find_password_step_two_title)
    TextView getmFindPasswordTitle;
    private View.OnClickListener mOnClickListener;
    private String mUserInput;
    private String mPhoneNumber;
    private String mEmail;
    private String mNikeName;
    private String mType;
    private FinishInputVerifyCode mFinishInputVerifyCode;
    private String mVerifyCode = "";
    private TimeCount time;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        mStepFinishButton.setEnabled(isFinishButtonEnabled());
        if (isFinishButtonEnabled()) {
            mFindPasswordTipLayout.setVisibility(View.GONE);
        } else {
            mFindPasswordTipLayout.setVisibility(View.VISIBLE);
            mFindPasswordTip.setText("手机验证码不能为空");
        }
        mVerifyCode = mVerifyCodeEditText.getText().toString();
    }

    public static FindPasswordTwoFragment newInstance(String nikename, String phone, String
            email, String type) {
        FindPasswordTwoFragment fragment = new FindPasswordTwoFragment();
        Bundle arguments = new Bundle();
        arguments.putString(Key.KEY_NIKE_NAME.getValue(), nikename);
        arguments.putString(Key.KEY_PHONE_NUMBER.getValue(), phone);
        arguments.putString(Key.KEY_EMAIL.getValue(), email);
        arguments.putString(Key.KEY_FIND_PASSWORD_TYPE.getValue(), type);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_code:
                sendVerifyCode();
                break;
            case R.id.submit_button:
                CommonRequest mPostVerifyCodeRequest = null;
                if (mType.equals("phone")) {
                    mPostVerifyCodeRequest = new CommonRequest(Api
                            .API_FIND_PASSWORD_VALIDATE_MOBILE, HttpWhat.HTTP_POST_VERIFY_CODE
                            .getValue(), RequestMethod.POST);
                    mPostVerifyCodeRequest.add("AccountModel[sms_captcha]", mVerifyCode);
                } else if (mType.equals("email")) {
                    mPostVerifyCodeRequest = new CommonRequest(Api
                            .API_FIND_PASSWORD_VALIDATE_EMAIL, HttpWhat.HTTP_POST_VERIFY_CODE
                            .getValue(), RequestMethod.POST);
                    mPostVerifyCodeRequest.add("AccountModel[email_captcha]", mVerifyCode);
                }
                addRequest(mPostVerifyCodeRequest);
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        mSendButton.setOnClickListener(this);
        mStepFinishButton.setOnClickListener(this);
        mVerifyCodeEditText.addTextChangedListener(this);
        mVerifyCodeEditText.setOnEditorActionListener(this);
        mStepFinishButton.setText(getResources().getString(R.string.next_step));

        Bundle arguments = getArguments();
        if (arguments != null) {
            mNikeName = arguments.getString(Key.KEY_NIKE_NAME.getValue());
            mPhoneNumber = arguments.getString(Key.KEY_PHONE_NUMBER.getValue());
            mEmail = arguments.getString(Key.KEY_EMAIL.getValue());
            mType = arguments.getString(Key.KEY_FIND_PASSWORD_TYPE.getValue());
        }

        mSendButton.setOnClickListener(this);

        if (mType.equals("phone")) {
            mDescription.setText(String.format(getResources().getString(R.string
                    .pleaseEnterVerifyCode), mPhoneNumber));
        } else if (mType.equals("email")) {
            mDescription.setText(String.format(getResources().getString(R.string
                    .pleaseEnterVerifyCode), mEmail));
        }
        if (mPhoneNumber == null && mEmail == null) {
            mDescription.setText(getResources().getString(R.string.binding_no_email_or_phone));
        }
        return v;
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_VERIFY_CODE:
                ResponseCommonModel model = JSON.parseObject(response, ResponseCommonModel.class);
                if (model.code == 0) {
                    Utils.makeToast(getActivity(), model.message);

                    time = new TimeCount(60000, 1000);
                    time.start();
                } else {
                    Utils.makeToast(getActivity(), model.message);
                }
                break;
            case HTTP_POST_VERIFY_CODE:
                FindPasswordStepTwo responseModel = JSON.parseObject(response,
                        FindPasswordStepTwo.class);

                if (time != null) {
                    time.cancel();
                }

                if (responseModel.code == 0) {
                    if (mFinishInputVerifyCode != null) {
                        mFinishInputVerifyCode.onFinishVerifyCode(Integer.toString(responseModel
                                .code));
                    }
                } else {
                    Utils.makeToast(getActivity(), responseModel.data.message);
                }

                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_find_password_two;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {
            case R.id.fragment_find_password_verify_code_editText:
                if (isFinishButtonEnabled()) {
                    mStepFinishButton.performClick();
                }
                return false;
            default:
                return false;
        }
    }

    public void setFinishInputVerifyCode(FinishInputVerifyCode finishInputVerifyCode) {
        mFinishInputVerifyCode = finishInputVerifyCode;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    private boolean isFinishButtonEnabled() {
        String code = mVerifyCodeEditText.getText().toString();
        return (!Utils.isNull(code));
    }

    private void sendVerifyCode() {
        CommonRequest mGetVerifyCodeRequest;
        if (mType.equals("phone")) {
            getmFindPasswordTitle.setText("手机验证");

            mGetVerifyCodeRequest = new CommonRequest(Api.API_FIND_PASSWORD_SMS_CAPTCHA, HttpWhat
                    .HTTP_VERIFY_CODE.getValue(), RequestMethod.POST);
            mGetVerifyCodeRequest.add("mobile", mPhoneNumber);
            addRequest(mGetVerifyCodeRequest);
            mDescription.setText(String.format(getResources().getString(R.string
                    .find_password_phone_code_send), mPhoneNumber));
        } else if (mType.equals("email")) {
            getmFindPasswordTitle.setText("邮箱验证");

            mGetVerifyCodeRequest = new CommonRequest(Api.API_FIND_PASSWORD_EMAIL_CAPTCHA,
                    HttpWhat.HTTP_VERIFY_CODE.getValue(), RequestMethod.POST);
            mGetVerifyCodeRequest.add("email", mEmail);
            addRequest(mGetVerifyCodeRequest);
            mDescription.setText(String.format(getResources().getString(R.string
                    .find_password_email_code_send), mEmail));
        }
        if (mPhoneNumber == null && mEmail == null) {
            Utils.makeToast(getActivity(), getActivity().getResources().getString(R.string
                    .binding_no_email_or_phone));
        }

       Utils.showSoftInputFromWindow(getActivity(),mVerifyCodeEditText);
    }

    public interface FinishInputVerifyCode {
        void onFinishVerifyCode(String code);
    }

    //发短信倒计时
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            mSendButton.setClickable(false);
            mSendButton.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            mSendButton.setText(getResources().getString(R.string.sendVerifyCode));
            mSendButton.setClickable(true);
        }
    }

    @Override
    public void onDestroy() {
        if (time != null) {
            time.cancel();
            time = null;
        }
        super.onDestroy();
    }
}
