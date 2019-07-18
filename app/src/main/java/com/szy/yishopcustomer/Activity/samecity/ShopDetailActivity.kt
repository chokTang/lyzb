package com.szy.yishopcustomer.Activity.samecity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ViewTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.szy.common.Other.CommonRequest
import com.szy.yishopcustomer.Activity.ProjectH5Activity
import com.szy.yishopcustomer.Activity.ShareActivity
import com.szy.yishopcustomer.Adapter.CityHomeAdapter.KEY_SHOP_ID
import com.szy.yishopcustomer.Adapter.samecity.ShopDetailEvaluationAdapter
import com.szy.yishopcustomer.Adapter.samecity.ShopDetailGroupBuyAdapter
import com.szy.yishopcustomer.Adapter.samecity.ShopDetailNearShopAdapter
import com.szy.yishopcustomer.Constant.Api
import com.szy.yishopcustomer.Constant.HttpWhat
import com.szy.yishopcustomer.Constant.Key
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Follow.ResponseFollowModel
import com.szy.yishopcustomer.Util.*
import com.szy.yishopcustomer.Util.json.GSONUtil
import com.szy.yishopcustomer.Util.json.GsonUtils
import com.szy.yishopcustomer.View.MenuPop
import com.szy.yishopcustomer.ViewModel.samecity.Comment
import com.szy.yishopcustomer.ViewModel.samecity.ShopBean
import com.szy.yishopcustomer.ViewModel.samecity.ShopDetailFloatingBean
import com.yolanda.nohttp.Logger
import com.yolanda.nohttp.NoHttp
import com.yolanda.nohttp.RequestMethod
import com.yolanda.nohttp.rest.OnResponseListener
import com.yolanda.nohttp.rest.Response
import kotlinx.android.synthetic.main.activity_shop_detail.*
import org.json.JSONObject
import java.util.*

class ShopDetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val peimission: String = "android.permission.CALL_PHONE"
        const val PERMISSION_CODE: Int = 456
        const val KEY_LONT = "longitude"
        const val KEY_LAT = "latitude"
        const val KEY_ADDRESS = "shopaddress"
        const val KEY_PRODUCT_ID = "productid"
        const val KEY_SHOP_BEAN = "shopbean"
        const val KEY_BUNDLE = "bundle"
    }

    var floatList: MutableList<ShopDetailFloatingBean> = arrayListOf()
    var popupMenu: MenuPop? = null
    var shareData: ArrayList<String> = ArrayList()

    private var shopBean: ShopBean? = null
    private var buyAdapter: ShopDetailGroupBuyAdapter? = null
    private var nearShopAdapter: ShopDetailNearShopAdapter? = null
    private var evaluationAdapter: ShopDetailEvaluationAdapter? = null
    var shopid: String = ""
    var latitude: String = ""
    var longitude: String = ""
    var phone: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_detail)
        initView()
        setOnclickListener()
    }

    /**
     * 初始化
     */
    fun initView() {
        val intent = intent
        shopid = intent.getStringExtra(KEY_SHOP_ID)
        if (TextUtils.isEmpty(App.getInstance().lat)) {
            getShopDetail(shopid, "", "")
        } else {
            getShopDetail(shopid, App.getInstance().lat, App.getInstance().lng)
        }
        getFloating()

        rv_shop_detail_group_buy.layoutManager = LinearLayoutManager(this)
        rv_shop_detail_group_buy.isNestedScrollingEnabled = false
        rv_shop_detail_near_shop.layoutManager = LinearLayoutManager(this)
        rv_shop_detail_near_shop.isNestedScrollingEnabled = false
        rv_shop_detail_user_evaluation.layoutManager = LinearLayoutManager(this)
        rv_shop_detail_user_evaluation.isNestedScrollingEnabled = false

        buyAdapter = ShopDetailGroupBuyAdapter(this, R.layout.item_group_buy_package)
        nearShopAdapter = ShopDetailNearShopAdapter(this, R.layout.item_group_buy_package)
        evaluationAdapter = ShopDetailEvaluationAdapter(this, R.layout.item_shop_detail_user_evalution)
        rv_shop_detail_group_buy.adapter = buyAdapter
        rv_shop_detail_near_shop.adapter = nearShopAdapter
        rv_shop_detail_user_evaluation.adapter = evaluationAdapter

        popupMenu = MenuPop(this)

    }


    /**
     * 设置监听
     */
    private fun setOnclickListener() {
        img_shop_detail_back.setOnClickListener(this)
        img_shop_detail_share.setOnClickListener(this)
        rl_shop_detail_phone.setOnClickListener(this)
        ll_shop_detail_address.setOnClickListener(this)
        tv_shop_detail_pay_bill.setOnClickListener(this)
        ll_shop_detail_group_buy_more.setOnClickListener(this)
        ll_shop_detail_evaluation_more.setOnClickListener(this)
        rl_shop_detail_takeaway.setOnClickListener(this)
        img_shop_detail_return_top.setOnClickListener(this)
        img_shop_detail_floating.setOnClickListener(this)
        tv_shop_collection.setOnClickListener(this)
        nearShopAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            if (TextUtils.isEmpty(App.getInstance().lat)) {
                getShopDetail((adapter as ShopDetailNearShopAdapter).data[position].shopId.toString(), "", "")
            } else {
                getShopDetail((adapter as ShopDetailNearShopAdapter).data[position].shopId.toString(), App.getInstance().lat, App.getInstance().lng)
            }
        }

        buyAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val bundle = Bundle()
            bundle.putString(KEY_SHOP_ID, shopBean!!.shopId.toString())
            startActivity(Intent(this@ShopDetailActivity, GroupBuyActivity::class.java)
                    .putExtra(KEY_PRODUCT_ID, (adapter as ShopDetailGroupBuyAdapter).data[position].prodId)
                    .putExtra(KEY_BUNDLE, bundle))
        }

        popupMenu!!.invoke(object : MenuPop.OnclickListener {
            override fun click(view: View) {
                when (view.id) {
                    R.id.tv_menu_home -> {//返回同城主页
                        finish()
                    }
                    R.id.tv_menu_share -> {//分享
                        shareData.clear()
                        shareData.add(Api.H5_CITYLIFE_URL + "/shopIndex?storeId=" + shopBean!!.shopId)
                        shareData.add(shopBean!!.shopName)
                        shareData.add(shopBean!!.shopDescription)
                        shareData.add(Utils.setImgNetUrl(Api.API_CITY_HOME_MER_IMG_URL, shopBean!!.shopLogo))
                        startActivity(Intent(this@ShopDetailActivity, ShareActivity::class.java)
                                .putExtra(ShareActivity.SHARE_SCOPE, "0")
                                .putStringArrayListExtra(ShareActivity.SHARE_DATA, shareData).putExtra(ShareActivity.SHARE_TYPE, ShareActivity.TYPE_SOURCE))
                    }
                }
            }
        })


        scrollView_shop_detail.setScrollViewListener { scrollView, x, y, oldx, oldy ->
            if (y >= 1000) {
//                img_shop_detail_return_top.visibility = View.VISIBLE
                img_shop_detail_return_top.visibility = View.GONE
            } else {
                img_shop_detail_return_top.visibility = View.GONE
            }
        }
    }

    /**
     * 获取店铺详情信息
     * id id 店铺D number @mock=1
     * latitude 纬度
     * longitude 精度
     */
    fun getShopDetail(id: String, latitude: String, longitude: String) {
        val requestQueue = NoHttp.newRequestQueue()
        val request = NoHttp.createStringRequest(Api.API_SHOP_DETAIL_URL, RequestMethod.GET)
        RequestAddHead.addNoHttpHead(request, this, Api.API_SHOP_DETAIL_URL, "GET")

        request.add("id", id)
        request.add("latitude", latitude)
        request.add("longitude", longitude)
        requestQueue.add(HttpWhat.HTTP_SHOP_DETAIL.value, request, object : OnResponseListener<String> {
            override fun onStart(what: Int) {
            }

            override fun onFinish(what: Int) {
            }

            override fun onSucceed(what: Int, response: Response<String>?) {
                if (response!!.responseCode() == 200)
                    requestCallback(what, response)
            }

            override fun onFailed(what: Int, response: Response<String>?) {
                Toast.makeText(this@ShopDetailActivity, R.string.data_error, Toast.LENGTH_SHORT).show()
            }

        })
    }

    /**
     * 获取浮窗数据
     */
    private fun getFloating() {
        val requestQueue = NoHttp.newRequestQueue()
        val request = NoHttp.createStringRequest(Api.API_SHOP_DETAIL_FLOATING_URL, RequestMethod.GET)
        RequestAddHead.addNoHttpHead(request, this, Api.API_SHOP_DETAIL_FLOATING_URL, "GET")

        request.add("areaId", App.getInstance().city_code)
        request.add("position", "A8")
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
     * 收藏店铺
     */
    private fun addCollectionShop() {
        val requestQueue = NoHttp.newRequestQueue()
        val request = CommonRequest(Api.API_COLLECT_TOGGLE, HttpWhat.HTTP_COLLECT_SHOP.value, RequestMethod.POST)

        request.add("shop_id", shopid)

        requestQueue.add(HttpWhat.HTTP_COLLECT_SHOP.value, request, object : OnResponseListener<String> {
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
     * 查看全部评论
     */
    fun getAllEvaluation(storeId: String, pageNum: String, pageSize: String) {
        val requestQueue = NoHttp.newRequestQueue()
        val request = NoHttp.createStringRequest(Api.API_SHOP_DETAIL_All_EVALUATION_URL, RequestMethod.GET)
        RequestAddHead.addNoHttpHead(request, this, Api.API_SHOP_DETAIL_All_EVALUATION_URL, "GET")
        request.add("pageNum", pageNum)
        request.add("storeId", storeId)
        request.add("pageSize", pageSize)

        requestQueue.add(HttpWhat.HTTP_SHOP_DETAIL_ALL_EVALUATION.value, request, object : OnResponseListener<String> {
            override fun onFinish(what: Int) {
            }

            override fun onSucceed(what: Int, response: Response<String>?) {
                if (response!!.responseCode() == 200)
                    requestCallback(what, response)
            }

            override fun onFailed(what: Int, response: Response<String>?) {
            }

            override fun onStart(what: Int) {
            }
        })
    }


    /**
     * 数据请求成功回调
     * what  请求编码
     * response  请求回调数据
     */
    @SuppressLint("SetTextI18n")
    fun requestCallback(what: Int, response: Response<String>?) {
        Logger.e("请求数据回调$response")
        when (what) {
            HttpWhat.HTTP_SHOP_DETAIL.value -> {//店铺详情
                if (response!!.responseCode() == Utils.CITY_NET_SUCES) {
                    shopBean = Gson().fromJson(response.get(), ShopBean::class.java)
//                    if (!TextUtils.isEmpty(shopBean!!.backgroundImg)) {
                    Glide.with(this)
                            .load(Utils.setImgNetUrl(Api.API_CITY_HOME_MER_IMG_URL, shopBean?.backgroundImg))
                            .apply(GlideUtil.EmptyOptions())
                            .into(object : ViewTarget<View, Drawable>(img_shop_detail) {
                                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                                    this.view.background = resource!!.current
                                }
                            })
//                    } else {
//                        Glide.with(this)
//                                .load(R.mipmap.image_load_error)
//                                .apply(GlideUtil.EmptyOptions())
//                                .into(object : ViewTarget<View, Drawable>(img_shop_detail) {
//                                    override fun onResourceReady(resource: Drawable?, transition: Transition<in Drawable>?) {
//                                        this.view.background = resource!!.current
//                                    }
//                                })
//                    }

                    if (!TextUtils.isEmpty(shopBean?.shopLogo)) {
                        Glide.with(this)
                                .load(Utils.setImgNetUrl(Api.API_CITY_HOME_MER_IMG_URL, shopBean!!.shopLogo))
                                .apply(GlideUtil.EmptyOptions())
                                .into(img_shop_detail_header)
                    }


                    //收藏
                    var drawableLeft: Drawable? = null
                    if (shopBean?.collect == "1") {
                        tv_shop_collection.text = "已收藏"
                        drawableLeft = resources.getDrawable(R.mipmap.ic_shop_collection_selected)
                    } else {
                        tv_shop_collection.text = "收藏本店"
                        drawableLeft = resources.getDrawable(R.mipmap.ic_shop_collection_normal)
                    }
                    tv_shop_collection.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)

                    tv_shop_detail_name.text = shopBean!!.shopName
                    tv_shop_detail_summary.text = shopBean!!.shopDescription
                    tv_shop_detail_address.text = shopBean!!.addressDetail
                    phone = shopBean!!.serviceTel
                    val distance = Math.ceil(shopBean!!.distance)
                    when {
                        distance > 1000 -> //距离 单位换算
                            tv_shop_detail_distance.text = Utils.toDistance(distance) + "km"
                        distance == 0.0 ->
                            tv_shop_detail_distance.visibility = View.GONE
                        else ->
                            tv_shop_detail_distance.text = Utils.toM(distance) + "m"
                    }
                    if (shopBean!!.takeOut) {
                        var drawable: Drawable? = null
                        rl_shop_detail_takeaway.visibility = View.VISIBLE
                        drawable = if (shopBean!!.saleSkip.toInt() > 0) {//
                            resources.getDrawable(R.mipmap.icon_diancan)
                        } else {//非美食
                            resources.getDrawable(R.mipmap.icon_dianpu)
                        }
                        if (shopBean!!.saleSkip.toInt() > 0){
                            rl_shop_detail_takeaway.text = "我要点餐"
                        }else{
                            rl_shop_detail_takeaway.text = "进入店铺"
                        }
                        rl_shop_detail_takeaway.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
                    } else {
                        rl_shop_detail_takeaway.visibility = View.GONE
                    }
                    if (shopBean!!.evalScore == 0.0) {
                        ratingBar_shop_detail.visibility = View.GONE
                        tv_shop_detail_score.visibility = View.GONE
                    } else {
                        ratingBar_shop_detail.visibility = View.VISIBLE
                        tv_shop_detail_score.visibility = View.VISIBLE
                        ratingBar_shop_detail.star = Math.ceil(shopBean!!.evalScore).toInt()
                        tv_shop_detail_score.text = shopBean!!.evalScore.toString() + "分"
                    }

                    if (shopBean!!.takeOut) {//是否支持外卖
                        var drawable: Drawable? = null
                        rl_shop_detail_takeaway.visibility = View.VISIBLE
                        drawable = if (shopBean!!.saleSkip.toInt() > 0) {//美食
                            resources.getDrawable(R.mipmap.icon_diancan)
                        } else {//非美食
                            resources.getDrawable(R.mipmap.icon_dianpu)
                        }
                        if (shopBean!!.saleSkip.toInt() > 0){
                            rl_shop_detail_takeaway.text = "我要点餐"
                        }else{
                            rl_shop_detail_takeaway.text = "进入店铺"
                        }
                        rl_shop_detail_takeaway.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
                    } else {
                        rl_shop_detail_takeaway.visibility = View.GONE
                    }

                    //团购餐券
                    if (shopBean!!.productCount > 0) {
                        tv_shop_detail_group_buy.visibility = View.VISIBLE
                        divider_group.visibility = View.VISIBLE
                        tv_shop_detail_group_buy.text = "团购套餐(" + shopBean!!.productList.size + ")"
                    } else {
                        tv_shop_detail_group_buy.visibility = View.GONE
                        divider_group.visibility = View.GONE
                    }

                    if (shopBean!!.productCount > 2) {//显示更多
                        buyAdapter!!.setNewData(shopBean!!.productList.subList(0, 2))
                        ll_shop_detail_group_buy_more.visibility = View.VISIBLE
                    } else {
                        buyAdapter!!.setNewData(shopBean!!.productList)
                        ll_shop_detail_group_buy_more.visibility = View.GONE
                    }

                    //用户评价
                    if (shopBean!!.commentCount > 0) {
                        tv_shop_detail_user_evaluation.visibility = View.VISIBLE
                        divider_evaluation.visibility = View.VISIBLE
                        tv_shop_detail_user_evaluation.text = "用户评价(" + shopBean!!.commentCount + ")"
                    } else {
                        tv_shop_detail_user_evaluation.visibility = View.GONE
                        divider_evaluation.visibility = View.GONE
                    }
                    if (shopBean!!.commentCount > 2) {//显示更多
                        evaluationAdapter!!.setNewData(shopBean!!.commentList.subList(0, 2))
                        ll_shop_detail_evaluation_more.visibility = View.VISIBLE
                    } else {
                        evaluationAdapter!!.setNewData(shopBean!!.commentList)
                        ll_shop_detail_evaluation_more.visibility = View.GONE
                    }

                    //附近商家
                    nearShopAdapter!!.setNewData(shopBean!!.nearbyShopList)
                } else {
                    Toast.makeText(this@ShopDetailActivity, R.string.data_error, Toast.LENGTH_SHORT).show()
                }

            }
            HttpWhat.HTTP_SHOP_DETAIL_ALL_EVALUATION.value -> {//获取全部评论
                val jsonObject = JSONObject(response!!.get())
                val commentList: MutableList<Comment>
                val type = object : TypeToken<List<Comment>>() {
                }.type
                commentList = Gson().fromJson(jsonObject.getString("list"), type)
                evaluationAdapter!!.setNewData(commentList)
                ll_shop_detail_evaluation_more.visibility = View.GONE
            }


            HttpWhat.HTTP_SHOP_DETAIL_FLOATING.value -> {//获取浮窗数据
                val jsonObject = JSONObject(response!!.get())
                floatList = GSONUtil.getEntityList(jsonObject.getString("list"), ShopDetailFloatingBean::class.java)
                if (floatList.size != 0) {
                    if (!TextUtils.isEmpty(floatList[0].imageUrl)) {
                        img_shop_detail_floating.visibility = View.VISIBLE
                        Glide.with(this)
                                .load(floatList[0].imageUrl)
                                .apply(GlideUtil.EmptyOptions())
                                .into(img_shop_detail_floating)
                    } else {
                        img_shop_detail_floating.visibility = View.GONE
                    }
                }

            }

            HttpWhat.HTTP_COLLECT_SHOP.value -> {//店铺收藏
                val bean = GsonUtils.toObj(response!!.get(), ResponseFollowModel::class.java)
                var drawableLeft: Drawable? = null

                if (bean!!.code == 99) {
                    Utils.makeToast(this, bean.message)
                } else {
                    if (bean!!.data == 1) {
                        tv_shop_collection.text = "已收藏"
                        drawableLeft = resources.getDrawable(R.mipmap.ic_shop_collection_selected)
                    } else {
                        tv_shop_collection.text = "收藏本店"
                        drawableLeft = resources.getDrawable(R.mipmap.ic_shop_collection_normal)
                    }
                    tv_shop_collection.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)
                }


            }
        }
    }


    /**
     * 拨打电话（直接拨打电话）
     * @param string 电话号码
     */
    @SuppressLint("MissingPermission")
    fun callPhone(string: String) {
        val intent = Intent(Intent.ACTION_CALL)
        val date = Uri.parse("tel:$string")
        intent.data = date
        startActivity(intent)
    }


    //说明：
    //Constants.CODE_CAMERA
    //这是在外部封装的一个常量类，里面有许多静态的URL以及权限的CODE，可以自定义
    //但是在调用的时候，记得这个CODE要和你自己定义的CODE一一对应
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                callPhone(phone)
            }
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            img_shop_detail_back -> {//返回
                finish()
            }
            img_shop_detail_share -> {//右上角的点击事件
                //PopupWindow在targetView下方弹出
                popupMenu!!.showAsDropDown(img_shop_detail_share, 0, 0)
            }
            rl_shop_detail_phone -> {//拨打电话
                if (PermissionUtils.hasPermission(this, peimission)) {
                    callPhone(phone)
                } else {
                    PermissionUtils.requestPermission(this, PERMISSION_CODE, peimission)
                }
            }
            ll_shop_detail_address -> {//地址
                val intent = Intent()
                intent.setClass(this, AmapActivity::class.java)
                intent.putExtra(KEY_LAT, shopBean!!.shopLat)
                intent.putExtra(KEY_LONT, shopBean!!.shopLng)
                intent.putExtra(KEY_ADDRESS, shopBean!!.addressDetail)
                startActivity(intent)

            }
            tv_shop_detail_pay_bill -> {//买单
                val bundle = Bundle()
                bundle.putSerializable(KEY_SHOP_BEAN, shopBean)
                startActivity(Intent(this, FacePayOrderActivity::class.java)
                        .putExtra(KEY_BUNDLE, bundle))
            }
            ll_shop_detail_group_buy_more -> {//显示更多团购套餐
                buyAdapter!!.setNewData(shopBean!!.productList)
                ll_shop_detail_group_buy_more.visibility = View.GONE
            }
            ll_shop_detail_evaluation_more -> {//显示更多评论
                getAllEvaluation(shopBean!!.shopId.toString(), "1", "50")
            }
            rl_shop_detail_takeaway -> {//外卖
                val intent = Intent(this, ProjectH5Activity::class.java)
                intent.putExtra(Key.KEY_URL.value, Api.H5_CITYLIFE_URL + "/homepage?storeId=" + shopid)
                startActivity(intent)
            }
            img_shop_detail_return_top -> {//回到顶部
                scrollView_shop_detail.smoothScrollTo(0, 0)
            }

            img_shop_detail_floating -> {//悬浮窗  点击进入广告
                startActivity(Intent(this, ProjectH5Activity::class.java)
                        .putExtra(Key.KEY_URL.value, floatList[0].linkUrl))
            }

            tv_shop_collection -> {//收藏店铺
                addCollectionShop()
            }
        }
    }

}



