package com.lyzb.jbx.mvp.presenter.account;

import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.net.helper.HttpResultStringFunc;
import com.like.longshaolib.net.helper.RequestSubscriber;
import com.like.longshaolib.net.inter.SubscriberOnNextListener;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.model.account.UserModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.account.ILoginView;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/12/30.
 */

public class LoginPresenter extends APPresenter<ILoginView> {

    public void onLogin(String password, String phoneNumber) {
        RequestSubscriber<String> requestSubscriber = new RequestSubscriber<String>(context)
                .bindCallbace(new SubscriberOnNextListener<String>() {
                    @Override
                    public void onNext(String s) {
                        if (isViewAttached()) {
                            getView().onLoginResult(GSONUtil.getEntity(s, UserModel.class));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached()) {
                            ((BaseFragment) getView()).showToast(e.getMessage());
                        }
                    }
                });
        Map<String, Object> params = new HashMap<>();
        params.put("user_phone", phoneNumber);
        params.put("user_pass", password);
        accountApi.onLogin(paramsDeal(params))
                .map(new HttpResultStringFunc())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(requestSubscriber);
    }
}
