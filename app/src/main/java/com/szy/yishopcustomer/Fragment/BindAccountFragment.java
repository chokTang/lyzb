package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.TextView;

import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.AddAccountActivity;
import com.szy.yishopcustomer.Adapter.BindAccountAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Deposit.Model;
import com.szy.yishopcustomer.ResponseModel.Deposit.WithdrawalList;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * Created by liuzhifeng on 2017/3/9.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class BindAccountFragment extends YSCBaseFragment implements OnEmptyViewClickListener, OnPullListener {

    @BindView(R.id.fragment_bind_account_RecyclerView)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.bottom_button)
    TextView mAddAccountButton;
    @BindView(R.id.fragment_bind_account_pullableLayout)
    PullableLayout mPullableLayout;
    private BindAccountAdapter adapter;
    private int mPosition;

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_REFRESH_BIND_LIST:
                refresh();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_ACCOUNT_DELETE:
                mPosition = position;
                showConfirmDialog(R.string.isDelete, ViewType.VIEW_TYPE_CLEAR_CONFIRM.ordinal());
                break;
            case VIEW_TYPE_ADD:
                openAddAccountActivity();
                break;
            default:
                super.onClick(view);
        }
    }

    private void openAddAccountActivity() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), AddAccountActivity.class);
        intent.putExtra(Key.KEY_TYPE.getValue(), "1");
        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_bind_account;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        Utils.setViewTypeForTag(mAddAccountButton, ViewType.VIEW_TYPE_ADD);
        mAddAccountButton.setOnClickListener(this);
        mPullableLayout.topComponent.setOnPullListener(this);

        adapter = new BindAccountAdapter();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
        final int topMargin = Utils.dpToPx(getContext(), 10);
        RecyclerView.ItemDecoration mItemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView
                    .State state) {
                super.getItemOffsets(outRect, view, parent, state);
                final int itemPosition = parent.getChildAdapterPosition(view);
                outRect.top = topMargin;
            }
        };
        mRecyclerView.addItemDecoration(mItemDecoration);
        adapter.onClickListener = this;
        mAddAccountButton.setText("绑定账户");
        refresh();

        return v;
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GET_WITHDRAWAL_LIST:
                refreshCallback(response);
                break;
            case HTTP_GET_WITHDRAWAL_DELETE:
                HttpResultManager.resolve(response,
                        ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
                            @Override
                            public void onSuccess(ResponseCommonModel model) {
                                //adapter.data.clear();
                                //refresh();
                                Utils.toastUtil.showToast(getActivity(), model.message);
                                adapter.data.remove(mPosition);
                                if (adapter.data.size() == 0) {
                                    mRecyclerView.showEmptyView();
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }, true);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private void refreshCallback(String response) {
        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model model) {

                try {
                    for (int i = 0; i < model.data.account_list.size(); i++) {
                        WithdrawalList withdrawalList = model.data.account_list.get(i);
                        if (!TextUtils.isEmpty(withdrawalList.id) && Integer.parseInt(withdrawalList.id) < 0) {
                            model.data.account_list.remove(i--);
                        }
                    }
                } catch (Exception e) {
                }

                if (model.data.account_list.size() <= 1) {
                    mRecyclerView.showEmptyView();
                } else {
                    model.data.account_list.remove(0);
                    adapter.setData(model.data.account_list);
                }
            }

            @Override
            public void onFailure(String message) {
                mRecyclerView.showEmptyView();
            }
        });
        adapter.notifyDataSetChanged();
        mPullableLayout.topComponent.finish(Result.SUCCEED);
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_DEPOSIT_ADD, HttpWhat
                .HTTP_GET_WITHDRAWAL_LIST.getValue());
        addRequest(request);
    }

    @Override
    public void onConfirmDialogConfirmed(int viewType, int position, int extraInfo) {
        switch (ViewType.valueOf(viewType)) {
            case VIEW_TYPE_CLEAR_CONFIRM:
                String id = adapter.data.get(mPosition).id;
                CommonRequest request = new CommonRequest(Api.API_USER_DEPOSIT_ACCOUNT_DELETE, HttpWhat
                        .HTTP_GET_WITHDRAWAL_DELETE.getValue(), RequestMethod.POST);
                request.add("id", id);
                addRequest(request);
                break;
        }
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
