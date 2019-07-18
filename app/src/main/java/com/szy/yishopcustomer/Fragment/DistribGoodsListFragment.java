package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Toast;

import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Adapter.DistribGoodsListAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.CommonModel;
import com.szy.yishopcustomer.ResponseModel.Distrib.DistribGoodsListModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Side;

/**
 * Created by liwei on 2017/9/26.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistribGoodsListFragment extends YSCBaseFragment {
    private static final int HTTP_WHAT_DISTRIB_LIST = 1;
    private static final int HTTP_WHAT_DISTRIB_LIST_SALE = 2;

    @BindView(R.id.fragment_distrib_goods_list)
    CommonRecyclerView mRecyclerView;

    private LinearLayoutManager mLayoutManager;
    private DistribGoodsListAdapter mAdapter;

    private boolean upDataSuccess = true;
    private String catId;

    //默认加载第一页
    private int cur_page = 1;
    private int page_size = 1;
    private int pageCount;
    private int mPosition;

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 0) {
                if (mRecyclerView.reachEdgeOfSide(Side.BOTTOM)) {
                    if (upDataSuccess) {
                        loadMore();
                    }

                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_distrib_goods_list;

        Bundle arguments = getArguments();
        catId = arguments.getString(Key.KEY_REQUEST_CATEGORY_ID.getValue());
        refresh(catId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mAdapter = new DistribGoodsListAdapter();
        mAdapter.onClickListener = this;
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            refresh(catId);
        } else {
            mAdapter.data.clear();
            cur_page = 1;
        }
    }

    @Override
    public void onClick(View v) {
        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        DistribGoodsListModel.DataBean.DistribGoodsItemModel goodsItemModel = (DistribGoodsListModel.DataBean.DistribGoodsItemModel)mAdapter.data.get(position);
        switch (viewType) {
            case VIEW_TYPE_GOODS:
                openGoodsActivity(goodsItemModel.goods_id);
                break;
            case VIEW_TYPE_SALE:
                mPosition = position;
                toggleSale(goodsItemModel.goods_id);
                break;
            default:
                super.onClick(v);
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_DISTRIB_LIST:
                refreshCallback(response);
                break;
            case HTTP_WHAT_DISTRIB_LIST_SALE:
                toggleSaleCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    public void refresh(String catId) {
        CommonRequest request = new CommonRequest(Api.API_DISTRIBUTOR_LIST, HTTP_WHAT_DISTRIB_LIST);
        request.add("cat_id", catId);
        request.add("page[cur_page]", cur_page);
        request.add("page[page_size]", page_size);
        addRequest(request);
    }

    private void refreshCallback(final String response) {
        HttpResultManager.resolve(response, DistribGoodsListModel.class, new HttpResultManager
                .HttpResultCallBack<DistribGoodsListModel>() {
            @Override
            public void onSuccess(DistribGoodsListModel distribGoodsListModel) {
                pageCount = distribGoodsListModel.data.page.page_count;
                upDataSuccess = true;

                if (cur_page == 1) {
                    mAdapter.data.clear();
                    mAdapter.setFooterView(null);
                }

                if (distribGoodsListModel.data.list.size() != 0) {
                    mRecyclerView.hideEmptyView();
                    mAdapter.data.addAll(distribGoodsListModel.data.list);
                }else {
                    upDataSuccess = false;
                    mRecyclerView.showEmptyView();
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void toggleSaleCallback(final String response) {
        HttpResultManager.resolve(response, CommonModel.class, new HttpResultManager
                .HttpResultCallBack<CommonModel>() {
            @Override
            public void onSuccess(CommonModel commonModel) {
                if(commonModel.code == 0){
                    Toast.makeText(getActivity(),commonModel.message,Toast.LENGTH_SHORT).show();
                }

                for (int i = 0; i < mAdapter.data.size(); i++) {
                    if(i == mPosition){
                        DistribGoodsListModel.DataBean.DistribGoodsItemModel goodsItemModel = (DistribGoodsListModel.DataBean.DistribGoodsItemModel)mAdapter.data.get(i);
                        goodsItemModel.is_sale = goodsItemModel.is_sale.equals("0")?"1":"0";
                    }
                }

                mAdapter.notifyDataSetChanged();

            }
        },true);
    }

    private void loadMore() {
        upDataSuccess = false;
        cur_page++;
        if (cur_page > pageCount) {
            upDataSuccess = false;

            View footer = LayoutInflater.from(getContext()).inflate(R.layout.fragment_goods_list_item_empty, null);
            mAdapter.setFooterView(footer);
        } else {
            refresh(catId);
        }
    }


    public void openGoodsActivity(String goodsId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_GOODS_ID.getValue(), goodsId);
        intent.setClass(getActivity(), GoodsActivity.class);
        startActivity(intent);
    }

    private void toggleSale(String goodsId){
        CommonRequest request = new CommonRequest(Api.API_DISTRIB_LIST_SALE, HTTP_WHAT_DISTRIB_LIST_SALE);
        request.add("goods_id", goodsId);
        addRequest(request);
    }

    public void setCatId(String cat_id){
        catId = cat_id;
    }
}
