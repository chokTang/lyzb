package com.szy.yishopcustomer.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.lyzb.jbx.R
import com.szy.yishopcustomer.Activity.samecity.AmapActivity
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.Companion.KEY_ADDRESS
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.Companion.KEY_LAT
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.Companion.KEY_LONT
import com.szy.yishopcustomer.Adapter.NearShopMoreAdapter
import com.szy.yishopcustomer.Constant.*
import com.szy.yishopcustomer.Util.*
import com.szy.yishopcustomer.Util.json.GSONUtil
import com.szy.yishopcustomer.Util.json.GsonUtils
import com.szy.yishopcustomer.ViewModel.HomeShopAndProductBean
import com.szy.yishopcustomer.ViewModel.NearShopMoreModel
import com.szy.yishopcustomer.ViewModel.samecity.ShopDetailFloatingBean
import com.yolanda.nohttp.NoHttp
import com.yolanda.nohttp.RequestMethod
import com.yolanda.nohttp.rest.OnResponseListener
import com.yolanda.nohttp.rest.Response
import kotlinx.android.synthetic.main.activity_near_shop_more.*
import me.zongren.pullablelayout.Constant.Result
import me.zongren.pullablelayout.Inteface.OnPullListener
import me.zongren.pullablelayout.Main.PullableComponent
import org.json.JSONObject

/**
 * Created by :TYK
 * Date: 2018/12/18 9:04
 * Desc:附近商家-更多
 */
class NearShopMoreActivity : AppCompatActivity(), OnPullListener, View.OnClickListener {


    companion object {
        const val ADDRESS = "address"
        const val BUNDLE = "bundle"
    }

    //附近商家 分页int
    private var page = 1
    private var more = true
    var adapter: NearShopMoreAdapter? = null
    var adverList: List<HomeShopAndProductBean.AdvertBean> = arrayListOf()
    var nearShopBeanList: List<HomeShopAndProductBean.NearbyBean> = arrayListOf()
    var nearShopBeanMoreList: MutableList<HomeShopAndProductBean.NearbyBean> = arrayListOf()
    var nearModelList: MutableList<NearShopMoreModel> = arrayListOf()


    override fun onSizeChanged(pullableComponent: PullableComponent?, mSize: Int) {
    }

    override fun onLoading(pullableComponent: PullableComponent?) {
        page = 1
        getNearShopMoreList()
    }

