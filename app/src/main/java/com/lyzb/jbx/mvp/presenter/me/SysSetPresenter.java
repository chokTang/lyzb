package com.lyzb.jbx.mvp.presenter.me;

import android.content.Context;

import com.like.longshaolib.base.inter.IRequestListener;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.ISysSetView;

import io.reactivex.Observable;

/**
 * @author wyx
 * @role 系统设置
 * @time 2019 2019/3/23 21:51
 */

public class SysSetPresenter extends APPresenter<ISysSetView> {

    public void Logout(final Context context) {
        onRequestDataHaveCommon(false, new IRequestListener<Object>() {
            @Override
            public Observable onCreateObservable() {
                return phpCommonApi.loginOut();
            }

            @Override
            public void onSuccess(Object object) {
                getView().loginOut();
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });


    }
}
