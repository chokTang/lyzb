package com.szy.yishopcustomer.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.FindPassword.FindPasswordStepOne;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import butterknife.BindView;

/**
 * Created by liwei on 2016/4/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class FindPasswordOneFragment extends YSCBaseFragment implements TextWatcher, TextView
        .OnEditorActionListener {

    private static final String TAG = "FindPasswordOneFragment";
    public String type;
    @BindView(R.id.fragment_find_password_username_editText)
    CommonEditText mUserNameEditText;
    @BindView(R.id.submit_button)
    Button mStepFinishButton;
    @BindView(R.id.warning_layout)
    LinearLayout mFindPasswordTipLayout;
    @BindView(R.id.warning_tip)
    TextView mFindPasswordTip;
    private FinishUserName mFinishUserName;
    private String mRegisterLink = "";
    private View.OnClickListener mOnClickListener;
    private String mUserName;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        mStepFinishButton.setEnabled(isFinishButtonEnabled());
        mUserName = mUserNameEditText.getText().toString();
        if (mUserNameEditText.getText().toString().length() != 0) {

            mFindPasswordTipLayout.setVisibility(View.GONE);
        } else {

            mFindPasswordTipLayout.setVisibility(View.VISIBLE);
            mFindPasswordTip.setText("请输入您的用户名/邮箱/已验证手机");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_button:
                CommonRequest mGetFindPasswordUserNameRequest = new CommonRequest(Api
                        .API_FIND_PASSWORD_STEP_ONE, HttpWhat.HTTP_USERNAME_EXIST.getValue(),
                        RequestMethod.POST);
                mGetFindPasswordUserNameRequest.add("AccountModel[username]", mUserName);
                addRequest(mGetFindPasswordUserNameRequest);
                break;
            default:
                super.onClick(v);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        mStepFinishButton.setOnClickListener(this);
        mUserNameEditText.addTextChangedListener(this);
        mUserNameEditText.setOnEditorActionListener(this);
        mStepFinishButton.setText(getResources().getString(R.string.next_step));
        Utils.showSoftInputFromWindow(getActivity(),mUserNameEditText);
        return v;
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_USERNAME_EXIST:
                FindPasswordStepOne model = JSON.parseObject(response, FindPasswordStepOne.class);
                if (model.code == 0) {
                    if (mFinishUserName != null) {
                        String nikename = model.data.model.username;
                        String phone = model.data.mobile;
                        String email = model.data.email;

                        if(!Utils.isNull(model.data.mobile)){
                            type = "phone";
                        }else{
                            type = "email";
                        }

                        mFinishUserName.onFinishUserName(nikename, phone, email, type);
                    }
                } else {
                    Utils.makeToast(getActivity(), model.data.error.username.get(0).toString());
                }
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_find_password_one;

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {
            case R.id.fragment_find_password_username_editText:
                if (isFinishButtonEnabled()) {
                    mStepFinishButton.performClick();
                }
                return false;
            default:
                return false;
        }
    }

    public void setFinishUserName(FinishUserName finishUserName) {
        mFinishUserName = finishUserName;
    }

/*    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }*/

    private boolean isFinishButtonEnabled() {
        String username = mUserNameEditText.getText().toString();
        boolean isEnabled = !Utils.isNull(username);
        Log.i(TAG, "isEnabled is " + isEnabled);
        return isEnabled;
    }

    public interface FinishUserName {
        void onFinishUserName(String nickname, String mobile, String email, String type);
    }
}
