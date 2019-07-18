package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.BackListFragment;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.View.MenuPopwindow;

/**
 * Created by liwei on 2017/3/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BackListActivity extends YSCBaseActivity {

    @Override
    protected BackListFragment createFragment() {
        return new BackListFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        mCustemMenuStyle = MenuPopwindow.MENU_STYLE_MESSAGE;
//        mBaseMenuId = R.menu.activity_message;
        super.onCreate(savedInstanceState);
    }
}