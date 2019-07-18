package com.szy.yishopcustomer.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.szy.yishopcustomer.Interface.OnFragmentFinish;
import com.lyzb.jbx.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liwei on 2017/7/11.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class EditPasswordThreeFragment extends YSCBaseFragment {

    private static final String TAG = "EditPasswordThreeFragment";

    @BindView(R.id.fragment_edit_password_next_button)
    Button mNextButton;

    @BindView(R.id.stepview)
    HorizontalStepView stepView;

    private OnFragmentFinish mListener;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_edit_password_next_button:
                if(mListener != null) {
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
        StepBean stepBean2 = new StepBean("完成", 0);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepView.setStepViewTexts(stepsBeanList).setTextSize(16);

        mNextButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_edit_password_three;
    }

    public static EditPasswordThreeFragment newInstance() {
        EditPasswordThreeFragment fragment = new EditPasswordThreeFragment();
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
