package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.customerManage.CustomerManageCompanyModel;

/**
 * @author wyx
 * @role
 * @time 2019 2019/5/8 13:37
 */

public interface ICustomerManageCompanyView {

    void onLoadMore(CustomerManageCompanyModel.DataBean b);

    void onReFresh(CustomerManageCompanyModel.DataBean b);

    void onNotData();

    void onFail(String msg);
}
