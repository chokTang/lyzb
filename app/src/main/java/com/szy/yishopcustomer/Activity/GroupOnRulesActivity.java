package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.GroupOnRulesFragment;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.View.MenuPopwindow;

/**
 * Created by liuzhifeng on 17/3/23.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GroupOnRulesActivity extends YSCBaseActivity {
    @Override
    protected GroupOnRulesFragment createFragment() {
        return new GroupOnRulesFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        mCustemMenuStyle = MenuPopwindow.MENU_STYLE_MESSAGE;
//        mBaseMenuId = R.menu.activity_message;
        super.onCreate(savedInstanceState);
    }
}
