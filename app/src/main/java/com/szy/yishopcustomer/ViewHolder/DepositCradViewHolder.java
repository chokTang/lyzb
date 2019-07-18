package com.szy.yishopcustomer.ViewHolder;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 2017/5/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DepositCradViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textView_par_value)
    public TextView textView_par_value;
    @BindView(R.id.textView_card_name)
    public TextView textView_card_name;
    @BindView(R.id.textView_card_number)
    public TextView textView_card_number;
    @BindView(R.id.textView_used_time)
    public TextView textView_used_time;

    @BindView(R.id.imageView_used_state)
    public ImageView imageView_used_state;

    public DepositCradViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
