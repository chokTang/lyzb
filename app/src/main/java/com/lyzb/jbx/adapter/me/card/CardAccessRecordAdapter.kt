package com.lyzb.jbx.adapter.me.card

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.like.longshaolib.adapter.BaseRecyleAdapter
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener
import com.like.longshaolib.base.BaseFragment
import com.like.utilslib.image.LoadImageUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.adapter.home.search.SearchAccountDynamicItemAdapter
import com.lyzb.jbx.adapter.home.search.SearchAccountDynamicItemAdapterAccress
import com.lyzb.jbx.fragment.dynamic.DynamicDetailFragment
import com.lyzb.jbx.model.common.DynamicItemModel
import com.lyzb.jbx.model.me.AccessModel
import com.lyzb.jbx.model.me.FansModel
import com.szy.yishopcustomer.Util.App
import com.xiao.nicevideoplayer.NiceVideoPlayerManager
import java.util.ArrayList

/**
 * Created by :TYK
 * Date: 2019/4/18  14:25
 * Desc: 名片中的访问记录适配器
 */

class CardAccessRecordAdapter : BaseQuickAdapter<AccessModel.ListBean, CardAccessRecordAdapter.DynamicHolder>(R.layout.recycle_account_dynamic) {
    private var fragment: BaseFragment<*>? = null
    fun setFragment(fragment: BaseFragment<*>) {
        this.fragment = fragment
    }

    override fun convert(helper: DynamicHolder?, item: AccessModel.ListBean?) {
        item.run {
            if (this!!.gsTopicVo != null && this.gsTopicVo.total > 2) {
                helper!!.itemAdapter.update(this.gsTopicVo.list.subList(0, 2))
                helper.setVisible(R.id.tv_dynamic_number, true)
                helper.setText(R.id.tv_dynamic_number, String.format("查看全部的%d条动态 >", this.gsTopicVo.total))
            } else {
                helper!!.itemAdapter.update(ArrayList())
                helper.setVisible(R.id.tv_dynamic_number, false)
            }
            headerInit(helper, this)
        }
        helper!!.addOnClickListener(R.id.recycle_account_dynamic)
        helper.addOnClickListener(R.id.tv_dynamic_number)
    }


    inner class DynamicHolder(view: View?) : BaseViewHolder(view) {

        var recyclerView: RecyclerView = view!!.findViewById(R.id.recycle_account_dynamic)
        var itemAdapter: SearchAccountDynamicItemAdapterAccress = SearchAccountDynamicItemAdapterAccress(view!!.context, null)

        init {
            recyclerView.adapter = itemAdapter
            recyclerView.addItemDecoration(DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_white_10))
            itemAdapter.setLayoutManager(recyclerView)
            recyclerView.setRecyclerListener { holder ->
                if (holder is SearchAccountDynamicItemAdapter.VideoHolder) {
                    val niceVideoPlayer = holder.videoPlayer
                    if (niceVideoPlayer === NiceVideoPlayerManager.instance().currentNiceVideoPlayer) {
                        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
                    }
                }
            }

            recyclerView.addOnItemTouchListener(object : OnRecycleItemClickListener() {
                override fun onItemClick(adapter: BaseRecyleAdapter<*>, view: View, position: Int) {
                    view.tag = ""
                    view.postDelayed({
                        val tag = view.tag
                        if (tag == null || TextUtils.isEmpty(tag.toString())) {
                            if (fragment != null) {
                                fragment!!.start(DynamicDetailFragment.newIntance(itemAdapter.getPositionModel(position).id))
                            }
                        }
                    }, 50)
                }
            })
        }
    }

    private fun headerInit(holder:BaseViewHolder, model: AccessModel.ListBean) {
        holder.setGone(R.id.tv_circle_dynamic, false)

        LoadImageUtil.loadRoundSizeImage(holder.getView(R.id.img_header), model.headimg, 50)
        holder.setText(R.id.tv_header_name, model.gsName)
        holder.setVisible(R.id.img_vip, model.userVipAction.size > 0)
        holder.setText(R.id.tv_header_company, model.shopName)

        holder.setGone(R.id.tv_follow, model.relationNum > 0)
        holder.setGone(R.id.tv_no_follow, model.relationNum == 0)
//        if (model.id == App.getInstance().userId) {
//            holder.setVisible(R.id.tv_no_follow, false)
//            holder.setVisible(R.id.tv_follow, false)
//        } else {
//            holder.setVisible(R.id.tv_no_follow, true)
//            val tv_no_follow = holder.getView<TextView>(R.id.tv_no_follow)
//            if (model.relationNum > 0) {//已关注--在线沟通
//                holder.setText(R.id.tv_no_follow, "在线沟通")
//                tv_no_follow.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
//            } else {
//                holder.setText(R.id.tv_no_follow, "关注")
//                tv_no_follow.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(holder.itemView.context, R.drawable.union_add_blue), null, null, null)
//            }
//        }

        holder.addOnClickListener(R.id.tv_follow)
        holder.addOnClickListener(R.id.tv_no_follow)
        holder.addOnClickListener(R.id.img_header)
    }
}