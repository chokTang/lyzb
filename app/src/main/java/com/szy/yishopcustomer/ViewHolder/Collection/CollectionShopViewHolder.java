package com.szy.yishopcustomer.ViewHolder.Collection;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 17/1/6.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CollectionShopViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.collection_shop_logo)
    public ImageView collectionShopLogo;
    @BindView(R.id.collection_shop_name_textView)
    public TextView shopNameTextView;
    @BindView(R.id.right_arrow)
    public ImageView rightArrowImageView;

    public CollectionShopViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
