package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.dynamic.DynamicModel;
import com.lyzb.jbx.model.dynamic.RemoveModel;

import java.util.List;

/**
 * 我的发表-动态
 * Created by shidengzhong on 2019/3/5.
 */

public interface IPubDynamicView {


    void onDataList(boolean isRefresh, List<DynamicModel> dynamicModelList);

    void onFinshOrLoadMore(boolean isRefresh);

    void onRemoveResult(RemoveModel bean, int position);

    void onZanResult(int postion);
}
