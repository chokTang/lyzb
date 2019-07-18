package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.UserCardFragment;
import com.szy.yishopcustomer.Fragment.UserPayFragment;

public class UserCardActivity extends YSCBaseActivity {

    public CommonFragment createFragment() {
        return new UserCardFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
