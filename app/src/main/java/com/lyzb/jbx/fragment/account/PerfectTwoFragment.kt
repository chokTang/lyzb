package com.lyzb.jbx.fragment.account

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.like.longshaolib.base.BaseFragment
import com.like.longshaolib.dialog.fragment.AlertDialogFragment
import com.lyzb.jbx.R
import com.lyzb.jbx.activity.HomeActivity
import com.lyzb.jbx.adapter.account.PerfectBusinessAdapter
import com.lyzb.jbx.dialog.BusinessDialog
import com.lyzb.jbx.fragment.account.PerfectOneFragment.Companion.RESULT_BUNDLE_KEY
import com.lyzb.jbx.model.account.BusinessModel
import com.lyzb.jbx.model.account.RequestPerfectBean
import com.lyzb.jbx.model.account.UpPerfectMessageBean
import com.lyzb.jbx.mvp.presenter.account.PerfectTwoPresenter
import com.lyzb.jbx.mvp.view.account.IPerfectTwoView
import com.szy.yishopcustomer.Util.App
import com.szy.yishopcustomer.Util.LogUtils
import com.szy.yishopcustomer.Util.json.GsonUtils
import kotlinx.android.synthetic.main.fragment_perfect_two.*
import kotlinx.android.synthetic.main.layout_xx_title.*
import java.io.Serializable

/**
 * Created by :TYK
 * Date: 2019/3/5  16:15
 * Desc: 完善信息页面2
 */

class PerfectTwoFragment : BaseFragment<PerfectTwoPresenter>(), IPerfectTwoView, View.OnClickListener {

    companion object {
        const val REQUEST_BEAN = "REQUEST_BEAN"
        fun newInstance(bean: RequestPerfectBean): PerfectTwoFragment {
            val fragment = PerfectTwoFragment()
            val bundle = Bundle()
            bundle.putSerializable(REQUEST_BEAN, bean)
            fragment.arguments = bundle
            return fragment
        }
    }

    var requestPerfectBean: RequestPerfectBean? = null
    var mBundle: Bundle? = null
    var adapter: PerfectBusinessAdapter? = null
    var listbusiness: MutableList<BusinessModel> = arrayListOf()
    var dialog: BusinessDialog? = null

    override fun OnGetListBusiness(bean: MutableList<BusinessModel>) {
        listbusiness = bean
        dialog!!.setList(listbusiness)
        dialog!!.show(fragmentManager!!, "选择行业")
    }

    override fun OnResult(bean: UpPerfectMessageBean) {
        App.getInstance().isUserInfoPer = true
        startActivity(Intent(activity, HomeActivity::class.java))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBundle = arguments
        requestPerfectBean = mBundle!!.getSerializable(REQUEST_BEAN) as RequestPerfectBean
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        adapter = PerfectBusinessAdapter()
        dialog = BusinessDialog()
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rv_business.layoutManager = linearLayoutManager
        rv_business.adapter = adapter
        val bean = BusinessModel()
        bean.name = ""
        listbusiness.add(bean)
        adapter!!.setNewData(listbusiness)

        adapter!!.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.item_business_null -> {//点击选择行业
                    listbusiness.clear()
                    mPresenter.getListBusiness()
                }
                R.id.ll_business -> {//已经选择后点击 选择行业栏
                    listbusiness.clear()
                    mPresenter.getListBusiness()
                }
            }
        }

        img_back.setOnClickListener(this)
        ll_business.setOnClickListener(this)
        tv_join_union.setOnClickListener(this)

        dialog!!.invoke(object : BusinessDialog.ClickListener {
            override fun click(v: View?, list: MutableList<BusinessModel>?) {
                when (v!!.id) {
                    R.id.tv_finish -> {//完成

                        var str = ""
                        for (i in 0 until list!!.size) {
                            str = if (i == 0) {
                                list[i].id.toString()
                            } else {
                                list[i].id.toString() + "," + str
                            }
                        }
                        requestPerfectBean!!.concernProfession = str
                        if (list.size > 0) {
                            adapter!!.setNewData(list)
                        } else {
                            val bean = BusinessModel()
                            bean.name = ""
                            listbusiness.add(bean)
                            adapter!!.setNewData(listbusiness)
                        }
                        dialog!!.dismiss()
                    }
                }
            }
        })
    }

    override fun onInitData(savedInstanceState: Bundle?) {
    }

    override fun getResId(): Any {
        return R.layout.fragment_perfect_two
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_back -> {//返回
                val  bundle = Bundle()
                bundle.putString(RESULT_BUNDLE_KEY,GsonUtils.toJson(requestPerfectBean!!))
                setFragmentResult(RESULT_OK,bundle)
                pop()
            }

            R.id.ll_business -> {//感兴趣行业
                LogUtils.e("你点击了感兴趣行业栏")
                listbusiness.clear()
                mPresenter.getListBusiness()
            }

            R.id.tv_join_union -> {//进入共商联盟
                checkIsNull(requestPerfectBean!!)
            }
        }
    }

    /**
     * 检查传入参数
     */
    fun checkIsNull(requestPerfectBean: RequestPerfectBean) {
        if (TextUtils.isEmpty(requestPerfectBean.concernProfession)) {
            showToast("请选择您感兴趣的行业")
            return
        }
        mPresenter.uploadMsg(requestPerfectBean)
    }


}
