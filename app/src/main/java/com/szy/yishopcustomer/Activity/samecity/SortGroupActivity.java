package com.szy.yishopcustomer.Activity.samecity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Adapter.NearTitleListAdapter;
import com.szy.yishopcustomer.Adapter.samecity.SortGroupAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.Near.NearTitleAll;
import com.szy.yishopcustomer.ResponseModel.SameCity.Near.NearTitleDistance;
import com.szy.yishopcustomer.ResponseModel.SameCity.Sort.SortGroupModel;
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
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * @author wyx
 * @role 分类 团购 ac
 * @time 2018 2018/8/15 10:59
 */

public class SortGroupActivity extends Activity implements View.OnClickListener, OnPullListener {

    public static String GROUP_ID = "GroupId";

    private static final int MENU_ALL = 1;
    private static final int MENU_NEAR = 2;
    private static final int MENU_SALE = 3;

    @BindView(R.id.img_sort_group_back)
    ImageView mImageView_Back;
    @BindView(R.id.linea_sort_seach)
    LinearLayout mLayout_Seach;

    @BindView(R.id.linea_sor_group_title)
    LinearLayout mLayout_Title;
    @BindView(R.id.rela_sort_group_all)
    RelativeLayout mLayout_All;
    @BindView(R.id.tv_sort_group_title_all)
    TextView mTextView_All;
    @BindView(R.id.img_sort_group_title_all)
    ImageView mImageView_All;

    @BindView(R.id.rela_sort_group_near)
    RelativeLayout mLayout_Near;
    @BindView(R.id.tv_sort_group_title_near)
    TextView mTextView_Near;
    @BindView(R.id.img_sort_group_title_near)
    ImageView mImageView_Near;

    @BindView(R.id.rela_sort_group_sale)
    RelativeLayout mLayout_Sale;
    @BindView(R.id.tv_sort_group_title_sale)
    TextView mTextView_Sale;
    @BindView(R.id.img_sort_group_title_sale)
    ImageView mImageView_Sale;

    @BindView(R.id.pull_sort_group)
    PullableLayout mPullableLayout_Group;
    @BindView(R.id.recy_sort_group)
    CommonRecyclerView mRecyclerView_Group;

    private boolean isSaleClick = false;

    private int parId;
    private String saleDefault;
    private int pageNumber = 1;
    private int disTance = 0;

    private ListPopupWindow mListPopupWindow;
    //标题内容 adapter
    private NearTitleListAdapter mTitleListAdapter = null;
    private NearTitleAll titleAll = null;
    private List<NearTitleAll> mAllList = new ArrayList<>();
    private List<NearTitleAll> mTitleAllList = new ArrayList<>();
    private List<NearTitleDistance> mDistanceList = new ArrayList<>();

    private List<SortGroupModel> mGroupModels = new ArrayList<>();
    private List<SortGroupModel> mGroupModels_More = new ArrayList<>();
    private SortGroupAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_group);
        ButterKnife.bind(this);

        parId = getIntent().getIntExtra(GROUP_ID, 0);

        mImageView_Back.setOnClickListener(this);
        mLayout_Seach.setOnClickListener(this);

        mLayout_All.setOnClickListener(this);
        mLayout_Near.setOnClickListener(this);
        mLayout_Sale.setOnClickListener(this);

        mPullableLayout_Group.bottomComponent.setOnPullListener(this);

        mRecyclerView_Group.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView_Group.addItemDecoration(ListItemDecoration.createVertical(this, getResources().getColor(R.color.divider), 1));

        mAdapter = new SortGroupAdapter(this);
        mRecyclerView_Group.setAdapter(mAdapter);

        mListPopupWindow = new ListPopupWindow(this);
        mListPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        mTitleListAdapter = new NearTitleListAdapter(this);
        mListPopupWindow.setAdapter(mTitleListAdapter);
        mListPopupWindow.setModal(true);
        mListPopupWindow.setAnchorView(mLayout_Title);

        mTitleListAdapter.setItemClick(new NearTitleListAdapter.onPopItemClick() {
            @Override
            public void onAllClick(String titleName, int titleId) {

                mTextView_All.setText(titleName);
                parId = titleId;
                mListPopupWindow.dismiss();

                mGroupModels.clear();
                getGroupData();
            }

            @Override
            public void onNearClick(String titleName, int distance) {

                mTextView_Near.setText(titleName);
                disTance = distance;
                mListPopupWindow.dismiss();

                mGroupModels.clear();
                getGroupData();
            }
        });

        titleAll = new NearTitleAll();
        titleAll.catgName = "全部";
        titleAll.catgId = parId;
        mTitleAllList.add(titleAll);

        getTitleData();
        Utils.addDisTitleData(mDistanceList);
        getGroupData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_sort_group_back:
                finish();
                break;
            case R.id.linea_sort_seach:
