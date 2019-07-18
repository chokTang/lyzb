package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuzhifeng on 2017/2/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class SearchTitleViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.activity_search_history_text_view)
    public TextView mTextView;

    @BindView(R.id.view_line)
    public View view_line;

    @BindView(R.id.line)
    public View line;

    public SearchTitleViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
