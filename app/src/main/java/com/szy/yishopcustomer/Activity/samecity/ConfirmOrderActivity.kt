package com.szy.yishopcustomer.Activity.samecity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.szy.common.Other.CommonRequest
import com.szy.yishopcustomer.Activity.LoginActivity
import com.szy.yishopcustomer.Activity.samecity.GroupBuyActivity.Companion.KEY_PRODUCT_BEAN
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.Companion.KEY_BUNDLE
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.Companion.KEY_SHOP_BEAN
import com.szy.yishopcustomer.Constant.Api
import com.szy.yishopcustomer.Constant.HttpWhat
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.App
import com.szy.yishopcustomer.Util.GlideUtil
import com.szy.yishopcustomer.Util.LogUtils
import com.szy.yishopcustomer.Util.RequestAddHead
import com.szy.yishopcustomer.Util.json.GsonUtils
import com.szy.yishopcustomer.ViewModel.samecity.OrderBean
import com.szy.yishopcustomer.ViewModel.samecity.OrderItemBean
import com.szy.yishopcustomer.ViewModel.samecity.ProductBean
import com.szy.yishopcustomer.ViewModel.samecity.ShopBean
import com.yolanda.nohttp.NoHttp
import com.yolanda.nohttp.RequestMethod
import com.yolanda.nohttp.rest.OnResponseListener
import com.yolanda.nohttp.rest.Response
import kotlinx.android.synthetic.main.activity_confirm_order.*
import org.json.JSONObject

class ConfirmOrderActivity : AppCompatActivity(), View.OnClickListener {


