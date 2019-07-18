package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.access.AccessMemberModel;

import java.util.List;

public interface IAccessDetailView {
    void onAccessResult(boolean isRefresh, int total,List<AccessMemberModel> list);

    void onInterAccountResult(int poistion);

    void onFinshOrLoadMore(boolean isRefresh);
}
