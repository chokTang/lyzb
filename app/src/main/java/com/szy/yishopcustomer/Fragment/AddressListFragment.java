package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.AddressActivity;
import com.szy.yishopcustomer.Activity.AddressListActivity;
import com.szy.yishopcustomer.Adapter.AddressListAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AddressList.AddressItemModel;
import com.szy.yishopcustomer.ResponseModel.AddressList.Model;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * Created by 宗仁 on 16/4/26.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 * 收货地址列表
 */
public class AddressListFragment extends YSCBaseFragment implements OnPullListener,
        OnEmptyViewClickListener {

    private static final int DELETE_ADDRESS = 100;

    @BindView(R.id.fragment_address_list_pullableLayout)
    PullableLayout mPullableLayout;
    @BindView(R.id.fragment_address_list_pullableRecyclerView)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.bottom_button)
    TextView mAddButton;

    private int mCurrentType = AddressListActivity.TYPE_SELECT;
    private LinearLayoutManager mLayoutManager;
    private AddressListAdapter mAdapter;
    private Model mModel;
    private AlertDialog mAlertDialog;
    private String mAddressId;
    private String mSelectedAddressId;

    private List<AddressItemModel> mList = new ArrayList<>();
    private List<AddressItemModel> mList_more = new ArrayList<>();

    private int data_page = 1;

    private boolean isShow = false;//是否显示地址编辑
    private boolean isMore = true;//是否加载更多 默认加载更多
    private int data_count = 0;//数据源-总数

    public void changeType(int type) {
        mCurrentType = type;
        setUpAdapterData();
    }

    @Override
    public void onCanceled(PullableComponent pullableComponent) {
        mRequestQueue.cancelAll();
    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {

        if (isMore) {
            data_page++;
            refresh();
        } else {
            mPullableLayout.bottomComponent.finish(Result.SUCCEED);
            Toast.makeText(getActivity(), "暂无更多数据", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }

    @Override
    public void onConfirmDialogConfirmed(int viewType, int position, int extraInfo) {
        switch (ViewType.valueOf(viewType)) {
            case VIEW_TYPE_CLEAR_CONFIRM:
                deleteAddressAction(mAddressId);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);

        switch (viewType) {
            case VIEW_TYPE_SET_DEFAULT:
                AddressItemModel addressItemModel = getAddressItemModel(position);
                if (addressItemModel.is_default == 1) {
                    return;
                }
                setDefault(getAddressId(position));
                break;
            case VIEW_TYPE_DELETE:
                showConfirmDialog(R.string.areYouSureToDeleteTheAddress, ViewType.VIEW_TYPE_CLEAR_CONFIRM
                        .ordinal());
                mAddressId = getAddressId(position);
                break;
            case VIEW_TYPE_EDIT:
                editAddress(getAddressId(position));
                break;
            case VIEW_TYPE_SELECT:
                selectAddress(getAddressId(position));
                break;
            case VIEW_TYPE_ADD:
                addAddress();
                break;
            default:
                super.onClick(view);
                break;
        }
    }


    public void setIsShow(Boolean isShow){
        this.isShow = isShow;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new ItemDecoration());
        Utils.setViewTypeForTag(mAddButton, ViewType.VIEW_TYPE_ADD);
        mAddButton.setOnClickListener(this);

        //加载更多 监听
        mPullableLayout.bottomComponent.setOnPullListener(this);

        mAddButton.setText(getResources().getString(R.string.buttonAddAddress));
        refresh();

        return view;
    }

    @Override
    public void onEmptyViewClicked() {
        refresh();
    }

    @Subscribe
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_ADD_ADDRESS:
            case EVENT_DELETE_ADDRESS:
            case EVENT_UPDATE_ADDRESS:
                refresh();
                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        mPullableLayout.topComponent.finish(Result.FAILED);
        mRecyclerView.showOfflineView();
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_USER_ADDRESS:
                refreshCallback(response);
                break;
            case HTTP_SET_DEFAULT_ADDRESS:
                setDefaultCallback(response);
                break;
            case HTTP_DELETE:
                deleteAddressCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_address_list;
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new AddressListAdapter();
        mAdapter.setShowEdit(isShow);
        Bundle arguments = getArguments();

        if (arguments != null) {
            mCurrentType = arguments.getInt(Key.KEY_ADDRESS_LIST_CURRENT_TYPE.getValue(), AddressListActivity.TYPE_SELECT);
        }

        mAdapter.onClickListener = this;
    }

    private void addAddress() {
        Intent intent = new Intent(getContext(), AddressActivity.class);
        startActivity(intent);
    }

    private void deleteAddressAction(String addressId) {
        CommonRequest request = new CommonRequest(Api.API_DELETE_ADDRESS, HttpWhat
                .HTTP_DELETE.getValue());
        request.setAjax(true);
        request.add("address_id", addressId);
        addRequest(request);
    }

    private void deleteAddressCallback(String response) {
        HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
            @Override
            public void onSuccess(ResponseCommonModel back) {
                Toast.makeText(getContext(), back.message, Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_DELETE_ADDRESS.getValue()));
            }
        }, true);
    }

    private void editAddress(String addressId) {
        Intent intent = new Intent(getContext(), AddressActivity.class);
        intent.putExtra(Key.KEY_ADDRESS_ID.getValue(), addressId);
        startActivity(intent);
    }

    private String getAddressId(int position) {
        return mAdapter.getModel(position).address_id;
    }

    private AddressItemModel getAddressItemModel(int position) {
        return mAdapter.getModel(position);
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_ADDRESS_LIST, HttpWhat.HTTP_USER_ADDRESS.getValue());
        request.add("page[cur_page]", data_page);
        addRequest(request);
    }

    private void refreshCallback(String response) {

        mModel = JSON.parseObject(response, Model.class);

        if (data_page == 1) {

            mAdapter.data.clear();

            if (mModel.data.list == null) {
                mRecyclerView.showEmptyView();
                mAddButton.setVisibility(View.VISIBLE);
            } else {
                mAdapter.data = mModel.data.list;
                mAdapter.notifyDataSetChanged();
            }

        } else {

            if (data_page == mModel.data.page.cur_page) {

                mAdapter.data.addAll(mModel.data.list);
                mAdapter.notifyDataSetChanged();
            } else {
                isMore = false;
                Toast.makeText(getActivity(), "暂无更多数据", Toast.LENGTH_SHORT).show();
            }
            mPullableLayout.bottomComponent.finish(Result.SUCCEED);
        }

    }

    private void selectAddress(String addressId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_ADDRESS_ID.getValue(), addressId);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void setDefault(String addressId) {
        CommonRequest request = new CommonRequest(Api.API_SET_DEFAULT_ADDRESS, HttpWhat.HTTP_SET_DEFAULT_ADDRESS.getValue(), RequestMethod.POST);
        request.setAjax(true);
        request.add("address_id", addressId);
        addRequest(request);
    }

    private void setDefaultCallback(String response) {
        HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
            @Override
            public void onSuccess(ResponseCommonModel back) {
                Toast.makeText(getContext(), back.message, Toast.LENGTH_SHORT).show();
                refresh();
            }
        }, true);
    }

    private void setUpAdapterData() {

        for (AddressItemModel addressItemModel : mAdapter.data) {
            addressItemModel.editing = mCurrentType == AddressListActivity.TYPE_EDIT;
        }
        mAdapter.notifyDataSetChanged();
    }

    private class ItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView
                .State state) {
            outRect.top = 0;
            outRect.left = 0;
            outRect.bottom = Utils.getPixel(getContext(), 10);
            outRect.right = 0;
        }
    }
}
