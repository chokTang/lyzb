package com.lyzb.jbx.mvp.presenter.me.customerManage;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.model.me.customerManage.CustomerTrakRecordModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.ICustomerTrackRecordView;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author wyx
 * @role
 * @time 2019 2019/5/9 11:19
 */

public class CustomerTrackRecordPresenter extends APPresenter<ICustomerTrackRecordView> {

    private int pageNum = 1;
    private final int pageSize = 10;

    public void getData(final boolean isRefresh, final String customerId) {
        if (isRefresh) {
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
                map.put("customerId", customerId);
                return meApi.getCustomerTrackRecord(getHeadersMap(GET, "/lbs/gsCustomers/selectFollowPageInfo"), map);
            }

            @Override
            public void onSuccess(String s) {
                CustomerTrakRecordModel b = GSONUtil.getEntity(s, CustomerTrakRecordModel.class);
                if (b == null || b.getList() == null || b.getList().size() < 1) {
                    getView().onNotData();
                    return;
                }
                if (isRefresh) {
                    getView().onReFreshSuccess(b);
                } else {
                    getView().onLoadMoreSuccess(b);
                }
            }

            @Override
            public void onFail(String message) {
                getView().onFail(message);
            }
        });
    }
}
