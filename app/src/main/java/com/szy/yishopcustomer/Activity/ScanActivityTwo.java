package com.szy.yishopcustomer.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.*;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.Util.CommonUtils;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.BaseEntity;
import com.szy.yishopcustomer.ResponseModel.Goods.ResponseGoodsModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.AttributeDialog;
import com.szy.yishopcustomer.ViewModel.FreeGoodsCountModel;
import com.szy.yishopcustomer.ViewModel.FreeSkuListModel;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.decoding.CaptureActivityHandler;
import com.yolanda.nohttp.RequestMethod;

public class ScanActivityTwo extends YSCBaseActivity implements AttributeDialog.OnGoodsBuyClick, View.OnClickListener {

    private static final int REQUEST_CODE = 100;
    private static final int PARSE_BARCODE_FAIL = 300;
    private static final int PARSE_BARCODE_SUC = 200;

    private CaptureFragment captureFragment;

    private ImageView scanLine;
    private EditText edittext_search;
    private View linearlayout_shop_cart;
    private Context mContext;
    private String shopId;
    private String shopName = "";
    private TextView textView_cart_number;

    private View linearlayout_shop_name;
    private TextView textView_shop_name;
    private FrameLayout framelayout_parent;

    ResponseGoodsModel mResponseGoodsModel;
    AttributeDialog dialog;
    Bitmap goodBitmap;


    public static boolean isOpen = false;

