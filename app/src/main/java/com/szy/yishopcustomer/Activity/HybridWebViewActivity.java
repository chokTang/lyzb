package com.szy.yishopcustomer.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.*;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;

import java.io.File;

import butterknife.BindView;
import me.iwf.photopicker.PhotoPicker;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/****
 * szy 商家入驻 webview
 */
public class HybridWebViewActivity extends YSCBaseActivity {

    private static final String JS_INTERFACE = "ysc";

    @BindView(R.id.activity_web_view_yscBaseWebView)
    WebView mWebView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_common_titleTextView)
    TextView titleView;

    private String alternateTitle = "";
    private ValueCallback mUploadMessage;

    private final static int FILECHOOSER_RESULTCODE = 10003;

    private String[] homePage = new String[]{"/shop/apply/result.html", "/shop/apply.html", "/shop/apply/cancel.html", "/shop/apply/progress.html"};


    private boolean needClearHistory = false;

    @Override
    public CommonFragment createFragment() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.activity_hybrid_web_view;
        mEnableBaseMenu = true;

        super.onCreate(savedInstanceState);
        String url = getIntent().getStringExtra(Key.KEY_URL.getValue());

        mWebView.setHorizontalScrollBarEnabled(false);//水平不显示
        mWebView.setVerticalScrollBarEnabled(false); //垂直不显示

//        String ua = mWebView.getSettings().getUserAgentString();
//        mWebView.getSettings().setUserAgentString(ua+";szyapp/android");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new XHSWebChromeClient());
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.addJavascriptInterface(this, JS_INTERFACE);
        mWebView.loadUrl(url);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Bundle data = msg.getData();
                    String val = data.getString("title");
                    setTitleView(val);
                    break;
                case 1:
                    goBack();
                    break;

            }
        }
    };

    @android.webkit.JavascriptInterface
    public void setTitle(String str) {
        String titleStr = "";
        if (!TextUtils.isEmpty(str) && !"undefined".equals(str)) {
            titleStr = str.trim();
        } else {
            if (!"undefined".equals(alternateTitle)) {
                titleStr = alternateTitle.trim();
            }
        }

        Message msg = new Message();
        msg.what = 0;
        Bundle data = new Bundle();
        data.putString("title", titleStr);
        msg.setData(data);
        handler.sendMessage(msg);
    }

    public void setTitleView(String msg) {
        if (!TextUtils.isEmpty(msg) && !"undefined".equals(msg)) {
            titleView.setText(msg);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @android.webkit.JavascriptInterface
    public void historyGo() {
        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mWebView.removeAllViews();
        mWebView.destroy();
    }

    /***
     * 清除cookies
     */
    public void clearCookies(Context context) {

        try {
            CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();//移除
            cookieManager.removeAllCookie();
            if (Build.VERSION.SDK_INT < 21) {
                CookieSyncManager.getInstance().sync();
            } else {
                CookieManager.getInstance().flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goBack() {
        if (isHomePage()) {

            clearCookies(this);

            finish();
        } else {

//            if(mWebView.canGoBackOrForward(-1)) {
//                mWebView.goBackOrForward(-1);//返回前一个页面
//            } else {
//                finish();
//            }

            WebBackForwardList mWebBackForwardList = mWebView.copyBackForwardList();
            //判断当前历史列表是否最顶端,其实canGoBack已经判断过
            if (mWebBackForwardList.getCurrentIndex() > 0) {
                //获取历史列表
                String historyUrl = mWebBackForwardList.getItemAtIndex(
                        mWebBackForwardList.getCurrentIndex() - 1).getUrl();
                //按照自己规则检查是否为可跳转地址
                //注意:这里可以根据自己逻辑循环判断,拿到可以跳转的那一个然后webView.goBackOrForward(steps)
                if (!historyUrl.endsWith("/shop/apply/progress.html")) {
                    //执行跳转逻辑
                    mWebView.goBack();
                    //webView.goBackOrForward(-1)
                } else {

                    clearCookies(this);

                    finish();
                }
            } else {

                clearCookies(this);

                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (!mWebView.canGoBack()) {
            clearCookies(this);
        }
    }

    WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.equals(Utils.getMallMBaseUrl() + "/")) {
                openRootIndex();
                return true;
            }

//            view.loadUrl(url);
            return false;
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);

            Log.e("onLoadResource", url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
            // html加载完成之后，添加监听图片的点击js函数
            addTitleDismiss(view);
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);
        }

        // 注入js函数监听
        private void addTitleDismiss(WebView view) {
            // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
            view.loadUrl("javascript:(function(){" +
                    "$('.header').hide();$('.footer-nav').hide();" +
//                    "$('.no-data-btn').attr('href','javascript:window."+JS_INTERFACE+".historyGo()');" +
                    "$('.no-data-btn').click(function(){window." + JS_INTERFACE + ".historyGo(); });" +
                    "var title = $('.header-middle').html(); window." + JS_INTERFACE + ".setTitle(title); " +
                    "})()");
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            return super.shouldInterceptRequest(view, url);
        }
    };

    public boolean isHomePage() {
        for (int i = 0, len = homePage.length; i < len; i++) {
            if (mWebView.getUrl() != null && mWebView.getUrl().endsWith(homePage[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == PhotoPicker.REQUEST_CODE && data != null) {
                String imgUrl = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS).get(0);

                Luban.with(this)
                        .load(imgUrl)
                        .putGear(3)
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onSuccess(File file) {

                                Uri uri = Uri.fromFile(new File(file.getAbsolutePath()));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    mUploadMessage.onReceiveValue(new Uri[]{uri});
                                } else {
                                    mUploadMessage.onReceiveValue(uri);
                                }

                                mUploadMessage = null;
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(mContext, "图片路径异常,请重新选择", Toast.LENGTH_SHORT).show();
                            }

                        }).launch();
            } else {
                Toast.makeText(mContext, "图片路径异常,请重新选择", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (null == mUploadMessage || resultCode != RESULT_OK) {
            mUploadMessage.onReceiveValue(null);
            mUploadMessage = null;
            return;
        }
    }

    public class XHSWebChromeClient extends WebChromeClient {

        // For Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
            }
            mUploadMessage = uploadMsg;
            openGallery();
        }

        // For Android 3.0+
        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
            }
            mUploadMessage = uploadMsg;
            openGallery();
        }

        // For Android 4.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
            }
            mUploadMessage = uploadMsg;
            openGallery();
        }

        //Android 5.0+
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
            }

            mUploadMessage = filePathCallback;
            String acceptType = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (fileChooserParams != null && fileChooserParams.getAcceptTypes() != null
                        && fileChooserParams.getAcceptTypes().length > 0) {
                    acceptType = fileChooserParams.getAcceptTypes()[0];
                }
            }

            openGallery();

            //startActivityForResult(createDefaultOpenableIntent(), FILECHOOSER_RESULTCODE);
            return true;
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);

            alternateTitle = title;
        }


        /***
         * 图库
         */
        public void openGallery() {

            PhotoPicker.builder()
                    .setPhotoCount(1)
                    .setShowCamera(true)//打开相机
                    .setShowGif(false)
                    .setPreviewEnabled(true)//可预览
                    .start(HybridWebViewActivity.this, PhotoPicker.REQUEST_CODE);
        }


