package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zongren on 16/7/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ArticleListItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_article_list_item_article_titleTextView)
    public TextView titleTextView;
    @BindView(R.id.fragment_article_list_item_article_dateTextView)
    public TextView dateTextView;

    public ArticleListItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
