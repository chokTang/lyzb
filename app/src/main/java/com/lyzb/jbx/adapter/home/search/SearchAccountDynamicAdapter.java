package com.lyzb.jbx.adapter.home.search;

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
import com.lyzb.jbx.fragment.dynamic.DynamicDetailFragment;
import com.lyzb.jbx.fragment.home.search.SearchAccountDynamicFragment;
import com.lyzb.jbx.model.account.SearchAccountModel;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import java.util.List;

public class SearchAccountDynamicAdapter extends BaseRecyleAdapter<SearchAccountModel> {

    private BaseFragment fragment;

    public SearchAccountDynamicAdapter(Context context, List<SearchAccountModel> list) {
        super(context, -1, list);
    }

    public void setFragment(BaseFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    protected void convert(BaseViewHolder holder, SearchAccountModel item) {
        DynamicHolder dynamicHolder = (DynamicHolder) holder;
        if (item.getGsTopicVo().getTotal() > 2) {
            dynamicHolder.itemAdapter.update(item.getGsTopicVo().getList().subList(0, 2));
            dynamicHolder.setVisible(R.id.tv_dynamic_number, true);
        } else {
            dynamicHolder.itemAdapter.update(item.getGsTopicVo().getList());
            dynamicHolder.setVisible(R.id.tv_dynamic_number, false);
        }
        dynamicHolder.setText(R.id.tv_dynamic_number, String.format("查看全部的%d条动态 >", item.getGsTopicVo().getTotal()));

        dynamicHolder.addItemClickListener(R.id.tv_dynamic_number);
        headerInit(dynamicHolder, item);
    }

    @Override
    protected BaseViewHolder onChildCreateViewHolder(int viewType, ViewGroup parent) {
        return new DynamicHolder(_context, getItemView(R.layout.recycle_account_dynamic, parent));
    }

    class DynamicHolder extends BaseViewHolder {

        RecyclerView recycle_account_dynamic;
        SearchAccountDynamicItemAdapter itemAdapter;

        public DynamicHolder(Context context, View itemView) {
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
                                    if (fragment instanceof SearchAccountDynamicFragment) {//判断是否是用户动态
                                        ((BaseFragment) fragment.getParentFragment()).start(DynamicDetailFragment.Companion.newIntance(
                                                itemAdapter.getPositionModel(position).getId()));
                                    } else {
                                        fragment.start(DynamicDetailFragment.Companion.newIntance(itemAdapter.getPositionModel(position).getId()));
                                    }
                                }
                            }
                        }
                    }, 50);
                }
            });
        }
    }

    private void headerInit(BaseViewHolder holder, SearchAccountModel model) {
        holder.setVisible(R.id.tv_circle_dynamic, false);

        holder.setRadiusImageUrl(R.id.img_header, model.getHeadimg(), 4);
        holder.setText(R.id.tv_header_name, model.getGsName());
        holder.setVisible(R.id.img_vip, model.getUserVipAction().size() > 0);
        holder.setText(R.id.tv_header_company, model.getShopName());
        holder.setVisible(R.id.tv_follow, model.getRelationNum() > 0);
        holder.setVisible(R.id.tv_no_follow, model.getRelationNum() == 0);

        holder.addItemClickListener(R.id.tv_follow);
        holder.addItemClickListener(R.id.tv_no_follow);
        holder.addItemClickListener(R.id.img_header);
    }
}
