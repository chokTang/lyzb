package com.szy.yishopcustomer.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.RadioGroup;

import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.yishopcustomer.Activity.samecity.CityLifeActivity;
import com.lyzb.jbx.R;

/**
 * @author wyx
 * @role 同城生活 订单
 * @time 2018 11:15
 */

public class SameCityOrderFragment extends YSCBaseFragment implements OnEmptyViewClickListener {

    View mView = null;
    private RadioGroup order_type_rg;

    private YSCBaseFragment[] fragments = new YSCBaseFragment[3];
    private int mCurrentIndex = 0;

    private CityLifeActivity parentActivty;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivty = (CityLifeActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragments[0] = new SameCityOrderTakeOutFragment();
        fragments[1] = new SameCityOrderTeamFragment();
        fragments[2] = new SameCityOrderPayShopFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_samecity_order, container, false);
        }
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        order_type_rg = findViewById(R.id.order_type_rg);
        FragmentTransaction mTransaction = getChildFragmentManager().beginTransaction();
        mTransaction.add(R.id.container, fragments[0]);
        mTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        order_type_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    //团购订单
                    case R.id.group_order_rbtn:
                        switchContent(1);
                        break;
                    //到店付订单
                    case R.id.pay_order_rbtn:
                        switchContent(2);
                        break;
                    //外卖订单
                    case R.id.takeout_order_rbtn:
                        switchContent(0);
                        break;
                }
            }
        });
    }

    /**
     * 初始化组件
     *
     * @param idres
     * @param <T>
     * @return
     */
    private <T> T findViewById(@IdRes int idres) {
        return (T) mView.findViewById(idres);
    }

    public void onHome() {
        parentActivty.onHome();
    }

    private void switchContent(int index) {
        if (index != mCurrentIndex) {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            if (!fragments[index].isAdded()) { // 判断是否被add过
                // 隐藏当前的fragment，将 下一个fragment 添加进去
                transaction.hide(fragments[mCurrentIndex]).add(R.id.container, fragments[index]).commitAllowingStateLoss();
            } else {
                // 隐藏当前的fragment，显示下一个fragment
                transaction.hide(fragments[mCurrentIndex]).show(fragments[index]).commitAllowingStateLoss();
            }
            mCurrentIndex = index;
        }
    }
}
