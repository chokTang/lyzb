package com.lyzb.jbx.mvp.presenter.me.basic;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.me.basic.CardCilpModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.basic.ICardCilpView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class CardCilpPresenter extends APPresenter<ICardCilpView> {

    private int pageIndex = 1;

    public void getList(final boolean isRefresh, final String searchKey) {
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
                params.put("pageSize", 10);
                params.put("searchName", searchKey);
                return cardCilpApi.getCardClipList(getHeadersMap(GET, "/lbs/gs/user/selectMyFolder"), params);
            }

            @Override
            public void onFail(String message) {
                getView().onFinshOrLoadMore(isRefresh);
                showFragmentToast(message);
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                int total = JSONUtil.get(resultObject, "total", 0);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                getView().onListResult(isRefresh, total, GSONUtil.getEntityList(listArray.toString(), CardCilpModel.class));
            }
        });
    }
}
