package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.ProfileFragment;

/**
 * Created by liuzhifeng on 16/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ProfileActivity extends YSCBaseActivity {

    public ProfileFragment createFragment() {
        return new ProfileFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
