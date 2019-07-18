package com.szy.yishopcustomer.ViewHolder.Result;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/8/1.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class TopViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_result_top_imageView)
    public ImageView imageView;
    @BindView(R.id.fragment_result_top_resultTextView)
    public TextView resultTextView;
    @BindView(R.id.fragment_result_top_moneyLabelTextView)
    public TextView moneyLabelTextView;
    @BindView(R.id.fragment_result_top_moneyTextView)
    public TextView moneyTextView;

    @BindView(R.id.linear_pay_suc_ingot)
    public LinearLayout mLayout_pay_sucess;
    @BindView(R.id.linearlayout_order_info)
    public View linearlayout_order_info;
    @BindView(R.id.imageView_order_sn)
    public ImageView imageView_order_sn;
    @BindView(R.id.textView_order_id)
    public TextView textView_order_id;
    @BindView(R.id.textView_time)
    public TextView textView_time;
    @BindView(R.id.look_ingot_text)
    public TextView text_look_ingot;
    @BindView(R.id.fragment_result_top_ingotLabelTextView)
    public TextView text_cons_ingot;
    @BindView(R.id.text_pay_ingot_number)
    public TextView text_pay_ingot;

    public TopViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
