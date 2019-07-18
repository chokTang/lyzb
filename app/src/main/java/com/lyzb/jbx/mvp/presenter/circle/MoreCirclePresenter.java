package com.lyzb.jbx.mvp.presenter.circle;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.circle.CircleModel;
import com.lyzb.jbx.model.me.ResultModel;
import com.lyzb.jbx.model.params.ApplyCircleBody;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.circle.IMoreCircleView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class MoreCirclePresenter extends APPresenter<IMoreCircleView> {

    private int pageSize = 10;
    private int pageIndex = 1;

    /**
     * 获取更多圈子
     *
     * @param isRefresh
     */
    public void getMoreCircle(final boolean isRefresh) {
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
                return circleApi.getMoreCircle(getHeadersMap(GET, "/lbs/gsGroup/selectByPageList"), params);
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                getView().onMoreCircle(isRefresh, GSONUtil.getEntityList(listArray.toString(), CircleModel.class));
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }


    /****
     * 申请加入 该圈子
     * @param model
     */
    public void applyCir(final ApplyCircleBody model) {
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.applyCir(getHeadersMap(POST, "/lbs/gsGroup/applyGroup"), model);
            }

            @Override
            public void onSuccess(String o) {
                ResultModel resultModel = GSONUtil.getEntity(o, ResultModel.class);

                if ("200".equals(resultModel.getStatus())) {
                    getView().onApply(model.getGroupId());
                } else {
                    showFragmentToast("请求数据异常");
                }
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
