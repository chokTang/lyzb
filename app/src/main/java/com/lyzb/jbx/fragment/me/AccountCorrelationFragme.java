package com.lyzb.jbx.fragment.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.dialog.fragment.AlertDialogFragment;
import com.like.longshaolib.net.model.HttpResult;
import com.lyzb.jbx.R;
import com.lyzb.jbx.mvp.presenter.AccountCorrelationPresenter;
import com.lyzb.jbx.mvp.view.me.IAccountCorrelationView;
import com.szy.common.Other.CommonEvent;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Util.App;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author wyx
 * @role 帐号绑定微信
 * @time 2019 2019/5/16 10:32
 */

public class AccountCorrelationFragme extends BaseFragment<AccountCorrelationPresenter>
        implements IAccountCorrelationView {
    @BindView(R.id.statistics_title_tv)
    TextView mStatisticsTitleTv;
    @BindView(R.id.account_correlation_account_tv)
    TextView mAccountCorrelationAccountTv;
    Unbinder unbinder;


    private IWXAPI api;
    private String mBinDingStatus;
    private String message = "您是否要解除账号与微信关联？解除后可用账号密码登录系统";

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        mStatisticsTitleTv.setText("帐号关联");
        mPresenter.checkBinDing(App.getInstance().userId);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public Object getResId() {
        return R.layout.fragment_account_correlation;
    }

    /**
     * 绑定微信成功
     */
    @Override
    public void onBinDingSuccess() {
        showToast("关联成功");
        mBinDingStatus = "1";
        mAccountCorrelationAccountTv.setText("已关联");
    }

    /**
     * 查询绑定状态成功
     */
    @Override
    public void onCheckBinDingSuccess(HttpResult<String> httpResult) {
        mBinDingStatus = httpResult.getData();
        message = httpResult.getMessage();
        if (mBinDingStatus.equals("1")) {
            mAccountCorrelationAccountTv.setText("已关联");
        } else {
            mAccountCorrelationAccountTv.setText("未关联");
        }
    }

    /**
     * 解除绑定成功
     */
    @Override
    public void onRemoveBinDingSuccess() {
        pop();
    }

    @Override
    public void onFail(String msg) {
        showToast(msg);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this, rootView);

        api = WXAPIFactory.createWXAPI(_mActivity, Config.WEIXIN_APP_ID, true);
        api.registerApp(Config.WEIXIN_APP_ID);

        return rootView;
    }

    /**
     * 获取微信授权
     */
    private void toWX() {
        //api注册
        if (api == null) {
            api = WXAPIFactory.createWXAPI(_mActivity, Config.WEIXIN_APP_ID, true);
            api.registerApp(Config.WEIXIN_APP_ID);
        }

        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        api.sendReq(req);
    }

    /**
     * @param event
     */
    @Subscribe
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_WEIXIN_SHARE:
                String code = event.getMessage();
                switch (Integer.parseInt(code)) {
                    case BaseResp.ErrCode.ERR_OK:
                        mPresenter.getOpenId();
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        //取消
                        break;
                    default:
                        //异常
                        break;
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.statistics_back_iv, R.id.account_correlation_account_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //返回
            case R.id.statistics_back_iv:
                pop();
                break;
            //绑定
            case R.id.account_correlation_account_tv:
                if (mBinDingStatus.equals("1")) {
                    showDialog();
                } else {
                    toWX();
                }
                break;
            default:
        }
    }

    /**
     * 解除绑定提示
     */
    private void showDialog() {
        AlertDialogFragment.newIntance()
                .setKeyBackable(false)
                .setCancleable(false)
                .setContent(message)
                .setCancleBtn(null)
                .setSureBtn("解除关联", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //解除绑定
                        mPresenter.removeBinDing(App.getInstance().userId);
                    }
                })
                .show(getFragmentManager(), "removeBinDing");
    }
}
