package com.lyzb.jbx.mvp.presenter.me;

import com.like.longshaolib.base.inter.IRequestListener;
import com.lyzb.jbx.model.me.CardUserInfoModel;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.IShareWordView;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.util.AppPreference;

import io.reactivex.Observable;

/**
 * @author wyx
 * @role
 * @time 2019 2019/5/14 14:20
 */

public class ShareWordPresenter extends APPresenter<IShareWordView> {
    /**
     * 获取分享语
     */
    public void getShareWord() {
        onRequestData(new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                return meApi.getShareWord(getHeadersMap(GET, "/lbs/gs/user/selectShareWord"));
            }

            @Override
            public void onSuccess(String s) {
                String cardShareWord = JSONUtil.get(JSONUtil.toJsonObject(s), "cardShareWord", "");
                String topicShareWord = JSONUtil.get(JSONUtil.toJsonObject(s), "topicShareWord", "");
                getView().onShareWord(cardShareWord, topicShareWord);
            }

            @Override
            public void onFail(String message) {
                getView().onFail(message);
            }
        });
    }

    /**
     * 设置分享语
     */
    public void setShareWord(final String cardShareWord,final String topicShareWord) {

        final CardUserInfoModel model = new CardUserInfoModel();
        model.setCardShareWord(cardShareWord);
        model.setTopicShareWord(topicShareWord);

        onRequestData(new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.saveOtherInfo(getHeadersMap(POST, "/lbs/gs/user/saveGaUserExt"), model);
            }

            @Override
            public void onSuccess(String s) {
                AppPreference.getIntance().setShareCardValue(cardShareWord);
                AppPreference.getIntance().setShareDynamicValue(topicShareWord);
                getView().onSetChareWordSuccess();
            }

            @Override
            public void onFail(String message) {
                getView().onFail(message);
            }
        });
    }
}
