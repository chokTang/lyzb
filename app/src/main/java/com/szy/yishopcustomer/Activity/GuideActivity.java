package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.GuideFragment;
import com.lyzb.jbx.R;

/**
 * Created by 宗仁 on 16/8/10.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GuideActivity extends YSCBaseActivity {
    @Override
    public GuideFragment createFragment() {
        return new GuideFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.activity_common_no_toolbar;
        super.onCreate(savedInstanceState);
    }
}
