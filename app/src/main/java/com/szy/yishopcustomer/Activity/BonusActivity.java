package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.BonusFragment;

/**
 * 用户中心-我的红包
 */
public class BonusActivity extends YSCBaseActivity{

    public BonusFragment createFragment() {
        return new BonusFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEnableBaseMenu = true;
    }
}