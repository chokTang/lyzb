package com.szy.yishopcustomer.ViewHolder.Back;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.RelativeLayout;

import com.szy.common.View.CommonRecyclerView;
import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 2017/3/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BackApplyImageViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_back_apply_imageRecyclerView)
    public CommonRecyclerView mImageRecyclerView;
    @BindView(R.id.fragment_back_apply_pic_relativeLayout)
    public RelativeLayout mPicRelativeLayout;

    public BackApplyImageViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
