package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.szy.common.View.CommonEditText;
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

public class SelectDetailAddressFragment extends YSCBaseFragment implements PoiSearch
        .OnPoiSearchListener, TextWatcher, LocationSource, Inputtips.InputtipsListener {

    @BindView(R.id.fragment_address_detail_poiNearResult)
    RecyclerView mPoiNearResultRecyclerView;
    @BindView(R.id.fragment_address_detail_commonEditText)
    CommonEditText mAddressDetailEdittext;

    @BindView(R.id.submit_button)
    TextView mFinishButton;

    private LinearLayoutManager mLayoutManager;
    private SelectAddressSubPoiAdapter mSelectAddressSubPoiAdapter;

    private String city = "";
    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private LatLng centerTarget;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_select_detail_address;

        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        mAddressDetailEdittext.addTextChangedListener(this);
        mSelectAddressSubPoiAdapter = new SelectAddressSubPoiAdapter();
        mLayoutManager = new LinearLayoutManager(getContext());
        mPoiNearResultRecyclerView.setLayoutManager(mLayoutManager);
        mPoiNearResultRecyclerView.setAdapter(mSelectAddressSubPoiAdapter);
        mSelectAddressSubPoiAdapter.onClickListener = this;

        Utils.setViewTypeForTag(mFinishButton, ViewType.VIEW_TYPE_FINISH_BUTTON);
        mFinishButton.setOnClickListener(this);
        mFinishButton.setEnabled(true);
        mFinishButton.setText(getResources().getString(R.string.confirm));
        searchSubPoi(centerTarget);
        return v;
    }

    @Override
    public void onClick(View v) {
        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        int extraInfo = Utils.getExtraInfoOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_NEAR_POI_ITEM:
                PoiListModel subPoiListModel = (PoiListModel) mSelectAddressSubPoiAdapter.data
                        .get(position);
                mAddressDetailEdittext.setText(subPoiListModel.title);
                break;
            case VIEW_TYPE_FINISH_BUTTON:

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
        String queryWord = s.toString();
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
            mSelectAddressSubPoiAdapter.data.clear();

            for (int j = 0; j < list.size(); j++) {
                PoiListModel poiListModel = new PoiListModel();
                poiListModel.title = list.get(j).getName();
                poiListModel.content = list.get(j).getDistrict();
                poiListModel.latLonPoint = list.get(j).getPoint();
                mSelectAddressSubPoiAdapter.data.add(poiListModel);
            }
            mSelectAddressSubPoiAdapter.notifyDataSetChanged();
        } else {
            mPoiNearResultRecyclerView.setVisibility(View.GONE);
        }
    }

    //通过回调接口 onPoiSearched 解析返回的结果，将查询到的 POI 以绘制点的方式显示在地图上
    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (!Utils.isNull(poiResult)) {
            ArrayList<PoiItem> pois = poiResult.getPois();
            mSelectAddressSubPoiAdapter.data.clear();

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
     * 初始化查询
     */
    private void initQuery(String queryWord) {
        query = new PoiSearch.Query(queryWord, "", city);//
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);//设置查询页码
    }


    private String formatCodeString(String input) {
        String regex = "(.{2})";
        input = input.replaceAll(regex, "$1,");
        input.substring(0, input.length() - 1);
        return input.substring(0, input.length() - 1);
    }

    /**
     * 搜索附近POI
     */
    private void searchSubPoi(LatLng centerTarget) {
        initQuery("");
        poiSearch = new PoiSearch(getActivity(), query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(centerTarget.latitude,
                centerTarget.longitude), 1000, true));//设置搜索区域为以lp点为圆心，其周围1000米范围;
        poiSearch.searchPOIAsyn();// 异步搜索
    }

    private void init() {
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
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }
}
