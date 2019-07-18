package com.szy.yishopcustomer.ViewHolder.Back;

import android.support.v7.widget.RecyclerView;
import android.view.*;

import com.szy.common.View.CommonEditText;
import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SB on 2017/3/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BackApplyContentViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.back_apply_content)
    public CommonEditText mContent;

    public BackApplyContentViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
