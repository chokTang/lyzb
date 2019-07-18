package com.szy.yishopcustomer.ViewHolder.GroupOn;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.View.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 16/8/2.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class UserGroupOnHeadImgViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.groupon_head_image)
    public CircleImageView headImage;
    @BindView(R.id.groupon_head_tipImage)
    public TextView headTipImage;

    public UserGroupOnHeadImgViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
