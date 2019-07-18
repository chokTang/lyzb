package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.AccountSecurityFragment;

public class AccountSecurityActivity extends YSCBaseActivity {

    private AccountSecurityFragment accountSecurityFragment;

    @Override
    protected CommonFragment createFragment() {
        return accountSecurityFragment = new AccountSecurityFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        accountSecurityFragment.refresh();
    }
}
