package com.lyzb.jbx.mvp.presenter.home.first;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.common.RecommentBannerModel;
import com.lyzb.jbx.model.dynamic.DynamicModel;
import com.lyzb.jbx.model.follow.InterestMemberModel;
import com.lyzb.jbx.mvp.presenter.dynamic.BaseDynamicPresenter;
import com.lyzb.jbx.mvp.view.home.first.IHomeRecommendView;
import com.szy.yishopcustomer.Util.App;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class HomeRecommendPresenter extends BaseDynamicPresenter<IHomeRecommendView> {

    private int pageIndex = 1;

    public void getInterestList(boolean isRefresh) {
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
                params.put("pageSize", 5);
                return followApi.getMeInterest(getHeadersMap(GET, "/lbs/gs/home/maybeInterest"), params);
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                getView().onInterestMember(GSONUtil.getEntityList(listArray.toString(), InterestMemberModel.class));
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }


    /**
     * 获取banner数据
     */
    public void getBannerList() {
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return accountApi.getRecommentBanner(getHeadersMap(GET, "/lbs/user/advertByPosition"), "A9");
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                getView().onBannerList(GSONUtil.getEntityList(listArray.toString(), RecommentBannerModel.class));
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    @Override
    public void getFollowDynamicList(final boolean isRefresh) {
        if (isRefresh) {
            pageIndexDynamic = 1;
        } else {
            pageIndexDynamic++;
        }
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> params = new HashMap<>();
                params.put("pageNum", pageIndexDynamic);
                params.put("pageSize", pageSize);
                params.put("userId", App.getInstance().userId==null?"":App.getInstance().userId);
                return commonApi.getRecomendDynamicList(params);
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                getView().onDynamicList(isRefresh, GSONUtil.getEntityList(listArray.toString(), DynamicModel.class));
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
                getView().onFinshRefresh(isRefresh);
            }
        });
    }
}
