package com.szy.yishopcustomer.Fragment;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.szy.common.Interface.OnEmptyViewClickListener;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonEditText;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.MapActivity;
import com.szy.yishopcustomer.Activity.ShopActivity;
import com.szy.yishopcustomer.Activity.ShopPrepareActivity;
import com.szy.yishopcustomer.Adapter.ShopStreetAdapter;
import com.szy.yishopcustomer.Adapter.ShopStreetCategoryLevelOneAdapter;
import com.szy.yishopcustomer.Adapter.ShopStreetCategoryLevelTwoAdapter;
import com.szy.yishopcustomer.Adapter.ShopStreetIntelligentAdapter;
import com.szy.yishopcustomer.Adapter.ShopStreetNearShopAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Interface.SimpleAnimatorListener;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.ShopListItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.ShopStreet.Model;
import com.szy.yishopcustomer.ResponseModel.ShopStreet.ShopStreetCateOneItemModel;
import com.szy.yishopcustomer.Service.Location;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.CategoryModel;

import java.util.ArrayList;

import butterknife.BindView;
import me.zongren.pullablelayout.Constant.Result;
import me.zongren.pullablelayout.Constant.Side;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * Created by liuzhifeng on 2016/6/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopStreetFragment extends YSCBaseFragment implements OnEmptyViewClickListener,
        OnPullListener {

    public static final int REQUEST_SHOPPREPARE_CODE = 10001;

//    public static CustomProgressDialog mProgress;
    public String name = "";
    @BindView(R.id.imageView_search)
    public ImageView imageView_search;
    @BindView(R.id.activity_search_search_eidttext)
    public CommonEditText mKeywordEditText;
    @BindView(R.id.fragment_shop_street_recyclerView_layout)
    PullableLayout fragment_pullableLayout;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.fragment_shop_street_all_relative_layout)
    RelativeLayout mAllRelativeLayout;
    @BindView(R.id.fragment_shop_street_sortWrapperRelativeLayout)
    LinearLayout mNearShopRelativeLayout;
    @BindView(R.id.fragment_shop_street_mask_layout)
    LinearLayout mMaskRelativeLayout;
    @BindView(R.id.fragment_shop_street_intelligentSortingRelativeLayout)
    LinearLayout mIntelligentSortingRelativeLayout;
    @BindView(R.id.fragment_shop_street_releaseRelativeLayout)
    LinearLayout mAllCategory;
    @BindView(R.id.fragment_shop_street_sortRecyclerView_one)
    RecyclerView mAllRecycleViewOne;
    @BindView(R.id.fragment_shop_street_sortRecyclerView_two)
    RecyclerView mAllRecycleViewTwo;
    @BindView(R.id.fragment_shop_street_nearShopRecyclerView)
    RecyclerView mNearShopRecycleView;
    @BindView(R.id.fragment_shop_street_intelligentSortingRecyclerView)
    RecyclerView mIntelligentSortingRecycleView;
    @BindView(R.id.fragment_shop_street_recyclerView)
    CommonRecyclerView mShopStreetRecyclerView;
    @BindView(R.id.fragment_shop_street_all_category_text_view)
    TextView mAllTextView;
    @BindView(R.id.fragment_shop_street_sortTextView)
    TextView mNearShopTextView;
    @BindView(R.id.fragment_shop_street_filterTextView)
    TextView mIntelligentTextView;
    @BindView(R.id.fragment_shop_street_all_category_image_view)
    ImageView mAllImageView;
    @BindView(R.id.fragment_shop_street_sortImageView)
    ImageView mNearShopImageView;
    @BindView(R.id.fragment_shop_street_filterImageView)
    ImageView mIntelligentImageView;
    private int mPosition;
    private ShopStreetAdapter mShopStreetAdapter;

    private ArrayList<CategoryModel> mLevelOneCategories;
    private ShopStreetCategoryLevelOneAdapter mShopStreetCategoryLevelOneAdapter;
    private ArrayList<CategoryModel> mLevelTwoCategories;
    private ShopStreetCategoryLevelTwoAdapter mShopStreetCategoryLevelOneAdapterTwo;
    private ArrayList<CategoryModel> mNearShopList;
    private ShopStreetNearShopAdapter mShopStreetNearShopAdapter;
    private ShopStreetIntelligentAdapter mShopStreetIntelligentAdapter;
    private ArrayList<CategoryModel> mIntelligentList;
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

    private String clsName;

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
            case VIEW_TYPE_SHOP_STREET_ALL:
                clickAllRelativeLayout();
                break;
            case VIEW_TYPE_SHOP_LOCATION:
                openMapActivity(position);
                break;
            case VIEW_TYPE_SHOP_STREET_MASK:
                checkWindowIsShow();
                break;
            case VIEW_TYPE_CATEGORY:
                refreshCategory(position);
                break;
            case VIEW_TYPE_CATEGORY_TWO:
                refreshCategoryTwo(position);
                break;
            case VIEW_TYPE_SHOP_STREET_NEAR:
                clickNearShopRelativeLayout();
                break;
            case VIEW_TYPE_CATEGORY_NEAR_SHOP:
                refreshCategoryNearShop(position);
                break;
            case VIEW_TYPE_CATEGORY_NEAR_INTENLLINGENT:
                refreshCategoryIntelligent(position);
                break;
            case VIEW_TYPE_SHOP_STREET_SORT:
                clickIntelligentSortingRelativeLayout();
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
        mLayoutId = R.layout.fragment_shop_street;
        mShopStreetAdapter = new ShopStreetAdapter();
