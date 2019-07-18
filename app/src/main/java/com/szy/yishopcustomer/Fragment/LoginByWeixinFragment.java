package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.szy.common.View.CustomProgressDialog;
import com.szy.yishopcustomer.Activity.LoginByOtherWayActivity;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.Utils;

import butterknife.BindView;

/**
 * Created by liwei on 2017/1/9.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class LoginByWeixinFragment extends YSCBaseFragment {
    public static ButtonDownListener mButtonListener;
    public static CustomProgressDialog mProgress;
    @BindView(R.id.fragment_login_by_otherWay_button)
    Button mLoginByOtherButton;
    @BindView(R.id.fragment_login_by_weixin_button)
    Button mLoginByWeiXinButton;
    @BindView(R.id.fragment_login_by_weixin_logo)
    ImageView mWeixinLoginLogoImageView;

    @Override
    public void onClick(View v) {
        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        int extraInfo = Utils.getExtraInfoOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_LOGIN_BY_OTHER_WAY:
                openOtherLoginWayActivity();
                break;
            case VIEW_TYPE_WEIXIN:
                if (Utils.isWeixinAvilible(getContext())) {
                    mProgress.show();
                    if (mButtonListener != null) {
                        mButtonListener.DownListener("weixin");
                    }
                } else {
                    Utils.toastUtil.showToast(getActivity(), "请先安装微信客户端");
                }
                break;
            default:
                super.onClick(v);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        Utils.setViewTypeForTag(mLoginByOtherButton, ViewType.VIEW_TYPE_LOGIN_BY_OTHER_WAY);
        mLoginByOtherButton.setOnClickListener(this);

        Utils.setViewTypeForTag(mLoginByWeiXinButton, ViewType.VIEW_TYPE_WEIXIN);
        mLoginByWeiXinButton.setOnClickListener(this);

        mProgress = new CustomProgressDialog(getActivity());
        mProgress.setCanceledOnTouchOutside(false);

        updateLogo();
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_login_by_weixin;
    }

    public void openOtherLoginWayActivity() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), LoginByOtherWayActivity.class);
        startActivity(intent);
    }

    public void setOnButtonListener(ButtonDownListener ButtonDownListener) {
        mButtonListener = ButtonDownListener;
    }

    private void updateLogo() {
        if (!Utils.isNull(App.getInstance().weixinLoginLogo)) {
            ImageLoader.getInstance().displayImage(Utils.urlOfImage(App.getInstance()
                    .weixinLoginLogo,false), mWeixinLoginLogoImageView);
        } else {
            mWeixinLoginLogoImageView.setBackgroundResource(R.mipmap.ic_weixin_other_login);
        }
    }

    public interface ButtonDownListener {
        void DownListener(String type);
    }
}

