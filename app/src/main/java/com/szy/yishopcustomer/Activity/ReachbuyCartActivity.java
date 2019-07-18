package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.ReachbuyCartFragment;

/**
 * Created by smart on 17/10/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ReachbuyCartActivity extends YSCBaseActivity {
    @Override
    public CommonFragment createFragment() {
        Bundle args = new Bundle();
        args.putBoolean("type", true);
        ReachbuyCartFragment cartFragment = new ReachbuyCartFragment();
        cartFragment.setArguments(args);
        return cartFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
