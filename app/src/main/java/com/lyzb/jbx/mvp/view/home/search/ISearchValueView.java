package com.lyzb.jbx.mvp.view.home.search;

import com.szy.yishopcustomer.ResponseModel.Search.SearchHintModel;

import java.util.List;

public interface ISearchValueView {
    void onSearchResultList(List<SearchHintModel> list);
}
