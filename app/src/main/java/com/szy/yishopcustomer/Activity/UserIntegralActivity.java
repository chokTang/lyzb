package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.szy.yishopcustomer.Fragment.OrderListFragment;
import com.szy.yishopcustomer.Fragment.UserIntegralFragment;
import com.lyzb.jbx.R;

/**
 * 我的积分界面
 */
public class UserIntegralActivity extends YSCBaseActivity {

    @Override
    public UserIntegralFragment createFragment() {
        return new UserIntegralFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requiredLanding = true;
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
    }



}
