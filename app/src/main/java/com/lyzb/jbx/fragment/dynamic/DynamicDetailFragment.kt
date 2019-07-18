package com.lyzb.jbx.fragment.dynamic

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hyphenate.easeui.EaseConstant
import com.like.longshaolib.adapter.BaseRecyleAdapter
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener
import com.like.longshaolib.dialog.fragment.AlertDialogFragment
import com.like.utilslib.app.CommonUtil
import com.like.utilslib.date.DateStyle
import com.like.utilslib.date.DateUtil
import com.like.utilslib.image.BitmapUtil
import com.like.utilslib.image.LoadImageUtil
import com.like.utilslib.screen.DensityUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.activity.GoodToMeActivity
import com.lyzb.jbx.activity.ScanAndLikeActivity
import com.lyzb.jbx.activity.SendMessageActivity
import com.lyzb.jbx.adapter.common.SingleImageAdapterDynamic
import com.lyzb.jbx.adapter.dynamic.CommentAdapter
import com.lyzb.jbx.adapter.dynamic.ProductAdapter
import com.lyzb.jbx.api.UrlConfig
import com.lyzb.jbx.dialog.CollectionRemindDialog
import com.lyzb.jbx.dialog.SendMessageDialog
import com.lyzb.jbx.fragment.base.BaseVideoStatusToolbarFrgament
import com.lyzb.jbx.fragment.me.card.CardFragment
import com.lyzb.jbx.inter.IRecycleAnyClickListener
import com.lyzb.jbx.model.dynamic.*
import com.lyzb.jbx.model.eventbus.DynamicItemStatusEventBus
import com.lyzb.jbx.model.send.GoodsList
import com.lyzb.jbx.mvp.presenter.dynamic.DynamicDetailPresenter
import com.lyzb.jbx.mvp.view.dynamic.IDynamicDetailView
import com.lyzb.jbx.util.AppCommonUtil
import com.lyzb.jbx.util.AppPreference
import com.lyzb.jbx.util.ImageUtil
import com.lyzb.jbx.webscoket.BaseClient
import com.szy.yishopcustomer.Activity.GoodsActivity
import com.szy.yishopcustomer.Activity.LoginActivity
import com.szy.yishopcustomer.Activity.TxCustomVideoPlayerController
import com.szy.yishopcustomer.Activity.YSCWebViewActivity
import com.szy.yishopcustomer.Activity.im.ImCommonActivity
import com.szy.yishopcustomer.Constant.Key
import com.szy.yishopcustomer.Util.App
import com.szy.yishopcustomer.ViewModel.im.ImHeaderGoodsModel
import com.xiao.nicevideoplayer.NiceVideoPlayer
import kotlinx.android.synthetic.main.dialog_send_message.*
import kotlinx.android.synthetic.main.fragment_dynamic_detail.*
import kotlinx.android.synthetic.main.fragment_dynamic_detail.view.*
import me.yokeyword.fragmentation.ISupportFragment
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * Created by :TYK
 * Date: 2019/3/14  15:16
 * Desc:动态详情 页面
 * 问题1：点击评论后返回遇到下拉冲突  原因：监听scorllview滑动 返回的时候没初始化成功  解决：点击评论时候 滑动到顶部 重新初始化
 */

class DynamicDetailFragment : BaseVideoStatusToolbarFrgament<DynamicDetailPresenter>(), IDynamicDetailView, View.OnClickListener {

    override fun getVideoPlayer(): NiceVideoPlayer {
        return nice_video_player
    }

    companion object {
        const val KEY_REQUESCODE = 0x223
        const val KEY_RESULT_CODE = 0x11
        const val PARAMS_ID = "PARAMS_ID"
        const val PARAMS_SHOWPING = "params_showping"
        fun newIntance(dynamicId: String): DynamicDetailFragment {
            return newIntance(dynamicId, false)
        }

        fun newIntance(dynamicId: String, isShow: Boolean = false): DynamicDetailFragment {
            val fragment = DynamicDetailFragment()
            val args = Bundle()
            args.putString(PARAMS_ID, dynamicId)
            args.putBoolean(PARAMS_SHOWPING, isShow)
            fragment.arguments = args
            return fragment
        }
    }