    override fun onCanceled(pullableComponent: PullableComponent?) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_near_shop_more)
        near_shop_more_pullableLayout.topComponent.setOnPullListener(this)
        initView()
        initListener()
    }


    /**
     * 初始化
     */
    private fun initView() {

        val intent = intent
//        if (TextUtils.isEmpty(App.getInstance().lat)) {
//            tv_address.text = intent.getBundleExtra(BUNDLE).getString(ADDRESS)
//        } else {
//            tv_address.text =App.getInstance().addressDetail
//        }

        tv_address.text = intent.getBundleExtra(BUNDLE).getString(ADDRESS)

        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = NearShopMoreAdapter(R.layout.item_home_near_shop)
        adapter!!.onClickListener = this
        rv_near_shop_more.layoutManager = manager
        rv_near_shop_more.adapter = adapter

        getAdverTisement()
        //获取附近商店数据
        getNearShopMoreList()
        rv_near_shop_more.addOnScrollListener(mOnScrollListener)

    }


    /**
     * 设置监听
     */
    private fun initListener() {
        img_near_shop_more_back.setOnClickListener(this)
        edt_search_near_shop_more.setOnClickListener(this)
        tv_address.setOnClickListener(this)
    }


    public val mOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            /*** 底部监听  */
            if (Utils.isRecyViewBottom(recyclerView!!)) {

                /** 加载更多  */
                if (more) {
                    page++
                    getNearShopMoreList()
                } else {
                    Toast.makeText(this@NearShopMoreActivity, "暂无更多数据", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * 获取附近商家列表
     */
    private fun getNearShopMoreList() {
        val requestQueue = NoHttp.newRequestQueue()
        val request = NoHttp.createStringRequest(Api.API_HOME_NEAR_SHOP_URL, RequestMethod.GET)
        RequestAddHead.addNoHttpHead(request, this, Api.API_HOME_NEAR_SHOP_URL, "GET")

        if (App.getInstance().isLocation) {//选择城市
            if (!TextUtils.isEmpty(App.getInstance().lat)) {//定位成功
                if (App.getInstance().clickChangeCity) {//选择城市(点击变换的)
                    request.add("areaId", App.getInstance().home_area_code)
                    request.add("regionCode", App.getInstance().city_code)
                    request.add("page", page)
                    request.add("size", 6)
                } else {
                    request.add("areaId", App.getInstance().adcode)
                    request.add("latitude", App.getInstance().lat)
                    request.add("longitude", App.getInstance().lng)
                    request.add("page", page)
                    request.add("size", 6)
                }
            } else {
                request.add("areaId", App.getInstance().adcode)
                request.add("latitude", App.getInstance().lat)
                request.add("longitude", App.getInstance().lng)
                request.add("page", page)
                request.add("size", 6)
            }
        } else {//定位失败(选择了城市)
            request.add("areaId", App.getInstance().home_area_code)
            request.add("regionCode", App.getInstance().city_code)
            request.add("page", page)
            request.add("size", 6)
        }

        requestQueue.add(HttpWhat.HTTP_HOME_NEAR_SHOP.value, request, object : OnResponseListener<String> {
            override fun onFinish(what: Int) {
            }

            override fun onSucceed(what: Int, response: Response<String>?) {
                LogUtils.e("数据--->" + response)
                if (response!!.responseCode() == 200) {
                    near_shop_more_pullableLayout.topComponent.finish(Result.SUCCEED)
                    requesCallBack(what, response)
                }
            }

            override fun onFailed(what: Int, response: Response<String>?) {
                near_shop_more_pullableLayout.topComponent.finish(Result.FAILED)
                Utils.makeToast(this@NearShopMoreActivity, R.string.data_error)
            }

            override fun onStart(what: Int) {
            }

        })
    }


    /**
     * 获取广告数据
     */
    private fun getAdverTisement() {
        val requestQueue = NoHttp.newRequestQueue()
        val request = NoHttp.createStringRequest(Api.API_SHOP_DETAIL_FLOATING_URL, RequestMethod.GET)
        RequestAddHead.addNoHttpHead(request, this, Api.API_SHOP_DETAIL_FLOATING_URL, "GET")

        request.add("areaId", App.getInstance().city_code)
        request.add("position", "A7")
        requestQueue.add(HttpWhat.HTTP_SHOP_DETAIL_FLOATING.value, request, object : OnResponseListener<String> {
            override fun onFinish(what: Int) {
            }

            override fun onSucceed(what: Int, response: Response<String>?) {
                if (response!!.responseCode() == 200) {
                    requesCallBack(what, response)
                }
            }

            override fun onFailed(what: Int, response: Response<String>?) {
            }

            override fun onStart(what: Int) {

            }

        })
    }


    /**
     *  数据请求回调
     */
    private fun requesCallBack(what: Int, response: Response<String>?) {
        when (what) {
            HttpWhat.HTTP_HOME_NEAR_SHOP.value -> {//附近商店
                nearModelList.clear()
                val jsonObject = JSONObject(response!!.get())
                val json = jsonObject.getString("list")
                nearShopBeanList = GsonUtils.toList(json, HomeShopAndProductBean.NearbyBean::class.java)!!
                if (adverList.isNotEmpty()) {//有前面广告model
                    for (i in 0 until 2) {
                        val nearmodel = NearShopMoreModel()
                        if (i == 0) {
                            nearmodel.type = 1
                            if (adverList.isNotEmpty()) {
                                nearmodel.adver = adverList
                            }
                        } else {
                            nearmodel.type = 2
                            if (page > 1) {//加载更多
                                more = if (nearShopBeanList.isNotEmpty()) {
                                    nearShopBeanMoreList.addAll(nearShopBeanList)
                                    nearmodel.nearby = nearShopBeanMoreList
                                    true
                                } else {
                                    false
                                }
                            } else {//刷新
                                if (nearShopBeanList.isEmpty()) {//没数据
                                    nearmodel.nearby.clear()
                                    more = false
                                } else {//有数据
                                    nearShopBeanMoreList.clear()
                                    nearShopBeanMoreList.addAll(nearShopBeanList)
                                    nearmodel.nearby = nearShopBeanMoreList
                                    more = true
                                }
                            }
                            adapter!!.setEnableLoadMore(more)
                        }
                        nearModelList.add(nearmodel)
                    }
                } else {//没得广告model
                    val nearmodel = NearShopMoreModel()
                    nearmodel.type = 2
                    if (page > 1) {//加载更多
                        more = if (nearShopBeanList.isNotEmpty()) {
                            nearShopBeanMoreList.addAll(nearShopBeanList)
                            nearmodel.nearby = nearShopBeanMoreList
                            true
                        } else {
                            false
                        }
                    } else {//刷新
                        if (nearShopBeanList.isEmpty()) {//没数据
                            nearmodel.nearby.clear()
                            more = false
                        } else {//有数据
                            nearShopBeanMoreList.clear()
                            nearShopBeanMoreList.addAll(nearShopBeanList)
                            nearmodel.nearby = nearShopBeanMoreList
                            more = true
                        }
                        adapter!!.setEnableLoadMore(more)
                    }
                    nearModelList.add(nearmodel)
                }
                adapter!!.setNewData(nearModelList)

            }
            HttpWhat.HTTP_SHOP_DETAIL_FLOATING.value -> {//广告
                val jsonObject = JSONObject(response!!.get())
                val json = jsonObject.getString("list")
                adverList = GsonUtils.toList(json, HomeShopAndProductBean.AdvertBean::class.java)!!
            }

        }

    }

    fun openHomeCenterAd(extraInfo: Int) {
        if (!TextUtils.isEmpty(adverList[extraInfo].linkUrl)) {
            BrowserUrlManager(adverList[extraInfo].linkUrl).parseUrl(this, adverList[extraInfo].linkUrl)
        } else {
            if (Config.DEBUG) {
                Toast.makeText(this, R.string.emptyLink, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(p0: View?) {
        val viewType = Utils.getViewTypeOfTag(p0)
        val extraInfo = Utils.getExtraInfoOfTag(p0)
        if (ViewType.VIEW_TYPE_HOME_CENTER_AD == viewType) {
            openHomeCenterAd(extraInfo)
        }
        when (p0) {
            img_near_shop_more_back -> {//返回
                finish()
            }
            edt_search_near_shop_more -> {//搜索
                val mIntent = Intent(this, ProjectH5Activity::class.java)
                mIntent.putExtra(Key.KEY_URL.value, Api.H5_CITYLIFE_URL + "/search")
                startActivity(mIntent)
            }
            tv_address -> {
                if (TextUtils.isEmpty(App.getInstance().lat)) {
                    return
                }
                val intent = Intent()
                intent.setClass(this, AmapActivity::class.java)
                intent.putExtra(KEY_LAT, App.getInstance().lat)
                intent.putExtra(KEY_LONT, App.getInstance().lng)
                intent.putExtra(KEY_ADDRESS, App.getInstance().addressDetail)
                startActivity(intent)
            }
        }


    }
}