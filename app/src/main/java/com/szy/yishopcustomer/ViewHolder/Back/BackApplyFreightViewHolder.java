package com.szy.yishopcustomer.ViewHolder.Back;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 运费方式选择
 * Created by 老子都不好意思写谁做的 on 2017/3/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BackApplyFreightViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.back_apply_freight_content)
    public TextView back_apply_freight_content;
    @BindView(R.id.freight_rl)
    public RelativeLayout freight_rl;

    public BackApplyFreightViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
