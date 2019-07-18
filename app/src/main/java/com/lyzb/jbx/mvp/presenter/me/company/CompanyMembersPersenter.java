package com.lyzb.jbx.mvp.presenter.me.company;

import com.like.longshaolib.base.inter.IRequestListener;
import com.lyzb.jbx.model.me.CompanyMembersModel;
import com.lyzb.jbx.model.me.ResultModel;
import com.lyzb.jbx.model.params.RemoveMembersBody;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.ICompanyMembersView;
import com.szy.yishopcustomer.Util.json.GSONUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class CompanyMembersPersenter extends APPresenter<ICompanyMembersView> {

    private int pageIndex = 1;
    private int pageSize = 15;

    public void getList(final boolean isRefrsh, final String companyId) {
        if (isRefrsh) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                Map<String, String> map = new HashMap<>();
                map.put("pageNum", String.valueOf(pageIndex));
                map.put("pageSize", String.valueOf(pageSize));
                map.put("companyId", companyId);
                return meApi.getCompanyMembers(getHeadersMap(GET, "/lbs/gs/distributor/getCompanyMembers"), map);
            }

            @Override
            public void onSuccess(String o) {
                CompanyMembersModel companyMembersModel = GSONUtil.getEntity(o, CompanyMembersModel.class);
                if ("200".equals(companyMembersModel.getStatus()) || "202".equals(companyMembersModel.getStatus())) {
                    getView().onList(isRefrsh, companyMembersModel);
                } else {
                    getView().onFinshOrLoadMore(isRefrsh);
                    showFragmentToast(companyMembersModel.getMsg());
                }

            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
                getView().onFinshOrLoadMore(isRefrsh);
            }
        });
    }

    public void onRemove(final RemoveMembersBody model, final int position) {
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.doRemoveMembers(getHeadersMap(POST, "/lbs/gs/distributor/doRemoveMembers"), model);
            }

            @Override
            public void onSuccess(String o) {
                ResultModel resultModel = GSONUtil.getEntity(o, ResultModel.class);
                if (Integer.parseInt(resultModel.getStatus()) == 200) {
                    getView().onRemove(position);
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

}
