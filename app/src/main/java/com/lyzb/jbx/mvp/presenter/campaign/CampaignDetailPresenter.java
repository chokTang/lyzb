package com.lyzb.jbx.mvp.presenter.campaign;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.campagin.CampaginDetailModel;
import com.lyzb.jbx.model.params.CampaignColloectBody;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.campaign.IcampaignDetailView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class CampaignDetailPresenter extends APPresenter<IcampaignDetailView> {

    /**
     * 获取活动详情
     *
     * @param id
     */
    public void getCampaignDetail(final String id) {
        onRequestData(new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return campaginApi.getCampaginDetail(getHeadersMap(GET, "/lbs/activity/selectById"), id);
            }

            @Override
            public void onSuccess(String s) {
                getView().onCampaignDetail(GSONUtil.getEntity(s, CampaginDetailModel.class));
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    /**
     * 收藏活动
     *
     * @param id
     */
    public void onCollectionCampaign(final String id) {
        onRequestData(new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return campaginApi.collectionCampaign(getHeadersMap(POST, "/lbs/participant/collectAddOrUpdate"), new CampaignColloectBody(id));
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                if ("200".equals(JSONUtil.get(resultObject, "status", ""))) {
                    getView().onCollectionResultScuess();
                } else {
                    showFragmentToast("收藏失败");
                }
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }


    /**
     * 取消收藏
     *
     *
     * @param id
     */
    public void onCancleCollectionCampaign(final String id) {
        onRequestData(new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return campaginApi.cancleCollectionCampaign(getHeadersMap(POST, "/lbs/participant/collectDelete"), new CampaignColloectBody(id));
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                if ("200".equals(JSONUtil.get(resultObject, "status", ""))) {
                    getView().onDeleteCollectionResultScuess();
                } else {
                    showFragmentToast("取消收藏失败");
                }
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    /**
     * 分享活动
     *
     * @param id
     */
    public void onShareCampaign(final String id) {
        onRequestData(new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return campaginApi.shareCampaign(getHeadersMap(POST, "/lbs/participant/shareAddOrUpdate"), id);
            }

            @Override
            public void onSuccess(String s) {
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
     * @param isFollow 1 关注 0 取消关注
     */
    public void onFollowUser(final String userId, final int isFollow) {
        onRequestData(new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                Map<String, Object> params = new HashMap<>();
                params.put("toUserId", userId);
                params.put("enabled", isFollow);
                return meApi.onCardFollow(getHeadersMap(POST, "/lbs/gs/user/saveUsersRelation"), params);
            }

            @Override
            public void onSuccess(String s) {
                getView().onCardFollowSuccess();
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
