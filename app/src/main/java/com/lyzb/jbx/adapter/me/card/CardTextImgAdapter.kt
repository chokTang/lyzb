package com.lyzb.jbx.adapter.me.card

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.hyphenate.util.DensityUtil
import com.like.utilslib.image.LoadImageUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.model.me.IntroductionContent
import com.lyzb.jbx.util.WebViewUtil
import com.lyzb.jbx.widget.NoClickRetalation
import com.szy.yishopcustomer.View.DisplayUtil

/**
 * Created by :TYK
 * Date: 2019/6/13  11:05
 * Desc: text，img 文字图片模板适配器
 */
class CardTextImgAdapter : BaseQuickAdapter<IntroductionContent, BaseViewHolder>(R.layout.item_text) {
    companion object {
        const val TYPE_TEXT = 1//文字
        const val TYPE_IMG = 2//图片
    }

    var isCanEdit = false
    var id = ""

    init {
        multiTypeDelegate = object : MultiTypeDelegate<IntroductionContent>() {
            override fun getItemType(t: IntroductionContent?): Int {
                return t!!.contentType.toInt()
            }
        }

        multiTypeDelegate.registerItemType(TYPE_TEXT, R.layout.item_text)
                .registerItemType(TYPE_IMG, R.layout.item_img)
    }

    override fun convert(helper: BaseViewHolder?, item: IntroductionContent?) {
        when (helper?.itemViewType) {
            TYPE_TEXT -> {
                item.run {

                    if (!TextUtils.isEmpty(this?.graphContent)) {
                        WebViewUtil.webviewSet(helper.getView<WebView>(R.id.webview))
                        if (isCanEdit) {
                            val layoutParams = helper.getView<NoClickRetalation>(R.id.rl_content).layoutParams as LinearLayout.LayoutParams
                            layoutParams.setMargins(DensityUtil.dip2px(mContext,10f),0,DensityUtil.dip2px(mContext,10f),0)
                            helper.getView<NoClickRetalation>(R.id.rl_content).layoutParams = layoutParams
                            helper.getView<NoClickRetalation>(R.id.rl_content).setPadding(DensityUtil.dip2px(mContext,10f),
                                    DensityUtil.dip2px(mContext,10f),DensityUtil.dip2px(mContext,10f),DensityUtil.dip2px(mContext,10f))
                            helper.getView<NoClickRetalation>(R.id.rl_content).setBackgroundColor(mContext.resources.getColor(R.color.windows_bg))
                            helper.getView<WebView>(R.id.webview).setBackgroundColor(mContext.resources.getColor(R.color.windows_bg))
                        } else {
                            val layoutParams = helper.getView<NoClickRetalation>(R.id.rl_content).layoutParams as LinearLayout.LayoutParams
                            layoutParams.setMargins(0, 0,0,0)
                            helper.getView<NoClickRetalation>(R.id.rl_content).layoutParams = layoutParams
                            helper.getView<NoClickRetalation>(R.id.rl_content).setPadding(0,0,0,0)
                            helper.getView<NoClickRetalation>(R.id.rl_content).setBackgroundColor(mContext.resources.getColor(R.color.white))
                            helper.getView<WebView>(R.id.webview).setBackgroundColor(mContext.resources.getColor(R.color.white))
                        }
                        helper.getView<WebView>(R.id.webview).loadDataWithBaseURL(null, this?.graphContent, "text/html", "utf-8", null)
                    }
                    //复制内容
                    helper.getView<WebView>(R.id.webview).setOnLongClickListener(View.OnLongClickListener {
                        val cm = helper.getView<WebView>(R.id.webview).context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val mClipData = ClipData.newPlainText("Label", this!!.graphContent)
                        cm.primaryClip = mClipData
                        Toast.makeText(mContext, "复制成功", Toast.LENGTH_SHORT).show()
                        true
                    })
                    helper.addOnClickListener(R.id.img_cancel)
                    helper.addOnClickListener(R.id.rl_content)
                }
            }
            TYPE_IMG -> {
                item.run {
                    LoadImageUtil.loadImage(helper.getView<ImageView>(R.id.img_img), this?.graphContent)
                    helper.addOnClickListener(R.id.img_img)
                    helper.addOnClickListener(R.id.img_cancel)
                }
            }
        }
        if (helper!!.adapterPosition == 0) {//第一个只显示向下箭头
            helper.setGone(R.id.img_turn_top, false)
            helper.setGone(R.id.img_turn_down, true)
        } else {
            if (helper.adapterPosition == itemCount - 1) {//最后一个只显示向上箭头
                helper.setGone(R.id.img_turn_top, true)
                helper.setGone(R.id.img_turn_down, false)
            } else {
                helper.setGone(R.id.img_turn_top, true)
                helper.setGone(R.id.img_turn_down, true)
            }
        }
        helper.setGone(R.id.ll_add, isCanEdit)
        helper.setGone(R.id.ll_bottom, isCanEdit)
        helper.addOnClickListener(R.id.ll_add)
        helper.addOnClickListener(R.id.img_turn_down)
        helper.addOnClickListener(R.id.img_turn_top)
//        helper.getView<RelativeLayout>(R.id.rl_content).setOnLongClickListener {
//            clickListener?.onItemLongClick(helper, helper.adapterPosition, item!!)!!
//        }
    }


//    var clickListener: IRecycleHolderAnyClilck? = null
//
//    operator fun invoke(clickListener: IRecycleHolderAnyClilck?) {
//        this.clickListener = clickListener
//    }
}


