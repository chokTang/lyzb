package com.szy.yishopcustomer.Activity.samecity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.*;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.szy.common.View.CommonEditText;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.CityListAdapter;
import com.szy.yishopcustomer.Adapter.CityResultListAdapter;
import com.szy.yishopcustomer.Adapter.CountyListAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.CityList.CityNameModel;
import com.szy.yishopcustomer.ResponseModel.SameCity.CityList.CountyModel;
import com.szy.yishopcustomer.ResponseModel.SameCity.CityList.SortModel;
import com.szy.yishopcustomer.Service.Location;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.ListItemDecoration;
import com.szy.yishopcustomer.Util.LogUtils;
import com.szy.yishopcustomer.Util.PinyinComparator;
import com.szy.yishopcustomer.Util.PinyinUtils;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;
import com.szy.yishopcustomer.View.SideBar;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wyx
 * @role 城市列表 定位失败-选择城市
 * @time 2018 14:29
 */

public class CityListActivity extends Activity {

    Toolbar mToolbar;
    CommonEditText mCommonEditText_Seach;
    TextView mTextView_CurrLocation;
    TextView mTextView_ChooseCounty;

    public static final int KEY_FINISH = 639;
    public static final int REQUEST_CODE = 63669;
    //城市列表
    private CommonRecyclerView item_recy_city_list;
    private TextView item_city_dialog;
    private TextView tv_refresh;
    private SideBar item_sidebar;
    private LinearLayoutManager manager;
    private List<SortModel> SourceDateList;

    private CityListAdapter mCityListAdapter;
    private List<CityNameModel> mCityNameModels;

    //区县列表
    private CommonRecyclerView county_list_recycle;
    private List<CountyModel> mCountyModelList = new ArrayList<>();
    private CountyListAdapter mCountyListAdapter;

