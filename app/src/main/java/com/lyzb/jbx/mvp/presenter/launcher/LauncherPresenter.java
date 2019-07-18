package com.lyzb.jbx.mvp.presenter.launcher;

import com.like.longshaolib.base.inter.IRequestListener;
import com.lyzb.jbx.model.launcher.AdvertModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.launcher.ILauncherView;

import io.reactivex.Observable;

public class LauncherPresenter extends APPresenter<ILauncherView> {

    /**
     * 获取是否有广告
     */
    public void getIsHaveAdvert() {
        onRequestDataHaveCommon(false, new IRequestListener<AdvertModel>() {
            @Override
            public Observable onCreateObservable() {
                return phpCommonApi.getAdvertInfo(1);
            }

            @Override
            public void onSuccess(AdvertModel s) {
                getView().onGetAdvert(s);
            }

            @Override
            public void onFail(String message) {
                getView().onGetAdvert(null);
            }
        });
    }
}
