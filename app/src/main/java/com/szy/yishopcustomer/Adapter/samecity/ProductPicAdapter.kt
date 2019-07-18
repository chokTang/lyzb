package com.szy.yishopcustomer.Adapter.samecity

import android.content.Context
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.GlideUtil
import com.szy.yishopcustomer.Util.LogUtils
import com.szy.yishopcustomer.ViewModel.samecity.ProductImageVoList


class ProductPicAdapter(context: Context, layoutResId: Int) : BaseQuickAdapter<ProductImageVoList, BaseViewHolder>(layoutResId) {

    var context: Context? = null
    init {
        this.context = context
    }

    override fun getItemCount(): Int {
        return 2
    }
    override fun convert(helper: BaseViewHolder?, item: ProductImageVoList?) {
        Glide.with(this.context!!).load(item!!.imgUrl).apply(GlideUtil.OptionsDefaultLogo()).thumbnail(0.5f).into(helper!!.getView(R.id.img_product_detail_pic)!!)
    }

}