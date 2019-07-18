package com.szy.yishopcustomer.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.BaseEntity;
import com.szy.yishopcustomer.Util.HttpResultManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UserIntegralFragment extends YSCBaseFragment {
    public static final String MODEL_SYSTEM = "0";
    public static final String MODEL_SHOP = "1";


    private final int HTTP_WHAT_USER_EXCHANGE = 1;

    @BindView(R.id.textView_exchange)
    TextView textView_exchange;
    @BindView(R.id.textView_integration_details)
    TextView textView_integration_details;
    @BindView(R.id.textView_my_points)
    TextView textView_my_points;

    @BindView(R.id.order_status_linear_layout)
    View order_status_linear_layout;

    @BindView(R.id.framelayout)
    FrameLayout framelayout;
    //弄三个fragment

    private List<Fragment> fragmentList = new ArrayList<>();

    Fragment childUserIntegralFragment;
    ChildIntegrationDetailsFragment childIntegrationDetailsFragment;
    ChildExchangeFragment childExchangeFragment;

    FragmentManager fragmentManager;
    int page = 0;

    private String integral_model = MODEL_SYSTEM;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_user_integral;

        page = getActivity().getIntent().getIntExtra(Key.KEY_PAGE.getValue(), 0);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        textView_exchange.setOnClickListener(this);
        textView_integration_details.setOnClickListener(this);
        textView_my_points.setOnClickListener(this);


        refresh();
        return view;
    }

    //接口成功回调
    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_USER_EXCHANGE:
                refreshSucceed(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    //设置数据
    private void refreshSucceed(String response) {
        HttpResultManager.resolve(response, BaseEntity.class, new HttpResultManager.HttpResultCallBack<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity back) {
                if ("1".equals(back.getData())) {
                    integral_model = MODEL_SHOP;
                }

                updateView();
            }

        }, false);
    }

    void updateView() {
        order_status_linear_layout.setVisibility(View.VISIBLE);
        if ("0".equals(integral_model)) {
            textView_my_points.setVisibility(View.GONE);
            if (page == 0) {
                page = 1;
            }
        }

        //设置默认
        switch (page) {
            case 0:
                switchButton(textView_my_points);
                switchFragment(page);
                break;
            case 1:
                switchButton(textView_integration_details);
                switchFragment(page);
                break;
            case 2:
                switchButton(textView_exchange);
                switchFragment(page);
                break;
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.textView_exchange:
                switchButton(view);
                switchFragment(2);
                break;
            case R.id.textView_integration_details:
                switchButton(view);
                switchFragment(1);
                break;
            case R.id.textView_my_points:
                switchButton(view);
                switchFragment(0);
                break;
        }
    }

    void switchButton(View w) {

        textView_exchange.setSelected(false);
        textView_integration_details.setSelected(false);
        textView_my_points.setSelected(false);

        w.setSelected(true);
    }

    void switchFragment(int position) {
        if (fragmentList == null || fragmentList.size() <= 0) {
            if("0".equals(integral_model)) {
                fragmentList.add(childUserIntegralFragment = new Fragment());
            } else {
                fragmentList.add(childUserIntegralFragment = new ChildUserIntegralFragment());
            }

            fragmentList.add(childIntegrationDetailsFragment = new ChildIntegrationDetailsFragment());
            fragmentList.add(childExchangeFragment = new ChildExchangeFragment());
        }

        if (fragmentManager == null) {
            fragmentManager = getActivity().getSupportFragmentManager();

            FragmentTransaction transaction = fragmentManager.beginTransaction();

            for (int i = 0; i < fragmentList.size(); i++) {
                transaction.add(R.id.framelayout, fragmentList.get(i));
            }

            transaction.commit();
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragmentList.size(); i++) {
            if (i == position) {
                transaction.show(fragmentList.get(i));
            } else {
                transaction.hide(fragmentList.get(i));
            }
        }
        transaction.commit();
    }

    @Override
    void refresh() {
        CommonRequest request = new CommonRequest(Api.API_USER_INTEGRAL_INDEX, HTTP_WHAT_USER_EXCHANGE);
        addRequest(request);
    }
}
