package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.OrderListFragment;

/**
 * Created by liwei on 2016/6/12.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class OrderListActivity extends YSCBaseActivity {

    @Override
    public OrderListFragment createFragment() {
        return new OrderListFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
    }
}
