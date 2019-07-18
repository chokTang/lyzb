package com.szy.yishopcustomer.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.LinearLayout;

import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.MapActivity;
import com.szy.yishopcustomer.Adapter.PickUpListAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.BaseEntity;
import com.szy.yishopcustomer.ResponseModel.Goods.PickUpModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.PickUpListModel;
import com.yolanda.nohttp.RequestMethod;

import java.util.List;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;


public class PickUpListFragment extends YSCBaseFragment implements OnPullListener {

    private static final int HTTP_WHAT_GOODS_SEARCH_PICKUP = 1;

    @BindView(R.id.fragment_pullableLayout)
    PullableLayout fragment_pullableLayout;
    @BindView(R.id.fragment_recyclerView)
    CommonRecyclerView fragment_recyclerView;

    @BindView(R.id.relativeLayout_empty)
    LinearLayout relativeLayout_empty;

    private PickUpListAdapter mAdapter;
    private PickUpListModel data;

    private String keyword;

    private List<PickUpModel> mPickUpList;
    private String shop_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_pick_up_list;

        Bundle arguments = getArguments();
        mPickUpList = arguments.getParcelableArrayList(Key.KEY_PICKUP_LIST.getValue());
        shop_id = arguments.getString("shop_id");

        mAdapter = new PickUpListAdapter();
        mAdapter.data.addAll(mPickUpList);
        mAdapter.onClickListener = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        fragment_recyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragment_recyclerView.setLayoutManager(layoutManager);
        fragment_pullableLayout.topComponent.setOnPullListener(this);

        return view;
    }

    @Override
    public void onCanceled(PullableComponent pullableComponent) {

    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        if (pullableComponent.getSide().toString().equals("TOP")) {
            refresh();
        }
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }


    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_GOODS_SEARCH_PICKUP, HTTP_WHAT_GOODS_SEARCH_PICKUP, RequestMethod.POST);
        request.add("keyword", keyword);
        request.add("shop_id", shop_id);
        addRequest(request);
    }

    //接口成功回调
    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_GOODS_SEARCH_PICKUP:
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

        HttpResultManager.resolve(response, BaseEntity.class, new HttpResultManager.HttpResultCallBack<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity back) {

                PickUpListModel pickUpListModel = JSON.parseObject(back.getData(),PickUpListModel.class);

                data = pickUpListModel;

                mAdapter.data.clear();

                if (pickUpListModel.data.pickup.size() > 0) {
                    relativeLayout_empty.setVisibility(View.GONE);
                    mAdapter.data.addAll(pickUpListModel.data.pickup);
                } else {
                    relativeLayout_empty.setVisibility(View.VISIBLE);
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                relativeLayout_empty.setVisibility(View.VISIBLE);
            }
        },true);
    }

    public void search(String mkeyword) {
        keyword = mkeyword;
        refresh();
    }


    @Override
    public void onClick(View v) {
        if (Utils.isDoubleClick()) {
            return;
        }
        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_PHONE:
                Utils.openPhoneDialog(getContext(), mAdapter.data.get(position).pickup_tel);
                break;
            case VIEW_TYPE_ITEM:
                openMap(position);
                break;
            default:
                super.onClick(v);
        }
    }

    private void openMap(int position) {
        PickUpModel pickUpModel = mAdapter.data.get(position);
        Intent intent = new Intent();
        intent.setClass(getActivity(), MapActivity.class);

        intent.putExtra(Key.KEY_MARKER_NAME.getValue(), pickUpModel.pickup_name);
        intent.putExtra(Key.KEY_MARKER_SNIPPET.getValue(), pickUpModel.pickup_desc);
        intent.putExtra(Key.KEY_LATITUDE.getValue(), pickUpModel.address_lat);
        intent.putExtra(Key.KEY_LONGITUDE.getValue(), pickUpModel.address_lng);
        intent.putExtra(Key.KEY_LATITUDE_ME.getValue(), App.getInstance().lat);
        intent.putExtra(Key.KEY_LONGITUDE_ME.getValue(), App.getInstance().lng);
        startActivity(intent);
    }
}
