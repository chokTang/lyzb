package com.lyzb.jbx.mvp.presenter.me.company;

import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.base.inter.IRequestListener;
import com.like.longshaolib.net.helper.HttpResultStringFunc;
import com.like.longshaolib.net.helper.RequestSubscriber;
import com.like.longshaolib.net.inter.SubscriberOnNextListener;
import com.like.longshaolib.net.model.HttpResult;
import com.lyzb.jbx.model.me.CompanyAccountDetailModel;
import com.lyzb.jbx.model.me.ResultModel;
import com.lyzb.jbx.model.params.CompanyAccountBody;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.IEditCompanyAccountView;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import com.szy.yishopcustomer.Util.json.GSONUtil;

public class EditCompanyAccountPersenter extends APPresenter<IEditCompanyAccountView> {
    public void getData(final String userId, final String companyId) {

        onRequestData(true, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                Map<String, String> map = new HashMap<>();

                map.put("companyId", companyId);
                map.put("userId", userId);
                return meApi.getCompanyAccountDetail(getHeadersMap(GET, "/lbs/gs/distributor/getCompanyAccountDetail"), map);
            }

            @Override
            public void onSuccess(String o) {
                CompanyAccountDetailModel accountDetailModel = GSONUtil.getEntity(o, CompanyAccountDetailModel.class);
                if ("200".equals(accountDetailModel.getStatus())) {
                    getView().onApplyData(accountDetailModel.getDetailData());
                } else {
                    showFragmentToast(accountDetailModel.getMsg());
                }

            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    public void onUpDataAccount(final CompanyAccountBody body) {
        onRequestData(true, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.uptCompanyAccount(getHeadersMap(POST, "/lbs/gs/distributor/uptCompanyAccount"), body);
            }

            @Override
            public void onSuccess(String o) {
                ResultModel resultModel = GSONUtil.getEntity(o, ResultModel.class);
                if (Integer.parseInt(resultModel.getStatus()) == 200) {
                    getView().onUpDataAccount();
                } else if ("203".equals(resultModel.getStatus()) || "205".equals(resultModel.getStatus())) {
                    getView().onPhoneStatus(resultModel.getStatus(), resultModel.getMsg());
                } else {
                    showFragmentToast(resultModel.getMsg());
                }
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    public void onUpDataPassword(final String userId, final String password) {
        onRequestDataHaveCommon(false, new IRequestListener<Object>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> params = new HashMap<>();
                params.put("password", password);
                params.put("user_id", userId);
                return phpCommonApi.onQuickModifyPassword(params);
            }

            @Override
            public void onSuccess(Object object) {
                getView().onUpDataPassword();
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    public void onSendCode(final String mobile) {
        onRequestDataHaveCommon(false, new IRequestListener<Object>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> params = new HashMap<>();
                params.put("mobile", mobile);
                return phpCommonApi.securitySMS(params);
            }

            @Override
            public void onSuccess(Object object) {
                getView().onSendMSMSuccess();
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    public void validateMobile(final String mobile, final String code, final String status) {
        onRequestDataHaveCommon(false, new IRequestListener<Object>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> params = new HashMap<>();
                params.put("EditMobileGsModel[mobile]", mobile);
                params.put("EditMobileGsModel[sms_captcha]", code);
                return phpCommonApi.validateMobile(params);
            }

            @Override
            public void onSuccess(Object object) {
                getView().onValidateMobileCodeSuccess(status);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
