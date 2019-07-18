package com.lyzb.jbx.mvp.presenter.me;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.me.ComdDetailModel;
import com.lyzb.jbx.model.params.ApplyOpenBody;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.IApplyOpenView;

import org.json.JSONObject;

import io.reactivex.Observable;

public class ApplyOpenPresenter extends APPresenter<IApplyOpenView> {

    /**
     * 申请运营伙伴
     *
     * @param body
     */
    public void onApplyCompany(final ApplyOpenBody body) {
        onRequestData(new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return meApi.onApplyCompany(getHeadersMap(POST, "/lbs/gs/distributor/sharePlatRegister"), body);
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                int status = JSONUtil.get(resultObject, "status", 0);
                String msg = JSONUtil.get(resultObject, "msg", "");
                if (status == 200) {
                    getView().onApplyResultSuccess();
                    showFragmentToast("申请成功");
                } else {
                    showFragmentToast(msg);
                }
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    /**
     * 获取我的企业信息
     */
    public void getCompantInfo(final String companyId) {
        onRequestData(new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.getComdInfo(getHeadersMap(GET, "/lbs/gs/distributor/getCompanyDetail"), companyId);
            }

            @Override
            public void onSuccess(String o) {
                ComdDetailModel model = GSONUtil.getEntity(o, ComdDetailModel.class);
                if (model!=null){
                    getView().onCompanyInfoResult(model.getGsDistributorVo());
                }else {
                    showFragmentToast("获取公司信息错误");
                }
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
