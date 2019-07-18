package com.lyzb.jbx.fragment.me.company

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.like.longshaolib.base.BaseToolbarFragment
import com.lyzb.jbx.R
import com.lyzb.jbx.fragment.circle.CircleDetailFragment
import com.lyzb.jbx.fragment.me.card.CardCompanyFragment
import com.lyzb.jbx.mvp.presenter.me.company.CompanyWebPresenter
import com.lyzb.jbx.mvp.view.me.ICompanyWebView
import kotlinx.android.synthetic.main.fragment_company_web.*

/**
 * Created by :TYK
 * Date: 2019/4/22  13:46
 * Desc: 企业官网
 */

class CompanyWebFragment : BaseToolbarFragment<CompanyWebPresenter>(), ICompanyWebView, View.OnClickListener {

    //企业ID  这里下级页面要用到来查询 商品数据
    var companyId = ""
    var groupId = ""
    var fragment: CardCompanyFragment? = null
    var companyName = ""
    var type = -1

    companion object {
        const val KEY_TYPE = "keytype"
        const val KEY_GROUP_ID = "keygroupId"
        const val KEY_COMPANY_ID = "keycompanyId"
        const val KEY_COMPANY_NAME = "keycompanyname"
        fun newInstance(companyId: String, companyName: String): CompanyWebFragment {
            return newInstance(companyId, "", companyName, -1)
        }

        fun newInstance(companyId: String, groupId: String, companyName: String, type: Int): CompanyWebFragment {
            val fragment = CompanyWebFragment()
            val bundle = Bundle()
            bundle.putString(KEY_COMPANY_ID, companyId)
            bundle.putString(KEY_GROUP_ID, groupId)
            bundle.putString(KEY_COMPANY_NAME, companyName)
            bundle.putInt(KEY_TYPE, type)
            fragment.arguments = bundle
            return fragment
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            companyId = bundle.getString(KEY_COMPANY_ID)
            groupId = bundle.getString(KEY_GROUP_ID)
            companyName = bundle.getString(KEY_COMPANY_NAME)
            type = bundle.getInt(KEY_TYPE)
        }
    }

    override fun getResId(): Any {
        return R.layout.fragment_company_web
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
        tv_title.text = companyName
        fragment = CardCompanyFragment()
        loadRootFragment(R.id.frame_layout, fragment)
        if (type==-1){
            fragment!!.onQuestCompany(companyId, false)
        }else{
            fragment!!.setIsEdit(companyId,type)
        }


        img_back.setOnClickListener(this)
        tv_go_circle.setOnClickListener(this)
    }

    override fun onInitData(savedInstanceState: Bundle?) {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_back -> {//返回
                pop()
            }
            R.id.tv_go_circle -> {//进圈
                if (TextUtils.isEmpty(groupId)) {//这次为空
                    groupId = fragment!!.groupId
                }
                if (TextUtils.isEmpty(groupId)) {
                    showToast("该企业暂未关联圈子")
                } else {
                    start(CircleDetailFragment.newIntance(groupId))
                }
            }
        }
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle?) {
        super.onFragmentResult(requestCode, resultCode, data)
        fragment!!.onFragmentResult(requestCode, resultCode, data)
    }
}