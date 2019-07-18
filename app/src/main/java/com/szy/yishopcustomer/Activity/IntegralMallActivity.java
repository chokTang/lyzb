package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.IntegralMallFragment;
import com.szy.yishopcustomer.Fragment.RechargeCardFragment;

/**
 * 积分商城
 */
public class IntegralMallActivity extends YSCBaseActivity {

    @Override
    protected CommonFragment createFragment() {
        return new IntegralMallFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
    }
}
