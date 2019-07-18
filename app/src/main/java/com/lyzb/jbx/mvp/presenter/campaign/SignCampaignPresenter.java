package com.lyzb.jbx.mvp.presenter.campaign;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.model.params.SignCampaginBody;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.campaign.ISignCampaignView;

import org.json.JSONObject;

import io.reactivex.Observable;

public class SignCampaignPresenter extends APPresenter<ISignCampaignView> {

    /**
     * 参加活动
     *
     * @param campaignId
     * @param name
     * @param phone
     * @param signNumber
     * @param nviterUser
     */
    public void onSignCampagin(final String campaignId, final String name, final String phone, final String signNumber, final String nviterUser) {
        onRequestData(new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                SignCampaginBody body = new SignCampaginBody();
                body.setActivityId(campaignId);
                body.setInvitePeople(nviterUser);
                body.setPhone(phone);
                body.setUserNum(signNumber);
                body.setUserName(name);
                return campaginApi.signCampaign(getHeadersMap(POST, "/lbs/participant/addOrUpdate"), body);
            }

            @Override
            public void onSuccess(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                int status = JSONUtil.get(resultObject, "status", 0);
                if (status == 200) {
                    getView().onSignResultSuccess();
                } else {
                    showFragmentToast("报名失败");
                }
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
