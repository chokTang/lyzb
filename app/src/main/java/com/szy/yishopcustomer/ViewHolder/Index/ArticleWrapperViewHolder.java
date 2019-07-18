package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/8/27.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ArticleWrapperViewHolder extends RecyclerView.ViewHolder {
    public ArticleItemViewHolder firstItemViewHolder;
    public ArticleItemViewHolder secondItemViewHolder;

    @BindView(R.id.fragment_index_article_wrapper_first)
    public View firstView;
    @BindView(R.id.fragment_index_article_wrapper_second)
    public View secondView;

    public ArticleWrapperViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);

        TextView tv1 = (TextView) firstView.findViewById(R.id.fragment_index_article_item_textView);
        tv1.setText("超赞");

        TextView tv2 = (TextView) secondView.findViewById(R.id.fragment_index_article_item_textView);
        tv2.setText("热文");

        firstItemViewHolder = new ArticleItemViewHolder(firstView);
        secondItemViewHolder = new ArticleItemViewHolder(secondView);
    }
}
