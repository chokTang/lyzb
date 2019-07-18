package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.DepositFragment;

public class DepositActivity extends YSCBaseActivity {
    @Override
    protected DepositFragment createFragment() {
        return new DepositFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
    }

}
