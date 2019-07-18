package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.LoginBindingFragmentStepThree;

public class LoginBindingStepThreeActivity extends YSCBaseActivity {

    @Override
    public LoginBindingFragmentStepThree createFragment() {
        return new LoginBindingFragmentStepThree();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
