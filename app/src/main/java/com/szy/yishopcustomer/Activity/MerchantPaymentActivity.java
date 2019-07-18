package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.MerchantPaymentFragment;
import com.szy.yishopcustomer.Fragment.UserCardFragment;

public class MerchantPaymentActivity extends YSCBaseActivity {

    public CommonFragment createFragment() {
        return new MerchantPaymentFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
