package com.lyzb.jbx.mvp.presenter.me;

import android.text.TextUtils;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.circle.CircleDetailItemModel;
import com.lyzb.jbx.mvp.presenter.dynamic.BaseDynamicPresenter;
import com.lyzb.jbx.mvp.view.me.ICircleDetView;
import com.lyzb.jbx.mvp.view.me.ICircleDynamictView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author wyx
 * @role
 * @time 2019 2019/5/15 9:34
 */

public class CircleDynamicPresenter extends BaseDynamicPresenter<ICircleDynamictView> {

    private int pageIndex = 1;
    private int pageSize = 10;

    /**
     * 圈子动态  分为普通圈子动态、成员动态、企业动态
     *
     * @param isRefresh
     * @param id        圈子id(圈子)或企业id(名片-新闻)
     * @param type      ==1 普通圈子动态 ==2 圈主(企业)动态  ==3成员动态  ==4新闻
     */
    public void getCircleDynamicList(final boolean isRefresh, final String id, int type, String loginUserId) {
        switch (type) {
            case 1:
                getCircleDynamicList(isRefresh, id);
                break;
            case 2:
            case 4:
                getCompanCircleDynamicList(isRefresh, id, type, loginUserId);
                break;
            case 3:
                getMemberDynamicList(isRefresh, id);
                break;
            default:
        }
    }

    /**
     * 普通圈子动态
     *
     * @param isRefresh
     * @param id
     */
    private void getCircleDynamicList(final boolean isRefresh, final String id) {
        if (isRefresh) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.getCirDync(getHeadersMap(GET, "/lbs/gsGroup/topicList"), id, pageIndex, pageSize);
            }

            @Override
            public void onSuccess(String o) {
                JSONObject resultObject = JSONUtil.toJsonObject(o);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                List<CircleDetailItemModel> list = GSONUtil.getEntityList(listArray.toString(), CircleDetailItemModel.class);
                getView().onDataList(isRefresh, list);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
                getView().onFishOrLoadMore(isRefresh);
            }
        });
    }

    /**
     * 企业动态or新闻
     *
     * @param isRefresh
     * @param loginUserId
     */
    private void getCompanCircleDynamicList(final boolean isRefresh, final String id, final int type, final String loginUserId) {
        //id为空时不去直接返回，不然会去查自己的数据
        if (TextUtils.isEmpty(loginUserId)) {
            getView().onDataList(isRefresh, new ArrayList<CircleDetailItemModel>());
            return;
        }
        if (isRefresh) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                Map<String, Object> map = new HashMap<>();
                map.put("pageNum", pageIndex);
                map.put("pageSize", pageSize);
                map.put("loginUserId", loginUserId);
                if (type == 2) {
                    //企业动态
                    map.put("groupId", id);
                } else {
                    //新闻
                    map.put("companyId", id);
                }
                return meApi.getCompanyDynamic(getHeadersMap(GET, "/lbs/gsGroup/newsPageList"), map);
            }

            @Override
            public void onSuccess(String o) {
                JSONObject resultObject = JSONUtil.toJsonObject(o);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                List<CircleDetailItemModel> list = GSONUtil.getEntityList(listArray.toString(), CircleDetailItemModel.class);
                getView().onDataList(isRefresh, list);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
                getView().onFishOrLoadMore(isRefresh);
            }
        });
    }

    /**
     * 成员动态
     *
     * @param isRefresh
     * @param groupId
     */
    private void getMemberDynamicList(final boolean isRefresh, final String groupId) {
        if (isRefresh) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                Map<String, Object> map = new HashMap<>();
                map.put("pageNum", pageIndex);
                map.put("pageSize", pageSize);
                map.put("groupId", groupId);
                return meApi.getMemberDynamic(getHeadersMap(GET, "/lbs/gsGroup/companyMemberDynamic"), map);
            }

            @Override
            public void onSuccess(String o) {
                JSONObject resultObject = JSONUtil.toJsonObject(o);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                List<CircleDetailItemModel> list = GSONUtil.getEntityList(listArray.toString(), CircleDetailItemModel.class);
                getView().onDataList(isRefresh, list);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
                getView().onFishOrLoadMore(isRefresh);
            }
        });
    }
}
