package com.szy.yishopcustomer.Fragment.Im;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.util.PathUtil;
import com.lyzb.jbx.activity.GoodToMeActivity;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Activity.im.GroupDetailActivity;
import com.szy.yishopcustomer.Activity.im.ImCommonActivity;
import com.szy.yishopcustomer.Activity.im.ImageGridActivity;
import com.szy.yishopcustomer.Activity.im.VideoCallActivity;
import com.szy.yishopcustomer.Activity.im.VoiceCallActivity;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.im.ImHeaderGoodsModel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by Administrator on 2018/7/20.
 */

public class ImCommonFragment extends EaseChatFragment {

    private ImHeaderGoodsModel mModel;
    private ImCommonActivity activity;
    private final static long timeValue = 1 * 60 * 60 * 1000;//间隔1个小时

    private int REQUEST_WRITE_EXTERNAL_STORAGE = 100102;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ImCommonActivity) context;
    }

    @Override
    protected void setUpView() {
        setChatFragmentHelper(new EaseChatFragmentHelper() {
            @Override
            public void onSetMessageAttributes(EMMessage message) {

            }

            @Override
            public void onEnterToChatDetails() {

            }

            @Override
            public void onAvatarClick(String username) {
                GoodToMeActivity.startIntoCard(getContext(),username.replaceAll("jbx",""));
            }

            @Override
            public void onAvatarLongClick(String username) {

            }

            @Override
            public boolean onMessageBubbleClick(EMMessage message) {
                return false;
            }

            @Override
            public void onMessageBubbleLongClick(EMMessage message) {

            }

            @Override
            public boolean onExtendMenuItemClick(int itemId, View view) {
                return false;
            }

            @Override
            public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
                return null;
            }
        });
        super.setUpView();
        mModel = (ImHeaderGoodsModel) fragmentArgs.getSerializable("model");

        titleBar.setRightLayoutVisibility(View.GONE);

        if (!isAddShop()) {//如果不可以添加商品，就不需要添加了
            return;
        }

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

            if (message.getChatType() == EMMessage.ChatType.Chat) {
                message.setAttribute(EaseConstant.EXTRA_FROM_USERID, App.getInstance().userId);
            } else if (message.getChatType() == EMMessage.ChatType.GroupChat) {
                message.setAttribute(EaseConstant.EXTRA_FROM_USERID, EaseConstant.CHAT_USER_ID);
            }

            message.setAttribute(EaseConstant.EXTRA_TO_NICKNAME, activity.getmGoodsModel().getShopName());
            message.setAttribute(EaseConstant.EXTRA_TO_USERID, activity.getmGoodsModel().getShopId());
            message.setAttribute(EaseConstant.EXTRA_TO_HEADER, activity.getmGoodsModel().getShopHeadimg());
            message.setStatus(EMMessage.Status.SUCCESS);


            if(mModel.getChatType()==EaseConstant.CHATTYPE_SINGLE){
                conversation.appendMessage(message);
            }

            messageList.refreshSelectLast();

            if (mModel.getChatType() == EaseConstant.CHATTYPE_SINGLE) {
                activity.sendMessageToServie(message, "", true);
            } else {
                activity.sendMessageToServie(message, "", false);
            }
        }
    }

    /****
     * 最终发送消息
     * @param message
     */
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

        String headimg = null;

        if (message.getChatType() == EMMessage.ChatType.GroupChat) {
            message.setAttribute(EaseConstant.EXTRA_FROM_HEADER, SharedPreferencesUtils.getParam(activity, Key.IM_USERHEADING.name(), "").toString());
            headimg = SharedPreferencesUtils.getParam(activity, Key.IM_USERHEADING.name(), "").toString();

        } else {

            if (!TextUtils.isEmpty(App.getInstance().headimg)) {
                message.setAttribute(EaseConstant.EXTRA_FROM_HEADER, Utils.urlOfImage(App.getInstance().headimg));
            } else {
                message.setAttribute(EaseConstant.EXTRA_FROM_HEADER, SharedPreferencesUtils.getParam(activity, Key.IM_USERHEADING.name(), "").toString());
            }
        }

        message.setAttribute(EaseConstant.EXTRA_FROM_USERID, EaseConstant.CHAT_USER_ID);

        message.setAttribute(EaseConstant.EXTRA_TO_NICKNAME, activity.getmGoodsModel().getShopName());
        message.setAttribute(EaseConstant.EXTRA_TO_USERID, activity.getmGoodsModel().getShopImName());
        message.setAttribute(EaseConstant.EXTRA_TO_HEADER, activity.getmGoodsModel().getShopHeadimg());
        message.setStatus(EMMessage.Status.SUCCESS);

        if(mModel.getChatType()==EaseConstant.CHATTYPE_SINGLE){
            conversation.appendMessage(message);
        }

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
    protected void inToGroupDetail() {
        GroupDetailActivity.isExitGroup = false;
        Intent intent = new Intent(getActivity(), GroupDetailActivity.class);
        intent.putExtra("groupId", mModel.getShopId());              //群组ID
        intent.putExtra("userId", EaseConstant.CHAT_USER_ID);        //当前用户ID
        startActivity(intent);
    }

    /***
     * 视频文件选择
     */
    @Override
    protected void chooseVideo() {

        //动态判断写入权限是否已授权
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(getActivity(), "请授权此权限,否则无法发送视频文件", Toast.LENGTH_SHORT).show();
            } else {
                //申请权限
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
            }

        } else {
            //已授权
            Intent intent = new Intent(getActivity(), ImageGridActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
        }
    }

    /***
     * 语音聊天
     */
    @Override
    protected void startVoiceCall() {
        if (!EMClient.getInstance().isConnected()) {
            Toast.makeText(getActivity(), com.hyphenate.easeui.R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(getActivity(), VoiceCallActivity.class).putExtra("username", activity.getmGoodsModel().getShopImName())
                    .putExtra("shopName", activity.getmGoodsModel().getShopName())
                    .putExtra("shopHeadUrl", activity.getmGoodsModel().getShopHeadimg())
                    .putExtra("isComingCall", false));
            // voiceCallBtn.setEnabled(false);

            inputMenu.hideExtendMenuContainer();
        }

    }

    /***
     * 视频聊天
     */
    @Override
    protected void startVideoCall() {
        if (!EMClient.getInstance().isConnected()) {
            Toast.makeText(getActivity(), com.hyphenate.easeui.R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(getActivity(), VideoCallActivity.class).putExtra("username", activity.getmGoodsModel().getShopImName())
                    .putExtra("shopName", activity.getmGoodsModel().getShopName())
                    .putExtra("shopHeadUrl", activity.getmGoodsModel().getShopHeadimg())
                    .putExtra("isComingCall", false));
            // videoCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
            } else {

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_VIDEO) {
            if (data != null) {
                int duration = data.getIntExtra("dur", 0);
                String videoPath = data.getStringExtra("path");
                File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
                    ThumbBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();
                    sendVideoMessage(videoPath, file.getAbsolutePath(), duration);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (GroupDetailActivity.isExitGroup) {
            getActivity().finish();

            GroupDetailActivity.isExitGroup = false;
        }
    }
}
