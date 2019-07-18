package com.szy.yishopcustomer.ViewHolder;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuzhifeng on 2017/2/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    @Nullable
    @BindView(R.id.item_category_imageButton)
    public ImageView imageButton;
    @BindView(R.id.item_category_textView)
    public TextView textView;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
