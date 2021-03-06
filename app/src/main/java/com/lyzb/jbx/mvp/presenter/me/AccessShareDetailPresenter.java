package com.lyzb.jbx.mvp.presenter.me;

import android.text.TextUtils;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.model.cenum.DayEnum;
import com.lyzb.jbx.model.me.access.AccessNumberDetailModel;
import com.lyzb.jbx.model.me.access.AccessShareDetailModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.IAccessShareDetailView;
import com.szy.yishopcustomer.Util.App;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class AccessShareDetailPresenter  extends APPresenter<IAccessShareDetailView> {

    public void getShareDetail(String id,String dayType){
        final String userId = TextUtils.isEmpty(id) ? App.getInstance().userId : id;
        if (dayType.equals(DayEnum.DAY_THIRTY.getValue())) {
            dayType = "30";
        } else if (dayType.equals(DayEnum.DAY_SEVEN.getValue())) {
            dayType = "7";
        } else if (dayType.equals(DayEnum.DAY_ZERO.getValue())){
            dayType = "1";
        }else {
            dayType="0";
        }
        final String dayNum = dayType;
        onRequestData(new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> params = new HashMap<>();
                params.put("dayNum", dayNum);
                params.put("userId", userId);
                return meApi.getAccessShareDetail(getHeadersMap(GET, "/lbs/gs/user/selectMyShareNumVo"), params);
            }

            @Override
            public void onSuccess(String s) {
                AccessShareDetailModel detailModel = GSONUtil.getEntity(s, AccessShareDetailModel.class);
                getView().onShareDetailResult(detailModel);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
