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

public class SearchHotViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_search_hot_textView)
    public TextView mTextView;


    public SearchHotViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
