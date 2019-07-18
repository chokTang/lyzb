package com.lyzb.jbx.mvp.view.me.basic;

import com.lyzb.jbx.model.me.basic.CardCilpModel;

import java.util.List;

public interface ICardCilpView {
    void onListResult(boolean isRefresh, int total, List<CardCilpModel> list);

    void onFinshOrLoadMore(boolean isRefresh);
}
