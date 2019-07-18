package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TYK.
 */
public class ShopNewJoinViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_title)
    public ImageView tvTitle;
    @BindView(R.id.rv_product)
    public RecyclerView recyclerView;


    public ShopNewJoinViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
