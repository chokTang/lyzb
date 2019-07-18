package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.BindDepositCardFragment;
import com.szy.yishopcustomer.Fragment.RechargeCardFragment;

public class BindDepositCardActivity extends YSCBaseActivity {

    @Override
    protected CommonFragment createFragment() {
        return new BindDepositCardFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
    }

}
