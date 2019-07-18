package com.lyzb.jbx.mvp.presenter.me;

import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.IHotAccessView;

import java.util.Arrays;

public class HotAccessPresenter extends APPresenter<IHotAccessView> {
    private int pageIndex =1;
    private int pageSize =10;

    public void  getListData(boolean isRefresh){
        if (isRefresh){
            pageIndex=1;
        }else {
            pageIndex++;
        }
        getView().onListResult(isRefresh, Arrays.asList("","",""));
    }
}
