package com.lyzb.jbx.mvp.presenter.me;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.model.me.CircleModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.IMyCircleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

/**
 * 我的圈子
 */

public class MyCirclePresenter extends APPresenter<IMyCircleView> {

    private int pageIndex = 1;
    private int pageSize = 10;

    public void getList(final boolean isRefrsh) {


        if (isRefrsh) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }

        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.getCircleList(getHeadersMap(GET, "/lbs/gsGroup/queryUserByAllGroup"), pageIndex, pageSize);
            }

            @Override
            public void onSuccess(String o) {

                CircleModel model = GSONUtil.getEntity(o, CircleModel.class);
                getView().onList(isRefrsh, model.getList());
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
                getView().onFinshOrLoadMore(isRefrsh);
            }
        });
    }

    private class Status {
        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public void getStatus() {

        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.getCirStatus(getHeadersMap(GET, "/lbs/gs/user/selectRoleMembership"));
            }

            @Override
            public void onSuccess(String o) {
                Status status = GSONUtil.getEntity(o, Status.class);
                if (status.getStatus() == 1) {
                    getView().onCreate();
                } else {
                    getView().onUnCreate();
                }
            }

            @Override
            public void onFail(String message) {
                getView().onUnCreate();
            }
        });
    }
}
