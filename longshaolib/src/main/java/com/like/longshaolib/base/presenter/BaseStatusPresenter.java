package com.like.longshaolib.base.presenter;

import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.base.inter.IRequestListener;
import com.like.longshaolib.net.helper.HttpResultFunc;
import com.like.longshaolib.net.helper.HttpResultStringFunc;
import com.like.longshaolib.net.helper.RequestSubscriber;
import com.like.longshaolib.net.inter.SubscriberOnNextListener;
import com.like.utilslib.other.NetUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/4/23.
 */

public class BaseStatusPresenter<V> extends BasePresenter<V> {

    protected <T> void onRequestData(boolean isShowDialog, final IRequestListener<T> listener) {
        if (!NetUtil.isConnected()) {
            ((BaseFragment) getView()).showView(BaseFragment.NONET_VIEW);
            return;
        }

        RequestSubscriber<T> requestSubscriber = new RequestSubscriber<>(context);
        requestSubscriber.isShowDilog(isShowDialog);
        requestSubscriber.bindCallbace(new SubscriberOnNextListener<T>() {
            @Override
            public void onNext(T s) {
                if (isViewAttached()) {
                    ((BaseFragment) getView()).showView(BaseFragment.MAIN_VIEW);
                    if (listener != null) {
                        listener.onSuccess(s);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    ((BaseFragment) getView()).showView(BaseFragment.ERROR_VIEW, e.getMessage());
                    if (listener != null) {
                        listener.onFail(e.getMessage());
                    }
                }
            }
        });

        listener.onCreateObservable()
                .map(new HttpResultStringFunc<T>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(requestSubscriber);
    }

    /**
     * 请求有拦截 返回的Reponse<String> 数据类型的请求
     *
     * @param listener
     * @param <T>
     */
    protected <T> void onRequestData(final IRequestListener<T> listener) {
        onRequestData(true, listener);
    }

    protected void showFragmentToast(String message) {
        if (isViewAttached())
            ((BaseFragment) getView()).showToast(message);
    }

    /**
     * 请求有拦截 返回的公共model为 HttpResult<T> 类型的请求
     *
     * @param isShowDialog
     * @param listener
     * @param <T>
     */
    protected <T> void onRequestDataHaveCommon(boolean isShowDialog, final IRequestListener<T> listener) {
        RequestSubscriber<T> requestSubscriber = new RequestSubscriber<>(context);
        requestSubscriber.isShowDilog(isShowDialog);
        requestSubscriber.bindCallbace(new SubscriberOnNextListener<T>() {
            @Override
            public void onNext(T s) {
                if (isViewAttached()) {
                    ((BaseFragment) getView()).showView(BaseFragment.MAIN_VIEW);
                    if (listener != null) {
                        listener.onSuccess(s);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    ((BaseFragment) getView()).showView(BaseFragment.ERROR_VIEW, e.getMessage());
                    if (listener != null) {
                        listener.onFail(e.getMessage());
                    }
                }
            }
        });

        listener.onCreateObservable()
                .map(new HttpResultFunc<T>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(requestSubscriber);
    }

    /**
     * 请求无拦截
     *
     * @param isShowDialog
     * @param listener
     * @param <T>
     */
    protected <T> void onRequestDataNoMap(boolean isShowDialog, final IRequestListener<T> listener) {
        RequestSubscriber<T> requestSubscriber = new RequestSubscriber<>(context);
        requestSubscriber.isShowDilog(isShowDialog);
        requestSubscriber.bindCallbace(new SubscriberOnNextListener<T>() {
            @Override
            public void onNext(T s) {
                if (isViewAttached()) {
                    ((BaseFragment) getView()).showView(BaseFragment.MAIN_VIEW);
                    if (listener != null) {
                        listener.onSuccess(s);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    ((BaseFragment) getView()).showView(BaseFragment.ERROR_VIEW, e.getMessage());
                    if (listener != null) {
                        listener.onFail(e.getMessage());
                    }
                }
            }
        });

        listener.onCreateObservable()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(requestSubscriber);
    }
}
