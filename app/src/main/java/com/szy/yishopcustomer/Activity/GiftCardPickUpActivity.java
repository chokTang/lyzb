package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.GiftCardPickUpFragment;
import com.szy.yishopcustomer.Fragment.GiftCardsFragment;
import com.lyzb.jbx.R;

/**
 * 提货券提货
 */
public class GiftCardPickUpActivity extends YSCBaseActivity {

    @Override
    protected CommonFragment createFragment() {
        return new GiftCardPickUpFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
