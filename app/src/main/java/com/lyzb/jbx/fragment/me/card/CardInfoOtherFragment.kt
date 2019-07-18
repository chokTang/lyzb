package com.lyzb.jbx.fragment.me.card

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.like.longshaolib.adapter.BaseRecyleAdapter
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener
import com.like.longshaolib.base.BaseFragment
import com.like.utilslib.app.CommonUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.adapter.me.card.HlikeAdapter
import com.lyzb.jbx.adapter.me.card.HobbyAdapter
import com.lyzb.jbx.dialog.BusinessDialog
import com.lyzb.jbx.model.account.BusinessModel
import com.lyzb.jbx.model.me.CardUserInfoModel
import com.lyzb.jbx.mvp.presenter.me.card.CdOtherInfoPresenter
import com.lyzb.jbx.mvp.view.me.ICdOtherInfoView
import com.szy.common.Activity.RegionActivity
import com.szy.common.Fragment.RegionFragment
import com.szy.yishopcustomer.Constant.Api
import com.szy.yishopcustomer.Constant.RequestCode
import kotlinx.android.synthetic.main.fragment_un_card_edtother.*
import me.yokeyword.fragmentation.ISupportFragment
import java.io.Serializable
import java.util.*

/**
 * @author wyx
 * @role 智能卡片-编辑其他信息
 * @time 2019 2019/3/6 8:56
 */

class CardInfoOtherFragment : BaseFragment<CdOtherInfoPresenter>(), ICdOtherInfoView, View.OnClickListener {


    companion object {
        private val DOM_KEY = "DOM_KEY"
        private val KNOW_KEY = "KNOW_KEY"
        private val HOB_KEY = "HOB_KEY"
        private val ADDRESS_KEY = "ADDRESS_KEY"

        private val AD_KEY = "AD_KEY"
        private val AD_ID_KEY = "AD_ID_KEY"

        private val SH_KEY = "SH_KEY"

        fun newItance(domList: List<BusinessModel>, knowList: List<BusinessModel>,
                      hobList: List<String>, addressList: List<String>, address: String, addressId: String, school: String): CardInfoOtherFragment {
            val fragment = CardInfoOtherFragment()
            val args = Bundle()
            args.putSerializable(DOM_KEY, domList as Serializable)
            args.putSerializable(KNOW_KEY, knowList as Serializable)
            args.putSerializable(HOB_KEY, hobList as Serializable)
            args.putSerializable(ADDRESS_KEY, addressList as Serializable)

            args.putString(AD_KEY, address)
            args.putString(AD_ID_KEY, addressId)

            args.putString(SH_KEY, school)
            fragment.arguments = args
            return fragment
        }
    }

    private var domsList: List<BusinessModel> = ArrayList()
    private var knowsList: List<BusinessModel> = ArrayList()
    private var hoysList: List<String> = ArrayList()
    private var addressList: List<String> = ArrayList()

    private var schoolT: String? = null

    private var addressText: String? = null
    private var addressId: String? = null

    private var mInfoModel: CardUserInfoModel? = null

    private var mHobbyDialog: EdtHobbyDialog? = null
    private var addressDialog: EdtHobbyDialog? = null

    private var domAdapter: HobbyAdapter? = null
    private var knowAdapter: HobbyAdapter? = null

    private var mHobbyAdapter: HlikeAdapter? = null
    private var addressAdapter: HlikeAdapter? = null

