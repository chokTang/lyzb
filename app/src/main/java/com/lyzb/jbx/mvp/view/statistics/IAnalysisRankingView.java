package com.lyzb.jbx.mvp.view.statistics;

import com.lyzb.jbx.model.statistics.AnalysisRankingModel;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/24 16:32
 */

public interface IAnalysisRankingView {

    void onData(AnalysisRankingModel b);

    void onFail(String msg);

    void onNull();

}
