package com.lyzb.jbx.mvp.presenter.me;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.model.me.CircleDetModel;
import com.lyzb.jbx.model.me.CircleMerberModel;
import com.lyzb.jbx.model.me.CircleOpeModel;
import com.lyzb.jbx.model.me.CircleRemModel;
import com.lyzb.jbx.model.me.ResultModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.ICircleMerView;

import io.reactivex.Observable;

/**
 * 我的圈子-成员列表
 */

public class CircleMerberPresenter extends APPresenter<ICircleMerView> {


    private int pageNumber = 1;
    private int pageSize = 10;

    public void getList(final boolean isRefrsh, final String groupId, final String userName) {
        if (isRefrsh) {
            pageNumber = 1;
        } else {
            pageNumber++;
        }
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.getCirMebList(getHeadersMap(GET, "/lbs/gsGroup/queryGroupMembers"), groupId, pageNumber, pageSize, userName);
            }

            @Override
            public void onSuccess(String o) {
                CircleMerberModel model = GSONUtil.getEntity(o, CircleMerberModel.class);
                getView().getMebList(isRefrsh, model);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });

    }

    //退出 解散圈子
    public void extCircle(final boolean isExit, final CircleOpeModel model) {
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                if (isExit) {
                    return meApi.onExitCircle(getHeadersMap(POST, "/lbs/gsGroup/introductionGroupMembers"), model);
                } else {
                    return meApi.onDissCircle(getHeadersMap(POST, "/lbs/gsGroup/deleteGroup"), model);
                }
            }

            @Override
            public void onSuccess(String o) {
                ResultModel resultModel = GSONUtil.getEntity(o, ResultModel.class);
                if ("200".equals(resultModel.getStatus())) {
                    if (isExit) {
                        getView().onExit();
                    } else {
                        getView().onDiss();
                    }
                } else {
                    showFragmentToast(resultModel.getMsg());
                }
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });

    }

    //移除某人
    public void removePeople(final CircleRemModel model, final int position) {
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return meApi.onRemoveCircle(getHeadersMap(POST, "/lbs/gsGroup/deleteGroupMembers"), model);
            }

            @Override
            public void onSuccess(String o) {
                ResultModel resultModel = GSONUtil.getEntity(o, ResultModel.class);
                if ("200".equals(resultModel.getStatus())) {
                    getView().onRemove(position);
                } else {
                    showFragmentToast(resultModel.getMsg());
                }
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    /**
     * 获取圈子详情数据
     *
     * @param id
     */
    public void getData(final String id) {
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.getCircleData(getHeadersMap(GET, "/lbs/gsGroup/detail"), id);
            }

            @Override
            public void onSuccess(String o) {
                CircleDetModel model = GSONUtil.getEntity(o, CircleDetModel.class);
                getView().onGetCircleMemberNum(model.getNotApplyCount());
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
