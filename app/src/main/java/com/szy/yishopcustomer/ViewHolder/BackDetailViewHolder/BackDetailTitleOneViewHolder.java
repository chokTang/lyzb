package com.szy.yishopcustomer.ViewHolder.BackDetailViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zongren on 16/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BackDetailTitleOneViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_back_detail_one_imageView)
    public ImageView mStatusImageview;

    @BindView(R.id.item_back_detail_one_title_textView)
    public TextView mTitle;

    @BindView(R.id.item_back_detail_one_name_one)
    public TextView mNameOne;
    @BindView(R.id.item_back_detail_one_name_two)
    public TextView mNameTwo;
    @BindView(R.id.item_back_detail_one_name_three)
    public TextView mNameThree;

    @BindView(R.id.item_back_detail_one_TextView_one)
    public TextView mTextViewOne;
    @BindView(R.id.item_back_detail_one_TextView_two)
    public TextView mTextViewTwo;
    @BindView(R.id.item_back_detail_one_TextView_three)
    public TextView mTextViewThree;
    @BindView(R.id.item_back_detail_button_layout)
    public LinearLayout mButtonLayout;


    public BackDetailTitleOneViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
