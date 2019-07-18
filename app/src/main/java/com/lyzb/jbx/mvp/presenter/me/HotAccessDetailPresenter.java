package com.lyzb.jbx.mvp.presenter.me;

import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.IHotAccessDetailView;

import java.util.Arrays;

public class HotAccessDetailPresenter extends APPresenter<IHotAccessDetailView> {

    private int pageIndex =1;
    private int pageSize =10;

    public void getList(boolean isRefresh,String userId){
        if (isRefresh){
            pageIndex=1;
        }else {
            pageIndex++;
        }
        getView().onListResult(isRefresh, Arrays.asList("","",""));
    }

    public void settingInterAccount(String userId){
        getView().onSettingInterAccountResult();
    }
}
