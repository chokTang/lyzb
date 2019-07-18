package com.lyzb.jbx.mvp.presenter.statistics;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.model.statistics.AnalyVisitCompanyModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.statistics.IAnalysisVisitCompanyView;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/23 14:17
 */

public class AnalysisVisitCompanyPresenter extends APPresenter<IAnalysisVisitCompanyView> {


    public void getVisitCompanyData(final int dayNum, final String departmentId) {
        onRequestData(true, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {

                Map<String, Object> map = new HashMap<>();
                map.put("dayNum", dayNum);
                map.put("departmentId", departmentId);
                return statisticsApi.getAnalysisVisitCompany(getHeadersMap(GET, "/lbs/gs/user/selectCurrentView"), map);
            }

            @Override
            public void onSuccess(String s) {
                AnalyVisitCompanyModel b = GSONUtil.getEntity(s, AnalyVisitCompanyModel.class);
                if (b == null) {
                    getView().onNull();
                    return;
                }
                getView().onData(b);
            }


            @Override
            public void onFail(String message) {
                getView().onFail(message);
            }
        });
    }
}
