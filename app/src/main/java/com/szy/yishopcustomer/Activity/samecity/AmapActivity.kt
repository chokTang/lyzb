package com.szy.yishopcustomer.Activity.samecity

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.model.BitmapDescriptorFactory
import com.amap.api.maps2d.model.LatLng
import com.amap.api.maps2d.model.Marker
import com.amap.api.maps2d.model.MarkerOptions
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.Companion.KEY_LAT
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.Companion.KEY_LONT
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.Companion.KEY_ADDRESS
import com.lyzb.jbx.R
import kotlinx.android.synthetic.main.activity_amap.*

class AmapActivity : AppCompatActivity() {
    private var mAMap: AMap? = null
    private var locationMarker: Marker? = null // 选择的点
    var latitude:Double = 0.0
    var longitude:Double = 0.0
    var shopAddress = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amap)
        mMapView.onCreate(savedInstanceState)
        val intent = intent
        latitude = intent.getStringExtra(KEY_LAT).toDouble()
        longitude = intent.getStringExtra(KEY_LONT).toDouble()
        shopAddress = intent.getStringExtra(KEY_ADDRESS)
        if (mAMap == null) {
            mAMap = mMapView.map
        }
       addmark(latitude,longitude)


        mAMap?.setOnMarkerClickListener(object :AMap.OnMarkerClickListener{
            override fun onMarkerClick(p0: Marker?): Boolean {
                locationMarker = p0//保存当前点击的Marker，以便点击地图其他地方设置InfoWindow消失
                return false//返回true，消费此事件。
            }

        })
        mAMap?.setOnMapTouchListener(object :AMap.OnMapTouchListener{
            override fun onTouch(p0: MotionEvent?) {
                if (mAMap != null && locationMarker != null) {
                    if (locationMarker?.isVisible!!){
                        locationMarker?.hideInfoWindow()
                    }
                }
            }

        })

    }


    private fun addmark(latitude: Double, longitude: Double) {
        val latlng = LatLng(latitude, longitude)
        //设置中心点和缩放比例  
        mAMap!!.moveCamera(CameraUpdateFactory.changeLatLng(latlng))
        mAMap!!.moveCamera(CameraUpdateFactory.zoomTo(20f))
        if (locationMarker == null) {
            locationMarker = mAMap!!.addMarker(MarkerOptions()
                    .title(shopAddress)
                    .position(LatLng(latitude, longitude))
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(resources, R.mipmap.ic_location_dark)))
                    .draggable(true))
        } else {
            locationMarker!!.position = LatLng(latitude, longitude)
            mAMap!!.invalidate()
        }
        locationMarker!!.showInfoWindow()

    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume()
    }


    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mMapView.onPause()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy()
    }

}