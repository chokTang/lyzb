package com.szy.yishopcustomer.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.JsonObject
import com.lyzb.jbx.R
import com.szy.yishopcustomer.Adapter.CouponOfflineAdapter
import com.szy.yishopcustomer.Constant.Api
import com.szy.yishopcustomer.Constant.HttpWhat
import com.szy.yishopcustomer.Util.LogUtils
import com.szy.yishopcustomer.Util.RequestAddHead
import com.szy.yishopcustomer.Util.Utils
import com.szy.yishopcustomer.Util.json.GSONUtil
import com.szy.yishopcustomer.Util.json.GsonUtils
import com.szy.yishopcustomer.ViewModel.CouponBean
import com.szy.yishopcustomer.ViewModel.HxqBean
import com.yolanda.nohttp.NoHttp
import com.yolanda.nohttp.RequestMethod
import com.yolanda.nohttp.rest.OnResponseListener
import com.yolanda.nohttp.rest.Response
import kotlinx.android.synthetic.main.fragment_coupon_offline.*
import org.json.JSONObject

/**
 * Created by :TYK
 * Date: 2019/2/26  13:45
 * Desc:优惠券线下
 */

class CouponOfflineFragment: Fragment(){

    var adpter: CouponOfflineAdapter? = null
    var startPage = 1
    var pageSize = 10
    var list: MutableList<HxqBean.ListBean> = ArrayList()
    var responslist: MutableList<HxqBean.ListBean> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(activity).inflate(R.layout.fragment_coupon_offline,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setListener()
        refreshView()
    }


    /**
     * 初始化
     */
    fun initView() {
        adpter = CouponOfflineAdapter(R.layout.item_coupon_offline)
        val manager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_offline.layoutManager = manager
        rv_offline.adapter = adpter
        getListCoupon(true)
    }

    /**
     * 设置监听
     */
    fun setListener(){
        adpter!!.setOnItemChildClickListener { adapter, view, position ->
            
        }
    }

    /**
     * 获取核销券列表
     */
    fun getListCoupon(isRefresh: Boolean) {

        val requestQueue = NoHttp.newRequestQueue()
        val request = NoHttp.createStringRequest(Api.API_COUPON_LIST_OFFLINE, RequestMethod.GET)
        RequestAddHead.addNoHttpHead(request, activity, Api.API_COUPON_LIST_OFFLINE, "GET")
        if (isRefresh) {
            startPage = 1
            request.add("pageNum",startPage)
        } else {
            startPage += 1
            request.add("pageNum",startPage)
        }
        request.add("pageSize",pageSize)
        requestQueue.add(HttpWhat.HTTP_COUPON_LIST_OFFLINE.value, request, object : OnResponseListener<String> {
            override fun onFinish(what: Int) {
            }

            override fun onSucceed(what: Int, response: Response<String>?) {

                LogUtils.e("------>>>>"+response!!.get())
                    if (!GSONUtil.isGoodGson(response.get(),HxqBean.ListBean::class.java)){
                        LogUtils.e("数据解析错误$response")
                        return
                    }
                    val jsonObject = JSONObject(response.get())
                    responslist = GsonUtils.toList(jsonObject.getString("list"), HxqBean.ListBean::class.java) as MutableList<HxqBean.ListBean>
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
                    getListCoupon(true)
        }
        refresh_layout.setOnLoadMoreListener {
                                getListCoupon(false)
        }
    }
}