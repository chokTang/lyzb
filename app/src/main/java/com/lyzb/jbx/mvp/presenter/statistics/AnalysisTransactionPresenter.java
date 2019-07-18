package com.lyzb.jbx.mvp.presenter.statistics;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.model.statistics.AnalysisTransactionModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.statistics.IAnalysisTransactionView;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/23 11:19
 */

public class AnalysisTransactionPresenter extends APPresenter<IAnalysisTransactionView> {
    /**
     * 获取企业名片商城交易记录
     *
     * @param departmentId
     * @param dayNum
     */
    public void getAnalysisTeansactionCompany(final String departmentId, final int dayNum) {
        onRequestData(true, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> map = new HashMap<>();
                map.put("departmentId", departmentId);
                map.put("dayNum", dayNum);
                return statisticsApi.getAnalysisTransactionCompany(getHeadersMap(GET, "/lbs/gs/disStatistics/getStatisticsOrderData"), map);
            }

            @Override
            public void onSuccess(String s) {
                AnalysisTransactionModel b = GSONUtil.getEntity(s, AnalysisTransactionModel.class);
                if (b == null) {
                    getView().onNotData();
                    return;
                }
                if (b.getCode() != 200) {
                    showFragmentToast(b.getMsg());
                    getView().onNotData();
                    return;
                }
                if (b.getOrderByDay() == null || b.getOrderByDay().size() < 1) {
                    getView().onNotData();
                    return;
                }
                getView().onData(b);
            }

            @Override
            public void onFail(String message) {
                getView().onFile(message);
            }
        });
    }

    /**
     * 获取用户名片商城交易记录
     *
     * @param userId
     * @param type
     */

    public void getAnalysisTeansactionUser(final String userId, final int type) {
        onRequestData(true, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> map = new HashMap<>();
                map.put("departmentId", userId);
                map.put("dayNum", type);
                return statisticsApi.getAnalysisTransactionUser(getHeadersMap(GET, "/lbs/gs/home/getOrdersByCard"), map);
            }

            @Override
            public void onSuccess(String s) {
                AnalysisTransactionModel b = GSONUtil.getEntity(s, AnalysisTransactionModel.class);
                if (b == null) {
                    getView().onNotData();
                    return;
                }
                if (b.getCode() != 200) {
                    showFragmentToast(b.getMsg());
                    getView().onNotData();
                    return;
                }
                if (b.getOrderByDay() == null || b.getOrderByDay().size() < 1) {
                    getView().onNotData();
                    return;
                }
                getView().onData(b);
            }

            @Override
            public void onFail(String message) {
                getView().onFile(message);
            }
        });
    }
}
