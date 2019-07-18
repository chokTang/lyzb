package com.lyzb.jbx.mvp.presenter.me;

import android.text.TextUtils;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.like.utilslib.other.LogUtil;
import com.lyzb.jbx.model.circle.CircleDetailItemModel;
import com.lyzb.jbx.model.me.CircleDetModel;
import com.lyzb.jbx.model.me.CompanyCircleTabModel;
import com.lyzb.jbx.model.me.ResultModel;
import com.lyzb.jbx.model.params.ApplyCircleBody;
import com.lyzb.jbx.model.params.SetCompanyTabBody;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.presenter.dynamic.BaseDynamicPresenter;
import com.lyzb.jbx.mvp.view.me.ICircleDetView;
import com.szy.yishopcustomer.ResponseModel.SameCity.Home.list;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * 圈子详情
 */

public class CircleDetPresenter extends APPresenter<ICircleDetView> {

    public void getData(final String id) {
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.getCircleData(getHeadersMap(GET, "/lbs/gsGroup/detail"), id);
            }

            @Override
            public void onSuccess(String o) {
                CircleDetModel model = GSONUtil.getEntity(o, CircleDetModel.class);
                getView().onData(model);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    /**
     * 获取动态详情
     */
    public void getData(final String id, final String value) {
        if (TextUtils.isEmpty(value)) {
            getData(id);
        } else {
            onRequestData(false, new IRequestListener<String>() {

                @Override
                public Observable onCreateObservable() {
                    Map<String, Object> params = new HashMap<>();
                    params.put("topicId", id);
                    params.put("topicDesc", value);
                    return dynamicApi.getDynamicDetailTwo(getHeadersMap(GET, "/lbs/gsGroup/topicInToGroupDetail"), params);
                }

                @Override
                public void onSuccess(String o) {
                    CircleDetModel model = GSONUtil.getEntity(o, CircleDetModel.class);
                    getView().onData(model);
                }

                @Override
                public void onFail(String message) {
                    showFragmentToast(message);
                }
            });
        }
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
                    getView().onApply();
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

    /**
     * 获取企业圈子tab
     */
    public void getCompanyCircleTab(final String groupId) {
        onRequestData(new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                Map<String, Object> map = new HashMap<>();
                map.put("groupId", groupId);
                map.put("type", 2);
                map.put("status", 1);
                return meApi.getCompanyCircleTab(getHeadersMap(GET, "/lbs/gs/user/selectGsCardFunction"), map);
            }

            @Override
            public void onSuccess(String s) {
                List<CompanyCircleTabModel> list = GSONUtil.getEntityList(s, CompanyCircleTabModel.class);
                getView().onCompanyCircleTabData(list);
            }

            @Override
            public void onFail(String message) {
                getView().onFail(message);
            }
        });
    }

    /**
     * 保存企业圈子tab
     */
    public void saveCompanyCircleTab(final SetCompanyTabBody body) {
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.saveCompanyCircleTab(getHeadersMap(POST, "/lbs/gs/user/saveGsCardFunctionSet"), body);
            }

            @Override
            public void onSuccess(String o) {
                getView().onSavaCompanyCircleTabSuccess();
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
