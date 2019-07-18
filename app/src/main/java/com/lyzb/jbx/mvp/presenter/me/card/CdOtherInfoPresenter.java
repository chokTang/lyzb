package com.lyzb.jbx.mvp.presenter.me.card;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.model.account.BusinessModel;
import com.lyzb.jbx.model.me.CardUserInfoModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.ICdOtherInfoView;

import java.util.List;

import io.reactivex.Observable;

/***
 * 智能名片-其他信息编辑(添加)
 */

public class CdOtherInfoPresenter extends APPresenter<ICdOtherInfoView> {

    /***
     * 获取 行业信息 data
     *
     */
    public void getList() {
        onRequestData(false, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return meApi.getIndustryList(getHeadersMap(GET, "/lbs/gs/user/selectGsProfessionList"));
            }

            @Override
            public void onSuccess(String s) {
                List<BusinessModel> modelList = GSONUtil.getEntityList(s, BusinessModel.class);  //行业信息list
                getView().onIndustryList(modelList);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    public void saveInfo(final CardUserInfoModel model) {

        onRequestData(false, new IRequestListener<Object>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.saveOtherInfo(getHeadersMap(POST, "/lbs/gs/user/saveGaUserExt"), model);
            }

            @Override
            public void onSuccess(Object o) {
                showFragmentToast("保存成功");

                getView().toCardInfo(o.toString());
            }

            @Override
            public void onFail(String message) {
                getView().saveFail();
            }
        });
    }

}
