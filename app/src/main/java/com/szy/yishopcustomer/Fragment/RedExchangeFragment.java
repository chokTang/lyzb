package com.szy.yishopcustomer.Fragment;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.RedExchangeAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.BaseEntity;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.RedExchangeModel;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Constant.Side;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

public class  RedExchangeFragment extends YSCBaseFragment implements OnPullListener {

    private static final int HTTP_WHAT_EXCHANGE_BONUS_LIST = 1;
    private static final int HTTP_WHAT_EXCHANGE_BONUS_EXCHANGE = 2;

    @BindView(R.id.fragment_red_exchange_listView_layout)
    PullableLayout fragment_red_exchange_listView_layout;
    @BindView(R.id.fragment_red_exchange_listView)
    CommonRecyclerView fragment_red_exchange_listView;

    @BindView(R.id.relativeLayout_empty)
    LinearLayout relativeLayout_empty;

    private Context mContext;
    private RedExchangeAdapter mAdapter;
//    private DepositCardModel mStoreModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_red_exchange;
        mContext = getActivity();
        mAdapter = new RedExchangeAdapter();
        mAdapter.onClickListener = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        fragment_red_exchange_listView.addOnScrollListener(mOnScrollListener);
        fragment_red_exchange_listView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragment_red_exchange_listView.setLayoutManager(layoutManager);

        fragment_red_exchange_listView_layout.topComponent.setOnPullListener(this);

        refresh();
        return view;
    }

    //默认加载第一页
    private int cur_page = 1;
    public void refresh() {
        if (data != null && cur_page > data.data.page.page_count) {
            //设置footer
            View footer = LayoutInflater.from(getContext()).inflate(R.layout.fragment_goods_list_item_empty, null);
            mAdapter.setFooterView(footer);
        } else {
            CommonRequest request = new CommonRequest(Api.API_EXCHANGE_BONUS_LIST, HTTP_WHAT_EXCHANGE_BONUS_LIST);
            request.add("page[cur_page]", cur_page);
            addRequest(request);
        }
    }

    public void refreshTwo(){
        cur_page = 1;
        CommonRequest request = new CommonRequest(Api.API_EXCHANGE_BONUS_LIST, HTTP_WHAT_EXCHANGE_BONUS_LIST);
        request.add("page[cur_page]", cur_page);
        addRequest(request);
    }

    //接口成功回调
    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_EXCHANGE_BONUS_LIST:
                refreshSucceed(response);
                break;
            case HTTP_WHAT_EXCHANGE_BONUS_EXCHANGE:
                refreshSucceedExchange(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private RedExchangeModel data;

    //设置数据
    private void refreshSucceed(String response) {
        fragment_red_exchange_listView_layout.topComponent.finish(Result.SUCCEED);

        HttpResultManager.resolve(response, RedExchangeModel.class, new HttpResultManager.HttpResultCallBack<RedExchangeModel>() {
            @Override
            public void onSuccess(RedExchangeModel back) {
                mAdapter.site_name = back.data.context.config.site_name;
                data = back;

                if(cur_page == 1) {
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

    //兑换红包成功
    private void refreshSucceedExchange(String response) {
        HttpResultManager.resolve(response, BaseEntity.class, new HttpResultManager.HttpResultCallBack<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity back) {
                toast("兑换成功");
                refreshTwo();
            }
        },true);
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

    @Override
    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }

        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_RED_EXCHANGE:
                //弹出对话框是否兑换
                exchange(position);
                break;
        }
    }

    private void exchange(int position){
        RedExchangeModel.DataBean.ListBean temp = mAdapter.data.get(position);
//        String msg = "兑换此红包，将需要扣除您 "+temp.shop_name+" 店铺 "+temp.bonus_data.exchange_points+" 积分，您确定是否兑换？";
        String msg = "兑换此红包，将需要扣除您 "+temp.bonus_data.exchange_points+" 积分，您确定是否兑换？";

        openPrompt(msg, ViewType.VIEW_TYPE_RED_EXCHANGE
                .ordinal(), position);
    }


    @Override
    public void onConfirmDialogConfirmed(int viewType, int position, int extraInfo) {
        switch (ViewType.valueOf(viewType)) {
            case VIEW_TYPE_RED_EXCHANGE:
                //调用兑换接口
                RedExchangeModel.DataBean.ListBean temp = mAdapter.data.get(position);
                CommonRequest request = new CommonRequest(Api.API_EXCHANGE_BONUS_EXCHANGE, HTTP_WHAT_EXCHANGE_BONUS_EXCHANGE);
                request.add("shop_id", temp.shop_id);
                request.add("id", temp.bonus_id);
                addRequest(request);
                break;
        }
    }


    private void openPrompt(String message, final int viewType, final int position){
        View view = LayoutInflater.from(getContext()).inflate(com.szy.common.R.layout.dialog_common_confirm, null);
        final android.support.v7.app.AlertDialog mConfirmDialog = new android.support.v7.app.AlertDialog.Builder(getContext()).create();
        mConfirmDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mConfirmDialog.setView(view);
        Button confirmButton = (Button) view.findViewById(com.szy.common.R.id.dialog_common_confirm_confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View button) {
                mConfirmDialog.hide();
                onConfirmDialogConfirmed(viewType, position, 0);
            }
        });

        Button cancelButton = (Button) view.findViewById(com.szy.common.R.id.dialog_common_confirm_cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View button) {
                mConfirmDialog.hide();
                onConfirmDialogCanceled(viewType, position, 0);
            }
        });

        TextView textView = (TextView) view.findViewById(com.szy.common.R.id.dialog_common_confirm_textView);
        textView.setText(message);

        mConfirmDialog.show();
    }


    private boolean upDataSuccess = true;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 0) {
                if (fragment_red_exchange_listView.reachEdgeOfSide(Side.BOTTOM)) {
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
