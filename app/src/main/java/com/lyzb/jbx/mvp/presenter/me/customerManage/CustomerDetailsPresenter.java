package com.lyzb.jbx.mvp.presenter.me.customerManage;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.me.customerManage.CustomerInfoModel;
import com.lyzb.jbx.model.params.IdBody;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.ICustomerDetailsView;

import io.reactivex.Observable;

/**
 * @author wyx
 * @role
 * @time 2019 2019/5/9 9:57
 */

public class CustomerDetailsPresenter extends APPresenter<ICustomerDetailsView> {

    /**
     * 获取客户信息
     */
    public void getCustomerInfo(final String id) {
        onRequestData(new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return meApi.getCustomerUserInfo(getHeadersMap(GET, "/lbs/gsCustomers/selectById"), id);
            }

            @Override
            public void onSuccess(String s) {
                CustomerInfoModel b = GSONUtil.getEntity(s, CustomerInfoModel.class);

                if (b == null) {
                    getView().onFail("");
                    return;
                }
                getView().onData(b);
            }

            @Override
            public void onFail(String message) {
                getView().onFail(message);
            }
        });
    }

    /**
     * 删除客户
     */
    public void deleteCustomer(final String id) {
        onRequestData(new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return meApi.deleteCustomer(getHeadersMap(POST, "/lbs/gsCustomers/deleteCustomers"), new IdBody(id));
            }

            @Override
            public void onSuccess(String s) {
                String status = JSONUtil.get(JSONUtil.toJsonObject(s), "status", "0");

                if (status.equals("200")) {
                    getView().onDeteleSuccess();
                } else {
                    String msg = JSONUtil.get(JSONUtil.toJsonObject(s), "msg", "0");
                    getView().onDeteleFail(msg);
                }
            }

            @Override
            public void onFail(String message) {
                getView().onDeteleFail(message);
            }
        });
    }
}
