package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuzhifeng on 17/1/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class SearchViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.search_item_text_view)
    public TextView mTextView;

    public SearchViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
