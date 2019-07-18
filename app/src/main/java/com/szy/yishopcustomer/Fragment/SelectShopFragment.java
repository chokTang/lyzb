package com.szy.yishopcustomer.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonEditText;
import com.szy.common.View.CommonRecyclerView;
import com.szy.common.View.CustomProgressDialog;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.MapActivity;
import com.szy.yishopcustomer.Activity.ScanActivityTwo;
import com.szy.yishopcustomer.Activity.ShopPrepareActivity;
import com.szy.yishopcustomer.Adapter.SelectShopListAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.ShopListItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.ShopStreet.Model;
import com.szy.yishopcustomer.Service.Location;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.SelectShopDialog;

import java.util.ArrayList;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Constant.Side;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectShopFragment extends YSCBaseFragment implements OnEmptyViewClickListener,OnPullListener {

    public static CustomProgressDialog mProgress;
    public String name = "";
    @BindView(R.id.imageView_search)
    public ImageView imageView_search;
    @BindView(R.id.activity_search_search_eidttext)
    public CommonEditText mKeywordEditText;
    @BindView(R.id.fragment_shop_street_recyclerView_layout)
    PullableLayout fragment_pullableLayout;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.fragment_shop_street_recyclerView)
    CommonRecyclerView mShopStreetRecyclerView;
    private int mPosition;
    private SelectShopListAdapter mShopStreetAdapter;

    private String clsId;
    private String sort;
    private String distance;
    private String lng;
    private String lat;
    private boolean upDataSuccess = false;
    private int page = 1;
    private int pageCount;

    private Model model;

    private boolean isShowAppBar = true;

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 0) {
                if (mShopStreetRecyclerView.reachEdgeOfSide(Side.BOTTOM)) {
                    if (upDataSuccess) {
                        loadMore();
                    }

                }
            }
        }
    };

    private void loadMore() {
        upDataSuccess = false;
        page++;
        if (page > pageCount) {
            upDataSuccess = false;
            CheckoutDividerModel blankModel = new CheckoutDividerModel();
            mShopStreetAdapter.data.add(blankModel);
            mShopStreetAdapter.notifyDataSetChanged();
            return;
        } else {
            sortRefresh();
        }
    }

    @Override
    public void onClick(View view) {
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        int info = Utils.getExtraInfoOfTag(view);

        switch (viewType) {
            case VIEW_TYPE_SHOP:
                openShopActivity(position);
                break;
            case VIEW_TYPE_SHOP_LOCATION:
                openMapActivity(position);
                break;
            case VIEW_TYPE_SHOP_PREPARE:
                openShopPrepareActivity(position);
                break;
            default:
                super.onClick(view);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_select_shop;
        mShopStreetAdapter = new SelectShopListAdapter();
        mProgress = new CustomProgressDialog(getActivity());
        mProgress.setCanceledOnTouchOutside(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        Intent intent = getActivity().getIntent();
        name = intent.getStringExtra(Key.KEY_KEYWORD.getValue());
        if (!Utils.isNull(name)) {
            getActivity().setTitle("店铺搜索");
        }
        init();

        imageView_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchForKeyWord(mKeywordEditText.getText().toString());
            }
        });
        mKeywordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchForKeyWord(v.getText().toString());
                    return true;
                }
                return false;
            }


        });

        if (!isShowAppBar) {
            appBarLayout.setVisibility(View.GONE);
        }

        fragment_pullableLayout.topComponent.setOnPullListener(this);
        mShopStreetRecyclerView.addOnScrollListener(mOnScrollListener);
        return v;
    }

    public void closeAppbar() {
        isShowAppBar = false;
        if (appBarLayout != null) {
            appBarLayout.setVisibility(View.GONE);
        }
    }

    private void init() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mShopStreetRecyclerView.setLayoutManager(layoutManager);
        mShopStreetRecyclerView.setAdapter(mShopStreetAdapter);
        mShopStreetAdapter.onClickListener = this;
        if (!Utils.isNull(App.getInstance().lat) && !Utils.isNull(App.getInstance().lng)) {
            lat = App.getInstance().lat;
            lng = App.getInstance().lng;
            request();
        } else {
            lat = "0";
            lng = "0";
            request();
        }
    }


    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_REFRESH_NEARBY:
                if (!Utils.isNull(App.getInstance().lat) && !Utils.isNull(App.getInstance().lng)) {
                    lat = App.getInstance().lat;
                    lng = App.getInstance().lng;
                    request();
                } else {
                    lat = "0";
                    lng = "0";
                    request();
                }
                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GET_SHOP_STREET:

                break;

            default:
                super.onRequestFailed(what, response);
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GET_SHOP_STREET:
                shopStreetCallback(response);
                break;
            case HTTP_GET_SHOP_STREET_TWO:
                sortRefreshCallback(response);
                break;
            case HTTP_GET_SHOP_STREET_ONE:
