package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.OrderDetailFragment;

/**
 * Created by liwei on 2016/6/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class OrderDetailActivity extends YSCBaseActivity {
    public OrderDetailFragment createFragment() {
        return new OrderDetailFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
