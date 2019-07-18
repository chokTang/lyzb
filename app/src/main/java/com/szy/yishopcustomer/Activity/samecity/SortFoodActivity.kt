package com.szy.yishopcustomer.Activity.samecity

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.ListPopupWindow
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.chad.library.adapter.base.BaseQuickAdapter
import com.szy.yishopcustomer.Activity.ProjectH5Activity
import com.szy.yishopcustomer.Activity.samecity.SortGroupActivity.GROUP_ID
import com.szy.yishopcustomer.Adapter.CityHomeAdapter.KEY_SHOP_ID
import com.szy.yishopcustomer.Adapter.FoodsBannerAdapter
import com.szy.yishopcustomer.Adapter.FoodsResultAdapter
import com.szy.yishopcustomer.Adapter.NearTitleListAdapter
import com.szy.yishopcustomer.Constant.Api
import com.szy.yishopcustomer.Constant.HttpWhat
import com.szy.yishopcustomer.Constant.Key
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.Near.NearTitleAll
import com.szy.yishopcustomer.ResponseModel.SameCity.Near.NearTitleDistance
import com.szy.yishopcustomer.Util.App
import com.szy.yishopcustomer.Util.LogUtils
import com.szy.yishopcustomer.Util.RequestAddHead
import com.szy.yishopcustomer.Util.Utils
import com.szy.yishopcustomer.Util.json.GSONUtil
import com.szy.yishopcustomer.View.BannerScroller
import com.szy.yishopcustomer.View.SelectDialog
import com.szy.yishopcustomer.ViewModel.FoodsAdBannerBean
import com.szy.yishopcustomer.ViewModel.FoodsSelectResultBean
import com.szy.yishopcustomer.ViewModel.samecity.FoodsSelectTitleBean
import com.yolanda.nohttp.NoHttp
import com.yolanda.nohttp.RequestMethod
import com.yolanda.nohttp.rest.OnResponseListener
import com.yolanda.nohttp.rest.Response
import kotlinx.android.synthetic.main.activity_sort_food.*
import kotlinx.android.synthetic.main.foods_ad_banner.*
import org.json.JSONObject

/**
 * 美食列表
 */
class SortFoodActivity : AppCompatActivity(), View.OnClickListener {


    companion object {
        private const val MENU_ALL = 1  //第一个选择项
        private const val MENU_NEAR = 2  //第二个选择项
        private const val MENU_SALE = 3  //第三个选择项
    }

    var isSaleClick = false

    private var parId: String = ""
    private var disTance = 0

    private var page = 1  //起始页
    private var more = true  //是否加载更多

    var mAdapter: FoodsResultAdapter? = null
    var selectDialog: SelectDialog? = null

    var acerstatus = "0"
    var takeoutstatus = "0"
    var perisontypeId = ""

    private var mListPopupWindow: ListPopupWindow? = null
    private var mTitleListAdapter: NearTitleListAdapter? = null
    private var titleAll: NearTitleAll? = null
    private var titlelist: MutableList<NearTitleAll> = arrayListOf()  //左边全部列表
    private var selectlist: MutableList<FoodsSelectTitleBean> = arrayListOf()  //筛选列表
    private var mTitleAllList: MutableList<NearTitleAll> = arrayListOf()
    private val mDistanceList: MutableList<NearTitleDistance> = arrayListOf()

    private var foodsList: MutableList<FoodsSelectResultBean> = arrayListOf()
    private var foodsAdList: MutableList<FoodsAdBannerBean> = arrayListOf()
    private var moreList: MutableList<FoodsSelectResultBean> = arrayListOf()


