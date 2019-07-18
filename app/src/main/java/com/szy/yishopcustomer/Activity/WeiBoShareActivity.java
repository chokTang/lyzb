package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.*;
import android.view.Window;
import android.view.WindowManager;

import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.szy.common.Fragment.CommonFragment;
import com.szy.common.Interface.SimpleImageLoadingListener;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.Util.WeiBoUtils;

import java.util.ArrayList;

/**
 * Created by 宗仁 on 16/8/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class WeiBoShareActivity extends YSCBaseActivity implements WbShareCallback {

    private WbShareHandler shareHandler;
    private WeiboMultiMessage weiboMessage;

    @Override
    public CommonFragment createFragment() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final ArrayList<String> shareData = intent.getStringArrayListExtra(
                Key.KEY_SHARE_DATA.getValue());
/*        String t = Utils.urlOfImage(shareData.get(3));
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pl_image);
        if(!Utils.isNull(t)){
            bitmap = ImageLoader.getInstance().loadImageSync(t);
        }else {

        }*/
//        weiboMessage = new WeiboMultiMessage();
        ImageLoader.loadImage(Utils.urlOfImage(shareData.get(3)), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                if (Utils.isNull(loadedImage)) {
                    loadedImage = BitmapFactory.decodeResource(getResources(), R.mipmap.pl_image);
                }
//                weiboMessage.mediaObject = WeiBoUtils.getWebpageObj(WeiBoShareActivity.this, shareData.get(0),
//                        shareData.get(1), shareData.get(2), Utils.shrinkImage(loadedImage, 50, 50));

                weiboMessage = WeiBoUtils.sendMultiMessage(WeiBoShareActivity.this, shareData.get(0),
                        shareData.get(1), shareData.get(2), Utils.shrinkImage(loadedImage, 200, 200));

                shareHandler = new WbShareHandler(WeiBoShareActivity.this);
                shareHandler.registerApp();

                shareHandler.shareMessage(weiboMessage, true);
            }

        });

        mActionBar.hide();

        overridePendingTransition(R.anim.pop_enter_anim, R.anim.pop_exit_anim);
        Window win = this.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        shareHandler.doResultIntent(intent,this);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.pop_enter_anim, R.anim.pop_exit_anim);
    }

    @Override
    public void onWbShareSuccess() {
        Utils.shareFinish(this, Macro.SHARE_SUCCEED);
    }

    @Override
    public void onWbShareCancel() {
        Utils.shareFinish(this, Macro.SHARE_CANCELED);
    }

    @Override
    public void onWbShareFail() {
        Utils.shareFinish(this, Macro.SHARE_FAILED);
    }
}
