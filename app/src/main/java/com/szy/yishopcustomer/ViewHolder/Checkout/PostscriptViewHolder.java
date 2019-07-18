package com.szy.yishopcustomer.ViewHolder.Checkout;

import android.support.v7.widget.RecyclerView;
import android.view.*;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.View.YSCBaseEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/7/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class PostscriptViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_checkout_postscript_contentCommonEditText)
    public YSCBaseEditText contentEditText;

    public PostscriptViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
