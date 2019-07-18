package com.lyzb.jbx.mvp.presenter.me.company;

import com.like.longshaolib.base.inter.IRequestListener;
import com.lyzb.jbx.model.me.CompanyApplyListModel;
import com.lyzb.jbx.model.me.ResultModel;
import com.lyzb.jbx.model.params.AuditMembersBody;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.ICompanyApplyListView;
import com.szy.yishopcustomer.Util.json.GSONUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class CompanyApplyListPersenter extends APPresenter<ICompanyApplyListView> {

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
                map.put("queryType", "datas");
                return meApi.getApplyJoinAuditList(getHeadersMap(GET, "/lbs/gs/distributor/getApplyJoinAuditList"), map);
            }

            @Override
            public void onSuccess(String o) {
                CompanyApplyListModel listModel = GSONUtil.getEntity(o, CompanyApplyListModel.class);
                if ("200".equals(listModel.getStatus()) || "202".equals(listModel.getStatus())){
                    getView().onApplyList(isRefrsh, listModel.getData().getList());
                } else{
                    getView().onFinshOrLoadMore(isRefrsh);
                    showFragmentToast(listModel.getMsg());
                }

            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
                getView().onFinshOrLoadMore(isRefrsh);
            }
        });
    }

    public void onAudit(final AuditMembersBody model, final int position) {
        onRequestData(false, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.doAuditMembers(getHeadersMap(POST, "/lbs/gs/distributor/doAuditMembers"), model);
            }

            @Override
            public void onSuccess(String o) {
                ResultModel resultModel = GSONUtil.getEntity(o, ResultModel.class);
                if (Integer.parseInt(resultModel.getStatus()) == 200) {
                    getView().onAudit(model.getAuditState() == 1 ? 2 : 3, position);
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
