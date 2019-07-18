package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/8/29.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class AdTitleViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_index_ad_title_left_line_view)
    public View leftLineView;
    @BindView(R.id.fragment_index_ad_title_right_line_view)
    public View rightLineView;
    @BindView(R.id.fragment_index_ad_title_textView)
    public TextView textView;

    public AdTitleViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
