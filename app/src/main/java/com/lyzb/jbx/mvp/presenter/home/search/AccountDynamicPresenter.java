package com.lyzb.jbx.mvp.presenter.home.search;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.account.SearchAccountModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.home.search.IAccountDynamicView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class AccountDynamicPresenter extends APPresenter<IAccountDynamicView> {
    private int pageIndex = 1;
    private int pageSize = 10;

    public void getList(final boolean isfresh, final String value) {
        final String valueAgin = value == null ? "" : value;
        if (isfresh) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }

        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> params = new HashMap<>();
                params.put("msg", valueAgin);
                params.put("pageNum", pageIndex);
                params.put("pageSize", pageSize);
                return dynamicApi.getSearchAccountDynamic(getHeadersMap(GET, "/lbs/gs/home/searchGsUser"), params);
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                getView().onListResult(isfresh, GSONUtil.getEntityList(listArray.toString(), SearchAccountModel.class));
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
                getView().finshLoadData(isfresh);
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
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
