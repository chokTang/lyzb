package com.lyzb.jbx.mvp.presenter.me.customerManage;

import android.text.TextUtils;

import com.like.longshaolib.base.inter.IRequestListener;
import com.lyzb.jbx.model.me.customerManage.CustomerManageModel;
import com.lyzb.jbx.mvp.APPresenter;
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

public class CustomerManagePresenter extends APPresenter<ICustomerManageView> {

    private final int pageSize = 10;
    private int pageNum = 1;

    /**
     * 获取客户管理信息
     */
    public void getData(final boolean isReFresh, final String addUserId, final String remarkName) {
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
                map.put("remarkName", remarkName);
                if (!TextUtils.isEmpty(addUserId)) {
                    map.put("addUserId", addUserId);
                }
                return meApi.getCustomerManage(getHeadersMap(GET, "/lbs/gsCustomers/selectByPageInfo"), map);
            }

            @Override
            public void onSuccess(String s) {
                CustomerManageModel b = GSONUtil.getEntity(s, CustomerManageModel.class);
                if (b == null || b.getList() == null) {
                    getView().onNotData();
                    return;
                }
                if (isReFresh) {
                    getView().onReFresh(b);
                } else {
                    getView().onLoadMore(b);
                }
            }

            @Override
            public void onFail(String message) {
                getView().onFail(message);
            }
        });
    }

}
