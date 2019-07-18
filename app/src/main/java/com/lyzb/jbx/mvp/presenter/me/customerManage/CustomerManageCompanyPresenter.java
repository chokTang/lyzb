package com.lyzb.jbx.mvp.presenter.me.customerManage;

import com.like.longshaolib.base.inter.IRequestListener;
import com.lyzb.jbx.model.me.customerManage.CustomerManageCompanyModel;
import com.lyzb.jbx.model.me.customerManage.CustomerManageModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.ICustomerManageCompanyView;
import com.lyzb.jbx.mvp.view.me.ICustomerManageView;
import com.szy.yishopcustomer.Util.json.GSONUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author wyx
 * @role 客户管理
 * @time 2019 2019/5/8 14:55
 */

public class CustomerManageCompanyPresenter extends APPresenter<ICustomerManageCompanyView> {
    private final int pageSize = 10;
    private int pageNum = 1;

    /**
     * 获取企业成员客户管理信息
     */
    public void getData(final boolean isReFresh, final String companyId) {
        if (isReFresh) {
            pageNum = 1;
        } else {
            pageNum++;
        }
        onRequestData(new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> map = new HashMap<>();
                map.put("pageNum", pageNum);
                map.put("pageSize", pageSize);
                map.put("companyId", companyId);
                return meApi.getCustomerManageCompany(getHeadersMap(GET, "/lbs/gs/distributor/getCompanyCustomer"), map);
            }

            @Override
            public void onSuccess(String s) {
                CustomerManageCompanyModel b = GSONUtil.getEntity(s, CustomerManageCompanyModel.class);
                if (b == null) {
                    getView().onNotData();
                    return;
                }
                if (!b.getStatus().equals("200")) {
                    getView().onFail(b.getMsg());
                    return;
                }
                if (b.getData() == null || b.getData().getList() == null || b.getData().getList().size() < 1) {
                    getView().onNotData();
                    return;
                }
                if (isReFresh) {
                    getView().onReFresh(b.getData());
                } else {
                    getView().onLoadMore(b.getData());
                }
            }

            @Override
            public void onFail(String message) {
                getView().onFail(message);
            }
        });
    }

}
