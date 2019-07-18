package com.szy.yishopcustomer.Activity.samecity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Other.CommonEvent;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Fragment.SameCityHomeFragment;
import com.szy.yishopcustomer.Fragment.SameCityNearFragment;
import com.szy.yishopcustomer.Fragment.SameCityOrderFragment;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.App;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 同城生活 原生 ac
 * @time 2018 9:45
 */

public class CityLifeActivity extends FragmentActivity implements View.OnClickListener {

    static final String TAB_HOME = "HOME";
    static final String TAB_NEAR = "NEAR";
    static final String TAB_ORDER = "ORDER";

    static final int HOME = 0;
    static final int NEAR = 1;
    static final int ORDER = 2;

    private FragmentManager fragmentManager;
    private FragmentTransaction mTransaction;

    private SameCityHomeFragment mHomeFragment;
    private SameCityNearFragment mNearFragment;
    private SameCityOrderFragment mOrderFragment;
    private Fragment mCurrentFragment;//当前的fragment

    @BindView(R.id.activity_city_tab_home)
    View mView_Home;
    @BindView(R.id.activity_city_tab_near)
    View mView_Near;
    @BindView(R.id.activity_city_tab_order)
    View mView_Order;

    @BindView(R.id.img_sameCity_tabHome)
    ImageView mImageView_Home;
    @BindView(R.id.tv_sameCity_tabHome)
    TextView mTextView_Home;
    @BindView(R.id.img_sameCity_tabNear)
    ImageView mImageView_Near;
    @BindView(R.id.tv_sameCity_tabNear)
    TextView mTextView_Near;
    @BindView(R.id.img_sameCity_tabOrder)
    ImageView mImageView_Order;
    @BindView(R.id.tv_sameCity_tabOrder)
    TextView mTextView_Order;

    private boolean isClickOrder = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_same_citylife);
        ButterKnife.bind(this);

        fragmentManager = getSupportFragmentManager();

        mView_Home.setOnClickListener(this);
        mView_Near.setOnClickListener(this);
        mView_Order.setOnClickListener(this);

        selectMenu(HOME);
        chooseTabBtn(HOME);

        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_city_tab_home:
                selectMenu(HOME);
                chooseTabBtn(HOME);
                break;
            case R.id.activity_city_tab_near:
                selectMenu(NEAR);
                chooseTabBtn(NEAR);
                break;
            case R.id.activity_city_tab_order:
                if (App.getInstance().isLogin()) {
                    selectMenu(ORDER);
                    chooseTabBtn(ORDER);
                } else {
                    isClickOrder = true;
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
        }
    }

    void selectMenu(int index) {
        mTransaction = fragmentManager.beginTransaction();
        switch (index) {
            case HOME:
                if (mHomeFragment == null) {
                    mHomeFragment = new SameCityHomeFragment();
                    mTransaction.add(R.id.flayout_same_citylife, mHomeFragment, TAB_HOME);
                    mTransaction.commitAllowingStateLoss();
                    mCurrentFragment = mHomeFragment;
                } else {
                    if (mHomeFragment != mCurrentFragment) {
                        if (!mHomeFragment.isAdded()) {
                            mTransaction.hide(mCurrentFragment).add(R.id.flayout_same_citylife, mHomeFragment).commitAllowingStateLoss();
                        } else {
                            mTransaction.hide(mCurrentFragment).show(mHomeFragment).commitAllowingStateLoss();
                        }
                        mCurrentFragment = mHomeFragment;
                    }
                }
                break;
            case NEAR:
                if (mNearFragment == null) {
                    mNearFragment = new SameCityNearFragment();
                    mTransaction.add(R.id.flayout_same_citylife, mNearFragment, TAB_NEAR);
                    mTransaction.hide(mCurrentFragment);
                    mTransaction.commitAllowingStateLoss();
                    mCurrentFragment = mNearFragment;
                } else {
                    if (mNearFragment != mCurrentFragment) {
                        if (!mNearFragment.isAdded()) {
                            mTransaction.hide(mCurrentFragment).add(R.id.flayout_same_citylife, mNearFragment).commitAllowingStateLoss();
                        } else {
                            mTransaction.hide(mCurrentFragment).show(mNearFragment).commitAllowingStateLoss();
                        }
                        mCurrentFragment = mNearFragment;
                    }
                }
                break;
            case ORDER:
                if (mOrderFragment == null) {
                    mOrderFragment = new SameCityOrderFragment();
                    mTransaction.add(R.id.flayout_same_citylife, mOrderFragment, TAB_ORDER);
                    mTransaction.hide(mCurrentFragment);
                    mTransaction.commitAllowingStateLoss();
                    mCurrentFragment = mOrderFragment;
                } else {
                    if (mOrderFragment != mCurrentFragment) {
                        if (!mOrderFragment.isAdded()) {
                            mTransaction.hide(mCurrentFragment).add(R.id.flayout_same_citylife, mOrderFragment).commitAllowingStateLoss();
                        } else {
                            mTransaction.hide(mCurrentFragment).show(mOrderFragment).commitAllowingStateLoss();
                        }
                        mCurrentFragment = mOrderFragment;
                    }
                }
                break;
        }
    }

    void chooseTabBtn(int index) {
        switch (index) {
            case HOME:
                mImageView_Home.setImageResource(R.mipmap.city_tab_home_selected);
                mTextView_Home.setTextColor(this.getResources().getColorStateList(R.color.colorPrimary));

                mImageView_Near.setImageResource(R.mipmap.tab_naer_normal);
                mTextView_Near.setTextColor(this.getResources().getColorStateList(R.color.colorTwo));

                mImageView_Order.setImageResource(R.mipmap.tab_order_normal);
                mTextView_Order.setTextColor(this.getResources().getColorStateList(R.color.colorTwo));
                break;
            case NEAR:
                mImageView_Near.setImageResource(R.mipmap.tab_naer_selected);
                mTextView_Near.setTextColor(this.getResources().getColorStateList(R.color.colorPrimary));

                mImageView_Home.setImageResource(R.mipmap.city_tab_home_normal);
                mTextView_Home.setTextColor(this.getResources().getColorStateList(R.color.colorTwo));

                mImageView_Order.setImageResource(R.mipmap.tab_order_normal);
                mTextView_Order.setTextColor(this.getResources().getColorStateList(R.color.colorTwo));
                break;
            case ORDER:
                mImageView_Order.setImageResource(R.mipmap.tab_order_selected);
                mTextView_Order.setTextColor(this.getResources().getColorStateList(R.color.colorPrimary));

                mImageView_Near.setImageResource(R.mipmap.tab_naer_normal);
                mTextView_Near.setTextColor(this.getResources().getColorStateList(R.color.colorTwo));

                mImageView_Home.setImageResource(R.mipmap.city_tab_home_normal);
                mTextView_Home.setTextColor(this.getResources().getColorStateList(R.color.colorTwo));
                break;
        }
    }

    public void onHome() {
        selectMenu(HOME);
        chooseTabBtn(HOME);
    }

    @Subscribe
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_LOGIN:

                break;
            case SAMECITY_HOME:
                if (!mTransaction.getClass().equals(mHomeFragment)) {
                    selectMenu(HOME);
                    chooseTabBtn(HOME);
                }
                break;
            case SAMECITY_ORDER:
                if (!mTransaction.getClass().equals(mOrderFragment)) {
                    selectMenu(ORDER);
                    chooseTabBtn(ORDER);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isClickOrder && App.getInstance().isLogin()) {
            selectMenu(ORDER);
            chooseTabBtn(ORDER);
        }
        isClickOrder = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
