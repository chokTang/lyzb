package com.lyzb.jbx.adapter.dynamic

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.like.utilslib.date.DateStyle
import com.like.utilslib.date.DateUtil
import com.like.utilslib.image.LoadImageUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.inter.IRecycleAnyClickListener
import com.lyzb.jbx.model.dynamic.DynamicCommentModel
import com.lyzb.jbx.util.AppCommonUtil
import com.lyzb.jbx.util.ImageUtil
import com.szy.yishopcustomer.Util.App
import kotlinx.android.synthetic.main.item_comment_dynamic.view.*

/**
 * Created by :TYK
 * Date: 2019/3/15  11:17
 * Desc:  动态详情 评论适配器
 */
class CommentAdapter : BaseQuickAdapter<DynamicCommentModel.ListBean, CommentAdapter.CommentViewHolder>(R.layout.item_comment_dynamic) {
    var replyAdapter: ReplyAdapter? = null
    var linearLayoutManager: LinearLayoutManager? = null
    var layoutManager: GridLayoutManager? = null
    var adapter: CommentPicAdapter? = null
    var mListener: IRecycleAnyClickListener? = null

    override fun convert(helper: CommentViewHolder?, item: DynamicCommentModel.ListBean?) {
        LoadImageUtil.loadRoundImage(helper!!.getView(R.id.img_avatar), item!!.headimg, 4)
        helper.setText(R.id.tv_name, item.userName)
        helper.setText(R.id.tv_shop_name, item.companyInfo)

        helper.setVisible(R.id.img_delete, TextUtils.equals(item.userId, App.getInstance().userId))
        if (item.userActionVos.size > 0) {
            helper.getView<ImageView>(R.id.img_vip).visibility = View.VISIBLE
        } else {
            helper.getView<ImageView>(R.id.img_vip).visibility = View.GONE
        }

        if (TextUtils.isEmpty(item.content)){
            helper.getView<TextView>(R.id.tv_content).visibility = View.GONE
        }else{
            helper.getView<TextView>(R.id.tv_content).visibility = View.VISIBLE
        }
        AppCommonUtil.autoLinkText(helper.getView(R.id.tv_content), item.content, "")

        helper.setText(R.id.tv_time, DateUtil.StringToString(item.createDate, DateStyle.MM_DD_HH_MM))

        if (item.giveNum == 0) {//没人赞
            helper.setText(R.id.tv_like, "赞")
        } else {
            helper.setText(R.id.tv_like, item.giveNum.toString())
        }
        //是否点赞：大于0表示点赞；小于等于0表示没有
        helper.getView<TextView>(R.id.tv_like).isSelected = item.giveLike > 0
        helper.addOnClickListener(R.id.tv_like)
        helper.addOnClickListener(R.id.tv_reply)
        helper.addOnClickListener(R.id.img_delete)

        replyAdapter = ReplyAdapter()
        adapter = CommentPicAdapter()
        layoutManager = GridLayoutManager(mContext, 3)
        linearLayoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        helper.getView<RecyclerView>(R.id.rv_pic).layoutManager = layoutManager
        helper.getView<RecyclerView>(R.id.rv_pic).adapter = adapter
        helper.getView<RecyclerView>(R.id.rv_reply).layoutManager = linearLayoutManager
        helper.getView<RecyclerView>(R.id.rv_reply).adapter = replyAdapter

        adapter!!.setOnItemClickListener { _, _, position ->
            val list: MutableList<String> = arrayListOf()
            for (i in 0 until item.fileVoList.size) {
                list.add(item.fileVoList[i].file)
            }
            ImageUtil.statPhotoViewActivity(helper.itemView.context, position, list)
        }

        adapter!!.setNewData(item.fileVoList)
        if (item.fileVoList.isEmpty()||item.fileVoList.size==0){
            helper.getView<RecyclerView>(R.id.rv_pic).visibility = View.GONE
        }else{
            helper.getView<RecyclerView>(R.id.rv_pic).visibility = View.VISIBLE
        }

        replyAdapter!!.setNewData(item.chiledrenList)
        if (item.chiledrenList.size == 0) {
            helper.getView<RecyclerView>(R.id.rv_reply).visibility = View.GONE
        } else {
            helper.getView<RecyclerView>(R.id.rv_reply).visibility = View.VISIBLE
        }

        replyAdapter!!.setOnItemChildClickListener { adpter, view, position ->
            if (mListener != null) {
                mListener!!.onItemClick(view, position, adpter!!.data[position])
            }
        }
    }

    class CommentViewHolder(view: View) : BaseViewHolder(view) {
        init {
            view.rv_pic.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect?, itemPosition: Int, parent: RecyclerView?) {
                    outRect!!.set(0, 0, 10, 10)
                }
            }, 0)
        }
    }

    public fun setListener(lister: IRecycleAnyClickListener) {
        mListener = lister
    }
}