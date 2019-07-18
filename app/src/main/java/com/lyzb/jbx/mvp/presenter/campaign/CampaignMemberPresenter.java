package com.lyzb.jbx.mvp.presenter.campaign;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.campagin.CampaginUserListModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.campaign.ICampaignMemberView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class CampaignMemberPresenter extends APPresenter<ICampaignMemberView> {
    private int pageSize = 10;
    private int pageIndex = 1;

    /**
     * 获取活动的成员列表
     *
     * @param isfresh
     * @param campaignId
     */
    public void getMemberList(final boolean isfresh, final String campaignId) {
        if (isfresh) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                Map<String, Object> params = new HashMap<>();
                params.put("activityId", campaignId);
                params.put("pageNum", pageIndex);
                params.put("pageSize", pageSize);
                return campaginApi.getCampaginMember(getHeadersMap(GET, "/lbs/participant/selectByPageList"), params);
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                int tatol = JSONUtil.get(resultObject, "total", 0);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                List<CampaginUserListModel> list = GSONUtil.getEntityList(listArray.toString(), CampaginUserListModel.class);
                getView().onCampaignMember(isfresh, tatol, list);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
