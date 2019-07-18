package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.ShopStreetActivity;
import com.szy.yishopcustomer.Adapter.ShopClassAdapter;
import com.szy.yishopcustomer.Adapter.ShopClassCategoryLevelOneAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.ShopClass.ClsList2Model;
import com.szy.yishopcustomer.ResponseModel.ShopClass.ClsListModel;
import com.szy.yishopcustomer.ResponseModel.ShopClass.Model;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.ShopClassModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liwei on 17/5/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopClassFragment extends YSCBaseFragment {

    @BindView(R.id.fragment_category_level_one_listView)
    CommonRecyclerView mLevelOneRecyclerView;
    @BindView(R.id.fragment_category_level_two_recycler_view)
    RecyclerView mLevelTwoRecyclerView;
    @BindView(R.id.relativeLayout_empty)
    LinearLayout mEmptyView;
    @BindView(R.id.empty_view_titleTextView)
    TextView mEmptyTitle;

    private ArrayList<ShopClassModel> mLevelOneCategories;
    private ShopClassCategoryLevelOneAdapter mLevelOneAdapter;
    private ArrayList<ShopClassModel> mLevelTwoCategories;
    private ShopClassAdapter mAdapter;
    private Intent intent = new Intent();
    private LinearLayoutManager mLayoutManagerOne;

    @Override
    public void onClick(View v) {
        if(Utils.isDoubleClick()) {
            return;
        }
        switch (v.getId()) {
/*            case R.id.fragment_category_title_input:
                openSearchActivity();
                break;*/
            default:
                ViewType viewType = Utils.getViewTypeOfTag(v);
                int position = Utils.getPositionOfTag(v);
                switch (viewType) {
                    case VIEW_TYPE_CATEGORY:
                        refreshCategory(position);
                        break;
                    case VIEW_TYPE_SHOP_STREET:
                        ShopClassModel category = mLevelOneCategories.get(position);
                        openShopStreetActivity(category);
                        break;
                }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        mLevelOneCategories = new ArrayList<>();
        mLevelOneAdapter = new ShopClassCategoryLevelOneAdapter(mLevelOneCategories);
        mLevelOneAdapter.onClickListener = this;
        mLayoutManagerOne = new LinearLayoutManager(getContext());
        mLevelOneRecyclerView.setLayoutManager(mLayoutManagerOne);
        mLevelOneRecyclerView.setAdapter(mLevelOneAdapter);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mLevelTwoCategories.get(position).getLevel()) {
                    case "1":
                        return 3;
                    case "2":
                    case "3":
                        return 1;
                    default:
                        return 1;
                }
            }
        });
        mLevelTwoRecyclerView.setLayoutManager(mLayoutManager);
        mLevelTwoRecyclerView.setHasFixedSize(true);
        mAdapter = new ShopClassAdapter(new ArrayList<ShopClassModel>());
        mLevelTwoRecyclerView.setAdapter(mAdapter);
        int windowWidth = Utils.getWindowWidth(getActivity());
        mAdapter.itemWidth = (int) Math.round(windowWidth * 0.75 / 3.5);

        mAdapter.setOnItemClickListener(new ShopClassAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, ShopClassModel data) {
                //标题不加点击事件
                if(!data.getLevel().equals("1")){
                    openShopStreetActivity(data);
                }
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
            case HTTP_WHAT_SHOP_CLASS:
                refreshCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_shop_class;
        mLevelOneCategories = new ArrayList<>();
        mLevelTwoCategories = new ArrayList<>();
        refresh();
    }

    private void openShopStreetActivity(ShopClassModel data) {
        Intent intent = new Intent();
        intent.putExtra("cls_id", data.getCls_level()+"_"+data.getCls_id()+"_"+data.getParent_id());
        intent.putExtra("cls_name", data.getCls_name());
        intent.setClass(getActivity(), ShopStreetActivity.class);
        startActivity(intent);
    }

    private void refreshCategory(int position) {
        ShopClassModel category = mLevelOneCategories.get(position);
        category.setLevel("1");

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

    public void refresh() {
        CommonRequest mRefreshRequest = new CommonRequest(Api.API_SHOP_CLASS, HttpWhat
                .HTTP_WHAT_SHOP_CLASS.getValue());
        addRequest(mRefreshRequest);
    }

    private void refreshCallback(String response) {

        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model mData) {
                //后台可以控制是否显示，但接口只返回能显示的分类，所以不用进行is_show判断
                if(mData.data.cls_list.size() == 0){
                    mEmptyView.setVisibility(View.VISIBLE);
                    mEmptyTitle.setText(R.string.emptyClass);
                }else {
                    mLevelOneRecyclerView.setVisibility(View.VISIBLE);

                    int i = 0;
                    for(ClsListModel clsListModel:mData.data.cls_list){
                        ShopClassModel categoryModel = new ShopClassModel();
                        categoryModel.setCls_id(clsListModel.cls_id);
                        categoryModel.setCls_name(clsListModel.cls_name);
                        categoryModel.setCls_image(clsListModel.cls_image);
                        categoryModel.setCls_list_2(clsListModel.cls_list_2);
                        categoryModel.setCls_level(clsListModel.cls_level);
                        categoryModel.setParent_id(clsListModel.parent_id);
                        if (i == 0) {
                            categoryModel.setClick(true);
                        }
                        mLevelOneCategories.add(categoryModel);
                        i++;

                        mLevelOneAdapter.setData(mLevelOneCategories);
                        mLevelOneAdapter.notifyDataSetChanged();
                    }

                    ShopClassModel category = mLevelOneCategories.get(0);
                    if(category.getCls_list_2().size()!=0){
                        refreshSubcategory(category);
                    }else{
                        mEmptyView.setVisibility(View.VISIBLE);
                        mEmptyTitle.setText(R.string.emptyClass);
                    }

                }
            }
        });
    }

    private void refreshSubcategory(ShopClassModel categoryModel) {
        //之前做过判断，有数据才会走到这
        mLevelTwoRecyclerView.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);

        List<ClsList2Model> items = categoryModel.getCls_list_2();
        mLevelTwoCategories = new ArrayList<>();
        ArrayList<ShopClassModel> tempArray = new ArrayList<>();
        int j = 0;

        //一次循环取出热门和更多
        if (items.size()!=0) {
            for (int i = 0; i < items.size(); i++) {
                if(items.get(i).is_hot.equals("1")) {
                    if(j==0){
                        ShopClassModel title = new ShopClassModel();
                        title.setCls_id("");
                        title.setCls_name("Hot热门");
                        title.setCls_image("");
                        title.setLevel("1");
                        mLevelTwoCategories.add(title);
                        j++;
                    }
                    ShopClassModel subCategoryModel = new ShopClassModel();
                    subCategoryModel.setCls_id(items.get(i).cls_id);
                    subCategoryModel.setCls_name(items.get(i).cls_name);
                    subCategoryModel.setCls_image(items.get(i).cls_image);
                    subCategoryModel.setLevel("2");
                    subCategoryModel.setCls_level(items.get(i).cls_level);
                    subCategoryModel.setParent_id(items.get(i).parent_id);
                    mLevelTwoCategories.add(subCategoryModel);
                }

                if(i==0){
                    ShopClassModel title2 = new ShopClassModel();
                    title2.setCls_id("");
                    title2.setCls_name("More更多");
                    title2.setCls_image("");
                    title2.setLevel("1");
                    tempArray.add(title2);

                    ShopClassModel subCategoryModel = new ShopClassModel();
                    subCategoryModel.setCls_id("");
                    subCategoryModel.setCls_name("全部");
                    subCategoryModel.setCls_image("");
                    subCategoryModel.setLevel("3");
                    tempArray.add(subCategoryModel);
                }

                ShopClassModel subCategoryModel2 = new ShopClassModel();
                subCategoryModel2.setCls_id(items.get(i).cls_id);
                subCategoryModel2.setCls_name(items.get(i).cls_name);
                subCategoryModel2.setCls_image(items.get(i).cls_image);
                subCategoryModel2.setLevel("3");
                subCategoryModel2.setCls_level(items.get(i).cls_level);
                subCategoryModel2.setParent_id(items.get(i).parent_id);
                tempArray.add(subCategoryModel2);

            }
        }
        mLevelTwoCategories.addAll(tempArray);

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

}