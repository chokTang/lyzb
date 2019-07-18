package com.lyzb.jbx.mvp.presenter.home;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.account.FunctionModel;
import com.lyzb.jbx.model.follow.FollowHomeModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.home.IHomeView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.Observable;

public class HomePresenter extends APPresenter<IHomeView> {

    public void getRecommentFunctionList() {
        onRequestData(false,new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return accountApi.getFunctionList(getHeadersMap(GET, "/lbs/gs/home/getEntrance"));
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "entranceList");
                int hasUserInfo = JSONUtil.get(resultObject, "hasUserInfo", 0);
                getView().onFunctionResult(GSONUtil.getEntityList(listArray.toString(), FunctionModel.class), hasUserInfo);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
                getView().onFunctionResult(new ArrayList<FunctionModel>(), 0);
            }
        });
    }

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
}
