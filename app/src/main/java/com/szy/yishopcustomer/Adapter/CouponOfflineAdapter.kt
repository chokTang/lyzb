package com.szy.yishopcustomer.Adapter

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lyzb.jbx.R
import com.szy.yishopcustomer.Util.GlideUtil
import com.szy.yishopcustomer.Util.Utils
import com.szy.yishopcustomer.ViewModel.CouponBean
import com.szy.yishopcustomer.ViewModel.HxqBean

/**
 * Created by :TYK
 * Date: 2019/2/27  11:53
 * Desc:线上优惠券适配器
 */

class CouponOfflineAdapter(layoutResId: Int) : BaseQuickAdapter<HxqBean.ListBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder?, item: HxqBean.ListBean?) {
        Glide.with(helper!!.itemView.context).load(item!!.prodLogo).apply(GlideUtil.RadioOptions(5)).thumbnail(0.5f).into(helper.getView(R.id.img_hxq)!!)
        helper.setText(R.id.tv_hxq_name, item.prodName)
        helper.setText(R.id.tv_hxq_ingot, "¥0+" + item.acer + "元宝")
        helper.setText(R.id.tv_hxq_send, "发行方：" + item.shopName)
        if (item.distance == 0.0) {
            helper.getView<TextView>(R.id.tv_hxq_distance).visibility = View.GONE
        } else {
            helper.getView<TextView>(R.id.tv_hxq_distance).visibility = View.VISIBLE
            if (item.distance >= 1000) {
                helper.setText(R.id.tv_hxq_distance, "距您" + Utils.toDistance(item.distance) + "km")
            } else {
                helper.setText(R.id.tv_hxq_distance, "距您" + String.format("%.2f", item.distance) + "m")
            }
        }


        helper.addOnClickListener(R.id.tv_now_get)

    }
}