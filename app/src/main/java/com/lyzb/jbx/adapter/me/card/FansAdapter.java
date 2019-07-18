package com.lyzb.jbx.adapter.me.card;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.home.search.SearchAccountDynamicItemAdapter;
import com.lyzb.jbx.fragment.dynamic.DynamicDetailFragment;
import com.lyzb.jbx.model.common.DynamicItemModel;
import com.lyzb.jbx.model.me.FansModel;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 粉丝适配器
 */
public class FansAdapter extends BaseRecyleAdapter<FansModel> {

    private BaseFragment fragment;
    private boolean isMe = true;

    public FansAdapter(Context context, List<FansModel> list) {
        super(context, -1, list);
    }

    public void setFragment(BaseFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    protected void convert(BaseViewHolder holder, FansModel item) {
        DynamicHolder dynamicHolder = (DynamicHolder) holder;
        dynamicHolder.addItemClickListener(R.id.recycle_account_dynamic);
        if (item.getGsTopicVo() != null && item.getGsTopicVo().getTotal() > 2) {
            dynamicHolder.itemAdapter.update(item.getGsTopicVo().getList().subList(0, 2));
            dynamicHolder.setVisible(R.id.tv_dynamic_number, true);
            dynamicHolder.setText(R.id.tv_dynamic_number, String.format("查看全部的%d条动态 >", item.getGsTopicVo().getTotal()));
        } else {
            dynamicHolder.itemAdapter.update(new ArrayList<DynamicItemModel>());
            dynamicHolder.setVisible(R.id.tv_dynamic_number, false);
        }
        headerInit(dynamicHolder, item);

        dynamicHolder.addItemClickListener(R.id.recycle_account_dynamic);
        dynamicHolder.addItemClickListener(R.id.tv_dynamic_number);
    }

    @Override
    protected BaseViewHolder onChildCreateViewHolder(int viewType, ViewGroup parent) {
        return new DynamicHolder(_context, getItemView(R.layout.recycle_account_dynamic, parent));
    }

    class DynamicHolder extends BaseViewHolder {

        RecyclerView recycle_account_dynamic;
        SearchAccountDynamicItemAdapter itemAdapter;

        public DynamicHolder(final Context context, View itemView) {
            super(context, itemView);
            recycle_account_dynamic = itemView.findViewById(R.id.recycle_account_dynamic);

            itemAdapter = new SearchAccountDynamicItemAdapter(context, null);
            recycle_account_dynamic.setAdapter(itemAdapter);
            recycle_account_dynamic.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_white_10));
            itemAdapter.setLayoutManager(recycle_account_dynamic);
            recycle_account_dynamic.setRecyclerListener(new RecyclerView.RecyclerListener() {
                @Override
                public void onViewRecycled(RecyclerView.ViewHolder holder) {
                    if (holder instanceof SearchAccountDynamicItemAdapter.VideoHolder) {
                        NiceVideoPlayer niceVideoPlayer = ((SearchAccountDynamicItemAdapter.VideoHolder) holder).videoPlayer;
                        if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                            NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                        }
                    }
                }
            });

            recycle_account_dynamic.addOnItemTouchListener(new OnRecycleItemClickListener() {
                @Override
                public void onItemClick(BaseRecyleAdapter adapter, final View view, final int position) {
                    view.setTag("");
                    view.postDelayed(new Runnable() {//处理item中的特殊点击
                        @Override
                        public void run() {
                            Object tag = view.getTag();
                            if (tag == null || TextUtils.isEmpty(tag.toString())) {
                                if (fragment != null) {
                                    fragment.start(DynamicDetailFragment.Companion.newIntance(itemAdapter.getPositionModel(position).getId()));
                                }
                            }
                        }
                    }, 50);
                }
            });
        }
    }

    private void headerInit(BaseViewHolder holder, FansModel model) {
        holder.setVisible(R.id.tv_circle_dynamic, false);

        holder.setRadiusImageUrl(R.id.img_header, model.getHeadimg(), 4);
        holder.setText(R.id.tv_header_name, model.getGsName());
        holder.setVisible(R.id.img_vip, model.getUserVipAction().size() > 0);
        holder.setText(R.id.tv_header_company, model.getShopName());
        holder.setVisible(R.id.tv_follow, model.getRelationNum() > 0 && isMe);
        if (model.getInterestNum() > 0) {
            holder.setText(R.id.tv_follow, "相互关注");
        } else {
            holder.setText(R.id.tv_follow, "已关注");
        }
        holder.setVisible(R.id.tv_no_follow, model.getRelationNum() == 0 && isMe);

        holder.addItemClickListener(R.id.tv_follow);
        holder.addItemClickListener(R.id.tv_no_follow);
        holder.addItemClickListener(R.id.img_header);
    }

    public void setMe(boolean me) {
        isMe = me;
    }
}
