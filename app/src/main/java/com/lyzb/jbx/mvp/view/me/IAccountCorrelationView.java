package com.lyzb.jbx.mvp.view.me;

import com.like.longshaolib.net.model.HttpResult;

/**
 * @author wyx
 * @role
 * @time 2019 2019/5/16 10:33
 */

public interface IAccountCorrelationView {

    void onBinDingSuccess();

    void onCheckBinDingSuccess(HttpResult<String> httpResult);

    void onRemoveBinDingSuccess();

    void onFail(String s);
}
