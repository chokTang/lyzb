package com.szy.yishopcustomer.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.szy.common.Fragment.CommonFragment;
import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.IngotListAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.IngotList.IngotGoodModel;
import com.szy.yishopcustomer.ResponseModel.IngotList.UsableIngotModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.ListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 我的元宝 fragment
 * @time 2018 15:18
 */

public class IngotListFragment extends CommonFragment implements View.OnClickListener, OnEmptyViewClickListener {

    /**
     * 我的元宝 列表
     **/
    @BindView(R.id.ingot_list_recyclerView)
    CommonRecyclerView mIngotlistRecyclerView;

    View mView = null;
    IngotListAdapter mAdapter;

    //1 低价换购  2 超值推荐
    private int IngotGoodType = 1;
    private List<IngotGoodModel> mLowPriceModels = new ArrayList<>();
    private List<IngotGoodModel> mRecommModels = new ArrayList<>();

    private UsableIngotModel mIngotModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_ingot_list, container, false);
            ButterKnife.bind(this, mView);
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mIngotlistRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mIngotlistRecyclerView.addItemDecoration(ListItemDecoration.createVertical(getActivity(), getResources().getColor(R.color.ads_itemd_bg), 36));

        mAdapter = new IngotListAdapter(getActivity());
        mIngotlistRecyclerView.setAdapter(mAdapter);

        getGoodData();

        mAdapter.setItemGoodClick(new IngotListAdapter.ItemGoodClick() {
            @Override
            public void onLowPriceClick() {
                IngotGoodType = 1;

                if (mLowPriceModels.size() > 0) {
                    mAdapter.mGoodModels.clear();

                    mAdapter.mGoodModels.addAll(mLowPriceModels);
                    mAdapter.notifyDataSetChanged();
                } else {
                    getGoodData();
                }
            }

            @Override
            public void onRecommClick() {
                IngotGoodType = 2;

                if (mRecommModels.size() > 0) {
                    mAdapter.mGoodModels.clear();

                    mAdapter.mGoodModels.addAll(mRecommModels);
                    mAdapter.notifyDataSetChanged();
                } else {

                    getGoodData();
                }
            }
        });
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            default:
                mIngotlistRecyclerView.showOfflineView();
                break;
        }
    }


    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_INGOT_RECOMM:
                if (JSON.parseObject(response).getInteger("code") == 0) {
                    JSONObject object = JSON.parseObject(response).getJSONObject("data");

                    if (IngotGoodType == 1) {
                        mLowPriceModels = JSON.parseArray(object.getString("list"), IngotGoodModel.class);
                        mAdapter.mGoodModels.addAll(mLowPriceModels);
                        mAdapter.notifyDataSetChanged();

                    } else if (IngotGoodType == 2) {
                        mAdapter.mGoodModels.clear();
                        mRecommModels = JSON.parseArray(object.getString("list"), IngotGoodModel.class);
                        mAdapter.mGoodModels.addAll(mRecommModels);

                        mAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case HTTP_INGOT_USABLE:
                mIngotModel = JSON.parseObject(response, UsableIngotModel.class);
                if (mIngotModel.getCode() == 0) {
                    mAdapter.mUserIngot = mIngotModel.getData().getTotal_integral().getIntegral_num();

                    App.getInstance().user_ingot_number = mIngotModel.getData().getTotal_integral().getIntegral_num();
                    mAdapter.notifyDataSetChanged();
                }
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private void getUserIngot() {
        CommonRequest request = new CommonRequest(Api.API_INGOT_USABLE, HttpWhat.HTTP_INGOT_USABLE.getValue());
        addRequest(request);
    }


    private void getGoodData() {

        CommonRequest request = new CommonRequest(Api.API_INGOT_RECOMM, HttpWhat.HTTP_INGOT_RECOMM.getValue());
        request.add("type", IngotGoodType);
        request.add("cur_page", 1);
        request.add("page_size", 20);
        addRequest(request);
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserIngot();
    }
}
