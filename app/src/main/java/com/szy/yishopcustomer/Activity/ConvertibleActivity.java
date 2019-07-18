package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.ConvertibleFragment;
import com.szy.yishopcustomer.Fragment.IntegralMallFragment;

public class ConvertibleActivity extends YSCBaseActivity{

    private ConvertibleFragment convertibleFragment;
    @Override
    protected ConvertibleFragment createFragment() {
        return convertibleFragment = new ConvertibleFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requiredLanding = true;
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
    }

}
