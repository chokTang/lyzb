package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuzhifeng on 2016/12/1.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class EmptyItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_goods_list_item_imageView)
    public ImageView emptyImageview;

    public EmptyItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