    /**
     * 刷新加载
     */
    fun refreshView() {

//        pull_sort_foods.setOnRefreshListener(object :SwipeRefreshLayout.OnRefreshListener{
//            override fun onRefresh() {
//                page = 1
//            getSelectResult()
//            }
//
//        })

        mAdapter!!.setOnLoadMoreListener {
            /** 加载更多  */
            if (more) {
                page++
                getSelectResult()
            } else {
                Toast.makeText(this@SortFoodActivity, "暂无更多数据", Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sort_food)
        initView()
        refreshView()
        setListener()
    }


    /**
     * 初始化
     */
    private fun initView() {


        parId = intent.getStringExtra(GROUP_ID)

        titleAll = NearTitleAll()
        titleAll!!.catgName = "全部"
        titleAll!!.catgId = parId.toInt()
        mTitleAllList.add(titleAll!!)
        selectDialog = SelectDialog(this, R.layout.dialog_select)

        mListPopupWindow = ListPopupWindow(this)
        mListPopupWindow!!.width = ViewGroup.LayoutParams.MATCH_PARENT

        mTitleListAdapter = NearTitleListAdapter(this)
        mListPopupWindow!!.setAdapter(mTitleListAdapter)
        mListPopupWindow!!.isModal = true
        mListPopupWindow!!.anchorView = linea_sort_title


        //title筛选选择器的回调
        mTitleListAdapter!!.setItemClick(object : NearTitleListAdapter.onPopItemClick {
            override fun onAllClick(titleName: String, titleId: Int) {

                tv_sort_left.text = titleName
                parId = titleId.toString()
                mListPopupWindow!!.dismiss()
                page = 1
                getSelectResult()
            }

            override fun onNearClick(titleName: String, distance: Int) {

                tv_sort_center.text = titleName
                disTance = distance
                mListPopupWindow!!.dismiss()
                page = 1
                getSelectResult()
            }
        })

        val manager = LinearLayoutManager(this)
        manager.isSmoothScrollbarEnabled = true
        manager.isAutoMeasureEnabled = true
        rv_sort_foods.layoutManager = manager
        mAdapter = FoodsResultAdapter(R.layout.item_foods_result)
        rv_sort_foods.setHasFixedSize(true)
        rv_sort_foods.adapter = mAdapter




        getTitleAllData()//获取筛选title  左边
        Utils.addDisTitleData(mDistanceList)
        getTitleSelectData()//获取筛选title  右边
        getSelectResult()//获取筛选结果
        getAdvertisement()//获取banner广告
    }


    private fun setListener() {
        img_sort_food_back.setOnClickListener(this)
        linea_sort_seach.setOnClickListener(this)
        rela_sort_left.setOnClickListener(this)
        rela_sort_center.setOnClickListener(this)
        rela_sort_right.setOnClickListener(this)
        ll_hexiaojuan.setOnClickListener(this)
        ll_diancan.setOnClickListener(this)


        selectDialog!!.setClickListener(object : SelectDialog.OnclickListener {
            override fun onclick(acerStatus: String, takeOutStatus: String, personTypeId: String) {
                acerstatus = acerStatus
                takeoutstatus = takeOutStatus
                if (TextUtils.isEmpty(personTypeId)) {
                    perisontypeId = ""
                } else {
                    perisontypeId = personTypeId
                }
                page = 1
                getSelectResult()
            }
        })

        mAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            var intent: Intent? = null
            val bean = adapter.data[position] as FoodsSelectResultBean
            if (bean.saleSkip.toDouble() <= 0) {//不是外卖
                intent = Intent(this, ShopDetailActivity::class.java)
                intent.putExtra(KEY_SHOP_ID, bean.shopId)
            } else {//外卖
                intent = Intent(this, ProjectH5Activity::class.java)
                intent.putExtra(Key.KEY_URL.value, Api.H5_CITYLIFE_URL + "/homepage?storeId=" + bean.shopId)
            }
            startActivity(intent)
        }
    }

    /**
     * 获取筛选数据title最左边的全部那个title里面的数据
     */
    private fun getTitleAllData() {
        val requestQueue = NoHttp.newRequestQueue()
        val request = NoHttp.createStringRequest(Api.API_CITY_HOME_NEAR_TITLE, RequestMethod.GET)
        RequestAddHead.addNoHttpHead(request, this, Api.API_CITY_HOME_NEAR_TITLE, "GET")
        request.add("parentId", parId)
        requestQueue.add(HttpWhat.HTTP_NEAR_TITLE.value, request, object : OnResponseListener<String> {
            override fun onStart(what: Int) {

            }

            override fun onSucceed(what: Int, response: Response<String>) {
                if (response.responseCode() == Utils.CITY_NET_SUCES) {
                    requestCallback(what, response)
                }
            }

            override fun onFailed(what: Int, response: Response<String>) {
                Toast.makeText(this@SortFoodActivity, R.string.data_error, Toast.LENGTH_SHORT).show()
            }

            override fun onFinish(what: Int) {

            }
        })
    }

    /**
     * 获取筛选数据title最右边的筛选那个title里面的数据
     *  几人套餐
     */
    private fun getTitleSelectData() {
        val requestQueue = NoHttp.newRequestQueue()
        val request = NoHttp.createStringRequest(Api.API_CITY_HOME_FOODS_SELECT, RequestMethod.GET)
        RequestAddHead.addNoHttpHead(request, this, Api.API_CITY_HOME_FOODS_SELECT, "GET")
        requestQueue.add(HttpWhat.HTTP_SELECT_TITLE.value, request, object : OnResponseListener<String> {
            override fun onStart(what: Int) {

            }

            override fun onSucceed(what: Int, response: Response<String>) {
                if (response.responseCode() == Utils.CITY_NET_SUCES) {
                    requestCallback(what, response)
                }
            }

            override fun onFailed(what: Int, response: Response<String>) {
                Toast.makeText(this@SortFoodActivity, R.string.data_error, Toast.LENGTH_SHORT).show()
            }

            override fun onFinish(what: Int) {

            }
        })
    }

    /**
     * 获取美食筛选数据结果
     *  筛选结果店铺
     */
    private fun getSelectResult() {
        val requestQueue = NoHttp.newRequestQueue()
        val request = NoHttp.createStringRequest(Api.API_CITY_HOME_FOODS_SELECT_RESULT, RequestMethod.GET)
        RequestAddHead.addNoHttpHead(request, this, Api.API_CITY_HOME_FOODS_SELECT_RESULT, "GET")
        request.add("pageNum", page)
        request.add("pageSize", "20")
        request.add("classId", parId)
        if (App.getInstance().isLocation) {
            request.add("latitude", App.getInstance().lat)
            request.add("longitude", App.getInstance().lng)
        } else if (App.getInstance().city_name != null) {
            request.add("regionCode", App.getInstance().city_code)
        }
        if (acerstatus != "0") {
            request.add("acerStatus", acerstatus)//是否可以抵扣元宝
        }
        if (takeoutstatus != "0") {
            request.add("takeOutStatus", takeoutstatus)//是否支持外卖
        }
        if (disTance != 0) {
            request.add("distance", disTance)
        }
        if (!TextUtils.isEmpty(perisontypeId)) {
            request.add("personTypeId", perisontypeId)//几人
        }

        LogUtils.e("请求参数+acerstatus" + acerstatus + "disTance" + disTance + "takeoutstatus" + takeoutstatus + "perisontypeId" + perisontypeId)
        requestQueue.add(HttpWhat.HTTP_FOODS_SELECT_RESULT.value, request, object : OnResponseListener<String> {
            override fun onStart(what: Int) {

            }

            override fun onSucceed(what: Int, response: Response<String>) {
                if (response.responseCode() == Utils.CITY_NET_SUCES) {
                    requestCallback(what, response)
                }
            }

            override fun onFailed(what: Int, response: Response<String>) {
                Toast.makeText(this@SortFoodActivity, R.string.data_error, Toast.LENGTH_SHORT).show()
            }

            override fun onFinish(what: Int) {

            }
        })
    }


    /**
     * 获取轮播广告
     */
    private fun getAdvertisement() {
        val requestQueue = NoHttp.newRequestQueue()
        val request = NoHttp.createStringRequest(Api.API_SHOP_DETAIL_FLOATING_URL, RequestMethod.GET)
        RequestAddHead.addNoHttpHead(request, this, Api.API_SHOP_DETAIL_FLOATING_URL, "GET")

        request.add("position", "A3")
        requestQueue.add(HttpWhat.HTTP_SHOP_DETAIL_FLOATING.value, request, object : OnResponseListener<String> {
            override fun onFinish(what: Int) {
            }

            override fun onSucceed(what: Int, response: Response<String>?) {
                if (response!!.responseCode() == 200) {
                    requestCallback(what, response)
                }
            }

            override fun onFailed(what: Int, response: Response<String>?) {
            }

            override fun onStart(what: Int) {

            }

        })
    }


    /**
     * 请求数据回调处理
     */
    private fun requestCallback(what: Int, response: Response<String>?) {
        when (what) {
            HttpWhat.HTTP_NEAR_TITLE.value -> {//筛选title数据(左边)
                titlelist = JSON.parseArray(response!!.get(), NearTitleAll::class.java)
            }
            HttpWhat.HTTP_SELECT_TITLE.value -> {//筛选title数据(右边  几人套餐系列)
                selectlist = JSON.parseArray(response!!.get(), FoodsSelectTitleBean::class.java)
                selectDialog!!.setData(selectlist)

            }

            HttpWhat.HTTP_FOODS_SELECT_RESULT.value -> {//拿到筛选结果
                LogUtils.e("请求结果数据---->>>" + response)
                val jsonObject = JSONObject(response!!.get())
                foodsList = GSONUtil.getEntityList(jsonObject.getString("list"), FoodsSelectResultBean::class.java)
                if (page > 1) {//加载数据
                    if (foodsList.size > 0) {//加载数据有更多
                        moreList.addAll(foodsList)
                        mAdapter!!.setNewData(moreList)
                    } else {//加载数据 没有数据
                        more = false
                    }
                } else {
                    if (foodsList.size > 0) {//刷新 有数据
                        mAdapter!!.setNewData(foodsList)
                    } else {//刷新  没有数据
                        foodsList.clear()
                        mAdapter!!.setNewData(foodsList)
                        more = false
                    }
//                    if (page == 1) {
//                        pull_sort_foods.isRefreshing = true
//                    }
                }
                mAdapter!!.notifyDataSetChanged()

            }

            HttpWhat.HTTP_SHOP_DETAIL_FLOATING.value -> {//获取轮播广告数据
                val jsonObject = JSONObject(response!!.get())
                foodsAdList = GSONUtil.getEntityList(jsonObject.getString("list"), FoodsAdBannerBean::class.java)
                setUpAdBanner(foodsAdList)
            }
        }
    }

    /**
     * 设置广告数据
     */
    private fun setUpAdBanner(foodsAdList: MutableList<FoodsAdBannerBean>) {

        //设置viewpager 滑动时间
        val scroller = BannerScroller(this)
        scroller.time = 1500
        scroller.initViewPagerScroll(fragment_index_banner_viewPager)

        val items = foodsAdList
        if (items.size == 0) {
            return
        }
        val windowWidth = Utils.getWindowWidth(this)
        val firstItem = items[0]
        var height = 0
        val imageWidth = firstItem.width.toDouble()
        val imageHeight = firstItem.height.toDouble()
        val realHeight = windowWidth * (imageHeight / imageWidth)
        height = realHeight.toInt()
        if (height <= 0) {
            //                viewHolder.viewPager.setAdapter(null);
            //                return;
            //如果没有宽高，默认使用第一张图片的宽高比
            height = (windowWidth * 0.31f).toInt()
        }

        val layoutParams = ll_banner.layoutParams
        layoutParams.height = height
        ll_banner.layoutParams = layoutParams
        //设置首页banner 数据
        val adapter = FoodsBannerAdapter(items)

        fragment_index_banner_viewPager.adapter = adapter

        if (foodsAdList.size != 1) {
            fragment_index_banner_pageIndicator.visibility = View.VISIBLE
            fragment_index_banner_pageIndicator.setViewPager(fragment_index_banner_viewPager)
            fragment_index_banner_pageIndicator.notifyDataSetChanged()
            fragment_index_banner_pageIndicator.isSnap = true
        } else {
            fragment_index_banner_pageIndicator.visibility = View.GONE
        }

    }

    private fun chanageBtn(menu: Int) {
        when (menu) {
            MENU_ALL -> {
                tv_sort_left.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                img_sort_left.setBackgroundResource(R.mipmap.near_tab_selected)

                tv_sort_center.setTextColor(ContextCompat.getColor(this, R.color.aliwx_black))
                img_sort_center.setBackgroundResource(R.mipmap.near_tab_normal)

                tv_sort_right.setTextColor(ContextCompat.getColor(this, R.color.aliwx_black))
                if (!isSaleClick) {
                    img_sort_right.setBackgroundResource(R.mipmap.near_tab_normal)
                } else {
                    img_sort_right.setBackgroundResource(R.mipmap.near_sale_top_normal)
                }
            }
            MENU_NEAR -> {

                tv_sort_center.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                img_sort_center.setBackgroundResource(R.mipmap.near_tab_selected)

                tv_sort_left.setTextColor(ContextCompat.getColor(this, R.color.aliwx_black))
                img_sort_left.setBackgroundResource(R.mipmap.near_tab_normal)

                tv_sort_right.setTextColor(ContextCompat.getColor(this, R.color.aliwx_black))
                if (!isSaleClick) {
                    img_sort_right.setBackgroundResource(R.mipmap.near_tab_normal)
                } else {
                    img_sort_right.setBackgroundResource(R.mipmap.near_sale_top_normal)
                }
            }
            MENU_SALE -> {
                tv_sort_left.setTextColor(ContextCompat.getColor(this, R.color.aliwx_black))
                img_sort_left.setBackgroundResource(R.mipmap.near_tab_normal)

                tv_sort_center.setTextColor(ContextCompat.getColor(this, R.color.aliwx_black))
                img_sort_center.setBackgroundResource(R.mipmap.near_tab_normal)

                tv_sort_right.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                if (!isSaleClick) {
                    img_sort_right.setBackgroundResource(R.mipmap.near_tab_selected)
                    isSaleClick = true
                } else {
                    img_sort_right.setBackgroundResource(R.mipmap.near_sale_top)
                    isSaleClick = false
                }
            }
        }
    }



    override fun onClick(p0: View?) {
        var intent: Intent? = null
        when (p0) {
            img_sort_food_back -> {//返回
                finish()
            }
            linea_sort_seach -> {//搜素
                intent = Intent(this, ProjectH5Activity::class.java)
                intent.putExtra(Key.KEY_URL.value, Api.H5_CITYLIFE_URL + "/search")
                startActivity(intent)
            }
            rela_sort_left -> {//筛选框左边
                chanageBtn(1)

                mListPopupWindow!!.height = 960
                mListPopupWindow!!.show()

                if (mTitleAllList.size > 0) {
                    mTitleAllList.addAll(titlelist)
                    mTitleListAdapter!!.setChanageData(true)
                    mTitleListAdapter!!.setAllList(mTitleAllList)
                    mTitleListAdapter!!.notifyDataSetChanged()
                }
            }
            rela_sort_center -> {//筛选框中间
                chanageBtn(2)

                mListPopupWindow!!.height = ViewGroup.LayoutParams.WRAP_CONTENT
                mListPopupWindow!!.show()

                if (mDistanceList.size > 0) {
                    mTitleListAdapter!!.setChanageData(false)
                    mTitleListAdapter!!.setNearList(mDistanceList)
                    mTitleListAdapter!!.notifyDataSetChanged()
                }

            }
            rela_sort_right -> {//筛选框右边
                chanageBtn(3)
                selectDialog!!.show()

            }

            ll_hexiaojuan -> {//核销券
                intent = Intent(this, ProjectH5Activity::class.java)
                intent.putExtra(Key.KEY_URL.value, Api.H5_CITYLIFE_URL + "/deliciousGroupIndex?parentId=" + parId)
                startActivity(intent)
            }

            ll_diancan -> {//点餐
                intent = Intent(this, ProjectH5Activity::class.java)
                intent.putExtra(Key.KEY_URL.value, Api.H5_CITYLIFE_URL + "/classificationindex?parentId=" + parId)
                startActivity(intent)
            }
        }
    }


}