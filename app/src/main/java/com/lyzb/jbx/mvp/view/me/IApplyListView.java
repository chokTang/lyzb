package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.ApplyListModel;

import java.util.List;

/**
 * 我的圈子-成员申请列表
 * Created by shidengzhong on 2019/3/5.
 */

public interface IApplyListView {

    void onApplyList(boolean isRefresh, List<ApplyListModel.ListBean> list);

    void onFinshOrLoadMore(boolean isRefrsh);

    void onAgreed(int position);

    void onRefuse(int position);
}