//                categoryOneCallback(response);
                break;

            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }


    private void sortRefreshCallback(String response) {
        upDataSuccess = true;

        mProgress.hide();
        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model mData) {
                pageCount = mData.data.page.page_count;
                if (mData.data.list.size() == 0) {
                    mData.data.list = new ArrayList<>();
                }

                for (ShopListItemModel shopListItemModel : mData.data.list) {
                    mShopStreetAdapter.data.add(shopListItemModel);
                }


                mShopStreetAdapter.notifyDataSetChanged();
                if (mData.data.list.size() == 0) {
                    upDataSuccess = false;
                    mShopStreetRecyclerView.setEmptyImage(R.mipmap.bg_public);
                    mShopStreetRecyclerView.setEmptyTitle(R.string.emptyData);
                    mShopStreetRecyclerView.showEmptyView();
                }
            }
        });
    }

    private void shopStreetCallback(String response) {
        fragment_pullableLayout.topComponent.finish(Result.SUCCEED);
        upDataSuccess = true;
        mShopStreetRecyclerView.addOnScrollListener(mOnScrollListener);
        mShopStreetAdapter.data.clear();

        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model back) {
                model = back;
                pageCount = model.data.page.page_count;
                if (model.data.list == null) {
                    model.data.list = new ArrayList<>();
                }


                for (ShopListItemModel shopListItemModel : model.data.list) {
                    mShopStreetAdapter.data.add(shopListItemModel);
                }


                mShopStreetAdapter.notifyDataSetChanged();
                if (model.data.list.size() == 0) {
                    upDataSuccess = false;
                    mShopStreetRecyclerView.setEmptyImage(R.mipmap.bg_public);
                    mShopStreetRecyclerView.setEmptyTitle(R.string.emptyData);
                    mShopStreetRecyclerView.showEmptyView();
                }
            }
        });
    }

    private void sortRefresh() {
       /* CommonRequest mShopStreetRequest;
        if (!Utils.isNull(name)) {
            mShopStreetRequest = new CommonRequest(Api.API_SHOP_SEARCH, HttpWhat
                    .HTTP_GET_SHOP_STREET_TWO.getValue());
            mShopStreetRequest.add("name", name);
            mShopStreetRequest.add("type", "1");
            mShopStreetRequest.alarm = false;
            mShopStreetRequest.add("cls_id", clsId);
            mShopStreetRequest.add("sort", sort);
            mShopStreetRequest.add("distance", distance);

            mShopStreetRequest.add("page[cur_page]", page);
            addRequest(mShopStreetRequest);
            mProgress.show();
        } else {
            mShopStreetRequest = new CommonRequest(Api.API_SHOP_STREET, HttpWhat
                    .HTTP_GET_SHOP_STREET_TWO.getValue());
            mShopStreetRequest.add("lng", App.getInstance().lng);
            mShopStreetRequest.add("lat", App.getInstance().lat);
            mShopStreetRequest.alarm = false;
            mShopStreetRequest.add("cls_id", clsId);
            mShopStreetRequest.add("sort", sort);
            mShopStreetRequest.add("distance", distance);

            mShopStreetRequest.add("page[cur_page]", page);

            addRequest(mShopStreetRequest);
            mProgress.show();
        }*/
        CommonRequest mShopStreetRequest;
        mShopStreetRequest = new CommonRequest(Api.API_FREEBUY_SEARCH_SHOP, HttpWhat
                .HTTP_GET_SHOP_STREET_TWO.getValue());

        mShopStreetRequest.setAjax(true);
        mShopStreetRequest.add("output",1);
//        mShopStreetRequest.add("is_online", "1");
        mShopStreetRequest.add("lng", lng);
        mShopStreetRequest.add("lat", lat);
        mShopStreetRequest.alarm = false;
        mShopStreetRequest.add("page[cur_page]", page);

        if (!Utils.isNull(name)) {
            mShopStreetRequest.add("name", name);
        }

        if (!Utils.isNull(clsId)) {
            mShopStreetRequest.add("cls_id", clsId);
        }

        if (!Utils.isNull(distance)) {
            mShopStreetRequest.add("distance", distance);
        }

        if (!Utils.isNull(sort)) {
            mShopStreetRequest.add("sort", sort);
        } else {
            mShopStreetRequest.add("sort", "distance-asc");
        }

        addRequest(mShopStreetRequest);
        mProgress.show();
    }

