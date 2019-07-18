package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.UserGroupOnListFragment;

/**
 * Created by liuzhifeng on 2017/3/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class UserGroupOnListActivity extends YSCBaseActivity {

    @Override
    protected UserGroupOnListFragment createFragment() {
        return new UserGroupOnListFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}