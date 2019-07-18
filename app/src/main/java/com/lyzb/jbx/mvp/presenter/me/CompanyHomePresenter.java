package com.lyzb.jbx.mvp.presenter.me;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.me.CompanyDetailModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.ICompanyHomeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class CompanyHomePresenter extends APPresenter<ICompanyHomeView> {

    /****
     * 获取 企业申请数量
     * @param comdId
     */
    public void getCompanyApplyNumber(final String comdId) {
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                Map<String, String> params = new HashMap<>();
                params.put("queryType", "count");
                params.put("companyId", comdId);
                return meApi.getApplyJoinAuditList(getHeadersMap(GET, "/lbs/gs/distributor/getApplyJoinAuditList"), params);
            }

            @Override
            public void onSuccess(String o) {
                JSONObject reslutObject = JSONUtil.toJsonObject(o);
                int number = JSONUtil.get(reslutObject, "applyCount", 0);
                getView().companyApplyNumber(number);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    /**
     * 获取企业
     * @param companyId
     */
    public void getCompanyDetail(final String companyId){
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                Map<String, String> params = new HashMap<>();
                params.put("companyId", companyId);
                return meApi.getCompanyDetail(getHeadersMap(GET, "/lbs/gs/distributor/getCompanyInfo"), params);
            }

            @Override
            public void onSuccess(String o) {
                try {
                    JSONObject object = new JSONObject(o);
                    CompanyDetailModel detailModel = GSONUtil.getEntity(object.toString(),CompanyDetailModel.class);
                    getView().getCompanyDetail(detailModel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
