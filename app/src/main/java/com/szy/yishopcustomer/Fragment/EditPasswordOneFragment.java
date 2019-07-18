package com.szy.yishopcustomer.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Activity.AccountSecurityStepActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Interface.OnFragmentFinish;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AddAccount.Model;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
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
 * Created by liwei on 2017/7/11.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class EditPasswordOneFragment extends YSCBaseFragment implements OnEmptyViewClickListener {

    private static final String TAG = "FindPasswordOneFragment";
    public String type;
    @BindView(R.id.fragment_edit_password_select_layout)
    LinearLayout mPasswordTypeLayout;

    @BindView(R.id.fragment_edit_password_password_edittext)
    EditText mPasswordEditText;
    @BindView(R.id.fragment_edit_password_show_password)
    ImageView mShowPasswordButton;
    @BindView(R.id.submit_button)
    Button mStepFinishButton;
    @BindView(R.id.fragment_edit_password_type)
    TextView mType;
    @BindView(R.id.fragment_edit_password_name)
    TextView mEditPasswordName;
    @BindView(R.id.fragment_edit_password_name_content)
    TextView mEditPasswordNameContent;

    @BindView(R.id.fragment_edit_password_password_layout)
    LinearLayout mEditPasswordPasswordLayout;

    @BindView(R.id.fragment_edit_password_code_layout)
    LinearLayout mEditPasswordCodeLayout;

    @BindView(R.id.layout_waring)
    View mEditPasswordTipLayout;
    @BindView(R.id.warning_tip)
    TextView mEditPasswordTip;
    @BindView(R.id.fragment_edit_password_code_edittext)
    CommonEditText mEditPasswordCode;
    @BindView(R.id.send_code)
    TextView mEditPasswordCodeButton;

    @BindView(R.id.stepview)
    HorizontalStepView stepView;

    @BindView(R.id.captcha_view)
    View captchaView;
    @BindView(R.id.layout_captcha)
    View captchaLayout;
    @BindView(R.id.captcha_edittext)
    EditText captchaEdittext;
    @BindView(R.id.captcha)
    ImageView captcha;
    private boolean showCaptcha;

    private String mPassword;
    private String mCode;
    private Model data;
    private ArrayList<String> list;
    private ArrayAdapter adapter;
    private TimeCount time;
    private AlertDialog mDialogDialog;

    private OnFragmentFinish mListener;

    Boolean mbDisplayFlg = false;

    private int step_type = AccountSecurityStepActivity.TYPE_STEP_PWD;

    public static EditPasswordOneFragment newInstance(int param) {
        EditPasswordOneFragment fragment = new EditPasswordOneFragment();
        Bundle args = new Bundle();
        args.putInt(AccountSecurityStepActivity.TYPE_STEP, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onOfflineViewClicked() {
        requestInit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_edit_password_select_layout:
                mDialogDialog.show();
                break;
            case R.id.submit_button:
                infoValidate();
                break;
            case R.id.fragment_edit_password_show_password:
                changePasswordDisplay();
                break;
            case R.id.captcha:
                getVerCode();
                break;
            default:
                ViewType viewType = Utils.getViewTypeOfTag(v);
                switch (viewType) {
                    case VIEW_TYPE_PHONE_CODE:
                        if (showCaptcha && Utils.isNull(captchaEdittext.getText().toString())) {
                            Toast.makeText(getActivity(), "图片验证码不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        requestPhoneCode();
                        break;
                    case VIEW_TYPE_EMAIL_CODE:
                        if (showCaptcha && Utils.isNull(captchaEdittext.getText().toString())) {
                            Toast.makeText(getActivity(), "图片验证码不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        requestEmailCode();
                        break;
                    default:
                        super.onClick(v);
                        break;
                }
        }
    }

    private void changePasswordDisplay() {
        if (!mbDisplayFlg) {
            mPasswordEditText.setTransformationMethod(HideReturnsTransformationMethod
                    .getInstance());

            mShowPasswordButton.setBackgroundResource(R.mipmap.btn_show_password_normal);

            mPasswordEditText.setSelection(mPasswordEditText.getText().toString().length());

        } else {
            mPasswordEditText.setTransformationMethod(PasswordTransformationMethod
                    .getInstance());
            mShowPasswordButton.setBackgroundResource(R.mipmap.btn_show_password_disabled);

            mPasswordEditText.setSelection(mPasswordEditText.getText().toString().length());
        }
        mbDisplayFlg = !mbDisplayFlg;
    }

    private void requestPhoneCode() {
        CommonRequest request = new CommonRequest(Api.API_SECURITY_SMS_CAPTCHA, HttpWhat
                .HTTP_POST_VERIFY_CODE.getValue(), RequestMethod.POST);
        request.add("mobile", data.mobile1);
        request.add("captcha", captchaEdittext.getText().toString());
        addRequest(request);
    }

    private void requestEmailCode() {
        CommonRequest request = new CommonRequest(Api.API_SECURITY_EMAIL_CAPTCHA, HttpWhat
                .HTTP_VERIFY_CODE.getValue(), RequestMethod.POST);
        request.add("email", data.email1);
        request.add("captcha", captchaEdittext.getText().toString());
        addRequest(request);
    }

    private void infoValidate() {

        CommonRequest request = new CommonRequest(Api.API_SECURITY_VALIDATE, HttpWhat.HTTP_INFO_VALIDATE.getValue(), RequestMethod.POST);

        if (type.equals("password")) {
            request.add("ValidateModel[password]", mPassword);
        } else if (type.equals("mobile")) {
            request.add("ValidateModel[mobile]", data.mobile1);
            request.add("ValidateModel[sms_captcha]", mCode);
        } else {
            request.add("ValidateModel[email]", data.email1);
            request.add("ValidateModel[email_captcha]", mCode);
        }

        if (showCaptcha) {
            request.add("ValidateModel[captcha]", captchaEdittext.getText().toString());
        }

        request.add("type", type);

        switch (step_type) {
            case AccountSecurityStepActivity.TYPE_STEP_EMAIL:
                request.add("service_type", "edit_email");
                break;
            case AccountSecurityStepActivity.TYPE_STEP_MOBILE:
                request.add("service_type", "edit_mobile");
                break;
            case AccountSecurityStepActivity.TYPE_STEP_PWD:
                request.add("service_type", "edit_password");
                break;
            case AccountSecurityStepActivity.TYPE_STEP_PAYPWD:
                request.add("service_type", "edit_surplus_password");
                break;
            case AccountSecurityStepActivity.TYPE_STEP_PAYPWD_CLOSE:
                request.add("service_type", "close_surplus_password");
                break;
            default:
                break;
        }

        addRequest(request);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("验证身份", 0);
        StepBean stepBean1 = new StepBean("设置登录密码", -1);

        switch (step_type) {
            case AccountSecurityStepActivity.TYPE_STEP_PWD:
                break;
            case AccountSecurityStepActivity.TYPE_STEP_EMAIL:
                stepBean1.setName("绑定验证邮箱地址");
                break;
            case AccountSecurityStepActivity.TYPE_STEP_MOBILE:
                stepBean1.setName("绑定验证手机号码");
                break;
            case AccountSecurityStepActivity.TYPE_STEP_PAYPWD:
                stepBean1.setName("设置余额支付密码");
                break;
            case AccountSecurityStepActivity.TYPE_STEP_PAYPWD_CLOSE:
                stepBean1.setName("");
                stepBean1 = null;
                break;

        }

        StepBean stepBean2 = new StepBean("完成", -1);
        stepsBeanList.add(stepBean0);
        if (stepBean1 != null) {
            stepsBeanList.add(stepBean1);
        }
        stepsBeanList.add(stepBean2);
        stepView.setStepViewTexts(stepsBeanList).setTextSize(16);

        mPasswordTypeLayout.setOnClickListener(this);
        mStepFinishButton.setOnClickListener(this);
        mShowPasswordButton.setOnClickListener(this);
        mStepFinishButton.setText(getResources().getString(R.string.next_step));
        captcha.setOnClickListener(this);
        addTextChangeListener();

        time = new TimeCount(60000, 1000);
        requestInit();
        return v;
    }

    private void addTextChangeListener() {
        mPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mPassword = mPasswordEditText.getText().toString();
                mStepFinishButton.setEnabled(isFinishButtonEnabled());
                if (mPassword.length() != 0) {
                    mEditPasswordTipLayout.setVisibility(View.GONE);
                } else {
                    mEditPasswordTipLayout.setVisibility(View.VISIBLE);
                    mEditPasswordTip.setText("登录密码不能为空");
                }
            }
        });

        mEditPasswordCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (type.equals("mobile")) {
                    mCode = mEditPasswordCode.getText().toString();
                    mStepFinishButton.setEnabled(isFinishButtonEnabled());

                    if (mCode.length() != 0) {
                        mEditPasswordTipLayout.setVisibility(View.GONE);
                    } else {
                        mEditPasswordTipLayout.setVisibility(View.VISIBLE);
                        mEditPasswordTip.setText("验证码不能为空");
                    }
                } else if (type.equals("email")) {
                    mCode = mEditPasswordCode.getText().toString();
                    mStepFinishButton.setEnabled(isFinishButtonEnabled());
                    if (mCode.length() != 0) {
                        mEditPasswordTipLayout.setVisibility(View.GONE);
                    } else {
                        mEditPasswordTipLayout.setVisibility(View.VISIBLE);
                        mEditPasswordTip.setText("验证码不能为空");
                    }
                }
            }
        });
        captchaEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mStepFinishButton.setEnabled(isFinishButtonEnabled());

                if (captchaEdittext.getText().toString().length() != 0) {
                    mEditPasswordTipLayout.setVisibility(View.GONE);
                } else {
                    mEditPasswordTipLayout.setVisibility(View.VISIBLE);
                    mEditPasswordTip.setText("图片验证码不能为空");
                }
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_edit_password_one;

        if (getArguments() != null) {
            step_type = getArguments().getInt(AccountSecurityStepActivity.TYPE_STEP, AccountSecurityStepActivity.TYPE_STEP_PWD);
        }

        list = new ArrayList<String>();
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_VALIDATE_TYPE_INIT:
                HttpResultManager.resolve(response, Model.class, new HttpResultManager
                        .HttpResultCallBack<Model>() {
                    @Override
                    public void onSuccess(Model back) {
                        data = back;
                        list.clear();
                        for (int i = 0; i < data.type_items.size(); i++) {
                            list.add(data.type_items.get(i).value);
                        }
                    }
                }, true);
                showCaptcha = data.show_captcha;
                initType(data.type);
                initDialog();
                break;
            case HTTP_INFO_VALIDATE:
                HttpResultManager.resolve(response, ResponseCommonModel.class, new
                        HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
                            @Override
                            public void onSuccess(ResponseCommonModel back) {
                                if (mListener != null) {
                                    mListener.onFinish(EditPasswordOneFragment.this);
                                }
                            }

                            @Override
                            public void onFailure(String message) {
                                super.onFailure(message);
                                getVerCode();
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
            case HTTP_VERIFY_CODE:
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
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private boolean isFinishButtonEnabled() {
        boolean isEnabled = true;

        if (showCaptcha && Utils.isNull(captchaEdittext.getText().toString())) {
            isEnabled = false;
        }

        if (type.equals("password")) {
            isEnabled = isEnabled && !Utils.isNull(mPassword);
        } else if (type.equals("mobile")) {
            isEnabled = isEnabled && !Utils.isNull(mCode);
        } else if (type.equals("email")) {
            isEnabled = isEnabled && !Utils.isNull(mCode);
        }
        return isEnabled;
    }

    private void requestInit() {
        CommonRequest request = new CommonRequest(Api.API_SECURITY_VALIDATE, HttpWhat
                .HTTP_VALIDATE_TYPE_INIT.getValue());
        addRequest(request);
    }

    private void initType(String t) {
        if (showCaptcha) {
            captchaView.setVisibility(View.VISIBLE);
            captchaLayout.setVisibility(View.VISIBLE);
            getVerCode();
        } else {
            captchaView.setVisibility(View.GONE);
            captchaLayout.setVisibility(View.GONE);
        }

        if (t.equals("password")) {
            type = "password";
            mType.setText("登录密码验证");
            mEditPasswordName.setText("用户名：");
            mEditPasswordNameContent.setText(data.user_name);
            mEditPasswordPasswordLayout.setVisibility(View.VISIBLE);
            mEditPasswordCodeLayout.setVisibility(View.GONE);
        } else if (t.equals("mobile")) {
            type = "mobile";
            mType.setText("手机验证");
            mEditPasswordName.setText("手机号：");
            mEditPasswordNameContent.setText(data.mobile);
            mEditPasswordCodeLayout.setVisibility(View.VISIBLE);
            mEditPasswordPasswordLayout.setVisibility(View.GONE);
            mEditPasswordCodeButton.setText(getResources().getString(R.string.sendVerifyCode));

            Utils.setViewTypeForTag(mEditPasswordCodeButton, ViewType.VIEW_TYPE_PHONE_CODE);
            mEditPasswordCodeButton.setOnClickListener(this);
        } else {
            type = "email";
            mType.setText("邮箱验证");
            mEditPasswordName.setText("邮箱：");
            mEditPasswordNameContent.setText(data.email);
            mEditPasswordCodeLayout.setVisibility(View.VISIBLE);
            mEditPasswordPasswordLayout.setVisibility(View.GONE);
            mEditPasswordCodeButton.setText("获取邮箱验证码");

            Utils.setViewTypeForTag(mEditPasswordCodeButton, ViewType.VIEW_TYPE_EMAIL_CODE);
            mEditPasswordCodeButton.setOnClickListener(this);
        }
    }

    private void initDialog() {
        adapter = new ArrayAdapter(getActivity(), R.layout.item_cancel_order, R.id
                .item_cancel_order_textView, list);
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View cancelOrderDialogView = layoutInflater.inflate(R.layout.order_cancel, null);
        final ListView listView = (ListView) cancelOrderDialogView.findViewById(R.id
                .order_cancel_reason_list_view);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setItemChecked(0, true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mEditPasswordTipLayout.setVisibility(View.GONE);
                if (list.get(position).equals("登录密码验证")) {
                    time.cancel();
                    initType("password");
                } else if (list.get(position).equals("邮箱验证")) {
                    time.cancel();
                    initType("email");
                } else if (list.get(position).equals("手机验证")) {
                    time.cancel();
                    initType("mobile");
                }
                mDialogDialog.dismiss();
            }
        });
        mDialogDialog = new AlertDialog.Builder(getActivity()).setView(cancelOrderDialogView)
                .create();
        TextView mDialogCancel = (TextView) cancelOrderDialogView.findViewById(R.id
                .dialog_call_cancle_textView);
        TextView mTitle = (TextView) cancelOrderDialogView.findViewById(R.id
                .dialog_cancel_order_textView);
        mTitle.setText("请选择验证身份方式");
        mDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogDialog.dismiss();
            }
        });
        TextView mDialogConfirm = (TextView) cancelOrderDialogView.findViewById(R.id
                .dialog_call_confirm_textView);
        mDialogConfirm.setVisibility(View.GONE);
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
