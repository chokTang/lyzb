package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 2017/7/5.
 */

public class IntegrationDetailViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textView_note)
    public TextView textView_note;
    @BindView(R.id.textView_changed_points)
    public TextView textView_changed_points;
    @BindView(R.id.textView_add_time)
    public TextView textView_add_time;


    public IntegrationDetailViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
