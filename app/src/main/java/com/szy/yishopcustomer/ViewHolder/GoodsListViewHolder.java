package com.szy.yishopcustomer.ViewHolder;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szy.common.View.SquareImageView;
import com.szy.yishopcustomer.Constant.Api;
import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/8/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_goods_list_goods_type)
    public TextView goodsType;
    @BindView(R.id.fragment_goods_list_tip_image_view)
    public ImageView tipImageView;
    @BindView(R.id.fragment_goods_list_item_goodsImageView)
    public SquareImageView goodsImageView;
    @BindView(R.id.fragment_goods_list_item_goodsNameTextView)
    public TextView goodsNameTextView;
    @BindView(R.id.fragment_goods_list_item_goodsPriceTextView)
    public TextView goodsPriceTextView;
    @Nullable
    @BindView(R.id.fragment_goods_list_item_goodsPriceTextView_acer)
    public TextView goodsPriceTextViewAcer;
    @BindView(R.id.fragment_goods_list_item_numberTextView)
    public TextView numberTextView;
    @BindView(R.id.fragment_goods_list_item_minusImageView)
    public ImageView minusImageView;
    @BindView(R.id.fragment_goods_list_item_plusImageView)
    public ImageView plusImageView;
    @BindView(R.id.fragment_goods_list_item_seller_all_goodsImageView)
    public ImageView sellerImageView;
    @Nullable
    @BindView(R.id.fragment_goods_list_item_saleTextView)
    public TextView saleTextView;
    @Nullable
    @BindView(R.id.fragment_goods_list_shop_image_view)
    public ImageView shopImageView;
    @Nullable
    @BindView(R.id.fragment_goods_list_shop_name_text_view)
    public TextView shopTextView;
    @Nullable
    @BindView(R.id.fragment_goods_list_item_saleLabelTextView)
    public TextView saleTextViewLabel;
    @Nullable
    @BindView(R.id.fragment_goods_list_disable_view)
    public View disableView;
    @Nullable
    @BindView(R.id.fragment_goods_list_item_deductibleTextView)
    public TextView deductibleTextView;
    @Nullable
    @BindView(R.id.fragment_goods_list_item_percentSaleProgressBar)
    public ProgressBar percentSaleProgressBar;
    @Nullable
    @BindView(R.id.fragment_goods_list_item_percentSaleTextView)
    public TextView percentSaleTextView;

    public Animation animation = new AlphaAnimation(0.1f, 1.0f);

    public GoodsListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);

        animation = new AlphaAnimation(0.1f, 1.0f);
        animation.setDuration(1000);
    }
}
