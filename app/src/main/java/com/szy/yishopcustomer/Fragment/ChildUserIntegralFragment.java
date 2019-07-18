package com.szy.yishopcustomer.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.LinearLayout;

import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.IntegralMallActivity;
import com.szy.yishopcustomer.Adapter.UserIntegralAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.UserIntegralModel;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Constant.Side;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * 我的积分，child
 */
public class ChildUserIntegralFragment extends YSCBaseFragment implements OnPullListener{

    private static final int HTTP_WHAT_USER_EXCHANGE = 1;

    @BindView(R.id.fragment_pullableLayout)
    PullableLayout fragment_pullableLayout;
    @BindView(R.id.fragment_recyclerView)
    CommonRecyclerView fragment_recyclerView;

    @BindView(R.id.relativeLayout_empty)
    LinearLayout relativeLayout_empty;

    private UserIntegralAdapter mAdapter;
    private UserIntegralModel data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_child_user_integral;

        mAdapter = new UserIntegralAdapter();
        mAdapter.onClickListener = this;
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

        View header = LayoutInflater.from(getContext()).inflate(R.layout.item_divider_header, null);
        mAdapter.setHeaderView(header);

        refresh();
        return view;
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
        if (data != null && cur_page > data.data.page.page_count) {
            //设置footer
            View footer = LayoutInflater.from(getContext()).inflate(R.layout.fragment_goods_list_item_empty, null);
            mAdapter.setFooterView(footer);
        } else {
            CommonRequest request = new CommonRequest(Api.API_USER_INTEGRAL_SHOP_POINTS, HTTP_WHAT_USER_EXCHANGE);
            request.add("page[cur_page]", cur_page);
            addRequest(request);
        }
    }


    //接口成功回调
    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_USER_EXCHANGE:
                refreshSucceed(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    //设置数据
    private void refreshSucceed(String response){
        fragment_pullableLayout.topComponent.finish(Result.SUCCEED);

        HttpResultManager.resolve(response, UserIntegralModel.class, new HttpResultManager.HttpResultCallBack<UserIntegralModel>() {
            @Override
            public void onSuccess(UserIntegralModel back) {
                data = back;

                if(cur_page == 1) {
                    mAdapter.data.clear();
                    mAdapter.setFooterView(null);
                }

                if(back.data.list.size() > 0) {
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
    public void onClick(View v) {
        if(Utils.isDoubleClick()) {
            return;
        }

        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);

        switch(viewType) {
            case VIEW_TYPE_SHIPPING_TYPE:
                UserIntegralModel.DataBean.ListBean temp = mAdapter.data.get(position);
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), ShopActivity.class);
//                intent.putExtra(Key.KEY_SHOP_ID.getValue(), temp.shop_id);
//                startActivity(intent);

                Intent intent = new Intent();
                intent.setClass(getActivity(), IntegralMallActivity.class);
                intent.putExtra(Key.KEY_SHOP_ID.getValue(), temp.shop_id);
                startActivity(intent);
                break;
        }
    }

    private boolean upDataSuccess = true;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 0) {
                if (fragment_recyclerView.reachEdgeOfSide(Side.BOTTOM)) {
                    if (upDataSuccess) {
                        try {
                            cur_page = data.data.page.cur_page + 1;
                        }catch (Exception e) {

                        }
                        refresh();
                    }
                }
            }
        }
    };


}
