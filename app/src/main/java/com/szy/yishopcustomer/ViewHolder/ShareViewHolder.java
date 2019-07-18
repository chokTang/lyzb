package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/8/17.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShareViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_share_item_imageView)
    public ImageView imageView;
    @BindView(R.id.fragment_share_item_titleTextView)
    public TextView titleTextView;

    public ShareViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
