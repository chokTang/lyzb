package com.szy.yishopcustomer.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;

import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.DepositListAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Other.BottomMenuController;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.DepositList.Model;
import com.szy.yishopcustomer.ResponseModel.DepositList.WithdrawalRecordList;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.EmptyItemModel;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Constant.Side;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * Created by liuzhifeng on 2016/7/23 0023.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DepositListFragment extends YSCBaseFragment implements OnPullListener {

    @BindView(R.id.fragment_withdrawal_record_listview)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.fragment_withdrawal_record_pushlayout)
    PullableLayout mPullableLayout;
    private DepositListAdapter adapter;
    private int page = 1;
    private List<Object> mRecordData;
    private int mPosition;
    private int pageCount;
    private boolean upDataSuccess = true;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 0) {
                if (mRecyclerView.reachEdgeOfSide(Side.BOTTOM)) {
                    if (upDataSuccess) {
                        refreshBottom();
                    }

                }
            }
        }
    };

    @Override
    public void onCanceled(PullableComponent pullableComponent) {

    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        if (pullableComponent.getSide().toString().equals("BOTTOM")) {
            refreshBottom();

        } else if (pullableComponent.getSide().toString().equals("TOP")) {
            refreshTop();
        }
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }

    @Override
    public void onClick(View v) {
        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        int extraInfo = Utils.getExtraInfoOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_DEPOSIT_CANCLE:
                refreshCancel(extraInfo, position);
                break;
            case VIEW_TYPE_DEPOSIT_DELETE:
                refreshDelete(extraInfo, position);
                break;
            default:
                super.onClick(v);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        BottomMenuController.init(getContext(),v);

        mRecordData = new ArrayList<>();
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        ArrayList<Object> data = new ArrayList<>();
        adapter = new DepositListAdapter(data);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
        adapter.onClickListener = this;
        mPullableLayout.topComponent.setOnPullListener(this);

        request();

        return v;
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GET_WITHDRAWAL_RECORD_LIST:
                recordCallback(response);
                break;
            case HTTP_GET_WITHDRAWAL_RECORD_LIST_DELETE:
                deleteCallback(response);
                break;
            case HTTP_GET_WITHDRAWAL_RECORD_LIST_CANCEL:
                cancelCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_deposit_list;
    }

    private void cancelCallback(String response) {
        Model model;
        model = JSON.parseObject(response, Model.class);
        if (model.code.equals("0")) {
            Utils.toastUtil.showToast(getActivity(), model.message);
            WithdrawalRecordList data = (WithdrawalRecordList) mRecordData.get(mPosition);
            data.status = "3";
            adapter.setData(mRecordData);
            adapter.notifyDataSetChanged();
        }
    }

    private void deleteCallback(String response) {
        Model model;
        model = JSON.parseObject(response, Model.class);
        if (model.code.equals("0")) {
            Utils.toastUtil.showToast(getActivity(), model.message);
            mRecordData.remove(mPosition);
            mRecordData.remove(mPosition - 1);
            adapter.setData(mRecordData);
            adapter.notifyDataSetChanged();
        }
    }

    private void recordCallback(String response) {
        upDataSuccess = true;
        Model model = JSON.parseObject(response, Model.class);
        if (!Utils.isNull(model.data.list) && model.data.list.size() != 0) {
            pageCount = model.data.page.page_count;
            for (WithdrawalRecordList withdrawalRecordList : model.data.list) {
                mRecordData.add(new DividerModel());
                mRecordData.add(withdrawalRecordList);
            }
            adapter.setData(mRecordData);
            if (page > 1) {
                mRecyclerView.smoothScrollBy(0, 100);
            }
            adapter.notifyDataSetChanged();

        } else {
            mRecyclerView.showEmptyView();
        }
    }

    private void refreshBottom() {
        upDataSuccess = false;
        page++;
        if (page > pageCount) {
            upDataSuccess = false;
            mRecordData.add(new EmptyItemModel());
            adapter.setData(mRecordData);
            adapter.notifyDataSetChanged();
            return;
        }
        request();
    }

    private void refreshCancel(int extraInfo, int position) {
        mPosition = position;
        CommonRequest request = new CommonRequest(Api.API_DEPOSIT_LIST_CANCEL, HttpWhat
                .HTTP_GET_WITHDRAWAL_RECORD_LIST_CANCEL.getValue(), RequestMethod.POST);
        request.add("id", extraInfo);
        addRequest(request);
    }

    private void refreshDelete(int extraInfo, int position) {
        mPosition = position;
        CommonRequest request = new CommonRequest(Api.API_DEPOSIT_LIST_DELETE, HttpWhat
                .HTTP_GET_WITHDRAWAL_RECORD_LIST_DELETE.getValue(), RequestMethod.POST);
        request.add("id", extraInfo);
        addRequest(request);
    }

    private void refreshTop() {
        mRecordData = new ArrayList<>();
        page = 1;
        request();
        mPullableLayout.topComponent.finish(Result.SUCCEED);
    }

    private void request() {
        CommonRequest request = new CommonRequest(Api.API_DEPOSIT_LIST, HttpWhat
                .HTTP_GET_WITHDRAWAL_RECORD_LIST.getValue());
        request.add("page[page_size]", "10");
        request.add("page[cur_page]", page);
        addRequest(request);
    }

}
