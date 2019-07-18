package com.lyzb.jbx.mvp.presenter.circle;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.model.circle.CircleModel;
import com.lyzb.jbx.model.dynamic.DynamicModel;
import com.lyzb.jbx.mvp.presenter.dynamic.BaseDynamicPresenter;
import com.lyzb.jbx.mvp.view.circle.IHomeCircleView;
import com.szy.yishopcustomer.Util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class HomeCirclePresenter extends BaseDynamicPresenter<IHomeCircleView> {

    /**
     * 获取我加入的圈子和推荐的圈子
     */
    public void getMyInjoinCirlce() {
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return circleApi.getHomeMyCircle(getHeadersMap(GET, "/lbs/gs/home/getRecommendGroup"));
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                List<CircleModel> list = GSONUtil.getEntityList(listArray.toString(), CircleModel.class);
                getView().onMyCircleList(list);
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
                return circleApi.getCircleDynamic(getHeadersMap(GET, "/lbs/gs/home/myGroupTopic"), params);
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = com.like.utilslib.json.JSONUtil.toJsonObject(s);
                JSONArray listArray = com.like.utilslib.json.JSONUtil.getJsonArray(resultObject, "list");
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
