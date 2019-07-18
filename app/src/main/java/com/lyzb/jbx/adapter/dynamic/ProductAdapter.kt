package com.lyzb.jbx.adapter.dynamic

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.like.utilslib.image.LoadImageUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.model.send.GoodsList
import com.szy.yishopcustomer.Constant.Api
import com.szy.yishopcustomer.Util.Utils

/**
 * Created by :TYK
 * Date: 2019/5/9  15:13
 * Desc:  动态详情商品适配器
 */
class ProductAdapter : BaseQuickAdapter<GoodsList.DataBean, BaseViewHolder>(R.layout.item_dynamic_product) {


    override fun convert(helper: BaseViewHolder?, item: GoodsList.DataBean?) {

        item.run {
            LoadImageUtil.loadRoundImage(helper!!.getView(R.id.img_product), Utils.setImgNetUrl(Api.API_HEAD_IMG_URL, this!!.goods_image), 4)
            helper.setText(R.id.tv_name, goods_name)
            helper.setText(R.id.tv_price, "¥$goods_price")
            helper.setText(R.id.tv_acer, "+" + max_integral_num + "元宝")
            helper.setText(R.id.tv_shop_name, shop_name)
            helper.addOnClickListener(R.id.parent)
        }
    }
}