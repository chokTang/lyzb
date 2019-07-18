package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.IngotDetailAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.IngotList.DetailModel;
import com.szy.yishopcustomer.ResponseModel.IngotList.OverdueIngotModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.ListItemDecoration;
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
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * @author wyx
 * @role 元宝明细  测试账号:15923886045
 * @time 2018 2018/7/4 10:05
 */

public class IngotDetailActivity extends Activity implements View.OnClickListener, OnPullListener {

    @BindView(R.id.toolbar_ingot_detail)
    Toolbar mToolbar;

    @BindView(R.id.tv_ingot_detail_income)
    TextView mTextView_Income;
    @BindView(R.id.view_ingot_income)
    View mView_Income;

    @BindView(R.id.tv_ingot_detail_expend)
    TextView mTextView_Expend;
    @BindView(R.id.view_ingot_expend)
    View mView_Expend;

    @BindView(R.id.tv_ingot_detail_overdue)
    TextView mTextView_Overdue;
    @BindView(R.id.view_ingot_overdue)
    View mView_Overdue;

    @BindView(R.id.ac_ingot_detail_pull_layout)
    PullableLayout mPullableLayout;
    @BindView(R.id.recy_ingot_detail)
    CommonRecyclerView mRecyclerView_Detail;

    private int DetailType = 0;
    private int DetailPage = 1;
    private int PageSize = 10;

    private boolean isMore = true;

    private IngotDetailAdapter mAdapter;

    private List<DetailModel> mDetailModels = new ArrayList<>();
    private List<DetailModel> mDetailModel_More = new ArrayList<>();

    private List<DetailModel> mExplModels = new ArrayList<>();
    private List<DetailModel> mExplModel_More = new ArrayList<>();

