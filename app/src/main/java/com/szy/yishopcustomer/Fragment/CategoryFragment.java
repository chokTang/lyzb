package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.GoodsListActivity;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Activity.ScanActivity;
import com.szy.yishopcustomer.Activity.SearchActivity;
import com.szy.yishopcustomer.Activity.im.LyMessageActivity;
import com.szy.yishopcustomer.Adapter.CategoryAdapter;
import com.szy.yishopcustomer.Adapter.CategoryLevelOneAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.CateforyModel.CategoryListModel;
import com.szy.yishopcustomer.ResponseModel.CateforyModel.Model;
import com.szy.yishopcustomer.ResponseModel.CateforyModel.SubcategoryModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.BrowserUrlManager;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.NoDoubleClickListener;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.CategoryModel;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;

/**
 * Created by liwei on 17/5/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CategoryFragment extends YSCBaseFragment {

    @BindView(R.id.fragment_category_level_one_listView)
    CommonRecyclerView mLevelOneRecyclerView;
    @BindView(R.id.fragment_category_level_two_recycler_view)
    RecyclerView mLevelTwoRecyclerView;
    @BindView(R.id.fragment_category_title_input)
    TextView mSearch;
    @BindView(R.id.fragment_category_title_scanButton)
    ImageView mScanning;
    @BindView(R.id.fragment_category_back)
    ImageView fragment_category_back;

    @BindView(R.id.textView_cart_badge)
    View mBadgeCircle;


    RootActivity rootActivity;

    private ArrayList<CategoryModel> mLevelOneCategories;
    private ArrayList<CategoryModel> mLevelTwoCategories;
    public CategoryAdapter mAdapter;
    private CategoryModel mClickedCategory;
    private Intent intent = new Intent();
    private CategoryLevelOneAdapter mLevelOneAdapter;
    private LinearLayoutManager mLayoutManagerOne;


    @Override
    public void onClick(View v) {
        if (Utils.isDoubleClick()) {
            return;
        }
        switch (v.getId()) {
/*            case R.id.fragment_category_title_input:
                openSearchActivity();
                break;*/
            case R.id.fragment_category_title_scanButton:
                openScanActivity();
                break;
            case R.id.fragment_category_back:
                rootActivity.setCurrentTab(0);
                break;
            default:
                ViewType viewType = Utils.getViewTypeOfTag(v);
                int position = Utils.getPositionOfTag(v);
                switch (viewType) {
                    case VIEW_TYPE_CATEGORY:
                        refreshCategory(position);
                        break;
                }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        mSearch.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                openSearchActivity();
            }
        });
        //mSearch.setOnClickListener(this);
        mScanning.setOnClickListener(this);
        fragment_category_back.setOnClickListener(this);
        mLevelOneCategories = new ArrayList<>();
        mLevelOneAdapter = new CategoryLevelOneAdapter(mLevelOneCategories);
        mLevelOneAdapter.onClickListener = this;
        mLayoutManagerOne = new LinearLayoutManager(getContext());
        mLevelOneRecyclerView.setLayoutManager(mLayoutManagerOne);
        mLevelOneRecyclerView.setAdapter(mLevelOneAdapter);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mLevelTwoCategories.get(position).getCatLevel()) {
                    case "1":
                    case "2":
                        return 3;
                    case "3":
                        return 1;
                    default:
                        return 1;
                }
            }
        });
        mLevelTwoRecyclerView.setLayoutManager(mLayoutManager);
        mLevelTwoRecyclerView.setHasFixedSize(true);
        mAdapter = new CategoryAdapter(new ArrayList<CategoryModel>());
        mLevelTwoRecyclerView.setAdapter(mAdapter);
        int windowWidth = Utils.getWindowWidth(getActivity());
        mAdapter.itemWidth = (int) Math.round(windowWidth * 0.75 / 3.5);

        mAdapter.setOnItemClickListener(new CategoryAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, CategoryModel data) {
                openGoodsListActivity(data);
            }
        });

        return v;
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_CAT_LIST:
                break;
            default:
                super.onRequestFailed(what, response);
                break;

        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_CAT_LIST:
                //levelOneCallback(response);
                refreshCallback(response);
                break;
            case HTTP_SUB_CAT_LIST:
                //levelTwoCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

