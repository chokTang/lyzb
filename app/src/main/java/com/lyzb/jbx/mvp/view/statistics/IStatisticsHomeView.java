package com.lyzb.jbx.mvp.view.statistics;

import com.lyzb.jbx.model.statistics.StatisticsHomeModel;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/16 15:09
 */

public interface IStatisticsHomeView {

    void onData(StatisticsHomeModel b);

    void onFail(String msg);

    void onNotData();
}
