package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Adapter.ShopStreetCateAdapter;
import com.szy.yishopcustomer.Adapter.ShopStreetCateTwoAdapter;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.ShopStreet.Model;
import com.szy.yishopcustomer.ResponseModel.ShopStreet.ShopStreetCateOneItemModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.JSON;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.List;

/**
 * Created by buqingqiang on 2016/6/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopStreetCateActivity extends Activity {
    private static final String TAG = "ShopStreetCateActivity";

    protected RequestQueue mRequestQueue;
    protected ProgressDialog mWaitDialog;
    private ShopStreetCateAdapter mShopStreetCateAdapter;
    private ShopStreetCateTwoAdapter mShopStreetCateTwoAdapter;
    private ListView mShopStreetCateListView;
    private ListView mShopStreetCateTwoListView;
    private TextView mShopStreetCateBackTextView;
    private String mCateId;
    private String mCateIdIntent;
    private OnResponseListener<String> mRequestListener = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            mWaitDialog = new ProgressDialog(ShopStreetCateActivity.this);
            mWaitDialog.setMessage("加载中...");
            mWaitDialog.show();
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            onRequestSucceed(what, response.get());
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            onRequestFailed(what, response.get());
        }

        @Override
        public void onFinish(int what) {
            mWaitDialog.dismiss();
            onRequestFinish(what);
        }
    };

    public void addRequest(CommonRequest Request) {
        mRequestQueue.cancelBySign(Request.getWhat());
        mRequestQueue.add(Request.getWhat(), Request, mRequestListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_stret_cate);
        mShopStreetCateListView = (ListView) findViewById(
                R.id.activity_shop_street_one_cate_listView);
        mShopStreetCateTwoListView = (ListView) findViewById(
                R.id.activity_shop_street_two_cate_listView);
        mShopStreetCateBackTextView = (TextView) findViewById(
                R.id.activity_shop_street_back_textView);
        mRequestQueue = NoHttp.newRequestQueue(1);
        mShopStreetCateBackTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mShopStreetCateAdapter = new ShopStreetCateAdapter();
        mShopStreetCateListView.setAdapter(mShopStreetCateAdapter);
        CommonRequest request = new CommonRequest(Config.BASE_URL + "/shop/street/index",
                HttpWhat.HTTP_GET_SHOP_STREET_CATE.getValue());
        addRequest(request);
    }

    protected void onRequestFailed(int what, String response) {
        switch (what) {
            default:
                Log.i(TAG, "Request failed,what is " + what + ",response is " + response);
                break;
        }
    }

    protected void onRequestFinish(int what) {
        switch (what) {
            default:
                Log.i(TAG, "Request finish,what is " + what);
                break;
        }
    }

    protected void onRequestStart(int what) {
        switch (what) {
            default:
                Log.i(TAG, "Request start,what is " + what);
                break;
        }
    }

    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GET_SHOP_STREET_CATE:
                HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
                    @Override
                    public void onSuccess(Model back) {
                        mShopStreetCateAdapter.setData(back.data.cls_list);
                        mShopStreetCateAdapter.notifyDataSetChanged();
                        mShopStreetCateListView.setOnItemClickListener(
                                new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        mShopStreetCateTwoAdapter = new ShopStreetCateTwoAdapter();
                                        mShopStreetCateTwoListView.setAdapter(mShopStreetCateTwoAdapter);
                                        CommonRequest request = new CommonRequest(
                                                Config.BASE_URL + "/site/cat-list",
                                                HttpWhat.HTTP_GET_SHOP_STREET_CATE_TWO.getValue());
                                        List<ShopStreetCateOneItemModel> shopStreet = mShopStreetCateAdapter.getData();
                                        mCateIdIntent = shopStreet.get(
                                                position).cls_level + "_" + shopStreet.get(
                                                position).cls_id + "_" + shopStreet.get(position).parent_id;
                                        mCateId = shopStreet.get(position).cls_id;
                                        request.add("id", mCateId);
                                        addRequest(request);
                                    }
                                });
                    }
                });


                break;
            case HTTP_GET_SHOP_STREET_CATE_TWO:
                HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
                    @Override
                    public void onSuccess(Model modelCate) {
                        if (modelCate.data.cat_list.size() != 0) {
                            mShopStreetCateTwoListView.setVisibility(View.VISIBLE);
                            mShopStreetCateTwoAdapter.setData(modelCate.data.cat_list);
                            mShopStreetCateTwoAdapter.notifyDataSetChanged();
                        } else {
                            mShopStreetCateTwoListView.setVisibility(View.GONE);
                   /* Intent intent = new Intent();
                    intent.putExtra("cls_id", mCateIdIntent);
                    intent.setClass(ShopStreetCateActivity.this, ShopStreetActivity.class);
                    startActivityForResult(intent);*/
                        }
                    }
                });

                break;

            default:
                Log.i(TAG, "Request succeed,what is " + what + ",response is " + response);
                break;
        }
    }

}
