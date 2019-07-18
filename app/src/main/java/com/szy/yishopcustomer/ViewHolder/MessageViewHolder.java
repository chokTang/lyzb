package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szy.yishopcustomer.Adapter.BonusAdapter;
import com.szy.yishopcustomer.Adapter.MessageAdapter;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.View.SlidingButtonView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zongren on 16/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class MessageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.message_layout)
    public RelativeLayout imageLayout;
    @BindView(R.id.item_message_imageView)
    public ImageView iconImageView;
    @BindView(R.id.item_message_titleTextView)
    public TextView titleTextView;
    @BindView(R.id.item_message_contentTextView)
    public TextView contentTextView;
    @BindView(R.id.item_message_dateTextView)
    public TextView dateTextView;
    @BindView(R.id.item_message_status)
    public TextView messageStatus;

    public MessageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
