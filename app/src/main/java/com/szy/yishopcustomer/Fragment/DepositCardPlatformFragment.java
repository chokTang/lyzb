package com.szy.yishopcustomer.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.DepositCardAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Other.BottomMenuController;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.DepositCardModel;
import com.szy.yishopcustomer.Util.HttpResultManager;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Constant.Side;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepositCardPlatformFragment extends YSCBaseFragment implements OnPullListener {

    private static final int HTTP_WHAT_USER_RECHARE_CARD = 1;

    @BindView(R.id.fragment_deposit_card_listView_layout)
    PullableLayout fragment_deposit_card_listView_layout;
    @BindView(R.id.fragment_deposit_card_listView)
    CommonRecyclerView mRecyclerView;

    @BindView(R.id.relativeLayout_empty)
    LinearLayout relativeLayout_empty;

    private DepositCardAdapter mAdapter;
    private DepositCardModel data;

    //默认加载第一页
    private int cur_page = 1;
    private int page_size = 10;

    public DepositCardPlatformFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_deposit_card_platform;

        mAdapter = new DepositCardAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        BottomMenuController.init(getContext(), view);

        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        fragment_deposit_card_listView_layout.topComponent.setOnPullListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_USER_RECHARGE_CARD,
                HTTP_WHAT_USER_RECHARE_CARD);
//        request.add("shop_id", mShopId);
        addRequest(request);
    }

    //接口成功回调
    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_USER_RECHARE_CARD:
                refreshSucceed(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }


    //设置数据
    private void refreshSucceed(String response) {
        fragment_deposit_card_listView_layout.topComponent.finish(Result.SUCCEED);

        HttpResultManager.resolve(response, DepositCardModel.class, new HttpResultManager
                .HttpResultCallBack<DepositCardModel>() {
            @Override
            public void onSuccess(DepositCardModel back) {

                data = back;

                if (cur_page == 1) {
                    mAdapter.data.clear();
                    mAdapter.setFooterView(null);
                }

                if (back.data.list.size() > 0) {
                    relativeLayout_empty.setVisibility(View.GONE);
                    mAdapter.data.addAll(back.data.list);
                } else {
                    relativeLayout_empty.setVisibility(View.VISIBLE);
                }


                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onEmptyData(int state) {
                relativeLayout_empty.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(String message) {
                relativeLayout_empty.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onCanceled(PullableComponent pullableComponent) {

    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        if (pullableComponent.getSide().toString().equals("TOP")) {
            cur_page = 1;
            refresh();
        }
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }


    private boolean upDataSuccess = true;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 0) {
                if (mRecyclerView.reachEdgeOfSide(Side.BOTTOM)) {
                    if (upDataSuccess) {
                        if (data != null) {
                            cur_page = data.data.page.cur_page + 1;
                            loadMore();
                        }
                    }
                }
            }
        }
    };

    public void loadMore() {
        if (data.data != null && cur_page > data.data.page.page_count) {
            //设置footer
            View footer = LayoutInflater.from(getContext()).inflate(R.layout.fragment_goods_list_item_empty, null);
            mAdapter.setFooterView(footer);
        } else {
            refresh();
        }
    }
}
