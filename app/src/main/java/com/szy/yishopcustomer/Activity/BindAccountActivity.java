package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.BindAccountFragment;
import com.szy.yishopcustomer.View.MenuPopwindow;

/**
 * Created by liuzhifeng on 2017/3/9.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BindAccountActivity extends YSCBaseActivity {

    @Override
    protected BindAccountFragment createFragment() {
        return new BindAccountFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        mCustemMenuStyle = MenuPopwindow.MENU_STYLE_MESSAGE;
        super.onCreate(savedInstanceState);
    }
}