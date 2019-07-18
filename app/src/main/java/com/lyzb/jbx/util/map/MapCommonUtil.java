package com.lyzb.jbx.util.map;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.lyzb.jbx.R;

public class MapCommonUtil {
    private TextureMapView mapView;

    private static class Holder {
        private static final MapCommonUtil INTANCE = new MapCommonUtil();
    }

    public static MapCommonUtil getIntance() {
        return Holder.INTANCE;
    }

    public MapCommonUtil withMapView(TextureMapView mapView) {
        this.mapView = mapView;
        return this;
    }

    /**
     * 设置地图类型
     *
     * @param type 0:普通地图[矢量地图] 1：卫星地图  2:夜间地图 3:导航地图
     */
    public MapCommonUtil setMapType(int type) {
        if (this.mapView == null) return this;
        switch (type) {
            case 0:
                this.mapView.getMap().setMapType(AMap.MAP_TYPE_NORMAL);
                break;
            case 1:
                this.mapView.getMap().setMapType(AMap.MAP_TYPE_SATELLITE);
                break;
            case 2:
                this.mapView.getMap().setMapType(AMap.MAP_TYPE_NIGHT);
                break;
            case 3:
                this.mapView.getMap().setMapType(AMap.MAP_TYPE_NAVI);
                break;
            default:
                break;
        }
        return this;
    }

    /**
     * 是否开启实时交通信息
     *
     * @param enabled
     */
    public MapCommonUtil setTrafficEnabled(boolean enabled) {
        if (this.mapView == null) return this;
        this.mapView.getMap().setTrafficEnabled(enabled);
        return this;
    }

    /**
     * 关闭一切手势
     *
     * @param enabled
     */
    public MapCommonUtil setAllGesturesEnabled(boolean enabled) {
        if (this.mapView == null) return this;
        this.mapView.getMap().getUiSettings().setAllGesturesEnabled(enabled);
        return this;
    }

    /**
     * 屏蔽旋转 true 为开启false为关闭
     *
     * @param enabled
     * @return
     */
    public MapCommonUtil setRotateGesturesEnabled(boolean enabled) {
        if (this.mapView == null) return this;
        this.mapView.getMap().getUiSettings().setRotateGesturesEnabled(enabled);
        return this;
    }

    /**
     * 设置地图缩放界别
     *
     * @param zoom 3-20
     * @return
     */
    public MapCommonUtil setZoom(int zoom) {
        if (this.mapView == null) return this;
        this.mapView.getMap().moveCamera(CameraUpdateFactory.zoomTo(zoom));
        return this;
    }

    /**
     * 是否允许缩放手势
     *
     * @param enabled
     * @return
     */
    public MapCommonUtil setZoomGesturesEnabled(boolean enabled) {
        if (this.mapView == null) return this;
        this.mapView.getMap().getUiSettings().setZoomGesturesEnabled(enabled);
        return this;
    }

    private boolean mIsLocation = false;//是否显示定位


    //初始化空的地图，无放大放小图标，无右下角百度图标
    public void ininEmptyMap() {
        if (this.mapView == null) return;
        mapView.getMap().getUiSettings().setZoomControlsEnabled(false);//隐藏放大缩小图标
        mapView.getMap().getUiSettings().setMyLocationButtonEnabled(false);//是否显示图标
        mapView.getMap().getUiSettings().setCompassEnabled(false);//是否显示指南针
    }

    /**
     * 再次定位
     */
    public void reLocation() {
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.strokeColor(android.R.color.transparent);
        myLocationStyle.radiusFillColor(R.color.red);
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.union_address));//设置定位图标
        this.mapView.getMap().setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        mapView.getMap().setMyLocationEnabled(true);// 设置为true表示
    }
}
