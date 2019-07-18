package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.access.AccessNewAccountModel;

import java.util.List;

public interface IAccessNewAccountView {
    void onAccountResultList(boolean isRefresh, int total, List<AccessNewAccountModel> list);
}
