package com.lyzb.jbx.mvp.presenter.me;

import android.text.TextUtils;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.cenum.AccessType;
import com.lyzb.jbx.model.cenum.DataType;
import com.lyzb.jbx.model.cenum.DayEnum;
import com.lyzb.jbx.model.me.AcsRecomdModel;
import com.lyzb.jbx.model.me.customerManage.CustomerInfoModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.IAcsDetView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * 某个访问人的详细记录
 */

public class AcsDetPresenter extends APPresenter<IAcsDetView> {

    private int pageIndex = 1;
    private int pageSize = 50;

    public void getAcsList(final boolean isRefresh, final String userId, String accessType, final int viewType, String dayNum, String openId, String userGuid) {
        if (isRefresh) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }
        final int shareView;
        if (accessType.equals(AccessType.SHARE.name())) {
            shareView = 1;
        } else {
            shareView = 0;
        }
        if (dayNum.equals(DayEnum.DAY_THIRTY.getValue())) {
            dayNum = "30";
        } else if (dayNum.equals(DayEnum.DAY_SEVEN.getValue())) {
            dayNum = "7";
        } else if (dayNum.equals(DayEnum.DAY_ZERO.getValue())) {
            dayNum = "1";
        } else {
            dayNum = "0";
        }
        final String dayType = dayNum;
        if (viewType == DataType.ACRTICE.getValue()) {//请求热文数据
            getAcsHotList(dayType, openId, userGuid);
            return;
        }
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.getAcsList(getHeadersMap(GET, "/lbs/gs/user/selectMyAccessUserVo"), pageIndex, pageSize, userId, shareView, viewType, dayType);
            }

            @Override
            public void onSuccess(String o) {
                JSONObject resultObject = JSONUtil.toJsonObject(o);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                getView().onAcsList(isRefresh, GSONUtil.getEntityList(listArray.toString(), AcsRecomdModel.class));
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    /**
     * 获取XX用户的热文推广数据
     */
    private void getAcsHotList(final String dayNum, final String openId, final String userGuid) {
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                Map<String, Object> params = new HashMap<>();
                params.put("dayNum", dayNum);
                params.put("openId", openId);
                params.put("userGuid", userGuid);
                return meApi.getAcsHotList(getHeadersMap(GET, "/lbs/gs/user/selectHotUserDetailList"), params);
            }

            @Override
            public void onSuccess(String o) {
                JSONObject resultObject = JSONUtil.toJsonObject(o);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                getView().onAcsList(true, GSONUtil.getEntityList(listArray.toString(), AcsRecomdModel.class));
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    /**
     * 设置为意向客户
     *
     * @param cardId 用户的名片ID
     */
    public void settingInterAccount(final String cardId) {
        if (TextUtils.isEmpty(cardId)) {
            showFragmentToast("TA还没有名片，暂无法设为意向");
            return;
        }
        onRequestData(new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return meApi.addCustomer(getHeadersMap(POST, "/lbs/gsCustomers/addOrUpdateCustomers"), new CustomerInfoModel(cardId));
            }

            @Override
            public void onSuccess(String s) {
                getView().onSettingAccountResult();
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
