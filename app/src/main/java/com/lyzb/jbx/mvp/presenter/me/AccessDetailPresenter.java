package com.lyzb.jbx.mvp.presenter.me;

import android.text.TextUtils;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.cenum.AccessType;
import com.lyzb.jbx.model.cenum.DataType;
import com.lyzb.jbx.model.cenum.DayEnum;
import com.lyzb.jbx.model.me.access.AccessMemberModel;
import com.lyzb.jbx.model.me.customerManage.CustomerInfoModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.IAccessDetailView;
import com.szy.yishopcustomer.Util.App;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class AccessDetailPresenter extends APPresenter<IAccessDetailView> {
    private int pageIndex = 1;
    private int pageSize = 10;

    /**
     * @param isRefrsh
     * @param id
     * @param dayType
     * @param scanType 分享浏览 ：1，普通浏览：0
     */
    public void getAccountAccess(final boolean isRefrsh, String id, String dayType, String scanType, final int dataVaule) {
        if (isRefrsh) {
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
        final String dayNum = dayType;
        final int shareView;
        if (scanType.equals(AccessType.SHARE.name())) {
            shareView = 1;
        } else {
            shareView = 0;
        }

        if (dataVaule== DataType.ACRTICE.getValue()) { //如果是热文数据
            getHotDataList(isRefrsh, dayType, pageIndex, pageSize);
            return;
        }
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> params = new HashMap<>();
                params.put("pageSize", pageSize);
                params.put("pageNum", pageIndex);
                params.put("dayNum", dayNum);
                if (shareView == 1) {
                    params.put("shareView", shareView);
                }
                params.put("userId", userId);
//                params.put("departmentId", scanType);
                params.put("viewType", dataVaule);
                return meApi.getAccessAccountList(getHeadersMap(GET, "/lbs/gs/user/selectMyViewRecordVoList"), params);
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                int total = JSONUtil.get(resultObject, "total", 0);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                getView().onAccessResult(isRefrsh, total, GSONUtil.getEntityList(listArray.toString(), AccessMemberModel.class));
            }

            @Override
            public void onFail(String message) {
                getView().onFinshOrLoadMore(isRefrsh);
                showFragmentToast(message);
            }
        });
    }

    /**
     * 获取热文推广数据
     */
    private void getHotDataList(final boolean isRefresh, final String dayNum, final int pageNum, final int pageSize) {
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> params = new HashMap<>();
                params.put("dayNum", dayNum);
                params.put("pageNum", pageNum);
                params.put("pageSize", pageSize);
                return meApi.getAccessHotList(getHeadersMap(GET, "/lbs/gs/user/selectHotUserList"), params);
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                int total = JSONUtil.get(resultObject, "total", 0);
                JSONArray listArray = JSONUtil.getJsonArray(resultObject, "list");
                getView().onAccessResult(isRefresh, total, GSONUtil.getEntityList(listArray.toString(), AccessMemberModel.class));
            }

            @Override
            public void onFail(String message) {
                getView().onFinshOrLoadMore(isRefresh);
                showFragmentToast(message);
            }
        });
    }

    /**
     * 设置意向客户
     *
     * @param poistion
     * @param cardId   用户的名片ID
     */
    public void setInterAccount(final int poistion, final String cardId) {
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
                getView().onInterAccountResult(poistion);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
