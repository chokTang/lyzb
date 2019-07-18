package com.lyzb.jbx.util;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.like.longshaolib.app.LongshaoAPP;
import com.like.longshaolib.app.config.ConfigType;
import com.lyzb.jbx.R;
import com.lyzb.jbx.activity.CricleDetailActivity;
import com.lyzb.jbx.model.common.WeiXinMinModel;
import com.lyzb.jbx.widget.GuideView;
import com.lyzb.jbx.widget.link.AutoLinkMode;
import com.lyzb.jbx.widget.link.AutoLinkOnClickListener;
import com.lyzb.jbx.widget.link.AutoLinkTextView;
import com.szy.yishopcustomer.Activity.ShareActivity;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.BrowserUrlManager;
import com.szy.yishopcustomer.Util.DataCleanManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * Created by Administrator on 2017/10/15.
 */

public class AppCommonUtil {
    public static String EmptyID = "00000000-0000-0000-0000-000000000000";

    /**
     * 获取URL完整路径
     *
     * @param relativeUrl
     * @return
     */
    public static String getImageUrl(String relativeUrl) {
        String headerUrl = LongshaoAPP.getConfiguration(ConfigType.API_HOST);
        headerUrl = headerUrl.substring(0, headerUrl.length() - 2);
        return headerUrl + relativeUrl;
    }

    /****
     * 获取应用缓存
     * @return
     */
    public static String getCache(Context context) {

        String cache = null;

        try {
            cache = DataCleanManager.getTotalCacheSize(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cache;
    }

    /****
     * 指定去除字符串中的文本
     * @param sourceStr 字符串
     * @param replaStr  要去除的文本
     * @return
     */
    public static String base64String(String sourceStr, char replaStr) {

        String resultStr = "";

        for (int i = 0; i < sourceStr.length(); i++) {
            if (sourceStr.charAt(i) != replaStr) {
                replaStr += sourceStr.charAt(i);
            }
        }

        return resultStr;
    }

    /****
     * base64字符串转bitmap
     * @param string
     * @return
     */
    public static Bitmap base64ToBitmap(String string) {

        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(string.split(",")[1], Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /****
     *  添加商品 限制输入两位小数
     */
    public static InputFilter inputFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            // 删除等特殊字符，直接返回
            if (TextUtils.isEmpty(source)) {
                return null;
            }
            String dValue = dest.toString();
            String[] splitArray = dValue.split("\\.");
            if (splitArray.length > 1) {
                String dotValue = splitArray[1];
                // 2 表示输入框的小数位数
                int diff = dotValue.length() + 1 - 2;
                if (diff > 0) {
                    return source.subSequence(start, end - diff);
                }
            }
            return null;
        }
    };

    /****
     * 根据图片路径 获取 图片的类型 image/png  image/jpeg  image/gif
     * @param imgUrl
     * @return
     */
    public static String getImgType(String imgUrl) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgUrl, options);
        return options.outMimeType;
    }

