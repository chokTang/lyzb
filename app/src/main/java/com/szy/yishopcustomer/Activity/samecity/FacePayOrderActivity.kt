package com.szy.yishopcustomer.Activity.samecity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.szy.yishopcustomer.Activity.LoginActivity
import com.szy.yishopcustomer.Activity.samecity.ConfirmOrderActivity.Companion.KEY_ORDER_ID
import com.szy.yishopcustomer.Activity.samecity.ConfirmOrderActivity.Companion.KEY_ORDER_MONEY
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.Companion.KEY_BUNDLE
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.Companion.KEY_SHOP_BEAN
import com.szy.yishopcustomer.Constant.Api
import com.szy.yishopcustomer.Constant.HttpWhat
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.App
import com.szy.yishopcustomer.Util.LogUtils
import com.szy.yishopcustomer.Util.RequestAddHead
import com.szy.yishopcustomer.ViewModel.samecity.OrderBean
import com.szy.yishopcustomer.ViewModel.samecity.OrderItemBean
import com.szy.yishopcustomer.ViewModel.samecity.ShopBean
import com.yolanda.nohttp.NoHttp
import com.yolanda.nohttp.RequestMethod
import com.yolanda.nohttp.rest.OnResponseListener
import com.yolanda.nohttp.rest.Response
import kotlinx.android.synthetic.main.activity_face_pay.*
import kotlinx.android.synthetic.main.layout_title_no_right.*

class FacePayOrderActivity : AppCompatActivity(), View.OnClickListener {
    private var shopBean: ShopBean? = null

    var money=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_pay)
        initView()
        setListener()
    }

    /**
     * 初始化
     */
    private fun initView() {
        val intent = intent
        shopBean = intent.getBundleExtra(KEY_BUNDLE).getSerializable(KEY_SHOP_BEAN) as ShopBean

        tv_title_name.text = shopBean!!.shopName
    }

    /**
     * 设置监听
     */
    private fun setListener() {
        img_title_back.setOnClickListener(this)
        tv_confirm_pay_order.setOnClickListener(this)
        edt_consume_money.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0 != null) {
                    if (p0.isNotEmpty()){
                        tv_consume_money.text = "¥"+p0.toString()
                        tv_confirm_pay_order.isSelected = true
                        tv_confirm_pay_order.isEnabled = true
                        money = p0.toString()
                    }else{
                        tv_confirm_pay_order.isSelected = false
                        tv_confirm_pay_order.isEnabled = false
                        tv_consume_money.text=""
                    }
                }else{
                    tv_confirm_pay_order.isSelected = false
                    tv_confirm_pay_order.isEnabled = false
                    tv_consume_money.text=""
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }


    /**
     * 下单
     * list产品相关参数
     * orderMent 1：团购，2：到店付,3:外卖
     */
    fun addOrder(list: MutableList<OrderItemBean>?, orderMent: Int) {
        val requestQueue = NoHttp.newRequestQueue()
        val request = NoHttp.createStringRequest(Api.API_ADD_ORDER_URL, RequestMethod.POST)
        RequestAddHead.addNoHttpHead(request, this, Api.API_ADD_ORDER_URL, "POST")

        val jsonObject = com.alibaba.fastjson.JSONObject()

        jsonObject["orderItems"] = list
        jsonObject["orderMent"] = orderMent
        jsonObject["payAmount"] = money
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
                    startActivity(Intent(this@FacePayOrderActivity,
                            OrderPayActivity::class.java).putExtra(KEY_ORDER_ID,orderBean.orderId)
                            .putExtra(KEY_ORDER_MONEY,money))
                }
            }

            override fun onFailed(what: Int, response: Response<String>?) {
                Toast.makeText(this@FacePayOrderActivity, R.string.data_error, Toast.LENGTH_SHORT).show()
            }

            override fun onStart(what: Int) {
            }

        })
    }

    override fun onClick(p0: View?) {
        when (p0) {
            tv_confirm_pay_order -> {//确认买单
                if (App.getInstance().isLogin){
                    addOrder(null,2)
                }else{
                    startActivity(Intent(this,LoginActivity::class.java))
                }
            }
            img_title_back -> {
                finish()
            }
        }
    }
}