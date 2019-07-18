package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.AddAccountFragment;
import com.szy.yishopcustomer.View.MenuPopwindow;

/**
 * Created by LIUZHIFENG on 2017/3/9.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class AddAccountActivity extends YSCBaseActivity {

    @Override
    protected AddAccountFragment createFragment() {
        return new AddAccountFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        mCustemMenuStyle = MenuPopwindow.MENU_STYLE_MESSAGE;
        super.onCreate(savedInstanceState);
    }
}