package com.lyzb.jbx.mvp.presenter.statistics;

import android.text.TextUtils;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.model.statistics.AnalyVisitCompanyModel;
import com.lyzb.jbx.model.statistics.AnalysisVisitUserModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.statistics.IAnalysisVisitUserView;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/23 14:17
 */

public class AnalysisVisitUserPresenter extends APPresenter<IAnalysisVisitUserView> {


    public void getVisitData(final int dayNum, final String userId) {
        onRequestData(true, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {

                Map<String, String> map = new HashMap<>();
                map.put("dayNum", String.valueOf(dayNum));
                map.put("userId", userId);
                return statisticsApi.getAnalysisVisitUser(getHeadersMap(GET, "/lbs/gs/user/getUserTrack"), map);
            }

            @Override
            public void onSuccess(String s) {
                AnalysisVisitUserModel b = GSONUtil.getEntity(s, AnalysisVisitUserModel.class);
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
