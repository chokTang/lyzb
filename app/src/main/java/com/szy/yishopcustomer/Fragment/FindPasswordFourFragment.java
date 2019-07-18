package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;

import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;

import butterknife.BindView;

/**
 * Created by liwei on 2016/4/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class FindPasswordFourFragment extends YSCBaseFragment {
    @BindView(R.id.submit_button)
    Button mLoginButton;
    private View.OnClickListener mOnClickListener;

    public static FindPasswordFourFragment newInstance(String password) {
        FindPasswordFourFragment fragment = new FindPasswordFourFragment();
        Bundle arguments = new Bundle();
        arguments.putString(Key.KEY_NEW_PASSWORD.getValue(), password);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_button:
                Intent intent = new Intent();
                intent.setClass(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            default:
                super.onClick(v);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        mLoginButton.setOnClickListener(this);
        mLoginButton.setText(getResources().getString(R.string.loginNow));
        mLoginButton.setEnabled(true);
        Utils.hideKeyboard(mLoginButton);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_find_password_four;

        //找回密码第三步传过来的值，预留
        Bundle arguments = getArguments();
        if (arguments != null) {
            String new_password = arguments.getString(Key.KEY_NEW_PASSWORD.getValue());
        }
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

}
