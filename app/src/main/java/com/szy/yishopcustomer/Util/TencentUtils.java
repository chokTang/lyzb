package com.szy.yishopcustomer.Util;

import android.app.Activity;
import android.os.Bundle;

import com.szy.yishopcustomer.Constant.Config;
import com.lyzb.jbx.R;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;

/**
 * Created by 宗仁 on 16/8/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class TencentUtils {

    public static Tencent shareAppToQQ(Activity activity, String url, String title, String
            summary, String imageUrl, IUiListener listener) {
        Tencent tencent = Tencent.createInstance(Config.TENCENT_ID, activity);
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, activity.getString(R.string.app_name));
        tencent.shareToQQ(activity, params, listener);
        return tencent;
    }

    public static Tencent shareLocalImageToQQ(Activity activity, String title, String summary,
                                              String targetUrl, int resourceId, IUiListener
                                                      listener) {
        Tencent tencent = Tencent.createInstance(Config.TENCENT_ID, activity);
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, Utils.getUri(activity, resourceId)
                .getPath());
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, activity.getResources().getString(R.string
                .app_name));
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        tencent.shareToQQ(activity, params, listener);
        return tencent;
    }

    public static Tencent shareLocalImageToQQ(Activity activity, String imageUrl, IUiListener
            listener) {
        Tencent tencent = Tencent.createInstance(Config.TENCENT_ID, activity);
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, activity.getResources().getString(R.string
                .app_name));
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        tencent.shareToQQ(activity, params, listener);
        return tencent;
    }

    public static Tencent shareLocalImageToQQ(Activity activity, String title, String summary,
                                              String targetUrl, String imageUrl, IUiListener
                                                      listener) {
        Tencent tencent = Tencent.createInstance(Config.TENCENT_ID, activity);
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, activity.getResources().getString(R.string
                .app_name));
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        tencent.shareToQQ(activity, params, listener);
        return tencent;
    }

    public static Tencent shareMusicToQQ(Activity activity, String url, String title, String
            summary, String imageUrl, IUiListener listener) {
        Tencent tencent = Tencent.createInstance(Config.TENCENT_ID, activity);
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);
        params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, url);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, activity.getResources().getString(R.string
                .app_name));
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        tencent.shareToQQ(activity, params, listener);
        return tencent;
    }

    public static Tencent shareToQZone(Activity activity, String url, String title, String
            summary, ArrayList<String> imageList, IUiListener listener) {
        Tencent tencent = Tencent.createInstance(Config.TENCENT_ID, activity);
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare
                .SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);//必填
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageList);
        tencent.shareToQzone(activity, params, listener);
        return tencent;
    }

    public static Tencent shareWebImageToQQ(Activity activity, String title, String summary,
                                            String targetUrl, String imageUrl, IUiListener
                                                    listener) {
        Tencent tencent = Tencent.createInstance(Config.TENCENT_ID, activity);
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, activity.getResources().getString(R.string
                .app_name));
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        tencent.shareToQQ(activity, params, listener);
        return tencent;
    }
}
