package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Adapter.SelectAddressAdapter;
import com.szy.yishopcustomer.Adapter.SelectAddressSubPoiAdapter;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SelectAddress.PoiListModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liuzhifeng on 2017/3/9.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class SelectAddressFragment extends YSCBaseFragment implements PoiSearch
        .OnPoiSearchListener, TextWatcher, LocationSource, Inputtips.InputtipsListener {


    @BindView(R.id.fragment_select_address_mapView)
    MapView mMapView;
    @BindView(R.id.fragment_select_address_poiResult)
    RecyclerView mPoiResultRecyclerView;
    @BindView(R.id.fragment_select_address_poiNearResult)
    RecyclerView mPoiNearResultRecyclerView;
    @BindView(R.id.fragment_select_address_input)
    CommonEditText mSearchEdittext;
    @BindView(R.id.btn_back_dark)
    ImageView mBackButton;
    @BindView(R.id.fragment_select_address_poiLayout)
    LinearLayout mPoiLayout;

    private LinearLayoutManager mLayoutManager;
    private LinearLayoutManager mLayoutManager2;
    private SelectAddressAdapter mSelectAddressAdapter;
    private SelectAddressSubPoiAdapter mSelectAddressSubPoiAdapter;

    private String city = "";
    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private AMap aMap;
    private LatLng centerTarget;
    private String queryWord;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_select_address;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        mSearchEdittext.addTextChangedListener(this);
        mSelectAddressAdapter = new SelectAddressAdapter();
        mLayoutManager = new LinearLayoutManager(getContext());
        mPoiResultRecyclerView.setLayoutManager(mLayoutManager);
        mPoiResultRecyclerView.setAdapter(mSelectAddressAdapter);
        mSelectAddressAdapter.onClickListener = this;

        mSelectAddressSubPoiAdapter = new SelectAddressSubPoiAdapter();
        mLayoutManager2 = new LinearLayoutManager(getContext());
        mPoiNearResultRecyclerView.setLayoutManager(mLayoutManager2);
        mPoiNearResultRecyclerView.setAdapter(mSelectAddressSubPoiAdapter);
        mSelectAddressSubPoiAdapter.onClickListener = this;

        Utils.setViewTypeForTag(mBackButton, ViewType.VIEW_TYPE_CLOSE);
        mBackButton.setOnClickListener(this);

        mMapView.onCreate(savedInstanceState);

        init();
        initUiSetting();

        return v;
    }

    @Override
    public void onClick(View v) {
        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        int extraInfo = Utils.getExtraInfoOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_POI_ITEM:
                mSearchEdittext.clearFocus();
                Utils.hideKeyboard(getView());

                PoiListModel poiListModel = (PoiListModel) mSelectAddressAdapter.data.get(position);
                centerTarget = new LatLng(poiListModel.latLonPoint.getLatitude(), poiListModel
                        .latLonPoint.getLongitude());
                mPoiResultRecyclerView.setVisibility(View.GONE);
                changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition
                        (centerTarget, 16, 30, 0)));

                //搜索选中点附近POI
                if (centerTarget != null) {
                    searchSubPoi(centerTarget, queryWord);
                }
                break;
            case VIEW_TYPE_NEAR_POI_ITEM:
                PoiListModel subPoiListModel = (PoiListModel) mSelectAddressSubPoiAdapter.data
                        .get(position);
                Intent intent = new Intent();
                intent.putExtra(Key.KEY_REGION_CODE.getValue(), subPoiListModel.regionCode);
                intent.putExtra(Key.KEY_REGION.getValue(), subPoiListModel.content);
                intent.putExtra(Key.KEY_TITLE.getValue(), subPoiListModel.title);
                intent.putExtra(Key.KEY_LATITUDE.getValue(), subPoiListModel.latLonPoint
                        .getLatitude());
                intent.putExtra(Key.KEY_LONGITUDE.getValue(), subPoiListModel.latLonPoint
                        .getLongitude());
                setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
                break;
            case VIEW_TYPE_CLOSE:
                finish();
                break;
            default:
                super.onClick(v);
        }
    }

    //根据输入自动提示
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        queryWord = s.toString();
        //第二个参数传入null或者“”代表在全国进行检索，否则按照传入的city进行检索
        InputtipsQuery inputquery = new InputtipsQuery(queryWord, city);
        inputquery.setCityLimit(true);//限制在当前城市
        Inputtips inputTips = new Inputtips(getActivity(), inputquery);
        inputTips.setInputtipsListener(this);
        inputTips.requestInputtipsAsyn();
    }

    //根据输入自动提示回调
    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        if (list.size() != 0) {
            mSelectAddressAdapter.data.clear();
            mPoiResultRecyclerView.setVisibility(View.VISIBLE);
            mPoiLayout.setVisibility(View.GONE);
            for (int j = 0; j < list.size(); j++) {
                PoiListModel poiListModel = new PoiListModel();
                poiListModel.title = list.get(j).getName();
                poiListModel.content = list.get(j).getDistrict();
                poiListModel.latLonPoint = list.get(j).getPoint();
                mSelectAddressAdapter.data.add(poiListModel);
            }
            mSelectAddressAdapter.notifyDataSetChanged();
        } else {
            mPoiResultRecyclerView.setVisibility(View.GONE);
        }
    }

    //通过回调接口 onPoiSearched 解析返回的结果，将查询到的 POI 以绘制点的方式显示在地图上
    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (!Utils.isNull(poiResult)) {
            ArrayList<PoiItem> pois = poiResult.getPois();
            mSelectAddressSubPoiAdapter.data.clear();
            mPoiResultRecyclerView.setVisibility(View.GONE);
            mPoiLayout.setVisibility(View.VISIBLE);
            for (int j = 0; j < pois.size(); j++) {
                PoiItem item = pois.get(j);
                PoiListModel poiListModel = new PoiListModel();
                poiListModel.title = item.getTitle();
                poiListModel.content = item.getProvinceName() + item.getCityName() + item
                        .getAdName();
                poiListModel.latLonPoint = item.getLatLonPoint();
                poiListModel.regionCode = formatCodeString(item.getAdCode());
                mSelectAddressSubPoiAdapter.data.add(poiListModel);
            }

            mSelectAddressSubPoiAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(), "对不起，没有搜索到相关数据！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * 对地图添加onMapIsAbroadListener,移动地图所触发的事件
     */
    private void setUpMap() {
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                centerTarget = cameraPosition.target;
                searchSubPoi(centerTarget, "");
            }

            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
            }
        });
    }

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update) {

        aMap.moveCamera(update);

    }

    /**
     * 搜索附近POI
     */
    private void searchSubPoi(LatLng centerTarget, String queryWord) {
        initQuery(queryWord);
        poiSearch = new PoiSearch(getActivity(), query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(centerTarget.latitude,
                centerTarget.longitude), 1000,true));//设置搜索区域为以lp点为圆心，其周围1000米范围;
        poiSearch.searchPOIAsyn();// 异步搜索
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mMapView.getMap();
        }

        double lat;
        double lon;
        if (!Utils.isNull(App.getInstance().lat)) {
            lat = Double.parseDouble(App.getInstance().lat);
            lon = Double.parseDouble(App.getInstance().lng);
        } else {
            lat = 39.90403;
            lon = 116.407525;
        }


        Intent intent = getActivity().getIntent();
        Double latitude = intent.getDoubleExtra(Key.KEY_LATITUDE.getValue(), lat);
        Double longitude = intent.getDoubleExtra(Key.KEY_LONGITUDE.getValue(), lon);
        centerTarget = new LatLng(latitude, longitude);
        //设置中心点和缩放比例地,图缩放级别范围为【4-20级】，值越大地图越详细，反之；
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerTarget, 15));
        setUpMap();
    }

    /**
     * 初始化AMap对象
     */
    private void initQuery(String queryWord) {
        query = new PoiSearch.Query(queryWord, "", city);//
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);//设置查询页码；0是第一页！0是第一页！0是第一页！
    }

    /**
     * 初始化控件
     */
    private void initUiSetting() {
        UiSettings mUiSettings;//定义一个UiSettings对象
        mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setMyLocationButtonEnabled(false); //显示默认的定位按钮
        aMap.setLocationSource(this);//通过aMap对象设置定位数据源的监听
        aMap.setMyLocationEnabled(true);// 可触发定位并显示当前位置
        mUiSettings.setZoomControlsEnabled(false);//不显示放大/缩小按钮
    }

    private String formatCodeString(String input) {
        String regex = "(.{2})";
        input = input.replaceAll(regex, "$1,");
        input.substring(0, input.length() - 1);
        return input.substring(0, input.length() - 1);
    }

    //定位回调
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        //Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deactivate() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}
