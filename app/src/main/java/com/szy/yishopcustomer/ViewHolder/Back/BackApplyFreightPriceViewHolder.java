package com.szy.yishopcustomer.ViewHolder.Back;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szy.common.View.CommonEditText;
import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 运费金额输入框
 * Created by 老子都不好意思写谁做的 on 2017/3/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BackApplyFreightPriceViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.back_apply_yunfeicontent)
    public CommonEditText back_apply_yunfeicontent;
    @BindView(R.id.yunfei_rl)
    public RelativeLayout yunfei_rl;

    public BackApplyFreightPriceViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
