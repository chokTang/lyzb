package com.lyzb.jbx.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.like.longshaolib.base.BaseActivity;
import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.fragment.circle.CircleDetailFragment;

/**
 * @author wyx
 * @role 动态详情activity
 * @time 2019 2019/4/9 16:07
 */

public class CricleDetailActivity extends BaseActivity {

    private String mValue = "";
    private String mDynamicId = "";

    public static void start(Context context, String value, String dynamicId) {
        Intent intent = new Intent(context, CricleDetailActivity.class);
        intent.putExtra("value", value);
        intent.putExtra("dynamicId", dynamicId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getIntent().getExtras();
        if (args != null) {
            mValue = args.getString("value");
            mDynamicId = args.getString("dynamicId");
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public BaseFragment setRootFragment() {
        return CircleDetailFragment.newIntance(mDynamicId, mValue);
    }
}
