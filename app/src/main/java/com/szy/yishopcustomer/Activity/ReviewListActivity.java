package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.ReviewListFragment;
import com.szy.yishopcustomer.View.MenuPopwindow;

/**
 * Created by buqingqiang on 2016/6/5.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ReviewListActivity extends YSCBaseActivity {
    @Override
    protected ReviewListFragment createFragment() {
        return new ReviewListFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        mCustemMenuStyle = MenuPopwindow.MENU_STYLE_MESSAGE;
        super.onCreate(savedInstanceState);
    }
}
