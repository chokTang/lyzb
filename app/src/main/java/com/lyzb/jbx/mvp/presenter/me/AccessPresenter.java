package com.lyzb.jbx.mvp.presenter.me;

import android.text.TextUtils;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.model.cenum.DayEnum;
import com.lyzb.jbx.model.me.AcceNumberModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.IAccessView;
import com.szy.yishopcustomer.Util.App;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * 我的访问
 */

public class AccessPresenter extends APPresenter<IAccessView> {

    /**
     * 获取几个数字的数量
     */
    public void getNumber(String dayType, String id) {
        if (TextUtils.isEmpty(id)) {
            id = App.getInstance().userId;
        }
        if (dayType.equals(DayEnum.DAY_THIRTY.getValue())) {
            dayType = "30";
        } else if (dayType.equals(DayEnum.DAY_SEVEN.getValue())) {
            dayType = "7";
        } else if (dayType.equals(DayEnum.DAY_ZERO.getValue())){
            dayType = "1";
        }else {
            dayType = "0";
        }
        final String dayNum = dayType;
        final String userId = id;
        onRequestData(false, new IRequestListener<Object>() {

            @Override
            public Observable onCreateObservable() {
                Map<String, Object> params = new HashMap<>();
                params.put("dayNum", dayNum);
                params.put("userId", userId);
                return meApi.getAccessNumber(getHeadersMap(GET, "/lbs/gs/user/getUserTrack"), params);
            }

            @Override
            public void onSuccess(Object o) {
                AcceNumberModel numberModel = GSONUtil.getEntity(o.toString(), AcceNumberModel.class);
                getView().onAccessNumber(numberModel);
            }

            @Override
            public void onFail(String message) {

            }
        });
    }
}
