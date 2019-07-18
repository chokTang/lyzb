package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TYK.
 *
 */
public class VideoShowViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.rv_home_video_show)
    public RecyclerView recyclerViewVideoShow;

    @BindView(R.id.tv_right_title)
    public TextView tv_right_title;


    public VideoShowViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
