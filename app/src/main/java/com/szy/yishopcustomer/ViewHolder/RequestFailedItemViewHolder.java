package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 2017/1/13.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class RequestFailedItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_goods_list_item_request_failed_textView)
    public TextView textView;

    public RequestFailedItemViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
