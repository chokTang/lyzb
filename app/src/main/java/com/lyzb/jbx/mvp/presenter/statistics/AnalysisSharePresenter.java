package com.lyzb.jbx.mvp.presenter.statistics;

import android.text.TextUtils;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.model.statistics.AnalysisShareModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.statistics.IAnalysisShareView;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/22 10:27
 */

public class AnalysisSharePresenter extends APPresenter<IAnalysisShareView> {
    /**
     * 获取数据统计-分享分析数据
     *
     * @param departmentId
     * @param dayNum
     */
    public void getData(final String departmentId, final int dayNum) {
        onRequestData(true, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, String> map = new HashMap<>();
                map.put("dayNum", String.valueOf(dayNum));
                map.put("departmentId", departmentId);
                return statisticsApi.getAnalysisShare(getHeadersMap(GET, "/lbs/gs/user/selectMyShareNumVo"), map);
            }

            @Override
            public void onSuccess(String s) {
                AnalysisShareModel b = GSONUtil.getEntity(s, AnalysisShareModel.class);
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
