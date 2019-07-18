package com.lyzb.jbx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.like.longshaolib.base.BaseActivity;
import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.fragment.home.MainFragment;
import com.lyzb.jbx.util.AppPreference;
import com.szy.yishopcustomer.Activity.RootActivity;

/**
 * 主页
 * Created by longshao on 2017/3/12.
 */

public class HomeActivity extends BaseActivity {

    private long exitTime = 0;

    @Override
    public BaseFragment setRootFragment() {
        return new MainFragment();
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            long nowTime = System.currentTimeMillis();
            if (nowTime - exitTime > 2000) {
                showToast("再点击一次退出程序");
                exitTime = nowTime;
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!AppPreference.getIntance().getDoubleHome()){
            startActivity(new Intent(this, RootActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppPreference.getIntance().setDoubleHome(true);
    }
}
