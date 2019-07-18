package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.GiftCardsFragment;
import com.szy.yishopcustomer.Fragment.PromotionFragment;

public class PromotionActivity extends YSCBaseActivity {

    @Override
    protected CommonFragment createFragment() {
        return new PromotionFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
    }
}
