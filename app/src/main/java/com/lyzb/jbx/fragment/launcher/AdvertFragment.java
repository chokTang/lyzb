package com.lyzb.jbx.fragment.launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;

import com.like.longshaolib.base.BaseFragment;
import com.like.utilslib.image.LoadImageUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.activity.HomeActivity;
import com.lyzb.jbx.inter.ILoginState;
import com.lyzb.jbx.model.launcher.AdvertModel;
import com.lyzb.jbx.mvp.presenter.launcher.AdvertPresenter;
import com.lyzb.jbx.mvp.view.launcher.IAdvertView;
import com.lyzb.jbx.util.AppPreference;
import com.lyzb.jbx.util.timer.BaseTimerTask;
import com.lyzb.jbx.util.timer.ITimerListener;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Activity.YSCWebViewActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Util.Utils;

import java.text.MessageFormat;
import java.util.Timer;

/**
 * 广告页面
 */
public class AdvertFragment extends BaseFragment<AdvertPresenter> implements IAdvertView, ITimerListener, View.OnClickListener {

    private static final String PARAMS_ADVERT = "PARAMS_ADVERT";
    private AdvertModel mModel;

    private ImageView img_advert;
    private AppCompatTextView advert_timer_tv;

    private Timer mTimer = null;
    private int mCount = 5;
    private ILoginState mILoginState;

    public static AdvertFragment newIntance(AdvertModel model) {
        AdvertFragment fragment = new AdvertFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARAMS_ADVERT, model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mModel = (AdvertModel) args.getSerializable(PARAMS_ADVERT);
            mCount = mModel.getAdvert_time();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILoginState) {
            mILoginState = (ILoginState) activity;
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        img_advert = findViewById(R.id.img_advert);
        advert_timer_tv = findViewById(R.id.advert_timer_tv);

        img_advert.setOnClickListener(this);
        advert_timer_tv.setOnClickListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        String imgaeUrl = Utils.setImgNetUrl(Api.API_HEAD_IMG_URL, mModel.getXhdpi());
        LoadImageUtil.loadImage(img_advert, imgaeUrl);

        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task, 0, 1000);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_advert;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击跳转到广告页面
            case R.id.img_advert:
                if (mTimer != null) {
                    mTimer.cancel();
                    mTimer = null;
                }
                Intent mainIntent = new Intent(getBaseActivity(), HomeActivity.class);
                Intent advertIntent;
                if (Utils.verCityLifeUrl(mModel.getAdvert_link())) {
                    advertIntent = new Intent(getBaseActivity(), ProjectH5Activity.class);
                } else {
                    advertIntent = new Intent(getBaseActivity(), YSCWebViewActivity.class);
                }
                advertIntent.putExtra(Key.KEY_URL.getValue(), mModel.getAdvert_link());
                getBaseActivity().startActivities(new Intent[]{mainIntent, advertIntent});
                getBaseActivity().finish();
                break;
            //点击跳过
            case R.id.advert_timer_tv:
                if (mTimer != null) {
                    mTimer.cancel();
                    mTimer = null;
                    checkIsShowScroll();
                }
                break;
        }
    }

    @Override
    public void onTimer() {
        getBaseActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (advert_timer_tv != null) {
                    advert_timer_tv.setText(MessageFormat.format("跳过\n{0}s", mCount));
                    mCount--;
                    if (mCount < 0) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                            checkIsShowScroll();
                        }
                    }
                }
            }
        });
    }

    private void checkIsShowScroll() {
        //检查用户是否登陆了APP
        if (AppPreference.getIntance().getAccountLonginState()) {
            if (mILoginState != null) {
                mILoginState.enterHomePage();
            }
        } else {
            if (mILoginState != null) {
                mILoginState.enterLoginPage();
            }
        }
    }
}
