package com.szy.yishopcustomer.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.szy.yishopcustomer.Activity.AccountSecurityStepActivity;
import com.szy.yishopcustomer.Interface.OnFragmentFinish;
import com.lyzb.jbx.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Smart on 2017/11/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class EditDefaultThreeFragment extends YSCBaseFragment {

    @BindView(R.id.fragment_edit_password_next_button)
    Button mNextButton;

    @BindView(R.id.fragment_edit_password_tip)
    TextView fragment_edit_password_tip;

    @BindView(R.id.stepview)
    HorizontalStepView stepView;

    private OnFragmentFinish mListener;

    private int step_type = AccountSecurityStepActivity.TYPE_STEP_PWD;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_edit_password_next_button:
                if (mListener != null) {
                    mListener.onFinish(this);
                }
                break;
            default:
                super.onClick(v);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("验证身份", 1);
        StepBean stepBean1 = new StepBean("设置登录密码", 1);

        switch (step_type) {
            case AccountSecurityStepActivity.TYPE_STEP_PWD:
                stepBean1.setName("设置登录密码");
                fragment_edit_password_tip.setText("新登录密码设置成功!");
                break;
            case AccountSecurityStepActivity.TYPE_STEP_EMAIL:
                break;
            case AccountSecurityStepActivity.TYPE_STEP_MOBILE:
                stepBean1.setName("绑定验证手机号码");
                fragment_edit_password_tip.setText("新手机号码绑定成功!");
                break;
            case AccountSecurityStepActivity.TYPE_STEP_PAYPWD:
                stepBean1.setName("设置余额支付密码");
                fragment_edit_password_tip.setText("新余额支付密码设置成功!");
                break;
            case AccountSecurityStepActivity.TYPE_STEP_PAYPWD_CLOSE:
                stepBean1.setName("");
                stepBean1 = null;
                fragment_edit_password_tip.setText("余额支付密码已关闭!");
                break;
        }

        StepBean stepBean2 = new StepBean("完成", 0);
        stepsBeanList.add(stepBean0);
        if (stepBean1 != null) {
            stepsBeanList.add(stepBean1);
        }
        stepsBeanList.add(stepBean2);
        stepView.setStepViewTexts(stepsBeanList).setTextSize(16);

        mNextButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_edit_password_three;

        if (getArguments() != null) {
            step_type = getArguments().getInt(AccountSecurityStepActivity.TYPE_STEP, AccountSecurityStepActivity.TYPE_STEP_PWD);
        }
    }

    public static EditDefaultThreeFragment newInstance(int param) {
        EditDefaultThreeFragment fragment = new EditDefaultThreeFragment();
        Bundle args = new Bundle();
        args.putInt(AccountSecurityStepActivity.TYPE_STEP, param);
        fragment.setArguments(args);
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
