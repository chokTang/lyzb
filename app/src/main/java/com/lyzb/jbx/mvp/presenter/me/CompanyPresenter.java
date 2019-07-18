package com.lyzb.jbx.mvp.presenter.me;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.other.LogUtil;
import com.lyzb.jbx.model.me.CompanyModel;
import com.lyzb.jbx.model.me.JoinCompanyBody;
import com.lyzb.jbx.model.me.JoinCompanyResponseModel;
import com.lyzb.jbx.model.me.ResultModel;
import com.lyzb.jbx.model.me.SearchResultCompanyModel;
import com.lyzb.jbx.model.me.SetComdModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.ICompanyView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * 我的企业
 */

public class CompanyPresenter extends APPresenter<ICompanyView> {

    /**
     * 根据关键字查询企业列表  联想
     */
    public void getCompanyList(final String keyword) {
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, String> map = new HashMap<>();
                map.put("disName", keyword);
                return meApi.getCompanyList(getHeadersMap(GET, "/lbs/gs/user/queryDistributorByName"), map);
            }

            @Override
            public void onSuccess(String o) {
                try {
                    JSONObject object = new JSONObject(o);
                    List<SearchResultCompanyModel> resultlist = GSONUtil.getEntityList(object.getString("dataList"), SearchResultCompanyModel.class);
                    getView().onQueryList(resultlist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(String message) {
            }
        });
    }

    /**
     * 加入企业
     */
    public void joinConpany(final String companyId) {
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                JoinCompanyBody body = new JoinCompanyBody();
                body.setCompanyId(companyId);
                return meApi.joinCompany(getHeadersMap(POST, "/lbs/gs/distributor/doJoinCompany"), body);
            }

            @Override
            public void onSuccess(String o) {

                JoinCompanyResponseModel model = GSONUtil.getEntity(o, JoinCompanyResponseModel.class);
                if (model.getStatus().equals("200")) {
                    getView().joinSuccess();
                } else {
                    getView().joinFail();
                    showFragmentToast(model.getMsg());
                }
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

}
