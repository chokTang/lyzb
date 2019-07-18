package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.view.LayoutInflater;
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
import com.szy.yishopcustomer.Activity.LoginActivity;
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
 * @role 同城生活 附近
 * @time 2018 11:15
 */

public class SameCityNearFragment extends Fragment implements OnPullListener, View.OnClickListener {

    @BindView(R.id.tv_same_city_naer_title)
    TextView mTextView_Title;

    @BindView(R.id.linea_same_city_naer_seach)
    LinearLayout mLayout_Seach;
    @BindView(R.id.tv_same_city_near_seach)
    TextView mTextView_Seach;

    @BindView(R.id.linea_near_title)
    LinearLayout mLayout_Title;
    @BindView(R.id.rela_near_all)
    RelativeLayout mRelativeLayout_All;
    @BindView(R.id.rela_near_near)
    RelativeLayout mRelativeLayout_Near;
    @BindView(R.id.rela_near_sale)
    RelativeLayout mRelativeLayout_Sale;

    @BindView(R.id.tv_near_title_all)
    TextView mTextView_All;
    @BindView(R.id.img_near_title_all)
    ImageView mImageView_All;

    @BindView(R.id.tv_near_title_near)
    TextView mTextView_Near;
    @BindView(R.id.img_near_title_near)
    ImageView mImageView_Near;

    @BindView(R.id.tv_near_title_sale)
    TextView mTextView_Sale;
    @BindView(R.id.img_near_title_sale)
    ImageView mImageView_Sale;

    @BindView(R.id.fragment_city_near_pull_layout)
    PullableLayout mPullableLayout;
    @BindView(R.id.recy_same_city_near_list)
    CommonRecyclerView mRecyclerView_List;

    private Intent mIntent = null;

    private ListPopupWindow mListPopupWindow;

    private NearListAdapter mListAdapter;

    private boolean isLoadMore = false;

    private final int TITLE_ALL = 1;
    private final int TITLE_NEAR = 2;
    private final int TITLE_SALE = 3;

    private int parId = 0;
    private int pageNumber = 1;
    private int disTance = 0;
    private String saleDefault = "";

    private NearTitleListAdapter mTitleListAdapter;

    private NearTitleAll titleAll = null;
    private List<NearTitleAll> mAllList = new ArrayList<>();
    private List<NearTitleAll> mTitleAllList = new ArrayList<>();
    private List<NearTitleDistance> mTitleDistanceList = new ArrayList<>();

    private List<NearListModel> mListModels = new ArrayList<>();
    private List<NearListModel> mListModels_More = new ArrayList<>();

    private boolean isSaleClick = false;

