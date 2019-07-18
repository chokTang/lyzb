package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.CartFragment;
import com.szy.yishopcustomer.Fragment.CartFreeFragment;

/**
 * Created by liwei on 16/5/28.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CartFreeActivity extends YSCBaseActivity {
    @Override
    public CartFreeFragment createFragment() {
        Bundle args = new Bundle();
        args.putBoolean("type", true);
        CartFreeFragment cartFragment = new CartFreeFragment();
        cartFragment.setArguments(args);
        return cartFragment;
        //return new CartFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar.hide();
    }
}
