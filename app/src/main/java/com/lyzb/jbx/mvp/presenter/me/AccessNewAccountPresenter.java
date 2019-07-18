package com.lyzb.jbx.mvp.presenter.me;

import android.text.TextUtils;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.cenum.DayEnum;
import com.lyzb.jbx.model.me.access.AccessNewAccountModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.IAccessNewAccountView;
import com.szy.yishopcustomer.Util.App;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class AccessNewAccountPresenter extends APPresenter<IAccessNewAccountView> {

    private int pageSize = 10;
    private int paseIndex = 1;

    public void getAccountList(final boolean isRefresh, String id, String dayType) {
        if (isRefresh) {
            paseIndex = 1;
        } else {
            paseIndex++;
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
                params.put("pageNum", paseIndex);
                params.put("pageSize", pageSize);
                params.put("days", dayNumber);
                params.put("shareUserId", userId);
                return meApi.getAccessNewAccount(getHeadersMap(GET, "/lbs/gsUserBelong/selectByPageInfo"), params);
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                int totalNumber = JSONUtil.get(resultObject, "total", 0);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                getView().onAccountResultList(isRefresh, totalNumber, GSONUtil.getEntityList(listArray.toString(), AccessNewAccountModel.class));
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
