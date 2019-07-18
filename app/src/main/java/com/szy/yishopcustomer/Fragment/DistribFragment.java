package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Activity.SetUpActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Distrib.DistribModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.CircleImageView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by liwei on 2017/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribFragment extends YSCBaseFragment {

    private static final int HTTP_WHAT_DISTRIB = 1;


    @BindView(R.id.fragment_distrib_setting)
    ImageView mUserSetting;
    @BindView(R.id.fragment_distrib_user_photo)
    CircleImageView mUserPhoto;
    @BindView(R.id.fragment_distrib_user_name)
    TextView mUserName;
    @BindView(R.id.fragment_distrib_tip)
    TextView mUserParentId;
    @BindView(R.id.fragment_distrib_total_amount)
    TextView mUserTotalAmount;
    @BindView(R.id.fragment_distrib_deposit_money)
    TextView mUserDepositMoney;

    @BindView(R.id.fragment_distrib_order_textView)
    TextView mTabDistribOrderTextView;

    @BindView(R.id.fragment_distrib_money_layout)
    LinearLayout mMoneyLayout;

    @BindView(R.id.fragment_distrib_income_add)
    LinearLayout mIncomeAddLayout;
    @BindView(R.id.fragment_distrib_order_layout)
    LinearLayout mOrderLayout;
    @BindView(R.id.fragment_distrib_team_layout)
    LinearLayout mTeamLayout;
    @BindView(R.id.fragment_distrib_help_layout)
    LinearLayout mHelpLayout;

    @BindView(R.id.fragment_distributor_index_layout)
    LinearLayout mDistributorIndexLayout;
    @BindView(R.id.fragment_distrib_income_layout)
    LinearLayout mIncomeLayout;
    @BindView(R.id.fragment_distrib_category_layout)
    LinearLayout mDistribCateboryLayout;
    @BindView(R.id.fragment_distrib_shop_set_layout)
    LinearLayout mDistribShopSetLayout;

    private String distribText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_distrib;

        Intent intent = getActivity().getIntent();
        distribText = intent.getStringExtra(Key.KEY_DISTRIB_TEXT.getValue());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        refresh();
        initView();
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_distrib_setting:
                openSettingActivity();
                break;
            case R.id.fragment_distrib_money_layout:
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_DISTRIB_INCOME.getValue()));
                break;
            case R.id.fragment_distrib_income_layout:
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_DISTRIB_INCOME.getValue()));
                break;
            case R.id.fragment_distrib_income_add:
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_DISTRIB_INCOME_ADD.getValue()));
                break;
            case R.id.fragment_distrib_order_layout:
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_DISTRIB_ORDER.getValue()));
                break;
            case R.id.fragment_distrib_team_layout:
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_DISTRIB_TEAM.getValue()));
                break;
            case R.id.fragment_distrib_help_layout:
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_DISTRIB_HELP.getValue()));
                break;
            case R.id.fragment_distributor_index_layout:
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_DISTRIBUTOR_INDEX.getValue()));
                break;
            case R.id.fragment_distrib_category_layout:
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_DISTRIB_CATEGORY.getValue()));
                break;
            case R.id.fragment_distrib_shop_set_layout:
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_DISTRIB_SET.getValue()));
                break;
            default:
                super.onClick(v);
        }
    }


    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_DISTRIB, HTTP_WHAT_DISTRIB);
        addRequest(request);
    }

    public void initView() {
        mTabDistribOrderTextView.setText(distribText + "订单");
        mUserSetting.setOnClickListener(this);
        mMoneyLayout.setOnClickListener(this);

        mOrderLayout.setOnClickListener(this);
        mTeamLayout.setOnClickListener(this);
        mIncomeAddLayout.setOnClickListener(this);
        mHelpLayout.setOnClickListener(this);

        mDistributorIndexLayout.setOnClickListener(this);
        mIncomeLayout.setOnClickListener(this);
        mDistribCateboryLayout.setOnClickListener(this);
        mDistribShopSetLayout.setOnClickListener(this);
    }

    //接口成功回调
    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_DISTRIB:
                refreshSucceed(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }


    //设置数据
    private void refreshSucceed(String response) {
        HttpResultManager.resolve(response, DistribModel.class, new HttpResultManager
                .HttpResultCallBack<DistribModel>() {
            @Override
            public void onSuccess(DistribModel back) {
                ImageLoader.displayImage(Utils.urlOfImage(back.data.info.user_info.headimg),
                        mUserPhoto);

                if(!Utils.isNull(back.data.info.user_info.nickname)){
                    mUserName.setText(back.data.info.user_info.nickname);
                }else{
                    mUserName.setText(back.data.info.user_info.user_name);
                }

                String parentId = back.data.info.user_info.parent_id;
                mUserParentId.setText("您是由" + (parentId.equals("0") ? "平台方" : "ID"+parentId) + "推荐");
                mUserTotalAmount.setText(Utils.formatMoney(mUserTotalAmount.getContext(), back.data.shop_info.total_amount));
                mUserDepositMoney.setText(back.data.deposit_money);

                App.setCartNumber(back.data.context.cart.goods_count, this);
            }
        });
    }

    private void openSettingActivity() {
        Intent intent = new Intent(getActivity(), SetUpActivity.class);
        startActivity(intent);
    }

}
