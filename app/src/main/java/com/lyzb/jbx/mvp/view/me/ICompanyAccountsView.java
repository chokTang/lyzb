package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.CompanyAccountsModel;

import java.util.List;

public interface ICompanyAccountsView {
    void onList(boolean isRefresh, List<CompanyAccountsModel.DataBean.ListBean> list);

    void onFinshOrLoadMore(boolean isRefrsh);

}
