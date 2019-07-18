package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Fragment.DistributorCheckFragment;

/**
 * 我的推荐
 * Created by liwei on 2017/7/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistributorCheckActivity extends YSCBaseActivity{

   DistributorCheckFragment mDistributorCheckFragment;

    @Override
    protected CommonFragment createFragment() {
        return mDistributorCheckFragment = new DistributorCheckFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String title = intent.getStringExtra(Key.KEY_GETDISTRIBUTOR_TEXT.getValue());
        this.setTitle("申请"+title);
        super.onCreate(savedInstanceState);
    }

}
