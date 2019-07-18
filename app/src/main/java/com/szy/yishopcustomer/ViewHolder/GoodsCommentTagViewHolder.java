package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.View.FilterCheckBox;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/8/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsCommentTagViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_goods_comment_tag_textView)
    public FilterCheckBox textView;

    public GoodsCommentTagViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
