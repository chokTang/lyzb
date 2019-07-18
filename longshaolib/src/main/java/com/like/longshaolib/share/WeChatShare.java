package com.like.longshaolib.share;

import android.graphics.Bitmap;

import com.like.longshaolib.wechat.LongWeChat;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;

import java.io.ByteArrayOutputStream;

/**
 * Created by longshao on 2017/9/8.
 */

public class WeChatShare {

    private static final int THUMB_SIZE = 150;

    private WeChatShare() {
    }

    private static class Holder {
        private static WeChatShare INTANCE = new WeChatShare();
    }

    public final WeChatShare getIntance() {
        return Holder.INTANCE;
    }

    /**
     * 分享到朋友聊天页面
     *
     * @param content 内容
     * @param descri  描述
     */
    public final void shareToChatText(String content, String descri) {
        shareToText(content, descri, true);
    }

    /**
     * 分享到朋友圈页面
     *
     * @param content 内容
     * @param descri  描述
     */
    public final void shareToFriendText(String content, String descri) {
        shareToText(content, descri, false);
    }

    /**
     * 微信纯图片分享到聊天页面
     *
     * @param bitmap
     */
    public final void shareToChatImage(Bitmap bitmap) {
        shareToImage(bitmap, true);
    }

    /**
     * 微信纯图片分享到朋友圈
     *
     * @param bitmap
     */
    public final void shareToFriendImage(Bitmap bitmap) {
        shareToImage(bitmap, false);
    }

    /**
     * 分享纯文本消息
     *
     * @param content
     * @param descri
     * @param isChat  是否分享到聊天页面
     */
    private final void shareToText(String content, String descri, boolean isChat) {
        WXTextObject textObject = new WXTextObject();
        textObject.text = content;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        msg.description = descri;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = isChat ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;

        LongWeChat.getIntance().getWXAPI().sendReq(req);
    }

    /**
     * 分享纯图片
     *
     * @param bitmap
     * @param isChat 是否分享到聊天页面
     */
    private final void shareToImage(Bitmap bitmap, boolean isChat) {
        if (bitmap == null)
            return;
        WXImageObject imgObject = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObject;

        Bitmap thumbBitmap = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
        bitmap.recycle();
        msg.thumbData = bitmapToByteArray(thumbBitmap, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = isChat ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;

        LongWeChat.getIntance().getWXAPI().sendReq(req);
    }

    /**
     * 初始化类型
     *
     * @param type
     * @return
     */
    private final String buildTransaction(String type) {
        return type + String.valueOf(System.currentTimeMillis());
    }

    /**
     * 微信调用
     *
     * @param bmp
     * @param needRecycle
     * @return
     */
    private final byte[] bitmapToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
