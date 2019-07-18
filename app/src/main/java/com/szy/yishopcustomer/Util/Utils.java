package com.szy.yishopcustomer.Util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.lyzb.jbx.R;
import com.szy.common.Util.CommonUtils;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.ResponseModel.SameCity.Near.NearTitleDistance;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

/**
 * Created by zongren on 2016/3/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class Utils extends CommonUtils {

    public final static int CITY_NET_SUCES = 200;
    public final static int CITY_NET_LOGIN = 401;


    /**
     * Change bitmap`s color.
     *
     * @param sourceBitmap The bitmap.
     * @param color        The color.
     * @return The new bitmap.
     */
    public static Bitmap changeColor(Bitmap sourceBitmap, int color) {
        Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth() -
                1, sourceBitmap.getHeight() - 1);
        Paint p = new Paint();
        ColorFilter filter = new LightingColorFilter(color, 1);
        p.setColorFilter(filter);

        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, p);
        return resultBitmap;
    }

    public static String date(String time) {
        return times(time, "yyyy-MM-dd");
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dpToPx(Context context, float dpValue) {
        if (dpValue <= 0) {
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static Bitmap encodeAsBitmap(String content, int width, int height) throws
            WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width,
                    height, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, w, h);
        return bitmap;
    }

    public static String formatMillisecond(int millisecond) {
        String retMillisecondStr;

        if (millisecond > 99) {
            retMillisecondStr = String.valueOf(millisecond / 10);
        } else if (millisecond <= 9) {
            retMillisecondStr = "0" + millisecond;
        } else {
            retMillisecondStr = String.valueOf(millisecond);
        }

        return retMillisecondStr;
    }

    private static Pattern pattern = Pattern.compile("[0-9]*");

    /***
     * 判断字符串是否为数字
     * @param content
     * @return
     */
    public static boolean isNumber(String content) {

        Matcher isNum = pattern.matcher(content);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 格式化金钱
     *
     * @param input The input money without formation.
     * @return The formatted money.
     */
    public static String formatMoney(Context context, String input) {
        if (TextUtils.isEmpty(input)) {
            return "";
        }

        input = input.replaceAll("¥", "");
        input = input.replaceAll("￥", "");
        String format = context.getString(R.string.formatMoney);
        return String.format(format, input);
    }


    public static boolean isPhone(String phone) {
        if (CommonUtils.isNull(phone)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^1(3|4|5|7|8|9)\\d{9}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.find();
    }

    /**
     *      * 判断GPS是否开启
     *      * @param context
     *      * @return true 表示开启
     *      
     */
    public static final boolean gPSIsOPen(final Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context
                .LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return gps;
    }

    /**
     * Get the view`s activity context.
     *
     * @param view The target view.
     * @return The activity.
     */
    public static Activity getActivity(View view) {
        return (Activity) view.getContext();
    }

    /**
     * Get the field of alipay response.
     *
     * @param rawResult The raw response result of alipay.
     * @param key       The key of the field.
     * @return The field.
     */
    public static String getAlipayField(String rawResult, String key) {
        if (Utils.isNull(rawResult)) {
            return "";
        }
        String theKey = key + "={";
        int start = rawResult.indexOf(theKey);
        int end = rawResult.indexOf("}", start);
        return rawResult.substring(start + theKey.length(), end);
    }

    /**
     * Get the memo filed of alipay response result.
     *
     * @param rawResult The raw response result of alipay.
     * @return The memo field.
     */
    public static String getAlipayMemo(String rawResult) {
        return Utils.getAlipayField(rawResult, "memo");

    }

    /**
     * Get the result filed of alipay response result;
     *
     * @param rawResult The raw response result of alipay.
     * @return The result field.
     */
    public static String getAlipayResult(String rawResult) {
        return Utils.getAlipayField(rawResult, "result");
    }

    /**
     * Get the result status from alipay response result;
     *
     * @param rawResult The raw response result of alipay.
     * @return The resultStatus field.
     */
    public static String getAlipayResultStatus(String rawResult) {
        return Utils.getAlipayField(rawResult, "resultStatus");
    }

    /**
     * Get view type according to template code.
     *
     * @param templateCode The template code.
     * @return The view type.
     */
    public static ViewType getTemplateViewType(String templateCode) {
        if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_LOADING)) {
            return ViewType.VIEW_TYPE_LOADING;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_LOAD_DISABLED)) {
            return ViewType.VIEW_TYPE_LOAD_DISABLED;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_SHOP_LIST_TITLE)) {
            return ViewType.VIEW_TYPE_SHOP_LIST_TITLE;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_SHOP_LIST)) {
            return ViewType.VIEW_TYPE_SHOP_LIST;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_GOODS_LIST_TITLE)) {
            return ViewType.VIEW_TYPE_GOODS_LIST_TITLE;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_GOODS_LIST)) {
            return ViewType.VIEW_TYPE_GOODS_LIST;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_SHOP_STREET)) {
            return ViewType.VIEW_TYPE_SHOP_STREET;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_AD_COLUMN)) {
            return ViewType.VIEW_TYPE_AD_COLUMN;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_AD_BANNER)) {
            return ViewType.VIEW_TYPE_AD_BANNER;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_GOODS_TWO_COLUMN)) {
            return ViewType.VIEW_TYPE_TWO_COLUMN_GOODS;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_GOODS_THREE_COLUMN)) {
            return ViewType.VIEW_TYPE_THREE_COLUMN_GOODS;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_NAVIGATION)) {
            return ViewType.VIEW_TYPE_NAVIGATION;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_BLANK_LINE)) {
            return ViewType.VIEW_TYPE_BLANK;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_GOODS_TITLE)) {
            return ViewType.VIEW_TYPE_GOODS_TITLE;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_AD_TITLE)) {
            return ViewType.VIEW_TYPE_AD_TITLE;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_ARTICLE)) {
            return ViewType.VIEW_TYPE_ARTICLE;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_NOTICE)) {
            return ViewType.VIEW_TYPE_NOTICE;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_GOODS_PROMOTION)) {
            return ViewType.VIEW_TYPE_GOODS_PROMOTION;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_GOODS_DUMMY)) {
            return ViewType.VIEW_TYPE_SHOP_LIST_DUMMY;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_GOODS_DUMMY_TITLE)) {
            return ViewType.VIEW_TYPE_GOODS_DUMMY_TITLE;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_AD_COLUMN_THREE)) {
            return ViewType.VIEW_TYPE_AD_COLUMN_THREE;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_AD_COLUMN_FOUR)) {
            return ViewType.VIEW_TYPE_AD_COLUMN_FOUR;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_NAVIGATION_FIVE)) {
            return ViewType.VIEW_TYPE_NAVIGATION_FIVE;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_AD_TITLE_TWO)) {
            return ViewType.VIEW_TYPE_AD_TITLE_TWO;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_VIDEO)) {
            return ViewType.VIEW_TYPE_VIDEO;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_GUESS_LIKE)) {
            return ViewType.VIEW_INDEX_GUESS_LIKE;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_GUESS_LIKE_TITLE)) {
            return ViewType.GUESS_LIKE_TITLE;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_INGOTS_BUY)) {
            return ViewType.VIEW_TYPE_INGOTS_BUY;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_VIEW_TYPE_AD_ADVERT)) {
            return ViewType.VIEW_TYPE_AD_ADVERT;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_NEAR_SHOP)) {
            return ViewType.VIEW_TYPE_NEAR_SHOP;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_SHOP_JION)) {
            return ViewType.VIEW_TYPE_SHOP_JION;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_SHOP_NEW_JOIN)) {
            return ViewType.VIEW_TYPE_SHOP_NEW_JION;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_NET_HOT_SHOP)) {
            return ViewType.VIEW_TYPE_NET_HOT_SHOP;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_LIKE_SHOP)) {
            return ViewType.VIEW_TYPE_LIKE_SHOP;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_VIEW_LEFT_RIGHT_TITLE)) {
            return ViewType.VIEW_LEFT_RIGHT_TITLE;
        } else if (templateCode.equalsIgnoreCase(Macro.TEMPLATE_CODE_HOME_VIDEO_SHOW)) {
            return ViewType.VIEW_HOME_VIDEO_SHOW;
        }
        return ViewType.VIEW_TYPE_NOT_SUPPORT;
    }

    /**
     * Get the uri of a certain resource.
     *
     * @param context    The context.
     * @param resourceId The resource id.
     * @return The uri of the resource.
     */
    public static Uri getUri(Context context, int resourceId) {
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getResources()
                .getResourcePackageName(resourceId) + '/' + context.getResources()
                .getResourceTypeName(resourceId) + '/' + context.getResources()
                .getResourceEntryName(resourceId));
    }

    public static ViewType getViewTypeOfTag(View view) {
        int viewType = CommonUtils.getViewTypeIntegerOfTag(view);

        return ViewType.valueOf(viewType);
    }


    public static int getAppAreaHeight(Context context) {
        //View绘制区域
        Rect outRect1 = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);

        return outRect1.height();
    }

    /**
     * Get the height of window.
     *
     * @param context
     * @return
     */
    public static int getWindowHeight(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int height = metrics.heightPixels;
        return height;
    }

    /**
     * Get the width of window.
     *
     * @param context
     * @return
     */
    public static int getWindowWidth(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        return width;
    }

    /**
     * Hide keyboard.
     *
     * @param view The input view.
     */
    public static void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, 0);
    }

    /**
     * 判断qq是否可用
     *
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isSame(List<String> specificationList, List<String>
            selectedSpecificationList) {
        return specificationList.containsAll(selectedSpecificationList) &&
                selectedSpecificationList.containsAll(specificationList);
    }

    /*判断手机是否安装微博客户端*/
    public static boolean isWeiboInstalled(Context context) {
        PackageManager pm;
        if ((pm = context.getApplicationContext().getPackageManager()) == null) {
            return false;
        }
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo info : packages) {
            String name = info.packageName.toLowerCase(Locale.ENGLISH);
            if ("com.sina.weibo".equals(name)) {
                return true;
            }
        }
        return false;
    }

    /*判断手机是否安装微信客户端*/
    public static boolean isWeixinAvilible(Context context) {
        IWXAPI wxApi = WXAPIFactory.createWXAPI(context, null);
        wxApi.registerApp(Config.WEIXIN_APP_ID);
        return wxApi.isWXAppInstalled();
    }

    public static boolean isAliPay(Context context) {

        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

    public static final int MIN_CLICK_DELAY_TIME = 800;
    private static long lastClickTime = 0;

    public static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            return false;
        }
        return true;
    }

    public static void openPhoneDialog(final Context context, final String number) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View mView = layoutInflater.inflate(R.layout.dialog_call, null);
        TextView callNum = (TextView) mView.findViewById(R.id.dialog_call_num_textView);
        if (Utils.isNull(number)) {
            callNum.setText("该商家暂无联系电话");
            toastUtil.showToast(context, "该店铺暂无联系方式");

            return;
        } else {
            callNum.setText(number);
        }
        final AlertDialog mDialogDialog = new AlertDialog.Builder(context).setView(mView).create();
        TextView call = (TextView) mView.findViewById(R.id.dialog_call_do_call_layout);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                context.startActivity(intent);
                mDialogDialog.dismiss();
            }
        });
        TextView copy = (TextView) mView.findViewById(R.id.dialog_call_do_copy_layout);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context
                        .CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", number);
                clipboard.setPrimaryClip(clip);
                toastUtil.showToast(context, "复制成功");
                mDialogDialog.dismiss();
            }
        });
        TextView sendMessage = (TextView) mView.findViewById(R.id
                .dialog_call_do_message_layout);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + number));
                smsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(smsIntent);
                mDialogDialog.dismiss();
            }
        });
        TextView addPerson = (TextView) mView.findViewById(R.id
                .dialog_call_do_add_layout);
        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_INSERT, Uri.withAppendedPath(Uri.parse
                        ("content://com.android.contacts"), "contacts"));
                intent.setType("vnd.android.cursor.dir/person");
                intent.putExtra(android.provider.ContactsContract.Intents.Insert.PHONE, number);
                context.startActivity(intent);
                mDialogDialog.dismiss();
            }
        });
        mDialogDialog.show();
    }

    public static void openWebViewActivity(Context context, String url) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(Key.KEY_URL.getValue(), url);
        Utils.openActivity(context, "WebViewActivity", parameters);
    }

    public static Map<String, String> removeInternalParameter(String packageName, Map<String,
            String> parameters) {
        Map<String, String> filteredParameters = new HashMap<>();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            if (!entry.getKey().contains(packageName)) {
                filteredParameters.put(entry.getKey(), entry.getValue());
            }
        }
        return filteredParameters;
    }

    public static Map<String, String> removeInternalParameter(String packageName, Bundle
            arguments) {
        Map<String, String> parameters = new HashMap<>();
        for (String key : arguments.keySet()) {
            Object value = arguments.get(key);
            if (value instanceof String) {
                parameters.put(key, (String) value);
            }
        }
        return parameters;
    }

    public static void setViewTypeForTag(View view, ViewType viewType) {
        CommonUtils.setViewTypeIntegerForTag(view, viewType.ordinal());
    }

    /**
     * Finish share activity and set result.
     *
     * @param activity        The activity.
     * @param shareResultCode The share result code.
     */
    public static void shareFinish(Activity activity, int shareResultCode) {
        Log.e("shareFinish", shareResultCode + "__");
        Intent result = new Intent();
        result.putExtra(Key.KEY_SHARE.getValue(), shareResultCode);
        activity.setResult(Activity.RESULT_OK, result);
        activity.finish();
    }

    public static float sp2px(Context context, float spValue) {
        if (spValue <= 0) {
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return spValue * scale;
    }

    /**
     * 价格显删除线转换方法
     */
    public static SpannableStringBuilder spanStrickhrough(Context context, String text) {

        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new StrikethroughSpan(), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return style;
    }

    /**
     * 价格显示驼峰样式转换方法
     */
    public static SpannableString spanPriceString(Context context, String text, int size) {
        SpannableString spannableString = new SpannableString(text);
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(dpToPx(context, size));

        int end = text.indexOf(".");
        if (end == -1) {
            end = text.length();
        }

        spannableString.setSpan(absoluteSizeSpan, 0, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    /**
     * 退款、投诉页面文字处理方法
     * xxx:xxx分别显示两种颜色
     */
    public static SpannableStringBuilder spanStringColor(int colorOne, int colorTwo, String text) {
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(colorOne), 0, text.indexOf(":") + 1, Spannable
                .SPAN_INCLUSIVE_INCLUSIVE);

        Pattern p;
        p = Pattern.compile("\\d");
        Matcher m;
        m = p.matcher(text);//获得匹配
        boolean find = m.find();

        if (find) {
            if (text.indexOf("秒") != -1) {
                String[] str = text.split("[\\d]");
                int i = str[0].length();
                style.setSpan(new ForegroundColorSpan(colorTwo), i, text.indexOf("秒") + 1,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }
        }
        return style;
    }

    /**
     * 字符串中改变字体颜色方法
     */
    public static SpannableStringBuilder spanStringColor(String text, int color, int startIndex,
                                                         int spanStringLength) {
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        try {
            int length = text.length();

            if (startIndex < 0 || startIndex > length) {
                startIndex = 0;
            }

            int endIndex = startIndex + spanStringLength;

            if (endIndex < startIndex || endIndex > length) {
                endIndex = 0;
            }

            style.setSpan(new ForegroundColorSpan(color), startIndex, endIndex, Spannable
                    .SPAN_INCLUSIVE_INCLUSIVE);
        } catch (Exception e) {

        }
        return style;
    }

    public static SpannableString spanSizeString(Context context, String text, int start, int end, int size) {
        SpannableString spannableString = new SpannableString(text);
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(dpToPx(context, size));

        spannableString.setSpan(absoluteSizeSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString;
    }


    /**
     * 调用此方法输入所要转换的时间戳输入
     *
     * @param time
     * @return
     */
    public static String times(String time) {

        if (time.length() == 13) {

            long long_time = Long.valueOf(time);
            return times((long_time / 1000) + "", "yyyy-MM-dd HH:mm:ss");
        } else {
            return times(time, "yyyy-MM-dd HH:mm:ss");
        }
    }

    public static String Day(long mss) {
        long days = mss / (60 * 60 * 24);
        long hours = (mss % (60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % (60 * 60)) / (60);
        long seconds = (mss % (60));
        return days + " 天 " + hours + " 小时 " + minutes + " 分 " + seconds + " 秒 ";
    }

    public static String times(String time, String format) {
        if (!TextUtils.isEmpty(time)) {
            SimpleDateFormat sdr = new SimpleDateFormat(format);
            @SuppressWarnings("unused") long lcc = Long.valueOf(time);
            int i = Integer.parseInt(time);
            String times = sdr.format(new Date(i * 1000L));
            return times;
        }

        return time;
    }

    /**
     * Convert drawable to bitmap.
     *
     * @param drawable The drawable.
     * @return The bitmap.
     */
    public static Bitmap toBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable
                .getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static Bitmap toBitmap(String content) {
        //        byte[] data = content.getBytes();
        byte[] data = Base64.decode(content, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    /**
     * Convert bitmap to drawable.
     *
     * @param context The context.
     * @param bitmap  The bitmap to be converted.
     * @return The bitmap.
     */
    public static Drawable toDrawable(Context context, Bitmap bitmap) {
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public static boolean isWxHeadImg(String image) {
        return image.startsWith("http://wx.qlogo.cn");
    }

    public static String urlOfImage(String image, boolean loadwebp) {
        if (TextUtils.isEmpty(image)) {
            return "";
        }
        if (image.indexOf("http://") != 0 && image.indexOf("https://") != 0) {

            String type = image.substring(image.indexOf(".") + 1, image.length());

            if (!type.equals("gif")) {
                if (loadwebp) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        if (image.indexOf("x-oss-process=image") > 0) {
                            image += "/format,webp/quality,q_75";
                        } else {
                            image += "?x-oss-process=image/format,webp/quality,q_75";
                        }
                    }
                }
            }

            image = App.getInstance().getImageBaseUrl() + "/" + image;
        }
        return Utils.removeExtraSlashOfUrl(image);
    }

    /**
     * Get image full path.
     *
     * @param image Relative image path.
     * @return Absolute path.
     */
    public static String urlOfImage(String image) {
        return urlOfImage(image, true);
    }

    /**
     * Get wap full path.
     *
     * @return Absolute path.
     */
    public static String urlOfWap() {
        String baseUrl = Config.BASE_URL;
        int t = baseUrl.indexOf(".");
        String temp = baseUrl.substring(7, baseUrl.indexOf("."));
        String wapUrl = baseUrl.replace(temp, "m");
        return Utils.removeExtraSlashOfUrl(wapUrl + "/");
    }

    /**
     * @return
     */
    public static String urlOfWapShopindex() {
        return Utils.removeExtraSlashOfUrl(getMallMBaseUrl() + "/shop/");
    }

    /**
     * 调用此方法判断颜色
     *
     * @param color
     * @return
     */
    public static boolean isColor(String color) {
        if (Utils.isNull(color)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$");
        Matcher matcher = pattern.matcher(color);
        return matcher.matches();
    }

    /**
     * 让banner条自动滚动
     */

    public static void setBannerScroller(final ViewPager viewPager, final int size) {
        if (size > 1) {
            final Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int i = viewPager.getCurrentItem();
                    if ((i + 1) != size) {
                        viewPager.setCurrentItem(i + 1, true);
                    } else {
                        viewPager.setCurrentItem(0, false);
                    }
                    h.postDelayed(this, 3000);
                }
            }, 3000);
        } else {
            return;
        }
    }

    public static boolean openActivity(Context context, String className) {
        return CommonUtils.openActivity(context, className, "com.szy.yishopcustomer");
    }

    public static boolean openActivity(Context context, String className, Map<String, String>
            parameters) {
        return CommonUtils.openActivity(context, className, "com.szy.yishopcustomer", parameters);
    }

    //去除字符串中的html代码
    public static String removeHTMLLabel(String value) {
        if (TextUtils.isEmpty(value)) {
            return "";
        }

        return Html.fromHtml(value).toString();
    }

    //替换字符串中的html代码
    public static String replaceTHMLLabel(String value) {
        if (TextUtils.isEmpty(value)) {
            return "";
        }

        value = value.replace("<em>", "");
        value = value.replace("</em>", "");
        value = value.replace("<br>", "\n");
        value = value.replace("</br>", "");
        value = value.replace("<br/>", "\n");
        value = value.replace("&nbsp;", " ");
        value = value.replace("<div>", "\n");
        value = value.replace("</div>", "");
        value = value.replace("<p>", "");
        value = value.replace("</p>", "");

        return value;
    }

    public static void Dial(Context context, String phoneNumber) {
        if (Utils.isNull(phoneNumber)) {
            Toast.makeText(context, context.getString(R.string.emptyPhoneNumber), Toast
                    .LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 判断网络状态
     *
     * @param context
     * @return
     */
    public static boolean isNetAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return (info != null && info.isAvailable());
    }

    /**
     * 因为被混淆了，不能根据实际的属性名获取，那就根据类型获取吧
     *
     * @param mClass
     * @param obj
     * @return
     */
    public static Object getFieldValueByType(Class mClass, Object obj) {

        try {
            Class cls = obj.getClass();
            Field[] fs = cls.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                Field f = fs[i];
                f.setAccessible(true);
                //这个值是我要获取的
                if (f.getType().equals(mClass)) {
                    return f.get(obj);
                }
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }

    /**
     * 判断str1是否大于str2版本
     *
     * @param str1
     * @param str2
     * @return
     */
    public static int compareTo(String str1, String str2) {
        str1 = str1.replaceAll("[^\\d.]", "");
        str2 = str2.replaceAll("[^\\d.]", "");
        String[] str1Array = str1.split("\\.");
        String[] str2Array = str2.split("\\.");

        int minNumLength;

        //获取小的
        int lengthDifference = str1Array.length - str2Array.length;

        if (lengthDifference >= 0) {
            minNumLength = str2Array.length;
        } else {
            minNumLength = str1Array.length;
        }

        for (int i = 0; i < minNumLength; i++) {
            int code = 0;
            try {
                code = Integer.parseInt(str1Array[i]) - Integer.parseInt(str2Array[i]);
            } catch (Exception e) {

            }

            //大于0说明str1大，小于0说明str2大
            if (code > 0) return 1;
            if (code < 0) return -1;
        }
        //执行到这里说明前面都相等，那么
        return lengthDifference;
    }

    public static void qRCodeDialog(Context context, String imageUrl, String title, String tip, DialogInterface.OnCancelListener cancelListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_qrcode, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color
                .TRANSPARENT));
        alertDialog.getWindow().setContentView(view);

        final LinearLayout cancelButton = (LinearLayout) view.findViewById(R.id.layout);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View button) {
                getActivity(cancelButton).finish();
            }
        });

        alertDialog.setOnCancelListener(cancelListener);
        //alertDialog.setOnCancelListener(cancelListener);
        ImageView imageView = (ImageView) view.findViewById(R.id.qrcode_image);
        ImageLoader.displayImage(Utils.urlOfImage(imageUrl), imageView);

        TextView titleTextView = (TextView) view.findViewById(R.id.qrcode_title);
        if (title.equals("")) {
            titleTextView.setVisibility(View.GONE);
        } else {
            titleTextView.setText(title);
        }

        TextView textView = (TextView) view.findViewById(R.id.tip);

        if (tip.equals("")) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setText(tip);
        }
    }

    public static void qRCodeDialog(Context context, Bitmap image, String title, String tip, DialogInterface.OnCancelListener cancelListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_qrcode, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color
                .TRANSPARENT));
        alertDialog.getWindow().setContentView(view);

        final LinearLayout cancelButton = (LinearLayout) view.findViewById(R.id.layout);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View button) {
                getActivity(cancelButton).finish();
            }
        });

        alertDialog.setOnCancelListener(cancelListener);
        //alertDialog.setOnCancelListener(cancelListener);
        ImageView imageView = (ImageView) view.findViewById(R.id.qrcode_image);
        imageView.setImageBitmap(image);

        TextView titleTextView = (TextView) view.findViewById(R.id.qrcode_title);
        if (title.equals("")) {
            titleTextView.setVisibility(View.GONE);
        } else {
            titleTextView.setText(title);
        }

        TextView textView = (TextView) view.findViewById(R.id.tip);
        if (tip.equals("")) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setText(tip);
        }
    }

    public static void qRCodeDialog(Context context, String imageUrl, String title, String tip) {
        qRCodeDialog(context, imageUrl, title, tip, null);
    }

    public static void qRCodeDialog(Context context, Bitmap imageUrl, String title, String tip) {
        qRCodeDialog(context, imageUrl, title, tip, null);
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY
                .equals(state);
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }


    public static List<String> getBackgroundUrl(String content) {
        List<String> list = new ArrayList<String>();
        Pattern p_img = Pattern.compile("(background)=(\"|\')(//.*?)(\"|\')");
        Matcher m_img = p_img.matcher(content);
        boolean result_img = m_img.find();
        while (result_img) {
            String str_img = m_img.group(3);
            list.add(str_img);

            result_img = m_img.find();
        }
        return list;
    }

    /**
     * 获取img标签中的src值
     *
     * @param content
     * @return
     */
    public static List<String> getImgSrc(String content) {

        List<String> list = new ArrayList<String>();
        //目前img标签标示有3种表达式
        //<img alt="" src="1.jpg"/>   <img alt="" src="1.jpg"></img>     <img alt="" src="1.jpg">
        //开始匹配content中的<img />标签
        Pattern p_img = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
        Matcher m_img = p_img.matcher(content);
        boolean result_img = m_img.find();
        if (result_img) {
            while (result_img) {
                //获取到匹配的<img />标签中的内容
                String str_img = m_img.group(2);

                //开始匹配<img />标签中的src
                Pattern p_src = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
                Matcher m_src = p_src.matcher(str_img);
                if (m_src.find()) {
                    String str_src = m_src.group(3);
                    list.add(str_src);
                }
                //结束匹配<img />标签中的src

                //匹配content中是否存在下一个<img />标签，有则继续以上步骤匹配<img />标签中的src
                result_img = m_img.find();
            }
        }
        return list;
    }

    /**
     * webView中对图片的处理
     * 商品详情页面的详情
     *
     * @param desc
     * @return
     */
    public static String imgContentSetMaxHeight(String desc) {
        List<String> imgs = getImgSrc(desc);
        imgs.addAll(getBackgroundUrl(desc));
        for (int i = 0, len = imgs.size(); i < len; i++) {
            if (!imgs.get(i).startsWith("http:") && !imgs.get(i).startsWith("https:")) {
                desc = desc.replaceAll(imgs.get(i), "http:" + imgs.get(i));
            }
        }

        desc = "<html lang=\"zh-CN\"><head>" +
                "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">\n" +
                "<meta content=\"telephone=no,email=no,address=no\" name=\"format-detection\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">\n" +
                "<meta name=\"msapplication-tap-highlight\" content=\"no\">\n" +
                "<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n" +
                "<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\">\n" +
                "<meta name=\"wap-font-scale\" content=\"no\">\n" +
                "<style type=\"text/css\">\n" +
                "img {display: block;max-width: 100% !important;height: auto;}\n" +
                "div {display: block;max-width: 100% !important;height: auto;}\n" +
                "table {width: 100% !important;height: auto;margin: auto;}" +
                "body {font-family: Helvetica, Tahoma, Arial, \"Hiragino Sans GB\", \"Hiragino Sans GB W3\", STXihei, STHeiti, \"Microsoft YaHei\", Heiti, SimSun, sans-serif;min-width: 320px;max-width: 640px;}" +
                "</style></head>\n<body>" + desc + "</body></html>";

        return desc;
    }

    public static void amountLimit(final EditText et, final String maxNum) {
        amountLimit(et);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String edit = editable.toString();
                if (!TextUtils.isEmpty(edit)) {
                    double amount = 0.00;
                    double totalAmount = 0.00;
                    try {
                        amount = Double.parseDouble(edit);
                        totalAmount = Double.parseDouble(maxNum);

                        if (amount > totalAmount) {
                            et.setText(maxNum);
                            et.setSelection(maxNum.length());
                        }
                    } catch (Exception e) {

                    }
                }
            }
        });
    }

    /**
     * 对于金额的一些限制
     *
     * @param et
     */
    public static void amountLimit(EditText et) {
        et.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        et.setFilters(FilterForAmount());
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.e("onFocusChange", b + "_");
                if (!b) {
                    EditText tet = (EditText) view;
                    String number = tet.getText().toString();
                    if (!TextUtils.isEmpty(number)) {
                        double dd = 0.00;
                        try {
                            dd = Double.parseDouble(number);
                        } catch (Exception e) {

                        }
                        tet.setText(dd + "");
                    }
                }
            }
        });
    }

    /**
     * 金额输入过滤器
     *
     * @return
     */
    public static InputFilter[] FilterForAmount() {
        return new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        String str = dest.toString() + source;
                        int position;
                        boolean require = false;
                        if ((position = str.indexOf(".")) >= 0) {
                            require = str.length() - 1 - position > 2;
                        }
                        if (require) {
                            source = "";
                        }
                        return source;
                    }
                }
        };
    }

    /**
     * 过滤最大金额
     *
     * @param maxNum
     * @return
     */
    public static InputFilter[] filterMaxNumber(final Context mContext, final double maxNum, final String messgae) {
        return new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        String str = dest.toString() + source;

                        try {
                            double dStr = Double.parseDouble(str);
                            if (dStr > maxNum) {
                                Toast.makeText(mContext, messgae.replace("$1", maxNum + ""), Toast.LENGTH_SHORT).show();
                                return "";
                            }

                        } catch (Exception e) {
                            return "";
                        }

                        return source;
                    }
                }
        };
    }

    /**
     * 过滤商品数量
     *
     * @param maxNum
     * @return
     */
    public static InputFilter[] filterForGoodsNumber(final Context mContext, final int maxNum, final String messgae) {
        return new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        String str = dest.toString() + source;

                        try {
                            double dStr = Double.parseDouble(str);
                            if (dStr > maxNum) {
                                Toast.makeText(mContext, messgae.replace("$1", maxNum + ""), Toast.LENGTH_SHORT).show();
                                return "";
                            }

                        } catch (Exception e) {
                            return "";
                        }

                        return source;
                    }
                }
        };
    }

    public static String ToDBC(String input) {
        if (TextUtils.isEmpty(input)) {
            return "";
        }
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }

            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public static void showSoftInputFromWindowTwo(final Activity activity, final EditText editText) {

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();

        editText.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, 0);
            }
        }, 200);

