package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.circle.CircleDetailItemModel;
import com.lyzb.jbx.model.me.CircleDetModel;
import com.lyzb.jbx.mvp.view.dynamic.IBaseDynamicView;

import java.util.List;

/**
 * 圈子详情
 * Created by shidengzhong on 2019/3/5.
 */

public interface ICircleDynamictView extends IBaseDynamicView {

    void onDataList(boolean isRefresh, List<CircleDetailItemModel> dynamicModelList);

    void onFishOrLoadMore(boolean isfresh);
}
