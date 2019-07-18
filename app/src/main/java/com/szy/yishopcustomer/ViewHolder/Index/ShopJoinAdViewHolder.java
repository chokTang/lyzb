package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TYK.
 */
public class ShopJoinAdViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_shop_join_count)
    public TextView tvShopJoinCount;

    @BindView(R.id.tv_text_shop_num)
    public TextView tv_text_shop_num;


    @BindView(R.id.rl_bg_shop_join_ad)
    public RelativeLayout rl_bg_shop_join_ad;



    public ShopJoinAdViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
