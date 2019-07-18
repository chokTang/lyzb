package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.*;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.szy.common.Other.CommonEvent;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.LikeDataAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.GuessGoodsModel;
import com.szy.yishopcustomer.ResponseModel.LikeGood.UserIngotModel;
import com.szy.yishopcustomer.Util.App;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 注册成功 activity
 * @time 2018 10:43
 */

public class RegisterSuccessActivity extends Activity {

    @BindView(R.id.toolbar_register_success)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_register_success_textview)
    TextView mToolbar_TextTitle;

    @BindView(R.id.register_success_recyclerView)
    CommonRecyclerView mRecyclerView;

    private LikeDataAdapter mAdapter;
    private UserIngotModel mIngotModel = new UserIngotModel();

    private List<GuessGoodsModel> mModelList = new ArrayList<>();

    private int like_pager_number = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);
        ButterKnife.bind(this);

        if (!TextUtils.isEmpty(getIntent().getStringExtra("isLogin"))) {

            mIngotModel.isLogin = true;
            mIngotModel.usableIngot = getIntent().getStringExtra("ingotNumber");
        }

        if (!TextUtils.isEmpty(getIntent().getStringExtra("ingot_number"))) {
            mIngotModel.isLogin = false;
            mIngotModel.giveIngot = getIntent().getStringExtra("ingot_number");
            mIngotModel.usableIngot = getIntent().getStringExtra("ingot_total");
        }

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new LikeDataAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setHintModel(mIngotModel);

        getLikeData();

        mAdapter.setOnItemClick(new LikeDataAdapter.onItemClick() {
            @Override
            public void lookIngotClick() {
                startActivity(new Intent(RegisterSuccessActivity.this, IngotListActivity.class));
            }

            @Override
            public void goHomeClick() {
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_INDEX.getValue()));
                finish();
            }
        });
    }

    void getLikeData() {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest(Api.API_GUESS_LIKE_URL, RequestMethod.GET);
        request.setUserAgent("szyapp/android");
        if (App.getInstance().isLogin()) {
            request.add("user_id", App.getInstance().userId);
        } else {
            request.add("user_id", 0);
        }
        request.add("cur_page", 1);
        request.add("page_size", 24);
        requestQueue.add(1, request, new SimpleResponseListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                super.onSucceed(what, response);

                try {

                    if (JSON.parseObject(response.get()).getInteger("code") == 0) {
                        JSONObject object_data = JSONObject.parseObject(response.get()).getJSONObject("data");
                        mModelList = JSON.parseArray(object_data.getString("list"), GuessGoodsModel.class);
                        if (mModelList.size() > 0) {
                            mAdapter.setLikeData(mModelList);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                super.onFailed(what, response);
                mRecyclerView.showOfflineView();
            }
        });
    }
}
