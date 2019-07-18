package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.ReachbuyFragment;
import com.szy.yishopcustomer.Fragment.ShopPrepareFragment;
import com.lyzb.jbx.R;

public class ReachbuyActivity extends YSCBaseActivity {

    @Override
    protected CommonFragment createFragment() {
        return new ReachbuyFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar.setDisplayHomeAsUpEnabled(false);

        setTitle("");
    }

}
