package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.CheckoutExchangeFragment;

public class CheckoutExchangeActivity extends YSCBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    public CheckoutExchangeFragment createFragment() {
        return new CheckoutExchangeFragment();
    }

}
