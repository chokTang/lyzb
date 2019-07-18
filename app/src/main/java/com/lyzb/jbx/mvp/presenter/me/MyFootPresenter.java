package com.lyzb.jbx.mvp.presenter.me;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.me.MeFootModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.IMyFootView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class MyFootPresenter extends APPresenter<IMyFootView> {
    private int pageIndex = 1;
    private int pageSize = 10;

    public void getFootList(final boolean isRefresh) {
        if (isRefresh) {
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
                return meApi.getAccessMyRecord(getHeadersMap(GET, "/lbs/gs/user/selectMyAccessRecord"), params);
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                getView().onFootListResult(isRefresh, GSONUtil.getEntityList(listArray.toString(), MeFootModel.class));
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    /**
     * 清空列表
     */
    public void clearFoot() {
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.clearAccessMyRecord(getHeadersMap(GET, "/lbs/gs/user/clearMyAccessRecord"));
            }

            @Override
            public void onSuccess(String s) {
                getView().onClearFootSuccess();
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
