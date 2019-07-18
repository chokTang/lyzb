package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.ApplyListModel;
import com.lyzb.jbx.model.me.CompanyApplyListModel;

import java.util.List;

public interface ICompanyApplyListView {
    void onApplyList(boolean isRefresh, List<CompanyApplyListModel.DataBean.ListBean> list);

    void onFinshOrLoadMore(boolean isRefrsh);

    void onAudit(int auditState, int position);
}
