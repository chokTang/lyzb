package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.like.utilslib.image.BitmapUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.common.WeiXinMinModel;
import com.lyzb.jbx.util.AppCommonUtil;
import com.szy.common.Fragment.CommonFragment;
import com.szy.common.Interface.SimpleImageLoadingListener;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.Util.WeiXinUtils;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by 宗仁 on 16/8/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class WeiXinShareActivity extends YSCBaseActivity {

    private int mType = 0;//0是默认的 1是微信小程序处理

    @Override
    public CommonFragment createFragment() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final int scene = intent.getIntExtra(Key.KEY_SCENE.getValue(),
                SendMessageToWX.Req.WXSceneSession);
        mType = intent.getIntExtra("mType", 0);

        if (mType == 1) {//处理微信小程序
            WeiXinMinModel model = (WeiXinMinModel) intent.getSerializableExtra(Key.KEY_SHARE_DATA.getValue());
            WeiXinUtils.shareWXMiniProject(WeiXinShareActivity.this,
                    model.getLowVersionUrl(), model.getTitle(), model.getDescribe(), model.getShareUrl(),
                    BitmapUtil.zoomImage(BitmapUtil.byte2Bitmap(AppCommonUtil.shareBitmp),500,400));
            AppCommonUtil.shareBitmp = null;
        } else {
            final ArrayList<String> shareData = intent.getStringArrayListExtra(
                    Key.KEY_SHARE_DATA.getValue());
            ImageLoader.loadImage(Utils.urlOfImage(shareData.get(3)), new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    if (Utils.isNull(loadedImage)) {
                        loadedImage = BitmapFactory.decodeResource(getResources(), R.mipmap.pl_image);
                    }

                    WeiXinUtils.shareUrl(WeiXinShareActivity.this, shareData.get(0), shareData.get(1),
                            shareData.get(2), BitmapUtil.compressScale(loadedImage, 32), scene);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, String failReason) {
                    super.onLoadingFailed(imageUri, view, failReason);
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                    WeiXinUtils.shareUrl(WeiXinShareActivity.this, shareData.get(0), shareData.get(1),
                            shareData.get(2), bitmap, scene);
                }
            });
        }

        mActionBar.hide();
        overridePendingTransition(R.anim.pop_enter_anim, R.anim.pop_exit_anim);
        Window win = this.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
    }

    @Subscribe
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_WEIXIN_SHARE:
                String code = event.getMessage();
                switch (Integer.parseInt(code)) {
                    case BaseResp.ErrCode.ERR_OK:
                        Utils.shareFinish(this, Macro.SHARE_SUCCEED);
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        Utils.shareFinish(this, Macro.SHARE_CANCELED);
                        break;
                    default:
                        Utils.shareFinish(this, Macro.SHARE_FAILED);
                        break;
                }
                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //如果已经成功打开微信在返回的时候是不会执行到这个onresume的
        //如果openWeixin等于ture，说明执行了打开了微信的动作，但是没有进入微信便返回了
        if (openWeixin) {
            finish();
        }
    }


    boolean openWeixin = false;

    @Override
    protected void onPause() {
        super.onPause();
        //进入这个方法说明已经执行调用微信了，用一个值记录
        openWeixin = true;
        AppCommonUtil.shareBitmp=null;
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.pop_enter_anim, R.anim.pop_exit_anim);
    }
}
