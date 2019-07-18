package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.szy.common.Fragment.CommonFragment;
import com.szy.common.Util.JSON;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Util.TencentUtils;
import com.szy.yishopcustomer.Util.Utils;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;

/**
 * Created by 宗仁 on 16/8/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class TencentShareActivity extends YSCBaseActivity implements IUiListener {
    private static final String TAG = "TencentShareActivity";
    public Tencent mTencent;

    @Override
    public CommonFragment createFragment() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        if (intent.getIntExtra(ShareIngotActivity.SHARE_INGOT, 0) != 0) {
            /***元宝 分享流程**/

            mTencent = TencentUtils.shareLocalImageToQQ(this, intent.getStringExtra(ShareIngotActivity.IMG_PATH), TencentShareActivity.this);
            mActionBar.hide();
        } else {

            ArrayList<String> shareData = intent.getStringArrayListExtra(Key.KEY_SHARE_DATA.getValue());


            mTencent = TencentUtils.shareLocalImageToQQ(TencentShareActivity.this,
                    shareData.get(1), shareData.get(2), shareData.get(0), shareData.get(3),
                    TencentShareActivity.this);
            mActionBar.hide();
        }

/*        overridePendingTransition(R.anim.pop_enter_anim, R.anim.pop_exit_anim);
        Window win = this.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);*/
    }

    @Override
    public void onComplete(Object object) {
        Utils.shareFinish(this, Macro.SHARE_SUCCEED);
    }

    @Override
    public void onError(UiError error) {

        Utils.shareFinish(this, Macro.SHARE_FAILED);
    }

    @Override
    public void onCancel() {
        Utils.shareFinish(this, Macro.SHARE_CANCELED);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, TencentShareActivity.this);
    }
/*
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.pop_enter_anim, R.anim.pop_exit_anim);
    }*/
}