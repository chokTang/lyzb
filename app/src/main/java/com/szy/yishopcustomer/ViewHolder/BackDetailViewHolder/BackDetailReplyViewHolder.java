package com.szy.yishopcustomer.ViewHolder.BackDetailViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.szy.common.View.CommonRecyclerView;
import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zongren on 16/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BackDetailReplyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_back_detail_title)
    public TextView mName;
    @BindView(R.id.recyclerView)
    public CommonRecyclerView mRecyclerView;
    @BindView(R.id.item_back_detail_time)
    public TextView mTime;

    public BackDetailReplyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