    private OverdueIngotModel mOverdueModels;

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingot_detail);
        ButterKnife.bind(this);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTextView_Income.setOnClickListener(this);
        mTextView_Expend.setOnClickListener(this);
        mTextView_Overdue.setOnClickListener(this);

        mPullableLayout.bottomComponent.setOnPullListener(this);

        mRecyclerView_Detail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView_Detail.addItemDecoration(ListItemDecoration.createVertical(this, getResources().getColor(R.color.ads_itemd_bg), 12));

        mAdapter = new IngotDetailAdapter(this);
        mRecyclerView_Detail.setAdapter(mAdapter);

        mRecyclerView_Detail.addOnScrollListener(mOnScrollListener);

        mRecyclerView_Detail.setEmptyViewClickListener(new OnEmptyViewClickListener() {
            @Override
            public void onEmptyViewClicked() {

            }

            @Override
            public void onOfflineViewClicked() {

                if (DetailType == 0 || DetailType == 1) {
                    getDetailData();
                } else {
                    getWillData();
                }
            }
        });

        selectTitle(mTextView_Income);
        getDetailData();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ingot_detail_income:
                selectTitle(mTextView_Income);

                DetailType = 0;
                isMore = true;

                if (mDetailModels.size() > 0) {
                    mAdapter.data.clear();

                    mAdapter.data.addAll(mDetailModels);
                    mAdapter.notifyDataSetChanged();
                } else {
                    DetailPage = 1;
                    getDetailData();
                }
                break;
            case R.id.tv_ingot_detail_expend:
                selectTitle(mTextView_Expend);

                DetailType = 1;
                isMore = true;

                if (mExplModels.size() > 0) {
                    mAdapter.data.clear();

                    mAdapter.data.addAll(mExplModels);
                    mAdapter.notifyDataSetChanged();
                } else {
                    DetailPage = 1;
                    getDetailData();
                }
                break;
            case R.id.tv_ingot_detail_overdue:
                selectTitle(mTextView_Overdue);


                if (mOverdueModels != null) {
                    mAdapter.data.clear();

                    mAdapter.data.add(mOverdueModels);
                    mAdapter.notifyDataSetChanged();
                } else {
                    getWillData();
                }
                break;
        }
    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {

        /** 加载更多数据 */
        if (isMore) {
            DetailPage++;
            getDetailData();
        } else {
            Toast.makeText(IngotDetailActivity.this, "暂无更多数据", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onCanceled(PullableComponent pullableComponent) {

    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }

    private void getDetailData() {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_INGOT_DETAIL, RequestMethod.GET);
        request.add("type", DetailType);
        request.add("cur_page", DetailPage);
        request.add("page_size", PageSize);

        requestQueue.add(HttpWhat.HTTP_INGOT_DETAIL.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                if (response.responseCode() == Utils.CITY_NET_SUCES) {
                    refreshDetailData(response.get());
                } else {
                    mRecyclerView_Detail.showOfflineView();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

                mRecyclerView_Detail.showOfflineView();
                if (DetailPage > 1) {
                    mPullableLayout.bottomComponent.finish(Result.FAILED);
                }
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    private void getWillData() {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_INGOT_EXPIRE, RequestMethod.GET);
        requestQueue.add(HttpWhat.HTTP_INGOT_EXPIRE.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                if (response.responseCode() == Utils.CITY_NET_SUCES) {
                    int dataCode = JSONObject.parseObject(response.get()).getInteger("code");
                    String dataMessage = JSONObject.parseObject(response.get()).getString("message");

                    if (dataCode == 0) {
                        mAdapter.data.clear();

                        String model = JSON.parseObject(response.get()).getString("data");
                        mOverdueModels = JSON.parseObject(model, OverdueIngotModel.class);
                        mAdapter.data.add(mOverdueModels);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        mRecyclerView_Detail.showOfflineView();
                        Toast.makeText(IngotDetailActivity.this, dataMessage, Toast.LENGTH_SHORT).show();
                    }
                } else {

                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void refreshDetailData(String response) {

        if (DetailPage == 1) {
            mAdapter.data.clear();
        }

        int dataCode = JSONObject.parseObject(response).getInteger("code");
        String dataMessage = JSONObject.parseObject(response).getString("message");
        if (dataCode == 0) {
            JSONObject object = JSONObject.parseObject(response).getJSONObject("data");
            if (DetailType == 0) {

                if (DetailPage > 1) {
                    mDetailModel_More = JSON.parseArray(object.getString("list"), DetailModel.class);

                    if (mDetailModel_More.size() > 0) {
                        mDetailModels.addAll(mDetailModel_More);
                        mAdapter.data.addAll(mDetailModels);
                    } else {
                        isMore = false;
                    }
                    mAdapter.notifyDataSetChanged();
                    mPullableLayout.bottomComponent.finish(Result.SUCCEED);

                } else {
                    mDetailModels = JSON.parseArray(object.getString("list"), DetailModel.class);

                    if (mDetailModels.size() > 0) {
                        mAdapter.data.addAll(mDetailModels);
                    } else {
                        mRecyclerView_Detail.showEmptyView();
                        mRecyclerView_Detail.setEmptyImage(R.mipmap.bg_public);
                        mRecyclerView_Detail.setEmptyTitle(R.string.near_data_empty);

                        isMore = false;
                    }
                    mAdapter.notifyDataSetChanged();
                }

            } else if (DetailType == 1) {

                if (DetailPage > 1) {
                    mExplModel_More = JSON.parseArray(object.getString("list"), DetailModel.class);

                    if (mExplModel_More.size() > 0) {
                        mExplModels.addAll(mExplModel_More);
                        mAdapter.data.addAll(mExplModels);
                    } else {
                        isMore = false;
                    }
                    mAdapter.notifyDataSetChanged();
                    mPullableLayout.bottomComponent.finish(Result.SUCCEED);

                } else {
                    mExplModels = JSON.parseArray(object.getString("list"), DetailModel.class);

                    if (mExplModels.size() > 0) {
                        mAdapter.data.addAll(mExplModels);
                    } else {
                        mRecyclerView_Detail.showEmptyView();
                        mRecyclerView_Detail.setEmptyImage(R.mipmap.bg_public);
                        mRecyclerView_Detail.setEmptyTitle(R.string.near_data_empty);

                        isMore = false;
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }

        } else {
            mRecyclerView_Detail.showOfflineView();
            Toast.makeText(this, dataMessage, Toast.LENGTH_SHORT).show();

            mPullableLayout.bottomComponent.finish(Result.FAILED);
        }
    }

    private void selectTitle(TextView textView) {

        mTextView_Income.setTextColor(getResources().getColor(R.color.ingot_detail_text));
        mTextView_Expend.setTextColor(getResources().getColor(R.color.ingot_detail_text));
        mTextView_Overdue.setTextColor(getResources().getColor(R.color.ingot_detail_text));

        textView.setTextColor(getResources().getColor(R.color.ing_number));

        if (textView == mTextView_Income) {
            mView_Income.setVisibility(View.VISIBLE);

            mView_Expend.setVisibility(View.GONE);
            mView_Overdue.setVisibility(View.GONE);
        } else if (textView == mTextView_Expend) {
            mView_Expend.setVisibility(View.VISIBLE);

            mView_Income.setVisibility(View.GONE);
            mView_Overdue.setVisibility(View.GONE);
        } else if (textView == mTextView_Overdue) {
            mView_Overdue.setVisibility(View.VISIBLE);

            mView_Income.setVisibility(View.GONE);
            mView_Expend.setVisibility(View.GONE);
        }
    }
}
