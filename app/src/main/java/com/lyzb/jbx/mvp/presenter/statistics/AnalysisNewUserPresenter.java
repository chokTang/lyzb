package com.lyzb.jbx.mvp.presenter.statistics;

import com.like.longshaolib.base.inter.IRequestListener;
import com.lyzb.jbx.model.statistics.AnalysisNewUserModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.statistics.IAnalysisNewUserView;
import com.szy.yishopcustomer.Util.json.GSONUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/23 17:54
 */

public class AnalysisNewUserPresenter extends APPresenter<IAnalysisNewUserView> {

    public void getAnalysisNewUser_Company(final String departmentId, final int dayNum) {
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> map = new HashMap<>();
                map.put("departmentId", departmentId);
                map.put("dayNum", dayNum);
                return statisticsApi.getAnalysisNewUserCompany(getHeadersMap(GET, "/lbs/gs/disStatistics/getStatisticsNewUserData"), map);
            }

            @Override
            public void onSuccess(String s) {
                AnalysisNewUserModel b = GSONUtil.getEntity(s, AnalysisNewUserModel.class);
                if (b == null || !b.getCode().equals("200")) {
                    getView().onNotData();
                    return;
                }
                if (b.getDataList() == null || b.getDataList().size() < 1) {
                    getView().onNotData();
                    return;
                }
                getView().onRefresh(b);

            }

            @Override
            public void onFail(String message) {
                getView().onFail(message);
            }
        });
    }

    private int pageNum = 1;
    private final int pageSize = 10;

    public void getAnalysisNewUser_User(final boolean isRefresh, final int days, final String shareUserId) {
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                if (isRefresh) {
                    pageNum = 1;
                } else {
                    pageNum++;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("pageNum", pageNum);
                map.put("pageSize", pageSize);
                map.put("days", days);
                map.put("shareUserId", shareUserId);
                return statisticsApi.getAnalysisNewUserUser(getHeadersMap(GET, "/lbs/gsUserBelong/selectByPageInfo"), map);
            }

            @Override
            public void onSuccess(String s) {
                AnalysisNewUserModel b = GSONUtil.getEntity(s, AnalysisNewUserModel.class);
                if (!isRefresh) {
                    getView().onLoadMore(b);
                    return;
                }
                if (b == null) {
                    getView().onNotData();
                    return;
                }
                if (b.getDataList() == null || b.getDataList().size() < 1) {
                    getView().onNotData();
                    return;
                }
                getView().onRefresh(b);
            }

            @Override
            public void onFail(String message) {
                getView().onFail(message);
            }
        });
    }
}
