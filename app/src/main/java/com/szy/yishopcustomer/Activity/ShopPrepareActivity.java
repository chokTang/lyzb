package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.ShopPrepareFragment;

/**
 * 预上线店铺主页
 * Created by Smart on 2017/5/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class ShopPrepareActivity extends YSCBaseActivity{

    private ShopPrepareFragment mShopPrepareFragment;
    @Override
    protected ShopPrepareFragment createFragment() {
        return mShopPrepareFragment = new ShopPrepareFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
