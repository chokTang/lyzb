package com.lyzb.jbx.adapter.me.card

import android.graphics.Bitmap
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.cfox.ivcliplib.CImageUtils
import com.cfox.ivcliplib.CImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.like.utilslib.image.LoadImageUtil
import com.like.utilslib.screen.DensityUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.model.me.CardModel
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.schedulers.Schedulers

/**
 * Created by :TYK
 * Date: 2019/5/6  17:11
 * Desc: 名片模板展示adapter
 */
class CardShowAdapter : BaseQuickAdapter<CardModel, BaseViewHolder>(R.layout.layout_card_template2) {

    /**
     * 区分 来源 1:我的智能名片  2:TA的智能名片
     **/
    var mFromType = 1
    /**
     * 这里是判断 是修改后回来刷新还是  初始化的时候刷新
     * 1.初始化的时候  无数据要展示“暂无”
     * 2.修改后回调转来 无数据代表未修改  并不一定是 “暂无”
     */
    var isUpdate = false

    var model: CardModel? = null

    var isShowView = true //是否显示 "编辑"，“更换模板”view

    companion object {
        const val TYPE_TEMP_1 = 10001  // 模板1  (高端款)
        const val TYPE_TEMP_2 = 10002  //模板2  （大气款）

        const val TEMP_ID_1 = "1"  //模板2 (高端款)模板ID
        const val TEMP_ID_2 = "2"  //模板1 （大气款）模板ID
    }

    init {
        multiTypeDelegate = object : MultiTypeDelegate<CardModel>() {
            override fun getItemType(t: CardModel?): Int {
                var type = 0
                when (t!!.getTemplateId()) {
                    TEMP_ID_1 -> {//模板1
                        type = TYPE_TEMP_2
                    }
                    TEMP_ID_2 -> {//模板2
                        type = TYPE_TEMP_1
                    }
                }
                return type
            }
        }

        multiTypeDelegate.registerItemType(TYPE_TEMP_1, R.layout.layout_card_template1)
                .registerItemType(TYPE_TEMP_2, R.layout.layout_card_template2)
    }

