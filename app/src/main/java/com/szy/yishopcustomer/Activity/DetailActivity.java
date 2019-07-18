package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.DetailFragment;

/**
 * Created by liuzhifeng on 16/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DetailActivity extends YSCBaseActivity {

    @Override
    protected DetailFragment createFragment() {
        return new DetailFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
    }

}
