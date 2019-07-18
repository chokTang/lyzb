package com.lyzb.jbx.mvp.presenter.me.customerManage;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.me.customerManage.CustomerInfoModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.ICustomerInfoModifyView;

import io.reactivex.Observable;

/**
 * @author wyx
 * @role 修改客户备注信息
 * @time 2019 2019/5/13 15:52
 */

public class CustomerInfoModifyPresenter extends APPresenter<ICustomerInfoModifyView> {


    public void modifyCustomerInfo(final CustomerInfoModel body) {
        onRequestData(new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return meApi.addCustomer(getHeadersMap(POST, "/lbs/gsCustomers/addOrUpdateCustomers"), body);
            }

            @Override
            public void onSuccess(String s) {
                String status = JSONUtil.get(JSONUtil.toJsonObject(s), "status", "0");
                if (status.equals("200")) {
                    getView().onSuccess();
                } else {
                    String msg = JSONUtil.get(JSONUtil.toJsonObject(s), "msg", "0");
                    getView().onFail(msg);
                }
            }

            @Override
            public void onFail(String message) {
                getView().onFail(message);
            }
        });
    }
}