    Camera mCamera;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_scan_two);

        Intent intent = getIntent();
        shopId = intent.getStringExtra(Key.KEY_SHOP_ID.getValue());
        shopName = intent.getStringExtra(Key.KEY_SHOP_NAME.getValue());

        framelayout_parent = (FrameLayout) findViewById(R.id.framelayout_parent);

        captureFragment = new CaptureFragment();
        captureFragment.setCameraInitCallBack(new CaptureFragment.CameraInitCallBack() {
            @Override
            public void callBack(Exception e) {
                if(e != null) {
                    framelayout_parent.setBackgroundColor(Color.parseColor("#000000"));
                    showConfirmDialog(R.string.openCameraPrompt,  com.szy.common.Constant.ViewType.VIEW_TYPE_DIALOG_CONFIRM, 0);
                }
            }
        });

        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera_two);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();


        scanLine = (ImageView) findViewById(R.id.capture_scan_line);
        edittext_search = (EditText) findViewById(R.id.edittext_search);
        textView_cart_number = (TextView) findViewById(R.id.textView_cart_number);
        linearlayout_shop_cart = findViewById(R.id.linearlayout_shop_cart);

        linearlayout_shop_name = findViewById(R.id.linearlayout_shop_name);
        textView_shop_name = (TextView) findViewById(R.id.textView_shop_name);

        if (TextUtils.isEmpty(shopName) || TextUtils.isEmpty(shopId)) {
            linearlayout_shop_name.setVisibility(View.GONE);
        } else {
            linearlayout_shop_name.setVisibility(View.VISIBLE);
            textView_shop_name.setText("去" + shopName + "转转");
            linearlayout_shop_name.setOnClickListener(this);
        }

        EditText.OnEditorActionListener editorActionListener =
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == KeyEvent.KEYCODE_SEARCH || actionId == EditorInfo.IME_ACTION_SEARCH) {
                            search();
                        }
                        return true;
                    }
                };
        edittext_search.setOnEditorActionListener(editorActionListener);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @SuppressLint("WrongConstant")
            @Override
            public void run() {
                TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -0.0f, Animation.RELATIVE_TO_PARENT,
                        1.0f);

                animation.setDuration(2000);
                animation.setRepeatCount(-1);
                animation.setRepeatMode(Animation.RESTART);
                scanLine.startAnimation(animation);
            }
        }, 1000);//1秒后执行Runnable中的run方法

        findViewById(R.id.imageView_back).setOnClickListener(this);
        findViewById(R.id.button_search).setOnClickListener(this);
        linearlayout_shop_cart.setOnClickListener(this);

        goodBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.btn_cart_list_disabled);
        mContext = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureFragment.onResume();

        getShopCartInfo();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void continuePreview() {
        CaptureActivityHandler handler = (CaptureActivityHandler) captureFragment.getHandler();
        Message message = new Message();
        message.what = com.uuzuche.lib_zxing.R.id.restart_preview;
        handler.handleMessage(message);
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {

            if (TextUtils.isEmpty(result)) {
                toast("商品条码未录入或条码不清晰，可重新扫描或手动输入");
                continuePreview();
            } else {
                isQrcode = true;
                getGoodsInfoForCode(result);
            }
        }

        @Override
        public void onAnalyzeFailed() {
            toast("商品条码未录入或条码不清晰，可重新扫描或手动输入");
            continuePreview();
        }
    };

    private void search(){
        String str = edittext_search.getText().toString();
        if (TextUtils.isEmpty(str)) {
            toast("请输入商品条码！");
        } else {
            isQrcode = false;
            getGoodsInfoForCode(str);
        }
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.capture_scan_photo: // 图片识别
                // 打开手机中的相册
                Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT); // "android.intent.action.GET_CONTENT"
                innerIntent.setType("image/*");
                Intent wrapperIntent = Intent.createChooser(innerIntent,
                        "选择二维码图片");
                this.startActivityForResult(wrapperIntent, REQUEST_CODE);
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
            case R.id.button_search:
                search();
                break;
            case R.id.linearlayout_shop_cart:
                openShopCart();
                break;
            case R.id.linearlayout_shop_name:
                //进入店铺
                Intent intent = new Intent(this, ShopActivity.class);
                intent.putExtra(Key.KEY_SHOP_ID.getValue(), shopId);
                startActivity(intent);
                break;
            default:
                com.szy.common.Constant.ViewType viewType = CommonUtils.commonGetViewTypeOfTag(v);
                switch (viewType) {
                    case VIEW_TYPE_DIALOG_CONFIRM:
                        //跳转到设置界面
                        startActivity(new Intent(Settings.ACTION_SETTINGS));
                        break;
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (RequestCode.valueOf(requestCode)) {
                case REQUEST_CODE_LOGIN_FOR_QUICK_BUY:
                    if (resultCode == Activity.RESULT_OK) {
                        quickBuy(tskuId, tgoodsNumber);
                    }
                    break;
            }
        }
    }

    private void openShopCart() {
        Intent intent = new Intent(mContext, CartFreeActivity.class);
        intent.putExtra("shop_id", shopId);
        startActivity(intent);
    }

    public boolean isQrcode = true;

    void getGoodsInfoForCode(String barcode) {
        CommonRequest request = new CommonRequest(Api.API_FREEBUY_SCAN_BUY_GET_SKU, HttpWhat.HTTP_GET_SCAN_SHOP
                .getValue());
        request.add("goods_barcode", barcode);
        request.add("shop_id", shopId);
        addRequest(request);
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GET_SCAN_SHOP:
                refreshGoodsInfo(response);
                break;
            case HTTP_GOODS:
//                refreshCallBack(response);
                break;
            case HTTP_CART_LIST:
                refreshShopCart(response);
                break;
            case HTTP_ADD_TO_CART:
                addToCartCallback(response);
                break;
            case HTTP_QUICK_BUY:
                quickBuyCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private void quickBuyCallback(String response) {
        HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager
                .HttpResultCallBack<ResponseCommonModel>() {
            @Override
            public void onSuccess(ResponseCommonModel back) {
                goCheckout();
            }

            @Override
            public void onLogin() {
                openLoginActivityForResult(RequestCode.REQUEST_CODE_LOGIN_FOR_QUICK_BUY);
            }
        }, true);
    }

    private void openLoginActivityForResult(RequestCode requestCode) {
        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivityForResult(intent, requestCode.getValue());
    }

    public void goCheckout() {
        closeDialog();

        Intent intent = new Intent();
        intent.setClass(mContext, CheckoutActivity.class);
        startActivity(intent);
    }

    private void addToCartCallback(String response) {
        HttpResultManager.resolve(response, BaseEntity.class, new HttpResultManager
                .HttpResultCallBack<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity back) {
                //成功，弄一个加入购物车动画，并关闭dialog
                closeDialog();
                startAddCartAnim(goodBitmap);
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
            }
        }, true);
    }

    private void startAddCartAnim(Bitmap bitmap) {
        if (bitmap != null) {
            int[] location = new int[2];
            location[0] = Utils.getWindowWidth(mContext) / 2;
            location[1] = Utils.getWindowHeight(mContext) / 2;

            int[] endLocation = new int[2];
            linearlayout_shop_cart.getLocationOnScreen(endLocation);
            endLocation[0] += linearlayout_shop_cart.getWidth() / 2;

            final View animationView = LayoutInflater.from(mContext).inflate(
                    R.layout.activity_shop_animator1, null);
            ImageView im = (ImageView) animationView.findViewById(R.id.imageView_anim);
            im.setImageBitmap(bitmap);
            framelayout_parent.addView(animationView);

            int picSize = Utils.dpToPx(im.getContext(), 50) / 2;

            ObjectAnimator animatorX = ObjectAnimator.ofFloat(animationView, "X", location[0] - picSize, endLocation[0] - picSize);
            ObjectAnimator animatorY = ObjectAnimator.ofFloat(animationView, "Y", location[1] - picSize, endLocation[1] - picSize);

            animatorY.setInterpolator(new AnticipateInterpolator(2.0f));
            animatorX.setInterpolator(new AccelerateInterpolator());


            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(700);
            animatorSet.playTogether(animatorX, animatorY);
            animatorSet.start();

            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    framelayout_parent.removeView(animationView);
                    startCartViewAnimation();
                }
            });
        }
    }

    private void startCartViewAnimation() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(linearlayout_shop_cart, "scaleX", 1.0f, 1.4f,
                1.0f, 1.2f, 1.0f);
        animatorX.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(linearlayout_shop_cart, "scaleY", 1.0f, 1.2f,
                1.0f, 1.2f, 1.0f);
        animatorY.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(200);
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                getShopCartInfo();
            }
        });
    }

    void getShopCartInfo() {
        CommonRequest request = new CommonRequest(Api.API_FREEBUY_CART_GOODS_COUNT, HttpWhat.HTTP_CART_LIST.getValue());
        request.setAjax(true);
        request.add("shop_id", shopId);
        addRequest(request);
    }

    void getGoodsDetail(String goodsid) {
        CommonRequest request = new CommonRequest(Config.BASE_URL + "/goods/" + goodsid, HttpWhat
                .HTTP_GOODS.getValue());
        addRequest(request);
    }

    private void refreshGoodsInfo(String response) {
        HttpResultManager.resolve(response, FreeSkuListModel.class, new HttpResultManager.HttpResultCallBack<FreeSkuListModel>() {
            @Override
            public void onSuccess(FreeSkuListModel data) {

                dialog = new AttributeDialog(mContext);
                dialog.setData(data, ScanActivityTwo.this);
                dialog.show();

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        continuePreview();
                    }
                });

                ImageLoader.getInstance().loadImage(Utils.urlOfImage(data.data.goods_image), new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        goodBitmap = loadedImage;
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });

            }

            @Override
            public void onFailure(String message) {
                if (isQrcode) {
                    toast("商品条码未录入或条码不清晰，可重新扫描或手动输入");
                } else {
                    toast("条码输入有误或商品不存在");
                }
                continuePreview();
            }
        }, true);
    }

    private void refreshShopCart(String response) {
        HttpResultManager.resolve(response, FreeGoodsCountModel.class, new HttpResultManager.HttpResultCallBack<FreeGoodsCountModel>() {
            @Override
            public void onSuccess(FreeGoodsCountModel back) {
                if (back.goods_count > 0) {
                    textView_cart_number.setVisibility(View.VISIBLE);
                    textView_cart_number.setText(back.goods_count + "");

                    linearlayout_shop_cart.setBackgroundResource(R.mipmap.ic_cashier_shopcar);
                } else {
                    textView_cart_number.setVisibility(View.GONE);
                    linearlayout_shop_cart.setBackgroundResource(R.mipmap.ic_cashier_shopcar_floating);
                }
            }
        }, true);
    }


    void closeDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void addToCart(String skuId, String goodsNumber) {
        CommonRequest mAddToCartRequest = new CommonRequest(Api.API_FREEBUY_CART_ADD, HttpWhat
                .HTTP_ADD_TO_CART.getValue(), RequestMethod.POST);
        mAddToCartRequest.add("sku_id", skuId);
        mAddToCartRequest.add("number", goodsNumber);
        mAddToCartRequest.add("shop_id", shopId);

        mAddToCartRequest.setAjax(true);
        addRequest(mAddToCartRequest);
    }

    private String tskuId;
    private String tgoodsNumber;

    @Override
    public void quickBuy(String skuId, String goodsNumber) {
        tskuId = skuId;
        tgoodsNumber = goodsNumber;

        CommonRequest mQuickBuyRequest = new CommonRequest(Api.API_QUICK_BUY, HttpWhat
                .HTTP_QUICK_BUY.getValue(), RequestMethod.POST);
        mQuickBuyRequest.add("sku_id", skuId);
        mQuickBuyRequest.add("number", goodsNumber);
        //判断是否拼团
//        boolean typeGroupOn = false;
//        if (typeGroupOn) {
//            mQuickBuyRequest.add("group_sn", 0);
//        }
        addRequest(mQuickBuyRequest);
    }
}