package com.szy.yishopcustomer.Activity.samecity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.szy.yishopcustomer.Activity.IBaseWebview.KEY_ORDER
import com.szy.yishopcustomer.Constant.Api
import com.szy.yishopcustomer.Constant.HttpWhat
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.Order.OrderModel
import com.szy.yishopcustomer.Util.RequestAddHead
import com.szy.yishopcustomer.Util.json.GsonUtils
import com.yolanda.nohttp.NoHttp
import com.yolanda.nohttp.RequestMethod
import com.yolanda.nohttp.rest.OnResponseListener
import com.yolanda.nohttp.rest.Response
import kotlinx.android.synthetic.main.activity_group_buy_pay_fail.*

class GroupBuyPayFailActivity : AppCompatActivity(), View.OnClickListener {

    var orderId=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_buy_pay_fail)
        initView()
        setListener()
    }

    /**
     * 初始化
     */
    private fun initView(){

        val intent = intent
        orderId = intent.getStringExtra(KEY_ORDER)

        tv_show_hint.text = "订单尚未支付成功，请您尽快支付！"+"\n"+"支付多少金额，送多少元宝哦"

        getOrderDetail(orderId)

    }

    /**
     * 设置监听
     */
    private fun setListener(){
        img_group_buy_order_pay_fail_back.setOnClickListener(this)
        tv_to_order_pay.setOnClickListener(this)
        tv_return_home.setOnClickListener(this)
    }

    private fun getOrderDetail(orderId:String){
        val requestQueue = NoHttp.newRequestQueue()
        val request = NoHttp.createStringRequest(Api.API_CITY_ORDER_DETAIL, RequestMethod.GET)
        RequestAddHead.addNoHttpHead(request, this, Api.API_CITY_ORDER_DETAIL, "GET")
        request.add("orderId", orderId)
        requestQueue.add(HttpWhat.HTTP_ORDER_RS_DATA.value,request,object :OnResponseListener<String>{
            override fun onFinish(what: Int) {
            }

            override fun onSucceed(what: Int, response: Response<String>?) {
                if (response!!.responseCode()==200){
                    val orderBean = GsonUtils.toObj(response.get(), OrderModel::class.java)
                    tv_pay_fail_money.text = "¥"+orderBean!!.payAmount.toString()
                }
            }

            override fun onFailed(what: Int, response: Response<String>?) {
            }

            override fun onStart(what: Int) {
            }

        })
    }


    override fun onClick(p0: View?) {
        when(p0){
            img_group_buy_order_pay_fail_back->{
                finish()
            }
            tv_to_order_pay->{//去支付订单页支付

            }
            tv_return_home->{//去首页

            }
        }
    }
}