package com.lyzb.jbx.fragment.me.company

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.like.longshaolib.adapter.BaseRecyleAdapter
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener
import com.like.longshaolib.base.BaseFragment
import com.like.longshaolib.dialog.fragment.AlertDialogFragment
import com.like.utilslib.image.LoadImageUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.adapter.me.CompanyHomeAdapter
import com.lyzb.jbx.adapter.me.company.MyCompanyAdapter
import com.lyzb.jbx.adapter.me.company.StaffAdapter
import com.lyzb.jbx.fragment.circle.CircleDetailFragment
import com.lyzb.jbx.fragment.me.card.CardFragment
import com.lyzb.jbx.fragment.me.customerManage.CustomerManageCompanyFragment
import com.lyzb.jbx.fragment.statistics.StatisticsHomeFragment
import com.lyzb.jbx.model.me.SetComdModel
import com.lyzb.jbx.model.me.company.OrganDetailModel
import com.lyzb.jbx.model.me.company.OrganThreeLvModel
import com.lyzb.jbx.model.me.company.OrganTowLvModel
import com.lyzb.jbx.model.me.company.StaffModel
import com.lyzb.jbx.model.msg.EventBusMsg
import com.lyzb.jbx.model.params.RemoveMembersBody
import com.lyzb.jbx.mvp.presenter.me.company.OrganDetailPresenter
import com.lyzb.jbx.mvp.view.me.IOrganDetailView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.szy.yishopcustomer.Constant.Config
import com.szy.yishopcustomer.Util.App
import com.szy.yishopcustomer.Util.BrowserUrlManager
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import kotlinx.android.synthetic.main.fragment_organ_detail.*
import org.greenrobot.eventbus.EventBus


/**
 *@author wyx
 *@version
 *@role 企业主页
 *@time 2019 2019/6/14 10:39
 */
