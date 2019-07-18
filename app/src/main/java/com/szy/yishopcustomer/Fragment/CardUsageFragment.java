package com.szy.yishopcustomer.Fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.CardUsageAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.CardUsageModel;
import com.szy.yishopcustomer.Util.HttpResultManager;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Constant.Side;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * Smart
 */
public class CardUsageFragment extends YSCBaseFragment implements OnPullListener {


    @BindView(R.id.fragment_pullableLayout)
    PullableLayout fragment_pullableLayout;
    @BindView(R.id.fragment_recyclerView)
    CommonRecyclerView fragment_recyclerView;

    @BindView(R.id.relativeLayout_empty)
    View relativeLayout_empty;

    private CardUsageAdapter mAdapter;
    private CardUsageModel data;

    private String card_id = "---";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_card_usage;

        mAdapter = new CardUsageAdapter();
        mAdapter.onClickListener = this;

        card_id = getActivity().getIntent().getStringExtra(Key.KEY_CARD_ID.getValue());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        fragment_recyclerView.addOnScrollListener(mOnScrollListener);
        fragment_recyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragment_recyclerView.setLayoutManager(layoutManager);
        fragment_pullableLayout.topComponent.setOnPullListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
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

    //默认加载第一页
    private int cur_page = 1;

    public void refresh() {
        if (data != null && cur_page > data.data.page.page_count && cur_page > 1) {
            //设置footer
            View footer = LayoutInflater.from(getContext()).inflate(R.layout.fragment_goods_list_item_empty, null);
            mAdapter.setFooterView(footer);
        } else {
            CommonRequest mOrderListRequest = new CommonRequest(Api.API_USER_STORE_RECHARGE_CARD_LOG_INFO, HttpWhat
                    .HTTP_ORDER_LIST.getValue());
            mOrderListRequest.add("card_id", card_id);
            mOrderListRequest.add("page[cur_page]", cur_page);

            addRequest(mOrderListRequest);
        }
    }

    //接口成功回调
    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_ORDER_LIST:
                refreshSucceed(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    //设置数据
    private void refreshSucceed(String response) {
        fragment_pullableLayout.topComponent.finish(Result.SUCCEED);

        HttpResultManager.resolve(response, CardUsageModel.class, new HttpResultManager.HttpResultCallBack<CardUsageModel>() {
            @Override
            public void onSuccess(CardUsageModel back) {
                data = back;

                if (cur_page == 1) {
                    mAdapter.data.clear();
                    mAdapter.setFooterView(null);
                }

                if (back.data.list != null && back.data.list.size() > 0) {
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
                toast(message);
                relativeLayout_empty.setVisibility(View.VISIBLE);
            }
        });
    }


    private boolean upDataSuccess = true;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 0) {
                if (fragment_recyclerView.reachEdgeOfSide(Side.BOTTOM)) {
                    if (upDataSuccess) {
                        if (data != null) {
                            cur_page = data.data.page.cur_page + 1;
                            refresh();
                        }
                    }
                }
            }
        }
    };
}
