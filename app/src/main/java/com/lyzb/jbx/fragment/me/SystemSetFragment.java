package com.lyzb.jbx.fragment.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.dialog.fragment.AlertDialogFragment;
import com.like.utilslib.app.AppUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.fragment.home.MainFragment;
import com.lyzb.jbx.model.eventbus.DynamicItemStatusEventBus;
import com.lyzb.jbx.model.eventbus.LonginInEventBus;
import com.lyzb.jbx.model.eventbus.MainIndexEventBus;
import com.lyzb.jbx.mvp.presenter.me.SysSetPresenter;
import com.lyzb.jbx.mvp.view.me.ISysSetView;
import com.lyzb.jbx.util.AppCommonUtil;
import com.szy.common.Other.CommonEvent;
import com.szy.yishopcustomer.Activity.AccountSecurityStepActivity;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.DataCleanManager;
import com.yolanda.nohttp.cookie.DBCookieStore;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 系统设置
 * @time 2019 2019/3/4 14:40
 */

public class SystemSetFragment extends BaseFragment<SysSetPresenter> implements ISysSetView {


    @BindView(R.id.img_union_me_set_back)
    ImageView backImg;

    @BindView(R.id.lin_union_me_set_pwd)
    LinearLayout pwdLin;
    @BindView(R.id.lin_union_me_set_status)
    LinearLayout statusLin;
    @BindView(R.id.lin_union_me_set_we)
    LinearLayout weLin;
    @BindView(R.id.lin_privacy_set)
    LinearLayout privacy_set;
    @BindView(R.id.lin_union_me_set_cache)
    LinearLayout lin_union_me_set_cache;

    @BindView(R.id.tv_union_me_cache)
    TextView cacheText;

    @BindView(R.id.tv_union_me_version)
    TextView versionText;

    @BindView(R.id.tv_set_login_out)
    TextView loginOut;

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {

        ButterKnife.bind(this, mView);

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        pwdLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改密码
                //start(new SetPwdFragment());

                Intent intent = new Intent(getContext(), AccountSecurityStepActivity.class);
                intent.putExtra(AccountSecurityStepActivity.TYPE_STEP, AccountSecurityStepActivity.TYPE_STEP_PWD);
                startActivity(intent);
            }
        });


        statusLin.setVisibility(View.GONE);

        statusLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //身份认证
                start(new StatusFragment());
            }
        });

        weLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关于我们
                start(new AboutFragment());
            }
        });

        privacy_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐私设置
                start(new PrivacySetFragment());
            }
        });

        cacheText.setText(AppCommonUtil.getCache(getActivity()));

        lin_union_me_set_cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((AppCommonUtil.getCache(getActivity()).contains("0.0"))) {
                    showToast("暂无缓存");
                    return;
                }

                AlertDialogFragment.newIntance()
                        .setCancleable(true)
                        .setContent("是否要清除缓存")
                        .setCancleBtn(null)
                        .setSureBtn(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DataCleanManager.clearAllCache(getActivity());
                                cacheText.setText(AppCommonUtil.getCache(getActivity()));
                                showToast("缓存清除成功");
                            }
                        })
                        .show(getFragmentManager(), "CancleTag");
            }
        });

        versionText.setText(AppUtil.getVersionName());

        loginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogFragment.newIntance()
                        .setKeyBackable(false)
                        .setCancleable(false)
                        .setContent("是否退出登录?")
                        .setCancleBtn(null)
                        .setSureBtn(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setLoginOut();
                            }
                        })
                        .show(getFragmentManager(), "LogOutTag");
            }
        });
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public Object getResId() {
        return R.layout.fragment_union_sys_set;
    }

    private void setLoginOut() {
        mPresenter.Logout(getContext());
    }

    @Override
    public void loginOut() {
        popTo(MainFragment.class,false);
        App.localLogOut(getContext());
        DataCleanManager.clearAllCache(getActivity());
        DBCookieStore store = new DBCookieStore(getContext());
        store.removeAll();//删除所有的cookie

        EventBus.getDefault().post(new CommonEvent(EventWhat.HX_UNLISTINENER.getValue()));
        EventBus.getDefault().post(new MainIndexEventBus(0));
        EventBus.getDefault().post(new LonginInEventBus());
        EventBus.getDefault().post(new DynamicItemStatusEventBus(true));
    }
}
