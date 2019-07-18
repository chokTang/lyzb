package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 宗仁 on 16/8/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsGroupOnViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.comment_headimg)
    public CircleImageView mUserImageView;
    @BindView(R.id.shop_name)
    public TextView mShopName;
    @BindView(R.id.groupOn_timer)
    public CountdownView mTime;
    @BindView(R.id.groupOn_number)
    public TextView mGroupOnNumber;
    @BindView(R.id.goupOn_shop)
    public TextView mGoFightGroup;

    @BindView(R.id.textView12)
    public TextView textView12;

    public GoodsGroupOnViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(this);
    }
}
