package com.lyzb.jbx.fragment.base;

import com.like.longshaolib.base.BaseToolbarFragment;
import com.like.longshaolib.base.presenter.BasePresenter;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

/**
 * @author wyx
 * @role 视频处理frgament
 * @time 2019 2019/4/10 10:52
 */

public abstract class BaseVideoToolbarFrgament<P extends BasePresenter> extends BaseToolbarFragment<P> {

    @Override
    public void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }


    @Override
    public boolean onBackPressedSupport() {
        NiceVideoPlayerManager.instance().onBackPressd();
        return super.onBackPressedSupport();
    }


    @Override
    public void pop() {
        NiceVideoPlayerManager.instance().onBackPressd();
        super.pop();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        NiceVideoPlayerManager.instance().suspendNiceVideoPlayer();
    }
}
