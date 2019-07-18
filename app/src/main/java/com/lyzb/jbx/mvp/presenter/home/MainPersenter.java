package com.lyzb.jbx.mvp.presenter.home;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.model.account.IsPerfectModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.home.IMainView;
import com.szy.yishopcustomer.Util.App;

import io.reactivex.Observable;

public class MainPersenter extends APPresenter<IMainView> {

    /**
     * 获取用户是否完善信息
     */
    public void getAccuontIsPerfect() {
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return accountApi.getAccountIsPerfect(getHeadersMap(GET, "/lbs/gs/user/queryIsPerfect"));
            }

            @Override
            public void onSuccess(String s) {
                IsPerfectModel model = GSONUtil.getEntity(s, IsPerfectModel.class);
                if (null != model) {
                    App.getInstance().isUserInfoPer = model.isSt();
                }
            }

            @Override
            public void onFail(String message) {

            }
        });
    }
}
