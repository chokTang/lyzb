package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.RechargeFragment;

/**
 * Created by 宗仁 on 16/5/3.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class RechargeActivity extends CommonPayActivity {
    @Override
    public RechargeFragment createFragment() {
        return new RechargeFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
    }
}
