package com.szy.yishopcustomer.ViewHolder.Back;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.View.CommonEditText;
import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SB on 2017/3/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BackApplyTypeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_title)
    public TextView tv_title;
    @BindView(R.id.fragment_address_region_valueTextView)
    public TextView fragment_address_region_valueTextView;

    @BindView(R.id.edt_address_detail)
    public CommonEditText edt_address_detail;
    @BindView(R.id.address_regionLayout)
    public LinearLayout address_regionLayout;
    @BindView(R.id.address_detailLayout)
    public LinearLayout address_detailLayout;

    @BindView(R.id.tv_type1)
    public TextView tv_type1;
    @BindView(R.id.tv_type2)
    public TextView tv_type2;
    @BindView(R.id.tv_type3)
    public TextView tv_type3;

    public BackApplyTypeViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
