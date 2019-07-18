package com.szy.yishopcustomer.ViewHolder.Recharge;

import android.support.v7.widget.RecyclerView;
import android.view.*;

import com.szy.common.View.CommonEditText;
import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/7/26.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class RechargeInputViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_recharge_item_input_editText)
    public CommonEditText editText;

    public RechargeInputViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
