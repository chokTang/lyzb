package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuzhifeng on 2016/7/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class DetailViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_fragment_detail_text_view_one)
    public TextView mTextViewOne;
    @BindView(R.id.item_fragment_detail_text_view_two)
    public TextView mTextViewTwo;
    @BindView(R.id.item_fragment_detail_text_view_three)
    public TextView mTextViewThree;
    @BindView(R.id.item_fragment_detail_text_view_four)
    public TextView mTextViewFour;

    public DetailViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
