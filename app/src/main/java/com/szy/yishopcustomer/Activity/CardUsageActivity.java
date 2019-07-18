package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.szy.common.Activity.CommonActivity;
import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.CardUsageFragment;
import com.szy.yishopcustomer.Fragment.RedExchangeFragment;

public class CardUsageActivity extends YSCBaseActivity {

    @Override
    protected CommonFragment createFragment() {
        return new CardUsageFragment();
    }
}
