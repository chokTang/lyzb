package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.customerManage.CustomerTrakRecordModel;

/**
 * @author wyx
 * @role 客户跟进记录
 * @time 2019 2019/5/9 10:40
 */

public interface ICustomerTrackRecordView {

    void onReFreshSuccess(CustomerTrakRecordModel b);

    void onLoadMoreSuccess(CustomerTrakRecordModel b);

    void onNotData();

    void onFail(String msg);
}
