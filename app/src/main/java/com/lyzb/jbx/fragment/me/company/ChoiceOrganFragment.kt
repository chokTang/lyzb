package com.lyzb.jbx.fragment.me.company

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.like.longshaolib.adapter.BaseRecyleAdapter
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener
import com.like.longshaolib.base.BaseFragment
import com.lyzb.jbx.R
import com.lyzb.jbx.adapter.me.company.OrganAdapter
import com.lyzb.jbx.adapter.me.company.OrganSelectAdapter
import com.lyzb.jbx.adapter.me.company.OrganSuperiorAdapter
import com.lyzb.jbx.model.me.company.ChildrenBean
import com.lyzb.jbx.mvp.presenter.me.company.ChoiceOrganPresenter
import com.lyzb.jbx.mvp.view.me.company.IChoiceOrganView
import kotlinx.android.synthetic.main.fragment_choice_organ.*
import kotlinx.android.synthetic.main.toolbar_statistics.*

/**
 *@author wyx
 *@version
 *@role 选择机构
 *@time 2019 2019/6/17 9:48
 */

class ChoiceOrganFragment : BaseFragment<ChoiceOrganPresenter>(), IChoiceOrganView, View.OnClickListener {

    private var mOrgans: MutableList<ChildrenBean> = mutableListOf()
    private var mOrganAdapter: OrganAdapter? = null

    private var mSuperiorOrgans: MutableList<ChildrenBean> = mutableListOf()
    private var mSuperiorOrganAdapter: OrganSuperiorAdapter? = null

    private var mSelectOrgans: MutableList<ChildrenBean> = mutableListOf()
    private var mSelectOrganAdapter: OrganSelectAdapter? = null

    /**
     * 类型，1添加机构-单选，2添加人员
     */
    private var mType = "1"
    /**
     * 机构类型（1.企业 2.部门）
     */
    private var mOrganType = "1"
    /**
     * 当前操作机构id
     */
    private var mOrganId = ""
    private var onChoiceOrganListener: OnChoiceOrganListener? = null

    companion object {
        /**
         * 机构id
         * 类型，1添加机构，2添加人员
         * 机构类型（1.企业 2.机构）
         */
        fun newIntance(organId: String, type: String, organType: String, onChoiceOrganListener: OnChoiceOrganListener): ChoiceOrganFragment {
            val fragment = ChoiceOrganFragment()
            fragment.mOrganId = organId
            fragment.mType = type
            fragment.mOrganType = organType
            fragment.onChoiceOrganListener = onChoiceOrganListener
            return fragment
        }
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        if (mType == "1") {
            statistics_title_tv.text = "选择上级机构"
            choice_organ_sure_tv.text = "确定"
        } else {
            //选中的机构
            statistics_title_tv.text = "选择机构"
            choice_organ_sure_tv.text = "确定(0)"
            mSelectOrganAdapter = OrganSelectAdapter(_mActivity, null)
            mSelectOrganAdapter!!.setLayoutManager(choice_organ_selectorgan_rv, LinearLayoutManager.HORIZONTAL)
            choice_organ_selectorgan_rv.visibility = View.VISIBLE
            choice_organ_selectorgan_rv.adapter = mSelectOrganAdapter
            choice_organ_selectorgan_rv.addOnItemTouchListener(object : OnRecycleItemClickListener() {
                override fun onItemChildClick(adapter: BaseRecyleAdapter<*>?, view: View?, position: Int) {
                    super.onItemChildClick(adapter, view, position)
                    val bean = adapter!!.getPositionModel(position) as ChildrenBean
                    for (model in mOrgans) {
                        if (bean.id == model.id) {
                            model.isSelection = false
                        }
                    }
                    mSelectOrgans.remove(bean)
                    mSelectOrganAdapter!!.update(mSelectOrgans)

                    mOrganAdapter!!.notifyDataSetChanged()
                    setSureText()
                }
            })
        }
        statistics_back_iv.setOnClickListener(this)
        choice_organ_sure_tv.setOnClickListener(this)
        //顶部的机构列表
        mSuperiorOrganAdapter = OrganSuperiorAdapter(_mActivity, null)
        mSuperiorOrganAdapter!!.setLayoutManager(choice_organ_superior_rv, LinearLayoutManager.HORIZONTAL)
        choice_organ_superior_rv.adapter = mSuperiorOrganAdapter
        choice_organ_superior_rv.addOnItemTouchListener(object : OnRecycleItemClickListener() {
            override fun onItemClick(adapter: BaseRecyleAdapter<*>?, view: View?, position: Int) {
                super.onItemClick(adapter, view, position)
                val itemModel = adapter!!.getPositionModel(position) as ChildrenBean
                setTopSelect(itemModel)
                showOrgan(itemModel)
            }
        })
        //中间的机构列表
        mOrganAdapter = OrganAdapter(_mActivity, null)
        mOrganAdapter!!.setLayoutManager(choice_organ_recyclerview)
        choice_organ_recyclerview.adapter = mOrganAdapter
        choice_organ_recyclerview.addOnItemTouchListener(object : OnRecycleItemClickListener() {
            override fun onItemClick(adapter: BaseRecyleAdapter<*>?, view: View?, position: Int) {
                super.onItemClick(adapter, view, position)
                val itemModel = adapter!!.getPositionModel(position) as ChildrenBean
                //点击item时展示下级机构数据,直到没有下级为止
                setTopSelect(itemModel)
                showOrgan(itemModel)
            }

            override fun onItemChildClick(adapter: BaseRecyleAdapter<*>?, view: View?, position: Int) {
                super.onItemChildClick(adapter, view, position)

                val itemModel = adapter!!.getPositionModel(position) as ChildrenBean
                //设置机构的选中
                itemModel.isSelection = !itemModel.isSelection
                //单选时让其他机构变成未选中状态
                for (model in mOrgans) {
                    if (model.id != itemModel.id && mType == "1") {
                        model.isSelection = false
                    }
                }
                //添加or取消底部的选中
                if (mSelectOrgans.contains(itemModel)) {
                    mSelectOrgans.remove(itemModel)
                } else {
                    if (mType == "1") {
                        mSelectOrgans.clear()
                    }
                    mSelectOrgans.add(itemModel)
                }
                setSureText()
                mOrganAdapter!!.notifyDataSetChanged()
            }
        })
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        mPresenter.getOrganList(mOrganId, mOrganType)
    }

