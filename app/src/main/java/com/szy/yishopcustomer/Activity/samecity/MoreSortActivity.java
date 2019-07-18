package com.szy.yishopcustomer.Activity.samecity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.samecity.MoreSortAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.MoreSort.MoreModel;
import com.szy.yishopcustomer.Util.RequestAddHead;
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
 * @role 更多分类 ac
 * @time 2018 2018/8/6 11:38
 */

public class MoreSortActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.img_more_sort_back)
    ImageView mImageView_Back;
    @BindView(R.id.tv_more_sort_seach)
    TextView mTextView_Seach;
    @BindView(R.id.recy_more_sort)
    CommonRecyclerView mRecyclerView;

    private int mPage = 1;
    private List<MoreModel> mMoreModelList = new ArrayList<>();
    private MoreSortAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_sort);
        ButterKnife.bind(this);

        mImageView_Back.setOnClickListener(this);
        mTextView_Seach.setOnClickListener(this);

        mAdapter = new MoreSortAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);

        getMoreData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_more_sort_back:
                finish();
                break;
            case R.id.tv_more_sort_seach:

                break;
        }
    }

    private void getMoreData() {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_MORE_SORT_URL, RequestMethod.GET);
        RequestAddHead.addNoHttpHead(request, this, Api.API_MORE_SORT_URL, "GET");
        request.add("pageNum", mPage);
        request.add("pageSize", 10);

        requestQueue.add(HttpWhat.HTTP_MORE_SORT.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                if (response.responseCode() == 200) {

                    String data = JSON.parseObject(response.get()).getString("list");
                    mMoreModelList = JSON.parseArray(data, MoreModel.class);

                    mAdapter.data.addAll(mMoreModelList);
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MoreSortActivity.this, R.string.data_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Toast.makeText(MoreSortActivity.this, R.string.data_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
}
