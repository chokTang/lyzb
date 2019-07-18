package com.lyzb.jbx.mvp.presenter.me.card;

import android.text.TextUtils;

import com.like.longshaolib.base.inter.IRequestListener;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.model.me.CardModel;
import com.lyzb.jbx.model.me.CardTabHideBody;
import com.lyzb.jbx.model.me.DoFocusModel;
import com.lyzb.jbx.model.me.DoLikeModel;
import com.lyzb.jbx.model.me.TabShowHideBean;
import com.lyzb.jbx.mvp.APPresenter;
import com.lyzb.jbx.mvp.view.me.ICardView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author wyx
 * @role 智能名片
 * @time 2019 2019/3/14 14:01
 */

public class CardPresenter extends APPresenter<ICardView> {

    /**
     * 获取名片详情
     *
     * @param isMe   是否是自己
     * @param userId 用户名片UserId
     * @param cardId 名片ID
     * @deprecated ：userId 和Cardid 任选一个
     */
    public void getData(final boolean isMe, final String userId, final String cardId) {

        onRequestData(true, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                if (isMe) {
                    return meApi.getCardData(getHeadersMap(GET, "/lbs/gs/user/getMyGaUserExtVoById"));
                } else {
                    Map<String, Object> parmas = new HashMap<>();
                    if (!TextUtils.isEmpty(userId)) {
                        parmas.put("userId", userId);
                    }
                    if (!TextUtils.isEmpty(cardId)) {
                        parmas.put("id", cardId);
                    }
                    return meApi.getOtherCardData(getHeadersMap(GET, "/lbs/gs/user/getGaUserExtVoById"), parmas);
                }
            }

            @Override
            public void onSuccess(String s) {
                CardModel modelList = GSONUtil.getEntity(s, CardModel.class);
                getView().onCardData(modelList);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }


    /**
     * 获取名片详情中的tab  如 新闻，动态，个人，官网这些  编辑栏目
     *
     * @param type    1 名片 2 圈子
     * @param userId  用户名片UserId
     * @param status  是否查看隐藏内容
     * @param groupId 圈子id 圈子时用
     * @deprecated ：userId 和Cardid 任选一个
     */
    public void getCardTabList(final int type, final String userId, final Boolean status, final String groupId) {

        onRequestData(true, new IRequestListener<String>() {
            @Override
            public Observable onCreateObservable() {
                Map<String, Object> map = new HashMap<>();
                map.put("type", type);
                map.put("userId", userId);
                map.put("status", status);
                map.put("groupId", groupId);
                return meApi.getCardTabList(getHeadersMap(GET, "/lbs/gs/user/selectGsCardFunction"), map);
            }

            @Override
            public void onSuccess(String s) {
                List<TabShowHideBean> modelList = GSONUtil.getEntityList(s, TabShowHideBean.class);
                getView().getTabList(modelList);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }


    /**
     * 请求数据 保存名片中的tab显示隐藏
     * @param body
     */
    public void saveCardTabShowHide(final CardTabHideBody body) {

        onRequestData(true, new IRequestListener<String>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.saveCardTabShowHide(getHeadersMap(POST, "/lbs/gs/user/saveGsCardFunctionSet"), body);
            }

            @Override
            public void onSuccess(String s) {
                List<TabShowHideBean> modelList = GSONUtil.getEntityList(s, TabShowHideBean.class);
                getView().saveSucess(modelList);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }

    /**
     * 分享、点赞走的是一个接口。不改之前的逻辑，我只新增个判断。cx 3.9.1 isShare
     *
     * @param isLike
     * @param isShare
     * @param model
     */
    public void doLike(final boolean isLike, final boolean isShare, final DoLikeModel model) {

        onRequestData(false, new IRequestListener<Object>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.doLike(getHeadersMap(POST, "/lbs/gs/user/saveGsOperRecord"), model);
            }

            @Override
            public void onSuccess(Object o) {
                if (!isShare) {
                    getView().doLike(isLike);
                }
            }

            @Override
            public void onFail(String message) {
                if (!isShare) {
                    showFragmentToast(message);
                }
            }
        });
    }

    public void doFocus(final boolean isFoucs, final DoFocusModel model) {

        onRequestData(true, new IRequestListener<Object>() {

            @Override
            public Observable onCreateObservable() {
                return meApi.doFocus(getHeadersMap(POST, "/lbs/gs/user/saveUsersRelation"), model);
            }

            @Override
            public void onSuccess(Object o) {
                getView().doFocus(isFoucs);
            }

            @Override
            public void onFail(String message) {
                showFragmentToast(message);
            }
        });
    }
}
