package com.szy.yishopcustomer.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.lyzb.jbx.R;
import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Activity.samecity.CityListActivity;
import com.szy.yishopcustomer.Activity.ScanActivity;
import com.szy.yishopcustomer.Adapter.CityHomeAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.ResponseModel.AppIndex.TemplateModel;
import com.szy.yishopcustomer.ResponseModel.IngotList.UsableIngotModel;
import com.szy.yishopcustomer.ResponseModel.SameCity.Home.appMoudle;
import com.szy.yishopcustomer.ResponseModel.SameCity.Home.carouselAdvert;
import com.szy.yishopcustomer.ResponseModel.SameCity.Home.middleAdvert;
import com.szy.yishopcustomer.Service.Location;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.LogUtils;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.Util.json.GSONUtil;
import com.szy.yishopcustomer.Util.json.GsonUtils;
import com.szy.yishopcustomer.ViewModel.HomeShopAndProductBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

import static com.szy.yishopcustomer.Constant.Macro.TEMPLATE_CODE_HOME_VIDEO_SHOW;
import static com.szy.yishopcustomer.Constant.Macro.TEMPLATE_CODE_INGOTS_BUY;
import static com.szy.yishopcustomer.Constant.Macro.TEMPLATE_CODE_LIKE_SHOP;
import static com.szy.yishopcustomer.Constant.Macro.TEMPLATE_CODE_NEAR_SHOP;
import static com.szy.yishopcustomer.Constant.Macro.TEMPLATE_CODE_NET_HOT_SHOP;
import static com.szy.yishopcustomer.Constant.Macro.TEMPLATE_CODE_SHOP_JION;
import static com.szy.yishopcustomer.Constant.Macro.TEMPLATE_CODE_SHOP_NEW_JOIN;
import static com.szy.yishopcustomer.Constant.Macro.TEMPLATE_VIEW_TYPE_AD_ADVERT;

/**
 * @author wyx
 * @role 同城生活 首页
 * @time 2018 11:15
 */

public class SameCityHomeFragment extends YSCBaseFragment implements OnPullListener, OnEmptyViewClickListener, View.OnClickListener {

    @BindView(R.id.fragment_city_home_title)
    LinearLayout mLayout_Title;

    @BindView(R.id.img_sc_home_back)
    ImageView mImageView_Back;
    @BindView(R.id.tv_sc_home_city)
    TextView mTextView_CityChoose;
    @BindView(R.id.img_sc_home_city_choose)
    ImageView mImageView_CityChoose;
    @BindView(R.id.rela_sc_home_seach)
    RelativeLayout mLayout_Seach;
    @BindView(R.id.rela_sc_home_scan)
    RelativeLayout mLayout_Scan;

    @BindView(R.id.fragment_city_home_pull_layout)
    PullableLayout mPullableLayout;

    @BindView(R.id.fragment_city_home_RecyclerView)
    CommonRecyclerView mRecyclerView;

    View mView = null;

    private LinearLayoutManager mLayoutManager;
    private CityHomeAdapter mAdapter;

    private Intent mIntent;

    private JSONObject mObject_Data;

    private List<carouselAdvert> mCarouselAdvertList = new ArrayList<>();
    private List<appMoudle> mAppMoudleList = new ArrayList<>();
    //    private List<appCategory> mAppCategoryList = new ArrayList<>();
    private List<middleAdvert> mMiddleAdvertList = new ArrayList<>();

    private List<TemplateModel> mList = new ArrayList<>();
    private List<TemplateModel> mListsMore = new ArrayList<>();

    private UsableIngotModel mIngotModel;

    private int pageNum = 1;
    private boolean isMore = true;

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (mRecyclerView.canScrollVertically(-1)) {
                mLayout_Title.setBackgroundResource(R.color.colorPrimary);
            } else {
                mLayout_Title.setBackgroundResource(android.R.color.transparent);
            }


