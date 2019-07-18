package com.lyzb.jbx.fragment.me.company

import android.os.Bundle
import android.view.View
import com.like.longshaolib.adapter.BaseRecyleAdapter
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener
import com.like.longshaolib.base.BaseFragment
import com.lyzb.jbx.R
import com.lyzb.jbx.adapter.me.company.ChoiceOrganAdminAdapter
import com.lyzb.jbx.model.me.company.OrganNenberModel
import com.lyzb.jbx.mvp.presenter.me.company.OrganAdminPresenter
import com.lyzb.jbx.mvp.view.me.IOrganAdminView
import kotlinx.android.synthetic.main.fragment_organ_admin.*
import kotlinx.android.synthetic.main.toolbar_statistics.*

/**
 * 选择机构负责人
 */
class ChoiceOrganAdminFragment : BaseFragment<OrganAdminPresenter>(), IOrganAdminView {

    var mOrganId = ""

    var onChoiceOrganAdminListener: OnChoiceOrganAdminListener? = null

    var mAdapter: ChoiceOrganAdminAdapter? = null

    companion object {
        fun newIntance(organId: String, onChoiceOrganAdminListener: OnChoiceOrganAdminListener): ChoiceOrganAdminFragment {
            val fragment = ChoiceOrganAdminFragment()
            fragment.mOrganId = organId
            fragment.onChoiceOrganAdminListener = onChoiceOrganAdminListener
            return fragment
        }
    }

    override fun onInitView(savedInstanceState: Bundle?) {

        statistics_title_tv.text = "选择机构负责人"
        statistics_back_iv.setOnClickListener {
            pop()
        }
        organ_admin_add_tv.text = "确定"
        organ_admin_add_tv.setOnClickListener {
            if (onChoiceOrganAdminListener != null) {
                val list :MutableList<OrganNenberModel.DataBean> = mutableListOf()
                for (model in mAdapter!!.list) {
                    model as OrganNenberModel.DataBean
                    if (model.isSelection) {
                        list.add(model)
                    }
                }
                onChoiceOrganAdminListener!!.onChoiceOrganAdminListener(list)
                pop()
            }
        }
        mAdapter = ChoiceOrganAdminAdapter(_mActivity, null)
        mAdapter!!.setLayoutManager(organ_admin_rv)
        organ_admin_rv.adapter = mAdapter
        organ_admin_rv.addOnItemTouchListener(object : OnRecycleItemClickListener() {
            override fun onItemClick(adapter: BaseRecyleAdapter<*>?, view: View?, position: Int) {
                super.onItemClick(adapter, view, position)
                for (model in adapter!!.list) {
                    model as OrganNenberModel.DataBean
                    var positionModel = adapter.getPositionModel(position) as OrganNenberModel.DataBean
                    model.isSelection = model.userId == positionModel.userId
                }
                mAdapter!!.notifyDataSetChanged()
            }
        })
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        mPresenter.getOrganMember(mOrganId, "all")
    }

    override fun getResId(): Any {
        return R.layout.fragment_organ_admin
    }

    override fun getMenberListSuccess(list: MutableList<OrganNenberModel.DataBean>) {
        for (model in list) {
            model.isSelection = model.roleType == OrganAdminFragment.ROLE_TYPE_PERSON_IN_CHARGE
        }
        mAdapter!!.update(list)
    }

    override fun onChangeAdminSuccess() {
    }

    interface OnChoiceOrganAdminListener {
        fun onChoiceOrganAdminListener(list: MutableList<OrganNenberModel.DataBean>)
    }
}