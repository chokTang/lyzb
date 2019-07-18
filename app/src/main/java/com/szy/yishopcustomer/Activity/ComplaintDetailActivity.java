package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.ComplaintDetailFragment;
import com.szy.yishopcustomer.View.MenuPopwindow;

/**
 * 投诉商家详情
 * Created by liwei on 2017/11/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ComplaintDetailActivity extends YSCBaseActivity {

    @Override
    protected CommonFragment createFragment() {
        return new ComplaintDetailFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        mCustemMenuStyle = MenuPopwindow.MENU_STYLE_MESSAGE;
//        mBaseMenuId = R.menu.activity_message;
        super.onCreate(savedInstanceState);
    }

}