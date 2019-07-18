package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.View.MyItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 17/10/31.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class VideoViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_index_video_recyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.fragment_index_video_relative_layout)
    public View relativeLayout;

    @BindView(R.id.textViewTitle)
    public TextView textViewTitle;

    public MyItemDecoration myItemDecoration;

    public VideoViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);

        myItemDecoration = new MyItemDecoration(view.getContext(), MyItemDecoration.HORIZONTAL);
        myItemDecoration.setDrawable(view.getContext().getResources().getDrawable(R.drawable.divider_bg));
    }
}
