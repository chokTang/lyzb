package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.LoginBindingFragmentStepOne;

public class LoginBindingStepOneActivity extends YSCBaseActivity {

    @Override
    public LoginBindingFragmentStepOne createFragment() {
        return new LoginBindingFragmentStepOne();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
