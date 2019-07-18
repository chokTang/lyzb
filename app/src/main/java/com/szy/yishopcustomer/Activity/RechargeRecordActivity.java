package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.RechargeRecordFragment;
import com.szy.yishopcustomer.View.MenuPopwindow;

/**
 * Created by liwei on 17/5/5.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class RechargeRecordActivity extends YSCBaseActivity {
    @Override
    protected RechargeRecordFragment createFragment() {
        return new RechargeRecordFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        mCustemMenuStyle = MenuPopwindow.MENU_STYLE_MESSAGE;
        super.onCreate(savedInstanceState);
    }
}
