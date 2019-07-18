package com.szy.yishopcustomer.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
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
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * Created by Smart on 2017/11/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class EditEmailTwoFragment extends YSCBaseFragment implements TextWatcher{

    @BindView(R.id.fragment_edit_password_password_edittext)
    CommonEditText mPasswordEdittext;
    @BindView(R.id.layout_waring)
    View mTipLayout;
    @BindView(R.id.warning_tip)
    TextView mTip;
    @BindView(R.id.submit_button)
    Button mNextButton;

    @BindView(R.id.fragment_edit_email_next_button)
    Button fragment_edit_email_next_button;

    @BindView(R.id.linearlayout_step_one)
    View linearlayout_step_one;
    @BindView(R.id.linearlayout_step_two)
    View linearlayout_step_two;

    @BindView(R.id.stepview)
    HorizontalStepView stepView;

    private String mPassword;
    private OnFragmentFinish mListener;

    TimerTask task;
    Timer timer;

    private boolean isFisrtOpen = true;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_button:
                changePassword();
                break;
            case R.id.fragment_edit_email_next_button:
                if (mListener != null) {
                    mListener.onFinish(EditEmailTwoFragment.this);
                }
                break;
            default:
                super.onClick(v);
        }
    }

    private void changePassword() {
        CommonRequest request = new CommonRequest(Api.API_USER_SECURITY_EDIT_EMAIL, HttpWhat
                .HTTP_POST_NEW_PASSWORD.getValue(), RequestMethod.POST);
        request.add("EditEmailModel[email]", mPassword);
        addRequest(request);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("验证身份", 1);
        StepBean stepBean1 = new StepBean("绑定验证邮箱地址", 0);
        StepBean stepBean2 = new StepBean("完成", -1);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepView.setStepViewTexts(stepsBeanList).setTextSize(16);

        mNextButton.setOnClickListener(this);
        fragment_edit_email_next_button.setOnClickListener(this);
        mNextButton.setText(getResources().getString(R.string.next_step));

        mPasswordEdittext.addTextChangedListener(this);

        return v;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_edit_email_two;
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_POST_NEW_PASSWORD:
                HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
                    @Override
                    public void onSuccess(ResponseCommonModel back) {
                        linearlayout_step_one.setVisibility(View.GONE);
                        linearlayout_step_two.setVisibility(View.VISIBLE);
                    }
                }, true);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    public static EditEmailTwoFragment newInstance() {
        EditEmailTwoFragment fragment = new EditEmailTwoFragment();
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mPassword = mPasswordEdittext.getText().toString();
        if (!TextUtils.isEmpty(mPassword)) {
            if (Utils.isEmail(mPassword)) {
                mTipLayout.setVisibility(View.GONE);
                mNextButton.setEnabled(true);
            } else {
                mTipLayout.setVisibility(View.VISIBLE);
                mTip.setText("新邮箱地址不是有效的邮箱地址。");
                mNextButton.setEnabled(false);
            }
        } else {
            mTipLayout.setVisibility(View.VISIBLE);
            mNextButton.setEnabled(false);
            mTip.setText("请输入新邮箱地址");
        }
    }
}
