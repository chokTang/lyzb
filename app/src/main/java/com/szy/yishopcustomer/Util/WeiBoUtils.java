package com.szy.yishopcustomer.Util;

import android.app.Activity;
import android.graphics.Bitmap;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.utils.Utility;

/**
 * Created by 宗仁 on 16/8/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class WeiBoUtils {

//    public static IWeiboShareAPI shareImage(Activity activity, Bitmap bitmap) {
//        ImageObject object = new ImageObject();
//        object.setImageObject(bitmap);
//        WeiboMessage message = new WeiboMessage();
//        message.mediaObject = object;
//        return sendRequest(activity, message);
//    }
//
//    public static IWeiboShareAPI shareMusic(Activity activity, String url, String title, String
//            description, Bitmap bitmap) {
//        MusicObject object = new MusicObject();
//        object.identify = Utility.generateGUID();
//        object.title = title;
//        object.description = description;
//        object.setThumbImage(bitmap);
//        object.actionUrl = url;
//        object.dataUrl = "www.weibo.com";
//        object.dataHdUrl = "www.weibo.com";
//        object.duration = 10;
//        object.defaultText = "分享链接";
//        WeiboMessage message = new WeiboMessage();
//        message.mediaObject = object;
//        return sendRequest(activity, message);
//    }
//
//    public static IWeiboShareAPI shareText(Activity activity, String text) {
//        TextObject object = new TextObject();
//        object.text = text;
//        WeiboMessage message = new WeiboMessage();
//        message.mediaObject = object;
//        return sendRequest(activity, message);
//    }
//
//    public static IWeiboShareAPI shareWebPage(Activity activity, String url, String title, String
//            description, Bitmap bitmap) {
//        WebpageObject object = new WebpageObject();
//        object.identify = Utility.generateGUID();
//        object.title = title;
//        object.description = description;
//        object.setThumbImage(bitmap);
//        object.actionUrl = url;
//        object.defaultText = "分享链接";
//        WeiboMessage message = new WeiboMessage();
//        message.mediaObject = object;
//        return sendRequest(activity, message);
//    }
//
//    private static IWeiboShareAPI sendRequest(Activity activity, WeiboMessage weiboMessage) {
//        IWeiboShareAPI api = WeiboShareSDK.createWeiboAPI(activity, Config.WEIBO_KEY);
//        api.registerApp();
//        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
//        request.transaction = String.valueOf(System.currentTimeMillis());
//        request.message = weiboMessage;
//        api.sendRequest(activity, request);
//        return api;
//    }


    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    public static WebpageObject getWebpageObj(Activity activity, String url, String title, String
            description, Bitmap bitmap) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = title;
        mediaObject.description = description;
        // 设置 Bitmap 类型的图片到视频对象里        设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = url;
        mediaObject.defaultText = "分享链接";
        return mediaObject;
    }

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     */
    public static WeiboMultiMessage sendMultiMessage(Activity activity, String url, String title, String
            description, Bitmap bitmap) {


        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();

        TextObject textObject = new TextObject();
        textObject.text = title;
        textObject.title = title;
        textObject.actionUrl = url;
        weiboMessage.textObject = textObject;

        ImageObject imageObject = new ImageObject();
        imageObject.setImageObject(bitmap);
        weiboMessage.imageObject = imageObject;

        weiboMessage.mediaObject = getWebpageObj(activity,url,title,description,bitmap);
//        if (multiImageCheckbox.isChecked()) {
//            weiboMessage.multiImageObject = getMultiImageObject();
//        }
//        if (videoCheckbox.isChecked()) {
//            weiboMessage.videoSourceObject = getVideoObject();
//        }

        return weiboMessage;
    }
}
