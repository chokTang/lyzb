package com.szy.yishopcustomer.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.szy.yishopcustomer.Constant.Config;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by 宗仁 on 16/8/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class WeiXinUtils {
    private static final int THUMB_SIZE = 150;
    private static String TYPE_TEXT = "TYPE_TEXT";
    private static String TYPE_IMAGE = "TYPE_IMAGE";
    private static String TYPE_MUSIC = "TYPE_MUSIC";
    private static String TYPE_VIDEO = "TYPE_VIDEO";
    private static String TYPE_URL = "TYPE_URL";

    public static IWXAPI oath(Context context, String state) {
        final SendAuth.Req request = new SendAuth.Req();
        request.scope = "snsapi_userinfo";
        request.state = state;
        return sendRequest(context, request);

    }

    public static IWXAPI shareImage(Context context, Bitmap image, int scene) {
        WXImageObject object = new WXImageObject(image);

        WXMediaMessage message = new WXMediaMessage();
        message.mediaObject = object;

        Bitmap thumb = Bitmap.createScaledBitmap(image, THUMB_SIZE, THUMB_SIZE, true);
        image.recycle();
        message.thumbData = toByteArray(thumb, true);

        return sendRequest(context, message, scene, TYPE_IMAGE);
    }

    public static IWXAPI shareMusic(Context context, String url, String title, String
            description, Bitmap bitmap, int scene) {
        WXMusicObject object = new WXMusicObject();
        object.musicUrl = url;

        WXMediaMessage message = new WXMediaMessage();
        message.mediaObject = object;
        message.title = title;
        message.description = description;
        message.thumbData = toByteArray(bitmap, true);

        return sendRequest(context, message, scene, TYPE_MUSIC);
    }

    public static IWXAPI shareText(Context context, String text, int scene) {
        WXTextObject object = new WXTextObject();
        object.text = text;

        WXMediaMessage message = new WXMediaMessage();
        message.mediaObject = object;
        message.description = text;

        return sendRequest(context, message, scene, TYPE_TEXT);
    }

    public static IWXAPI shareUrl(Context context, String url, String title, String description,
                                  Bitmap bitmap, int scene) {
        WXWebpageObject object = new WXWebpageObject();
        object.webpageUrl = url;

        WXMediaMessage message = new WXMediaMessage();
        message.mediaObject = object;
        message.title = title;
        if (!TextUtils.isEmpty(description)) {
            if (description.length() > 30) {
                message.description = description.substring(0, 30) + "...";
            } else {
                message.description = description;
            }
        } else {
            message.description = title;
        }
        message.thumbData = toByteArray(bitmap, true);

        return sendRequest(context, message, scene, TYPE_URL);
    }

    public static IWXAPI shareVideo(Context context, String url, String title, String
            description, Bitmap bitmap, int scene) {
        WXVideoObject object = new WXVideoObject();
        object.videoUrl = url;

        WXMediaMessage message = new WXMediaMessage();
        message.mediaObject = object;
        message.title = title;
        message.description = description;
        message.thumbData = toByteArray(bitmap, true);

        return sendRequest(context, message, scene, TYPE_VIDEO);
    }

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System
                .currentTimeMillis();
    }

    private static IWXAPI sendRequest(Context context, SendAuth.Req request) {
        IWXAPI api = WXAPIFactory.createWXAPI(context, Config.WEIXIN_APP_ID);
        api.sendReq(request);
        return api;
    }

    private static IWXAPI sendRequest(Context context, WXMediaMessage message, int scene, String
            type) {
        IWXAPI api = WXAPIFactory.createWXAPI(context, Config.WEIXIN_APP_ID);
        SendMessageToWX.Req request = new SendMessageToWX.Req();
        request.transaction = buildTransaction(type);
        request.message = message;
        request.scene = scene;
        api.sendReq(request);
        return api;
    }

    private static byte[] toByteArray(final Bitmap bitmap, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, output);
        if (needRecycle) {
            bitmap.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 分享微信小程序
     *
     * @param context
     * @param pageUrl
     * @param title
     * @param desc
     * @param bitmap
     */
    public static void shareWXMiniProject(Context context, String pageUrl, String title, String desc, String uerId, Bitmap bitmap) {
        IWXAPI api = WXAPIFactory.createWXAPI(context, Config.WEIXIN_APP_ID);
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = pageUrl; // 兼容低版本的网页链接
        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = Config.WXMINI_APP_ID;     // 小程序原始id
//        miniProgramObj.path = "/pages/media";            //小程序页面路径
        miniProgramObj.path = uerId;            //小程序页面路径 路径
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = title;                    // 小程序消息title
        msg.description = desc;               // 小程序消息desc
        msg.thumbData = toByteArray(bitmap, true);                      // 小程序消息封面图片，小于128k

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("miniProgram");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
        api.sendReq(req);
    }
}
