package com.lyzb.jbx.adapter.circle

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.like.utilslib.image.LoadImageUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.model.me.CardMallModel
import com.szy.yishopcustomer.Constant.Api
import com.szy.yishopcustomer.Util.Utils

class CircleMallAdapter : BaseQuickAdapter<CardMallModel.ListBean, BaseViewHolder>(R.layout.item_un_me_card_mall) {

    override fun convert(helper: BaseViewHolder?, item: CardMallModel.ListBean?) {
        val imageView = helper!!.getView<ImageView>(R.id.img_un_me_card_good_img)
        LoadImageUtil.loadImage(imageView, Utils.setImgNetUrl(Api.API_HEAD_IMG_URL, item!!.goods_image))
        helper.setText(R.id.tv_un_me_card_good_name, item.goods_name)

        helper.setText(R.id.tv_un_me_card_good_tag_price, "￥" + item.goods_price)
        helper.setText(R.id.tv_un_me_card_good_tag_ingot, "+" + item.max_integral_num + "元宝")
        helper.setText(R.id.tv_un_me_card_good_shop_name, item.shop_name)
        helper.setVisible(R.id.ll_pop, false)
        helper.setVisible(R.id.tv_un_me_card_good_status, false)
        helper.setVisible(R.id.ll_bottom, true)

        helper.addOnClickListener(R.id.img_un_me_card_good_img)
        helper.addOnClickListener(R.id.tv_un_me_card_good_name)
    }

}