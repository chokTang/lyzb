package com.szy.yishopcustomer.Activity.samecity

import android.os.Bundle
import android.os.Message
import android.view.View
import com.google.gson.Gson
import com.lyzb.jbx.R
import com.szy.yishopcustomer.Activity.IBaseWebview
import com.szy.yishopcustomer.Activity.samecity.ConfirmOrderActivity.Companion.KEY_ORDER_ID
import com.szy.yishopcustomer.Activity.samecity.ConfirmOrderActivity.Companion.KEY_ORDER_MONEY
import com.szy.yishopcustomer.Constant.Api
import com.szy.yishopcustomer.Constant.HttpWhat
import com.szy.yishopcustomer.Util.LogUtils
import com.szy.yishopcustomer.Util.RequestAddHead
import com.szy.yishopcustomer.Util.json.GsonUtils
import com.szy.yishopcustomer.ViewModel.samecity.AliPayCallBackBean
import com.szy.yishopcustomer.ViewModel.samecity.WxPayCallBackBean
import com.yolanda.nohttp.NoHttp
import com.yolanda.nohttp.RequestMethod
import com.yolanda.nohttp.rest.OnResponseListener
import com.yolanda.nohttp.rest.Response
import kotlinx.android.synthetic.main.activity_order.*

class OrderPayActivity : IBaseWebview(), View.OnClickListener {

    companion object {
        const val TYPE_WX = "wxpay"
        const val TYPE_ALI_PAY = "alipay"
    }
    var orderId = ""
    var orderMoney = ""
    var isSelectedIndex = TYPE_WX

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        mActivity = this
        initView()
        setListener()
    }

    /**
     * 初始化
     */
    fun initView() {
        //默认选择微信
        img_wechat_selector.isSelected = true

        val intent = intent
        orderId = intent.getStringExtra(KEY_ORDER_ID)
        orderMoney = intent.getStringExtra(KEY_ORDER_MONEY)
        tv_order_pay_money.text = orderMoney
    }

    /**
     * 设置监听
     */
    fun setListener() {
        img_order_pay_back.setOnClickListener(this)
        ll_wechat_pay.setOnClickListener(this)
        ll_ali_pay.setOnClickListener(this)
        tv_confirm_pay_order.setOnClickListener(this)
    }


    /**
     * 支付订单
     */
    private fun payOrder(id: String, payMent: Int) {
        LogUtils.e("当前传入参数$id")
        val requestQueue = NoHttp.newRequestQueue()
        val request = NoHttp.createStringRequest(Api.API_PAY_ORDER_URL, RequestMethod.POST)
        RequestAddHead.addNoHttpHead(request, this, Api.API_PAY_ORDER_URL, "POST")

        val jsonObject = com.alibaba.fastjson.JSONObject()
        jsonObject["orderId"] = id
        jsonObject["payMent"] = payMent
        request.setDefineRequestBodyForJson(jsonObject.toJSONString())

        requestQueue.add(HttpWhat.HTTP_PAY_ORDER.value, request, object : OnResponseListener<String> {
            override fun onFinish(what: Int) {
            }

            override fun onSucceed(what: Int, response: Response<String>?) {
                LogUtils.e("++++++>>>" + response.toString())
                if (response!!.responseCode() == 200) {
                    LogUtils.e("----->>>" + response.toString())
                    when (payMent) {
                        1 -> {
                            val wxPayCallBackBean = GsonUtils.toObj(response.get(), WxPayCallBackBean::class.java)
                            wxPayCallBackBean!!.orderId = orderId
                            weixinPayOrder(GsonUtils.toJson(wxPayCallBackBean))
                        }
                        2 -> {
                            val aliPayCallBackBean = GsonUtils.toObj(response.get(), AliPayCallBackBean::class.java)
                            aliPayCallBackBean!!.orderId = orderId
                            aliPayOrder(GsonUtils.toJson(aliPayCallBackBean))
                        }
                    }
                }
            }

            override fun onFailed(what: Int, response: Response<String>?) {
            }

            override fun onStart(what: Int) {
            }

        })
    }

    /**
     * 选择支付方式
     */
    private fun selectedPayType(type: String) {
        when (type) {
            TYPE_WX -> {//选择微信
                img_wechat_selector.isSelected = true
                img_alipay_selector.isSelected = false
            }
            TYPE_ALI_PAY -> {//选择阿里
                img_wechat_selector.isSelected = false
                img_alipay_selector.isSelected = true
            }
        }
    }
    /**
     * 微信
     */
    fun weixinPayOrder(orderData: String) {
        val mMessage = Message()
        mMessage.what = PAY_WX
        mMessage.obj = orderData
        mMessage.arg1 = 1 //1代表原生支付
        mHandler.sendMessage(mMessage)
    }

    /**
     * 支付宝
     */
    fun aliPayOrder(orderData: String) {
        val mMessage = Message()
        mMessage.what = PAY_ALI
        mMessage.obj = orderData
        mMessage.arg1 = 1  //1代表原生支付
        mHandler.sendMessage(mMessage)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            img_order_pay_back -> {
                finish()
            }
            ll_wechat_pay -> {//选择微信支付
                isSelectedIndex = TYPE_WX
                selectedPayType(isSelectedIndex)
            }

            ll_ali_pay -> {//选择支付宝支付
                isSelectedIndex = TYPE_ALI_PAY
                selectedPayType(isSelectedIndex)
            }
            tv_confirm_pay_order -> {//确认买单
                when (isSelectedIndex) {
                    TYPE_WX -> {//选择微信
                        payOrder(orderId, 1)
                    }
                    TYPE_ALI_PAY -> {//选择阿里
                        payOrder(orderId, 2)
                    }
                }
            }
        }
    }


}