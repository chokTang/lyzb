package com.szy.yishopcustomer.ViewHolder.Index;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zongren on 16/5/30.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DummyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_index_shop_list_recyclerView)
    public RecyclerView recyclerView;

    @BindView(R.id.linearlayout_empty)
    public LinearLayout linearlayout_empty;
    @BindView(R.id.textView_empty_tip)
    public TextView textView_empty_tip;

    @BindView(R.id.linearlayout_root)
    public View linearlayout_root;

    public DummyViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
