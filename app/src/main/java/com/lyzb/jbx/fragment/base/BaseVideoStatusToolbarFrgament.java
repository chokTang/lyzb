package com.lyzb.jbx.fragment.base;

import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.base.BaseStatusToolbarFragment;
import com.like.longshaolib.base.presenter.BasePresenter;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

/**
 * @author wyx
 * @role 视频处理frgament
 * @time 2019 2019/4/10 10:52
 */

public abstract class BaseVideoStatusToolbarFrgament<P extends BasePresenter> extends BaseFragment<P> {

    protected abstract NiceVideoPlayer getVideoPlayer();

    @Override
    public void onStop() {
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
        super.onStop();
    }

    @Override
    public boolean onBackPressedSupport() {
        if (getVideoPlayer()!=null){
            getVideoPlayer().releasePlayer();
        }
        NiceVideoPlayerManager.instance().onBackPressd();
        return super.onBackPressedSupport();
    }


    @Override
    public void pop() {
        if (getVideoPlayer()!=null){
            getVideoPlayer().releasePlayer();
        }
        NiceVideoPlayerManager.instance().onBackPressd();
        super.pop();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        NiceVideoPlayerManager.instance().suspendNiceVideoPlayer();
    }
}
