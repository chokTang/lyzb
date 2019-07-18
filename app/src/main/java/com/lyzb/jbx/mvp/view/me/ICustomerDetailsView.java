package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.customerManage.CustomerInfoModel;

/**
 * @author wyx
 * @role 客户详情
 * @time 2019 2019/5/9 9:54
 */

public interface ICustomerDetailsView {

    void onData(CustomerInfoModel b);

    void onFail(String msg);

    void onDeteleSuccess();

    void onDeteleFail(String msg);
}
