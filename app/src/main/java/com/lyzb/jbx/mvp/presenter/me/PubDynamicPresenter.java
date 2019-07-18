package com.lyzb.jbx.mvp.presenter.me;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.dynamic.AddLikeOrFollowBody;
import com.lyzb.jbx.model.dynamic.DynamicModel;
import com.lyzb.jbx.model.dynamic.RemoveModel;
import com.lyzb.jbx.model.dynamic.RequestRemoveDynamic;
import com.lyzb.jbx.model.me.PubDynamicModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.IPubDynamicView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

/**
 * 我的发表-动态
 */

public class PubDynamicPresenter extends APPresenter<IPubDynamicView> {

    private int pageIndex = 1;
    private int pageSize = 10;

    /**
     * 获取动态列表
     *
     * @param isRefrsh
     */
    public void getList(final boolean isRefrsh) {

        if (isRefrsh) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }

        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.getMeDycList(getHeadersMap(GET, "/lbs/gs/topic/selectTopicInfoList"), pageIndex, pageSize);
            }

            @Override
            public void onSuccess(String o) {
                JSONObject resultObject = JSONUtil.toJsonObject(o);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                List<DynamicModel> list = GSONUtil.getEntityList(listArray.toString(), DynamicModel.class);
                getView().onDataList(isRefrsh, list);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
                getView().onFinshOrLoadMore(isRefrsh);
            }
        });
    }


    /**
     * 删除动态
     */
    public void removeDynamic(final RequestRemoveDynamic body, final int position) {

        onRequestData(true, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return meApi.removeDynamic(getHeadersMap(POST, "/lbs/gs/topic/delPublish"), body);

            }

            @Override
            public void onSuccess(String s) {
                RemoveModel bean = GSONUtil.getEntity(s,RemoveModel.class);
                getView().onRemoveResult(bean,position);
            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    /**
     * 点赞
     *
     * @param dynamicId
     */
    public void onZan(final String dynamicId, final int position) {
        onRequestData(new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return dynamicApi.onAddLikeOrFollow(getHeadersMap(POST, "/lbs/gs/topic/saveGsTopicFavour"),
                        new AddLikeOrFollowBody(dynamicId, "2"));
            }

            @Override
            public void onSuccess(String s) {
                getView().onZanResult(position);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    /**
     * 取消点赞
     *
     * @param dynamicId
     */
    public void onCancleZan(final String dynamicId, final int position) {
        onRequestData(new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return dynamicApi.onCancelLikeOrFollow(getHeadersMap(POST, "/lbs/gs/topic/delGsTopicFavour"),
                        new AddLikeOrFollowBody(dynamicId, "2"));
            }

            @Override
            public void onSuccess(String s) {
                getView().onZanResult(position);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
