package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
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

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Activity.BindAccountActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
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

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by liuzhifeng on 2017/3/9.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class AddAccountFragment extends YSCBaseFragment {

    private static final String TYPE_ONE = "typeOne";
    private static final String TYPE_TWO = "typeTwo";

    private static final String TYPE_PASSWORD = "password";
    private static final String TYPE_EMAIL = "email";
    private static final String TYPE_MOBILE = "mobile";

    private static final String TYPE_BANK = "bank";
    private static final String TYPE_ALIPAY = "alipay";

    @BindView(R.id.item_add_account_image)
    ImageView mImageView;

    @BindView(R.id.item_add_account_one_relative)
    LinearLayout mRelativeLayoutOne;
    @BindView(R.id.item_add_account_one_text_view)
    TextView mTextViewOneOne;
    @BindView(R.id.item_add_account_one_ediText)
    TextView mTextViewOneTwo;

    @BindView(R.id.item_add_account_two_text_view)
    TextView mTextViewTwoOne;
    @BindView(R.id.item_add_account_two_ediText)
    CommonEditText mEditTextTwoTwo;

    @BindView(R.id.item_add_account_three_text_view)
    TextView mTextViewThreeOne;
    @BindView(R.id.send_code)
    TextView mTextViewThreeThree;
    @BindView(R.id.item_add_account_three_ediText)
    CommonEditText mEditTextThreeTwo;

    @BindView(R.id.img_show_password)
    ImageView imgShowPassword;

    @BindView(R.id.item_add_account_four_text_view)
    TextView mTextViewFourOne;
    @BindView(R.id.item_add_account_four_ediText)
    CommonEditText mEditTextFourTwo;
    @BindView(R.id.item_add_account_four_relative)
    LinearLayout mRelativeLayoutFour;
    @BindView(R.id.item_add_account_all_relative)
    LinearLayout mRelativeLayoutAll;
    @BindView(R.id.fragment_add_account_tipLayout)
    LinearLayout mTipLayout;

    @BindView(R.id.submit_button)
    Button mTextViewSubmit;

    @BindView(R.id.captcha_view)
    View captchaView;
    @BindView(R.id.layout_captcha)
    View captchaLayout;
    @BindView(R.id.captcha_edittext)
    EditText captchaEdittext;
    @BindView(R.id.captcha)
    ImageView captcha;

    @BindView(R.id.layout_waring)
    View warningLayout;
    @BindView(R.id.warning_tip)
    TextView warningTip;

    private boolean showCaptcha;
    private boolean mbDisplayFlg;

    private String type = TYPE_ONE;
    private ArrayList<String> list;
    private ArrayAdapter adapter;
    private boolean isAddData = true;
    private AlertDialog mDialogDialog;
    private TimeCount time;
    private String checkTypeOne = "password";
    private String checkTypeTwo = "银行转账";
    private Model data;
    private String activityType;

    @Override
    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }
        ViewType viewType = Utils.getViewTypeOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_ADD_ACCOUNT_SUBMIT_ONE:
                requestTypeOne();
                break;
            case VIEW_TYPE_ADD_ACCOUNT_SUBMIT_TWO:
                requestTypeTwo();
                break;
            case VIEW_TYPE_ADD_ACCOUNT_SUBMIT_THREE:
                //activityType：是不是由绑定提现账户页面打开
                //如果是，绑定完直接关掉，然后刷新
                //否则需要打开绑定提现账户页面
                if ("1".equals(activityType)) {
                    EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_REFRESH_BIND_LIST
                            .getValue()));
                    finish();
                } else {
                    openBindAccountActivity();
                    finish();
                }

                break;
            case VIEW_TYPE_PHONE_CODE:
                if(showCaptcha&&Utils.isNull(captchaEdittext.getText().toString())){
                    Toast.makeText(getActivity(),"图片验证码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                requestSMS();
                break;
            case VIEW_TYPE_EMAIL_CODE:
                if(showCaptcha&&Utils.isNull(captchaEdittext.getText().toString())){
                    Toast.makeText(getActivity(),"图片验证码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                requestEmailCaptcha();
                break;
            case VIEW_TYPE_DIALOG_CONFIRM:
                if (type.equals(TYPE_TWO)) {
                    if (isAddData) {
                        list.clear();
                        list.add("银行转账");
                        list.add("支付宝");
                        isAddData = false;
                    }
                }
                dialog();
                break;
            case VIEW_TYPE_REFRESH:
                getVerCode();
                break;
            case VIEW_TYPE_SHOW_PASSWORD:
                if (!mbDisplayFlg) {
                    mEditTextThreeTwo.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());
                    imgShowPassword.setBackgroundResource(R.mipmap.btn_show_password_normal);
                    mEditTextThreeTwo.setSelection(mEditTextThreeTwo.getText().toString().length());
                } else {
                    mEditTextThreeTwo.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());
                    imgShowPassword.setBackgroundResource(R.mipmap.btn_show_password_disabled);

                    mEditTextThreeTwo.setSelection(mEditTextThreeTwo.getText().toString().length());
                }
                mbDisplayFlg = !mbDisplayFlg;
                break;
            default:
                super.onClick(view);
                break;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_add_account;

        Intent intent = getActivity().getIntent();
        activityType = intent.getStringExtra(Key.KEY_TYPE.getValue());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        list = new ArrayList<String>();
        time = new TimeCount(60000, 1000);
        Utils.setViewTypeForTag(mTextViewSubmit, ViewType.VIEW_TYPE_ADD_ACCOUNT_SUBMIT_ONE);
        mTextViewSubmit.setOnClickListener(this);
        Utils.setViewTypeForTag(mRelativeLayoutOne, ViewType.VIEW_TYPE_DIALOG_CONFIRM);
        mRelativeLayoutOne.setOnClickListener(this);

        Utils.setViewTypeForTag(captcha, ViewType.VIEW_TYPE_REFRESH);
        captcha.setOnClickListener(this);
        mTextViewSubmit.setText(getResources().getString(R.string.next_step));
        mTextViewSubmit.setEnabled(true);
        requestInit();

        return v;
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
                if (list.get(position).equals("登录密码验证")) {
                    time.cancel();
                    mTextViewThreeThree.setEnabled(true);
                    initTypeOne(TYPE_PASSWORD);
                } else if (list.get(position).equals("邮箱验证")) {
                    time.cancel();
                    mTextViewThreeThree.setEnabled(true);
                    initTypeOne(TYPE_EMAIL);
                } else if (list.get(position).equals("手机验证")) {
                    time.cancel();
                    mTextViewThreeThree.setEnabled(true);
                    initTypeOne(TYPE_MOBILE);
                } else if (list.get(position).equals("银行转账")) {
                    initTypeTwo(TYPE_BANK);
                } else if (list.get(position).equals("支付宝")) {
                    initTypeTwo(TYPE_ALIPAY);
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
        mTitle.setText("请选择");
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

    private void initTypeOne(String type) {
        if (showCaptcha) {
            captchaView.setVisibility(View.VISIBLE);
            captchaLayout.setVisibility(View.VISIBLE);
            getVerCode();
            captchaEdittext.setFocusable(true);
            captchaEdittext.setFocusableInTouchMode(true);
            captchaEdittext.requestFocus();
        } else {
            captchaView.setVisibility(View.GONE);
            captchaLayout.setVisibility(View.GONE);

            mEditTextThreeTwo.setFocusable(true);
            mEditTextThreeTwo.setFocusableInTouchMode(true);
            mEditTextThreeTwo.requestFocus();
        }

        if (type.equals(TYPE_PASSWORD)) {
            checkTypeOne = "password";
            mTextViewOneTwo.setText("登录密码验证");
            mTextViewTwoOne.setText("用户名：");
            mEditTextTwoTwo.setText(data.user_name);
            mTextViewThreeOne.setText("登录密码：");
            mTextViewThreeThree.setVisibility(View.GONE);

            mEditTextThreeTwo.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mEditTextThreeTwo.setTransformationMethod(PasswordTransformationMethod.getInstance());

            imgShowPassword.setVisibility(View.VISIBLE);
            Utils.setViewTypeForTag(imgShowPassword, ViewType.VIEW_TYPE_SHOW_PASSWORD);
            imgShowPassword.setOnClickListener(this);

        } else if (type.equals(TYPE_EMAIL)) {
            checkTypeOne = "email";
            mTextViewOneTwo.setText("邮箱验证");
            mTextViewTwoOne.setText("邮箱：");
            mTextViewThreeOne.setText("验证码：");
            mEditTextTwoTwo.setText(data.email);
            mTextViewThreeThree.setText(getResources().getString(R.string.sendVerifyCode));
            mTextViewThreeThree.setVisibility(View.VISIBLE);

            Utils.setViewTypeForTag(mTextViewThreeThree, ViewType.VIEW_TYPE_EMAIL_CODE);
            mTextViewThreeThree.setOnClickListener(this);

            imgShowPassword.setVisibility(View.GONE);
            mEditTextThreeTwo.setInputType(InputType.TYPE_CLASS_TEXT);
            mEditTextThreeTwo.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            checkTypeOne = "mobile";
            mTextViewOneTwo.setText("手机号验证");
            mTextViewTwoOne.setText("手机号：");
            mTextViewThreeOne.setText("验证码：");
            mEditTextTwoTwo.setText(data.mobile);
            mTextViewThreeThree.setText(getResources().getString(R.string.sendVerifyCode));
            mTextViewThreeThree.setVisibility(View.VISIBLE);

            Utils.setViewTypeForTag(mTextViewThreeThree, ViewType.VIEW_TYPE_PHONE_CODE);
            mTextViewThreeThree.setOnClickListener(this);

            imgShowPassword.setVisibility(View.GONE);
            mEditTextThreeTwo.setInputType(InputType.TYPE_CLASS_TEXT);
            mEditTextThreeTwo.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        mTextViewOneOne.setText("验证身份：");
        mEditTextTwoTwo.setEnabled(false);

        mRelativeLayoutFour.setVisibility(View.GONE);
    }

    private void initTypeTwo(String typetwo) {
        captchaView.setVisibility(View.GONE);
        captchaLayout.setVisibility(View.GONE);

        mTextViewOneOne.setText("提现方：");
        mTextViewTwoOne.setText("收款人：");
        mEditTextTwoTwo.setText("");
        mEditTextTwoTwo.setEnabled(true);
        mEditTextTwoTwo.setFocusable(true);
        mEditTextTwoTwo.setFocusableInTouchMode(true);
        mEditTextTwoTwo.requestFocus();
        mEditTextThreeTwo.setText("");
        mTextViewThreeThree.setVisibility(View.GONE);
        imgShowPassword.setVisibility(View.GONE);
        mEditTextThreeTwo.setInputType(InputType.TYPE_CLASS_TEXT);
        mEditTextThreeTwo.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        type = TYPE_TWO;


        if (typetwo.equals(TYPE_BANK)) {
            checkTypeTwo = "银行转账";
            mTextViewOneTwo.setText("银行转账");
            mRelativeLayoutFour.setVisibility(View.VISIBLE);
            mTextViewThreeOne.setText("银行卡：");
            mTextViewFourOne.setText("开户行：");

            //银行卡EditText监听
            mEditTextThreeTwo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    boolean flag = false;
                    if(Utils.isNull(s.toString())){
                        warningTip.setText("银行卡号不能为空");
                        flag = true;
                    }
                    if(s.toString().length()>30){
                        warningTip.setText("银行卡号号只能包含至多30个字符");
                        flag = true;
                    }

                    if(flag){
                        warningLayout.setVisibility(View.VISIBLE);
                    }else {
                        warningLayout.setVisibility(View.GONE);
                    }
                }
            });

            //开户行EditText监听
            mEditTextFourTwo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    boolean flag = false;
                    if(Utils.isNull(s.toString())){
                        warningTip.setText("开户行不能为空");
                        flag = true;
                    }
                    if(s.toString().length()>60){
                        warningTip.setText("开户行只能包含至多60个字符");
                        flag = true;
                    }
                    if(flag){
                        warningLayout.setVisibility(View.VISIBLE);
                    }else {
                        warningLayout.setVisibility(View.GONE);
                    }

                }
            });

        } else {
            checkTypeTwo = "支付宝";
            mTextViewOneTwo.setText("支付宝");
            mRelativeLayoutFour.setVisibility(View.GONE);
            mTextViewThreeOne.setText("支付宝：");
            mEditTextFourTwo.setText("");

            warningTip.setText("");

            //支付宝EditText监听
            mEditTextThreeTwo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    boolean flag = false;
                    if(Utils.isNull(s.toString())){
                        warningTip.setText("支付宝帐号不能为空");
                        flag = true;
                    }
                    if(s.toString().length()>30){
                        warningTip.setText("支付宝帐号只能包含至多30个字符");
                        flag = true;
                    }

                    if(flag){
                        warningLayout.setVisibility(View.VISIBLE);
                    }else {
                        warningLayout.setVisibility(View.GONE);
                    }
                }
            });
        }

        //收款人EditText监听
        mEditTextTwoTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean flag = false;
                if(Utils.isNull(s.toString())){
                    warningTip.setText("收款人姓名不能为空");
                    flag = true;
                }
                if(s.toString().length()>30){
                    warningTip.setText("收款人姓名只能包含至多30个字符");
                    flag = true;
                }

                if(flag){
                    warningLayout.setVisibility(View.VISIBLE);
                }else {
                    warningLayout.setVisibility(View.GONE);
                }
            }
        });

    }

    private void dialog() {
        mDialogDialog.show();
    }

    private void requestTypeOne() {
        CommonRequest request = new CommonRequest(Api.API_DEPOSIT_ACCOUNT_VALIDATE, HttpWhat
                .HTTP_INFO_VALIDATE.getValue(), RequestMethod.POST);
        request.add("DepositValidateModel[type]", checkTypeOne);
        String captcha = mEditTextThreeTwo.getText().toString();

        if (checkTypeOne.equals("password")) {
            if(showCaptcha&&Utils.isNull(captchaEdittext.getText().toString())){
                Toast.makeText(getActivity(),"图片验证码不能为空",Toast.LENGTH_SHORT).show();
                return;
            }
            if(Utils.isNull(captcha)){
                Toast.makeText(getActivity(),"登录密码不能为空",Toast.LENGTH_SHORT).show();
                return;
            }
        }else{
            if(showCaptcha&&Utils.isNull(captchaEdittext.getText().toString())){
                Toast.makeText(getActivity(),"图片验证码不能为空",Toast.LENGTH_SHORT).show();
                return;
            }
            if(Utils.isNull(captcha)){
                Toast.makeText(getActivity(),"验证码不能为空",Toast.LENGTH_SHORT).show();
                return;
            }
        }


        if (checkTypeOne.equals("password")) {
            request.add("DepositValidateModel[password]", captcha);
        } else if (checkTypeOne.equals("mobile")) {
            request.add("DepositValidateModel[mobile]", data.mobile1);
            request.add("DepositValidateModel[sms_captcha]", captcha);
        } else {
            request.add("DepositValidateModel[email]", data.email1);
            request.add("DepositValidateModel[email_captcha]", captcha);
        }

        if(showCaptcha){
            request.add("DepositValidateModel[captcha]", captchaEdittext.getText().toString());
        }
        addRequest(request);
    }

    private void requestTypeTwo() {

        if(checkTypeTwo.equals("支付宝")){
            boolean flag = false;
            warningTip.setText("");
            if(Utils.isNull(mEditTextTwoTwo.getText().toString())){
                warningTip.append("收款人姓名不能为空"+"\n");
                flag = true;
            }
            if(Utils.isNull(mEditTextThreeTwo.getText().toString())){
                warningTip.append("支付宝账号不能为空");
                flag = true;
            }

            if(flag){
                warningLayout.setVisibility(View.VISIBLE);
                return;
            }else {
                warningLayout.setVisibility(View.GONE);
            }
        }

        if(checkTypeTwo.equals("银行转账")){
            boolean flag = false;
            warningTip.setText("");
            if(Utils.isNull(mEditTextTwoTwo.getText().toString())){
                warningTip.append("收款人姓名不能为空"+"\n");
                flag = true;
            }
            if(Utils.isNull(mEditTextThreeTwo.getText().toString())){
                warningTip.append("银行卡号不能为空"+"\n");
                flag = true;
            }
            if(Utils.isNull(mEditTextFourTwo.getText().toString())){
                warningTip.append("开户行不能为空");
                flag = true;
            }

            if(flag){
                warningLayout.setVisibility(View.VISIBLE);
                return;
            }else {
                warningLayout.setVisibility(View.GONE);
            }
        }



        CommonRequest request = new CommonRequest(Api.API_CANCEL_GOODS_LIST_TWO, HttpWhat
                .HTTP_ADD_ACCOUNT_TYPE_TWO.getValue(), RequestMethod.POST);
        request.add("DepositAccountModel[account_type]", checkTypeTwo);
        if (checkTypeTwo.equals("银行转账")) {
            request.add("DepositAccountModel[account_name]", mEditTextTwoTwo.getText().toString());
            request.add("DepositAccountModel[account]", mEditTextThreeTwo.getText().toString());
            request.add("DepositAccountModel[bank_account]", mEditTextFourTwo.getText().toString());
        } else {
            request.add("DepositAccountModel[account_name]", mEditTextTwoTwo.getText().toString());
            request.add("DepositAccountModel[account]", mEditTextThreeTwo.getText().toString());
        }

        addRequest(request);
    }

    private void requestInit() {
        CommonRequest request = new CommonRequest(Api.API_DEPOSIT_ACCOUNT_VALIDATE, HttpWhat
                .HTTP_VALIDATE_TYPE_INIT.getValue());
        addRequest(request);
    }

    private void requestSMS() {
        CommonRequest request = new CommonRequest(Api.API_CANCEL_GOODS_LIST_SMS, HttpWhat
                .HTTP_CHECK_CAPTCHA.getValue(), RequestMethod.POST);
        request.add("mobile", data.mobile1);
        if(showCaptcha){
            request.add("captcha", captchaEdittext.getText().toString());
        }
        addRequest(request);
    }

    private void requestEmailCaptcha() {
        CommonRequest request = new CommonRequest(Api.API_DEPOSIT_ACCOUNT_EMAIL_CAPTCHA, HttpWhat
                .HTTP_CHECK_CAPTCHA.getValue(), RequestMethod.POST);
        request.add("email", data.email1);
        if(showCaptcha){
            request.add("captcha", captchaEdittext.getText().toString());
        }
        addRequest(request);
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_INFO_VALIDATE:
                typeOneCallback(response);
                break;
            case HTTP_ADD_ACCOUNT_TYPE_TWO:
                HttpResultManager.resolve(response, ResponseCommonModel.class, new
                        HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
                            @Override
                            public void onSuccess(ResponseCommonModel back) {
                                mRelativeLayoutAll.setVisibility(View.GONE);
                                mTipLayout.setVisibility(View.VISIBLE);
                                mTextViewSubmit.setText("返回我的提现列表");
                                Utils.setViewTypeForTag(mTextViewSubmit, ViewType
                                        .VIEW_TYPE_ADD_ACCOUNT_SUBMIT_THREE);
                                mImageView.setImageResource(R.mipmap.bg_verification_step3);
                            }
                        }, true);
                break;
            case HTTP_CHECK_CAPTCHA:
                smsCallback(response);
                break;
            case HTTP_VALIDATE_TYPE_INIT:
                HttpResultManager.resolve(response, Model.class, new HttpResultManager
                        .HttpResultCallBack<Model>() {
                    @Override
                    public void onSuccess(Model back) {
                        data = back;
                        for (int i = 0; i < data.type_items.size(); i++) {
                            list.add(data.type_items.get(i).value);
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        super.onFailure(message);
                        getVerCode();
                    }

                }, true);
                showCaptcha = data.show_captcha;
                initTypeOne(data.type);

                initDialog();
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private void typeOneCallback(String response) {
        HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager
                .HttpResultCallBack<ResponseCommonModel>() {
            @Override
            public void onSuccess(ResponseCommonModel back) {
                initTypeTwo(TYPE_BANK);
                Utils.setViewTypeForTag(mTextViewSubmit, ViewType.VIEW_TYPE_ADD_ACCOUNT_SUBMIT_TWO);
                mImageView.setImageResource(R.mipmap.bg_verification_step2);
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                getVerCode();
            }
        }, true);
    }

    private void smsCallback(String response) {
        HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager
                .HttpResultCallBack<ResponseCommonModel>() {
            @Override
            public void onSuccess(ResponseCommonModel back) {
                Utils.toastUtil.showToast(getActivity(), back.message);
                time.start();
            }
        }, true);

    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            mTextViewThreeThree.setText(getResources().getString(R.string.sendVerifyCode));
            mTextViewThreeThree.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTextViewThreeThree.setEnabled(false);
            mTextViewThreeThree.setText(millisUntilFinished / 1000 + "秒");
        }
    }

    private void openBindAccountActivity() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), BindAccountActivity.class);
        startActivity(intent);
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
