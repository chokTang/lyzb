package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.View.CustomProgressDialog;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.Utils;

import butterknife.BindView;

/**
 * Created by liwei on 2017/1/9.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 *
 */
public class LoginByOtherWayFragment extends YSCBaseFragment {
    private ButtonDownListener mButtonListener;
    private CustomProgressDialog mProgress;
    @BindView(R.id.cancel_button)
    Button mCancelButton;
    @BindView(R.id.cancel_layout)
    TextView mCancelLayout;
    @BindView(R.id.login_by_weibo_layout)
    LinearLayout mWeiboLogin;
    @BindView(R.id.login_by_qq_layout)
    LinearLayout mQQLogin;
    @BindView(R.id.login_by_phone_layout)
    LinearLayout mPhoneLogin;

    @Override
    public void onClick(View v) {
        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        int extraInfo = Utils.getExtraInfoOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_CANCEL:
                getActivity().finish();
                break;
            case VIEW_TYPE_PHONE:
                getActivity().finish();
                openLoginActivity();
                break;
            case VIEW_TYPE_QQ:
                if (mButtonListener != null) {
                    mButtonListener.DownListener("qq");
                }
                break;
            case VIEW_TYPE_WEIBO:
                if (mButtonListener != null) {
                    mButtonListener.DownListener("weibo");
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

        Utils.setViewTypeForTag(mCancelButton, ViewType.VIEW_TYPE_CANCEL);
        mCancelButton.setOnClickListener(this);
        Utils.setViewTypeForTag(mCancelLayout, ViewType.VIEW_TYPE_CANCEL);
        mCancelLayout.setOnClickListener(this);

        if (Config.WEIBO_KEY.equals("")) {
            mWeiboLogin.setVisibility(View.GONE);
        } else {
            mWeiboLogin.setVisibility(View.VISIBLE);
        }

        if (Config.TENCENT_ID.equals("")) {
            mQQLogin.setVisibility(View.GONE);
        } else {
            mQQLogin.setVisibility(View.VISIBLE);
        }

        Utils.setViewTypeForTag(mWeiboLogin, ViewType.VIEW_TYPE_WEIBO);
        mWeiboLogin.setOnClickListener(this);
        Utils.setViewTypeForTag(mQQLogin, ViewType.VIEW_TYPE_QQ);
        mQQLogin.setOnClickListener(this);
        Utils.setViewTypeForTag(mPhoneLogin, ViewType.VIEW_TYPE_PHONE);
        mPhoneLogin.setOnClickListener(this);

        mProgress = new CustomProgressDialog(getActivity());
        mProgress.setCanceledOnTouchOutside(false);

        if(TextUtils.isEmpty(Config.WEIBO_KEY.trim())) {
            mWeiboLogin.setVisibility(View.GONE);
        }

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_login_by_other_way;
    }

    public void setOnButtonListener(ButtonDownListener ButtonDownListener) {
        mButtonListener = ButtonDownListener;
    }

    private void openLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }

    public interface ButtonDownListener {
        void DownListener(String type);
    }

    public void mProgressSwitch(boolean flag){
        if(mProgress != null) {
            if(flag) {
                if(!mProgress.isShowing()) mProgress.show();
            } else {
                if(mProgress.isShowing()) mProgress.dismiss();
            }
        }
    }
}