    override fun getResId(): Any {
        return R.layout.fragment_choice_organ
    }

    override fun onSuccess(list: MutableList<ChildrenBean>) {
        mOrgans = list
        val homeBean = ChildrenBean()
        homeBean.id = "0"
        homeBean.companyName = "首页"
        homeBean.isSelection = true

        //后台返回的数据有点问题，整个双重循环来处理下
        for (model in mOrgans) {
            //默认是顶级机构
            var noSuperior = true
            //再循环一次机构列表
            for (model2 in mOrgans) {
                //机构的上级id跟二重循环里面的机构id相同就说明是有上级机构的
                if (model.parentOrgId == model2.id) {
                    noSuperior = false
                    break
                }
            }
            if (noSuperior) {
                model.parentOrgId = "0"
            }
        }

        setTopSelect(homeBean)
        showOrgan(homeBean)
    }

    /**
     * 返回所有机构，根据选中的上级机构显示相应的机构
     */
    fun showOrgan(bean: ChildrenBean) {
        val organList: MutableList<ChildrenBean> = mutableListOf()

        //根据选中机构id得到当前机构数据
        for (organModel in mOrgans) {
            if (organModel.parentOrgId == bean.id || mOrgans.size == 1) {
                organList.add(organModel)
            }
        }
        //判断有没下级机构
        for (currentModel in organList) {
            for (organModel in mOrgans) {
                if (currentModel.id == organModel.parentOrgId) {
                    currentModel.isNoBranch = false
                    break
                }
            }
        }
        if (organList.size > 0) {
            mOrganAdapter!!.update(organList)
        }
    }

    /**
     * 设置顶部机构列表的选中效果
     */
    private fun setTopSelect(bean: ChildrenBean) {
        if (bean.isNoBranch && bean.id != "0") return
        if (!mSuperiorOrgans.contains(bean)) {
            mSuperiorOrgans.add(bean)
        }
        //倒序删除至当前选中item(首页除外)
        for (i in mSuperiorOrgans.size - 1 downTo 1) {
            if (mSuperiorOrgans[i].id != bean.id) {
                mSuperiorOrgans.removeAt(i)
            } else {
                break
            }
        }
        for (model in mSuperiorOrgans) {
            model.isSelection_Top = model.id == bean.id
        }
        mSuperiorOrganAdapter!!.update(mSuperiorOrgans)
        showOrgan(bean)
    }

    /**
     * 设置确定按钮颜色及数量
     */
    private fun setSureText() {
        if (mSelectOrgans.size < 1) {
            choice_organ_sure_tv.setBackgroundResource(R.drawable.sl_99999_r3)
        } else {
            choice_organ_sure_tv.setBackgroundResource(R.drawable.shape_blue_radius_bg)
        }
        if (mType == "1") {
            choice_organ_sure_tv.text = "确定"
        } else {
            val text = "确定(" + mSelectOrgans.size + ")"
            choice_organ_sure_tv.text = text
            mSelectOrganAdapter!!.update(mSelectOrgans)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            //返回键
            R.id.statistics_back_iv -> {
                pop()
            }
            //确定
            R.id.choice_organ_sure_tv -> {
                if (mSelectOrgans.size < 1 || onChoiceOrganListener == null) return
                onChoiceOrganListener!!.onChoiceOrganListener(mSelectOrgans)
                pop()
            }
        }
    }

    interface OnChoiceOrganListener {
        fun onChoiceOrganListener(organs: MutableList<ChildrenBean>)
    }
}