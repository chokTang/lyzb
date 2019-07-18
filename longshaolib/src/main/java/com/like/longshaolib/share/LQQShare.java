package com.like.longshaolib.share;

import android.app.Activity;
import android.os.Bundle;

import com.like.longshaolib.app.LongshaoAPP;
import com.like.longshaolib.app.config.ConfigType;
import com.like.longshaolib.share.qq.QAPPBulilder;
import com.like.longshaolib.share.qq.QImageBuilder;
import com.like.longshaolib.share.qq.QImageTextBuilder;
import com.like.longshaolib.share.inter.IShareResult;
import com.like.longshaolib.share.qq.QZImageTextBuilder;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;

/**
 * QQ分享
 * Created by longshao on 2017/9/8.
 */

public class LQQShare {

    private Tencent mTencent;
    private String mQQShareTitle;
    private String mQQShareContent;
    private String mQQShareTargetUrl;
    private String mQQShareImageUrl;
    private ArrayList<String> mQQZoneImageUrl;
    private String mQQShareAPPName;//分享的APPname
    private int mQQExtInt;//分享的额外2中选项分享额外选项，两种类型可选
    // （默认是不隐藏分享到QZone按钮且不自动打开分享到QZone的对话框）：LQQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN，
    // 分享时自动打开分享到QZone的对话框。LQQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE，分享时隐藏分享到QZone按钮
    private IShareResult mShareResult;

    public LQQShare(String mQQShareTitle,
                    String mQQShareContent,
                    String mQQShareTargetUrl,
                    String mQQShareImageUrl,
                    String mQQShareAPPName,
                    int mQQExtInt,
                    IShareResult mShareResult,
                    ArrayList<String> mQQZoneImageUrl) {
        this.mQQShareTitle = mQQShareTitle;
        this.mQQShareContent = mQQShareContent;
        this.mQQShareTargetUrl = mQQShareTargetUrl;
        this.mQQShareImageUrl = mQQShareImageUrl;
        this.mQQShareAPPName = mQQShareAPPName;
        this.mQQExtInt = mQQExtInt;
        this.mShareResult = mShareResult;
        this.mQQZoneImageUrl = mQQZoneImageUrl;

        mTencent = Tencent.createInstance(LongshaoAPP.getConfiguration(ConfigType.QQ_APP_ID).toString(), LongshaoAPP.getApplicationContext());
    }

    public static QImageTextBuilder BuilderImageText() {
        return new QImageTextBuilder();
    }

    public static QImageBuilder BuilderImage() {
        return new QImageBuilder();
    }

    public static QAPPBulilder BuilderApp() {
        return new QAPPBulilder();
    }

    public static QZImageTextBuilder BuilderQzImageText() {
        return new QZImageTextBuilder();
    }

    /**
     * 分享图文消息
     */
    public void shareImageText() {
        final Bundle params = new Bundle();
        params.putInt(com.tencent.connect.share.QQShare.SHARE_TO_QQ_KEY_TYPE, com.tencent.connect.share.QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        if (mQQShareTitle == null || mQQShareTitle.isEmpty()) {
            throw new RuntimeException("QQ分享标题必填");
        } else {
            params.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_TITLE, mQQShareTitle);
        }

        if (mQQShareContent != null && !mQQShareContent.isEmpty()) {
            params.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_SUMMARY, mQQShareContent);
        }

