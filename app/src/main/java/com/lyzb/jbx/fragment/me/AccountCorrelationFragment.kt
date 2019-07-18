package com.lyzb.jbx.fragment.me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.like.longshaolib.base.BaseFragment
import com.like.longshaolib.dialog.fragment.AlertDialogFragment
import com.like.longshaolib.net.model.HttpResult
import com.lyzb.jbx.R
import com.lyzb.jbx.mvp.presenter.AccountCorrelationPresenter
import com.lyzb.jbx.mvp.view.me.IAccountCorrelationView
import com.szy.common.Other.CommonEvent
import com.szy.yishopcustomer.Constant.Config
import com.szy.yishopcustomer.Constant.EventWhat
import com.szy.yishopcustomer.Util.App
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import kotlinx.android.synthetic.main.fragment_account_correlation.*
import kotlinx.android.synthetic.main.toolbar_statistics.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 *@author wyx
 *@version
 *@role
 *@time 2019 2019/5/29 13:50
 */
class AccountCorrelationFragment : BaseFragment<AccountCorrelationPresenter>(), IAccountCorrelationView, View.OnClickListener {

    var status = ""
    var message = ""
    var api: IWXAPI? = null

    override fun onInitView(savedInstanceState: Bundle?) {
        account_correlation_account_tv.setOnClickListener(this)
        statistics_back_iv.setOnClickListener(this)
        statistics_title_tv.text = "帐号关联"
        mPresenter.checkBinDing(App.getInstance().userId)
    }

    override fun onInitData(savedInstanceState: Bundle?) {
    }

    override fun getResId(): Any {
        return R.layout.fragment_account_correlation
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        EventBus.getDefault().register(this)
        api = WXAPIFactory.createWXAPI(_mActivity, Config.WEIXIN_APP_ID, true)
        api!!.registerApp(Config.WEIXIN_APP_ID)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.account_correlation_account_tv -> {
                if (status.equals("1")) {
                    showDialog()
                } else {
                    toWx()
                }

            }
            R.id.statistics_back_iv -> {
                pop()
            }
        }
    }

    /**
     * 获取微信授权
     */
    fun toWx() {
        if (null == api) {
            api = WXAPIFactory.createWXAPI(_mActivity, Config.WEIXIN_APP_ID, true)
            api!!.registerApp(Config.WEIXIN_APP_ID)
        }
        var req: SendAuth.Req? = SendAuth.Req()
        req!!.scope = "snsapi_userinfo"
        api!!.sendReq(req)
    }

    @Subscribe
    fun onEvent(event: CommonEvent) {
        when (EventWhat.valueOf(event!!.what)) {
            EventWhat.EVENT_WEIXIN_SHARE -> {
                var code = event.message
                when (Integer.valueOf(code)) {
                    BaseResp.ErrCode.ERR_OK -> {
                        mPresenter.getOpenId()
                    }
                    BaseResp.ErrCode.ERR_USER_CANCEL -> {
                        //用户取消
                    }
                //异常
                }
            }
        }
    }

    /**
     * 解除绑定提示框
     */
    fun showDialog() {
        AlertDialogFragment.newIntance()
                .setKeyBackable(false)
                .setCancleable(false)
                .setContent(message)
                .setCancleBtn(null)
                .setSureBtn("解除关联", object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        mPresenter.removeBinDing(App.getInstance().userId)
                    }

                })
                .show(fragmentManager, "")
    }

    /**
     * 绑定成功
     */
    override fun onBinDingSuccess() {
        showToast("关联成功")
        status = "1"
        account_correlation_account_tv.text = "已关联"
        mPresenter.checkBinDing(App.getInstance().userId)
    }

    override fun onCheckBinDingSuccess(httpResult: HttpResult<String>?) {
        status = httpResult!!.data
        message = httpResult!!.message
        if (status.equals("1")) {
            account_correlation_account_tv.text = "已关联"
        } else {
            account_correlation_account_tv.text = "未关联"
        }
    }

    /**
     * 解除绑定成功
     */
    override fun onRemoveBinDingSuccess() {
        pop()
    }

    override fun onFail(s: String?) {
        showToast(s)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }
}