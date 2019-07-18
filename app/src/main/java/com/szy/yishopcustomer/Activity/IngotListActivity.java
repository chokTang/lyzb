package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.IngotListFragment;

/**
 * @author wyx
 * @role 我的元宝 activity
 * @time 2018 15:15
 */

public class IngotListActivity extends YSCBaseActivity {

    @Override
    public IngotListFragment createFragment() {
        return new IngotListFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = false;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
