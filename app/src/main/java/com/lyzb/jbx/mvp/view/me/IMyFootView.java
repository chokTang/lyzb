package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.MeFootModel;

import java.util.List;

public interface IMyFootView {
    void onFootListResult(boolean isRefresh, List<MeFootModel> list);

    void onClearFootSuccess();
}
