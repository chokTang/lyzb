package com.like.utilslib.app;

/**
 * Created by longshao
 * on 2016/5/17.
 */

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.like.utilslib.UtilApp;

import java.io.File;
import java.util.List;

/**
 * 跟App相关的辅助类
 */
public class AppUtil {

    private AppUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    /**
     * 获取应用程序名称
     *
     * @return 当前应用的名字
     */
    public static String getAppName() {
        try {
            PackageManager packageManager = UtilApp.getIntance().getApplicationContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    UtilApp.getIntance().getApplicationContext().getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return UtilApp.getIntance().getApplicationContext().getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @return 当前应用的版本名称
     */
    public static String getVersionName() {
        try {
            PackageManager packageManager = UtilApp.getIntance().getApplicationContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    UtilApp.getIntance().getApplicationContext().getPackageName(), 0);
            return packageInfo.versionName;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本号信息]
     *
     * @return 当前应用的版本号
     */
    public static int getVersionCode() {
        try {
            PackageManager packageManager = UtilApp.getIntance().getApplicationContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    UtilApp.getIntance().getApplicationContext().getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * [获取应用程序版本号信息]
     *
     * @return 当前应用的版本号
     */
    public static String getPackageName() {
        return  UtilApp.getIntance().getApplicationContext().getPackageName();
    }

    /**
     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * @param context
     */
    public static void cleanInternalCache() {
        deleteFilesByDirectory(UtilApp.getIntance().getApplicationContext().getCacheDir());
    }

    /**
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * * @param context
     */
    public static void cleanDatabases() {
        deleteFilesByDirectory(new File("/data/data/"
                + UtilApp.getIntance().getApplicationContext().getPackageName() + "/databases"));
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
     * context
     */
    public static void cleanSharedPreference() {
        deleteFilesByDirectory(new File("/data/data/"
                + UtilApp.getIntance().getApplicationContext().getPackageName() + "/shared_prefs"));
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    /**
     * 调用系统安装了的应用分享
     *
     * @param title
     * @param url
     */
    public static void showSystemShareOption(Context context, final String title, final String url) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享：" + title);
        intent.putExtra(Intent.EXTRA_TEXT, title + " " + url);
        context.startActivity(Intent.createChooser(intent, "选择分享"));
    }

    /**
     * 安装应用 这里未处理8.0未知应用安装的问题
     *
     * @param file
     */
    public static void installAPK(Context context, File file) {
        if (file == null || !file.exists())
            return;
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(getUriForFile(context, file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 发送邮件
     *
     * @param subject 主题
     * @param content 内容
     * @param emails  邮件地址
     */
    public static void sendEmail(Context context, String subject, String content, String... emails) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            // 模拟器
            // intent.setType("text/plain");
            intent.setType("message/rfc822"); // 真机
            intent.putExtra(Intent.EXTRA_EMAIL, emails);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, content);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拨打电话
     *
     * @param number
     */
    public static void openDial(Context context, String number) {
        Uri uri = Uri.parse("tel:" + number);
        Intent it = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(it);
    }

    /**
     * 发送短信
     *
     * @param context
     * @param smsBody
     * @param tel
     */
    public static void openSMS(Context context, String smsBody, String tel) {
        Uri uri = Uri.parse("smsto:" + tel);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", smsBody);
        context.startActivity(it);
    }

    /**
     * 获取手机的IMEI码 需要读取权限
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI() {
        TelephonyManager tel = (TelephonyManager) UtilApp.getIntance().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tel.getDeviceId();
    }

    /**
     * 获取URI地址的文件
     *
     * @param file
     * @return
     */
    public static Uri getUriForFile(Context context, File file) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            fileUri = getUriForFile24(context, file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    /**
     * * 获取URI地址的文件
     *
     * @param file
     * @return
     */
    public static Uri getUriForFile24(Context context, File file) {
        Uri fileUri = android.support.v4.content.FileProvider.getUriForFile(context,
                context.getPackageName() + ".fileprovider",
                file);
        return fileUri;
    }

    /**
     * 检查是否安装了某应用
     *
     * @param packageName 包名
     * @return
     */
    public static boolean isAvilible(String packageName, Context mContext) {
        final PackageManager packageManager = mContext.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }
}

