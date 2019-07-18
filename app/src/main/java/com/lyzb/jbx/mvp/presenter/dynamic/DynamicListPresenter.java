package com.lyzb.jbx.mvp.presenter.dynamic;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.dynamic.DynamicModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.dynamic.IDynamicListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by shidengzhong on 2019/3/5.
 */

public class DynamicListPresenter extends BaseDynamicPresenter<IDynamicListView> {

    private int pageIndex = 1;
    private int pageSize = 10;

    /**
     * 获取动态列表
     *
     * @param isRefrsh
     */
    public void getList(final boolean isRefrsh, final String searchValue) {
        if (isRefrsh) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> params = new HashMap<>();
                params.put("msg", searchValue);
                params.put("pageNum", pageIndex);
                params.put("pageSize", pageSize);
                return dynamicApi.getSearchDynamic(getHeadersMap(GET, "/lbs/gs/home/searchTopic"), params);
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                JSONArray listArray =JSONUtil.getJsonArray(resultObject,"list");
                getView().onDynamicList(isRefrsh, GSONUtil.getEntityList(listArray.toString(),DynamicModel.class));
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
                getView().onFinshRefresh(isRefrsh);
            }
        });
    }
}
