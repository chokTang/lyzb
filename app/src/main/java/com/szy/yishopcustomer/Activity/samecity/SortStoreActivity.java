package com.szy.yishopcustomer.Activity.samecity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.text.TextUtils;
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
import com.szy.yishopcustomer.Adapter.NearListAdapter;
import com.szy.yishopcustomer.Adapter.NearTitleListAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.Near.NearListModel;
import com.szy.yishopcustomer.ResponseModel.SameCity.Near.NearTitleAll;
import com.szy.yishopcustomer.ResponseModel.SameCity.Near.NearTitleDistance;
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
 * @role 分类页面 (美业)ac
 * @time 2018 2018/8/6 10:43
 */

public class SortStoreActivity extends Activity implements View.OnClickListener, OnPullListener {

    public static String SORT_NAME = "SortName";
    public static String SORT_TYPE = "SortType";

    /***
     * 用于接受 更多页面传递的值
     * titleId  搜索内容的父级id
     * TitleName 名称
     */
    public static String TITLE_ID = "TitleId";
    public static String TITLE_NAME = "TitleName";
    private int mTitleId = 0;
    private String mTitleName;



    private static final int MENU_ALL = 1;
    private static final int MENU_NEAR = 2;
    private static final int MENU_SALE = 3;

    @BindView(R.id.img_sort_back)
    ImageView mImageView_Back;
    @BindView(R.id.tv_sort_title)
    TextView mTextView_Title;

    @BindView(R.id.linea_sort_seach)
    LinearLayout mLayout_Seach;

    @BindView(R.id.rela_sort_all)
    RelativeLayout mRelativeLayout_All;
    @BindView(R.id.tv_sort_title_all)
    TextView mTextView_All;
    @BindView(R.id.img_sort_title_all)
    ImageView mImageView_All;

    @BindView(R.id.linea_sor_title)
    LinearLayout mLinearLayout_Title;

    @BindView(R.id.rela_sort_near)
    RelativeLayout mRelativeLayout_Near;
    @BindView(R.id.tv_sort_title_near)
    TextView mTextView_Near;
    @BindView(R.id.img_sort_title_near)
    ImageView mImageView_Near;

    @BindView(R.id.rela_sort_sale)
    RelativeLayout mRelativeLayout_Sale;
    @BindView(R.id.tv_sort_title_sale)
    TextView mTextView_Sale;
    @BindView(R.id.img_sort_title_sale)
    ImageView mImageView_Sale;

    @BindView(R.id.pull_store_sort)
    PullableLayout mPullableLayout_Sort;
    @BindView(R.id.recy_store_sort)
    CommonRecyclerView mRecyclerView_Sort;

    private ListPopupWindow mListPopupWindow;

    private boolean isSaleClick = false;
    private String saleDefault;
    private int parId = 0;
    private int pageNumber = 1;
    private int disTance = 0;

    private NearTitleListAdapter mTitleListAdapter = null;
    private NearListAdapter mListAdapter = null;

    private NearTitleAll titleAll = null;
    private List<NearTitleAll> mAllList = new ArrayList<>();
    private List<NearTitleAll> mTitleAllList = new ArrayList<>();

    private List<NearTitleDistance> mDistanceList = new ArrayList<>();

