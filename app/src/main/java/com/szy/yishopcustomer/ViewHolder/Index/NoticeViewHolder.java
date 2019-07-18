package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.View.MarqueeView;
import com.szy.yishopcustomer.View.ReadView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 17/5/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class NoticeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.marqueeView)
    public MarqueeView marqueeView;
    @BindView(R.id.readView)
    public ReadView readView;
    @BindView(R.id.fragment_index_notice_imageView)
    public ImageView imageView;

    public boolean flag = false;

    public NoticeViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
