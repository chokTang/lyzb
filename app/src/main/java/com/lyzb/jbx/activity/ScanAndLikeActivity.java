package com.lyzb.jbx.activity;

import android.os.Bundle;

import com.like.longshaolib.base.BaseActivity;
import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.fragment.dynamic.ScanAndLikeFragment;

/**
 * 动态的点赞效果
 */
public class ScanAndLikeActivity extends BaseActivity {

    private String mDynamicId = "";
    private int mtype = 0;
    private int scanNumber = 0;
    private int likeNumebr = 0;

    @Override
    public BaseFragment setRootFragment() {
        Bundle args = getIntent().getExtras();
        if (args != null) {
            mDynamicId = args.getString("Id");
            mtype = args.getInt("type", 0);
            scanNumber = args.getInt("scanNumber", 0);
            likeNumebr = args.getInt("likeNumebr", 0);
        }
        return ScanAndLikeFragment.Companion.newIncetance(mDynamicId, mtype, scanNumber, likeNumebr);
    }
}