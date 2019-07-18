package com.szy.yishopcustomer.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.like.longshaolib.dialog.original.AlertDialog;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.R;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Interface.OnFragmentFinish;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.User.OldUserModel;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.CacheMode;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Smart on 2017/11/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class EditMobileTwoFragment extends YSCBaseFragment implements TextWatcher {

    @BindView(R.id.fragment_edit_password_password_edittext)
    CommonEditText mPasswordEdittext;
    @BindView(R.id.layout_waring)
    View mTipLayout;
    @BindView(R.id.warning_tip)
    TextView mTip;
    @BindView(R.id.submit_button)
    Button mNextButton;
    private TimeCount time;

    @BindView(R.id.send_code)
    TextView mEditPasswordCodeButton;

    @BindView(R.id.stepview)
    HorizontalStepView stepView;

    @BindView(R.id.fragment_edit_password_code_edittext)
    CommonEditText mEditPasswordCode;

    private OnFragmentFinish mListener;

    @BindView(R.id.fragment_login_password_captcha_layout)
    View loginPasswordCaptchaLayout;
    @BindView(R.id.fragment_login_password_login_captcha)
    ImageView passwordLoginCaptcha;
    @BindView(R.id.fragment_login_password_captcha_edittext)
    EditText passwordLoginCaptchaEdit;

    private OldUserModel userModel;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        isTelValid = false;
        mNextButton.setEnabled(isFinishButtonEnabled());
        String mPassword = mPasswordEdittext.getText().toString();
        if (!TextUtils.isEmpty(mPassword)) {
            if (Utils.isPhone(mPassword)) {
                //调用接口判断是否是原手机号，或者已经使用的手机号
                mTipLayout.setVisibility(View.GONE);
                validTel(mPassword);
            } else {
                mTipLayout.setVisibility(View.VISIBLE);
                mTip.setText("新手机号码是无效的。");
            }
        } else {
            mTipLayout.setVisibility(View.VISIBLE);
            mTip.setText("请输入新手机号码");
        }
    }

    public void validTel(String tel) {
        CommonRequest request = new CommonRequest(Api.API_SECURITY_CHECKOUT_MODEL, HttpWhat
                .HTTP_SECURITY_CLIENT_VALIDATE.getValue(), RequestMethod.POST);
        request.add("mobile", tel);
        addRequest(request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_button:
                if (userModel.getState()==1){
                    new AlertDialog(getContext())
                            .builder()
                            .setMsg("是否继续绑定？")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    changePassword();
                                }
                            })
                            .setNegativeButton("",null)
                            .show();
                }else {
                    changePassword();
                }
                break;
            case R.id.send_code:
                requestPhoneCode();
                break;
            default:
                super.onClick(v);
        }
    }

    private void requestPhoneCode() {
        String tel = mPasswordEdittext.getText().toString().trim();
        String captcha = passwordLoginCaptchaEdit.getText().toString().trim();
        if (TextUtils.isEmpty(captcha)) {
            toast("请输入验证码");
            return;
        }

        if (Utils.isPhone(tel)) {
            CommonRequest request = new CommonRequest(Api.API_SECURITY_SEND_CODE, HttpWhat
                    .HTTP_POST_VERIFY_CODE.getValue(), RequestMethod.POST);
            request.add("mobile", tel);
            request.add("captcha", captcha);
            addRequest(request);
        } else {
            toast("新手机号码是无效的");
        }
    }


    private void changePassword() {
        String code = mEditPasswordCode.getText().toString().trim();
        String tel = mPasswordEdittext.getText().toString().trim();
        String captcha = passwordLoginCaptchaEdit.getText().toString().trim();

//        mNextButton.setEnabled(isFinishButtonEnabled());
//        CommonRequest request = new CommonRequest(Api.API_SECURITY_EDIT_MOBILE, HttpWhat
//                .HTTP_POST_NEW_PASSWORD.getValue(), RequestMethod.POST);
//        request.add("EditMobileModel[mobile]", tel);
//        request.add("EditMobileModel[sms_captcha]", code);
//        request.add("EditMobileModel[captcha]", captcha);

        mNextButton.setEnabled(isFinishButtonEnabled());
        CommonRequest request = new CommonRequest(Api.API_SECURITY_BIND_MODEL, HttpWhat
                .HTTP_POST_NEW_PASSWORD.getValue(), RequestMethod.POST);
        request.add("mobile", tel);
        request.add("user_id", App.getInstance().userId);
        request.add("code", code);
        addRequest(request);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        passwordLoginCaptcha.setVisibility(View.VISIBLE);
        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("验证身份", 1);
        StepBean stepBean1 = new StepBean("绑定验证手机号码", 0);
        StepBean stepBean2 = new StepBean("完成", -1);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepView.setStepViewTexts(stepsBeanList).setTextSize(16);

        mNextButton.setOnClickListener(this);
        mPasswordEdittext.addTextChangedListener(this);
        mEditPasswordCodeButton.setOnClickListener(this);

        mEditPasswordCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mNextButton.setEnabled(isFinishButtonEnabled());
            }
        });

        mNextButton.setText(R.string.next_step);

        passwordLoginCaptcha.setOnClickListener(this);

        getVerCode();
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_edit_mobile_two;
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_POST_NEW_PASSWORD:
                HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
                    @Override
                    public void onSuccess(ResponseCommonModel back) {

                        if (mListener != null) {
                            mListener.onFinish(EditMobileTwoFragment.this);
                        }
                    }
                }, true);
                break;
            case HTTP_POST_VERIFY_CODE:
                HttpResultManager.resolve(response, ResponseCommonModel.class, new
                        HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
                            @Override
                            public void onSuccess(ResponseCommonModel back) {
                                Toast.makeText(getActivity(), back.message, Toast.LENGTH_SHORT).show();
                                time = new TimeCount(60000, 1000);
                                time.start();
                            }
                        }, true);
                break;
            case HTTP_SECURITY_CLIENT_VALIDATE:
                userModel = GSONUtil.getEntity(response, OldUserModel.class);
                if (userModel.getCode() == 0) {
                    if (userModel.getState() == 0) {//表示改手机号可以使用
                        isTelValid = true;
                        mTipLayout.setVisibility(View.GONE);
                    } else if (userModel.getState() == 1) {//表示手机号已经被占用
                        isTelValid = true;
                        mTipLayout.setVisibility(View.VISIBLE);
                        mTip.setText(userModel.getMessage());
                    } else {
                        isTelValid = false;
                        mTipLayout.setVisibility(View.VISIBLE);
                        mTip.setText(userModel.getMessage());
                    }
                } else {
                    Toast.makeText(getContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    //判断手机是否通过验证
    private boolean isTelValid = false;

    private boolean isFinishButtonEnabled() {
        boolean isEnabled, isEnabledTwo = false;
        isEnabled = !Utils.isNull(mEditPasswordCode.getText().toString());
        isEnabledTwo = !Utils.isNull(mPasswordEdittext.getText().toString());
        return isEnabled && isEnabledTwo && isTelValid;
    }

    public static EditMobileTwoFragment newInstance() {
        EditMobileTwoFragment fragment = new EditMobileTwoFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentFinish) {
            mListener = (OnFragmentFinish) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentFinish");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            mEditPasswordCodeButton.setText(getResources().getString(R.string.sendVerifyCode));
            mEditPasswordCodeButton.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mEditPasswordCodeButton.setClickable(false);
            mEditPasswordCodeButton.setText(millisUntilFinished / 1000 + "秒");
        }
    }

    @Override
    public void onDestroy() {
        if (time != null) {
            time.cancel();
            time = null;
        }
        super.onDestroy();
    }

    private void getVerCode() {
        Request<Bitmap> imageRequest = NoHttp.createImageRequest(Api.API_REFRESH_CAPTCHA);//这里 RequestMethod.GET可以不写（删除掉即
        imageRequest.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        imageRequest.setUserAgent("szyapp/android");

        this.mRequestQueue.add(HttpWhat.HTTP_CAPTCHA.getValue(), imageRequest, new SimpleResponseListener<Bitmap>() {
            @Override//请求成功的回调
            public void onSucceed(int i, Response<Bitmap> response) {
                passwordLoginCaptcha.setImageBitmap(response.get());
            }

            @Override
            public void onFailed(int what, Response<Bitmap> response) {
                super.onFailed(what, response);
            }
        });
    }


}
