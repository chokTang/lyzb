package com.lyzb.jbx.activity;

import android.content.Intent;

import com.like.longshaolib.base.BaseActivity;
import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.fragment.launcher.LauncherFragment;
import com.lyzb.jbx.inter.ILoginState;

/**
 * 闪屏引导页
 * Created by Administrator on 2017/10/15.
 */

public class GuideActivity extends BaseActivity implements ILoginState {
    @Override
    public BaseFragment setRootFragment() {
        return new LauncherFragment();
    }

    @Override
    public void enterLoginPage() {
        startActivity(new Intent(this, HomeActivity.class));
        this.finish();
    }

    @Override
    public void enterHomePage() {
        startActivity(new Intent(this, HomeActivity.class));
        this.finish();
    }

}
