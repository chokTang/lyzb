package com.lyzb.jbx.mvp.presenter.home.first;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.campagin.CampaignModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.home.first.IHomeCampaignView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class HomeCampaignPresenter extends APPresenter<IHomeCampaignView> {

    private int pageIndex = 1;
    private int pageSize = 10;

    /**
     * 获取活动列表
     *
     * @param isRefresh
     */
    public void getList(final boolean isRefresh) {
        if (isRefresh) {
            pageIndex = 1;
        } else {
            pageSize++;
        }
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> params = new HashMap<>();
                params.put("pageNum", pageIndex);
                params.put("pageSize", pageSize);
                params.put("title", "");
                return campaginApi.getHomeCampaginList(getHeadersMap(GET, "/lbs/activity/selectByPageList"), params);
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                List<CampaignModel> list = GSONUtil.getEntityList(listArray.toString(), CampaignModel.class);
                getView().onGetCampaignResult(isRefresh, list);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
