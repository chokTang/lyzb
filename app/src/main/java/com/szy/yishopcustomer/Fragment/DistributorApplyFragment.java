package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Activity.DistribActivity;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Distrib.DistribApplyModel;
import com.szy.yishopcustomer.ResponseModel.Distributor.CheckModel;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by liwei on 2017/7/17.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistributorApplyFragment extends YSCBaseFragment implements TextWatcher {

    private static final int HTTP_WHAT_DISTRIBUTOR_APPLY = 1;
    private static final int HTTP_WHAT_DISTRIBUTOR_CHECK = 2;

    @BindView(R.id.fragment_distributor_apply_shop_name)
    CommonEditText mShopNameEditText;
    @BindView(R.id.fragment_distributor_apply_qq)
    CommonEditText mQqEditText;
    @BindView(R.id.fragment_distributor_apply_weixin)
    CommonEditText mWeixinEditText;
    @BindView(R.id.fragment_distributor_apply_tip_layout)
    LinearLayout mTipLayout;
    @BindView(R.id.fragment_edit_password_tip)
    TextView mTipOne;

    @BindView(R.id.fragment_distributor_apply_tip_layout2)
    LinearLayout mTipLayout2;
    @BindView(R.id.fragment_distrib_apply_tip2)
    TextView mTipTwo;
    @BindView(R.id.fragment_distributor_apply_tip_layout3)
    LinearLayout mTipLayout3;
    @BindView(R.id.fragment_distrib_apply_tip3)
    TextView mTipThree;

    @BindView(R.id.submit_button)
    Button mApplyButton;

    @BindView(R.id.fragment_distributor_applay_layout)
    LinearLayout mApplylLayout;
    @BindView(R.id.fragment_distributor_result)
    LinearLayout mResutlLayout;
    @BindView(R.id.fragment_distributor_result_tip)
    TextView mResutlTip;
    @BindView(R.id.fragment_distributor_result_buttonOne)
    Button mResutlButtonOne;
    @BindView(R.id.fragment_distributor_result_buttonTwo)
    Button mResutlButtonTwo;

    private String mShopName;
    private String mQq;
    private String mWeixin;
    private String distribText;
    private String getDistributorText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_distributor_apply;

        Intent intent = getActivity().getIntent();
        distribText = intent.getStringExtra(Key.KEY_DISTRIB_TEXT.getValue());
        getDistributorText = intent.getStringExtra(Key.KEY_GETDISTRIBUTOR_TEXT.getValue());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mShopNameEditText.addTextChangedListener(this);
        mQqEditText.addTextChangedListener(this);
        mWeixinEditText.addTextChangedListener(this);

        mApplyButton.setOnClickListener(this);
        mResutlButtonOne.setOnClickListener(this);
        mResutlButtonTwo.setOnClickListener(this);
        mApplyButton.setText(getResources().getString(R.string.submitTwo));
        refresh();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_button:
                submit();
                break;
            default:
                ViewType viewType = Utils.getViewTypeOfTag(v);
                int position = Utils.getPositionOfTag(v);
                switch (viewType) {
                    case VIEW_TYPE_USER_PROFILE:
                        startActivity(new Intent().setClass(getActivity(), RootActivity.class));
                        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_USER_TAB.getValue()));
                        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_REFRESH_USER_INFO.getValue()));
                        getActivity().finish();
                        break;
                    case VIEW_TYPE_INDEX:
                        goIndex();
                        break;
                    case VIEW_TYPE_DISTRIB:
                        openDistrib();
                        getActivity().finish();
                        break;
                    default:
                        super.onClick(v);
                }
        }
    }

    private void goIndex() {
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_INDEX.getValue()));
        startActivity(new Intent().setClass(getActivity(), RootActivity.class));
        this.finish();
    }

    private void openDistrib() {
        Intent intent = new Intent();
        intent.setClass(getActivity(),DistribActivity.class);
        intent.putExtra(Key.KEY_GETDISTRIBUTOR_TEXT.getValue(),getDistributorText);
        intent.putExtra(Key.KEY_DISTRIB_TEXT.getValue(),distribText);
        startActivity(intent);
    }

    public void refresh(){
        CommonRequest request = new CommonRequest(Api.API_DISTRIBUTOR_APPLY,HTTP_WHAT_DISTRIBUTOR_CHECK);
        addRequest(request);
    }

    public void submit() {
        CommonRequest request = new CommonRequest(Api.API_DISTRIBUTOR_APPLY,
                HTTP_WHAT_DISTRIBUTOR_APPLY, RequestMethod.POST);
        request.add("DistributorModel[shop_name]",mShopNameEditText.getText().toString());
        request.add("DistributorModel[qq]",mQqEditText.getText().toString());
        request.add("DistributorModel[wechat]",mWeixinEditText.getText().toString());
        addRequest(request);
    }

    //接口成功回调
    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_DISTRIBUTOR_APPLY:
                submitSucceed(response);
                break;
            case HTTP_WHAT_DISTRIBUTOR_CHECK:
                refreshSucceed(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    //设置数据
    private void submitSucceed(String response) {
        DistribApplyModel back = JSON.parseObject(response, DistribApplyModel.class);
        if(back.code == 0){
            if(back.data.is_audit.equals("1")){
                initView();
            }else{
                initView2();
            }
        }else {
            Toast.makeText(getActivity(),back.message,Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshSucceed(String response) {
        CheckModel back = JSON.parseObject(response, CheckModel.class);
        if(back.code.equals("0")){//可申请
            mApplylLayout.setVisibility(View.VISIBLE);
            mResutlLayout.setVisibility(View.GONE);
        }else if(back.code.equals("-1")){//不符合申请条件
            Toast.makeText(getActivity(),"不符合申请条件",Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }else if(back.code.equals("1")){//已经申请
            if(back.data.status.equals("0")){//审核中

                initView();

            }else if(back.data.status.equals("1")){//审核通过
                initView2();
            }
        }
    }

    private void initView() {
        mApplylLayout.setVisibility(View.GONE);
        mResutlLayout.setVisibility(View.VISIBLE);

        mResutlTip.setText("您的信息已提交成功，等待管理员审核");
        mResutlButtonTwo.setText("查看个人中心");

        Utils.setViewTypeForTag(mResutlButtonOne, ViewType.VIEW_TYPE_INDEX);
        Utils.setViewTypeForTag(mResutlButtonTwo, ViewType.VIEW_TYPE_USER_PROFILE);
    }
    private void initView2() {
        mApplylLayout.setVisibility(View.GONE);
        mResutlLayout.setVisibility(View.VISIBLE);

        mResutlTip.setText("恭喜您，已经成为"+getDistributorText);
        mResutlButtonTwo.setText("查看我的"+distribText);
        Utils.setViewTypeForTag(mResutlButtonOne, ViewType.VIEW_TYPE_INDEX);
        Utils.setViewTypeForTag(mResutlButtonTwo, ViewType.VIEW_TYPE_DISTRIB);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mShopName = mShopNameEditText.getText().toString();
        mApplyButton.setEnabled(isFinishButtonEnabled());

        if(mShopNameEditText.hasFocus()){
            if(mShopName.length() == 0){
                mTipLayout.setVisibility(View.VISIBLE);
                mTipOne.setText("店铺名称不能为空");
            }else {
                if(mShopName.length()>20){
                    mTipLayout.setVisibility(View.VISIBLE);
                    mTipOne.setText("店铺名称只能包含至多20个字符。");
                }else{
                    mTipLayout.setVisibility(View.GONE);
                }
            }
        }

        if(mQqEditText.hasFocus()){
            if(mQqEditText.getText().toString().length()>20){
                mTipLayout2.setVisibility(View.VISIBLE);
            }else{
                mTipLayout2.setVisibility(View.GONE);
            }
        }
        if(mWeixinEditText.hasFocus()){
            if(mWeixinEditText.getText().toString().length()>20){
                mTipLayout3.setVisibility(View.VISIBLE);
            }else{
                mTipLayout3.setVisibility(View.GONE);
            }
        }



    }

    private boolean isFinishButtonEnabled() {
        boolean isEnabled = false;
        String shopName = mShopNameEditText.getText().toString();
        String qq = mQqEditText.getText().toString();
        String weixin = mWeixinEditText.getText().toString();
        isEnabled = !Utils.isNull(shopName)&&shopName.length()<21&&qq.length()<21&&weixin.length()<21;
        return isEnabled;
    }
}
