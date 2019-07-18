package com.lyzb.jbx.mvp.view.me;


import com.lyzb.jbx.model.me.CompanyMembersModel;

import java.util.List;

public interface ICompanyMembersView {

    void onList(boolean isRefresh,CompanyMembersModel model);

    void onFinshOrLoadMore(boolean isRefrsh);

    void onRemove(int position);

}
