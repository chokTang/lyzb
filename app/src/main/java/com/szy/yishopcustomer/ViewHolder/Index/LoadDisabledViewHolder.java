package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zongren on 16/6/25.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class LoadDisabledViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_index_load_disabled_text)
    public TextView loadDisabledText;

    public LoadDisabledViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
