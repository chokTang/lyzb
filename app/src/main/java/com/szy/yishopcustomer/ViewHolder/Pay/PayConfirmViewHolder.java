package com.szy.yishopcustomer.ViewHolder.Pay;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.Button;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/8/2.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class PayConfirmViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.submit_button)
    public Button button;

    public PayConfirmViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
