package com.lyzb.jbx.fragment.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.fragment.guide.GuideFragment;
import com.lyzb.jbx.inter.ILoginState;
import com.lyzb.jbx.model.launcher.AdvertModel;
import com.lyzb.jbx.mvp.presenter.launcher.LauncherPresenter;
import com.lyzb.jbx.mvp.view.launcher.ILauncherView;
import com.lyzb.jbx.util.AppPreference;

import qiu.niorgai.StatusBarCompat;

/**
 * APP的启动页面
 * Created by Administrator on 2017/7/31.
 */

public class LauncherFragment extends BaseFragment<LauncherPresenter> implements ILauncherView {

    private ILoginState mILoginState;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILoginState) {
            mILoginState = (ILoginState) activity;
        }
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_launcher_layout;
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mPresenter.getIsHaveAdvert();
    }

    private void checkIsShowScroll(AdvertModel model) {
        if (AppPreference.getIntance().getIsFirstLogin()) {//判断是否第一次登陆
            startWithPop(new GuideFragment());
        } else {
            //表示有广告页面
            if (model != null && !TextUtils.isEmpty(model.getAdvert_link())) {
                startWithPop(AdvertFragment.newIntance(model));
            } else {
                //检查用户是否登陆了APP
                if (AppPreference.getIntance().getAccountLonginState()) {
                    if (mILoginState != null) {
                        mILoginState.enterHomePage();
                    }
                } else {
                    if (mILoginState != null) {
                        mILoginState.enterLoginPage();
                    }
                }
            }
        }
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StatusBarCompat.translucentStatusBar(_mActivity, true);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarCompat.translucentStatusBar(_mActivity, true);
        }
    }

    @Override
    public void onGetAdvert(AdvertModel advertModel) {
        checkIsShowScroll(advertModel);
    }
}
