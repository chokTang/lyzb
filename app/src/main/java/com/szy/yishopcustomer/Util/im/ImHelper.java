package com.szy.yishopcustomer.Util.im;

import android.os.Build;
import android.text.TextUtils;

import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.push.handler.GetTokenHandler;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;
import com.like.utilslib.other.LogUtil;
import com.lyzb.jbx.util.AppPreference;
import com.szy.yishopcustomer.Constant.Api;

/**
 * Created by Administrator on 2018/7/6.
 */

public class ImHelper {

    private static final class Holder {
        private final static ImHelper INTANCE = new ImHelper();
    }

    public static final ImHelper getIntance() {
        return Holder.INTANCE;
    }

    /**
     * IM 注册
     *
     * @param name
     * @param password
     * @param listener
     */
    public final void onRegister(String name, String password, IMDoneListener listener) {
        try {
            EMClient.getInstance().createAccount(name, password);//同步方法
            if (listener != null) {
                listener.onSuccess();
            }
        } catch (HyphenateException e) {
            if (listener != null) {
                listener.onFailer(e.getMessage());
            }
        }
    }

    /**
     * 加载消息分组 最好放在闪屏页面-Oncretae中
     */
    public final void onLoadMessageGroup() {
        EMClient.getInstance().chatManager().loadAllConversations();
        EMClient.getInstance().groupManager().loadAllGroups();
        String huaWeiToken = AppPreference.getIntance().getHXHuaWeiToken();
        String deviceName = Build.MANUFACTURER;
        if (deviceName.equals("HUAWEI")) {
            if (TextUtils.isEmpty(huaWeiToken)) {
                HMSAgent.Push.getToken(new GetTokenHandler() {
                    @Override
                    public void onResult(int rst) {
                        LogUtil.loge("环信-华为推送获取token返回值:" + rst);
                    }
                });
            } else {
                EMClient.getInstance().sendHMSPushTokenToServer("100219423", huaWeiToken);
            }
        }
    }

    /**
     * 环信登录
     *
     * @param userName
     * @param password
     * @param listener
     */
    public final void onLogin(String userName, String password, final IMDoneListener listener) {
        EMClient.getInstance().login(userName, password, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                if (listener != null) {
                    listener.onSuccess();
                }
                EaseConstant.SC_IMG_BASE = Api.SAME_CITY_URL;
                onLoadMessageGroup();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                if (listener != null) {
                    listener.onFailer(message);
                }
            }
        });
    }

    /**
     * 退出登录
     */
    public final void onLoginOut(final IMDoneListener listener) {
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                if (listener != null) {
                    listener.onSuccess();
                }
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(int code, String message) {
                if (listener != null) {
                    listener.onFailer(message);
                }
            }
        });
    }
}
