package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.OrderListFragment;
import com.szy.yishopcustomer.Fragment.OrderListFreeFragment;

/**
 * Created by smart on 2017/6/12.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class OrderListFreeActivity extends YSCBaseActivity {

    @Override
    public OrderListFreeFragment createFragment() {
        return new OrderListFreeFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
    }
}
