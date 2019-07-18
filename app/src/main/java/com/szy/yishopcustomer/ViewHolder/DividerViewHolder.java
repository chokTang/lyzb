package com.szy.yishopcustomer.ViewHolder;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.*;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 2016/11/29.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DividerViewHolder extends RecyclerView.ViewHolder {

    @Nullable
    @BindView(R.id.view_line)
    public View view_line;

    public DividerViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
