package com.lyzb.jbx.mvp.view.me;

import java.util.List;

public interface IHotAccessView {
    void onRefreshOrLoadMore(boolean isRefresh);

    void onListResult(boolean isRefresh, List<String> list);
}
