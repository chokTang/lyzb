package com.lyzb.jbx.fragment.campaign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.like.longshaolib.base.BaseToolbarFragment;
import com.like.longshaolib.widget.ClearEditText;
import com.like.utilslib.other.RegexUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.mvp.presenter.campaign.SignCampaignPresenter;
import com.lyzb.jbx.mvp.view.campaign.ISignCampaignView;

/**
 * 活动报名页面
 */
public class SignCampaignFragment extends BaseToolbarFragment<SignCampaignPresenter> implements ISignCampaignView {

    private static final String PARAMS_ID = "PARAMS_ID";
    private String mCampaignId = "";

    private ClearEditText cet_name;
    private ClearEditText cet_phone;
    private ClearEditText cet_number;
    private ClearEditText cet_inviter;
    private TextView btn_save;

    public static SignCampaignFragment newIntance(String campaignId) {
        SignCampaignFragment fragment = new SignCampaignFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_ID, campaignId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args =getArguments();
        if (args!=null){
            mCampaignId =args.getString(PARAMS_ID);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();
        setToolbarTitle("我要报名");

        cet_name=findViewById(R.id.cet_name);
        cet_phone=findViewById(R.id.cet_phone);
        cet_number=findViewById(R.id.cet_number);
        cet_inviter=findViewById(R.id.cet_inviter);
        btn_save=findViewById(R.id.btn_save);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameValue =cet_name.getText().toString().trim();
                if (TextUtils.isEmpty(nameValue)){
                    showToast("请填写联系人姓名");
                    return;
                }
                String phoneValue =cet_phone.getText().toString().trim();
                if (!RegexUtil.checkMobile(phoneValue)){
                    showToast("请输入正确的电话号码");
                    return;
                }
                String numberValue =cet_number.getText().toString().trim();
                if (TextUtils.isEmpty(numberValue)){
                    showToast("请填写报名的人数");
                    return;
                }
                String nviterValue =cet_inviter.getText().toString().trim();
                mPresenter.onSignCampagin(mCampaignId,nameValue,phoneValue,numberValue,nviterValue);
            }
        });
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_sign_campaign;
    }

    @Override
    public void onSignResultSuccess() {
        setFragmentResult(RESULT_OK,new Bundle());
        pop();
    }
}
