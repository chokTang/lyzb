package com.like.longshaolib.base.presenter;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * Created by longshao on 2017/10/12.
 */

public abstract class BasePresenter<V> {

    protected WeakReference<V> mViewRef;
    protected Context context;
    
    public void attachContext(Context context) {
        this.context = context;
    }

    public void attachView(V view) {
        mViewRef = new WeakReference<>(view);
    }

    protected V getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
