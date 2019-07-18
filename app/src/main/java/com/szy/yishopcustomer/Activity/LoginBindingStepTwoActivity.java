package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.LoginBindingFragmentStepTwo;

public class LoginBindingStepTwoActivity extends YSCBaseActivity {

    @Override
    public LoginBindingFragmentStepTwo createFragment() {
        return new LoginBindingFragmentStepTwo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
