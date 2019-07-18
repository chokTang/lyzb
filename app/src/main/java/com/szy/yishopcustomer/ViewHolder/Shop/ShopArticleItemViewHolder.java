package com.szy.yishopcustomer.ViewHolder.Shop;

import android.view.*;
import android.widget.TextView;

import com.szy.common.ViewHolder.CommonViewHolder;
import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buqingqiang on 2016/8/23.
 */
public class ShopArticleItemViewHolder extends CommonViewHolder {
    @BindView(R.id.fragment_shop_article_textView)
    public TextView shopArticleTextView;

    public ShopArticleItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, view);
    }
}
