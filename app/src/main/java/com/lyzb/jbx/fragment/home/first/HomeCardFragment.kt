package com.lyzb.jbx.fragment.home.first

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.hyphenate.easeui.EaseConstant
import com.like.longshaolib.adapter.BaseRecyleAdapter
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener
import com.like.longshaolib.base.BaseFragment
import com.like.longshaolib.dialog.original.ActionSheetDialog
import com.like.utilslib.image.BitmapUtil
import com.like.utilslib.image.LoadImageUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.adapter.home.first.HomeCardAdapter
import com.lyzb.jbx.dialog.FollowRemindDialog
import com.lyzb.jbx.dialog.ShareDialog
import com.lyzb.jbx.fragment.home.HomeFragment
import com.lyzb.jbx.fragment.home.MeFragment
import com.lyzb.jbx.fragment.me.basic.CardCilpFrgament
import com.lyzb.jbx.fragment.me.card.CardFragment
import com.lyzb.jbx.fragment.me.card.CardPosterFragment
import com.lyzb.jbx.model.account.BusinessModel
import com.lyzb.jbx.model.common.WeiXinMinModel
import com.lyzb.jbx.model.eventbus.DynamicItemStatusEventBus
import com.lyzb.jbx.model.follow.InterestMemberModel
import com.lyzb.jbx.model.me.CardModel
import com.lyzb.jbx.model.me.DoLikeModel
import com.lyzb.jbx.mvp.presenter.home.first.HomeCardPresenter
import com.lyzb.jbx.mvp.view.home.first.IHomeCardView
import com.lyzb.jbx.util.AppPreference
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.szy.yishopcustomer.Activity.LoginActivity
import com.szy.yishopcustomer.Activity.im.ImCommonActivity
import com.szy.yishopcustomer.Constant.Config
import com.szy.yishopcustomer.Util.App
import com.szy.yishopcustomer.Util.WeiXinUtils
import com.szy.yishopcustomer.ViewModel.im.ImHeaderGoodsModel
import kotlinx.android.synthetic.main.fragment_home_card.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * 首页-卡片列表
 */
class HomeCardFragment : BaseFragment<HomeCardPresenter>(), IHomeCardView, OnRefreshLoadMoreListener, View.OnClickListener {

