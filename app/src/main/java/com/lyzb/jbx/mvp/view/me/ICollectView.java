package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.CollectModel;
import com.lyzb.jbx.mvp.view.dynamic.IBaseDynamicView;

import java.util.List;

/**
 * 我的发表-回复
 * Created by shidengzhong on 2019/3/5.
 */

public interface ICollectView extends IBaseDynamicView {
    //获取动态列表
    void onCollectList(boolean isfresh, List<CollectModel> list);
}