//        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (imm != null) {
//            editText.requestFocus();
//            imm.showSoftInput(editText, 0);
//        }
    }


    /**
     * 隐藏软键盘
     */
    public static void hideSoftInputFromWindow(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String pathName = context.getString(R.string.app_name);
        File appDir = new File(Environment.getExternalStorageDirectory(), pathName);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));

        Toast.makeText(context, "图片已经保存到相册->" + pathName, Toast.LENGTH_SHORT).show();
    }


    private static final String SD_PATH = "/sdcard/jbx/pic/share/";
    private static final String IN_PATH = "/jbx/pic/share/";

    /****
     * 保存bitmap到本地
     * @param context
     * @param bitmap
     * @param imgName  图片名称
     * @return
     */
    public static String saveBitmapFile(Context context, Bitmap bitmap, String imgName) {

        String savePath;
        File filePic;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {
            savePath = context.getApplicationContext().getFilesDir().getAbsolutePath() + IN_PATH;
        }

        try {
            filePic = new File(savePath + imgName + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }

    //获取微商城的baseUrl
    public static String getMallMBaseUrl() {
        URI uri = URI.create(Config.BASE_URL);
        String host = uri.getHost();
        try {
            return uri.getScheme() + "://" + host.replace("www", "m");
        } catch (Exception e) {

        }
        return Config.BASE_URL;
    }

    /**
     * 通过uri获取图片并进行压缩
     *
     * @param uri
     */
    public static Bitmap getBitmapFormUri(Activity ac, Uri uri) throws IOException {
        InputStream input = ac.getContentResolver().openInputStream(uri);

        int degree = readPictureDegree(getRealPathFromURI(ac, uri));

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.RGB_565;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1)) return null;
        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0) be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;//optional
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = rotateBitmap(BitmapFactory.decodeStream(input, null, bitmapOptions), degree);
        input.close();

        return compressImage(bitmap);//再进行质量压缩
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//        int options = 100;
//        while (baos.toByteArray().length / 1024 > 1024) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
//            baos.reset();//重置baos即清空baos
//            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
//            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
// 这里压缩options%，把压缩后的数据存放到baos中
//            options -= 10;//每次都减少10
//        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        //把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /*
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean IDNumber(String idNumber) {
        String pattern = "(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])";
        return Pattern.matches(pattern, idNumber);
    }

    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI,
                new String[]{MediaStore.Images.ImageColumns.DATA},//
                null, null, null);
        if (cursor == null) result = contentURI.getPath();
        else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }

    /**
     * 读取照片旋转角度
     *
     * @param path 照片路径
     * @return 角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片，使图片保持正确的方向。
     *
     * @param bitmap  原始图片
     * @param degrees 原始图片的角度
     * @return Bitmap 旋转后的图片
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        if (degrees == 0 || null == bitmap) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (null != bitmap) {
            bitmap.recycle();
        }
        return bmp;
    }

    public static Bitmap bimapRound(Bitmap mBitmap, float index) {
        Bitmap bitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_4444);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        //设置矩形大小
        Rect rect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        RectF rectf = new RectF(rect);

        // 相当于清屏
        canvas.drawARGB(0, 0, 0, 0);
        //画圆角
        canvas.drawRoundRect(rectf, index, index, paint);
        // 取两层绘制，显示上层
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // 把原生的图片放到这个画布上，使之带有画布的效果
        canvas.drawBitmap(mBitmap, rect, rect, paint);
        return bitmap;

    }

    public static boolean isPasswordValid(String password) {
        if (CommonUtils.isNull(password)) {
            return false;
        }
        String regex = "^[^(\\u4e00-\\u9fa5)]{6,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    public static boolean isIncludeSpace(String password) {
        if (CommonUtils.isNull(password)) {
            return false;
        }
        String regex = "\\s+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    /**
     * 秒转化为天小时分秒字符串
     *
     * @param seconds
     * @return String
     */
    public static String formatSeconds(long seconds) {
        String timeStr = seconds + "秒";
        if (seconds > 60) {
            long second = seconds % 60;
            long min = seconds / 60;
            timeStr = min + "分" + second + "秒";
            if (min > 60) {
                min = (seconds / 60) % 60;
                long hour = (seconds / 60) / 60;
                timeStr = hour + "小时" + min + "分" + second + "秒";
                if (hour > 24) {
                    hour = ((seconds / 60) / 60) % 24;
                    long day = (((seconds / 60) / 60) / 24);
                    timeStr = day + "天" + hour + "小时" + min + "分" + second + "秒";
                }
            }
        }
        return timeStr;
    }

    /**
     * 服务器返回url，通过url去获取视频的第一帧
     * Android 原生给我们提供了一个MediaMetadataRetriever类
     * 提供了获取url视频第一帧的方法,返回Bitmap对象
     *
     * @param videoUrl
     * @return
     */
    public static Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }


    /**
     * 异步加载视频首帧图片
     *
     * @param imageView
     * @param url
     */

    public static void asyncloadImage(final ImageView imageView, final String url) {
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    Bitmap bitmap = (Bitmap) msg.obj;
                    if (imageView != null && url != null) {
                        imageView.setImageBitmap(bitmap);
                    }

                }
            }
        };
        // 子线程，开启子线程去下载或者去缓存目录找图片，并且返回图片在缓存目录的地址
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    //这个URI是图片下载到本地后的缓存目录中的URI
                    if (url != null) {
                        Bitmap bitmap = Utils.getNetVideoBitmap(url);
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = bitmap;
                        mHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }


    /**
     * 数字格式化显示
     * 小于万默认显示 大于万以1.7万方式显示最大是9999.9万
     * 大于亿以1.1亿方式显示最大没有限制都是亿单位
     *
     * @param num 格式化的数字
     * @param b   是否格式化百,为true,并且num大于99就显示99+,小于等于99就正常显示
     * @return
     */
    public static StringBuffer formatNum(String num, Boolean b) {
        StringBuffer sb = new StringBuffer();
        BigDecimal b0 = new BigDecimal("100");
        BigDecimal b1 = new BigDecimal("10000");
        BigDecimal b2 = new BigDecimal("100000000");
        BigDecimal b3 = new BigDecimal(num);

        String formatNumStr = "";
        String unit = "";

        // 以百为单位处理
        if (b) {
            if (b3.compareTo(b0) == 0 || b3.compareTo(b0) == 1) {
                return sb.append("99+");
            }
            return sb.append(num);
        }

        // 以万为单位处理
        if (b3.compareTo(b1) == -1) {
            formatNumStr = b3.toString();
        } else if ((b3.compareTo(b1) == 0 && b3.compareTo(b1) == 1)
                || b3.compareTo(b2) == -1) {
            unit = "万";

            formatNumStr = b3.divide(b1).toString();
        } else if (b3.compareTo(b2) == 0 || b3.compareTo(b2) == 1) {
            unit = "亿";
            formatNumStr = b3.divide(b2).toString();

        }
        if (!"".equals(formatNumStr)) {
            int i = formatNumStr.indexOf(".");
            if (i == -1) {
                sb.append(formatNumStr).append(unit);
            } else {
                i = i + 1;
                String v = formatNumStr.substring(i, i + 1);
                if (!v.equals("0")) {
                    sb.append(formatNumStr.substring(0, i + 1)).append(unit);
                } else {
                    sb.append(formatNumStr.substring(0, i - 1)).append(unit);
                }
            }
        }
        if (sb.length() == 0)
            return sb.append("0");
        return sb;
    }

    /***
     * Uri 转 File路径
     * @param context
     * @param uri
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);

                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {

            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static String saveFileImg(Context context, String imgName, Bitmap bitmap) {

//        String pathName = context.getString(R.string.app_name);
        String pathName = context.getPackageName();

        File file = new File(pathName);

        if (!file.exists()) {
            file.mkdir();
        }

        File fileImg = new File(pathName + "/" + imgName);


        try {
            FileOutputStream fos = new FileOutputStream(fileImg.getAbsoluteFile());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            Log.d("wyx", "分享图片地址:" + file.getAbsolutePath());

            return file.getAbsolutePath();

        } catch (FileNotFoundException e) {

            Log.d("wyx", "分享图片异常/FileNotFoundException");

            e.printStackTrace();
            return null;
        } catch (IOException e) {

            Log.d("wyx", "分享图片异常/IOException");

            e.printStackTrace();
            return null;
        }
    }

    /***
     *  生成二维码
     * @param codetext 二维码内容
     * @return
     */
    public static Bitmap createBarCode(Context context, String codetext) {
        Bitmap bitmap = null;
        BitMatrix bitMatrix = null;

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            bitMatrix = multiFormatWriter.encode(codetext, BarcodeFormat.QR_CODE, Utils.dpToPx(context, 104), Utils.dpToPx(context, 104));

            int w = bitMatrix.getWidth();
            int h = bitMatrix.getHeight();
            int[] pixels = new int[w * h];
            for (int y = 0; y < h; y++) {
                int offset = y * w;
                for (int x = 0; x < w; x++) {
                    pixels[offset + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
                }
            }
            bitmap = Bitmap.createBitmap(Utils.dpToPx(context, 104), Utils.dpToPx(context, 104), Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, w, 0, 0, w, h);

        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static Bitmap getBitmap(Context context, Bitmap bitmap) {
        int widhts = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.postScale(Utils.dpToPx(context, 80), Utils.dpToPx(context, 80));

        Bitmap newBt = Bitmap.createBitmap(bitmap, 0, 0, widhts, height, matrix, true);
        return newBt;
    }


    public static String toDistance(Double distance) {

        float distanceValue = Math.round((distance / 10f)) / 100f;
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(distanceValue);
    }

    public static String toM(Double distance) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(distance);
    }

    /***
     * 测量view的高度
     * @param view
     */
    public static void measureWidthAndHeight(View view) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 解决SDK 23以下 判断是否拥有相机权限
     * 抛异常方式
     *
     * @return
     */
    public static boolean cameraIsCanUse() {
        boolean isCanUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            isCanUse = false;
        }
        if (mCamera != null) {
            try {
                mCamera.release();
            } catch (Exception e) {
                e.printStackTrace();
                return isCanUse;
            }
        }
        return isCanUse;
    }

    public static boolean checkPhoneNumber(String phoneNumber) {

        Pattern pattern = Pattern.compile("^1[0-9]{10}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    /***
     * 图片数据地址是否为网络地址
     * @return
     */
    public static String setImgNetUrl(String headImgUrl, String imgUrl) {

        if (TextUtils.isEmpty(imgUrl)) {
            return "";
        }
        if (imgUrl.contains("http")) {
            return imgUrl;
        } else {
            return headImgUrl + imgUrl;
        }
    }

    /****
     * 判断szy url地址是否为 零售商城 weburl or 同城生活 weburl
     * @param Url
     * @return
     *  true 同城生活
     *  false  零售商城
     */
    public static boolean verCityLifeUrl(String Url) {

        if (Url.contains("webview_type=2")) {
            /**同城生活url*/
            return true;
        } else if (Url.contains("lbsnew/index.html") || Url.contains("lbs/index.html")) {
            return true;
        } else if (Url.contains("webview_type=1")) {
            /**零售商城url*/
            return false;
        } else {
            return false;
        }
    }

    /***
     * 添加 同城生活 附近or分类页面 距离排序title 数据
     * @param list
     * @return
     */
    public static List<NearTitleDistance> addDisTitleData(List<NearTitleDistance> list) {

        NearTitleDistance listModel_1 = new NearTitleDistance();
        listModel_1.disContent = "智能排序";
        listModel_1.disTance = 0;
        list.add(listModel_1);

        NearTitleDistance listModel_2 = new NearTitleDistance();
        listModel_2.disContent = "1km";
        listModel_2.disTance = 1000;
        list.add(listModel_2);

        NearTitleDistance listModel_3 = new NearTitleDistance();
        listModel_3.disContent = "3km";
        listModel_3.disTance = 3000;
        list.add(listModel_3);

        NearTitleDistance listModel_4 = new NearTitleDistance();
        listModel_4.disContent = "5km";
        listModel_4.disTance = 5000;
        list.add(listModel_4);

        NearTitleDistance listModel_5 = new NearTitleDistance();
        listModel_5.disContent = "10km";
        listModel_5.disTance = 10000;
        list.add(listModel_5);

        NearTitleDistance listModel_6 = new NearTitleDistance();
        listModel_6.disContent = "全城";
        listModel_6.disTance = 0;
        list.add(listModel_6);

        return list;
    }

    public static int[] getImageSize(String imgUrl) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //Bitmap bitmap = BitmapFactory.decodeFile(imgUrl, options);

        try {
            Bitmap bitmap = BitmapFactory.decodeStream(new URL(imgUrl).openStream(), null, options);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new int[]{options.outWidth, options.outHeight};
    }


    /****
     * 监听 RecyclerView 是否滑动到底部item
     *
     * @param recyclerView
     * @return
     */
    public static boolean isRecyViewBottom(RecyclerView recyclerView) {

        //得到当前显示的最后一个item的view
        View lastChildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount() - 1);
        //得到lastChildView的bottom坐标值
        int lastChildBottom = lastChildView.getBottom();
        //得到Recyclerview的底部坐标减去底部padding值，也就是显示内容最底部的坐标
        int recyclerBottom = recyclerView.getBottom() - recyclerView.getPaddingBottom();
        //通过这个lastChildView得到这个view当前的position值
        int lastPosition = recyclerView.getLayoutManager().getPosition(lastChildView);

        //判断lastChildView的bottom值跟recyclerBottom
        //判断lastPosition是不是最后一个position
        //如果两个条件都满足则说明是真正的滑动到了底部
        if (lastChildBottom == recyclerBottom && lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
            return true;
        } else {
            return false;
        }
    }

    /***
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }


    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }


    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    public static String getGoodId(String messageValue) {
        String goodId = "";
        if (messageValue.contains("/goods/")) {
            try {
                String goodsFirst = messageValue.split("\\?")[0];
                String webList = goodsFirst.substring(goodsFirst.lastIndexOf("/") + 1);
                goodsFirst = webList.substring(0, webList.indexOf("."));
                goodId = goodsFirst;
            } catch (Exception e) {
                goodId = null;
            }
        } else if (messageValue.contains("/goods-")) {
            try {
                goodId = messageValue.substring(messageValue.indexOf("-") + 1, messageValue.indexOf("."));
            } catch (Exception e) {
                goodId = null;
            }
        }
        return goodId;
    }
}
