package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Express.PostmanInfoModel;
import com.szy.yishopcustomer.Util.HttpResultManager;

import butterknife.BindView;

/**
 * Created by liwei on 17/7/26.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ExpressDeliveryFragment extends YSCBaseFragment implements AMap.InfoWindowAdapter {

    private static final int HTTP_WHAT_POSTMAN_INFP = 1;

    @BindView(R.id.fragment_express_delivery_mapView)
    MapView mMapView;
    @BindView(R.id.message_close_button)
    ImageView mCloseButton;

    private AMap aMap;
    private String deliverySn;
    private String shopId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, parent, savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
            aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
        }

        mCloseButton.setOnClickListener(this);

        Intent intent = getActivity().getIntent();
        deliverySn = intent.getStringExtra(Key.KEY_DELIVERY_SN.getValue());
        shopId = intent.getStringExtra(Key.KEY_SHOP_ID.getValue());
        refresh();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_express_delivery;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.message_close_button:
                getActivity().finish();
                break;
            default:
                super.onClick(v);
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_POSTMAN_INFP:
                refreshSucceed(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    public void refresh() {
        CommonRequest mExpressRequest = new CommonRequest(Api.API_USER_ORDER_POSTMAN_INFO,
                HTTP_WHAT_POSTMAN_INFP);
        mExpressRequest.add("delivery_sn", deliverySn);
        mExpressRequest.add("shop_id", shopId);
        addRequest(mExpressRequest);
    }

    //设置数据
    private void refreshSucceed(String response) {
        HttpResultManager.resolve(response, PostmanInfoModel.class, new HttpResultManager
                .HttpResultCallBack<PostmanInfoModel>() {
            @Override
            public void onSuccess(PostmanInfoModel back) {
                //快递员位置
                String[] str_Array = back.data.deliver_point.split(",");
                Double lng = Double.parseDouble(str_Array[0]);
                Double lat = Double.parseDouble(str_Array[1]);
                LatLng latLng = new LatLng(lat, lng);

                //目的地位置
                String[] str_Array2 = back.data.receive_point.split(",");
                Double lng2 = Double.parseDouble(str_Array2[0]);
                Double lat2 = Double.parseDouble(str_Array2[1]);
                LatLng latLng2 = new LatLng(lat2, lng2);

                //改变视角
                changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 18,
                        30, 0)));

                //绘制点
                final Marker marker = aMap.addMarker(makerOptions(latLng, latLng2));
                marker.showInfoWindow();
            }
        });
    }

    //自定义Marker
    private MarkerOptions makerOptions(LatLng latLng1, LatLng latLng2) {
        float distance = AMapUtils.calculateLineDistance(latLng1, latLng2);

        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng1);
        markerOption.title("距离商家" + (int)distance + "m");
        //.snippet("");

        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource
                (getResources(), R.mipmap.ic_postmen_map1)));

        return markerOption;
    }

    //改变可视区域
    private void changeCamera(CameraUpdate update) {

        aMap.moveCamera(update);

    }

    //自定义Maker的Infowindow
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View infoContent = getActivity().getLayoutInflater().inflate(
                R.layout.custom_info_contents, null);
        render(marker, infoContent);
        return infoContent;
    }

    public void render(Marker marker, View view) {
        String title = marker.getTitle();
        TextView titleUi = ((TextView) view.findViewById(R.id.title));
        if (title != null) {
            SpannableString titleText = new SpannableString(title);
            titleText.setSpan(new ForegroundColorSpan(Color.RED), 4,
                    titleText.length()-1, 0);
            titleUi.setTextSize(15);
            titleUi.setText(titleText);

        } else {
            titleUi.setText("");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}
