package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zongren on 16/5/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ArticleItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_index_article_item_TextView)
    public TextView textView;

    public ArticleItemViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
