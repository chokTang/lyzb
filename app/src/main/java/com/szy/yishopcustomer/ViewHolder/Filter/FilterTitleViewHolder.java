package com.szy.yishopcustomer.ViewHolder.Filter;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 2016/8/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class FilterTitleViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_filter_title_textView)
    public TextView textView;
    @BindView(R.id.fragment_filter_title_allImageView)
    public ImageView imageView;
    @BindView(R.id.fragment_filter_title_allTextView)
    public TextView allTextView;

    public FilterTitleViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
