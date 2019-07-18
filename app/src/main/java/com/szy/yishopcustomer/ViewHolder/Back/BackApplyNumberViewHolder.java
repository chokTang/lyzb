package com.szy.yishopcustomer.ViewHolder.Back;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 2017/3/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BackApplyNumberViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.back_apply_number_content)
    //public CommonEditText mContent;
    public TextView mContent;

    public BackApplyNumberViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
