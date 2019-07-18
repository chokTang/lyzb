package com.lyzb.jbx.fragment.dynamic

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration
import com.like.longshaolib.base.BaseFragment
import com.like.longshaolib.base.BaseToolbarFragment
import com.lyzb.jbx.R
import com.lyzb.jbx.adapter.dynamic.LikeAdapter
import com.lyzb.jbx.fragment.me.card.CardFragment
import com.lyzb.jbx.model.dynamic.DynamicLikeModel
import com.lyzb.jbx.mvp.presenter.dynamic.LikePresenter
import com.lyzb.jbx.mvp.view.dynamic.ILikeView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.szy.yishopcustomer.Util.App
import kotlinx.android.synthetic.main.frgament_like.*

/**
 * Created by :TYK
 * Date: 2019/3/14  20:11
 * Desc: 点赞页面
 */

class LikeFragment : BaseToolbarFragment<LikePresenter>(), ILikeView {

    companion object {
        const val KEY_DYNAMIC_ID = "dynamicId"
        fun newIncetance(dynamicId:String):LikeFragment{
            val likeFragment = LikeFragment()
            val bundle = Bundle()
            bundle.putString(KEY_DYNAMIC_ID,dynamicId)
            likeFragment.arguments = bundle
            return likeFragment
        }
    }
    var adater: LikeAdapter? = null
    var dynamicId = ""
    override fun getResId(): Any {
        return R.layout.frgament_like
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle!=null){
            dynamicId = bundle.getString(KEY_DYNAMIC_ID)
        }
    }
    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
        setToolbarTitle("点赞列表")
        onBack()
        adater = LikeAdapter()
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_like.layoutManager = linearLayoutManager
        rv_like.addItemDecoration(DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_white_10))
        rv_like.adapter = adater

        refresh_like.setOnRefreshListener {
            mPresenter.getLikeList(true, dynamicId)
        }
        refresh_like.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mPresenter.getLikeList(false, dynamicId)
            }
        })
        mPresenter.getLikeList(true, dynamicId)

        adater!!.setOnItemClickListener { adapter, view, position ->
            var userId = (adater!!.data[position] as DynamicLikeModel.ListBean).userId.toString()
            if (userId != App.getInstance().userId) {
                start(CardFragment.newIntance(2, userId))
            }else{
                start(CardFragment.newIntance(1, userId))
            }
        }
    }

    override fun onInitData(savedInstanceState: Bundle?) {
    }

    var list: MutableList<DynamicLikeModel.ListBean> = arrayListOf()

    override fun onSuccess(isRefresh: Boolean, bean: DynamicLikeModel) {
        if (isRefresh) {//刷新
            refresh_like.finishRefresh()
            list.clear()
            list.addAll(bean.list)
        } else {//加载
            refresh_like.finishLoadMore()
            list.addAll(bean.list)
            if (bean.list.size < bean.pageSize) {//添加完数据
                refresh_like.finishLoadMoreWithNoMoreData()
            }
        }
        if (list.size == 0) {//没有数据
            adater!!.setNewData(list)
            val emptyView = LayoutInflater.from(context).inflate(R.layout.empty_layout, null, false)
            adater!!.emptyView = emptyView
        } else {//有数据
            adater!!.setNewData(list)
        }
    }

    /**
     * 请求点赞数据
     */
    fun onRequestData() {
        mPresenter.getLikeList(true, dynamicId)
    }
}