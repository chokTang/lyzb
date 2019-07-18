package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.DepositListFragment;

public class DepositListActivity extends YSCBaseActivity {
    @Override
    protected DepositListFragment createFragment() {
        return new DepositListFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
    }
}
