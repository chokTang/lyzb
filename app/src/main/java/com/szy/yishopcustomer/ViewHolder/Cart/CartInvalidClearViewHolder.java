package com.szy.yishopcustomer.ViewHolder.Cart;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 17/2/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CartInvalidClearViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_cart_invalid_clear_button)
    public TextView clearButton;


    public CartInvalidClearViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
