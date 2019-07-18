package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.customerManage.CustomerManageModel;

/**
 * @author wyx
 * @role
 * @time 2019 2019/5/8 13:37
 */

public interface ICustomerManageView {

    void onLoadMore(CustomerManageModel b);

    void onReFresh(CustomerManageModel b);

    void onNotData();

    void onFail(String msg);
}
