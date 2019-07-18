package com.lyzb.jbx.mvp.presenter.statistics;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.model.statistics.StatisticsHomeModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.statistics.IStatisticsHomeView;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author wyx
 * @role 数据统计主页
 * @time 2019 2019/4/16 15:09
 */

public class StatisticsHomePresenter extends APPresenter<IStatisticsHomeView> {

    /**
     * 获取统计数据
     * dataType   30=30天，7=7天，1=今天
     * screenType 排序（1.按访问次数-默认 2.按分享次数 3.按新用户人数 4.按交易金额）默认为 1
     */
    public void getData(final int dayNum, final int sortType, final String departmentId) {
        onRequestData(true, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, String> map = new HashMap<>();
                map.put("departmentId", departmentId);
                map.put("dayNum", String.valueOf(dayNum));
                map.put("sortType", String.valueOf(sortType));
                return statisticsApi.getStatistics(getHeadersMap(GET, "/lbs/gs/disStatistics/getAllStatisticsData"), map);
            }

            @Override
            public void onSuccess(String s) {
                StatisticsHomeModel b = GSONUtil.getEntity(s, StatisticsHomeModel.class);
                if (b == null) {
                    getView().onNotData();
                }
                if (b.getCode() != 200) {
                    getView().onFail(b.getMsg());
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
