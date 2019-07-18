package com.lyzb.jbx.mvp.presenter.dynamic;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.dynamic.AddLikeOrFollowBody;
import com.lyzb.jbx.model.dynamic.DynamicModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.dynamic.IBaseDynamicView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class BaseDynamicPresenter<V extends IBaseDynamicView> extends APPresenter<V> {

    protected int pageSize = 10;
    protected int pageIndexDynamic = 1;

    /**
     * 获取动态列表
     *
     * @param isRefresh
     */
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
                return dynamicApi.getFollowDynamicList(getHeadersMap(GET, "/lbs/gs/home/mySubscribersList"), params);
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

    /**
     * 关注某人 动态里面关注某人
     *
     * @param userId
     * @param enabled 1 关注 0 取消关注
     */
    public void onDynamciFollowUser(final String userId, final int enabled, final int position) {
        onRequestData(new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> params = new HashMap<>();
                params.put("enabled", enabled);
                params.put("toUserId", userId);
                return meApi.onCardFollow(getHeadersMap(GET, "/lbs/gs/user/saveUsersRelation"), params);
            }

            @Override
            public void onSuccess(String s) {
                getView().onFollowItemResult(position);
                if (enabled==1){
                    showFragmentToast("关注成功");
                }else {
                    showFragmentToast("已取消关注");
                }
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    /**
     * 点赞
     *
     * @param dynamicId
     */
    public void onZan(final String dynamicId, final int position) {
        onRequestData(false,new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return dynamicApi.onAddLikeOrFollow(getHeadersMap(POST, "/lbs/gs/topic/saveGsTopicFavour"),
                        new AddLikeOrFollowBody(dynamicId, "2"));
            }

            @Override
            public void onSuccess(String s) {
                getView().onZanResult(position);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    /**
     * 取消点赞
     *
     * @param dynamicId
     */
    public void onCancleZan(final String dynamicId, final int position) {
        onRequestData(false,new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return dynamicApi.onCancelLikeOrFollow(getHeadersMap(POST, "/lbs/gs/topic/delGsTopicFavour"),
                        new AddLikeOrFollowBody(dynamicId, "2"));
            }

            @Override
            public void onSuccess(String s) {
                getView().onZanResult(position);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
