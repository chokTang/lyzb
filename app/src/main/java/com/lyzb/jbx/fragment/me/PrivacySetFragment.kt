package com.lyzb.jbx.fragment.me

import android.os.Bundle
import com.like.longshaolib.base.BaseToolbarFragment
import com.lyzb.jbx.R
import com.lyzb.jbx.mvp.presenter.me.PrivacySetPresenter
import com.lyzb.jbx.mvp.view.me.IPrivacySetView
import com.lyzb.jbx.util.AppPreference
import kotlinx.android.synthetic.main.fragment_privacy_set.*

/**
 * Created by :TYK
 * Date: 2019/4/19  14:32
 * Desc:  隐私设置frgament
 */

class PrivacySetFragment : BaseToolbarFragment<PrivacySetPresenter>(), IPrivacySetView {

    override fun getResId(): Any {
        return R.layout.fragment_privacy_set
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
        onBack()
        setToolbarTitle("隐私设置")

        switch_btn.setOnClickListener {
            if (AppPreference.getIntance().userShowInfo) {
                mPresenter.setPrivace("0")
            } else {
                mPresenter.setPrivace("1")
            }
        }
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        switch_btn.isChecked = AppPreference.getIntance().userShowInfo
    }

    override fun setPrivacySwith(showInfo: String) {
        AppPreference.getIntance().userShowInfo = (showInfo == "1")
    }
}