    companion object {
        const val KEY_ORDER_ID = "keyorderid"
        const val KEY_ORDER_MONEY = "keyordermoney"
    }
    var price = 0.0
    var acer = 0
    var acerDeduction = 0  //元宝抵扣
    var count = 1
    var isAddRed = false
    var isInput = false
    var productBean: ProductBean? = null
    var shopBean: ShopBean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_order)
        initView()
        setOnclickListener()
    }


    /**
     * 初始化
     */
    fun initView() {
        val intent = intent
        productBean = intent.getBundleExtra(KEY_BUNDLE).getSerializable(KEY_PRODUCT_BEAN) as ProductBean?
        shopBean = intent.getBundleExtra(KEY_BUNDLE).getSerializable(KEY_SHOP_BEAN) as ShopBean?
        //初始化的时候 不显示光标要输入需要点击输入符号
        edt_order_phone.isFocusable = false
        edt_order_phone.isFocusableInTouchMode = false

        showView()
    }

    /**
     * 设置监听
     */
    private fun setOnclickListener() {
        tv_sub.setOnClickListener(this)
        tv_add.setOnClickListener(this)
        img_input.setOnClickListener(this)
        img_confirm_order_back.setOnClickListener(this)
        tv_now_buy.setOnClickListener(this)
    }

    fun showView() {
        if (!TextUtils.isEmpty(productBean!!.productImageVoList[0].imgUrl)) {
            //商品图片
            Glide.with(this)
                    .load(productBean!!.productImageVoList[0].imgUrl)
                    .apply(GlideUtil.EmptyOptions())
                    .into(img_order_detail)
        }
        tv_order_shop_name.text = shopBean!!.shopName
        tv_order_detail_name.text = productBean!!.prodName
        tv_order_detail_price.text = "¥" + productBean!!.jcPrice.toString()
        if (App.getInstance().user_ingot_number != null) {
            acerDeduction = if (App.getInstance().user_ingot_number.toInt() >= productBean!!.acer) {
                productBean!!.acer
            } else {
                App.getInstance().user_ingot_number.toInt()
            }
        } else {
            App.getInstance().user_ingot_number = "0"
            acerDeduction = if (App.getInstance().user_ingot_number.toInt() >= productBean!!.acer) {
                productBean!!.acer
            } else {
                App.getInstance().user_ingot_number.toInt()
            }
        }
        tv_order_acer.text = "¥$acerDeduction"
        tv_order_real_price.text = "¥" + (productBean!!.jcPrice - acerDeduction)
        tv_group_buy_detail_new_price.text = "总计:¥" + (productBean!!.jcPrice - acerDeduction)
        tv_group_buy_send_acer.text = "(支付送" + (productBean!!.jcPrice - acerDeduction) + "元宝)"
        if (!TextUtils.isEmpty(App.getInstance().userPhone)) {
            edt_order_phone.text = Editable.Factory.getInstance().newEditable(App.getInstance().userPhone)
        }
    }


    /**
     * 下单
     * list产品相关参数
     * orderMent 1：团购，2：到店付,3:外卖
     */
    fun addOrder(list: MutableList<OrderItemBean>, orderMent: Int) {
        val requestQueue = NoHttp.newRequestQueue()
        val request = NoHttp.createStringRequest(Api.API_ADD_ORDER_URL, RequestMethod.POST)
        RequestAddHead.addNoHttpHead(request, this, Api.API_ADD_ORDER_URL, "POST")

        val jsonObject = com.alibaba.fastjson.JSONObject()

        jsonObject["orderItems"] = list
        jsonObject["orderMent"] = orderMent
        jsonObject["payAmount"] = price
        jsonObject["payPoint"] = acer
        jsonObject["prodCount"] = tv_count.text.toString()
        jsonObject["storeId"] = shopBean!!.shopId
        jsonObject["storeName"] = shopBean!!.shopName
        jsonObject["userId"] = App.getInstance().userId
        jsonObject["userPhone"] = App.getInstance().userPhone

        LogUtils.e("传入参数为" + jsonObject.toJSONString())
        request.setDefineRequestBodyForJson(jsonObject.toJSONString())
        requestQueue.add(HttpWhat.HTTP_ADD_ORDER.value, request, object : OnResponseListener<String> {
            override fun onFinish(what: Int) {
            }

            override fun onSucceed(what: Int, response: Response<String>?) {
                LogUtils.e(response.toString())
                if (response!!.responseCode() == 200) {
                    val orderBean = Gson().fromJson(response.get(), OrderBean::class.java)
                    startActivity(Intent(this@ConfirmOrderActivity,
                            OrderPayActivity::class.java).putExtra(KEY_ORDER_ID, orderBean.orderId)
                            .putExtra(KEY_ORDER_MONEY, tv_group_buy_detail_new_price.text))
                }
            }

            override fun onFailed(what: Int, response: Response<String>?) {
                Toast.makeText(this@ConfirmOrderActivity, R.string.data_error, Toast.LENGTH_SHORT).show()
            }

            override fun onStart(what: Int) {
            }

        })
    }


    @SuppressLint("ShowToast")
    override fun onClick(p0: View?) {
        when (p0) {
            img_confirm_order_back -> {
                finish()
            }
            tv_sub -> {//减少
                isAddRed = false
                tv_sub.isSelected = !isAddRed
                tv_add.isSelected = isAddRed
                if (count > 1) {
                    count--
                    tv_count.text = count.toString()
                    price = count*(productBean!!.jcPrice - acerDeduction)
                    tv_order_real_price.text = "¥"+price.toString()
                    tv_group_buy_detail_new_price.text="总计:¥"+price
                    tv_order_acer.text="¥"+acer
                    tv_group_buy_send_acer.text = "(支付送" + price + "元宝)"

                } else {
                    Toast.makeText(this, "不能再减少了哟", Toast.LENGTH_SHORT).show()
                }
            }
            tv_add -> {//添加

                isAddRed = true
                tv_sub.isSelected = !isAddRed
                tv_add.isSelected = isAddRed
                if (productBean!!.restrictionNum>0){//限购
                    if (count < productBean!!.restrictionNum) {
                        count++
                        tv_count.text = count.toString()
                        price = count*(productBean!!.jcPrice - acerDeduction)
                        acer  = count*acerDeduction
                        tv_order_real_price.text ="¥"+ price.toString()
                        tv_group_buy_detail_new_price.text="总计:¥"+price
                        tv_order_acer.text="¥"+acer
                        tv_group_buy_send_acer.text = "(支付送" + price + "元宝)"
                    } else {
                        Toast.makeText(this, "此商品限购" + productBean!!.restrictionNum + "份", Toast.LENGTH_SHORT).show()
                    }
                }else{//不限购
                    count++
                    tv_count.text = count.toString()
                    price = count*(productBean!!.jcPrice - acerDeduction)
                    acer  = count*acerDeduction
                    tv_order_real_price.text ="¥"+ price.toString()
                    tv_group_buy_detail_new_price.text="总计:¥"+price
                    tv_order_acer.text="¥"+acer
                    tv_group_buy_send_acer.text = "(支付送" + price + "元宝)"
                }

            }
            img_input -> {//点击输入
                isInput = !isInput
                if (isInput) {//点击后可以输入
                    edt_order_phone.isFocusable = true
                    edt_order_phone.isFocusableInTouchMode = true
                    edt_order_phone.requestFocus()
                    if (!TextUtils.isEmpty(edt_order_phone.text)) {
                        edt_order_phone.setSelection(edt_order_phone.text.length)
                    }
                } else {
                    edt_order_phone.isFocusable = false
                    edt_order_phone.isFocusableInTouchMode = false
                }
            }

            tv_now_buy -> {//提交订单
                if (App.getInstance().isLogin) {//需要登录权限
                    if (!TextUtils.isEmpty(edt_order_phone.text)) {
                        val list: MutableList<OrderItemBean> = arrayListOf()
                        val bean = OrderItemBean(productBean!!.prodName, productBean!!.productImageVoList[0].imgUrl
                                , productBean!!.jcPrice * (tv_count.text.toString().toInt())
                                , productBean!!.prodId, tv_count.text.toString().toInt(), productBean!!.jcPrice, productBean!!.storeId.toInt())
                        list.add(bean)
                        acer  = count*acerDeduction
                        addOrder(list, 1)


                    } else {
                        Toast.makeText(this, "电话不能为空", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                }


            }
        }
    }
}