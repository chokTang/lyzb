package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuzhifeng on 2016/8/5 0005.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class ExpressImageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_express_imageView)
    public ImageView imageView;

    public ExpressImageViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
