package com.lyzb.jbx.adapter.send

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.lyzb.jbx.R
import com.lyzb.jbx.fragment.send.UnionSendTWFragment.Companion.ADD_PIC
import com.lyzb.jbx.inter.IRecycleHolderAnyClilck
import com.lyzb.jbx.model.params.FileBody
import com.szy.yishopcustomer.Util.LogUtils

/**
 * Created by :TYK
 * Date: 2019/3/4  19:25
 * Desc: 发送图片适配器
 */

class PicAdapter : BaseQuickAdapter<FileBody, BaseViewHolder>(R.layout.item_send_pic_add) {

    companion object {
        const val ADD = 0//添加图标  显示添加图标
        const val NO_ADD = 1  //图片
    }
    init {
        multiTypeDelegate = object : MultiTypeDelegate<FileBody>() {
            override fun getItemType(t: FileBody?): Int {
                var type = 1
                type = if (t!!.file == ADD_PIC) {
                    ADD
                } else {
                    NO_ADD
                }
                return type
            }
        }
        multiTypeDelegate.registerItemType(ADD, R.layout.item_send_pic_add)
                .registerItemType(NO_ADD, R.layout.item_send_pic)
    }
    override fun convert(helper: BaseViewHolder?, item: FileBody?) {
        when (helper!!.itemViewType) {
            ADD -> {//添加图标
                LogUtils.e("当前有添加图标")
            }
            NO_ADD -> {//图片
                Glide.with(helper.itemView.context).load(item!!.file)
                        .thumbnail(0.2f).into(helper.getView<ImageView>(R.id.pic))
                helper.addOnClickListener(R.id.img_delete)
                helper.itemView.setOnLongClickListener {
                    clickListener?.onItemLongClick(helper, helper.adapterPosition, item!!)!!
                }
            }

        }

    }



    var clickListener: IRecycleHolderAnyClilck? = null

    operator fun invoke(clickListener: IRecycleHolderAnyClilck?) {
        this.clickListener = clickListener
    }
}