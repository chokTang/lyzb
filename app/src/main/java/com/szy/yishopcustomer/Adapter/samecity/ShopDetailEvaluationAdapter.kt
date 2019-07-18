package com.szy.yishopcustomer.Adapter.samecity

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.ReviewList.CommentCount
import com.szy.yishopcustomer.Util.GlideUtil
import com.szy.yishopcustomer.Util.LogUtils
import com.szy.yishopcustomer.View.RatingBar
import com.szy.yishopcustomer.ViewModel.samecity.Comment

class ShopDetailEvaluationAdapter(context: Context, layoutResId: Int) : BaseQuickAdapter<Comment, BaseViewHolder>(layoutResId) {

    var context: Context? = null

    init {
        this.context = context
    }


    override fun convert(helper: BaseViewHolder?, item: Comment?) {
        if (!TextUtils.isEmpty(item!!.userPhoto)) {
            Glide.with(this.context!!).load(item.userPhoto).apply(GlideUtil.OptionsDefaultAvata()).into(helper!!.getView(R.id.img_shop_detail_user_avatar)!!)
        }
        helper!!.setText(R.id.tv_shop_detail_user_name, item.userNickName)
        helper.setText(R.id.tv_shop_detail_time, item.gmtCreate)
        //隐藏团购标志
//        helper.setText(R.id.tv_shop_detail_evaluation_group_buy, item.saleName)
        helper.getView<TextView>(R.id.tv_shop_detail_evaluation_group_buy).visibility = View.GONE
        helper.getView<RatingBar>(R.id.ratingBar_shop_detail_user_evaluation).star = Math.ceil(item.evalScore).toInt()

        helper.setText(R.id.tv_shop_detail_evaluation_description, item.evalContent)
        if (TextUtils.isEmpty(item.shopComment)) {
            helper.getView<TextView>(R.id.tv_shop_detail_reply).visibility = View.GONE
        } else {
            helper.getView<TextView>(R.id.tv_shop_detail_reply).visibility = View.VISIBLE
            helper.setText(R.id.tv_shop_detail_reply, "商家回复:" + item.shopComment)
        }
    }

}