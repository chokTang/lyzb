package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.View.ArticleViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TYK.
 *
 */
public class IngotsBuyViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.rv_home_ingot_buy)
    public RecyclerView recyclerViewIngotsBuy;

    @BindView(R.id.tv_right_title)
    public TextView tv_right_title;


    public IngotsBuyViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