            /*** 底部监听 **/
            if (Utils.isRecyViewBottom(recyclerView)) {

                /** 加载更多 **/
                if (isMore) {
                    pageNum++;
                    getShopAndProduct();

                } else {
                    Toast.makeText(getActivity(), "暂无更多数据", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_samecity_home, container, false);
            ButterKnife.bind(this, mView);
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mImageView_Back.setOnClickListener(this);
        mTextView_CityChoose.setOnClickListener(this);
        mImageView_CityChoose.setOnClickListener(this);
        mLayout_Seach.setOnClickListener(this);
        mLayout_Scan.setOnClickListener(this);

        mPullableLayout.topComponent.setOnPullListener(this);

        mRecyclerView.addOnScrollListener(mOnScrollListener);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CityHomeAdapter(getActivity());
        mAdapter.windowWidth = Utils.getWindowWidth(getActivity());

        mRecyclerView.setAdapter(mAdapter);


        /** 处理 notifyItemChanged 刷新闪烁 **/
        mRecyclerView.getItemAnimator().setChangeDuration(0);

        getBannerData();
        getShopAndProduct();

        /***
         * 高德定位 1.数据空  2.未选择城市
         */
        if (App.getInstance().city == null && App.getInstance().city_name == null) {

            /***开启定位 获取位置信息**/
            Location.locationCallback(new Location.OnLocationListener() {
                @Override
                public void onSuccess(AMapLocation amapLocation) {
                    App.getInstance().addressDetail = amapLocation.getAoiName();
                    App.getInstance().isLocation = true;
                    getShopAndProduct();
                }

                @Override
                public void onError(AMapLocation amapLocation) {

                    App.getInstance().isLocation = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("定位失败请选择城市.");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            startActivity(new Intent(getActivity(), CityListActivity.class));
                        }
                    });
                    builder.create().show();
                }

                @Override
                public void onFinished(AMapLocation amapLocation) {
                    Location.stopLocation();
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_sc_home_back:
                getActivity().finish();
                break;
            case R.id.tv_sc_home_city:
                startActivity(new Intent(getActivity(), CityListActivity.class));
                break;
            case R.id.img_sc_home_city_choose:
                startActivity(new Intent(getActivity(), CityListActivity.class));
                break;
            case R.id.rela_sc_home_seach:
                mIntent = new Intent(getActivity(), ProjectH5Activity.class);
                mIntent.putExtra(Key.KEY_URL.getValue(), Api.H5_CITYLIFE_URL + "/search");
                startActivity(mIntent);
                break;
            case R.id.rela_sc_home_scan:
                if (Utils.cameraIsCanUse()) {
                    mIntent = new Intent(getActivity(), ScanActivity.class);
                    mIntent.putExtra(Key.KEY_TYPE.getValue(), ScanActivity.TYPE_CITYLIFE);
                    startActivityForResult(mIntent, RequestCode.REQUEST_CODE_SCAN.getValue());
                } else {
                    Toast.makeText(getActivity(), "没有拍照权限,请到设置里开启权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            default:
                mRecyclerView.showOfflineView();
                mPullableLayout.topComponent.finish(Result.FAILED);
                break;
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        mPullableLayout.topComponent.finish(Result.SUCCEED);
        switch (HttpWhat.valueOf(what)) {
            case HTTP_HOME_AD:
                mObject_Data = JSONObject.parseObject(response);
                mCarouselAdvertList = JSON.parseArray(mObject_Data.getString("carouselAdvert"), carouselAdvert.class);
                mAdapter.mCarouselAdvertList = mCarouselAdvertList;

                mAppMoudleList = JSON.parseArray(mObject_Data.getString("appMoudle"), appMoudle.class);
                mAdapter.mAppMoudleList = mAppMoudleList;

                mMiddleAdvertList = JSON.parseArray(mObject_Data.getString("middleAdvert"), middleAdvert.class);
                mAdapter.mMiddleAdvertList = mMiddleAdvertList;
                mAdapter.notifyDataSetChanged();
                break;
//            case HTTP_HOME_MERCHANTS:
//                if (pageNum > 1) {
//                    mListsMore = JSON.parseArray(mObject_Data.getString("list"), list.class);
//                    if (mListsMore.size() > 0) {
//                        mList.addAll(mListsMore);
//                        mAdapter.mLists = mList;
//                    } else {
//                        isMore = false;
//                    }
//
//                } else {
//                    mList = JSON.parseArray(mObject_Data.getString("list"), list.class);
//                    if (mList.size() > 0) {
//                        mAdapter.mLists = mList;
//                    } else {
//                        mList.clear();
//                        mAdapter.mLists = mList;
//                        isMore = false;
//                    }
//                }
//                mAdapter.notifyDataSetChanged();
//                mPullableLayout.topComponent.finish(Result.SUCCEED);
//
//                App.getInstance().isCityChanage = false;
//                break;
            case HTTP_INGOT_USABLE:
                mIngotModel = JSON.parseObject(response, UsableIngotModel.class);
                if (mIngotModel.getCode() == 0) {
                    mAdapter.user_ingot = mIngotModel.getData().getTotal_integral().getIntegral_num();
                    App.getInstance().user_ingot_number = mIngotModel.getData().getTotal_integral().getIntegral_num();
                } else {
                    mAdapter.user_ingot = "0";
                }
                mAdapter.notifyItemChanged(1);
                break;

            case HTTP_HOME_NEAR_SHOP:
                callBackData(what, response);
                break;
        }
    }

    @Override
    public void onCanceled(PullableComponent pullableComponent) {

    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {

        //页面刷新
        getBannerData();
        getShopAndProduct();
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }

    /***
     * 首页 广告数据 API
     */
    void getBannerData() {
        CommonRequest adRequest = new CommonRequest(Api.API_CITY_HOME_AD, HttpWhat.HTTP_HOME_AD.getValue());
        RequestAddHead.addHead(adRequest, getActivity(), Api.API_CITY_HOME_AD, "GET");
        adRequest.add("showType", 3);//3表示Android
        addRequest(adRequest);
    }
//
//    /***
//     * 推荐商家 API
//     * @param latitude 纬度
//     * @param longitude 经度
//     * @param cityCode 区域编码
//     *
//     * 第1个版本:定位成功-传经纬度,不传区域编码,定位失败后手动选择城市 或者 定位成功后手动选择城市 传递参数: 区域编码,经纬度为null
//     * 第2个版本:定位成功-传经纬度,不传区域编码,定位失败后手动选择城市 或者 定位成功后手动选择城市 拿到区域编码 传递参数为:经纬度+区域编码
//     * 第3个版本:定位成功-传经纬度,不传区域编码,定位失败后手动选择城市 或者 定位成功后手动选择城市 传递参数: 区域编码 经纬度为null
//     * 新增 上拉加载更多
//     *
//     **/
//    public void getMerChantsData(String latitude, String longitude, String cityCode) {
//
//        CommonRequest adRequest = new CommonRequest(Api.API_CITY_HOME_MERCHANTS, HttpWhat.HTTP_HOME_MERCHANTS.getValue());
//        RequestAddHead.addHead(adRequest, getActivity(), Api.API_CITY_HOME_MERCHANTS, "GET");
//
//        adRequest.add("latitude", latitude);
//        adRequest.add("longitude", longitude);
//
//        adRequest.add("regionCode", cityCode);
//
//        adRequest.add("pageNum", pageNum);
//        adRequest.add("pageSize", 30);
//        addRequest(adRequest);
//    }


    /**
     * 获取首页商品和店铺的相关信息
     */
    private void getShopAndProduct() {
        CommonRequest request = new CommonRequest(Api.API_SHOP_AND_PRODUCT, HttpWhat.HTTP_HOME_NEAR_SHOP.getValue());
        if (App.getInstance().isLocation) {//定位成功
            if (!TextUtils.isEmpty(App.getInstance().lat)) {//定位成功
                if (App.getInstance().clickChangeCity) {//选择城市(点击变换的)
                    request.add("areaId", App.getInstance().home_area_code);
                    request.add("regionCode", App.getInstance().city_code);
                    request.add("type", 1);
                } else {
                    request.add("areaId", App.getInstance().adcode);
                    request.add("latitude", App.getInstance().lat);
                    request.add("longitude", App.getInstance().lng);
                    request.add("type", 1);
                }
            } else {
                request.add("areaId", App.getInstance().home_area_code);
                request.add("regionCode", App.getInstance().city_code);
                request.add("type", 1);
            }
        } else {//定位失败(选择了城市)
            request.add("areaId", App.getInstance().home_area_code);
            request.add("regionCode", App.getInstance().city_code);
            request.add("type", 1);
        }

        addRequest(request);
    }


    /**
     * 商品和店铺数据请求回调
     *
     * @param what
     * @param response
     */
    private void callBackData(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_HOME_NEAR_SHOP:
                mList.clear();
                LogUtils.Companion.e("同城附近商家数据-->" + response);
                if (!GSONUtil.isGoodGson(response,HomeShopAndProductBean.class)){
                    LogUtils.Companion.e("数据解析错误"+response);
                    return;
                }
                HomeShopAndProductBean bean = GsonUtils.Companion.toObj(response, HomeShopAndProductBean.class);
                assert bean != null;
                if (null!=bean.getSort()) {
                    for (int i = 0; i < bean.getSort().size(); i++) {
                        TemplateModel templateModel = null;
                        //不将live数据添加进去  同城生活
                        if (!bean.getSort().get(i).getType().equals(TEMPLATE_CODE_HOME_VIDEO_SHOW)) {
                            templateModel = new TemplateModel();
                            if (TEMPLATE_VIEW_TYPE_AD_ADVERT.equals(bean.getSort().get(i).getType())) {
                                templateModel.temp_code = bean.getSort().get(i).getKey();
                            } else {
                                templateModel.temp_code = bean.getSort().get(i).getType();
                            }
                            switch (bean.getSort().get(i).getType()) {
                                case TEMPLATE_VIEW_TYPE_AD_ADVERT:
                                    switch (bean.getSort().get(i).getKey()) {
                                        case TEMPLATE_VIEW_TYPE_AD_ADVERT:
                                            templateModel.data = GsonUtils.Companion.toJson(bean.getAdvert());
                                            break;
                                        case TEMPLATE_CODE_SHOP_JION:
                                            if (null != bean.getShop_num()) {
                                                templateModel.data = GsonUtils.Companion.toJson(bean.getShop_num());
                                            }
                                            break;
                                    }
                                    break;
                                case TEMPLATE_CODE_INGOTS_BUY:
                                    templateModel.data = GsonUtils.Companion.toJson(bean.getRepurchase());
                                    break;
                                case TEMPLATE_CODE_NEAR_SHOP:
                                    templateModel.data = GsonUtils.Companion.toJson(bean.getNearby());
                                    break;
                                case TEMPLATE_CODE_SHOP_NEW_JOIN:
                                    templateModel.data = GsonUtils.Companion.toJson(bean.getShop_new());
                                    break;
                                case TEMPLATE_CODE_NET_HOT_SHOP:
                                    templateModel.data = GsonUtils.Companion.toJson(bean.getShop_hot());
                                    break;
                                case TEMPLATE_CODE_LIKE_SHOP:
                                    templateModel.data = GsonUtils.Companion.toJson(bean.getShop_like());
                                    break;
                            }
                            mList.add(templateModel);
                        }
                    }
                }
                mAdapter.data = mList;
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (RequestCode.valueOf(requestCode) == RequestCode.REQUEST_CODE_SCAN) {
            if (resultCode == ProjectH5Activity.QR_CODE_RESULT) {

                mIntent = new Intent(getActivity(), ProjectH5Activity.class);
                mIntent.putExtra(Key.KEY_URL.getValue(), data.getStringExtra(Key.KEY_RESULT.getValue()));
                startActivity(mIntent);
            }
        }
    }

    private void getIngotNumber() {

        CommonRequest request = new CommonRequest(Api.API_INGOT_USABLE, HttpWhat.HTTP_INGOT_USABLE.getValue());
        addRequest(request);
    }

    @Override
    public void onResume() {
        super.onResume();

        isMore = true;
        pageNum = 1;

        if (App.getInstance().isCityChanage) {

            if (App.getInstance().city != null) {
                /** 定位成功 */
                mTextView_CityChoose.setText(App.getInstance().city);
                /** 定位成功后选择城市 */
                if (App.getInstance().city_name != null) {
                    mTextView_CityChoose.setText(App.getInstance().city_name);
                }
                getShopAndProduct();
            } else {
                /** 定位失败 选择城市 */
                if (App.getInstance().city_name != null) {
                    mTextView_CityChoose.setText(App.getInstance().city_name);
                    getShopAndProduct();
                }
            }
        }
        if (App.getInstance().isLogin()) {
            /** 获取元宝数量 */
            getIngotNumber();
        }
    }
}
