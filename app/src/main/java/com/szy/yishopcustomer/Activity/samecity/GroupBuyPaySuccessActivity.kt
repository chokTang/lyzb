package com.szy.yishopcustomer.Activity.samecity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.lyzb.jbx.R
import com.szy.yishopcustomer.Activity.IBaseWebview.KEY_ORDER
import com.szy.yishopcustomer.Constant.Api
import com.szy.yishopcustomer.Constant.HttpWhat
import com.szy.yishopcustomer.ResponseModel.SameCity.Order.OrderModel
import com.szy.yishopcustomer.Util.App
import com.szy.yishopcustomer.Util.RequestAddHead
import com.szy.yishopcustomer.Util.json.GsonUtils
import com.yolanda.nohttp.NoHttp
import com.yolanda.nohttp.RequestMethod
import com.yolanda.nohttp.rest.OnResponseListener
import com.yolanda.nohttp.rest.Response
import kotlinx.android.synthetic.main.activity_group_buy_pay_success.*

class GroupBuyPaySuccessActivity : AppCompatActivity(), View.OnClickListener {

    var orderId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_buy_pay_success)
        initView()
        setListener()
    }


    private fun initView() {
        val intent = intent
        orderId = intent.getStringExtra(KEY_ORDER)
        getOrderDetail(orderId)
    }

    private fun setListener() {
        img_group_buy_order_pay_success_back.setOnClickListener(this)
    }

    /**
     * 查询订单详情
     */
    private fun getOrderDetail(orderId: String) {
        val requestQueue = NoHttp.newRequestQueue()
        val request = NoHttp.createStringRequest(Api.API_CITY_ORDER_DETAIL, RequestMethod.GET)
        RequestAddHead.addNoHttpHead(request, this, Api.API_CITY_ORDER_DETAIL, "GET")
        request.add("orderId", orderId)
        requestQueue.add(HttpWhat.HTTP_ORDER_RS_DATA.value, request, object : OnResponseListener<String> {
            override fun onFinish(what: Int) {
            }

            override fun onSucceed(what: Int, response: Response<String>?) {
                if (response!!.responseCode() == 200) {
                    val orderBean = GsonUtils.toObj(response.get(), OrderModel::class.java)
                    tv_pay_success_money.text = "¥" + orderBean!!.payAmount.toString()
                    tv_pay_success_acer.text = orderBean.payPoint.toString()
                    tv_pay_order_success_shop_name.text = orderBean.storeName
                    if (orderBean.costCode == null) {
                        tv_pay_order_coupon.visibility = View.GONE
                        tv_show_hint.visibility = View.GONE
                    } else {
                        tv_pay_order_coupon.visibility = View.VISIBLE
                        tv_show_hint.visibility = View.VISIBLE
                        tv_pay_order_coupon.text = "券码:" + orderBean.costCode
                    }
                    if (orderBean.payAmount >= 1) {
                        tv_get_acer.text = Math.ceil(orderBean.payAmount.toDouble()).toString()
                    } else {
                        tv_get_acer.text = "0"
                    }
                    tv_can_use_acer.text = "可用" + App.getInstance().user_ingot_number + "元宝"
                }
            }

            override fun onFailed(what: Int, response: Response<String>?) {
            }

            override fun onStart(what: Int) {
            }

        })
    }


    override fun onClick(p0: View?) {
        when (p0) {
            img_group_buy_order_pay_success_back -> {
                finish()
            }
        }
    }

}