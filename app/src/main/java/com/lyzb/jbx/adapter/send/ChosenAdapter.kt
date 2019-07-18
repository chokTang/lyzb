package com.lyzb.jbx.adapter.send

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.like.utilslib.image.LoadImageUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.inter.IRecycleHolderAnyClilck
import com.lyzb.jbx.model.send.DefaultProductModel
import com.lyzb.jbx.model.send.GoodsModel

/**
 * Created by :TYK
 * Date: 2019/5/8  15:50
 * Desc: 已选择商品适配器
 */

class ChosenAdapter : BaseQuickAdapter<GoodsModel, BaseViewHolder>(R.layout.item_chosen_product) {


    override fun convert(helper: BaseViewHolder?, item: GoodsModel?) {

        item.run {
            LoadImageUtil.loadRoundImage(helper!!.getView<ImageView>(R.id.img_product), this!!.goods_image, 4)
            helper.addOnClickListener(R.id.ll_delete)
            helper.itemView.setOnLongClickListener {
                clickListener?.onItemLongClick(helper, helper.adapterPosition, item!!)!!
            }
        }
    }


    var clickListener: IRecycleHolderAnyClilck? = null

    operator fun invoke(clickListener: IRecycleHolderAnyClilck?) {
        this.clickListener = clickListener
    }
}