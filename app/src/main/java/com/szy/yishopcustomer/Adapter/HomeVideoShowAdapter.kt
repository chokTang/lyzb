package com.szy.yishopcustomer.Adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lyzb.jbx.R
import com.szy.yishopcustomer.Constant.Api
import com.szy.yishopcustomer.Util.GlideUtil
import com.szy.yishopcustomer.Util.Utils
import com.szy.yishopcustomer.ViewModel.HomeShopAndProductBean

/**
 * Created by :TYK
 * Date: 2019/1/14  15:01
 * Desc:
 */

class HomeVideoShowAdapter(layoutResId: Int) : BaseQuickAdapter<HomeShopAndProductBean.LiveBean, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder?, item: HomeShopAndProductBean.LiveBean?) {
        Glide.with(helper!!.itemView.context).load(Utils.setImgNetUrl(Api.API_CITY_HOME_MER_IMG_URL, item!!.live_img))
                .apply(GlideUtil.EmptyOptions()).into(helper.getView(R.id.img_video))
        helper.setText(R.id.tv_video_name, item.live_title)
        helper.setText(R.id.tv_video_author, item.nick_name)
    }
}