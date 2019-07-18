package com.lyzb.jbx.fragment.me.publish;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseStatusFragment;
import com.like.longshaolib.dialog.fragment.AlertDialogFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.publish.PubReplyAdapter;
import com.lyzb.jbx.fragment.dynamic.DynamicDetailFragment;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.inter.IRecycleAnyClickListener;
import com.lyzb.jbx.model.me.PubReplyCommentModel;
import com.lyzb.jbx.model.me.PubReplyModel;
import com.lyzb.jbx.mvp.presenter.me.PubReplyPresenter;
import com.lyzb.jbx.mvp.view.me.IPubReplyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

/**
 * @author wyx
 * @role 我的发表-回复
 * @time 2019 2019/3/6 17:40
 */

public class PubReplyFragment extends BaseStatusFragment<PubReplyPresenter> implements IPubReplyView, OnRefreshLoadMoreListener {

    private SmartRefreshLayout sr_un_me_pub_reply;
    private RecyclerView recy_un_me_pub_reply;
    private View empty_view;

    private PubReplyAdapter mReplyAdapter = null;

    @Override
    public Integer getMainResId() {
        return R.layout.fragment_un_me_pub_reply;
    }

    @Override
    public void onLazyRequest() {
        mPresenter.getList(true);
    }

    @Override
    public void onAgainRequest() {
        onLazyRequest();
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        sr_un_me_pub_reply = (SmartRefreshLayout) main_view;
        recy_un_me_pub_reply = findViewById(R.id.recy_un_me_pub_reply);
        empty_view = findViewById(R.id.empty_view);

        sr_un_me_pub_reply.setOnRefreshListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mReplyAdapter = new PubReplyAdapter(getContext(), null);
        mReplyAdapter.setLayoutManager(recy_un_me_pub_reply);
        recy_un_me_pub_reply.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST,
                R.drawable.listdivider_window_10));
        recy_un_me_pub_reply.setAdapter(mReplyAdapter);

        recy_un_me_pub_reply.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_un_pub_reply_text:
                        childStart(DynamicDetailFragment.Companion.newIntance(mReplyAdapter.getPositionModel(position).getTopicId()));
                        break;
                    //点击头像
                    case R.id.img_un_pub_reply_head:
                        start(CardFragment.newIntance(2, mReplyAdapter.getPositionModel(position).getUserId()));
                        break;
                }
            }
        });

        mReplyAdapter.setListener(new IRecycleAnyClickListener() {
            @Override
            public void onItemClick(View view, int position, final Object item) {
                AlertDialogFragment.newIntance()
                        .setSureBtn(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                PubReplyCommentModel model = (PubReplyCommentModel) item;
                                mPresenter.deleteDynamic(model.getId());
                            }
                        })
                        .setCancleBtn(null)
                        .setContent("是否删除该条回复？")
                        .show(getFragmentManager(), "deteletTag");
            }
        });
    }

    @Override
    public void onRelyListResult(boolean isRefresh, List<PubReplyModel> list) {
        if (isRefresh) {
            sr_un_me_pub_reply.finishRefresh();
            mReplyAdapter.update(list);
            if (list.size() < 10) {
                sr_un_me_pub_reply.finishLoadMoreWithNoMoreData();
            }
        } else {
            mReplyAdapter.addAll(list);
            if (list.size() < 10) {
                sr_un_me_pub_reply.finishLoadMoreWithNoMoreData();
            } else {
                sr_un_me_pub_reply.finishLoadMore();
            }
        }
        if (mReplyAdapter.getItemCount() == 0) {
            empty_view.setVisibility(View.VISIBLE);
        } else {
            empty_view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFinshOrLoadMore(boolean isFresh) {
        if (isFresh) {
            sr_un_me_pub_reply.finishRefresh();
        } else {
            sr_un_me_pub_reply.finishLoadMore();
        }
    }

    @Override
    public void onDeleteResult() {
        mPresenter.getList(true);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getList(true);
    }
}
