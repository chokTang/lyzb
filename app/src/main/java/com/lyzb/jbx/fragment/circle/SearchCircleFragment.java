package com.lyzb.jbx.fragment.circle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.dialog.fragment.AlertDialogFragment;
import com.like.longshaolib.widget.ClearEditText;
import com.like.utilslib.app.CommonUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.circle.SearchCircleAdapter;
import com.lyzb.jbx.fragment.home.search.HomeSearchFragment;
import com.lyzb.jbx.model.circle.CircleModel;
import com.lyzb.jbx.model.params.ApplyCircleBody;
import com.lyzb.jbx.mvp.presenter.circle.SearchCirclePresenter;
import com.lyzb.jbx.mvp.view.circle.ISearchCircleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

/**
 * 搜索圈子 列表
 */
public class SearchCircleFragment extends BaseFragment<SearchCirclePresenter>
        implements ISearchCircleView, OnRefreshLoadMoreListener {

    private static final String PARAMS_VALUE = "PARAMS_VALUE";
    private String mSearchValue = "";
    private boolean isFromMoreCircle = false;

    private SmartRefreshLayout refresh_circle;
    private RecyclerView recycle_circle;
    private View empty_view;
    private ClearEditText edt_circle_search_value;
    private ImageView img_circle_search;
    private LinearLayout ll_circle_sreach;

    private SearchCircleAdapter mAdapter;

    public static SearchCircleFragment newIntance(String value) {
        SearchCircleFragment fragment = new SearchCircleFragment();
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
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        refresh_circle = findViewById(R.id.refresh_circle);
        recycle_circle = findViewById(R.id.recycle_circle);
        empty_view = findViewById(R.id.empty_view);
        edt_circle_search_value = findViewById(R.id.edt_circle_search_value);
        img_circle_search = findViewById(R.id.img_circle_search);
        ll_circle_sreach = findViewById(R.id.ll_circle_sreach);


        refresh_circle.setOnRefreshLoadMoreListener(this);

        mAdapter = new SearchCircleAdapter(getContext(), null);
        mAdapter.setLayoutManager(recycle_circle);
        recycle_circle.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_window_10));
        recycle_circle.setAdapter(mAdapter);
        //判断是否为更多圈子进来的
        if (!TextUtils.isEmpty(mSearchValue) && mSearchValue.equals(MoreCircleFragment.FROM_MORE_CIRCLE)) {
            isFromMoreCircle = true;
            ll_circle_sreach.setVisibility(View.VISIBLE);
            edt_circle_search_value.post(new Runnable() {
                @Override
                public void run() {
                    showSoftInput(edt_circle_search_value);
                }
            });
        } else {
            isFromMoreCircle = false;
            ll_circle_sreach.setVisibility(View.GONE);
        }
        //返回键
        findViewById(R.id.img_circle_sreach_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        //键盘上的搜索,调更多圈子接口
        edt_circle_search_value.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    String value = edt_circle_search_value.getText().toString().trim();
                    if (TextUtils.isEmpty(value)) {
                        showToast("请输入内容");
                        return true;
                    }
                    mSearchValue = value;
                    mPresenter.getMoreCircle(true, mSearchValue);
                }
                return true;
            }
        });
        //右上角的搜索,调更多圈子接口
        img_circle_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = edt_circle_search_value.getText().toString().trim();
                if (TextUtils.isEmpty(value)) {
                    showToast("请输入内容");
                    return;
                }
                mSearchValue = value;
                mPresenter.getMoreCircle(true, mSearchValue);
            }
        });
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        recycle_circle.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                //进入圈子
                if (getParentFragment() instanceof HomeSearchFragment){
                    childStart(CircleDetailFragment.newIntance(mAdapter.getPositionModel(position).getId()));
                }else {
                    start(CircleDetailFragment.newIntance(mAdapter.getPositionModel(position).getId()));
                }
            }

            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, final int position) {
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()) {
                    case R.id.circle_more_item_status_tv:
                        //未加入的才能加入
                        if (CommonUtil.converToT(mAdapter.getList().get(position).getIsJoin(), 0) != 3) {
                            return;
                        }
                        //申请加入圈子
                        AlertDialogFragment.newIntance()
                                .setContent("是否加入该圈子？")
                                .setCancleBtn(null)
                                .setSureBtn(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mPresenter.applyCir(new ApplyCircleBody(mAdapter.getPositionModel(position).getId()));
                                    }
                                })
                                .show(getFragmentManager(), "ApplyCircleTag");
                        break;
                    default:
                }

            }
        });
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_search_circle;
    }

    @Override
    public void onSearchCircle(boolean isrefresh, List<CircleModel> list) {
        if (isrefresh) {
            refresh_circle.finishRefresh();
            mAdapter.update(list);
            if (list.size() < 10) {
                refresh_circle.finishLoadMoreWithNoMoreData();
            }
        } else {
            mAdapter.addAll(list);
            if (list.size() < 10) {
                refresh_circle.finishLoadMoreWithNoMoreData();
            } else {
                refresh_circle.finishLoadMore();
            }
        }
        empty_view.setVisibility(mAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onFinshRequest(boolean isrefresh) {
        if (isrefresh) {
            refresh_circle.finishRefresh();
        } else {
            refresh_circle.finishLoadMore();
        }
    }

    @Override
    public void onApply(String groupId) {
        //有需要审核的圈子也有不需要审核的   做下判断,真为不需要
        for (int i = 0; i < mAdapter.getList().size(); i++) {
            CircleModel b = mAdapter.getList().get(i);
            if (b.getId().equals(groupId)) {
                if (b.isPublicStr()) {
                    b.setIsJoin("2");
                } else {
                    b.setIsJoin("4");
                }
                mAdapter.notifyItemChanged(i);
            }
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        //更多圈子进来的就调更多圈子接口
        if (isFromMoreCircle) {
            mPresenter.getMoreCircle(false, mSearchValue);
        } else {
            mPresenter.getList(false, mSearchValue);
        }

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        //更多圈子进来的就调更多圈子接口
        if (isFromMoreCircle) {
            mPresenter.getMoreCircle(true, mSearchValue);
        } else {
            mPresenter.getList(true, mSearchValue);
        }
    }

    /**
     * 搜索发生改变时候调用
     *
     * @param value
     */
    public void notifySeacrhValue(String value) {
        mSearchValue = value;
        mPresenter.getList(true, mSearchValue);
    }

    @Override
    public boolean isDelayedData() {
        return false;
    }
}