        if (mQQShareTargetUrl != null && !mQQShareTargetUrl.isEmpty()) {
            params.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_TARGET_URL, mQQShareTargetUrl);
        } else {
            throw new RuntimeException("QQ分享目标URL必填");
        }

        if (mQQShareImageUrl != null && !mQQShareImageUrl.isEmpty()) {
            params.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_IMAGE_URL, mQQShareImageUrl);
        }

        if (mQQShareAPPName != null && !mQQShareAPPName.isEmpty()) {
            params.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_APP_NAME, mQQShareAPPName);
        }

        if (mQQExtInt != 0) {
            params.putInt(com.tencent.connect.share.QQShare.SHARE_TO_QQ_EXT_INT, mQQExtInt);
        }

        mTencent.shareToQQ((Activity) LongshaoAPP.getConfiguration(ConfigType.ACTIVITY), params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                if (mShareResult != null) {
                    mShareResult.onShareSuccess();
                }
            }

            @Override
            public void onError(UiError uiError) {
                if (mShareResult != null) {
                    mShareResult.onShareError(uiError.errorMessage);
                }
            }

            @Override
            public void onCancel() {
                if (mShareResult != null) {
                    mShareResult.onShareCancle();
                }
            }
        });
    }

    /**
     * 分享纯图片
     */
    public void shareImage() {
        final Bundle params = new Bundle();
        params.putInt(com.tencent.connect.share.QQShare.SHARE_TO_QQ_KEY_TYPE, com.tencent.connect.share.QQShare.SHARE_TO_QQ_TYPE_IMAGE);

        if (mQQShareImageUrl != null && !mQQShareImageUrl.isEmpty()) {
            params.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, mQQShareImageUrl);
        } else {
            throw new RuntimeException("QQ纯图片分享，图片路径必填");
        }

        if (mQQShareAPPName != null && !mQQShareAPPName.isEmpty()) {
            params.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_APP_NAME, mQQShareAPPName);
        }

        if (mQQExtInt != 0) {
            params.putInt(com.tencent.connect.share.QQShare.SHARE_TO_QQ_EXT_INT, mQQExtInt);
        }

        mTencent.shareToQQ((Activity) LongshaoAPP.getConfiguration(ConfigType.ACTIVITY), params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                if (mShareResult != null) {
                    mShareResult.onShareSuccess();
                }
            }

            @Override
            public void onError(UiError uiError) {
                if (mShareResult != null) {
                    mShareResult.onShareError(uiError.errorMessage);
                }
            }

            @Override
            public void onCancel() {
                if (mShareResult != null) {
                    mShareResult.onShareCancle();
                }
            }
        });
    }

    /**
     * 分享应用
     */
    public void shareAPP() {
        final Bundle params = new Bundle();
        params.putInt(com.tencent.connect.share.QQShare.SHARE_TO_QQ_KEY_TYPE, com.tencent.connect.share.QQShare.SHARE_TO_QQ_TYPE_APP);
        if (mQQShareTitle == null || mQQShareTitle.isEmpty()) {
            throw new RuntimeException("QQ分享标题必填");
        } else {
            params.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_TITLE, mQQShareTitle);
        }

        if (mQQShareContent != null && !mQQShareContent.isEmpty()) {
            params.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_SUMMARY, mQQShareContent);
        }

        if (mQQShareImageUrl != null && !mQQShareImageUrl.isEmpty()) {
            params.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_IMAGE_URL, mQQShareImageUrl);
        }

        if (mQQShareAPPName != null && !mQQShareAPPName.isEmpty()) {
            params.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_APP_NAME, mQQShareAPPName);
        }

        if (mQQExtInt != 0) {
            params.putInt(com.tencent.connect.share.QQShare.SHARE_TO_QQ_EXT_INT, mQQExtInt);
        }

        mTencent.shareToQQ((Activity) LongshaoAPP.getConfiguration(ConfigType.ACTIVITY), params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                if (mShareResult != null) {
                    mShareResult.onShareSuccess();
                }
            }

            @Override
            public void onError(UiError uiError) {
                if (mShareResult != null) {
                    mShareResult.onShareError(uiError.errorMessage);
                }
            }

            @Override
            public void onCancel() {
                if (mShareResult != null) {
                    mShareResult.onShareCancle();
                }
            }
        });
    }

    /**
     * 分享到QQ空间 目前只支持图文形式分享
     */
    public void shareQZoneImageText() {
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        if (mQQShareTitle == null || mQQShareTitle.isEmpty()) {
            throw new RuntimeException("QQ分享标题必填");
        } else {
            params.putString(QzoneShare.SHARE_TO_QQ_TITLE, mQQShareTitle);
        }

        if (mQQShareContent != null && !mQQShareContent.isEmpty()) {
            params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, mQQShareContent);
        }

        if (mQQShareTargetUrl != null && !mQQShareTargetUrl.isEmpty()) {
            params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, mQQShareTargetUrl);
        } else {
            throw new RuntimeException("QQ分享目标URL必填");
        }

        if (mQQZoneImageUrl != null && mQQZoneImageUrl.size() > 0) {
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, mQQZoneImageUrl);
        }

        mTencent.shareToQzone((Activity) LongshaoAPP.getConfiguration(ConfigType.ACTIVITY), params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                if (mShareResult != null) {
                    mShareResult.onShareSuccess();
                }
            }

            @Override
            public void onError(UiError uiError) {
                if (mShareResult != null) {
                    mShareResult.onShareError(uiError.errorMessage);
                }
            }

            @Override
            public void onCancel() {
                if (mShareResult != null) {
                    mShareResult.onShareCancle();
                }
            }
        });
    }
}
