package com.lyzb.jbx.adapter.home.first;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.circle.DynamicCircleListAdapter;
import com.lyzb.jbx.inter.IRecycleAnyClickListener;
import com.lyzb.jbx.model.dynamic.DynamicModel;
import com.lyzb.jbx.model.follow.FollowAdoutUserModel;
import com.lyzb.jbx.model.follow.FollowHomeModel;
import com.szy.yishopcustomer.Util.App;

import java.util.ArrayList;
import java.util.List;

/**
 * 关注列表的数据
 */
public class FollowAdapter extends DynamicCircleListAdapter {

    private final static int TYPE_HEADER_FRIST = 0x292;//关注头部模块
    private final static int TYPE_HEADER_SECOND = 0x293;//可能认识的人

    //关注
    private FollowHomeModel model = null;

    //可能认识的人
    private List<FollowAdoutUserModel> mAdoutList = new ArrayList<>();
    private IRecycleAnyClickListener anyClickListener;

    public FollowAdapter(Context context, List<DynamicModel> list) {
        super(context, list);
        if (_list != null) {
            _list.add(0, new DynamicModel());
            _list.add(1, new DynamicModel());
        }
    }

    @Override
    protected void convert(BaseViewHolder holder, DynamicModel item) {
        switch (holder.getItemViewType()) {
            //关注的人
            case TYPE_HEADER_FRIST:
                holder.setVisible(R.id.image_first_header, false);
                holder.setVisible(R.id.image_second_header, false);
                holder.setVisible(R.id.image_three_header, false);
                if (App.getInstance().isLogin()) {
                    if (model != null) {
                        if (model.getFollowMeNumber() == 0) {
                            holder.setVisible(R.id.tv_follow_number, false);
                        } else {
                            holder.setVisible(R.id.tv_follow_number, true);
                        }
                        if (model.getList().size() > 0) {
                            holder.setVisible(R.id.image_first_header, true);
                            holder.setRoundImageUrl(R.id.image_first_header, model.getList().get(0).getHeadimg(), 20);
                        }
                        if (model.getList().size() > 1) {
                            holder.setVisible(R.id.image_second_header, true);
                            holder.setRoundImageUrl(R.id.image_second_header, model.getList().get(1).getHeadimg(), 20);
                        }
                        if (model.getList().size() > 2) {
                            holder.setVisible(R.id.image_three_header, true);
                            holder.setRoundImageUrl(R.id.image_three_header, model.getList().get(2).getHeadimg(), 20);
                        }
                        holder.setText(R.id.tv_follow_number, String.format("等%d人关注了您", model.getFollowMeNumber()));
                    } else {
                        holder.setVisible(R.id.tv_follow_number, false);
                    }
                } else {
                    holder.setVisible(R.id.tv_follow_number, false);
                }

                holder.addItemClickListener(R.id.focus_num);
                break;
            //可能认识的人
            case TYPE_HEADER_SECOND:
                FollowSecondHolder secondHolder = (FollowSecondHolder) holder;
                secondHolder.recommendAdapter.update(mAdoutList);

                holder.setVisible(R.id.tv_no_follow_value, model == null || model.getMyFollowNumber() == 0);

                holder.addItemClickListener(R.id.tv_follow_change);
                break;
        }
        super.convert(holder, item);
    }

    @Override
    protected BaseViewHolder onChildCreateViewHolder(int viewType, ViewGroup parent) {
        switch (viewType) {
            case TYPE_HEADER_FRIST:
                return new BaseViewHolder(_context, getItemView(R.layout.recycle_home_follow_first, parent));
            case TYPE_HEADER_SECOND:
                return new FollowSecondHolder(_context, getItemView(R.layout.recycle_home_follow_second, parent));
        }
        return super.onChildCreateViewHolder(viewType, parent);
    }

    @Override
    protected int getChildItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER_FRIST;
            case 1:
                return TYPE_HEADER_SECOND;
        }
        return super.getChildItemViewType(position);
    }

    public void noticeHeaderData(FollowHomeModel value) {
        model = value;
        notifyItemChanged(0);
    }

    public void noticeHeaderSecondData(List<FollowAdoutUserModel> list) {
        mAdoutList = list;
        notifyItemChanged(1);
    }

    @Override
    public void update(List<DynamicModel> items) {
        _list.clear();
        _list.add(0, new DynamicModel());
        _list.add(1, new DynamicModel());
        if (items != null) {
            _list.addAll(items);
        }
        notifyDataSetChanged();
    }

    class FollowSecondHolder extends BaseViewHolder {

        RecyclerView reycle_remmond_account;
        FollowRecommendAdapter recommendAdapter;

        public FollowSecondHolder(Context context, View itemView) {
            super(context, itemView);
            reycle_remmond_account = itemView.findViewById(R.id.reycle_remmond_account);
            recommendAdapter = new FollowRecommendAdapter(context, null);
            recommendAdapter.setLayoutManager(reycle_remmond_account, LinearLayoutManager.HORIZONTAL);
            reycle_remmond_account.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.HORIZONTAL_LIST, R.drawable.listdivider_white_10));
            reycle_remmond_account.setAdapter(recommendAdapter);

            reycle_remmond_account.addOnItemTouchListener(new OnRecycleItemClickListener() {

                @Override
                public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                    if (anyClickListener != null) {
                        anyClickListener.onItemClick(view, position, mAdoutList.get(position));
                    }
                }

                @Override
                public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                    switch (view.getId()) {
                        //关注
                        case R.id.tv_follow:
                            if (anyClickListener != null) {
                                anyClickListener.onItemClick(view, position, mAdoutList.get(position));
                            }
                            break;
                    }
                }
            });
        }
    }

    public void setAnyClickListener(IRecycleAnyClickListener anyClickListener) {
        this.anyClickListener = anyClickListener;
    }
}
