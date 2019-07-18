package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.SendRecordAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.IngotList.DetailModel;
import com.szy.yishopcustomer.ResponseModel.IngotList.IngotShareRecordModel;
import com.szy.yishopcustomer.Util.ListItemDecoration;
import com.szy.yishopcustomer.Util.NoDoubleClickListener;
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
 * @role 元宝赠送(分享)记录
 * @time 2018 2018/7/11 16:08
 */

public class SendRecordActivity extends Activity {

    @BindView(R.id.toolbar_ingot_send_record)
    Toolbar mToolbar;
    @BindView(R.id.ingot_send_record_title)
    TextView mTextView_Title;

    @BindView(R.id.tv_ingot_send_number)
    TextView mTextView_SendNumber;

    @BindView(R.id.recy_ingot_send_record)
    CommonRecyclerView mRecyclerView;

    SendRecordAdapter mAdapter;
    public String mUserHeadImg, mGuid, mCode;

    private List<DetailModel> mDetailModels = new ArrayList<>();
    private List<IngotShareRecordModel> mRecordModels = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingot_send_record);
        ButterKnife.bind(this);

        mToolbar.setNavigationOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                finish();
            }
        });

        if (getIntent().getIntExtra(IngotSendActivity.SEND_INGOT_TYOE, 0) == 1) {
            mTextView_Title.setText("账号赠送元宝记录");
            getSendReData();
        } else {
            mTextView_Title.setText("微信赠送元宝记录");
            getShareReData();
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(ListItemDecoration.createVertical(this, getResources().getColor(R.color.ads_itemd_bg), 12));

        mAdapter = new SendRecordAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    /****
     * 获取赠送记录
     */
    private void getSendReData() {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_INGOT_DETAIL, RequestMethod.GET);
        request.add("type", 2);
        request.add("cur_page", 1);
        request.add("page_size", 20);

        requestQueue.add(HttpWhat.HTTP_INGOT_DETAIL.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                int code = JSONObject.parseObject(response.get()).getInteger("code");
                String message = JSONObject.parseObject(response.get()).getString("message");

                if (code == 0) {
                    JSONObject object = JSON.parseObject(response.get()).getJSONObject("data");
                    String total = object.getString("total_giving_integral");

                    mDetailModels = JSON.parseArray(object.getString("list"), DetailModel.class);
                    if (mDetailModels.size() > 0) {
                        mAdapter.mList.addAll(mDetailModels);
                    } else {
                        mAdapter.mList.clear();

                        mRecyclerView.showEmptyView();
                        mRecyclerView.setEmptyImage(R.mipmap.bg_public);
                        mRecyclerView.setEmptyTitle(R.string.near_data_empty);
                    }

                    mAdapter.notifyDataSetChanged();

                    mTextView_SendNumber.setText("累计赠送" + total + "元宝");
                } else {
                    Toast.makeText(SendRecordActivity.this, message, Toast.LENGTH_SHORT).show();
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

    private void getShareReData() {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_INGOT_SHARE_RECORD, RequestMethod.GET);
        request.add("cur_page", 1);
        request.add("page_size", 20);

        requestQueue.add(HttpWhat.HTTP_INGOT_RECORD.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                int code = JSONObject.parseObject(response.get()).getInteger("code");
                String message = JSONObject.parseObject(response.get()).getString("message");

                if (code == 0) {

                    JSONObject object = JSON.parseObject(response.get()).getJSONObject("data");
                    String total = object.getString("total_count");
                    mUserHeadImg = object.getString("headimg");
                    mGuid = object.getString("guid");
                    mCode = object.getString("invite_code");

                    mRecordModels = JSON.parseArray(object.getString("list"), IngotShareRecordModel.class);
                    mAdapter.mHeadImg = mUserHeadImg;
                    mAdapter.mGuid = mGuid;
                    mAdapter.mInviteCode = mCode;

                    if (mRecordModels.size() > 0) {
                        mAdapter.mList.addAll(mRecordModels);

                    } else {
                        mAdapter.mList.clear();

                        mRecyclerView.showEmptyView();
                        mRecyclerView.setEmptyImage(R.mipmap.bg_public);
                        mRecyclerView.setEmptyTitle(R.string.near_data_empty);
                    }
                    mAdapter.notifyDataSetChanged();

                    mTextView_SendNumber.setText("累计赠送" + total + "次");

                } else {
                    Toast.makeText(SendRecordActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Toast.makeText(SendRecordActivity.this, "数据异常,请稍候尝试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (RequestCode.valueOf(requestCode)) {
            case REQUEST_CODE_WEIXIN_SHARE:
            case REQUEST_CODE_WEIXIN_CIRCLE_SHARE:
                shareCallback(resultCode, data);
                break;
        }
    }

    private void shareCallback(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (data.getIntExtra(Key.KEY_SHARE.getValue(), Macro.SHARE_FAILED)) {
                case Macro.SHARE_SUCCEED:
                    Toast.makeText(this, R.string.shareSucceed, Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case Macro.SHARE_FAILED:
                    Toast.makeText(this, R.string.shareFailed, Toast.LENGTH_SHORT).show();
                    break;
                case Macro.SHARE_CANCELED:
                    Toast.makeText(this, R.string.shareCanceled, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    mAdapter.mShareDialog.dismiss();
                    break;
            }
        } else {
            Toast.makeText(this, R.string.shareCanceled, Toast.LENGTH_SHORT).show();
        }
    }
}
