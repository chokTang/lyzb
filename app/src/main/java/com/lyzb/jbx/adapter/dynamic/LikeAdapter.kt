package com.lyzb.jbx.adapter.dynamic

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.like.utilslib.image.LoadImageUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.model.dynamic.DynamicLikeModel

/**
 * Created by :TYK
 * Date: 2019/3/15  11:27
 * Desc: 动态详情  点赞适配器
 */

class LikeAdapter : BaseQuickAdapter<DynamicLikeModel.ListBean, BaseViewHolder>(R.layout.item_like_dynamic) {

    override fun convert(helper: BaseViewHolder?, item: DynamicLikeModel.ListBean?) {
        helper?.let {
            LoadImageUtil.loadRoundImage(it.getView(R.id.img_avatar), item!!.headimg,4)
            it.setText(R.id.tv_name, item.userName)
            it.setText(R.id.tv_shop_name, item.shopName)
            if (item.userActionVos.size > 0) {//vip类型；0：非缴费用户
                it.getView<ImageView>(R.id.img_vip).visibility = View.VISIBLE
            } else {
                it.getView<ImageView>(R.id.img_vip).visibility = View.GONE
            }
            it.addOnClickListener(R.id.tv_like)
        }
    }
}