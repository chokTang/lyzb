package com.szy.yishopcustomer.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lyzb.jbx.R
import com.szy.yishopcustomer.Adapter.CouponOnlineAdapter
import com.szy.yishopcustomer.Constant.Api
import com.szy.yishopcustomer.Constant.HttpWhat
import com.szy.yishopcustomer.Util.RequestAddHead
import com.szy.yishopcustomer.Util.Utils
import com.szy.yishopcustomer.Util.json.GsonUtils
import com.szy.yishopcustomer.ViewModel.CouponBean
import com.yolanda.nohttp.NoHttp
import com.yolanda.nohttp.RequestMethod
import com.yolanda.nohttp.rest.OnResponseListener
import com.yolanda.nohttp.rest.Response
import kotlinx.android.synthetic.main.fragment_coupon_online.*

/**
 * Created by :TYK
 * Date: 2019/2/26  13:45
 * Desc:优惠券线上
 */

class CouponOnlineFragment : Fragment() {

    var shopId = ""
    var adpter: CouponOnlineAdapter? = null
    var startPage = 1
    var pageSize = 10
    var list: MutableList<CouponBean.ListBean> = ArrayList()
    var responslist: MutableList<CouponBean.ListBean> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(activity).inflate(R.layout.fragment_coupon_online, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        refreshView()
    }

    /**
     * 初始化
     */
    fun initView() {
        adpter = CouponOnlineAdapter(R.layout.item_coupon_online)
        val manager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_online.layoutManager = manager
        rv_online.adapter = adpter
//        getListCoupon(true,shopId)
    }

    /**
     * 获取优惠券列表
     */
    fun getListCoupon(isRefresh: Boolean, shopId: String) {

        val requestQueue = NoHttp.newRequestQueue()
        val request = NoHttp.createStringRequest(Api.API_COUPON_LIST_ONLINE, RequestMethod.POST)
        RequestAddHead.addNoHttpHead(request, activity, Api.API_COUPON_LIST_ONLINE, "POST")

        val jsonobject = com.alibaba.fastjson.JSONObject()
        if (isRefresh) {
            startPage = 1
            jsonobject["pageNum"] = startPage
        } else {
            startPage += 1
            jsonobject["pageNum"] = startPage
        }
        jsonobject["pageSize"] = pageSize
        jsonobject["shopId"] = shopId

        request.setDefineRequestBodyForJson(jsonobject.toJSONString())
        requestQueue.add(HttpWhat.HTTP_COUPON_LIST_ONLINE.value, request, object : OnResponseListener<String> {
            override fun onFinish(what: Int) {
                Utils.makeToast(activity, R.string.data_error)
            }

            override fun onSucceed(what: Int, response: Response<String>?) {

                if (response!!.responseCode() == 200) {
                    responslist = GsonUtils.toList(response.get(), CouponBean.ListBean::class.java) as MutableList<CouponBean.ListBean>
                    if (isRefresh) {//刷新
                        refresh_layout.finishRefresh()
                        list.clear()
                        list.addAll(responslist)
                    } else {//加载
                        refresh_layout.finishLoadMore()
                        if (responslist.isNotEmpty()) {
                            list.addAll(responslist)
                        }
                    }
                    adpter!!.setNewData(list)
                }

            }

            override fun onFailed(what: Int, response: Response<String>?) {
            }

            override fun onStart(what: Int) {
            }


        })
    }


    /**
     * 刷新加载
     */
    fun refreshView() {

        refresh_layout.setOnRefreshListener {
            //        getListCoupon(true,shopId)
        }
        refresh_layout.setOnLoadMoreListener {
            //                    getListCoupon(false,shopId)
        }
    }
}