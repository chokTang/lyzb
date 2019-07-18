package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.RecommendFragment;

/**
 * 我的推荐
 * Created by liwei on 2017/7/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class RecommendActivity extends YSCBaseActivity{

    RecommendFragment mRecommendFragment;

    @Override
    protected CommonFragment createFragment() {
        return mRecommendFragment = new RecommendFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
    }

}
