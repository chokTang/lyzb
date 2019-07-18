package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Activity.DistributorApplyActivity;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Distributor.InfoModel;
import com.szy.yishopcustomer.ResponseModel.Distributor.Model;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by liwei on 2017/7/17.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistributorCheckFragment extends YSCBaseFragment {

    private static final int HTTP_WHAT_DISTRIBUTOR_CHECK = 1;
    private static final int VIEW_TYPE_DISTRIBUTOR_APPLY = 2;

    @BindView(R.id.fragment_distributor_check_title)
    TextView mCheckTitle;
    @BindView(R.id.fragment_distributor_check_permise)
    TextView mCheckPermise;
    @BindView(R.id.fragment_distributor_check_status_imageView)
    ImageView mCheckPermiseStatusImageView;
    @BindView(R.id.fragment_distributor_check_permise_status)
    TextView mCheckPermiseStatus;
    @BindView(R.id.fragment_distributor_check_permise_status2)
    TextView mCheckPermiseStatus2;
    @BindView(R.id.fragment_distributor_check_permise_layout)
    RelativeLayout mCheckPermiseLayout;
    @BindView(R.id.fragment_distributor_check_button)
    Button mCheckPermiseButton;
    @BindView(R.id.fragment_distributor_check_privilege)
    TextView mCheckPermisePrivilege;
    @BindView(R.id.fragment_distributor_check_rebate)
    TextView mCheckPermiseRebate;
    @BindView(R.id.fragment_distributor_check_explain)
    TextView mCheckPermiseExplain;

    private String distribText;
    private String getDistributorText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_distributor_check;

        Intent intent = getActivity().getIntent();
        distribText = intent.getStringExtra(Key.KEY_DISTRIB_TEXT.getValue());
        getDistributorText = intent.getStringExtra(Key.KEY_GETDISTRIBUTOR_TEXT.getValue());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        refresh();
        initView();
        return view;
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_DISTRIBUTOR_APPLY_CHECK,
                HTTP_WHAT_DISTRIBUTOR_CHECK);
        addRequest(request);
    }

    public void initView() {
        mCheckTitle.setText("申请成为" + getDistributorText + "的前提");
        mCheckPermisePrivilege.setText(getDistributorText + "特权");
        mCheckPermiseRebate.setText(distribText + "返利");
        mCheckPermiseExplain.setText(distribText + "佣金由平台方统一设置，" + getDistributorText +
                "无需囤货、发货、占用资源，发展的会员参与永久分成。");
        mCheckPermiseButton.setOnClickListener(this);
    }

    //接口成功回调
    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_DISTRIBUTOR_CHECK:
                refreshSucceed(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_DISTRIBUTOR_APPLY:
                openDistibutorApply();
                break;
            case VIEW_TYPE_INDEX:
                goIndex();
                break;
            default:
                super.onClick(v);
        }
    }

    //设置数据
    private void refreshSucceed(String response) {
        HttpResultManager.resolve(response, Model.class, new HttpResultManager
                .HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model back) {
                InfoModel info = back.data.info;
                if (info.dis_con.equals("1")) {
                    mCheckPermiseLayout.setVisibility(View.VISIBLE);
                    mCheckPermise.setText("账号在商城累计消费额满"+info.dis_order_money+"元");
                    if (info.code.equals("0")) {
                        mCheckPermiseStatusImageView.setImageResource(R.mipmap.ic_distribution_application1);
                        mCheckPermiseStatus.setText("已达成");
                        mCheckPermiseStatus2.setVisibility(View.GONE);
                        mCheckPermiseStatus.setTextColor(getResources().getColor(R.color
                                .colorGreen));
                    } else {
                        mCheckPermiseStatusImageView.setImageResource(R.mipmap.ic_distribution_application3);
                        mCheckPermiseStatus.setText("未达成");
                        mCheckPermiseStatus2.setVisibility(View.VISIBLE);
                        mCheckPermiseStatus2.setText("(已消费" + info.order_amount + "元)");
                        mCheckPermiseStatus.setTextColor(getResources().getColor(R.color
                                .colorThree));
                    }
                } else if (info.dis_con.equals("2")) {
                    mCheckPermiseLayout.setVisibility(View.VISIBLE);
                    mCheckPermise.setText("账号在商城累计交易订单"+info.dis_order_count+"个");
                    if (info.code.equals("0")) {
                        mCheckPermiseStatusImageView.setImageResource(R.mipmap.ic_distribution_application1);
                        mCheckPermiseStatus.setText("已达成");
                        mCheckPermiseStatus2.setVisibility(View.GONE);
                        mCheckPermiseStatus.setTextColor(getResources().getColor(R.color
                                .colorGreen));
                    } else {
                        mCheckPermiseStatusImageView.setImageResource(R.mipmap.ic_distribution_application3);
                        mCheckPermiseStatus.setText("未达成");
                        mCheckPermiseStatus2.setVisibility(View.VISIBLE);
                        mCheckPermiseStatus2.setText("(已交易" + info.order_count + "个)");
                        mCheckPermiseStatus.setTextColor(getResources().getColor(R.color
                                .colorThree));
                    }
                } else if (info.dis_con.equals("0")) {
                    mCheckPermiseLayout.setVisibility(View.GONE);
                }

                if (info.code.equals("0")) {
                    mCheckPermiseButton.setText("申请成为" + getDistributorText);
                    Utils.setViewTypeForTag(mCheckPermiseButton, ViewType
                            .VIEW_TYPE_DISTRIBUTOR_APPLY);
                } else {
                    mCheckPermiseButton.setText("继续逛逛");
                    Utils.setViewTypeForTag(mCheckPermiseButton, ViewType
                            .VIEW_TYPE_INDEX);
                }

            }
        });
    }

    private void goIndex() {
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_INDEX.getValue()));
        startActivity(new Intent().setClass(getActivity(), RootActivity.class));
        this.finish();
    }

    private void openDistibutorApply() {
        Intent intent = new Intent();
        intent.setClass(getActivity(),DistributorApplyActivity.class);
        intent.putExtra(Key.KEY_GETDISTRIBUTOR_TEXT.getValue(),getDistributorText);
        intent.putExtra(Key.KEY_DISTRIB_TEXT.getValue(),distribText);
        startActivity(intent);
    }

}
