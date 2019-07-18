package com.lyzb.jbx.fragment.vip

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.like.longshaolib.base.BaseFragment
import com.lyzb.jbx.R
import com.lyzb.jbx.adapter.vip.VipAdapter
import com.lyzb.jbx.model.vip.VipBean
import com.lyzb.jbx.mvp.presenter.vip.VipPresenter
import com.lyzb.jbx.mvp.view.vip.IVipView
import kotlinx.android.synthetic.main.activity_vip_service.*

/**
 * Created by :TYK
 * Date: 2019/3/2  13:30
 * Desc:Vip服务列表页面
 */

class VipFragment : BaseFragment<VipPresenter>(),IVipView {

    override fun getResId(): Any {
        return R.layout.activity_vip_service
    }

    var adapter: VipAdapter? = null

    override fun onInitView(savedInstanceState: Bundle?) {
        adapter = VipAdapter()
        val linearLayoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        rv_vip_service.layoutManager = linearLayoutManager
        rv_vip_service.adapter = adapter
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        mPresenter.getVipDataList()
    }


    /**
     * vip数据处理
     */
    override fun getVipData(list: List<VipBean>) {

    }







}