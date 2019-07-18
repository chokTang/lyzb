package com.szy.yishopcustomer.ViewHolder.Result;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szy.common.View.CommonRecyclerView;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.DividerGridItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/8/1.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ConfirmViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_result_confirm_orderListButton)
    public Button orderListButton;
    @BindView(R.id.fragment_result_confirm_continueShoppingButton)
    public Button continueShoppingButton;
    @BindView(R.id.fragment_result_confirm_wrapperLinearLayout)
    public LinearLayout mWrapperLinearLayout;
    @BindView(R.id.fragment_result_top_descriptionTextView)
    public TextView descriptionTextView;

    @BindView(R.id.fragment_result_confirm_wrapperLinearLayout2)
    public RelativeLayout mWrapperLinearLayout2;
    @BindView(R.id.fragment_result_confirm_continueShoppingButton2)
    public Button continueShoppingButton2;

    @BindView(R.id.tv_pay_like_title)
    public TextView mTextView_LikeTitle;
    @BindView(R.id.recy_pay_success)
    public CommonRecyclerView mRecyclerView_Like;

    public ConfirmViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);

        mRecyclerView_Like.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        mRecyclerView_Like.addItemDecoration(new DividerGridItemDecoration(view.getContext(), R.drawable.recy_grid_line));
        mRecyclerView_Like.setFocusable(false);
        mRecyclerView_Like.requestFocus();
    }
}
