package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.RedExchangeFragment;

public class RedExchangeActivity extends YSCBaseActivity {

    @Override
    protected CommonFragment createFragment() {
        return new RedExchangeFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requiredLanding = true;
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
    }

}
