package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zongren on 16/6/25.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class NearShopTitleViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_index_near_shop_title_wrapperBorderRelativeLayout)
    public RelativeLayout mLayout;
    @BindView(R.id.fragment_index_near_shop_title_moreImageView)
    public ImageView mImageView;

    public NearShopTitleViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
