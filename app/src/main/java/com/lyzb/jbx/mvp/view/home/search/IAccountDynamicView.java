package com.lyzb.jbx.mvp.view.home.search;

import com.lyzb.jbx.model.account.SearchAccountModel;

import java.util.List;

public interface IAccountDynamicView {

    void finshLoadData(boolean isrefresh);

    void onListResult(boolean isRefrsh, List<SearchAccountModel> list);

    void onFollowItemResult(int position);
}
