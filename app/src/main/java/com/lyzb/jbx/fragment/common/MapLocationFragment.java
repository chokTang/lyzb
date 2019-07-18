package com.lyzb.jbx.fragment.common;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.like.longshaolib.base.inter.IPermissonResultListener;
import com.lyzb.jbx.R;
import com.lyzb.jbx.fragment.base.BaseMapToolbarFragment;
import com.lyzb.jbx.util.AppPreference;
import com.lyzb.jbx.util.map.MapInfoWindowAdapter;
import com.szy.yishopcustomer.Activity.AmapNaviActivity;
import com.szy.yishopcustomer.Util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 定位的map功能
 */
public class MapLocationFragment extends BaseMapToolbarFragment implements AMapLocationListener {

    private TextureMapView map_location;

    //传地址参数
    private final static String PARAMS_ADRESS = "PARAMS_ADRESS";
    private String mAddress = "";
    //传经纬度参数
    private final static String PARAMS_LATITUDE = "PARAMS_LATITUDE";
    private double latitude = 0;
    private final static String PARAMS_LONGITUDE = "PARAMS_LONGITUDE";
    private double longitude = 0;

    //地理位置编译
    private GeocodeSearch geocoderSearch;
    private MapInfoWindowAdapter windowAdapter;

    //定位
    public AMapLocationClient mLocationClient = null;

    public static MapLocationFragment newIntance(String address) {
        return newIntance(address, 0, 0);
    }

    public static MapLocationFragment newIntance(double latitude, double longitude) {
        return newIntance("", latitude, longitude);
    }

    public static MapLocationFragment newIntance(String address, double latitude, double longitude) {
        MapLocationFragment fragment = new MapLocationFragment();
        Bundle args = new Bundle();
        args.putDouble(PARAMS_LATITUDE, latitude);
        args.putDouble(PARAMS_LONGITUDE, longitude);
        args.putString(PARAMS_ADRESS, address);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mAddress = args.getString(PARAMS_ADRESS);
            latitude = args.getDouble(PARAMS_LATITUDE, 0);
            longitude = args.getDouble(PARAMS_LONGITUDE, 0);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();
        setToolbarTitle("查看地理位置");

        map_location = (TextureMapView) findViewById(R.id.map_location);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        super.onInitData(savedInstanceState);
        windowAdapter = new MapInfoWindowAdapter(getContext());

        geocoderSearch = new GeocodeSearch(getContext());
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                if (geocodeResult != null) {
                    List<GeocodeAddress> addressList = geocodeResult.getGeocodeAddressList();
                    if (addressList != null && addressList.size() > 0) {
                        GeocodeAddress address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatLonPoint().getLatitude(), address.getLatLonPoint().getLongitude());
                        latitude = address.getLatLonPoint().getLatitude();
                        longitude = address.getLatLonPoint().getLongitude();
                        onLoadAddress(latLng);
                    }
                }
            }
        });
        onRequestPermisson(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                new IPermissonResultListener() {
                    @Override
                    public void onSuccess() {
                        if (TextUtils.isEmpty(mAddress) && latitude > 0 && longitude > 0) {
                            LatLng latLng = new LatLng(latitude, longitude);
                            onLoadAddress(latLng);
                        } else {
                            //如果地址为空 那么就是精确定位
                            if (TextUtils.isEmpty(mAddress)) {
                                LatLng latLng = new LatLng(latitude, longitude);
                                onLoadAddress(latLng);
                            } else {
                                String[] shengStr = mAddress.split("省");
                                String city = "";
                                if (shengStr.length > 1) {
                                    city = String.format("%省", shengStr[0]);
                                } else {
                                    String[] shiStr = mAddress.split("市");
                                    if (shengStr.length > 1) {
                                        city = String.format("%市", shiStr[0]);
                                    }
                                }
                                GeocodeQuery query = new GeocodeQuery(mAddress, city);
                                geocoderSearch.getFromLocationNameAsyn(query);
                            }
                        }
                    }

                    @Override
                    public void onFail(List<String> fail) {
                        showToast("拒绝权限导致定位失败");
                    }
                });
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_map_location;
    }

    @Override
    protected TextureMapView getMapView() {
        return (TextureMapView) findViewById(R.id.map_location);
    }

    /**
     * 定位目标位置
     *
     * @param latLng
     */
    private void onLoadAddress(LatLng latLng) {
        map_location.getMap().moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        map_location.getMap().moveCamera(CameraUpdateFactory.zoomTo(20));
        Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title(mAddress).snippet(
                mAddress).draggable(true).setFlat(true).icon(
                BitmapDescriptorFactory.fromBitmap(
                        BitmapFactory.decodeResource(getResources(),
                                R.mipmap.ic_location_dark))).draggable(true));
        map_location.getMap().setInfoWindowAdapter(windowAdapter);
        marker.showInfoWindow();
        windowAdapter.setContent(mAddress);
        windowAdapter.setLoactionCilck(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float latitude_me = AppPreference.getIntance().getMeMapLatitude();
                float longitude_me = AppPreference.getIntance().getMeMapLongitude();
                if (latitude_me == 0 && longitude_me == 0) {
                    if (!Utils.gPSIsOPen(getContext())) {
                        showToast("为了保证精准导航，请开启GPS定位");
                    } else {
                        onLoctaion();
                    }
                    return;
                }
                startMapNaviActivity(latitude_me, longitude_me);
            }
        });
    }

    private void onLoctaion() {
        mLocationClient = new AMapLocationClient(getContext());
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//采用高精度定位
        option.setOnceLocationLatest(true);//获取最近3s内精度最高的一次定位结果：
        option.setWifiActiveScan(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(this);

        onRequestPermisson(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS}, new IPermissonResultListener() {
            @Override
            public void onSuccess() {
                mLocationClient.startLocation();
            }

            @Override
            public void onFail(List<String> fail) {
                showToast("权限被拒绝，定位失败");
            }
        });
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                AppPreference.getIntance().setMeMapLatitude((float) aMapLocation.getLatitude());
                AppPreference.getIntance().setMeMapLongitude((float) aMapLocation.getLongitude());
                startMapNaviActivity(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            } else {
                showToast("为了保证精准导航，请开启GPS定位");
            }
        } else {
            showToast("为了保证精准导航，请开启GPS定位");
        }
    }

    /**
     * 跳转到导航页面
     */
    private void startMapNaviActivity(double latitude_me, double longitude_me) {
        JSONArray options = new JSONArray();

        JSONObject start = new JSONObject();
        try {
            start.put("latitude", latitude_me);
            start.put("KEY_LONGITUDE", longitude_me);
            options.put(null); // 起点

            JSONObject ways = new JSONObject();
            options.put(ways); // 途径点

            JSONObject end = new JSONObject();
            end.put("latitude", latitude);
            end.put("KEY_LONGITUDE", longitude);
            options.put(end); // 终点

            options.put(false); // 是否模拟驾驶
            options.put(0); // 高德坐标
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(getContext(), AmapNaviActivity.class);
        intent.putExtra(AmapNaviActivity.NAVI_WAY, AmapNaviActivity.NaviWay.DRIVE);
        intent.putExtra(AmapNaviActivity.NAVI_DATA, options.toString());
        startActivity(intent);
    }
}
