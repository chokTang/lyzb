package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.GiftCardPickUpFragment;
import com.szy.yishopcustomer.Fragment.GiftCardPickUpListFragment;


/*
* 选择提货礼品
* */
public class GiftCardPickUpListActivity extends YSCBaseActivity {

    @Override
    protected CommonFragment createFragment() {
        return new GiftCardPickUpListFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
