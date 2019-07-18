/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.szy.yishopcustomer.Activity.im;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.like.longshaolib.app.LongshaoAPP;
import com.like.longshaolib.app.config.ConfigType;
import com.like.longshaolib.net.convert.BodyConverterFactory;
import com.like.longshaolib.net.helper.HttpResultStringFunc;
import com.like.longshaolib.net.helper.RequestSubscriber;
import com.like.longshaolib.net.inter.SubscriberOnNextListener;
import com.like.utilslib.json.JSONUtil;
import com.lyzb.jbx.api.ICommonApi;
import com.lyzb.jbx.model.params.ImUserInfoBody;
import com.lyzb.jbx.util.JavaMD5Util;
import com.szy.yishopcustomer.Util.App;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/***
 * 被动接受到消息的receiver
 *
 */
public class CallReceiver extends BroadcastReceiver {

    private Retrofit retrofit;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!EMClient.getInstance().isLoggedInBefore())
            return;
        //username
        String from = intent.getStringExtra("from");
        //call type
        String type = intent.getStringExtra("type");

        String headUrl, userName;

        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(from);
        if (conversation != null) {
            EMMessage lastMessage = conversation.getLatestMessageFromOthers();
            if (lastMessage != null) {
                userName = lastMessage.getStringAttribute(EaseConstant.EXTRA_FROM_NICKNAME, "");
                headUrl = lastMessage.getStringAttribute(EaseConstant.EXTRA_FROM_HEADER, "");

                if ("video".equals(type)) { //video call
                    context.startActivity(new Intent(context, VideoCallActivity.class).
                            putExtra("username", from)
                            .putExtra("isComingCall", true)
                            .putExtra("shopName", userName)
                            .putExtra("shopHeadUrl", headUrl)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else { //voice call
                    context.startActivity(new Intent(context, VoiceCallActivity.class).
                            putExtra("username", from)
                            .putExtra("isComingCall", true)
                            .putExtra("shopName", userName)
                            .putExtra("shopHeadUrl", headUrl)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            } else {
                getImInfoByImName(context,from.replaceAll("jbx", ""), type);
            }
        } else {
            getImInfoByImName(context,from.replaceAll("jbx", ""), type);
        }
    }

    /**
     * 请求网络
     *
     * @param userId
     * @param type
     */
    private synchronized void getImInfoByImName(final Context context, String userId, final String type) {
        if (context == null) return;

        OkHttpClient.Builder mHttpBuilder = new OkHttpClient.Builder();
        mHttpBuilder.connectTimeout(5, TimeUnit.SECONDS);//设置超时时间
        mHttpBuilder.retryOnConnectionFailure(false);//连接超时了是否重新连接

        retrofit = new Retrofit.Builder()
                .client(mHttpBuilder.build())
                .addConverterFactory(BodyConverterFactory.create())//设置返回来为String类型的数据
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(LongshaoAPP.getConfiguration(ConfigType.API_HOST).toString() + "lbsapi/")
                .build();

        RequestSubscriber<String> requestSubscriber = new RequestSubscriber<>(context);
        requestSubscriber.isShowDilog(false);
        requestSubscriber.bindCallbace(new SubscriberOnNextListener<String>() {
            @Override
            public void onNext(String s) {
                JSONObject object = JSONUtil.toJsonObject(s);
                int status = JSONUtil.get(object, "status", 0);
                if (status == 200) {
                    String nickName = JSONUtil.get(object, "nickName", "");
                    String headimg = JSONUtil.get(object, "headimg", "");
                    String userName = JSONUtil.get(object, "userName", "");

                    if ("video".equals(type)) { //video call
                        context.startActivity(new Intent(context, VideoCallActivity.class).
                                putExtra("username", userName)
                                .putExtra("isComingCall", true)
                                .putExtra("shopName", nickName)
                                .putExtra("shopHeadUrl", headimg)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    } else { //voice call
                        context.startActivity(new Intent(context, VoiceCallActivity.class).
                                putExtra("username", userName)
                                .putExtra("isComingCall", true)
                                .putExtra("shopName", nickName)
                                .putExtra("shopHeadUrl", headimg)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });

        retrofit.create(ICommonApi.class).getImUserInfo(getHeadersMap("POST", "/lbs/queryUserNameAndPassword")
                , new ImUserInfoBody(userId))
                .map(new HttpResultStringFunc<String>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(requestSubscriber);
    }


    /**
     * 封装的java 统一的请求头部信息
     *
     * @param method
     * @param mUrl
     * @return
     */
    protected Map<String, String> getHeadersMap(String method, String mUrl) {
        String mUuid = UUID.randomUUID().toString();
        String timeTemp = System.currentTimeMillis() + "";

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(method).append("\n");
        stringBuffer.append("*/*").append("\n");
        stringBuffer.append("application/json; charset=utf-8").append("\n");
        stringBuffer.append("x-ca-nonce:" + mUuid).append("\n");
        stringBuffer.append("x-ca-timestamp:" + timeTemp).append("\n");
        stringBuffer.append(mUrl);
        StringBuffer mBuffer = new StringBuffer();
        mBuffer.append(JavaMD5Util.toMD5(stringBuffer.toString().trim())).append("$lw1XRJhQ#ys2q");

        Map<String, String> params = new HashMap<>();
        params.put("token", App.getInstance().user_token == null ? "" : App.getInstance().user_token);
        params.put("x-ca-key", "appScript");
        params.put("x-ca-timestamp", timeTemp);
        params.put("x-ca-nonce", mUuid);
        params.put("Accept", "*/*");
        params.put("Content-Type", "application/json; charset=utf-8");
        params.put("x-ca-signature", JavaMD5Util.toMD5(mBuffer.toString().trim()));
        return params;
    }
}
