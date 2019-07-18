package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.ExchangeDetailFragment;

public class ExchangeDetailActivity extends YSCBaseActivity {
    
    public ExchangeDetailFragment createFragment() {
        return new ExchangeDetailFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
    }
}
