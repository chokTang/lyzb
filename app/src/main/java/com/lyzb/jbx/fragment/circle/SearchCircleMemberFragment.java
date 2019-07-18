package com.lyzb.jbx.fragment.circle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.like.utilslib.screen.DensityUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.CircleMerberAdapter;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.me.CircleDetModel;
import com.lyzb.jbx.model.me.CircleMerberModel;
import com.lyzb.jbx.model.me.CircleRemModel;
import com.lyzb.jbx.mvp.presenter.me.CircleMerberPresenter;
import com.lyzb.jbx.mvp.view.me.ICircleMerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/25 19:35
 */

public class SearchCircleMemberFragment extends BaseFragment<CircleMerberPresenter>
        implements ICircleMerView, OnRefreshLoadMoreListener {
    @BindView(R.id.img_circle_sreach_back)
    ImageView mImgCircleSreachBack;
    @BindView(R.id.edt_circle_search_value)
    ClearEditText mEdtCircleSearchValue;
    @BindView(R.id.img_circle_search)
    ImageView mImgCircleSearch;
    @BindView(R.id.ll_circle_sreach)
    LinearLayout mLlCircleSreach;
    @BindView(R.id.recycle_circle)
    RecyclerView mRecycleCircle;
    @BindView(R.id.refresh_circle)
    SmartRefreshLayout mRefreshCircle;
    @BindView(R.id.empty_view)
    View mEmptyView;
    Unbinder unbinder;

    private List<CircleMerberModel.DataBean.ListBean> mebList = new ArrayList<>();
    private CircleMerberAdapter mMerberAdapter = null;
    private CircleMerberModel mMerberModel;
    private CircleDetModel mDetModel = null;
    private String shareValue;

    public static SearchCircleMemberFragment newIntance(CircleDetModel model) {
        SearchCircleMemberFragment fragment = new SearchCircleMemberFragment();
        Bundle args = new Bundle();
        args.putSerializable(CircleMemberFragment.DATA_KEY, model);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        mLlCircleSreach.setVisibility(View.VISIBLE);

        mMerberAdapter = new CircleMerberAdapter(getContext(), null);
        mMerberAdapter.setLayoutManager(mRecycleCircle);
        mRecycleCircle.setPadding(DensityUtil.dpTopx(10), 0, 0, 0);
        mRecycleCircle.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST));
        mRecycleCircle.setAdapter(mMerberAdapter);

        mRecycleCircle.addOnItemTouchListener(new OnRecycleItemClickListener() {

            @Override
            public void onItemChildClick(final BaseRecyleAdapter adapter, View view, final int position) {
                switch (view.getId()) {
                    //他的名片
                    case R.id.img_item_cir_merber_head:
                        CircleMerberModel.DataBean.ListBean model = (CircleMerberModel.DataBean.ListBean) adapter.getPositionModel(position);
                        start(CardFragment.newIntance(2, model.getUserId()));
                        break;
                    case R.id.tv_item_cir_merber_remove:
                        new AlertDialogFragment()
                                .setContent("是否移除该成员?")
                                .setCancleBtn(null)
                                .setSureBtn(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        CircleMerberModel.DataBean.ListBean bean = mMerberAdapter.getPositionModel(position);
                                        CircleRemModel remModel = new CircleRemModel();
                                        remModel.setId(bean.getGroupId());
                                        remModel.setUsername(bean.getMemberId());
                                        //移除该成员
                                        mPresenter.removePeople(remModel, position);
                                    }
                                }).show(getFragmentManager(), "deteleMemberTag");
                        break;
                    default:
                }
            }

            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                //进入他的名片
                CircleMerberModel.DataBean.ListBean model = (CircleMerberModel.DataBean.ListBean) adapter.getPositionModel(position);
                start(CardFragment.newIntance(2, model.getUserId()));
            }
        });

        mRefreshCircle.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mEdtCircleSearchValue.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    String value = mEdtCircleSearchValue.getText().toString().trim();
                    if (TextUtils.isEmpty(value)) {
                        showToast("请输入内容");
                        return true;
                    }
                    shareValue = value;
                    mPresenter.getList(true, mDetModel.getId(), shareValue);
                }
                return true;
            }
        });


        mEdtCircleSearchValue.post(new Runnable() {
            @Override
            public void run() {
                showSoftInput(mEdtCircleSearchValue);
            }
        });
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_search_circle;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mDetModel = (CircleDetModel) bundle.getSerializable(CircleMemberFragment.DATA_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getMebList(boolean isRefresh, CircleMerberModel model) {
        if (model == null || model.getData() == null || model.getData().getList() == null || model.getData().getList().size() < 1) {
            mEmptyView.setVisibility(View.VISIBLE);
            return;
        }
        mMerberModel = model;
        mebList = model.getData().getList();
        //移除群主这个item
        for (int i = 0; i < mebList.size(); i++) {
            if (mebList.get(i).isOwner()) {
                mebList.remove(i);
            }
        }
        mMerberAdapter.isOwner(true);
        if (isRefresh) {
            mRefreshCircle.finishRefresh();
            mMerberAdapter.update(mebList);
        } else {
            mRefreshCircle.finishLoadMore();
            mMerberAdapter.addAll(mebList);
        }
    }

    @Override
    public void onExit() {
        pop();
    }

    @Override
    public void onDiss() {
        pop();
    }

    @Override
    public void onRemove(int position) {
        mMerberAdapter.remove(position);
        mMerberModel.getData().setTotal(mMerberModel.getData().getTotal() - 1);
        if (mMerberAdapter.getItemCount() == 1) {
            mPresenter.getList(true, mDetModel.getId(), shareValue);
        }
    }

    @Override
    public void onGetCircleMemberNum(int number) {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (TextUtils.isEmpty(shareValue)) {
            mRefreshCircle.finishLoadMore();
            return;
        }
        mPresenter.getList(false, mDetModel.getId(), shareValue);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (TextUtils.isEmpty(shareValue)) {
            mRefreshCircle.finishRefresh();
            return;
        }
        mPresenter.getList(true, mDetModel.getId(), shareValue);
    }

    @OnClick({R.id.img_circle_sreach_back, R.id.img_circle_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_circle_sreach_back:
                pop();
                break;
            case R.id.img_circle_search:
                String value = mEdtCircleSearchValue.getText().toString().trim();
                if (TextUtils.isEmpty(value)) {
                    showToast("请输入内容");
                    return;
                }
                shareValue = value;
                mPresenter.getList(true, mDetModel.getId(), shareValue);
                break;
            default:
        }
    }
}
