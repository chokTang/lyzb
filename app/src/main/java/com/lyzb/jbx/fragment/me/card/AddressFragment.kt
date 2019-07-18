package com.lyzb.jbx.fragment.me.card

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.amap.api.location.AMapLocation
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.model.*
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.geocoder.GeocodeQuery
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeResult
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.like.longshaolib.base.BaseToolbarFragment
import com.like.utilslib.other.LogUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.adapter.me.card.LocationAddressAdapter
import com.lyzb.jbx.dialog.CreateModelDialog
import com.lyzb.jbx.model.LocationAddressInfo
import com.lyzb.jbx.mvp.presenter.me.card.AmapPresenter
import com.lyzb.jbx.mvp.view.me.IAmapView
import kotlinx.android.synthetic.main.fragment_info_address.*
import me.yokeyword.fragmentation.ISupportFragment
import java.util.*


/**
 * Created by :TYK
 * Date: 2019/6/10  16:25
 * Desc: 地图页面
 */

open class AddressFragment : BaseToolbarFragment<AmapPresenter>(), IAmapView, PoiSearch.OnPoiSearchListener {

    private var mAMap: AMap? = null
    private var locationMarker: Marker? = null // 选择的点
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var title = ""
    var query: PoiSearch.Query? = null
    var poiResult: PoiResult? = null
    var listAdapter: LocationAddressAdapter? = null

    companion object {
        const val KEY_ADDRESS = "key_address"
        const val LAT = "lat"
        const val LNG = "lng"
        const val ADDRESS = "address"
        fun newIncetance(latitude: Double, longitude: Double, address: String): AddressFragment {
            val addressFragment = AddressFragment()
            val bundle = Bundle()
            bundle.putDouble(LAT, latitude)
            bundle.putDouble(LNG, longitude)
            bundle.putString(ADDRESS, address)
            addressFragment.arguments = bundle
            return addressFragment
        }
    }

    override fun getResId(): Any {
        return R.layout.fragment_info_address
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null && !TextUtils.isEmpty(bundle.getString(ADDRESS))) {
            latitude = bundle.getDouble(LAT)
            longitude = bundle.getDouble(LNG)
            title = bundle.getString(ADDRESS)
        }

    }

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
        onBack()
        setToolbarTitle("选择地址")

        mMapView.onCreate(savedInstanceState)
        if (mAMap == null) {
            mAMap = mMapView.map
        }
        if (latitude == 0.0 && longitude == 0.0) {//没有地址
            //地图定位,使自定位Maker处于地图中心
            com.szy.yishopcustomer.Service.Location.locationCallback(object : com.szy.yishopcustomer.Service.Location.OnLocationListener {
                override fun onSuccess(amapLocation: AMapLocation?) {
                    title = amapLocation!!.address
                    tv_city.text = amapLocation.city
                    addmark(amapLocation.latitude, amapLocation.longitude)
                }

                override fun onError(amapLocation: AMapLocation?) {
                }

                override fun onFinished(amapLocation: AMapLocation?) {
                }

            })
        } else {//已经有地址
            addmark(latitude, longitude)
        }


        //地图移动，自定义Maker始终处于地图中心点
        mAMap!!.setOnCameraChangeListener(object : AMap.OnCameraChangeListener {
            override fun onCameraChangeFinish(cameraPosition: CameraPosition?) {
                if (locationMarker != null) {
                    val latLng = cameraPosition!!.target
                    locationMarker!!.position = latLng
                    doSearchQuery(2, "", latLng.latitude, latLng.longitude)
                }
            }

            override fun onCameraChange(cameraPosition: CameraPosition?) {
            }

        })

        mAMap?.setOnMarkerClickListener(object : AMap.OnMarkerClickListener {
            override fun onMarkerClick(p0: Marker?): Boolean {
                LogUtil.loge("你点击了中心点")
                return true//返回true，消费此事件。
            }

        })
        mAMap?.setOnMapTouchListener(object : AMap.OnMapTouchListener {
            override fun onTouch(p0: MotionEvent?) {
                if (mAMap != null && locationMarker != null) {
                    if (locationMarker?.isVisible!!) {
                        locationMarker?.hideInfoWindow()
                    }
                }
            }

        })

        listAdapter = LocationAddressAdapter()
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_address.layoutManager = layoutManager
        rv_address.adapter = listAdapter

