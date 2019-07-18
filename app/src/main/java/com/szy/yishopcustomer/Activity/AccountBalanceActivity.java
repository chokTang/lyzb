package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.AccountBalanceFragment;

/**
 * Created by liwei on 16/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class AccountBalanceActivity extends YSCBaseActivity {

    @Override
    protected AccountBalanceFragment createFragment() {
        return new AccountBalanceFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
