package com.szy.yishopcustomer.Util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import static com.ta.utdid2.android.utils.TimeUtils.TAG;

public class PermissionUtils {


    /**
     * 判断是否拥有权限
     *
     * @param permissions
     * @return
     */
    public static boolean hasPermission(Context context, String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    /**
     * 请求权限
     */
    public static void requestPermission(AppCompatActivity context, int code, String... permissions) {
        ActivityCompat.requestPermissions(context, permissions, code);
        Utils.toastUtil.showToast(context, "如果拒绝授权,会导致应用无法正常使用");
    }

    /**
     * 请求权限
     */
    public static void requestPermission(Activity context, int code, String... permissions) {
        ActivityCompat.requestPermissions(context, permissions, code);
    }




}
