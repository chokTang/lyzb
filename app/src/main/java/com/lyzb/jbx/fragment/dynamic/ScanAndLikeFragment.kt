package com.lyzb.jbx.fragment.dynamic

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.like.longshaolib.adapter.BaseRecyleAdapter
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener
import com.like.longshaolib.base.BaseFragment
import com.lyzb.jbx.R
import com.lyzb.jbx.adapter.dynamic.DynamicScanAdapter
import com.lyzb.jbx.adapter.dynamic.LikeAdapter
import com.lyzb.jbx.fragment.me.card.CardFragment
import com.lyzb.jbx.model.dynamic.DynamicLikeModel
import com.lyzb.jbx.model.dynamic.DynamicScanModel
import com.lyzb.jbx.mvp.presenter.dialog.ScanAndLikeDPresenter
import com.lyzb.jbx.mvp.view.dialog.IScanAndLikeDView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.szy.yishopcustomer.Util.App
import kotlinx.android.synthetic.main.fragment_scan_like.*

/**
 * 动态详情浏览数和点赞数页面
 */
class ScanAndLikeFragment : BaseFragment<ScanAndLikeDPresenter>(), View.OnClickListener, IScanAndLikeDView, OnRefreshLoadMoreListener {

    var dynamicId = ""
    var mType = 0 //0表示点赞数 1表示浏览量
    var mScancleNumber = 0
    var likNumber = 0

    var list: MutableList<DynamicLikeModel.ListBean> = arrayListOf()
    var adater: LikeAdapter? = null

    //浏览列表
    var scanAdapter: DynamicScanAdapter? = null

    companion object {
        const val KEY_DYNAMIC_ID = "params_id"
        const val PARAMS_TYPE = "params_type"
        const val PARAMS_SCAN_NUMBER = "params_scan_number"
        const val PARAMS_LIKE_NUMBER = "params_like_number"

        fun newIncetance(dynamicId: String, type: Int, scanNumber: Int, likeNumber: Int): ScanAndLikeFragment {
            val likeFragment = ScanAndLikeFragment()
            val bundle = Bundle()
            bundle.putString(KEY_DYNAMIC_ID, dynamicId)
            bundle.putInt(PARAMS_TYPE, type)
            bundle.putInt(PARAMS_SCAN_NUMBER, scanNumber)
            bundle.putInt(PARAMS_LIKE_NUMBER, likeNumber)
            likeFragment.arguments = bundle
            return likeFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            dynamicId = bundle.getString(KEY_DYNAMIC_ID)
            mType = bundle.getInt(PARAMS_TYPE)
            mScancleNumber = bundle.getInt(PARAMS_SCAN_NUMBER, 0)
            likNumber = bundle.getInt(PARAMS_LIKE_NUMBER, 0)
        }
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        btnCancel.setOnClickListener(this)
        btnBack.setOnClickListener(this)
        refresh_scan_like.setOnRefreshLoadMoreListener(this)
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        adater = LikeAdapter()
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recycle_like.layoutManager = linearLayoutManager
        recycle_like.addItemDecoration(DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_white_4))
        recycle_like.adapter = adater
        adater!!.setOnItemClickListener { adapter, view, position ->
            var userId = (adater!!.data[position] as DynamicLikeModel.ListBean).userId.toString()
            if (userId != App.getInstance().userId) {
                start(CardFragment.newIntance(2, userId))
            } else {
                start(CardFragment.newIntance(1, userId))
            }
        }

        scanAdapter = DynamicScanAdapter(context, null)
        scanAdapter!!.setLayoutManager(recycle_scan)
        recycle_scan.addItemDecoration(DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_white_4))
        recycle_scan.adapter = scanAdapter
        recycle_scan.addOnItemTouchListener(object : OnRecycleItemClickListener() {
            override fun onItemClick(adapter: BaseRecyleAdapter<*>?, view: View?, position: Int) {
                var userId = scanAdapter!!.getPositionModel(position).createMan
                if (userId != App.getInstance().userId) {
                    start(CardFragment.newIntance(2, userId))
                } else {
                    start(CardFragment.newIntance(1, userId))
                }
            }
        })

        tablayout_scan.addTab("点赞($likNumber)")
        tablayout_scan.addTab("浏览量($mScancleNumber)")
        tablayout_scan.tabLayout.getTabAt(mType)!!.select()
        showTab(mType)

        tablayout_scan.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                showTab(tab!!.position)
                onQuestApi(tab!!.position, true)
            }
        })

        onQuestApi(mType, true)
    }

    override fun getResId(): Any {
        return R.layout.fragment_scan_like
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnBack,
            R.id.btnCancel -> {
                activity!!.finish()
            }
        }
    }

    override fun onSuccess(isRefresh: Boolean, bean: DynamicLikeModel?) {
        if (isRefresh) {//刷新
            refresh_scan_like.finishRefresh()
            list.clear()
            list.addAll(bean!!.list)
        } else {//加载
            refresh_scan_like.finishLoadMore()
            list.addAll(bean!!.list)
            if (bean!!.list.size < bean!!.pageSize) {//添加完数据
                refresh_scan_like.finishLoadMoreWithNoMoreData()
            }
        }
        if (list.size == 0) {//没有数据
            empty_view.visibility = View.VISIBLE
        } else {//有数据
            empty_view.visibility = View.GONE
            adater!!.setNewData(list)
        }
    }

    override fun onScanResultList(isRefresh: Boolean, total: Int, list: MutableList<DynamicScanModel>?) {
        if (isRefresh) {//刷新
            refresh_scan_like.finishRefresh()
            if (list!!.size < 10) {
                refresh_scan_like.finishLoadMoreWithNoMoreData()
            }
            scanAdapter!!.update(list)
        } else {//加载
            if (list!!.size < 10) {
                refresh_scan_like.finishLoadMoreWithNoMoreData()
            } else {
                refresh_scan_like.finishLoadMore()
            }
            scanAdapter!!.addAll(list)
        }
        if (scanAdapter!!.itemCount == 0) {//没有数据
            empty_view.visibility = View.VISIBLE
        } else {//有数据
            empty_view.visibility = View.GONE
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        onQuestApi(mType, true)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        onQuestApi(mType, false)
    }

    fun onQuestApi(type: Int, isRefresh: Boolean) {
        if (type == 0) {
            mPresenter.getLikeList(isRefresh, dynamicId)
        } else {
            mPresenter.getScanList(isRefresh, dynamicId)
        }
    }

    fun showTab(type: Int) {
        if (type == 0) {//点赞
            recycle_like.visibility = View.VISIBLE
            recycle_scan.visibility = View.GONE
            empty_view.visibility = View.GONE
        } else {//浏览
            recycle_like.visibility = View.GONE
            recycle_scan.visibility = View.VISIBLE
            empty_view.visibility = View.GONE
        }
    }
}