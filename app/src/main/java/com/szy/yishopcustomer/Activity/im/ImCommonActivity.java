package com.szy.yishopcustomer.Activity.im;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.lyzb.jbx.R;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Activity.YSCBaseActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Fragment.Im.ImCommonFragment;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.Oss;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.im.ImHeaderGoodsModel;
import com.yolanda.nohttp.RequestMethod;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * IM 聊天页面
 * Created by shidengzhong on 2018/7/9.
 */

public class ImCommonActivity extends YSCBaseActivity {

    //参数
    public static final String PARAMS_GOODS = "PARAMS_GOODS";
    private ImHeaderGoodsModel mGoodsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_fragment_layout);

        Bundle args = getIntent().getExtras();

        if (args != null) {
            mGoodsModel = (ImHeaderGoodsModel) args.getSerializable(PARAMS_GOODS);
        }

        ImCommonFragment fragment = new ImCommonFragment();
        Bundle args1 = new Bundle();
        args1.putInt(EaseConstant.EXTRA_CHAT_TYPE, mGoodsModel.getChatType());
        args1.putBoolean(EaseConstant.EXTRA_GROUP_IS_BANNED, mGoodsModel.getIsBanned());
        args1.putString(EaseConstant.EXTRA_USER_ID, mGoodsModel.getShopImName());
        args1.putString(EaseConstant.EXTRA_OTHER_NAME, mGoodsModel.getShopName());
        args1.putSerializable("model", mGoodsModel);
        fragment.setArguments(args1);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.root_layout, fragment)
                .commit();
    }


    /****
     * 发送消息
     *  isHaveGoodsInfo 是否要携带商品信息
     * @param content
     */
    public void sendMessageToServie(EMMessage message, String content, boolean isHaveGoodsInfo) {

        final CommonRequest request = new CommonRequest(Api.API_CITY_IM_SENDMESSAGE, HttpWhat.HTTP_IM_SENDMESSAGE.getValue(), RequestMethod.POST);
        RequestAddHead.addHead(request, this, Api.API_CITY_IM_SENDMESSAGE, "POST");
        final JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("username", mGoodsModel.getShopImName());
            jsonObject.put("message", content);
            if (message.getChatType() == EMMessage.ChatType.Chat) {
                jsonObject.put("targetTypeus", "users");
                jsonObject.put("fromUser", EaseConstant.CHAT_USER_ID);
            } else {
                jsonObject.put("targetTypeus", "chatgroups");
                jsonObject.put("fromUser", EaseConstant.CHAT_USER_ID);
            }
            JSONObject goodsObject = new JSONObject();

            if (isHaveGoodsInfo) {

                if (!TextUtils.isEmpty(mGoodsModel.getGoodPrice())) {
                    goodsObject.put("goodsName", mGoodsModel.getGoodsName());
                    goodsObject.put("goodsPrice", mGoodsModel.getGoodPrice());
                    goodsObject.put("goodsUrl", mGoodsModel.getImageUrl());
                    goodsObject.put("gooosId", mGoodsModel.getGoodsId());
                    goodsObject.put("msg_type", "msg_share");
                }
            }


            if (!TextUtils.isEmpty(App.getInstance().nickName)) {
                goodsObject.put(EaseConstant.EXTRA_FROM_NICKNAME, App.getInstance().nickName);
            } else {
                goodsObject.put(EaseConstant.EXTRA_FROM_NICKNAME, SharedPreferencesUtils.getParam(this, Key.IM_USERNICK.name(), "").toString());
            }

            //区分群聊和单聊
            if (message.getChatType() == EMMessage.ChatType.Chat) {
                goodsObject.put(EaseConstant.EXTRA_FROM_USERID, App.getInstance().userId);
            } else if (message.getChatType() == EMMessage.ChatType.GroupChat) {
                goodsObject.put(EaseConstant.EXTRA_FROM_USERID, EaseConstant.CHAT_USER_ID);
            }


            if (!TextUtils.isEmpty(App.getInstance().headimg)) {
                goodsObject.put(EaseConstant.EXTRA_FROM_HEADER, Utils.urlOfImage(App.getInstance().headimg));
            } else {
                goodsObject.put(EaseConstant.EXTRA_FROM_HEADER, SharedPreferencesUtils.getParam(this, Key.IM_USERHEADING.name(), "").toString());
            }

            goodsObject.put(EaseConstant.EXTRA_TO_USERID, mGoodsModel.getShopId());
            goodsObject.put(EaseConstant.EXTRA_TO_HEADER, mGoodsModel.getShopHeadimg());
            goodsObject.put(EaseConstant.EXTRA_TO_NICKNAME, mGoodsModel.getShopName());

            JSONObject pushObject = new JSONObject();
            pushObject.put("em_push_name", "您有新的消息");
            pushObject.put("em_push_content", "来自于"+goodsObject.getString(EaseConstant.EXTRA_FROM_NICKNAME)+":"+content);

            //设置推送的消息的标题和内容
            message.setAttribute("em_apns_ext",pushObject);
            goodsObject.put("em_apns_ext", pushObject);

            jsonObject.put("ext", goodsObject);

            if (message.getType() == EMMessage.Type.TXT) {

                jsonObject.put("type", "txt");
                request.setDefineRequestBodyForJson(jsonObject.toString());
                request.alarm = false;
                addRequest(request);

            } else if (message.getType() == EMMessage.Type.IMAGE) {

                jsonObject.put("type", "img");
                EMImageMessageBody body = (EMImageMessageBody) message.getBody();
                String user_token = (String) SharedPreferencesUtils.getParam(ImCommonActivity.this, Key.USER_INFO_TOKEN.getValue(), "");
                Oss.getInstance().updaLoadImage(ImCommonActivity.this, user_token, body.getLocalUrl(), new Oss.OssListener() {
                    @Override
                    public void onProgress(int progress) {

                    }

                    @Override
                    public void onSuccess(String url) {
                        try {
                            jsonObject.put("filePath", url);
                            request.setDefineRequestBodyForJson(jsonObject.toString());
                            request.alarm = false;
                            addRequest(request);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure() {
                        showToast("上传文件失败");
                    }
                });
            } else if (message.getType() == EMMessage.Type.VOICE) {
                jsonObject.put("type", "audio");
                EMVoiceMessageBody body = (EMVoiceMessageBody) message.getBody();
                String user_token = (String) SharedPreferencesUtils.getParam(ImCommonActivity.this, Key.USER_INFO_TOKEN.getValue(), "");
                Oss.getInstance().updaLoadFile(ImCommonActivity.this, user_token, body.getLocalUrl(), new Oss.OssListener() {
                    @Override
                    public void onProgress(int progress) {

                    }

                    @Override
                    public void onSuccess(String url) {
                        try {
                            jsonObject.put("filePath", url);
                            request.setDefineRequestBodyForJson(jsonObject.toString());
                            request.alarm = false;
                            addRequest(request);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure() {
                        showToast("上传文件失败");
                    }
                });
            } else if (message.getType() == EMMessage.Type.LOCATION) {
                //发送位置信息 环信本身的通道
                EMClient.getInstance().chatManager().sendMessage(message);
            } else if (message.getType() == EMMessage.Type.VIDEO) {
                //发送视频文件
                EMClient.getInstance().chatManager().sendMessage(message);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ImHeaderGoodsModel getmGoodsModel() {
        return mGoodsModel;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
