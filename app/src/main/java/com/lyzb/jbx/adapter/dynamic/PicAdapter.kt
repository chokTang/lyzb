package com.lyzb.jbx.adapter.dynamic

import android.view.ViewGroup
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.like.utilslib.image.LoadImageUtil
import com.like.utilslib.screen.DensityUtil
import com.like.utilslib.screen.ScreenUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.model.dynamic.DynamicDetailModel
import com.szy.yishopcustomer.Constant.Api
import com.szy.yishopcustomer.Util.Utils

/**
 * Created by :TYK
 * Date: 2019/3/15  15:32
 * Desc: 动态详情中的图片适配器
 */

class PicAdapter : BaseQuickAdapter<DynamicDetailModel.TopicFileListBean, BaseViewHolder>(R.layout.item_pic) {
    private var params: ViewGroup.LayoutParams? = null

    init {
        val imgWidth = (ScreenUtil.getScreenWidth() - DensityUtil.dpTopx(28f)) / 3
        params = ViewGroup.LayoutParams(imgWidth, imgWidth)
    }

    override fun convert(helper: BaseViewHolder?, item: DynamicDetailModel.TopicFileListBean?) {
        val imgview = helper!!.getView(R.id.img_pic) as ImageView
        imgview.layoutParams = params
        LoadImageUtil.loadImage(imgview, Utils.setImgNetUrl(Api.API_CITY_HOME_MER_IMG_URL, item!!.file))

    }
}