/*    private void addRequest(String lat, String lng) {
        CommonRequest mShopStreetRequest;
        if (!Utils.isNull(name)) {
            mShopStreetRequest = new CommonRequest(Api.API_SHOP_SEARCH, HttpWhat
                    .HTTP_GET_SHOP_STREET.getValue());
            mShopStreetRequest.add("keyword", name);
            mShopStreetRequest.add("type", "1");
            addRequest(mShopStreetRequest);
        } else {
            mShopStreetRequest = new CommonRequest(Api.API_SHOP_STREET, HttpWhat
                    .HTTP_GET_SHOP_STREET.getValue());
            mShopStreetRequest.add("lng", lng);
            mShopStreetRequest.add("lat", lat);
            mShopStreetRequest.add("sort", "distance-asc");
            addRequest(mShopStreetRequest);
        }
    }*/

    private void request() {
        CommonRequest mShopStreetRequest;
        mShopStreetRequest = new CommonRequest(Api.API_FREEBUY_SEARCH_SHOP, HttpWhat.HTTP_GET_SHOP_STREET
                .getValue());

        mShopStreetRequest.setAjax(true);
        mShopStreetRequest.add("output",1);
        mShopStreetRequest.add("lng", lng);
        mShopStreetRequest.add("lat", lat);

        mShopStreetRequest.add("page[cur_page]", page);

        if (!Utils.isNull(name)) {
            mShopStreetRequest.add("name", name);
        }

        if (!Utils.isNull(clsId)) {
            mShopStreetRequest.add("cls_id", clsId);
        }

        if (!Utils.isNull(distance)) {
            mShopStreetRequest.add("distance", distance);
        }

        if (!Utils.isNull(sort)) {
            mShopStreetRequest.add("sort", sort);
        } else {
            mShopStreetRequest.add("sort", "distance-asc");
        }
        addRequest(mShopStreetRequest);
    }


    //通过关键字查找店铺
    public void searchForKeyWord(String keyword) {
        name = keyword;
        mShopStreetAdapter.data.clear();
        page = 1;
        sortRefresh();
    }

    private void openMapActivity(int position) {
        ShopListItemModel shopListItemModel = (ShopListItemModel) mShopStreetAdapter.data.get(position);

        ShopListItemModel slim = null;
        try {
            slim = shopListItemModel;
        } catch (Exception e) {

        }

        if (slim != null) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), MapActivity.class);
            intent.putExtra(Key.KEY_MARKER_NAME.getValue(), slim.shop_name);
            intent.putExtra(Key.KEY_MARKER_SNIPPET.getValue(), slim
                    .simply_introduce);
            intent.putExtra(Key.KEY_LATITUDE.getValue(), slim.shop_lat);
            intent.putExtra(Key.KEY_LONGITUDE.getValue(), slim.shop_lng);
            intent.putExtra(Key.KEY_LATITUDE_ME.getValue(), App.getInstance().lat);
            intent.putExtra(Key.KEY_LONGITUDE_ME.getValue(), App.getInstance().lng);
            startActivity(intent);
        }
    }

    SelectShopDialog dialog;

    private void openShopActivity(int position) {
        if (dialog == null) {
            dialog = new SelectShopDialog(getActivity());
        }

        final ShopListItemModel shopListItemModel = (ShopListItemModel) mShopStreetAdapter.data.get(position);
        dialog.show();
        dialog.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(getContext(), ScanActivityTwo.class);
                intent.putExtra(Key.KEY_SHOP_ID.getValue(), shopListItemModel.shop_id);
                intent.putExtra(Key.KEY_SHOP_NAME.getValue(), shopListItemModel.shop_name);
                startActivity(intent);
            }
        });
        dialog.setShopName(shopListItemModel.shop_name);
        dialog.setAddress(shopListItemModel.address);

    }

    private void openShopPrepareActivity(int position) {
        ShopListItemModel shopListItemModel = (ShopListItemModel) mShopStreetAdapter.data.get(position);
        if (App.getInstance().isLogin()) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), ShopPrepareActivity.class);
            intent.putExtra(Key.KEY_SHOP_ID.getValue(), shopListItemModel.shop_id);
            intent.putExtra(Key.KEY_SHOP_NAME.getValue(), shopListItemModel.shop_name);
            intent.putExtra(Key.KEY_SHOP_LOGO.getValue(), shopListItemModel.shop_logo);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onCanceled(PullableComponent pullableComponent) {

    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        if (pullableComponent.getSide().toString().equals("TOP")) {
            Location.locationCallback(new Location.OnLocationListener.DefaultLocationListener(){

                @Override
                public void onSuccess(AMapLocation amapLocation) {
                    lat = App.getInstance().lat;
                    lng = App.getInstance().lng;
                }

                @Override
                public void onFinished(AMapLocation amapLocation) {
                    page = 1;
                    request();
                }
            });
        }
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {
    }

}
