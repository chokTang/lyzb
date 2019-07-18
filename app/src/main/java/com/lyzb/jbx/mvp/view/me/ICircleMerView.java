package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.CircleMerberModel;

/**
 * 我的圈子-成员列表
 * Created by shidengzhong on 2019/3/5.
 */

public interface ICircleMerView {

    void getMebList(boolean isRefresh,CircleMerberModel model);

    void onExit();

    void onDiss();

    void onRemove(int position);

    void onGetCircleMemberNum(int number);
}
