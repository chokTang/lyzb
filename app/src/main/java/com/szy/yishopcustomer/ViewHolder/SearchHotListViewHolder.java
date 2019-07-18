package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.View.FlowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 2017/12/11.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class SearchHotListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.flowLayout_hot)
    public FlowLayout flowLayout_hot;

    @BindView(R.id.LinearLayout_hot)
    public LinearLayout linearLayout_hot;

    public SearchHotListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
