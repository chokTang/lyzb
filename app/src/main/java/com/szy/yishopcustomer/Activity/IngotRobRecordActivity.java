package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.IngotRobAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.IngotList.IngotRobRecordModel;
import com.szy.yishopcustomer.Util.ListItemDecoration;
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
 * @role (分享元宝记录item)抢元宝记录
 * @time 2018 2018/7/13 9:42
 */

public class IngotRobRecordActivity extends Activity {

    public static String KEY_SHAREID = "SHARE_ID";

    @BindView(R.id.toolbar_ingot_rob)
    Toolbar mToolbar;

    @BindView(R.id.tv_ingot_rob_number)
    TextView mTextView_Number;
    @BindView(R.id.recy_ingot_rob_record)
    CommonRecyclerView mRecyclerView;

    private String mShareId;
    private IngotRobAdapter mAdapter;

    private List<IngotRobRecordModel> mModels_One = new ArrayList<>();

    private List<IngotRobRecordModel> mModels = new ArrayList<>();
    private IngotTotal mIngotTotal = new IngotTotal();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingot_rob_record);
        ButterKnife.bind(this);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(ListItemDecoration.createVertical(this, getResources().getColor(R.color.ads_itemd_bg), 15));

        mAdapter = new IngotRobAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mShareId = getIntent().getStringExtra(KEY_SHAREID);

        getRobData();
    }

    private void getRobData() {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_INGOT_ROB_RECORD, RequestMethod.GET);
        request.add("share_id", mShareId);
        request.add("cur_page", 1);
        request.add("page_size", 20);

        requestQueue.add(HttpWhat.HTTP_INGOT_ROB.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                if(TextUtils.isEmpty(response.get())){
                    Toast.makeText(IngotRobRecordActivity.this, "数据异常,请稍候尝试", Toast.LENGTH_SHORT).show();
                }else{
                    int code = JSONObject.parseObject(response.get()).getInteger("code");
                    String message = JSONObject.parseObject(response.get()).getString("message");

                    if (code == 0) {
                        JSONObject object = JSON.parseObject(response.get()).getJSONObject("data");
                        JSONObject total_object = JSON.parseObject(object.getString("title_count"));

                        mIngotTotal.integral_total = total_object.getInteger("integral_total");
                        mIngotTotal.integral_surplus = total_object.getInteger("integral_surplus");
                        mIngotTotal.num_total = total_object.getInteger("num_total");
                        mIngotTotal.num_surplus = total_object.getInteger("num_surplus");

                        mTextView_Number.setText("已领取" + (mIngotTotal.num_total - mIngotTotal.num_surplus) + "/"
                                + mIngotTotal.num_total + "个,共" + (mIngotTotal.integral_total - mIngotTotal.integral_surplus) + "/" + mIngotTotal.integral_total + "个元宝");

                        mModels = JSON.parseArray(object.getString("list"), IngotRobRecordModel.class);

                        mAdapter.mList.addAll(mModels);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(IngotRobRecordActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                mRecyclerView.showOfflineView();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    public class IngotTotal {

        public int integral_total;//分享元宝总数量

        public int integral_surplus;//分享元宝剩余数量

        public int num_total;//分享元宝总份数

        public int num_surplus;//分享元宝剩余份数
    }
}