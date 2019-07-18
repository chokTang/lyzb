package com.lyzb.jbx.mvp.presenter.me.customerManage;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.params.CustomerAddTrackBody;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.ICustomerAddTrackView;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author wyx
 * @role 添加跟进
 * @time 2019 2019/5/10 16:55
 */

public class CustomerAddTrackPresenter extends APPresenter<ICustomerAddTrackView> {

    public void addTrack(final String customerId, final String content) {
        onRequestData(new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                CustomerAddTrackBody body = new CustomerAddTrackBody();
                body.setContent(content);
                body.setCustomerId(customerId);
                return meApi.addTrack(getHeadersMap(POST, "/lbs/gsCustomers/addOrUpdateCustomersFollow"), body);
            }

            @Override
            public void onSuccess(String s) {
                String status = JSONUtil.get(JSONUtil.toJsonObject(s), "status", "0");
                if (status.equals("200")) {
                    getView().onSuccess();
                } else {
                    String msg = JSONUtil.get(JSONUtil.toJsonObject(s), "status", "");
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
