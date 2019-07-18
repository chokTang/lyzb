package com.lyzb.jbx.mvp.view.dialog;

import com.lyzb.jbx.model.dynamic.DynamicLikeModel;
import com.lyzb.jbx.model.dynamic.DynamicScanModel;

import java.util.List;

public interface IScanAndLikeDView {
    void onSuccess(boolean isRefresh, DynamicLikeModel bean);

    void onScanResultList(boolean isRefresh, int total, List<DynamicScanModel> list);
}
