package com.lyzb.jbx.mvp.presenter.me;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.me.CardModel;
import com.lyzb.jbx.model.me.FuctionItemModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.IMeView;
import com.lyzb.jbx.util.AppPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author wyx
 * @role 智能名片
 * @time 2019 2019/3/14 14:01
 */

public class MePresenter extends APPresenter<IMeView> {

    public void getData() {
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return meApi.getCardData(getHeadersMap(GET, "/lbs/gs/user/getMyGaUserExtVoById"));
            }

            @Override
            public void onSuccess(String s) {
                try {
                    CardModel modelList = GSONUtil.getEntity(s, CardModel.class);
                    AppPreference.getIntance().setUserShowInfo("1".equals(modelList.getShowInfo()));
                    AppPreference.getIntance().setUserIsVip(modelList.getUserVipAction().size() > 0);
                    getView().onMeData(modelList);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    /**
     * 获取功能模块数据
     */
    public void getFunctionData() {
        onRequestData(true, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                Map<String, Object> params = new HashMap<>();
                params.put("type", 1);
                return meApi.getMeFunctionList(getHeadersMap(GET, "/lbs/gs/distributor/getRoleListByUser"), params);
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject =JSONUtil.toJsonObject(s);
                JSONArray reultArray = JSONUtil.getJsonArray(resultObject,"roleList");
                if (reultArray.length() > 0) {
                    getView().onFunctionList(GSONUtil.getEntityList(JSONUtil.getJsonArray(JSONUtil.getJsonObject(reultArray, 0), "childResource").toString(), FuctionItemModel.class));
                } else {
                    showFragmentToast("数据异常");
                }
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
