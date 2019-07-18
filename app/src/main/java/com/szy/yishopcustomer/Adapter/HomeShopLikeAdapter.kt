package com.szy.yishopcustomer.Adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.szy.yishopcustomer.Constant.Api
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.GlideUtil
import com.szy.yishopcustomer.ViewModel.HomeShopAndProductBean

/**
 * Created by :TYK
 * Date: 2018/12/1416:07
 * Desc:
 */

class HomeShopLikeAdapter(layoutResId: Int) : BaseQuickAdapter<HomeShopAndProductBean.ShopLikeBean, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder?, item: HomeShopAndProductBean.ShopLikeBean?) {
        helper!!.setText(R.id.tv_product_name, item!!.shop_name)
        helper.setText(R.id.tv_see_count, item.trips)
        Glide.with(helper.itemView.context).load(Api.API_CITY_HOME_MER_IMG_URL+item.shop_image)
                .thumbnail(0.2f).apply(GlideUtil.RadioOptions(5)).into(helper.getView<ImageView>(R.id.img_product_pic))
    }
}