package com.like.longshaolib.base.bottom;

import com.like.longshaolib.base.BaseStatusFragment;
import com.like.longshaolib.base.presenter.BasePresenter;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * 基础的Fragment
 * Created by longshao on 2017/8/16.
 */

public abstract class BottomItemFragment<P extends BasePresenter> extends BaseStatusFragment<P> {

    @Override
    public void start(ISupportFragment toFragment) {
        ((BaseBottomFragment) getParentFragment()).start(toFragment);
    }

    @Override
    public void start(ISupportFragment toFragment, int launchMode) {
        ((BaseBottomFragment) getParentFragment()).start(toFragment, launchMode);
    }

    @Override
    public void startForResult(ISupportFragment toFragment, int requestCode) {
        ((BaseBottomFragment) getParentFragment()).startForResult(toFragment, requestCode);
    }
}
