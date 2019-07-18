package com.lyzb.jbx.mvp.view.me;

import java.util.List;

public interface IHotAccessDetailView {
    void onSettingInterAccountResult();

    void finshRefreshOrLoadMore(boolean isRefresh);

    void onListResult(boolean isRefresh, List<String> list);
}