class OrganDetailFragment : BaseFragment<OrganDetailPresenter>(), OnRefreshLoadMoreListener,
        View.OnClickListener, IOrganDetailView {

    var title = ""

    var mOrganId: String = ""
    /**
     * 企业下的那一堆按钮
     */
    var mFunctionAdapter: CompanyHomeAdapter? = null
    /**
     * 企业、机构列表
     */
    var mOrganAdapter: MyCompanyAdapter = MyCompanyAdapter(null)

    /**
     * 企业人员列表
     */
    var mStaffAdapter: StaffAdapter? = null

    var mOrganDetailModel: OrganDetailModel = OrganDetailModel()

    companion object {
        fun newIntance(companyId: String): OrganDetailFragment {
            val fragment = OrganDetailFragment()
            fragment.mOrganId = companyId
            return fragment
        }
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        //先把界面隐藏了，接口返回成功再显示，不然难得处理
        organ_detail_scrollview.visibility = View.GONE
        //返回键监听
        organ_detail_back_iv.setOnClickListener(this)
        //下拉刷新监听
        organ_detail_smartrefreshlayout.setOnRefreshLoadMoreListener(this)
        //控制title的背景
        organ_detail_scrollview.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            val bgHeight = organ_detail_topbg_iv.height
            if (Math.abs(scrollY) > bgHeight / 1.2) {
                organ_detail_title_rl.setBackgroundColor(Color.parseColor("#ffffff"))
                organ_detail_back_iv.setImageResource(R.mipmap.btn_back_dark)
                organ_detail_title_line.visibility = View.VISIBLE
                organ_detail_title_tv.text = title
            } else {
                organ_detail_title_rl.setBackgroundColor(Color.parseColor("#00000000"))
                organ_detail_back_iv.setImageResource(R.drawable.ic_video_back)
                organ_detail_title_line.visibility = View.GONE
                organ_detail_title_tv.text = ""
            }
        }

        //设置编辑机构信息监听
        organ_detail_edit_iv.setOnClickListener(this)
        //设为当前机构监听
        organ_detail_setcurrent_tv.setOnClickListener(this)
        //机构下的各种功能
        mFunctionAdapter = CompanyHomeAdapter(_mActivity, null)
        mFunctionAdapter!!.setGridLayoutManager(organ_detail_function_rv, 4)
        organ_detail_function_rv.adapter = mFunctionAdapter
        organ_detail_function_rv.addOnItemTouchListener(object : OnRecycleItemClickListener() {
            override fun onItemClick(adapter: BaseRecyleAdapter<*>?, view: View?, position: Int) {
                super.onItemClick(adapter, view, position)
                val itemModel = mFunctionAdapter!!.getPositionModel(position)
                functionClick(itemModel)
            }
        })
        //机构信息列表
        val manage = LinearLayoutManager(_mActivity)
        manage.orientation = LinearLayoutManager.VERTICAL
        organ_detail_organ_rv.layoutManager = manage
        organ_detail_organ_rv.adapter = mOrganAdapter
        mOrganAdapter.setOnItemClickListener { adapter, view, position ->
            //item的点击，刷新当前页面
            val itemModel = adapter!!.getItem(position) as MultiItemEntity
            //只处理2、3级item，1级就是当前页面   不管
            when (itemModel.itemType) {
                MyCompanyAdapter.TYPE_TOWLV_ORGAN -> {
                    itemModel as OrganTowLvModel
                    mPresenter.getOrganDetail(true, itemModel.id)
                }
                MyCompanyAdapter.TYPE_THREELV_ORGAN -> {
                    itemModel as OrganThreeLvModel
                    mPresenter.getOrganDetail(true, itemModel.id)
                }
            }
        }

        //员工列表
        mStaffAdapter = StaffAdapter(_mActivity, null)
        mStaffAdapter!!.setLayoutManager(organ_detail_staff_rv)
        organ_detail_staff_rv.adapter = mStaffAdapter
        organ_detail_staff_rv.addOnItemTouchListener(object : OnRecycleItemClickListener() {
            override fun onItemChildClick(adapter: BaseRecyleAdapter<*>?, view: View?, position: Int) {
                val staffModel = adapter!!.getPositionModel(position) as StaffModel
                when (view!!.id) {
                    R.id.staff_item_edit_iv -> {
                        start(AddStaffFragment.newIntance(AddStaffFragment.TYPE_EDIT, mOrganId, staffModel.userId, ""))
                    }
                    R.id.staff_item_head_iv -> {
                        start(CardFragment.newIntance(2, staffModel.userId))
                    }
                }

            }
        })
    }

    override fun onInitData(savedInstanceState: Bundle?) {
    }

    override fun getResId(): Any {
        return R.layout.fragment_organ_detail
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        mView.postDelayed({ mPresenter.getOrganDetail(true, mOrganId) }, 500)
    }

    /**
     * 机构信息
     */
    override fun onOrganDetail(b: OrganDetailModel) {
        //机构信息获取成功之后，去获取申请人数
        mPresenter.getCompanyApplyNumber(mOrganId)
        organ_detail_smartrefreshlayout.finishRefresh()
        mOrganDetailModel = b
        mOrganId = b.data.id
        title = b.data.companyName
        //机构信息获取成功之后就把界面展示出
        organ_detail_scrollview.visibility = View.VISIBLE

        if (b.data.disType == "2") {
            //不是企业就隐藏logo、地址、电话
            organ_detail_head_iv.visibility = View.GONE
            organ_detail_phone_tv.visibility = View.GONE
            organ_detail_address_tv.visibility = View.GONE
            organ_detail_industry_tv.text = String.format("所属企业：%s", b.data.parentOrgName)
        } else {
            //机构logo
            LoadImageUtil.loadRoundImage(organ_detail_head_iv, b.data.logoFilePath, 4)
            //电话
            organ_detail_phone_tv.text = b.data.telPhone
            //地址
            organ_detail_address_tv.text = b.data.address
            //行业
            //行业and成员数，没有行业时仅显示成员数
            var members = String.format("%d成员", b.data.membersNum)
            if (!b.data.industryName.isNullOrEmpty()) {
                members = b.data.industryName + "·" + members
            }
            organ_detail_industry_tv.text = members
        }
        //机构名称
        organ_detail_name_tv.text = b.data.companyName
        //是否展示编辑机构按钮
        if (b.isShowEditBtn) {
            organ_detail_edit_iv.visibility = View.VISIBLE
        } else {
            organ_detail_edit_iv.visibility = View.GONE
        }
        //是否展示设为当前机构
        if (b.isShowSetBtn) {
            organ_detail_setcurrent_tv.visibility = View.VISIBLE
            //是否展示“修改机构信息按钮”、是否展示修改成员按钮
            if (b.data.isSelectSt) {
                organ_detail_setcurrent_tv.text = "已设为当前机构"
                organ_detail_setcurrent_tv.setTextColor(ContextCompat.getColor(_mActivity, R.color.fontcColor3))
                organ_detail_setcurrent_tv.setBackgroundResource(R.drawable.st_999999_r2)
            } else {
                organ_detail_setcurrent_tv.text = "设为当前机构"
                organ_detail_setcurrent_tv.setTextColor(ContextCompat.getColor(_mActivity, R.color.fontcColor1))
                organ_detail_setcurrent_tv.setBackgroundResource(R.drawable.bg_stock_gray)
            }
        } else {
            organ_detail_setcurrent_tv.visibility = View.GONE
        }
        //机构成员
        mStaffAdapter!!.update(b.data.membersVoList)
        //机构列表
        mOrganAdapter.setNewData(b.data.companyList as List<MultiItemEntity>)
        mOrganAdapter.expandAll()
        //按钮集
        if (b.data.authList != null && b.data.authList.size > 0 && b.data.authList[0].childResource != null) {
            mFunctionAdapter!!.update(b.data.authList[0].childResource)
        }else{
            mFunctionAdapter!!.update(emptyList())
        }
    }

    /**
     * 加载更多员工
     */
    override fun loadMoreStaff(list: MutableList<StaffModel>) {
        mStaffAdapter!!.addAll(list)
        organ_detail_smartrefreshlayout.finishLoadMore()
    }

    /**
     * 错误的回调
     */
    override fun onFail(code: String) {
        if (code == "203") {
            pop()
        } else {
            organ_detail_smartrefreshlayout.finishRefresh()
            organ_detail_smartrefreshlayout.finishLoadMore()
        }
    }

    /**
     * 获取企业申请人数
     */
    override fun onCompanyApplyNumber(number: Int) {
        for (bean in mFunctionAdapter!!.list) {
            bean as OrganDetailModel.DataBean.AuthListBean
            if (bean.display == "申请列表") {
                bean.messageNumber = number
            }
        }
        mFunctionAdapter!!.notifyDataSetChanged()
    }

    /**
     * 设为当前机构成功
     */
    override fun setDefaultOrganSuccess() {
        mOrganDetailModel.data.isSelectSt = true
        organ_detail_setcurrent_tv.setTextColor(ContextCompat.getColor(_mActivity, R.color.fontcColor3))
        organ_detail_setcurrent_tv.text = "已设为当前机构"
        val eventBusMsg = EventBusMsg()
        eventBusMsg.setType(1)
        eventBusMsg.setMsg("当前是从个人名片中的官网跳转过来")
        if (mOrganDetailModel.data.disType == "2") {
            eventBusMsg.setCompanyId(mOrganDetailModel.data.parentOrgId)
        } else {
            eventBusMsg.setCompanyId(mOrganDetailModel.data.id)
        }
        EventBus.getDefault().post(eventBusMsg)
    }

    /**
     * 退出企业
     */
    override fun onExitCompanySuccess() {
        pop()
    }


    /**
     * 企业下一堆按钮的点击
     */

    fun functionClick(itemModel: OrganDetailModel.DataBean.AuthListBean) {
        when (itemModel.class2) {
            "0", "APP" -> {
                when (itemModel.code) {
                    //添加机构
                    "APP0201" -> {
                        start(AddOrganFragment.newInstance(0, mOrganDetailModel.data.disType, mOrganId, mOrganDetailModel.data.parentOrgId))
                    }
                    //添加员工
                    "APP0202" -> {
                        start(AddStaffFragment.newIntance(AddStaffFragment.TYPE_ADD, mOrganId, "", ""))
                    }
                    //企业官网
                    "APP0203" -> {
                        if (itemModel.childResource == null) {
                            start(CompanyWebFragment.newInstance(mOrganId, mOrganDetailModel.data.groupId, mOrganDetailModel.data.companyName, 0))
                        } else {
                            start(CompanyWebFragment.newInstance(mOrganId, mOrganDetailModel.data.groupId, mOrganDetailModel.data.companyName, 1))
                        }

                    }
                    //企业圈子
                    "APP0204" -> {
                        if (TextUtils.isEmpty(mOrganDetailModel.data.groupId)) {
                            showToast("该企业暂未关联圈子")
                        } else {
                            start(CircleDetailFragment.newIntance(mOrganDetailModel.data.groupId))
                        }
                    }
                    //申请列表
                    "APP0205" -> {
                        start(CompanyApplyListFragment.newIntance(mOrganId))
                    }
                    //管理员
                    "APP0206" -> {
                        start(OrganAdminFragment.newIntance(mOrganId))
                    }
                    //企业商城
                    "APP0207" -> {
                        start(CompanyMallFragment.newInstance(1, mOrganId, mOrganDetailModel.data.creatorId))
                    }
                    //数据统计
                    "APP0208" -> {
                        start(StatisticsHomeFragment.newIntance(mOrganId))
                    }
                    //客户管理
                    "APP0209" -> {
                        start(CustomerManageCompanyFragment.newIntance(mOrganId))
                    }
                    //申请经销
                    "APP0210" -> {
                        start(ApplyIdentityFragment.newIntance(1, mOrganId))
                    }
                    //申请运营
                    "APP0211" -> {
                        start(ApplyIdentityFragment.newIntance(2, mOrganId))
                    }
                    //退出企业
                    "APP0212" -> {
                        showExitCompanyDialog("exit", "确定要退出该企业吗？")
                    }
                    //解散企业
                    "APP0213" -> {
                        showExitCompanyDialog("del", "解散企业后，所有员工也会自动退出企业，关联的圈子会变为普通圈子，确定要解散该企业吗？")
                    }
                }
            }
            //跳H5
            "1", "H5" -> {
                var url = itemModel.pathAndroid
                url += "?userId=" + App.getInstance().userId
                BrowserUrlManager(url).parseUrl(activity, url)
            }
            //跳小程序
            "02", "WXMINI" -> {
                val api = WXAPIFactory.createWXAPI(_mActivity, Config.WEIXIN_APP_ID, true)
                api!!.registerApp(Config.WEIXIN_APP_ID)
                val req = WXLaunchMiniProgram.Req()
                req.userName = itemModel.pathAndroid // 填小程序原始id
                req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE// 可选打开 开发版，体验版和正式版
                api.sendReq(req)
            }
        }

    }

    /**
     * 退出企业
     */
    private fun showExitCompanyDialog(type: String, content: String) {
        AlertDialogFragment.newIntance().setKeyBackable(false)
                .setCancleable(false)
                .setTitle("提示")
                .setCancleBtn(null)
                .setContent(content)
                .setSureBtn {
                    if (type == "del") {
                        mPresenter.deleteCompany(mOrganId)
                    } else if (type == "exit") {
                        val body = RemoveMembersBody()
                        body.companyId = mOrganId
                        body.removeType = 2
                        body.userId = App.getInstance().userId
                        mPresenter.exitCompany(body)
                    }
                }
                .show(fragmentManager!!, "LogOutTag")
    }

    /**
     * 设为当前机构弹框
     */
    private fun showSetDefaultOrganDialog() {
        AlertDialogFragment.newIntance().setKeyBackable(false)
                .setCancleable(false)
                .setTitle("提示")
                .setCancleBtn(null)
                .setContent("切换机构后，您名片中的机构相关信息将展示为新机构的内容，确定切换吗？")
                .setSureBtn {
                    mPresenter.setComd(SetComdModel(mOrganId))
                }
                .show(fragmentManager!!, "LogOutTag")
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPresenter.getOrganDetail(true, mOrganId)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getOrganDetail(false, mOrganId)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            //返回键
            R.id.organ_detail_back_iv -> {
                pop()
            }
            //编辑机构信息
            R.id.organ_detail_edit_iv -> {
                start(AddOrganFragment.newInstance(1, mOrganDetailModel.data.disType, mOrganId, mOrganDetailModel.data.parentOrgId))
            }
            //设为当前机构
            R.id.organ_detail_setcurrent_tv -> {
                if (!mOrganDetailModel.data.isSelectSt) {
                    showSetDefaultOrganDialog()
                }
            }
        }
    }
}