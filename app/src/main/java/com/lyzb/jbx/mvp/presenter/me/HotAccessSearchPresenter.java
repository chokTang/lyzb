package com.lyzb.jbx.mvp.presenter.me;

import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.IHotAccessSearchView;

public class HotAccessSearchPresenter extends APPresenter<IHotAccessSearchView> {

    private int pageSize =10;
    private int pageIndex =1;

    public void search(boolean isRefresh,String startTime,String endTime){
        if (isRefresh){
            pageIndex=1;
        }else {
            pageIndex++;
        }
    }
}
