package com.lyzb.jbx.mvp.view.dynamic;

import com.lyzb.jbx.model.dynamic.DynamicModel;

import java.util.List;

public interface IBaseDynamicView {

    //完成刷新的时候
    void onFinshRefresh(boolean isfresh);

    //获取动态列表
    void onDynamicList(boolean isfresh, List<DynamicModel> list);

    //点赞结果
    void onZanResult(int position);

    //关注结果
    void onFollowItemResult(int position);
}
