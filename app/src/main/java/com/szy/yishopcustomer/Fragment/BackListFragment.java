package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyzb.jbx.R;
import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.BackDetailActivity;
import com.szy.yishopcustomer.Adapter.BackListAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.ResponseModel.Back.BackItemModel;
import com.szy.yishopcustomer.ResponseModel.Back.Model;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * Created by liwei on 2017/3/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BackListFragment extends YSCBaseFragment implements OnEmptyViewClickListener, OnPullListener {

    @BindView(R.id.fragment_back_list_RecyclerView)
    CommonRecyclerView mBackListRecyclerView;
    @BindView(R.id.fragment_back_list_pullableLayout)
    PullableLayout mPullableLayout;

    private LinearLayoutManager mLayoutManager;
    private BackListAdapter mBackListAdapter;

    private Model mModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_back_list;
        mModel = new Model();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        mBackListAdapter = new BackListAdapter();
        mLayoutManager = new LinearLayoutManager(getContext());
        mBackListRecyclerView.setLayoutManager(mLayoutManager);
        mBackListRecyclerView.setAdapter(mBackListAdapter);
        mBackListAdapter.onClickListener = this;
        mPullableLayout.topComponent.setOnPullListener(this);

        refresh();
        return v;
    }

    @Override
    public void onClick(View v) {
        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        int extraInfo = Utils.getExtraInfoOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_BACK_DETAIL:
                openBackDetailActivity(String.valueOf(extraInfo));
                break;
            default:
                super.onClick(v);
        }
    }

    @Override
    public void onOfflineViewClicked() {
        refresh();
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_BACK_LIST:
                mPullableLayout.topComponent.finish(Result.FAILED);
                mBackListRecyclerView.showOfflineView();
                break;
            default:
                super.onRequestFailed(what, response);
                break;
        }
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_BACK_LIST:
                refreshCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
        }
    }


    public void refresh() {
        CommonRequest mGroupBuyRequest = new CommonRequest(Api.API_BACK_LIST, HttpWhat
                .HTTP_BACK_LIST.getValue());
        addRequest(mGroupBuyRequest);
    }

    private void refreshCallback(String response) {
        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model back) {
                mPullableLayout.topComponent.finish(Result.SUCCEED);
                mModel = back;
                setUpAdapterData();
            }
        });
    }

    private void setUpAdapterData() {
        DividerModel dividerModel = new DividerModel();
        if (!Utils.isNull(mModel.data.list)) {
            mBackListRecyclerView.hideEmptyView();
            mBackListAdapter.data.clear();
            for (BackItemModel backItemModel : mModel.data.list) {
                mBackListAdapter.data.add(dividerModel);
                mBackListAdapter.data.add(backItemModel);
            }
        } else {
            mBackListRecyclerView.showEmptyView();
        }
        mBackListAdapter.notifyDataSetChanged();
    }

    public void openBackDetailActivity(String backId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_BACK_ID.getValue(), backId);
        intent.setClass(getActivity(), BackDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCanceled(PullableComponent pullableComponent) {

    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        refresh();
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }
}
