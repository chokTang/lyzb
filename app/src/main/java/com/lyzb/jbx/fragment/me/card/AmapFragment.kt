package com.lyzb.jbx.fragment.me.card

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.model.BitmapDescriptorFactory
import com.amap.api.maps2d.model.LatLng
import com.amap.api.maps2d.model.Marker
import com.amap.api.maps2d.model.MarkerOptions
import com.amap.api.services.geocoder.GeocodeQuery
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeResult
import com.like.longshaolib.base.BaseFragment
import com.lyzb.jbx.R
import com.lyzb.jbx.dialog.MapSelectDialog
import com.lyzb.jbx.dialog.MapSelectDialog.Companion.BAIDU
import com.lyzb.jbx.dialog.MapSelectDialog.Companion.GAODE
import com.lyzb.jbx.dialog.MapSelectDialog.Companion.TENCENT
import com.lyzb.jbx.mvp.presenter.me.card.AmapPresenter
import com.lyzb.jbx.mvp.view.me.IAmapView
import com.lyzb.jbx.util.map.MapUtil
import kotlinx.android.synthetic.main.fragment_amap.*


/**
 * Created by :TYK
 * Date: 2019/6/10  16:25
 * Desc: 地图页面
 */

class AmapFragment : BaseFragment<AmapPresenter>(), IAmapView, View.OnClickListener {

    companion object {
        const val KEY_ADDRESS = "address"
        const val KEY_LAT = "lat"
        const val KEY_LNG = "long"
        fun newIncetance(string: String, latitude: Double, longitude: Double): AmapFragment {
            val fragment = AmapFragment()
            val bundle = Bundle()
            bundle.putString(KEY_ADDRESS, string)
            bundle.putDouble(KEY_LAT, latitude)
            bundle.putDouble(KEY_LNG, longitude)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var mAMap: AMap? = null
    private var locationMarker: Marker? = null // 选择的点
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var shopAddress = ""
    val mapList: MutableList<String> = arrayListOf()
    override fun getResId(): Any {
        return R.layout.fragment_amap
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        mMapView.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            shopAddress = bundle.getString(KEY_ADDRESS)
            latitude = bundle.getDouble(KEY_LAT)
            longitude = bundle.getDouble(KEY_LNG)
        }
        if (mAMap == null) {
            mAMap = mMapView.map
        }
        if (MapUtil.checkMapAppsIsExist(activity, MapUtil.GAODE_PKG)) {//已经安装了 高德
            mapList.add(GAODE)
        }
        if (MapUtil.checkMapAppsIsExist(activity, MapUtil.BAIDU_PKG)) {//已经安装了百度
            mapList.add(BAIDU)
        }
        if (MapUtil.checkMapAppsIsExist(activity, MapUtil.TENCTENT_PKG)) {//已经安装了腾讯
            mapList.add(TENCENT)
        }

        if (latitude!=0.0&&longitude!=0.0) {
            addmark(latitude, longitude)
        } else {
            val search = GeocodeSearch(activity)
            search.setOnGeocodeSearchListener(object : GeocodeSearch.OnGeocodeSearchListener {
                override fun onRegeocodeSearched(p0: RegeocodeResult?, p1: Int) {
                }

                override fun onGeocodeSearched(geocodeResult: GeocodeResult?, p1: Int) {
                    // 获取解析得到的第一个地址
                    val geo = geocodeResult!!.geocodeAddressList[0]
                    // 获取解析得到的经纬度
                    val pos = geo.latLonPoint
                    addmark(pos.latitude, pos.longitude)
                }

            })
            val query = GeocodeQuery(shopAddress, "")//空表示全国
            // 根据地址执行异步地址解析
            search.getFromLocationNameAsyn(query)  // ①
        }



        mAMap?.setOnMarkerClickListener(object : AMap.OnMarkerClickListener {
            override fun onMarkerClick(p0: Marker?): Boolean {
                locationMarker = p0//保存当前点击的Marker，以便点击地图其他地方设置InfoWindow消失
                return false//返回true，消费此事件。
            }

        })



        mAMap?.setOnMapTouchListener(object : AMap.OnMapTouchListener {
            override fun onTouch(p0: MotionEvent?) {
                if (mAMap != null && locationMarker != null) {
                    if (locationMarker?.isVisible!!) {
//                        locationMarker?.hideInfoWindow()
                    }
                }
            }

        })


        img_back.setOnClickListener(this)
        tv_other.setOnClickListener(this)
    }


    override fun onInitData(savedInstanceState: Bundle?) {
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


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_back -> {
                pop()
            }
            R.id.tv_other -> {
                if (mapList.size == 0) {
                    showToast("您手机上未安装地图应用")
                } else {
                    MapSelectDialog.newIncetance()
                            .setList(mapList)
                            .setNewClickListener(object : MapSelectDialog.ClickListener {
                                override fun click(v: View?) {
                                    when (v!!.id) {
                                        R.id.tv_baidu -> {//百度
                                            val options = MarkerOptions()
                                            val latLng = LatLng(latitude, longitude)
                                            options.position(latLng)
                                            MapUtil.openBaidu(activity, options)
                                        }
                                        R.id.tv_gaode -> {//高德
                                            MapUtil.openGaoDe(activity, latitude, longitude)
                                        }
                                        R.id.tv_tencent -> {//腾讯
                                            MapUtil.openTencent(activity, latitude, longitude, shopAddress)
                                        }
                                    }

                                }
                            }).show(childFragmentManager, "createMapDialog")
                }
            }

        }
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
        if (null != mMapView) {
            mMapView.onDestroy()
        }
    }

}