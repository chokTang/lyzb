package com.szy.yishopcustomer.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.yishopcustomer.Activity.GiftCardPickUpListActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.BaseEntity;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GiftCardPickUpFragment extends YSCBaseFragment {

    @BindView(R.id.fragment_edittext_gift_card_number)
    EditText fragment_edittext_gift_card_number;

    @BindView(R.id.fragment_edit_password_password_edittext)
    EditText fragment_edit_password_password_edittext;

    @BindView(R.id.submit_button)
    Button fragment_password_submit_button;

    @BindView(R.id.fragment_edit_password_show_password)
    ImageView mShowPasswordButton;

    private String card_num = "";
    private String card_pwd = "";

    boolean mbDisplayFlg = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_gift_card_pick_up;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        Utils.showSoftInputFromWindowTwo(getActivity(), fragment_edittext_gift_card_number);
        fragment_edittext_gift_card_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                card_num = s.toString().trim();
                isSubmitEnable();
            }
        });

        fragment_edit_password_password_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                card_pwd = s.toString().trim();
                isSubmitEnable();
            }
        });

        mShowPasswordButton.setOnClickListener(this);
        fragment_password_submit_button.setOnClickListener(this);
        fragment_password_submit_button.setText("确认提交");
        refresh("goods");
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_button:
                submit();
                break;
            case R.id.fragment_edit_password_show_password:
                changePasswordDisplay();
                break;
            default:
                super.onClick(view);
                break;
        }
    }

    private void changePasswordDisplay() {
        if (!mbDisplayFlg) {
            fragment_edit_password_password_edittext.setTransformationMethod(HideReturnsTransformationMethod
                    .getInstance());

            mShowPasswordButton.setBackgroundResource(R.mipmap.btn_show_password_normal);

            fragment_edit_password_password_edittext.setSelection(fragment_edit_password_password_edittext.getText().toString().length());

        } else {
            fragment_edit_password_password_edittext.setTransformationMethod(PasswordTransformationMethod
                    .getInstance());
            mShowPasswordButton.setBackgroundResource(R.mipmap.btn_show_password_disabled);

            fragment_edit_password_password_edittext.setSelection(fragment_edit_password_password_edittext.getText().toString().length());
        }
        mbDisplayFlg = !mbDisplayFlg;
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_SIMPLE:
                HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
                    @Override
                    public void onSuccess(ResponseCommonModel back) {
                        openGiftCardPickUpListActivity();
                    }
                }, true);
                break;
            case HTTP_INDEX:
                HttpResultManager.resolve(response, BaseEntity.class, new HttpResultManager.HttpResultCallBack<BaseEntity>() {
                    @Override
                    public void onSuccess(BaseEntity back) {

                        if (!TextUtils.isEmpty(back.redirect)) {
                            refresh(back.redirect);
                        }

                    }
                }, true);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    void openGiftCardPickUpListActivity() {
        startActivity(new Intent(getContext(), GiftCardPickUpListActivity.class));
        getActivity().finish();
    }

    public void refresh(String redirect) {
        CommonRequest request = new CommonRequest(Api.API_USER_GIFT_CARD + redirect, HttpWhat
                .HTTP_INDEX.getValue(), RequestMethod.GET);
        addRequest(request);
    }

    private void submit() {
        CommonRequest request = new CommonRequest(Api.API_USER_GIFT_CARD_INDEX, HttpWhat
                .HTTP_SIMPLE.getValue(), RequestMethod.POST);
        if (!TextUtils.isEmpty(card_num)) {
            request.add("card_no", card_num);
        }

        if (!TextUtils.isEmpty(card_pwd)) {
            request.add("card_pass", card_pwd);
        }

        addRequest(request);
    }

    private void isSubmitEnable() {
        if (TextUtils.isEmpty(card_pwd) || TextUtils.isEmpty(card_num)) {
            fragment_password_submit_button.setEnabled(false);
        } else {
            fragment_password_submit_button.setEnabled(true);
        }
    }

}