//        mProgress = new CustomProgressDialog(getActivity());
//        mProgress.setCanceledOnTouchOutside(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        Intent intent = getActivity().getIntent();
        name = intent.getStringExtra(Key.KEY_KEYWORD.getValue());
        clsId = intent.getStringExtra("cls_id");
        clsName = intent.getStringExtra("cls_name");

        if(!Utils.isNull(clsName)){
            mAllTextView.setText(clsName);
        }

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

        Utils.setViewTypeForTag(mAllCategory, ViewType.VIEW_TYPE_SHOP_STREET_ALL);
        mAllCategory.setOnClickListener(this);
        Utils.setViewTypeForTag(mMaskRelativeLayout, ViewType.VIEW_TYPE_SHOP_STREET_MASK);
        mMaskRelativeLayout.setOnClickListener(this);
        Utils.setViewTypeForTag(mNearShopRelativeLayout, ViewType.VIEW_TYPE_SHOP_STREET_NEAR);
        mNearShopRelativeLayout.setOnClickListener(this);
        Utils.setViewTypeForTag(mIntelligentSortingRelativeLayout, ViewType
                .VIEW_TYPE_SHOP_STREET_SORT);
        mIntelligentSortingRelativeLayout.setOnClickListener(this);

        mLevelOneCategories = new ArrayList<>();
        mShopStreetCategoryLevelOneAdapter = new ShopStreetCategoryLevelOneAdapter
                (mLevelOneCategories);
        mShopStreetCategoryLevelOneAdapter.onClickListener = this;
        LinearLayoutManager mLayoutManagerOne = new LinearLayoutManager(getContext());
        mAllRecycleViewOne.setLayoutManager(mLayoutManagerOne);
        mAllRecycleViewOne.setAdapter(mShopStreetCategoryLevelOneAdapter);

        mLevelTwoCategories = new ArrayList<>();
        mShopStreetCategoryLevelOneAdapterTwo = new ShopStreetCategoryLevelTwoAdapter
                (mLevelTwoCategories);
        mShopStreetCategoryLevelOneAdapterTwo.onClickListener = this;
        LinearLayoutManager mLayoutManagerTwo = new LinearLayoutManager(getContext());
        mAllRecycleViewTwo.setLayoutManager(mLayoutManagerTwo);
        mAllRecycleViewTwo.setAdapter(mShopStreetCategoryLevelOneAdapterTwo);
        mShopStreetCategoryLevelOneAdapter.setData(mLevelOneCategories);


        upDataNearShopData();
        mShopStreetNearShopAdapter = new ShopStreetNearShopAdapter(mNearShopList);
        mShopStreetNearShopAdapter.onClickListener = this;
        LinearLayoutManager mLayoutManagerNearShop = new LinearLayoutManager(getContext());
        mNearShopRecycleView.setLayoutManager(mLayoutManagerNearShop);
        mNearShopRecycleView.setAdapter(mShopStreetNearShopAdapter);
        mShopStreetNearShopAdapter.setData(mNearShopList);
        mShopStreetNearShopAdapter.notifyDataSetChanged();

        upDataIntelligentData();
        mShopStreetIntelligentAdapter = new ShopStreetIntelligentAdapter(mNearShopList);
        mShopStreetIntelligentAdapter.onClickListener = this;
        LinearLayoutManager mLayoutManagerIntelligent = new LinearLayoutManager(getContext());
        mIntelligentSortingRecycleView.setLayoutManager(mLayoutManagerIntelligent);
        mIntelligentSortingRecycleView.setAdapter(mShopStreetIntelligentAdapter);
        mShopStreetIntelligentAdapter.setData(mIntelligentList);
        mShopStreetIntelligentAdapter.notifyDataSetChanged();
    }

    private void refreshCategoryIntelligent(int position) {
        if (mIntelligentList.get(position).isClick()) {
            if (mIntelligentList.get(position).getCatImage().equals("DESC")) {
                mIntelligentList.get(position).setCatImage("ASC");
                mShopStreetIntelligentAdapter.notifyDataSetChanged();
                if (position == 2){
                    mIntelligentTextView.setText(mIntelligentList.get(position).getCatName() + "由近到远");

                }else {
                    mIntelligentTextView.setText(mIntelligentList.get(position).getCatName() + "由低到高");
                }
                if (position == 0) {
                    sort = "sale-asc";
                } else if (position == 1) {
                    sort = "credit-asc";
                } else {
                    sort = "distance-asc";
                }

                mShopStreetAdapter.data.clear();
                page = 1;
                sortRefresh();
            } else {
                mIntelligentList.get(position).setCatImage("DESC");
                mShopStreetIntelligentAdapter.notifyDataSetChanged();
                if (position == 2){
                    mIntelligentTextView.setText(mIntelligentList.get(position).getCatName() + "由远到近");
                }else {
                    mIntelligentTextView.setText(mIntelligentList.get(position).getCatName() + "由高到低");
                }
                if (position == 0) {
                    sort = "sale-desc";
                } else if (position == 1) {
                    sort = "credit-desc";
                } else {
                    sort = "distance-desc";
                }

                mShopStreetAdapter.data.clear();
                page = 1;
                sortRefresh();
            }

        } else {
            for (int i = 0; i < mIntelligentList.size(); i++) {
                mIntelligentList.get(i).setClick(false);
                mIntelligentList.get(i).setCatImage("DESC");
            }
            mIntelligentList.get(position).setClick(true);
            mShopStreetIntelligentAdapter.notifyDataSetChanged();
            if (position == 2){
                mIntelligentTextView.setText(mIntelligentList.get(position).getCatName() + "由远到近");
            }else {
                mIntelligentTextView.setText(mIntelligentList.get(position).getCatName() + "由高到低");
            }
            if (position == 0) {
                sort = "sale-desc";
            } else if (position == 1) {
                sort = "credit-desc";
            } else {
                sort = "distance-desc";
            }

            mShopStreetAdapter.data.clear();
            page = 1;
            sortRefresh();
        }
        hideIntelligentSortingRelativeLayout();
    }

    private void refreshCategoryNearShop(int position) {
        for (int i = 0; i < mNearShopList.size(); i++) {
            mNearShopList.get(i).setClick(false);
        }
        if (position != 0) {
            distance = mNearShopList.get(position).getCatId();
            sort = "distance-asc";
            mNearShopTextView.setText(mNearShopList.get(position).getCatName());
        } else {
            distance = "";
            sort = "";
            mNearShopTextView.setText("附近商家");
        }


        mShopStreetAdapter.data.clear();
        page = 1;
        sortRefresh();

        mNearShopList.get(position).setClick(true);
        mShopStreetNearShopAdapter.notifyDataSetChanged();
        hideNearShopRelativeLayout();
    }

    private void checkWindowIsShow() {
        if (mAllRelativeLayout.getVisibility() == View.VISIBLE) {
            hideAllRelativeLayout();
        }
        if (mNearShopRecycleView.getVisibility() == View.VISIBLE) {
            hideNearShopRelativeLayout();
        }
        if (mIntelligentSortingRecycleView.getVisibility() == View.VISIBLE) {
            hideIntelligentSortingRelativeLayout();
        }
    }

    private void refreshCategory(int position) {
        mPosition = position;
        for (int i = 0; i < mLevelOneCategories.size(); i++) {
            mLevelOneCategories.get(i).setClick(false);
        }

        CategoryModel categoryModel = mLevelOneCategories.get(position);

        mLevelOneCategories.get(position).setClick(true);
        clsName = "";
        mShopStreetCategoryLevelOneAdapter.notifyDataSetChanged();
        if (position == 0) {
            mShopStreetCategoryLevelOneAdapterTwo.mData.clear();
            mShopStreetCategoryLevelOneAdapterTwo.notifyDataSetChanged();
            mAllTextView.setText("全部分类");
            clsId = "";

            mShopStreetAdapter.data.clear();
            page = 1;
            sortRefresh();
            hideAllRelativeLayout();
            return;
        }

        if(!categoryModel.isHaveChild()) {
            mShopStreetCategoryLevelOneAdapterTwo.mData.clear();
            mShopStreetCategoryLevelOneAdapterTwo.notifyDataSetChanged();
            mAllTextView.setText(categoryModel.getCatName());
            clsId = categoryModel.getCatLevel()+"_"+categoryModel.getCatId()+"_0";
            mShopStreetAdapter.data.clear();
            page = 1;
            sortRefresh();
            hideAllRelativeLayout();
            return;
        }

        categoryOneCallback(position - 1);

    }

    private void refreshCategoryTwo(int position) {
        for (int i = 0; i < mLevelTwoCategories.size(); i++) {
            mLevelTwoCategories.get(i).setClick(false);
        }
        mLevelTwoCategories.get(position).setClick(true);
        mShopStreetCategoryLevelOneAdapterTwo.notifyDataSetChanged();
        if (position == 0) {
            mAllTextView.setText(mLevelOneCategories.get(mPosition).getCatName());
            clsId = mLevelOneCategories.get(mPosition).getCatLevel() + "_" + mLevelOneCategories
                    .get(mPosition).getCatId() + "_" + mLevelOneCategories.get(mPosition)
                    .getParentId();
        } else {
            mAllTextView.setText(mLevelTwoCategories.get(position).getCatName());
            clsId = mLevelTwoCategories.get(position).getCatLevel() + "_" + mLevelTwoCategories
                    .get(position).getCatId() + "_" + mLevelTwoCategories.get(position)
                    .getParentId();
        }


        mShopStreetAdapter.data.clear();
        page = 1;
        sortRefresh();
    }


    private void clickAllRelativeLayout() {
        if (mNearShopRecycleView.getVisibility() == View.VISIBLE) {
            hideNearShopRelativeLayout();
        }
        if (mIntelligentSortingRecycleView.getVisibility() == View.VISIBLE) {
            hideIntelligentSortingRelativeLayout();
        }
        if (mAllRelativeLayout.getVisibility() == View.VISIBLE) {
            hideAllRelativeLayout();
        } else {
            showAllRelativeLayout();
        }
    }

    private void clickNearShopRelativeLayout() {
        if (mAllRelativeLayout.getVisibility() == View.VISIBLE) {
            hideAllRelativeLayout();
        }
        if (mIntelligentSortingRecycleView.getVisibility() == View.VISIBLE) {
            hideIntelligentSortingRelativeLayout();
        }
        if (mNearShopRecycleView.getVisibility() == View.VISIBLE) {
            hideNearShopRelativeLayout();
        } else {
            showNearShopRelativeLayout();
        }
    }

    private void clickIntelligentSortingRelativeLayout() {
        if (mAllRelativeLayout.getVisibility() == View.VISIBLE) {
            hideAllRelativeLayout();
        }
        if (mNearShopRecycleView.getVisibility() == View.VISIBLE) {
            hideNearShopRelativeLayout();
        }
        if (mIntelligentSortingRecycleView.getVisibility() == View.VISIBLE) {
            hideIntelligentSortingRelativeLayout();
        } else {
            showIntelligentSortingRelativeLayout();
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

    private void categoryOneCallback(int position) {
        mLevelTwoCategories.clear();
//        Model data = JSON.parseObject(response, Model.class);
        if (model.data.cls_list.get(position).cls_list_2 != null) {
            for (int i = 0; i < model.data.cls_list.get(position).cls_list_2.size() + 1; i++) {
                CategoryModel categoryModel = new CategoryModel();
                if (i == 0) {
                    categoryModel.setCatId("-1");
                    categoryModel.setCatName("全部");
                } else {
                    categoryModel.setCatId(model.data.cls_list.get(position).cls_list_2.get(i -
                            1).cls_id);
                    categoryModel.setCatName(model.data.cls_list.get(position).cls_list_2.get(i -
                            1).cls_name);
                    categoryModel.setCatLevel(model.data.cls_list.get(position).cls_list_2.get(i
                            - 1).cls_level);
                    categoryModel.setParentId(model.data.cls_list.get(position).cls_list_2.get(i
                            - 1).parent_id);
                }
                if (i == 0) {
                    categoryModel.setClick(true);
                }
                mLevelTwoCategories.add(categoryModel);
            }

            mShopStreetCategoryLevelOneAdapterTwo.setData(mLevelTwoCategories);
            mShopStreetCategoryLevelOneAdapterTwo.notifyDataSetChanged();
        }
    }

    private void sortRefreshCallback(String response) {
        fragment_pullableLayout.topComponent.finish(Result.SUCCEED);
        upDataSuccess = true;


//        mProgress.hide();
        HttpResultManager.resolve(response, Model.class, new HttpResultManager
                .HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model mData) {

                if(page == 1) {
                    mShopStreetAdapter.data.clear();
                }

                pageCount = mData.data.page.page_count;
                if (mData.data.list.size() == 0) {
                    mData.data.list = new ArrayList<>();
                }

                for (ShopListItemModel shopListItemModel : mData.data.list) {
                    mShopStreetAdapter.data.add(shopListItemModel);
                }


                mShopStreetAdapter.notifyDataSetChanged();
                hideAllRelativeLayout();
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

        mShopStreetCategoryLevelOneAdapter.mData.clear();
        HttpResultManager.resolve(response, Model.class, new HttpResultManager
                .HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model back) {
                model = back;
                pageCount = model.data.page.page_count;
                if (model.data.list == null) {
                    model.data.list = new ArrayList<>();
                }
                for (int i = 0; i < model.data.cls_list.size() + 1; i++) {
                    CategoryModel categoryModel = new CategoryModel();
                    if (i == 0) {
                        categoryModel.setCatId("-1");
                        categoryModel.setCatName("全部");
                    } else {
                        ShopStreetCateOneItemModel shopStreetCateOneItemModel = model.data.cls_list.get(i - 1);
                        if(shopStreetCateOneItemModel.cls_list_2 != null && shopStreetCateOneItemModel.cls_list_2.size() > 0) {
                            categoryModel.setHaveChild(true);
                        }
                        categoryModel.setCatId(shopStreetCateOneItemModel.cls_id);
                        categoryModel.setCatName(shopStreetCateOneItemModel.cls_name);
                        categoryModel.setCatLevel(shopStreetCateOneItemModel.cls_level);
                        categoryModel.setParentId(shopStreetCateOneItemModel.parent_id);
                    }
                    if (i == 0) {
                        categoryModel.setClick(true);
                    }
                    mLevelOneCategories.add(categoryModel);
                }


                for (ShopListItemModel shopListItemModel : model.data.list) {
                    mShopStreetAdapter.data.add(shopListItemModel);
                }


                mShopStreetCategoryLevelOneAdapter.notifyDataSetChanged();
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

    @Override
    public void onOfflineViewClicked() {
        page = 1;
        sortRefresh();
    }

    private boolean isFistLoad = true;
    private void sortRefresh() {
        CommonRequest mShopStreetRequest;
        mShopStreetRequest = new CommonRequest(Api.API_SHOP_STREET, HttpWhat
                .HTTP_GET_SHOP_STREET_TWO.getValue());

        if(isFistLoad) {
            mShopStreetRequest.alarm = false;
            isFistLoad = false;
        }

        mShopStreetRequest.add("lng", lng);
        mShopStreetRequest.add("lat", lat);
//        mShopStreetRequest.alarm = false;
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
//        mProgress.show();
    }

    //默认加载第一页

    private void request() {
        if (model != null && page > model.data.page.page_count && page > 1) {
            //设置footer
//            View footer = LayoutInflater.from(getContext()).inflate(R.layout
// .fragment_goods_list_item_empty, null);
//            mShopStreetAdapter.setFooterView(footer);
        } else {
            CommonRequest mShopStreetRequest;
            mShopStreetRequest = new CommonRequest(Api.API_SHOP_STREET, HttpWhat
                    .HTTP_GET_SHOP_STREET.getValue());
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

    }


    //通过关键字查找店铺
    public void searchForKeyWord(String keyword) {
        //这里改成
        name = keyword;
        mShopStreetAdapter.data.clear();
        page = 1;
        sortRefresh();
    }

    private void openMapActivity(int position) {
        ShopListItemModel shopListItemModel = (ShopListItemModel) mShopStreetAdapter.data.get
                (position);

        ShopListItemModel slim = null;
        try {
            slim = shopListItemModel;
        } catch (Exception e) {

        }

        if (slim != null) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), MapActivity.class);
            intent.putExtra(Key.KEY_MARKER_NAME.getValue(), slim.shop_name);
            intent.putExtra(Key.KEY_MARKER_SNIPPET.getValue(), slim.simply_introduce);
            intent.putExtra(Key.KEY_LATITUDE.getValue(), slim.shop_lat);
            intent.putExtra(Key.KEY_LONGITUDE.getValue(), slim.shop_lng);
            intent.putExtra(Key.KEY_LATITUDE_ME.getValue(), App.getInstance().lat);
            intent.putExtra(Key.KEY_LONGITUDE_ME.getValue(), App.getInstance().lng);
            startActivity(intent);
        }
    }

    private void openShopActivity(int position) {
        ShopListItemModel shopListItemModel = (ShopListItemModel) mShopStreetAdapter.data.get
                (position);
        Intent intent = new Intent();
        intent.setClass(getActivity(), ShopActivity.class);
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), shopListItemModel.shop_id);
        intent.putExtra(Key.KEY_SHOP_NAME.getValue(), shopListItemModel.shop_name);
        intent.putExtra(Key.KEY_SHOP_LOGO.getValue(), shopListItemModel.shop_logo);
        startActivity(intent);
    }

    private void openShopPrepareActivity(int position) {
        ShopListItemModel shopListItemModel = (ShopListItemModel) mShopStreetAdapter.data.get
                (position);
        if (App.getInstance().isLogin()) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), ShopPrepareActivity.class);
            intent.putExtra(Key.KEY_SHOP_ID.getValue(), shopListItemModel.shop_id);
            intent.putExtra(Key.KEY_SHOP_NAME.getValue(), shopListItemModel.shop_name);
            intent.putExtra(Key.KEY_SHOP_LOGO.getValue(), shopListItemModel.shop_logo);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivityForResult(intent,REQUEST_SHOPPREPARE_CODE);
        }
    }

    /**
     * 下拉框下面的黑影部分
     */
    private void showShadowView() {
        mMaskRelativeLayout.clearAnimation();
        if (mMaskRelativeLayout.getVisibility() != View.VISIBLE) {
            mMaskRelativeLayout.setVisibility(View.VISIBLE);
        }
        mMaskRelativeLayout.animate().alpha(1).setListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                mMaskRelativeLayout.setAlpha(1);
                mMaskRelativeLayout.setVisibility(View.VISIBLE);
            }
        }).start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_SHOPPREPARE_CODE:
                if(resultCode == getActivity().RESULT_OK) {
                    page = 1;
                    sortRefresh();
                }
                break;
        }
    }

    private void hideShadowView() {
        mMaskRelativeLayout.clearAnimation();
        mMaskRelativeLayout.animate().alpha(0).setListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                mMaskRelativeLayout.setVisibility(View.INVISIBLE);
            }
        }).start();
    }

    /**
     * 显示全部分类的下拉数据
     */
    private void showAllRelativeLayout() {
        if(!"".equals(clsName)){
            for (int i = 0; i < mLevelOneCategories.size(); i++) {
                mLevelOneCategories.get(i).setClick(false);
                if(mLevelOneCategories.get(i).getCatName().equals(clsName)){
                    refreshCategory(i);
                    //mLevelOneCategories.get(i).setClick(true);
                }
            }
        }
        mShopStreetCategoryLevelOneAdapter.notifyDataSetChanged();

        mAllTextView.setSelected(true);
        mAllImageView.setImageResource(R.mipmap.bg_arrow_up_gray);
        showShadowView();
        int height = mAllRelativeLayout.getMeasuredHeight();
        mAllRelativeLayout.setTranslationY(-height);
        mAllRelativeLayout.setVisibility(View.VISIBLE);
        mAllRelativeLayout.animate().translationYBy(height).setListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                mAllRelativeLayout.setVisibility(View.VISIBLE);
                mAllRelativeLayout.setTranslationY(0);
            }
        }).start();
    }

    private void hideAllRelativeLayout() {
        mAllTextView.setSelected(false);
        mAllImageView.setImageResource(R.mipmap.bg_arrow_down_gray);
        hideShadowView();
        int height = mAllRelativeLayout.getMeasuredHeight();
        mAllRelativeLayout.animate().translationYBy(-height).setListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                mAllRelativeLayout.setVisibility(View.INVISIBLE);
                mAllRelativeLayout.setTranslationY(0);
            }
        }).start();
    }

    private void showNearShopRelativeLayout() {
        showShadowView();
        mNearShopImageView.setImageResource(R.mipmap.bg_arrow_up_gray);
        mNearShopTextView.setSelected(true);
        int height = mNearShopRecycleView.getMeasuredHeight();
        mNearShopRecycleView.setTranslationY(-height);
        mNearShopRecycleView.setVisibility(View.VISIBLE);
        mNearShopRecycleView.animate().translationYBy(height).setListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                mNearShopRecycleView.setVisibility(View.VISIBLE);
                mNearShopRecycleView.setTranslationY(0);
            }
        }).start();
    }

    private void hideNearShopRelativeLayout() {
        hideShadowView();
        mNearShopImageView.setImageResource(R.mipmap.bg_arrow_down_gray);
        mNearShopTextView.setSelected(false);
        int height = mNearShopRecycleView.getMeasuredHeight();
        mNearShopRecycleView.animate().translationYBy(-height).setListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                mNearShopRecycleView.setVisibility(View.INVISIBLE);
                mNearShopRecycleView.setTranslationY(0);
            }
        }).start();
    }

    private void showIntelligentSortingRelativeLayout() {
        showShadowView();
        mIntelligentImageView.setImageResource(R.mipmap.bg_arrow_up_gray);
        mIntelligentTextView.setSelected(true);
        int height = mIntelligentSortingRecycleView.getMeasuredHeight();
        mIntelligentSortingRecycleView.setTranslationY(-height);
        mIntelligentSortingRecycleView.setVisibility(View.VISIBLE);
        mIntelligentSortingRecycleView.animate().translationYBy(height).setListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                mIntelligentSortingRecycleView.setVisibility(View.VISIBLE);
                mIntelligentSortingRecycleView.setTranslationY(0);
            }
        }).start();
    }

    private void hideIntelligentSortingRelativeLayout() {
        hideShadowView();
        mIntelligentImageView.setImageResource(R.mipmap.bg_arrow_down_gray);
        mIntelligentTextView.setSelected(false);
        int height = mIntelligentSortingRecycleView.getMeasuredHeight();
        mIntelligentSortingRecycleView.animate().translationYBy(-height).setListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                mIntelligentSortingRecycleView.setVisibility(View.INVISIBLE);
                mIntelligentSortingRecycleView.setTranslationY(0);
            }
        }).start();
    }

    private void upDataNearShopData() {
        mNearShopList = new ArrayList<>();
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setCatId("-1");
        categoryModel.setCatName("全部");
        categoryModel.setClick(true);
        mNearShopList.add(categoryModel);

        categoryModel = new CategoryModel();
        categoryModel.setCatId("1");
        categoryModel.setCatName("1千米");
        mNearShopList.add(categoryModel);

        categoryModel = new CategoryModel();
        categoryModel.setCatId("3");
        categoryModel.setCatName("3千米");
        mNearShopList.add(categoryModel);

        categoryModel = new CategoryModel();
        categoryModel.setCatId("5");
        categoryModel.setCatName("5千米");
        mNearShopList.add(categoryModel);

        categoryModel = new CategoryModel();
        categoryModel.setCatId("10");
        categoryModel.setCatName("10千米");
        mNearShopList.add(categoryModel);
    }

    private void upDataIntelligentData() {
        mIntelligentList = new ArrayList<>();
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setCatId("-1");
        categoryModel.setCatName("销量");
        categoryModel.setCatImage("DESC");
        mIntelligentList.add(categoryModel);

        categoryModel = new CategoryModel();
        categoryModel.setCatId("-1");
        categoryModel.setCatName("信誉");
        categoryModel.setCatImage("DESC");
        mIntelligentList.add(categoryModel);

        categoryModel = new CategoryModel();
        categoryModel.setCatId("-1");
        categoryModel.setCatName("距离");
        categoryModel.setCatImage("DESC");
        mIntelligentList.add(categoryModel);
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
                    if(!TextUtils.isEmpty(App.getInstance().lat) && !TextUtils.isEmpty(App.getInstance().lng)) {
                        lat = App.getInstance().lat;
                        lng = App.getInstance().lng;
                    }
                }

                @Override
                public void onFinished(AMapLocation amapLocation) {
                    page = 1;
                    sortRefresh();
//                    request();
                }
            });
        }
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {
    }
}
