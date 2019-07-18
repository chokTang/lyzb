package com.lyzb.jbx.util.map;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;

import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.lyzb.jbx.util.GPSUtil;

public class MapUtil {

    public final static String BAIDU_PKG = "com.baidu.BaiduMap"; //百度地图的包名

    public final static String GAODE_PKG = "com.autonavi.minimap";//高德地图的包名

    public final static String TENCTENT_PKG = "com.tencent.map";//腾讯地图的包名

    public static void openGaoDe(Context context, double latitude, double longitude) {
        Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("androidamap://navi?sourceApplication=你的APP名称&lat=" + latitude + "&lon=" + longitude + "&dev=0"));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }

    public static void openBaidu(Context context, MarkerOptions markerOption) {
        Intent i1 = new Intent();
        double[] position = GPSUtil.gcj02_To_Bd09(markerOption.getPosition().latitude, markerOption.getPosition().longitude);
        i1.setData(Uri.parse("baidumap://map/geocoder?location=" + position[0] + "," + position[1]));
        context.startActivity(i1);
    }

    public static void openTencent(Context context,double latitude, double longitude,String mAddressStr) {
        LatLng endPoint =new LatLng(latitude, longitude);
        StringBuffer stringBuffer = new StringBuffer("qqmap://map/routeplan?type=drive")
                .append("&tocoord=").append(endPoint.latitude).append(",").append(endPoint.longitude).append("&to=" + mAddressStr);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
        context.startActivity(intent);
    }

    /**
     * 检测地图应用是否安装
     *
     * @param context
     * @param packagename
     * @return
     */
    public static boolean checkMapAppsIsExist(Context context, String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (Exception e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }
}