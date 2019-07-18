package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuzhifeng on 2016/9/12.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class ScanShopGoodsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_scan_shop_goods_imageView)
    public ImageView mImageView;
    @BindView(R.id.item_scan_shop_name_textView)
    public TextView mGoodsName;
    @BindView(R.id.fragment_scan_shop_numberTextView)
    public TextView mPriceTextView;
    @BindView(R.id.item_scan_goods)
    public RelativeLayout mGoodsLayout;
    @BindView(R.id.fragment_scan_shop_goods_number)
    public TextView mGoodsNumberTextView;
    @BindView(R.id.fragment_scan_shop_plus)
    public ImageView mPlusImageView;
    @BindView(R.id.fragment_scan_shop_minus)
    public ImageView mMinusImageView;
    @BindView(R.id.fragment_scan_shop_percentSaleProgressBar)
    public ProgressBar percentSaleProgressBar;
    @BindView(R.id.fragment_scan_shop_percentSaleTextView)
    public TextView percentSaleTextView;
    @BindView(R.id.fragment_scan_shop_deductibleTextView)
    public TextView deductibleTextView;

    public ScanShopGoodsViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
