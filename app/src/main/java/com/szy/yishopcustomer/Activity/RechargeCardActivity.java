package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.DepositCardFragment;
import com.szy.yishopcustomer.Fragment.RechargeCardFragment;
import com.szy.yishopcustomer.Fragment.YSCBaseFragment;
import com.lyzb.jbx.R;

public class RechargeCardActivity extends YSCBaseActivity {

    private RechargeCardFragment rechargeCardFragment;
    @Override
    protected RechargeCardFragment createFragment() {
        return rechargeCardFragment = new RechargeCardFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
    }

}
