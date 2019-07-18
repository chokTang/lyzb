package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.dynamic.DynamicModel;
import com.lyzb.jbx.model.me.CollectModel;
import com.lyzb.jbx.mvp.view.dynamic.IBaseDynamicView;

import java.util.List;

/**
 * 智能卡片-动态
 * Created by shidengzhong on 2019/3/5.
 */

public interface ICdDynamicView extends IBaseDynamicView {

    void onDataList(boolean isRefresh, List<DynamicModel> dynamicModelList);

    void onFinshOrLoadMore(boolean isRefresh);
}
