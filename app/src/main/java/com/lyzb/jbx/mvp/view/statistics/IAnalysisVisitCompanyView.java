package com.lyzb.jbx.mvp.view.statistics;

import com.lyzb.jbx.model.statistics.AnalyVisitCompanyModel;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/22 10:29
 */

public interface IAnalysisVisitCompanyView {

    void onData(AnalyVisitCompanyModel b);

    void onFail(String msg);

    void onNull();
}
