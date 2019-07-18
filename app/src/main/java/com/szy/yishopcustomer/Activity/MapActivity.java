package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.lyzb.jbx.R;
import com.szy.common.Fragment.CommonFragment;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.PickupAddresModel;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * Created by 宗仁 on 16/9/26.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class MapActivity extends YSCBaseActivity {
    String markerName;
    String latitude_me;
    String longitude_me;

    String latitude;
    String longitude;
    AMap aMap;

    private String pickup_id;

    @BindView(R.id.activity_map_mapView)
    MapView mMapView;
    @BindView(R.id.map_button)
    TextView mButton;
    @BindView(R.id.map_shop_name_textview)
    TextView mShopNameTextView;
    @BindView(R.id.map_shop_simply_textview)
    TextView mShopSimplyTextView;
    private String markerSnippet;

    public void driveNavi(View view) throws org.json.JSONException {
        if (!Utils.gPSIsOPen(this)) {
            Utils.toastUtil.showToast(MapActivity.this, "为了保证精准导航，请开启GPS定位");
            return;
        }

        JSONArray options = new JSONArray();

        JSONObject start = new JSONObject();
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

        Intent intent = new Intent(this, AmapNaviActivity.class);
        intent.putExtra(AmapNaviActivity.NAVI_WAY, AmapNaviActivity.NaviWay.DRIVE);
        intent.putExtra(AmapNaviActivity.NAVI_DATA, options.toString());
        startActivity(intent);
    }

    @Override
    protected CommonFragment createFragment() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.activity_map;
        super.onCreate(savedInstanceState);
        if (getIntent() == null) {
            return;
        }
        Intent intent = getIntent();

        latitude_me = intent.getStringExtra(Key.KEY_LATITUDE_ME.getValue());
        longitude_me = intent.getStringExtra(Key.KEY_LONGITUDE_ME.getValue());
        pickup_id = intent.getStringExtra(Key.KEY_PICKUP_ID.getValue());

        String title = intent.getStringExtra(Key.KEY_TITLE.getValue());
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }


        mMapView.onCreate(savedInstanceState);
        if (TextUtils.isEmpty(pickup_id)) {
            markerName = intent.getStringExtra(Key.KEY_MARKER_NAME.getValue());
            markerSnippet = intent.getStringExtra(Key.KEY_MARKER_SNIPPET.getValue());
            if (TextUtils.isEmpty(markerSnippet)) {
                markerSnippet = "";
            }

            if (TextUtils.isEmpty(markerName)) {
                markerName = "";
            }

            latitude = intent.getStringExtra(Key.KEY_LATITUDE.getValue());
            longitude = intent.getStringExtra(Key.KEY_LONGITUDE.getValue());

            action();
        } else {
            refresh();
        }

    }


    //设置数据
    private void refreshSucceed(String response) {
        HttpResultManager.resolve(response, PickupAddresModel.class, new HttpResultManager
                .HttpResultCallBack<PickupAddresModel>() {
            @Override
            public void onSuccess(PickupAddresModel back) {
                PickupAddresModel model = back;
                markerName = model.data.pickup_name;
                markerSnippet = model.data.pickup_address;
                latitude = model.data.address_lat;
                longitude = model.data.address_lng;
                action();
            }
        });
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_PICKUP_ADDRESS:
                refreshSucceed(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }


    private static final int HTTP_WHAT_PICKUP_ADDRESS = 1;

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_USER_ORDER_GET_PICKUP_ADDRESS, HTTP_WHAT_PICKUP_ADDRESS);
        request.add("id", pickup_id);
        addRequest(request);
    }

    private void action() {
        if (Utils.isNull(latitude) || Utils.isNull(longitude)) {
            return;
        }
        if (Utils.isNull(markerName)) {
            markerName = getString(R.string.shop);
            markerSnippet = getString(R.string.simply);
        }
        mShopNameTextView.setText(markerName);
        mShopSimplyTextView.setText(Html.fromHtml(markerSnippet).toString());
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        LatLng latlng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        //设置中心点和缩放比例  
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latlng));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
        Marker marker = aMap.addMarker(
                new MarkerOptions().position(latlng).title(markerName).snippet(
                        markerSnippet).draggable(true).setFlat(true).icon(
                        BitmapDescriptorFactory.fromBitmap(
                                BitmapFactory.decodeResource(getResources(),
                                        R.mipmap.ic_location_dark))).draggable(true));
        //        marker.showInfoWindow();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
}
