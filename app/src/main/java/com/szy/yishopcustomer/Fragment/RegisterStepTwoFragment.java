package com.szy.yishopcustomer.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.ShowCaptchaModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.CacheMode;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;

import butterknife.BindView;

/**
 * Created by liwei on 2016/4/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class RegisterStepTwoFragment extends YSCBaseFragment implements TextWatcher {
    @BindView(R.id.fragment_register_desc)
    TextView mDescription;
    @BindView(R.id.fragment_register_verify_code_editText)
    CommonEditText mVerifyCodeEditText;
    @BindView(R.id.send_code)
    TextView mSendButton;
    @BindView(R.id.submit_button)
    Button mStepFinishButton;
    @BindView(R.id.warning_layout)
    LinearLayout mRegisterStepTwoTipLayout;
    @BindView(R.id.warning_tip)
    TextView mRegisterStepTwoTip;
    private String mPhoneNumber;
    private FinishInputVerifyCode mFinishInputVerifyCode;
    private String mVerifyCode = "";
    private TimeCount time;


    @BindView(R.id.captcha_view)
    View captchaView;
    @BindView(R.id.layout_captcha)
    View captchaLayout;
    @BindView(R.id.captcha_edittext)
    EditText captchaEdittext;
    @BindView(R.id.captcha)
    ImageView captcha;
    private boolean showCaptcha;
    private int flag;

    public static RegisterStepTwoFragment newInstance(String phoneNumber) {
        RegisterStepTwoFragment fragment = new RegisterStepTwoFragment();
        Bundle arguments = new Bundle();
        arguments.putString(Key.KEY_PHONE_NUMBER.getValue(), phoneNumber);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        mStepFinishButton.setEnabled(isFinishButtonEnabled());
        mVerifyCode = mVerifyCodeEditText.getText().toString();

        if (mVerifyCode.length() != 0) {
            mRegisterStepTwoTipLayout.setVisibility(View.GONE);
        } else {
            mRegisterStepTwoTipLayout.setVisibility(View.VISIBLE);
            mRegisterStepTwoTip.setText("短信校验码不能为空");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_code:
                if(showCaptcha&&Utils.isNull(captchaEdittext.getText().toString())){
                    Toast.makeText(getActivity(),"图片验证码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                sendVerifyCode();
                break;
            case R.id.submit_button:
                checkVerifyCode(mPhoneNumber, mVerifyCode);
                break;
            case R.id.captcha:
                getVerCode();
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        mDescription.setText(String.format(getResources().getString(R.string
                .pleaseEnterVerifyCode), mPhoneNumber));
        mSendButton.setOnClickListener(this);
        mStepFinishButton.setOnClickListener(this);

        mVerifyCodeEditText.addTextChangedListener(this);
        mStepFinishButton.setText(getResources().getString(R.string.next_step));
        time = new TimeCount(60000, 1000);

        captcha.setOnClickListener(this);
        captchaEdittext.requestFocus();
        Utils.showSoftInputFromWindow(getActivity(),captchaEdittext);
        getVerCode();
        sendVerifyCode();
        return v;
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_VERIFY_CODE:
                ShowCaptchaModel model = JSON.parseObject(response, ShowCaptchaModel.class);

                if(model.code == 0){
                    time.start();
                    Utils.makeToast(getActivity(), model.message);
                }else if(model.code == -1){
                    //CODE值为-1，    "message":"每60秒内只能发送一次短信验证码，请稍候重试"
                    Toast.makeText(getActivity(),model.message,Toast.LENGTH_SHORT).show();
                }else{
                    //CODE值为1，    "message":"请输入图片验证码"
                    showCaptcha = model.data.show_captcha;
                    if(showCaptcha){
                        captchaView.setVisibility(View.VISIBLE);
                        captchaLayout.setVisibility(View.VISIBLE);
                    }else{
                        captchaView.setVisibility(View.GONE);
                        captchaLayout.setVisibility(View.GONE);
                    }
                }
                break;
            case HTTP_CHECK_CAPTCHA:
                HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
                    @Override
                    public void onSuccess(ResponseCommonModel back) {
                        time.onFinish();
                        Utils.makeToast(getActivity(), back.message);
                        if (mFinishInputVerifyCode != null) {
                            mFinishInputVerifyCode.onFinishVerifyCode(mPhoneNumber, mVerifyCode);
                        }
                    }
                },true);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_register_step_two;
        Bundle arguments = getArguments();
        if (arguments != null) {
            mPhoneNumber = arguments.getString(Key.KEY_PHONE_NUMBER.getValue());
        }
    }

    public void setFinishInputVerifyCode(FinishInputVerifyCode finishInputVerifyCode) {
        mFinishInputVerifyCode = finishInputVerifyCode;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
        mDescription.setText(String.format(getResources().getString(R.string
                .pleaseEnterVerifyCode), mPhoneNumber));
    }

    private void checkVerifyCode(String mobile, String code) {
        CommonRequest mCheckVerifyCodeRequest = new CommonRequest(Api.API_CHECK_SMS_CAPTCHA, HttpWhat.HTTP_CHECK_CAPTCHA.getValue(), RequestMethod.POST);
        mCheckVerifyCodeRequest.add("mobile", mobile);
        mCheckVerifyCodeRequest.add("sms_captcha", code);
        addRequest(mCheckVerifyCodeRequest);
    }

    private boolean isFinishButtonEnabled() {
        String code = mVerifyCodeEditText.getText().toString();
        return !Utils.isNull(code);
    }

/*    private String phoneNum(String phoneNumber) {
        String str = "";
        for (int i = 0; i < phoneNumber.length(); i++) {
            if (i == phoneNumber.length() - 11) {
                str += phoneNumber.charAt(i);
            } else if (i == phoneNumber.length() - 10) {
                str += phoneNumber.charAt(i);
            } else if (i == phoneNumber.length() - 9) {
                str += phoneNumber.charAt(i);
            } else if (i == phoneNumber.length() - 3) {
                str += phoneNumber.charAt(i);
            } else if (i == phoneNumber.length() - 2) {
                str += phoneNumber.charAt(i);
            } else if (i == phoneNumber.length() - 1) {
                str += phoneNumber.charAt(i);
            } else {
                str += "*";
            }
        }
        return str;
    }*/

    private void sendVerifyCode() {
        CommonRequest mGetVerifyCodeRequest = new CommonRequest(Api.API_REGISTER_SMS_CAPTCHA,
                HttpWhat.HTTP_VERIFY_CODE.getValue(), RequestMethod.POST);
        mGetVerifyCodeRequest.add("mobile", mPhoneNumber);
        if(showCaptcha){
            mGetVerifyCodeRequest.add("captcha", captchaEdittext.getText().toString());
        }
        addRequest(mGetVerifyCodeRequest);
    }

    public interface FinishInputVerifyCode {
        void onFinishVerifyCode(String phoneNumber, String verifyCode);
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            mSendButton.setClickable(false);
            mSendButton.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            mSendButton.setText(getResources().getString(R.string.sendVerifyCode));
            mSendButton.setClickable(true);
        }
    }

    //刷新并获取图形验证码图片
    private String refreshCaptcha() {
        return Api.API_REFRESH_CAPTCHA;
    }

    //获取图形验证码图片
    private String getCaptcha() {
        return Api.API_CAPTCHA;
    }

    private void getVerCode() {
        Request<Bitmap> imageRequest = NoHttp.createImageRequest(refreshCaptcha());//这里 RequestMethod.GET可以不写（删除掉即
        imageRequest.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        imageRequest.setUserAgent("szyapp/android");

        this.mRequestQueue.add(HttpWhat.HTTP_CAPTCHA.getValue(), imageRequest, new SimpleResponseListener<Bitmap>() {
            @Override//请求成功的回调
            public void onSucceed(int i, Response<Bitmap> response) {
                captcha.setImageBitmap(response.get());
            }

            @Override
            public void onFailed(int what, Response<Bitmap> response) {
                super.onFailed(what, response);
            }
        });
    }

    @Override
    public void onDestroy() {
        if (time != null) {
            time.cancel();
            time = null;
        }
        super.onDestroy();
    }
}