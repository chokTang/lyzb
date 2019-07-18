package com.lyzb.jbx.mvp.view.me;

import com.lyzb.jbx.model.me.CardModel;
import com.lyzb.jbx.model.me.FuctionItemModel;

import java.util.List;

/**
 * 智能名片
 * Created by shidengzhong on 2019/3/5.
 */

public interface IMeView {

    void onMeData(CardModel model);

    void onFunctionList(List<FuctionItemModel> list);
}
