package com.lyzb.jbx.fragment.school;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.widget.AutoWidthTabLayout;
import com.like.longshaolib.widget.ClearEditText;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.school.ArticleAdapter;
import com.lyzb.jbx.adapter.school.ArticlePagerApdater;
import com.lyzb.jbx.model.school.SchoolModel;
import com.lyzb.jbx.model.school.SchoolTypeModel;
import com.lyzb.jbx.mvp.presenter.school.SchoolListPresenter;
import com.lyzb.jbx.mvp.view.school.IBusinessSchoolView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Util.App;

import java.util.ArrayList;
import java.util.List;

/**
 * 共商学院列表
 */
public class BusinessSchoolListFragment extends BaseFragment<SchoolListPresenter> implements IBusinessSchoolView,
        View.OnClickListener, OnRefreshLoadMoreListener {

    private static String PARAMS_TYPE = "params_type";
    private int mType = 1;//1:共商学院列表，2：帮助中心

    private ImageView img_back;
    private TextView tv_school_title;
    private ImageView img_search_school;
    private LinearLayout layout_school_search;
    private ClearEditText edt_search_value;
    private TextView btn_search;

    //渲染tab数据
    private AutoWidthTabLayout tab_school;
    private ViewPager pager_school;
    private ArticlePagerApdater pagerApdater;

    //搜索数据
    private SmartRefreshLayout refresh_school;
    private RecyclerView recycle_school;
    private View empty_view;
    private String mSearchValue = "";
    private ArticleAdapter mAdapter;

    public static BusinessSchoolListFragment newIntance(int type) {
        BusinessSchoolListFragment fragment = new BusinessSchoolListFragment();
        Bundle args = new Bundle();
        args.putInt(PARAMS_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mType = args.getInt(PARAMS_TYPE, 1);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        img_back = findViewById(R.id.img_back);
        img_search_school = findViewById(R.id.img_search_school);
        layout_school_search = findViewById(R.id.layout_school_search);
        edt_search_value = findViewById(R.id.edt_search_value);
        btn_search = findViewById(R.id.btn_search);
        tab_school = findViewById(R.id.tab_school);
        refresh_school = findViewById(R.id.refresh_school);
        recycle_school = findViewById(R.id.recycle_school);
        empty_view = findViewById(R.id.empty_view);
        pager_school = findViewById(R.id.pager_school);
        tv_school_title = findViewById(R.id.tv_school_title);

        img_back.setOnClickListener(this);
        img_search_school.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        refresh_school.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        tv_school_title.setText(mType == 1 ? "共商学院" : "帮助中心");

        mAdapter = new ArticleAdapter(getContext(), null);
        mAdapter.setLayoutManager(recycle_school);
        recycle_school.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST));
        recycle_school.setAdapter(mAdapter);

        edt_search_value.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    String value = edt_search_value.getText().toString().trim();
                    if (TextUtils.isEmpty(value)) {
                        showToast("请输入内容");
                        return true;
                    }
                    onSearchData(value);
                }
                return true;
            }
        });

        pager_school.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tab_school.getTabLayout().getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //请求数据
        mPresenter.getArticleTypeList(mType);

        recycle_school.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                //跳转到详情
//                start(ArticleDetailFragment.newIntance(mAdapter.getPositionModel(position).getArticleId()));
                Intent intent = new Intent(getContext(), ProjectH5Activity.class);
                intent.putExtra(Key.KEY_URL.getValue(), String.format("%s/union/college/article?articleId=%s", Api.H5_CITYLIFE_URL
                        , mAdapter.getPositionModel(position).getArticleId()));
                startActivity(intent);
            }

            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    //点赞操作
                    case R.id.tv_zan_number:
                        if (App.getInstance().isLogin()) {
                            view.startAnimation(AnimationUtils.loadAnimation(
                                    getContext(), R.anim.zan_anim));
                            mPresenter.zan(mAdapter.getPositionModel(position).getArticleId(),
                                    mAdapter.getPositionModel(position).isZan() ? 2 : 1, position);
                        } else {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        }
                        break;
                }
            }
        });
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_school_list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                pop();
                break;
            //显示搜索
            case R.id.img_search_school:
                img_search_school.setVisibility(View.GONE);
                layout_school_search.setVisibility(View.VISIBLE);
                break;
            //搜索
            case R.id.btn_search:
                String value = edt_search_value.getText().toString().trim();
                if (TextUtils.isEmpty(value)) {
                    showToast("请输入内容");
                    return;
                }
                onSearchData(value);
                break;
        }
    }

    @Override
    public void onSchoolResult(boolean isfresh, List<SchoolModel> list) {
        if (isfresh) {
            refresh_school.finishRefresh();
            mAdapter.update(list);
            if (list.size() < 10) {
                refresh_school.finishLoadMoreWithNoMoreData();
            }
        } else {
            mAdapter.addAll(list);
            if (list.size() < 10) {
                refresh_school.finishLoadMoreWithNoMoreData();
            } else {
                refresh_school.finishLoadMore();
            }
        }
        if (mAdapter.getItemCount() == 0) {
            empty_view.setVisibility(View.VISIBLE);
        } else {
            empty_view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onGetArticleTypeList(List<SchoolTypeModel> list) {
        List<BaseFragment> fragments = new ArrayList<>();
//        List<SchoolTypeModel> childList = list.subList(0, 10);
        for (SchoolTypeModel model : list) {
            fragments.add(SchoolItemFragment.newIntance(model.getCatId(),mType));
            tab_school.addTab(model.getCatName());
        }
        pagerApdater = new ArticlePagerApdater(getChildFragmentManager(), fragments);
        pager_school.setAdapter(pagerApdater);
        tab_school.setupWithViewPager(pager_school);
    }

    //点赞回调
    @Override
    public void onZanReuslt(int position) {
        SchoolModel model = mAdapter.getPositionModel(position);
        model.setZan(!model.isZan());
        model.setArticleThumb(model.isZan() ? model.getArticleThumb() + 1 : model.getArticleThumb() - 1);
        mAdapter.change(position, model);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getArticleListBySearch(false, mSearchValue,mType);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getArticleListBySearch(true, mSearchValue,mType);
    }

    private void onSearchData(String value) {
        hideSoftInput();
        mSearchValue = value;
        tab_school.setVisibility(View.GONE);
        pager_school.setVisibility(View.GONE);
        refresh_school.setVisibility(View.VISIBLE);
        mPresenter.getArticleListBySearch(true, mSearchValue,mType);
    }
}
