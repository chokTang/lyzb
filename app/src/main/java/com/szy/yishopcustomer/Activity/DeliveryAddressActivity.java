package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.DeliveryAddressFragment;

/**
 * Created by liuzhifeng on 2017/3/9.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DeliveryAddressActivity extends YSCBaseActivity {

    @Override
    protected DeliveryAddressFragment createFragment() {
        return new DeliveryAddressFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}