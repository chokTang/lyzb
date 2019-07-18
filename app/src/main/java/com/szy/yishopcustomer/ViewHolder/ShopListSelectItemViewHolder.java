package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopListSelectItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_index_shop_list_item_nameTextView)
    public TextView nameTextView;
    @BindView(R.id.fragment_index_shop_list_item_addressTextView)
    public TextView addressTextView;
    @BindView(R.id.fragment_index_shop_list_item_distanceTextView)
    public TextView distanceTextView;
    @BindView(R.id.fragment_index_shop_list_item_location)
    public LinearLayout location;

    public ShopListSelectItemViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
