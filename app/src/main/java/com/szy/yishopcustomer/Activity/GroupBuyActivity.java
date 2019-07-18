package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.GroupBuyFragment;

/**
 * Created by liwei on 2016/5/28.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GroupBuyActivity extends YSCBaseActivity {

    @Override
    public GroupBuyFragment createFragment() {
        return new GroupBuyFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
    }
}