    /****
     * 判断手机系统 是否为小米 用于申请权限询问
     * @return
     */
    public static boolean isMIUI() {

        //检测MIUI
        final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
        final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
        final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

        String device = Build.MANUFACTURER;
        System.out.println("Build.MANUFACTURER = " + device);

        if (device.equals("Xiaomi")) {
            Properties prop = new Properties();
            try {
                prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } else {
            return false;
        }
    }

    /****
     * 判断小米MIUI系统中授权管理中对应的权限授取
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean initMiuiPermission(Context context) {

        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);

        int locationOp = appOpsManager.checkOp(AppOpsManager.OPSTR_FINE_LOCATION, Binder.getCallingUid(), context.getPackageName());
        if (locationOp == AppOpsManager.MODE_IGNORED) {
            return false;
        }

        int cameraOp = appOpsManager.checkOp(AppOpsManager.OPSTR_CAMERA, Binder.getCallingUid(), context.getPackageName());
        if (cameraOp == AppOpsManager.MODE_IGNORED) {
            return false;
        }

        int phoneStateOp = appOpsManager.checkOp(AppOpsManager.OPSTR_READ_PHONE_STATE, Binder.getCallingUid(), context.getPackageName());
        if (phoneStateOp == AppOpsManager.MODE_IGNORED) {
            return false;
        }

        int readSDOp = appOpsManager.checkOp(AppOpsManager.OPSTR_READ_EXTERNAL_STORAGE, Binder.getCallingUid(), context.getPackageName());
        if (readSDOp == AppOpsManager.MODE_IGNORED) {
            return false;
        }

        int writeSDOp = appOpsManager.checkOp(AppOpsManager.OPSTR_WRITE_EXTERNAL_STORAGE, Binder.getCallingUid(), context.getPackageName());
        if (writeSDOp == AppOpsManager.MODE_IGNORED) {
            return false;
        }
        return true;
    }

    /**
     * 处理文本内容
     *
     * @param textView
     * @param value
     */
    public static void autoLinkText(final AutoLinkTextView textView, final String value, final String dynamicId) {
        if (TextUtils.isEmpty(value)) {
            return;
        }
        final Context _context = textView.getContext();
        textView.setCustomRegex("#(.*?)#");
        textView.addAutoLinkMode(AutoLinkMode.MODE_CUSTOM, AutoLinkMode.MODE_URL);
        textView.setCustomModeColor(ContextCompat.getColor(_context, R.color.app_blue));
        textView.setUrlModeColor(ContextCompat.getColor(_context, R.color.app_blue));
        textView.setText(value);

        if (!TextUtils.isEmpty(dynamicId)) {
            textView.setAutoLinkOnClickListener(new AutoLinkOnClickListener() {
                @Override
                public void onAutoLinkTextClick(AutoLinkMode autoLinkMode, String matchedText) {
                    ((View) textView.getParent()).setTag("I have click");
                    switch (autoLinkMode) {
                        //进入h5页面
                        case MODE_URL:
                            new BrowserUrlManager(matchedText).parseUrl(_context, matchedText);
                            break;
                        //进入动态详情页面
                        case MODE_CUSTOM:
                            CricleDetailActivity.start(_context, "#" + matchedText + "#", dynamicId);
                            break;
                        default:
                            break;
                    }
                }
            });
        }

    }

    /**
     * 动态的分享
     *
     * @param context
     * @param dynamicId
     * @param dynamicTitle
     * @param ownerId      拥有者ID
     */
    public static void startAdapterShare(Context context, String dynamicId, String ownerId, String dynamicTitle, byte[] bitmap) {
        final Intent shareIntent = new Intent(context, ShareActivity.class);
        final WeiXinMinModel model = new WeiXinMinModel();
        model.setLowVersionUrl(Config.BASE_URL);                    //微信低版本 网络地址
        if (TextUtils.equals(ownerId, App.getInstance().userId)) {
            if (TextUtils.isEmpty(AppPreference.getIntance().getShareDynamicValue())) {
                model.setTitle(dynamicTitle + "的动态");     //分享的标题
            } else {
                model.setTitle(AppPreference.getIntance().getShareDynamicValue());     //分享的标题
            }
        } else {
            model.setTitle(dynamicTitle + "的动态");     //分享的标题
        }
        model.setDescribe(" ");                                //描述
        model.setShareUrl("/pages/dynamic/detail?id=" + dynamicId
                + "&gsName=" + dynamicTitle + "&shareFromId=" + App.getInstance().userId + "&originUserId=" + ownerId);   //分享地址

        shareBitmp = bitmap;//这里写死的赋值，是因为intent携带的参数超过了200KB有问题
//        model.setImageUrl(bitmap);

        shareIntent.putExtra(ShareActivity.SHARE_DATA, model);
        shareIntent.putExtra(ShareActivity.SHARE_SCOPE, 2);
        shareIntent.putExtra(ShareActivity.SHARE_SOURCE, ShareActivity.TYPE_SOURCE);
        context.startActivity(shareIntent);
    }

    //分享的时候携带的数据
    public static byte[] shareBitmp = null;

    public static URI getUrl(String url) {
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public static void showGuideView(final View targetView,String tagerValue) {
        final ImageView iv1 = new ImageView(targetView.getContext());
        iv1.setImageResource(R.drawable.union_guide_access);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        iv1.setLayoutParams(params1);

        final TextView iv2 = new TextView(targetView.getContext());
        iv2.setText(tagerValue);
        iv2.setTextColor(ContextCompat.getColor(targetView.getContext(), R.color.white));
        iv2.setTextSize(18);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        iv2.setLayoutParams(params2);

       final GuideView guideView = GuideView.Builder
                .newInstance(targetView.getContext())
                .setTargetView(targetView)  //设置目标view
                .setTextGuideView(iv1)      //设置文字图片
                .setCustomGuideView(iv2)    //设置 我知道啦图片
                .setOffset(0, 50)           //偏移量  x=0 y=80
                .setDirction(GuideView.Direction.BOTTOM)   //方向
                .setShape(GuideView.MyShape.RECTANGULAR)   //矩形
                .setRadius(0)                             //圆角
                .setContain(true)                         //透明的方块时候包含目标view  默认false
                .setOnclickListener(new GuideView.OnClickCallback() {
                    @Override
                    public void onClickedGuideView() {
//                        guideView.hide();
                    }
                })
                .build();
        guideView.show();
    }
}
