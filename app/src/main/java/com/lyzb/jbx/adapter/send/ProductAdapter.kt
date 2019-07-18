package com.lyzb.jbx.adapter.send

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.like.utilslib.image.LoadImageUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.model.send.DefaultProductModel
import com.lyzb.jbx.model.send.GoodsModel

/**
 * Created by :TYK
 * Date: 2019/5/8  14:56
 * Desc: 商品显示适配器
 */

class ProductAdapter : BaseQuickAdapter<GoodsModel, BaseViewHolder>(R.layout.item_default_product) {
    override fun convert(helper: BaseViewHolder?, item: GoodsModel?) {
        item.run {
            LoadImageUtil.loadRoundImage(helper!!.getView<ImageView>(R.id.img_product), this!!.goods_image, 4)
            helper.setText(R.id.tv_name, goods_name)
            helper.setText(R.id.tv_price, "¥$goods_price")
            if (max_integral_num != "0") {
                helper.setGone(R.id.tv_acer, true)
                helper.setText(R.id.tv_acer, "+${max_integral_num}元宝")
            } else {
                helper.setGone(R.id.tv_acer, false)
            }
            if (isSelected) {
                helper.setText(R.id.tv_choice, "已选择")
            } else {
                helper.setText(R.id.tv_choice, "选择")
            }
            helper.getView<TextView>(R.id.tv_choice).isSelected = !isSelected
            helper.setText(R.id.tv_shop_name, shop_name)
            helper.addOnClickListener(R.id.tv_choice)
        }
    }
}