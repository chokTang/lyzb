package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v7.widget.RecyclerView;
import android.view.*;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 2018/1/3.
 */

public class GoodsNavigationWrapperViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_index_navigation_recyclerView)
    public RecyclerView recyclerView;

    public GoodsNavigationWrapperViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}