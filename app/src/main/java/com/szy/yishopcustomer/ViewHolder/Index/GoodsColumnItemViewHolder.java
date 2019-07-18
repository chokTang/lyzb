package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/8/29.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsColumnItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_index_goods_thumbImageView)
    public ImageView thumbImageView;
    @BindView(R.id.fragment_index_goods_nameTextView)
    public TextView nameTextView;

    @Nullable
    @BindView(R.id.fragment_index_gooods_ingot)
    public TextView ingotText;
    @BindView(R.id.fragment_index_goods_priceTextView)
    public TextView priceTextView;

    @BindView(R.id.fragment_index_goods_tag)
    public ImageView goodsTag;

    @BindView(R.id.fragment_index_goods_deductibleTextView)
    public TextView deductibleTextView;//宝箱价

    public GoodsColumnItemViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
