package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.szy.common.Activity.RegionActivity;
import com.szy.common.Adapter.TextWatcherAdapter;
import com.szy.common.Fragment.RegionFragment;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Adapter.AddressReceiveDetailPoiAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Address.Model;
import com.szy.yishopcustomer.ResponseModel.Region.ResponseRegionModel;
import com.szy.yishopcustomer.ResponseModel.RegionName.ResponseRegionNameModel;
import com.szy.yishopcustomer.ResponseModel.SelectAddress.PoiListModel;
import com.szy.yishopcustomer.Service.Location;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.View.PullableRecyclerView;

/**
 * Created by zongren on 16/4/26.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 * 新增or编辑收货地址
 */
public class AddressFragment extends YSCBaseFragment implements EditText.OnEditorActionListener,
        OnPullListener, View.OnFocusChangeListener, ViewTreeObserver.OnScrollChangedListener,
        TextWatcherAdapter.TextWatcherListener, Inputtips.InputtipsListener, PoiSearch
                .OnPoiSearchListener {
    @BindView(R.id.fragment_address_layout)
    LinearLayout layoutPartOne;
    @BindView(R.id.fragment_address_poi_list)
    PullableRecyclerView address_poi_list;/*
    @BindView(R.id.fragment_address_poi_list_parent)
    public View address_poi_list_parent;*/

    @BindView(R.id.fragment_address_regionLayout)
    LinearLayout regionLayout;
    @BindView(R.id.fragment_address_consignee_commonEditText)
    CommonEditText consigneeCommonEditText;
    @BindView(R.id.fragment_address_phone_commonEditText)
    CommonEditText phoneCommonEditText;
    @BindView(R.id.fragment_address_region_valueTextView)
    TextView regionValueTextView;

    @BindView(R.id.fragment_address_detail_commonEditText)
    CommonEditText detailCommonEditText;
    @BindView(R.id.fragment_address_detail_labelTextView_complete)
    TextView detailCommonTextView;
    @BindView(R.id.bottom_button)
    TextView mConfirmButton;

    private String mAddressId;
    private int mAddressType;
    private Model mModel = new Model();

    //除去标题栏的view高度
    private int viewHeight;

    //poi搜索
    private String city = App.getInstance().city;
    private AddressReceiveDetailPoiAdapter mSelectAddressAdapter;

    public void deleteAddress(int alertInfo) {
        showConfirmDialog(alertInfo, ViewType.VIEW_TYPE_CLEAR_CONFIRM.ordinal());
    }

    @Override
    public void onConfirmDialogConfirmed(int viewType, int position, int extraInfo) {
        switch (ViewType.valueOf(viewType)) {
            case VIEW_TYPE_CLEAR_CONFIRM:
                deleteAddressAction(mAddressId);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (RequestCode.valueOf(requestCode)) {
            case REQUEST_CODE_REGION_CODE:
                if (data != null) {
                    Bundle addressDate = data.getExtras();
                    String regionName = addressDate.getString(RegionFragment.KEY_REGION_LIST);
                    String regionCode = addressDate.getString(RegionFragment.KEY_REGION_CODE);

                    city = getTwoForStringSplit(regionName);

                    mModel.data.model.regionName = regionName;
                    mModel.data.model.region_code = regionCode;
                    setUpAdapterData();
                }
                break;
            case REQUEST_CODE_ADDRESS:

                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCanceled(PullableComponent pullableComponent) {
        mRequestQueue.cancelAll();
    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        refresh();
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {

    }


    /**
     * @param view
     */
    @Override
    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }
        ViewType viewType = Utils.getViewTypeOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_REGION:
                openRegionActivity(mModel.data.model.region_code);
                break;
            case VIEW_TYPE_CONFIRM:
                submit();
                break;
            case VIEW_TYPE_ADDRESS:
                if (view instanceof EditText) {
                    EnhancedInputForPOI();
                } else {
                    EnhancedInputForPOIClose();
                }
                break;
            case VIEW_TYPE_NEAR_POI_ITEM:
                int position = Utils.getPositionOfTag(view);
                PoiListModel subPoiListModel = (PoiListModel) mSelectAddressAdapter.data.get(position);

                mModel.data.model.address_detail = subPoiListModel.content + " " +
                        subPoiListModel.title;
                //mModel.data.model.region_code = subPoiListModel.regionCode;

                mModel.data.model.address_lat = subPoiListModel.latLonPoint.getLatitude() + "";
                mModel.data.model.address_lng = subPoiListModel.latLonPoint.getLongitude() + "";

                setUpAdapterData();
                break;
            default:
                super.onClick(view);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_address;
        Bundle arguments = getArguments();
        mModel = new Model();
        if (arguments != null) {
            mAddressId = arguments.getString(Key.KEY_ADDRESS_ID.getValue());
            mAddressType = arguments.getInt(Key.KEY_ADDRESS_TYPE.getValue());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        initView();

        refresh();

        return view;
    }

    private void initView() {
        consigneeCommonEditText.setOnFocusChangeListener(this);
        consigneeCommonEditText.setOnEditorActionListener(this);
        consigneeCommonEditText.setTextWatcherListener(this);

        phoneCommonEditText.setOnEditorActionListener(this);
        phoneCommonEditText.setOnFocusChangeListener(this);
        phoneCommonEditText.setTextWatcherListener(this);

        Utils.setViewTypeForTag(detailCommonEditText, ViewType.VIEW_TYPE_ADDRESS);
        detailCommonEditText.setOnClickListener(this);
        detailCommonEditText.setOnEditorActionListener(this);
        detailCommonEditText.setOnFocusChangeListener(this);
        detailCommonEditText.setTextWatcherListener(this);

        Utils.setViewTypeForTag(detailCommonEditText, ViewType.VIEW_TYPE_ADDRESS);
        detailCommonEditText.setOnClickListener(this);

        Utils.setViewTypeForTag(detailCommonTextView, ViewType.VIEW_TYPE_ADDRESS);
        detailCommonTextView.setOnClickListener(this);

        Utils.setViewTypeForTag(mConfirmButton, ViewType.VIEW_TYPE_CONFIRM);
        mConfirmButton.setOnClickListener(this);

        if (mAddressType == 2) {
            mConfirmButton.setText(getResources().getString(R.string.address_save));
        } else {
            mConfirmButton.setText(getResources().getString(R.string.buttonSaveAndUse));
        }

        mSelectAddressAdapter = new AddressReceiveDetailPoiAdapter();
        mSelectAddressAdapter.onClickListener = this;
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        mConfirmButton.setVisibility(View.VISIBLE);
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GET_REGION_NAME:
                refreshRegionNameCallback(response);
                break;
            case HTTP_ADDRESS:
                refreshCallback(response);
                break;
            case HTTP_SUBMIT:
                submitCallback(response);
                break;
            case HTTP_DELETE:
                deleteAddressCallback(response);
                break;
            case HTTP_REGION_NAME:
                getRegionNameCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
        }
    }

    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            Utils.hideKeyboard(view);

            switch (view.getId()) {
                case R.id.fragment_address_consignee_commonEditText:
                    mModel.data.model.consignee = view.getText().toString();
                    break;
                case R.id.fragment_address_phone_commonEditText:
                    mModel.data.model.mobile = view.getText().toString();
                    break;
                case R.id.fragment_address_detail_commonEditText:
                    mModel.data.model.address_detail = view.getText().toString();
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {

        if (!hasFocus) {
            switch (view.getId()) {
                case R.id.fragment_address_consignee_commonEditText:
                    mModel.data.model.consignee = ((TextView) view).getText().toString();
                    break;
                case R.id.fragment_address_phone_commonEditText:
                    mModel.data.model.mobile = ((TextView) view).getText().toString();
                    break;
                case R.id.fragment_address_detail_commonEditText:
                    mModel.data.model.address_detail = ((TextView) view).getText().toString();
                    break;
            }
        } else {
            switch (view.getId()) {
                case R.id.fragment_address_detail_commonEditText:
                    //获取焦点的时候，显示下拉列表，上移整体的reclyView，显示右侧的确认,隐藏最底下的确认提交按钮
                    EnhancedInputForPOI();
                    searchPoiInfo("1");
                    break;
            }
        }
    }

    /**
     * 详细地址获取焦点后的操作
     */
    private void EnhancedInputForPOI() {

        layoutPartOne.setVisibility(View.GONE);
        //隐藏保存并使用按钮
        mConfirmButton.setVisibility(View.GONE);

        //显示poi列表 和 详情的完成按钮
/*        ViewGroup.LayoutParams lp = address_poi_list_parent.getLayoutParams();
        lp.height = Utils.getAppAreaHeight(getActivity()) - Utils.dpToPx(getActivity(), 100);
        address_poi_list_parent.setLayoutParams(lp);*/

        address_poi_list.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (address_poi_list.getAdapter() != null) {
            address_poi_list.getAdapter();
        } else {
            address_poi_list.setAdapter(mSelectAddressAdapter);
        }

        /*address_poi_list_parent.setVisibility(View.VISIBLE);*/
        address_poi_list.setVisibility(View.VISIBLE);
        detailCommonTextView.setVisibility(View.VISIBLE);
    }

    /**
     * 详细地址点击完成后的操作
     */
    private void EnhancedInputForPOIClose() {

        layoutPartOne.setVisibility(View.VISIBLE);
        //隐藏保存并使用按钮
        mConfirmButton.setVisibility(View.VISIBLE);

        //隐藏poi列表
        /*address_poi_list_parent.setVisibility(View.INVISIBLE);*/
        address_poi_list.setVisibility(View.GONE);
        detailCommonTextView.setVisibility(View.GONE);

        setUpAdapterData();

    }

    @Override
    public void onScrollChanged() {
        //hideKeyboard();
    }

    @Override
    public void onTextChanged(EditText view, String text) {
        switch (view.getId()) {
            case R.id.fragment_address_consignee_commonEditText:
                mModel.data.model.consignee = view.getText().toString();
                break;
            case R.id.fragment_address_phone_commonEditText:
                mModel.data.model.mobile = view.getText().toString();
                break;
            case R.id.fragment_address_detail_commonEditText:
                String queryWord = view.getText().toString();
                mModel.data.model.address_detail = queryWord;

                if (Utils.isNull(queryWord)) {
                    queryWord = "1";
                }


                if (view.isFocused()) {
                    searchPoiInfo(queryWord);
                }
                break;
        }
    }

    private void searchPoiInfo(final String queryWord) {

        int currentPage = 0;
        PoiSearch.Query query = null;

        if (TextUtils.isEmpty(queryWord)) {
            query = new PoiSearch.Query(queryWord, "", city);
        } else {
            query = new PoiSearch.Query(queryWord, "", city);
        }

        query.setPageSize(1);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
//                LatLonPoint lp = new LatLonPoint(39.972231, 119.59871);
//                LatLonPoint lp = new LatLonPoint(latlng.latitude, latlng.longitude);
        PoiSearch poiSearch = new PoiSearch(getContext(), query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {

                if (!Utils.isNull(poiResult)) {
                    ArrayList<PoiItem> pois = poiResult.getPois();
                    for (int j = 0; j < 1; j++) {
                        PoiItem item = pois.get(j);
                        searchPoiInfo(queryWord, item.getLatLonPoint());
                    }
                } else {
                    if (!TextUtils.isEmpty(queryWord)) {
                        Toast.makeText(getActivity(), "对不起，没有搜索到相关数据！", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
//                poiSearch.setBound(new PoiSearch.SearchBound(lp, 5000, true));//
        poiSearch.searchPOIAsyn();// 异步搜索
    }

    private void searchPoiInfo(String queryWord, LatLonPoint latlon) {
        int currentPage = 0;
        PoiSearch.Query query = new PoiSearch.Query(queryWord, "", city);
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        PoiSearch poiSearch = new PoiSearch(getContext(), query);

        poiSearch.setBound(new PoiSearch.SearchBound(latlon, 1000));//设置周边搜索的中心点以及半径
        poiSearch.setOnPoiSearchListener(this);
//                poiSearch.setBound(new PoiSearch.SearchBound(lp, 5000, true));//
        poiSearch.searchPOIAsyn();// 异步搜索
    }

    private void changeDefault() {
        mModel.data.isDefaultAddress = mModel.data.isDefaultAddress == 1 ? 0 : 1;
        setUpAdapterData();
    }

    private boolean check() {
        String message = "";
        if (Utils.isNull(mModel.data.model.consignee)) {
            message = "请输入收货人";
        } else if (Utils.isNull(mModel.data.model.mobile)) {
            message = "请输入手机号";
        } else if (!Utils.checkPhoneNumber(mModel.data.model.mobile)) {
            message = "手机号格式不正确";
        } else if (Utils.isNull(mModel.data.model.region_code)) {
            message = "请选择地区";
        } else if (Utils.isNull(mModel.data.model.address_detail)) {
            message = "请输入详细地址";
        }

        if (Utils.isNull(message)) {
            return true;
        } else {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void deleteAddressAction(String addressId) {
        CommonRequest request = new CommonRequest(Api.API_DELETE_ADDRESS, HttpWhat
                .HTTP_DELETE.getValue());
        request.setAjax(true);
        request.add("address_id", addressId);
        addRequest(request);
    }

    private void deleteAddressCallback(String response) {
        HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager
                .HttpResultCallBack<ResponseCommonModel>() {
            @Override
            public void onSuccess(ResponseCommonModel back) {
                //data不为空，返回值为剩余地址数量
                Toast.makeText(getContext(), back.message, Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_DELETE_ADDRESS
                        .getValue()));
                finish();
            }
        }, true);
    }

    private String getApi() {
        if (mAddressId != null) {
            return Api.API_EDIT_ADDRESS + "?address_id=" + mAddressId;
        } else {
            return Api.API_ADD_ADDRESS;
        }
    }

    private void getRegionName(String regionCode) {
        CommonRequest request = new CommonRequest(Api.API_SALE_REGION_NAME, HttpWhat
                .HTTP_REGION_NAME.getValue());
        request.add("region_code", regionCode);
        addRequest(request);
    }

    private void getRegionNameCallback(String response) {
        Utils.showSoftInputFromWindowTwo(getActivity(), consigneeCommonEditText);
        HttpResultManager.resolve(response, ResponseRegionNameModel.class, new HttpResultManager
                .HttpResultCallBack<ResponseRegionNameModel>() {
            @Override
            public void onSuccess(ResponseRegionNameModel back) {
                if (Utils.isNull(mModel.data.model.regionName)) {
                    mModel.data.model.regionName = back.data.region_name;

                    city = getTwoForStringSplit(back.data.region_name);
                    setUpAdapterData();
                }
            }
        }, true);
    }


    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            Utils.hideKeyboard(view);
        }
    }

    /**
     * 选择地区
     *
     * @param regionCode
     */
    private void openRegionActivity(String regionCode) {
        Intent intent = new Intent(getContext(), RegionActivity.class);
        intent.putExtra(RegionFragment.KEY_REGION_CODE, regionCode);
        intent.putExtra(RegionFragment.KEY_API, Api.API_REGION_LIST);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_REGION_CODE.getValue());
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(getApi(), HttpWhat.HTTP_ADDRESS.getValue());
        request.setAjax(true);
        addRequest(request);
    }

    private void refreshCallback(String response) {
        HttpResultManager.resolve(response, Model.class, new HttpResultManager
                .HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model back) {
                mModel = back;

                Utils.setViewTypeForTag(regionLayout, ViewType.VIEW_TYPE_REGION);
                regionLayout.setOnClickListener(AddressFragment.this);

                mModel.data.switchEnabled = mModel.data.isDefaultAddress != 1;
                if (mAddressId != null) {
                    getRegionName(mModel.data.model.region_code);
                } else {
                    showLocation();
                }
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
            }
        });
    }

    private void refreshRegionNameCallback(String response) {
        Utils.showSoftInputFromWindow(getActivity(), consigneeCommonEditText);
        HttpResultManager.resolve(response, ResponseRegionModel.class, new HttpResultManager
                .HttpResultCallBack<ResponseRegionModel>() {
            @Override
            public void onSuccess(ResponseRegionModel back) {
                mModel.data.model.regionName = back.data.region_name;
                mModel.data.model.region_code = back.data.region_code;
                setUpAdapterData();
            }
        }, true);

    }

    private void setUpAdapterData() {
        if (mModel.data.model.consignee != null) {
            consigneeCommonEditText.setText(mModel.data.model.consignee);
            consigneeCommonEditText.setSelection(mModel.data.model.consignee.length());
        }
        if (mModel.data.model.mobile != null) {
            phoneCommonEditText.setText(mModel.data.model.mobile);
        }
        if (mModel.data.model.regionName != null) {
            regionValueTextView.setText(mModel.data.model.regionName);
        }
        if (mModel.data.model.address_detail != null) {
            detailCommonEditText.setText(mModel.data.model.address_detail);
        }
    }

    private void showLocation() {
        final CommonRequest request = new CommonRequest(Api.API_GET_REGION_NAME, HttpWhat
                .HTTP_GET_REGION_NAME.getValue());
        Location.locationCallback(new Location.OnLocationListener.DefaultLocationListener() {
            @Override
            public void onFinished(AMapLocation amapLocation) {
                if (!Utils.isNull(App.getInstance().lat) && !Utils.isNull(App.getInstance().lng) && !App
                        .getInstance().lat.equals("0") && !App.getInstance().lat.equals("0")) {

                    mModel.data.model.address_lat = App.getInstance().lat;
                    mModel.data.model.address_lng = App.getInstance().lng;

                    request.add("lat", App.getInstance().lat);
                    request.add("lng", App.getInstance().lng);
                    addRequest(request);
                } else {
                    Utils.toastUtil.showToast(getActivity(), "定位失败，请手动选择！");
                    request.add("lat", "");
                    request.add("lng", "");
                    addRequest(request);
                }
            }
        });
    }

    /***
     * 新增(编辑)收货地址 保存
     */
    private void submit() {
        if (!check()) {
            return;
        }
        CommonRequest request = new CommonRequest(getApi(), HttpWhat.HTTP_SUBMIT.getValue(),
                RequestMethod.POST);
        request.setAjax(true);
        request.add("UserAddressModel[address_name]", "");
        request.add("UserAddressModel[consignee]", mModel.data.model.consignee);
        request.add("UserAddressModel[address_detail]", mModel.data.model.address_detail);
        request.add("UserAddressModel[mobile]", mModel.data.model.mobile);
        request.add("UserAddressModel[tel]", mModel.data.model.tel);
        request.add("UserAddressModel[email]", mModel.data.model.email);
        request.add("UserAddressModel[region_code]", mModel.data.model.region_code);
        if (!Utils.isNull(mModel.data.model.address_lat)) {
            request.add("UserAddressModel[address_lat]", mModel.data.model.address_lat);
            request.add("UserAddressModel[address_lng]", mModel.data.model.address_lng);
        }
        //1 集宝箱  2 同城生活
        if (mAddressType != 2) {
            mAddressType = 1;
        }
        //address_from
        request.add("UserAddressModel[address_from]", mAddressType);
        addRequest(request);

    }

    private void submitCallback(String response) {
        HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager
                .HttpResultCallBack<ResponseCommonModel>() {
            @Override
            public void onSuccess(ResponseCommonModel back) {
                Toast.makeText(getContext(), back.message, Toast.LENGTH_SHORT).show();
                if (mAddressId != null) {
                    EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_UPDATE_ADDRESS
                            .getValue()));
                } else {
                    EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_ADD_ADDRESS
                            .getValue()));
                }
                finish();
            }
        }, true);
    }

    //暂时不使用tip获取poi联想，这个方法没有被使用
    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        if (list.size() != 0) {
            mSelectAddressAdapter.data.clear();
            for (int j = 0; j < list.size(); j++) {
                PoiListModel poiListModel = new PoiListModel();
                poiListModel.title = list.get(j).getName();
                poiListModel.content = list.get(j).getDistrict();
                poiListModel.latLonPoint = list.get(j).getPoint();
                poiListModel.address = list.get(j).getAddress();
                mSelectAddressAdapter.data.add(poiListModel);
            }
            mSelectAddressAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (!Utils.isNull(poiResult)) {
            ArrayList<PoiItem> pois = poiResult.getPois();
            mSelectAddressAdapter.data.clear();
            for (int j = 0; j < pois.size(); j++) {
                PoiItem item = pois.get(j);
                PoiListModel poiListModel = new PoiListModel();
                poiListModel.title = item.getTitle();
                poiListModel.content = item.getSnippet();
                poiListModel.latLonPoint = item.getLatLonPoint();
                poiListModel.regionCode = formatCodeString(item.getAdCode());
                mSelectAddressAdapter.data.add(poiListModel);
            }

            mSelectAddressAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
    }

    private String formatCodeString(String input) {
        String regex = "(.{2})";
        input = input.replaceAll(regex, "$1,");
        input.substring(0, input.length() - 1);
        return input.substring(0, input.length() - 1);
    }

    private String getTwoForStringSplit(String t_regionName) {

        String[] sss = t_regionName.split(" ");

        String locaitionName = t_regionName;
        if (sss != null && sss.length >= 1) {
            locaitionName = sss[0];
        }

        Geocoder geocoder = new Geocoder(getContext());
        try {
            List<Address> addresses = geocoder.getFromLocationName(locaitionName, 1);
            if (addresses != null && addresses.size() > 0) {

                mModel.data.model.address_lat = addresses.get(0).getLatitude() + "";
                mModel.data.model.address_lng = addresses.get(0).getLongitude() + "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (sss != null && sss.length >= 2) {
            return sss[1];
        }

        return t_regionName;
    }
}
