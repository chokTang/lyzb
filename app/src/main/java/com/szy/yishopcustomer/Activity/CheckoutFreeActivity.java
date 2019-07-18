package com.szy.yishopcustomer.Activity;

import com.szy.yishopcustomer.Fragment.CheckoutFragment;
import com.szy.yishopcustomer.Fragment.CheckoutFreeFragment;

/**
 * Created by Smart on 2017/6/2.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CheckoutFreeActivity extends CommonPayActivity {
    @Override
    public CheckoutFreeFragment createFragment() {
        return new CheckoutFreeFragment();
    }
}

