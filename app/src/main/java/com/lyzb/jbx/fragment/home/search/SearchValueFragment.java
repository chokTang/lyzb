package com.lyzb.jbx.fragment.home.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.home.search.SearchValueAdapter;
import com.lyzb.jbx.dbdata.SearchHistroyDb;
import com.lyzb.jbx.inter.IRecycleAnyClickListener;
import com.lyzb.jbx.model.search.HistroyModel;
import com.lyzb.jbx.mvp.presenter.home.search.SearchValuePresenter;
import com.lyzb.jbx.mvp.view.home.search.ISearchValueView;
import com.szy.yishopcustomer.Activity.ShopStreetActivity;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.ResponseModel.Search.SearchHintModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索结果页面
 */
public class SearchValueFragment extends BaseFragment<SearchValuePresenter> implements ISearchValueView {

    private RecyclerView recycle_search_list;
    private SearchValueAdapter mAdapter;

    private static final String PARAMS_VALUE = "PARAMS_VALUE";
    private String mSearchValue = "";

    private HomeSearchFragment parmentFragment;

    public static SearchValueFragment newIntance(String value) {
        SearchValueFragment fragment = new SearchValueFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_VALUE, value);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mSearchValue = args.getString(PARAMS_VALUE);
        }
        parmentFragment = (HomeSearchFragment) getParentFragment();
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        recycle_search_list = findViewById(R.id.recycle_search_list);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mAdapter = new SearchValueAdapter(getContext(), null);
        mAdapter.setLayoutManager(recycle_search_list);
        recycle_search_list.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST));
        recycle_search_list.setAdapter(mAdapter);

        recycle_search_list.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                SearchHistroyDb.getIntance().insert(new HistroyModel(mSearchValue));
                switch (position) {
                    //用户
                    case 0:
                        parmentFragment.startFragment(3, mSearchValue);
                        break;
                    //圈子
                    case 1:
                        parmentFragment.startFragment(4, mSearchValue);
                        break;
                    //动态
                    case 2:
                        parmentFragment.startFragment(2, mSearchValue);
                        break;
                    //店铺
                    case 3:
                        Intent intent = new Intent();
                        intent.putExtra(Key.KEY_KEYWORD.getValue(), mSearchValue);
                        intent.setClass(getActivity(), ShopStreetActivity.class);
                        startActivity(intent);
                        break;
                }
            }

            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                switch (view.getId()){
                    //搜索商品
                    case R.id.tv_second_value:
                        SearchHistroyDb.getIntance().insert(new HistroyModel(mSearchValue));
                        parmentFragment.startFragment(5, mSearchValue);
                        break;
                }
            }
        });

        mAdapter.setClickListener(new IRecycleAnyClickListener() {
            @Override
            public void onItemClick(View view, int position, Object item) {
                SearchHistroyDb.getIntance().insert(new HistroyModel(mSearchValue));
                parmentFragment.startFragment(5, String.format("%s  %s", mSearchValue, item.toString()));

            }
        });

        onDataNoticeChange(mSearchValue);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_search_value;
    }

    @Override
    public void onSearchResultList(List<SearchHintModel> list) {
        mAdapter.update(mSearchValue, list);
    }

    public void onDataNoticeChange(String value) {
        mSearchValue = value;
        if (TextUtils.isEmpty(value)) {
            mAdapter.update(new ArrayList<SearchHintModel>());
        } else {
            mPresenter.getList(value);
        }
    }

    @Override
    public boolean isDelayedData() {
        return false;
    }
}
