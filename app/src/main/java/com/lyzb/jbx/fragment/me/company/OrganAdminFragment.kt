package com.lyzb.jbx.fragment.me.company

import android.os.Bundle
import android.view.View
import com.like.longshaolib.adapter.BaseRecyleAdapter
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener
import com.like.longshaolib.base.BaseFragment
import com.lyzb.jbx.R
import com.lyzb.jbx.adapter.me.company.OrganAdminAdapter
import com.lyzb.jbx.model.me.company.OrganNenberModel
import com.lyzb.jbx.model.params.ChangeAdminBody
import com.lyzb.jbx.mvp.presenter.me.company.OrganAdminPresenter
import com.lyzb.jbx.mvp.view.me.IOrganAdminView
import kotlinx.android.synthetic.main.fragment_organ_admin.*
import kotlinx.android.synthetic.main.toolbar_statistics.*

/**
 *@author wyx
 *@version
 *@role 机构管理员,添加、删除管理员都在这个页面，所以逻辑可能有点复杂
 *@time 2019 2019/6/15 9:56
 */
class OrganAdminFragment : BaseFragment<OrganAdminPresenter>(), IOrganAdminView, View.OnClickListener {
    var mOrganId = ""

    var mMemberAdapter: OrganAdminAdapter? = null

    var mMemberList: MutableList<OrganNenberModel.DataBean> = mutableListOf()

    var mAdminList: MutableList<OrganNenberModel.DataBean> = mutableListOf()

    var mBodyList: MutableList<ChangeAdminBody> = mutableListOf()
    /**
     * 0默认状态，1删除管理员页面，2完成添加管理员页面
     */
    var type = 0

    companion object {
        /**
         * 创建者
         */
        const val ROLE_TYPE_CREATOR = "1"
        /**
         * 员工
         */
        const val ROLE_TYPE_STAFF = "2"
        /**
         * 管理员
         */
        const val ROLE_TYPE_ADMIN = "3"
        /**
         * 负责人
         */
        const val ROLE_TYPE_PERSON_IN_CHARGE = "4"

        fun newIntance(organId: String): OrganAdminFragment {
            val fragment = OrganAdminFragment()
            fragment.mOrganId = organId
            return fragment
        }
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        statistics_back_iv.setOnClickListener(this)
        statistics_right_tv.setOnClickListener(this)
        organ_admin_add_tv.setOnClickListener(this)

        statistics_title_tv.text = "管理员"
        statistics_right_tv.text = "编辑"
        statistics_right_tv.visibility = View.VISIBLE
        mMemberAdapter = OrganAdminAdapter(_mActivity, null)
        mMemberAdapter!!.setLayoutManager(organ_admin_rv)
        organ_admin_rv.adapter = mMemberAdapter
        organ_admin_rv.addOnItemTouchListener(object : OnRecycleItemClickListener() {
            override fun onItemClick(adapter: BaseRecyleAdapter<*>?, view: View?, position: Int) {
                super.onItemClick(adapter, view, position)
                //默认状态时不处理
                if (type == 0) return
                val itemModel = adapter!!.getPositionModel(position) as OrganNenberModel.DataBean
                when (itemModel.roleType) {
                    //创建者和负责人不可变更
                    ROLE_TYPE_CREATOR, ROLE_TYPE_PERSON_IN_CHARGE -> return
                    //管理员只能在删除时可选中
                    ROLE_TYPE_ADMIN -> {
                        if (type != 1) {
                            return
                        }
                    }
                }
                itemModel.isSelection = !itemModel.isSelection
                mBodyList.clear()
                for (model in adapter.list) {
                    model as OrganNenberModel.DataBean
                    if (!model.isSelection) continue
                    val body = ChangeAdminBody()
                    //加进选中列表时把状态添加进去type=1删除，type=2添加
                    if (type == 1) {
                        body.optType = "del"
                    } else {
                        body.optType = "add"
                    }
                    body.orgId = mOrganId
                    body.userId = model.userId
                    mBodyList.add(body)
                }
                if (type == 1) {
                    organ_admin_add_tv.text = String.format("删除(%d)", mBodyList.size)
                } else {
                    organ_admin_add_tv.text = String.format("确定(%d)", mBodyList.size)
                }
                if (mBodyList.size < 1) {
                    organ_admin_add_tv.setBackgroundResource(R.drawable.sl_99999_r3)
                } else {
                    organ_admin_add_tv.setBackgroundResource(R.drawable.shape_blue_radius_bg)
                }
                mMemberAdapter!!.notifyDataSetChanged()
            }
        })
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        mPresenter.getOrganMember(mOrganId, "all")
    }

    override fun getResId(): Any {
        return R.layout.fragment_organ_admin
    }

    /**
     * 获取员工数据成功，获取的所有
     */
    override fun getMenberListSuccess(list: MutableList<OrganNenberModel.DataBean>) {
        //每次都清空，后面再加进去
        mMemberList.clear()
        mAdminList.clear()
        mMemberList.addAll(list)
        //管理员列表
        for (model: OrganNenberModel.DataBean in list) {
            if (model.roleType != ROLE_TYPE_STAFF) mAdminList.add(model)
        }

        mMemberAdapter!!.update(mAdminList)
    }

    /**
     * 变更管理员完成，恢复初始状态
     */
    override fun onChangeAdminSuccess() {
        type = 0
        organ_admin_add_tv.text = "添加"
        statistics_title_tv.text = "添加管理员"
        statistics_right_tv.text = "编辑"
        statistics_right_tv.visibility = View.VISIBLE
        organ_admin_add_tv.setBackgroundResource(R.drawable.shape_blue_radius_bg)
        mMemberAdapter!!.type = type
        mPresenter.getOrganMember(mOrganId, "all")
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            //返回键
            R.id.statistics_back_iv -> pop()
            //编辑or完成
            R.id.statistics_right_tv -> {
                if (type == 0) {
                    type = 1
                    organ_admin_add_tv.text = "删除(0)）"
                    statistics_right_tv.text = "完成"
                    organ_admin_add_tv.setBackgroundResource(R.drawable.sl_99999_r3)
                } else if (type == 1) {
                    type = 0
                    organ_admin_add_tv.text = "添加"
                    statistics_right_tv.text = "编辑"
                    organ_admin_add_tv.setBackgroundResource(R.drawable.shape_blue_radius_bg)
                }
                mMemberAdapter!!.type = type
                mMemberAdapter!!.notifyDataSetChanged()
            }
            //添加
            R.id.organ_admin_add_tv -> {
                when (type) {
                    //添加-查看所有成员
                    0 -> {
                        type = 2
                        mMemberAdapter!!.update(mMemberList)
                        statistics_right_tv.visibility = View.GONE
                        statistics_title_tv.text = "添加管理员"
                        organ_admin_add_tv.text = "确定(0)"
                        organ_admin_add_tv.setBackgroundResource(R.drawable.sl_99999_r3)
                        mMemberAdapter!!.type = type
                        mMemberAdapter!!.notifyDataSetChanged()
                    }
                    //删除-删除管理员
                    1 -> {
                        if (mBodyList.size < 1) return
                        mPresenter.changeAdmin(mBodyList)
                    }
                    //完成-添加管理员
                    2 -> {
                        if (mBodyList.size < 1) return
                        mPresenter.changeAdmin(mBodyList)
                    }
                }
            }
        }
    }
}