package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.CheckoutExchangeFragment;
import com.szy.yishopcustomer.Fragment.CheckoutIntegralFragment;

public class CheckoutIntegralActivity  extends YSCBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    public CheckoutIntegralFragment createFragment() {
        return new CheckoutIntegralFragment();
    }

}
