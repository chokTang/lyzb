package com.szy.yishopcustomer.Adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lyzb.jbx.R
import com.szy.yishopcustomer.ViewModel.CouponBean

/**
 * Created by :TYK
 * Date: 2019/2/27  11:53
 * Desc:线上优惠券适配器
 */

class CouponOnlineAdapter(layoutResId: Int) : BaseQuickAdapter<CouponBean.ListBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder?, item: CouponBean.ListBean?) {

        helper!!.setText(R.id.tv_coupon_amount, item!!.bonusAmount)
        helper.setText(R.id.tv_coupon_name, item.bonusName)
        helper.setText(R.id.tv_condition, "（使用"+ item.bonusAmount+"元宝可得）")
        helper.setText(R.id.tv_send, item. bonusName)
    }
}