    var sendMessageDialog: SendMessageDialog? = null
    var adater: CommentAdapter? = null
    var mDynamicId = ""
    var mShowEdit = false
    var bean: DynamicDetailModel? = null
    var picAdapter: SingleImageAdapterDynamic? = null
    var list: MutableList<String> = arrayListOf()
    var timer: Timer? = null
    var productAdapter: ProductAdapter? = null

    override fun getResId(): Any {
        return R.layout.fragment_dynamic_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDynamicId = arguments?.getString(PARAMS_ID) ?: ""
        mShowEdit = arguments?.getBoolean(PARAMS_SHOWPING, false) ?: false
    }


    override fun onRequestSuccess(bean: DynamicDetailModel) {
        if (bean == null) {
            showToast("该动态不存在！")
            pop()
            return
        }
        this.bean = bean

        productAdapter?.setNewData(bean.goodsList.data)
        BaseClient.getInstance().setMessage(2, mDynamicId, this.bean!!.createMan)
        //处理动态是否所属圈子
        if (TextUtils.isEmpty(bean.groupName)) {
            (tv_group.parent as View).visibility = View.GONE
        }
        LoadImageUtil.loadRoundSizeImage(img_group, bean.groupLogo, 50)
        tv_group.text = bean.groupName
//        tv_comment_number.text = "共有" + bean.commentCount + "条评论"

        LoadImageUtil.loadRoundImage(img_avatar, bean.headimg, 4)
        LoadImageUtil.loadRoundImage(img_float_avatar, bean.headimg, 4)
        tv_name.text = bean.userName
        tv_float_name.text = bean.userName
//        LoadImageUtil.loadImage(img_vip, bean.vipType)
        if (bean.userActionVos.size > 0) {
            img_vip.visibility = View.VISIBLE
            img_float_vip.visibility = View.VISIBLE
        } else {
            img_vip.visibility = View.GONE
            img_float_vip.visibility = View.GONE
        }
        tv_shop_name.text = bean.shopName

        AppCommonUtil.autoLinkText(tv_content, bean.content, bean.id)

        if (App.getInstance().isLogin) {//登录了后  如果显示的是自己的动态 不显示关注与费关注
            if (bean.createMan == App.getInstance().userId) {//自己的
                img_focused.visibility = View.GONE
                img_float_focused.visibility = View.GONE
                img_add_focus.visibility = View.GONE
                img_float_add_focus.visibility = View.GONE
            } else {
                if (bean.concern > 0) {//已经关注  显示已经关注
                    showAddFollow(false)
                } else {//未关注   显示添加关注
                    showAddFollow(true)
                }
            }
        } else {//未登录则不显示关注与非关注
            showAddFollow(true)
        }
        //是否点赞 大于0表示点赞，反之则未点赞
        tv_like.isSelected = bean.giveLike > 0
        //是否收藏：大于0表示已收藏；反之则未收藏
        tv_follow.isSelected = bean.collect > 0

        if (bean.topicFileList.size > 0) {//文件类型 1 图片 2 视频 3热文 4名片5商品
            when (bean.topicFileList[0].class1) {
                "1" -> {
                    nice_video_player.visibility = View.GONE
                    if (bean.topicFileList.size == 1) {
                        rv_pic.visibility = View.GONE
                        image_first_img.visibility = View.VISIBLE
                        LoadImageUtil.loadImage(image_first_img, bean.topicFileList[0].file)
                    } else {
                        rv_pic.visibility = View.VISIBLE
                        image_first_img.visibility = View.GONE
                        picAdapter!!.update(bean.topicFileList)
                    }
                }
                "2" -> {
                    nice_video_player.visibility = View.VISIBLE
                    initVideoShow(bean)
                }
            }
        } else {
            nice_video_player.visibility = View.GONE
        }
        tv_time.text = DateUtil.StringToString(bean.createDate, DateStyle.MM_DD_HH_MM)
        tv_scan_number.text = (bean.viewCount+1).toString()
        tv_like_number.text = this.bean!!.upCount.toString()


        mPresenter.getCommentList(true, bean.id)
        refresh_dynamic_comment.setEnableRefresh(false)

        refresh_dynamic_comment.setOnLoadMoreListener { mPresenter.getCommentList(false, bean.id) }

        mView.postDelayed(object : Runnable {
            override fun run() {
                if (mShowEdit) {
                    startActivityForResult(Intent(context, SendMessageActivity::class.java), KEY_RESULT_CODE)
                }
            }
        }, 500)
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        //图片
        picAdapter = SingleImageAdapterDynamic(context, null)
        val layoutManager = GridLayoutManager(activity, 3)
        rv_pic.layoutManager = layoutManager
        rv_pic.adapter = picAdapter
        rv_pic.isNestedScrollingEnabled = false
        rv_pic.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect?, itemPosition: Int, parent: RecyclerView?) {
                outRect!!.set(0, 0, 10, 10)
            }
        }, 0)

        //设置内容可复制
        CommonUtil.textViewCopy(tv_content)

        //评论
        adater = CommentAdapter()
        val linearLayoutManager1 = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_comment.layoutManager = linearLayoutManager1
        rv_comment.addItemDecoration(DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST))
        rv_comment.adapter = adater
        rv_comment.isNestedScrollingEnabled = false

        //商品
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rv_product.layoutManager = linearLayoutManager
        productAdapter = ProductAdapter()
        rv_product.adapter = productAdapter
        rv_product.addItemDecoration(DividerItemDecoration(DividerItemDecoration.HORIZONTAL_LIST, R.drawable.listdivider_white_10))
        rv_product.isNestedScrollingEnabled = false

        //商品点击
        productAdapter?.setOnItemChildClickListener { adapter, view, position ->
            val bean = adapter.data[position] as GoodsList.DataBean
            when (view.id) {
                R.id.parent -> {
                    if (App.getInstance().isLogin) {
                        if (bean.can_buy == 1) {//能买
                            //跳转商品详情页面
                            val mIntent = Intent()
                            mIntent.setClass(activity!!, GoodsActivity::class.java)
                            mIntent.putExtra(Key.KEY_GOODS_ID.value, bean.goods_id)
                            mIntent.putExtra("card_user_id", App.getInstance().userId)
                            startActivity(mIntent)
                        } else {
                            val pfIntent = Intent(context, YSCWebViewActivity::class.java)
                            pfIntent.putExtra(Key.KEY_URL.value, UrlConfig.GOODS_URL + bean.goods_id + "&card_user_id=" + App.getInstance().userId)
                            startActivity(pfIntent)
                        }
                    } else {
                        startActivity(Intent(activity!!, LoginActivity::class.java))
                    }

                }
            }
        }

        rv_pic.addOnItemTouchListener(object : OnRecycleItemClickListener() {
            override fun onItemClick(adapter: BaseRecyleAdapter<*>?, view: View?, position: Int) {
                val list: MutableList<String> = arrayListOf()
                for (i in 0 until picAdapter!!.list.size) {
                    list.add((adapter!!.list[i] as DynamicDetailModel.TopicFileListBean).file)
                }
                ImageUtil.statPhotoViewActivity(activity!!, position, list)
            }
        })
        image_first_img.setOnClickListener {
            val list: MutableList<String> = arrayListOf()
            list.add(bean!!.topicFileList[0].file)
            ImageUtil.statPhotoViewActivity(activity!!, 0, list)
        }


        //滑动高度
        app_bar.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (rl_header != null) {
                if (rl_header.height + DensityUtil.dpTopx(10f) <= scrollY) {
                    title.visibility = View.GONE
                    float_title.visibility = View.VISIBLE
                } else {
                    title.visibility = View.VISIBLE
                    float_title.visibility = View.GONE
                }
            }
        }

        adater!!.setOnItemChildClickListener { adapter, view, position ->
            val bean = adater!!.data[position] as DynamicCommentModel.ListBean
            when (view.id) {
                R.id.tv_like -> {//点赞
                    if (App.getInstance().isLogin) {
                        if (!view.tv_like.isSelected) {//未选中  点赞
                            view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zan_anim))
                            mPresenter.addLikeOrFollow(4, bean.id, position)
                        } else {//选中了  取消点赞
                            mPresenter.cancelLikeOrFollow(4, bean.id, position)
                        }
                    } else {
                        startActivity(Intent(context, LoginActivity::class.java))
                    }
                }
                R.id.img_delete -> {//删除
                    AlertDialogFragment.newIntance()
                            .setContent("是否删除该条评论？")
                            .setCancleBtn { }
                            .setSureBtn { mPresenter.deleteDynamic(bean.id, position) }
                            .show(fragmentManager, "AlertDialog")
                }
                R.id.tv_reply -> {//回复
                    sendMessageDialog = SendMessageDialog()
                    sendMessageDialog!!.setOnDismissListener(DialogInterface.OnDismissListener {
                        timer = Timer()
                        timer!!.schedule(object : TimerTask() {
                            override fun run() {
                                hideSoftInput()
                            }
                        }, 500)
                    })
                    sendMessageDialog!!.show(fragmentManager, "sendTag")
                    sendMessageDialog!!.invoke(object : SendMessageDialog.ClickListener {
                        override fun onClick(s: String) {
                            if (App.getInstance().isLogin) {
                                val body = RequestBodyComment()
                                body.fileList = null
                                val topicCommentBean = RequestBodyComment.GsTopicCommentBean()
                                topicCommentBean.content = s
                                topicCommentBean.topicId = bean.topicId
                                topicCommentBean.type = 1
                                topicCommentBean.replyId = bean.id
                                body.gsTopicComment = topicCommentBean
                                if (TextUtils.isEmpty(body.gsTopicComment.content)) {
                                    showToast("请输入内容")
                                    return
                                }
                                mPresenter.addCommentOrReply(position, body)

                            } else {
                                startActivity(Intent(context, LoginActivity::class.java))
                            }
                        }
                    })
                }
            }
        }

        adater!!.setOnItemClickListener { adapter, view, position ->
            val userId = (adater!!.data[position] as DynamicCommentModel.ListBean).userId.toString()
            if (userId != App.getInstance().userId) {
                start(CardFragment.newIntance(2, userId))
            } else {
                start(CardFragment.newIntance(1, userId))
            }
        }

        ll_connect.setOnClickListener(this)
        ll_comment.setOnClickListener(this)
        ll_like.setOnClickListener(this)
        ll_follow.setOnClickListener(this)
        ll_focus.setOnClickListener(this)
        ll_float_focus.setOnClickListener(this)
        img_avatar.setOnClickListener(this)
        ll_name.setOnClickListener(this)
        back.setOnClickListener(this)
        img_right.setOnClickListener(this)
        back_title.setOnClickListener(this)
        img_right_title.setOnClickListener(this)
        tv_like_number.setOnClickListener(this)
        tv_scan_number.setOnClickListener(this)
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        mPresenter.getDynamicDetail(mDynamicId)

        adater!!.setListener(IRecycleAnyClickListener { view, position, item ->
            AlertDialogFragment.newIntance()
                    .setContent("是否删除该条评论？")
                    .setCancleBtn { }
                    .setSureBtn { mPresenter.deleteDynamic((item as DynamicCommentModel.ListBean.ChiledrenListBean).id, -1) }
                    .show(fragmentManager, "AlertDialog")
        })
    }

    /**
     * 点赞和收藏
     */
    override fun addLikeOrFollow(status: Int, bean: AddLikeOrFollowModel) {
        if (status == 2) {//点赞
            ll_like.tv_like.isSelected = true
            this.bean!!.upCount += 1
            tv_like_number.text = this.bean!!.upCount.toString()
            this.bean!!.giveLike = 1
            //发送通知
            val model = DynamicItemStatusEventBus()
            model.isZan = true
            model.commentNumber = this.bean!!.commentCount
            model.isFollow = this.bean!!.concern > 0
            EventBus.getDefault().post(model)
        } else {//收藏
            ll_follow.tv_follow.isSelected = true
            if (AppPreference.getIntance().firstCollection) {
                CollectionRemindDialog().show(childFragmentManager, "ShowCollectionTag")
            }
        }
    }

    /**
     * 点赞和收藏 取消
     */
    override fun cancleLikeOrFollow(status: Int, bean: AddLikeOrFollowModel) {
        if (status == 2) {//点赞
            ll_like.tv_like.isSelected = false
            this.bean!!.upCount -= 1
            tv_like_number.text = this.bean!!.upCount.toString()
            this.bean!!.giveLike = 0
            //发送通知
            val model = DynamicItemStatusEventBus()
            model.isZan = false
            model.commentNumber = this.bean!!.commentCount
            model.isFollow = this.bean!!.concern > 0
            EventBus.getDefault().post(model)
        } else {//收藏
            ll_follow.tv_follow.isSelected = false
        }
    }

    /**
     * 关注结果回调
     */
    override fun resultFocus(bean: DynamicFocusModel) {
        if (bean.enabled == 1) {//1 关注成功 0 取消关注成功
            this.bean!!.concern = 1
            showAddFollow(false)
        } else {
            this.bean!!.concern = 0
            showAddFollow(true)
        }

        //发送通知
        val model = DynamicItemStatusEventBus()
        model.isZan = (this.bean!!.giveLike > 0)
        model.commentNumber = this.bean!!.commentCount
        model.isFollow = (bean.enabled == 1)
        EventBus.getDefault().post(model)
    }

    /**
     * 评论回复结果回调
     */
    override fun addCommentOrReply(positon: Int, bean: AddCommentOrReplyModel) {
        if (bean.gsTopicComment.type == 1) {//回复
            sendMessageDialog!!.edt_content.text = Editable.Factory.getInstance().newEditable("")
            sendMessageDialog!!.edt_content.isFocusable = false
            sendMessageDialog!!.dismiss()

            val newModel = DynamicCommentModel.ListBean.ChiledrenListBean()
            newModel.content = bean.gsTopicComment.content
            newModel.id = bean.gsTopicComment.id
            newModel.topicId = bean.gsTopicComment.topicId
            newModel.replyId = bean.gsTopicComment.replyId
            newModel.type = bean.gsTopicComment.type
            newModel.userName = bean.gsUserExt.gsName
            newModel.headimg = bean.gsUserExt.headimg
            newModel.userId=App.getInstance().userId
            newModel.companyInfo = bean.gsUserExt.companyInfo
            newModel.createDate = bean.gsTopicComment.createDate
            newModel.userActionVos = bean.userVipAction

            if (adater!!.data[positon].chiledrenList.size == 0) {
                val list: MutableList<DynamicCommentModel.ListBean.ChiledrenListBean> = arrayListOf()
                list.add(0, newModel)
                adater!!.data[positon].chiledrenList = list
            } else {
                adater!!.data[positon].chiledrenList.add(0, newModel)
            }
            adater!!.refreshNotifyItemChanged(positon)
        } else {
            val newModel = DynamicCommentModel.ListBean()
            val filelist: MutableList<DynamicCommentModel.ListBean.FileVoList> = arrayListOf()
            newModel.id = bean.gsTopicComment.id
            newModel.topicId = bean.gsTopicComment.topicId
            newModel.replyId = bean.gsTopicComment.replyId
            newModel.type = bean.gsTopicComment.type
            newModel.userName = bean.gsUserExt.gsName
            newModel.headimg = bean.gsUserExt.headimg
            newModel.content = bean.gsTopicComment.content
            newModel.companyInfo = bean.gsUserExt.companyInfo
            newModel.createDate = bean.gsTopicComment.createDate
            newModel.userId = App.getInstance().userId
            newModel.userActionVos = bean.userVipAction
            for (i in 0 until bean.fileList.size) {
                val fileBean = DynamicCommentModel.ListBean.FileVoList()
                fileBean.file = bean.fileList[i].file
                filelist.add(fileBean)
            }
            newModel.fileVoList = filelist
            adater!!.data.add(0, newModel)
            adater!!.refreshNotifyItemChanged(0)

            this.bean!!.commentCount += 1
            tv_comment_number.text = "共有${this.bean!!.commentCount}条评论"
            //发送通知
            val model = DynamicItemStatusEventBus()
            model.isZan = (this.bean!!.giveLike > 0)
            model.commentNumber = this.bean!!.commentCount
            model.isFollow = this.bean!!.concern > 0
            EventBus.getDefault().post(model)
        }
    }

    var commentlist: MutableList<DynamicCommentModel.ListBean> = arrayListOf()
    /**
     * 这是原来commentfragment 碎片移动过来的
     * 评论列表数据回调
     */
    override fun onSuccess(isRefresh: Boolean, bean: DynamicCommentModel) {
        tv_comment_number.text = "共有${bean.total}条评论"
        this.bean!!.commentCount = bean.total;
        if (isRefresh) {//刷新
            refresh_dynamic_comment.finishRefresh()
            commentlist.clear()
            commentlist.addAll(bean.list)
        } else {//加载
            refresh_dynamic_comment.finishLoadMore()
            commentlist.addAll(bean.list)
            if (bean.list.size < bean.pageSize) {//添加完数据
                refresh_dynamic_comment.finishLoadMoreWithNoMoreData()
            }
        }
        if (commentlist.size == 0) {//没有数据
            val emptyView = LayoutInflater.from(context).inflate(R.layout.empty_layout, null, false)
            adater!!.emptyView = emptyView
        } else {//有数据
            adater!!.setNewData(commentlist)
        }

    }

    /**
     * 这是原来commentfragment 碎片移动过来的
     */
    override fun addLikeOrFollow(status: Int, model: AddLikeOrFollowModel, position: Int) {
        val bean = adater!!.data[position] as DynamicCommentModel.ListBean
        bean.giveLike = 1
        bean.giveNum = bean.giveNum + 1
        adater!!.setData(position, bean)
        adater!!.refreshNotifyItemChanged(position)
    }

    /**
     * 这是原来commentfragment 碎片移动过来的
     */
    override fun cancleLikeOrFollow(status: Int, bean: AddLikeOrFollowModel, position: Int) {
        val bean = adater!!.data[position] as DynamicCommentModel.ListBean
        bean.giveLike = 0
        bean.giveNum = bean.giveNum - 1
        adater!!.setData(position, bean)
        adater!!.refreshNotifyItemChanged(position)
    }


    /**
     * 点击事件
     */
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ll_focus, R.id.ll_float_focus -> {//关注
                if (App.getInstance().isLogin) {
                    if (img_add_focus.visibility == View.GONE || img_float_add_focus.visibility == View.GONE) {//已经关注  显示已经关注  点击取消关注
                        mPresenter.onFocusUser(0, bean!!.createMan)
                    } else {//未关注   显示添加关注  点击添加关注
                        mPresenter.onFocusUser(1, bean!!.createMan)
                    }
                } else {
                    startActivity(Intent(context, LoginActivity::class.java))
                }
            }
            R.id.back, R.id.back_title -> {//返回
                if (baseActivity is GoodToMeActivity) {
                    baseActivity.finish()
                } else {
                    pop()
                }
            }
            R.id.img_right, R.id.img_right_title -> {//分享
                if (App.getInstance().isLogin) {
                    mPresenter.shareDynamic(bean!!.id)
                }
                val bitmap = BitmapUtil.createViewBitmap(layout_header)
                AppCommonUtil.startAdapterShare(context, bean!!.id, bean!!.createMan, bean!!.userName, BitmapUtil.bitmap2Bytes(bitmap))
            }
            R.id.ll_connect -> {//联系
                if (App.getInstance().isLogin) {
                    val intent = Intent(context, ImCommonActivity::class.java)
                    val model = ImHeaderGoodsModel()

                    model.chatType = EaseConstant.CHATTYPE_SINGLE
                    if (TextUtils.isEmpty(this.bean!!.comAaccount)) {
                        showToast("该用户没有IM账号")
                        return
                    }
                    model.shopImName = this.bean!!.comAaccount
                    model.shopName = this.bean!!.userName
                    model.shopHeadimg = this.bean!!.headimg
                    model.shopId = ""

                    val args = Bundle()
                    args.putSerializable(ImCommonActivity.PARAMS_GOODS, model)
                    intent.putExtras(args)
                    startActivity(intent)
                } else {
                    startActivity(Intent(context, LoginActivity::class.java))
                }
            }
            R.id.ll_comment -> {//评论
//                app_bar.fling(0)
//                app_bar.smoothScrollTo(0, 0)
//                startForResult(UnionSendTWFragment.newIntance(SEND_COMMENT, ""), KEY_REQUESCODE)

                if (!App.getInstance().isLogin) {
                    startActivity(Intent(context, LoginActivity::class.java))
                    return
                }
                startActivityForResult(Intent(context, SendMessageActivity::class.java), KEY_RESULT_CODE)
            }
            R.id.ll_like -> {//点赞
                if (App.getInstance().isLogin) {
                    if (!ll_like.tv_like.isSelected) {
                        v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zan_anim))
                        mPresenter.addLikeOrFollow(2, mDynamicId, -1)
                    } else {
                        mPresenter.cancelLikeOrFollow(2, mDynamicId, -1)
                    }
                } else {
                    startActivity(Intent(context, LoginActivity::class.java))
                }
            }
            R.id.ll_follow -> {//收藏
                if (App.getInstance().isLogin) {
                    if (!ll_follow.tv_follow.isSelected) {
                        mPresenter.addLikeOrFollow(3, mDynamicId, -1)
                    } else {
                        mPresenter.cancelLikeOrFollow(3, mDynamicId, -1)
                    }
                } else {
                    startActivity(Intent(context, LoginActivity::class.java))
                }
            }
            R.id.img_avatar, R.id.ll_name -> {//跳转到名片
                if (TextUtils.isEmpty(App.getInstance().userId)) {
                    start(CardFragment.newIntance(2, bean!!.createMan))
                } else {
                    if (bean!!.createMan == App.getInstance().userId) {//发布人是自己
                        start(CardFragment.newIntance(1, bean!!.createMan))
                    } else {//发布人是别人
                        start(CardFragment.newIntance(2, bean!!.createMan))
                    }
                }
            }
            R.id.tv_like_number -> {//点击点赞按钮 进入点赞列表
                var intent = Intent(activity, ScanAndLikeActivity::class.java)
                var arg = Bundle()
                arg.putString("Id", this.mDynamicId)
                arg.putInt("type", 0)
                arg.putInt("scanNumber", this.bean!!.viewCount+1)
                arg.putInt("likeNumebr", this.bean!!.upCount)
                intent.putExtras(arg)
                startActivity(intent)
            }
            R.id.tv_scan_number -> {//浏览
                var intent = Intent(activity, ScanAndLikeActivity::class.java)
                var arg = Bundle()
                arg.putString("Id", this.mDynamicId)
                arg.putInt("type", 1)
                arg.putInt("scanNumber", this.bean!!.viewCount+1)
                arg.putInt("likeNumebr", this.bean!!.upCount)
                intent.putExtras(arg)
                startActivity(intent)
            }
        }
    }

    /**
     * 初始化播放器
     */
    fun initVideoShow(bean: DynamicDetailModel) {
        nice_video_player.setPlayerType(NiceVideoPlayer.TYPE_NATIVE) // or NiceVideoPlayer.TYPE_NATIVE
        nice_video_player.setUp(bean.topicFileList[0].file, null)
        val controller = TxCustomVideoPlayerController(activity)
        controller.setTitle("")
        Glide.with(activity!!).setDefaultRequestOptions(RequestOptions()
                .frame(1)
                .centerCrop()).load(bean.topicFileList[0].file).into(controller.imageView())
        nice_video_player.setController(controller)

        controller.setDoVideoPlayer { isplay, ishow, iscontroller ->
            if (isplay && !ishow) {
                if (nice_video_player.isIdle) {
                    nice_video_player.start()
                } else {
                    if (nice_video_player.isPlaying || nice_video_player.isBufferingPlaying) {
                        nice_video_player.pause()
                    } else if (nice_video_player.isPaused || nice_video_player.isBufferingPaused) {
                        nice_video_player.restart()
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }

    /**
     * 评论回调
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != ISupportFragment.RESULT_OK || data == null) {
            return
        }
        Log.e(TAG, "RESULT_OK")
        hideSoftInput()
        when (requestCode) {
            KEY_RESULT_CODE -> {
                val bodyComment = data.getSerializableExtra("RequestBodyComment") as RequestBodyComment
                bodyComment.gsTopicComment.type = 0
                bodyComment.gsTopicComment.topicId = mDynamicId
                mPresenter.addCommentOrReply(0, bodyComment)
            }
        }
    }

    override fun onFail(isRefresh: Boolean) {
        if (isRefresh) {
            refresh_dynamic_comment.finishRefresh()
        } else {
            refresh_dynamic_comment.finishLoadMore()
        }
    }


    override fun onSupportVisible() {
        super.onSupportVisible()
        if (this.bean == null) return
        BaseClient.getInstance().setMessage(2, mDynamicId, this.bean!!.createMan)
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        BaseClient.getInstance().close()
    }

    /**
     * 是否显示关注
     * @param isShow true：显示关注按钮 反之隐藏
     */
    fun showAddFollow(isShow: Boolean) {
        if (isShow) {
            img_add_focus.visibility = View.VISIBLE
            img_float_add_focus.visibility = View.VISIBLE
            img_focused.visibility = View.GONE
            img_float_focused.visibility = View.GONE
        } else {
            img_add_focus.visibility = View.GONE
            img_float_add_focus.visibility = View.GONE
            img_focused.visibility = View.VISIBLE
            img_float_focused.visibility = View.VISIBLE
        }
    }

    override fun onDeleteResult(position: Int) {
        if (position < 0 || position >= adater!!.data.size) {
            mPresenter.getCommentList(true, bean!!.id)
            return
        }
        adater!!.data.removeAt(position)
        adater!!.notifyItemRemoved(position)

        this.bean!!.commentCount -= 1
        tv_comment_number.text = "共有${bean!!.commentCount}条评论"
        //发送通知
        val model = DynamicItemStatusEventBus()
        model.isZan = (this.bean!!.giveLike > 0)
        model.commentNumber = this.bean!!.commentCount
        model.isFollow = this.bean!!.concern > 0
        EventBus.getDefault().post(model)
    }
}