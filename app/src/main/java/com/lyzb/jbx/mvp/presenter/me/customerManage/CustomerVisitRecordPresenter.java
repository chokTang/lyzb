package com.lyzb.jbx.mvp.presenter.me.customerManage;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.model.cenum.AccessType;
import com.lyzb.jbx.model.me.customerManage.CustomerTrakRecordModel;
import com.lyzb.jbx.model.me.customerManage.CustomerVisitRecordModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.ICustomerVisitRecordView;


import io.reactivex.Observable;

/**
 * @author wyx
 * @role
 * @time 2019 2019/5/14 10:00
 */

public class CustomerVisitRecordPresenter extends APPresenter<ICustomerVisitRecordView> {


    private int pageIndex = 1;
    private int pageSize = 50;

    public void getVisitRecord(final boolean isRefresh, final String userId) {
        if (isRefresh) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }

        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return meApi.getAcsList(getHeadersMap(GET, "/lbs/gs/user/selectMyAccessUserVo"), pageIndex, pageSize, userId, 30,0,"0");
            }

            @Override
            public void onSuccess(String s) {
                CustomerVisitRecordModel b = GSONUtil.getEntity(s, CustomerVisitRecordModel.class);
                if (b == null || b.getList() == null || b.getList().size() < 1) {
                    getView().onNotData();
                    return;
                }
                if (isRefresh) {
                    getView().onReFreshSuccess(b.getList());
                } else {
                    getView().onLoadMoreSuccess(b.getList());
                }
            }

            @Override
            public void onFail(String message) {
                getView().onFail(message);
            }
        });
    }
}
