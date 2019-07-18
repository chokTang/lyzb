package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.access.AccessGoodsDetailModel;

public interface IAccessGoodsView {
    void onGoodsListResult(boolean isRefresh, AccessGoodsDetailModel model);
}
