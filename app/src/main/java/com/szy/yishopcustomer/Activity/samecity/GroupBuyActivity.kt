package com.szy.yishopcustomer.Activity.samecity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.szy.yishopcustomer.Activity.LoginActivity
import com.szy.yishopcustomer.Activity.ShareActivity
import com.szy.yishopcustomer.Activity.ViewOriginalImageActivity
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.Companion.KEY_ADDRESS
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.Companion.KEY_BUNDLE
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.Companion.KEY_LAT
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.Companion.KEY_LONT
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.Companion.KEY_PRODUCT_ID
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.Companion.KEY_SHOP_BEAN
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.Companion.PERMISSION_CODE
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.Companion.peimission
import com.szy.yishopcustomer.Adapter.CityHomeAdapter.KEY_SHOP_ID
import com.szy.yishopcustomer.Adapter.samecity.ProductDetailAdapter
import com.szy.yishopcustomer.Adapter.samecity.ProductPicAdapter
import com.szy.yishopcustomer.Adapter.samecity.ShopDetailGroupBuyAdapter
import com.szy.yishopcustomer.Adapter.samecity.ShopDetailNearShopAdapter
import com.szy.yishopcustomer.Constant.Api
import com.szy.yishopcustomer.Constant.HttpWhat
import com.szy.yishopcustomer.Constant.Key
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.*
import com.szy.yishopcustomer.Util.json.GsonUtils
import com.szy.yishopcustomer.ViewModel.samecity.*
import com.yolanda.nohttp.NoHttp
import com.yolanda.nohttp.RequestMethod
import com.yolanda.nohttp.rest.OnResponseListener
import com.yolanda.nohttp.rest.Response
import kotlinx.android.synthetic.main.activity_group_buy_detail.*
import kotlinx.android.synthetic.main.layout_group_buy_detail_shop_detail.*

class GroupBuyActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val KEY_PRODUCT_BEAN = "productBean"
    }

    var shareData: ArrayList<String> = ArrayList()
    var picdata: ArrayList<String> = ArrayList()
    var group = 0
    var decrow = 0
    var isShowAll = true
    var productId = ""
    var prodDecBean: ProdDecBean? = null
    var productBean: ProductBean? = null
    var shopBean: ShopBean? = null
    var shopId = ""
    var productpicAdapter: ProductPicAdapter? = null
    var productDetailAdapter: ProductDetailAdapter? = null
    private var buyAdapter: ShopDetailGroupBuyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_buy_detail)
        initView()
        setListener()
    }

    /**
     * 初始化
     */
    fun initView() {
        val intent = intent
        productId = intent.getStringExtra(KEY_PRODUCT_ID)
        shopId = intent.getBundleExtra(KEY_BUNDLE).getString(KEY_SHOP_ID)


        rv_pic.layoutManager = GridLayoutManager(this, 2)
        rv_group_detail_product_detail.layoutManager = LinearLayoutManager(this)
        rv_group_detail_near_shop.layoutManager = LinearLayoutManager(this)
        rv_pic.isNestedScrollingEnabled = false
        rv_group_detail_product_detail.isNestedScrollingEnabled = false
        rv_group_detail_near_shop.isNestedScrollingEnabled = false
        productpicAdapter = ProductPicAdapter(this, R.layout.item_product_pic)
        productDetailAdapter = ProductDetailAdapter(this, R.layout.item_product_detail)
        buyAdapter = ShopDetailGroupBuyAdapter(this, R.layout.item_group_buy_package)

        rv_pic.adapter = productpicAdapter
        rv_group_detail_product_detail.adapter = productDetailAdapter
        rv_group_detail_near_shop.adapter = buyAdapter
        if (TextUtils.isEmpty(App.getInstance().lat)) {
            getShopDetail(shopId, "", "")
        } else {
            getShopDetail(shopId, App.getInstance().lat, App.getInstance().lng)
        }
    }

    /**
     * 设置监听
     */
    fun setListener() {
        tv_check_more.setOnClickListener(this)
        tv_group_buy_shop_address.setOnClickListener(this)
        rl_group_buy_detail_phone.setOnClickListener(this)
        img_group_buy_detail_back.setOnClickListener(this)
        img_group_buy_detail_share.setOnClickListener(this)
        ll_shop_msg.setOnClickListener(this)
        tv_now_buy.setOnClickListener(this)
        productpicAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            picdata.clear()
            for (i in 0 until adapter.data.size) {
                picdata.add((adapter.data[i] as ProductImageVoList).imgUrl)
            }
            openImageGallery(position, picdata)
        }
        buyAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            getProductIdDetail((adapter.data[position] as Product).prodId)
        }
    }


    /**
     * 浏览大图(查看大图)
     */
    fun openImageGallery(postion: Int, imgList: java.util.ArrayList<String>) {
        val intent = Intent()
        intent.setClass(this, ViewOriginalImageActivity::class.java)
        intent.putStringArrayListExtra(Key.KEY_GOODS_IMAGE_LIST.value, imgList)
        intent.putExtra(Key.KEY_GOODS_IMAGE_SELECTED_INDEX.value, postion)
        startActivity(intent)
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
                Toast.makeText(this@GroupBuyActivity, R.string.data_error, Toast.LENGTH_SHORT).show()
            }

        })
    }


    /**
     * 获取商品详情信息
     * productId  商品ID
     */
    fun getProductIdDetail(productId: String) {
        val requestQueue = NoHttp.newRequestQueue()
        val request = NoHttp.createStringRequest(Api.API_PRODUCT_DETAIL_URL, RequestMethod.GET)
        RequestAddHead.addNoHttpHead(request, this, Api.API_PRODUCT_DETAIL_URL, "GET")

        request.add("prodId", productId)
        requestQueue.add(HttpWhat.HTTP_PRODUCT_DETAIL.value, request, object : OnResponseListener<String> {
            override fun onStart(what: Int) {
            }

            override fun onFinish(what: Int) {
            }

            override fun onSucceed(what: Int, response: Response<String>?) {
                if (response!!.responseCode() == 200)
                    requestCallback(what, response)
            }

            override fun onFailed(what: Int, response: Response<String>?) {
                Toast.makeText(this@GroupBuyActivity, R.string.data_error, Toast.LENGTH_SHORT).show()
            }
        })
    }


    @SuppressLint("SetTextI18n")
    fun requestCallback(what: Int, response: Response<String>?) {
        when (what) {
            HttpWhat.HTTP_SHOP_DETAIL.value -> {//店铺详情
                shopBean = Gson().fromJson(response!!.get(), ShopBean::class.java)
                getProductIdDetail(productId)
            }
            HttpWhat.HTTP_PRODUCT_DETAIL.value -> {//查询产品详情
                LogUtils.e("产品详情" + response.toString())
                if (response!!.responseCode() == Utils.CITY_NET_SUCES) {
                    if (shopBean!!.shopName.isNotEmpty()) {
                        tv_group_buy_detail_shop_name.text = shopBean!!.shopName
                    }
                    productBean = Gson().fromJson(response.get(), ProductBean::class.java)
                    if (null == productBean) {
                        return
                    }
                    tv_group_buy_detail_product_name.text = productBean!!.prodName

                    if (!TextUtils.isEmpty(productBean!!.prodDesc)) {
                        try {//这里这样写是因为当前字段有时候返回的是一个实体类的json字符串,有时候又是返回直接描述的字符串
                            prodDecBean = GsonUtils.toObj(productBean!!.prodDesc, ProdDecBean::class.java)
                            if (prodDecBean != null && !TextUtils.isEmpty(prodDecBean!!.desc)) {
                                tv_group_buy_detail_dec.text = prodDecBean!!.desc
                            }
                        } catch (e: JsonSyntaxException) {
                            if (!TextUtils.isEmpty(productBean!!.prodDesc)) {
                                tv_group_buy_detail_dec.text = productBean!!.prodDesc
                            }
                        }

                    }


                    productpicAdapter!!.setNewData(productBean!!.productImageVoList)
                    tv_group_buy_detail_title.text = productBean!!.prodName


                    //最多显示7行(要看第七行是在第几组第几行)
                    var countRow = 0
                    var j = 0 // 第几组
                    var topRow = 0 //第7行的上一组最后一条是第几行
                    if (prodDecBean != null) {
                        for (i in 0 until prodDecBean!!.prodDescAttributes.size) {
                            countRow += prodDecBean!!.prodDescAttributes[i].list.size + 1
                            j = if (i >= 1) {//前面一组小于7 后面一组大于7
                                if (prodDecBean!!.prodDescAttributes[i - 1].list.size + 1 < 7 &&
                                        prodDecBean!!.prodDescAttributes[i].list.size + 1 >= 7) { //这里是查看地7条在第几组
                                    if (prodDecBean!!.prodDescAttributes[i - 1].list.size + 1 == 7) {
                                        i
                                    } else {
                                        i + 1
                                    }
                                } else {
                                    1
                                }
                            } else {
                                i + 1
                            }
                        }
                    }
                    group = j
                    productDetailAdapter!!.group = j
                    if (j - 1 == 0) {//只有一组
                        decrow = 6
                        productDetailAdapter!!.decrow = 6
                    } else {
                        for (m in 0 until j - 1) {
                            //查看第7行的上一组最后一条是第几行(好显示最后一组多少行)
                            //如 7行在第3组 第2组最后一行是4条 则最后一组显示3行 凑齐7行
                            topRow += prodDecBean!!.prodDescAttributes[m].list.size + 1
                        }
                        decrow = countRow - topRow
                        productDetailAdapter!!.decrow = countRow - topRow
                    }
                    if (prodDecBean != null) {
                        if (prodDecBean!!.prodDescAttributes.isEmpty()) {
                            tv_check_more.visibility = View.GONE
                        } else {
                            productDetailAdapter!!.setNewData(prodDecBean!!.prodDescAttributes)
                            tv_check_more.visibility = View.VISIBLE
                        }
                    } else {
                        tv_check_more.visibility = View.GONE
                    }
                    if (!TextUtils.isEmpty(shopBean!!.shopLogo)) {
                        //商家信息
                        Glide.with(this)
                                .load(Utils.setImgNetUrl(Api.API_CITY_HOME_MER_IMG_URL, shopBean!!.shopLogo))
                                .apply(GlideUtil.EmptyOptions())
                                .into(img_group_buy_shop_logo)
                    }

                    tv_group_buy_shop_name.text = shopBean!!.shopName
                    tv_group_buy_acer.text = "元宝最高抵扣¥" + shopBean!!.acer
                    tv_group_buy_shop_address.text = shopBean!!.addressDetail

                    //相关推荐 要求最多显示五个
                    if (shopBean!!.productList.size > 5) {
                        buyAdapter!!.setNewData(shopBean!!.productList.subList(0, 5))
                    } else {
                        buyAdapter!!.setNewData(shopBean!!.productList)
                    }

                    //订单价格信息
                    tv_group_buy_detail_new_price.text = productBean!!.jcPrice.toString()
                    tv_group_buy_old_price.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG //中划线
                    tv_group_buy_old_price.text = "¥" + productBean!!.marketPrice.toString()
                    tv_acer_hint.text = "最高抵扣"
                    tv_group_buy_discount.text = "¥" + productBean!!.acer.toString()
                }
            }

        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            img_group_buy_detail_back -> {
                finish()
            }
            img_group_buy_detail_share -> {
                shareData.clear()
                shareData.add(Api.H5_CITYLIFE_URL + "/groupDetailIndex?goodsId=" + productId)
                shareData.add(productBean!!.prodName)
                shareData.add(prodDecBean!!.desc)
                if (TextUtils.isEmpty(productBean!!.productImageVoList[0].imgUrl)) {
                    shareData.add(Utils.setImgNetUrl(Api.API_CITY_HOME_MER_IMG_URL, shopBean!!.shopLogo))
                } else {
                    shareData.add(productBean!!.productImageVoList[0].imgUrl)
                }
                startActivity(Intent(this@GroupBuyActivity, ShareActivity::class.java)
                        .putExtra(ShareActivity.SHARE_SCOPE, "0")
                        .putStringArrayListExtra(ShareActivity.SHARE_DATA, shareData).putExtra(ShareActivity.SHARE_TYPE, ShareActivity.TYPE_SOURCE))
            }
            tv_check_more -> {//点击查看更多

                if (prodDecBean!!.prodDescAttributes.isEmpty()) {
                    return
                }
                if (isShowAll) {//显示全部
                    productDetailAdapter!!.group = 0
                    productDetailAdapter!!.decrow = 0
                    tv_check_more.text = "收起"
                } else {//收缩
                    productDetailAdapter!!.group = group
                    productDetailAdapter!!.decrow = decrow
                    tv_check_more.text = "查看更多"
                }
                productDetailAdapter!!.setNewData(prodDecBean!!.prodDescAttributes)
                isShowAll = !isShowAll

            }
            tv_group_buy_shop_address -> {//点击地址
                val intent = Intent()
                intent.setClass(this, AmapActivity::class.java)
                intent.putExtra(KEY_LAT, shopBean!!.shopLat)
                intent.putExtra(KEY_LONT, shopBean!!.shopLng)
                intent.putExtra(KEY_ADDRESS, shopBean!!.addressDetail)
                startActivity(intent)
            }
            rl_group_buy_detail_phone -> {//拨打电话
                if (PermissionUtils.hasPermission(this, peimission)) {
                    callPhone(shopBean!!.serviceTel)
                } else {
                    PermissionUtils.requestPermission(this, PERMISSION_CODE, peimission)
                }
            }
            ll_shop_msg -> {//点击商店
                val mIntent = Intent(this, ShopDetailActivity::class.java)
                mIntent.putExtra(KEY_SHOP_ID, shopId)
                startActivity(mIntent)
                finish()

            }
            tv_now_buy -> {//点击购买
                val bundle = Bundle()
                bundle.putSerializable(KEY_PRODUCT_BEAN, productBean)
                bundle.putSerializable(KEY_SHOP_BEAN, shopBean)
                if (App.getInstance().isLogin) {//需要登录权限
                    startActivity(Intent(this, ConfirmOrderActivity::class.java)
                            .putExtra(KEY_BUNDLE, bundle))
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
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
}