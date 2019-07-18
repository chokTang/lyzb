package com.szy.yishopcustomer.ViewHolder.Filter;

import android.support.v7.widget.RecyclerView;
import android.view.*;

import com.szy.common.View.CommonEditText;
import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/8/23.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class FilterInputViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_filter_input_minimumEditText)
    public CommonEditText minimumEditText;
    @BindView(R.id.fragment_filter_input_maximumEditText)
    public CommonEditText maximumEditText;

    public FilterInputViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
