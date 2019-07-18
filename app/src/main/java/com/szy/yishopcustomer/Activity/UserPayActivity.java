package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.OrderDetailFragment;
import com.szy.yishopcustomer.Fragment.UserPayFragment;
import com.lyzb.jbx.R;

public class UserPayActivity extends YSCBaseActivity {


    public CommonFragment createFragment() {
        return new UserPayFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.activity_common_no_toolbar;
        super.onCreate(savedInstanceState);
    }

}
