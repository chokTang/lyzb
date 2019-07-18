package com.lyzb.jbx.mvp.presenter.home.first;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.dynamic.AddLikeOrFollowBody;
import com.lyzb.jbx.model.dynamic.DynamicModel;
import com.lyzb.jbx.model.follow.FollowAdoutUserModel;
import com.lyzb.jbx.model.follow.FollowHomeModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.presenter.dynamic.BaseDynamicPresenter;
import com.lyzb.jbx.mvp.view.home.first.IHomeFollowView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class HomeFollowPresenter extends BaseDynamicPresenter<IHomeFollowView> {

    private int pageIndexUser = 1;
    private int pageSizeUser = 5;

    /**
     * 获取我的粉丝列表（包含了我关注的人 和 关注我的人信息）
     */
    public void getMyFollowList() {
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return followApi.getMyFollowList(getHeadersMap(GET, "/lbs/gs/home/getSimpleRelation"));
            }

            @Override
            public void onSuccess(String s) {
                getView().onFollowListReuslt(GSONUtil.getEntity(s, FollowHomeModel.class));
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    /**
     * 获取与我相关的人信息
     */
    public void getAdoutMeUserList(boolean isRefresh) {
        if (isRefresh) {
            pageIndexUser = 1;
        } else {
            pageIndexUser++;
        }
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> params = new HashMap<>();
                params.put("pageNum", pageIndexUser);
                params.put("pageSize", pageSizeUser);
                return followApi.getAdoutMeList(getHeadersMap(GET, "/lbs/gs/home/maybeKnow"), params);
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                getView().onFollowAdoutMeList(GSONUtil.getEntityList(listArray.toString(), FollowAdoutUserModel.class));
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    /**
     * 关注某人
     *
     * @param userId
     */
    public void onFollowUser(final String userId) {
        onRequestData(new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> params = new HashMap<>();
                params.put("enabled", 1);
                params.put("toUserId", userId);
                return meApi.onCardFollow(getHeadersMap(GET, "/lbs/gs/user/saveUsersRelation"), params);
            }

            @Override
            public void onSuccess(String s) {
                getAdoutMeUserList(true);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
