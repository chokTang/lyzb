package com.lyzb.jbx.activity;

import com.like.longshaolib.base.BaseActivity;
import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.fragment.account.PerfectOneFragment;

/**
 * @author wyx
 * @role
 * @time 2019 2019/3/24 15:42
 */

public class PerfectActivity extends BaseActivity {
    @Override
    public BaseFragment setRootFragment() {
        return new PerfectOneFragment();
    }
}
