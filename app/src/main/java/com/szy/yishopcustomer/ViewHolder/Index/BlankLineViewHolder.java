package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.LinearLayout;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/8/27.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BlankLineViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_index_blank)
    public LinearLayout blank;

    public BlankLineViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
