package com.szy.yishopcustomer.ViewHolder.Back;

import android.support.v7.widget.RecyclerView;
import android.view.*;

import com.szy.common.View.CommonEditText;
import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 2017/3/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BackApplyRefundViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.back_apply_refund_content)
    public CommonEditText mContent;

    public BackApplyRefundViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
