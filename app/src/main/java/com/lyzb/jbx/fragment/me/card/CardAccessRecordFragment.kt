package com.lyzb.jbx.fragment.me.card

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration
import com.like.longshaolib.base.BaseToolbarFragment
import com.like.longshaolib.dialog.fragment.AlertDialogFragment
import com.lyzb.jbx.R
import com.lyzb.jbx.adapter.home.search.SearchAccountDynamicItemAdapter
import com.lyzb.jbx.adapter.me.card.CardAccessRecordAdapter
import com.lyzb.jbx.model.dynamic.DynamicModel
import com.lyzb.jbx.model.me.AccessModel
import com.lyzb.jbx.mvp.presenter.me.card.CardAccessRecordPresenter
import com.lyzb.jbx.mvp.view.me.ICardAccessRecordView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.szy.yishopcustomer.Activity.LoginActivity
import com.szy.yishopcustomer.Util.App
import com.xiao.nicevideoplayer.NiceVideoPlayerManager
import kotlinx.android.synthetic.main.empty_layout_card_mall.view.*
import kotlinx.android.synthetic.main.fragment_card_record.*

/**
 * Created by :TYK
 * Date: 2019/4/18  13:55
 * Desc: 个人名片中的  浏览记录(访问记录)
 */
class CardAccessRecordFragment : BaseToolbarFragment<CardAccessRecordPresenter>()
        , ICardAccessRecordView, OnRefreshLoadMoreListener {

    var userId = ""
    var num = 0
    var mTotal = 0
    var recordAdapter: CardAccessRecordAdapter? = null

    companion object {
        const val KEY_USER_ID = "key_user_id"
        const val KEY_NUM = "key_num"
        fun newInstance(userId:String,num:Int):CardAccessRecordFragment {
            val fragment = CardAccessRecordFragment()
            val bundle = Bundle()
            bundle.putString(KEY_USER_ID,userId)
            bundle.putInt(KEY_NUM,num)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle!=null){
            userId = bundle.getString(KEY_USER_ID)
            num = bundle.getInt(KEY_NUM)
        }
    }

    override fun getResId(): Any {
        return R.layout.fragment_card_record
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
        onBack()
        setToolbarTitle("访问记录")

        recordAdapter = CardAccessRecordAdapter()
        val linearLayoutManager = LinearLayoutManager(activity)
        rv_access.layoutManager = linearLayoutManager
        rv_access.adapter = recordAdapter

        //视频列表的 视频播放器释放
        rv_access.setRecyclerListener { holder ->
            if (holder is SearchAccountDynamicItemAdapter.VideoHolder) {
                val niceVideoPlayer = holder.videoPlayer
                if (niceVideoPlayer === NiceVideoPlayerManager.instance().currentNiceVideoPlayer) {
                    NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
                }
            }
        }

        refresh_layout_access.setOnRefreshLoadMoreListener(this)
        recordAdapter!!.setOnItemChildClickListener { adapter, view, position ->
            val model = adapter.getItem(position) as AccessModel.ListBean
            when (view.id) {
                R.id.tv_follow//取消关注
                -> if (App.getInstance().isLogin) {
                    AlertDialogFragment.newIntance()
                            .setSureBtn { mPresenter.onDynamciFollowUser(model.userId, 0, position) }
                            .setCancleBtn(null)
                            .setContent("确定不再关注该用户？")
                            .show(fragmentManager!!, "follow_tag")
                } else {
                    startActivity(Intent(activity, LoginActivity::class.java))
                }
                R.id.tv_no_follow->{//添加关注
                    if (App.getInstance().isLogin) {
                        mPresenter.onDynamciFollowUser(model.userId, 1, position)
                    } else {
                        startActivity(Intent(activity, LoginActivity::class.java))
                    }
                }
                R.id.tv_dynamic_number -> start(CardFragment.myIntance(2, 1, model.userId))
                //点击头像
                R.id.img_header -> start(CardFragment.newIntance(2, model.userId))
            }
        }

        rv_access.addItemDecoration(DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_window_10))
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        mPresenter.getAccessRecordList(true,userId)
    }

    var list: MutableList<AccessModel.ListBean> = arrayListOf()

    @SuppressLint("SetTextI18n")
    override fun getRecordList(isRefesh:Boolean, total:Int, list: MutableList<AccessModel.ListBean>) {
        mTotal = total
        tv_access_number.text = mTotal.toString()+"人浏览了${num}次"
        if (isRefesh) {//刷新
            refresh_layout_access.finishRefresh()
            this.list.clear()
            this.list = list
        } else {//加载
            refresh_layout_access.finishLoadMore()
            this.list.addAll(list)
            if (list.size < 10) {//添加完数据
                refresh_layout_access.finishLoadMoreWithNoMoreData()
            }
        }
        recordAdapter!!.setNewData(this.list)
        if (recordAdapter!!.itemCount == 0) {
            inc_focus_empty.visibility = View.VISIBLE
            inc_focus_empty.empty_tv.text = "你暂时还没人浏览哟~"
        } else {
            inc_focus_empty.visibility = View.GONE
        }
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getAccessRecordList(false,userId)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPresenter.getAccessRecordList(true,userId)
    }

    override fun onFinshRefresh(isfresh: Boolean) {
        if (isfresh) {
            refresh_layout_access.finishRefresh()
        } else {
            refresh_layout_access.finishLoadMore()
        }
    }

    override fun onDynamicList(isfresh: Boolean, list: MutableList<DynamicModel>?) {
        //不要该方法
    }

    override fun onZanResult(position: Int) {
    }

    override fun onFollowItemResult(position: Int) {
        val model = recordAdapter!!.getItem(position) as AccessModel.ListBean
        model.relationNum = if (model.relationNum == 0) 1 else 0
        recordAdapter!!.setData(position, model)
    }


}