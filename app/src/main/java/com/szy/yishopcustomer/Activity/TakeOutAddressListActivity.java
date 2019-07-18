package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.szy.common.Other.CommonEvent;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.AdsListAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.Address.AddressList;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.ListItemDecoration;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 外卖 收货地址列表 上限10条
 * @time 2018 16:28
 */

public class TakeOutAddressListActivity extends Activity {

    @BindView(R.id.toolbar_adlist)
    Toolbar mToolbar;
    @BindView(R.id.tv_adlist_title)
    TextView mTextView_Title;
    @BindView(R.id.recy_adlist)
    CommonRecyclerView mRecyclerView_Adlist;
    @BindView(R.id.frame_new_ads)
    FrameLayout mLayout_NewAddress;

    private List<AddressList> mAddressLists = new ArrayList<>();
    private AdsListAdapter mAdapter;

    private Intent mIntent;
    private int mAdsType = 2;

    private int shopId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresslist);
        ButterKnife.bind(this);

        shopId = getIntent().getIntExtra(Key.ADS_SHOPID.getValue(), 0);

        mTextView_Title.setText(R.string.city_address_list);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRecyclerView_Adlist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView_Adlist.addItemDecoration(ListItemDecoration.createVertical(this, getResources().getColor(R.color.ads_itemd_bg), 22));

        mAdapter = new AdsListAdapter(this);
        mRecyclerView_Adlist.setAdapter(mAdapter);

        EventBus.getDefault().register(this);

        mAdapter.setOnItemClick(new AdsListAdapter.onItemClick() {

            /***
             * 返回地址数据到H5页面
             * @param addressList
             */
            @Override
            public void onAdsNormal(AddressList addressList) {

                EventBus.getDefault().post(new CommonEvent(EventWhat.SAMECITY_ADDRESS_INFO.getValue()));
                App.getInstance().ads_info=addressList.addressDetail;
                App.getInstance().ads_id=addressList.addressId;
                App.getInstance().ads_name=addressList.consignee;
                App.getInstance().ads_phone=addressList.mobile;

                finish();
            }

            @Override
            public void onAdsOutRange(AddressList addressList) {

                final int addressId = addressList.addressId;

                final AlertDialog.Builder builder = new AlertDialog.Builder(TakeOutAddressListActivity.this);
                builder.setTitle("该地址不在商家配送范围内,请修改地址");
                builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mIntent = new Intent(TakeOutAddressListActivity.this, AddressActivity.class);
                        mIntent.putExtra(Key.KEY_ADDRESS_ID.getValue(), addressId + "");
                        mIntent.putExtra(Key.KEY_ADDRESS_TYPE.getValue(), mAdsType);
                        startActivity(mIntent);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }

            /***
             * 编辑地址
             * @param addressList
             */
            @Override
            public void onAdsEdt(AddressList addressList) {
                mIntent = new Intent(TakeOutAddressListActivity.this, AddressActivity.class);
                mIntent.putExtra(Key.KEY_ADDRESS_ID.getValue(), addressList.addressId + "");
                mIntent.putExtra(Key.KEY_ADDRESS_TYPE.getValue(), mAdsType);
                startActivity(mIntent);
            }
        });

        mLayout_NewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mIntent = new Intent(TakeOutAddressListActivity.this, AddressActivity.class);
                mIntent.putExtra(Key.KEY_ADDRESS_TYPE.getValue(), mAdsType);
                startActivity(mIntent);
            }
        });
    }

    @Subscribe
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_LOGIN:

                break;
        }
    }

    private void getListData(int shopId) {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_CITY_ADDRESS_LIST, RequestMethod.GET);
        RequestAddHead.addNoHttpHead(request, this, Api.API_CITY_ADDRESS_LIST, "GET");

        request.add("shopId", shopId);
        requestQueue.add(HttpWhat.HTTP_ADDRESS_LIST.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                if (response.responseCode() == Utils.CITY_NET_SUCES) {

                    mAddressLists = JSONObject.parseArray(response.get(), AddressList.class);
                    mAdapter.mAddressLists = mAddressLists;
                    mAdapter.notifyDataSetChanged();

                    if (mAdapter.mAddressLists.size() == 0) {

                        mRecyclerView_Adlist.setEmptyImage(R.mipmap.bg_public);
                        mRecyclerView_Adlist.setEmptyTitle(R.string.address_empty_title);
                        mRecyclerView_Adlist.setEmptySubtitle(R.string.address_empty_content);
                        mRecyclerView_Adlist.showEmptyView();

                        mLayout_NewAddress.setVisibility(View.VISIBLE);
                    } else {
                        mRecyclerView_Adlist.hideEmptyView();

                        if (mAdapter.mAddressLists.size() < 20) {
                            mLayout_NewAddress.setVisibility(View.VISIBLE);
                        }
                    }
                } else if (response.responseCode() == Utils.CITY_NET_LOGIN) {

                    Toast.makeText(TakeOutAddressListActivity.this, R.string.loginout_hint, Toast.LENGTH_SHORT).show();
                    logOut();
                } else {
                    Toast.makeText(TakeOutAddressListActivity.this, "数据异常,请稍候尝试", Toast.LENGTH_SHORT).show();
                    mRecyclerView_Adlist.showOfflineView();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                mRecyclerView_Adlist.showOfflineView();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    public void logOut() {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_SIGN_OFF, RequestMethod.POST);

        requestQueue.add(106, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                App.loginOut(response.get(), TakeOutAddressListActivity.this);
                startActivity(new Intent(TakeOutAddressListActivity.this, LoginActivity.class));
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                startActivity(new Intent(TakeOutAddressListActivity.this, LoginActivity.class));
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (App.getInstance().isLogin()) {
            getListData(shopId);
        } else {
            Toast.makeText(this, R.string.loginout_hint, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(TakeOutAddressListActivity.this, LoginActivity.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