//        /**
//         * 默认支持上传多种文件类型
//         */
//        private Intent createDefaultOpenableIntent() {
//
//            //打开相册
//            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//            i.addCategory(Intent.CATEGORY_OPENABLE);
//            i.setType("image/*");
//
//            Intent chooser = createChooserIntent(createCameraIntent());
//            chooser.putExtra(Intent.EXTRA_INTENT, i);
//            chooser.putExtra(Intent.EXTRA_TITLE, "请选择要上传的文件");
//
//            return chooser;
//        }


//        private Intent createCameraIntent() {
//            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            File externalDataDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
//            File cameraDataDir = new File(externalDataDir.getAbsolutePath() + File.separator + "browser-photos");
//
//            cameraDataDir.mkdirs();
//            String mCameraFilePath = cameraDataDir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
//            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mCameraFilePath)));
//            return cameraIntent;

//            //打开相机
//            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            // 判断存储卡是否可以用，可用进行存储
//            if (Utils.hasSdcard()) {
//                File externalDataDir = Environment.getExternalStoragePublicDirectory(
//                        Environment.DIRECTORY_DCIM);
//                File cameraDataDir = new File(externalDataDir.getAbsolutePath() +
//                        File.separator + "browser-photos");
//                cameraDataDir.mkdirs();
//                String mCameraFilePath = cameraDataDir.getAbsolutePath() + File.separator +
//                        System.currentTimeMillis() + ".jpg";
//                if (currentapiVersion < 24) {
//                    // 从文件中创建uri
//                    imageUri = Uri.fromFile(new File(mCameraFilePath));
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                } else {
//                    //兼容android7.0 使用共享文件的形式
//                    ContentValues contentValues = new ContentValues(1);
//                    contentValues.put(MediaStore.Images.Media.DATA, mCameraFilePath);
//
//                    if (ContextCompat.checkSelfPermission(mContext, Manifest.permission
//                            .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                        //申请WRITE_EXTERNAL_STORAGE权限
//                        toast("请开启存储权限");
//                    }
//
//                    imageUri = mContext.getContentResolver().insert(MediaStore.Images.Media
//                            .EXTERNAL_CONTENT_URI, contentValues);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                }
//            }
//
//            return intent;
//        }

//        private Intent createChooserIntent(Intent... intents) {
//            Intent chooser = new Intent(Intent.ACTION_CHOOSER);
//            chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
//            return chooser;
//        }

    }
}