    private List<NearListModel> mListModels = new ArrayList<>();
    private List<NearListModel> mListModels_More = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_store);
        ButterKnife.bind(this);

        if (!TextUtils.isEmpty(getIntent().getStringExtra(SORT_NAME))) {
            mTextView_Title.setText(getIntent().getStringExtra(SORT_NAME));
        } else if (App.getInstance().location != null) {
            mTextView_Title.setText(App.getInstance().location);
        }

        if (!TextUtils.isEmpty(getIntent().getStringExtra(SORT_TYPE))) {
            parId = Integer.parseInt(getIntent().getStringExtra(SORT_TYPE));
            mTitleId = Integer.parseInt(getIntent().getStringExtra(SORT_TYPE));
        } else if (!TextUtils.isEmpty(getIntent().getStringExtra(TITLE_ID))) {
            parId = getIntent().getIntExtra(TITLE_ID, 0);
        }

        if (!TextUtils.isEmpty(getIntent().getStringExtra(TITLE_NAME))) {
            mTitleName = getIntent().getStringExtra(TITLE_NAME);
            mTextView_All.setText(mTitleName);
        } else {
            mTextView_All.setText("全部");
        }
        mTextView_Sale.setText("元宝抵扣");
        mImageView_Back.setOnClickListener(this);
        mLayout_Seach.setOnClickListener(this);

        mRelativeLayout_All.setOnClickListener(this);
        mRelativeLayout_Near.setOnClickListener(this);
        mRelativeLayout_Sale.setOnClickListener(this);

        mPullableLayout_Sort.bottomComponent.setOnPullListener(this);

        mRecyclerView_Sort.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView_Sort.addItemDecoration(ListItemDecoration.createVertical(this, getResources().getColor(R.color.divider), 1));

        mListPopupWindow = new ListPopupWindow(this);
        mListPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        mTitleListAdapter = new NearTitleListAdapter(this);
        mListPopupWindow.setAdapter(mTitleListAdapter);
        mListPopupWindow.setModal(true);
        mListPopupWindow.setAnchorView(mLinearLayout_Title);

        mListAdapter = new NearListAdapter(this);
        mRecyclerView_Sort.setAdapter(mListAdapter);

        mTitleListAdapter.setItemClick(new NearTitleListAdapter.onPopItemClick() {
            @Override
            public void onAllClick(String titleName, int titleId) {

                mTextView_All.setText(titleName);
                parId = titleId;
                mListPopupWindow.dismiss();

                mListModels.clear();
                getSortData();
            }

            @Override
            public void onNearClick(String titleName, int distance) {

                mTextView_Near.setText(titleName);
                disTance = distance;
                mListPopupWindow.dismiss();

                mListModels.clear();
                getSortData();
            }
        });

        titleAll = new NearTitleAll();
        titleAll.catgName = "全部";
        titleAll.catgId = mTitleId;//美业 就应该是美业的linkpath
        mTitleAllList.add(titleAll);

        getTitleData();
        Utils.addDisTitleData(mDistanceList);
        getSortData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_sort_back:
                finish();
                break;
            case R.id.linea_sort_seach:

