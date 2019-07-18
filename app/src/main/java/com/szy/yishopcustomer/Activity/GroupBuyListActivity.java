package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.GroupBuyListFragment;

/**
 * Created by liwei on 2016/6/17.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class GroupBuyListActivity extends YSCBaseActivity {
    public GroupBuyListFragment createFragment() {
        return new GroupBuyListFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
    }
}