//                Intent intent = new Intent(this, SortSeachActivity.class);
//                intent.putExtra(SortSeachActivity.SEACH_TYPE_ID, getIntent().getIntExtra(GROUP_ID, 0));
//                startActivity(intent);

                Intent intent = new Intent(this, ProjectH5Activity.class);
                intent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/search");
                startActivity(intent);
                break;
            case R.id.rela_sort_group_all:
                chanageBtn(MENU_ALL);
                mListPopupWindow.setHeight(960);
                mListPopupWindow.show();

                if (mAllList.size() > 0) {
                    mTitleAllList.addAll(mAllList);
                    mTitleListAdapter.setChanageData(true);
                    mTitleListAdapter.setAllList(mTitleAllList);
                    mTitleListAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.rela_sort_group_near:
                chanageBtn(MENU_NEAR);
                mListPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                mListPopupWindow.show();

                if (mDistanceList.size() > 0) {
                    mTitleListAdapter.setChanageData(false);
                    mTitleListAdapter.setNearList(mDistanceList);
                    mTitleListAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.rela_sort_group_sale:
                chanageBtn(MENU_SALE);

                mGroupModels.clear();
                getGroupData();
                break;

        }
    }

    @Override
    public void onCanceled(PullableComponent pullableComponent) {

    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        mGroupModels_More.clear();

        pageNumber++;
        getGroupData();
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }

    private void getTitleData() {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_CITY_HOME_NEAR_TITLE, RequestMethod.GET);
        RequestAddHead.addNoHttpHead(request, this, Api.API_CITY_HOME_NEAR_TITLE, "GET");
        request.add("parentId",0);

        requestQueue.add(HttpWhat.HTTP_NEAR_TITLE.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                if (response.responseCode() == Utils.CITY_NET_SUCES) {
                    mAllList = JSON.parseArray(response.get(), NearTitleAll.class);
                } else {

                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Toast.makeText(SortGroupActivity.this, R.string.data_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void getGroupData() {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_SORT_GROUP_URL, RequestMethod.GET);
        RequestAddHead.addNoHttpHead(request, this, Api.API_SORT_GROUP_URL, "GET");
        request.add("distance", disTance);
        if (App.getInstance().lat != null) {
            request.add("latitude", App.getInstance().lat);
            request.add("longitude", App.getInstance().lng);
        }
        request.add("orderByClause", saleDefault);
        request.add("pageNum", pageNumber);
        request.add("prodClassifyId", parId);

        requestQueue.add(HttpWhat.HTTP_SORT_GROUP.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                if (response.responseCode() == Utils.CITY_NET_SUCES) {

                    JSONObject object = JSONObject.parseObject(response.get());
                    if (pageNumber > 1) {
                        mPullableLayout_Group.bottomComponent.finish(Result.SUCCEED);
                        mGroupModels_More = JSON.parseArray(object.getString("list"), SortGroupModel.class);
                        if (mGroupModels_More.size() > 0) {
                            mGroupModels.addAll(mGroupModels_More);
                            mAdapter.listModels = mGroupModels;
                            mAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(SortGroupActivity.this, "暂无更多数据", Toast.LENGTH_SHORT).show();
                            pageNumber = 1;
                        }
                    } else {
                        mGroupModels = JSON.parseArray(object.getString("list"), SortGroupModel.class);
                        if (mGroupModels.size() > 0) {
                            mAdapter.listModels = mGroupModels;
                            mAdapter.notifyDataSetChanged();
                        } else {
                            mRecyclerView_Group.setEmptyImage(R.mipmap.bg_public);
                            mRecyclerView_Group.setEmptyTitle(R.string.near_data_empty);
                            mRecyclerView_Group.setEmptySubtitle(R.string.order_type_button);
                            mRecyclerView_Group.showEmptyView();

                            //取消 加载更多
                            mPullableLayout_Group.bottomComponent.finish(Result.FAILED);
                        }
                    }
                } else {
                    Toast.makeText(SortGroupActivity.this, R.string.data_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Toast.makeText(SortGroupActivity.this, R.string.data_error, Toast.LENGTH_SHORT).show();
                mRecyclerView_Group.showOfflineView();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void chanageBtn(int menu) {

        switch (menu) {
            case MENU_ALL:

                mTextView_All.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                mImageView_All.setBackgroundResource(R.mipmap.near_tab_selected);

                mTextView_Near.setTextColor(ContextCompat.getColor(this, R.color.aliwx_black));
                mImageView_Near.setBackgroundResource(R.mipmap.near_tab_normal);

                mTextView_Sale.setTextColor(ContextCompat.getColor(this, R.color.aliwx_black));
                if (!isSaleClick) {
                    mImageView_Sale.setBackgroundResource(R.mipmap.near_tab_normal);
                } else {
                    mImageView_Sale.setBackgroundResource(R.mipmap.near_sale_top_normal);
                }
                break;
            case MENU_NEAR:

                mTextView_Near.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                mImageView_Near.setBackgroundResource(R.mipmap.near_tab_selected);

                mTextView_All.setTextColor(ContextCompat.getColor(this, R.color.aliwx_black));
                mImageView_All.setBackgroundResource(R.mipmap.near_tab_normal);

                mTextView_Sale.setTextColor(ContextCompat.getColor(this, R.color.aliwx_black));
                if (!isSaleClick) {
                    mImageView_Sale.setBackgroundResource(R.mipmap.near_tab_normal);
                } else {
                    mImageView_Sale.setBackgroundResource(R.mipmap.near_sale_top_normal);
                }
                break;
            case MENU_SALE:

                mTextView_All.setTextColor(ContextCompat.getColor(this, R.color.aliwx_black));
                mImageView_All.setBackgroundResource(R.mipmap.near_tab_normal);

                mTextView_Near.setTextColor(ContextCompat.getColor(this, R.color.aliwx_black));
                mImageView_Near.setBackgroundResource(R.mipmap.near_tab_normal);

                mTextView_Sale.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                if (!isSaleClick) {
                    mImageView_Sale.setBackgroundResource(R.mipmap.near_tab_selected);
                    isSaleClick = true;
                    saleDefault = "p.conversion_ratio desc";
                } else {
                    mImageView_Sale.setBackgroundResource(R.mipmap.near_sale_top);
                    isSaleClick = false;
                    saleDefault = "p.conversion_ratio ASC";
                }
                break;
        }
    }
}
