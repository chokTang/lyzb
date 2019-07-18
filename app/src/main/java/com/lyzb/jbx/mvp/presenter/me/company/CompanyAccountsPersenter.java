package com.lyzb.jbx.mvp.presenter.me.company;

import com.like.longshaolib.base.inter.IRequestListener;
import com.lyzb.jbx.model.me.CompanyAccountsModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.ICompanyAccountsView;
import com.szy.yishopcustomer.Util.json.GSONUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class CompanyAccountsPersenter extends APPresenter<ICompanyAccountsView> {
    private int pageIndex = 1;
    private int pageSize = 15;

    public void getList(final boolean isRefrsh, final String companyId) {
        if (isRefrsh) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                Map<String, String> map = new HashMap<>();
                map.put("pageNum", String.valueOf(pageIndex));
                map.put("pageSize", String.valueOf(pageSize));
                map.put("companyId", companyId);
                return meApi.getCompanyAccounts(getHeadersMap(GET, "/lbs/gs/distributor/getCompanyAccounts"), map);
            }

            @Override
            public void onSuccess(String o) {
                CompanyAccountsModel listModel = GSONUtil.getEntity(o, CompanyAccountsModel.class);
                if ("200".equals(listModel.getStatus()) || "202".equals(listModel.getStatus())) {
                    getView().onList(isRefrsh, listModel.getData().getList());
                } else {
                    getView().onFinshOrLoadMore(isRefrsh);
                    showFragmentToast(listModel.getMsg());
                }

            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
                getView().onFinshOrLoadMore(isRefrsh);
            }
        });
    }
}
