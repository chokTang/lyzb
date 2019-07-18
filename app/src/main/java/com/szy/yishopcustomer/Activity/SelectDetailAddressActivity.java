package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.SelectDetailAddressFragment;

/**
 * Created by liwei on 2017/4/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class SelectDetailAddressActivity extends YSCBaseActivity {

    @Override
    protected SelectDetailAddressFragment createFragment() {
        return new SelectDetailAddressFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}