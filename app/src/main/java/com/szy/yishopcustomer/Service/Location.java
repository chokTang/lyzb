package com.szy.yishopcustomer.Service;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.szy.common.Util.JSON;
import com.szy.yishopcustomer.Util.App;


/**
 * Created by Smart on 2017/8/31.
 */
public class Location {
    private static AMapLocationClientOption mLocationOption = null;
    private static AMapLocationClient mlocationClient = null;

    static {
        mlocationClient = new AMapLocationClient(App.getInstance().mContext);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();

        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(1000);
        mLocationOption.setOnceLocationLatest(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
    }

    public static void locationCallback(final OnLocationListener listener) {
        //设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {

            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        if (amapLocation.getLatitude() != 0 && amapLocation.getLongitude() != 0) {
                            App.getInstance().lat = amapLocation.getLatitude() + "";//获取纬度
                            App.getInstance().lng = amapLocation.getLongitude() + "";//获取经度

                            App.getInstance().city = amapLocation.getCity();
                            App.getInstance().adcode = amapLocation.getAdCode();
//                            App.getInstance().citycode = amapLocation.getCityCode();
                            App.getInstance().location = amapLocation.getAddress();
                            App.getInstance().district = amapLocation.getDistrict();

                            App.getInstance().province = amapLocation.getProvince();

                            if (listener != null) {
                                listener.onSuccess(amapLocation);
                            }
                        }

                    } else {
                        if (listener != null) {
                            listener.onError(amapLocation);
                        }
                    }
                }

                if (listener != null) {
                    listener.onFinished(amapLocation);
                }

                mlocationClient.stopLocation();
            }
        });
        mlocationClient.startLocation();
    }

    public interface OnLocationListener {
        void onSuccess(AMapLocation amapLocation);

        void onError(AMapLocation amapLocation);

        void onFinished(AMapLocation amapLocation);

        public class DefaultLocationListener implements OnLocationListener {

            @Override
            public void onSuccess(AMapLocation amapLocation) {
            }

            @Override
            public void onError(AMapLocation amapLocation) {
            }

            @Override
            public void onFinished(AMapLocation amapLocation) {
            }
        }
    }

    public static void stopLocation() {
        mlocationClient.stopLocation();
    }



}
