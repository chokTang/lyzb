package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 2017/12/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class SearchDeleteViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.linearlayout_delete_search)
    public LinearLayout linearlayout_delete_search;

    public SearchDeleteViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
