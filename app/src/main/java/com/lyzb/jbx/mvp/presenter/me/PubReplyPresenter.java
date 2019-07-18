package com.lyzb.jbx.mvp.presenter.me;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.me.PubReplyModel;
import com.lyzb.jbx.model.params.IdBody;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.IPubReplyView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * 我的发表-回复
 */

public class PubReplyPresenter extends APPresenter<IPubReplyView> {

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
                Map<String, Object> params = new HashMap<>();
                params.put("pageNum", pageIndex);
                params.put("pageSize", pageSize);
                return meApi.getMeReleaseList(getHeadersMap(GET, "/lbs/gs/topic/selectMyTopicComment"), params);
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                getView().onRelyListResult(isRefrsh, GSONUtil.getEntityList(listArray.toString(), PubReplyModel.class));
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
                getView().onFinshOrLoadMore(isRefrsh);
            }
        });
    }

    /**
     * 删除回复
     */
    public void deleteDynamic(final String id) {
        onRequestData(new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return dynamicApi.deleteDynamicComment(getHeadersMap(POST, "/lbs/gs/topic/delGsTopicComment"), new IdBody(id));
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                int result = JSONUtil.get(resultObject, "index", 0);
                if (result == 1) {
                    getView().onDeleteResult();
                } else {
                    showFragmentToast("删除失败");
                }
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
