package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 17/10/31.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class VideoItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_index_videoView)
    public ImageView imageView;
    @BindView(R.id.fragment_index_video_relativeLayout)
    public RelativeLayout layout;

    public VideoItemViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
