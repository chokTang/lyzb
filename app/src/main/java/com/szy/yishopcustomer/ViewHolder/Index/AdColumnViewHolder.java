package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/8/27.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class AdColumnViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_index_ad_column_recyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.fragment_index_ad_column_relative_layout)
    public RelativeLayout relativeLayout;

    public boolean decorated;

    public DividerItemDecoration itemDecoration;
    public AdColumnViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        itemDecoration = new DividerItemDecoration(view.getContext(), LinearLayout.HORIZONTAL);
    }
}
