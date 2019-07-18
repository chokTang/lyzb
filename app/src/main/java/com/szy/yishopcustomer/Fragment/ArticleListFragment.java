package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;

import com.szy.common.Interface.OnAdapterItemClickListener;
import com.szy.yishopcustomer.Activity.YSCWebViewActivity;
import com.szy.yishopcustomer.Adapter.ArticleListAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.ArticleItemModel;

import java.util.ArrayList;

import butterknife.BindView;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * Created by zongren on 16/7/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ArticleListFragment extends YSCBaseFragment implements
        OnAdapterItemClickListener<ArticleItemModel> {

    @BindView(R.id.fragment_pullableLayout)
    PullableLayout fragment_pullableLayout;

    @BindView(R.id.fragment_article_list_recyclerView)
    RecyclerView mRecyclerView;
    private ArrayList<ArticleItemModel> mData;
    private LinearLayoutManager mLayoutManager;
    private ArticleListAdapter mAdapter;

    //默认加载第一页
    private int cur_page = 1;

    @Override
    public void onAdapterItemClicked(int position, ArticleItemModel item) {
        Intent intent = new Intent(getContext(), YSCWebViewActivity.class);
        intent.putExtra(Key.KEY_URL.getValue(), Api.API_ARTICLE + item.article_id);
        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_article_list;
        Bundle arguments = getArguments();
        if (arguments == null) {
            return;
        }
        mData = arguments.getParcelableArrayList(Key.KEY_ARTICLE_LIST.getValue());
        mAdapter = new ArticleListAdapter(mData);
        mAdapter.setAdapterItemClickListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new ItemSeparateDecoration());
        return view;
    }


    public static class ItemSeparateDecoration extends RecyclerView.ItemDecoration {
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView
                .State state) {
            outRect.top = 1;
        }
    }
}
