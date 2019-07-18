package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.CheckoutFragment;

/**
 * Created by zongren on 16/6/2.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CheckoutActivity extends CommonPayActivity {
    @Override
    public CheckoutFragment createFragment() {
        return new CheckoutFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
    }
}

