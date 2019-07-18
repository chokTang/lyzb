package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.GoodsBonusAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Goods.BonusModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;

import butterknife.BindView;

//import com.szy.common.View.CustomProgressDialog;

/**
 * Created by liwei on 2016/12/10
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class GoodsBonusFragment extends YSCBaseFragment {
    @BindView(R.id.fragment_goods_bonus_recyclerView)
    CommonRecyclerView mRecyclerView;

    @BindView(R.id.fragment_goods_bonus_shop_name)
    TextView mShopNameLabel;
    @BindView(R.id.fragment_goods_bonus_cancel_button)
    Button mCloseButton;
    @BindView(R.id.fragment_goods_bonus_cancel_layout)
    TextView mCloseLayout;
    @BindView(R.id.fragment_bonus_shop_info_relativelayout)
    View fragment_bonus_shop_info_relativelayout;

    private LinearLayoutManager mLayoutManager;
    private GoodsBonusAdapter mGoodsBonusAdapter;
    private ArrayList<BonusModel> mBonusList;
    private String mShopName;
    private String mBonusId;

    //默认的红包领取界面，0，1是自由购的
    private int type = 0;

    @Override
    public void onClick(View v) {
        if(Utils.isDoubleClick()) {
            return;
        }
        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        int extraInfo = Utils.getExtraInfoOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_CLOSE:
                getActivity().finish();
                break;
            case VIEW_TYPE_TAKE_BONUS:
                mBonusId = extraInfo + "";
                receiveBonus(mBonusId);
                break;
            default:
                super.onClick(v);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);

        Bundle arguments = getArguments();
        mBonusList = arguments.getParcelableArrayList(Key.KEY_BONUS_LIST.getValue());
        mShopName = arguments.getString(Key.KEY_SHOP_NAME.getValue());
        mShopNameLabel.setText(mShopName);

        type = arguments.getInt("type");

        mGoodsBonusAdapter = new GoodsBonusAdapter(mBonusList);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mGoodsBonusAdapter);

        Utils.setViewTypeForTag(mCloseButton, ViewType.VIEW_TYPE_CLOSE);
        mCloseButton.setOnClickListener(this);
        Utils.setViewTypeForTag(mCloseLayout, ViewType.VIEW_TYPE_CLOSE);
        mCloseLayout.setOnClickListener(this);

        mGoodsBonusAdapter.onClickListener = this;

        if(type == 1) {
            fragment_bonus_shop_info_relativelayout.setBackgroundColor(Color.WHITE);
            mRecyclerView.setBackgroundResource(R.color.colorTen);
            mCloseButton.setBackgroundResource(R.color.free_buy_primary);
            mGoodsBonusAdapter.type = 1;

        }

        return view;
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_RECEIVE_BONUS:
                break;
            default:
                super.onRequestFailed(what, response);
                break;
        }

    }

    @Override
    protected void onRequestSucceed(int what, String response) {

        switch (HttpWhat.valueOf(what)) {
            case HTTP_RECEIVE_BONUS:
                receiveBonusCallBack(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_goods_bonus;
    }

    private void receiveBonus(String bonusId) {
        CommonRequest mRequest = new CommonRequest(Api.API_RECEIVE_BONUS, HttpWhat
                .HTTP_RECEIVE_BONUS.getValue(), RequestMethod.POST);
        mRequest.add("bonus_id", bonusId);
        //mRequest.setAjax(true);
        addRequest(mRequest);
    }

    private void receiveBonusCallBack(String response) {
        ResponseCommonModel model = JSON.parseObject(response, ResponseCommonModel.class);
        HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
            @Override
            public void onSuccess(ResponseCommonModel model) {
                Utils.makeToast(getActivity(), model.message);

                Intent result = new Intent();
                getActivity().setResult(Activity.RESULT_OK, result);
                getActivity().finish();
            }
        },true);
    }

    @Override
    public void onOfflineViewClicked() {
        receiveBonus(mBonusId);
    }

}