/*    private void levelTwoCallback(String response) {
        mLevelTwoCategories = new ArrayList<>();
        mLevelTwoCategories.add(mClickedCategory);
        Model mDataTwo = JSON.parseObject(response, Model.class);
        //去除空数据
        mDataTwo.data.cat_list = removeEmptyData(mDataTwo.data.cat_list);

        for (int i = 0; i < mDataTwo.data.cat_list.size(); i++) {
            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setCatId(mDataTwo.data.cat_list.get(i).cat_id);
            categoryModel.setCatName(mDataTwo.data.cat_list.get(i).cat_name);
            categoryModel.setCatImage(mDataTwo.data.cat_list.get(i).cat_image);
            categoryModel.setCatLevel((mDataTwo.data.cat_list.get(i).cat_level + 1) + "");
            mLevelTwoCategories.add(categoryModel);
        }
        mAdapter.setData(mLevelTwoCategories);
        mAdapter.notifyDataSetChanged();
        mLevelTwoRecyclerView.getLayoutManager().smoothScrollToPosition
                (mLevelTwoRecyclerView, null, 0);
    }

    private void levelOneCallback(String response) {
        Model mData = JSON.parseObject(response, Model.class);

        //去除空数据
        mData.data.cat_list = removeEmptyData(mData.data.cat_list);

        for (int i = 0; i < mData.data.cat_list.size(); i++) {
            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setCatId(mData.data.cat_list.get(i).cat_id);
            categoryModel.setCatName(mData.data.cat_list.get(i).cat_name);
            categoryModel.setCatImage(mData.data.cat_list.get(i).cat_image);
            if (i == 0) {
                categoryModel.setClick(true);
            }
            mLevelOneCategories.add(categoryModel);
        }
        mLevelOneAdapter.setData(mLevelOneCategories);
        mLevelOneAdapter.notifyDataSetChanged();
        CategoryModel category = mLevelOneCategories.get(0);
        category.setCatLevel("1");
        mClickedCategory = category;
        levelTwoRefresh(category);
    }


    private List<CatListMode> removeEmptyData(List<CatListMode> temp){
        List<CatListMode> data = new ArrayList<>();

        for (int i = 0; i < temp.size(); i++) {
            if(temp.get(i) != null) {
                data.add(temp.get(i));
            }
        }

        return data;
    }

    private void levelOnerefresh(){
        CommonRequest mRefreshRequest = new CommonRequest(Api.API_CATEGORY, HttpWhat
        .HTTP_CAT_LIST.getValue());
        mRefreshRequest.add("deep", "1");
        mRefreshRequest.add("id", "0");
        mRefreshRequest.add("is_show", "1");
        addRequest(mRefreshRequest);
    }

    private void levelTwoRefresh(CategoryModel category){
        CommonRequest mSubCatRequest = new CommonRequest(Api.API_CATEGORY, HttpWhat
        .HTTP_SUB_CAT_LIST.getValue());
        mSubCatRequest.add("deep", "2");
        mSubCatRequest.add("id", category.getCatId());
        addRequest(mSubCatRequest);
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_category;
        rootActivity = (RootActivity) getActivity();
        mLevelOneCategories = new ArrayList<>();
        mLevelTwoCategories = new ArrayList<>();
        refresh();


        //levelOnerefresh();
    }

    private void openGoodsListActivity(CategoryModel data) {
        //优先打开后台-商城-商品-分类管理  设置的链接
        if (!Utils.isNull(data.getCatLink())) {
            new BrowserUrlManager().parseUrl(getContext(), data.getCatLink());
        } else {
            Intent intent = new Intent();
            intent.putExtra(Key.KEY_CATEGORY.getValue(), data.getCatId());
            intent.setClass(getActivity(), GoodsListActivity.class);
            startActivity(intent);
        }

        //这种方式可以在rootactivityt直接加载fragment
//        Map<String,Object> params = new HashMap<>();
//        params.put(Key.KEY_REQUEST_CATEGORY_ID.getValue(),data.getCatId());
//        params.put(Key.KEY_API.getValue(),Api.API_GOODS_LIST);
//        params.put(Key.KEY_VISIBLE_MODEL.getValue(), GoodsListFragment.TOP_MODEL |
//                GoodsListFragment.RIGHT_MODEL);
//
//        CommonEvent commonEvent = new CommonEvent(EventWhat.EVENT_LOAD_FRAGMENT.getValue());
//        commonEvent.setMessage("GoodsListFragment");
//        commonEvent.setMessageSource(JSON.toJSONString(params));
//        EventBus.getDefault().post(commonEvent);
//        startActivity(new Intent().setClass(getActivity(), RootActivity.class));
    }

    private void openScanActivity() {
        if (cameraIsCanUse()) {
            intent.setClass(getActivity(), ScanActivity.class);
            startActivity(intent);
        } else {
            Utils.toastUtil.showToast(getActivity(), getResources().getString(R.string
                    .noCameraPermission));
        }
//        intent.setClass(getActivity(), ScanActivity.class);
//        startActivity(intent);
    }

 /*   private void openMessageActivity() {
        intent.setClass(getActivity(), MessageActivity.class);
        startActivity(intent);
    }*/

    public void openMessageActivity() {
        startActivity(new Intent(getContext(), LyMessageActivity.class));
    }

    private void openLoginActivityForResult(RequestCode requestCode) {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivityForResult(intent, requestCode.ordinal());
    }

    private void openSearchActivity() {
        intent.setClass(getActivity(), SearchActivity.class);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_GOODS_LIST_CODE.getValue());
    }

    private void refreshCategory(int position) {
        CategoryModel category = mLevelOneCategories.get(position);
        category.setCatLevel("1");
        mClickedCategory = category;
        refreshSubcategory(category);
        for (int i = 0; i < mLevelOneCategories.size(); i++) {
            mLevelOneCategories.get(i).setClick(false);
        }
        mLevelOneCategories.get(position).setClick(true);
        if (position != mLevelOneCategories.size() - 1) {
            int n = position - mLayoutManagerOne.findFirstVisibleItemPosition();
            if (0 <= n && n < mLevelOneRecyclerView.getChildCount()) {
                int top = mLevelOneRecyclerView.getChildAt(n).getTop();
                if (500 > top) {
                    mLevelOneRecyclerView.smoothScrollBy(0, -(500 - top));
                } else {
                    mLevelOneRecyclerView.smoothScrollBy(0, top - 500);
                }

            }
        }
        mLevelOneAdapter.notifyDataSetChanged();
    }

    private boolean isFistLoad = true;

    public void refresh() {
        CommonRequest mRefreshRequest = new CommonRequest(Api.API_CATEGORY, HttpWhat
                .HTTP_CAT_LIST.getValue());
        if (isFistLoad) {
            mRefreshRequest.alarm = false;
            isFistLoad = false;
        }
        addRequest(mRefreshRequest);
    }

    private void refreshCallback(String response) {
        mLevelOneRecyclerView.setVisibility(View.VISIBLE);
        mLevelTwoRecyclerView.setVisibility(View.VISIBLE);

        mBadgeCircle.setVisibility(App.getInstance().isUnreadMessage);

        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model mData) {
                int i = 0;
                for (TreeMap.Entry<Integer, CategoryListModel> entry : mData.data.list.entrySet()) {
                    CategoryModel categoryModel = new CategoryModel();
                    categoryModel.setCatId(entry.getValue().cat_id);
                    categoryModel.setCatName(entry.getValue().cat_name);
                    categoryModel.setCatImage(entry.getValue().cat_image);
                    categoryModel.setCatItems(entry.getValue().items);
                    categoryModel.setCatLink(entry.getValue().cat_link);
                    if (i == 0) {
                        categoryModel.setClick(true);
                    }
                    mLevelOneCategories.add(categoryModel);
                    i++;

                    mLevelOneAdapter.setData(mLevelOneCategories);
                    mLevelOneAdapter.notifyDataSetChanged();

                    CategoryModel category = mLevelOneCategories.get(0);
                    category.setCatLevel("1");
                    mClickedCategory = category;

                    //levelTwoRefresh(category);
                    refreshSubcategory(category);
                }
            }
        });
    }

    private void refreshSubcategory(CategoryModel categoryModel) {
        List<SubcategoryModel> items = categoryModel.getCatItems();

        mLevelTwoCategories = new ArrayList<>();
        mLevelTwoCategories.add(mClickedCategory);

        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                CategoryModel subCategoryModel = new CategoryModel();
                subCategoryModel.setCatId(items.get(i).cat_id);
                subCategoryModel.setCatName(items.get(i).cat_name);
                subCategoryModel.setCatImage(items.get(i).cat_image);
                subCategoryModel.setCatLevel((items.get(i).cat_level) + "");
                subCategoryModel.setCatLink(items.get(i).cat_link);
                mLevelTwoCategories.add(subCategoryModel);
                if (!Utils.isNull(items.get(i).items)) {
                    for (int j = 0; j < items.get(i).items.size(); j++) {
                        CategoryModel subCategoryModel2 = new CategoryModel();
                        subCategoryModel2.setCatId(items.get(i).items.get(j).cat_id);
                        subCategoryModel2.setCatName(items.get(i).items.get(j).cat_name);
                        subCategoryModel2.setCatImage(items.get(i).items.get(j).cat_image);
                        subCategoryModel2.setCatLevel((items.get(i).items.get(j).cat_level) + "");
                        subCategoryModel2.setCatLink(items.get(i).items.get(j).cat_link);
                        mLevelTwoCategories.add(subCategoryModel2);
                    }
                }
            }
        }
        mAdapter.setData(mLevelTwoCategories);
        mAdapter.notifyDataSetChanged();
        mLevelTwoRecyclerView.getLayoutManager().smoothScrollToPosition(mLevelTwoRecyclerView,
                null, 0);
    }

    public boolean isLoadCompleted() {
        return !(mLevelOneCategories == null || mLevelOneCategories.size() <= 0);

    }

    public void againLoadData() {
        if (mLevelOneCategories != null && mLevelOneCategories.size() <= 0)
            refresh();
    }


    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_UPDATE_MESSAGE_VISIBILE:
                if ("0".equals(event.getMessage())) {
                    mBadgeCircle.setVisibility(View.VISIBLE);
                } else {
                    mBadgeCircle.setVisibility(View.INVISIBLE);
                }
//
                break;
            default:
                super.onEvent(event);
                break;
        }
    }
}