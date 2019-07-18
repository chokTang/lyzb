package com.lyzb.jbx.mvp.view.home.search;

import com.szy.yishopcustomer.ResponseModel.GoodsModel;

import java.util.List;

public interface ISearchGoodsView {
    void finshLoadData(boolean isRefrsh);

    void onGoodsListResult(boolean isRefrsh, List<GoodsModel> list);
}
