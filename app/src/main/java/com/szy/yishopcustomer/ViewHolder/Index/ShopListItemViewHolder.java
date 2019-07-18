package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/8/29.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopListItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_index_shop_list_item_imageView)
    public ImageView imageView;
    @BindView(R.id.fragment_index_shop_list_item_nameTextView)
    public TextView nameTextView;
    @BindView(R.id.fragment_index_shop_list_item_distanceTextView)
    public TextView distanceTextView;
    @BindView(R.id.fragment_index_shop_list_item_location)
    public LinearLayout location;
    @BindView(R.id.fragment_index_shop_list_item_rankImageView)
    public ImageView rankImageView;
    @BindView(R.id.fragment_shop_street_sleep)
    public TextView sleep;
    @BindView(R.id.fragment_index_shop_list_item_saleTextView)
    public TextView saleTextView;
    @BindView(R.id.fragment_index_shop_list_item_relativeLayout)
    public RelativeLayout all;
    @BindView(R.id.fragment_shop_recommend_num)
    public TextView fragment_shop_recommend_num;
    @BindView(R.id.fragment_index_shop_list_item_support_shop_tip)
    public TextView supportShopTip;

    @BindView(R.id.fragment_index_shop_list_item_synopsisTextView)
    public TextView synopsisTextView;

    public ShopListItemViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
