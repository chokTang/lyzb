package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.CompanyAccountDetailModel;

public interface IEditCompanyAccountView {
    void onApplyData(CompanyAccountDetailModel.DetailDataBean dataBean);

    void onUpDataAccount();

    void onUpDataPassword();

    void onSendMSMSuccess();

    void onValidateMobileCodeSuccess(String status);

    void onPhoneStatus(String status, String msg);
}
