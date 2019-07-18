package com.lyzb.jbx.fragment.base;

import com.like.longshaolib.base.BaseStatusFragment;
import com.like.longshaolib.base.BaseToolbarFragment;
import com.like.longshaolib.base.presenter.BasePresenter;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

/**
 * @author wyx
 * @role 视频处理frgament
 * @time 2019 2019/4/10 10:52
 */
public abstract class BaseVideoStatusFrgament<P extends BasePresenter> extends BaseStatusFragment<P> {

    @Override
    public void onStop() {
        super.onStop();//2041
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
