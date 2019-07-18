package com.szy.yishopcustomer.Activity;

import com.szy.yishopcustomer.Fragment.OrderPayFragment;

/**
 * Created by 宗仁 on 16/8/2.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class OrderPayActivity extends CommonPayActivity {

    @Override
    public OrderPayFragment createFragment() {
        return new OrderPayFragment();
    }
}
