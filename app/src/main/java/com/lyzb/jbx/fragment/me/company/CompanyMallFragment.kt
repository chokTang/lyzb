package com.lyzb.jbx.fragment.me.company

import android.os.Bundle
import com.like.longshaolib.base.BaseToolbarFragment
import com.lyzb.jbx.R
import com.lyzb.jbx.mvp.presenter.me.company.CompanyMallPresenter
import com.lyzb.jbx.mvp.view.me.ICompanyMallView

/**
 * Created by :TYK
 * Date: 2019/4/22  9:51
 * Desc: 企业商城
 */

class CompanyMallFragment : BaseToolbarFragment<CompanyMallPresenter>(), ICompanyMallView {


    /**
     * 区分 来源 1:自己的企业  2:别人的企业
     */
    //来源 1:自己的企业  2:别人的企业  这里下级页面要用到来显示布局
    var FromType = 1
    //企业ID  这里下级页面要用到来查询 商品数据
    var companyid = ""
    var creatorId = ""

    companion object {
        const val KEY_TYPE = "keytype"
        const val KEY_COMPANY_ID = "keycompanyId"
        const val KEY_CREATOR_ID = "creatorId"
        fun newInstance(type: Int, companyId: String, creatorId: String): CompanyMallFragment {
            val fragment = CompanyMallFragment()
            val bundle = Bundle()
            bundle.putInt(KEY_TYPE, type)
            bundle.putString(KEY_COMPANY_ID, companyId)
            bundle.putString(KEY_CREATOR_ID,creatorId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            companyid = bundle.getString(KEY_COMPANY_ID)
            FromType = bundle.getInt(KEY_TYPE)
            creatorId = bundle.getString(KEY_CREATOR_ID)
        }
    }

    override fun getResId(): Any {
        return R.layout.fragment_company_mall
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
        onBack()
        setToolbarTitle("企业商城")
        loadRootFragment(R.id.frame_layout, CompanyMallLayoutFragment())
    }

    override fun onInitData(savedInstanceState: Bundle?) {
    }
}