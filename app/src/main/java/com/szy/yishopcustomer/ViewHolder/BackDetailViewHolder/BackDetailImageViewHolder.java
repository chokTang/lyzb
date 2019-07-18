package com.szy.yishopcustomer.ViewHolder.BackDetailViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.RelativeLayout;

import com.szy.common.View.CommonRecyclerView;
import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zongren on 16/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BackDetailImageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_complaint_imageRecyclerView)
    public CommonRecyclerView mRecyclerView;
    @BindView(R.id.fragment_complaint_imageLayout)
    public RelativeLayout mRelativeLayout;

    public BackDetailImageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
