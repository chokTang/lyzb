package com.szy.yishopcustomer.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Interface.OnFragmentFinish;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liwei on 2017/7/11.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class EditPasswordTwoFragment extends YSCBaseFragment implements TextWatcher {

    private static final String TAG = "EditPasswordTwoFragment";

    @BindView(R.id.fragment_edit_password_password_edittext)
    CommonEditText mPasswordEdittext;
    @BindView(R.id.layout_waring)
    View mTipLayout;
    @BindView(R.id.warning_tip)
    TextView mTip;
    @BindView(R.id.submit_button)
    Button mNextButton;

    @BindView(R.id.fragment_edit_password_show_password)
    ImageView mShowPasswordButton;

    @BindView(R.id.stepview)
    HorizontalStepView stepView;

    private String mPassword;
    private OnFragmentFinish mListener;
    Boolean mbDisplayFlg = false;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        mPassword = mPasswordEdittext.getText().toString();
        mNextButton.setEnabled(isFinishButtonEnabled());
        if (mPassword.length() != 0) {
            if (mPassword.length() >= 6 && mPassword.length() <= 20) {
                mTipLayout.setVisibility(View.GONE);
            } else {
                mTipLayout.setVisibility(View.VISIBLE);
                mTip.setText(getActivity().getResources().getString(R.string.tipPasswordRule));
            }
        } else {
            mTipLayout.setVisibility(View.VISIBLE);
            mTip.setText("请输入登录密码");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_button:
                if (mPasswordEdittext.getText().toString().trim().length() <= 0) {

                } else if (mPasswordEdittext.getText().toString().trim().length() < 6) {

                } else if (mPasswordEdittext.getText().toString().trim().length() > 20) {

                } else {
                    changePassword();
                }
                break;
            case R.id.fragment_edit_password_show_password:
                changePasswordDisplay();
                break;
            default:
                super.onClick(v);
        }
    }

    private void changePasswordDisplay() {
        if (!mbDisplayFlg) {
            mPasswordEdittext.setTransformationMethod(HideReturnsTransformationMethod
                    .getInstance());

            mShowPasswordButton.setBackgroundResource(R.mipmap.btn_show_password_normal);

            mPasswordEdittext.setSelection(mPasswordEdittext.getText().toString().length());

        } else {
            mPasswordEdittext.setTransformationMethod(PasswordTransformationMethod
                    .getInstance());
            mShowPasswordButton.setBackgroundResource(R.mipmap.btn_show_password_disabled);

            mPasswordEdittext.setSelection(mPasswordEdittext.getText().toString().length());
        }
        mbDisplayFlg = !mbDisplayFlg;
    }

    private void changePassword() {
        CommonRequest request = new CommonRequest(Api.API_SECURITY_EDIT_PASSWORD, HttpWhat
                .HTTP_POST_NEW_PASSWORD.getValue(), RequestMethod.POST);
        request.add("EditPasswordModel[password]", mPassword);
        addRequest(request);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("验证身份", 1);
        StepBean stepBean1 = new StepBean("设置登录密码", 0);
        StepBean stepBean2 = new StepBean("完成", -1);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepView.setStepViewTexts(stepsBeanList).setTextSize(16);

        mNextButton.setOnClickListener(this);
        mShowPasswordButton.setOnClickListener(this);
        mPasswordEdittext.addTextChangedListener(this);
        mNextButton.setText(getResources().getString(R.string.next_step));
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_edit_password_two;
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_POST_NEW_PASSWORD:
                HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
                    @Override
                    public void onSuccess(ResponseCommonModel back) {

                        if (mListener != null) {
                            mListener.onFinish(EditPasswordTwoFragment.this);
                        }
                    }
                }, true);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private boolean isFinishButtonEnabled() {
        boolean isEnabled = false;
        isEnabled = !Utils.isNull(mPasswordEdittext.getText().toString());
        return isEnabled;
    }

    public static EditPasswordTwoFragment newInstance() {
        EditPasswordTwoFragment fragment = new EditPasswordTwoFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentFinish) {
            mListener = (OnFragmentFinish) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentFinish");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
