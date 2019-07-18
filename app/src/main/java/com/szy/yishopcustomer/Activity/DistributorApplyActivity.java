package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Fragment.DistributorApplyFragment;

/**
 * 分销申请
 * Created by liwei on 2017/7/17.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistributorApplyActivity extends YSCBaseActivity{

   DistributorApplyFragment mDistributorApplyFragment;

    @Override
    protected CommonFragment createFragment() {
        return mDistributorApplyFragment = new DistributorApplyFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String title = intent.getStringExtra(Key.KEY_GETDISTRIBUTOR_TEXT.getValue());
        this.setTitle("申请"+title);
        super.onCreate(savedInstanceState);
    }

}