    private var isDoma = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {

            domsList = bundle.getSerializable(DOM_KEY) as List<BusinessModel>
            knowsList = bundle.getSerializable(KNOW_KEY) as List<BusinessModel>
            hoysList = bundle.getSerializable(HOB_KEY) as List<String>
            addressList = bundle.getSerializable(ADDRESS_KEY) as List<String>

            addressText = bundle.getString(AD_KEY)
            addressId = bundle.getString(AD_ID_KEY)
            schoolT = bundle.getString(SH_KEY)
        }
    }

    override fun getResId(): Any {
        return R.layout.fragment_un_card_edtother
    }

    override fun onInitView(savedInstanceState: Bundle?) {

        mInfoModel = CardUserInfoModel()

        mHobbyDialog = EdtHobbyDialog()
        addressDialog = EdtHobbyDialog()

        domAdapter = HobbyAdapter(context, null)
        recy_other_domain!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recy_other_domain!!.adapter = domAdapter
        recy_other_domain!!.addItemDecoration(com.like.longshaolib.adapter.rvhelper.DividerItemDecoration(DividerItemDecoration.HORIZONTAL, R.drawable.listdivider_white_4))

        knowAdapter = HobbyAdapter(context, null)
        recy_other_know!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recy_other_know!!.adapter = knowAdapter
        recy_other_know!!.addItemDecoration(com.like.longshaolib.adapter.rvhelper.DividerItemDecoration(DividerItemDecoration.HORIZONTAL, R.drawable.listdivider_white_4))

        mHobbyAdapter = HlikeAdapter(context, null)
        recy_other_hobb!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recy_other_hobb!!.adapter = mHobbyAdapter

        recy_other_hobb!!.addOnItemTouchListener(object : OnRecycleItemClickListener() {
            override fun onItemChildClick(adapter: BaseRecyleAdapter<*>, view: View, position: Int) {
                super.onItemChildClick(adapter, view, position)

                //这里是用,隔开的  所以得区分开来然后删除  然后在用,来隔开.....(以前的人这样做的)
                val list = ArrayList<String>()
                if (mInfoModel!!.interest.contains(",")) {
                    for (i in 0 until mInfoModel!!.interest.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size) {
                        list.add(mInfoModel!!.interest.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[i])
                    }
                } else {
                    list.add(mInfoModel!!.interest)
                }
                when (view.id) {
                    R.id.img_info_hobby_re -> {
                        //删除点击的某项然后重新用,组装起来
                        list.removeAt(position)
                        var string = ""
                        for (i in list.indices) {
                            if (i > 0) {
                                string = string + "," + list[i]
                            } else {
                                string = list[0]
                            }
                        }
                        mInfoModel!!.interest = string
                        adapter.remove(position)
                    }

                }
            }
        })

        //常来往地
        addressAdapter = HlikeAdapter(context, null)
        rv_stay_address!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_stay_address!!.adapter = addressAdapter

        rv_stay_address!!.addOnItemTouchListener(object : OnRecycleItemClickListener() {
            override fun onItemChildClick(adapter: BaseRecyleAdapter<*>, view: View, position: Int) {
                super.onItemChildClick(adapter, view, position)

                //这里是用,隔开的  所以得区分开来然后删除  然后在用,来隔开.....(以前的人这样做的)
                val list = ArrayList<String>()
                if (mInfoModel!!.oftenToPace.contains(",")) {
                    for (i in 0 until mInfoModel!!.oftenToPace.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size) {
                        list.add(mInfoModel!!.oftenToPace.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[i])
                    }
                } else {
                    list.add(mInfoModel!!.oftenToPace)
                }
                when (view.id) {
                    R.id.img_info_hobby_re -> {
                        //删除点击的某项然后重新用,组装起来
                        list.removeAt(position)
                        var string = ""
                        for (i in list.indices) {
                            if (i > 0) {
                                string = string + "," + list[i]
                            } else {
                                string = list[0]
                            }
                        }
                        mInfoModel!!.oftenToPace = string
                        adapter.remove(position)
                    }
                    else -> {
                    }
                }
            }
        })

        //已有数据 保存到model
        if (domsList.isNotEmpty()) {
            //熟悉领域
            var str: String? = null
            for (i in domsList.indices) {
                if (i == 0) {
                    str = domsList[i].id.toString()
                } else {
                    str = domsList[i].id.toString() + "," + str
                }

                domAdapter!!.add(domsList[i].name)
            }

            mInfoModel!!.professionId = str
        }

        if (knowsList.isNotEmpty()) {
            //期待认识
            var str: String? = null
            for (i in knowsList.indices) {
                if (i == 0) {
                    str = knowsList[i].id.toString()
                } else {
                    str = knowsList[i].id.toString() + "," + str
                }
                knowAdapter!!.add(knowsList[i].name)
            }
            mInfoModel!!.concernProfession = str
        }

        if (hoysList.isNotEmpty()) {
            //兴趣爱好
            for (i in hoysList.indices) {
                if (i > 0) {
                    mInfoModel!!.interest = mInfoModel!!.interest + "," + hoysList[i]
                } else {
                    mInfoModel!!.interest = hoysList[0]
                }
            }
            mHobbyAdapter!!.update(hoysList)
        }

        //常来往地
        if (addressList.isNotEmpty()) {
            //兴趣爱好
            for (i in addressList.indices) {
                if (i > 0) {
                    mInfoModel!!.oftenToPace = mInfoModel!!.oftenToPace + "," + addressList[i]
                } else {
                    mInfoModel!!.oftenToPace = addressList[0]
                }
            }
            addressAdapter!!.update(addressList)
        }

        edt_other_school_add!!.setText(schoolT)
        //毕业学校
        mInfoModel!!.education = schoolT

        if (!TextUtils.isEmpty(addressId)) {
            tv_other_city_add!!.text = addressText

            //添加城市id
            var addsId: List<String> = ArrayList()
            addsId = CommonUtil.StringToList(";", addressId)

            if (addsId.size == 1) {
                mInfoModel!!.residence = addsId[0]
            } else if (addsId.size == 2) {
                mInfoModel!!.residence = addsId[0] + ";" + addsId[0] + "," + addsId[1]
            } else if (addsId.size == 3) {
                mInfoModel!!.residence = addsId[0] + ";" + addsId[0] + "," + addsId[1] + ";" + addsId[0] + "," + addsId[1] + "," + addsId[2]
            }
        }

        img_un_cd_info_onther_edt_back!!.setOnClickListener { pop() }

        tv_un_card_other_info_save!!.setOnClickListener {
            //保存编辑
            saveOtherInfo()
        }

        img_other_domain_add!!.setOnClickListener {
            isDoma = true
            //熟悉领域添加
            mPresenter.getList()
        }

        ll_domain!!.setOnClickListener {
            isDoma = true
            //熟悉领域添加
            mPresenter.getList()
        }

        recy_other_domain!!.addOnItemTouchListener(object : OnRecycleItemClickListener() {
            override fun onItemClick(adapter: BaseRecyleAdapter<*>, view: View, position: Int) {
                isDoma = true
                //熟悉领域添加
                mPresenter.getList()
            }
        })

        img_other_dat_add!!.setOnClickListener {
            isDoma = false
            //期待认识
            mPresenter.getList()
        }


        recy_other_know!!.addOnItemTouchListener(object : OnRecycleItemClickListener() {
            override fun onItemClick(adapter: BaseRecyleAdapter<*>, view: View, position: Int) {
                isDoma = false
                //期待认识
                mPresenter.getList()
            }
        })

        ll_other_dat!!.setOnClickListener {
            isDoma = false
            //期待认识
            mPresenter.getList()
        }

        tv_other_like_add!!.setOnClickListener(View.OnClickListener {
            if (mHobbyAdapter!!.itemCount >= 10) {
                showToast("最多添加10个兴趣爱好")
                return@OnClickListener
            }
            //兴趣爱好
            mHobbyDialog?.setType(0)
            mHobbyDialog!!.show(fragmentManager!!, "")
        })

        tv_stay_address!!.setOnClickListener(View.OnClickListener {
            if (addressAdapter!!.itemCount >= 10) {
                showToast("最多添加10个来往城市")
                return@OnClickListener
            }
            //来往城市
            addressDialog?.setType(1)
            addressDialog!!.show(fragmentManager!!, "")
        })

        ll_city!!.setOnClickListener {
            //来自城市
            chooseCity("500112")
        }


        mHobbyDialog!!.setListener { text ->
            if (!TextUtils.isEmpty(text)) {
                if (!mInfoModel!!.interest.contains(text)) {
                    if (TextUtils.isEmpty(mInfoModel!!.interest)) {
                        mInfoModel!!.interest = text
                    } else {
                        mInfoModel!!.interest = mInfoModel!!.interest + "," + text
                    }
                }

            } else {
                mInfoModel!!.interest = text
            }
            mHobbyAdapter!!.addNoRepeat(text)
            mHobbyDialog!!.dismiss()
        }


        addressDialog!!.setListener { text ->
            if (!TextUtils.isEmpty(text)) {
                if (!mInfoModel!!.oftenToPace.contains(text)) {
                    if (TextUtils.isEmpty(mInfoModel!!.oftenToPace)) {
                        mInfoModel!!.oftenToPace = text
                    } else {
                        mInfoModel!!.oftenToPace = mInfoModel!!.oftenToPace + "," + text
                    }
                }

            } else {
                mInfoModel!!.oftenToPace = text
            }
            addressAdapter!!.addNoRepeat(text)
            addressDialog!!.dismiss()
        }

        recy_other_domain!!.setOnClickListener {
            mPresenter.getList()
            isDoma = true
        }

        recy_other_know!!.setOnClickListener {
            mPresenter.getList()
            isDoma = false
        }
    }

    override fun onInitData(savedInstanceState: Bundle?) {

    }

    override fun onIndustryList(list: MutableList<BusinessModel>) {
        BusinessDialog
                .newIntance()
                .setList(list)
                .invoke(object : BusinessDialog.ClickListener {
                    override fun click(v: View?, list: MutableList<BusinessModel>?) {
                        var str = ""
                        var listString:MutableList<String> = arrayListOf()
                        for (i in list!!.indices) {
                            if (i == 0) {
                                str = list[i].id.toString()
                            } else {
                                str = list[i].id.toString() + "," + str
                            }
//                            if (isDoma) {
//                                domAdapter!!.addNoRepeat(list[i].name)
//                            } else {
//                                knowAdapter!!.addNoRepeat(list[i].name)
//                            }
                            listString.add(list[i].name)
                            if (isDoma) {
                                domAdapter!!.update(listString)
                            } else {
                                knowAdapter!!.update(listString)
                            }
                        }

                        if (isDoma) {
                            if (!TextUtils.isEmpty(str)) {//添加后面加起的 熟悉领域
//                                if (!mInfoModel!!.professionId.contains(str)) {
//                                    if (TextUtils.isEmpty(mInfoModel!!.professionId)) {
//                                        mInfoModel!!.professionId = str
//                                    } else {
//                                        mInfoModel!!.professionId = mInfoModel!!.professionId + "," + str
//                                    }
//                                }
                                mInfoModel!!.professionId = str

                            }
                        } else {//添加后面加起的 期待认识
                            if (!TextUtils.isEmpty(str)) {
//                                if (!mInfoModel!!.concernProfession.contains(str)) {
//                                    if (TextUtils.isEmpty(mInfoModel!!.concernProfession)) {
//                                        mInfoModel!!.concernProfession = str
//                                    } else {
//                                        mInfoModel!!.concernProfession = mInfoModel!!.concernProfession + "," + str
//                                    }
//                                }
                                mInfoModel!!.concernProfession = str
                            }
                        }
                        for (i in list.indices) {
                            list[i].isChecked = false
                        }
                    }
                }).show(fragmentManager!!, "SelectList")
    }

    /**
     * 保存成功
     * @param data
     */
    override fun toCardInfo(data: String) {
        mInfoModel!!.residence = ""
        val bundle = Bundle()
        bundle.putString("CardInfoOther", data)
        setFragmentResult(ISupportFragment.RESULT_OK, bundle)
        pop()
    }

    override fun saveFail() {
        mInfoModel!!.residence = ""
    }

    private fun chooseCity(regionCode: String) {
        val intent = Intent(context, RegionActivity::class.java)
        intent.putExtra(RegionFragment.KEY_REGION_CODE, regionCode)
        intent.putExtra(RegionFragment.KEY_API, Api.API_REGION_LIST)
        startActivityForResult(intent, RequestCode.REQUEST_CODE_REGION_CODE.value)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (RequestCode.valueOf(requestCode) == RequestCode.REQUEST_CODE_REGION_CODE) {
            if (data != null) {

                val addressDate = data.extras
                val regionName = addressDate!!.getString(RegionFragment.KEY_REGION_LIST)
                val regionCode = addressDate.getString(RegionFragment.KEY_REGION_CODE)

                if (!TextUtils.isEmpty(regionCode)) {
                    val address = regionCode!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    when (address.size) {
                        1 -> mInfoModel!!.residence = address[0]
                        2 -> mInfoModel!!.residence = address[0] + ";" + address[0] + "," + address[1]
                        3 -> mInfoModel!!.residence = address[0] + ";" + address[0] + "," + address[1] + ";" + address[0] + "," + address[1] + "," + address[2]
                    }
                    tv_other_city_add!!.text = regionName
                }
            }
        }
    }

    private fun saveOtherInfo() {

        if (TextUtils.isEmpty(mInfoModel!!.professionId)) {
            showToast("至少添加一个熟悉领域")
            return
        }

        if (TextUtils.isEmpty(mInfoModel!!.concernProfession)) {
            showToast("至少添加一个期待认识的行业")
            return
        }

        //        if (TextUtils.isEmpty(mInfoModel.getInterest())) {
        //            showToast("至少添加一个兴趣爱好");
        //            return;
        //        }

        //        if (TextUtils.isEmpty(mInfoModel.getResidence())) {
        //            showToast("请选择来自城市");
        //            return;
        //        }
        //
        //        if (TextUtils.isEmpty(edt_other_school_add.getText().toString().trim())) {
        //            showToast("请输入毕业院校");
        //            return;
        //        }

        //去重
        mInfoModel!!.concernProfession = removeDoublue(mInfoModel!!.concernProfession)
        mInfoModel!!.professionId = removeDoublue(mInfoModel!!.professionId)
        mInfoModel!!.interest = removeDoublue(mInfoModel!!.interest)
        mInfoModel!!.oftenToPace = removeDoublue(mInfoModel!!.oftenToPace)
        mInfoModel!!.education = edt_other_school_add!!.text.toString()
        mPresenter.saveInfo(mInfoModel)
    }


    /**
     * 去重字符串中用逗号隔开的字符串  如   "1,1,2"->"1,2"
     *
     * @param str
     * @return
     */
    private fun removeDoublue(str: String): String {
        val noDouble = ArrayList<String>()

        val list = CommonUtil.StringToList(str)
        return if (list != null) {
            for (i in list.indices) {
                if (!noDouble.contains(list[i])) {
                    noDouble.add(list[i])
                }
            }
            CommonUtil.ListToString(noDouble)
        } else {
            ""
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }


}
