package com.lyzb.jbx.mvp.presenter.statistics;

import android.text.TextUtils;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.model.statistics.AnalysisRankingModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.statistics.IAnalysisRankingView;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/24 16:31
 */

public class AnalysisRankingPresenter extends APPresenter<IAnalysisRankingView> {
    /**
     * @param departmentId 企业id
     * @param rankingType  排行榜类型(visit：访问排行； share：分享排行 ； newUser：引流排行 ； order：交易排行)
     * @param visitSort    访问排行、分享排行的排序（all：全部；ext：名片；goods：商品；topic：动态）。默认为 all
     * @param dayNum       时间维度 默认=1（1天=1；7天=7；30天=30）
     */
    public void getAnalysisRanking(final String departmentId, final String rankingType, final String visitSort, final int dayNum) {
        onRequestData(true, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> map = new HashMap<>();
                map.put("departmentId", departmentId);
                map.put("rankingType", rankingType);
                map.put("visitSort", visitSort);
                map.put("dayNum", dayNum);
                return statisticsApi.getAnalysisRanking(getHeadersMap(GET, "/lbs/gs/disStatistics/getStatisticsRankData"), map);
            }

            @Override
            public void onSuccess(String s) {
                AnalysisRankingModel b = GSONUtil.getEntity(s, AnalysisRankingModel.class);
                if (b == null || b.getCode() != 200 || b.getDataList() == null || b.getDataList().size() < 1) {
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
