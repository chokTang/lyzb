package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 2017/2/13.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class SearchEmptyViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.activity_search_empty_text_view)
    public TextView mTextView;

    public SearchEmptyViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
