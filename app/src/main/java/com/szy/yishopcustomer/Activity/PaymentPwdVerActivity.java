package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.MerchantPaymentFragment;
import com.szy.yishopcustomer.Fragment.PaymentPwdVerFragment;

public class PaymentPwdVerActivity extends YSCBaseActivity {

    public CommonFragment createFragment() {
        return new PaymentPwdVerFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
