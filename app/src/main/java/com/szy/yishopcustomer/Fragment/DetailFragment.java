package com.szy.yishopcustomer.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.DetailAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.DetailModel.Model;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.DetailModel;
import com.szy.yishopcustomer.ViewModel.EmptyItemModel;

import java.util.ArrayList;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Constant.Side;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * Created by liuzhfieng on 16/7/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DetailFragment extends YSCBaseFragment  implements OnPullListener {

    @BindView(R.id.fragment_detail_list_view)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.fragment_detail_list_view_layout)
    PullableLayout mPullableLayout;
    @BindView(R.id.fragment_detail_text_view_all)
    TextView mAll;
    @BindView(R.id.fragment_detail_text_view_income)
    TextView mInCome;
    @BindView(R.id.fragment_detail_text_view_spending)
    TextView mSpending;
    boolean upDataSuccess = true;
    private ArrayList<Object> list;
    private DetailAdapter adapter;

    private String trade_type = "trans-detail";
    private int page = 1;

    private final int ALL = 0;//全部
    private final int INCOME = 1;//收入
    private final int SPENDING = 2;//支出
    private int type = ALL;

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
        mLayoutId = R.layout.fragment_detail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        initViews();
        refresh();

        return v;
    }


    @Override
    public void onClick(View v) {
        if (Utils.isDoubleClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.fragment_detail_text_view_all:

                if(mRecyclerView.getScrollState() != 0){
                    return;
                }

                if (type != ALL){
                    list.clear();
                    page = 1;
                    trade_type = "trans-detail";
                    switchButton(mAll);
                    upDataSuccess = true;
                    type = ALL;
                    refresh();
                }
                break;
            case R.id.fragment_detail_text_view_income:

                if(mRecyclerView.getScrollState() != 0){
                    return;
                }

                if (type != INCOME){
                    list.clear();
                    page = 1;
                    trade_type = "income";
                    switchButton(mInCome);
                    upDataSuccess = true;
                    type = INCOME;
                    refresh();
                }
                break;
            case R.id.fragment_detail_text_view_spending:

                if(mRecyclerView.getScrollState() != 0){
                    return;
                }

                if (type != SPENDING){
                    list.clear();
                    page = 1;
                    trade_type = "expend";
                    switchButton(mSpending);
                    upDataSuccess = true;
                    type = SPENDING;
                    refresh();
                }
                break;
            default:
                super.onClick(v);
                break;
        }
    }


    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_CAPITAL_ACCOUNT:
                setData(response,ALL);
                break;
            case HTTP_CAPITAL_ACCOUNT_INCOME:
                setData(response,INCOME);
                break;
            case HTTP_CAPITAL_ACCOUNT_SPENDING:
                setData(response,SPENDING);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    /**
     * 设置数据
     * @param response  成功返回数据
     * @param position  请求type
     */
    private void setData(String response,int position){
        if (type != position){
            return;
        }
        upDataSuccess = true;
        Model mData = JSON.parseObject(response, Model.class);
        int pageCount = mData.data.page.page_count;
        mPullableLayout.topComponent.finish(Result.SUCCEED);
        if (mData.code.equals("0")) {
            if (pageCount == 0) {
                mRecyclerView.showEmptyView();
            } else if(page > pageCount){
                list.add(new EmptyItemModel());
                upDataSuccess = false;

                adapter.setData(list);
                adapter.notifyDataSetChanged();
            } else{
                if (mData.data.list != null && mData.data.list.size() != 0) {
                    for (int i = 0; i < mData.data.list.size(); i++) {
                        list.add(new CheckoutDividerModel());
                        DetailModel goods = new DetailModel();
                        goods.setMoney(mData.data.list.get(i).amount);
                        goods.setName(mData.data.list.get(i).note);
                        goods.setTime(Utils.toTimeString(mData.data.list.get(i).add_time));
                        goods.setType(mData.data.list.get(i).trade_type);
                        list.add(goods);
                    }
                }
                adapter.setData(list);
                adapter.notifyDataSetChanged();
            }
        } else {
            Utils.makeToast(getActivity(), "error");
            mPullableLayout.topComponent.finish(Result.FAILED);
        }
    }

    public void refresh() {
        CommonRequest request ;
        switch (type){
            case ALL:
                request = new CommonRequest(Api.API_CAPITAL_ACCOUNT, HttpWhat.HTTP_CAPITAL_ACCOUNT
                        .getValue());
                break;
            case INCOME:
                request = new CommonRequest(Api.API_CAPITAL_ACCOUNT, HttpWhat.HTTP_CAPITAL_ACCOUNT_INCOME
                        .getValue());
                break;
            case SPENDING:
                request = new CommonRequest(Api.API_CAPITAL_ACCOUNT, HttpWhat.HTTP_CAPITAL_ACCOUNT_SPENDING
                        .getValue());
                break;
                default:
                    request = new CommonRequest(Api.API_CAPITAL_ACCOUNT, HttpWhat.HTTP_CAPITAL_ACCOUNT
                            .getValue());
                    break;
        }
        request.add("trade_type", trade_type);
        request.add("page[cur_page]", page);
        request.add("page[page_size]", 10);
        addRequest(request);
    }

    void switchButton(View w) {
        mAll.setSelected(false);
        mInCome.setSelected(false);
        mSpending.setSelected(false);

        w.setSelected(true);
    }

    private void loadMore() {
        upDataSuccess = false;
        page++;
        refresh();
    }

    private void initViews() {
        switchButton(mAll);
        mAll.setOnClickListener(this);
        mInCome.setOnClickListener(this);
        mSpending.setOnClickListener(this);

        list = new ArrayList<Object>();
        adapter = new DetailAdapter();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mPullableLayout.topComponent.setOnPullListener(this);
    }

    @Override
    public void onCanceled(PullableComponent pullableComponent) {

    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        list.clear();
        page = 1;
        refresh();
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }
}
