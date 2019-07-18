package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.IntegralOutLinePayFragment;

/**
 * Created by Smart on 2018/1/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class IntegralOutLinePayActivity extends YSCBaseActivity {

    public CommonFragment createFragment() {
        return new IntegralOutLinePayFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = false;
        super.onCreate(savedInstanceState);
    }
}