//        edt_search_address.addTextChangedListener(object : TextWatcher {
//            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
//                val keyWord = charSequence.toString()
//                if (TextUtils.isEmpty(keyWord)) {
//                    Toast.makeText(activity, "请输入搜索关键字", Toast.LENGTH_SHORT).show()
//                    return
//                } else {
//                    doSearchQuery(1, keyWord, 0.0, 0.0)
//                }
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//        })

        edt_search_address.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    var keyValue = edt_search_address.text.toString().trim();
                    if (keyValue.isEmpty()) {
                        showToast("请输入搜索关键字")
                    } else {
                        hideSoftInput()
                        doSearchQuery(1, keyValue, 0.0, 0.0)
                    }
                    return true
                }
                return false
            }
        })

        listAdapter!!.setOnItemClickListener { adapter, view, position ->
            val model = adapter.data[position] as LocationAddressInfo
            for (i in 0 until adapter.data.size) {
                (adapter.data[i] as LocationAddressInfo).isSelect = position == i
            }
            adapter.notifyItemChanged(position)

            addmark((adapter.data[position] as LocationAddressInfo).lat, (adapter.data[position] as LocationAddressInfo).lon)
            CreateModelDialog.newIntance()
                    .setTitle("确认地址")
                    .setHint("请确认地址")
                    .setContent(model.text)
                    .setSureBtn { }.setCancleBtn { }.setClickListener { view, edtString ->
                        if (model.text != edtString) {
                            addressToLatLon(edtString)
                            model.text = edtString
                        }
                        val bundle = Bundle()
                        bundle.putSerializable(KEY_ADDRESS, model)
                        setFragmentResult(ISupportFragment.RESULT_OK, bundle)
                        hideSoftInput()
                        mView.postDelayed({ pop() }, 100)
                    }.show(fragmentManager!!, "comfirmAddress")
        }
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
                    .title(title)
                    .position(LatLng(latitude, longitude))
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(resources, R.mipmap.ic_location_dark)))
                    .draggable(true))
        } else {
            locationMarker!!.position = LatLng(latitude, longitude)
            mAMap!!.invalidate()
        }
    }

    /**
     * 开始进行poi搜索
     * keyString 和经纬度只能传一个
     * keySting是根据关键词搜索  type = 1  keySting 不能为空
     * 经纬度是搜索周围  type = 2  keySting 必须为空
     */
    protected fun doSearchQuery(type: Int, keyString: String, latitude: Double, longitude: Double) {
        val currentPage = 0
        //不输入城市名称有些地方搜索不到
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = PoiSearch.Query(keyString, "", "")
        // 设置每页最多返回多少条poiitem
        query?.pageSize = 20
        // 设置查询页码
        query?.pageNum = currentPage

        //构造 PoiSearch 对象，并设置监听
        val poiSearch = PoiSearch(activity, query)
        if (type == 2) {
            poiSearch.bound = PoiSearch.SearchBound(LatLonPoint(latitude, longitude), 10000)
        }
        poiSearch.setOnPoiSearchListener(this)
        //调用 PoiSearch 的 searchPOIAsyn() 方法发送请求。
        poiSearch.searchPOIAsyn()
    }

    /**
     * 根据地址反编译经纬度
     */
    fun addressToLatLon(address: String) {
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(activity, "请输入有效的地址", Toast.LENGTH_LONG).show()
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
            val query = GeocodeQuery(address, "")//空表示全国
            // 根据地址执行异步地址解析
            search.getFromLocationNameAsyn(query)  // ①
        }
    }

    /**
     * POI信息查询回调方法
     */
    override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {
    }

    /**
     * POI信息查询回调方法
     */
    override fun onPoiSearched(result: PoiResult?, rCode: Int) {
        //rCode 为1000 时成功,其他为失败
        if (rCode == 1000) {
            // 解析result   获取搜索poi的结果
            if (result?.query != null) {
                if (result.query == query) {  // 是否是同一条
                    poiResult = result
                    val data = ArrayList<LocationAddressInfo>()//自己创建的数据集合
                    // 取得第一页的poiitem数据，页数从数字0开始
                    //poiResult.getPois()可以获取到PoiItem列表
                    val poiItems = poiResult?.pois

                    //若当前城市查询不到所需POI信息，可以通过result.getSearchSuggestionCitys()获取当前Poi搜索的建议城市
                    val suggestionCities = poiResult?.searchSuggestionCitys
                    //如果搜索关键字明显为误输入，则可通过result.getSearchSuggestionKeywords()方法得到搜索关键词建议。
                    val suggestionKeywords = poiResult?.searchSuggestionKeywords

                    //解析获取到的PoiItem列表
                    if (poiItems != null) {
                        for (item in poiItems) {
                            //获取经纬度对象
                            val llp = item.latLonPoint
                            val lon = llp.longitude
                            val lat = llp.latitude
                            //返回POI的名称
                            val title = item.title
                            //返回POI的地址
                            val text = item.snippet
                            data.add(LocationAddressInfo(lon, lat, title, text))
                        }
                    }
                    listAdapter?.setNewData(data)
                }
            } else {
                Toast.makeText(activity, "无搜索结果", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(activity, "错误码$rCode", Toast.LENGTH_SHORT).show()
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