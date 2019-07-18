package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.ApplyRecommShopFragment;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.View.MenuPopwindow;

/**
 * 申请店铺开店
 * Created by Smart on 2017/5/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ApplyRecommShopActivity extends YSCBaseActivity {


    @Override
    protected CommonFragment createFragment() {
        return new ApplyRecommShopFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        mCustemMenuStyle = MenuPopwindow.MENU_STYLE_MESSAGE;
//        mBaseMenuId = R.menu.activity_message;
        super.onCreate(savedInstanceState);
    }

}
