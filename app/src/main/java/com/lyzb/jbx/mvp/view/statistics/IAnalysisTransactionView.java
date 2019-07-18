package com.lyzb.jbx.mvp.view.statistics;

import com.lyzb.jbx.model.statistics.AnalysisTransactionModel;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/23 11:19
 */

public interface IAnalysisTransactionView {

    void onData(AnalysisTransactionModel b);

    void onFile(String msg);

    void onNotData();

}
