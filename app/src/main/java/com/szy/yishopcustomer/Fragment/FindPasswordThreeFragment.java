package com.szy.yishopcustomer.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.FindPassword.FindPasswordStepThree;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import butterknife.BindView;

/**
 * Created by liwei on 2016/4/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 *
 * @role 找回密码 获取验证码 Fragment
 */
public class FindPasswordThreeFragment extends YSCBaseFragment implements TextWatcher, TextView
        .OnEditorActionListener {
    private static final String TAG = "FindPwdThreeFragment";
    @BindView(R.id.fragment_set_new_password)
    CommonEditText mSetNewPassword;
    @BindView(R.id.submit_button)
    Button mStepFinishButton;
    @BindView(R.id.fragment_find_password_three_password_imageview)
    ImageView mPasswordImageView;
    @BindView(R.id.warning_tip)
    TextView mPasswordTip;
    @BindView(R.id.warning_layout)
    LinearLayout mFindPasswordThreeTipLayout;
    private FinishNewPassword mFinishNewPassword;
    private String mNewPassword = "";
    private boolean mbDisplayFlg = false;
    private View.OnClickListener mOnClickListener;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        updateFinishButtonEnabled();
        mNewPassword = mSetNewPassword.getText().toString();
    }

    public static FindPasswordThreeFragment newInstance(String verifyCode) {
        FindPasswordThreeFragment fragment = new FindPasswordThreeFragment();
        Bundle arguments = new Bundle();
        arguments.putString(Key.KEY_FIND_PASSWORD_VERIFY_CODE.getValue(), verifyCode);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_button:
                CommonRequest mPostNewPasswordRequest = new CommonRequest(Api
                        .API_FIND_PASSWORD_RESET, HttpWhat.HTTP_POST_NEW_PASSWORD.getValue(),
                        RequestMethod.POST);
                mPostNewPasswordRequest.add("AccountModel[password]", mNewPassword);

                addRequest(mPostNewPasswordRequest);
                //Log.d("lyzb", "重置密码请求开始-");

                break;
            case R.id.fragment_find_password_three_password_imageview:
                if (!mbDisplayFlg) {

                    mSetNewPassword.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());
                    mPasswordImageView.setBackgroundResource(R.mipmap.btn_show_password_normal);
                    mSetNewPassword.setSelection(mSetNewPassword.getText().toString().length());

                } else {

                    mSetNewPassword.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());
                    mPasswordImageView.setBackgroundResource(R.mipmap.btn_show_password_disabled);
                    mSetNewPassword.setSelection(mSetNewPassword.getText().toString().length());
                }
                mbDisplayFlg = !mbDisplayFlg;

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

        mStepFinishButton.setOnClickListener(this);
        mSetNewPassword.addTextChangedListener(this);
        mSetNewPassword.setOnEditorActionListener(this);
        mPasswordImageView.setOnClickListener(this);
        mStepFinishButton.setText(getResources().getString(R.string.finish));
        Utils.showSoftInputFromWindow(getActivity(), mSetNewPassword);
        return v;
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_POST_NEW_PASSWORD:
                FindPasswordStepThree responseModel = JSON.parseObject(response,
                        FindPasswordStepThree.class);
                if (responseModel.code == 0) {
                    if (mFinishNewPassword != null) {
                        mFinishNewPassword.onFinishNewPassword(mNewPassword);
                    }

                } else {

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
        mLayoutId = R.layout.fragment_find_password_three;

        //找回密码第二步传过来的值预留
        Bundle arguments = getArguments();
        if (arguments != null) {

            String code = arguments.getString(Key.KEY_FIND_PASSWORD_VERIFY_CODE.getValue());
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {
            default:
                return false;
        }
    }

    public void setFinishNewPassword(FinishNewPassword finishNewPassword) {
        mFinishNewPassword = finishNewPassword;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    private boolean isFinishButtonEnabled() {
        String password = mSetNewPassword.getText().toString();
        return Utils.isPasswordValid(password);
    }

    private void updateFinishButtonEnabled() {
        mStepFinishButton.setEnabled(isFinishButtonEnabled());
        if (!isFinishButtonEnabled()) {
            String password = mSetNewPassword.getText().toString();
            if (password.length() < 6) {
                mFindPasswordThreeTipLayout.setVisibility(View.VISIBLE);
                mPasswordTip.setText("新密码应该包含至少6个字符");
            } else if (password.length() > 20) {
                mFindPasswordThreeTipLayout.setVisibility(View.VISIBLE);
                mPasswordTip.setText("新密码只能包含至多20个字符");
            } else {
                if (Utils.isIncludeSpace(password)) {
                    mFindPasswordThreeTipLayout.setVisibility(View.VISIBLE);
                    mPasswordTip.setText(getResources().getString(R.string
                            .registerSetPasswordTip));
                } else {
                    mFindPasswordThreeTipLayout.setVisibility(View.GONE);
                }
            }
        } else {
            mFindPasswordThreeTipLayout.setVisibility(View.GONE);
        }
    }

    public interface FinishNewPassword {
        void onFinishNewPassword(String password);
    }
}