    private var mAdapter: HomeCardAdapter? = null
    private var cardModel: CardModel? = null
    private var mIndustryDialog: ActionSheetDialog? = null
    private var list: MutableList<BusinessModel> = arrayListOf()
    private var parentFragment: HomeFragment? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        parentFragment = getParentFragment() as HomeFragment?
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)

        refresh_card.setOnRefreshLoadMoreListener(this)
        recycle_card.isNestedScrollingEnabled = false
        layout_screen.visibility = View.GONE
        tv_industry.setOnClickListener(this)
        tv_phone.setOnClickListener(this)

        recycle_card.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView!!.layoutManager as LinearLayoutManager
                if (parentFragment != null) {
                    if (layoutManager.findFirstVisibleItemPosition() > 0) {
                        parentFragment?.isShowRefresh(true)
                        isTop = false
                    } else {
                        parentFragment?.isShowRefresh(false)
                        isTop = true
                    }
                }
            }
        })
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        mAdapter = HomeCardAdapter(context, null)
        mAdapter!!.setFastLayoutManager(recycle_card)
        recycle_card.adapter = mAdapter

        recycle_card.addOnItemTouchListener(object : OnRecycleItemClickListener() {

            override fun onItemClick(adapter: BaseRecyleAdapter<*>?, view: View?, position: Int) {
                if (position > 0 && mAdapter!!.list.size > 1) {
                    childDoubleStart(CardFragment.newIntance(2, mAdapter!!.getPositionModel(position).userId))
                }
            }

            override fun onItemChildClick(adapter: BaseRecyleAdapter<*>?, view: View?, position: Int) {
                var itemModel: InterestMemberModel = mAdapter!!.getPositionModel(position)
                when (view!!.id) {
                    R.id.tv_industry -> {
                        if (list == null || list.size == 0) {
                            mPresenter.getIndustryList()
                        } else {
                            showIndustryDialog()
                        }
                    }
                    R.id.tv_phone -> {
                        showPhoneDialog()
                    }
                    R.id.tv_throw_card -> {//递名片
                        showCard()
                    }
                    R.id.tv_card_file -> {//名片夹
                        childDoubleStart(CardCilpFrgament())
                    }
                    R.id.tv_me_info -> {//进入名片
                        childDoubleStart(CardFragment.newIntance(1))
                    }
                    R.id.tv_follow -> {//关注
                        if (App.getInstance().isLogin) {
                            if (itemModel.relationNum > 0) {//在线沟通
                                val intent = Intent(context, ImCommonActivity::class.java)
                                val model = ImHeaderGoodsModel()

                                model.chatType = EaseConstant.CHATTYPE_SINGLE
                                model.shopImName = "jbx" + itemModel.userId
                                model.shopName = itemModel.gsName
                                model.shopHeadimg = itemModel.headimg
                                model.shopId = ""

                                val args = Bundle()
                                args.putSerializable(ImCommonActivity.PARAMS_GOODS, model)
                                intent.putExtras(args)
                                startActivity(intent)
                            } else {//关注某人
                                mPresenter.onDynamciFollowUser(itemModel.userId, 1, position)
                            }
                        } else {
                            startActivity(Intent(context, LoginActivity::class.java))
                        }
                    }
                }
            }
        })

        recycle_card.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManger = recyclerView!!.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManger.findFirstVisibleItemPosition()//可见范围内的第一项的位置
                if (firstVisibleItemPosition >= 1) {
                    if (layout_screen.visibility == View.GONE) {
                        layout_screen.visibility = View.VISIBLE
                    }
                } else {
                    if (layout_screen.visibility == View.VISIBLE) {
                        layout_screen.visibility = View.GONE
                    }
                }
            }
        })

        if (App.getInstance().isLogin) {
            mPresenter.getCardData()
        }
        mPresenter.getCardList(true, mAdapter!!.industryId, mAdapter!!.phoneId)
    }

    override fun getResId(): Any {
        return R.layout.fragment_home_card
    }

    override fun isDelayedData(): Boolean {
        return false
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_industry -> {
                if (list == null || list.size == 0) {
                    mPresenter.getIndustryList()
                } else {
                    showIndustryDialog()
                }
            }
            R.id.tv_phone -> {
                showPhoneDialog()
            }
        }
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getCardList(false, mAdapter!!.industryId, mAdapter!!.phoneId)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPresenter.getCardList(true, mAdapter!!.industryId, mAdapter!!.phoneId)
    }

    override fun onIndustryResult(list: MutableList<BusinessModel>) {
        this.list = list
        showIndustryDialog()
    }

    override fun onFollowItemResult(position: Int) {
        val model = mAdapter!!.getPositionModel(position)
        model.relationNum = if (model.relationNum == 0) 1 else 0
        mAdapter!!.change(position, model)
        //设置第一次关注成功以后的提示
        if (AppPreference.getIntance().firstFollow) {
            FollowRemindDialog()
                    .show(fragmentManager!!, "showFollowHint")
        }
    }

    override fun onCardResult(isRefresh: Boolean, list: MutableList<InterestMemberModel>) {
        if (isRefresh) {
            if (list.size < 10) {
                refresh_card.finishLoadMoreWithNoMoreData()
            }
            refresh_card.finishRefresh()
            mAdapter!!.update(list)
        } else {
            if (list.size < 10) {
                refresh_card.finishLoadMoreWithNoMoreData()
            } else {
                refresh_card.finishLoadMore()
            }
            mAdapter!!.addAll(list)
        }
    }

    override fun onFinshOrLoadMore(isRefresh: Boolean) {
        if (isRefresh) {
            refresh_card.finishRefresh()
        } else {
            refresh_card.finishLoadMore()
        }
    }

    private fun showPhoneDialog() {
        ActionSheetDialog(context)
                .builder()
                .addSheetItem("全部") {
                    mAdapter!!.searchMap["phoneId"] = "0"
                    mAdapter!!.searchMap["phoneName"] = "全部"
                    tv_phone.text = "全部"
                    mAdapter!!.notifyItemChanged(0)
                    mPresenter.getCardList(true, mAdapter!!.industryId, mAdapter!!.phoneId)
                }
                .addSheetItem("有手机号") {
                    mAdapter!!.searchMap["phoneId"] = "1"
                    mAdapter!!.searchMap["phoneName"] = "有手机号"
                    tv_phone.text = "有手机号"
                    mAdapter!!.notifyItemChanged(0)
                    mPresenter.getCardList(true, mAdapter!!.industryId, mAdapter!!.phoneId)
                }
                .addSheetItem("无手机号") {
                    mAdapter!!.searchMap["phoneId"] = "2"
                    mAdapter!!.searchMap["phoneName"] = "无手机号"
                    tv_phone.text = "无手机号"
                    mAdapter!!.notifyItemChanged(0)
                    mPresenter.getCardList(true, mAdapter!!.industryId, mAdapter!!.phoneId)
                }.show()
    }

    private fun showIndustryDialog() {
        mIndustryDialog = ActionSheetDialog(context)
                .builder()
        mIndustryDialog!!.addSheetItem("全部") {
            mAdapter!!.searchMap["industryId"] = "0"
            mAdapter!!.searchMap["industryName"] = "全部"
            tv_industry.text = "全部"
            mAdapter!!.notifyItemChanged(0)
            mPresenter.getCardList(true, mAdapter!!.industryId, mAdapter!!.phoneId)
        }
        for (model in list) {
            mIndustryDialog!!.addSheetItem(model.name) {
                mAdapter!!.searchMap["industryId"] = model.id.toString()
                mAdapter!!.searchMap["industryName"] = model.name
                tv_industry.text = model.name
                mAdapter!!.notifyItemChanged(0)
                mPresenter.getCardList(true, mAdapter!!.industryId, mAdapter!!.phoneId)
            }
        }
        mIndustryDialog!!.show()
    }

    override fun onCardData(model: CardModel) {
        cardModel = model
        mAdapter!!.noticeFirstItem(model)
        initCardInfo(model)
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        mView.postDelayed(object : Runnable {
            override fun run() {
                val meFragment = (parentFragment as BaseFragment<*>).findFragment(MeFragment::class.java)
                if (meFragment != null) {
                    if (cardModel != meFragment.mModel) {
                        cardModel = meFragment.mModel
                        if (cardModel != null) {
                            mAdapter!!.noticeFirstItem(meFragment.mModel)
                            initCardInfo(meFragment.mModel)
                        }
                    }
                }
                if (cardModel == null) {
                    if (App.getInstance().isLogin) {
                        mPresenter.getCardData()
                    }
                }
                if (parentFragment != null) {
                    parentFragment!!.isShowRefresh(!isTop)
                }
            }
        }, 500)
    }

    /**
     * 初始化名片信息块
     */
    private fun initCardInfo(model: CardModel) {
        if (model == null) return
        LoadImageUtil.loadRoundImage(img_card_head, model.headimg, 4)
        if (model.myViewUserList.size > 0) {
            img_vip.visibility = View.VISIBLE
        } else {
            img_vip.visibility = View.GONE
        }
        tv_card_name.text = model.gsName
        tv_card_position.text = model.position
        LoadImageUtil.loadImage(img_company_logo, model.shopLogo)
        tv_card_phone.text = model.mobile
    }

    private fun showCard() {
        layout_card_info.visibility = View.VISIBLE
        ShareDialog.newInstance().invoke(object : ShareDialog.ClickListener {
            override fun click(v: View?) {
                if (v == null) {
                    layout_card_info.visibility = View.GONE
                    return
                }
                when (v!!.id) {
                    R.id.tv_share_wx -> {//分享微信
                        //分享时掉次分享接口，3.9.1
                        val likeModel1 = DoLikeModel()
                        likeModel1.handleId = cardModel!!.id
                        likeModel1.operType = 2
                        likeModel1.recordType = 0
                        mPresenter.doLike(false, true, likeModel1)

                        val model = WeiXinMinModel()
                        model.lowVersionUrl = Config.BASE_URL
                        if (TextUtils.isEmpty(AppPreference.getIntance().shareCardValue)) {
                            model.title = cardModel!!.gsName + "的智能名片"
                        } else {
                            model.title = AppPreference.getIntance().shareCardValue
                        }
                        model.describe = " "
                        model.shareUrl = ("/pages/card/card?id=" + cardModel!!.userId + "&gsName=" + cardModel!!.gsName
                                + "&shareFromId=" + App.getInstance().userId + "&originUserId=" + cardModel!!.userId)
                        var bitmap = BitmapUtil.createViewBitmap(layout_card_info)
                        bitmap = BitmapUtil.zoomMaxImage(bitmap, 500.0, 400.0)//分享的图片配置

                        WeiXinUtils.shareWXMiniProject(context,
                                model.lowVersionUrl, model.title, model.describe, model.shareUrl,
                                BitmapUtil.zoomImage(bitmap, 500.0, 400.0))
                        layout_card_info.visibility = View.GONE
                    }
                    R.id.tv_share_haibao// 生成海报
                    -> {
                        layout_card_info.visibility = View.GONE
                        childDoubleStart(CardPosterFragment.newIncetance(cardModel!!))
                    }
                    R.id.tv_cancel -> {
                        layout_card_info.visibility = View.GONE
                    }
                }
            }
        }).show(fragmentManager!!, "shareTag")
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun notifyItemChange(eventBus: DynamicItemStatusEventBus) {
        if (eventBus.isLoginOut) {
            mPresenter.getCardList(true, mAdapter!!.industryId, mAdapter!!.phoneId)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    fun scrollToTop() {
        recycle_card.smoothScrollToPosition(0)
        recycle_card.postDelayed(object : Runnable {
            override fun run() {
                if (App.getInstance().isLogin) {
                    mPresenter.getCardData()
                }
                mPresenter.getCardList(true, mAdapter!!.industryId, mAdapter!!.phoneId)
            }
        }, 500)
    }
}