    View mView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_samecity_near, container, false);
            ButterKnife.bind(this, mView);
        }
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (App.getInstance().location != null) {
            mTextView_Title.setText(App.getInstance().location);
        }

        mPullableLayout.bottomComponent.setOnPullListener(this);

        mRecyclerView_List.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView_List.addItemDecoration(ListItemDecoration.createVertical(getActivity(), Color.GRAY, 1));

        mLayout_Seach.setOnClickListener(this);

        mRelativeLayout_All.setOnClickListener(this);
        mRelativeLayout_Near.setOnClickListener(this);
        mRelativeLayout_Sale.setOnClickListener(this);

        mListAdapter = new NearListAdapter(getActivity());
        mRecyclerView_List.setAdapter(mListAdapter);

        mListPopupWindow = new ListPopupWindow(getActivity());
        mListPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mListPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        mTitleListAdapter = new NearTitleListAdapter(getActivity());
        mListPopupWindow.setAdapter(mTitleListAdapter);
        mListPopupWindow.setModal(true);
        mListPopupWindow.setAnchorView(mLayout_Title);

        mTitleListAdapter.setItemClick(new NearTitleListAdapter.onPopItemClick() {
            @Override
            public void onAllClick(String titleName, int titleId) {

                mTextView_All.setText(titleName);
                parId = titleId;

                mListPopupWindow.dismiss();
                getNearList();
            }

            @Override
            public void onNearClick(String titleName, int distance) {

                mTextView_Near.setText(titleName);
                disTance = distance;

                mListPopupWindow.dismiss();
                getNearList();
            }
        });


        getNearTitleData(0);
        Utils.addDisTitleData(mTitleDistanceList);
        getNearList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.linea_same_city_naer_seach:
                mIntent = new Intent(getActivity(), ProjectH5Activity.class);
                mIntent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/search");
                startActivity(mIntent);
                break;
            case R.id.rela_near_all:
                titleChoose(TITLE_ALL);

                //使用listpopupwindow
                mListPopupWindow.show();
                mTitleAllList.clear();

                titleAll = new NearTitleAll();
                titleAll.catgName = "全部";
                titleAll.catgId = 0;
                mTitleAllList.add(titleAll);

                if (mAllList.size() > 0) {
                    mTitleAllList.addAll(mAllList);
                    mTitleListAdapter.setChanageData(true);
                    mTitleListAdapter.setAllList(mTitleAllList);

                    mTitleListAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.rela_near_near:
                titleChoose(TITLE_NEAR);
                mListPopupWindow.show();

                if (mTitleDistanceList.size() > 0) {
                    mTitleListAdapter.setChanageData(false);
                    mTitleListAdapter.setNearList(mTitleDistanceList);

                    mTitleListAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.rela_near_sale:
                titleChoose(TITLE_SALE);
                mListModels_More.clear();
                getNearList();
                break;
        }
    }

    /***
     *
     * @param parId 0
     */
    void getNearTitleData(int parId) {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_CITY_HOME_NEAR_TITLE, RequestMethod.GET);
        RequestAddHead.addNoHttpHead(request, getActivity(), Api.API_CITY_HOME_NEAR_TITLE, "GET");
        request.add("parentId", parId);

        requestQueue.add(HttpWhat.HTTP_NEAR_TITLE.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                if (response.responseCode() == 200) {
                    mAllList = JSON.parseArray(response.get(), NearTitleAll.class);
                } else {
                    Toast.makeText(getActivity(), R.string.data_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Toast.makeText(getActivity(), R.string.data_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /***
     * 获取附近列表数据
     * classId 必传 默认0
     * distance 智能排序  传值为0
     * latitude 纬度 必传
     * longitude 经度 必传
     * orderByClause 选中后 传值  判断非空
     * pageNum 动态传值
     *
     *
     *
     */
    void getNearList() {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_CITY_HOME_NEAR_LIST, RequestMethod.GET);
        RequestAddHead.addNoHttpHead(request, getActivity(), Api.API_CITY_HOME_NEAR_LIST, "GET");

        request.add("classId", parId);
        request.add("distance", disTance);
        if (App.getInstance().lat != null) {
            request.add("latitude", App.getInstance().lat);
            request.add("longitude", App.getInstance().lng);
        } else if (App.getInstance().city_name != null) {
            request.add("regionCode", App.getInstance().city_code);
        }

        request.add("orderByClause", saleDefault);
        request.add("pageNum", pageNumber);
        request.add("pageSize", 15);

        requestQueue.add(HttpWhat.HTTP_NEAR_LIST.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                if (response.responseCode() == Utils.CITY_NET_SUCES) {
                    JSONObject jsonObject = JSONObject.parseObject(response.get());
                    if (pageNumber > 1) {

                        mListModels_More = JSON.parseArray(jsonObject.getString("list"), NearListModel.class);
                        if (mListModels_More.size() > 0) {
                            mListModels.addAll(mListModels_More);
                            mListAdapter.setData(mListModels);
                            mListAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "暂无更多数据", Toast.LENGTH_SHORT).show();
                            pageNumber = 1;
                        }
                        mPullableLayout.bottomComponent.finish(Result.SUCCEED);
                        isLoadMore = false;
                    } else {
                        mListModels = JSON.parseArray(jsonObject.getString("list"), NearListModel.class);
                        if (mListModels.size() > 0) {
                            mRecyclerView_List.hideEmptyView();

                            mListAdapter.setData(mListModels);
                            mListAdapter.notifyDataSetChanged();
                        } else {
                            //暂无数据 视图提示
                            mListModels.clear();
                            mListAdapter.setData(mListModels);
                            mListAdapter.notifyDataSetChanged();

                            mRecyclerView_List.setEmptyImage(R.mipmap.bg_public);
                            mRecyclerView_List.setEmptyTitle(R.string.near_data_empty);
                            mRecyclerView_List.setEmptySubtitle(R.string.order_type_button);
                            mRecyclerView_List.showEmptyView();

                            //取消 加载更多
                            mPullableLayout.bottomComponent.finish(Result.FAILED);
                        }
                    }

                } else if (response.responseCode() == Utils.CITY_NET_LOGIN) {
                    Toast.makeText(getActivity(), "登录已失效,请登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    Toast.makeText(getActivity(), R.string.data_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                if (pageNumber > 1) {
                    mPullableLayout.bottomComponent.finish(Result.FAILED);
                }
                mRecyclerView_List.showOfflineView();
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }


    @Override
    public void onCanceled(PullableComponent pullableComponent) {

    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {

        isLoadMore = true;
        pageNumber++;
        getNearList();
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }

    //title选中效果 处理
    private void titleChoose(int menuState) {

        switch (menuState) {
            case TITLE_ALL:
                //全部title被选中  其余title置灰
                mTextView_All.setTextColor(this.getResources().getColorStateList(R.color.colorPrimary));
                mImageView_All.setBackgroundResource(R.mipmap.near_tab_selected);

                mTextView_Near.setTextColor(this.getResources().getColorStateList(R.color.aliwx_black));
                mImageView_Near.setBackgroundResource(R.mipmap.near_tab_normal);

                mTextView_Sale.setTextColor(this.getResources().getColorStateList(R.color.aliwx_black));
                if (!isSaleClick) {
                    mImageView_Sale.setBackgroundResource(R.mipmap.near_tab_normal);
                } else {
                    mImageView_Sale.setBackgroundResource(R.mipmap.near_sale_top_normal);
                }
                break;
            case TITLE_NEAR:

                mTextView_Near.setTextColor(this.getResources().getColorStateList(R.color.colorPrimary));
                mImageView_Near.setBackgroundResource(R.mipmap.near_tab_selected);

                mTextView_All.setTextColor(this.getResources().getColorStateList(R.color.aliwx_black));
                mImageView_All.setBackgroundResource(R.mipmap.near_tab_normal);

                mTextView_Sale.setTextColor(this.getResources().getColorStateList(R.color.aliwx_black));
                if (!isSaleClick) {
                    mImageView_Sale.setBackgroundResource(R.mipmap.near_tab_normal);
                } else {
                    mImageView_Sale.setBackgroundResource(R.mipmap.near_sale_top_normal);
                }
                break;
            case TITLE_SALE:
                mTextView_All.setTextColor(this.getResources().getColorStateList(R.color.aliwx_black));
                mImageView_All.setBackgroundResource(R.mipmap.near_tab_normal);

                mTextView_Near.setTextColor(this.getResources().getColorStateList(R.color.aliwx_black));
                mImageView_Near.setBackgroundResource(R.mipmap.near_tab_normal);


                mTextView_Sale.setTextColor(this.getResources().getColorStateList(R.color.colorPrimary));
                if (!isSaleClick) {
                    //第一次点击
                    mImageView_Sale.setBackgroundResource(R.mipmap.near_tab_selected);
                    isSaleClick = true;

                    saleDefault = "soldNum asc";
                } else {
                    //第二次点击
                    mImageView_Sale.setBackgroundResource(R.mipmap.near_sale_top);
                    isSaleClick = false;

                    saleDefault = "soldNum DESC";
                }
                break;

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
}
