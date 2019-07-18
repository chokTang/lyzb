package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.BackDetailFragment;
import com.szy.yishopcustomer.View.MenuPopwindow;

/**
 * Created by liuzhifeng on 2017/3/9.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BackDetailActivity extends YSCBaseActivity {

    @Override
    protected BackDetailFragment createFragment() {
        return new BackDetailFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        mCustemMenuStyle = MenuPopwindow.MENU_STYLE_MESSAGE;
//        mBaseMenuId = R.menu.activity_message;
        super.onCreate(savedInstanceState);
    }
}