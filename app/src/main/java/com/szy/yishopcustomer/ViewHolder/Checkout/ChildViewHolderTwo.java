package com.szy.yishopcustomer.ViewHolder.Checkout;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/7/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ChildViewHolderTwo extends RecyclerView.ViewHolder {

    @BindView(R.id.radioGroup)
    public RadioGroup radioGroup;
    @BindView(R.id.textView_since_some_tip)
    public TextView textView_since_some_tip;
    @BindView(R.id.textView_since_some_modify)
    public TextView textView_since_some_modify;
    @BindView(R.id.linearlayout_since_some)
    public LinearLayout linearlayout_since_some;

    public ChildViewHolderTwo(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
