package com.szy.yishopcustomer.Fragment;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Activity.im.ImCommonActivity;
import com.szy.yishopcustomer.Activity.im.VideoCallActivity;
import com.szy.yishopcustomer.Activity.im.VoiceCallActivity;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.im.ImHeaderGoodsModel;

import java.util.List;

/**
 * Created by Administrator on 2018/7/20.
 */

public class ImCommonFragment extends EaseChatFragment {

    private ImHeaderGoodsModel mModel;
    private ImCommonActivity activity;
    private final static long timeValue = 1 * 60 * 60 * 1000;//间隔1个小时

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ImCommonActivity) context;
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        mModel = (ImHeaderGoodsModel) fragmentArgs.getSerializable("model");
        if (mModel != null) {
            titleBar.setTitle(mModel.getShopName());
        }

        if (!isAddShop()) {//如果不可以添加商品，就不需要添加了
            return;
        }
        //这里来判断
        if (mModel != null && !TextUtils.isEmpty(mModel.getImageUrl())) {
            EMMessage message = EMMessage.createTxtSendMessage("商品数据", toChatUsername);
            message.setAttribute("msg_type", "msg_share");
            message.setAttribute("goodsName", mModel.getGoodsName());
            message.setAttribute("goodsPrice", mModel.getGoodPrice());
            message.setAttribute("goodsUrl", mModel.getImageUrl());
            message.setAttribute("goodsId", mModel.getGoodsId());
            message.setAttribute("userName", SharedPreferencesUtils.getParam(getContext(), Key.IM_USERNICK.name(), "").toString());

            if (!TextUtils.isEmpty(App.getInstance().nickName)) {
                message.setAttribute(EaseConstant.EXTRA_FROM_NICKNAME, App.getInstance().nickName);
            } else {
                message.setAttribute(EaseConstant.EXTRA_FROM_NICKNAME, SharedPreferencesUtils.getParam(activity, Key.IM_USERNICK.name(), "").toString());
            }
            if (!TextUtils.isEmpty(App.getInstance().headimg)) {
                message.setAttribute(EaseConstant.EXTRA_FROM_HEADER, Utils.urlOfImage(App.getInstance().headimg));
            } else {
                message.setAttribute(EaseConstant.EXTRA_FROM_HEADER, SharedPreferencesUtils.getParam(activity, Key.IM_USERHEADING.name(), "").toString());
            }
            message.setAttribute(EaseConstant.EXTRA_FROM_USERID, App.getInstance().userId);

            message.setAttribute(EaseConstant.EXTRA_TO_NICKNAME, activity.getmGoodsModel().getShopName());
            message.setAttribute(EaseConstant.EXTRA_TO_USERID, activity.getmGoodsModel().getShopId());
            message.setAttribute(EaseConstant.EXTRA_TO_HEADER, activity.getmGoodsModel().getShopHeadimg());
            message.setStatus(EMMessage.Status.SUCCESS);
            conversation.appendMessage(message);
            messageList.refreshSelectLast();
            activity.sendMessageToServie(message, "", true);
        }
    }

    @Override
    protected void sendMessageToServie(EMMessage message) {
        activity.sendMessageToServie(message, EaseSmileUtils.getSmiledText(getContext(), EaseCommonUtils.getMessageDigest(message, this.getContext())).toString(), false);
        if (!TextUtils.isEmpty(activity.getmGoodsModel().getGoodPrice())) {
            message.setAttribute("goodsName", activity.getmGoodsModel().getGoodsName());
            message.setAttribute("goodsPrice", activity.getmGoodsModel().getGoodPrice());
            message.setAttribute("goodsUrl", activity.getmGoodsModel().getImageUrl());
            message.setAttribute("goodsId", activity.getmGoodsModel().getGoodsId());
        }

        if (!TextUtils.isEmpty(App.getInstance().nickName)) {
            message.setAttribute(EaseConstant.EXTRA_FROM_NICKNAME, App.getInstance().nickName);
        } else {
            message.setAttribute(EaseConstant.EXTRA_FROM_NICKNAME, SharedPreferencesUtils.getParam(activity, Key.IM_USERNICK.name(), "").toString());
        }
        if (!TextUtils.isEmpty(App.getInstance().headimg)) {
            message.setAttribute(EaseConstant.EXTRA_FROM_HEADER, Utils.urlOfImage(App.getInstance().headimg));
        } else {
            message.setAttribute(EaseConstant.EXTRA_FROM_HEADER, SharedPreferencesUtils.getParam(activity, Key.IM_USERHEADING.name(), "").toString());
        }
        message.setAttribute(EaseConstant.EXTRA_FROM_USERID, App.getInstance().userId);

        message.setAttribute(EaseConstant.EXTRA_TO_NICKNAME, activity.getmGoodsModel().getShopName());
        message.setAttribute(EaseConstant.EXTRA_TO_USERID, activity.getmGoodsModel().getShopId());
        message.setAttribute(EaseConstant.EXTRA_TO_HEADER, activity.getmGoodsModel().getShopHeadimg());
        message.setStatus(EMMessage.Status.SUCCESS);
        conversation.appendMessage(message);
        messageList.refreshSelectLast();
    }

    private boolean isWihle = true;//循环
    private String startMsgId;

    /**
     * 是否可以添加商品消息
     *
     * @return
     */
    private boolean isAddShop() {
        if (mModel == null || TextUtils.isEmpty(mModel.getImageUrl())) {
            return false;
        } else {
            List<EMMessage> messages = conversation.getAllMessages();
            if (messages == null || messages.size() == 0) {
                return true;
            } else {
                EMMessage goodsMessage = null;
                final int size = messages.size();

                for (int i = size - 1; i > 0; i--) {
                    EMMessage message = messages.get(i);
                    String goodsId = message.getStringAttribute("goodsId", "");
                    if (!TextUtils.isEmpty(goodsId)) {
                        goodsMessage = message;
                        break;
                    }
                }
                if (goodsMessage == null) {
                    EMMessage firstMessage = messages.get(0);
                    if (System.currentTimeMillis() - firstMessage.getMsgTime() > timeValue) {
                        return true;
                    } else {
                        startMsgId = messages.get(0).getMsgId();
                        boolean isAddGoods = true;
                        while (isWihle) {
                            List<EMMessage> wihleMessage = conversation.loadMoreMsgFromDB(startMsgId, 1);
                            if (wihleMessage == null || wihleMessage.size() == 0) {
                                isWihle = false;
                                isAddGoods = false;
                            } else {
                                EMMessage lastMessage = wihleMessage.get(0);
                                String goodsId = lastMessage.getStringAttribute("goodsId", "");
                                if (System.currentTimeMillis() - lastMessage.getMsgTime() > timeValue) {
                                    isWihle = false;
                                    isAddGoods = true;
                                } else {
                                    if (!TextUtils.isEmpty(goodsId) && !goodsId.equals(mModel.getGoodsId())) {
                                        isWihle = false;
                                        isAddGoods = true;
                                    }
                                }
                            }
                        }
                        return isAddGoods;
                    }
                } else {
                    String goodsId = goodsMessage.getStringAttribute("goodsId", "");
                    if (goodsId.equals(mModel.getGoodsId())) {//如果相等，判断2个商品间隔时间
                        if (System.currentTimeMillis() - goodsMessage.getMsgTime() > timeValue) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return true;
                    }
                }
            }
        }
    }

    @Override
    protected void inToShop() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ShopActivity.class);
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), activity.getmGoodsModel().getShopId());
        startActivity(intent);
    }


    @Override
    protected void startVoiceCall() {
        if (!EMClient.getInstance().isConnected()) {
            Toast.makeText(getActivity(), com.hyphenate.easeui.R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(getActivity(), VoiceCallActivity.class).putExtra("username", activity.getmGoodsModel().getShopImName())
                    .putExtra("isComingCall", false));
            // voiceCallBtn.setEnabled(false);

            inputMenu.hideExtendMenuContainer();
        }

    }

    @Override
    protected void startVideoCall() {
        if (!EMClient.getInstance().isConnected()) {
            Toast.makeText(getActivity(), com.hyphenate.easeui.R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(getActivity(), VideoCallActivity.class).putExtra("username", activity.getmGoodsModel().getShopImName())
                    .putExtra("isComingCall", false));
            // videoCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
        }
    }
}
