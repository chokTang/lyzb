package com.szy.yishopcustomer.ViewHolder.Cart;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 2016/8/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CartUnpayedMoreViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_cart_unpayed_list_more)
    public TextView unpayedMore;

    public CartUnpayedMoreViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

}
