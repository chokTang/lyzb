package com.lyzb.jbx.fragment.guide;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.inter.ILoginState;

import qiu.niorgai.StatusBarCompat;

/**
 * Created by Administrator on 2017/7/31.
 */

public class GuideFragment extends BaseFragment {
    //    private BGABanner guide_banner;
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
        return R.layout.fragment_guide_layout;
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
//        guide_banner = (BGABanner) findViewById(R.id.guide_banner);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
//        guide_banner.setEnterSkipViewIdAndDelegate(R.id.guide_signin_tv, 0, new BGABanner.GuideDelegate() {
//            @Override
//            public void onClickEnterOrSkip() {
//                /*设置已经进入过引导页*/
//                AppPreference.getIntance().setFirstLogin(false);
//                if (mILoginState != null) {
//                    mILoginState.enterLoginPage();
//                }
//            }
//        });
//        BGALocalImageSize localImageSize = new BGALocalImageSize(1080, 1920, 720, 1280);
//        guide_banner.setData(localImageSize, ImageView.ScaleType.FIT_XY, R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3);
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
}
