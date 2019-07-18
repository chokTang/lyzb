package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zongren on 16/5/5.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DividerThreeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_divider_three_view)
    public View divider;

    public DividerThreeViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