    override fun convert(helper: BaseViewHolder?, item: CardModel?) {
        helper!!.addOnClickListener(R.id.img_card_qrcode)
        helper.addOnClickListener(R.id.tv_card_send_msg)
        helper.addOnClickListener(R.id.tv_card_call_phone)
        helper.addOnClickListener(R.id.tv_card_focus)
        helper.addOnClickListener(R.id.ll_record)
        helper.addOnClickListener(R.id.rl_fans)
        helper.addOnClickListener(R.id.rl_focus)
        helper.addOnClickListener(R.id.img_card_info_edt)
        helper.addOnClickListener(R.id.img_change_template)
        helper.addOnClickListener(R.id.fl_avatar)
        helper.addOnClickListener(R.id.img_company_logo)
        helper.addOnClickListener(R.id.tv_card_edt)
        helper.addOnClickListener(R.id.tv_change_template)
        helper.addOnClickListener(R.id.tv_card_address)
        when (helper!!.itemViewType) {
            TYPE_TEMP_1 -> {//模板1  左半边
                item.run {
                    LoadImageUtil.loadRoundSizeImage(helper.getView<ImageView>(R.id.img_card_head), this?.headimg, 50)
                    loadImage1(helper.getView<CImageView>(R.id.img_card_template), this?.posterImg!!)
                    if (!TextUtils.isEmpty(this?.shopLogo)) {
                        helper.setGone(R.id.img_company_logo, true)
                        //公司logo
                        LoadImageUtil.loadImage(helper.getView<ImageView>(R.id.img_company_logo), this?.shopLogo)
                    } else {
                        helper.setGone(R.id.img_company_logo, false)
                    }
                    if (this!!.userVipAction.size > 0) {
                        helper.setGone(R.id.img_vip, true)
                    } else {
                        helper.setGone(R.id.img_vip, false)
                    }
                    if (mFromType == 1) {//自己的名片
                        helper.setGone(R.id.lin_card_contact, false)
                        helper.setGone(R.id.tv_card_edt, isShowView)
                        helper.setGone(R.id.tv_change_template, isShowView)
                    } else {//别人的名片
                        helper.setGone(R.id.tv_card_edt, false)
                        helper.setGone(R.id.tv_change_template, false)
                        helper.setGone(R.id.lin_card_contact, true)
                        if (model!!.relation > 0) {
                            helper.setText(R.id.tv_card_focus, "已关注")
                        } else {
                            helper.setText(R.id.tv_card_focus, "关注")
                        }
                    }
                    //关注的显示
                    helper.setText(R.id.tv_follow_num, model!!.relationGz.toString())
                    //粉丝的显示
                    helper.setText(R.id.tv_fans_num, model!!.relationFs.toString())

                    //初始化三个头像
                    if (model!!.myViewUserList.size > 0) {
                        helper.setGone(R.id.img_card_other_head_one, true)
                        LoadImageUtil.loadRoundSizeImage(helper.getView<ImageView>(R.id.img_card_other_head_one), model!!.myViewUserList[0].headimg, 36)
                    }
                    if (model!!.myViewUserList.size > 1) {
                        helper.setGone(R.id.img_card_other_head_two, true)
                        LoadImageUtil.loadRoundSizeImage(helper.getView<ImageView>(R.id.img_card_other_head_two), model!!.myViewUserList[1].headimg, 36)
                    }
                    if (model!!.myViewUserList.size > 2) {
                        helper.setGone(R.id.img_card_other_head_three, true)
                        LoadImageUtil.loadRoundSizeImage(helper.getView<ImageView>(R.id.img_card_other_head_three), model!!.myViewUserList[2].headimg, 36)
                    }
                    helper.setText(R.id.tv_card_other_browse, model!!.viewCount.toString() + "次浏览")

                    if (isUpdate) {//修改转来刷新的  无数据代表未修改
                        helper.setText(R.id.tv_card_name, gsName)
                        helper.setText(R.id.tv_card_position, position)
                        helper.setText(R.id.tv_card_phone, mobile)
                        if (!TextUtils.isEmpty(shopName)) {
                            helper.setText(R.id.tv_card_company, shopName)
                        }
                        if (!TextUtils.isEmpty(shopAddress)) {
                            helper.setText(R.id.tv_card_address, shopAddress)
                        }

                        if (!TextUtils.isEmpty(wxImg)) {
                            helper.setGone(R.id.img_card_qrcode, true)
                            //初始化二维码
                            LoadImageUtil.loadImage(helper.getView<ImageView>(R.id.img_card_qrcode), wxImg)
                        } else {
                            helper.setGone(R.id.img_card_qrcode, false)
                        }
                    } else {//初始化的时候  无数据会展示“暂无”
                        //初始化头像 及个人信息
                        if (TextUtils.isEmpty(gsName)) {
                            helper.setText(R.id.tv_card_name, "暂无")
                        } else {
                            helper.setText(R.id.tv_card_name, gsName)
                        }
                        if (TextUtils.isEmpty(position)) {
                            helper.setText(R.id.tv_card_position, "暂无")
                        } else {
                            helper.setText(R.id.tv_card_position, position)
                        }

                        if (TextUtils.isEmpty(mobile)) {
                            helper.setText(R.id.tv_card_phone, "暂无")
                        } else {
                            helper.setText(R.id.tv_card_phone, mobile)
                        }

                        if (TextUtils.isEmpty(shopName)) {
                            helper.setText(R.id.tv_card_company, "暂无")
                        } else {
                            helper.setText(R.id.tv_card_company, shopName)

                        }
                        if (!TextUtils.isEmpty(shopAddress)) {
                            helper.setText(R.id.tv_card_address, shopAddress)
                        } else {
                            helper.setText(R.id.tv_card_address, "暂无")
                        }
                        if (!TextUtils.isEmpty(wxImg)) {
                            helper.setGone(R.id.img_card_qrcode, true)
                            //初始化二维码
                            LoadImageUtil.loadImage(helper.getView<ImageView>(R.id.img_card_qrcode), wxImg)
                        } else {
                            helper.setGone(R.id.img_card_qrcode, false)
                        }


                    }

                }
            }

            TYPE_TEMP_2 -> {//模板2  上半边
                item.run {
                    LoadImageUtil.loadRoundImage(helper.getView<ImageView>(R.id.img_card_head), this?.headimg, 4)
                    LoadImageUtil.loadImage(helper.getView<ImageView>(R.id.img_card_template), this?.posterImg,R.mipmap.bg_card_default)

                    if (!TextUtils.isEmpty(this?.shopLogo)) {
                        helper.setGone(R.id.img_company_logo, true)
                        //公司logo
                        LoadImageUtil.loadImage(helper.getView<ImageView>(R.id.img_company_logo), this?.shopLogo)
                    } else {
                        helper.setGone(R.id.img_company_logo, false)
                    }
                    if (this!!.userVipAction.size > 0) {
                        helper.setGone(R.id.img_vip, true)
                    } else {
                        helper.setGone(R.id.img_vip, false)
                    }
                    if (mFromType == 1) {//自己的名片
                        helper.setGone(R.id.lin_card_contact, false)
                        helper.setGone(R.id.img_card_info_edt, isShowView)
                        helper.setGone(R.id.img_change_template, isShowView)
                    } else {//别人的名片
                        helper.setGone(R.id.img_card_info_edt, false)
                        helper.setGone(R.id.img_change_template, false)
                        helper.setGone(R.id.lin_card_contact, true)
                        if (model!!.relation > 0) {
                            helper.setText(R.id.tv_card_focus, "已关注")
                        } else {
                            helper.setText(R.id.tv_card_focus, "关注")
                        }
                    }
                    //关注的显示
                    helper.setText(R.id.tv_follow_num, model!!.relationGz.toString())
                    //粉丝的显示
                    helper.setText(R.id.tv_fans_num, model!!.relationFs.toString())

                    //初始化三个头像
                    if (model!!.myViewUserList.size > 0) {
                        helper.setGone(R.id.img_card_other_head_one, true)
                        LoadImageUtil.loadRoundSizeImage(helper.getView<ImageView>(R.id.img_card_other_head_one), model!!.myViewUserList[0].headimg, 36)
                    }
                    if (model!!.myViewUserList.size > 1) {
                        helper.setGone(R.id.img_card_other_head_two, true)
                        LoadImageUtil.loadRoundSizeImage(helper.getView<ImageView>(R.id.img_card_other_head_two), model!!.myViewUserList[1].headimg, 36)
                    }
                    if (model!!.myViewUserList.size > 2) {
                        helper.setGone(R.id.img_card_other_head_three, true)
                        LoadImageUtil.loadRoundSizeImage(helper.getView<ImageView>(R.id.img_card_other_head_three), model!!.myViewUserList[2].headimg, 36)
                    }
                    helper.setText(R.id.tv_card_other_browse, model!!.viewCount.toString() + "次浏览")

                    if (isUpdate) {//修改转来刷新的  无数据代表未修改
                        helper.setText(R.id.tv_card_name, gsName)
                        helper.setText(R.id.tv_card_position, position)
                        helper.setText(R.id.tv_card_phone, mobile)
                        if (!TextUtils.isEmpty(shopName)) {
                            helper.setText(R.id.tv_card_company, shopName)
                        } else {
                            helper.setText(R.id.tv_card_company, "暂无")
                        }
                        if (!TextUtils.isEmpty(shopAddress)) {
                            helper.setText(R.id.tv_card_address, shopAddress)
                        } else {
                            helper.setText(R.id.tv_card_address, "暂无")
                        }

                        if (!TextUtils.isEmpty(wxImg)) {
                            helper.setGone(R.id.img_card_qrcode, true)
                            //初始化二维码
                            LoadImageUtil.loadImage(helper.getView<ImageView>(R.id.img_card_qrcode), wxImg)
                        } else {
                            helper.setGone(R.id.img_card_qrcode, false)
                        }
                    } else {//初始化的时候  无数据会展示“暂无”
                        //初始化头像 及个人信息
                        if (TextUtils.isEmpty(gsName)) {
                            helper.setText(R.id.tv_card_name, "暂无")
                        } else {
                            helper.setText(R.id.tv_card_name, gsName)
                        }
                        if (TextUtils.isEmpty(position)) {
                            helper.setText(R.id.tv_card_position, "暂无")
                        } else {
                            helper.setText(R.id.tv_card_position, position)
                        }

                        if (TextUtils.isEmpty(mobile)) {
                            helper.setText(R.id.tv_card_phone, "暂无")
                        } else {
                            helper.setText(R.id.tv_card_phone, mobile)
                        }

                        if (TextUtils.isEmpty(shopName)) {
                            helper.setText(R.id.tv_card_company, "暂无")
                        } else {
                            helper.setText(R.id.tv_card_company, shopName)

                        }
                        if (!TextUtils.isEmpty(shopAddress)) {
                            helper.setText(R.id.tv_card_address, shopAddress)
                        } else {
                            helper.setText(R.id.tv_card_address, "暂无")
                        }
                        if (!TextUtils.isEmpty(wxImg)) {
                            helper.setGone(R.id.img_card_qrcode, true)
                            //初始化二维码
                            LoadImageUtil.loadImage(helper.getView<ImageView>(R.id.img_card_qrcode), wxImg)
                        } else {
                            helper.setGone(R.id.img_card_qrcode, false)
                        }


                    }

                }
            }

        }
    }

    /**
     * 加载图片
     * 默认0.2  倍缩略图
     *
     * @param img
     * @param imageRes
     */
    fun loadImage1(img: ImageView, imageRes: String) {
        Glide.with(img.context)
                .asBitmap()
                .load(imageRes).into<SimpleTarget<Bitmap>>(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        Observable.just(resource)
                                .map(object : Func1<Bitmap, Bitmap> {
                                    override fun call(t: Bitmap?): Bitmap {
                                        var resource1 = CImageUtils.instance(img.context).cropToBitmap(t, DensityUtil.dpTopx(70f), DensityUtil.dpTopx(140f))
                                        return resource1
                                    }
                                })
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe {
                                    img.setImageBitmap(it)
                                }
                    }
                })
    }
}