package com.szy.yishopcustomer.Activity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.szy.yishopcustomer.Fragment.GoodsRankPriceFragment;
import com.lyzb.jbx.R;

/**
 * Created by 宗仁 on 16/4/26.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class GoodsRankPriceActivity extends YSCBaseActivity {

    @Override
    public GoodsRankPriceFragment createFragment() {
        return new GoodsRankPriceFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
