package com.szy.yishopcustomer.ViewHolder;

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

public class RecommStoreViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textView_shop_name)
    public TextView textView_shop_name;
    @BindView(R.id.textView_server_tel)
    public TextView textView_server_tel;
    @BindView(R.id.textView_address_info)
    public TextView textView_address_info;
    @BindView(R.id.imageView_status)
    public ImageView imageView_status;

    public RecommStoreViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
