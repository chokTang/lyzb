package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.customerManage.CustomerVisitRecordModel;

import java.util.List;

/**
 * @author wyx
 * @role
 * @time 2019 2019/5/14 10:01
 */

public interface ICustomerVisitRecordView {

    void onReFreshSuccess(List<CustomerVisitRecordModel.ListBean> list);

    void onLoadMoreSuccess(List<CustomerVisitRecordModel.ListBean> list);

    void onNotData();

    void onFail(String msg);
}
