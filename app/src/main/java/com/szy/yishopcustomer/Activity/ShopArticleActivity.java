package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.ShopArticleFragment;

/**
 * Created by liuzhifeng on 16/7/9.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopArticleActivity extends YSCBaseActivity {
    @Override
    protected ShopArticleFragment createFragment() {
        return new ShopArticleFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar.hide();
    }
}
