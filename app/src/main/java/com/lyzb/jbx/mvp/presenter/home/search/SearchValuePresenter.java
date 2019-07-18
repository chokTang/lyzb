package com.lyzb.jbx.mvp.presenter.home.search;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.home.search.ISearchValueView;
import com.szy.yishopcustomer.ResponseModel.Search.SearchHintModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class SearchValuePresenter extends APPresenter<ISearchValueView> {

    public void getList(final String value) {
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return commonApi.getSearchByKey(getHeadersMap(GET, "/esapi/lbs/search/suggest"), value);
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                List<SearchHintModel> list = GSONUtil.getEntityList(listArray.toString(), SearchHintModel.class);
                getView().onSearchResultList(list);
            }

            @Override
            public void onFail(String message) {
                getView().onSearchResultList(new ArrayList<SearchHintModel>());
                showFragmentToast(message);
            }
        });
    }
}
