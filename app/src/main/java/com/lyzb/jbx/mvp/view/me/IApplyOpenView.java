package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.ComdDetailModel;

public interface IApplyOpenView {
    void onApplyResultSuccess();

    void onCompanyInfoResult(ComdDetailModel.GsDistributorVoBean model);
}
