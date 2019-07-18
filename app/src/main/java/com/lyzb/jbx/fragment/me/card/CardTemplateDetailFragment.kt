package com.lyzb.jbx.fragment.me.card

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import com.like.longshaolib.base.BaseToolbarFragment
import com.like.utilslib.other.LogUtil
import com.lyzb.jbx.R
import com.lyzb.jbx.adapter.me.card.MyPagerAdapter
import com.lyzb.jbx.model.me.CardTemplateModel
import com.lyzb.jbx.model.me.SaveTemplateBody
import com.lyzb.jbx.mvp.presenter.me.card.CardTemplateDetailPresenter
import com.lyzb.jbx.mvp.view.me.ICardTemplateDetailView
import com.lyzb.jbx.widget.RotationPageTransformer
import kotlinx.android.synthetic.main.fragment_template_detail.*
import me.yokeyword.fragmentation.ISupportFragment


/**
 * Created by :TYK
 * Date: 2019/5/6  14:27
 * Desc: 名片模板详情
 */

class CardTemplateDetailFragment : BaseToolbarFragment<CardTemplateDetailPresenter>(), ICardTemplateDetailView, View.OnClickListener {

    companion object {
        const val KEY_IS_SELECT = "isSelect"
        const val KEY_USED_TEMPLATE = "usedTemplate"
        fun newInstance(templateId: String): CardTemplateDetailFragment {
            val fragment = CardTemplateDetailFragment()
            val bundle = Bundle()
            bundle.putString(KEY_USED_TEMPLATE,templateId)
            fragment.arguments = bundle
            return fragment
        }
    }

    var templateId = ""
    var adpter: MyPagerAdapter? = null
    val templateList: MutableList<CardTemplateModel> = arrayListOf()

    override fun getResId(): Any {
        return R.layout.fragment_template_detail
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)

        val bundle = arguments
        templateId = bundle?.getString(KEY_USED_TEMPLATE)!!

        onBack()
        setToolbarTitle("名片模板")

        viewPager.setPageTransformer(true, RotationPageTransformer())
        viewPager.offscreenPageLimit = 2//设置预加载的数量，这里设置了2,会预加载中心item左边两个Item和右边两个Item
        viewPager.offscreenPageLimit = 3



        tv_confirm.setOnClickListener(this)
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        mPresenter.getTemplateList()
    }


    /**
     * 请求成功回调
     */
    override fun getTemplateList(list: MutableList<CardTemplateModel>) {
        templateList.clear()
        templateList.addAll(list)
        adpter = MyPagerAdapter(activity, list)
        viewPager.adapter = adpter
        if (list[0].id == templateId) {
            tv_confirm.isClickable = false
            tv_confirm.text = "使用中"
            tv_confirm.setBackgroundColor(resources.getColor(R.color.gray))
        } else {
            tv_confirm.isClickable = true
            tv_confirm.text = "启用此模板"
        }
        viewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {

                if (list[position].id == templateId) {
                    tv_confirm.isClickable = false
                    tv_confirm.text = "使用中"
                    tv_confirm.setBackgroundColor(resources.getColor(R.color.gray))
                } else {
                    tv_confirm.isClickable = true
                    tv_confirm.text = "启用此模板"
                    tv_confirm.setBackgroundColor(resources.getColor(R.color.fontcColor1))

                }
            }

        })
    }

    /**
     * 启用成功回调
     */
    override fun saveTemplate(mode: CardTemplateModel) {
        val bundle = Bundle()
        bundle.putSerializable(KEY_IS_SELECT, templateList[viewPager.currentItem])
        setFragmentResult(ISupportFragment.RESULT_OK, bundle)
        pop()
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_confirm -> {
                val body = SaveTemplateBody()
                body.templateId = (templateList[viewPager.currentItem]).id
                mPresenter.saveCardTemlate(body)
            }
        }
    }

}