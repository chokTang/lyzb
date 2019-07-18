package com.lyzb.jbx.fragment.me.card

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.like.longshaolib.base.BaseFragment
import com.like.utilslib.file.FileUtil
import com.like.utilslib.image.BitmapUtil
import com.like.utilslib.image.LoadImageUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.model.me.CardModel
import com.lyzb.jbx.model.me.MiniQrUrlModel
import com.lyzb.jbx.mvp.presenter.me.card.PosterPresenter
import com.lyzb.jbx.mvp.view.me.IPosterView
import com.szy.yishopcustomer.Util.App
import com.szy.yishopcustomer.Util.WeiXinUtils
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import kotlinx.android.synthetic.main.fragment_poster.*

/**
 * Created by :TYK
 * Date: 2019/5/25  13:54
 * Desc:  名片海报
 */

class CardPosterFragment : BaseFragment<PosterPresenter>(), IPosterView, View.OnClickListener {


    var cardModel: CardModel? = null

    companion object {
        const val KEY_CARD = "key_card_model"
        fun newIncetance(model: CardModel): CardPosterFragment {
            val fragment = CardPosterFragment()
            val bundle = Bundle()
            bundle.putSerializable(KEY_CARD, model)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        cardModel = bundle?.getSerializable(KEY_CARD) as CardModel
    }

    override fun getResId(): Any {
        return R.layout.fragment_poster
    }


    override fun onInitView(savedInstanceState: Bundle?) {
        LoadImageUtil.loadRoundImage(img_card_head, cardModel?.headimg, 4)
        if (TextUtils.isEmpty(cardModel?.headimg)) {
            img_center_avatar.visibility = View.GONE
        } else {
            img_center_avatar.visibility = View.VISIBLE
            LoadImageUtil.loadRoundImage(img_center_avatar, cardModel?.headimg, 30)
        }
        tv_card_name.text = cardModel?.gsName
        tv_card_position.text = cardModel?.position
        if (!TextUtils.isEmpty(cardModel?.shopLogo)) {
            img_company_logo.visibility = View.VISIBLE
            //公司logo
            LoadImageUtil.loadImage(img_company_logo, cardModel?.shopLogo)
        } else {
            img_company_logo.visibility = View.GONE

        }

        if (cardModel!!.userVipAction.size > 0) {
            img_vip.visibility = View.VISIBLE
        } else {
            img_vip.visibility = View.GONE
        }

        if (TextUtils.isEmpty(cardModel?.mobile)) {
            tv_card_phone.text = "暂无"
        } else {
            tv_card_phone.text = cardModel?.mobile
        }
        if (TextUtils.isEmpty(cardModel?.shopName)) {
            tv_card_company.text = "暂无"
        } else {
            tv_card_company.text = cardModel?.shopName
        }
        if (!TextUtils.isEmpty(cardModel?.shopAddress)) {
            tv_card_address.text = cardModel?.shopAddress
        } else {
            tv_card_address.text = "暂无"
        }



        tv_share_wx.setOnClickListener(this)
        tv_share_circle.setOnClickListener(this)
        tv_download.setOnClickListener(this)
        tv_cancel.setOnClickListener(this)

    }

    override fun onInitData(savedInstanceState: Bundle?) {
        mPresenter.getCardTabList(cardModel!!.userId, cardModel!!.distributorCreatorID)
    }

    override fun getMiniqrQrUrlSucess(model: MiniQrUrlModel) {
        if (!TextUtils.isEmpty(model.imgPath)) {
            img_card_qrcode.visibility = View.VISIBLE
            //初始化二维码
            LoadImageUtil.loadImage(img_card_qrcode, model.imgPath)
        } else {
            img_card_qrcode.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        val bitmap = BitmapUtil.createViewBitmapNo(rl_cut_img)
        when (v?.id) {
            R.id.tv_share_wx -> {//微信朋友
                WeiXinUtils.shareImage(context, bitmap, SendMessageToWX.Req
                        .WXSceneSession)
            }
            R.id.tv_share_circle -> {//微信朋友圈
                WeiXinUtils.shareImage(context, bitmap, SendMessageToWX.Req
                        .WXSceneTimeline)
            }
            R.id.tv_download -> {//保存到手机
                FileUtil.saveBitmap(bitmap, "lyzb", 100)
                showToast("保存成功~")
            }
            R.id.tv_cancel -> {//取消
                pop()
            }
        }
    }
}