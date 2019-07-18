package com.lyzb.jbx.fragment.circle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseToolbarFragment;
import com.like.longshaolib.dialog.fragment.AlertDialogFragment;
import com.like.utilslib.image.LoadImageUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.CircleMerberAdapter;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.me.CircleDetModel;
import com.lyzb.jbx.model.me.CircleMerberModel;
import com.lyzb.jbx.model.me.CircleOpeModel;
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
import butterknife.Unbinder;

/**
 * @author wyx
 * @role 圈子成员
 * @time 2019 2019/3/13 17:51
 */

public class CircleMemberFragment extends BaseToolbarFragment<CircleMerberPresenter>
        implements ICircleMerView, OnRefreshLoadMoreListener {

    public static final String DATA_KEY = "DATA_KEY";
    @BindView(R.id.tv_circle_apply)
    TextView mTvCircleApply;
    @BindView(R.id.tv_circle_apply_number)
    TextView mTvCircleApplyNumber;
    @BindView(R.id.lin_circle_applylist)
    LinearLayout mLinCircleApplylist;
    @BindView(R.id.tv_circle_merber_head)
    ImageView mTvCircleMerberHead;
    @BindView(R.id.tv_circle_merber_name)
    TextView mTvCircleMerberName;
    @BindView(R.id.tv_circle_merber_com)
    TextView mTvCircleMerberCom;
    @BindView(R.id.tv_circle_merber_number)
    TextView mTvCircleMerberNumber;
    @BindView(R.id.recy_circle_merber)
    RecyclerView mRecyCircleMerber;
    @BindView(R.id.sf_circle_merber)
    SmartRefreshLayout mSfCircleMerber;
    @BindView(R.id.tv_circle_out)
    TextView mTvCircleOut;
    @BindView(R.id.tv_circle_search)
    TextView mTvCircleSearch;
    @BindView(R.id.fl_circle_search)
    FrameLayout mFlCircleSearch;
    Unbinder unbinder;
    private CircleDetModel mDetModel = null;

    private CircleMerberAdapter mMerberAdapter = null;

    private List<CircleMerberModel.DataBean.ListBean> mebList = new ArrayList<>();
    private boolean isOwner = false;

    private boolean isOut = false; //用于标示 是退出圈子（true） 解散圈子（false）
    private String hintTitle = null;
    private boolean isNeedRefresh = false;//是否要刷新数据
    private CircleMerberModel mMerberModel;

    public static CircleMemberFragment newIntance(CircleDetModel model) {
        CircleMemberFragment fragment = new CircleMemberFragment();
        Bundle args = new Bundle();
        args.putSerializable(DATA_KEY, model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mDetModel = (CircleDetModel) bundle.getSerializable(DATA_KEY);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();
        setToolbarTitle("全部成员");
        ButterKnife.bind(this, mView);

        findViewById(R.id.lin_circle_applylist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //申请列表
                if (mDetModel.getNotApplyCount() > 0) {
                    isNeedRefresh = true;
                    start(ApplyListFragment.newTance(mDetModel.getId()));
                } else {
                    showToast("暂无申请成员");
                }
            }
        });

        mTvCircleOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CircleOpeModel model = new CircleOpeModel();
                model.setId(mDetModel.getId());
                if (isOut) {
                    hintTitle = "是否要退出圈子?";
                } else {
                    hintTitle = "是否要解散圈子?";
                }
                AlertDialogFragment.newIntance()
                        .setContent(hintTitle)
                        .setCancleBtn(null)
                        .setSureBtn(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPresenter.extCircle(isOut, model);
                            }
                        })
                        .show(getFragmentManager(), "EixttTag");

            }
        });

        mTvCircleSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(SearchCircleMemberFragment.newIntance(mDetModel));
            }
        });
        mMerberAdapter = new CircleMerberAdapter(getContext(), null);
        mMerberAdapter.setLayoutManager(mRecyCircleMerber);
        mRecyCircleMerber.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST));
        mRecyCircleMerber.setAdapter(mMerberAdapter);

        mRecyCircleMerber.addOnItemTouchListener(new OnRecycleItemClickListener() {

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

        mSfCircleMerber.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        if (mDetModel != null) {
            //初始化圈主信息 及未申请人员数
            LoadImageUtil.loadRoundImage(mTvCircleMerberHead, mDetModel.getOwnerVo().getHeadimg(), 4);
            mTvCircleMerberName.setText(mDetModel.getOwnerVo().getUserName());
            mTvCircleApplyNumber.setText(String.valueOf(mDetModel.getNotApplyCount()));
            //圈主的头像点击
            mTvCircleMerberHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    start(CardFragment.newIntance(2, mDetModel.getOwnerVo().getUserId()));
                }
            });
        }
        mPresenter.getList(true, mDetModel.getId(), "");
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_un_me_circle_merber;
    }

    @Override
    public void getMebList(boolean isRefresh, CircleMerberModel model) {
        if (model == null) {
            return;
        }
        mMerberModel = model;
        //如果没有加入
        if (!model.isIsJoin()) {
            mTvCircleOut.setVisibility(View.GONE);
        } else {
            mTvCircleOut.setVisibility(View.VISIBLE);
            if (model.isIsOwner()) {
                mLinCircleApplylist.setVisibility(View.VISIBLE);
                mFlCircleSearch.setVisibility(View.VISIBLE);
                isOut = false;
                isOwner = true;
                mTvCircleOut.setText("解散圈子");
                //企业圈子不允许解散
                if (!TextUtils.isEmpty(mDetModel.getOrgId())) {
                    mTvCircleOut.setVisibility(View.GONE);
                }
            } else {
                mLinCircleApplylist.setVisibility(View.GONE);
                isOut = true;
                isOwner = false;
                mTvCircleOut.setText("退出圈子");
            }
        }
        mebList = model.getData().getList();
        //移除群主这个item
        for (int i = 0; i < mebList.size(); i++) {
            if (mebList.get(i).isOwner()) {
                mTvCircleMerberCom.setText(mebList.get(i).getCompanyInfo());
                mebList.remove(i);
            }
        }
        mMerberAdapter.isOwner(isOwner);
        if (isRefresh) {
            mSfCircleMerber.finishRefresh();
            mMerberAdapter.update(mebList);
        } else {
            mSfCircleMerber.finishLoadMore();
            mMerberAdapter.addAll(mebList);
        }
        mTvCircleMerberNumber.setText("其他成员(" + (model.getData().getTotal() - 1) + "人)");
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
        mTvCircleMerberNumber.setText("其他成员(" + (mMerberModel.getData().getTotal() - 1) + "人)");
        if (mMerberAdapter.getItemCount() == 1) {
            mPresenter.getList(true, mDetModel.getId(), "");
        }
    }

    @Override
    public void onGetCircleMemberNum(int number) {
        mDetModel.setNotApplyCount(number);
        mTvCircleApplyNumber.setText(String.valueOf(mDetModel.getNotApplyCount()));
        isNeedRefresh = false;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (isNeedRefresh) {
            mPresenter.getData(mDetModel.getId());
            mPresenter.getList(true, mDetModel.getId(), "");
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(false, mDetModel.getId(), "");
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(true, mDetModel.getId(), "");
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
}
