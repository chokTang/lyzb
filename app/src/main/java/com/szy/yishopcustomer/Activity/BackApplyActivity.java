package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.BackApplyFragment;

/**
 * Created by liwei on 2017/3/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BackApplyActivity extends YSCBaseActivity {

    @Override
    public BackApplyFragment createFragment() {
        return new BackApplyFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
    }
}
