package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 16/5/5.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class EmptyViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.empty_view_imageView)
    public ImageView imageView;
    @BindView(R.id.empty_view_titleTextView)
    public TextView title;
    @BindView(R.id.empty_view_subtitleTextView)
    public TextView subTitle;
    @BindView(R.id.empty_view_button)
    public Button button;

    public EmptyViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }
}