    //搜索结果
    private CommonRecyclerView sreach_result_recycle;
    private CityResultListAdapter resultListAdapter;
    private List<SortModel> mCityResultModels = new ArrayList<>();
    private RelativeLayout data_rl;
    private RelativeLayout rl_no_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citylist);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_same_city_list);
        mCommonEditText_Seach = (CommonEditText) findViewById(R.id.edt_city_list_seach);
        mTextView_CurrLocation = (TextView) findViewById(R.id.tv_city_list_curr_location);
        mTextView_ChooseCounty = (TextView) findViewById(R.id.tv_city_list_choose_county);
        tv_refresh = (TextView) findViewById(R.id.tv_refresh);

        county_list_recycle = (CommonRecyclerView) findViewById(R.id.county_list_recycle);

        sreach_result_recycle = (CommonRecyclerView) findViewById(R.id.sreach_result_recycle);
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        sreach_result_recycle.setLayoutManager(manager1);
        resultListAdapter = new CityResultListAdapter(this, mCityResultModels);
        sreach_result_recycle.setAdapter(resultListAdapter);


        county_list_recycle.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        county_list_recycle.addItemDecoration(new ListItemDecoration(this, 1, Color.WHITE, 3));

        mCountyListAdapter = new CountyListAdapter(this);
        county_list_recycle.setAdapter(mCountyListAdapter);

        item_recy_city_list = (CommonRecyclerView) findViewById(R.id.item_recy_city_list);
        item_city_dialog = (TextView) findViewById(R.id.item_city_dialog);
        item_sidebar = (SideBar) findViewById(R.id.item_sidebar);
        data_rl = (RelativeLayout) findViewById(R.id.data_rl);
        rl_no_date = (RelativeLayout) findViewById(R.id.rl_no_date);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (App.getInstance().province != null && App.getInstance().district != null && App.getInstance().city != null) {

            if (App.getInstance().city_name != null) {
                mTextView_CurrLocation.setText("当前:" + App.getInstance().city_name);
            } else {
                mTextView_CurrLocation.setText("当前:" + App.getInstance().province + "-" + App.getInstance().district);
            }
        } else {
            mTextView_CurrLocation.setText("定位失败,请选择城市");
        }

        getCityListData();

        tv_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location.locationCallback(new Location.OnLocationListener() {

                    @Override
                    public void onSuccess(AMapLocation amapLocation) {
                        App.getInstance().addressDetail = amapLocation.getAoiName();
                        App.getInstance().isLocation = true;
                    }

                    @Override
                    public void onError(AMapLocation amapLocation) {

                    }

                    @Override
                    public void onFinished(AMapLocation amapLocation) {

                    }
                });
            }
        });

        //选择区县
        mTextView_ChooseCounty.setOnClickListener(new View.OnClickListener() {

            boolean isCounty = false;

            @Override
            public void onClick(View v) {

                if (isCounty == false) {
                    mCountyListAdapter.mCountyModels.clear();
                    getCountyList();
                    isCounty = true;
                } else {
                    county_list_recycle.setVisibility(View.GONE);
                    isCounty = false;
                }
            }
        });

        //城市列表搜索结果 点击
        resultListAdapter.setListener(new CityResultListAdapter.ItemListener() {


            @Override
            public void onItemListener(SortModel model, int position) {

                App.getInstance().city_code = model.getCode();
                App.getInstance().city_name = model.getName();
                App.getInstance().home_area_code = model.getStandardCode();
                App.getInstance().city_province = model.getProvince_name();

                SharedPreferencesUtils.setParam(CityListActivity.this, Key.KEY_CITY_PROVINCE.getValue(), model.getProvince_name());
                SharedPreferencesUtils.setParam(CityListActivity.this, Key.KEY_CITY_LATELY_CODE.getValue(), model.getCode());
                SharedPreferencesUtils.setParam(CityListActivity.this, Key.KEY_CITY_STANDER_CODE.getValue(), model.getStandardCode());
                SharedPreferencesUtils.setParam(CityListActivity.this, Key.KEY_CITY_LATELY.getValue(), model.getName());
                App.getInstance().isCounty = false;
                App.getInstance().clickChangeCity = true;
                App.getInstance().isCityChanage = true;
                setResult(KEY_FINISH);
                finish();
            }

        });

        //区县列表 点击
        mCountyListAdapter.setItemListener(new CountyListAdapter.ItemListener() {
            @Override
            public void onItemClick(CountyModel model) {
                App.getInstance().city_code = model.regionCode;
                App.getInstance().home_area_code = model.standardCode;
                App.getInstance().city_name = model.regionName;
                App.getInstance().isCounty = true;

                App.getInstance().isCityChanage = true;
                App.getInstance().clickChangeCity = true;
                setResult(KEY_FINISH);
                finish();
            }
        });

        mCommonEditText_Seach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                onSearch(s.toString());
            }
        });
    }

    /**
     * 获取城市列表数据
     */
    void getCityListData() {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_CITY_HOME_CITY_LIST, RequestMethod.GET);
        RequestAddHead.addNoHttpHead(request, this, Api.API_CITY_HOME_CITY_LIST, "GET");

        requestQueue.add(HttpWhat.HTTP_HOME_CITY_LIST.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                LogUtils.Companion.e("城市列表数据源" + response);
                if (response.responseCode() == 200) {
                    mCityNameModels = JSON.parseArray(response.get(), CityNameModel.class);
                    initCityData();

                    item_sidebar.setVisibility(View.VISIBLE);

                    if (App.getInstance().isCounty == false) {
                        //选择区县 显示
                        mTextView_ChooseCounty.setVisibility(View.VISIBLE);
                    }
                } else {

                    item_sidebar.setVisibility(View.GONE);
                    Toast.makeText(CityListActivity.this, "数据异常,请稍候尝试", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                item_recy_city_list.showOfflineView();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    //获取区县数据
    void getCountyList() {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_CITY_HOME_COUNTY_LIST, RequestMethod.GET);
        RequestAddHead.addNoHttpHead(request, this, Api.API_CITY_HOME_COUNTY_LIST, "GET");

        if (App.getInstance().city_name != null) {
            request.add("city", App.getInstance().city_name);
            request.add("province", App.getInstance().city_province);
        } else {
            request.add("city", App.getInstance().city);
            request.add("province", App.getInstance().province);
        }

        requestQueue.add(HttpWhat.HTTP_HOME_COUNTY_LIST.getValue(), request, new OnResponseListener<String>() {

            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                if (response.responseCode() == 200) {

                    mCountyModelList = JSON.parseArray(response.get(), CountyModel.class);
                    if (mCountyModelList.size() > 0) {
                        county_list_recycle.setVisibility(View.VISIBLE);

                        mCountyListAdapter.mCountyModels.addAll(mCountyModelList);
                        mCountyListAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(CityListActivity.this, response.get(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CityListActivity.this, "数据异常,请稍候尝试", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Toast.makeText(CityListActivity.this, "数据异常,请稍候尝试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void initCityData() {
        PinyinComparator pinyinComparator = new PinyinComparator();
        item_sidebar.setTextView(item_city_dialog);

        item_sidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {

                if ("A".equals(s)) {
                    manager.scrollToPositionWithOffset(1, 0);
                } else {
                    //该字母首次出现的位置
                    int position = mCityListAdapter.getPositionForSection(s.charAt(0));
                    if (position != -1) {
                        manager.scrollToPositionWithOffset(position, 0);
                    }
                }
            }
        });

        SourceDateList = filledData(mCityNameModels);

        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        item_recy_city_list.setLayoutManager(manager);

        mCityListAdapter = new CityListAdapter(this, SourceDateList);
        item_recy_city_list.setAdapter(mCityListAdapter);


        //城市 列表点击
        mCityListAdapter.setListener(new CityListAdapter.ItemListener() {
            @Override
            public void onItemListener() {
                App.getInstance().isCityChanage = true;
                App.getInstance().clickChangeCity = true;
                App.getInstance().isCounty = false;

                setResult(KEY_FINISH);
                finish();
            }

            @Override
            public void onHisItemListener() {
                App.getInstance().isCityChanage = true;
                App.getInstance().clickChangeCity = true;
                App.getInstance().isCounty = false;
                setResult(KEY_FINISH);
                finish();
            }
        });

    }

    private List<SortModel> filledData(List<CityNameModel> nameModels) {
        List<SortModel> mSortList = new ArrayList<>();

        for (int i = 0; i < nameModels.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setProvince_name(nameModels.get(i).parentName);
            sortModel.setName(nameModels.get(i).regionName);
            sortModel.setCode(nameModels.get(i).regionCode);
            sortModel.setStandardCode(nameModels.get(i).standardCode);
            //汉字转换成拼音
            String pinyin = PinyinUtils.getPingYin(nameModels.get(i).regionName);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setLetters(sortString.toUpperCase());
            } else {
                sortModel.setLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;
    }

    private void onSearch(final String result) {
        mCityResultModels.clear();
        if (TextUtils.isEmpty(result)) {
            sreach_result_recycle.setVisibility(View.GONE);
            data_rl.setVisibility(View.VISIBLE);
        } else {
            sreach_result_recycle.setVisibility(View.VISIBLE);
            data_rl.setVisibility(View.GONE);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (SortModel item : SourceDateList) {
                    if (item.getName().indexOf(result) != -1) {
                        mCityResultModels.add(item);
                    }
                }
                if (mCityResultModels.size() == 0) {
                    rl_no_date.setVisibility(View.VISIBLE);
                    sreach_result_recycle.setVisibility(View.GONE);
                } else {
                    rl_no_date.setVisibility(View.GONE);
                    sreach_result_recycle.setVisibility(View.VISIBLE);
                    resultListAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
