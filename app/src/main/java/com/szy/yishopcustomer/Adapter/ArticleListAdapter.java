package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;

import com.szy.common.Interface.OnAdapterItemClickListener;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.ArticleItemModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.ArticleListItemViewHolder;

import java.util.List;

/**
 * Created by zongren on 16/7/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListItemViewHolder> {
    private List<ArticleItemModel> mData;
    private OnAdapterItemClickListener<ArticleItemModel> mAdapterItemClickListener;

    public ArticleListAdapter(List<ArticleItemModel> data) {
        mData = data;
    }

    @Override
    public ArticleListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_article_list_item_article, parent, false);
        ArticleListItemViewHolder viewHolder = new ArticleListItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ArticleListItemViewHolder holder, int position) {
        ArticleItemModel itemModel = mData.get(position);
        holder.titleTextView.setText(itemModel.title);
        if(!Utils.isNull(itemModel.add_time)){
            holder.dateTextView.setText(Utils.toDateString(itemModel.add_time));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAdapterItemClickListener != null) {
                    mAdapterItemClickListener.onAdapterItemClicked(holder.getAdapterPosition(),
                            mData.get(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setAdapterItemClickListener(OnAdapterItemClickListener<ArticleItemModel>
                                                    adapterItemClickListener) {
        mAdapterItemClickListener = adapterItemClickListener;
    }
}
