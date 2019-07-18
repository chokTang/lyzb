package com.lyzb.jbx.mvp.view.statistics;

import com.lyzb.jbx.model.statistics.AnalysisNewUserModel;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/16 15:09
 */

public interface IAnalysisNewUserView {
    void onLoadMore(AnalysisNewUserModel b);

    void onRefresh(AnalysisNewUserModel b);

    void onNotData();

    void onFail(String msg);


}