//                 Intent intent = new Intent(this, SortSeachActivity.class);
//                 intent.putExtra(SortSeachActivity.SEACH_TYPE_ID, Integer.parseInt(getIntent().getStringExtra(SORT_TYPE)));
//                 startActivity(intent);

                Intent intent = new Intent(this, ProjectH5Activity.class);
                intent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/search");
                startActivity(intent);
                break;
            case R.id.rela_sort_all:
                chanageBtn(MENU_ALL);

                mTitleListAdapter.setChanageData(true);
                mTitleListAdapter.setAllList(mTitleAllList);
                mTitleListAdapter.notifyDataSetChanged();
                mListPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                mListPopupWindow.show();
                break;
            case R.id.rela_sort_near:
                chanageBtn(MENU_NEAR);

                mTitleListAdapter.setChanageData(false);
                mTitleListAdapter.setNearList(mDistanceList);
                mTitleListAdapter.notifyDataSetChanged();

                mListPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                mListPopupWindow.show();
                break;
            case R.id.rela_sort_sale:
                chanageBtn(MENU_SALE);
                mListModels_More.clear();

                getSortData();
                break;
        }
    }


    /****
     * 按钮状态改变
     * @param menu
     */
    public void chanageBtn(int menu) {

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
                //以前是销量  现在改成元宝抵扣了

                mTextView_All.setTextColor(ContextCompat.getColor(this, R.color.aliwx_black));
                mImageView_All.setBackgroundResource(R.mipmap.near_tab_normal);

                mTextView_Near.setTextColor(ContextCompat.getColor(this, R.color.aliwx_black));
                mImageView_Near.setBackgroundResource(R.mipmap.near_tab_normal);

                mTextView_Sale.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));


                if (!isSaleClick) {
                    mImageView_Sale.setBackgroundResource(R.mipmap.near_tab_selected);
                    isSaleClick = true;
                    saleDefault = "p.conversion_ratio desc";//降序
                } else {
                    mImageView_Sale.setBackgroundResource(R.mipmap.near_sale_top);
                    isSaleClick = false;
                    saleDefault = "p.conversion_ratio ASC";//升序
                }
                break;
        }
    }

    @Override
    public void onCanceled(PullableComponent pullableComponent) {

    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {

        pageNumber++;
        getSortData();
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }

    /***
     * 获取 头部title数据
     * parentId 默认为0
     */
    private void getTitleData() {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_CITY_HOME_NEAR_TITLE, RequestMethod.GET);
        RequestAddHead.addNoHttpHead(request, this, Api.API_CITY_HOME_NEAR_TITLE, "GET");
        request.add("parentId", mTitleId);

        requestQueue.add(HttpWhat.HTTP_NEAR_TITLE.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                if (response.responseCode() == Utils.CITY_NET_SUCES) {
                    mAllList = JSON.parseArray(response.get(), NearTitleAll.class);

                    if (mAllList.size() > 0) {
                        mTitleAllList.addAll(mAllList);
                        mTitleListAdapter.setAllList(mTitleAllList);
                    } else {
                        mTitleListAdapter.setAllList(mTitleAllList);
                    }
                    mTitleListAdapter.setChanageData(true);
                    mTitleListAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(SortStoreActivity.this, R.string.data_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Toast.makeText(SortStoreActivity.this, R.string.data_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /***
     * 获取分类列表数据
     */
    private void getSortData() {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_CITY_HOME_NEAR_LIST, RequestMethod.GET);
        RequestAddHead.addNoHttpHead(request, this, Api.API_CITY_HOME_NEAR_LIST, "GET");

        request.add("classId", parId);
        request.add("distance", disTance);
        if (App.getInstance().isLocation) {
            request.add("latitude", App.getInstance().lat);
            request.add("longitude", App.getInstance().lng);
        } else if (App.getInstance().city_name != null) {
            request.add("regionCode", App.getInstance().city_code);
        }

        request.add("orderByClause", saleDefault);
        request.add("pageNum", pageNumber);

        requestQueue.add(HttpWhat.HTTP_NEAR_LIST.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                if (response.responseCode() == Utils.CITY_NET_SUCES) {
                    JSONObject jsonObject = JSONObject.parseObject(response.get());

                    if (pageNumber > 1) {
                        mPullableLayout_Sort.bottomComponent.finish(Result.SUCCEED);

                        mListModels_More = JSON.parseArray(jsonObject.getString("list"), NearListModel.class);
                        if (mListModels_More.size() > 0) {
                            mListModels.addAll(mListModels_More);
                            mListAdapter.setData(mListModels);
                            mListAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(SortStoreActivity.this, "暂无更多数据", Toast.LENGTH_SHORT).show();
                            pageNumber = 1;
                        }
                    } else {
                        mListModels = JSON.parseArray(jsonObject.getString("list"), NearListModel.class);
                        if (mListModels.size() > 0) {

                            mRecyclerView_Sort.hideEmptyView();

                            mListAdapter.setData(mListModels);
                            mListAdapter.notifyDataSetChanged();

                        } else {
                            mListModels.clear();
                            mListAdapter.setData(mListModels);
                            mListAdapter.notifyDataSetChanged();

                            mRecyclerView_Sort.setEmptyImage(R.mipmap.bg_public);
                            mRecyclerView_Sort.setEmptyTitle(R.string.near_data_empty);
                            mRecyclerView_Sort.setEmptySubtitle(R.string.order_type_button);
                            mRecyclerView_Sort.showEmptyView();

                            //取消 加载更多
                            mPullableLayout_Sort.bottomComponent.finish(Result.FAILED);
                        }
                    }


                } else {
                    Toast.makeText(SortStoreActivity.this, R.string.data_error, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Toast.makeText(SortStoreActivity.this, R.string.data_error, Toast.LENGTH_SHORT).show();
                mRecyclerView_Sort.showOfflineView();
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }
}
