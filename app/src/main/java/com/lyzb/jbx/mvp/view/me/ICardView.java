package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.CardModel;
import com.lyzb.jbx.model.me.TabShowHideBean;

import java.util.List;

/**
 * 智能名片
 * Created by shidengzhong on 2019/3/5.
 */

public interface ICardView {

    void onCardData(CardModel model);

    void doLike(boolean isLike);

    void doFocus(boolean isFocus);

    void getTabList(List<TabShowHideBean> modelList);
    void saveSucess(List<TabShowHideBean> modelList);
}
