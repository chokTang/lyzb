package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Fragment.ReviewFragment;
import com.zhihu.matisse.Matisse;

import java.util.List;

/**
 * Created by buqingqiang on 2016/6/3.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ReviewActivity extends YSCBaseActivity {

    public static MenuItem items;
    private ReviewFragment mFragment;

    @Override
    protected CommonFragment createFragment() {
        mFragment = new ReviewFragment();
        return mFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}