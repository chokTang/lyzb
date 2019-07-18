package com.szy.yishopcustomer.Activity.samecity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.szy.common.View.CommonEditText;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.NearListAdapter;
import com.szy.yishopcustomer.Adapter.samecity.SeachHisAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.Near.NearListModel;
import com.szy.yishopcustomer.ResponseModel.SameCity.SeachHisModel;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 分类页面的搜索页面  ac
 * @time 2018 2018/8/13 9:43
 */

public class SortSeachActivity extends Activity {

    /***
     * 搜索类型ID
     */
    public static String SEACH_TYPE_ID = "SeachTypeId";
    private int seachTypeId;

    @BindView(R.id.img_sort_seach_back)
    ImageView mImageView_Back;
    @BindView(R.id.edt_sort_seach_content)
    CommonEditText mEditText_Seach;
    @BindView(R.id.tv_sort_seach_text)
    TextView mTextView_Seach;
    @BindView(R.id.recy_sort_seach)
    CommonRecyclerView mRecyclerView_List;

    @BindView(R.id.lin_sort_seach_his)
    LinearLayout mLinearLayout_His;
    @BindView(R.id.recy_sort_seach_his)
    CommonRecyclerView mRecyclerView_His_List;
    @BindView(R.id.tv_delete_sort_his)
    TextView mTextView_Delete_His;

    private String shopNmae = null;
    private String currentText = null;
    private List<NearListModel> mListModels = new ArrayList<>();

    private SeachHisAdapter mHisAdapter;

    private NearListAdapter mListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_seach);
        ButterKnife.bind(this);

        seachTypeId = getIntent().getIntExtra(SEACH_TYPE_ID, 0);
        mImageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTextView_Seach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEditText_Seach.getText().toString().trim())) {
                    Toast.makeText(SortSeachActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                } else {
                    shopNmae = mEditText_Seach.getText().toString().trim();

                    if (currentText == null) {
                        getSeachResult(false);
                    } else {
                        if (currentText.equals(shopNmae)) {
                            Toast.makeText(SortSeachActivity.this, "搜索条件未发生变化", Toast.LENGTH_SHORT).show();
                        } else {
                            getSeachResult(false);
                        }
                    }
                }
            }
        });

        //搜索历史recy
        mRecyclerView_His_List.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView_His_List.addItemDecoration(ListItemDecoration.createVertical(this, Color.GRAY, 1));
        mHisAdapter = new SeachHisAdapter(this);
        mRecyclerView_His_List.setAdapter(mHisAdapter);

        //搜索结果recy
        mRecyclerView_List.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView_List.addItemDecoration(ListItemDecoration.createVertical(this, Color.GRAY, 1));
        mListAdapter = new NearListAdapter(this);
        mRecyclerView_List.setAdapter(mListAdapter);

        if (App.getInstance().mHisModels.size() > 0) {
            mLinearLayout_His.setVisibility(View.VISIBLE);
            mHisAdapter.data.addAll(App.getInstance().mHisModels);
            mHisAdapter.notifyDataSetChanged();
        } else {
            mLinearLayout_His.setVisibility(View.GONE);
        }

        mTextView_Delete_His.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getInstance().mHisModels.clear();
                mLinearLayout_His.setVisibility(View.GONE);
            }
        });

        //搜索历史 item 跳转
        mHisAdapter.setOnItemClick(new SeachHisAdapter.onItemClick() {
            @Override
            public void onClick(String name) {
                shopNmae = name;
                getSeachResult(true);
                mLinearLayout_His.setVisibility(View.GONE);
            }
        });
    }

    /****
     * 输入结果搜索or历史文本搜索
     * @param isHis true 为历史文本搜索-结果
     */
    private void getSeachResult(final boolean isHis) {
        mListModels.clear();

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_SEACH_SHOP_URL, RequestMethod.GET);
        RequestAddHead.addNoHttpHead(request, this, Api.API_SEACH_SHOP_URL, "GET");

        request.add("classId", seachTypeId);
        if (App.getInstance().lat != null) {
            request.add("latitude", App.getInstance().lat);
            request.add("longitude", App.getInstance().lng);
        } else if (App.getInstance().city_code != null) {
            request.add("regionCode", App.getInstance().city_code);
        }
        request.add("pageNum", 1);
        request.add("shopName", shopNmae);

        currentText = shopNmae;

        requestQueue.add(HttpWhat.HTTP_SEACH_SHOP.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                if (response.responseCode() == Utils.CITY_NET_SUCES) {

                    JSONObject jsonObject = JSONObject.parseObject(response.get());
                    mListModels = JSON.parseArray(jsonObject.getString("list"), NearListModel.class);
                    if (mListModels.size() > 0) {
                        mListAdapter.setData(mListModels);
                        mListAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(SortSeachActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SortSeachActivity.this, R.string.data_error, Toast.LENGTH_SHORT).show();
                }

                if (isHis == false) {

                    //搜索内容文本存入model
                    if (App.getInstance().mHisModels.size() == 0) {
                        SeachHisModel model = new SeachHisModel();
                        model.his_name = shopNmae;
                        App.getInstance().mHisModels.add(model);
                    } else {

                        for (int i = 0; i < App.getInstance().mHisModels.size(); i++) {
                            //重复搜索内容 不添加
                            if (App.getInstance().mHisModels.get(i).his_name.equals(shopNmae)) {
                                return;
                            } else {

                                SeachHisModel model = new SeachHisModel();
                                model.his_name = shopNmae;
                                App.getInstance().mHisModels.add(model);
                            }
                        }
                    }

                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Toast.makeText(SortSeachActivity.this, R.string.data_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
}
