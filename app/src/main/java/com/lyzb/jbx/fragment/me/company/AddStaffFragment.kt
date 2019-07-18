package com.lyzb.jbx.fragment.me.company

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.like.longshaolib.adapter.BaseRecyleAdapter
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener
import com.like.longshaolib.base.BaseFragment
import com.lyzb.jbx.R
import com.lyzb.jbx.adapter.me.company.AddStaffOrganAdapter
import com.lyzb.jbx.dialog.EditCompanyAccountsDialog
import com.lyzb.jbx.model.me.company.AccountPrefixModel
import com.lyzb.jbx.model.me.company.ChildrenBean
import com.lyzb.jbx.model.me.company.StaffInfoModel
import com.lyzb.jbx.model.params.AddStaffBody
import com.lyzb.jbx.model.params.AuditMembersBody
import com.lyzb.jbx.mvp.presenter.me.company.AddStaffPresenter
import com.lyzb.jbx.mvp.view.me.IAddStaffView
import kotlinx.android.synthetic.main.fragment_addstaff.*
import kotlinx.android.synthetic.main.toolbar_statistics.*

/**
 *@author wyx
 *@version
 *@role
 *@time 2019 2019/6/14 14:46
 */
class AddStaffFragment : BaseFragment<AddStaffPresenter>(), IAddStaffView, View.OnClickListener,
        ChoiceOrganFragment.OnChoiceOrganListener {

    /**
     * add=添加员工，apply=申请加入,edit_add=编辑员工(企业添加),edit_apply(申请加入的)
     */
    private var mType = "add"

    private var mStaffId = ""

    private var mOrganId = ""

    private var mApplyId = ""

    private var mOrganAdapter: AddStaffOrganAdapter? = null
    /**
     * 员工信息，编辑、申请加入时
     */
    private var mStaffInfoModel: StaffInfoModel.DataBean = StaffInfoModel().DataBean()
    /**
     * 验证码弹框
     */
    private var editCompanyAccountsDialog = EditCompanyAccountsDialog.newIntance("", "")

    private var mPassword = ""

    private var mStaffUserId = ""

    companion object {
        const val TYPE_ADD = "add"
        const val TYPE_APPLY = "apply"
        const val TYPE_EDIT = "edit"

        fun newIntance(type: String, organId: String, staffId: String, applyId: String): AddStaffFragment {
            val fragment = AddStaffFragment()
            fragment.mType = type
            fragment.mStaffId = staffId
            fragment.mOrganId = organId
            fragment.mApplyId = applyId
            return fragment
        }
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        initCommon()
        when (mType) {
            TYPE_ADD -> {
                statistics_title_tv.text = "添加员工"
                mPresenter.getAccountPrefix(mOrganId)
            }
            TYPE_APPLY -> {
                statistics_title_tv.text = "同意加入"
                initApply()
                mPresenter.getUserInfo(mStaffId)
            }
            //编辑
            TYPE_EDIT -> {
                statistics_title_tv.text = "编辑员工"
                mPresenter.getStaffDetail(mStaffId, mOrganId)
            }
        }
        statistics_back_iv.setOnClickListener(this)
        addstaff_organ_tv.setOnClickListener(this)
        addstaff_organ_iv.setOnClickListener(this)
        addstaff_sure_tv.setOnClickListener(this)

        mOrganAdapter = AddStaffOrganAdapter(_mActivity, null)
        mOrganAdapter!!.setLayoutManager(addstaff_organ_rv)
        addstaff_organ_rv.adapter = mOrganAdapter
        addstaff_organ_rv.addOnItemTouchListener(object : OnRecycleItemClickListener() {
            override fun onItemChildClick(adapter: BaseRecyleAdapter<*>?, view: View?, position: Int) {
                super.onItemChildClick(adapter, view, position)
                val itemModel = adapter!!.getPositionModel(position) as ChildrenBean
                when (view!!.id) {
                    R.id.addstaff_organ_item_staff_rbtn -> {
                        itemModel.role = "2"
                    }
                    R.id.addstaff_organ_item_admin_rbtn -> {
                        itemModel.role = "3"
                    }
                }
            }
        })
    }

    override fun onInitData(savedInstanceState: Bundle?) {
    }

    override fun getResId(): Any {
        return R.layout.fragment_addstaff
    }

    /**
     * 初始化添加员工外通用的东西
     */
    private fun initCommon() {
        if (mType == TYPE_ADD) return

        addstaff_accounts_prefix_ll.visibility = View.GONE
        addstaff_accounts_prefix_line.visibility = View.GONE
        addstaff_accounts_hint_tv.visibility = View.GONE
        addstaff_password_hint_tv.visibility = View.GONE
        addstaff_name_line.visibility = View.VISIBLE
    }

    /**
     * 初始化申请加入的员工
     */
    private fun initApply() {
        addstaff_accounts.text = "帐号"
        addstaff_name_edit.hint = ""
        addstaff_phone_edit.hint = ""
        //电话不可改
        addstaff_phone_edit.isFocusable = false
        addstaff_phone_edit.isFocusableInTouchMode = false
        //姓名不可改
        addstaff_name_edit.isFocusable = false
        addstaff_name_edit.isFocusableInTouchMode = false
        addstaff_password_ll.visibility = View.GONE
        if (mType != TYPE_APPLY) {
            addstaff_delete_tv.visibility = View.VISIBLE
            addstaff_delete_tv.setOnClickListener(this)
        }
    }

    /**
     * 初始化企业添加
     */
    private fun initCompanyAdd() {
        addstaff_accounts_prefix_ll.visibility = View.GONE
        addstaff_accounts_prefix_line.visibility = View.GONE
        addstaff_setlogin_line.visibility = View.VISIBLE
        addstaff_setlogin_ll.visibility = View.VISIBLE
        addstaff_accounts_line.visibility = View.VISIBLE
        addstaff_password_tv.text = "修改密码"
        addstaff_password_edit.hint = ""
        addstaff_setlogin_ll.setOnClickListener(this)
    }

    /**
     * 添加员工时获取帐号前缀
     */
    override fun getAccountPrefixSuccess(model: AccountPrefixModel.DataBean) {
        //有前缀时不可更改
        if (TextUtils.isEmpty(model.prefix)) return
        addstaff_accounts_prefix_edit.setText(model.prefix)
        addstaff_accounts_prefix_edit.isFocusable = false
        addstaff_accounts_prefix_edit.isFocusableInTouchMode = false
        addstaff_accounts_tv.text = model.account
        mStaffUserId = model.userId
    }


    /**
     * 编辑、同意加入时获取员工信息
     */
    override fun getStaffInfoSuccess(model: StaffInfoModel.DataBean) {
        mStaffInfoModel = model
        mStaffUserId = model.userId
        addstaff_accounts_tv.text = model.accountName
        addstaff_setlogin_iv.visibility = View.VISIBLE
        if (model.accountSt == 0) {
            addstaff_setlogin_iv.setImageResource(R.mipmap.ic_canlogin_unchecked)
        } else {
            addstaff_setlogin_iv.setImageResource(R.mipmap.ic_canlogin_selection)
        }
        //能否删除
        if (model.isCanDel) {
            initApply()
        } else if (mType == TYPE_EDIT) {
            addstaff_delete_tv.visibility = View.GONE
            initCompanyAdd()
        }
        addstaff_phone_edit.setText(model.mobile)
        addstaff_name_edit.setText(model.gsName)

        if (mType == TYPE_EDIT) {
            mOrganList = model.orgList
            mOrganAdapter!!.update(model.orgList)
        }
    }

    /**
     * 生成密码成功
     */
    override fun getPassWordSuccess(password: String) {
        mPassword = password
        checkPhone()
    }

    /**
     * 审批通过
     */
    override fun onAudit() {
        pop()
    }

    /**
     * 手机号校验成功
     */
    override fun checkPhoneSuccess(code: String, content: String) {
        showCodeDialog(code, content)
    }

    /**
     * 获取验证码成功
     */
    override fun onSendMSMSuccess() {
        editCompanyAccountsDialog.sendCode()
    }

    /**
     * 验证码校验成功
     */
    override fun onValidateMobileCodeSuccess() {
        if (editCompanyAccountsDialog != null) {
            editCompanyAccountsDialog.dismiss()
        }
        addStaff()
    }

    /**
     * 添加or编辑员工成功
     */
    override fun onAddStaffSuccess() {
        pop()
    }

    private var mOrganList: MutableList<ChildrenBean> = mutableListOf()
    /**
     * 选择机构回调
     */
    override fun onChoiceOrganListener(organs: MutableList<ChildrenBean>) {
        mOrganList = organs
        mOrganAdapter!!.update(organs)
    }

    /**
     * 验证码的弹框
     */
    private fun showCodeDialog(status: String, content: String) {
        editCompanyAccountsDialog = EditCompanyAccountsDialog.newIntance(status, content)
        editCompanyAccountsDialog.onClickListener = EditCompanyAccountsDialog.OnClickListener { view, status, code ->
            when (view!!.id) {
                R.id.tv_confirm -> if (TextUtils.isEmpty(code)) {
                    showToast("请填写验证码")
                } else {
                    mPresenter.validateMobile(addstaff_phone_edit.text.toString(), code)
                }
                //发送验证码
                R.id.btn_code -> mPresenter.onSendCode(addstaff_phone_edit.text.toString())
            }
        }
        editCompanyAccountsDialog.show(fragmentManager, "EditCompanyAccountsDialog")
        mPresenter.onSendCode(addstaff_phone_edit.text.toString())
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            //返回
            R.id.statistics_back_iv -> {
                pop()
            }
            //是否可登录
            R.id.addstaff_setlogin_ll -> {
                if (mStaffInfoModel.accountSt == 0) {
                    mStaffInfoModel.accountSt = 1
                    addstaff_setlogin_iv.setImageResource(R.mipmap.ic_canlogin_selection)
                } else {
                    mStaffInfoModel.accountSt = 0
                    addstaff_setlogin_iv.setImageResource(R.mipmap.ic_canlogin_unchecked)
                }
            }
            //选择机构
            R.id.addstaff_organ_tv, R.id.addstaff_organ_iv -> {
                start(ChoiceOrganFragment.newIntance(mOrganId, "2", "2", this))
            }
            //确定
            R.id.addstaff_sure_tv -> {
                if (addstaff_accounts_prefix_edit.text.length < 3 && mType == TYPE_ADD) {
                    showToast("请输入帐号前缀")
                    return
                }
                if (mOrganList.size < 1) {
                    showToast("请选择机构")
                    return
                }
                if (mType != TYPE_APPLY && addstaff_password_edit.text.length > 1) {
                    if (addstaff_password_edit.text.length < 6) {
                        showToast("请输入6位以上密码")
                    } else {
                        mPresenter.getPassWord(addstaff_password_edit.text.toString())
                    }
                    return
                }
                checkPhone()
            }
            //删除员工
            R.id.addstaff_delete_tv -> {
                val body = AddStaffBody()
                body.userId = mStaffInfoModel.userId
                body.optType = "del"
                body.orgId = mOrganId
                mPresenter.addOrDeleteStaff(body)
            }
        }
    }

    /**
     *判断是否更改手机号
     */
    private fun checkPhone() {
        //申请加入的时候不管手机号
        if (mType != TYPE_APPLY && addstaff_phone_edit.text.length in 1..10) {
            showToast("请输入正确手机号")
            return
        }
        if (addstaff_phone_edit.text.length == 11 && addstaff_phone_edit.text.toString() != mStaffInfoModel.mobile) {
            mPresenter.checkPhone(addstaff_phone_edit.text.toString())
            return
        }
        addStaff()
    }

    /**
     * 添加、修改员工
     */
    private fun addStaff() {
        val body = AddStaffBody()
        when (mType) {
            //添加成员
            TYPE_ADD -> {
                body.optType = "add"
            }
            //同意申请加入
            TYPE_APPLY -> {
                val applyBody = AuditMembersBody()
                applyBody.applyId = mApplyId
                applyBody.auditState = 1
                applyBody.memberRoles = mOrganList
                mPresenter.onAudit(applyBody)
                return
            }
            //编辑成员
            else -> {
                body.optType = "upt"
            }

        }
        body.userId = mStaffUserId
        body.status = mStaffInfoModel.accountSt
        body.fullName = addstaff_name_edit.text.toString()
        if (!mStaffInfoModel.isCanDel && mType != TYPE_APPLY) {
            body.pwd = mPassword
            body.prefix = addstaff_accounts_prefix_edit.text.toString()
            body.account = addstaff_accounts_tv.text.toString()
        }
        if (addstaff_phone_edit.text.isNotEmpty()) {
            body.mobile = addstaff_phone_edit.text.toString()
        }
        body.orgId = mOrganId
        body.memberRoles = mOrganList
        mPresenter.addOrDeleteStaff(body)
    }
}