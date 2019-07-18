package com.szy.yishopcustomer.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.*;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Other.BarcodePatternModel;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.BrowserUrlManager;
import com.szy.yishopcustomer.Util.RxPhotoTool;
import com.szy.yishopcustomer.Util.Utils;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.decoding.CaptureActivityHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Smart on 17/7/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ScanActivity extends YSCBaseActivity {

    //正常扫码模式
    public static final int TYPE_DEFAULT = 1;
    //给商户支付扫码
    public static final int TYPE_PAYMENT = 2;
    //同城生活 扫码 店铺二维码  商家收款码
    public static final int TYPE_CITYLIFE = 3;

    private static final int REQUEST_CODE = 100;
    private static final int PARSE_BARCODE_FAIL = 300;
    private static final int PARSE_BARCODE_SUC = 200;

    public static boolean isOpen = false;
    private CaptureFragment captureFragment;

    private ImageView scanLine;

    BrowserUrlManager mBrowserUrlManager = new BrowserUrlManager();
    Pattern pattern = Pattern.compile("^(\\d+)$");

    private View framelayout_parent;
    private String code;
    private Context mContext;

    private int type = TYPE_DEFAULT;
    /**
     * 图片的路径
     */
    private String photoPath;

    private String shopId;

    private boolean isHasSurface = false;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_scan_qr);

        framelayout_parent = findViewById(R.id.framelayout_parent);

        captureFragment = new CaptureFragment();
        captureFragment.setCameraInitCallBack(new CaptureFragment.CameraInitCallBack() {
            @Override
            public void callBack(Exception e) {
                if (e != null) {
                    framelayout_parent.setBackgroundColor(Color.parseColor("#000000"));
                    showConfirmDialog(R.string.openCameraPrompt, com.szy.common.Constant.ViewType.VIEW_TYPE_DIALOG_CONFIRM, 0);
                }
            }
        });

        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();

        mContext = this;
        scanLine = (ImageView) findViewById(R.id.capture_scan_line);
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -0.0f, Animation.RELATIVE_TO_PARENT,
                1.0f);
        animation.setDuration(2000);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.RESTART);
        scanLine.startAnimation(animation);

        findViewById(R.id.capture_flashlight).setOnClickListener(this);
        findViewById(R.id.capture_scan_photo).setOnClickListener(this);
        findViewById(R.id.imageView_back).setOnClickListener(this);

        mContext = this;
        mBrowserUrlManager.addPattern(new BarcodePatternModel());

        Intent intent = getIntent();
        code = intent.getStringExtra(Key.KEY_ACT_CODE.getValue());
        shopId = intent.getStringExtra(Key.KEY_SHOP_ID.getValue());

        type = intent.getIntExtra(Key.KEY_TYPE.getValue(), TYPE_DEFAULT);
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {

            if (TextUtils.isEmpty(result)) {
                toast("解析二维码信息失败");
                continuePreview();
            } else {

                switch (type) {
                    case TYPE_DEFAULT:
                        if (!Utils.isNull(code) && code.equals("DeliveryAddressActivity")) {
                            Intent intent = new Intent();
                            intent.putExtra(Key.KEY_RESULT.getValue(), result);
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        } else if (result.contains(Api.SCAN_URL)) {

                            if (result.contains(Api.SCAN_URL+"/qrcode")) {

                                if (App.getInstance().isLogin()) {
                                    Intent intent = new Intent(ScanActivity.this, HybridWebViewActivity.class);
                                    intent.putExtra(Key.KEY_URL.getValue(), result);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(mContext, "商家入驻请先登录!", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Intent mIntentLife = new Intent(ScanActivity.this, ProjectH5Activity.class);

                                if (result.contains("user/getRedirectRegist")) {
                                    String code_url = result.substring(result.indexOf("?"));
                                    mIntentLife.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/directPay?" + code_url + "&isFromScan=1");
                                } else {
                                    mIntentLife.putExtra(Key.KEY_URL.getValue(), result);
                                }
                                startActivity(mIntentLife);
                            }

                            finish();
                        } else {

                            Matcher matcher = pattern.matcher(result);
                            if (matcher.matches()) {
                                Intent intent = new Intent();
                                intent.putExtra(Key.KEY_BARCODE.getValue(), result);
                                intent.setClass(ScanActivity.this, BarcodeSearchActivity.class);
                                intent.putExtra(Key.KEY_SHOP_ID.getValue(), shopId);
                                Utils.openActivity(ScanActivity.this, intent);
                            } else {
                                mBrowserUrlManager.parseUrl(ScanActivity.this, result);
                            }
                            continuePreview();
                        }
                        break;
                    case TYPE_PAYMENT:
                        Intent intent = new Intent();
                        intent.setClass(ScanActivity.this, MerchantPaymentActivity.class);
                        intent.putExtra(Key.KEY_BARCODE.getValue(), result);
                        startActivity(intent);
                        finish();
                        break;
                    case TYPE_CITYLIFE:
                        Intent mIntentLife = new Intent();
                        if (result.contains("user/getRedirectRegist")) {
                            String code_url = result.substring(result.indexOf("?"));
                            mIntentLife.putExtra(Key.KEY_RESULT.getValue(), Api.H5_CITYLIFE_URL + "/directPay?" + code_url + "&isFromScan=1");
                            setResult(IBaseWebview.QR_CODE_RESULT, mIntentLife);
                        } else {
                            mIntentLife.putExtra(Key.KEY_RESULT.getValue(), result);
                            setResult(IBaseWebview.QR_CODE_RESULT, mIntentLife);
                        }
                        finish();
                        break;
                    default:
                        break;
                }
            }
        }

        @Override
        public void onAnalyzeFailed() {
            toast("解析二维码信息失败");
            continuePreview();
        }
    };

    private void continuePreview() {
        CaptureActivityHandler handler = (CaptureActivityHandler) captureFragment.getHandler();
        Message message = new Message();
        message.what = com.uuzuche.lib_zxing.R.id.restart_preview;
        handler.handleMessage(message);

//        CaptureActivityHandler handler = (CaptureActivityHandler) captureFragment.getHandler();
//        Class cls = handler.getClass();
//        //获得类的私有方法
//        Method method = null;
//        try {
//            method = cls.getDeclaredMethod("restartPreviewAndDecode");
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        method.setAccessible(true); //没有设置就会报错
//        //调用该方法
//        try {
//            method.invoke(handler);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE:
                    if (data != null) {
                        Uri uri = data.getData();
                        try {
                            CodeUtils.analyzeBitmap(RxPhotoTool.getImageAbsolutePath(this, uri), analyzeCallback);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.capture_scan_photo: // 图片识别
                // 打开手机中的相册
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE);
                break;

            case R.id.capture_flashlight:
                if (!isOpen) {
                    CodeUtils.isLightEnable(true);
                    isOpen = true;
                } else {
                    CodeUtils.isLightEnable(false);
                    isOpen = false;
                }
                break;
            case R.id.imageView_back:
                finish();
                break;
            default:
                break;
        }
    }
}