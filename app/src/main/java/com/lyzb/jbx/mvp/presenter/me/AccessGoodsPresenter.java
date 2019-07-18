package com.lyzb.jbx.mvp.presenter.me;

import android.text.TextUtils;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.model.cenum.DayEnum;
import com.lyzb.jbx.model.me.access.AccessGoodsDetailModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.IAccessGoodsView;
import com.szy.yishopcustomer.Util.App;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class AccessGoodsPresenter extends APPresenter<IAccessGoodsView> {

    private int pageSize = 10;
    private int pageIndex = 1;

    public void getGoodsList(final boolean isfresh, String id, String dayType) {
        if (isfresh) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }
        final String userId = TextUtils.isEmpty(id) ? App.getInstance().userId : id;
        if (dayType.equals(DayEnum.DAY_THIRTY.getValue())) {
            dayType = "30";
        } else if (dayType.equals(DayEnum.DAY_SEVEN.getValue())) {
            dayType = "7";
        } else if (dayType.equals(DayEnum.DAY_ZERO.getValue())) {
            dayType = "1";
        } else {
            dayType = "0";
        }
        final int dayNumber = Integer.parseInt(dayType);
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> params = new HashMap<>();
                params.put("type", dayNumber);
                params.put("userId", userId);
                return meApi.getAccessShop(getHeadersMap(GET, "/lbs/gs/home/getOrdersByCard"), params);
            }

            @Override
            public void onSuccess(String s) {
                AccessGoodsDetailModel detailModel = GSONUtil.getEntity(s, AccessGoodsDetailModel.class);
                getView().onGoodsListResult(isfresh, detailModel);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
