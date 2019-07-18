package com.lyzb.jbx.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.TextureMapView;
import com.like.longshaolib.base.BaseToolbarFragment;
import com.like.longshaolib.base.presenter.BasePresenter;
import com.lyzb.jbx.util.map.MapCommonUtil;

/**
 * 基础的MapView Toolbar
 *
 * @param <P>
 */
public abstract class BaseMapToolbarFragment<P extends BasePresenter> extends BaseToolbarFragment<P> {

    protected abstract TextureMapView getMapView();

    protected TextureMapView mapView;
    protected AMap aMap;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mapView = getMapView();
        aMap = mapView.getMap();
        mapView.onCreate(savedInstanceState);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        MapCommonUtil.getIntance()
                .withMapView(mapView)
                .setMapType(0)
                .setRotateGesturesEnabled(false)
                .setZoom(16)
                .setZoomGesturesEnabled(true)
                .ininEmptyMap();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null)
            mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null)
            mapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null)
            mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null)
            mapView.onPause();
    }

}