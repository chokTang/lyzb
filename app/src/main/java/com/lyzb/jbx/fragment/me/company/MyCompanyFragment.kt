package com.lyzb.jbx.fragment.me.company

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.like.longshaolib.base.BaseFragment
import com.lyzb.jbx.R
import com.lyzb.jbx.adapter.me.company.MyCompanyAdapter
import com.lyzb.jbx.model.me.company.CompanyModel
import com.lyzb.jbx.model.me.company.MyCompanyModel
import com.lyzb.jbx.model.me.company.OrganThreeLvModel
import com.lyzb.jbx.model.me.company.OrganTowLvModel
import com.lyzb.jbx.mvp.presenter.me.company.MyCompanyPresenter
import com.lyzb.jbx.mvp.view.me.company.IMyCompanyView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_mycompany.*
import kotlinx.android.synthetic.main.toolbar_statistics.*

/**
 *@author wyx
 *@version
 *@role 我的企业-列表
 *@time 2019 2019/6/13 16:08
 */
class MyCompanyFragment : BaseFragment<MyCompanyPresenter>(), OnRefreshLoadMoreListener,
        IMyCompanyView, View.OnClickListener {

    var mAdapter: MyCompanyAdapter = MyCompanyAdapter(null)

    override fun onInitView(savedInstanceState: Bundle?) {
        statistics_title_tv.text = "我的企业"
        statistics_right_tv.text = "创建"

        statistics_back_iv.setOnClickListener(this)
        statistics_right_tv.setOnClickListener(this)
        mycompany_search_view.setOnClickListener(this)

        val manage = LinearLayoutManager(context)
        manage.orientation = LinearLayoutManager.VERTICAL
        mycompany_recyclerview.layoutManager = manage
        mycompany_recyclerview.adapter = mAdapter

        mycompany_smartrefreshlayout.setOnRefreshLoadMoreListener(this)

        mAdapter.expandAll()

        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            var id = ""
            val data: MultiItemEntity = adapter.data[position] as MultiItemEntity
            when (data.itemType) {
                MyCompanyAdapter.TYPE_COMPANY -> {
                    val b = data as CompanyModel
                    id = b.id
                }
                MyCompanyAdapter.TYPE_TOWLV_ORGAN -> {
                    val b = data as OrganTowLvModel
                    id = b.id
                }
                MyCompanyAdapter.TYPE_THREELV_ORGAN -> {
                    val b = data as OrganThreeLvModel
                    id = b.id
                }
            }
            start(OrganDetailFragment.newIntance(id))
        }
    }

    override fun onInitData(savedInstanceState: Bundle?) {
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        mView.postDelayed({ mPresenter.getCompanyList(true) }, 500)
    }

    override fun getResId(): Any {
        return R.layout.fragment_mycompany
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.statistics_back_iv -> {
                pop()
            }
            R.id.statistics_right_tv -> {
                start(CreateCompanyFragment())
            }
            R.id.mycompany_search_view -> {
                start(CompanySearchFragment())
            }
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPresenter.getCompanyList(true)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getCompanyList(false)
    }

    override fun onRefresh(bean: MyCompanyModel) {
        if (bean.isShowCreate) {
            statistics_right_tv.visibility = View.VISIBLE
            mycompany_search_fl.visibility = View.VISIBLE
        } else {
            statistics_right_tv.visibility = View.GONE
            mycompany_search_fl.visibility = View.GONE
        }
        mycompany_smartrefreshlayout.finishRefresh()
        mAdapter.setNewData(bean.data as List<MultiItemEntity>)
        mAdapter.expandAll()
        mycompany_recyclerview.visibility = View.VISIBLE
        mycompany_nodata.visibility = View.GONE
        if (bean.data.size < 1 && mAdapter.itemCount < 1) {
            onFail("")
            return
        }
    }

    override fun onLoadMore(bean: MyCompanyModel) {
        if (bean.isShowCreate) {
            statistics_right_tv.visibility = View.VISIBLE
            mycompany_search_fl.visibility = View.VISIBLE
        } else {
            statistics_right_tv.visibility = View.GONE
            mycompany_search_fl.visibility = View.GONE
        }
        mAdapter.addData(bean.data)
        mAdapter.expandAll()
        mycompany_recyclerview.visibility = View.VISIBLE
        mycompany_nodata.visibility = View.GONE
        mycompany_smartrefreshlayout.finishLoadMore()
    }

    override fun onFail(msg: String) {
        mycompany_smartrefreshlayout.finishLoadMore()
        mycompany_smartrefreshlayout.finishRefresh()
        showToast(msg)
        if (mAdapter.itemCount < 1) {
            mycompany_nodata.visibility = View.VISIBLE
            mycompany_recyclerview.visibility = View.GONE
        } else {
            mycompany_nodata.visibility = View.GONE
            mycompany_recyclerview.visibility = View.VISIBLE
        }
    }
}